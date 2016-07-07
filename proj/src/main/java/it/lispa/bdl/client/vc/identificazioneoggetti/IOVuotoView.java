package it.lispa.bdl.client.vc.identificazioneoggetti;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class IOVuotoView.
 */
public class IOVuotoView extends ViewImpl implements IOVuotoController.iIOVuotoView {

	private final Widget widget;
	
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOVuotoView> {
	}
    
	/**
	 * Istanzia un nuovo IO vuoto view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public IOVuotoView(final EventBus eventBus, final Binder binder) {
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