package it.lispa.bdl.client.vc.ammvalidutenti;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.AmmValidUtentiMsg;
import it.lispa.bdl.shared.services.AmmValidUtentiService;
import it.lispa.bdl.shared.services.AmmValidUtentiServiceAsync;
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
 * Class AmmValidUtentiView.
 */
public class AmmValidUtentiView extends ViewImpl implements
AmmValidUtentiController.iAmmValidUtentiView {

	AmmValidUtentiServiceAsync servizio = (AmmValidUtentiServiceAsync) GWT.create(AmmValidUtentiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmValidUtentiView> {
	}

	private ListStore<UtenteDTO> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<UtenteDTO>> loader;

	@UiField(provided = true) Grid<UtenteDTO> grid;

	@UiField(provided = true) PagingToolBar pagToolBar;

	@UiField ContentPanel panel;

	@UiField TextButton visualizza;

	@UiField TextButton valida;

	/**
	 * Istanzia un nuovo amm valid utenti view.
	 *
	 * @param binder  binder
	 * @param messages  messages
	 */
	@Inject
	public AmmValidUtentiView(final Binder binder, AmmValidUtentiMsg messages) {


		// Rpc Data Proxy
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UtenteDTO>> dataProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<UtenteDTO>>() {
			@SuppressWarnings("unchecked")
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<UtenteDTO>> callback) { 
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
				servizio.getUtentiDaValidare(filters, sortField, orderDir, loadConfig.getOffset(), loadConfig.getLimit(), callback);
			}
		};
		loader = new PagingLoader<FilterPagingLoadConfig,PagingLoadResult<UtenteDTO>>(dataProxy){
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		
		loader.setRemoteSort(true);
		final UtenteDTO.UtenteDTOProperties props = GWT.create(UtenteDTO.UtenteDTOProperties.class);


		store = new ListStore<UtenteDTO>(props.cdUtente());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, UtenteDTO, PagingLoadResult<UtenteDTO>>(store));

		pagToolBar = new PagingToolBar(25);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");

		IdentityValueProvider<UtenteDTO> identity = new IdentityValueProvider<UtenteDTO>();
		final CheckBoxSelectionModel<UtenteDTO> sm = new CheckBoxSelectionModel<UtenteDTO>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);

		ColumnConfig<UtenteDTO, String> nomeCol = new ColumnConfig<UtenteDTO, String>(props.nome(), 80, messages.nome());
		ColumnConfig<UtenteDTO, String> cognomeCol = new ColumnConfig<UtenteDTO, String>(props.cognome(), 150, messages.cognome());
		ColumnConfig<UtenteDTO, String> cfCol = new ColumnConfig<UtenteDTO, String>(props.cf(), 150, messages.cf());
		ColumnConfig<UtenteDTO, String> emailCol = new ColumnConfig<UtenteDTO, String>(props.email(), 150, messages.email());
		ColumnConfig<UtenteDTO, String> ruoloCol = new ColumnConfig<UtenteDTO, String>(props.ruolo(), 80, messages.ruolo());
		ColumnConfig<UtenteDTO, String> statoCol = new ColumnConfig<UtenteDTO, String>(props.statoHuman(), 80, messages.stato());
		ColumnConfig<UtenteDTO, Date> regCol = new ColumnConfig<UtenteDTO, Date>(props.dataRegistrazione(), 80, messages.dataRegistrazione());
		
		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		DateCell dc = new DateCell(fmt);
		regCol.setCell(dc);
		
		List<ColumnConfig<UtenteDTO, ?>> l = new ArrayList<ColumnConfig<UtenteDTO, ?>>();
		l.add(sm.getColumn());
		l.add(nomeCol);
		l.add(cognomeCol);
		l.add(cfCol);
		l.add(emailCol);
		l.add(ruoloCol);
		l.add(statoCol);
		l.add(regCol);

		loader.addSortInfo(new SortInfoBean(cognomeCol.getPath(), SortDir.ASC));		
		
		ColumnModel<UtenteDTO> cm = new ColumnModel<UtenteDTO>(l);

		grid = new Grid<UtenteDTO>(store, cm) {
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

		final GridFilters<UtenteDTO> filters = new GridFilters<UtenteDTO>(loader);
		filters.initPlugin(grid);
		filters.setLocal(false);
		filters.addFilter(new StringFilter<UtenteDTO>(props.nome()));
		filters.addFilter(new StringFilter<UtenteDTO>(props.cognome()));
		filters.addFilter(new StringFilter<UtenteDTO>(props.cf()));
		filters.addFilter(new StringFilter<UtenteDTO>(props.email()));
		/*
		filters.addFilter(new NumericFilter<UtenteDTO, Integer>(props.edad(), new NumberPropertyEditor.IntegerPropertyEditor()));
		 */
		

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
				filters.addFilter(new ListFilter<UtenteDTO, String>(props.ruolo(), fRuoloStore));
			}
		});

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UtenteDTO>() {

			public void onSelectionChanged(
					SelectionChangedEvent<UtenteDTO> event) {
				int size = event.getSelection().size();
				if(size > 1){
					visualizza.setEnabled(false);
					valida.setEnabled(true);
				}else if(size == 1){
					visualizza.setEnabled(true);
					valida.setEnabled(true);
				}else{
					visualizza.setEnabled(false);
					valida.setEnabled(false);
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
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController.iAmmValidUtentiView#getVisualizza()
	 */
	public TextButton getVisualizza() {
		return visualizza;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController.iAmmValidUtentiView#getValida()
	 */
	public TextButton getValida() {
		return valida;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController.iAmmValidUtentiView#load()
	 */
	public void load(){
		loader.load();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController.iAmmValidUtentiView#getGrid()
	 */
	public Grid<UtenteDTO> getGrid() {
		return grid;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController.iAmmValidUtentiView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

}