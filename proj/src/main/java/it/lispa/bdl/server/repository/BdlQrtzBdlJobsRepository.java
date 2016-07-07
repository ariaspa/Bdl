package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlQrtzBdlJobs;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlQrtzBdlJobsRepository.
 */
public interface BdlQrtzBdlJobsRepository extends PagingAndSortingRepository<BdlQrtzBdlJobs, BigDecimal> {

	/**
	 * Find by codice bdl jobs.
	 *
	 * @param cdBdlJobs  codice bdl jobs
	 * @return bdl qrtz bdl jobs
	 */
	BdlQrtzBdlJobs findByCdBdlJobs(BigDecimal cdBdlJobs);
	
	/**
	 * Find by codice utente.
	 *
	 * @param cdUtente  codice utente
	 * @return list
	 */
	List<BdlQrtzBdlJobs> findByCdUtente(BigDecimal cdUtente);
	
}
