package it.lispa.bdl.client.vc.monitoraggiolavorazione;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.messages.MonitoraggioLavorazioneMsg;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.VOggettiServiceAsync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.AggregationNumberSummaryRenderer;
import com.sencha.gxt.widget.core.client.grid.AggregationRowConfig;
import com.sencha.gxt.widget.core.client.grid.AggregationSafeHtmlRenderer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.SummaryType;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class MonitoraggioLavorazioneView.
 */
public class MonitoraggioLavorazioneView extends ViewImpl implements MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView {

	VOggettiServiceAsync servizioTreeGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);
	
	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, MonitoraggioLavorazioneView> {

	}
	SubMenuItemDTO treeItem;

	private TreeStore<SubMenuItemDTO> treeStore;

	@UiField(provided = true)
	Tree<SubMenuItemDTO, String> tree;

	private ListStore<VOggettoCountDTO> gridStore;

	@UiField(provided = true)
	Grid<VOggettoCountDTO> grid;

	@UiField
	ContentPanel panel;

	@UiField
	TextButton action;

	@UiField 
	TextButton resetta;
	
	/**
	 * Istanzia un nuovo monitoraggio lavorazione view.
	 *
	 * @param binder  binder
	 * @param img  img
	 * @param messages  messages
	 */
	@Inject
	public MonitoraggioLavorazioneView(final Binder binder, Images img, MonitoraggioLavorazioneMsg messages) {

		VOggettoCountDTO.VOggettoCountDTOProperties gridProps = GWT.create(VOggettoCountDTO.VOggettoCountDTOProperties.class);

		gridStore = new ListStore<VOggettoCountDTO>(gridProps.statoFlag());
		
		ColumnConfig<VOggettoCountDTO, String> col1 = new ColumnConfig<VOggettoCountDTO, String>(gridProps.statoHuman(), 150, messages.lStato());
		ColumnConfig<VOggettoCountDTO, String> col2 = new ColumnConfig<VOggettoCountDTO, String>(gridProps.flagHuman(), 80, messages.lFlag());
		ColumnConfig<VOggettoCountDTO, BigDecimal> col3 = new ColumnConfig<VOggettoCountDTO, BigDecimal>(gridProps.oggetti(), 80, messages.lOggetti());
		ColumnConfig<VOggettoCountDTO, BigDecimal> col4 = new ColumnConfig<VOggettoCountDTO, BigDecimal>(gridProps.immaginiPreviste(), 80, messages.lImgPreviste());
		ColumnConfig<VOggettoCountDTO, BigDecimal> col5 = new ColumnConfig<VOggettoCountDTO, BigDecimal>(gridProps.immaginiDigitalizzate(), 80, messages.lImgDigitalizzate());

		List<ColumnConfig<VOggettoCountDTO, ?>> l = new ArrayList<ColumnConfig<VOggettoCountDTO, ?>>();
		l.add(col1);
		l.add(col2);
		l.add(col3);
		l.add(col4);
		l.add(col5);
		
		ColumnModel<VOggettoCountDTO> cm = new ColumnModel<VOggettoCountDTO>(l);

		grid = new Grid<VOggettoCountDTO>(gridStore, cm);

		grid.getView().setStripeRows(true);
		grid.getView().setAutoFill(true);
		grid.setLoadMask(true);
		
	    final NumberFormat numberFormat = NumberFormat.getFormat("0");
		
		AggregationRowConfig<VOggettoCountDTO> sumRow = new AggregationRowConfig<VOggettoCountDTO>();
		sumRow.setRenderer(col1, new AggregationSafeHtmlRenderer<VOggettoCountDTO>(messages.lTotale()));

		sumRow.setRenderer(col3, new AggregationNumberSummaryRenderer<VOggettoCountDTO, Number>(numberFormat, new SummaryType.SumSummaryType<Number>()));
		sumRow.setRenderer(col4, new AggregationNumberSummaryRenderer<VOggettoCountDTO, Number>(numberFormat, new SummaryType.SumSummaryType<Number>()));
		sumRow.setRenderer(col5, new AggregationNumberSummaryRenderer<VOggettoCountDTO, Number>(numberFormat, new SummaryType.SumSummaryType<Number>()));
		
	    cm.addAggregationRow(sumRow);
		
		SubMenuItemDTO.SubMenuItemDTOProperties treeProps = GWT.create(SubMenuItemDTO.SubMenuItemDTOProperties.class);
		treeStore = new TreeStore<SubMenuItemDTO>(treeProps.chiave());

		tree = new Tree<SubMenuItemDTO, String>(treeStore, treeProps.label());


		tree.getStyle().setLeafIcon(img.getFrecciaOff());
		
		tree.getStyle().setJointOpenIcon(img.getClear());
		tree.getStyle().setJointCloseIcon(img.getClear());
		
		tree.getStyle().setNodeOpenIcon(img.getFrecciaOn());
		tree.getStyle().setNodeCloseIcon(img.getFrecciaOff());
		
		tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		tree.setAutoExpand(true);
		
		tree.getSelectionModel().addSelectionHandler(new SelectionHandler<SubMenuItemDTO>() {
			public void onSelection(SelectionEvent<SubMenuItemDTO> event) {
				treeItem = event.getSelectedItem();
				loadGridData();
				resetta.enable();
			}
		});
		widget = binder.createAndBindUi(this);
	}

	/**
	 * On resetta.
	 *
	 * @param event  event
	 */
	@UiHandler("resetta")
	public void onResetta(SelectEvent event) {
		SubMenuItemDTO sel = tree.getSelectionModel().getSelectedItem();
		tree.getSelectionModel().deselect(sel);
		resetta.disable();
		treeItem = null;
		loadGridData();
	}
	
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#loadGridData()
	 */
	public void loadGridData(){
		loadGridData(treeItem);
	}
	
	/**
	 * Load grid data.
	 *
	 * @param treeItem  tree item
	 */
	public void loadGridData(SubMenuItemDTO treeItem){
		
		grid.mask();

		BigDecimal filterIstituto = null;
		BigDecimal filterProgetto = null;
		BigDecimal filterCollezione = null;
		
		if(treeItem!=null){
			if(treeItem.getTipo().equals(SubMenuItemDTO.TIPO_ISTITUTO)){
				filterIstituto = treeItem.getCodice();				
			}else if(treeItem.getTipo().equals(SubMenuItemDTO.TIPO_PROGETTO)){
				filterProgetto = treeItem.getCodice();
			}else if(treeItem.getTipo().equals(SubMenuItemDTO.TIPO_COLLEZIONE)){
				filterCollezione = treeItem.getCodice();
			}
		}
		
		servizioTreeGrid.getGridCounters(filterIstituto, filterProgetto, filterCollezione, new AsyncCallback<List<VOggettoCountDTO>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				AlertMessageBox box = new AlertMessageBox("RPC", caught.toString());
				box.setPredefinedButtons(PredefinedButton.OK);
				box.setIcon(MessageBox.ICONS.error());
				box.show();
				grid.unmask();
			}
			@Override
			public void onSuccess(List<VOggettoCountDTO> items) {
				gridStore.clear();
				gridStore.addAll(items);
				grid.unmask();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#loadTreeData()
	 */
	public void loadTreeData(){		
		servizioTreeGrid.getTreeCounters(new AsyncCallback<List<SubMenuItemDTO>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				AlertMessageBox box = new AlertMessageBox("RPC", caught.toString());
				box.setPredefinedButtons(PredefinedButton.OK);
				box.setIcon(MessageBox.ICONS.error());
				box.show();
			}
			@Override
			public void onSuccess(List<SubMenuItemDTO> items) {

				treeStore.clear();

				Iterator<SubMenuItemDTO> mnuItr = items.iterator();
				while(mnuItr.hasNext()){
					SubMenuItemDTO item = (SubMenuItemDTO) mnuItr.next();
					treeStore.add(item);
					if(item.hasChildren()){
						processTreeChildren(item);
					}
				}
				tree.expandAll();
			}
		});
	}
	private void processTreeChildren(SubMenuItemDTO rootNode) {
		for (SubMenuItemDTO child : rootNode.getChildren()) {
			treeStore.add(rootNode, child);
			if (child.hasChildren()) {
				processTreeChildren((SubMenuItemDTO) child);
			}
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getGrid()
	 */
	public Grid<VOggettoCountDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getTree()
	 */
	public Tree<SubMenuItemDTO, String> getTree() {
		return tree;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getTreeStore()
	 */
	public TreeStore<SubMenuItemDTO> getTreeStore() {
		return treeStore;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getAction()
	 */
	@Override
	public TextButton getAction() {
		return action;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#setTreeItem(it.lispa.bdl.shared.dto.SubMenuItemDTO)
	 */
	public void setTreeItem(SubMenuItemDTO treeItem) {
		this.treeItem = treeItem;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView#getTreeItem()
	 */
	public SubMenuItemDTO getTreeItem() {
		return treeItem;
	}

}
