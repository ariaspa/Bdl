package it.lispa.bdl.client.vc.correzioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CorrezioneOggettiMsg;
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
 * Class CorrezioneOggettiFormController.
 */
public class CorrezioneOggettiFormController extends PresenterWidget<CorrezioneOggettiFormController.iCorrezioneOggettiFormView>
implements CorrezioneOggettiFormHandler{

	@Inject CorrezioneOggettiMsg messages;
	
	CaricamentoImmaginiServiceAsync servizioFunzione = (CaricamentoImmaginiServiceAsync) GWT.create(CaricamentoImmaginiService.class);
	
	/**
	 * Interface iCorrezioneOggettiFormView.
	 */
	public interface iCorrezioneOggettiFormView extends PopupView, HasUiHandlers<CorrezioneOggettiFormHandler> {
		
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
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo correzione oggetti form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CorrezioneOggettiFormController(final EventBus eventBus, final iCorrezioneOggettiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormHandler#onAction()
	 */
	public void onAction() {
		int widgetSelected = getView().getStepLayout().getWidgetIndex(getView().getStepLayout().getActiveWidget());
		if(widgetSelected==0){
			/* Step 0 */
			getView().getAction().setText(messages.btnItemStep2Action());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(1));
			
		} else if(widgetSelected==1){
			/* Step 1 */
			final BigDecimal cdOggettoOriginale = getView().getItem().getO_CdOggettoOriginale();
			
			final BigDecimal nrImmaginiDigitalizzate = new BigDecimal(getView().getNrImmaginiDigitalizzate()); 
			
			servizioFunzione.caricaImmagini(cdOggettoOriginale, nrImmaginiDigitalizzate, Boolean.FALSE, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().getDialog().hide();
					eventBus.fireEvent(new ChangeGridState());
					new GXTMessageBox(messages.OKTitlePanel(), messages.esitoOK(), GXTMessageBox.DO_SHOW);
				}
				@Override
				public void onFailure(Throwable caught) {
					
					GWT.log("Caricamento immagini fallito!");
					eventBus.fireEvent(new ChangeGridState());

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
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormHandler#onAnnulla()
	 */
	@Override
	public void onAnnulla() {
		int widgetSelected = getView().getStepLayout().getWidgetIndex(getView().getStepLayout().getActiveWidget());
		if(widgetSelected==0){
			getView().hide();	
		}else if(widgetSelected==1){
			getView().getAction().setText(messages.btnItemAction());
			getView().getStepLayout().setActiveWidget(getView().getStepLayout().getWidget(0));
		}	
	}
}
