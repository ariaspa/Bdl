package it.lispa.bdl.client.vc.schedulatoretriggers;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.SchedulatoreTriggerDTO;
import it.lispa.bdl.shared.messages.SchedulatoreTriggersMsg;
import it.lispa.bdl.shared.services.SchedulatoreTriggersService;
import it.lispa.bdl.shared.services.SchedulatoreTriggersServiceAsync;

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
import com.sencha.gxt.widget.core.client.grid.Grid;

//http://stackoverflow.com/questions/10694589/how-to-use-gwts-editor-framework-with-gwt-platform
/**
 * Class SchedulatoreTriggersController.
 */
public class SchedulatoreTriggersController extends Presenter<SchedulatoreTriggersController.iSchedulatoreTriggersView, SchedulatoreTriggersController.iSchedulatoreTriggersProxy> {

	@Inject
	SchedulatoreTriggersMsg messages;

	SchedulatoreTriggersServiceAsync servizio = (SchedulatoreTriggersServiceAsync) GWT.create(SchedulatoreTriggersService.class);

	private EventBus eventBus;

	/**
	 * Interface iSchedulatoreTriggersView.
	 */
	public interface iSchedulatoreTriggersView extends View {

		/**
		 * Load grid data.
		 */
		public void loadGridData();
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
		public Grid<SchedulatoreTriggerDTO> getGrid();

		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
	}

	/**
	 * Interface iSchedulatoreTriggersProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.SchedulatoreTriggers)
	public interface iSchedulatoreTriggersProxy extends ProxyPlace<SchedulatoreTriggersController> {
	}

	/**
	 * Istanzia un nuovo amm gest utenti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public SchedulatoreTriggersController(final EventBus eventBus, final iSchedulatoreTriggersView view, final iSchedulatoreTriggersProxy proxy) {

		super(eventBus, view, proxy);
		this.eventBus = eventBus;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	private final SaveHandler saveHandler = new SaveHandler() {
		public void onSave(ChangeGridState event) {
			getView().loadGridData();
		}
	};
	@Override
	protected void onBind() {
		super.onBind();

		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));
		getView().getCancella().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				final List<SchedulatoreTriggerDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

				String message;
				if (gridItems.size() == 1) {
					message = messages.lMsgCancellaConfirm(gridItems.get(0).getDsTipo()+" "+gridItems.get(0).getDsInput());
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
	@Override
	protected void onReveal() {
		super.onReveal();
		eventBus.fireEvent(new ChangeGridState());
	}

}
