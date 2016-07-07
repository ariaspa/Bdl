package it.lispa.bdl.client.vc.ammvalidutenti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.AmmValidUtentiMsg;
import it.lispa.bdl.shared.services.AmmValidUtentiService;
import it.lispa.bdl.shared.services.AmmValidUtentiServiceAsync;

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
 * Class AmmValidUtentiController.
 */
public class AmmValidUtentiController extends
Presenter<AmmValidUtentiController.iAmmValidUtentiView, AmmValidUtentiController.iAmmValidUtentiProxy> {

	@Inject AmmValidUtentiMsg messages;
	
	AmmValidUtentiServiceAsync servizio = (AmmValidUtentiServiceAsync) GWT.create(AmmValidUtentiService.class);

	private EventBus eventBus;

	/**
	 * Interface iAmmValidUtentiView.
	 */
	public interface iAmmValidUtentiView extends View {
		
		/**
		 * Legge valida.
		 *
		 * @return valida
		 */
		public TextButton getValida();
		
		/**
		 * Legge visualizza.
		 *
		 * @return visualizza
		 */
		public TextButton getVisualizza();
		
		/**
		 * Load.
		 */
		public void load();
		
		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<UtenteDTO> getGrid();		
		
		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
	}

	/**
	 * Interface iAmmValidUtentiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.AmmValidUtenti)
	public interface iAmmValidUtentiProxy extends ProxyPlace<AmmValidUtentiController> {
	}

	private AmmValidUtentiFormController editorPresenter;
	private UtenteDTO gridItem;

	/**
	 * Istanzia un nuovo amm valid utenti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public AmmValidUtentiController(final EventBus eventBus, final iAmmValidUtentiView view,
			final iAmmValidUtentiProxy proxy, AmmValidUtentiFormController editorPresenter) {

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


		getView().getVisualizza().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				gridItem = getView().getGrid().getSelectionModel().getSelectedItem();
				
				editorPresenter.getView().refreshFields(gridItem);

				editorPresenter.getView().setHeadingText(gridItem.getNome()+" "+gridItem.getCognome());

				addToPopupSlot(editorPresenter);
			}
		});

		getView().getValida().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				final List<UtenteDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

				String message;
				if(gridItems.size() == 1) {
					message = messages.validazioneConfirm(gridItems.get(0).getNome()+" "+gridItems.get(0).getCognome());
				} else {
					message = messages.validazioneAllConfirm(gridItems.size());
				}

				ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
				box.setSize("420","130");
				box.addHideHandler(new HideHandler() {

					public void onHide(HideEvent event) {
						Dialog btn = (Dialog) event.getSource();
						if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
							servizio.validaUtenti(gridItems, new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Non fare nulla: l'errore generico viene gestito a monte...
									// e il metodo non tira un AsyncServiceException
								}
								@Override
								public void onSuccess(Void v) {
									eventBus.fireEvent(new ChangeGridState());
									new GXTMessageBox(messages.titlePanel(), messages.validazioneOk(), GXTMessageBox.DO_SHOW);
								}
							});
							
						} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
							// perform NO action
						}
					}
				});
				box.show();
			}
		});
	}

}
