package it.lispa.bdl.client.vc.ammgestutenti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.messages.AmmGestUtentiMsg;
import it.lispa.bdl.shared.services.AmmGestUtentiService;
import it.lispa.bdl.shared.services.AmmGestUtentiServiceAsync;

import java.math.BigDecimal;

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
 * Class AmmGestUtentiFormController.
 */
public class AmmGestUtentiFormController extends PresenterWidget<AmmGestUtentiFormController.iAmmGestUtentiFormView>
implements AmmGestUtentiFormHandler{

	@Inject AmmGestUtentiMsg messages;

	AmmGestUtentiServiceAsync servizio = (AmmGestUtentiServiceAsync) GWT.create(AmmGestUtentiService.class);

	/**
	 * Interface iAmmGestUtentiFormView.
	 */
	public interface iAmmGestUtentiFormView extends PopupView, HasUiHandlers<AmmGestUtentiFormHandler> {
		
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
		
		/**
		 * Legge form dto.
		 *
		 * @return form dto
		 */
		public UtenteDTO getFormDTO();

		/**
		 * Activate insert form.
		 */
		public void activateInsertForm();
		
		/**
		 * Activate modify form.
		 *
		 * @param item  item
		 */
		public void activateModifyForm(UtenteDTO item);
		
		/**
		 * Activate view.
		 *
		 * @param item  item
		 */
		public void activateView(UtenteDTO item);
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo amm gest utenti form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public AmmGestUtentiFormController(final EventBus eventBus, final iAmmGestUtentiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	UtenteDTO item;
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * Cancella item.
	 */
	public void cancellaItem() {
		String message =  messages.fMsgCancellaConfirm(getView().getItem().getNome()+" "+getView().getItem().getCognome());
		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","120");
		box.addHideHandler(new HideHandler() {
			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {

					servizio.cancellaItemByCodice(getView().getItem().getCdUtente(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							// Non fare nulla: l'errore generico viene gestito a monte...
							// e il metodo non tira un AsyncServiceException
						}
						@Override
						public void onSuccess(Void v) {
							eventBus.fireEvent(new ChangeGridState());
							new GXTMessageBox(messages.titlePanel(), messages.fMsgCancellaEsito(), GXTMessageBox.DO_SHOW);
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

	/**
	 * On annulla.
	 */
	public void onAnnulla() {
	}

	/**
	 * Activate insert form.
	 */
	public void activateInsertForm(){
		getView().activateInsertForm();
	}
	
	/**
	 * Activate modify form.
	 *
	 * @param item  item
	 */
	public void activateModifyForm(UtenteDTO item){
		getView().activateModifyForm(item);
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(UtenteDTO item){
		getView().activateView(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onVBtnAnnulla()
	 */
	@Override
	public void onVBtnAnnulla() {
		getView().hide();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onVBtnCancella()
	 */
	@Override
	public void onVBtnCancella() {
		cancellaItem();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onVBtnModifica()
	 */
	@Override
	public void onVBtnModifica() {
		activateModifyForm(getView().getItem());
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onFBtnAnnulla()
	 */
	@Override
	public void onFBtnAnnulla() {
		if(getView().getItem()==null){
			getView().hide();
		}else{
			activateView(getView().getItem());
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onFBtnCancella()
	 */
	@Override
	public void onFBtnCancella() {
		cancellaItem();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormHandler#onFBtnSalva()
	 */
	@Override
	public void onFBtnSalva() {
		
		BigDecimal cdItem = null;
		if(getView().getItem()!=null){
			cdItem = getView().getItem().getCdUtente();
		}
		UtenteDTO itemDaSalvare = getView().getFormDTO();
		itemDaSalvare.setCdUtente(cdItem);
		
		final Boolean modifying;
		if(cdItem!=null){
			modifying = true;  
		}else{
			modifying = false;
		}
		
		servizio.salvaItem(itemDaSalvare, new AsyncCallback<UtenteDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(UtenteDTO itemSalvato) {
				eventBus.fireEvent(new ChangeGridState());
				String message = messages.fMsgInserimentoEsito();
				if(modifying){
					message = messages.fMsgModificaEsito();
				}
				new GXTMessageBox(messages.titlePanel(), message, GXTMessageBox.DO_SHOW);
				activateView(itemSalvato);
			}
		});
		
	}


}
