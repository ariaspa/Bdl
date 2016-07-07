
package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.messages.CatalogazioneOggettiMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class CatalogazioneOggettiController.
 */
public class CatalogazioneOggettiController 
extends Presenter<CatalogazioneOggettiController.iCatalogazioneOggettiView, CatalogazioneOggettiController.iCatalogazioneOggettiProxy> 
implements CatalogazioneOggettiHandler {

	@Inject CatalogazioneOggettiMsg messages;

	CatalogazioneOggettiServiceAsync servizioCatalogazione = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	/**
	 * Interface iCatalogazioneOggettiView.
	 */
	public interface iCatalogazioneOggettiView extends View, HasUiHandlers<CatalogazioneOggettiHandler> {
		
		/**
		 * Load tree data.
		 */
		public void loadTreeData();

		void setInSlot(Object slot, Widget content);
		void setContentSlot(PresenterWidget<?> content);
		SubMenuItemDTO getTreeItem();
		void setTreeItem(SubMenuItemDTO itm);
		Tree<SubMenuItemDTO, String> getTree();
		void resetContentSlot();
		void resetTreeSelection();
	}

	/**
	 * Interface iCatalogazioneOggettiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.CatalogazioneOggetti)
	public interface iCatalogazioneOggettiProxy extends	ProxyPlace<CatalogazioneOggettiController> {
	}

	/**
	 * Istanzia un nuovo catalogazione oggetti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param vuotoController  vuoto controller
	 * @param progettoController  progetto controller
	 * @param collezioneController  collezione controller
	 * @param oggettoListaController  oggetto lista controller
	 * @param oggettoDettaglioController  oggetto dettaglio controller
	 * @param oggettoAnteprimaImmagineController  oggetto anteprima immagine controller
	 */
	@Inject
	public CatalogazioneOggettiController(	
			final EventBus eventBus, 
			final iCatalogazioneOggettiView view, 
			final iCatalogazioneOggettiProxy proxy,
			final CatVuotoController vuotoController,
			final CatProgettoController progettoController,
			final CatCollezioneController collezioneController,
			final CatOggettoListaController oggettoListaController,
			final CatOggettoDettaglioController oggettoDettaglioController,
			final CatOggettoAnteprimaImmagineController oggettoAnteprimaImmagineController,
			final CatOggettoImportaExcelController oggettoImportaExcelController,
			final CatOggettoImportaTocController oggettoImportaTocController,
			final CatOggettoImportaCatalogoController oggettoImportaCatalogoController) {

		super(eventBus, view, proxy);

		this.vuotoController = vuotoController;
		this.vuotoController.setListener(this);

		this.progettoController = progettoController;
		this.progettoController.setListener(this);

		this.collezioneController = collezioneController;
		this.collezioneController.setListener(this);

		this.oggettoListaController = oggettoListaController;
		this.oggettoListaController.setListener(this);

		this.oggettoDettaglioController = oggettoDettaglioController;
		this.oggettoDettaglioController.setListener(this);

		this.oggettoAnteprimaImmagineController = oggettoAnteprimaImmagineController;
		this.oggettoAnteprimaImmagineController.setListener(this);

		this.oggettoImportaExcelController = oggettoImportaExcelController;
		this.oggettoImportaExcelController.setListener(this);
		
		this.oggettoImportaTocController = oggettoImportaTocController;
		this.oggettoImportaTocController.setListener(this);
		
		this.oggettoImportaCatalogoController = oggettoImportaCatalogoController;
		this.oggettoImportaCatalogoController.setListener(this);

		getView().setUiHandlers(this);
	}

	final CatVuotoController vuotoController;
	final CatProgettoController progettoController;
	final CatCollezioneController collezioneController;
	final CatOggettoListaController oggettoListaController;
	final CatOggettoDettaglioController oggettoDettaglioController;
	final CatOggettoAnteprimaImmagineController oggettoAnteprimaImmagineController;
	final CatOggettoImportaExcelController oggettoImportaExcelController;
	final CatOggettoImportaTocController oggettoImportaTocController;
	final CatOggettoImportaCatalogoController oggettoImportaCatalogoController;

	/** La Costante IDOGG_CONTENT. */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> IDOGG_CONTENT = new Type<RevealContentHandler<?>>();

	private final SaveHandler saveHandler = new SaveHandler() {
		public void onSave(ChangeGridState event) {
			getView().loadTreeData();
		}
	};

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));

		getView().getTree().getSelectionModel().addSelectionHandler(new SelectionHandler<SubMenuItemDTO>() {
			public void onSelection(SelectionEvent<SubMenuItemDTO> event) {
				getView().setTreeItem(event.getSelectedItem());
				changeContent(event.getSelectedItem());
			}			
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		GWT.log("onReveal di CatalogazioneOggetti");
		getView().loadTreeData();
		goToVuoto();
	}

	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di CatalogazioneOggetti");
		getView().loadTreeData();
	}

	private void changeContent(SubMenuItemDTO node) {

		if(node!=null){
			if(node.getTipo().equals(SubMenuItemDTO.TIPO_PROGETTO)){
				servizioCatalogazione.getProgetto(node.getCodice(), new AsyncCallback<ProgettoDTO>() {
					public void onFailure(Throwable caught) {
						// gestisco l'errore AsyncServiceException
						new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
					}
					@Override
					public void onSuccess(ProgettoDTO item) {
						goToProgettoView(item);
					}
				});


			} else if(node.getTipo().equals(SubMenuItemDTO.TIPO_COLLEZIONE)){
				servizioCatalogazione.getCollezione(node.getCodice(), new AsyncCallback<CollezioneDTO>() {
					public void onFailure(Throwable caught) {
						// gestisco l'errore AsyncServiceException
						new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
					}
					@Override
					public void onSuccess(CollezioneDTO item) {
						goToCollezioneView(item);
					}
				});
			}else if(node.getTipo().equals(SubMenuItemDTO.TIPO_COLLOGGETTI)){
				servizioCatalogazione.getCollezione(node.getCodice(), new AsyncCallback<CollezioneDTO>() {
					public void onFailure(Throwable caught) {
						// gestisco l'errore AsyncServiceException
						new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
					}
					@Override
					public void onSuccess(CollezioneDTO item) {
						goToOggettoList(item.getCdProgetto(),item.getCdCollezione());
					}
				});
			}else{
				goToVuoto();				
			}
		}
	}

	/**
	 * Go to vuoto.
	 */
	public void goToVuoto() {
		getView().resetTreeSelection();
		getView().setContentSlot(vuotoController);		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiHandler#goToProgettoModifyForm(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	public void goToProgettoModifyForm(ProgettoDTO item) {
		progettoController.activateModifyForm(item);
		getView().setContentSlot(progettoController);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiHandler#goToProgettoView(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	public void goToProgettoView(ProgettoDTO item) {
		progettoController.activateView(item);
		getView().setContentSlot(progettoController);		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiHandler#goToCollezioneModifyForm(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	public void goToCollezioneModifyForm(CollezioneDTO item) {
		collezioneController.activateModifyForm(item);
		getView().setContentSlot(collezioneController);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiHandler#goToCollezioneView(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	public void goToCollezioneView(CollezioneDTO item) {
		collezioneController.activateView(item);
		getView().setContentSlot(collezioneController);		
	}

	/**
	 * Go to oggetto list.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void goToOggettoList(BigDecimal cdProgetto,BigDecimal cdCollezione) {
		oggettoListaController.activateList(cdProgetto,cdCollezione);
		getView().setContentSlot(oggettoListaController);		
	}
	
	/**
	 * Go to oggetto dettaglio.
	 *
	 * @param item  item
	 */
	public void goToOggettoDettaglio(OggettoDTO item) {
		oggettoDettaglioController.activateCatalogazione(item.getCdProgetto(),item.getCdCollezione(),item);
		getView().setContentSlot(oggettoDettaglioController);		
	}

	/**
	 * Go to oggetto anteprima immagine.
	 *
	 * @param immagine  immagine
	 */
	public void goToOggettoAnteprimaImmagine(ImmagineDTO immagine) {
		oggettoAnteprimaImmagineController.setImmagine(immagine);
		addToPopupSlot(oggettoAnteprimaImmagineController);
	}

	public void goToOggettoImportaExcel(BigDecimal cdProgetto, BigDecimal cdCollezione) {
		oggettoImportaExcelController.setCdCollezione(cdCollezione);
		addToPopupSlot(oggettoImportaExcelController);
	}
	
	public void goToOggettoImportaToc(BigDecimal cdProgetto, BigDecimal cdCollezione) {
		oggettoImportaTocController.setCdCollezione(cdCollezione);
		addToPopupSlot(oggettoImportaTocController);
	}
	
	/**
	 * Go to oggetto importa catalogo.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param cdOggetto  codice oggetto
	 */
	public void goToOggettoImportaCatalogo(BigDecimal cdProgetto, BigDecimal cdCollezione, BigDecimal cdOggetto) {
		oggettoImportaCatalogoController.setCdCollezione(cdCollezione);
		oggettoImportaCatalogoController.setCdOggetto(cdOggetto);
		//attivo lo step 1
		oggettoImportaCatalogoController.activateStep1();
		//visualizzo la pop-up passandole tutte le info
		addToPopupSlot(oggettoImportaCatalogoController);
	}
}