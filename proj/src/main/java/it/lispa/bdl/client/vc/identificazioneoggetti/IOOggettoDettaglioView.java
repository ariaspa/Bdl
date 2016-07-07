package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.IOOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;

/**
 * Class IOOggettoDettaglioView.
 */
public class IOOggettoDettaglioView extends ViewWithUiHandlers<IOOggettoDettaglioHandler> implements IOOggettoDettaglioController.iIOOggettoDettaglioView {

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	private final Widget widget;
	
	@UiField CardLayoutContainer stepLayout;

	@UiField FieldSet vFieldset;
	@UiField VerticalLayoutContainer vFieldsetVlc;
	@UiField FieldSet fFieldset;
	@UiField VerticalLayoutContainer fFieldsetVlc;
	
	@UiField InlineLabel vTitolo;
	@UiField InlineLabel vTitoloSup;
	@UiField InlineLabel vImmaginiPreviste;
	@UiField InlineLabel vTipologiaOggetto;
	@UiField InlineLabel vIncludeAltriTitoli;

	@UiField HBoxLayoutContainer vButtons;
	@UiField HBoxLayoutContainer fButtons;

	@Inject IOOggettoDettaglioMsg messages;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
 
	@UiField TextField fTitolo;
	
	ListStore<ComboDTO> fTitoloSupStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField (provided = true) ComboBox<ComboDTO> fTitoloSup = new ComboBox<ComboDTO>(fTitoloSupStore, cmbProps.desc());
	
	ComboDTO fTitoloSupSelected;
	
	@UiField(provided = true) NumberField<Integer> fImmaginiPreviste;

	ListStore<ComboDTO> fTipologiaOggettoStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField (provided = true) ComboBox<ComboDTO> fTipologiaOggetto = new ComboBox<ComboDTO>(fTipologiaOggettoStore, cmbProps.desc());
	
	ComboDTO fTipologiaOggettoSelected;
	
	ListStore<ComboDTO> fIncludeAltriTitoliStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField (provided = true) ComboBox<ComboDTO> fIncludeAltriTitoli = new ComboBox<ComboDTO>(fIncludeAltriTitoliStore, cmbProps.desc());

	ComboDTO fIncludeAltriTitoliSelected;

	
	@UiField TextButton fSalva;
	@UiField TextButton fCancella;
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOOggettoDettaglioView> {
	}
    
	/**
	 * Istanzia un nuovo IO oggetto dettaglio view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public IOOggettoDettaglioView(final EventBus eventBus, final Binder binder) {
    	super();
    	
    	fImmaginiPreviste = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
    	fImmaginiPreviste.addValidator(new MinNumberValidator<Integer>(1));
		
        widget = binder.createAndBindUi(this);

        vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        
        fTitolo.addValidator(new MaxLengthValidator(500));
        
        /*
         * MOCK
        List<ComboDTO> items = new ArrayList<ComboDTO>();
        items.add(new ComboDTO(new BigDecimal(1),"Testo a stampa"));
        items.add(new ComboDTO(new BigDecimal(2),"Carta geografica"));
        fTipologiaOggettoStore.clear();
        fTipologiaOggettoStore.addAll(items);
         */

