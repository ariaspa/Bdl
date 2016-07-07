package it.lispa.bdl.client.utils;

import com.sencha.gxt.widget.core.client.box.MessageBox;

/**
 * Class GXTAlertBox.
 */
public class GXTAlertBox extends MessageBox {

	/** La Costante DO_SHOW. */
	public static final Boolean DO_SHOW = true;
	
	/** La Costante DO_NOT_SHOW. */
	public static final Boolean DO_NOT_SHOW = false;
			

	/**
	 * Istanzia un nuovo GXT alert box.
	 *
	 * @param title  title
	 * @param message  message
	 * @param doShow  do show
	 */
	public GXTAlertBox(String title, String message, Boolean doShow) {
		super(title, message);
		setIcon(ICONS.error());
		setPredefinedButtons(PredefinedButton.OK);
		if(doShow) {
			show();
		}
	}

}
