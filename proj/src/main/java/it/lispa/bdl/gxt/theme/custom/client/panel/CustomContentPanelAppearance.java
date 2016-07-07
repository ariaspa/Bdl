package it.lispa.bdl.gxt.theme.custom.client.panel;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.base.client.panel.ContentPanelBaseAppearance;
import com.sencha.gxt.theme.base.client.widget.HeaderDefaultAppearance;

/**
 * Class CustomContentPanelAppearance.
 */
public class CustomContentPanelAppearance extends ContentPanelBaseAppearance {

	  /**
  	 * Interface CustomContentPanelResources.
  	 */
  	public interface CustomContentPanelResources extends ContentPanelResources {

	    @Source({"com/sencha/gxt/theme/base/client/panel/ContentPanel.css", "CustomContentPanel.css"})
	    @Override
	    CustomContentPanelStyle style();

	  }

	  /**
  	 * Interface CustomContentPanelStyle.
  	 */
  	public interface CustomContentPanelStyle extends ContentPanelStyle {

	  }

	  /**
  	 * Istanzia un nuovo custom content panel appearance.
  	 */
  	public CustomContentPanelAppearance() {
		  super(GWT.<CustomContentPanelResources> create(CustomContentPanelResources.class),
			        GWT.<ContentPanelTemplate> create(ContentPanelTemplate.class));
	  }

	  /**
  	 * Istanzia un nuovo custom content panel appearance.
  	 *
  	 * @param resources  resources
  	 */
  	public CustomContentPanelAppearance(CustomContentPanelResources resources) {
		  super(resources, GWT.<ContentPanelTemplate> create(ContentPanelTemplate.class));
	  }

	  /* (non-Javadoc)
  	 * @see com.sencha.gxt.widget.core.client.ContentPanel.ContentPanelAppearance#getHeaderAppearance()
  	 */
  	@Override
	  public HeaderDefaultAppearance getHeaderAppearance() {
	    return new CustomHeaderAppearance();
	  }
}