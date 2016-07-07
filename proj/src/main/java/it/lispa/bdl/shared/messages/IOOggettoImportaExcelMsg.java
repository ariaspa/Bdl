package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface IOOggettoImportaExcelMsg.
 */
public interface IOOggettoImportaExcelMsg extends Messages{
	
	/**
	 * Info panel.
	 *
	 * @return string
	 */
	String infoPanel();

	/**
	 * Txt spiegazione.
	 *
	 * @return string
	 */
	String txtSpiegazione();
	
	/**
	 * Btn action.
	 *
	 * @return string
	 */
	String btnAction();
	
	/**
	 * Btn annulla.
	 *
	 * @return string
	 */
	String btnAnnulla();
	
	/**
	 * Btn indietro.
	 *
	 * @return string
	 */
	String btnIndietro();
	
	/**
	 * Btn chiudi.
	 *
	 * @return string
	 */
	String btnChiudi();

	/**
	 * Txt esito.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String txtEsito(String arg0);

	/**
	 * Esito ok.
	 *
	 * @return string
	 */
	String esitoOK();
	
	/**
	 * Esito ko.
	 *
	 * @return string
	 */
	String esitoKO();

	/**
	 * Action confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String actionConfirm(String arg0);

	
	
}
