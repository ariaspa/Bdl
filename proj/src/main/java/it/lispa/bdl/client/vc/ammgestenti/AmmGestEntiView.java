package it.lispa.bdl.client.vc.ammgestenti;

import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.messages.AmmGestEntiMsg;
import it.lispa.bdl.shared.services.AmmGestEntiService;
import it.lispa.bdl.shared.services.AmmGestEntiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

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
 * Class AmmGestEntiView.
 */
public class AmmGestEntiView extends ViewImpl implements AmmGestEntiController.iAmmGestEntiView {

	AmmGestEntiServiceAsync servizio = (AmmGestEntiServiceAsync) GWT.create(AmmGestEntiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestEntiView> {
	}

	private ListStore<EnteDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<EnteDTO>> loader;

	@UiField(provided = true)
	Grid<EnteDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField
	ContentPanel panel;

	@UiField
	TextButton visualizza;

	@UiField
	TextButton inserisci;

	@UiField
	TextButton cancella;

	private final String orderFieldDefault;
	private final String orderDirDefault;

	/**
	 * Istanzia un nuovo amm gest enti view.
	 *
	 * @param binder  binder
	 * @param messages  messages
	 */
	@Inject
	public AmmGestEntiView(final Binder binder, AmmGestEntiMsg messages) {

		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<EnteDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<EnteDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<EnteDTO>> callback) {
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
				servizio.getItems(filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(),orderFieldDefault,orderDirDefault, callback);
			}
		};
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<EnteDTO>>(dataProxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};

		loader.setRemoteSort(true);
		
		final EnteDTO.EnteDTOProperties props = GWT.create(EnteDTO.EnteDTOProperties.class);

		store = new ListStore<EnteDTO>(props.cdEnte());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, EnteDTO, PagingLoadResult<EnteDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<EnteDTO> identity = new IdentityValueProvider<EnteDTO>();
		final CheckBoxSelectionModel<EnteDTO> sm = new CheckBoxSelectionModel<EnteDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);

		ColumnConfig<EnteDTO, String> nomeCol = new ColumnConfig<EnteDTO, String>(props.nome(), 80, messages.lNome());
		ColumnConfig<EnteDTO, String> comuneCol = new ColumnConfig<EnteDTO, String>(props.comune(), 150, messages.lComune());
		ColumnConfig<EnteDTO, String> provinciaCol = new ColumnConfig<EnteDTO, String>(props.provinciaHuman(), 150, messages.lProvincia());
		ColumnConfig<EnteDTO, String> classeCol = new ColumnConfig<EnteDTO, String>(props.classeHuman(), 150, messages.lClasse());

		List<ColumnConfig<EnteDTO, ?>> l = new ArrayList<ColumnConfig<EnteDTO, ?>>();
		l.add(sm.getColumn());
		l.add(nomeCol);
		l.add(comuneCol);
		l.add(provinciaCol);
		l.add(classeCol);

		orderFieldDefault = nomeCol.getPath();
		orderDirDefault = "asc";
		loader.addSortInfo(new SortInfoBean(orderFieldDefault, SortDir.ASC));

		ColumnModel<EnteDTO> cm = new ColumnModel<EnteDTO>(l);

		grid = new Grid<EnteDTO>(store, cm) {
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

		final GridFilters<EnteDTO> filters = new GridFilters<EnteDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<EnteDTO>(props.nome()));
		filters.addFilter(new StringFilter<EnteDTO>(props.comune()));
		filters.addFilter(new StringFilter<EnteDTO>(props.provinciaHuman()));

		ListStore<String> fClasseStore = new ListStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});
		fClasseStore.add( BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN);
		fClasseStore.add( BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN);
		filters.addFilter(new ListFilter<EnteDTO, String>(props.classeHuman(), fClasseStore));

		/*
		 * filters.addFilter(new NumericFilter<EnteDTO,
		 * Integer>(props.edad(), new
		 * NumberPropertyEditor.IntegerPropertyEditor()));
		 */

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<EnteDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<EnteDTO> event) {
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
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#load()
	 */
	public void load() {
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#getGrid()
	 */
	public Grid<EnteDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#getInserisci()
	 */
	@Override
	public TextButton getInserisci() {
		return inserisci;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#getVisualizza()
	 */
	@Override
	public TextButton getVisualizza() {
		return visualizza;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController.iAmmGestEntiView#getCancella()
	 */
	@Override
	public TextButton getCancella() {
		return cancella;
	}

}