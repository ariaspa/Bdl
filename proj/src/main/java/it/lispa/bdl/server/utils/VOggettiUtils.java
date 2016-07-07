/**
 * Created on Oct 18, 2011
 */
package it.lispa.bdl.server.utils;

import it.lispa.bdl.server.rest.dto.JSONBdlCollection;
import it.lispa.bdl.server.rest.dto.JSONBdlCollectionLight;
import it.lispa.bdl.server.rest.dto.JSONBdlInstitute;
import it.lispa.bdl.server.rest.dto.JSONBdlItem;
import it.lispa.bdl.server.rest.dto.JSONBdlRef;
import it.lispa.bdl.server.rest.dto.JSONBdlRefCount;
import it.lispa.bdl.server.rest.dto.JSONBdlSearchComparator;
import it.lispa.bdl.server.rest.dto.JSONBdlSearchFilter;
import it.lispa.bdl.server.utils.management.ManagementUtils;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * Class VOggettiUtils.
 */
@Repository
@Transactional
public class VOggettiUtils  {

	/** La Costante Q_ISTITUTI. */
	private final static String Q_ISTITUTI  = "SELECT I_CD_ISTITUTO,I_DN_NOME FROM V_OGGETTI ";
	
	/** La Costante G_ISTITUTI. */
	private final static String G_ISTITUTI  = " GROUP BY I_CD_ISTITUTO,I_DN_NOME ORDER BY I_DN_NOME";

	/** La Costante Q_PROGETTI. */
	private final static String Q_PROGETTI  = "SELECT P_CD_PROGETTO,P_DN_TITOLO FROM V_OGGETTI ";
	
	/** La Costante G_PROGETTI. */
	private final static String G_PROGETTI  = " GROUP BY P_CD_PROGETTO,P_DN_TITOLO ORDER BY P_DN_TITOLO";

	/** La Costante Q_COLLEZIONI. */
	private final static String Q_COLLEZIONI  = "SELECT C_CD_COLLEZIONE,C_DN_TITOLO FROM V_OGGETTI  ";
	
	/** La Costante Q_COLLEZIONI_COUNT. */
	private final static String Q_COLLEZIONI_COUNT = "SELECT COUNT(*) AS COUNT_VALUE, C_CD_COLLEZIONE, C_DN_TITOLO FROM V_OGGETTI";
	
	/** La Costante G_COLLEZIONI. */
	private final static String G_COLLEZIONI  = " GROUP BY C_CD_COLLEZIONE,C_DN_TITOLO  ORDER BY C_DN_TITOLO";
	
	/** La Costante G_COLLEZIONI_COUNT. */
	private final static String G_COLLEZIONI_COUNT  = " GROUP BY C_CD_COLLEZIONE, C_DN_TITOLO ORDER BY COUNT_VALUE DESC";
	
	/** La Costante Q_OGGETTI_COUNT. */
	private final static String Q_OGGETTI_COUNT  = "SELECT COUNT(O_CD_OGGETTO_ORIGINALE) AS COUNT_OGGETTI FROM V_OGGETTI ";

	/** La Costante Q_OGGETTI. */
	private final static String Q_OGGETTI  = "SELECT D_CD_DIGITALIZZATORE,D_DN_NOME,I_CD_ISTITUTO,I_DN_NOME,P_CD_PROGETTO,P_DN_TITOLO,P_DS_STATO,C_CD_COLLEZIONE,C_DN_TITOLO,O_CD_OGGETTO_ORIGINALE, "+
    	  "O_DN_TITOLO,O_DN_TITOLO_FE,O_DN_TITOLO_SUP,O_CD_OGGETTO_ORIGINALE_SUP,O_FL_OGGETTO_SUPERIORE,O_NR_OGGETTI_INFERIORI,T_CD_TIPO_OGGETTO,T_DN_NOME,O_FL_OGGETTO_DIGITALE,O_FL_CORREZIONE, "+
    	  "O_DS_NOTA_CORREZIONE,O_FL_ANOMALIA_IMMAGINI,O_DS_LOG_ANOMALIA,O_DS_STATO,O_DS_NOTE_NON_VALIDAZIONE,O_DS_NOTE_NON_PUBBLICAZIONE,O_NR_IMMAGINI_PREVISTE,O_NR_IMMAGINI_DIGITALIZZATE, " +
    	  "O_DS_CONTENUTISTICA, O_DS_FISICA, O_CD_LIVELLO_BIBLIO, O_DS_LIVELLO_BIBLIO, O_CD_SOGGETTO, O_DS_SOGGETTO, O_CD_AUTORE, O_DS_AUTORE, O_CD_QUALIFICA_AUTORE, " +
    	  "O_DS_QUALIFICA_AUTORE, O_CD_AUTORE_SEC, O_DS_AUTORE_SEC, O_CD_QUALIFICA_AUTORE_SEC, O_DS_QUALIFICA_AUTORE_SEC, O_CD_EDITORE, O_DS_EDITORE, O_CD_LINGUA, O_DS_LINGUA, " +
    	  "O_CD_TIPO_IDENTIFICATIVO, O_DS_TIPO_IDENTIFICATIVO, O_CD_IDENTIFICATIVO, O_CD_IDENTIFICATIVO_ISBN, O_CD_IDENTIFICATIVO_ISSN, O_DN_LUOGO_PUBBLICAZIONE, " +
    	  "O_CD_QUALIFICATORE_DATA, O_DS_QUALIFICATORE_DATA, O_DS_DATA, O_DS_NOTE, O_CD_SUPPORTO, O_DS_SUPPORTO, O_CD_TECNICA_GRAFICA, O_DS_TECNICA_GRAFICA, " +
    	  "O_CD_TIPO_GRAFICA, O_DS_TIPO_GRAFICA, O_DS_SCALA, O_DS_PROIEZIONE, O_DS_COORDINATE, O_CD_SOGGETTO_PROD, O_DS_SOGGETTO_PROD, O_CD_CONTESTO_ARCH, " +
    	  "O_DS_CONTESTO_ARCH, O_CD_TIPO_ARCHIVIO, O_DS_TIPO_ARCHIVIO, O_DS_LINK_CATALOGO, O_DS_DIRITTI, O_DS_ALTREINFO, O_FL_ISCRITICO, " +
    	  "O_DS_NOTACRITICO, O_DT_PUBBLICATOIL, O_NR_RILEVANZA, O_FL_PDF_MULTIPAGINA, O_CD_WORK_FILESYSTEM, O_FL_RICERCA_OCR, COV_CD_IMMAGINE, COV_DN_NOME_IMMAGINE, "+
    	  "COV_NR_PX_LARGHEZZA_THUMB, COV_NR_PX_ALTEZZA_THUMB, COV_NR_PX_LARGHEZZA_READER, "+
    	  "COV_NR_PX_ALTEZZA_READER, COV_NR_PX_LARGHEZZA_ZOOM, COV_NR_PX_ALTEZZA_ZOOM, O_SEGNATURA " +
	  "FROM V_OGGETTI ";
	
	private final static String Q_OGGETTI_PAGINAZIONE = "SELECT D_CD_DIGITALIZZATORE,D_DN_NOME,I_CD_ISTITUTO,I_DN_NOME,P_CD_PROGETTO,P_DN_TITOLO,P_DS_STATO,C_CD_COLLEZIONE,C_DN_TITOLO,O_CD_OGGETTO_ORIGINALE, "+
	    	  "O_DN_TITOLO,O_DN_TITOLO_FE,O_DN_TITOLO_SUP,O_CD_OGGETTO_ORIGINALE_SUP,O_FL_OGGETTO_SUPERIORE,O_NR_OGGETTI_INFERIORI,T_CD_TIPO_OGGETTO,T_DN_NOME,O_FL_OGGETTO_DIGITALE,O_FL_CORREZIONE, "+
	    	  "O_DS_NOTA_CORREZIONE,O_FL_ANOMALIA_IMMAGINI,O_DS_LOG_ANOMALIA,O_DS_STATO,O_DS_NOTE_NON_VALIDAZIONE,O_DS_NOTE_NON_PUBBLICAZIONE,O_NR_IMMAGINI_PREVISTE,O_NR_IMMAGINI_DIGITALIZZATE, " +
	    	  "O_DS_CONTENUTISTICA, O_DS_FISICA, O_CD_LIVELLO_BIBLIO, O_DS_LIVELLO_BIBLIO, O_CD_SOGGETTO, O_DS_SOGGETTO, O_CD_AUTORE, O_DS_AUTORE, O_CD_QUALIFICA_AUTORE, " +
	    	  "O_DS_QUALIFICA_AUTORE, O_CD_AUTORE_SEC, O_DS_AUTORE_SEC, O_CD_QUALIFICA_AUTORE_SEC, O_DS_QUALIFICA_AUTORE_SEC, O_CD_EDITORE, O_DS_EDITORE, O_CD_LINGUA, O_DS_LINGUA, " +
	    	  "O_CD_TIPO_IDENTIFICATIVO, O_DS_TIPO_IDENTIFICATIVO, O_CD_IDENTIFICATIVO, O_CD_IDENTIFICATIVO_ISBN, O_CD_IDENTIFICATIVO_ISSN, O_DN_LUOGO_PUBBLICAZIONE, " +
	    	  "O_CD_QUALIFICATORE_DATA, O_DS_QUALIFICATORE_DATA, O_DS_DATA, O_DS_NOTE, O_CD_SUPPORTO, O_DS_SUPPORTO, O_CD_TECNICA_GRAFICA, O_DS_TECNICA_GRAFICA, " +
	    	  "O_CD_TIPO_GRAFICA, O_DS_TIPO_GRAFICA, O_DS_SCALA, O_DS_PROIEZIONE, O_DS_COORDINATE, O_CD_SOGGETTO_PROD, O_DS_SOGGETTO_PROD, O_CD_CONTESTO_ARCH, " +
	    	  "O_DS_CONTESTO_ARCH, O_CD_TIPO_ARCHIVIO, O_DS_TIPO_ARCHIVIO, O_DS_LINK_CATALOGO, O_DS_DIRITTI, O_DS_ALTREINFO, O_FL_ISCRITICO, " +
	    	  "O_DS_NOTACRITICO, O_DT_PUBBLICATOIL, O_NR_RILEVANZA, O_FL_PDF_MULTIPAGINA, O_CD_WORK_FILESYSTEM, O_FL_RICERCA_OCR, COV_CD_IMMAGINE, COV_DN_NOME_IMMAGINE, "+
	    	  "COV_NR_PX_LARGHEZZA_THUMB, COV_NR_PX_ALTEZZA_THUMB, COV_NR_PX_LARGHEZZA_READER, "+
	    	  "COV_NR_PX_ALTEZZA_READER, COV_NR_PX_LARGHEZZA_ZOOM, COV_NR_PX_ALTEZZA_ZOOM, O_SEGNATURA, ROWNUM rN " +
		  "FROM V_OGGETTI ";
	
