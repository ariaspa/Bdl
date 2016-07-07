package it.lispa.bdl.client.vc.correzioneoggetti;

import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CorrezioneOggettiMsg;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.CaricamentoImmaginiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;

/**
 * Class CorrezioneOggettiFormView.
 */
public class CorrezioneOggettiFormView extends
GXTPopupViewWithUiHandlers<CorrezioneOggettiFormHandler> implements
CorrezioneOggettiFormController.iCorrezioneOggettiFormView {

	private final Widget widget;

	@Inject CorrezioneOggettiMsg messages;
	
	CaricamentoImmaginiServiceAsync servizioFunzione = (CaricamentoImmaginiServiceAsync) GWT.create(CaricamentoImmaginiService.class);

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CorrezioneOggettiFormView> {
	}

	VOggettoDTO item;
	

	@UiField VerticalLayoutContainer fieldsetVlc;

	@UiField CardLayoutContainer stepLayout;
	@UiField FieldLabel panelLogAnomaliaLbl;

	@UiField InlineLabel id;
	@UiField InlineLabel istituto;
	@UiField InlineLabel progetto;
	@UiField InlineLabel collezione;
	@UiField InlineLabel titolo;
	@UiField InlineLabel titoloFe;
	@UiField InlineLabel titoloSup;
	@UiField InlineLabel immaginiPreviste;
	@UiField InlineLabel immaginiDigitalizzate;
	@UiField InlineLabel tipologiaOggetto;
	@UiField TextArea logAnomalia;
	@UiField TextArea notaCorrezione;

	@UiField Window dialog;

	@UiField TextButton annulla;
	@UiField TextButton action;

	@UiField HeadingElement titleStep2;
	@UiField ParagraphElement contentStep2;
	@UiField FieldLabel numImages;
	
	@UiField(provided = true)
	NumberField<Integer> immaginiDigitalizzateStep2;
	
	

	/**
	 * Istanzia un nuovo correzione oggetti form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public CorrezioneOggettiFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		immaginiDigitalizzateStep2 = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		immaginiDigitalizzateStep2.addValidator(new MinNumberValidator<Integer>(1));
		
		widget = binder.createAndBindUi(this);
		fieldsetVlc.setScrollMode(ScrollMode.AUTOY);
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
    	boolean areAllValid = true;
    	
    	int widgetSelected = getStepLayout().getWidgetIndex(getStepLayout().getActiveWidget());
		if(widgetSelected==1){
			areAllValid = immaginiDigitalizzateStep2.isValid() && areAllValid;
		}
		if (areAllValid && getUiHandlers() != null) {
			if(widgetSelected==1){
				stepLayout.mask("Caricamento immagini in corso...");
				annulla.disable();
				action.disable();
			}
			getUiHandlers().onAction();
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#refreshFields(it.lispa.bdl.shared.dto.VOggettoDTO)
	 */
	public void refreshFields(VOggettoDTO item) {
		this.item = item;

		stepLayout.unmask();
		
		if(BdlSharedConstants.FLAG_TRUE.equals(item.getO_FlAnomaliaImmagini())){
			this.panelLogAnomaliaLbl.setVisible(true);
			this.logAnomalia.setVisible(true);
		}else{
			this.panelLogAnomaliaLbl.setVisible(false);
			this.logAnomalia.setVisible(false);
		}
		
		this.id.setText(item.getO_DigitalizzatoreId());
		this.istituto.setText(item.getI_DnNome());
		this.progetto.setText(item.getP_DnTitolo());
		this.collezione.setText(item.getC_DnTitolo());
		this.titolo.setText(item.getO_DnTitolo());
		this.titoloFe.setText(item.getO_DnTitoloFe());
		this.titoloSup.setText(item.getO_DnTitoloSup());
		
		this.immaginiPreviste.setText(item.getO_NrImmaginiPreviste()==null ? "" : item.getO_NrImmaginiPreviste().toString());
		this.immaginiDigitalizzate.setText(item.getO_NrImmaginiDigitalizzate()==null ? "" : item.getO_NrImmaginiDigitalizzate().toString());
		this.tipologiaOggetto.setText(item.getT_DnNome());
		this.logAnomalia.setValue(item.getO_DsLogAnomalia());

		this.notaCorrezione.setValue(item.getO_DsNotaCorrezione());
		
		this.immaginiDigitalizzateStep2.setValue(item.getO_NrImmaginiDigitalizzate()==null ? Integer.valueOf(0) : item.getO_NrImmaginiDigitalizzate().intValue());

		this.titleStep2.setInnerHTML(messages.titleStep2(item.getO_DnTitolo()));
		this.contentStep2.setInnerHTML(messages.contentStep2(item.getI_DnNome(),item.getO_DigitalizzatoreId()));

		this.numImages.setVisible(true);
		
		servizioFunzione.isNrImmaginiVisibile(item.getO_CdOggettoOriginale(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(!result){
					Integer nrImg = Integer.valueOf(1);
//					numImages.disable();
					numImages.setVisible(false);
					immaginiPreviste.setText(nrImg.toString());
					immaginiDigitalizzateStep2.setValue(nrImg);
				}else{
//					numImages.enable();
					numImages.setVisible(true);
				}
			}
			public void onFailure(Throwable caught) {
				new GXTAlertBox(messages.errorTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
				getDialog().hide();
			}
		});
		
		annulla.enable();
		action.enable();

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#setHeadingText(java.lang.String)
	 */
	public void setHeadingText(String text) {
		dialog.setHeadingText(text);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#getItem()
	 */
	@Override
	public VOggettoDTO getItem() {
		return item;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#getStepLayout()
	 */
	public CardLayoutContainer getStepLayout() {
		return stepLayout;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#getAction()
	 */
	public TextButton getAction() {
		return action;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController.iCorrezioneOggettiFormView#getNrImmaginiDigitalizzate()
	 */
	public Integer getNrImmaginiDigitalizzate() {
		return immaginiDigitalizzateStep2.getValue();
	}
}
