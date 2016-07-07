package it.lispa.bdl.gxt.theme.custom.client.panel;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.base.client.widget.HeaderDefaultAppearance;
import com.sencha.gxt.theme.blue.client.panel.BlueHeaderAppearance.BlueHeaderStyle;

/**
 * Class CustomHeaderAppearance.
 */
public class CustomHeaderAppearance extends HeaderDefaultAppearance {

	  /**
  	 * Interface CustomHeaderStyle.
  	 */
  	public interface CustomHeaderStyle extends HeaderStyle {
	    String header();

	    String headerIcon();

	    String headerHasIcon();

	    String headerText();

	    String headerBar();
	  }

	  /**
  	 * Interface CustomHeaderResources.
  	 */
  	public interface CustomHeaderResources extends HeaderResources {

	    @Source({"com/sencha/gxt/theme/base/client/widget/Header.css", "CustomHeader.css"})
	    BlueHeaderStyle style();
	    
	   
	  }
	  

	  /**
  	 * Istanzia un nuovo custom header appearance.
  	 */
  	public CustomHeaderAppearance() {
	    this(GWT.<CustomHeaderResources> create(CustomHeaderResources.class),
	        GWT.<Template> create(Template.class));
	  }

	  /**
  	 * Istanzia un nuovo custom header appearance.
  	 *
  	 * @param resources  resources
  	 * @param template  template
  	 */
  	public CustomHeaderAppearance(HeaderResources resources, Template template) {
	    super(resources, template);
	  }
}
