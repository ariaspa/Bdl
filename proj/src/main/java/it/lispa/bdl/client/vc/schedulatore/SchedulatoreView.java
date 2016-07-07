package it.lispa.bdl.client.vc.schedulatore;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class SchedulatoreView.
 */
public class SchedulatoreView extends ViewImpl implements SchedulatoreController.iSchedulatoreView {
	

    private final Widget widget;

    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, SchedulatoreView> {
    	
    }

    /**
     * Istanzia un nuovo Schedulatore view.
     *
     * @param binder  binder
     */
    @Inject
    public SchedulatoreView(final Binder binder) {
            widget = binder.createAndBindUi(this);
    }

    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    public Widget asWidget() {
            return widget;
    }
}