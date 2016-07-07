package it.lispa.bdl.client.ui;

import it.lispa.bdl.client.images.Images;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;

/**
 * Class FooterView.
 */
public class FooterView implements FooterController.iFooterView{
	
	private final Widget widget;

    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, FooterView> {
    }
      
    /**
     * Interface iHeaderView.
     */
    public interface iHeaderView extends View {

	}
  
  
    /**
     * Istanzia un nuovo footer view.
     *
     * @param binder  binder
     * @param img  img
     */
    @Inject
    public FooterView(final Binder binder, Images img) {
        widget = binder.createAndBindUi(this);
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
     */
    public Widget asWidget() {
            return widget;
    }

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.View#addToSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void addToSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.View#removeFromSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.View#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void setInSlot(Object slot, IsWidget content) {
	}

	
}
    
