package it.lispa.bdl.client.vc.pubblicazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.PubOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.PubblicazioneOggettiService;
import it.lispa.bdl.shared.services.PubblicazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;

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

/**
 * Class PubOggettoDettaglioController.
 */
public class PubOggettoDettaglioController extends PresenterWidget<PubOggettoDettaglioController.iPubOggettoDettaglioView> implements PubOggettoDettaglioHandler{

	/**
	 * Interface iPubOggettoDettaglioView.
	 */
	public interface iPubOggettoDettaglioView extends View, HasUiHandlers<PubOggettoDettaglioHandler> {

		void activatePubblicazione(OggettoDTO item, String naturaOggettoOriginale);
		void activateCatalogazioneView(OggettoDTO item, String naturaOggettoOriginale);
		void activateCronologia(OggettoDTO item, String naturaOggettoOriginale);
		void activateSommario(OggettoDTO item, String naturaOggettoOriginale);
		void activateAnteprima(OggettoDTO item, String naturaOggettoOriginale);
		void activateCorrezioni(OggettoDTO item, String naturaOggettoOriginale);
	}

	PubblicazioneOggettiServiceAsync servizioPubblicazione = (PubblicazioneOggettiServiceAsync) GWT.create(PubblicazioneOggettiService.class);

	String naturaOggettoOriginale;
	
	OggettoDTO item;
	BigDecimal cdIstituto;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;

	@Inject PubOggettoDettaglioMsg messages;

	PubblicazioneOggettiController listener;

	EventBus eventBus;

	/**
	 * Istanzia un nuovo pub oggetto dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public PubOggettoDettaglioController(final EventBus eventBus, final iPubOggettoDettaglioView view) {
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
	public PubblicazioneOggettiController getListener() {
		return listener;
	}

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(PubblicazioneOggettiController listener) {
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
	 * Activate pubblicazione.
	 *
	 * @param cdIstituto  codice istituto
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void activatePubblicazione(BigDecimal cdIstituto, BigDecimal cdProgetto, BigDecimal cdCollezione, OggettoDTO item){
		this.item = item;
		this.cdIstituto = cdIstituto;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		
		servizioPubblicazione.getNatura(item.getCdOggettoOriginale(), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// do nothing, errore gestito a monte
			}
			@Override
			public void onSuccess(String naturaOggetto) {
				naturaOggettoOriginale = naturaOggetto;
				getView().activatePubblicazione(getItem(), naturaOggettoOriginale);
			}
		});
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnAnnulla()
	 */
	@Override
	public void onBtnAnnulla() {
		listener.goToOggettoList(cdIstituto,cdProgetto, cdCollezione);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnPubblica()
	 */
	@Override
	public void onBtnPubblica() {
		
		String message = messages.pubblicaConfirm(item.getTitolo());
		ConfirmMessageBox box = new ConfirmMessageBox(messages.infoTitle(), message);
		box.setSize("400","120");
		box.addHideHandler(new HideHandler() {
			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					servizioPubblicazione.pubblicaOggetto(item.getCdOggettoOriginale(), new AsyncCallback<Void>() {
	        			public void onFailure(Throwable caught) {
	        				// gestisco l'errore AsyncServiceException
	        			}
						@Override
						public void onSuccess(Void v) {	
							eventBus.fireEvent(new ChangeGridState());
							new GXTMessageBox(messages.infoTitle(), messages.pubblicaEsito(), GXTMessageBox.DO_SHOW);
							listener.goToOggettoList(cdIstituto,cdProgetto, cdCollezione);		
						}
					});
				} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
					// perform NO action
				}
			}
		});
		box.show();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnDiniega(java.lang.String)
	 */
	@Override
	public void onBtnDiniega(final String val) {
		if(val==null || val.trim().equals("")){
			new GXTMessageBox(messages.infoTitle(), messages.diniegaMancaTesto(), GXTMessageBox.DO_SHOW);
		}else{
			String message = messages.diniegaConfirm(item.getTitolo());
			ConfirmMessageBox box = new ConfirmMessageBox(messages.infoTitle(), message);
			box.setSize("400","120");
			box.addHideHandler(new HideHandler() {
				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
						servizioPubblicazione.diniegaOggetto(item.getCdOggettoOriginale(), val, new AsyncCallback<Void>() {
		        			public void onFailure(Throwable caught) {
		        				// gestisco l'errore AsyncServiceException
		        			}
							@Override
							public void onSuccess(Void v) {	
								eventBus.fireEvent(new ChangeGridState());
								new GXTMessageBox(messages.infoTitle(), messages.diniegaEsito(), GXTMessageBox.DO_SHOW);
								listener.goToOggettoList(cdIstituto,cdProgetto, cdCollezione);
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
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnEsportaMetsCatalogazioneView()
	 */
	@Override
	public void onBtnEsportaMetsCatalogazioneView() {
		String link = GWT.getHostPageBaseURL() + "../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+ item.getCdOggettoOriginale()+"/mets";
		Window.open(link, "_blank", null);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnBookreaderAnteprima()
	 */
	@Override
	public void onBtnBookreaderAnteprima() {
		if(item.getOggettoDigitale()){
			if(naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_MAP)){
				String link = GWT.getHostPageBaseURL() + BdlSharedConstants.BDL_MAPREADER_PART_URL + item.getCdOggettoOriginale();
				Window.open(link,"mapreader",null);
			}else if(naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)){
				String link = GWT.getHostPageBaseURL() + BdlSharedConstants.BDL_AUDIOPLAYER_PART_URL + item.getCdOggettoOriginale();
				Window.open(link,"audioplayer","menubar=no,location=no,resizable=yes,scrollbars=yes,status=no,width=600,height=350");
			}else{
				String link = GWT.getHostPageBaseURL() + BdlSharedConstants.BDL_BOOKREADER_PART_URL + item.getCdOggettoOriginale();
				Window.open(link,"bookreader",null);
			} 
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onBtnPdfAnteprima()
	 */
	@Override
	public void onBtnPdfAnteprima() {
		if(item.getOggettoDigitale()){
			String link = GWT.getHostPageBaseURL() + "../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+item.getCdOggettoOriginale()+"/pdf";
			Window.open(link,"pdf",null);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioHandler#onImgAnteprimaSelection(it.lispa.bdl.shared.dto.ImmagineDTO)
	 */
	@Override
	public void onImgAnteprimaSelection(ImmagineDTO img) {
		this.listener.goToOggettoAnteprimaImmagine(img);		
	}

}