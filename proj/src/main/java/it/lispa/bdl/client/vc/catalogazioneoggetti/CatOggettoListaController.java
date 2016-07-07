package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CatOggettoListaMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * Class CatOggettoListaController.
 */
public class CatOggettoListaController extends PresenterWidget<CatOggettoListaController.iCatOggettoListaView> implements CatOggettoListaHandler{

	/**
	 * Interface iCatOggettoListaView.
	 */
	public interface iCatOggettoListaView extends View, HasUiHandlers<CatOggettoListaHandler> {
		
		/**
		 * Load grid data.
		 */
		public void loadGridData();
		
		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<VOggettoDTO> getGrid();		
		
		/**
		 * Imposta codice collezione.
		 *
		 * @param cdCollezione nuovo codice collezione
		 */
		public void setCdCollezione(BigDecimal cdCollezione);
	}

	CatalogazioneOggettiServiceAsync servizioCatalogazione = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	BigDecimal cdProgetto;
	BigDecimal cdCollezione;

	@Inject CatOggettoListaMsg messages;

	CatalogazioneOggettiController listener;

	EventBus eventBus;

	/**
	 * Istanzia un nuovo cat oggetto lista controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatOggettoListaController(final EventBus eventBus, final iCatOggettoListaView view) {
		super(eventBus, view);
		this.eventBus = eventBus;
		getView().setUiHandlers(this);
	}

	private final SaveHandler saveHandler = new SaveHandler() {
		public void onSave(ChangeGridState event) {
			getView().loadGridData();
		}
	};

	@Override
	protected void onBind() {
		super.onBind();

		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));

	}

	@Override
	protected void onReveal() {
		super.onReveal();
		getView().loadGridData();

	}

	protected void onReset() {
		super.onReset();
		getView().loadGridData();

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



	/**
	 * Legge codice progetto.
	 *
	 * @return codice progetto
	 */
	public BigDecimal getCdProgetto() {
		return cdProgetto;
	}
	
	/**
	 * Imposta codice progetto.
	 *
	 * @param cdProgetto nuovo codice progetto
	 */
	public void setCdProgetto(BigDecimal cdProgetto) {
		this.cdProgetto = cdProgetto;
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
	 * Activate list.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdCollezione  codice collezione
	 */
	public void activateList(BigDecimal cdProgetto, BigDecimal cdCollezione){
		this.cdProgetto = cdProgetto;
		this.cdCollezione = cdCollezione;
		this.getView().setCdCollezione(cdCollezione);
		this.getView().loadGridData();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaHandler#onLRichiediValidazione()
	 */
	@Override
	public void onLRichiediValidazione() {

		final List<VOggettoDTO> gridItems = getView().getGrid().getSelectionModel().getSelectedItems();

		String message;

		Boolean esisteOggettoSup = false;
		// cerco degli oggetti superiori tra quelli selezionati
		Iterator<VOggettoDTO> gridItemsItr = gridItems.iterator();
		while(gridItemsItr.hasNext()){
			VOggettoDTO item = gridItemsItr.next();
			if(BdlSharedConstants.FLAG_TRUE.equals(item.getO_FlOggettoSuperiore())){
				esisteOggettoSup = true;
			}
		}

		if(esisteOggettoSup){
			new GXTAlertBox(messages.lTitlePanel(), messages.lOggettoSupInSelection(), GXTAlertBox.DO_SHOW);			
		}else{
			if(gridItems.size() == 1) {
				message = messages.lRichiediValidazioneConfirm(gridItems.get(0).getO_DnTitolo());
			} else {
				message = messages.lRichiediValidazioneAllConfirm(gridItems.size());
			}

			ConfirmMessageBox box = new ConfirmMessageBox(messages.lTitlePanel(), message);
			box.setSize("400","120");
			box.addHideHandler(new HideHandler() {

				public void onHide(HideEvent event) {
					Dialog btn = (Dialog) event.getSource();
					if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
						servizioCatalogazione.mandaInValidazione(gridItems, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								// gestisco l'errore AsyncServiceException
								new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
							}
							@Override
							public void onSuccess(Void v) {	
								eventBus.fireEvent(new ChangeGridState());
								new GXTMessageBox(messages.lTitlePanel(), messages.lRichiediValidazioneEsito(), GXTMessageBox.DO_SHOW);
							}
						});
					} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
						// perform NO action
					}
				}
			});
			box.show();

		}


	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaHandler#onLVisualizza()
	 */
	@Override
	public void onLVisualizza() {

		VOggettoDTO gridItem = getView().getGrid().getSelectionModel().getSelectedItem();

		servizioCatalogazione.getOggetto(gridItem.getO_CdOggettoOriginale(), new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.lTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO item) {
				listener.goToOggettoDettaglio(item);
			}
		});
	}

	@Override
	public void onLImportaExcel() {
		this.listener.goToOggettoImportaExcel(cdProgetto, cdCollezione);
	}
	
	@Override
	public void onLImportaToc() {
		this.listener.goToOggettoImportaToc(cdProgetto, cdCollezione);
	}
	
	@Override
	public void onLImportaCatalogo() {
		VOggettoDTO gridItem = getView().getGrid().getSelectionModel().getSelectedItem();
		listener.goToOggettoImportaCatalogo(cdProgetto, cdCollezione, gridItem.getO_CdOggettoOriginale());
	}
}