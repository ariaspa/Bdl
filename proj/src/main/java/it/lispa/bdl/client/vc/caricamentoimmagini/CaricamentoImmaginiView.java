package it.lispa.bdl.client.vc.caricamentoimmagini;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CaricamentoImmaginiMsg;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.VOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class CaricamentoImmaginiView.
 */
public class CaricamentoImmaginiView extends ViewWithUiHandlers<CaricamentoImmaginiHandler> implements CaricamentoImmaginiController.iCaricamentoImmaginiView {
	
	VOggettiServiceAsync servizioTreeGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);
	
	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CaricamentoImmaginiView> {

	}


	private TreeStore<SubMenuItemDTO> treeStore;

	@UiField(provided = true)
	Tree<SubMenuItemDTO, String> tree;

	private SubMenuItemDTO treeItem;	

	private ListStore<VOggettoDTO> gridStore;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> gridLoader;

	@UiField(provided = true)
	Grid<VOggettoDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField
	ContentPanel panel;

	@UiField TextButton lAction;
	@UiField TextButton lActionDue;

	final List<String> statiOggetto = new ArrayList<String>();
	
	final Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
	
	/**
	 * Istanzia un nuovo caricamento immagini view.
	 *
	 * @param binder  binder
	 * @param img  img
	 * @param messages  messages
	 */
	@Inject
	public CaricamentoImmaginiView(final Binder binder, Images img, CaricamentoImmaginiMsg messages) {

		statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
		statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE, new Boolean(false));
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE, new Boolean(false));
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE, new Boolean(false));

		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback) { 
				List<FilterConfig> filters = loadConfig.getFilters();
				String sortField = null;
				List<SortInfoBean> sortInfo = (List<SortInfoBean>) loadConfig.getSortInfo();
				SortDir sortDir = SortDir.ASC;
				if(!sortInfo.isEmpty()){
					if (sortInfo.get(0).getSortField() != null) {  
						sortDir = sortInfo.get(0).getSortDir();
						sortField = sortInfo.get(0).getSortField();
					}
				}
				String orderDir = null;
				if(sortDir ==  SortDir.DESC){
					orderDir = "DESC";
				}else{
					orderDir = "ASC";
				}
				
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
				
				servizioTreeGrid.getGridItemsForDigitalizzatore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto, filtriFlag, filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(), callback);
			}
		};
		gridLoader = new PagingLoader<FilterPagingLoadConfig,PagingLoadResult<VOggettoDTO>>(dataProxy){
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		
		gridLoader.setRemoteSort(true);
		VOggettoDTO.VOggettoDTOProperties gridProps = GWT.create(VOggettoDTO.VOggettoDTOProperties.class);

		gridStore = new ListStore<VOggettoDTO>(gridProps.O_CdOggettoOriginale());
		gridLoader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, VOggettoDTO, PagingLoadResult<VOggettoDTO>>(gridStore));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(gridLoader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<VOggettoDTO> identity = new IdentityValueProvider<VOggettoDTO>();
		final CheckBoxSelectionModel<VOggettoDTO> sm = new CheckBoxSelectionModel<VOggettoDTO>(identity);
		sm.setSelectionMode(SelectionMode.SINGLE);
		

		ColumnConfig<VOggettoDTO, String> col1 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DigitalizzatoreId(), 80, messages.id());
		//ColumnConfig<VOggettoDTO, String> col2 = new ColumnConfig<VOggettoDTO, String>(gridProps.T_DnNome(), 150, messages.tipologiaOggetto());
		ColumnConfig<VOggettoDTO, String> col3 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitolo(), 150, messages.titolo());
		ColumnConfig<VOggettoDTO, String> col8 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloFe(), 150, messages.titoloFe());
		ColumnConfig<VOggettoDTO, String> col4 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloSup(), 80, messages.titoloSup());
		ColumnConfig<VOggettoDTO, BigDecimal> col5 = new ColumnConfig<VOggettoDTO, BigDecimal>(gridProps.O_NrImmaginiPreviste(), 60, messages.immaginiPreviste());
		ColumnConfig<VOggettoDTO, BigDecimal> col6 = new ColumnConfig<VOggettoDTO, BigDecimal>(gridProps.O_NrImmaginiDigitalizzate(), 60, messages.immaginiDigitalizzate());
		ColumnConfig<VOggettoDTO, String> col7 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_FlAnomaliaImmagini_Human(), 60, messages.anomalia());

		List<ColumnConfig<VOggettoDTO, ?>> l = new ArrayList<ColumnConfig<VOggettoDTO, ?>>();
		l.add(sm.getColumn());
		l.add(col1);
		//l.add(col2);
		l.add(col3);
		l.add(col8);
		l.add(col4);
		l.add(col5);
		l.add(col6);
		l.add(col7);

		gridLoader.addSortInfo(new SortInfoBean(col1.getPath(), SortDir.ASC));		
		
		ColumnModel<VOggettoDTO> cm = new ColumnModel<VOggettoDTO>(l);

		grid = new Grid<VOggettoDTO>(gridStore, cm);

		grid.setLoader(gridLoader);
		grid.setSelectionModel(sm);
		grid.setSelectionModel(new GridSelectionModel<VOggettoDTO>());		    
		grid.getView().setStripeRows(true);
		grid.getView().setAutoFill(true);
		grid.setLoadMask(true);

		GridFilters<VOggettoDTO> filters = new GridFilters<VOggettoDTO>(gridLoader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.I_DnNome()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.P_DnTitolo()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.C_DnTitolo()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitolo()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitoloFe()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitoloSup()));
		//filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.T_DnNome()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_FlAnomaliaImmagini_Human()));
		
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<VOggettoDTO>() {
			public void onSelectionChanged(SelectionChangedEvent<VOggettoDTO> event) {
				int size = event.getSelection().size();
				if(size == 1){
					lAction.setEnabled(true);
					lActionDue.setEnabled(true);
				}else{
					lAction.setEnabled(false);
					lActionDue.setEnabled(false);
				}
			}
		});

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
			}
			
		});
		
		loadTreeData();
		widget = binder.createAndBindUi(this);
	}

	/**
	 * On l action.
	 *
	 * @param event  event
	 */
	@UiHandler("lAction")
	public void onLAction(SelectEvent event) {
		if (getUiHandlers() != null) {
			/* Carica */
			getUiHandlers().onLAction();
		}
	}
	
	/**
	 * On l action.
	 *
	 * @param event  event
	 */
	@UiHandler("lActionDue")
	public void onLActionDue(SelectEvent event) {
		if (getUiHandlers() != null) {
			/* Carica e genera */
			getUiHandlers().onLActionDue();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#loadGridData()
	 */
	public void loadGridData(){
		gridLoader.load();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#loadTreeData()
	 */
	public void loadTreeData(){

		
		servizioTreeGrid.getTreeItemsForDigitalizzatore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto, filtriFlag, new AsyncCallback<List<SubMenuItemDTO>>() {
			public void onFailure(Throwable caught) {
				// Non fare nulla: l'errore generico viene gestito a monte...
				// e il metodo non tira un AsyncServiceException
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
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#getGrid()
	 */
	public Grid<VOggettoDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#getTree()
	 */
	public Tree<SubMenuItemDTO, String> getTree() {
		return tree;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#getTreeStore()
	 */
	public TreeStore<SubMenuItemDTO> getTreeStore() {
		return treeStore;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController.iCaricamentoImmaginiView#setTreeItem(it.lispa.bdl.shared.dto.SubMenuItemDTO)
	 */
	public void setTreeItem(SubMenuItemDTO treeItem) {
		this.treeItem = treeItem;
	}
}
