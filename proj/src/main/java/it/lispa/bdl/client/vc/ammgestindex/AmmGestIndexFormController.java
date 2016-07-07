package it.lispa.bdl.client.vc.ammgestindex;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.AmmGestIndexMsg;
import it.lispa.bdl.shared.services.AmmGestIndexService;
import it.lispa.bdl.shared.services.AmmGestIndexServiceAsync;

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
 * Class AmmGestIndexFormController.
 */
public class AmmGestIndexFormController extends PresenterWidget<AmmGestIndexFormController.iAmmGestIndexFormView>
implements AmmGestIndexFormHandler{

	@Inject AmmGestIndexMsg messages;

	AmmGestIndexServiceAsync servizio = (AmmGestIndexServiceAsync) GWT.create(AmmGestIndexService.class);

	/**
	 * Interface iAmmGestIndexFormView.
	 */
	public interface iAmmGestIndexFormView extends PopupView, HasUiHandlers<AmmGestIndexFormHandler> {
		
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
		public OggettoDTO getItem();
		
		/**
		 * Legge form dto.
		 *
		 * @return form dto
		 */
		public OggettoDTO getFormDTO();

		/**
		 * Activate insert form.
		 */
		public void activateInsertForm();
		
		/**
		 * Activate modify form.
		 *
		 * @param item  item
		 */
		public void activateModifyForm(OggettoDTO item);
		
		/**
		 * Activate view.
		 *
		 * @param item  item
		 */
		public void activateView(OggettoDTO item);
	}
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo amm gest index form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public AmmGestIndexFormController(final EventBus eventBus, final iAmmGestIndexFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	OggettoDTO item;
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * Cancella item.
	 */
	public void cancellaItem() {
		String message =  messages.fMsgCancellaConfirm(getView().getItem().getTitolo());
		ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), message);
		box.setSize("420","120");
		box.addHideHandler(new HideHandler() {
			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {

					servizio.cancellaItemByCodice(getView().getItem().getCdOggettoOriginale(), new AsyncCallback<Void>() {
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
	public void activateModifyForm(OggettoDTO item){
		getView().activateModifyForm(item);
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(OggettoDTO item){
		getView().activateView(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onVBtnAnnulla()
	 */
	@Override
	public void onVBtnAnnulla() {
		getView().hide();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onVBtnCancella()
	 */
	@Override
	public void onVBtnCancella() {
		cancellaItem();		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onVBtnModifica()
	 */
	@Override
	public void onVBtnModifica() {
		activateModifyForm(getView().getItem());
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onFBtnAnnulla()
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
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onFBtnCancella()
	 */
	@Override
	public void onFBtnCancella() {
		cancellaItem();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormHandler#onFBtnSalva()
	 */
	@Override
	public void onFBtnSalva() {
		
		BigDecimal cdItem = null;
		if(getView().getItem()!=null){
			cdItem = getView().getItem().getCdOggettoOriginale();
		}
		OggettoDTO itemDaSalvare = getView().getFormDTO();
		itemDaSalvare.setCdOggettoOriginale(cdItem);
		
		final Boolean modifying;
		if(cdItem!=null){
			modifying = true;  
		}else{
			modifying = false;
		}
		
		servizio.salvaItem(itemDaSalvare, new AsyncCallback<OggettoDTO>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.titlePanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(OggettoDTO itemSalvato) {
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
