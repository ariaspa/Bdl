package it.lispa.bdl.client.vc.schedulatorejobs;

import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.messages.SchedulatoreJobsMsg;
import it.lispa.bdl.shared.services.SchedulatoreJobsService;
import it.lispa.bdl.shared.services.SchedulatoreJobsServiceAsync;
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
 * Class SchedulatoreJobsView.
 */
public class SchedulatoreJobsView extends ViewImpl implements SchedulatoreJobsController.iSchedulatoreJobsView {

	SchedulatoreJobsServiceAsync servizio = (SchedulatoreJobsServiceAsync) GWT.create(SchedulatoreJobsService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, SchedulatoreJobsView> {
	}

	private ListStore<SchedulatoreJobsDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreJobsDTO>> loader;

	@UiField(provided = true)
	Grid<SchedulatoreJobsDTO> grid;

	@UiField(provided = true)
	PagingToolBar pagToolBar;

	@UiField
	ContentPanel panel;

	@UiField
	TextButton cancella;

	@UiField
	TextButton visualizza;
	
	private final String orderFieldDefault;
	private final String orderDirDefault;

	/**
	 * Istanzia un nuovo amm gest utenti view.
	 *
	 * @param binder  binder
	 * @param messages  messages
	 */
	@Inject
	public SchedulatoreJobsView(final Binder binder, SchedulatoreJobsMsg messages) {

		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreJobsDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreJobsDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<SchedulatoreJobsDTO>> callback) {
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
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SchedulatoreJobsDTO>>(dataProxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};

		loader.setRemoteSort(true);
		
		final SchedulatoreJobsDTO.SchedulatoreJobsDTOProperties props = GWT.create(SchedulatoreJobsDTO.SchedulatoreJobsDTOProperties.class);

		store = new ListStore<SchedulatoreJobsDTO>(props.cdBdlJobs());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, SchedulatoreJobsDTO, PagingLoadResult<SchedulatoreJobsDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<SchedulatoreJobsDTO> identity = new IdentityValueProvider<SchedulatoreJobsDTO>();
		final CheckBoxSelectionModel<SchedulatoreJobsDTO> sm = new CheckBoxSelectionModel<SchedulatoreJobsDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);
		
		
		ColumnConfig<SchedulatoreJobsDTO, String> c1Col = new ColumnConfig<SchedulatoreJobsDTO, String>(props.nomeUtente(), 80, messages.lNomeUtente());
		ColumnConfig<SchedulatoreJobsDTO, Date> c2Col = new ColumnConfig<SchedulatoreJobsDTO, Date>(props.dtStart(), 80, messages.lDtStart());
		ColumnConfig<SchedulatoreJobsDTO, Date> c3Col = new ColumnConfig<SchedulatoreJobsDTO, Date>(props.dtEnd(), 80, messages.lDtEnd());
		ColumnConfig<SchedulatoreJobsDTO, String> c4Col = new ColumnConfig<SchedulatoreJobsDTO, String>(props.dsTipo(), 80, messages.lDsTipo());
		ColumnConfig<SchedulatoreJobsDTO, String> c5Col = new ColumnConfig<SchedulatoreJobsDTO, String>(props.dsEsito(), 80, messages.lDsEsito());
		ColumnConfig<SchedulatoreJobsDTO, String> c6Col = new ColumnConfig<SchedulatoreJobsDTO, String>(props.dsInput(), 80, messages.lDsInput());
		ColumnConfig<SchedulatoreJobsDTO, String> c7Col = new ColumnConfig<SchedulatoreJobsDTO, String>(props.dsOutput(), 290, messages.lDsOutput());


		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		DateCell dc = new DateCell(fmt);
		c2Col.setCell(dc);
		c3Col.setCell(dc);
		
		List<ColumnConfig<SchedulatoreJobsDTO, ?>> l = new ArrayList<ColumnConfig<SchedulatoreJobsDTO, ?>>();
		l.add(sm.getColumn());
		l.add(c1Col);
		l.add(c2Col);
		l.add(c3Col);
		l.add(c4Col);
		l.add(c5Col);
		l.add(c6Col);
		l.add(c7Col);

		orderFieldDefault = c2Col.getPath();
		orderDirDefault = "desc";
		loader.addSortInfo(new SortInfoBean(c2Col.getPath(), SortDir.DESC));

		ColumnModel<SchedulatoreJobsDTO> cm = new ColumnModel<SchedulatoreJobsDTO>(l);

		grid = new Grid<SchedulatoreJobsDTO>(store, cm) {
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

		final GridFilters<SchedulatoreJobsDTO> filters = new GridFilters<SchedulatoreJobsDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		/*
		filters.addFilter(new StringFilter<SchedulatoreJobsDTO>(props.nome()));
		filters.addFilter(new StringFilter<SchedulatoreJobsDTO>(props.cognome()));
		filters.addFilter(new StringFilter<SchedulatoreJobsDTO>(props.cf()));
		filters.addFilter(new StringFilter<SchedulatoreJobsDTO>(props.email()));
		*/
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<SchedulatoreJobsDTO>() {

			public void onSelectionChanged(SelectionChangedEvent<SchedulatoreJobsDTO> event) {
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
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsController.iSchedulatoreJobsView#load()
	 */
	public void load() {
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsController.iSchedulatoreJobsView#getGrid()
	 */
	public Grid<SchedulatoreJobsDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsController.iSchedulatoreJobsView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController.iAmmGestIndexView#getVisualizza()
	 */
	@Override
	public TextButton getVisualizza() {
		return visualizza;
	}
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsController.iSchedulatoreJobsView#getCancella()
	 */
	@Override
	public TextButton getCancella() {
		return cancella;
	}
	public void loadGridData(){
		loader.load();
	}

}