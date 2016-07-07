package it.lispa.bdl.client.vc.ammvalidutenti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.AmmValidUtentiMsg;
import it.lispa.bdl.shared.services.AmmValidUtentiService;
import it.lispa.bdl.shared.services.AmmValidUtentiServiceAsync;

import com.google.gwt.core.shared.GWT;
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
 * Class AmmValidUtentiFormController.
 */
public class AmmValidUtentiFormController extends PresenterWidget<AmmValidUtentiFormController.iAmmValidUtentiFormView>
implements AmmValidUtentiFormHandler{

	@Inject AmmValidUtentiMsg messages;

	AmmValidUtentiServiceAsync servizio = (AmmValidUtentiServiceAsync) GWT.create(AmmValidUtentiService.class);

	/**
	 * Interface iAmmValidUtentiFormView.
	 */
	public interface iAmmValidUtentiFormView extends PopupView, HasUiHandlers<AmmValidUtentiFormHandler> {
		
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
		public void refreshFields(UtenteDTO item);
		
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
		public UtenteDTO getItem();
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo amm valid utenti form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public AmmValidUtentiFormController(final EventBus eventBus, final iAmmValidUtentiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormHandler#onValidazione()
	 */
	public void onValidazione() {

		String message =  messages.validazioneConfirm(getView().getItem().getNome()+" "+getView().getItem().getCognome());

		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","130");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {

					servizio.validaUtente(getView().getItem().getCdUtente(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Non fare nulla: l'errore generico viene gestito a monte...
							// e il metodo non tira un AsyncServiceException
						}
						@Override
						public void onSuccess(Void v) {
							eventBus.fireEvent(new ChangeGridState());
							new GXTMessageBox(messages.titlePanel(), messages.validazioneOk(), GXTMessageBox.DO_SHOW);
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
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormHandler#onAnnulla()
	 */
	public void onAnnulla() {
		getView().hide();
	}

}
