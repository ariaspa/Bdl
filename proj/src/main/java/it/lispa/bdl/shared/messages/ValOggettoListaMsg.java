package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface ValOggettoListaMsg.
 */
public interface ValOggettoListaMsg extends Messages{
	
	/**
	 * L title panel.
	 *
	 * @return string
	 */
	String lTitlePanel();
	
	/**
	 * L valida.
	 *
	 * @return string
	 */
	String lValida();
	
	/**
	 * L visualizza.
	 *
	 * @return string
	 */
	String lVisualizza();
	
	/**
	 * L titolo.
	 *
	 * @return string
	 */
	String lTitolo();
	
	/**
	 * L titolo.
	 *
	 * @return string
	 */
	String lTitoloFe();
	
	/**
	 * L titolo sup.
	 *
	 * @return string
	 */
	String lTitoloSup();
	
	/**
	 * L immagini previste.
	 *
	 * @return string
	 */
	String lImmaginiPreviste();
	
	/**
	 * L tipologia oggetto.
	 *
	 * @return string
	 */
	String lTipologiaOggetto();
	
	/**
	 * L stato.
	 *
	 * @return string
	 */
	String lStato();

	/**
	 * L valida confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lValidaConfirm(String arg0);
	
	/**
	 * L valida all confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lValidaAllConfirm(Integer arg0);
	
	/**
	 * L valida esito.
	 *
	 * @return string
	 */
	String lValidaEsito();

	/**
	 * L oggetto sup in selection.
	 *
	 * @return string
	 */
	String lOggettoSupInSelection();
}
