package it.lispa.bdl.client.vc.catalogazioneoggetti;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class CatVuotoView.
 */
public class CatVuotoView extends ViewImpl implements CatVuotoController.iCatVuotoView {

	private final Widget widget;
	
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CatVuotoView> {
	}
    
	/**
	 * Istanzia un nuovo cat vuoto view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public CatVuotoView(final EventBus eventBus, final Binder binder) {
    	super();
        widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

}