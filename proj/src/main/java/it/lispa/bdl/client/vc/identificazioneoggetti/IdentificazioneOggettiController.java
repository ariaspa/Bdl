
package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.messages.IdentificazioneOggettiMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

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
 * Class IdentificazioneOggettiController.
 */
public class IdentificazioneOggettiController
extends
Presenter<IdentificazioneOggettiController.iIdentificazioneOggettiView, IdentificazioneOggettiController.iIdentificazioneOggettiProxy> implements IdentificazioneOggettiHandler{

	@Inject IdentificazioneOggettiMsg messages;

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	/**
	 * Interface iIdentificazioneOggettiView.
	 */
	public interface iIdentificazioneOggettiView extends View, HasUiHandlers<IdentificazioneOggettiHandler> {
		
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
	 * Interface iIdentificazioneOggettiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.IdentificazioneOggetti)
	public interface iIdentificazioneOggettiProxy extends	ProxyPlace<IdentificazioneOggettiController> {
	}

	/**
	 * Istanzia un nuovo identificazione oggetti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param vuotoController  vuoto controller
	 * @param progettoController  progetto controller
	 * @param collezioneController  collezione controller
	 * @param oggettoListaController  oggetto lista controller
	 * @param oggettoDettaglioController  oggetto dettaglio controller
	 * @param oggettoImportaExcelController  oggetto importa excel controller
	 * @param oggettoImportaCatalogoController  oggetto importa catalogo controller
	 */
	@Inject
	public IdentificazioneOggettiController(	
			final EventBus eventBus, 
			final iIdentificazioneOggettiView view, 
			final iIdentificazioneOggettiProxy proxy,
			final IOVuotoController vuotoController,
			final IOProgettoController progettoController,
			final IOCollezioneController collezioneController,
			final IOOggettoListaController oggettoListaController,
			final IOOggettoDettaglioController oggettoDettaglioController,
			final IOOggettoImportaExcelController oggettoImportaExcelController,
			final IOOggettoImportaCatalogoController oggettoImportaCatalogoController) {

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

		this.oggettoImportaExcelController = oggettoImportaExcelController;
		this.oggettoImportaExcelController.setListener(this);

		this.oggettoImportaCatalogoController = oggettoImportaCatalogoController;
		this.oggettoImportaCatalogoController.setListener(this);
		
		getView().setUiHandlers(this);
	}

    final IOVuotoController vuotoController;
    final IOProgettoController progettoController;
	final IOCollezioneController collezioneController;
	final IOOggettoListaController oggettoListaController;
	final IOOggettoDettaglioController oggettoDettaglioController;
	final IOOggettoImportaExcelController oggettoImportaExcelController;
	final IOOggettoImportaCatalogoController oggettoImportaCatalogoController;
	
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
		GWT.log("onReveal di IdentificazioneOggetti");
		getView().loadTreeData();
		goToVuoto();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di IdentificazioneOggetti");
		getView().loadTreeData();
	}

	private void changeContent(SubMenuItemDTO node) {

		if(node!=null){
			if(node.getTipo().equals(SubMenuItemDTO.TIPO_PROGETTO)){
				/*
				 * MOCK
				ProgettoDTO proj=new ProgettoDTO(new BigDecimal(23),"Il progetto di me","Il titolo breve","La descrizione IT","la descrizione EN",new java.util.Date(), null,it.lispa.bdl.shared.utils.BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO_HUMAN);
				this.goToProgettoView(proj); 
				 */

				servizioIdentOggetti.getProgetto(node.getCodice(), new AsyncCallback<ProgettoDTO>() {
					public void onFailure(Throwable caught) {
						// gestisco l'errore AsyncServiceException
						new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
					}
					@Override
					public void onSuccess(ProgettoDTO item) {
						goToProgettoView(item);
					}
				});
				
				
			}else if(node.getTipo().equals(SubMenuItemDTO.TIPO_COLLEZIONE)){
				/*
				 * MOCK
				CollezioneDTO coll=new CollezioneDTO(new BigDecimal(31) , new BigDecimal(23),
						"Il progetto di me", "La collezione di me", "La descrizione di me in italiano",
						"La descrizione di me in inglese","diritti","ambitodisciplinare",
						"Copertura geografica", "PERIODONE", "1209 forse 12010",
						"1390 forse 1391");
				this.goToCollezioneView(coll);
				 */
				servizioIdentOggetti.getCollezione(node.getCodice(), new AsyncCallback<CollezioneDTO>() {
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
				/*
				 * MOCK
				this.goToOggettoList(new BigDecimal(31),node.getCodice()); 
				 */
				
				servizioIdentOggetti.getCollezione(node.getCodice(), new AsyncCallback<CollezioneDTO>() {
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
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToProgettoInsertForm()
	 */
	@Override
	public void goToProgettoInsertForm() {
		progettoController.activateInsertForm();
		getView().resetTreeSelection();
		getView().setContentSlot(progettoController);		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToProgettoModifyForm(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	public void goToProgettoModifyForm(ProgettoDTO item) {
		progettoController.activateModifyForm(item);
		getView().setContentSlot(progettoController);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToProgettoView(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	public void goToProgettoView(ProgettoDTO item) {
		progettoController.activateView(item);
		getView().setContentSlot(progettoController);		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToCollezioneInsertForm(java.math.BigDecimal)
	 */
	public void goToCollezioneInsertForm(BigDecimal cdProgetto) {
		collezioneController.activateInsertForm(cdProgetto);
		getView().setContentSlot(collezioneController);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToCollezioneModifyForm(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	public void goToCollezioneModifyForm(CollezioneDTO item) {
		collezioneController.activateModifyForm(item);
		getView().setContentSlot(collezioneController);		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiHandler#goToCollezioneView(it.lispa.bdl.shared.dto.CollezioneDTO)
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
	 * Go to oggetto insert form.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void goToOggettoInsertForm(BigDecimal cdProgetto, BigDecimal cdCollezione) {
		oggettoDettaglioController.activateInsertForm(cdProgetto, cdCollezione);
		getView().setContentSlot(oggettoDettaglioController);		
	}
	
	/**
	 * Go to oggetto modify form.
	 *
	 * @param item  item
	 */
	public void goToOggettoModifyForm(OggettoDTO item) {
		oggettoDettaglioController.activateModifyForm(item.getCdProgetto(),item.getCdCollezione(),item);
		getView().setContentSlot(oggettoDettaglioController);		
	}
	
	/**
	 * Go to oggetto view.
	 *
	 * @param item  item
	 */
	public void goToOggettoView(OggettoDTO item) {
		oggettoDettaglioController.activateView(item.getCdProgetto(),item.getCdCollezione(),item);
		getView().setContentSlot(oggettoDettaglioController);		
	}
	
	/**
	 * Go to oggetto importa excel.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void goToOggettoImportaExcel(BigDecimal cdProgetto, BigDecimal cdCollezione) {
		oggettoImportaExcelController.setCdCollezione(cdCollezione);
		addToPopupSlot(oggettoImportaExcelController);
	}
	
	/**
	 * Go to oggetto importa catalogo.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void goToOggettoImportaCatalogo(BigDecimal cdProgetto, BigDecimal cdCollezione) {
		oggettoImportaCatalogoController.setCdCollezione(cdCollezione);
		oggettoImportaCatalogoController.activateStep1();
		addToPopupSlot(oggettoImportaCatalogoController);
	}
}