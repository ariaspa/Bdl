package it.lispa.bdl.client.vc.ammgestutenti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.messages.AmmGestUtentiMsg;
import it.lispa.bdl.shared.services.AmmGestUtentiService;
import it.lispa.bdl.shared.services.AmmGestUtentiServiceAsync;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

//http://stackoverflow.com/questions/10694589/how-to-use-gwts-editor-framework-with-gwt-platform
/**
 * Class AmmGestUtentiController.
 */
public class AmmGestUtentiController extends Presenter<AmmGestUtentiController.iAmmGestUtentiView, AmmGestUtentiController.iAmmGestUtentiProxy> {

	@Inject
	AmmGestUtentiMsg messages;

	AmmGestUtentiServiceAsync servizio = (AmmGestUtentiServiceAsync) GWT.create(AmmGestUtentiService.class);

	private EventBus eventBus;

	/**
	 * Interface iAmmGestUtentiView.
	 */
	public interface iAmmGestUtentiView extends View {
		
		/**
		 * Legge inserisci.
		 *
		 * @return inserisci
		 */
		public TextButton getInserisci();

		/**
		 * Legge visualizza.
		 *
		 * @return visualizza
		 */
		public TextButton getVisualizza();

		/**
		 * Legge cancella.
		 *
		 * @return cancella
		 */
		public TextButton getCancella();

		/**
		 * Load.
		 */
		public void load();

		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<UtenteLightDTO> getGrid();

		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
	}

	/**
	 * Interface iAmmGestUtentiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.AmmGestUtenti)
	public interface iAmmGestUtentiProxy extends ProxyPlace<AmmGestUtentiController> {
	}

	private AmmGestUtentiFormController editorPresenter;
	private UtenteLightDTO gridItem;

	/**
	 * Istanzia un nuovo amm gest utenti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public AmmGestUtentiController(final EventBus eventBus, final iAmmGestUtentiView view, final iAmmGestUtentiProxy proxy,
			AmmGestUtentiFormController editorPresenter) {

		super(eventBus, view, proxy);
		this.eventBus = eventBus;
		this.editorPresenter = editorPresenter;
	}

	private final SaveHandler saveHandler = new SaveHandler() {

		public void onSave(ChangeGridState event) {
			getView().load();
		}
	};

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();

		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));

		editorPresenter.getView().getDialog().addHideHandler(new HideHandler() {
			public void onHide(HideEvent event) {
				getView().getPanel().unmask();
			}
		});
		editorPresenter.getView().getDialog().addShowHandler(new ShowHandler() {
			public void onShow(ShowEvent event) {
				getView().getPanel().mask();
			}
		});

		getView().getInserisci().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				editorPresenter.activateInsertForm();
				addToPopupSlot(editorPresenter);
			}
		});

		getView().getVisualizza().addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent event) {
				gridItem = getView().getGrid().getSelectionModel().getSelectedItem();
				servizio.getItemByCodice(gridItem.getCdUtente(), new AsyncCallback<UtenteDTO>() {
					public void onFailure(Throwable caught) {
						// Non fare nulla: l'errore generico viene gestito a monte...
					}

					@Override
					public void onSuccess(UtenteDTO item) {
						editorPresenter.activateView(item);
						addToPopupSlot(editorPresenter);
					}
				});
			}
		});

		getView().getCancella().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				final List<UtenteLightDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

				String message;
				if (gridItems.size() == 1) {
					message = messages.lMsgCancellaConfirm(gridItems.get(0).getNome() + " " + gridItems.get(0).getCognome());
				} else {
					message = messages.lMsgCancellaAllConfirm(gridItems.size());
				}

				ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
				box.setSize("400", "100");
				box.addHideHandler(new HideHandler() {

					public void onHide(HideEvent event) {
						Dialog btn = (Dialog) event.getSource();
						if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
							servizio.cancellaItems(gridItems, new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Non fare nulla: l'errore generico viene
									// gestito a monte...
									// e il metodo non tira un
									// AsyncServiceException
								}

								@Override
								public void onSuccess(Void v) {
									new GXTMessageBox(messages.titlePanel(), messages.lMsgCancellaEsito(), GXTMessageBox.DO_SHOW);
									eventBus.fireEvent(new ChangeGridState());
								}
							});

						} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())) {
							// perform NO action
						}
					}
				});
				box.show();
			}
		});
	}

}
