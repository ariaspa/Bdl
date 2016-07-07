package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.CatOggettoImportaCatalogoMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class CatOggettoImportaCatalogoController.
 */
public class CatOggettoImportaCatalogoController extends PresenterWidget<CatOggettoImportaCatalogoController.iCatOggettoImportaCatalogoView>
implements CatOggettoImportaCatalogoHandler{

	@Inject CatOggettoImportaCatalogoMsg messages;

	CatalogazioneOggettiServiceAsync servizioCatOggetti = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);
	
	/**
	 * Interface iCatOggettoImportaCatalogoView.
	 */
	public interface iCatOggettoImportaCatalogoView extends PopupView, HasUiHandlers<CatOggettoImportaCatalogoHandler> {
		
		/**
		 * Reset step1 form.
		 */
		public void resetStep1Form();
		
		/**
		 * Activate step1.
		 */
		public void activateStep1();
	}
	private EventBus eventBus;
	
	CatalogazioneOggettiController listener;
	BigDecimal cdCollezione;
	BigDecimal cdOggetto;
	
	final CatOggettoImportaCatalogoDettaglioController oggettoImportaCatalogoDettaglioController;
	
	/**
	 * Istanzia un nuovo Cat oggetto importa catalogo controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param oggettoImportaCatalogoDettaglioController  oggetto importa catalogo dettaglio controller
	 */
	@Inject
	public CatOggettoImportaCatalogoController(final EventBus eventBus, final iCatOggettoImportaCatalogoView view,
			final CatOggettoImportaCatalogoDettaglioController oggettoImportaCatalogoDettaglioController) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.oggettoImportaCatalogoDettaglioController = oggettoImportaCatalogoDettaglioController;
		this.oggettoImportaCatalogoDettaglioController.setListener(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.CatOggettoImportaCatalogoHandler#onAvanti()
	 */
	@Override
	public void onAvanti() {
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.CatOggettoImportaCatalogoHandler#onChiudi()
	 */
	@Override
	public void onChiudi() {
		getView().resetStep1Form();
		getView().hide();		
		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.CatOggettoImportaCatalogoHandler#onOpacSbnVisualizza(java.util.List)
	 */
	@Override
	public void onOpacSbnVisualizza(List<UnimarcDTO> items) {
		
		oggettoImportaCatalogoDettaglioController.getView().resetForm(items.get(0));
		addToPopupSlot(oggettoImportaCatalogoDettaglioController);
		oggettoImportaCatalogoDettaglioController.getView().show();
		
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
	 * Legge codice oggetto.
	 *
	 * @return codice oggetto
	 */
	public BigDecimal getCdOggetto() {
		return cdOggetto;
	}
	
	/**
	 * Imposta codice oggetto.
	 *
	 * @param cdOggetto nuovo codice oggetto
	 */
	public void setCdOggetto(BigDecimal cdOggetto) {
		this.cdOggetto = cdOggetto;
	}
	
	/**
	 * Activate step1.
	 */
	public void activateStep1(){
		getView().activateStep1();
	}
	
	public void onOpacSbnImporta(UnimarcDTO gridItem) {
		servizioCatOggetti.opacSbnImport(cdCollezione, cdOggetto, gridItem, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoPanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(Void v) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.opacSbnImportazioneOk();
				new GXTMessageBox(messages.infoPanel(), message, GXTMessageBox.DO_SHOW);
				onChiudi();
			}
		});
	}
}
