package it.lispa.bdl.client.validators;

import it.lispa.bdl.shared.messages.ValidatorMsg;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;


/**
 * Class CfValidator.
 */
public class CfValidator extends AbstractValidator<String>{
    private static ValidatorMsg validatorMessage = GWT.create(ValidatorMsg.class);


	/* (non-Javadoc)
	 * @see com.sencha.gxt.widget.core.client.form.Validator#validate(com.google.gwt.editor.client.Editor, java.lang.Object)
	 */
	@Override
    public List<EditorError> validate(Editor<String> field, String value) {
	      List<EditorError> errors = null;
	      if (value != null && (value.length()!=16)) {
	        String message = validatorMessage.cfValidationError();
	        errors = createError(field, message, value);
	      }
		  return errors;
	 }
	
	

}
