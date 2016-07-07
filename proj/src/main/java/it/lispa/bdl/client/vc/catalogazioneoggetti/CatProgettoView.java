package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.i18n.shared.DateTimeFormat;
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
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Class CatProgettoView.
 */
public class CatProgettoView extends ViewWithUiHandlers<CatProgettoHandler> implements CatProgettoController.iCatProgettoView {

	private final Widget widget;
	
	@UiField CardLayoutContainer stepLayout;

	@UiField FieldSet vFieldset;
	@UiField VerticalLayoutContainer vFieldsetVlc;
	@UiField FieldSet fFieldset;
	@UiField VerticalLayoutContainer fFieldsetVlc;
	
	
	@UiField InlineLabel vTitolo;
	@UiField InlineLabel vTitoloBreve;
	@UiField InlineLabel vDescrizioneIT;
	@UiField InlineLabel vDescrizioneEN;
	@UiField InlineLabel vDataInizio;
	@UiField InlineLabel vDataConclusione;
	@UiField InlineLabel vStato;
	
	@UiField TextField fTitolo;
	@UiField TextField fTitoloBreve;
	@UiField TextArea fDescrizioneIT;
	@UiField TextArea fDescrizioneEN;
	@UiField DateField fDataInizio;
	@UiField DateField fDataConclusione;

	@UiField HBoxLayoutContainer vButtons;
	@UiField HBoxLayoutContainer fButtons;
	
	@UiField TextButton fSalva;
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CatProgettoView> {
	}
    


	/**
	 * Istanzia un nuovo cat progetto view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public CatProgettoView(final EventBus eventBus, final Binder binder) {
    	super();
        widget = binder.createAndBindUi(this);
        vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
        fFieldsetVlc.setScrollMode(ScrollMode.AUTOY);
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
    	areAllValid = fTitoloBreve.isValid() && areAllValid;
    	areAllValid = fDescrizioneIT.isValid() && areAllValid;
    	areAllValid = fDescrizioneEN.isValid() && areAllValid;
    	areAllValid = fDataInizio.isValid() && areAllValid;
    	areAllValid = fDataConclusione.isValid() && areAllValid;
    	
    	if (areAllValid && getUiHandlers() != null) {
        	getUiHandlers().onFSalva();
        }
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#getStepLayout()
	 */
	public CardLayoutContainer getStepLayout() {
		return stepLayout;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#refreshFields(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	public void refreshFields(ProgettoDTO item) {

		fTitolo.reset();
		fTitoloBreve.reset();
		fDescrizioneIT.reset();
		fDescrizioneEN.reset();
		fDataInizio.reset();
		fDataConclusione.reset();
		
		if(item == null){

			vTitolo.setText("");
			vTitoloBreve.setText("");
			vDescrizioneIT.setText("");
			vDescrizioneEN.setText("");
			vDataInizio.setText("");
			vDataConclusione.setText("");
			vStato.setText("");

			fTitolo.setText("");
			fTitoloBreve.setText("");
			fDescrizioneIT.setText("");
			fDescrizioneEN.setText("");
			fDataInizio.setValue(null);
			fDataConclusione.setValue(null);
		}else{

			DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
			
			vTitolo.setText(item.getTitolo());
			vTitoloBreve.setText(item.getTitoloBreve());
			vDescrizioneIT.setText(item.getDescrizioneIt());
			vDescrizioneEN.setText(item.getDescrizioneEn());

			if(item.getInizio()!=null){
				vDataInizio.setText(fmt.format(item.getInizio()));				
			}else{
				vDataInizio.setText("");
			}
			if(item.getConclusione()!=null){
				vDataConclusione.setText(fmt.format(item.getConclusione()));				
			}else{
				vDataConclusione.setText("");
			}
			vStato.setText(item.getStatoHuman());

			fTitolo.setValue(item.getTitolo());
			fTitoloBreve.setValue(item.getTitoloBreve());
			fDescrizioneIT.setValue(item.getDescrizioneIt());
			fDescrizioneEN.setValue(item.getDescrizioneEn());
			fDataInizio.setValue(item.getInizio());
			fDataConclusione.setValue(item.getConclusione());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#getfFieldset()
	 */
	public FieldSet getfFieldset() {
		return fFieldset;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#getProgettoDTO()
	 */
	@Override
	public ProgettoDTO getProgettoDTO() {
		return new ProgettoDTO(
			null,
			fTitolo.getCurrentValue(),
			fTitoloBreve.getCurrentValue(), 
			fDescrizioneIT.getCurrentValue(), 
			fDescrizioneEN.getCurrentValue(), 
			fDataInizio.getCurrentValue(), 
			fDataConclusione.getCurrentValue(),
			BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO
		);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#activateModifyForm()
	 */
	public void activateModifyForm(){
		stepLayout.setActiveWidget(stepLayout.getWidget(1));
		fButtons.forceLayout();
		fFieldset.forceLayout();
		fFieldsetVlc.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController.iCatProgettoView#activateView()
	 */
	public void activateView(){
		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldset.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}