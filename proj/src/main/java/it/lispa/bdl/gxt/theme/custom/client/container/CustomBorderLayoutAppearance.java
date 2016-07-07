package it.lispa.bdl.gxt.theme.custom.client.container;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.base.client.container.BorderLayoutBaseAppearance;

/**
 * Class CustomBorderLayoutAppearance.
 */
public class CustomBorderLayoutAppearance extends BorderLayoutBaseAppearance {

	  /**
  	 * Interface CustomBorderLayoutResources.
  	 */
  	public interface CustomBorderLayoutResources extends BorderLayoutResources {
	    
    	/* (non-Javadoc)
    	 * @see com.sencha.gxt.theme.base.client.container.BorderLayoutBaseAppearance.BorderLayoutResources#css()
    	 */
    	@Override
	    @Source({"com/sencha/gxt/theme/base/client/container/BorderLayout.css", "CustomBorderLayout.css"})
	    public CustomBorderLayoutStyle css();
	  }

	  /**
  	 * Interface CustomBorderLayoutStyle.
  	 */
  	public interface CustomBorderLayoutStyle extends BorderLayoutStyle {

	  }

	  /**
  	 * Istanzia un nuovo custom border layout appearance.
  	 */
  	public CustomBorderLayoutAppearance() {
	    super(GWT.<CustomBorderLayoutResources> create(CustomBorderLayoutResources.class));
	  }
}