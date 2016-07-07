package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.CatOggettoImportaCatalogoMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class CatOggettoImportaCatalogoDettaglioController.
 */
public class CatOggettoImportaCatalogoDettaglioController extends PresenterWidget<CatOggettoImportaCatalogoDettaglioController.iCatOggettoImportaCatalogoDettaglioView>
implements CatOggettoImportaCatalogoDettaglioHandler{

	@Inject CatOggettoImportaCatalogoMsg messages;

	CatalogazioneOggettiServiceAsync servizioIdentOggetti = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);
	
	/**
	 * Interface iCatOggettoImportaCatalogoDettaglioView.
	 */
	public interface iCatOggettoImportaCatalogoDettaglioView extends PopupView, HasUiHandlers<CatOggettoImportaCatalogoDettaglioHandler> {
		
		/**
		 * Reset form.
		 *
		 * @param item  item
		 */
		public void resetForm(UnimarcDTO item);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	CatOggettoImportaCatalogoController listener;

	/**
	 * Istanzia un nuovo Cat oggetto importa catalogo dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatOggettoImportaCatalogoDettaglioController(final EventBus eventBus, final iCatOggettoImportaCatalogoDettaglioView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.CatOggettoImportaCatalogoDettaglioHandler#onChiudi()
	 */
	@Override
	public void onChiudi() {
		getView().resetForm(null);
		getView().hide();		
	}
	

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(CatOggettoImportaCatalogoController listener) {
		this.listener = listener;
	}
}
