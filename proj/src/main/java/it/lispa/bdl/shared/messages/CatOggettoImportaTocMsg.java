/*
 * 
 */
package it.lispa.bdl.shared.messages;
import com.google.gwt.i18n.client.Messages;

/**
 * Interface CatOggettoImportaExcelMsg.
 */
public interface CatOggettoImportaTocMsg extends Messages{

    /**
     * Info panel.
     *
     * @return string
     */
    String infoPanel();

    /**
     * Btn chiudi.
     *
     * @return string
     */
    String btnChiudi();
    
    /**
     * Btn scarica template.
     *
     * @return string
     */
    String btnScaricaTemplate();

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
