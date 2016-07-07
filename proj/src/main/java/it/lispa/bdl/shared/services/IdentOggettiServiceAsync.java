package it.lispa.bdl.shared.services;


import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface IdentOggettiServiceAsync.
 */
public interface IdentOggettiServiceAsync {

	/**
	 * Legge progetto.
	 *
	 * @param cdProgetto  codice progetto
	 * @param callback  callback
	 * @return progetto
	 */
	void getProgetto(BigDecimal cdProgetto, AsyncCallback<ProgettoDTO> callback);
	
	/**
	 * Legge collezione.
	 *
	 * @param cdCollezione  codice collezione
	 * @param callback  callback
	 * @return collezione
	 */
	void getCollezione(BigDecimal cdCollezione, AsyncCallback<CollezioneDTO> callback);
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 * @return oggetto
	 */
	void getOggetto(BigDecimal cdOggetto, AsyncCallback<OggettoDTO> callback);
	
	/**
	 * Legge oggetti superiori.
	 *
	 * @param callback  callback
	 * @return oggetti superiori
	 */
	void getOggettiSuperiori(AsyncCallback<List<ComboDTO>> callback);

	/**
	 * Legge tipologia oggetti.
	 *
	 * @param callback  callback
	 * @return tipologia oggetti
	 */
	void getTipologiaOggetti(AsyncCallback<List<ComboDTO>> callback);

	/**
	 * Salva progetto.
	 *
	 * @param progetto  progetto
	 * @param callback  callback
	 */
	void salvaProgetto(ProgettoDTO progetto, AsyncCallback<ProgettoDTO> callback);
	
	/**
	 * Salva collezione.
	 *
	 * @param collezione  collezione
	 * @param callback  callback
	 */
	void salvaCollezione(CollezioneDTO collezione, AsyncCallback<CollezioneDTO> callback);
	
	/**
	 * Salva oggetto.
	 *
	 * @param oggetto  oggetto
	 * @param callback  callback
	 */
	void salvaOggetto(OggettoDTO oggetto, AsyncCallback<OggettoDTO> callback);
	
	/**
	 * Cancella oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 */
	void cancellaOggetto(BigDecimal cdOggetto, AsyncCallback<Void> callback);
	
	/**
	 * Legge tree items.
	 *
	 * @param callback  callback
	 * @return tree items
	 */
	void getTreeItems(AsyncCallback<List<SubMenuItemDTO>> callback );
	
	/**
	 * Manda in verifica.
	 *
	 * @param gridItems  grid items
	 * @param asyncCallback  async callback
	 */
	void mandaInVerifica(List<VOggettoDTO> gridItems, AsyncCallback<Void> asyncCallback);
	
	/**
	 * Importa excel.
	 *
	 * @param cdCollezione  codice collezione
	 * @param asyncCallback  async callback
	 */
	void importaExcel(BigDecimal cdCollezione, String filename, Long filesize, AsyncCallback<Void> asyncCallback);
	
	/**
	 * Legge tipologia oggetti con catalogo.
	 *
	 * @param asyncCallback  async callback
	 * @return tipologia oggetti con catalogo
	 */
	void getTipologiaOggettiConCatalogo(AsyncCallback<List<ComboStringDTO>> asyncCallback);
	
	/**
	 * Opac sbn import.
	 *
	 * @param cdCollezione  codice collezione
	 * @param items  items
	 * @param asyncCallback  async callback
	 */
	void opacSbnImport(BigDecimal cdCollezione, List<UnimarcDTO> items, AsyncCallback<Void> asyncCallback);
	
	/**
	 * Opac sbn search.
	 *
	 * @param sbn  sbn
	 * @param autore  autore
	 * @param titolo  titolo
	 * @param editore  editore
	 * @param annoPubblicazione  anno pubblicazione
	 * @param asyncCallback  async callback
	 */
	void opacSbnSearch(String sbn, String autore, String titolo, String editore, String annoPubblicazione, AsyncCallback<List<UnimarcDTO>> asyncCallback);
	
	/**
	 * Controlla cartografia.
	 *
	 * @param cdTipoOgg  codice tipo ogg
	 * @param callback  callback
	 */
	void hasCartografia(BigDecimal cdTipoOgg, AsyncCallback<Boolean> callback);
}
