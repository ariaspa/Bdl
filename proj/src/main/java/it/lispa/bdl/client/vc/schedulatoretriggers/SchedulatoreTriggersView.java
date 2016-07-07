package it.lispa.bdl.client.vc.schedulatoretriggers;

import it.lispa.bdl.shared.dto.SchedulatoreTriggerDTO;
import it.lispa.bdl.shared.messages.SchedulatoreTriggersMsg;
import it.lispa.bdl.shared.services.SchedulatoreTriggersService;
import it.lispa.bdl.shared.services.SchedulatoreTriggersServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * Class SchedulatoreTriggersView.
 */
public class SchedulatoreTriggersView extends ViewImpl implements SchedulatoreTriggersController.iSchedulatoreTriggersView {

	SchedulatoreTriggersServiceAsync servizio = (SchedulatoreTriggersServiceAsync) GWT.create(SchedulatoreTriggersService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, SchedulatoreTriggersView> {
	}

	private ListStore<SchedulatoreTriggerDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreTriggerDTO>> loader;

	@UiField(provided = true)
	Grid<SchedulatoreTriggerDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField
	ContentPanel panel;

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
	public SchedulatoreTriggersView(final Binder binder, SchedulatoreTriggersMsg messages) {

		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreTriggerDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreTriggerDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<SchedulatoreTriggerDTO>> callback) {
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
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreTriggerDTO>>(dataProxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};

		loader.setRemoteSort(true);
		
		final SchedulatoreTriggerDTO.SchedulatoreTriggerDTOProperties props = GWT.create(SchedulatoreTriggerDTO.SchedulatoreTriggerDTOProperties.class);

		store = new ListStore<SchedulatoreTriggerDTO>(props.codicePk());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, SchedulatoreTriggerDTO, PagingLoadResult<SchedulatoreTriggerDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<SchedulatoreTriggerDTO> identity = new IdentityValueProvider<SchedulatoreTriggerDTO>();
		final CheckBoxSelectionModel<SchedulatoreTriggerDTO> sm = new CheckBoxSelectionModel<SchedulatoreTriggerDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);
		
		
		ColumnConfig<SchedulatoreTriggerDTO, String> c1Col = new ColumnConfig<SchedulatoreTriggerDTO, String>(props.nomeUtente(), 80, messages.lNomeUtente());
		ColumnConfig<SchedulatoreTriggerDTO, Date> c2Col = new ColumnConfig<SchedulatoreTriggerDTO, Date>(props.dtStart(), 80, messages.lDtStart());
		//ColumnConfig<SchedulatoreTriggerDTO, Date> c3Col = new ColumnConfig<SchedulatoreTriggerDTO, Date>(props.dtEnd(), 150, messages.lDtEnd());
		ColumnConfig<SchedulatoreTriggerDTO, String> c4Col = new ColumnConfig<SchedulatoreTriggerDTO, String>(props.dsTipo(), 80, messages.lDsTipo());
		ColumnConfig<SchedulatoreTriggerDTO, String> c5Col = new ColumnConfig<SchedulatoreTriggerDTO, String>(props.dsEsito(), 80, messages.lDsEsito());
		ColumnConfig<SchedulatoreTriggerDTO, String> c6Col = new ColumnConfig<SchedulatoreTriggerDTO, String>(props.dsInput(), 240, messages.lDsInput());
		//ColumnConfig<SchedulatoreTriggerDTO, String> c7Col = new ColumnConfig<SchedulatoreTriggerDTO, String>(props.dsOutput(), 80, messages.lDsOutput());

		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		DateCell dc = new DateCell(fmt);
		c2Col.setCell(dc);
		//c3Col.setCell(dc);
		
		List<ColumnConfig<SchedulatoreTriggerDTO, ?>> l = new ArrayList<ColumnConfig<SchedulatoreTriggerDTO, ?>>();
		l.add(sm.getColumn());
		l.add(c1Col);
		l.add(c2Col);
		//l.add(c3Col);
		l.add(c4Col);
		l.add(c5Col);
		l.add(c6Col);
		//l.add(c7Col);

		orderFieldDefault = c2Col.getPath();
		orderDirDefault = "desc";
		loader.addSortInfo(new SortInfoBean(c2Col.getPath(), SortDir.DESC));

		ColumnModel<SchedulatoreTriggerDTO> cm = new ColumnModel<SchedulatoreTriggerDTO>(l);

		grid = new Grid<SchedulatoreTriggerDTO>(store, cm) {
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

		final GridFilters<SchedulatoreTriggerDTO> filters = new GridFilters<SchedulatoreTriggerDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		/*
		filters.addFilter(new StringFilter<SchedulatoreTriggerDTO>(props.nome()));
		filters.addFilter(new StringFilter<SchedulatoreTriggerDTO>(props.cognome()));
		filters.addFilter(new StringFilter<SchedulatoreTriggerDTO>(props.cf()));
		filters.addFilter(new StringFilter<SchedulatoreTriggerDTO>(props.email()));
		*/
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<SchedulatoreTriggerDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<SchedulatoreTriggerDTO> event) {
				int size = event.getSelection().size();
				if (size > 1) {
					cancella.setEnabled(true);
				} else if (size == 1) {
					cancella.setEnabled(true);
				} else {
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
	 * @see it.lispa.bdl.client.vc.SchedulatoreTriggers.SchedulatoreTriggersController.iSchedulatoreTriggersView#load()
	 */
	public void load() {
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreTriggers.SchedulatoreTriggersController.iSchedulatoreTriggersView#getGrid()
	 */
	public Grid<SchedulatoreTriggerDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreTriggers.SchedulatoreTriggersController.iSchedulatoreTriggersView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreTriggers.SchedulatoreTriggersController.iSchedulatoreTriggersView#getCancella()
	 */
	@Override
	public TextButton getCancella() {
		return cancella;
	}
	public void loadGridData(){
		loader.load();
	}

}