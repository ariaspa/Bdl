package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlSoggettoProd;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlSoggettoProdRepository.
 */
public interface BdlSoggettoProdRepository extends PagingAndSortingRepository<BdlSoggettoProd, BigDecimal> {

	/**
	 * Find by codice soggetto prod.
	 *
	 * @param cdSoggettoProd  codice soggetto prod
	 * @return bdl soggetto prod
	 */
	BdlSoggettoProd findByCdSoggettoProd(BigDecimal cdSoggettoProd);
	

}
