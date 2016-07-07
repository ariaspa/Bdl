package it.lispa.bdl.client.vc.catalogazioneoggetti;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.messages.CatOggettoImportaTocMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;

public class CatOggettoImportaTocView extends GXTPopupViewWithUiHandlers<CatOggettoImportaTocHandler> implements CatOggettoImportaTocController.iCatOggettoImportaTocView {

	private final Widget widget;

	CatalogazioneOggettiServiceAsync servizioCatOggetti = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	public interface Binder extends UiBinder<Widget, CatOggettoImportaTocView> {
	}

	@Inject CatOggettoImportaTocMsg messages;

	@UiField TextButton btnChiudi;
	@UiField TextButton btnScaricaTemplate;

	@UiField Window dialog;
	@UiField MultiUploader uploader;

	@UiField TextArea logImport;
	@UiField InlineLabel txtEsito;

	UploadedInfo importInfo;

	public Widget asWidget() {
		return widget;
	}
	@Override
	public void mask(String str) {
		dialog.mask(str);
	}
	@Override
	public void unmask() {
		dialog.unmask();
	}

	public void setLog(String logStr){
		logImport.setValue(logStr);
	}
	public UploadedInfo getImportInfo(){
		return importInfo;
	}
	public InlineLabel getTxtEsito() {
		return txtEsito;
	}

	@Inject
	public CatOggettoImportaTocView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		widget = binder.createAndBindUi(this);

		uploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	}

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				UploadedInfo info = uploader.getServerInfo();
				importInfo = info;
				if (getUiHandlers() != null) {
					getUiHandlers().onUploadComplete();
				}
			}
		}
	};

	@UiHandler("btnChiudi")
	public void onChiudi(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onChiudi();
		}
	}
	@UiHandler("btnScaricaTemplate")
	public void onScaricaTemplate(SelectEvent event) {
		btnScaricaTemplate.focus();
	    getUiHandlers().onScaricaTemplate();
	}

	public void resetForm() {
		uploader.reset();
		importInfo = null;
		logImport.setValue("");
		txtEsito.setText("");
	}

	public void refreshFields(OggettoDTO item) {
		
	}
}
