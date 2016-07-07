package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

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
 * Class IOOggettoImportaCatalogoController.
 */
public class IOOggettoImportaCatalogoController extends PresenterWidget<IOOggettoImportaCatalogoController.iIOOggettoImportaCatalogoView>
implements IOOggettoImportaCatalogoHandler{

	@Inject IOOggettoImportaCatalogoMsg messages;

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	/**
	 * Interface iIOOggettoImportaCatalogoView.
	 */
	public interface iIOOggettoImportaCatalogoView extends PopupView, HasUiHandlers<IOOggettoImportaCatalogoHandler> {
		
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
	
	IdentificazioneOggettiController listener;
	BigDecimal cdCollezione;
	
	final IOOggettoImportaCatalogoDettaglioController oggettoImportaCatalogoDettaglioController;
	
	/**
	 * Istanzia un nuovo IO oggetto importa catalogo controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param oggettoImportaCatalogoDettaglioController  oggetto importa catalogo dettaglio controller
	 */
	@Inject
	public IOOggettoImportaCatalogoController(final EventBus eventBus, final iIOOggettoImportaCatalogoView view,
			final IOOggettoImportaCatalogoDettaglioController oggettoImportaCatalogoDettaglioController) {
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
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoHandler#onAvanti()
	 */
	@Override
	public void onAvanti() {
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoHandler#onChiudi()
	 */
	@Override
	public void onChiudi() {
		getView().resetStep1Form();
		getView().hide();		
		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoHandler#onOpacSbnVisualizza(java.util.List)
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
	public void setListener(IdentificazioneOggettiController listener) {
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
	 * Activate step1.
	 */
	public void activateStep1(){
		getView().activateStep1();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoHandler#onOpacSbnImporta(java.util.List)
	 */
	public void onOpacSbnImporta(List<UnimarcDTO> gridItems){
		servizioIdentOggetti.opacSbnImport(cdCollezione,gridItems, new AsyncCallback<Void>() {
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
