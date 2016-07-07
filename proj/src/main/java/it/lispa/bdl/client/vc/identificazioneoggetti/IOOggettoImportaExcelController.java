package it.lispa.bdl.client.vc.identificazioneoggetti;

import gwtupload.client.IUploader.UploadedInfo;
import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.messages.IOOggettoImportaExcelMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

/**
 * Class IOOggettoImportaExcelController.
 */
public class IOOggettoImportaExcelController extends PresenterWidget<IOOggettoImportaExcelController.iIOOggettoImportaExcelView>
implements IOOggettoImportaExcelHandler{

	@Inject IOOggettoImportaExcelMsg messages;

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	/**
	 * Interface iIOOggettoImportaExcelView.
	 */
	public interface iIOOggettoImportaExcelView extends PopupView, HasUiHandlers<IOOggettoImportaExcelHandler> {
		
		/**
		 * Reset form.
		 */
		public void resetForm();
		
		/**
		 * Legge import info.
		 *
		 * @return import info
		 */
		public UploadedInfo getImportInfo();
		
		/**
		 * Imposta log.
		 *
		 * @param logStr nuovo log
		 */
		public void setLog(String logStr);
		
		/**
		 * Legge txt esito.
		 *
		 * @return txt esito
		 */
		public InlineLabel getTxtEsito();
		
		/**
		 * Mask.
		 *
		 * @param str  str
		 */
		public void mask(String str);
		
		/**
		 * Unmask.
		 */
		public void unmask();
	}
	private EventBus eventBus;
	
	IdentificazioneOggettiController listener;
	BigDecimal cdCollezione;

	/**
	 * Istanzia un nuovo IO oggetto importa excel controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOOggettoImportaExcelController(final EventBus eventBus, final iIOOggettoImportaExcelView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelHandler#onUploadComplete()
	 */
	public void onUploadComplete() {
		final UploadedInfo info = getView().getImportInfo();
		
		String message =  messages.actionConfirm(info.getFileName());

		ConfirmMessageBox box = new ConfirmMessageBox(messages.infoPanel(), message);
		box.setSize("420","150");
		box.addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				Dialog btn = (Dialog) event.getSource();
				if (btn.getHideButton() == btn.getButtonById(PredefinedButton.YES.name())) {
					getView().getTxtEsito().setText("");
					getView().setLog("");
					getView().mask("Importazione in corso...");
					
					Integer filesizeI = new Integer(info.getSize());
					Long filesizeL = filesizeI.longValue();
					servizioIdentOggetti.importaExcel(cdCollezione, info.getFileName(), filesizeL,  new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							String message = messages.esitoKO();
							getView().setLog(caught.getMessage());
							new GXTAlertBox(messages.infoPanel(), message, GXTAlertBox.DO_SHOW);
							getView().getTxtEsito().setText(messages.txtEsito(info.getFileName()));
							getView().unmask();
						}
						@Override
						public void onSuccess(Void v) {
							getView().unmask();
							eventBus.fireEvent(new ChangeGridState());
							String message = messages.esitoOK();
							new GXTMessageBox(messages.infoPanel(), message, GXTMessageBox.DO_SHOW);
							onChiudi();
						}
					});
				} else if (btn.getHideButton() == btn.getButtonById(PredefinedButton.NO.name())){
					// perform NO action
					getView().resetForm();
				}
			}
		});
		box.show();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelHandler#onChiudi()
	 */
	@Override
	public void onChiudi() {
		getView().unmask();
		getView().resetForm();
		getView().hide();		
		
	}

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(IdentificazioneOggettiController listener) {
		this.listener = listener;
	}

	/**
	 * Legge codice collezione.
	 *
	 * @return codice collezione
	 */
	public BigDecimal getCdCollezione() {
		return cdCollezione;
	}

	/**
	 * Imposta codice collezione.
	 *
	 * @param cdCollezione nuovo codice collezione
	 */
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
	}


}
