package it.lispa.bdl.client.vc.caricamentoimmagini;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CaricamentoImmaginiMsg;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.CaricamentoImmaginiServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;

/**
 * Class CaricamentoImmaginiFormController.
 */
public class CaricamentoImmaginiFormController extends PresenterWidget<CaricamentoImmaginiFormController.iCaricamentoImmaginiFormView>
implements CaricamentoImmaginiFormHandler{

	@Inject CaricamentoImmaginiMsg messages;
	/*
	 * MOCK
	 */
	CaricamentoImmaginiServiceAsync servizioFunzione = (CaricamentoImmaginiServiceAsync) GWT.create(CaricamentoImmaginiService.class);

	/**
	 * Interface iCaricamentoImmaginiFormView.
	 */
	public interface iCaricamentoImmaginiFormView extends PopupView, HasUiHandlers<CaricamentoImmaginiFormHandler> {
		
		/**
		 * Imposta heading text.
		 *
		 * @param text nuovo heading text
		 */
		public void setHeadingText(String text);
		
		/**
		 * Refresh fields.
		 *
		 * @param item  item
		 */
		public void refreshFields(VOggettoDTO item);
		
		/**
		 * Legge dialog.
		 *
		 * @return dialog
		 */
		public Window getDialog();
		
		/**
		 * Legge item.
		 *
		 * @return item
		 */
		public VOggettoDTO getItem();
		
		/**
		 * Legge step layout.
		 *
		 * @return step layout
		 */
		public CardLayoutContainer getStepLayout();
		
		/**
		 * Legge action.
		 *
		 * @return action
		 */
		public TextButton getAction();
		
		Integer getNrImmaginiDigitalizzate();
		
		Boolean getIsCreaImmaginiDerivate();

		void setIsGenerazioneImmagini(Boolean bval);
		Boolean getIsGenerazioneImmagini();
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo caricamento immagini form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CaricamentoImmaginiFormController(final EventBus eventBus, final iCaricamentoImmaginiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiFormHandler#onAction()
	 */
	public void onAction() {
		int widgetSelected = getView().getStepLayout().getWidgetIndex(getView().getStepLayout().getActiveWidget());
		if(widgetSelected==0) {
			/* Step 0 */ 
//			new GXTAlertBox("Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			getView().getAction().setText(!getView().getIsGenerazioneImmagini()?messages.btnItemAction():messages.btnConfermaGenerazione());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(!getView().getIsGenerazioneImmagini()?Integer.valueOf(2):Integer.valueOf(1)));
//			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(Integer.valueOf(1)));
		} else if(widgetSelected==1) {
			/* Step 1 */
//			new GXTAlertBox("Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			getView().getAction().setText(messages.btnItemAction());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(2));
		} else if(widgetSelected==2) {
			/* Step 2 */
//			new GXTAlertBox("Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			final BigDecimal 	cdOggettoOriginale 		= getView().getItem().getO_CdOggettoOriginale();
			final BigDecimal 	nrImmaginiDigitalizzate = new BigDecimal(getView().getNrImmaginiDigitalizzate()); 
			final Boolean 		isCreaImmaginiDerivate 	= new Boolean(getView().getIsCreaImmaginiDerivate()); 
			/* isCreaImmaginiDerivate := la scelta fatta allo step 2
			 * isGenerazioneImmagini := la scelta fatta la click del bottone
			 */
			servizioFunzione.caricaImmagini(cdOggettoOriginale, nrImmaginiDigitalizzate, isCreaImmaginiDerivate, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().getDialog().hide();
					eventBus.fireEvent(new ChangeGridState());
					new GXTMessageBox(messages.OKTitlePanel(), messages.esitoOK(), GXTMessageBox.DO_SHOW);
				}
				@Override
				public void onFailure(Throwable caught) {
					
					GWT.log(">>>>> Caricamento immagini fallito!");
					eventBus.fireEvent(new ChangeGridState());
					new GXTMessageBox(messages.errorSchedTitlePanel(), caught.getMessage(), GXTMessageBox.DO_SHOW);

					servizioFunzione.getOggetto(cdOggettoOriginale, new AsyncCallback<VOggettoDTO>() {
						@Override
						public void onSuccess(VOggettoDTO result) {
							getView().refreshFields(result);
							onAnnulla();
						}
						public void onFailure(Throwable caught) {
							new GXTAlertBox(messages.errorTitlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
							getView().getDialog().hide();
						}
					});
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiFormHandler#onAnnulla()
	 */
	@Override
	public void onAnnulla() {
		int widgetSelected = getView().getStepLayout().getWidgetIndex(getView().getStepLayout().getActiveWidget());
		if(widgetSelected==0) {
			/* Step 0 */
//			new GXTAlertBox("___Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			getView().hide();	
		} else if(widgetSelected==1) {
			/* Step 1 */
//			new GXTAlertBox("___Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			getView().getAction().setText(messages.btnItemStep2Action());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(0));
		} else if(widgetSelected==2) {
			/* Step 2 */
//			new GXTAlertBox("___Step "+widgetSelected+" di 2", getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
			getView().getAction().setText(getView().getIsGenerazioneImmagini()?messages.btnConfermaGenerazione():messages.btnItemStep2Action());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(getView().getIsGenerazioneImmagini()?Integer.valueOf(1):Integer.valueOf(0)));
//			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(Integer.valueOf(0)));
		}
	}
}
