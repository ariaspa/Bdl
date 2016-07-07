package it.lispa.bdl.client.vc.ammgestenti;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.client.validators.EmailValidator;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.messages.AmmGestEntiMsg;
import it.lispa.bdl.shared.services.AmmGestEntiService;
import it.lispa.bdl.shared.services.AmmGestEntiServiceAsync;
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
import com.sencha.gxt.core.client.Style.HideMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;

/**
 * Class AmmGestEntiFormView.
 */
public class AmmGestEntiFormView extends GXTPopupViewWithUiHandlers<AmmGestEntiFormHandler> implements AmmGestEntiFormController.iAmmGestEntiFormView {

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestEntiFormView> {
	}

	private final Widget widget;

	AmmGestEntiServiceAsync servizio = (AmmGestEntiServiceAsync) GWT.create(AmmGestEntiService.class);

	EnteDTO item;

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
	InlineLabel vClasse;
	@UiField
	InlineLabel vNome;
	@UiField
	InlineLabel vIndirizzo;
	@UiField
	InlineLabel vComune;
	@UiField
	InlineLabel vProvincia;
	@UiField
	InlineLabel vCap;
	@UiField
	InlineLabel vTelefono;
	@UiField
	InlineLabel vFax;
	@UiField
	InlineLabel vEmail;
	@UiField
	FieldLabel vLblIndirizzoWww;
	@UiField
	InlineLabel vIndirizzoWww;
	@UiField
	FieldLabel vLblDigitalizzatore;
	@UiField
	InlineLabel vDigitalizzatore;

	@UiField
	TextButton vBtnAnnulla;
	@UiField
	TextButton vBtnCancella;
	@UiField
	TextButton vBtnModifica;

	
	ListStore<ComboStringDTO> fClasseStore = new ListStore<ComboStringDTO>(cmbStringProps.id());
	@UiField(provided = true)
	ComboBox<ComboStringDTO> fClasse = new ComboBox<ComboStringDTO>(fClasseStore, cmbStringProps.desc());
	
	@UiField
	TextField fNome;
	@UiField
	TextField fIndirizzo;
	@UiField
	TextField fComune;

	ListStore<ComboStringDTO> fProvinciaStore = new ListStore<ComboStringDTO>(cmbStringProps.id());
	@UiField(provided = true)
	ComboBox<ComboStringDTO> fProvincia = new ComboBox<ComboStringDTO>(fProvinciaStore, cmbStringProps.desc());
	@UiField
	TextField fCap;
	@UiField
	TextField fTelefono;
	@UiField
	TextField fFax;
	@UiField
	TextField fEmail;
	@UiField
	FieldLabel fLblIndirizzoWww;
	@UiField
	TextField fIndirizzoWww;
	@UiField
	FieldLabel fLblDigitalizzatore;

	ListStore<ComboDTO> fDigitalizzatoreStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fDigitalizzatore = new ComboBox<ComboDTO>(fDigitalizzatoreStore, cmbProps.desc());

	
	@UiField
	TextButton fBtnAnnulla;
	@UiField
	TextButton fBtnCancella;
	@UiField
	TextButton fBtnSalva;

	@Inject
	AmmGestEntiMsg messages;
	
