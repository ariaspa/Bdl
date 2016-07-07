package it.lispa.bdl.client.vc.validazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.ValOggettoListaMsg;
import it.lispa.bdl.shared.services.ValidazioneOggettiService;
import it.lispa.bdl.shared.services.ValidazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * Class ValOggettoListaController.
 */
public class ValOggettoListaController extends PresenterWidget<ValOggettoListaController.iValOggettoListaView> implements ValOggettoListaHandler{

	/**
	 * Interface iValOggettoListaView.
	 */
	public interface iValOggettoListaView extends View, HasUiHandlers<ValOggettoListaHandler> {
		
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
	
	ValidazioneOggettiServiceAsync servizioValidazione = (ValidazioneOggettiServiceAsync) GWT.create(ValidazioneOggettiService.class);

	BigDecimal cdIstituto;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;
	
	@Inject ValOggettoListaMsg messages;
	ValidazioneOggettiController listener;
	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo val oggetto lista controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public ValOggettoListaController(final EventBus eventBus, final iValOggettoListaView view) {
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
	public ValidazioneOggettiController getListener() {
		return listener;
	}
	
	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(ValidazioneOggettiController listener) {
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
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoListaHandler#onLValida()
	 */
	@Override
	public void onLValida() {
		final List<VOggettoDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();
		String message;
		Boolean esisteOggettoSup = false;
		// cerco degli oggetti superiori tra quelli selezionati
		Iterator<VOggettoDTO> gridItemsItr = gridItems.iterator();
		while(gridItemsItr.hasNext()){
			VOggettoDTO item = gridItemsItr.next();
			if(BdlSharedConstants.FLAG_TRUE.equals(item.getO_FlOggettoSuperiore())){
				esisteOggettoSup = true;
			}
		}
		if(esisteOggettoSup){
			new GXTAlertBox(messages.lTitlePanel(), messages.lOggettoSupInSelection(), GXTAlertBox.DO_SHOW);			
		}else{
			if(gridItems.size() == 1) {
				message = messages.lValidaConfirm(gridItems.get(0).getO_DnTitolo());
			} else {
				message = messages.lValidaAllConfirm(gridItems.size());
			}
				
			ConfirmMessageBox box = new ConfirmMessageBox(messages.lTitlePanel(), message);
			box.setSize("400","120");
			box.addHideHandler(new HideHandler() {
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
						servizioValidazione.validaOggetti(gridItems, new AsyncCallback<Void>() {
		        			public void onFailure(Throwable caught) {
		        				// gestisco l'errore AsyncServiceException
		        				new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
		        			}
							@Override
							public void onSuccess(Void v) {	
								eventBus.fireEvent(new ChangeGridState());
								new GXTMessageBox(messages.lTitlePanel(), messages.lValidaEsito(), GXTMessageBox.DO_SHOW);
							}
						});
					} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
						// perform NO action
					}
				}
			});
			box.show();
		}			
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoListaHandler#onLVisualizza()
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