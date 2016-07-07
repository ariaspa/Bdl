package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface SchedulatoreTriggersMsg.
 */
public interface SchedulatoreTriggersMsg extends Messages{

	/**
	 * Title panel.
	 *
	 * @return string
	 */
	String titlePanel();
	
	/**
	 * L btn cancella.
	 *
	 * @return string
	 */
	String lBtnCancella();

	/**
	 * L nome utente.
	 *
	 * @return string
	 */
	String lNomeUtente();
	
	/**
	 * L data start.
	 *
	 * @return string
	 */
	String lDtStart();
	
	/**
	 * L data end.
	 *
	 * @return string
	 */
	String lDtEnd();
	
	/**
	 * L descrizione tipo.
	 *
	 * @return string
	 */
	String lDsTipo();
	
	/**
	 * L descrizione esito.
	 *
	 * @return string
	 */
	String lDsEsito();
	
	/**
	 * L descrizione input.
	 *
	 * @return string
	 */
	String lDsInput();
	
	/**
	 * L descrizione output.
	 *
	 * @return string
	 */
	String lDsOutput();
	
	/**
	 * L msg cancella confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lMsgCancellaConfirm(String arg0);
	
	/**
	 * L msg cancella all confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lMsgCancellaAllConfirm(Integer arg0);
	
	/**
	 * L msg cancella esito.
	 *
	 * @return string
	 */
	String lMsgCancellaEsito();
	
	
}
