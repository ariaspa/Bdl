package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlProgetto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlProgettoRepository.
 */
public interface BdlProgettoRepository extends PagingAndSortingRepository<BdlProgetto, BigDecimal> {

	/**
	 * Find by codice progetto.
	 *
	 * @param cdProgetto  codice progetto
	 * @return bdl progetto
	 */
	BdlProgetto findByCdProgetto(BigDecimal cdProgetto);
	
	/**
	 * Find by codice ente and descrizione stato order by denominazione titolo asc.
	 *
	 * @param cdEnte  codice ente
	 * @param stato  stato
	 * @return list
	 */
	List<BdlProgetto> findByCdEnteAndDsStatoOrderByDnTitoloAsc(BigDecimal cdEnte, String stato);
}
