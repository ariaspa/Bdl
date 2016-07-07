package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlSoggetto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlSoggettoRepository.
 */
public interface BdlSoggettoRepository extends PagingAndSortingRepository<BdlSoggetto, BigDecimal> {

	/**
	 * Find by codice soggetto.
	 *
	 * @param codice  codice
	 * @return bdl soggetto
	 */
	BdlSoggetto findByCdSoggetto(BigDecimal codice);
	
	/**
	 * Find by descrizione descrizione.
	 *
	 * @param descrizione  descrizione
	 * @return list
	 */
	public List<BdlSoggetto> findByDsDescrizione(String descrizione);

	/**
	 * Find subjects by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT s.* FROM BDL_SOGGETTO s WHERE s.CD_SOGGETTO IS NOT NULL AND s.CD_SOGGETTO IN ("+
			"SELECT vOO.O_CD_SOGGETTO FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlSoggetto> findSubjectsByStatoOggetto(String statoOggetto);
}
