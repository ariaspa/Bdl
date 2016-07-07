package it.lispa.bdl.client.vc.pubblicazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.messages.PubOggettoAnteprimaImmagineMsg;
import it.lispa.bdl.shared.messages.ValOggettoAnteprimaImmagineMsg;

import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.core.client.Style.Anchor;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.core.client.util.Point;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;

/**
 * Class PubOggettoAnteprimaImmagineView.
 */
public class PubOggettoAnteprimaImmagineView extends
GXTPopupViewWithUiHandlers<PubOggettoAnteprimaImmagineHandler> implements
PubOggettoAnteprimaImmagineController.iPubOggettoAnteprimaImmagineView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, PubOggettoAnteprimaImmagineView> {
	}


	@UiField(provided = true) Window dialog;
	
	@UiField ContentPanel vContent;
	
	ValOggettoAnteprimaImmagineMsg messages;
	
	@UiField Image imageWidget;

	/**
	 * Istanzia un nuovo pub oggetto anteprima immagine view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param messages  messages
	 * @param img  img
	 */
	@Inject
	public PubOggettoAnteprimaImmagineView(final EventBus eventBus, final Binder binder,PubOggettoAnteprimaImmagineMsg messages, Images img) {
		super(eventBus);
		
		dialog = new Window();
		dialog.setHeadingText(messages.infoTitle());
		dialog.setModal(true);

		
		widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoAnteprimaImmagineController.iPubOggettoAnteprimaImmagineView#resetImage(it.lispa.bdl.shared.dto.ImmagineDTO)
	 */
	public void resetImage(ImmagineDTO img) {

	    dialog.hide();
	    
		Integer width = img.getNrPxLarghezzaReader()+10;
		Integer height = img.getNrPxAltezzaReader()+10;
		Point p = dialog.getElement().getAlignToXY(Document.get().getBody(), new AnchorAlignment(Anchor.CENTER, Anchor.TOP),null);
	    dialog.setPagePosition(p.getX(), p.getY());
		imageWidget.setUrl(img.getPathReader());
		
		double ratio =(double)width / (double)height;
		
		if((Document.get().getBody().getClientHeight()-10)<height){
			height = Document.get().getBody().getClientHeight()-10;
			width = (int) (ratio * height);
		}
		
		dialog.setPixelSize(width, height);
		
	    dialog.show();
	}
}
