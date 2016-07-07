package it.lispa.bdl.client.vc.ammgestenti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.messages.AmmGestEntiMsg;
import it.lispa.bdl.shared.services.AmmGestEntiService;
import it.lispa.bdl.shared.services.AmmGestEntiServiceAsync;

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
 * Class AmmGestEntiFormController.
 */
public class AmmGestEntiFormController extends PresenterWidget<AmmGestEntiFormController.iAmmGestEntiFormView>
implements AmmGestEntiFormHandler{

	@Inject AmmGestEntiMsg messages;

	AmmGestEntiServiceAsync servizio = (AmmGestEntiServiceAsync) GWT.create(AmmGestEntiService.class);

	/**
	 * Interface iAmmGestEntiFormView.
	 */
	public interface iAmmGestEntiFormView extends PopupView, HasUiHandlers<AmmGestEntiFormHandler> {
		
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
		public EnteDTO getItem();
		
		/**
		 * Legge form dto.
		 *
		 * @return form dto
		 */
		public EnteDTO getFormDTO();

		/**
		 * Activate insert form.
		 */
		public void activateInsertForm();
		
		/**
		 * Activate modify form.
		 *
		 * @param item  item
		 */
		public void activateModifyForm(EnteDTO item);
		
		/**
		 * Activate view.
		 *
		 * @param item  item
		 */
		public void activateView(EnteDTO item);
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo amm gest enti form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public AmmGestEntiFormController(final EventBus eventBus, final iAmmGestEntiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	EnteDTO item;
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * Cancella item.
	 */
	public void cancellaItem() {
		String message =  messages.fMsgCancellaConfirm(getView().getItem().getNome());
		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","120");
		box.addHideHandler(new HideHandler() {
			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {

					servizio.cancellaItemByCodice(getView().getItem().getCdEnte(), new AsyncCallback<Void>() {
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
	public void activateModifyForm(EnteDTO item){
		getView().activateModifyForm(item);
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(EnteDTO item){
		getView().activateView(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onVBtnAnnulla()
	 */
	@Override
	public void onVBtnAnnulla() {
		getView().hide();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onVBtnCancella()
	 */
	@Override
	public void onVBtnCancella() {
		cancellaItem();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onVBtnModifica()
	 */
	@Override
	public void onVBtnModifica() {
		activateModifyForm(getView().getItem());
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onFBtnAnnulla()
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
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onFBtnCancella()
	 */
	@Override
	public void onFBtnCancella() {
		cancellaItem();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormHandler#onFBtnSalva()
	 */
	@Override
	public void onFBtnSalva() {
		
		BigDecimal cdItem = null;
		if(getView().getItem()!=null){
			cdItem = getView().getItem().getCdEnte();
		}
		EnteDTO itemDaSalvare = getView().getFormDTO();
		itemDaSalvare.setCdEnte(cdItem);
		
		final Boolean modifying;
		if(cdItem!=null){
			modifying = true;  
		}else{
			modifying = false;
		}
		
		servizio.salvaItem(itemDaSalvare, new AsyncCallback<EnteDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(EnteDTO itemSalvato) {
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
