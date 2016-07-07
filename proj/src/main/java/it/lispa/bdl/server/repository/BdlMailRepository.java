
package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlMail;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlMailRepository.
 */
public interface BdlMailRepository extends PagingAndSortingRepository<BdlMail, BigDecimal> {
	
	/**
	 * Find by codice comunicazione.
	 *
	 * @param cd  codice
	 * @return bdl mail
	 */
	BdlMail findByCdComunicazione(String cd);
}
