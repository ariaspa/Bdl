package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.messages.CatOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
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

/**
 * Class CatOggettoDettaglioController.
 */
public class CatOggettoDettaglioController extends PresenterWidget<CatOggettoDettaglioController.iCatOggettoDettaglioView> implements CatOggettoDettaglioHandler{

	/**
	 * Interface iCatOggettoDettaglioView.
	 */
	public interface iCatOggettoDettaglioView extends View, HasUiHandlers<CatOggettoDettaglioHandler> {

		void activateCatalogazioneForm(OggettoDTO item, String naturaOggettoOriginale);
		void activateCatalogazioneView(OggettoDTO item, String naturaOggettoOriginale);
		void activateCronologia(OggettoDTO item, String naturaOggettoOriginale);
		void activateSommario(OggettoDTO item, String naturaOggettoOriginale);
		void activateAnteprima(OggettoDTO item, String naturaOggettoOriginale);
		void activateCorrezioni(OggettoDTO item, String naturaOggettoOriginale);
		OggettoDTO getOggettoDTO();
		String getNoteCorrezioni();
	}

	CatalogazioneOggettiServiceAsync servizioCatalogazione = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	String naturaOggettoOriginale;
	
	OggettoDTO item;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;

	@Inject CatOggettoDettaglioMsg messages;

	CatalogazioneOggettiController listener;

	EventBus eventBus;

	/**
	 * Istanzia un nuovo cat oggetto dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatOggettoDettaglioController(final EventBus eventBus, final iCatOggettoDettaglioView view) {
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
	public CatalogazioneOggettiController getListener() {
		return listener;
	}


	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(CatalogazioneOggettiController listener) {
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
	 * Activate catalogazione.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void activateCatalogazione(BigDecimal cdProgetto,BigDecimal cdCollezione, OggettoDTO item){
		this.setItem(item);
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		
		servizioCatalogazione.getNatura(item.getCdOggettoOriginale(), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// do nothing, errore gestito a monte
			}
			@Override
			public void onSuccess(String naturaOggetto) {
				naturaOggettoOriginale = naturaOggetto;
				getView().activateCatalogazioneView(getItem(), naturaOggettoOriginale);
			}
		});
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnAnnullaCatalogazioneView()
	 */
	@Override
	public void onBtnAnnullaCatalogazioneView() {
		listener.goToOggettoList(cdProgetto, cdCollezione);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnModificaCatalogazioneView()
	 */
	@Override
	public void onBtnModificaCatalogazioneView() {
		getView().activateCatalogazioneForm(item, naturaOggettoOriginale);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnEsportaMetsCatalogazioneView()
	 */
	@Override
	public void onBtnEsportaMetsCatalogazioneView() {
		String link = GWT.getHostPageBaseURL()+"../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+item.getCdOggettoOriginale()+"/mets";
		Window.open(link, "_blank", null);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnAnnullaCatalogazioneForm()
	 */
	@Override
	public void onBtnAnnullaCatalogazioneForm() {
		getView().activateCatalogazioneView(item, naturaOggettoOriginale);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnSalvaCatalogazioneForm()
	 */
	@Override
	public void onBtnSalvaCatalogazioneForm() {
		BigDecimal cdOggetto = null;
		if(this.item!=null){
			cdOggetto = this.item.getCdOggettoOriginale();
		}
		final OggettoDTO oggetto = getView().getOggettoDTO();
		oggetto.setCdOggettoOriginale(cdOggetto);
		oggetto.setCdCollezione(cdCollezione);

		servizioCatalogazione.salvaOggetto(oggetto, new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO oggetto) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.esitoSalvaCatalogazioneForm();
				new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
				activateCatalogazione(cdProgetto, cdCollezione, oggetto);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnSalvaSommario(java.util.List)
	 */
	@Override
	public void onBtnSalvaSommario(List<TocSommarioDTO> saveTreeSommarioData){

		servizioCatalogazione.salvaToc(this.item.getCdOggettoOriginale(), saveTreeSommarioData, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(Void v) {
				String message = messages.esitoSalvaSommario();
				new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnBookreaderAnteprima()
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
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnPdfAnteprima()
	 */
	@Override
	public void onBtnPdfAnteprima() {
		if(item.getOggettoDigitale()){
			String link = GWT.getHostPageBaseURL() + "../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+item.getCdOggettoOriginale()+"/pdf";
			Window.open(link,"pdf",null);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onBtnInviaCorrezioni()
	 */
	@Override
	public void onBtnInviaCorrezioni() {
		
		if(getView().getNoteCorrezioni()!=null && !"".equals(getView().getNoteCorrezioni().trim())){

			ConfirmMessageBox box = new ConfirmMessageBox(messages.infoTitle(), messages.confirmInvioCorrezioni());
			box.setSize("450","130");
			box.addHideHandler(new HideHandler() {

				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
						servizioCatalogazione.inviaCorrezioni(item.getCdOggettoOriginale(),getView().getNoteCorrezioni(), new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								// gestisco l'errore AsyncServiceException
								new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
							}
							@Override
							public void onSuccess(Void nullo) {
								eventBus.fireEvent(new ChangeGridState());
								String message = messages.esitoInvioCorrezioni();
								new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
								listener.goToOggettoList(cdProgetto, cdCollezione);	
							}
						});	
					} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
						// perform NO action
					}
				}
			});
			box.show();

		}else{
			new GXTAlertBox(messages.infoTitle(), messages.correzioneMancaTesto(), GXTAlertBox.DO_SHOW);
		}
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioHandler#onImgAnteprimaSelection(it.lispa.bdl.shared.dto.ImmagineDTO)
	 */
	@Override
	public void onImgAnteprimaSelection(ImmagineDTO img) {
		this.listener.goToOggettoAnteprimaImmagine(img);		
	}

	
}