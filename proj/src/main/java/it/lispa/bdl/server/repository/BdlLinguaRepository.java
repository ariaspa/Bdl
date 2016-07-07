package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlLingua;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlLinguaRepository.
 */
public interface BdlLinguaRepository extends PagingAndSortingRepository<BdlLingua, BigDecimal> {

	/**
	 * Find by codice lingua.
	 *
	 * @param cdLingua  codice lingua
	 * @return bdl lingua
	 */
	BdlLingua findByCdLingua(BigDecimal cdLingua);
	
	/**
	 * Find by descrizione codlang.
	 *
	 * @param dsCodlang  descrizione codlang
	 * @return list
	 */
	List<BdlLingua> findByDsCodlang(String dsCodlang);
	
	/**
	 * Find languages by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT l.* FROM BDL_LINGUA l WHERE l.CD_LINGUA IS NOT NULL AND l.CD_LINGUA IN ("+
			"SELECT vOO.O_CD_LINGUA FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlLingua> findLanguagesByStatoOggetto(String statoOggetto);
}
