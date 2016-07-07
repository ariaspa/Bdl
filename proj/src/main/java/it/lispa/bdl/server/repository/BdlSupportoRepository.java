package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlSupporto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlSupportoRepository.
 */
public interface BdlSupportoRepository extends PagingAndSortingRepository<BdlSupporto, BigDecimal> {
	
	/**
	 * Find by codice supporto.
	 *
	 * @param cdSupporto  codice supporto
	 * @return bdl supporto
	 */
	BdlSupporto findByCdSupporto(BigDecimal cdSupporto);
	
	/**
	 * Find supports by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT s.* FROM BDL_SUPPORTO s WHERE s.CD_SUPPORTO IS NOT NULL AND s.CD_SUPPORTO IN ("+
			"SELECT vOO.O_CD_SUPPORTO FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlSupporto> findSupportsByStatoOggetto(String statoOggetto);
}
