package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlContestoArch;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlContestoArchRepository.
 */
public interface BdlContestoArchRepository extends PagingAndSortingRepository<BdlContestoArch, BigDecimal> {

	/**
	 * Find by codice contesto arch.
	 *
	 * @param cdContestoArch  codice contesto arch
	 * @return bdl contesto arch
	 */
	BdlContestoArch findByCdContestoArch(BigDecimal cdContestoArch);
	

}
