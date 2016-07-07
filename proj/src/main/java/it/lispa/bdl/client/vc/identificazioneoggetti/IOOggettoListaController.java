package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.IOOggettoListaMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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
 * Class IOOggettoListaController.
 */
public class IOOggettoListaController extends PresenterWidget<IOOggettoListaController.iIOOggettoListaView> implements IOOggettoListaHandler{

	/**
	 * Interface iIOOggettoListaView.
	 */
	public interface iIOOggettoListaView extends View, HasUiHandlers<IOOggettoListaHandler> {
		
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
		 * Imposta codice collezione.
		 *
		 * @param cdCollezione nuovo codice collezione
		 */
		public void setCdCollezione(BigDecimal cdCollezione);
	}

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;
	
	@Inject IOOggettoListaMsg messages;

	IdentificazioneOggettiController listener;

	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo IO oggetto lista controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOOggettoListaController(final EventBus eventBus, final iIOOggettoListaView view) {
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
	public IdentificazioneOggettiController getListener() {
		return listener;
	}


	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(IdentificazioneOggettiController listener) {
		this.listener = listener;
	}
	


	/**
	 * Legge codice progetto.
	 *
	 * @return codice progetto
	 */
	public BigDecimal getCdProgetto() {
		return cdProgetto;
	}
	
	/**
	 * Imposta codice progetto.
	 *
	 * @param cdProgetto nuovo codice progetto
	 */
	public void setCdProgetto(BigDecimal cdProgetto) {
		this.cdProgetto = cdProgetto;
	}
	

	/**
	 * Legge codice collezione.
	 *
	 * @return codice collezione
	 */
	public BigDecimal getCdCollezione() {
		return cdCollezione;
	}
	
	/**
	 * Imposta codice collezione.
	 *
	 * @param cdCollezione nuovo codice collezione
	 */
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
	}
	
	/**
	 * Activate list.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void activateList(BigDecimal cdProgetto, BigDecimal cdCollezione){
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		this.getView().setCdCollezione(cdCollezione);
		this.getView().loadGridData();
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLInserisciOggetto()
	 */
	@Override
	public void onLInserisciOggetto() {
		this.listener.goToOggettoInsertForm(this.cdProgetto,this.cdCollezione);		
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLImportaCatalogo()
	 */
	@Override
	public void onLImportaCatalogo() {
		this.listener.goToOggettoImportaCatalogo(cdProgetto, cdCollezione);
		
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLImportaExcel()
	 */
	@Override
	public void onLImportaExcel() {
		this.listener.goToOggettoImportaExcel(cdProgetto, cdCollezione);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLScaricaTemplate()
	 */
	@Override
	public void onLScaricaTemplate() {
		String link = GWT.getHostPageBaseURL() + "../IdentificazioneOggettiTpl.xlsx";

		Window.open(link, "_blank", null);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLVerifica()
	 */
	@Override
	public void onLVerifica() {

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
				message = messages.lVerificaConfirm(gridItems.get(0).getO_DnTitolo());
			} else {
				message = messages.lVerificaAllConfirm(gridItems.size());
			}

			ConfirmMessageBox box = new ConfirmMessageBox(messages.lTitlePanel(), message);
			box.setSize("450","120");
			box.addHideHandler(new HideHandler() {

				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
						servizioIdentOggetti.mandaInVerifica(gridItems, new AsyncCallback<Void>() {
		        			public void onFailure(Throwable caught) {
		        				// gestisco l'errore AsyncServiceException
		        				new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
		        			}
							@Override
							public void onSuccess(Void v) {	
								eventBus.fireEvent(new ChangeGridState());
								new GXTMessageBox(messages.lTitlePanel(), messages.lVerificaEsito(), GXTMessageBox.DO_SHOW);
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
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaHandler#onLVisualizza()
	 */
	@Override
	public void onLVisualizza() {

		VOggettoDTO gridItem = getView().getGrid().getSelectionModel().getSelectedItem();

		servizioIdentOggetti.getOggetto(gridItem.getO_CdOggettoOriginale(), new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO item) {
				listener.goToOggettoView(item);
			}
		});
		/*
		 * MOCK
		OggettoDTO oggetto = new OggettoDTO(
				new BigDecimal(12), 
				new BigDecimal(1), "Testo a stampa",
				new BigDecimal(2), "Il signore degli anelli", 
				new BigDecimal(33), "Il mio progetto", 
				new BigDecimal(12), "La mia collezione", 
				"Il tutolodi questo", false, new BigDecimal(12),
				false, false, "", false,
				"", it.lispa.bdl.shared.utils.BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO, "", new BigDecimal(122),
				new BigDecimal(12),"me",new java.util.Date(), "", new java.util.Date());
		this.listener.goToOggettoModifyForm(oggetto);
		 */
		
	}
}