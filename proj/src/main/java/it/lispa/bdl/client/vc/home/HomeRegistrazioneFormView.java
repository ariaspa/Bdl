package it.lispa.bdl.client.vc.home;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.client.validators.CfValidator;
import it.lispa.bdl.client.validators.EmailValidator;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.HomeMsg;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;
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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Class HomeRegistrazioneFormView.
 */
public class HomeRegistrazioneFormView extends
		GXTPopupViewWithUiHandlers<HomeRegistrazioneFormHandler> implements
		HomeRegistrazioneFormController.iHomeRegistrazioneFormView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, HomeRegistrazioneFormView> {
	}

	@Inject HomeMsg messages;
	
	ExternalServiceAsync externalService = (ExternalServiceAsync) GWT.create(ExternalService.class);
	
	@UiField FieldLabel lblEnte;
	
	@UiField TextButton action;
	
	@UiField TextField txtNome;
	@UiField TextField txtCognome;
	@UiField TextField txtCf;
	@UiField TextField txtEmail;
	@UiField TextField txtTelefono;
	@UiField HTMLPanel flRichiediImmissioneIstituto;
	@UiField HTMLPanel flMultiIstituto;
	
	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
 
	ListStore<ComboDTO> cmbRuoloStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField (provided = true) 
	ComboBox<ComboDTO> cmbRuolo = new ComboBox<ComboDTO>(cmbRuoloStore, cmbProps.desc());

	ListStore<ComboDTO> cmbEnteStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField (provided = true) 
	ComboBox<ComboDTO> cmbEnte = new ComboBox<ComboDTO>(cmbEnteStore, cmbProps.desc());
	
	@UiField
	Window dialog;

	/**
	 * Istanzia un nuovo home registrazione form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public HomeRegistrazioneFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		widget = binder.createAndBindUi(this);
		
		/*
		cmbRuolo.addValueChangeHandler(new ValueChangeHandler<ComboDTO>() {
			@Override
			public void onValueChange(ValueChangeEvent<ComboDTO> event) {
				ruoloSelected = event.getValue();
				loadCmbEnte();
			}
		});
		*/
		flRichiediImmissioneIstituto.setVisible(false);
		flMultiIstituto.setVisible(false);
		cmbRuolo.addSelectionHandler(new SelectionHandler<ComboDTO>() {
	        @Override
	        public void onSelection(SelectionEvent<ComboDTO> event) {
	        	ComboDTO ruoloSelected = event.getSelectedItem();
				if (ruoloSelected.getDesc().equalsIgnoreCase("catalogatore")) {
					flRichiediImmissioneIstituto.setVisible(true);
					flMultiIstituto.setVisible(true);
				} else {
					flRichiediImmissioneIstituto.setVisible(false);
					flMultiIstituto.setVisible(false);
				}
				
				loadCmbEnte();
	        }
	    });
		
		loadCmbRuolo();

		txtEmail.addValidator(new EmailValidator());
		txtEmail.setAutoValidate(true);
		txtCf.addValidator(new CfValidator());
		txtCf.setAutoValidate(true);
		
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/**
	 * On annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("annulla")
	public void onAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onAnnulla();
		}
	}

	/**
	 * On action.
	 *
	 * @param event  event
	 */
	@UiHandler("action")
	public void onAction(SelectEvent event) {

		action.focus();

    	boolean areAllValid = true;

    	areAllValid = txtNome.isValid() && areAllValid;
    	areAllValid = txtCognome.isValid() && areAllValid;
    	areAllValid = txtCf.isValid() && areAllValid;
    	areAllValid = txtEmail.isValid() && areAllValid;
    	areAllValid = txtTelefono.isValid() && areAllValid;
    	areAllValid = cmbRuolo.isValid() && areAllValid;
    	areAllValid = cmbEnte.isValid() && areAllValid;
    	
    	if (areAllValid && getUiHandlers() != null) {
        	getUiHandlers().onAction();
        }
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormController.iHomeRegistrazioneFormView#setHeadingText(java.lang.String)
	 */
	public void setHeadingText(String text) {
		dialog.setHeadingText(text);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormController.iHomeRegistrazioneFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}


	/**
	 * Imposta cmb ruolo store.
	 *
	 * @param lista nuovo cmb ruolo store
	 */
	public void setCmbRuoloStore(List<ComboDTO> lista) {
		this.cmbRuoloStore.addAll(lista);
	}

	/**
	 * Imposta cmb ente store.
	 *
	 * @param lista nuovo cmb ente store
	 */
	public void setCmbEnteStore(List<ComboDTO> lista) {
		this.cmbEnteStore.clear();
		this.cmbEnteStore.addAll(lista);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormController.iHomeRegistrazioneFormView#getUtenteDTO()
	 */
	public UtenteDTO getUtenteDTO(){
		
		List<BigDecimal> cdEnti = new ArrayList<BigDecimal>();
		cdEnti.add(cmbEnte.getCurrentValue().getId());
		
		UtenteDTO user = new UtenteDTO(
				null, 
				txtNome.getValue(),
				txtCognome.getValue(),
				txtCf.getValue().toUpperCase(),
				txtEmail.getValue(),
				txtTelefono.getValue(),
				cmbRuolo.getCurrentValue().getId(), 
				null, 
				null, 
				null, 
				null, 
				null,
				cdEnti
		);
		return user;
	}

	
	/**
	 * Legge txt nome.
	 *
	 * @return txt nome
	 */
	public TextField getTxtNome() {
		return txtNome;
	}

	/**
	 * Legge txt cognome.
	 *
	 * @return txt cognome
	 */
	public TextField getTxtCognome() {
		return txtCognome;
	}

	/**
	 * Legge txt cf.
	 *
	 * @return txt cf
	 */
	public TextField getTxtCf() {
		return txtCf;
	}

	/**
	 * Legge txt email.
	 *
	 * @return txt email
	 */
	public TextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * Legge txt telefono.
	 *
	 * @return txt telefono
	 */
	public TextField getTxtTelefono() {
		return txtTelefono;
	}
	
	/**
	 * Load cmb ruolo.
	 */
	public void loadCmbRuolo(){
		externalService.getRuoliRegistrabili(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				AlertMessageBox box = new AlertMessageBox("RPC", caught.toString());
				box.setPredefinedButtons(PredefinedButton.OK);
				box.setIcon(MessageBox.ICONS.error());
				box.show();
			}
			@Override
			public void onSuccess(List<ComboDTO> items) {
				cmbRuoloStore.clear();
				cmbRuoloStore.addAll(items);
			}
		});
	}

	/**
	 * Load cmb ente.
	 */
	public void loadCmbEnte(){
		externalService.getEnti(cmbRuolo.getCurrentValue().getId(), new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				AlertMessageBox box = new AlertMessageBox("RPC", caught.toString());
				box.setPredefinedButtons(PredefinedButton.OK);
				box.setIcon(MessageBox.ICONS.error());
				box.show();
			}
			@Override
			public void onSuccess(List<ComboDTO> items) {
				if(cmbRuolo.getCurrentValue().getDesc().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_CATALOGATORE)){
					lblEnte.setText(messages.lblIstituto());
				}else{
					lblEnte.setText(messages.lblDigitalizzatore());
				}
				cmbEnteStore.clear();
				cmbEnteStore.addAll(items);
				cmbEnte.setValue(null);
				cmbEnte.redraw();
				cmbEnte.setText(null);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormController.iHomeRegistrazioneFormView#refreshFields()
	 */
	public void refreshFields() {
		txtNome.reset();
		txtCognome.reset();
		txtCf.reset();
		txtEmail.reset();
		txtTelefono.reset();
		cmbRuolo.reset();
		cmbEnteStore.clear();
		cmbEnte.reset();
	}
}
