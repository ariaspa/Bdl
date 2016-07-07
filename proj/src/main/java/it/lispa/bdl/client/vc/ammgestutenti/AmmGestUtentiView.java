package it.lispa.bdl.client.vc.ammgestutenti;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.messages.AmmGestUtentiMsg;
import it.lispa.bdl.shared.services.AmmGestUtentiService;
import it.lispa.bdl.shared.services.AmmGestUtentiServiceAsync;
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
 * Class AmmGestUtentiView.
 */
public class AmmGestUtentiView extends ViewImpl implements AmmGestUtentiController.iAmmGestUtentiView {

	AmmGestUtentiServiceAsync servizio = (AmmGestUtentiServiceAsync) GWT.create(AmmGestUtentiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestUtentiView> {
	}

	private ListStore<UtenteLightDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<UtenteLightDTO>> loader;

	@UiField(provided = true)
	Grid<UtenteLightDTO> grid;

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
	 * Istanzia un nuovo amm gest utenti view.
	 *
	 * @param binder  binder
	 * @param messages  messages
	 */
	@Inject
	public AmmGestUtentiView(final Binder binder, AmmGestUtentiMsg messages) {

		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UtenteLightDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UtenteLightDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UtenteLightDTO>> callback) {
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
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<UtenteLightDTO>>(dataProxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};

		loader.setRemoteSort(true);
		
		final UtenteLightDTO.UtenteLightDTOProperties props = GWT.create(UtenteLightDTO.UtenteLightDTOProperties.class);

		store = new ListStore<UtenteLightDTO>(props.cdUtente());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, UtenteLightDTO, PagingLoadResult<UtenteLightDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<UtenteLightDTO> identity = new IdentityValueProvider<UtenteLightDTO>();
		final CheckBoxSelectionModel<UtenteLightDTO> sm = new CheckBoxSelectionModel<UtenteLightDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);

		ColumnConfig<UtenteLightDTO, String> nomeCol = new ColumnConfig<UtenteLightDTO, String>(props.nome(), 80, messages.lNome());
		ColumnConfig<UtenteLightDTO, String> cognomeCol = new ColumnConfig<UtenteLightDTO, String>(props.cognome(), 150, messages.lCognome());
		ColumnConfig<UtenteLightDTO, String> cfCol = new ColumnConfig<UtenteLightDTO, String>(props.cf(), 150, messages.lCf());
		ColumnConfig<UtenteLightDTO, String> emailCol = new ColumnConfig<UtenteLightDTO, String>(props.email(), 150, messages.lEmail());
		ColumnConfig<UtenteLightDTO, String> ruoloCol = new ColumnConfig<UtenteLightDTO, String>(props.ruolo(), 80, messages.lRuolo());
		ColumnConfig<UtenteLightDTO, String> statoCol = new ColumnConfig<UtenteLightDTO, String>(props.statoHuman(), 80, messages.lStato());

		List<ColumnConfig<UtenteLightDTO, ?>> l = new ArrayList<ColumnConfig<UtenteLightDTO, ?>>();
		l.add(sm.getColumn());
		l.add(nomeCol);
		l.add(cognomeCol);
		l.add(cfCol);
		l.add(emailCol);
		l.add(ruoloCol);
		l.add(statoCol);

		orderFieldDefault = cognomeCol.getPath();
		orderDirDefault = "asc";
		loader.addSortInfo(new SortInfoBean(cognomeCol.getPath(), SortDir.ASC));

		ColumnModel<UtenteLightDTO> cm = new ColumnModel<UtenteLightDTO>(l);

		grid = new Grid<UtenteLightDTO>(store, cm) {
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

		final GridFilters<UtenteLightDTO> filters = new GridFilters<UtenteLightDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<UtenteLightDTO>(props.nome()));
		filters.addFilter(new StringFilter<UtenteLightDTO>(props.cognome()));
		filters.addFilter(new StringFilter<UtenteLightDTO>(props.cf()));
		filters.addFilter(new StringFilter<UtenteLightDTO>(props.email()));

		final ListStore<String> fRuoloStore = new ListStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});

		servizio.getRuoli(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboDTO> items) {
				for(ComboDTO item:items){
					fRuoloStore.add(item.getDesc());
				}
				filters.addFilter(new ListFilter<UtenteLightDTO, String>(props.ruolo(), fRuoloStore));
			}
		});

		ListStore<String> fStatoStore = new ListStore<String>(new ModelKeyProvider<String>() {
			@Override
			public String getKey(String item) {
				return item;
			}
		});
		fStatoStore.add( BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE_HUMAN);
		fStatoStore.add( BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO_HUMAN);
		filters.addFilter(new ListFilter<UtenteLightDTO, String>(props.statoHuman(), fStatoStore));

		/*
		 * filters.addFilter(new NumericFilter<UtenteLightDTO,
		 * Integer>(props.edad(), new
		 * NumberPropertyEditor.IntegerPropertyEditor()));
		 */

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UtenteLightDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<UtenteLightDTO> event) {
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
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#load()
	 */
	public void load() {
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#getGrid()
	 */
	public Grid<UtenteLightDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#getInserisci()
	 */
	@Override
	public TextButton getInserisci() {
		return inserisci;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#getVisualizza()
	 */
	@Override
	public TextButton getVisualizza() {
		return visualizza;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController.iAmmGestUtentiView#getCancella()
	 */
	@Override
	public TextButton getCancella() {
		return cancella;
	}

}