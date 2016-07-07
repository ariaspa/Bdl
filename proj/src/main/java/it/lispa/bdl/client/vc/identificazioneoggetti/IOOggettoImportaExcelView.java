package it.lispa.bdl.client.vc.identificazioneoggetti;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.VOggettoDTO;

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

/**
 * Class IOOggettoImportaExcelView.
 */
public class IOOggettoImportaExcelView extends
GXTPopupViewWithUiHandlers<IOOggettoImportaExcelHandler> implements
IOOggettoImportaExcelController.iIOOggettoImportaExcelView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOOggettoImportaExcelView> {
	}

	VOggettoDTO item;

	@UiField Window dialog;
	@UiField MultiUploader uploader;
	@UiField TextButton btnChiudi;
	@UiField TextArea logImport;
	@UiField InlineLabel txtEsito;
	
	UploadedInfo importInfo;

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

	/**
	 * Istanzia un nuovo IO oggetto importa excel view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public IOOggettoImportaExcelView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		widget = binder.createAndBindUi(this);
		

		uploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}
	
	/**
	 * On chiudi.
	 *
	 * @param event  event
	 */
	@UiHandler("btnChiudi")
	public void onChiudi(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onChiudi();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#resetForm()
	 */
	public void resetForm() {
		uploader.reset();
		importInfo=null;
		logImport.setValue("");
		txtEsito.setText("");
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#setLog(java.lang.String)
	 */
	public void setLog(String logStr){
		logImport.setValue(logStr);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#getImportInfo()
	 */
	public UploadedInfo getImportInfo(){
		return importInfo;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#getTxtEsito()
	 */
	public InlineLabel getTxtEsito() {
		return txtEsito;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#mask(java.lang.String)
	 */
	@Override
	public void mask(String str) {
		dialog.mask(str);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController.iIOOggettoImportaExcelView#unmask()
	 */
	@Override
	public void unmask() {
		dialog.unmask();
	}

}
