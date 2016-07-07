package it.lispa.bdl.client.vc.monitoraggiocrono;

import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.MonCronoDettaglioMsg;
import it.lispa.bdl.shared.services.MonitoraggioCronoService;
import it.lispa.bdl.shared.services.MonitoraggioCronoServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * Class MonCronoDettaglioController.
 */
public class MonCronoDettaglioController extends PresenterWidget<MonCronoDettaglioController.iMonCronoDettaglioView> implements MonCronoDettaglioHandler{

	/**
	 * Interface iMonCronoDettaglioView.
	 */
	public interface iMonCronoDettaglioView extends View, HasUiHandlers<MonCronoDettaglioHandler> {

		void activateMonCrono(OggettoDTO item);

	}

	MonitoraggioCronoServiceAsync servizioMonitoraggioCrono = (MonitoraggioCronoServiceAsync) GWT.create(MonitoraggioCronoService.class);

	OggettoDTO item;
	BigDecimal cdIstituto;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;

	@Inject MonCronoDettaglioMsg messages;

	MonitoraggioCronoController listener;

	EventBus eventBus;

	/**
	 * Istanzia un nuovo mon crono dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public MonCronoDettaglioController(final EventBus eventBus, final iMonCronoDettaglioView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}

	@Override
	protected void onReveal() {

	}

	protected void onReset() {
		super.onReset();
	}


	/**
	 * Legge listener.
	 *
	 * @return listener
	 */
	public MonitoraggioCronoController getListener() {
		return listener;
	}

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(MonitoraggioCronoController listener) {
		this.listener = listener;
	}

	/**
	 * Legge item.
	 *
	 * @return item
	 */
	public OggettoDTO getItem() {
		return item;
	}
	
	/**
	 * Imposta item.
	 *
	 * @param item nuovo item
	 */
	public void setItem(OggettoDTO item) {
		this.item = item;
	}

	/**
	 * Activate mon crono.
	 *
	 * @param cdIstituto  codice istituto
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void activateMonCrono(BigDecimal cdIstituto, BigDecimal cdProgetto, BigDecimal cdCollezione, OggettoDTO item){
		this.item = item;
		this.cdIstituto = cdIstituto;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		getView().activateMonCrono(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoDettaglioHandler#onLIndietro()
	 */
	@Override
	public void onLIndietro() {
		this.listener.goToOggettoList(cdIstituto, cdProgetto, cdCollezione);		
	}

}