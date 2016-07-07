package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface CaricamentoImmaginiMsg.
 */
public interface CaricamentoImmaginiMsg extends Messages{

	/**
	 * Title panel.
	 *
	 * @return string
	 */
	String titlePanel();

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
	 * Info panel.
	 *
	 * @return string
	 */
	String infoPanel();
	
	/**
	 * Error title panel.
	 *
	 * @return string
	 */
	String errorTitlePanel();
	
	/**
	 * L action.
	 *
	 * @return string
	 */
	String lAction();
	
	/**
	 * L action due.
	 *
	 * @return string
	 */
	String lActionDue();
	
	/**
	 * Id.
	 *
	 * @return string
	 */
	String id();
	
	/**
	 * Anomalia.
	 *
	 * @return string
	 */
	String anomalia();
	
	/**
	 * Titolo.
	 *
	 * @return string
	 */
	String titolo();
	
	/**
	 * TitoloFe.
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
	 * Immagini previste.
	 *
	 * @return string
	 */
	String immaginiPreviste();
	
	/**
	 * Immagini digitalizzate.
	 *
	 * @return string
	 */
	String immaginiDigitalizzate();
	
	/**
	 * Tipologia oggetto.
	 *
	 * @return string
	 */
	String tipologiaOggetto();

	/**
	 * Id item.
	 *
	 * @return string
	 */
	String idItem();
	
	/**
	 * Istituto item.
	 *
	 * @return string
	 */
	String istitutoItem();
	
	/**
	 * Progetto item.
	 *
	 * @return string
	 */
	String progettoItem();
	
	/**
	 * Collezione item.
	 *
	 * @return string
	 */
	String collezioneItem();
	
	/**
	 * Titolo item.
	 *
	 * @return string
	 */
	String titoloItem();
	
	/**
	 * TitoloFe item.
	 *
	 * @return string
	 */
	String titoloFeItem();
	
	/**
	 * Titolo sup item.
	 *
	 * @return string
	 */
	String titoloSupItem();
	
	/**
	 * Immagini previste item.
	 *
	 * @return string
	 */
	String immaginiPrevisteItem();
	
	/**
	 * Immagini digitalizzate item.
	 *
	 * @return string
	 */
	String immaginiDigitalizzateItem();
	
	/**
	 * Tipologia oggetto item.
	 *
	 * @return string
	 */
	String tipologiaOggettoItem();
	
	/**
	 * Log anomalia item.
	 *
	 * @return string
	 */
	String logAnomaliaItem();


	/**
	 * Title step2.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String titleStep3(String arg0);
	
	/**
	 * Content step2.
	 *
	 * @param arg0  arg0
	 * @param arg1  arg1
	 * @return string
	 */
	String contentStep3(String arg0, String arg1);
	
	/**
	 * Immagini digitalizzate step2.
	 *
	 * @return string
	 */
	String immaginiDigitalizzateStep3();
	
	/**
	 * Btn item step2 action.
	 *
	 * @return string
	 */
	String btnItemStep2Action();

	/**
	 * Esito ok.
	 *
	 * @return string
	 */
	String esitoOK();
	
	/**
	 * OK title panel.
	 *
	 * @return string
	 */
	String OKTitlePanel();
	
	/**
	 * Esito ko.
	 *
	 * @param arg0  arg0
	 * @return string
	 */
	String esitoKO(String arg0);

	////////////////////////////////////
	
	String titleStep2(String arg0);
	
	String contentStep2(String arg0);
	
	String isCreaImmaginiDerivateStep2();
	
	String yes();
	String no();
	
	String btnConfermaGenerazione();
	
	String errorSchedTitlePanel();
}
