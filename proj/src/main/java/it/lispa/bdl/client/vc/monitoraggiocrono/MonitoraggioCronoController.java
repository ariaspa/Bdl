
package it.lispa.bdl.client.vc.monitoraggiocrono;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.messages.MonitoraggioCronoMsg;
import it.lispa.bdl.shared.services.MonitoraggioCronoService;
import it.lispa.bdl.shared.services.MonitoraggioCronoServiceAsync;

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
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class MonitoraggioCronoController.
 */
public class MonitoraggioCronoController
extends
Presenter<MonitoraggioCronoController.iMonitoraggioCronoView, MonitoraggioCronoController.iMonitoraggioCronoProxy> implements MonitoraggioCronoHandler{

	@Inject MonitoraggioCronoMsg messages;

	MonitoraggioCronoServiceAsync servizioMonitoraggioCrono = (MonitoraggioCronoServiceAsync) GWT.create(MonitoraggioCronoService.class);
	
	/**
	 * Interface iMonitoraggioCronoView.
	 */
	public interface iMonitoraggioCronoView extends View, HasUiHandlers<MonitoraggioCronoHandler> {
		
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
		TextButton getResetta();
	}

	/**
	 * Interface iMonitoraggioCronoProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.MonitoraggioCrono)
	public interface iMonitoraggioCronoProxy extends	ProxyPlace<MonitoraggioCronoController> {
	}

	/**
	 * Istanzia un nuovo monitoraggio crono controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param oggettoListaController  oggetto lista controller
	 * @param oggettoDettaglioController  oggetto dettaglio controller
	 */
	@Inject
	public MonitoraggioCronoController(	
			final EventBus eventBus, 
			final iMonitoraggioCronoView view, 
			final iMonitoraggioCronoProxy proxy,
			final MonCronoListaController oggettoListaController,
			final MonCronoDettaglioController oggettoDettaglioController) {

		super(eventBus, view, proxy);
		
		this.oggettoListaController = oggettoListaController;
		this.oggettoListaController.setListener(this);

		this.oggettoDettaglioController = oggettoDettaglioController;
		this.oggettoDettaglioController.setListener(this);
		
		getView().setUiHandlers(this);
	}

	final MonCronoListaController oggettoListaController;
	final MonCronoDettaglioController oggettoDettaglioController;
	
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
				getView().getResetta().enable();
				changeContent(event.getSelectedItem());
			}			
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		GWT.log("onReveal di MonitoraggioCrono");
		getView().loadTreeData();
		goToOggettoList(null,null,null);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di MonitoraggioCrono");
		getView().loadTreeData();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocrono.MonitoraggioCronoHandler#onResetta()
	 */
	public void onResetta(){

		SubMenuItemDTO sel = getView().getTree().getSelectionModel().getSelectedItem();
		getView().getTree().getSelectionModel().deselect(sel);
		getView().getResetta().disable();
		changeContent(null);
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
		}else{
			goToOggettoList(null, null, null);
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
		oggettoDettaglioController.activateMonCrono(cdIstituto, cdProgetto,cdCollezione, item);
		getView().setContentSlot(oggettoDettaglioController);		
	}
}