package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.messages.IOProgettoMsg;
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
 * Class IOProgettoController.
 */
public class IOProgettoController extends PresenterWidget<IOProgettoController.iIOProgettoView> implements IOProgettoHandler{

	/**
	 * Interface iIOProgettoView.
	 */
	public interface iIOProgettoView extends View, HasUiHandlers<IOProgettoHandler> {
		void refreshFields(ProgettoDTO item);
		CardLayoutContainer getStepLayout();
		FieldSet getfFieldset();
		ProgettoDTO getProgettoDTO();
		void activateInsertForm();
		void activateModifyForm();
		void activateView();
	}

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	ProgettoDTO item;
	
	@Inject IOProgettoMsg messages;

	IdentificazioneOggettiController listener;

	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo IO progetto controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOProgettoController(final EventBus eventBus, final iIOProgettoView view) {
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
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoHandler#onVAnnulla()
	 */
	@Override
	public void onVAnnulla() {
		this.listener.goToVuoto();
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoHandler#onVCollezione()
	 */
	@Override
	public void onVCollezione() {
		this.listener.goToCollezioneInsertForm(this.item.getCdProgetto());		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoHandler#onVModifica()
	 */
	@Override
	public void onVModifica() {
		activateModifyForm(this.item);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoHandler#onFAnnulla()
	 */
	@Override
	public void onFAnnulla() {
		if(this.item!=null){
			activateView(this.item);
		}else{
			this.listener.goToVuoto();
		}
			
		
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoHandler#onFSalva()
	 */
	@Override
	public void onFSalva() {
		
		BigDecimal cdProgetto = null;
		if(this.item!=null){
			cdProgetto = this.item.getCdProgetto();
		}
		final ProgettoDTO progetto = getView().getProgettoDTO();
		progetto.setCdProgetto(cdProgetto);
		
		servizioIdentOggetti.salvaProgetto(progetto, new AsyncCallback<ProgettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(ProgettoDTO progetto) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.esitoInserimento();
				if(item!=null){
					message = messages.esitoModifica();
				}
				new GXTMessageBox(messages.infoTitle(), message, GXTMessageBox.DO_SHOW);
				
				activateView(progetto);
			}
		});
	}
	


	/**
	 * Legge item.
	 *
	 * @return item
	 */
	public ProgettoDTO getItem() {
		return item;
	}


	/**
	 * Imposta item.
	 *
	 * @param item nuovo item
	 */
	public void setItem(ProgettoDTO item) {
		this.item = item;
	}
	
	/**
	 * Activate insert form.
	 */
	public void activateInsertForm(){
		this.item = null;
		getView().getfFieldset().setHeadingText(messages.fFieldsetNuovo());
		getView().refreshFields(this.item);
		getView().activateInsertForm();
		
	}
	
	/**
	 * Activate modify form.
	 *
	 * @param item  item
	 */
	public void activateModifyForm(ProgettoDTO item){
		this.item = item;
		getView().getfFieldset().setHeadingText(messages.fFieldsetModifica());
		getView().refreshFields(this.item);
		getView().activateModifyForm();
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(ProgettoDTO item){
		this.item = item;
		getView().refreshFields(this.item);
		getView().activateView();
	}

}