package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface PubOggettoListaMsg.
 */
public interface PubOggettoListaMsg extends Messages{
	
	/**
	 * L title panel.
	 *
	 * @return string
	 */
	String lTitlePanel();
	
	/**
	 * L pubblica.
	 *
	 * @return string
	 */
	String lPubblica();
	
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
	 * L pubblica confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lPubblicaConfirm(String arg0);
	
	/**
	 * L pubblica all confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lPubblicaAllConfirm(Integer arg0);
	
	/**
	 * L pubblica esito.
	 *
	 * @return string
	 */
	String lPubblicaEsito();

	/**
	 * L oggetto sup in selection.
	 *
	 * @return string
	 */
	String lOggettoSupInSelection();
}
