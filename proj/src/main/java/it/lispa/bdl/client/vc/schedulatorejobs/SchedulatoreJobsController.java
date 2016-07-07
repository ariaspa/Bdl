package it.lispa.bdl.client.vc.schedulatorejobs;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.messages.SchedulatoreJobsMsg;
import it.lispa.bdl.shared.services.SchedulatoreJobsService;
import it.lispa.bdl.shared.services.SchedulatoreJobsServiceAsync;

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
 * Class SchedulatoreJobsController.
 */
public class SchedulatoreJobsController extends Presenter<SchedulatoreJobsController.iSchedulatoreJobsView, SchedulatoreJobsController.iSchedulatoreJobsProxy> {

	@Inject
	SchedulatoreJobsMsg messages;

	SchedulatoreJobsServiceAsync servizio = (SchedulatoreJobsServiceAsync) GWT.create(SchedulatoreJobsService.class);

	private EventBus eventBus;

	/**
	 * Interface iSchedulatoreJobsView.
	 */
	public interface iSchedulatoreJobsView extends View {

		/**
		 * Legge visualizza.
		 *
		 * @return visualizza
		 */
		public TextButton getVisualizza();
		
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
		public Grid<SchedulatoreJobsDTO> getGrid();

		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
	}

	/**
	 * Interface iSchedulatoreJobsProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.SchedulatoreJobs)
	public interface iSchedulatoreJobsProxy extends ProxyPlace<SchedulatoreJobsController> {
	}

	private SchedulatoreJobsFormController editorPresenter;
	private SchedulatoreJobsDTO gridItem;
	
	/**
	 * Istanzia un nuovo amm gest utenti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public SchedulatoreJobsController(final EventBus eventBus, final iSchedulatoreJobsView view, final iSchedulatoreJobsProxy proxy,
			SchedulatoreJobsFormController editorPresenter) {

		super(eventBus, view, proxy);
		this.eventBus = eventBus;
		this.editorPresenter = editorPresenter;
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
				editorPresenter.activateView(gridItem);
				addToPopupSlot(editorPresenter);
			}
		});

		getView().getCancella().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				final List<SchedulatoreJobsDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

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
