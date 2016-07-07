package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlQualificaAutore;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlQualificaAutoreRepository.
 */
public interface BdlQualificaAutoreRepository extends PagingAndSortingRepository<BdlQualificaAutore, BigDecimal> {

	/**
	 * Find by codice qualifica autore.
	 *
	 * @param cdQualificaAutore  codice qualifica autore
	 * @return bdl qualifica autore
	 */
	BdlQualificaAutore findByCdQualificaAutore(BigDecimal cdQualificaAutore);
	

}
