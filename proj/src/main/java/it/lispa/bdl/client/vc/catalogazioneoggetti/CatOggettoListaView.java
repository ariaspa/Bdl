package it.lispa.bdl.client.vc.catalogazioneoggetti;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CatOggettoListaMsg;
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
 * Class CatOggettoListaView.
 */
public class CatOggettoListaView extends ViewWithUiHandlers<CatOggettoListaHandler> implements CatOggettoListaController.iCatOggettoListaView {

	VOggettiServiceAsync servizioTreeGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CatOggettoListaView> {

	}

	private BigDecimal cdCollezione;
	private ListStore<VOggettoDTO> gridStore;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> gridLoader;

	@UiField(provided = true)
	Grid<VOggettoDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField TextButton lVisualizza;
	@UiField TextButton lRichiediValidazione;
	@UiField TextButton labelImportaExcel;
	@UiField TextButton labelImportaToc;
	@UiField TextButton lImportaCatalogo;

	/**
	 * Istanzia un nuovo cat oggetto lista view.
	 *
	 * @param binder  binder
	 * @param img  img
	 * @param messages  messages
	 */
	@Inject
	public CatOggettoListaView(final Binder binder, Images img, CatOggettoListaMsg messages) {

		/*
		 * MOCK
        	com.sencha.gxt.data.shared.loader.DataProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> dataProxy = new com.sencha.gxt.data.shared.loader.DataProxy<FilterPagingLoadConfig,PagingLoadResult<VOggettoDTO>>() {

        	    @Override
        	    public void load(
        		    FilterPagingLoadConfig loadConfig,
        		    com.google.gwt.core.client.Callback<PagingLoadResult<VOggettoDTO>, Throwable> callback) {
        		callback.onSuccess(
        			it.lispa.bdl.client.mockservice.Mock.CatOggettoListaViewGrid(loadConfig)
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

				BigDecimal filterProgetto = null;
				BigDecimal filterCollezione = null;

				filterCollezione = cdCollezione;
				List<String> statiOggetto = new ArrayList<String>();
				statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
				statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
				statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
				statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO);
				Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
				filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE, new Boolean(false));
				servizioTreeGrid.getGridItemsForCatalogatore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto, filtriFlag, filterProgetto, filterCollezione, filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(), callback);

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


		ColumnConfig<VOggettoDTO, String> col0 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DigitalizzatoreId(), 75, messages.lIdOggetto());
		ColumnConfig<VOggettoDTO, String> col1 = new ColumnConfig<VOggettoDTO, String>(gridProps.T_DnNome(), 75, messages.lTipologiaOggetto());
		ColumnConfig<VOggettoDTO, String> col2 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitolo(), 150, messages.lTitolo());
		ColumnConfig<VOggettoDTO, String> col6 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloFe(), 150, messages.lTitoloFe());
		ColumnConfig<VOggettoDTO, String> col3 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DnTitoloSup(), 80, messages.lTitoloSup());
		ColumnConfig<VOggettoDTO, BigDecimal> col4 = new ColumnConfig<VOggettoDTO, BigDecimal>(gridProps.O_NrImmaginiPreviste(), 80, messages.lImmaginiPreviste());
		ColumnConfig<VOggettoDTO, String> col5 = new ColumnConfig<VOggettoDTO, String>(gridProps.O_DsStato(), 80, messages.lStato());

		List<ColumnConfig<VOggettoDTO, ?>> l = new ArrayList<ColumnConfig<VOggettoDTO, ?>>();
		l.add(sm.getColumn());
		l.add(col0);
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
		filters.addFilter(new StringFilter<VOggettoDTO>(gridProps.O_DigitalizzatoreId()));
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

				lImportaCatalogo.setEnabled(false);
				labelImportaExcel.setEnabled(true);
				labelImportaToc.setEnabled(true);
				if(size > 1){
					lVisualizza.setEnabled(false);
					lRichiediValidazione.setEnabled(true);
					lImportaCatalogo.setEnabled(false);
				}else if(size == 1){
					lVisualizza.setEnabled(true);
					lRichiediValidazione.setEnabled(true);
					lImportaCatalogo.setEnabled(true);
				}else{
					lVisualizza.setEnabled(false);
					lRichiediValidazione.setEnabled(false);
					lImportaCatalogo.setEnabled(false);
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
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaController.iCatOggettoListaView#loadGridData()
	 */
	public void loadGridData(){
		gridLoader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaController.iCatOggettoListaView#getGrid()
	 */
	public Grid<VOggettoDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaController.iCatOggettoListaView#setCdCollezione(java.math.BigDecimal)
	 */
	@Override
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;		
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
	
	/**
	 * On l richiedi validazione.
	 *
	 * @param event  event
	 */
	@UiHandler("lRichiediValidazione")
	public void onLRichiediValidazione(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLRichiediValidazione();
		}
	}
	@UiHandler("labelImportaExcel")
	public void onLImportaExcel(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLImportaExcel();
		}
	}
	@UiHandler("labelImportaToc")
	public void onLImportaToc(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLImportaToc();
		}
	}
	
	/**
	 * On l importa catalogo.
	 *
	 * @param event  event
	 */
	@UiHandler("lImportaCatalogo")
	public void onLImportaCatalogo(SelectEvent event) {
		MultiUploader defaultUploader = new MultiUploader();
		defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
		if (getUiHandlers() != null) {
			getUiHandlers().onLImportaCatalogo();
		}
	}
	
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {
	        // The server sends useful information to the client by default
	    	@SuppressWarnings("unused")
	        UploadedInfo info = uploader.getServerInfo();
	      }
	    }
	  };
}
