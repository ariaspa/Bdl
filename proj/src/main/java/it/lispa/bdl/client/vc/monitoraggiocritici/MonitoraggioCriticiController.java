package it.lispa.bdl.client.vc.monitoraggiocritici;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.MonitoraggioCriticiMsg;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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
 * Class MonitoraggioCriticiController.
 */
public class MonitoraggioCriticiController extends Presenter<MonitoraggioCriticiController.iMonitoraggioCriticiView, MonitoraggioCriticiController.iMonitoraggioCriticiProxy> {
	
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	@Inject MonitoraggioCriticiMsg messages;

	/* Interfaccia condivisa tra la vista e il controller */
	/**
	 * Interface iMonitoraggioCriticiView.
	 */
	public interface iMonitoraggioCriticiView extends View {
		
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
		
		/**
		 * Legge tree item.
		 *
		 * @return tree item
		 */
		public SubMenuItemDTO getTreeItem();
		PagingLoadResult<VOggettoDTO> getDataForGrid(FilterPagingLoadConfig config);
	}

	/**
	 * Interface iMonitoraggioCriticiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.MonitoraggioCritici)
	public interface iMonitoraggioCriticiProxy extends ProxyPlace<MonitoraggioCriticiController> {
	}

	private MonitoraggioCriticiFormController editorPresenter;
	private VOggettoDTO gridItem;	
	
	/**
	 * Istanzia un nuovo monitoraggio critici controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public MonitoraggioCriticiController(final EventBus eventBus,
			final iMonitoraggioCriticiView view, final iMonitoraggioCriticiProxy proxy, MonitoraggioCriticiFormController editorPresenter) {

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
				SubMenuItemDTO treeItem = getView().getTreeItem();
				String filter = null;
				BigDecimal filterCode = null;
				if(treeItem!=null){
					filterCode = treeItem.getCodice();
					filter = treeItem.getTipo();
				}
				String link = GWT.getHostPageBaseURL() + "../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/xls/monitoraggiocritici/";
				if(filterCode != null){
					link += filter + "/" + filterCode;
				}
				Window.open(link,"xls",null);
			}
		});

	}

	@Override
	protected void onReveal() {
		super.onReveal();
		GWT.log("onReveal di MonitoraggioCritici");
		getView().setTreeItem(null);
		getView().loadGridData();
		getView().loadTreeData();
		
	}
	
}