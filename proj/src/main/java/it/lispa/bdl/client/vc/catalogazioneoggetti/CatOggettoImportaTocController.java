package it.lispa.bdl.client.vc.catalogazioneoggetti;

import gwtupload.client.IUploader.UploadedInfo;
import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTMessageBox;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.messages.CatOggettoImportaTocMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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

public class CatOggettoImportaTocController extends PresenterWidget<CatOggettoImportaTocController.iCatOggettoImportaTocView> implements CatOggettoImportaTocHandler{

	@Inject CatOggettoImportaTocMsg messages;

	CatalogazioneOggettiServiceAsync servizioCatOggetti = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	public interface iCatOggettoImportaTocView extends PopupView, HasUiHandlers<CatOggettoImportaTocHandler> {
		public void resetForm();
		public UploadedInfo getImportInfo();
		public void setLog(String logStr);
		public InlineLabel getTxtEsito();
		public void mask(String str);
		public void unmask();
	}

	private EventBus eventBus;

	@Inject
	public CatOggettoImportaTocController(final EventBus eventBus, final iCatOggettoImportaTocView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}
	@Override
	protected void onBind() {
		super.onBind();
	}

	CatalogazioneOggettiController listener;

	ComboDTO tipoOggetto;
	BigDecimal cdCollezione;

	public ComboDTO getTipoOggetto() {
		return tipoOggetto;
	}
	public void setTipoOggetto(ComboDTO tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
	public BigDecimal getCdCollezione() {
		return cdCollezione;
	}
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
	}
	public void setListener(CatalogazioneOggettiController listener) {
		this.listener = listener;
	}

	@Override
	public void onScaricaTemplate() {
		String link = GWT.getHostPageBaseURL() + "../ImportaTocTpl.xlsx";
		Window.open(link, "_blank", null);
	}
	@Override
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

					servizioCatOggetti.importaToc(new AsyncCallback<Void>() {
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
	@Override
	public void onChiudi() {
		getView().unmask();
		getView().resetForm();
		getView().hide();		
	}
}
