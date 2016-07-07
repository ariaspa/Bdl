package it.lispa.bdl.client.vc.monitoraggiocrono;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.MonCronoDettaglioMsg;
import it.lispa.bdl.shared.services.MonitoraggioCronoService;
import it.lispa.bdl.shared.services.MonitoraggioCronoServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * Class MonCronoDettaglioView.
 */
public class MonCronoDettaglioView extends ViewWithUiHandlers<MonCronoDettaglioHandler> implements MonCronoDettaglioController.iMonCronoDettaglioView {

	OggettoDTO item;

	MonitoraggioCronoServiceAsync servizioMonitoraggioCrono = (MonitoraggioCronoServiceAsync) GWT.create(MonitoraggioCronoService.class);

	private final Widget widget;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
	ImmagineDTO.ImmagineDTOProperties imageProps = GWT.create(ImmagineDTO.ImmagineDTOProperties.class);

	private ListStore<CronologiaDTO> gridCronologiaStore;
	@UiField(provided = true) Grid<CronologiaDTO> gridCronologia;

	MonCronoDettaglioMsg messages;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, MonCronoDettaglioView> {
	}

	/**
	 * Istanzia un nuovo mon crono dettaglio view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param myMessages  my messages
	 * @param img  img
	 */
	@Inject
	public MonCronoDettaglioView(final EventBus eventBus, final Binder binder, MonCronoDettaglioMsg myMessages, Images img) {

		super();

		this.messages = myMessages;

		CronologiaDTO.CronologiaDTOProperties gridCronologiaProps = GWT.create(CronologiaDTO.CronologiaDTOProperties.class);

		gridCronologiaStore = new ListStore<CronologiaDTO>(gridCronologiaProps.cdCronologia());

		ColumnConfig<CronologiaDTO, String> col1Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.descrizione(), 150, messages.colGridDescrizione());
		ColumnConfig<CronologiaDTO, String> col2Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.ruolo(), 80, messages.colGridRuolo());
		ColumnConfig<CronologiaDTO, String> col3Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.cognomeNome(), 80, messages.colGridCognomeNome());
		ColumnConfig<CronologiaDTO, Date>   col4Cronologia = new ColumnConfig<CronologiaDTO, Date>(gridCronologiaProps.dataOperazione(), 150, messages.colGridDataOperazione());
		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		DateCell dcData = new DateCell(fmt);
		col4Cronologia.setCell(dcData);
		List<ColumnConfig<CronologiaDTO, ?>> lCronologia = new ArrayList<ColumnConfig<CronologiaDTO, ?>>();
		lCronologia.add(col1Cronologia);
		lCronologia.add(col2Cronologia);
		lCronologia.add(col3Cronologia);
		lCronologia.add(col4Cronologia);

		ColumnModel<CronologiaDTO> cmCronologia = new ColumnModel<CronologiaDTO>(lCronologia);

		gridCronologia = new Grid<CronologiaDTO>(gridCronologiaStore, cmCronologia);

		gridCronologia.getView().setStripeRows(true);
		gridCronologia.getView().setAutoFill(true);
		
		widget = binder.createAndBindUi(this);

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/**
	 * Refresh data.
	 *
	 * @param item  item
	 */
	public void refreshData(final OggettoDTO item) {
		this.item = item;

		/*
		 * MOCK
		gridCronologiaStore.clear();
		gridCronologiaStore.addAll(it.lispa.bdl.client.mockservice.Mock.MonCronoDettaglioView_refreshDataGridCronologia());
		 */

		servizioMonitoraggioCrono.getCronologia(item.getCdOggettoOriginale(),new AsyncCallback<List<CronologiaDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<CronologiaDTO> oggetti) {
				gridCronologiaStore.clear();
				gridCronologiaStore.addAll(oggetti);
			}
		});

	}

	/**
	 * On l indietro.
	 *
	 * @param event  event
	 */
	@UiHandler("lIndietro")
	public void onLIndietro(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLIndietro();
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoDettaglioController.iMonCronoDettaglioView#activateMonCrono(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Override
	public void activateMonCrono(OggettoDTO item) {
		this.refreshData(item);
	}
}