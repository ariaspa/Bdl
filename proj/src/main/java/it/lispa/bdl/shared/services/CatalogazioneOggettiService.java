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
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface CatalogazioneOggettiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/catalogazioneOggettiService")
public interface CatalogazioneOggettiService extends RemoteService {
	
	public List<ComboDTO> getTitoliInf(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Legge progetto.
	 *
	 * @param cdProgetto  codice progetto
	 * @return progetto
	 * @throws AsyncServiceException async service exception
	 */
	public ProgettoDTO getProgetto(BigDecimal cdProgetto) throws AsyncServiceException;
	
	/**
	 * Legge collezione.
	 *
	 * @param cdCollezione  codice collezione
	 * @return collezione
	 * @throws AsyncServiceException async service exception
	 */
	public CollezioneDTO getCollezione(BigDecimal cdCollezione) throws AsyncServiceException;

	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public OggettoDTO getOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public String getOggettoId(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Salva progetto.
	 *
	 * @param progetto  progetto
	 * @return progetto dto
	 * @throws AsyncServiceException async service exception
	 */
	public ProgettoDTO salvaProgetto(ProgettoDTO progetto) throws AsyncServiceException;
	
	/**
	 * Salva collezione.
	 *
	 * @param collezione  collezione
	 * @return collezione dto
	 * @throws AsyncServiceException async service exception
	 */
	public CollezioneDTO salvaCollezione(CollezioneDTO collezione) throws AsyncServiceException;
	
	/**
	 * Salva oggetto.
	 *
	 * @param oggetto  oggetto
	 * @return oggetto dto
	 * @throws AsyncServiceException async service exception
	 */
	public OggettoDTO salvaOggetto(OggettoDTO oggetto) throws AsyncServiceException;
	
	/**
	 * Salva oggetto.
	 *
	 * @param oggettoDTO  oggettoDTO
	 * @return oggetto dto
	 * @throws AsyncServiceException async service exception
	 */
	public OggettoDTO salvaOggettoForImportaCatalogo(OggettoDTO oggettoDTO) throws AsyncServiceException;
	
	/**
	 * Legge tree items.
	 *
	 * @return tree items
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeItems() throws AsyncServiceException;

	/**
	 * Manda in validazione.
	 *
	 * @param gridItems  grid items
	 * @throws AsyncServiceException async service exception
	 */
	public void mandaInValidazione(List<VOggettoDTO> gridItems) throws AsyncServiceException;
	
	/**
	 * Manda in validazione oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void mandaInValidazioneOggetto(BigDecimal cdOggetto) throws AsyncServiceException;

	/**
	 * Legge autore.
	 *
	 * @return autore
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getAutore() throws AsyncServiceException;
	
	/**
	 * Legge contesto archivistico.
	 *
	 * @return contesto archivistico
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getContestoArchivistico() throws AsyncServiceException;
	
	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return cronologia
	 * @throws AsyncServiceException async service exception
	 */
	public List<CronologiaDTO> getCronologia(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Legge editore.
	 *
	 * @return editore
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getEditore() throws AsyncServiceException;
	
	/**
	 * Legge lingua.
	 *
	 * @return lingua
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getLingua() throws AsyncServiceException;
	
	/**
	 * Legge livello biblio.
	 *
	 * @return livello biblio
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getLivelloBiblio() throws AsyncServiceException;
	
	/**
	 * Legge qualifica autore.
	 *
	 * @return qualifica autore
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getQualificaAutore() throws AsyncServiceException;
	
	/**
	 * Legge qualificatore data.
	 *
	 * @return qualificatore data
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getQualificatoreData() throws AsyncServiceException;
	
	/**
	 * Legge soggetto prod.
	 *
	 * @return soggetto prod
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getSoggettoProd() throws AsyncServiceException;
	
	/**
	 * Legge soggetto.
	 *
	 * @return soggetto
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getSoggetto() throws AsyncServiceException;
	
	/**
	 * Legge supporto.
	 *
	 * @return supporto
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getSupporto() throws AsyncServiceException;
	
	/**
	 * Legge tecnica grafica.
	 *
	 * @return tecnica grafica
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTecnicaGrafica() throws AsyncServiceException;
	
	/**
	 * Legge tipo archivio.
	 *
	 * @return tipo archivio
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTipoArchivio() throws AsyncServiceException;
	
	/**
	 * Legge tipo grafica.
	 *
	 * @return tipo grafica
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTipoGrafica() throws AsyncServiceException;
	
	/**
	 * Legge tipo identificativo.
	 *
	 * @return tipo identificativo
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTipoIdentificativo() throws AsyncServiceException;
	
	/**
	 * Invia correzioni.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param correzione  correzione
	 * @throws AsyncServiceException async service exception
	 */
	public void inviaCorrezioni(BigDecimal cdOggetto, String correzione) throws AsyncServiceException;
	
	/**
	 * Legge immagini oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return immagini oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public List<ImmagineDTO> getImmaginiOggetto(BigDecimal cdOggetto)  throws AsyncServiceException;
	
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return toc sommario oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public List<TocSommarioDTO> getTocSommarioOggetto(BigDecimal cdOggetto)  throws AsyncServiceException;
	
	/**
	 * Salva toc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param saveTreeSommarioData  save tree sommario data
	 * @throws AsyncServiceException async service exception
	 */
	public void salvaToc(BigDecimal cdOggettoOriginale, List<TocSommarioDTO> saveTreeSommarioData) throws AsyncServiceException;
	
	/**
	 * Legge natura.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return natura
	 */
	public String getNatura(BigDecimal cdOggettoOriginale);

	/**
	 * Importa excel.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @throws AsyncServiceException async service exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void importaExcel(BigDecimal cdTipoOggetto) throws AsyncServiceException, IOException;
	/**
	 * Importa toc.
	 *
	 * @throws AsyncServiceException async service exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void importaToc() throws AsyncServiceException, IOException;
	
	/**
	 * Legge tipologia oggetti.
	 *
	 * @return tipologia oggetti
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTipologiaOggetti() throws AsyncServiceException;
	
	/**
	 * Opac sbn search.
	 *
	 * @param sbn  sbn
	 * @param autore  autore
	 * @param titolo  titolo
	 * @param editore  editore
	 * @param annoPubblicazione  anno pubblicazione
	 * @return list
	 * @throws AsyncServiceException async service exception
	 * @throws Exception exception
	 */
	public List<UnimarcDTO> opacSbnSearch(String sbn, String autore, String titolo, 
			String editore, String annoPubblicazione) throws AsyncServiceException, Exception;
	
	/**
	 * Legge tipologia oggetti con catalogo.
	 *
	 * @return tipologia oggetti con catalogo
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboStringDTO> getTipologiaOggettiConCatalogo() throws AsyncServiceException;
	
	public void opacSbnImport(BigDecimal cdCollezione, BigDecimal cdOggetto,
			UnimarcDTO item) throws AsyncServiceException, IOException;
}
