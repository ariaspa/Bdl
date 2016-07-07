package it.lispa.bdl.gxt.theme.custom.client.tree;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.sencha.gxt.theme.base.client.tree.TreeBaseAppearance;

/**
 * Class CustomTreeAppearance.
 */
public class CustomTreeAppearance extends TreeBaseAppearance {

	  /**
  	 * Interface CustomTreeResources.
  	 */
  	public interface CustomTreeResources extends TreeResources, ClientBundle {

	    @Source({"com/sencha/gxt/theme/base/client/tree/Tree.css", "TreeStyle.css"})
	    TreeBaseStyle style();
	  
	 }

	  /**
  	 * Istanzia un nuovo custom tree appearance.
  	 */
  	public CustomTreeAppearance() {
	      
		  super((TreeResources) GWT.create(CustomTreeResources.class));
	  }
}