package it.lispa.bdl.client.vc.home;

import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.HomeMsg;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;

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
 * Class HomeRegistrazioneFormController.
 */
public class HomeRegistrazioneFormController extends PresenterWidget<HomeRegistrazioneFormController.iHomeRegistrazioneFormView>
implements HomeRegistrazioneFormHandler{

	@Inject HomeMsg messages;

	ExternalServiceAsync externalService = (ExternalServiceAsync) GWT.create(ExternalService.class);

	/**
	 * Interface iHomeRegistrazioneFormView.
	 */
	public interface iHomeRegistrazioneFormView extends PopupView, HasUiHandlers<HomeRegistrazioneFormHandler> {
		
		/**
		 * Imposta heading text.
		 *
		 * @param text nuovo heading text
		 */
		public void setHeadingText(String text);
		
		/**
		 * Legge dialog.
		 *
		 * @return dialog
		 */
		public Window getDialog();
		
		/**
		 * Refresh fields.
		 */
		public void refreshFields();
		
		/**
		 * Legge utente dto.
		 *
		 * @return utente dto
		 */
		public UtenteDTO getUtenteDTO();
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo home registrazione form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public HomeRegistrazioneFormController(final EventBus eventBus, final iHomeRegistrazioneFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormHandler#onAction()
	 */
	public void onAction() {
		final UtenteDTO user = getView().getUtenteDTO();

		ConfirmMessageBox box = new ConfirmMessageBox(messages.infoTitle(), messages.confirmRegistrazione());
		box.setSize("420","130");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					externalService.registraUtente(user, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							new GXTAlertBox(messages.errorTitle(), caught.getMessage(), GXTAlertBox.DO_SHOW);
						}
						@Override
						public void onSuccess(Void v) {
							getView().hide();
							new GXTMessageBox(messages.infoTitle(), messages.registrazioneOK(), GXTMessageBox.DO_SHOW);
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
	 * @see it.lispa.bdl.client.vc.home.HomeRegistrazioneFormHandler#onAnnulla()
	 */
	@Override
	public void onAnnulla() {
		getView().hide();		
	}

}
