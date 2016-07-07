package it.lispa.bdl.client.utils;

import com.sencha.gxt.widget.core.client.box.MessageBox;

/**
 * Class GXTMessageBox.
 */
public class GXTMessageBox extends MessageBox {

	/** La Costante DO_SHOW. */
	public static final Boolean DO_SHOW = true;
	
	/** La Costante DO_NOT_SHOW. */
	public static final Boolean DO_NOT_SHOW = false;

	/**
	 * Istanzia un nuovo GXT message box.
	 *
	 * @param title  title
	 * @param message  message
	 * @param doShow  do show
	 */
	public GXTMessageBox(String title, String message, Boolean doShow) {
		super(title, message);
		setIcon(ICONS.info());
		setPredefinedButtons(PredefinedButton.OK);
		if(doShow) {
			show();
		}
	}

}
