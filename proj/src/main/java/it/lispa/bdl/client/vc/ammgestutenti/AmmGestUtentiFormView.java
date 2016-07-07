package it.lispa.bdl.client.vc.ammgestutenti;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.client.validators.CfValidator;
import it.lispa.bdl.client.validators.EmailValidator;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.AmmGestUtentiMsg;
import it.lispa.bdl.shared.services.AmmGestUtentiService;
import it.lispa.bdl.shared.services.AmmGestUtentiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DateLabel;
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
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;

/**
 * Class AmmGestUtentiFormView.
 */
public class AmmGestUtentiFormView extends GXTPopupViewWithUiHandlers<AmmGestUtentiFormHandler> implements AmmGestUtentiFormController.iAmmGestUtentiFormView {

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmGestUtentiFormView> {
	}

	private final Widget widget;

	AmmGestUtentiServiceAsync servizio = (AmmGestUtentiServiceAsync) GWT.create(AmmGestUtentiService.class);

	UtenteDTO item;

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
	InlineLabel vNome;
	@UiField
	InlineLabel vCognome;
	@UiField
	InlineLabel vCf;
	@UiField
	InlineLabel vEmail;
	@UiField
	InlineLabel vTelefono;
	@UiField
	InlineLabel vRuolo;
	@UiField
	InlineLabel vStato;

	@UiField (provided = true)
	DateLabel vDataRegistrazione;
	@UiField (provided = true)
	DateLabel vDataValidazione;

	@UiField
	FieldLabel vLblEnteI;
	@UiField
	InlineLabel vEnteI;
	@UiField
	FieldLabel vLblEnteD;
	@UiField
	InlineLabel vEnteD;

	@UiField
	TextButton vBtnAnnulla;
	@UiField
	TextButton vBtnCancella;
	@UiField
	TextButton vBtnModifica;

	@UiField
	TextField fNome;
	@UiField
	TextField fCognome;
	@UiField
	TextField fCf;
	@UiField
	TextField fEmail;
	@UiField
	TextField fTelefono;

	ListStore<ComboDTO> fRuoloStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fRuolo = new ComboBox<ComboDTO>(fRuoloStore, cmbProps.desc());

	ListStore<ComboStringDTO> fStatoStore = new ListStore<ComboStringDTO>(cmbStringProps.id());
	@UiField(provided = true)
	ComboBox<ComboStringDTO> fStato = new ComboBox<ComboStringDTO>(fStatoStore, cmbStringProps.desc());

	@UiField
	FieldLabel fLblEnteI;
	ListStore<ComboDTO> fEnteISourceStore = new ListStore<ComboDTO>(cmbProps.id());
	ListStore<ComboDTO> fEnteIDestStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	DualListField<ComboDTO, String> fEnteI = new DualListField<ComboDTO, String>(fEnteISourceStore, fEnteIDestStore, cmbProps.value(), new TextCell());

	@UiField
	FieldLabel fLblEnteD;
	ListStore<ComboDTO> fEnteDStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField(provided = true)
	ComboBox<ComboDTO> fEnteD = new ComboBox<ComboDTO>(fEnteDStore, cmbProps.desc());

	@UiField
	TextButton fBtnAnnulla;
	@UiField
	TextButton fBtnCancella;
	@UiField
	TextButton fBtnSalva;

	@Inject
	AmmGestUtentiMsg messages;
	
