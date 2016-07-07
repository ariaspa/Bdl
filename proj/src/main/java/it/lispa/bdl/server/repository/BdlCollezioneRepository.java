package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlCollezione;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlCollezioneRepository.
 */
public interface BdlCollezioneRepository extends PagingAndSortingRepository<BdlCollezione, BigDecimal> {
	
	/**
	 * Find by codice collezione.
	 *
	 * @param cdCollezione  codice collezione
	 * @return bdl collezione
	 */
	public BdlCollezione findByCdCollezione(BigDecimal cdCollezione);
	
	/**
	 * Find by codice progetto.
	 *
	 * @param cdProgetto  codice progetto
	 * @return list
	 */
	public List<BdlCollezione> findByCdProgetto(BigDecimal cdProgetto);
	
	/**
	 * Find by codice progetto order by denominazione titolo asc.
	 *
	 * @param cdProgetto  codice progetto
	 * @return list
	 */
	public List<BdlCollezione> findByCdProgettoOrderByDnTitoloAsc(BigDecimal cdProgetto);
	
	/**
	 * Find by descrizione ambito disciplinare is not null.
	 *
	 * @return list
	 */
	public List<BdlCollezione> findByDsAmbitoDisciplinareIsNotNull();
	
	/**
	 * Find collezioni by ambito disciplinare and stato oggetto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT b.* FROM BDL_COLLEZIONE b WHERE b.DS_AMBITO_DISCIPLINARE = ("+
						"SELECT c.DS_AMBITO_DISCIPLINARE FROM BDL_COLLEZIONE c WHERE c.CD_COLLEZIONE=?1 AND c.CD_COLLEZIONE IN ("+
								"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?2))", nativeQuery = true)
	public List<BdlCollezione> findCollezioniByAmbitoDisciplinareAndStatoOggetto(BigDecimal cdCollezione, String statoOggetto);
	
	/**
	 * Find collezioni by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT b.* FROM BDL_COLLEZIONE b WHERE b.CD_COLLEZIONE IN ("+
								"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlCollezione> findCollezioniByStatoOggetto(String statoOggetto);
	
	/**
	 * Find collezione by codice collezione and stato oggetto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param statoOggetto  stato oggetto
	 * @return bdl collezione
	 */
	@Query(value = "SELECT b.* FROM BDL_COLLEZIONE b WHERE b.CD_COLLEZIONE=?1 AND b.CD_COLLEZIONE IN ("+
			"SELECT vOO.C_CD_COLLEZIONE FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?2)", nativeQuery = true)
	public BdlCollezione findCollezioneByCdCollezioneAndStatoOggetto(BigDecimal cdCollezione, String statoOggetto);
}