		servizioIdentOggetti.getTipologiaOggetti(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(List<ComboDTO> items) {
				fTipologiaOggettoStore.clear();
				fTipologiaOggettoStore.addAll(items);
			}
		});
		
        List<ComboDTO> items2 = new ArrayList<ComboDTO>();
        items2.add(new ComboDTO(new BigDecimal(1),BdlSharedConstants.FLAG_TRUE_HUMAN));
        items2.add(new ComboDTO(new BigDecimal(2),BdlSharedConstants.FLAG_FALSE_HUMAN));
        fIncludeAltriTitoliStore.clear();
        fIncludeAltriTitoliStore.addAll(items2);

        fIncludeAltriTitoli.addSelectionHandler(new SelectionHandler<ComboDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboDTO> event) {
	        	ComboDTO valueSelected = event.getSelectedItem();
        		fTitoloSupStore.clear();
        		fIncludeAltriTitoliSelected = valueSelected;
	        	if(valueSelected.getDesc().equals(BdlSharedConstants.FLAG_FALSE_HUMAN)){
	        		fImmaginiPreviste.setAllowBlank(false);
	        		fImmaginiPreviste.clearInvalid();
	        		/*
	        		 * MOCK
	                List<ComboDTO> items2 = new ArrayList<ComboDTO>();
	                items2.add(new ComboDTO(new BigDecimal(1),"La divina commedia"));
	                items2.add(new ComboDTO(new BigDecimal(2),"Il signore degli anelli"));
	                items2.add(new ComboDTO(new BigDecimal(3),"La bibbia"));
	                fTitoloSupStore.clear();
	                fTitoloSupStore.addAll(items2);
	                */
	        		servizioIdentOggetti.getOggettiSuperiori(new AsyncCallback<List<ComboDTO>>() {
	        			public void onFailure(Throwable caught) {
	        				// gestisco l'errore AsyncServiceException
	        				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
	        			}
	        			@Override
	        			public void onSuccess(List<ComboDTO> items) {
	    	                fTitoloSupStore.clear();
	    	                fTitoloSupStore.addAll(items);
	        			}
	        		});
	        	}else{
	        		fTitoloSupSelected = null;
	    			fTitoloSup.clear();
	        		fImmaginiPreviste.setAllowBlank(true);
	        		fImmaginiPreviste.setValue(null);
	        		fImmaginiPreviste.clearInvalid();
	        	}
	        }
	    });
        
        fTipologiaOggetto.addSelectionHandler(new SelectionHandler<ComboDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboDTO> event) {
	        	ComboDTO valueSelected = event.getSelectedItem();
	        	fTipologiaOggettoSelected = valueSelected;
	        	
	    		if(fTipologiaOggettoSelected!=null){
	    			checkCartografia(fTipologiaOggettoSelected.getId());
	    		}
	        }
	    });
        
        fTitoloSup.addSelectionHandler(new SelectionHandler<ComboDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboDTO> event) {
	        	ComboDTO valueSelected = event.getSelectedItem();
	        	fTitoloSupSelected = valueSelected;
	        }
	    });
        
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/**
	 * On v annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("vAnnulla")
	public void onVAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVAnnulla();
		}
	}
	
	/**
	 * On v oggetto.
	 *
	 * @param event  event
	 */
	@UiHandler("vCancella")
	public void onVOggetto(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVCancella();
		}
	}
	
	/**
	 * On v modifica.
	 *
	 * @param event  event
	 */
	@UiHandler("vModifica")
	public void onVModifica(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVModifica();
		}
	}
	
	/**
	 * On f annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("fAnnulla")
	public void onFAnnulla(SelectEvent event) {
		fCancella.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onFAnnulla();
		}
	}
	
	/**
	 * On f cancella.
	 *
	 * @param event  event
	 */
	@UiHandler("fCancella")
	public void onFCancella(SelectEvent event) {
		fCancella.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onFCancella();
		}
	}
	
	/**
	 * On f salva.
	 *
	 * @param event  event
	 */
	@UiHandler("fSalva")
	public void onFSalva(SelectEvent event) {
		
		fSalva.focus();

    	boolean areAllValid = true;

    	areAllValid = fTitolo.isValid() && areAllValid;
    	areAllValid = fTitoloSup.isValid() && areAllValid;
    	areAllValid = fImmaginiPreviste.isValid() && areAllValid;
    	areAllValid = fTipologiaOggetto.isValid() && areAllValid;
    	areAllValid = fIncludeAltriTitoli.isValid() && areAllValid;

    	if (areAllValid && getUiHandlers() != null) {
    		getUiHandlers().onFSalva();
        }
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#getStepLayout()
	 */
	public CardLayoutContainer getStepLayout() {
		return stepLayout;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#refreshFields(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	public void refreshFields(OggettoDTO item) {

		fTitolo.reset();
		fTitoloSup.reset();
		fImmaginiPreviste.reset();
		fImmaginiPreviste.enable();
		fTipologiaOggetto.reset();
		fIncludeAltriTitoli.reset();

		fTipologiaOggettoSelected = null;
		fTitoloSupSelected = null;
		fIncludeAltriTitoliSelected = null;
		
		if(item == null){
			fCancella.disable();
			
			vTitolo.setText("");
			vTitoloSup.setText("");
			vImmaginiPreviste.setText("");
			vTipologiaOggetto.setText("");
			vIncludeAltriTitoli.setText("");
			
			fTitolo.setValue("");
			fTitoloSup.clear();
			fImmaginiPreviste.setValue(null);
			fTipologiaOggetto.clear();
			fIncludeAltriTitoli.clear();
			fImmaginiPreviste.enable();
			
		}else{
			fCancella.enable();
			
			vTitolo.setText(item.getTitolo());
			vTitoloSup.setText(item.getTitoloOggettoOriginaleSup());
			vImmaginiPreviste.setText(item.getImmaginiPreviste()==null ? "" : item.getImmaginiPreviste().toString());
			vTipologiaOggetto.setText(item.getNomeTipoOggetto());
			if(item.getOggettoSuperiore()){
				vIncludeAltriTitoli.setText(BdlSharedConstants.FLAG_TRUE_HUMAN);
			}else{
				vIncludeAltriTitoli.setText(BdlSharedConstants.FLAG_FALSE_HUMAN);				
			}
			
			fTitolo.setValue(item.getTitolo());
			
			fImmaginiPreviste.setValue(item.getImmaginiPreviste()==null ? null : item.getImmaginiPreviste().intValue());
			
			fTipologiaOggettoSelected = new ComboDTO(item.getCdTipoOggetto(),item.getNomeTipoOggetto());
			fTipologiaOggetto.setValue(fTipologiaOggettoSelected);
			
			if(item.getOggettoSuperiore()){
				fIncludeAltriTitoliSelected = new ComboDTO(new BigDecimal(1),BdlSharedConstants.FLAG_TRUE_HUMAN);
				fIncludeAltriTitoli.setValue(fIncludeAltriTitoliSelected);
				fTitoloSupStore.clear();
        		fImmaginiPreviste.setAllowBlank(true);
			}else{
        		fImmaginiPreviste.setAllowBlank(false);
				fIncludeAltriTitoliSelected = new ComboDTO(new BigDecimal(2),BdlSharedConstants.FLAG_FALSE_HUMAN);
				fIncludeAltriTitoli.setValue(fIncludeAltriTitoliSelected);

                /*
                 * MOCK
                List<ComboDTO> items2 = new ArrayList<ComboDTO>();
                items2.add(new ComboDTO(new BigDecimal(1),"La divina commedia"));
                items2.add(new ComboDTO(new BigDecimal(2),"Il signore degli anelli"));
                items2.add(new ComboDTO(new BigDecimal(3),"La bibbia"));
                fTitoloSupStore.clear();
                fTitoloSupStore.addAll(items2);	        	
                */
				final OggettoDTO item2combo = item;
        		servizioIdentOggetti.getOggettiSuperiori(new AsyncCallback<List<ComboDTO>>() {
        			public void onFailure(Throwable caught) {
        				// gestisco l'errore AsyncServiceException
        				new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
        			}
        			@Override
        			public void onSuccess(List<ComboDTO> items) {
    	                fTitoloSupStore.clear();
    	                fTitoloSupStore.addAll(items);
    	                fTitoloSupSelected = new ComboDTO(item2combo.getCdOggettoOriginaleSup(),item2combo.getTitoloOggettoOriginaleSup());
    					fTitoloSup.setValue(fTitoloSupSelected);
        			}
        		});
                
			}
			
		}
		
		if(fTipologiaOggettoSelected!=null){
			checkCartografia(fTipologiaOggettoSelected.getId());
		}
	}
	
	private void checkCartografia(BigDecimal cdTipoOgg){
		if(cdTipoOgg==null){
			fImmaginiPreviste.enable();
		}else{
			servizioIdentOggetti.hasCartografia(cdTipoOgg, new AsyncCallback<Boolean>() {
				public void onFailure(Throwable caught) {
					// gestisco l'errore AsyncServiceException
					new GXTAlertBox(messages.infoTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
				}
				@Override
				public void onSuccess(Boolean flag) {
					if(flag){
	    				Integer tmp = Integer.valueOf(1);

	    				fImmaginiPreviste.clearInvalid();
	    				fImmaginiPreviste.setValue(tmp);
	    				fImmaginiPreviste.disable();
	    				
	    				vImmaginiPreviste.setText(tmp.toString());
					}else{
						String myText = fImmaginiPreviste.getText();
						
						fImmaginiPreviste.clearInvalid();
						fImmaginiPreviste.enable();
						
						if(!myText.isEmpty()) {
							fImmaginiPreviste.setText(myText);
						}
					}
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#getfFieldset()
	 */
	public FieldSet getfFieldset() {
		return fFieldset;
	}
	
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#getOggettoDTO()
	 */
	@Override
	public OggettoDTO getOggettoDTO() {
		BigDecimal immaginiPreviste = null;
		if(fImmaginiPreviste.getCurrentValue() != null && !fIncludeAltriTitoliSelected.getId().equals(new BigDecimal(1))){
			immaginiPreviste = new BigDecimal(fImmaginiPreviste.getCurrentValue());
		}
		return new OggettoDTO(
				null,
				fTipologiaOggettoSelected==null ? null : fTipologiaOggettoSelected.getId(),
				fTitoloSupSelected==null ? null : fTitoloSupSelected.getId(),
				null,
				fTitolo.getCurrentValue(),
				fIncludeAltriTitoliSelected.getId().equals(new BigDecimal(1)) ? true : false,
				immaginiPreviste
								
		);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#activateInsertForm()
	 */
	public void activateInsertForm(){
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();		
		fFieldset.forceLayout();
		fFieldsetVlc.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#activateModifyForm()
	 */
	public void activateModifyForm(){
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldset.forceLayout();
		fFieldsetVlc.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController.iIOOggettoDettaglioView#activateView()
	 */
	public void activateView(){
		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldset.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}