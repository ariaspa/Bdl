package it.lispa.bdl.client.vc.monitoraggiocrono;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.MonCronoListaMsg;
import it.lispa.bdl.shared.services.MonitoraggioCronoService;
import it.lispa.bdl.shared.services.MonitoraggioCronoServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * Class MonCronoListaController.
 */
public class MonCronoListaController extends PresenterWidget<MonCronoListaController.iMonCronoListaView> implements MonCronoListaHandler{

	/**
	 * Interface iMonCronoListaView.
	 */
	public interface iMonCronoListaView extends View, HasUiHandlers<MonCronoListaHandler> {
		
		/**
		 * Load grid data.
		 */
		public void loadGridData();
		
		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<VOggettoDTO> getGrid();		
		
		/**
		 * Imposta codice istituto.
		 *
		 * @param cdIstituto nuovo codice istituto
		 */
		public void setCdIstituto(BigDecimal cdIstituto);
		
		/**
		 * Imposta codice progetto.
		 *
		 * @param cdProgetto nuovo codice progetto
		 */
		public void setCdProgetto(BigDecimal cdProgetto);
		
		/**
		 * Imposta codice collezione.
		 *
		 * @param cdCollezione nuovo codice collezione
		 */
		public void setCdCollezione(BigDecimal cdCollezione);
	}
	
	MonitoraggioCronoServiceAsync servizioValidazione = (MonitoraggioCronoServiceAsync) GWT.create(MonitoraggioCronoService.class);

	BigDecimal cdIstituto;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;
	
	@Inject MonCronoListaMsg messages;
	MonitoraggioCronoController listener;
	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo mon crono lista controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public MonCronoListaController(final EventBus eventBus, final iMonCronoListaView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
		getView().setUiHandlers(this);
	}
	private final SaveHandler saveHandler = new SaveHandler() {
		public void onSave(ChangeGridState event) {
			getView().loadGridData();
		}
	};
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		getView().loadGridData();	
	}
	protected void onReset() {
		super.onReset();
		getView().loadGridData();	
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
	 * Activate list.
	 *
	 * @param cdIstituto  codice istituto
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void activateList(BigDecimal cdIstituto, BigDecimal cdProgetto, BigDecimal cdCollezione){
		this.cdIstituto = cdIstituto;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		this.getView().setCdIstituto(cdIstituto);
		this.getView().setCdProgetto(cdProgetto);
		this.getView().setCdCollezione(cdCollezione);
		this.getView().loadGridData();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaHandler#onLVisualizza()
	 */
	@Override
	public void onLVisualizza() {
		VOggettoDTO gridItem = getView().getGrid().getSelectionModel().getSelectedItem();
		servizioValidazione.getOggetto(gridItem.getO_CdOggettoOriginale(), new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO item) {
				listener.goToOggettoDettaglio(cdIstituto, cdProgetto,cdCollezione, item);
			}
		});
	}
}