	/** La Costante G_OGGETTI. */
	private final static String G_OGGETTI = " ORDER BY O_DN_TITOLO";
	
	/** La Costante G_RILEVANZA. */
	private final static String G_RILEVANZA = " ORDER BY O_NR_RILEVANZA DESC, O_DN_TITOLO";

	/** La Costante W_INIT. */
	private final static String W_INIT = " WHERE ";
	
	/** La Costante W_O_DS_STATO. */
	private final static String W_O_DS_STATO = "O_DS_STATO=:statoOggetto";

	/** La Costante W_O_FL_OGGETTO_SUPERIORE. */
	private final static String W_O_FL_OGGETTO_SUPERIORE = "O_FL_OGGETTO_SUPERIORE=:flOggettoSuperiore";
	
	/** La Costante W_O_FL_OGGETTO_DIGITALE. */
	private final static String W_O_FL_OGGETTO_DIGITALE = "O_FL_OGGETTO_DIGITALE=:flOggettoDigitale";
	
	/** La Costante W_O_FL_CORREZIONE. */
	private final static String W_O_FL_CORREZIONE = "O_FL_CORREZIONE=:flCorrezione";
	
	/** La Costante W_O_FL_ANOMALIA_IMMAGINI. */
	private final static String W_O_FL_ANOMALIA_IMMAGINI = "O_FL_ANOMALIA_IMMAGINI=:flAnomaliaImmagini";
	
	/** La Costante W_O_FL_IS_CRITICO. */
	private final static String W_O_FL_IS_CRITICO = "O_FL_ISCRITICO=:flIsCritico";
	
	/** La Costante W_O_DS_STATO_BUILD. */
	private final static String W_O_DS_STATO_BUILD = "statoOggetto";
	
	/** em. */
	@PersistenceContext 
	EntityManager em;
	
	/** log. */
	private static Logger log = Logger.getLogger(VOggettiUtils.class);
	
