package it.lispa.bdl.shared.services;


import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface CatalogazioneOggettiServiceAsync.
 */
public interface CatalogazioneOggettiServiceAsync {

	void getTitoliInf(BigDecimal cdProgetto, AsyncCallback<List<ComboDTO>> callback);
	
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
	 * Torna oggetto ID.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 * @return oggetto
	 */
	void getOggettoId(BigDecimal cdOggetto, AsyncCallback<String> callback);

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
	 * Salva oggetto.
	 *
	 * @param oggettoDTO  oggettoDTO
	 * @param callback  callback
	 */
	void salvaOggettoForImportaCatalogo(OggettoDTO oggettoDTO, AsyncCallback<OggettoDTO> callback);
	
	/**
	 * Legge tree items.
	 *
	 * @param callback  callback
	 * @return tree items
	 */
	void getTreeItems(AsyncCallback<List<SubMenuItemDTO>> callback );
	
	/**
	 * Manda in validazione.
	 *
	 * @param gridItems  grid items
	 * @param asyncCallback  async callback
	 */
	void mandaInValidazione(List<VOggettoDTO> gridItems, AsyncCallback<Void> asyncCallback);

	/**
	 * Manda in validazione oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 */
	void mandaInValidazioneOggetto(BigDecimal cdOggetto, AsyncCallback<Void> asyncCallback);

	/**
	 * Legge autore.
	 *
	 * @param asyncCallback  async callback
	 * @return autore
	 */
	void getAutore(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge contesto archivistico.
	 *
	 * @param asyncCallback  async callback
	 * @return contesto archivistico
	 */
	void getContestoArchivistico(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return cronologia
	 */
	void getCronologia(BigDecimal cdOggetto,AsyncCallback<List<CronologiaDTO>> asyncCallback);
	
	/**
	 * Legge editore.
	 *
	 * @param asyncCallback  async callback
	 * @return editore
	 */
	void getEditore(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge lingua.
	 *
	 * @param asyncCallback  async callback
	 * @return lingua
	 */
	void getLingua(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge livello biblio.
	 *
	 * @param asyncCallback  async callback
	 * @return livello biblio
	 */
	void getLivelloBiblio(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge qualifica autore.
	 *
	 * @param asyncCallback  async callback
	 * @return qualifica autore
	 */
	void getQualificaAutore(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge qualificatore data.
	 *
	 * @param asyncCallback  async callback
	 * @return qualificatore data
	 */
	void getQualificatoreData(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge soggetto prod.
	 *
	 * @param asyncCallback  async callback
	 * @return soggetto prod
	 */
	void getSoggettoProd(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge soggetto.
	 *
	 * @param asyncCallback  async callback
	 * @return soggetto
	 */
	void getSoggetto(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge supporto.
	 *
	 * @param asyncCallback  async callback
	 * @return supporto
	 */
	void getSupporto(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge tecnica grafica.
	 *
	 * @param asyncCallback  async callback
	 * @return tecnica grafica
	 */
	void getTecnicaGrafica(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge tipo archivio.
	 *
	 * @param asyncCallback  async callback
	 * @return tipo archivio
	 */
	void getTipoArchivio(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge tipo grafica.
	 *
	 * @param asyncCallback  async callback
	 * @return tipo grafica
	 */
	void getTipoGrafica(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Legge tipo identificativo.
	 *
	 * @param asyncCallback  async callback
	 * @return tipo identificativo
	 */
	void getTipoIdentificativo(AsyncCallback<List<ComboDTO>> asyncCallback);
	
	/**
	 * Invia correzioni.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param correzione  correzione
	 * @param asyncCallback  async callback
	 */
	void inviaCorrezioni(BigDecimal cdOggetto, String correzione, AsyncCallback<Void> asyncCallback);
	
	/**
	 * Legge immagini oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return immagini oggetto
	 */
	void getImmaginiOggetto(BigDecimal cdOggetto, AsyncCallback<List<ImmagineDTO>> asyncCallback);
	
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return toc sommario oggetto
	 */
	void getTocSommarioOggetto(BigDecimal cdOggetto, AsyncCallback<List<TocSommarioDTO>> asyncCallback);
	
	/**
	 * Salva toc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param saveTreeSommarioData  save tree sommario data
	 * @param asyncCallback  async callback
	 */
	void salvaToc(BigDecimal cdOggettoOriginale, List<TocSommarioDTO> saveTreeSommarioData, AsyncCallback<Void> asyncCallback);
	
	/**
	 * Legge natura.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param callback  callback
	 * @return natura
	 */
	void getNatura(BigDecimal cdOggettoOriginale, AsyncCallback<String> callback);

	/**
	 * Importa excel.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param asyncCallback  async callback
	 */
	void importaExcel(BigDecimal cdTipoOggetto, AsyncCallback<Void> asyncCallback);
	/**
	 * Importa toc.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param asyncCallback  async callback
	 */
	void importaToc(AsyncCallback<Void> asyncCallback);
	
	/**
	 * Legge tipologia oggetti.
	 *
	 * @param callback  callback
	 * @return tipologia oggetti
	 */
	void getTipologiaOggetti(AsyncCallback<List<ComboDTO>> callback);

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
	 * Legge tipologia oggetti con catalogo.
	 *
	 * @param asyncCallback  async callback
	 * @return tipologia oggetti con catalogo
	 */
	void getTipologiaOggettiConCatalogo(AsyncCallback<List<ComboStringDTO>> asyncCallback);
	
	void opacSbnImport(BigDecimal cdCollezione, BigDecimal cdOggetto, UnimarcDTO item,
			AsyncCallback<Void> asyncCallback);
}
