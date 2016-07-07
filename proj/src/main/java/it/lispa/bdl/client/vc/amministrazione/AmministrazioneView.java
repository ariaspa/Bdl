package it.lispa.bdl.client.vc.amministrazione;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class AmministrazioneView.
 */
public class AmministrazioneView extends ViewImpl implements AmministrazioneController.iAmministrazioneView {
	

    private final Widget widget;

    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, AmministrazioneView> {
    	
    }

    /**
     * Istanzia un nuovo amministrazione view.
     *
     * @param binder  binder
     */
    @Inject
    public AmministrazioneView(final Binder binder) {
            widget = binder.createAndBindUi(this);
    }

    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    public Widget asWidget() {
            return widget;
    }
}