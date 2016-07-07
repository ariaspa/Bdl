package it.lispa.bdl.client.vc.monitoraggiolavorazione;

import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.messages.MonitoraggioLavorazioneMsg;
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
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class MonitoraggioLavorazioneController.
 */
public class MonitoraggioLavorazioneController extends Presenter<MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView, MonitoraggioLavorazioneController.iMonitoraggioLavorazioneProxy> {
		
	@Inject MonitoraggioLavorazioneMsg messages;

	/* Interfaccia condivisa tra la vista e il controller */
	/**
	 * Interface iMonitoraggioLavorazioneView.
	 */
	public interface iMonitoraggioLavorazioneView extends View {
		
		/**
		 * Legge action.
		 *
		 * @return action
		 */
		public TextButton getAction();
		
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
		public Grid<VOggettoCountDTO> getGrid();		
		
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
	}

	/**
	 * Interface iMonitoraggioLavorazioneProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.MonitoraggioLavorazione)
	public interface iMonitoraggioLavorazioneProxy extends ProxyPlace<MonitoraggioLavorazioneController> {
	}

	
	/**
	 * Istanzia un nuovo monitoraggio lavorazione controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 */
	@Inject
	public MonitoraggioLavorazioneController(final EventBus eventBus,
			final iMonitoraggioLavorazioneView view, final iMonitoraggioLavorazioneProxy proxy) {

		super(eventBus, view, proxy);
	}
	
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		getView().getAction().addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent event) {
				SubMenuItemDTO treeItem = getView().getTreeItem();
				String filter = null;
				BigDecimal filterCode = null;
				if(treeItem!=null){
					filterCode = treeItem.getCodice();
					filter = treeItem.getTipo();
				}
				String link = GWT.getHostPageBaseURL() + "../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/xls/monitoraggiolavorazione/";
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
		GWT.log("onReveal di MonitoraggioLavorazione");
		getView().setTreeItem(null);
		getView().loadGridData();
		getView().loadTreeData();
		
	}
	
}