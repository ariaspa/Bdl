package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlWorkFilesystem;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlWorkFilesystemRepository.
 */
public interface BdlWorkFilesystemRepository extends PagingAndSortingRepository<BdlWorkFilesystem, BigDecimal> {

	/**
	 * Find by codice 
	 *
	 * @param cdWorkFilesystem  codice
	 * @return BdlWorkFilesystem
	 */
	BdlWorkFilesystem findByCdWorkFilesystem(BigDecimal cdWorkFilesystem);
	
	/**
	 * Find by flag in uso
	 *
	 * @param cdUtente  codice utente
	 * @return list
	 */
	List<BdlWorkFilesystem> findByFlInUso(Boolean flInUso);
	
}
