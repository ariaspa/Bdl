package it.lispa.bdl.client.vc.ammgestindex;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.AmmGestIndexMsg;
import it.lispa.bdl.shared.services.AmmGestIndexService;
import it.lispa.bdl.shared.services.AmmGestIndexServiceAsync;
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
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;

/**
 * Class AmmGestIndexFormView.
 */
public class AmmGestIndexFormView extends GXTPopupViewWithUiHandlers<AmmGestIndexFormHandler> implements AmmGestIndexFormController.iAmmGestIndexFormView {

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestIndexFormView> {
	}

	private final Widget widget;

	AmmGestIndexServiceAsync servizio = (AmmGestIndexServiceAsync) GWT.create(AmmGestIndexService.class);

	OggettoDTO item;

	@UiField
	Window dialog;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
	ComboStringDTO.ComboStringDTOProperties cmbStringProps = GWT.create(ComboStringDTO.ComboStringDTOProperties.class);

	@UiField
	CardLayoutContainer stepLayout;

	@UiField
	VerticalLayoutContainer vFieldsetVlc;
	@UiField
	VerticalLayoutContainer fFieldsetVlc;
	@UiField
	HBoxLayoutContainer vButtons;
	@UiField
	HBoxLayoutContainer fButtons;

	@UiField
	InlineLabel vTitolo;
	@UiField
	InlineLabel vTitoloSup;
	@UiField
	InlineLabel vImmaginiPreviste;
	@UiField
	InlineLabel vTipologiaOggetto;
	@UiField
	InlineLabel vIncludeAltriTitoli;

	@UiField
	TextButton vBtnAnnulla;
	@UiField
	TextButton vBtnCancella;
	@UiField
	TextButton vBtnModifica;

	@UiField
	TextField fTitolo;

	ListStore<ComboDTO> fTitoloSupStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fTitoloSup = new ComboBox<ComboDTO>(fTitoloSupStore, cmbProps.desc());

	@UiField(provided = true)
	NumberField<Integer> fImmaginiPreviste;

	ListStore<ComboDTO> fTipologiaOggettoStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fTipologiaOggetto = new ComboBox<ComboDTO>(fTipologiaOggettoStore, cmbProps.desc());

	ListStore<ComboDTO> fIncludeAltriTitoliStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fIncludeAltriTitoli = new ComboBox<ComboDTO>(fIncludeAltriTitoliStore, cmbProps.desc());

	@UiField
	TextButton fBtnAnnulla;
	@UiField
	TextButton fBtnCancella;
	@UiField
	TextButton fBtnSalva;

	@Inject
	AmmGestIndexMsg messages;

