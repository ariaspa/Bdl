package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.messages.IOCollezioneMsg;
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
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldSet;

/**
 * Class IOCollezioneController.
 */
public class IOCollezioneController extends PresenterWidget<IOCollezioneController.iIOCollezioneView> implements IOCollezioneHandler{

	/**
	 * Interface iIOCollezioneView.
	 */
	public interface iIOCollezioneView extends View, HasUiHandlers<IOCollezioneHandler> {
		void refreshFields(CollezioneDTO item);
		CardLayoutContainer getStepLayout();
		FieldSet getfFieldset();
		CollezioneDTO getCollezioneDTO();

		void activateInsertForm();
		void activateModifyForm();
		void activateView();
	}

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	CollezioneDTO item;
	BigDecimal cdProgetto;
	
	@Inject IOCollezioneMsg messages;

	IdentificazioneOggettiController listener;

	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo IO collezione controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOCollezioneController(final EventBus eventBus, final iIOCollezioneView view) {
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
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneHandler#onVAnnulla()
	 */
	@Override
	public void onVAnnulla() {
		this.listener.goToVuoto();
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneHandler#onVOggetto()
	 */
	@Override
	public void onVOggetto() {
		this.listener.goToOggettoInsertForm(this.item.getCdProgetto(),this.item.getCdCollezione());
		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneHandler#onVModifica()
	 */
	@Override
	public void onVModifica() {
		activateModifyForm(this.item);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneHandler#onFAnnulla()
	 */
	@Override
	public void onFAnnulla() {
		if(this.item!=null){
			activateView(this.item);
		}else{
			this.listener.goToProgettoView(this.listener.progettoController.getItem());
		}
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneHandler#onFSalva()
	 */
	@Override
	public void onFSalva() {
		BigDecimal cdCollezione = null;
		if(this.item!=null){
			cdCollezione = this.item.getCdCollezione();
		}
		final CollezioneDTO collezione = getView().getCollezioneDTO();
		
		collezione.setCdCollezione(cdCollezione);
		collezione.setCdProgetto(cdProgetto);
		
		servizioIdentOggetti.salvaCollezione(collezione, new AsyncCallback<CollezioneDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(CollezioneDTO collezione) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.esitoInserimento();
				if(item!=null){
					message = messages.esitoModifica();
				}
				new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
				activateView(collezione);
			}
		});
		
	}
	


	/**
	 * Legge item.
	 *
	 * @return item
	 */
	public CollezioneDTO getItem() {
		return item;
	}


	/**
	 * Imposta item.
	 *
	 * @param item nuovo item
	 */
	public void setItem(CollezioneDTO item) {
		this.item = item;
	}
	
	/**
	 * Activate insert form.
	 *
	 * @param cdProgetto  codice progetto
	 */
	public void activateInsertForm(BigDecimal cdProgetto){
		this.item = null;
		this.cdProgetto = cdProgetto;
		getView().getfFieldset().setHeadingText(messages.fFieldsetNuovo());
		getView().refreshFields(this.item);
		getView().activateInsertForm();
		
	}
	
	/**
	 * Activate modify form.
	 *
	 * @param item  item
	 */
	public void activateModifyForm(CollezioneDTO item){
		this.item = item;
		this.cdProgetto = item.getCdProgetto();
		getView().getfFieldset().setHeadingText(messages.fFieldsetModifica());
		getView().refreshFields(this.item);
		getView().activateModifyForm();
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(CollezioneDTO item){
		this.item = item;
		this.cdProgetto = item.getCdProgetto();
		getView().refreshFields(this.item);
		getView().activateView();
	}

}