package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface VerificaOggettiMsg.
 */
public interface VerificaOggettiMsg extends Messages{

	/**
	 * Title panel.
	 *
	 * @return string
	 */
	String titlePanel();

	/**
	 * Btn lista visualizza.
	 *
	 * @return string
	 */
	String btnListaVisualizza();
	
	/**
	 * Btn lista action.
	 *
	 * @return string
	 */
	String btnListaAction();
	
	/**
	 * Btn item action.
	 *
	 * @return string
	 */
	String btnItemAction();
	
	/**
	 * Btn item annulla.
	 *
	 * @return string
	 */
	String btnItemAnnulla();
	
	/**
	 * Btn item rifiuta.
	 *
	 * @return string
	 */
	String btnItemRifiuta();
	
	/**
	 * Info panel.
	 *
	 * @return string
	 */
	String infoPanel();
	
	/**
	 * Esito ok.
	 *
	 * @return string
	 */
	String esitoOk();
	
	/**
	 * Action confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String actionConfirm(String arg0);
	
	/**
	 * Action all confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String actionAllConfirm(Integer arg0);
	
	
	/**
	 * Istituto.
	 *
	 * @return string
	 */
	String istituto();
	
	/**
	 * Progetto.
	 *
	 * @return string
	 */
	String progetto();
	
	/**
	 * Collezione.
	 *
	 * @return string
	 */
	String collezione();
	
	/**
	 * Titolo.
	 *
	 * @return string
	 */
	String titolo();
	
	/**
	 * Titolo.
	 *
	 * @return string
	 */
	String titoloFe();
	
	/**
	 * Titolo sup.
	 *
	 * @return string
	 */
	String titoloSup();
	
	/**
	 * Immagini.
	 *
	 * @return string
	 */
	String immagini();
	
	/**
	 * Tipologia oggetto.
	 *
	 * @return string
	 */
	String tipologiaOggetto();
	

	/**
	 * Rifiuta confirm.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String rifiutaConfirm(String arg0);
	
	/**
	 * Rifiuta ok.
	 *
	 * @return string
	 */
	String rifiutaOk();
}
