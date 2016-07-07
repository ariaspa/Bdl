package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.shared.dto.CollezioneDTO;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;

/**
 * Class IOCollezioneView.
 */
public class IOCollezioneView extends ViewWithUiHandlers<IOCollezioneHandler> implements IOCollezioneController.iIOCollezioneView {

	private final Widget widget;
	
	@UiField CardLayoutContainer stepLayout;

	@UiField FieldSet vFieldset;
	@UiField VerticalLayoutContainer vFieldsetVlc;
	@UiField FieldSet fFieldset;
	@UiField VerticalLayoutContainer fFieldsetVlc;
	
	@UiField InlineLabel vTitolo;
	@UiField InlineLabel vDescrizioneIT;
	@UiField InlineLabel vDescrizioneEN;
	@UiField InlineLabel vDiritti;
	@UiField InlineLabel vAmbitoDisciplinare;
	@UiField InlineLabel vCoperturaGeografica;
	@UiField InlineLabel vPeriodo;
	@UiField InlineLabel vAnnoAntico;
	@UiField InlineLabel vAnnoRecente;

	@UiField TextField fTitolo;
	@UiField TextArea fDescrizioneIT;
	@UiField TextArea fDescrizioneEN;
	@UiField TextField fDiritti;
	@UiField TextField fAmbitoDisciplinare;
	@UiField TextField fCoperturaGeografica;
	@UiField TextField fPeriodo;
	@UiField TextField fAnnoAntico;
	@UiField TextField fAnnoRecente;

	@UiField HBoxLayoutContainer vButtons;
	@UiField HBoxLayoutContainer fButtons;
	
	@UiField TextButton fSalva;
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOCollezioneView> {
	}
    


	/**
	 * Istanzia un nuovo IO collezione view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public IOCollezioneView(final EventBus eventBus, final Binder binder) {
    	super();
        widget = binder.createAndBindUi(this);

        vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        
        fTitolo.addValidator(new MaxLengthValidator(500));
        fDescrizioneIT.addValidator(new MaxLengthValidator(1000));
        fDescrizioneEN.addValidator(new MaxLengthValidator(1000));
        fDiritti.addValidator(new MaxLengthValidator(250));
        fAmbitoDisciplinare.addValidator(new MaxLengthValidator(500));
        fCoperturaGeografica.addValidator(new MaxLengthValidator(500));
        fPeriodo.addValidator(new MaxLengthValidator(500));
        fAnnoAntico.addValidator(new MaxLengthValidator(4));
        fAnnoRecente.addValidator(new MaxLengthValidator(4));
        
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
	@UiHandler("vOggetto")
	public void onVOggetto(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVOggetto();
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
		fSalva.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onFAnnulla();
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
    	areAllValid = fDescrizioneIT.isValid() && areAllValid;
    	areAllValid = fDescrizioneEN.isValid() && areAllValid;
    	areAllValid = fDiritti.isValid() && areAllValid;
    	areAllValid = fAmbitoDisciplinare.isValid() && areAllValid;
    	areAllValid = fCoperturaGeografica.isValid() && areAllValid;
    	areAllValid = fCoperturaGeografica.isValid() && areAllValid;
    	areAllValid = fAnnoAntico.isValid() && areAllValid;
    	areAllValid = fAnnoRecente.isValid() && areAllValid;
    	
    	if (areAllValid && getUiHandlers() != null) {
        	getUiHandlers().onFSalva();
        }
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#getStepLayout()
	 */
	public CardLayoutContainer getStepLayout() {
		return stepLayout;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#refreshFields(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	public void refreshFields(CollezioneDTO item) {

		
		fTitolo.reset();
		fDescrizioneIT.reset();
		fDescrizioneEN.reset();
		fDiritti.reset();
		fAmbitoDisciplinare.reset();
		fCoperturaGeografica.reset();
		fPeriodo.reset();
		fAnnoAntico.reset();
		fAnnoRecente.reset();
		
		if(item == null){
			
			vTitolo.setText("");
			vDescrizioneIT.setText("");
			vDescrizioneEN.setText("");
			vDiritti.setText("");
			vAmbitoDisciplinare.setText("");
			vCoperturaGeografica.setText("");
			vPeriodo.setText("");
			vAnnoAntico.setText("");
			vAnnoRecente.setText("");
			
			fTitolo.setValue("");
			fDescrizioneIT.setValue("");
			fDescrizioneEN.setValue("");
			fDiritti.setValue("");
			fAmbitoDisciplinare.setValue("");
			fCoperturaGeografica.setValue("");
			fPeriodo.setValue("");
			fAnnoAntico.setValue("");
			fAnnoRecente.setValue("");
			
		}else{

			vTitolo.setText(item.getTitolo());
			vDescrizioneIT.setText(item.getDescrizioneIt());
			vDescrizioneEN.setText(item.getDescrizioneEn());
			vDiritti.setText(item.getDiritti());
			vAmbitoDisciplinare.setText(item.getAmbitoDisciplinare());
			vCoperturaGeografica.setText(item.getCoperturaGeografica());
			vPeriodo.setText(item.getPeriodo());
			vAnnoAntico.setText(item.getAnnoOggAntico());
			vAnnoRecente.setText(item.getAnnoOggRecente());

			fTitolo.setValue(item.getTitolo());
			fDescrizioneIT.setValue(item.getDescrizioneIt());
			fDescrizioneEN.setValue(item.getDescrizioneEn());
			fDiritti.setValue(item.getDiritti());
			fAmbitoDisciplinare.setValue(item.getAmbitoDisciplinare());
			fCoperturaGeografica.setValue(item.getCoperturaGeografica());
			fPeriodo.setValue(item.getPeriodo());
			fAnnoAntico.setValue(item.getAnnoOggAntico());
			fAnnoRecente.setValue(item.getAnnoOggRecente());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#getfFieldset()
	 */
	public FieldSet getfFieldset() {
		return fFieldset;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#getCollezioneDTO()
	 */
	@Override
	public CollezioneDTO getCollezioneDTO() {
		return new CollezioneDTO(
				null,
				null,
				fTitolo.getCurrentValue(), 
				fDescrizioneIT.getCurrentValue(), 
				fDescrizioneEN.getCurrentValue(), 
				fDiritti.getCurrentValue(), 
				fAmbitoDisciplinare.getCurrentValue(), 
				fCoperturaGeografica.getCurrentValue(), 
				fPeriodo.getCurrentValue(), 
				fAnnoAntico.getCurrentValue(), 
				fAnnoRecente.getCurrentValue()
		);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#activateInsertForm()
	 */
	public void activateInsertForm(){
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();		
		fFieldset.forceLayout();
		fFieldsetVlc.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#activateModifyForm()
	 */
	public void activateModifyForm(){
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldset.forceLayout();
		fFieldsetVlc.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController.iIOCollezioneView#activateView()
	 */
	public void activateView(){
		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldset.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}