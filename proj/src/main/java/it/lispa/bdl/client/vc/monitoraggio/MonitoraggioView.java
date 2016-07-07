package it.lispa.bdl.client.vc.monitoraggio;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class MonitoraggioView.
 */
public class MonitoraggioView extends ViewImpl implements MonitoraggioController.iMonitoraggioView {
	

    private final Widget widget;

    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, MonitoraggioView> {
    	
    }

    /**
     * Istanzia un nuovo monitoraggio view.
     *
     * @param binder  binder
     */
    @Inject
    public MonitoraggioView(final Binder binder) {
            widget = binder.createAndBindUi(this);
    }

    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    public Widget asWidget() {
            return widget;
    }
}