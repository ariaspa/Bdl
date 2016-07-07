package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.IOOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

import java.math.BigDecimal;

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
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.form.FieldSet;

/**
 * Class IOOggettoDettaglioController.
 */
public class IOOggettoDettaglioController extends PresenterWidget<IOOggettoDettaglioController.iIOOggettoDettaglioView> implements IOOggettoDettaglioHandler{

	/**
	 * Interface iIOOggettoDettaglioView.
	 */
	public interface iIOOggettoDettaglioView extends View, HasUiHandlers<IOOggettoDettaglioHandler> {
		void refreshFields(OggettoDTO item);
		CardLayoutContainer getStepLayout();
		FieldSet getfFieldset();
		OggettoDTO getOggettoDTO();

		void activateInsertForm();
		void activateModifyForm();
		void activateView();
	}

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	OggettoDTO item;
	BigDecimal cdProgetto;
	BigDecimal cdCollezione;
	
	@Inject IOOggettoDettaglioMsg messages;

	IdentificazioneOggettiController listener;

	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo IO oggetto dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOOggettoDettaglioController(final EventBus eventBus, final iIOOggettoDettaglioView view) {
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


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onVAnnulla()
	 */
	@Override
	public void onVAnnulla() {
		this.listener.goToOggettoList(this.cdProgetto,this.cdCollezione);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onVCancella()
	 */
	@Override
	public void onVCancella() {
		onFCancella();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onVModifica()
	 */
	@Override
	public void onVModifica() {
		activateModifyForm(this.cdProgetto, this.cdCollezione, this.item);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onFAnnulla()
	 */
	@Override
	public void onFAnnulla() {
		if(this.item!=null){
			activateView(this.cdProgetto, this.cdCollezione, this.item);
		}else{
			this.listener.goToOggettoList(this.cdProgetto,this.cdCollezione);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onFCancella()
	 */
	@Override
	public void onFCancella() {
		final BigDecimal myCdOggetto = this.item.getCdOggettoOriginale();

		ConfirmMessageBox box = new ConfirmMessageBox(messages.infoTitle(), messages.confirmCancellazione(this.item.getTitolo()));
		box.setSize("420","130");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					servizioIdentOggetti.cancellaOggetto(myCdOggetto, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// gestisco l'errore AsyncServiceException
							new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
						}
						@Override
						public void onSuccess(Void v) {
							eventBus.fireEvent(new ChangeGridState());
							String message = messages.esitoCancellazione();
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
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioHandler#onFSalva()
	 */
	@Override
	public void onFSalva() {
		BigDecimal cdOggetto = null;
		if(this.item!=null){
			cdOggetto = this.item.getCdOggettoOriginale();
		}
		final OggettoDTO oggetto = getView().getOggettoDTO();
		oggetto.setCdOggettoOriginale(cdOggetto);
		oggetto.setCdCollezione(cdCollezione);
		
		servizioIdentOggetti.salvaOggetto(oggetto, new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO oggetto) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.esitoInserimento();
				if(item!=null){
					message = messages.esitoModifica();
				}
				new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
				activateView(cdProgetto, cdCollezione, oggetto);
			}
		});
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
	 * Activate insert form.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void activateInsertForm(BigDecimal cdProgetto,BigDecimal cdCollezione){
		this.item = null;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		getView().getfFieldset().setHeadingText(messages.fFieldsetNuovo());
		getView().refreshFields(this.item);
		getView().activateInsertForm();
	}
	
	/**
	 * Activate modify form.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void activateModifyForm(BigDecimal cdProgetto,BigDecimal cdCollezione, OggettoDTO item){
		this.item = item;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		getView().getfFieldset().setHeadingText(messages.fFieldsetModifica());
		getView().refreshFields(this.item);
		getView().activateModifyForm();
	}
	
	/**
	 * Activate view.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void activateView(BigDecimal cdProgetto,BigDecimal cdCollezione, OggettoDTO item){
		this.item = item;
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		getView().refreshFields(this.item);
		getView().activateView();
	}

}