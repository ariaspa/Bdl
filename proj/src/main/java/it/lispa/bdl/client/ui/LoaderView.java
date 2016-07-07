package it.lispa.bdl.client.ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;
 
/**
 * Class LoaderView.
 */
public final class LoaderView extends PopupViewImpl implements LoaderController.iLoaderView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, LoaderView> {
	}
	
	/**
	 * Istanzia un nuovo loader view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
    public LoaderView(final EventBus eventBus, final Binder binder) {
    	super(eventBus);
		widget = binder.createAndBindUi(this); 
    }
    
    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    @Override
    public Widget asWidget() {
        return widget;
    }
    
    /* (non-Javadoc)
     * @see it.lispa.bdl.client.ui.LoaderController.iLoaderView#stopProcessing()
     */
    @Override
    public void stopProcessing() {
        hide();
    }
    
    /* (non-Javadoc)
     * @see it.lispa.bdl.client.ui.LoaderController.iLoaderView#startProcessing()
     */
    @Override
    public void startProcessing() {
        center();
        show();
    }
    
    /* (non-Javadoc)
     * @see it.lispa.bdl.client.ui.LoaderController.iLoaderView#showWidget()
     */
    @Override
    public void showWidget() {
        startProcessing();
    }
}