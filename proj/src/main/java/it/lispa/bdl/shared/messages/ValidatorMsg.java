package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface ValidatorMsg.
 */
public interface ValidatorMsg extends Messages{

	/**
	 * Email validation error.
	 *
	 * @return string
	 */
	String emailValidationError();
	
	/**
	 * Cf validation error.
	 *
	 * @return string
	 */
	String cfValidationError();

}