	/**
	 * Istanzia un nuovo amm gest utenti form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public AmmGestUtentiFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		vDataRegistrazione = new DateLabel(fmt);
		vDataValidazione = new DateLabel(fmt);
		
		widget = binder.createAndBindUi(this);

		fLblEnteI.setHideMode(HideMode.OFFSETS);
		fLblEnteD.setHideMode(HideMode.OFFSETS);
		
        vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);

    	fNome.addValidator(new MaxLengthValidator(50));
    	fNome.setAutoValidate(true);
    	
    	fCognome.addValidator(new MaxLengthValidator(50));
    	fCognome.setAutoValidate(true);
    	
    	fTelefono.addValidator(new MaxLengthValidator(50));
    	fTelefono.setAutoValidate(true);
        
        
		fEmail.addValidator(new EmailValidator());
		fEmail.setAutoValidate(true);
		fCf.addValidator(new CfValidator());
		fCf.setAutoValidate(true);

		servizio.getRuoli(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboDTO> items) {
				fRuoloStore.clear();
				fRuoloStore.addAll(items);
			}
		});

		servizio.getIstituti(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboDTO> items) {
				fEnteISourceStore.clear();
				fEnteISourceStore.addAll(items);
			}
		});
		servizio.getDigitalizzatori(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non tira un errore da visualizzare
			}

			@Override
			public void onSuccess(List<ComboDTO> items) {
				fEnteDStore.clear();
				fEnteDStore.addAll(items);
			}
		});

		List<ComboStringDTO> cmbStatoItems = new ArrayList<ComboStringDTO>();
		cmbStatoItems.add(new ComboStringDTO(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE, BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE_HUMAN));
		cmbStatoItems.add(new ComboStringDTO(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO, BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO_HUMAN));
		fStatoStore.clear();
		fStatoStore.addAll(cmbStatoItems);
		

		fRuolo.addSelectionHandler(new SelectionHandler<ComboDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboDTO> event) {
	        	ComboDTO valueSelected = event.getSelectedItem();
	        	if(valueSelected.getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_CATALOGATORE) || 
	        			valueSelected.getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_SUPERVISORE)){
					fLblEnteI.show();
					fLblEnteD.hide();
	        	}else if(valueSelected.getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE)){
					fLblEnteI.hide();
					fLblEnteD.show();
	        	}else{
					fLblEnteI.hide();
					fLblEnteD.hide();
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

    	areAllValid = fNome.isValid() && areAllValid;
    	areAllValid = fCognome.isValid() && areAllValid;
    	areAllValid = fCf.isValid() && areAllValid;
    	areAllValid = fEmail.isValid() && areAllValid;
    	areAllValid = fTelefono.isValid() && areAllValid;
    	areAllValid = fRuolo.isValid() && areAllValid;
    	areAllValid = fStato.isValid() && areAllValid;
    	areAllValid = fEnteI.isValid() && areAllValid;
    	areAllValid = fEnteD.isValid() && areAllValid;
    	
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

	private void refreshItem(UtenteDTO item) {
		this.item = item;

		fNome.clearInvalid();
		fCognome.clearInvalid();
		fCf.clearInvalid();
		fEmail.clearInvalid();
		fTelefono.clearInvalid();
		fRuolo.clearInvalid();
		fStato.clearInvalid();
		fEnteI.clearInvalid();
		fEnteD.clearInvalid();

		if (item == null) {
			
			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(false);
			vBtnModifica.setEnabled(false);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(false);
			fBtnSalva.setEnabled(true);

			vNome.setText(null);
			vCognome.setText(null);
			vCf.setText(null);
			vEmail.setText(null);
			vTelefono.setText(null);
			vRuolo.setText(null);
			vStato.setText(null);

			vDataRegistrazione.setValue(null);
			vDataValidazione.setValue(null);

			vLblEnteI.hide();
			vEnteI.setText(null);

			vLblEnteD.hide();
			vEnteD.setText(null);

			fNome.setValue(null);
			fCognome.setValue(null);
			fCf.setValue(null);
			fEmail.setValue(null);
			fTelefono.setValue(null);

			fRuolo.clear();
			fStato.clear();

			fLblEnteI.hide();
			fLblEnteD.hide();
			
			fEnteIDestStore.clear();
			fEnteI.setValue(new ArrayList<ComboDTO>());

			fEnteD.clear();

			servizio.getIstituti(new AsyncCallback<List<ComboDTO>>() {
				public void onFailure(Throwable caught) {
					// non tira un errore da visualizzare
				}

				@Override
				public void onSuccess(List<ComboDTO> items) {
					fEnteISourceStore.clear();
					fEnteISourceStore.addAll(items);
				}
			});
			
		} else {
			
			vBtnAnnulla.setEnabled(true);
			vBtnCancella.setEnabled(true);
			vBtnModifica.setEnabled(true);
			fBtnAnnulla.setEnabled(true);
			fBtnCancella.setEnabled(true);
			fBtnSalva.setEnabled(true);

			vNome.setText(item.getNome());
			vCognome.setText(item.getCognome());
			vCf.setText(item.getCf());
			vEmail.setText(item.getEmail());
			vTelefono.setText(item.getTelefono());
			vRuolo.setText(item.getRuolo());
			vStato.setText(item.getStatoHuman());

			vDataRegistrazione.setValue(item.getDataRegistrazione());
			vDataValidazione.setValue(item.getDataValidazione());

			vEnteI.setText(item.getEntiLabel());
			vEnteD.setText(item.getEntiLabel());
			
			if (BdlSharedConstants.BDL_RUOLO_CATALOGATORE.equalsIgnoreCase(item.getRuolo()) || 
					BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(item.getRuolo())) {
				vLblEnteI.show();
				vLblEnteD.hide();
			} else if (item.getRuolo().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE)) {
				vLblEnteI.hide();
				vLblEnteD.show();
			} else {
				vLblEnteI.hide();
				vLblEnteD.hide();
			}

			fNome.setValue(item.getNome());
			fCognome.setValue(item.getCognome());
			fCf.setValue(item.getCf());
			fEmail.setValue(item.getEmail());
			fTelefono.setValue(item.getTelefono());

			fRuolo.setValue(new ComboDTO(item.getCdRuolo(), item.getRuolo()));

			if (item.getStato().equals(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE)) {
				fStato.setValue(new ComboStringDTO(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE, BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE_HUMAN));
			} else {
				fStato.setValue(new ComboStringDTO(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO, BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO_HUMAN));
			}

			if (BdlSharedConstants.BDL_RUOLO_CATALOGATORE.equalsIgnoreCase(item.getRuolo()) 
				|| BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(item.getRuolo())) {
				fEnteIDestStore.clear();
				fEnteI.setValue(new ArrayList<ComboDTO>());
				List<ComboDTO> its = new ArrayList<ComboDTO>();
				List<BigDecimal> entiCd = item.getCdEnti();
				List<String> entiStr = item.getEnti();
				Iterator<BigDecimal> itr = entiCd.iterator();
				Iterator<String> itr2 = entiStr.iterator();
				while (itr.hasNext()) {
					its.add(new ComboDTO(itr.next(), itr2.next()));
				}
				fEnteIDestStore.addAll(its);
				fEnteI.setValue(its);
				fEnteD.clear();
				fLblEnteI.show();
				fLblEnteD.hide();
			} else if (item.getRuolo().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE)) {
				fEnteIDestStore.clear();
				fEnteI.setValue(new ArrayList<ComboDTO>());
				//List<ComboDTO> its = new ArrayList<ComboDTO>();
				List<BigDecimal> entiCd = item.getCdEnti();
				List<String> entiStr = item.getEnti();
				Iterator<BigDecimal> itr = entiCd.iterator();
				Iterator<String> itr2 = entiStr.iterator();
				while (itr.hasNext()) {
					fEnteD.setValue(new ComboDTO(itr.next(), itr2.next()));
				}
				fLblEnteI.hide();
				fLblEnteD.show();
			} else {
				fEnteIDestStore.clear();
				fEnteI.setValue(new ArrayList<ComboDTO>());
				fEnteD.clear();
				fLblEnteI.hide();
				fLblEnteD.hide();
			}

			servizio.getIstituti(new AsyncCallback<List<ComboDTO>>() {
				public void onFailure(Throwable caught) {
					// non tira un errore da visualizzare
				}

				@Override
				public void onSuccess(List<ComboDTO> items) {
					fEnteISourceStore.clear();
					for(ComboDTO item:items){
						if(fEnteIDestStore.findModel(item)==null) {
							fEnteISourceStore.add(item);
						}
					}
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#getItem()
	 */
	@Override
	public UtenteDTO getItem() {
		return item;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#getFormDTO()
	 */
	@Override
	public UtenteDTO getFormDTO() {
		
		List<BigDecimal> cdEnti = new ArrayList<BigDecimal>();
		
		if(BdlSharedConstants.BDL_RUOLO_CATALOGATORE.equalsIgnoreCase(fRuolo.getCurrentValue().getDesc()) || 
				BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(fRuolo.getCurrentValue().getDesc())){
			List<ComboDTO> istituti = fEnteIDestStore.getAll();
			for(ComboDTO istituto:istituti){
				cdEnti.add(istituto.getId());
			}
		} else if(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE.equalsIgnoreCase(fRuolo.getCurrentValue().getDesc())){
			if(fEnteD.getCurrentValue()!=null) {
				cdEnti.add(fEnteD.getCurrentValue().getId());
			}
		}		
    	
		UtenteDTO itemDto = new UtenteDTO(
				null, 
				fNome.getCurrentValue()!=null ? fNome.getCurrentValue() : null,
				fCognome.getCurrentValue()!=null ? fCognome.getCurrentValue() : null,
				fCf.getCurrentValue()!=null ? fCf.getCurrentValue() : null,
				fEmail.getCurrentValue()!=null ? fEmail.getCurrentValue() : null,
				fTelefono.getCurrentValue()!=null ? fTelefono.getCurrentValue() : null,
				fRuolo.getCurrentValue()!=null ? fRuolo.getCurrentValue().getId() : null,
				null, 
				fStato.getCurrentValue()!=null ? fStato.getCurrentValue().getId() : null,
				null, 
				null, 
				null, 
				cdEnti
		);
		return itemDto;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#activateInsertForm()
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
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#activateModifyForm(it.lispa.bdl.shared.dto.UtenteDTO)
	 */
	@Override
	public void activateModifyForm(UtenteDTO item) {
		refreshItem(item);
		dialog.setHeadingText(messages.infoTitleModify(item.getNome()+" "+item.getCognome()));
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldsetVlc.forceLayout();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController.iAmmGestUtentiFormView#activateView(it.lispa.bdl.shared.dto.UtenteDTO)
	 */
	@Override
	public void activateView(UtenteDTO item) {
		refreshItem(item);

		dialog.setHeadingText(messages.infoTitleView(item.getNome()+" "+item.getCognome()));
		
		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}