	/**
	 * Find istituti.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findIstituti(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		Query q = em.createNativeQuery(Q_ISTITUTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) +  G_ISTITUTI);

		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_ISTITUTO);
	}
	
	/**
	 * Find istituti by enti abilitati.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param cdEntiAbilitati  codice enti abilitati
	 * @param flAmministratore  flag amministratore
	 * @return list
	 */
	public List<SubMenuItemDTO> findIstitutiByEntiAbilitati(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, List<BigDecimal> cdEntiAbilitati, boolean flAmministratore) {		
		String qString = Q_ISTITUTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		
		if(!flAmministratore) {
			qString += buildWhereOnCdEntiDisponibili(cdEntiAbilitati);
		}
		
		qString+= G_ISTITUTI;
		Query q = em.createNativeQuery(qString);

		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_ISTITUTO);
	}
	
	/**
	 * Find istituti by digitalizzatore.
	 *
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findIstitutiByDigitalizzatore(BigDecimal cdDigitalizzatore, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		Query q = em.createNativeQuery(Q_ISTITUTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + "AND D_CD_DIGITALIZZATORE=:cdDigitalizzatore " + G_ISTITUTI);

		q.setParameter("cdDigitalizzatore",cdDigitalizzatore);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_ISTITUTO);
	}

	/**
	 * Find progetti.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findProgetti(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		Query q = em.createNativeQuery(Q_PROGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag)   +  G_PROGETTI);

		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_PROGETTO);
	}

	/**
	 * Find progetto by istituto.
	 *
	 * @param cdProgetto  codice progetto
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return sub menu item dto
	 */
	public SubMenuItemDTO findProgettoByIstituto(BigDecimal cdProgetto, BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {
		
		Query q = em.createNativeQuery(Q_PROGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND P_CD_PROGETTO=:cdProgetto  AND I_CD_ISTITUTO=:cdIstituto "  +  G_PROGETTI);

		q.setParameter("cdProgetto",cdProgetto);
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		List<SubMenuItemDTO> risultati = mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_PROGETTO);
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}

	/**
	 * Find progetti by istituto.
	 *
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findProgettiByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto,Map<String,Boolean> filtriFlag) {
		
		Query q = em.createNativeQuery(Q_PROGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND I_CD_ISTITUTO=:cdIstituto "  +  G_PROGETTI);

		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_PROGETTO);
	}

	/**
	 * Find collezioni.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findCollezioni(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		Query q = em.createNativeQuery(Q_COLLEZIONI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) +  G_COLLEZIONI);

		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_COLLEZIONE);
	}

	/**
	 * Find collezione by istituto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return sub menu item dto
	 */
	public SubMenuItemDTO findCollezioneByIstituto(BigDecimal cdCollezione, BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {
		
		Query q = em.createNativeQuery( Q_COLLEZIONI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND C_CD_COLLEZIONE=:cdCollezione AND I_CD_ISTITUTO=:cdIstituto "  +  G_COLLEZIONI);

		q.setParameter("cdCollezione",cdCollezione);
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		List<SubMenuItemDTO> risultati = mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_COLLEZIONE);
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find collezione.
	 *
	 * @param cdCollezione  codice collezione
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return sub menu item dto
	 */
	public SubMenuItemDTO findCollezione(BigDecimal cdCollezione, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {
		
		Query q = em.createNativeQuery( Q_COLLEZIONI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND C_CD_COLLEZIONE=:cdCollezione "  +  G_COLLEZIONI);

		q.setParameter("cdCollezione",cdCollezione);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		List<SubMenuItemDTO> risultati = mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_COLLEZIONE);
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find collezioni by istituto.
	 *
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterProgetto  filter progetto
	 * @return list
	 */
	public List<SubMenuItemDTO> findCollezioniByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto) {		
		
		String qu = Q_COLLEZIONI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND I_CD_ISTITUTO=:cdIstituto " ;
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		qu += G_COLLEZIONI;
		
		Query q = em.createNativeQuery(qu);

		q.setParameter("cdIstituto",cdIstituto);
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_COLLEZIONE);
	}
	
	/**
	 * Find collezioni by progetto.
	 *
	 * @param cdProgetto  codice progetto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return list
	 */
	public List<SubMenuItemDTO> findCollezioniByProgetto(BigDecimal cdProgetto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		Query q = em.createNativeQuery(Q_COLLEZIONI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND P_CD_PROGETTO=:cdProgetto " +  G_COLLEZIONI);

		q.setParameter("cdProgetto",cdProgetto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToSubMenuItemDTO(q.getResultList(),SubMenuItemDTO.TIPO_COLLEZIONE);
	}

	/**
	 * Find oggetti.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return list
	 */
	public List<VOggettoDTO> findOggetti(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione) {		
		
		log.debug(ManagementUtils.ps+"[findOggetti] Start");
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		if(filterIstituto!=null){
			qu += " AND I_CD_ISTITUTO=:filterIstituto";
		}
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );

		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterIstituto!=null){
			q.setParameter("filterIstituto",filterIstituto);
		}
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		log.debug(ManagementUtils.ps+"[findOggetti] Req: q.getResultList()");
		List<?> result = q.getResultList();
		log.debug(ManagementUtils.ps+"[findOggetti] Req: q.getResultList()");

		log.debug(ManagementUtils.ps+"[findOggetti] Req: mapResultToVOggettoDTO()");
		List<VOggettoDTO> toRet = mapResultToVOggettoDTO(result);
		log.debug(ManagementUtils.ps+"[findOggetti] Req: mapResultToVOggettoDTO()");
		
		log.debug(ManagementUtils.ps+"[findOggetti] End");
		return toRet;
	}
	
	/**
	 * Find oggetti by enti abilitati.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param cdEntiAbilitati  codice enti abilitati
	 * @param flAmministratore  flag amministratore
	 * @return list
	 */
	public List<VOggettoDTO> findOggettiByEntiAbilitati(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<BigDecimal> cdEntiAbilitati, boolean flAmministratore) {		
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		if(filterIstituto!=null){
			qu += " AND I_CD_ISTITUTO=:filterIstituto";
		}
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		
		if(!flAmministratore) {
			qu += buildWhereOnCdEntiDisponibili(cdEntiAbilitati);
		}
		
		qu+= G_OGGETTI;
		
		Query q = em.createNativeQuery( qu );

		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterIstituto!=null){
			q.setParameter("filterIstituto",filterIstituto);
		}
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToVOggettoDTO(q.getResultList());
	}
	
	
	/**
	 * Find oggetto by digitalizzatore.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return v oggetto dto
	 */
	public VOggettoDTO findOggettoByDigitalizzatore(BigDecimal cdOggetto, BigDecimal cdDigitalizzatore, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		

		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_CD_OGGETTO_ORIGINALE=:cdOggetto ";
		qu += " AND D_CD_DIGITALIZZATORE=:cdDigitalizzatore ";
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );

		q.setParameter("cdOggetto", cdOggetto);
		q.setParameter("cdDigitalizzatore",cdDigitalizzatore);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);

		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find oggetti by digitalizzatore.
	 *
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return list
	 */
	public List<VOggettoDTO> findOggettiByDigitalizzatore(BigDecimal cdDigitalizzatore, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione) {		
		

		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND D_CD_DIGITALIZZATORE=:cdDigitalizzatore ";
		if(filterIstituto!=null){
			qu += " AND I_CD_ISTITUTO=:filterIstituto ";
		}
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto ";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione ";
		}
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );
		
		q.setParameter("cdDigitalizzatore",cdDigitalizzatore);
		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterIstituto!=null){
			q.setParameter("filterIstituto",filterIstituto);
		}
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToVOggettoDTO(q.getResultList());
	}

	/**
	 * Find oggetto by istituto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return v oggetto dto
	 */
	public VOggettoDTO findOggettoByIstituto(BigDecimal cdOggetto, BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_CD_OGGETTO_ORIGINALE=:cdOggetto ";
		qu += " AND I_CD_ISTITUTO=:cdIstituto ";
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );

		q.setParameter("cdOggetto", cdOggetto);
		q.setParameter("cdIstituto", cdIstituto);
		q.setParameter("statoProgetto", statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
				
		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find oggetto by codice.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return v oggetto dto
	 */
	public VOggettoDTO findOggettoByCodice(BigDecimal cdOggetto) {		
		return findOggettoByCodice(cdOggetto,null,null,null);
	}
	
	/**
	 * Find oggetto by codice.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return v oggetto dto
	 */
	public VOggettoDTO findOggettoByCodice(BigDecimal cdOggetto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_CD_OGGETTO_ORIGINALE=:cdOggetto ";
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );

		q.setParameter("cdOggetto", cdOggetto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
				
		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	public VOggettoDTO findOggettoByTitolo(String dnTitolo, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_DN_TITOLO=:dnTitolo ";
		qu += G_OGGETTI;
		Query q = em.createNativeQuery(qu);

		q.setParameter("dnTitolo", dnTitolo);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
				
		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	public VOggettoDTO findOggettoByTitoloFe(String dnTitoloFe, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {		
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_DN_TITOLO_FE=:dnTitoloFe ";
		qu += G_OGGETTI;
		Query q = em.createNativeQuery(qu);

		q.setParameter("dnTitoloFe", dnTitoloFe);
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
				
		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find oggetti by istituto.
	 *
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return list
	 */
	public List<VOggettoDTO> findOggettiByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione) {
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND I_CD_ISTITUTO=:cdIstituto ";
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );
		
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToVOggettoDTO(q.getResultList());
	}
	
	public List<VOggettoDTO> findMainOggettoByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, 
			Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione) {
		
		String qu = "SELECT * FROM(";
		qu += Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND I_CD_ISTITUTO=:cdIstituto ";
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		qu += " ORDER BY O_NR_RILEVANZA asc";
		qu += ") WHERE ROWNUM<=1";
		
		Query q = em.createNativeQuery(qu);
		
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToVOggettoDTO(q.getResultList());
	}
	
	/**
	 * Find titolo sup by istituto and titolo.
	 *
	 * @param titolo  titolo
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return v oggetto dto
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public VOggettoDTO findTitoloSupByIstitutoAndTitolo(String titolo, BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione) {
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_FL_OGGETTO_SUPERIORE='T' ";
		qu += " AND O_DN_TITOLO=:titolo ";
		qu += " AND I_CD_ISTITUTO=:cdIstituto ";
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );

		q.setParameter("titolo",titolo);
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);

		List<VOggettoDTO> risultati = mapResultToVOggettoDTO(q.getResultList());
		if(risultati.size()==1){
			return risultati.get(0);
		}
		return null;
	}
	
	/**
	 * Find titoli sup by istituto.
	 *
	 * @param cdIstituto  codice istituto
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return list
	 */
	public List<VOggettoDTO> findTitoliSupByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione) {
		
		String qu = Q_OGGETTI + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag);
		qu += " AND O_FL_OGGETTO_SUPERIORE='T' ";
		qu += " AND I_CD_ISTITUTO=:cdIstituto ";
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		qu += G_OGGETTI;
		Query q = em.createNativeQuery( qu );
		
		q.setParameter("cdIstituto",cdIstituto);
		buildParameterOnStatoProgetto(q, statoProgetto);
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		
		return mapResultToVOggettoDTO(q.getResultList());
	}

	/**
	 * Map result to sub menu item dto.
	 *
	 * @param result  result
	 * @param tipo  tipo
	 * @return list
	 */
	@SuppressWarnings({"rawtypes"})
	private List<SubMenuItemDTO> mapResultToSubMenuItemDTO(List result, String tipo){
		List<SubMenuItemDTO> items = new ArrayList<SubMenuItemDTO>();

		Iterator itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			SubMenuItemDTO item = new SubMenuItemDTO((BigDecimal)row[0],(String)row[1], tipo );
			items.add(item);
		}
		return items;
	}
	
	/**
	 * String as list.
	 *
	 * @param str  str
	 * @return list
	 */
	public List<String> stringAsList(String str){
		List<String> strList = new ArrayList<String>();
		strList.add(str);
		return strList;
	}
	
	/**
	 * Crea where on stato progetto.
	 *
	 * @param statoProgetto  stato progetto
	 * @return string
	 */
	private String buildWhereOnStatoProgetto(String statoProgetto){
		if(statoProgetto!=null){
			return " P_DS_STATO=:statoProgetto AND ";
		}else{
			return " P_DS_STATO=P_DS_STATO AND ";
		}
	}
	
	/**
	 * Crea where on stato oggetto.
	 *
	 * @param statiOggetto  stati oggetto
	 * @return string
	 */
	private String buildWhereOnStatoOggetto(List<String> statiOggetto){
		if(statiOggetto!=null && !statiOggetto.isEmpty()){
			String qPart = " (";
			List<String> filters = new ArrayList<String>();
			for(int i=0;i<statiOggetto.size();i++){
				filters.add(W_O_DS_STATO+(i+1));
			}
			qPart+=StringUtils.join(filters, " OR ");
			qPart+=") AND ";
			return qPart;
		}else{
			return " O_DS_STATO=O_DS_STATO AND ";
		}
	}
	
	
	/**
	 * Crea where on flag.
	 *
	 * @param filtriFlag  filtri flag
	 * @return string
	 */
	private String buildWhereOnFlag(Map<String,Boolean> filtriFlag){
		if(filtriFlag!=null && !filtriFlag.isEmpty()){
			String qPart = " (";
			
			List<String> filters = new ArrayList<String>();
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE)!=null){
				filters.add(W_O_FL_OGGETTO_SUPERIORE);
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE)!=null){
				filters.add(W_O_FL_OGGETTO_DIGITALE);
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE)!=null){
				filters.add(W_O_FL_CORREZIONE);
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI)!=null){
				filters.add(W_O_FL_ANOMALIA_IMMAGINI);
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO)!=null){
				filters.add(W_O_FL_IS_CRITICO);
			}
			
			qPart+=StringUtils.join(filters, " AND ");
			qPart+=") ";
			return qPart;
		}else{
			return " O_FL_OGGETTO_SUPERIORE=O_FL_OGGETTO_SUPERIORE ";
		}
	}
	
	/**
	 * Crea where on codice enti disponibili.
	 *
	 * @param cdEntiDisponibili  codice enti disponibili
	 * @return string
	 */
	private String buildWhereOnCdEntiDisponibili(List<BigDecimal> cdEntiDisponibili){
		StringBuilder strBuff = new StringBuilder();
		
		strBuff.append(" AND I_CD_ISTITUTO IN ( ");
		if(cdEntiDisponibili!=null && !cdEntiDisponibili.isEmpty()){
			for(int i=0; i<cdEntiDisponibili.size(); i++){
				BigDecimal tmpCdEnte = cdEntiDisponibili.get(i);
				if(i==0) {
					strBuff.append(" "+tmpCdEnte);
				} else {
					strBuff.append(" ,"+tmpCdEnte);
				}
			}
		}
		strBuff.append(" ) ");
		
		return strBuff.toString();
	}
	
	/**
	 * Crea parameter on stato oggetto.
	 *
	 * @param q  q
	 * @param statiOggetto  stati oggetto
	 */
	private void buildParameterOnStatoOggetto(Query q, List<String> statiOggetto){
		if(statiOggetto!=null && !statiOggetto.isEmpty()){
			for(int i=0;i<statiOggetto.size();i++){
				String statoOggetto = statiOggetto.get(i);
				q.setParameter("statoOggetto"+(i+1),statoOggetto);
			}
		}
	}
	
	/**
	 * Crea parameter on stato progetto.
	 *
	 * @param q  q
	 * @param statoProgetto  stato progetto
	 */
	private void buildParameterOnStatoProgetto(Query q, String statoProgetto){
		if(statoProgetto!=null){
			q.setParameter("statoProgetto",statoProgetto);
		}
	}
	
	/**
	 * Crea parameter on flag.
	 *
	 * @param q  q
	 * @param filtriFlag  filtri flag
	 */
	private void buildParameterOnFlag(Query q, Map<String,Boolean> filtriFlag){
		if(filtriFlag!=null && !filtriFlag.isEmpty()){
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE)!=null){
				if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE)){
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE,BdlSharedConstants.FLAG_TRUE);
				}else{
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE,BdlSharedConstants.FLAG_FALSE);
				}
				
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE)!=null){
				if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE)){
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE,BdlSharedConstants.FLAG_TRUE);
				}else{
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_DIGITALE,BdlSharedConstants.FLAG_FALSE);
				}
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE)!=null){
				if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE)){
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE,BdlSharedConstants.FLAG_TRUE);
				}else{
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE,BdlSharedConstants.FLAG_FALSE);
				}
			}
			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI)!=null){
				if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI)){
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI,BdlSharedConstants.FLAG_TRUE);
				}else{
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI,BdlSharedConstants.FLAG_FALSE);
				}
			}

			if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO)!=null){
				if(filtriFlag.get(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO)){
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO,BdlSharedConstants.FLAG_TRUE);
				}else{
					q.setParameter(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO,BdlSharedConstants.FLAG_FALSE);
				}
			}
		}
	}
	
	/**
	 * Grid apply filters sorting pagination.
	 *
	 * @param items  items
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @return paging load result bean
	 */
	public PagingLoadResultBean<VOggettoDTO> gridApplyFiltersSortingPagination(List<VOggettoDTO> items, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		String orderFieldDefault = "O_DnTitolo";
		String orderDirDefault = "asc";
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
	}
	

	/**
	 * Find conteggio per stato.
	 *
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param cdEntiAbilitati  codice enti abilitati
	 * @param flAmministratore  flag amministratore
	 * @return list
	 */
	public List<VOggettoCountDTO> findConteggioPerStato(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<BigDecimal> cdEntiAbilitati, boolean flAmministratore) {		
		String qu = "SELECT " +
				    "O_DS_STATO4MONITOR as STATO , " +
				    "COUNT(*) AS TOT_OGGETTI, " +
				    "SUM(O_NR_IMMAGINI_PREVISTE) TOT_IMMAGINI_PREVISTE, " +
				    "SUM(O_NR_IMMAGINI_DIGITALIZZATE) AS TOT_IMMAGINI_DIGITALIZZATE " +
				    "FROM V_OGGETTI " +
				    "WHERE O_DS_STATO=O_DS_STATO ";
		if(filterIstituto!=null){
			qu += " AND I_CD_ISTITUTO=:filterIstituto";
		}
		if(filterProgetto!=null){
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		if(filterCollezione!=null){
			qu += " AND C_CD_COLLEZIONE=:filterCollezione";
		}
		
		if(!flAmministratore) {
			qu+= buildWhereOnCdEntiDisponibili(cdEntiAbilitati);
		}
		
		qu += " GROUP BY O_DS_STATO4MONITOR";
		Query q = em.createNativeQuery( qu );

		if(filterIstituto!=null){
			q.setParameter("filterIstituto",filterIstituto);
		}
		if(filterProgetto!=null){
			q.setParameter("filterProgetto",filterProgetto);
		}
		if(filterCollezione!=null){
			q.setParameter("filterCollezione",filterCollezione);
		}
		
		
		List<VOggettoCountDTO> items = mapResultToVOggettoCountDTO(q.getResultList());
		
		List<String> stati = new ArrayList<String>();
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE+"|"+BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE+"|"+BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO+"|"+BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO+"|"+BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO);
		stati.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO);
		

		List<VOggettoCountDTO> newItems = new ArrayList<VOggettoCountDTO>();
		for(int i=0;i<stati.size();i++){
			String stato = stati.get(i);
			VOggettoCountDTO item = getItemByStatoFlag(stato,items);
			if(item == null){
				item = new VOggettoCountDTO(stato,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
			}
			newItems.add(item);
		}
		
		return newItems;
	}
	
	/**
	 * Map result to v oggetto count dto.
	 *
	 * @param result  result
	 * @return list
	 */
	@SuppressWarnings({"rawtypes"})
	private List<VOggettoCountDTO> mapResultToVOggettoCountDTO(List result){
		List<VOggettoCountDTO> items = new ArrayList<VOggettoCountDTO>();

		Iterator itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			if(row[1]==null){
				row[1] = BigDecimal.ZERO;
			}
			if(row[2]==null){
				row[2] = BigDecimal.ZERO;
			}
			if(row[3]==null){
				row[3] = BigDecimal.ZERO;
			}
			VOggettoCountDTO item = new VOggettoCountDTO(
					(String) row[0],
					(BigDecimal) row[1],
					(BigDecimal) row[2],
					(BigDecimal) row[3]
			);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * Map result to v oggetto dto.
	 *
	 * @param result  result
	 * @return list
	 */
	@SuppressWarnings({"rawtypes"})
	private List<VOggettoDTO> mapResultToVOggettoDTO(List result){
		log.debug("[mapResultToVOggettoDTO] Parsing "+result.size()+" results");
		List<VOggettoDTO> items = new ArrayList<VOggettoDTO>();
		
		Iterator itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			VOggettoDTO item = new VOggettoDTO(
					(BigDecimal) row[0],
					(String) row[1],
					(BigDecimal) row[2],
					(String) row[3],
					(BigDecimal) row[4],
					(String) row[5],
					(String) row[6],
					(BigDecimal) row[7],
					(String) row[8],
					(BigDecimal) row[9],
					(String) row[10],
					(String) row[11],
					(String) row[12],
					(BigDecimal) row[13],
					(Character) row[14],
					(BigDecimal) row[15],
					(BigDecimal) row[16],
					(String) row[17],
					(Character) row[18],
					(Character) row[19],
					(String) row[20],
					(Character) row[21],
					(String) row[22],
					(String) row[23],
					(String) row[24],
					(String) row[25],
					(BigDecimal) row[26],
					(BigDecimal) row[27],
					(String) row[28],
					(String) row[29],
					(BigDecimal) row[30],
					(String) row[31],
					(BigDecimal) row[32],
					(String) row[33],
					(BigDecimal) row[34],
					(String) row[35],
					(BigDecimal) row[36],
					(String) row[37],
					(BigDecimal) row[38],
					(String) row[39],
					(BigDecimal) row[40],
					(String) row[41],
					(BigDecimal) row[42],
					(String) row[43],
					(BigDecimal) row[44],
					(String) row[45],
					(BigDecimal) row[46],
					(String) row[47],
					(String) row[48],
					(String) row[49],
					(String) row[50],
					(String) row[51],
					(BigDecimal) row[52],
					(String) row[53],
					(String) row[54],
					(String) row[55],
					(BigDecimal) row[56],
					(String) row[57],
					(BigDecimal) row[58],
					(String) row[59],
					(BigDecimal) row[60],
					(String) row[61],
					(String) row[62],
					(String) row[63],
					(String) row[64],
					(BigDecimal) row[65],
					(String) row[66],
					(BigDecimal) row[67],
					(String) row[68],
					(BigDecimal) row[69],
					(String) row[70],
					(String) row[71],
					(String) row[72],
					(String) row[73],
					(Character) row[74],
					(String) row[75],
					row[76]!=null ? new Date(((Timestamp) row[76]).getTime()) : null,
					(BigDecimal) row[77],
					(Character) row[78],
					(BigDecimal) row[79],
					(Character) row[80],
					(BigDecimal) row[81],
					(String) row[82],
					(BigDecimal) row[83],
					(BigDecimal) row[84],
					(BigDecimal) row[85],
					(BigDecimal) row[86],
					(BigDecimal) row[87],
					(BigDecimal) row[88]					
			);
			item.setO_Segnatura((String) row[89]);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * Legge item by stato flag.
	 *
	 * @param statoFlag  stato flag
	 * @param items  items
	 * @return item by stato flag
	 */
	private VOggettoCountDTO getItemByStatoFlag(String statoFlag, List<VOggettoCountDTO> items){
		VOggettoCountDTO item = null;
		
		for(int i=0;i<items.size();i++){
			if(items.get(i).getStatoFlag().equals(statoFlag)){
				item = items.get(i);
				break;
			}
		}
		return item;
	}
	
	/**
	 * Find ambiti disciplinari for json.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	public List<SubMenuItemDTO> findAmbitiDisciplinariForJson(String statoOggetto) {
		Query q = em.createNativeQuery("SELECT DISTINCT(c.DS_AMBITO_DISCIPLINARE) FROM BDL_COLLEZIONE c WHERE c.CD_COLLEZIONE IN ("+
				"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE :statoOggetto)");
		q.setParameter("statoOggetto",statoOggetto);
		List result = q.getResultList();

		List<SubMenuItemDTO> items = new ArrayList<SubMenuItemDTO>();
		
		Iterator itr = result.iterator();
		while(itr.hasNext()) {
			Object row = (Object) itr.next();
			String ambitoDisciplinare = (String) row;
			
			Query subQuery = em.createNativeQuery("SELECT c.CD_COLLEZIONE FROM BDL_COLLEZIONE c WHERE c.DS_AMBITO_DISCIPLINARE = :ambitoDisciplinare AND c.CD_COLLEZIONE IN ("+
					"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE :statoOggetto)");
			subQuery.setParameter("statoOggetto",statoOggetto);
			subQuery.setParameter("ambitoDisciplinare",ambitoDisciplinare);
			List subResult = subQuery.getResultList();
			
			Iterator subItr = subResult.iterator();
			while(subItr.hasNext()) {
				Object subRow = (Object) subItr.next();
				SubMenuItemDTO item = new SubMenuItemDTO((BigDecimal) subRow, ambitoDisciplinare, SubMenuItemDTO.TIPO_COLLEZIONE);
				items.add(item);
				break;
			}
		}
		return items;
	}
	
	/**
	 * Find tipi oggetto for json.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	public List<SubMenuItemDTO> findTipiOggettoForJson(String statoOggetto) {
		Query q = em.createNativeQuery("SELECT c.CD_TIPO_OGGETTO, c.DN_NOME FROM BDL_TIPO_OGGETTO c WHERE c.CD_TIPO_OGGETTO IN ("+
				"SELECT DISTINCT(vOO.T_CD_TIPO_OGGETTO) FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE :statoOggetto)");
		q.setParameter("statoOggetto",statoOggetto);
		List result = q.getResultList();

		List<SubMenuItemDTO> items = new ArrayList<SubMenuItemDTO>();
		
		Iterator itr = result.iterator();
		while(itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			SubMenuItemDTO item = new SubMenuItemDTO((BigDecimal) row[0], (String) row[1], SubMenuItemDTO.TIPO_OGGETTO);
			items.add(item);
		}
		return items;
	}

	private String jsonBuildQueryFilter(List<JSONBdlSearchFilter> filters) {
		/* Usato per evitare sovrapposizioni dell'indice dato a buildComparisonParamName() */
		final Integer FATTORE_MOLTIPLICATIVO = 10000;
		
		String qFilter = "";
		if(filters!=null) {
			log.debug("[jsonBuildQuery] filters non e' nullo");
			Collections.sort(filters);
		
			for(int i=1;i<=filters.size();i++){
				JSONBdlSearchFilter filter = filters.get(i-1);
				log.debug("[jsonBuildQuery] Tratto il filtro "+i);
				log.debug("[jsonBuildQuery] filter.getComparisonOperator()="+filter.getComparisonOperator());
				log.debug("[jsonBuildQuery] filter.getLogicalOperator()="+filter.getLogicalOperator());
				log.debug("[jsonBuildQuery] filter.getFilterType()="+filter.getFilterType());
	
				if(!(Arrays.asList(BdlServerConstants.JSON_COMPARISONOPS).contains(filter.getComparisonOperator()))){
					filter.setComparisonOperator(BdlServerConstants.JSON_COMPARISONOP_LIKE);
				}
				if(!(Arrays.asList(BdlServerConstants.JSON_LOGICALOPS).contains(filter.getLogicalOperator()))){
					filter.setLogicalOperator(BdlServerConstants.JSON_LOGICALOP_AND);
				}
				if(!(Arrays.asList(BdlServerConstants.JSON_FILTERTYPES).contains(filter.getFilterType()))){
					filter.setFilterType(BdlServerConstants.JSON_FILTERTYPE_TITLE);
				}
				if(i!=1){
					// la query viene composta con la logica del primo ordine
					String logOp = "";
					if(filter.getLogicalOperator().equals(BdlServerConstants.JSON_LOGICALOP_OR)){
						logOp = BdlServerConstants.JSON_LOGICALOPDB_OR;
					}else if(filter.getLogicalOperator().equals(BdlServerConstants.JSON_LOGICALOP_ANDNOT)){
						logOp = BdlServerConstants.JSON_LOGICALOPDB_ANDNOT;
					}else{
						logOp = BdlServerConstants.JSON_LOGICALOPDB_AND;
					}

					qFilter += " "+logOp+" ";
				}
				
				if(BdlServerConstants.JSON_FILTERTYPE_TYPE.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("T_CD_TIPO_OGGETTO", "T_DN_NOME");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_TITLE.equals(filter.getFilterType())) {
					/* TitoloFE */
					qFilter += "(";
					qFilter += filter.buildFieldName("O_DN_TITOLO_FE", "O_DN_TITOLO_FE");
					qFilter += filter.buildComparisonParamName(i);
					

					qFilter += " OR ( O_DS_LIVELLO_BIBLIO='Monografia' AND O_CD_OGGETTO_ORIGINALE_SUP IS NOT NULL AND ";
					qFilter += filter.buildFieldName("O_DN_TITOLO_SUP", "O_DN_TITOLO_SUP");
					qFilter += filter.buildComparisonParamName(i);
					qFilter += " )";
					
					qFilter += ")";
					
				} else if(BdlServerConstants.JSON_FILTERTYPE_DATE.equals(filter.getFilterType())) {
					if(BdlServerConstants.JSON_COMPARISONOP_EQUALID.equals(filter.getComparisonOperator())){
						qFilter += "O_CD_QUALIFICATORE_DATA=:DATEQUALIFIER AND ";	
					}				
					qFilter += "O_DS_DATA";
					qFilter += filter.buildComparisonParamName(i);
	 			} else if(BdlServerConstants.JSON_FILTERTYPE_PUBLISHER.equals(filter.getFilterType())) {
	 				qFilter += filter.buildFieldName("O_CD_EDITORE", "O_DS_EDITORE");
	 				qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_LANG.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_LINGUA", "O_DS_LINGUA");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_COLLECTION.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("C_CD_COLLEZIONE", "C_DN_TITOLO");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_KIND.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_LIVELLO_BIBLIO", "O_DS_LIVELLO_BIBLIO");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_INSTITUTE.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("I_CD_ISTITUTO", "I_DN_NOME");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_GRAPHICMATERIAL.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_TIPO_GRAFICA", "O_DS_TIPO_GRAFICA");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_SUPPORT.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_SUPPORTO", "O_DS_SUPPORTO");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_TECNIQUE.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_TECNICA_GRAFICA", "O_DS_TECNICA_GRAFICA");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_AUTHOR.equals(filter.getFilterType())) {
					qFilter += " ( ";
					qFilter += filter.buildFieldName("O_CD_AUTORE", "O_DS_AUTORE");
					qFilter += filter.buildComparisonParamName(i);
					qFilter += " OR ";
					qFilter += filter.buildFieldName("O_CD_AUTORE_SEC", "O_DS_AUTORE_SEC");
					qFilter += filter.buildComparisonParamName(i*FATTORE_MOLTIPLICATIVO);
					qFilter += " ) ";
				} else if(BdlServerConstants.JSON_FILTERTYPE_SUBJECT.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_CD_SOGGETTO", "O_DS_SOGGETTO");
					qFilter += filter.buildComparisonParamName(i);
				} else if(BdlServerConstants.JSON_FILTERTYPE_SEGNATURA.equals(filter.getFilterType())) {
					qFilter += filter.buildFieldName("O_SEGNATURA", "O_SEGNATURA");
					qFilter += filter.buildComparisonParamName(i);
				}
	
			}
		}
		return qFilter;
	}
	
	/**
	 * Json build query.
	 *
	 * @param filters  filters
	 * @param statiOggetto  stati oggetto
	 * @return string
	 */
	private String jsonBuildQuery(List<JSONBdlSearchFilter> filters, List<String> statiOggetto){
		log.debug("[jsonBuildQuery] Entro ");
		String qu = Q_OGGETTI + W_INIT;
		qu += buildWhereOnStatoOggetto(statiOggetto);
		qu += getFiltroFigli(null)+" AND ";
		String qFilter = jsonBuildQueryFilter(filters);		
		if(!qFilter.equals(""))
			qu += " ("+qFilter+") ";
		else
			qu += " O_DN_TITOLO_FE=O_DN_TITOLO_FE ";
		qu += G_RILEVANZA;
		log.debug("[jsonBuildQuery] Ritorno: "+qu);
		return qu;
	}
	private String jsonBuildCountQuery(List<JSONBdlSearchFilter> filters, List<String> statiOggetto){
		log.debug("[jsonBuildCountQuery] Entro ");
		String qu = Q_OGGETTI_COUNT + W_INIT;
		qu += buildWhereOnStatoOggetto(statiOggetto);
		qu += getFiltroFigli(null)+" AND ";
		String qFilter = jsonBuildQueryFilter(filters);		
		if(!qFilter.equals(""))
			qu += " ("+qFilter+") ";
		else
			qu += " O_DN_TITOLO_FE=O_DN_TITOLO_FE ";
		qu += G_RILEVANZA;
		log.debug("[jsonBuildCountQuery] Ritorno: "+qu);
		return qu;
	}
	private String jsonBuildPaginazioneQuery(List<JSONBdlSearchFilter> filters, List<String> statiOggetto){
		log.debug("[jsonBuildPaginazioneQuery] Entro ");
		String qu = Q_OGGETTI_PAGINAZIONE + W_INIT;
		qu += buildWhereOnStatoOggetto(statiOggetto);
		qu += getFiltroFigli(null)+" AND ";
		String qFilter = jsonBuildQueryFilter(filters);		
		if(!qFilter.equals(""))
			qu += " ("+qFilter+") ";
		else
			qu += " O_DN_TITOLO_FE=O_DN_TITOLO_FE ";
		qu += G_RILEVANZA;
		log.debug("[jsonBuildPaginazioneQuery] Ritorno: "+qu);
		return qu;
	}
	
	/**
	 * Json build parameters.
	 *
	 * @param filters  filters
	 * @return map
	 */
	private Map<String,Object> jsonBuildParameters(List<JSONBdlSearchFilter> filters){
		log.debug("[jsonBuildParameters] Entro nel metodo");
		/* Usato per evitare sovrapposizioni dell'indice dato a buildComparisonParamValue() */
		final Integer FATTORE_MOLTIPLICATIVO = 10000;
		
		Map<String,Object> toRet = new HashMap<String , Object>();
		if(filters!=null){
			Collections.sort(filters);
			
			for(int i=1;i<=filters.size();i++){
				JSONBdlSearchFilter filter = filters.get(i-1);
				if(BdlServerConstants.JSON_FILTERTYPE_TYPE.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));				
				} else if(BdlServerConstants.JSON_FILTERTYPE_TITLE.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_DATE.equals(filter.getFilterType())) {
					if(BdlServerConstants.JSON_COMPARISONOP_EQUALID.equals(filter.getComparisonOperator())){
						String cmpVal = filter.getComparisonValues().get(filter.getComparisonValues().size()-1).getComparisonValue();
						BigDecimal cmpRealVal = null;
						
						/* Non avendo una mappatura precisa, la soluzione 
						 * più realistica è questa mappatura punto-punto */
						if(cmpVal.equals("SECOLO")){
							cmpRealVal=new BigDecimal(1);
						}else if(cmpVal.equals("RANGE")){
							cmpRealVal=new BigDecimal(2);
						}else if(cmpVal.equals("CIRCA")){
							cmpRealVal=new BigDecimal(3);
						}else{
							cmpRealVal=new BigDecimal(4);
						}
						toRet.put("DATEQUALIFIER",cmpRealVal);
					}
					toRet.putAll(filter.buildComparisonParamValue(i));
	 			} else if(BdlServerConstants.JSON_FILTERTYPE_PUBLISHER.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_LANG.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_COLLECTION.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_KIND.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_INSTITUTE.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_GRAPHICMATERIAL.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_SUPPORT.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_TECNIQUE.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_AUTHOR.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
					toRet.putAll(filter.buildComparisonParamValue(i*FATTORE_MOLTIPLICATIVO));
				} else if(BdlServerConstants.JSON_FILTERTYPE_SUBJECT.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				} else if(BdlServerConstants.JSON_FILTERTYPE_SEGNATURA.equals(filter.getFilterType())) {
					toRet.putAll(filter.buildComparisonParamValue(i));
				}			
			}
		}
		
		log.debug("[jsonBuildParameters] Ritorno "+toRet.toString());
		return toRet;
	}

	/**
	 * Json search.
	 *
	 * @param filters  filters
	 * @param statiOggetto  stati oggetto
	 * @return list
	 */
	public List<VOggettoDTO> jsonSearch(List<JSONBdlSearchFilter> filters, List<String> statiOggetto) {
		log.debug("[jsonSearch] Entro nel metodo");
		
		String qu = jsonBuildQuery(filters, statiOggetto);

		Map<String,Object> params = jsonBuildParameters(filters);
		
		Query q = em.createNativeQuery(qu);
		
		if(statiOggetto!=null && !statiOggetto.isEmpty()){
			for(int i=0; i<statiOggetto.size(); i++){
				q.setParameter(W_O_DS_STATO_BUILD+(i+1), statiOggetto.get(i));
			}
		}
		
		for(Map.Entry<String,Object> myEntry : params.entrySet()) {
			String myKey 	= myEntry.getKey();
			Object myValue  = myEntry.getValue();
			q.setParameter(myKey, myValue);
		}

		log.debug("[jsonSearch] Lancio la getResultList ed esco");
		return mapResultToVOggettoDTO(q.getResultList());
	}

	public BigDecimal jsonSearchCount(List<JSONBdlSearchFilter> filters, List<String> statiOggetto) {
		log.debug("[jsonSearchCount] Entro");
		
		String qu = jsonBuildCountQuery(filters, statiOggetto);
		
		Map<String,Object> params = jsonBuildParameters(filters);
		
		Query q = em.createNativeQuery(qu);
		
		if(statiOggetto!=null && !statiOggetto.isEmpty()){
			for(int i=0; i<statiOggetto.size(); i++){
				q.setParameter(W_O_DS_STATO_BUILD+(i+1), statiOggetto.get(i));
				log.debug("[jsonSearchCount] set parameter "+W_O_DS_STATO_BUILD+(i+1)+"="+ statiOggetto.get(i));
			}
		}
		
		for(Map.Entry<String,Object> myEntry : params.entrySet()) {
			String myKey 	= myEntry.getKey();
			Object myValue  = myEntry.getValue();
			q.setParameter(myKey, myValue);
			log.debug("[jsonSearchCount] set parameter "+myKey+"="+myValue.toString());
		}
		
		log.debug("[jsonSearchCount] q="+q.unwrap(org.hibernate.Query.class).getQueryString());
		return (BigDecimal) q.getResultList().get(0);
	}

	public List<VOggettoDTO> jsonSearchPaginated(List<JSONBdlSearchFilter> filters, List<String> statiOggetto, BigDecimal startItem, BigDecimal endItem) {
		log.debug("[jsonSearchPaginated] Entro nel metodo");
		
		String qu = jsonBuildPaginazioneQuery(filters, statiOggetto);
		String finalQu = "SELECT a.* FROM("+qu+") a WHERE a.rn >= :startItem AND a.rn <= :endItem ";

		Map<String,Object> params = jsonBuildParameters(filters);
		
		Query q = em.createNativeQuery(finalQu);
		
		if(statiOggetto!=null && !statiOggetto.isEmpty()) {
			for(int i=0; i<statiOggetto.size(); i++){
				q.setParameter(W_O_DS_STATO_BUILD+(i+1), statiOggetto.get(i));
				log.debug("[jsonSearchPaginated] set parameter "+W_O_DS_STATO_BUILD+(i+1)+"="+ statiOggetto.get(i));
			}
		}
		
		for(Map.Entry<String,Object> myEntry : params.entrySet()) {
			String myKey 	= myEntry.getKey();
			Object myValue  = myEntry.getValue();
			q.setParameter(myKey, myValue);
			log.debug("[jsonSearchPaginated] set parameter "+myKey+"="+myValue.toString());
		}
		
		q.setParameter("startItem", startItem);
		q.setParameter("endItem", endItem);
		
		log.debug("[jsonSearchPaginated] q="+q.unwrap(org.hibernate.Query.class).getQueryString());
		log.debug("[jsonSearchPaginated] Lancio la getResultList ed esco");
		return mapResultToVOggettoDTO(q.getResultList());
	}

	/**
	 * Legge other json filter.
	 *
	 * @param filters  filters
	 * @param filtertype  filtertype
	 * @param statiOggetto  stati oggetto
	 * @return other json filter
	 */
	@SuppressWarnings("rawtypes")
	public List<JSONBdlSearchFilter> getOtherJsonFilter(List<JSONBdlSearchFilter> filters, String filtertype, List<String> statiOggetto) {
		log.debug("[getOtherJsonFilter] Entro nel metodo filtertype="+filtertype);
		
		List<JSONBdlSearchFilter> filtersAdditional = new ArrayList<JSONBdlSearchFilter>();
		
		String fNameCd = "C_CD_COLLEZIONE";
		String fNameDescr = "C_DN_TITOLO";
		
		if(BdlServerConstants.JSON_FILTERTYPE_DATE.equals(filtertype)) {
			fNameCd = "O_DS_DATA";
			fNameDescr = "O_DS_DATA";
		} else if(BdlServerConstants.JSON_FILTERTYPE_LANG.equals(filtertype)) {
			fNameCd = "O_CD_LINGUA";
			fNameDescr = "O_DS_LINGUA";
		} else if(BdlServerConstants.JSON_FILTERTYPE_COLLECTION.equals(filtertype)) {
			fNameCd = "C_CD_COLLEZIONE";
			fNameDescr = "C_DN_TITOLO";
		} else if(BdlServerConstants.JSON_FILTERTYPE_KIND.equals(filtertype)) {
			fNameCd = "O_CD_LIVELLO_BIBLIO";
			fNameDescr = "O_DS_LIVELLO_BIBLIO";
		} else if(BdlServerConstants.JSON_FILTERTYPE_INSTITUTE.equals(filtertype)) {
			fNameCd = "I_CD_ISTITUTO";
			fNameDescr = "I_DN_NOME";
		} else if(BdlServerConstants.JSON_FILTERTYPE_AUTHOR.equals(filtertype)) {
			fNameCd = "O_CD_AUTORE";
			fNameDescr = "O_DS_AUTORE";
		} else if(BdlServerConstants.JSON_FILTERTYPE_SUBJECT.equals(filtertype)) {
			fNameCd = "O_CD_SOGGETTO";
			fNameDescr = "O_DS_SOGGETTO";
		}
			
		String quInternaUno = "SELECT "+fNameCd+" as CODE, "+fNameDescr+" as DESCRIPTION"
								+ " FROM("+ jsonBuildQuery(filters, statiOggetto)+")"
								+ " WHERE "+fNameCd+" IS NOT NULL AND "+fNameDescr+" IS NOT NULL";
		
		String quInternaDue = null;
		if(BdlServerConstants.JSON_FILTERTYPE_AUTHOR.equals(filtertype)) {
			fNameCd = "O_CD_AUTORE_SEC";
			fNameDescr = "O_DS_AUTORE_SEC";
			
			quInternaDue = "SELECT "+fNameCd+" as CODE, "+fNameDescr+" as DESCRIPTION"
								+ " FROM("+jsonBuildQuery(filters, statiOggetto)+")"
								+ " WHERE "+fNameCd+" IS NOT NULL AND "+fNameDescr+" IS NOT NULL";
		}
		
		StringBuilder qu = new StringBuilder();
		
		qu.append("SELECT CODE, DESCRIPTION, COUNT(CODE) as TOTOK FROM(");
		if(quInternaDue!=null) {
			qu.append("("+quInternaUno+") UNION ("+quInternaDue+")");
		} else {
			qu.append(quInternaUno);
		}
		qu.append(") GROUP BY CODE, DESCRIPTION ORDER BY TOTOK DESC");
		
		log.debug("[getOtherJsonFilter] qu="+qu.toString());
		
		Map<String,Object> params = jsonBuildParameters(filters);
		Query q = em.createNativeQuery(qu.toString());

		if(statiOggetto!=null && !statiOggetto.isEmpty()){
			for(int i=0; i<statiOggetto.size(); i++){
				q.setParameter(W_O_DS_STATO_BUILD+(i+1), statiOggetto.get(i));
			}
		}
		
		for(Map.Entry<String,Object> myEntry : params.entrySet()) {
			String myKey 	= myEntry.getKey();
			Object myValue  = myEntry.getValue();
			q.setParameter(myKey, myValue);
		}

		List result = q.getResultList();
		Iterator itr=result.iterator();
//		Json myJson = new Json();
		while(itr.hasNext() && filtersAdditional.size()<=25){
			Object[] row = (Object[])itr.next();
			Integer countInt = Integer.valueOf(0);
			String comparisonValue = "";
			String comparisonLabel = "";
			
			String newFilterCmpOp = BdlServerConstants.JSON_COMPARISONOP_EQUALID;
			
			if(BdlServerConstants.JSON_FILTERTYPE_DATE.equals(filtertype)) {
				String code = (String) row[0];
				String description = (String) row[1];
				BigDecimal counter = (BigDecimal) row[2];
				
				countInt = Integer.valueOf(counter.intValueExact());
				comparisonValue = code;
				comparisonLabel = description;
				newFilterCmpOp = BdlServerConstants.JSON_COMPARISONOP_EQUAL;
			}else{
				BigDecimal code = (BigDecimal) row[0];
				String description = (String) row[1];
				BigDecimal counter = (BigDecimal) row[2];
				
				countInt = Integer.valueOf(counter.intValueExact());
				comparisonValue = code.toBigInteger().toString();
				comparisonLabel = description;
			}

			if(!isJsonFilterContained(filters, filtertype, newFilterCmpOp, comparisonValue)){
				JSONBdlSearchFilter newFilter =  new JSONBdlSearchFilter();
				newFilter.setFilterType(filtertype);
				newFilter.setComparisonOperator(newFilterCmpOp);
				newFilter.setLogicalOperator(BdlServerConstants.JSON_LOGICALOP_AND);
				newFilter.setItemNumber(countInt);
				List<JSONBdlSearchComparator> newCompList = new ArrayList<JSONBdlSearchComparator>();
				JSONBdlSearchComparator newComparator =  new JSONBdlSearchComparator(comparisonValue, comparisonLabel);
				newCompList.add(newComparator);
				newFilter.setComparisonValues(newCompList);
				filtersAdditional.add(newFilter);
			}
		}
		return filtersAdditional;
	}
	
	/**
	 * Controlla se json filter contained.
	 *
	 * @param filters  filters
	 * @param filtertype  filtertype
	 * @param comparisonOperator  comparison operator
	 * @param comparisonValue  comparison value
	 * @return boolean
	 */
	private Boolean isJsonFilterContained(List<JSONBdlSearchFilter> filters, String filtertype, String comparisonOperator, String comparisonValue){
		if(filters!=null) {
			Iterator<JSONBdlSearchFilter> itr=filters.iterator();
			while(itr.hasNext()){
				JSONBdlSearchFilter filtro = (JSONBdlSearchFilter)itr.next();
				if(filtro.getFilterType().equals(filtertype) && filtro.getComparisonOperator().equals(comparisonOperator)){
					List<JSONBdlSearchComparator> compList = filtro.getComparisonValues();
					for(int i=0;i<compList.size();i++){
						JSONBdlSearchComparator compValue = compList.get(i);
						if(compValue.getComparisonValue().equals(comparisonValue)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Json search semantic.
	 *
	 * @param textToSearch  text to search
	 * @param typeToSearch  type to search
	 * @return list
	 */
	public List<List<SubMenuItemDTO>> jsonSearchSemantic(String textToSearch, BigDecimal typeToSearch) {
		List<List<SubMenuItemDTO>> objToRet = new ArrayList<List<SubMenuItemDTO>>();
		if(!"".equals(textToSearch.trim())){
			// Mi aspetto typeToSearch=0 nel caso di ricerca su tutti i tipioggetto
			objToRet.add(findForJsonSearchSemantic(typeToSearch.intValueExact()==0?null:typeToSearch.toString(), textToSearch, null, null));
			objToRet.add(findForJsonSearchSemantic(typeToSearch.intValueExact()==0?null:typeToSearch.toString(), null, textToSearch, null));
			objToRet.add(findForJsonSearchSemantic(typeToSearch.intValueExact()==0?null:typeToSearch.toString(), null, null, textToSearch));
		}
		return objToRet;
	}
	
	/**
	 * Find for json search semantic.
	 *
	 * @param filterTipoOggetto  filter tipo oggetto
	 * @param filterTitolo  filter titolo
	 * @param filterAutore  filter autore
	 * @param filterSoggetto  filter soggetto
	 * @return list
	 */
	private List<SubMenuItemDTO> findForJsonSearchSemantic(String filterTipoOggetto, String filterTitolo, String filterAutore, String filterSoggetto) {
		log.debug("[findForJsonSearchSemantic] Entro...");
		
		String quInterna1 = "SELECT"
				+ " a.CD_RIFERIMENTO as codiceOO,"
				+ " a.DS_INDICE as indice,"
				+ " BDL_RANK(a.DS_INDICE, :textToSearch) as rankOO"
				+ " FROM BDL_INDICE_RICERCA a ";
		if(filterTitolo!=null)
			quInterna1 += "JOIN V_OGGETTI b on a.CD_RIFERIMENTO=b.O_CD_OGGETTO_ORIGINALE";
		quInterna1 += " WHERE a.DS_RISORSA=:filterRisorsa";
		
		// Mi aspetto filterTipoOggetto=null nel caso di ricerca su tutti i tipioggetto
		if(filterTipoOggetto!=null)
			quInterna1 += " AND a.CD_TIPO_OGGETTO=:filterTipoOggetto";
		
		if(filterTitolo!=null)
			quInterna1 += " AND "+getFiltroFigli("b")+" ";
//		log.debug("[findForJsonSearchSemantic] quInterna1="+quInterna1);
		
		String quInterna2 = "SELECT codiceOO, indice, rankOO FROM (" + quInterna1 + ") WHERE rankOO!=0 ORDER BY rankOO DESC";
//		log.debug("[findForJsonSearchSemantic] quInterna2="+quInterna2);
		
		String quFinale = "SELECT codiceOO,indice FROM(" + quInterna2 + ") WHERE rownum<=5";
		Query q = em.createNativeQuery(quFinale);
		log.debug("[findForJsonSearchSemantic] quFinale="+quFinale);
		
		// Mi aspetto filterTipoOggetto=null nel caso di ricerca su tutti i tipioggetto
		if(filterTipoOggetto!=null)
			q.setParameter("filterTipoOggetto",filterTipoOggetto);
		
		if(filterTitolo!=null) {
			q.setParameter("filterRisorsa","oggetto");
			q.setParameter("textToSearch",filterTitolo);
		} else if(filterAutore!=null) {
			q.setParameter("filterRisorsa","autore");
			q.setParameter("textToSearch",filterAutore);
		} else if(filterSoggetto!=null){
			q.setParameter("filterRisorsa","soggetto");
			q.setParameter("textToSearch",filterSoggetto);
		}
		return mapResultToSubMenuItemDTO(q.getResultList(), null);
	}
	
	/**
	 * Find oggetti contenuti.
	 *
	 * @param cdOOPadre  codice oo padre
	 * @param statoOO  stato oo
	 * @return list
	 */
	public List<SubMenuItemDTO> findOggettiContenuti(BigDecimal cdOOPadre, String statoOO) {
		log.debug("[findOggettiContenuti] entro");
		String qu = "SELECT"
				+ " CD_OGGETTO_ORIGINALE as codiceOO, DN_TITOLO as titoloOO"
				+ " FROM BDL_OGGETTO_ORIGINALE"
				+ " WHERE CD_OGGETTO_ORIGINALE_SUP=:cdOOPadre AND DS_STATO LIKE :statoOO"
				+ " ORDER BY DN_TITOLO_FE asc";
		Query q = em.createNativeQuery(qu);
		log.debug("[findOggettiContenuti] compongo: "+qu);
		q.setParameter("cdOOPadre", cdOOPadre);
		q.setParameter("statoOO", statoOO);
		return mapResultToSubMenuItemDTO(q.getResultList(), null);
	}
	
	public List<?> findMainCollezioniByIstituto(BigDecimal cdIstituto, String statoProgetto, List<String> statiOggetto,
			Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, Integer pLimit) {		
		
		String qu = "SELECT * FROM(";
//		String myQu = "SELECT COUNT(*) AS NUM_OGGETTI_IN_COLLEZIONE, C_CD_COLLEZIONE, C_DN_TITOLO FROM V_OGGETTI WHERE I_CD_ISTITUTO=:cdIstituto GROUP BY C_CD_COLLEZIONE, C_DN_TITOLO";
		qu += Q_COLLEZIONI_COUNT + W_INIT + buildWhereOnStatoProgetto(statoProgetto) + buildWhereOnStatoOggetto(statiOggetto) + buildWhereOnFlag(filtriFlag) + " AND I_CD_ISTITUTO=:cdIstituto " ;
		if(filterProgetto!=null) {
			qu += " AND P_CD_PROGETTO=:filterProgetto";
		}
		qu += G_COLLEZIONI_COUNT;
		qu += ") WHERE ROWNUM<=:pLimit";
		
		Query q = em.createNativeQuery(qu);

		q.setParameter("cdIstituto", cdIstituto);
		if(filterProgetto!=null) {
			q.setParameter("filterProgetto",filterProgetto);
		}
		buildParameterOnStatoProgetto(q, statoProgetto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		buildParameterOnFlag(q, filtriFlag);
		q.setParameter("pLimit", pLimit);
		
		return q.getResultList();
	}
	
	/**
	 * Legge filtro figli.
	 *
	 * @param tabAlias  tab alias
	 * @return filtro figli
	 */
	private String getFiltroFigli(String tabAlias){
		String replacement = "";
		if(tabAlias!=null){
			replacement = tabAlias+".";
		}
		return 	  "("
				+ 	"("+replacement+"O_DS_LIVELLO_BIBLIO='Monografia' AND "+replacement+"O_CD_OGGETTO_ORIGINALE_SUP IS NOT NULL) OR " 
				+ 	"("+replacement+"O_DS_LIVELLO_BIBLIO='Monografia' AND "+replacement+"O_CD_OGGETTO_ORIGINALE_SUP IS NULL AND (O_NR_OGGETTI_INFERIORI IS NULL OR O_NR_OGGETTI_INFERIORI=0)) OR " 
				+ 	"("+replacement+"O_DS_LIVELLO_BIBLIO='Periodico' AND "+replacement+"O_CD_OGGETTO_ORIGINALE_SUP IS NULL) OR " 
				+ 	"("+replacement+"O_DS_LIVELLO_BIBLIO<>'Monografia' AND "+replacement+"O_DS_LIVELLO_BIBLIO<>'Periodico') OR "
				+ 	"("+replacement+"O_DS_LIVELLO_BIBLIO IS NULL)"
			    + ")";
	}
	
	public List<JSONBdlInstitute> jsonGetIstituti(List<String> statiOggetto) {
		return jsonGetIstitutiFromVOggetti(statiOggetto, null);
	}
	public List<JSONBdlInstitute> jsonGetIstituto(List<String> statiOggetto, BigDecimal cdIstituto) {
		return jsonGetIstitutiFromVOggetti(statiOggetto, cdIstituto);
	}
	
	private List<JSONBdlRefCount> getCollezioniPrincipaliByIstituto(List<String> statiOggetto, BigDecimal cdIstituto, Integer rowNum) {		
		log.debug(ManagementUtils.ps+"[getCollezioniPrincipaliByIstituto] Entro...");
		String qu = "SELECT * FROM(" + Q_COLLEZIONI_COUNT + W_INIT + buildWhereOnStatoOggetto(statiOggetto) + " I_CD_ISTITUTO=:cdIstituto " + G_COLLEZIONI_COUNT + ") WHERE ROWNUM<=:rowNum";
		
		Query q = em.createNativeQuery(qu);

		q.setParameter("cdIstituto", cdIstituto);
		buildParameterOnStatoOggetto(q, statiOggetto);
		q.setParameter("rowNum", rowNum);
		
		return mapResultToJSONBdlRefCount(q.getResultList());
	}
	
    private List<JSONBdlRefCount> mapResultToJSONBdlRefCount(List<?> result){
		List<JSONBdlRefCount> objToRet = new ArrayList<JSONBdlRefCount>();
		Iterator<?> itr=result.iterator();
		while(itr.hasNext()) {
			Object[] row = (Object[])itr.next();
			objToRet.add(new JSONBdlRefCount((BigDecimal)row[1], (String)row[2], (BigDecimal)row[0]));
		}
		return objToRet;
	}
    
	private List<JSONBdlInstitute> jsonGetIstitutiFromVOggetti(List<String> statiOggetto, BigDecimal cdIstituto) {
		log.debug(ManagementUtils.ps+"[jsonGetIstitutiFromVOggetti] Entro...");
		String quConteggioOOGroupByIstituto = "SELECT COUNT(*) as CONTEGGIO, I_CD_ISTITUTO, I_DN_NOME "+ 
	            "FROM V_OGGETTI WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" C_CD_COLLEZIONE=C_CD_COLLEZIONE "+ 
	            "GROUP BY I_CD_ISTITUTO, I_DN_NOME ";
		List<JSONBdlRefCount> collezioniPrincipali = null;
		if(cdIstituto!=null) {
			log.debug(ManagementUtils.ps+"[jsonGetIstitutiFromVOggetti] cdIstituto="+cdIstituto);
			quConteggioOOGroupByIstituto = "SELECT COUNT(*) as CONTEGGIO, I_CD_ISTITUTO, I_DN_NOME "+ 
		            "FROM V_OGGETTI WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" I_CD_ISTITUTO=:cdIstituto "+ 
		            "GROUP BY I_CD_ISTITUTO, I_DN_NOME ";
			/* Recupero le collezioniPrincipali dell'Istituto */
			collezioniPrincipali = getCollezioniPrincipaliByIstituto(statiOggetto, cdIstituto, new Integer(5));
		}
		
		String quMaxRilevanzaGroupByIstituto = "SELECT I_CD_ISTITUTO, MAX(O_CD_OGGETTO_ORIGINALE) oggettoMain FROM ("+
			    "SELECT NVL(O_NR_RILEVANZA,0) RIL, I_CD_ISTITUTO, O_CD_OGGETTO_ORIGINALE, MAX(NVL(O_NR_RILEVANZA,0)) over (partition by I_CD_ISTITUTO) MAX_RIL "+
				"FROM V_OGGETTI) WHERE RIL=MAX_RIL GROUP BY I_CD_ISTITUTO ";
		
		String qu = "SELECT a.CONTEGGIO, i.CD_ENTE, i.DN_NOME, i.DS_INDIRIZZO, i.DN_COMUNE, i.DN_PROVINCIA, "
				+ "i.DS_CAP, i.NR_TELEFONO, i.NR_FAX, i.DN_EMAIL, i.DN_INDIRIZZO_WWW, i.FL_CLASSE, i.CD_ENTE_DIGIT, "
				+ "i.DS_CREATODA, i.DT_CREATOIL, i.DS_MODIFICATODA, i.DT_MODIFICATOIL, c.oggettoMain "+
    		"FROM ("+quConteggioOOGroupByIstituto+") a "+
    		"LEFT JOIN BDL_ENTE i on i.CD_ENTE=a.I_CD_ISTITUTO "+
    		"LEFT JOIN ("+quMaxRilevanzaGroupByIstituto+") c ON c.I_CD_ISTITUTO=a.I_CD_ISTITUTO";
		
		Query q = em.createNativeQuery( qu );
		
		buildParameterOnStatoOggetto(q, statiOggetto);
		if(cdIstituto!=null) {
			q.setParameter("cdIstituto", cdIstituto);
		}
		
		log.debug(ManagementUtils.ps+"[jsonGetIstitutiFromVOggetti] Req: q.getResultList()");
		List<?> result = q.getResultList();
		log.debug(ManagementUtils.ps+"[jsonGetIstitutiFromVOggetti] Res: q.getResultList()");

		List<JSONBdlInstitute> toRet = new ArrayList<JSONBdlInstitute>();
		
		Iterator<?> itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			
			JSONBdlItem mainItem = new JSONBdlItem();
			mainItem.setId((BigDecimal) row[17]);
			
			JSONBdlInstitute item = new JSONBdlInstitute(
					(BigDecimal) row[1], 
					(String) row[2], 
					(String) row[3], 
					(String) row[4], 
					(String) row[5], 
					(String) row[6], 
					(String) row[7], 
					(String) row[8], 
					(String) row[9], 
					(String) row[10], 
	    			((BigDecimal) row[0]).intValueExact(), 
	    			mainItem,
	    			collezioniPrincipali
	    	);
			toRet.add(item);
		}
		
		log.debug(ManagementUtils.ps+"[jsonGetIstitutiFromVOggetti] ...Esco");
		return toRet;
	}
	
	public List<JSONBdlCollection> jsonGetCollezione(List<String> statiOggetto, BigDecimal cdCollezione) {	
		return jsonGetCollezioniFromVOggetti(statiOggetto, cdCollezione);
	}
	public List<JSONBdlCollection> jsonGetCollezioni(List<String> statiOggetto) {	
		return jsonGetCollezioniFromVOggetti(statiOggetto, null);
	}
	public List<JSONBdlCollection> jsonGetCollezioniForAree(List<String> statiOggetto) {	
//		@Query(value = "SELECT b.* FROM BDL_COLLEZIONE b WHERE b.DS_AMBITO_DISCIPLINARE = ("+
//				"SELECT c.DS_AMBITO_DISCIPLINARE FROM BDL_COLLEZIONE c WHERE c.CD_COLLEZIONE=?1 AND c.CD_COLLEZIONE IN ("+
//					"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?2))", nativeQuery = true)
		return jsonGetCollezioniFromVOggetti(statiOggetto, null);
	}

	private List<JSONBdlCollection> jsonGetCollezioniFromVOggetti(List<String> statiOggetto, BigDecimal cdCollezione) {	
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniFromVOggetti] Entro...");
		String quConteggioOOGroupByCollezione = "SELECT COUNT(*) as CONTEGGIO, I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE "+ 
	            "FROM V_OGGETTI WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" C_CD_COLLEZIONE=C_CD_COLLEZIONE "+ 
	            "GROUP BY I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE ";
		if(cdCollezione!=null) {
			log.debug(ManagementUtils.ps+"[jsonGetCollezioniFromVOggetti] cdCollezione="+cdCollezione);
			quConteggioOOGroupByCollezione = "SELECT COUNT(*) as CONTEGGIO, I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE "+ 
		            "FROM V_OGGETTI WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" C_CD_COLLEZIONE=:cdCollezione "+ 
		            "GROUP BY I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE ";
		}
		
		String quMaxRilevanzaGroupByCollezione = "SELECT C_CD_COLLEZIONE, MAX(O_CD_OGGETTO_ORIGINALE) oggettoMain FROM ("+
			    "SELECT NVL(O_NR_RILEVANZA,0) RIL,C_CD_COLLEZIONE, O_CD_OGGETTO_ORIGINALE, MAX(NVL(O_NR_RILEVANZA,0)) over (partition by C_CD_COLLEZIONE) MAX_RIL "+
				"FROM V_OGGETTI) WHERE RIL=MAX_RIL GROUP BY C_CD_COLLEZIONE ";
		
		String qu = "SELECT a.CONTEGGIO, a.I_CD_ISTITUTO, a.I_DN_NOME, b.CD_COLLEZIONE, b.DN_TITOLO, "+
				"b.DS_DESCRIZIONE_IT, b.DS_DESCRIZIONE_EN, b.DS_DIRITTI, b.DS_AMBITO_DISCIPLINARE, "+ 
				"b.DS_COPERTURA_GEOGRAFICA, b.DS_PERIODO, b.DS_ANNO_OGG_ANTICO, b.DS_ANNO_OGG_RECENTE, "+ 
				"b.CD_PROGETTO, b.DS_CREATODA, b.DT_CREATOIL, b.DS_MODIFICATODA, b.DT_MODIFICATOIL, c.oggettoMain "+
    		"FROM ("+quConteggioOOGroupByCollezione+") a "+
    		"LEFT JOIN BDL_COLLEZIONE b on b.CD_COLLEZIONE=a.C_CD_COLLEZIONE "+
    		"LEFT JOIN ("+quMaxRilevanzaGroupByCollezione+") c ON c.C_CD_COLLEZIONE=a.C_CD_COLLEZIONE";
		
		Query q = em.createNativeQuery( qu );
		
		buildParameterOnStatoOggetto(q, statiOggetto);
		if(cdCollezione!=null) {
			q.setParameter("cdCollezione", cdCollezione);
		}
		
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniFromVOggetti] Req: q.getResultList()");
		List<?> result = q.getResultList();
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniFromVOggetti] Res: q.getResultList()");

		List<JSONBdlCollection> toRet = new ArrayList<JSONBdlCollection>();
		
		Iterator<?> itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			
			JSONBdlItem mainItem = new JSONBdlItem();
			mainItem.setId((BigDecimal) row[18]);
			
			JSONBdlCollection item = new JSONBdlCollection(
					(BigDecimal) row[3], 
	    			new JSONBdlRef((BigDecimal) row[1], (String) row[2]), 
	    			(String) row[4], 
	    			new JSONBdlRef((BigDecimal) row[3], (String) row[8]), 
	    			(String) row[9],
	    			(String) row[10], 
	    			(String) row[5], 
	    			(String) row[6], 
	    			((BigDecimal) row[0]).intValueExact(),
	    			mainItem
	    	);
			toRet.add(item);
		}
		
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniFromVOggetti] ...Esco");
		return toRet;
	}
	public List<JSONBdlCollectionLight> jsonGetCollezioniLight(List<String> statiOggetto) {		
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniLight] Entro...");
		
		String quConteggioOOGroupByCollezione = "SELECT COUNT(*) as CONTEGGIO, I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE "+ 
            "FROM V_OGGETTI WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" C_CD_COLLEZIONE=C_CD_COLLEZIONE "+ 
            "GROUP BY I_CD_ISTITUTO, I_DN_NOME, C_CD_COLLEZIONE ";
		
		String quMaxRilevanzaGroupByCollezione = "SELECT C_CD_COLLEZIONE, MAX(O_CD_OGGETTO_ORIGINALE) oggettoMain FROM ("+
			    "SELECT NVL(O_NR_RILEVANZA,0) RIL,C_CD_COLLEZIONE, O_CD_OGGETTO_ORIGINALE, MAX(NVL(O_NR_RILEVANZA,0)) over (partition by C_CD_COLLEZIONE) MAX_RIL "+
				"FROM V_OGGETTI) WHERE RIL=MAX_RIL GROUP BY C_CD_COLLEZIONE ";
		
		String qu = "SELECT a.CONTEGGIO, a.I_CD_ISTITUTO, a.I_DN_NOME, b.CD_COLLEZIONE, b.DN_TITOLO, "+
				"b.DS_DESCRIZIONE_IT, b.DS_DESCRIZIONE_EN, b.DS_DIRITTI, b.DS_AMBITO_DISCIPLINARE, "+ 
				"b.DS_COPERTURA_GEOGRAFICA, b.DS_PERIODO, b.DS_ANNO_OGG_ANTICO, b.DS_ANNO_OGG_RECENTE, "+ 
				"b.CD_PROGETTO, b.DS_CREATODA, b.DT_CREATOIL, b.DS_MODIFICATODA, b.DT_MODIFICATOIL, c.oggettoMain "+
    		"FROM ("+quConteggioOOGroupByCollezione+") a "+
    		"LEFT JOIN BDL_COLLEZIONE b on b.CD_COLLEZIONE=a.C_CD_COLLEZIONE "+
    		"LEFT JOIN ("+quMaxRilevanzaGroupByCollezione+") c ON c.C_CD_COLLEZIONE=a.C_CD_COLLEZIONE";
		
		Query q = em.createNativeQuery( qu );
		
		buildParameterOnStatoOggetto(q, statiOggetto);
		
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniLight] Req: q.getResultList()");
		List<?> result = q.getResultList();
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniLight] Res: q.getResultList()");

		List<JSONBdlCollectionLight> toRet = new ArrayList<JSONBdlCollectionLight>();
		
		Iterator<?> itr=result.iterator();
		while(itr.hasNext()){
			Object[] row = (Object[])itr.next();
			
			JSONBdlCollectionLight item = new JSONBdlCollectionLight(
					(BigDecimal) row[3], 
	    			new JSONBdlRef((BigDecimal) row[1], (String) row[2]), 
	    			(String) row[4], 
	    			new JSONBdlRef((BigDecimal) row[3], (String) row[8]), 
	    			(String) row[9],
	    			(String) row[10], 
	    			(String) row[5], 
	    			(String) row[6], 
	    			((BigDecimal) row[0]).intValueExact()
	    	);
			toRet.add(item);
		}
		
		log.debug(ManagementUtils.ps+"[jsonGetCollezioniLight] ...Esco");
		return toRet;
	}
	
	public List<VOggettoDTO> jsonGetOggettiInIstituto(List<String> statiOggetto, BigDecimal cdIstituto) {
		return findOggetti(null, statiOggetto, null, cdIstituto, null, null);
	}
	
	public List<VOggettoDTO> jsonGetOggettiInCollezione(List<String> statiOggetto, BigDecimal cdCollezione) {
		return findOggetti(null, statiOggetto, null, null, null, cdCollezione);
	}
	
	public List<VOggettoDTO> jsonGetOggettiInVetrina(List<String> statiOggetto, Integer nroOggetti) {
		log.debug(ManagementUtils.ps+"[jsonGetOggettiInVetrina] Entro...");

		String qu = "SELECT * FROM (" + 
				Q_OGGETTI + "vOO JOIN BDL_VETRINA v ON vOO.O_CD_OGGETTO_ORIGINALE=v.CD_OGGETTO_ORIGINALE " +
			"WHERE "+buildWhereOnStatoOggetto(statiOggetto)+" v.FL_ATTIVO='Y' ORDER BY v.DT_INSERITOIL DESC) WHERE ROWNUM<=:nroOggetti";
		
		Query q = em.createNativeQuery( qu );
		
		buildParameterOnStatoOggetto(q, statiOggetto);
		if(nroOggetti==null)
			nroOggetti = Integer.valueOf(10);
		q.setParameter("nroOggetti", nroOggetti);
		
		log.debug(ManagementUtils.ps+"[jsonGetOggettiInVetrina] Req: q.getResultList()");
		List<?> resultList = q.getResultList();
		log.debug(ManagementUtils.ps+"[jsonGetOggettiInVetrina] Res: q.getResultList()");
		
		log.debug(ManagementUtils.ps+"[jsonGetOggettiInVetrina] ...Esco");
		return mapResultToVOggettoDTO(resultList);
	}
}