	/**
	 * Istanzia un nuovo amm gest enti form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public AmmGestEntiFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		//DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		
		widget = binder.createAndBindUi(this);

		fLblIndirizzoWww.setHideMode(HideMode.OFFSETS);
		fLblDigitalizzatore.setHideMode(HideMode.OFFSETS);
		
        vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);

		fEmail.addValidator(new EmailValidator());
		fEmail.setAutoValidate(true);

		fCap.addValidator(new MaxLengthValidator(5));

		servizio.getProvince(new AsyncCallback<List<ComboStringDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboStringDTO> items) {
				fProvinciaStore.clear();
				fProvinciaStore.addAll(items);
			}
		});
		
		List<ComboStringDTO> cmbClasseItems = new ArrayList<ComboStringDTO>();
		cmbClasseItems.add(new ComboStringDTO(Character.toString(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE), BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN));
		cmbClasseItems.add(new ComboStringDTO(Character.toString(BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO), BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN));
		fClasseStore.clear();
		fClasseStore.addAll(cmbClasseItems);
		

		fClasse.addSelectionHandler(new SelectionHandler<ComboStringDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboStringDTO> event) {
	        	ComboStringDTO valueSelected = event.getSelectedItem();
	        	if(valueSelected.getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN)){
	        		fLblIndirizzoWww.show();
	        		fLblDigitalizzatore.hide();
	        	}else if(valueSelected.getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN)){
	        		fLblIndirizzoWww.hide();
	        		fLblDigitalizzatore.show();
	        	}else{
	        		fLblIndirizzoWww.hide();
	        		fLblDigitalizzatore.hide();
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
		// Necessario per updatare i CurrentValue e i Value del modulo
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
		// Necessario per updatare i CurrentValue e i Value del modulo
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
		// Necessario per updatare i CurrentValue e i Value del modulo
		fBtnSalva.focus();

    	boolean areAllValid = true;
    	
    	areAllValid = fClasse.isValid() && areAllValid;
    	areAllValid = fNome.isValid() && areAllValid;
    	areAllValid = fIndirizzo.isValid() && areAllValid;
    	areAllValid = fComune.isValid() && areAllValid;
    	areAllValid = fProvincia.isValid() && areAllValid;
    	areAllValid = fCap.isValid() && areAllValid;
    	areAllValid = fTelefono.isValid() && areAllValid;
    	areAllValid = fFax.isValid() && areAllValid;
    	areAllValid = fEmail.isValid() && areAllValid;
    	areAllValid = fIndirizzoWww.isValid() && areAllValid;
    	areAllValid = fDigitalizzatore.isValid() && areAllValid;
      	
    	
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

	private void refreshItem(EnteDTO item) {
		this.item = item;

		servizio.getDigitalizzatori(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboDTO> items) {
				fDigitalizzatoreStore.clear();
				fDigitalizzatoreStore.addAll(items);
			}
		});

    	fClasse.clearInvalid();
    	fNome.clearInvalid();
    	fIndirizzo.clearInvalid();
    	fComune.clearInvalid();
    	fProvincia.clearInvalid();
    	fCap.clearInvalid();
    	fTelefono.clearInvalid();
    	fFax.clearInvalid();
    	fEmail.clearInvalid();
    	fIndirizzoWww.clearInvalid();
    	fDigitalizzatore.clearInvalid();
		
		
		if (item == null) {
			
			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(false);
			vBtnModifica.setEnabled(false);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(false);
			fBtnSalva.setEnabled(true);

	    	vClasse.setText(null);
	    	vNome.setText(null);
	    	vIndirizzo.setText(null);
	    	vComune.setText(null);
	    	vProvincia.setText(null);
	    	vCap.setText(null);
	    	vTelefono.setText(null);
	    	vFax.setText(null);
	    	vEmail.setText(null);

	    	vLblIndirizzoWww.hide();
	    	vIndirizzoWww.setText(null);

	    	vLblDigitalizzatore.hide();
	    	vDigitalizzatore.setText(null);
	    	

	    	fClasse.clear();
	    	fNome.setValue(null);
	    	fIndirizzo.setValue(null);
	    	fComune.setValue(null);
	    	fProvincia.clear();
	    	fCap.setValue(null);
	    	fTelefono.setValue(null);
	    	fFax.setValue(null);
	    	fEmail.setValue(null);

	    	fLblIndirizzoWww.hide();
	    	fIndirizzoWww.setValue(null);

	    	fLblDigitalizzatore.hide();
	    	fDigitalizzatore.clear();
	    	
		} else {
			
			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(true);
			vBtnModifica.setEnabled(true);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(true);
			fBtnSalva.setEnabled(true);


	    	vClasse.setText(item.getClasseHuman());
	    	vNome.setText(item.getNome());
	    	vIndirizzo.setText(item.getIndirizzo());
	    	vComune.setText(item.getComune());
	    	vProvincia.setText(item.getProvinciaHuman());
	    	vCap.setText(item.getCap());
	    	vTelefono.setText(item.getTelefono());
	    	vFax.setText(item.getFax());
	    	vEmail.setText(item.getEmail());
	    	
	    	if(item.getClasse()==BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE){
		    	vLblIndirizzoWww.show();
		    	vIndirizzoWww.setText(item.getIndirizzoWww());
		    	vLblDigitalizzatore.hide();
		    	vDigitalizzatore.setText(null);
	    	}else{
		    	vLblIndirizzoWww.hide();
		    	vIndirizzoWww.setText(null);
		    	vLblDigitalizzatore.show();
		    	vDigitalizzatore.setText(item.getEnteDigit());
	    	}

	    	

			if (item.getClasse()==BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE){
				fClasse.setValue(new ComboStringDTO(Character.toString(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE), BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN));
			} else {
				fClasse.setValue(new ComboStringDTO(Character.toString(BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO), BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN));
			}

	    	fNome.setValue(item.getNome());
	    	fIndirizzo.setValue(item.getIndirizzo());
	    	fComune.setValue(item.getComune());
	    	fProvincia.setValue(new ComboStringDTO(item.getProvincia(),item.getProvinciaHuman()));
	    	fCap.setValue(item.getCap());
	    	fTelefono.setValue(item.getTelefono());
	    	fFax.setValue(item.getFax());
	    	fEmail.setValue(item.getEmail());


	    	if(item.getClasse()==BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE){
		    	fLblIndirizzoWww.show();
		    	fIndirizzoWww.setValue(item.getIndirizzoWww());
		    	fLblDigitalizzatore.hide();
		    	fDigitalizzatore.clear();
	    	}else{
		    	fLblIndirizzoWww.hide();
		    	fIndirizzoWww.setValue(null);
		    	fLblDigitalizzatore.show();
		    	fDigitalizzatore.setValue(new ComboDTO(item.getCdEnteDigit(),item.getEnteDigit()));
	    	}
	    	
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#getItem()
	 */
	@Override
	public EnteDTO getItem() {
		return item;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#getFormDTO()
	 */
	@Override
	public EnteDTO getFormDTO() {

		String indirizzoWww = null;
		BigDecimal cdEnteDigitalizzatore = null;
		String enteDigitalizzatore = null;

    	char classe ;
    	String classeHuman = null;
    	if(fClasse.getCurrentValue().getId().equals(Character.toString(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE))){
    		indirizzoWww = fIndirizzoWww.getCurrentValue();
    		classe = BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE;
    		classeHuman = BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN;
    	}else{
			if(fDigitalizzatore.getCurrentValue()!=null){
				cdEnteDigitalizzatore = fDigitalizzatore.getCurrentValue().getId();
				enteDigitalizzatore = fDigitalizzatore.getCurrentValue().getDesc();
			}
    		classe = BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO;
    		classeHuman = BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN;
    	}
    	
		    	
		EnteDTO itemDto = new EnteDTO(

				null, 
				cdEnteDigitalizzatore, 
				enteDigitalizzatore, 
				fNome.getCurrentValue()!=null ? fNome.getCurrentValue() : null,
				fIndirizzo.getCurrentValue()!=null ? fIndirizzo.getCurrentValue() : null,
				fComune.getCurrentValue()!=null ? fComune.getCurrentValue() : null,
				fProvincia.getCurrentValue()!=null ? fProvincia.getCurrentValue().getId() : null,
				fProvincia.getCurrentValue()!=null ? fProvincia.getCurrentValue().getDesc() : null,
				fCap.getCurrentValue()!=null ? fCap.getCurrentValue() : null,
				fTelefono.getCurrentValue()!=null ? fTelefono.getCurrentValue() : null,
				fFax.getCurrentValue()!=null ? fFax.getCurrentValue() : null,
				fEmail.getCurrentValue()!=null ? fEmail.getCurrentValue() : null,
				indirizzoWww, 
				classe, 
				classeHuman
		);
		return itemDto;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#activateInsertForm()
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
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#activateModifyForm(it.lispa.bdl.shared.dto.EnteDTO)
	 */
	@Override
	public void activateModifyForm(EnteDTO item) {
		refreshItem(item);
		dialog.setHeadingText(messages.infoTitleModify(item.getNome()));
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldsetVlc.forceLayout();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController.iAmmGestEntiFormView#activateView(it.lispa.bdl.shared.dto.EnteDTO)
	 */
	@Override
	public void activateView(EnteDTO item) {
		refreshItem(item);

		dialog.setHeadingText(messages.infoTitleView(item.getNome()));
		
		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}
