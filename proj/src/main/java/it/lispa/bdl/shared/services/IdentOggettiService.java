package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
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
 * Interface IdentOggettiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/identOggettiService")
public interface IdentOggettiService extends RemoteService {
	
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
	 * Legge oggetti superiori.
	 *
	 * @return oggetti superiori
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getOggettiSuperiori() throws AsyncServiceException;
	
	/**
	 * Legge tipologia oggetti.
	 *
	 * @return tipologia oggetti
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboDTO> getTipologiaOggetti() throws AsyncServiceException;

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
	 * Cancella oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void cancellaOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Legge tree items.
	 *
	 * @return tree items
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeItems() throws AsyncServiceException;

	/**
	 * Manda in verifica.
	 *
	 * @param gridItems  grid items
	 * @throws AsyncServiceException async service exception
	 */
	public void mandaInVerifica(List<VOggettoDTO> gridItems) throws AsyncServiceException;
	
	/**
	 * Importa excel.
	 *
	 * @param cdCollezione  codice collezione
	 * @throws AsyncServiceException async service exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void importaExcel(BigDecimal cdCollezione, String filename, Long filesize) throws AsyncServiceException, IOException;
	
	/**
	 * Legge tipologia oggetti con catalogo.
	 *
	 * @return tipologia oggetti con catalogo
	 * @throws AsyncServiceException async service exception
	 */
	public List<ComboStringDTO> getTipologiaOggettiConCatalogo() throws AsyncServiceException;
	
	/**
	 * Opac sbn import.
	 *
	 * @param cdCollezione  codice collezione
	 * @param items  items
	 * @throws AsyncServiceException async service exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void opacSbnImport(BigDecimal cdCollezione, List<UnimarcDTO> items) throws AsyncServiceException, IOException;
	
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
	public List<UnimarcDTO> opacSbnSearch(String sbn, String autore, String titolo, String editore, String annoPubblicazione) throws AsyncServiceException,  Exception;
	
	/**
	 * Controlla cartografia.
	 *
	 * @param cdTipoOgg  codice tipo ogg
	 * @return boolean
	 * @throws AsyncServiceException async service exception
	 */
	public Boolean hasCartografia(BigDecimal cdTipoOgg) throws AsyncServiceException;
}
