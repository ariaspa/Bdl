package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.messages.CatProgettoMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

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
 * Class CatProgettoController.
 */
public class CatProgettoController extends PresenterWidget<CatProgettoController.iCatProgettoView> implements CatProgettoHandler{

	/**
	 * Interface iCatProgettoView.
	 */
	public interface iCatProgettoView extends View, HasUiHandlers<CatProgettoHandler> {
		void refreshFields(ProgettoDTO item);
		CardLayoutContainer getStepLayout();
		FieldSet getfFieldset();
		ProgettoDTO getProgettoDTO();
		void activateModifyForm();
		void activateView();
	}

	CatalogazioneOggettiServiceAsync servizioCatalogazione = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);
	
	ProgettoDTO item;
	
	@Inject CatProgettoMsg messages;

	CatalogazioneOggettiController listener;

	EventBus eventBus;
	
	/**
	 * Istanzia un nuovo cat progetto controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatProgettoController(final EventBus eventBus, final iCatProgettoView view) {
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


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoHandler#onVAnnulla()
	 */
	@Override
	public void onVAnnulla() {
		this.listener.goToVuoto();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoHandler#onVModifica()
	 */
	@Override
	public void onVModifica() {
		activateModifyForm(this.item);
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoHandler#onFAnnulla()
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
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoHandler#onFSalva()
	 */
	@Override
	public void onFSalva() {
		
		BigDecimal cdProgetto = null;
		if(this.item!=null){
			cdProgetto = this.item.getCdProgetto();
		}
		final ProgettoDTO progetto = getView().getProgettoDTO();
		progetto.setCdProgetto(cdProgetto);
		
		servizioCatalogazione.salvaProgetto(progetto, new AsyncCallback<ProgettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(ProgettoDTO progetto) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.esitoModifica();
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