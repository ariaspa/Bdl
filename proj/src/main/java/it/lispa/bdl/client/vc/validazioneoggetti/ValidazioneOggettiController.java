
package it.lispa.bdl.client.vc.validazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.messages.ValidazioneOggettiMsg;
import it.lispa.bdl.shared.services.ValidazioneOggettiService;
import it.lispa.bdl.shared.services.ValidazioneOggettiServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
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
 * Class ValidazioneOggettiController.
 */
public class ValidazioneOggettiController
extends
Presenter<ValidazioneOggettiController.iValidazioneOggettiView, ValidazioneOggettiController.iValidazioneOggettiProxy> implements ValidazioneOggettiHandler{

	@Inject ValidazioneOggettiMsg messages;

	ValidazioneOggettiServiceAsync servizioValidazione = (ValidazioneOggettiServiceAsync) GWT.create(ValidazioneOggettiService.class);
	
	/**
	 * Interface iValidazioneOggettiView.
	 */
	public interface iValidazioneOggettiView extends View, HasUiHandlers<ValidazioneOggettiHandler> {
		
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
	 * Interface iValidazioneOggettiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.ValidazioneOggetti)
	public interface iValidazioneOggettiProxy extends	ProxyPlace<ValidazioneOggettiController> {
	}

	/**
	 * Istanzia un nuovo validazione oggetti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param oggettoListaController  oggetto lista controller
	 * @param oggettoDettaglioController  oggetto dettaglio controller
	 * @param oggettoAnteprimaImmagineController  oggetto anteprima immagine controller
	 */
	@Inject
	public ValidazioneOggettiController(	
			final EventBus eventBus, 
			final iValidazioneOggettiView view, 
			final iValidazioneOggettiProxy proxy,
			final ValOggettoListaController oggettoListaController,
			final ValOggettoDettaglioController oggettoDettaglioController,
			final ValOggettoAnteprimaImmagineController oggettoAnteprimaImmagineController) {

		super(eventBus, view, proxy);
		
		this.oggettoListaController = oggettoListaController;
		this.oggettoListaController.setListener(this);

		this.oggettoDettaglioController = oggettoDettaglioController;
		this.oggettoDettaglioController.setListener(this);

		this.oggettoAnteprimaImmagineController = oggettoAnteprimaImmagineController;
		this.oggettoAnteprimaImmagineController.setListener(this);
		
		getView().setUiHandlers(this);
	}

	final ValOggettoListaController oggettoListaController;
	final ValOggettoDettaglioController oggettoDettaglioController;
	final ValOggettoAnteprimaImmagineController oggettoAnteprimaImmagineController;
	
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
		GWT.log("onReveal di ValidazioneOggetti");
		getView().loadTreeData();
		goToOggettoList(null,null,null);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di ValidazioneOggetti");
		getView().loadTreeData();
	}

	private void changeContent(SubMenuItemDTO node) {

		if(node!=null){
			if(node.getTipo().equals(SubMenuItemDTO.TIPO_ISTITUTO)){
				goToOggettoList(node.getCodice(), null, null);				
			}else if(node.getTipo().equals(SubMenuItemDTO.TIPO_PROGETTO)){
				goToOggettoList(null, node.getCodice(), null);				
			}else if(node.getTipo().equals(SubMenuItemDTO.TIPO_COLLEZIONE)){
				goToOggettoList(null, null, node.getCodice());
			}else{
				goToOggettoList(null, null, null);
			}
		}
	}

	/**
	 * Go to oggetto list.
	 *
	 * @param cdIstituto  codice istituto
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void goToOggettoList(BigDecimal cdIstituto, BigDecimal cdProgetto,BigDecimal cdCollezione) {
		oggettoListaController.activateList(cdIstituto, cdProgetto, cdCollezione);
		getView().setContentSlot(oggettoListaController);		
	}
	
	/**
	 * Go to oggetto dettaglio.
	 *
	 * @param cdIstituto  codice istituto
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 * @param item  item
	 */
	public void goToOggettoDettaglio(BigDecimal cdIstituto, BigDecimal cdProgetto,BigDecimal cdCollezione, OggettoDTO item) {
		oggettoDettaglioController.activateValidazione(cdIstituto, cdProgetto,cdCollezione, item);
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
}