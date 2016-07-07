package it.lispa.bdl.client.vc.ammgestindex;

import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.AmmGestIndexMsg;
import it.lispa.bdl.shared.services.AmmGestIndexService;
import it.lispa.bdl.shared.services.AmmGestIndexServiceAsync;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.VOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.ListFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * Class AmmGestIndexView.
 */
public class AmmGestIndexView extends ViewImpl implements AmmGestIndexController.iAmmGestIndexView {

	AmmGestIndexServiceAsync servizio = (AmmGestIndexServiceAsync) GWT.create(AmmGestIndexService.class);
	VOggettiServiceAsync servizioGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestIndexView> {
	}

	private ListStore<VOggettoDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> loader;

	@UiField(provided = true)
	Grid<VOggettoDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField
	ContentPanel panel;

	@UiField
	TextButton visualizza;
	
	/*
	@UiField
	TextButton inserisci;
	 */
	
	@UiField
	TextButton cancella;

	private final String orderFieldDefault;
	@SuppressWarnings("unused")
	private final String orderDirDefault;
		

	/**
	 * Istanzia un nuovo amm gest index view.
	 *
	 * @param binder  binder
	 * @param messages  messages
	 */
	@Inject
	public AmmGestIndexView(final Binder binder, AmmGestIndexMsg messages) {
		
		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback) {
				List<FilterConfig> filters = loadConfig.getFilters();
				String sortField = null;
				List<SortInfoBean> sortInfo = (List<SortInfoBean>) loadConfig.getSortInfo();
				SortDir sortDir = SortDir.ASC;
				if (!sortInfo.isEmpty()) {
					if (sortInfo.get(0).getSortField() != null) {
						sortDir = sortInfo.get(0).getSortDir();
						sortField = sortInfo.get(0).getSortField();
					}
				}
				String orderDir = null;
				if (sortDir == SortDir.DESC) {
					orderDir = "DESC";
				} else {
					orderDir = "ASC";
				}
				

				BigDecimal filterProgetto = null;
				BigDecimal filterCollezione = null;
				BigDecimal filterIstituto = null;
				filterCollezione = null;
				List<String> statiOggetto = null;
				
				servizioGrid.getGridItemsForSupervisore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto, filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(), callback);
			}
		};
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<VOggettoDTO>>(dataProxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};

		loader.setRemoteSort(true);
		
		final VOggettoDTO.VOggettoDTOProperties props = GWT.create(VOggettoDTO.VOggettoDTOProperties.class);

		store = new ListStore<VOggettoDTO>(props.O_CdOggettoOriginale());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, VOggettoDTO, PagingLoadResult<VOggettoDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<VOggettoDTO> identity = new IdentityValueProvider<VOggettoDTO>();
		final CheckBoxSelectionModel<VOggettoDTO> sm = new CheckBoxSelectionModel<VOggettoDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);

		ColumnConfig<VOggettoDTO, String> titoloSupCol = new ColumnConfig<VOggettoDTO, String>(props.O_DnTitoloSup(), 80, messages.lTitoloSup());
		ColumnConfig<VOggettoDTO, String> titoloCol = new ColumnConfig<VOggettoDTO, String>(props.O_DnTitolo(), 150, messages.lTitolo());
		ColumnConfig<VOggettoDTO, String> statoCol = new ColumnConfig<VOggettoDTO, String>(props.O_DsStato(), 150, messages.lStato());
		ColumnConfig<VOggettoDTO, BigDecimal> immaginiCol = new ColumnConfig<VOggettoDTO, BigDecimal>(props.O_NrImmaginiPreviste(), 80, messages.lNumeroImmagini());

		List<ColumnConfig<VOggettoDTO, ?>> l = new ArrayList<ColumnConfig<VOggettoDTO, ?>>();
		l.add(sm.getColumn());
		l.add(titoloSupCol);
		l.add(titoloCol);
		l.add(statoCol);
		l.add(immaginiCol);

		orderFieldDefault = titoloCol.getPath();
		orderDirDefault = "asc";
		loader.addSortInfo(new SortInfoBean(orderFieldDefault, SortDir.ASC));

		ColumnModel<VOggettoDTO> cm = new ColumnModel<VOggettoDTO>(l);

		grid = new Grid<VOggettoDTO>(store, cm) {
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					public void execute() {
						loader.load();
					}
				});
			}
		};

		grid.setLoader(loader);
		grid.setSelectionModel(sm);
		grid.getView().setStripeRows(true);
		grid.getView().setAutoFill(true);
		grid.setLoadMask(true);

		final GridFilters<VOggettoDTO> filters = new GridFilters<VOggettoDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<VOggettoDTO>(props.O_DnTitoloSup()));
		filters.addFilter(new StringFilter<VOggettoDTO>(props.O_DnTitolo()));
		filters.addFilter(new StringFilter<VOggettoDTO>(props.O_DsStato()));

		ListStore<String> fStatoStore = new ListStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO);
		fStatoStore.add( BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO);
		filters.addFilter(new ListFilter<VOggettoDTO, String>(props.O_DsStato(), fStatoStore));

		/*
		 * filters.addFilter(new NumericFilter<VOggettoDTO,
		 * Integer>(props.edad(), new
		 * NumberPropertyEditor.IntegerPropertyEditor()));
		 */

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<VOggettoDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<VOggettoDTO> event) {
				int size = event.getSelection().size();
				if (size > 1) {
					visualizza.setEnabled(false);
					cancella.setEnabled(true);
				} else if (size == 1) {
					visualizza.setEnabled(true);
					cancella.setEnabled(true);
				} else {
					visualizza.setEnabled(false);
					cancella.setEnabled(false);
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
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#load()
	 */
	public void load() {
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#getGrid()
	 */
	public Grid<VOggettoDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/*@Override
	public TextButton getInserisci() {
		//return inserisci;
		return null;
	}*/

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#getVisualizza()
	 */
	@Override
	public TextButton getVisualizza() {
		return visualizza;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#getCancella()
	 */
	@Override
	public TextButton getCancella() {
		return cancella;
	}

}