package it.lispa.bdl.client.vc.verificaoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.VerificaOggettiMsg;
import it.lispa.bdl.shared.services.VerificaOggettiService;
import it.lispa.bdl.shared.services.VerificaOggettiServiceAsync;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
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
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class VerificaOggettiController.
 */
public class VerificaOggettiController extends Presenter<VerificaOggettiController.iVerificaOggettiView, VerificaOggettiController.iVerificaOggettiProxy> {
	
	VerificaOggettiServiceAsync servizioFunzione = (VerificaOggettiServiceAsync) GWT.create(VerificaOggettiService.class);

	private EventBus eventBus;
	
	@Inject VerificaOggettiMsg messages;

	/* Interfaccia condivisa tra la vista e il controller */
	/**
	 * Interface iVerificaOggettiView.
	 */
	public interface iVerificaOggettiView extends View {
		
		/**
		 * Legge action.
		 *
		 * @return action
		 */
		public TextButton getAction();
		
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
		 * Load tree data.
		 */
		public void loadTreeData();
		Tree<SubMenuItemDTO, String> getTree();
		TreeStore<SubMenuItemDTO> getTreeStore();
		
		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<VOggettoDTO> getGrid();		
		
		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
		
		/**
		 * Imposta tree item.
		 *
		 * @param treeItem nuovo tree item
		 */
		public void setTreeItem(SubMenuItemDTO treeItem);
		PagingLoadResult<VOggettoDTO> getDataForGrid(FilterPagingLoadConfig config);
	}

	/**
	 * Interface iVerificaOggettiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.VerificaOggetti)
	public interface iVerificaOggettiProxy extends ProxyPlace<VerificaOggettiController> {
	}

	private VerificaOggettiFormController editorPresenter;
	private VOggettoDTO gridItem;	
	
	/**
	 * Istanzia un nuovo verifica oggetti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public VerificaOggettiController(final EventBus eventBus,
			final iVerificaOggettiView view, final iVerificaOggettiProxy proxy, VerificaOggettiFormController editorPresenter) {

		super(eventBus, view, proxy);
		this.eventBus = eventBus;
		this.editorPresenter = editorPresenter;
		
	}

	private final SaveHandler saveHandler = new SaveHandler() {
		
		public void onSave(ChangeGridState event) {
			getView().loadGridData();
			getView().loadTreeData();
		}
	};
	
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

				editorPresenter.getView().setHeadingText(gridItem.getO_DnTitolo());

				addToPopupSlot(editorPresenter);
				
			}
		});
		getView().getAction().addSelectHandler(new SelectHandler() {

			public void onSelect(SelectEvent event) {
				final List<VOggettoDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

				String message;
				if(gridItems.size() == 1) {
					message = messages.actionConfirm(gridItems.get(0).getO_DnTitolo());
				} else {
					message = messages.actionAllConfirm(gridItems.size());
				}

				ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
				box.setSize("420","130");
				box.addHideHandler(new HideHandler() {

					public void onHide(HideEvent event) {
						Dialog btn = (Dialog) event.getSource();
						if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
							servizioFunzione.verificaOggetti(gridItems, new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									// Non fare nulla: l'errore generico viene gestito a monte...
									// e il metodo non tira un AsyncServiceException
								}
								@Override
								public void onSuccess(Void v) {
									eventBus.fireEvent(new ChangeGridState());
									new GXTMessageBox(messages.titlePanel(), messages.esitoOk(), GXTMessageBox.DO_SHOW);
									
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

	@Override
	protected void onReveal() {
		super.onReveal();
		GWT.log("onReveal di VerificaOggetti");
		getView().setTreeItem(null);
		getView().loadGridData();
		getView().loadTreeData();
		
	}
	
}