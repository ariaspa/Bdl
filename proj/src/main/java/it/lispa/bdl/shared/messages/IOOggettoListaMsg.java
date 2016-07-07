package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface IOOggettoListaMsg.
 */
public interface IOOggettoListaMsg extends Messages{
	
	/**
	 * L title panel.
	 *
	 * @return string
	 */
	String lTitlePanel();
	
	/**
	 * L inserisci oggetto.
	 *
	 * @return string
	 */
	String lInserisciOggetto();
	
	/**
	 * L importa catalogo.
	 *
	 * @return string
	 */
	String lImportaCatalogo();
	
	/**
	 * L importa excel.
	 *
	 * @return string
	 */
	String lImportaExcel();
	
	/**
	 * L scarica template.
	 *
	 * @return string
	 */
	String lScaricaTemplate();
	
	/**
	 * L verifica.
	 *
	 * @return string
	 */
	String lVerifica();
	
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
	 * L verifica confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lVerificaConfirm(String arg0);
	
	/**
	 * L verifica all confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String lVerificaAllConfirm(Integer arg0);
	
	/**
	 * L verifica esito.
	 *
	 * @return string
	 */
	String lVerificaEsito();

	/**
	 * L oggetto sup in selection.
	 *
	 * @return string
	 */
	String lOggettoSupInSelection();
}
