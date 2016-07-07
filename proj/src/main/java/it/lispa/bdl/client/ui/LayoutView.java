package it.lispa.bdl.client.ui;



import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;

/**
 * Class LayoutView.
 */
public class LayoutView extends ViewImpl implements LayoutController.iLayoutView{

	private final Widget wdg;
    @UiField ContentPanel contentPanel;
    @UiField ContentPanel sidebarPanel;
    @UiField HTMLPanel headerPanel;
    @UiField HTMLPanel footerPanel;
    
    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, LayoutView> {
    }
    
    /**
     * Istanzia un nuovo layout view.
     *
     * @param binder  binder
     */
    @Inject
    public LayoutView(final Binder binder) {
    	wdg = binder.createAndBindUi(this);
    }
    
    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    public Widget asWidget() {
    	return wdg;
    }
	
    
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#addToSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void addToSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#removeFromSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
		 if(slot == LayoutController.SLOT_sidebar) {
	    	 sidebarPanel.remove(content);
	    	 
	    	 return;
	     }
		
		 super.removeFromSlot(slot, content);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if(slot == LayoutController.SLOT_header){
            headerPanel.clear();
            if(content != null){
                    headerPanel.add(content);
            }
            return;
		}
		
		if(slot == LayoutController.SLOT_footer){
            footerPanel.clear();
            if(content != null){
                    footerPanel.add(content);
            }
            return;
		}
		
		if(slot == LayoutController.SLOT_content){
             contentPanel.clear();
             if(content != null){
                     contentPanel.add(content);
                     contentPanel.forceLayout();
             }
             return;
	     }
	     if(slot == LayoutController.SLOT_sidebar){
	             sidebarPanel.clear();
	             if(content != null){
	                     sidebarPanel.add(content);
	             }
	             return;
	     }
	     
     	super.setInSlot(slot, content);
		
	}
	
}