	/**
	 * Istanzia un nuovo amm gest index form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public AmmGestIndexFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		//DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		
    	fImmaginiPreviste = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
    	fImmaginiPreviste.addValidator(new MinNumberValidator<Integer>(1));

		widget = binder.createAndBindUi(this);

		vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
		fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
		
    	
        fTitolo.addValidator(new MaxLengthValidator(500));

        servizio.getTipologiaOggetti(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
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
	        	if(valueSelected.getDesc().equals(BdlSharedConstants.FLAG_FALSE_HUMAN)){
	        		fImmaginiPreviste.setAllowBlank(false);
	        		fImmaginiPreviste.clearInvalid();
	        		
	        		servizio.getOggettiSuperiori(new AsyncCallback<List<ComboDTO>>() {
	        			public void onFailure(Throwable caught) {
	        				// gestisco l'errore AsyncServiceException
	        			}
	        			@Override
	        			public void onSuccess(List<ComboDTO> items) {
	    	                fTitoloSupStore.clear();
	    	                fTitoloSupStore.addAll(items);
	        			}
	        		});
	        	}else{
	    			fTitoloSup.clear();
	        		fImmaginiPreviste.setAllowBlank(true);
	        		fImmaginiPreviste.setValue(null);
	        		fImmaginiPreviste.clearInvalid();
	        	}
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
	 * On f btn annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("fBtnAnnulla")
	public void onFBtnAnnulla(SelectEvent event) {
		// Necessario per updatare i valori di CurrentValue e Value del form
		fBtnAnnulla.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onFBtnAnnulla();
		}
	}

	/**
	 * On f btn cancella.
	 *
	 * @param event  event
	 */
	@UiHandler("fBtnCancella")
	public void onFBtnCancella(SelectEvent event) {
		// Necessario per updatare i valori di CurrentValue e Value del form
		fBtnCancella.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onFBtnCancella();
		}
	}

	/**
	 * On f btn salva.
	 *
	 * @param event  event
	 */
	@UiHandler("fBtnSalva")
	public void onFBtnSalva(SelectEvent event) {
		// Necessario per updatare i valori di CurrentValue e Value del form
		fBtnSalva.focus();

		boolean areAllValid = true;

    	areAllValid = fTitolo.isValid() && areAllValid;
    	areAllValid = fTitoloSup.isValid() && areAllValid;
    	areAllValid = fImmaginiPreviste.isValid() && areAllValid;
    	areAllValid = fTipologiaOggetto.isValid() && areAllValid;
    	areAllValid = fIncludeAltriTitoli.isValid() && areAllValid;

		if (areAllValid && getUiHandlers() != null) {
			getUiHandlers().onFBtnSalva();
		}
	}

	/**
	 * On v btn annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("vBtnAnnulla")
	public void onVBtnAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVBtnAnnulla();
		}
	}

	/**
	 * On v btn cancella.
	 *
	 * @param event  event
	 */
	@UiHandler("vBtnCancella")
	public void onVBtnCancella(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVBtnCancella();
		}
	}

	/**
	 * On v btn modifica.
	 *
	 * @param event  event
	 */
	@UiHandler("vBtnModifica")
	public void onVBtnModifica(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVBtnModifica();
		}
	}

	private void refreshItem(OggettoDTO item) {
		this.item = item;

    	fTitolo.clearInvalid();
    	fTitoloSup.clearInvalid();
    	fImmaginiPreviste.clearInvalid();
    	fTipologiaOggetto.clearInvalid();
    	fIncludeAltriTitoli.clearInvalid();

		if (item == null) {

			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(false);
			vBtnModifica.setEnabled(false);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(false);
			fBtnSalva.setEnabled(true);
	    	
	    	vTitolo.setText(null);
	    	vTitoloSup.setText(null);
	    	vImmaginiPreviste.setText(null);
	    	vTipologiaOggetto.setText(null);
			vIncludeAltriTitoli.setText(null);

	    	fTitolo.clearInvalid();
	    	fTitoloSup.clearInvalid();
	    	fImmaginiPreviste.clearInvalid();
	    	fTipologiaOggetto.clearInvalid();
	    	fIncludeAltriTitoli.clearInvalid();

			fTitolo.setValue(null);
			fTitoloSup.clear();
			fImmaginiPreviste.setValue(null);
			fTipologiaOggetto.clear();
			fIncludeAltriTitoli.clear();

		} else {

			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(true);
			vBtnModifica.setEnabled(true);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(true);
			fBtnSalva.setEnabled(true);


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
			
			fTipologiaOggetto.setValue(new ComboDTO(item.getCdTipoOggetto(),item.getNomeTipoOggetto()));
			
			if(item.getOggettoSuperiore()){
				fIncludeAltriTitoli.setValue(new ComboDTO(new BigDecimal(1),BdlSharedConstants.FLAG_TRUE_HUMAN));
				fTitoloSupStore.clear();
        		fImmaginiPreviste.setAllowBlank(true);
			}else{
        		fImmaginiPreviste.setAllowBlank(false);
				fIncludeAltriTitoli.setValue( new ComboDTO(new BigDecimal(2),BdlSharedConstants.FLAG_FALSE_HUMAN));

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
        		servizio.getOggettiSuperiori(new AsyncCallback<List<ComboDTO>>() {
        			public void onFailure(Throwable caught) {
        				// gestisco l'errore AsyncServiceException
        			}
        			@Override
        			public void onSuccess(List<ComboDTO> items) {
    	                fTitoloSupStore.clear();
    	                fTitoloSupStore.addAll(items);
    					fTitoloSup.setValue(new ComboDTO(item2combo.getCdOggettoOriginaleSup(),item2combo.getTitoloOggettoOriginaleSup()));
        			}
        		});
                
			}
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#getItem()
	 */
	@Override
	public OggettoDTO getItem() {
		return item;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#getFormDTO()
	 */
	@Override
	public OggettoDTO getFormDTO() {
		BigDecimal immaginiPreviste = null;
		if(fImmaginiPreviste.getCurrentValue() != null && !fIncludeAltriTitoli.getCurrentValue().getId().equals(new BigDecimal(1))){
			immaginiPreviste = new BigDecimal(fImmaginiPreviste.getCurrentValue());
		}
		return new OggettoDTO(
				null,
				fTipologiaOggetto.getCurrentValue()==null ? null : fTipologiaOggetto.getCurrentValue().getId(),
				fTitoloSup.getCurrentValue()==null ? null : fTitoloSup.getCurrentValue().getId(),
				null,
				fTitolo.getCurrentValue(),
				fIncludeAltriTitoli.getCurrentValue().getId().equals(new BigDecimal(1)) ? true : false,
				immaginiPreviste
								
		);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#activateInsertForm()
	 */
	@Override
	public void activateInsertForm() {
		refreshItem(null);
		dialog.setHeadingText(messages.infoTitleInsert());
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldsetVlc.forceLayout();

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#activateModifyForm(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Override
	public void activateModifyForm(OggettoDTO item) {
		refreshItem(item);
		dialog.setHeadingText(messages.infoTitleModify(item.getTitolo()));
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldsetVlc.forceLayout();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController.iAmmGestIndexFormView#activateView(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Override
	public void activateView(OggettoDTO item) {
		refreshItem(item);

		dialog.setHeadingText(messages.infoTitleView(item.getTitolo()));

		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}
