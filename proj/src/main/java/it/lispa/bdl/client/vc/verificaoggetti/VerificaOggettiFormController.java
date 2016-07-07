package it.lispa.bdl.client.vc.verificaoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.VerificaOggettiMsg;
import it.lispa.bdl.shared.services.VerificaOggettiService;
import it.lispa.bdl.shared.services.VerificaOggettiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

/**
 * Class VerificaOggettiFormController.
 */
public class VerificaOggettiFormController extends PresenterWidget<VerificaOggettiFormController.iVerificaOggettiFormView>
implements VerificaOggettiFormHandler{

	@Inject VerificaOggettiMsg messages;

	VerificaOggettiServiceAsync servizioFunzione = (VerificaOggettiServiceAsync) GWT.create(VerificaOggettiService.class);

	/**
	 * Interface iVerificaOggettiFormView.
	 */
	public interface iVerificaOggettiFormView extends PopupView, HasUiHandlers<VerificaOggettiFormHandler> {
		
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
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo verifica oggetti form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public VerificaOggettiFormController(final EventBus eventBus, final iVerificaOggettiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiFormHandler#onAction()
	 */
	public void onAction() {

		String message =  messages.actionConfirm(getView().getItem().getO_DnTitolo());

		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","130");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					servizioFunzione.verificaOggetto(getView().getItem().getO_CdOggettoOriginale(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Non fare nulla: l'errore generico viene gestito a monte...
							// e il metodo non tira un AsyncServiceException
						}
						@Override
						public void onSuccess(Void v) {		
							eventBus.fireEvent(new ChangeGridState());
							new GXTMessageBox(messages.titlePanel(), messages.esitoOk(), GXTMessageBox.DO_SHOW);
							getView().hide();
						}
					});
				} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
					// perform NO action
				}
			}
		});
		box.show();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiFormHandler#onRifiuta()
	 */
	public void onRifiuta() {

		String message =  messages.rifiutaConfirm(getView().getItem().getO_DnTitolo());

		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","130");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					servizioFunzione.rifiutaOggetto(getView().getItem().getO_CdOggettoOriginale(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Non fare nulla: l'errore generico viene gestito a monte...
							// e il metodo non tira un AsyncServiceException
						}
						@Override
						public void onSuccess(Void v) {		
							eventBus.fireEvent(new ChangeGridState());
							new GXTMessageBox(messages.titlePanel(), messages.rifiutaOk(), GXTMessageBox.DO_SHOW);
							getView().hide();
						}
					});
				} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
					// perform NO action
				}
			}
		});
		box.show();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiFormHandler#onAnnulla()
	 */
	@Override
	public void onAnnulla() {
		getView().hide();		
	}

}
