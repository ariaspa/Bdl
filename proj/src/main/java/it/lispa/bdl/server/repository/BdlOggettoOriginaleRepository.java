package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlOggettoOriginale;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface BdlOggettoOriginaleRepository.
 */
public interface BdlOggettoOriginaleRepository extends PagingAndSortingRepository<BdlOggettoOriginale, BigDecimal> {
	
	/**
	 * Find by codice oggetto originale.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return bdl oggetto originale
	 */
	BdlOggettoOriginale findByCdOggettoOriginale(BigDecimal cdOggettoOriginale);

	/**
	 * Find by titolo and catalogatore.
	 *
	 * @param titolo  titolo
	 * @param cdCatalogatore  codice catalogatore
	 * @return list
	 */
	@Query(value = "SELECT oo.* FROM " +
    		"BDL_OGGETTO_ORIGINALE oo " +
    		"LEFT JOIN BDL_COLLEZIONE cc ON oo.CD_COLLEZIONE=cc.CD_COLLEZIONE " +
    		"LEFT JOIN BDL_PROGETTO pp ON pp.CD_PROGETTO=cc.CD_PROGETTO " +
    		"WHERE oo.DN_TITOLO=?1 AND pp.CD_ENTE=?2", nativeQuery = true)
    public List<BdlOggettoOriginale> findByTitoloAndCatalogatore(String titolo, BigDecimal cdCatalogatore);

	/**
	 * Find by denominazione titolo.
	 *
	 * @param titolo  titolo
	 * @return bdl oggetto originale
	 */
	public BdlOggettoOriginale findByDnTitolo(String titolo);
	
	/**
	 * Find by denominazione titolo ignore case.
	 *
	 * @param titolo  titolo
	 * @return bdl oggetto originale
	 */
	public BdlOggettoOriginale findByDnTitoloIgnoreCase(String titolo);
	
	/**
	 * Find by data pubblicatoil is not null order by data pubblicatoil desc.
	 *
	 * @return list
	 */
	public List<BdlOggettoOriginale> findByDtPubblicatoilIsNotNullOrderByDtPubblicatoilDesc();
	
	/**
	 * Update nr rilevanza.
	 *
	 * @param cdOO  codice oo
	 * @return integer
	 */
	@Modifying
	@Transactional(readOnly=false)
	@Query(value = "UPDATE BDL_OGGETTO_ORIGINALE SET NR_RILEVANZA=NVL(NR_RILEVANZA, 1)+1 WHERE CD_OGGETTO_ORIGINALE=?1", nativeQuery = true)
    public Integer updateNrRilevanza(BigDecimal cdOO);
	
	/**
	 * Find main item by codice collezione and descrizione stato.
	 *
	 * @param cdCollezione  codice collezione
	 * @param statoOO  stato oo
	 * @return bdl oggetto originale
	 */
	@Query(value = "SELECT * FROM("
			+ "SELECT oo.* FROM BDL_OGGETTO_ORIGINALE oo "
			+ "WHERE oo.DS_STATO LIKE ?2 AND oo.CD_COLLEZIONE=?1 "
			+ "ORDER BY oo.NR_RILEVANZA desc) WHERE ROWNUM<=1", nativeQuery = true)
	public BdlOggettoOriginale findMainItemByCdCollezioneAndDsStato(BigDecimal cdCollezione, String statoOO);
	
//	@Query(value = "SELECT oo.* FROM BDL_OGGETTO_ORIGINALE oo WHERE oo.CD_OGGETTO_ORIGINALE_SUP=?1 ORDER BY oo.DN_TITOLO asc ", nativeQuery = true)
	public List<BdlOggettoOriginale> findByCdOggettoOriginaleSupOrderByDnTitoloAsc(BigDecimal cdOggettoSup);
	
	@Query(value = "SELECT * FROM(" +
		"SELECT oo.* FROM BDL_OGGETTO_ORIGINALE oo JOIN BDL_VETRINA v ON oo.CD_OGGETTO_ORIGINALE=v.CD_OGGETTO_ORIGINALE " +
    		"WHERE v.FL_ATTIVO='Y' ORDER BY v.DT_INSERITOIL DESC" +
    	") WHERE ROWNUM<=?1", nativeQuery=true)
	public List<BdlOggettoOriginale> findOggettiAttiviOrderByDtInseritoilDesc(Integer rowNum);
	
	@Query(value = "SELECT * FROM(" +
		"SELECT oo.* FROM BDL_OGGETTO_ORIGINALE oo JOIN BDL_VETRINA v ON oo.CD_OGGETTO_ORIGINALE=v.CD_OGGETTO_ORIGINALE " +
	    	"WHERE v.FL_ATTIVO='Y' AND " + 
				"(DT_SCADEIL IS NULL OR DT_SCADEIL>=sysdate) ORDER BY v.DT_INSERITOIL DESC" +
		") WHERE ROWNUM<=?1", nativeQuery=true)
	public List<BdlOggettoOriginale> findOggettiAttiviNonScadutiOrderByDtInseritoilDesc(Integer rowNum);
}
