package it.lispa.bdl.gxt.theme.custom.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.core.client.resources.ThemeStyles.Styles;
import com.sencha.gxt.core.client.resources.ThemeStyles.ThemeAppearance;

/**
 * Class CustomThemeAppearance.
 */
public class CustomThemeAppearance implements ThemeAppearance {

	 static interface Bundle extends ClientBundle {

	    @Source({"com/sencha/gxt/theme/base/client/BaseTheme.css", "CustomTheme.css"})
	    Styles css();

	 }

	 private Bundle bundle;
	 private Styles style;

	  
	 /**
 	 * Istanzia un nuovo custom theme appearance.
 	 */
 	public CustomThemeAppearance() {
		   this.bundle = GWT.create(Bundle.class);
		   this.style = bundle.css();

		   StyleInjectorHelper.ensureInjected(this.style, true);
	}
	  
	/* (non-Javadoc)
	 * @see com.sencha.gxt.core.client.resources.ThemeStyles.ThemeAppearance#style()
	 */
	@Override
	public Styles style() {
	  return style;
	}

	 

	/* (non-Javadoc)
	 * @see com.sencha.gxt.core.client.resources.ThemeStyles.ThemeAppearance#moreIcon()
	 */
	@Override
	public ImageResource moreIcon() {
		return null;
	}

	  

}
