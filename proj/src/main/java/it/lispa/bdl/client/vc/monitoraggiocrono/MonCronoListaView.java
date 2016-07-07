package it.lispa.bdl.client.vc.monitoraggiocrono;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.MonCronoListaMsg;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.VOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * Class MonCronoListaView.
 */
public class MonCronoListaView extends ViewWithUiHandlers<MonCronoListaHandler> implements MonCronoListaController.iMonCronoListaView {

	VOggettiServiceAsync servizioTreeGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, MonCronoListaView> {

	}

	private BigDecimal cdIstituto;
	private BigDecimal cdProgetto;
	private BigDecimal cdCollezione;
	private ListStore<VOggettoDTO> gridStore;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> gridLoader;

	@UiField(provided = true)
	Grid<VOggettoDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField TextButton lVisualizza;

	/**
	 * Istanzia un nuovo mon crono lista view.
	 *
	 * @param binder  binder
	 * @param img  img
	 * @param messages  messages
	 */
	@Inject
	public MonCronoListaView(final Binder binder, Images img, MonCronoListaMsg messages) {

		/*
		 * MOCK
		com.sencha.gxt.data.shared.loader.DataProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> dataProxy = new com.sencha.gxt.data.shared.loader.DataProxy<FilterPagingLoadConfig,PagingLoadResult<VOggettoDTO>>() {

			@Override
			public void load(
					FilterPagingLoadConfig loadConfig,
					com.google.gwt.core.client.Callback<PagingLoadResult<VOggettoDTO>, Throwable> callback) {
				callback.onSuccess(
						it.lispa.bdl.client.mockservice.Mock.MonCronoListaViewGrid(loadConfig)
						);

			}
		};
		 */
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

				BigDecimal filterIstituto = cdIstituto;
				BigDecimal filterProgetto = cdProgetto;
				BigDecimal filterCollezione = cdCollezione;
				
				List<String> statiOggetto = new ArrayList<String>();
				Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
				servizioTreeGrid.getGridItemsForSupervisore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto, filtriFlag, filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(), callback);
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
		sm.setSelectionMode(SelectionMode.MULTI);


		ColumnConfig<VOggettoDTO, String> col1 = new ColumnConfig<VOggettoDTO, String>(gridProps.T_DnNome(), 150, messages.lTipologiaOggetto());
		ColumnConfig<VOggettoDTO, String> col2 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitolo(), 150, messages.lTitolo());
		ColumnConfig<VOggettoDTO, String> col6 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloFe(), 150, messages.lTitoloFe());
		ColumnConfig<VOggettoDTO, String> col3 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloSup(), 80, messages.lTitoloSup());
		ColumnConfig<VOggettoDTO, BigDecimal> col4 = new ColumnConfig<VOggettoDTO, BigDecimal>(gridProps.O_NrImmaginiPreviste(), 80, messages.lImmaginiPreviste());
		ColumnConfig<VOggettoDTO, String> col5 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DsStato(), 80, messages.lStato());

		List<ColumnConfig<VOggettoDTO, ?>> l = new ArrayList<ColumnConfig<VOggettoDTO, ?>>();
		l.add(sm.getColumn());
		l.add(col1);
		l.add(col2);
		l.add(col6);
		l.add(col3);
		l.add(col4);
		l.add(col5);

		gridLoader.addSortInfo(new SortInfoBean(col2.getPath(), SortDir.ASC));	
		
		ColumnModel<VOggettoDTO> cm = new ColumnModel<VOggettoDTO>(l);

		grid = new Grid<VOggettoDTO>(gridStore, cm);

		grid.setLoader(gridLoader);
		grid.setSelectionModel(sm);		      
		grid.getView().setStripeRows(true);
		grid.getView().setAutoFill(true);
		grid.setLoadMask(true);

		GridFilters<VOggettoDTO> filters = new GridFilters<VOggettoDTO>(gridLoader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitolo()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitoloFe()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.T_DnNome()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DnTitoloSup()));
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DsStato()));

		/*
		filters.addFilter(new NumericFilter<VOggettoDTO, Integer>(props.edad(), new NumberPropertyEditor.IntegerPropertyEditor()));
		 */
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<VOggettoDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<VOggettoDTO> event) {
				int size = event.getSelection().size();
				if(size > 1){
					lVisualizza.setEnabled(false);
				}else if(size == 1){
					lVisualizza.setEnabled(true);
				}else{
					lVisualizza.setEnabled(false);
				}
			}
		});

		
		widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController.iMonCronoListaView#loadGridData()
	 */
	public void loadGridData(){
		gridLoader.load();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController.iMonCronoListaView#getGrid()
	 */
	public Grid<VOggettoDTO> getGrid() {
		return grid;
	}

	/**
	 * On l visualizza.
	 *
	 * @param event  event
	 */
	@UiHandler("lVisualizza")
	public void onLVisualizza(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLVisualizza();
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController.iMonCronoListaView#setCdIstituto(java.math.BigDecimal)
	 */
	@Override
	public void setCdIstituto(BigDecimal cdIstituto) {
		this.cdIstituto = cdIstituto;	
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController.iMonCronoListaView#setCdProgetto(java.math.BigDecimal)
	 */
	@Override
	public void setCdProgetto(BigDecimal cdProgetto) {
		this.cdProgetto = cdProgetto;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController.iMonCronoListaView#setCdCollezione(java.math.BigDecimal)
	 */
	@Override
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
	}

}
