package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlEnteUtente;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlEnteUtenteRepository.
 */
public interface BdlEnteUtenteRepository extends PagingAndSortingRepository<BdlEnteUtente, BigDecimal> {

	/**
	 * Find by codice ente.
	 *
	 * @param cdEnte  codice ente
	 * @return list
	 */
	List<BdlEnteUtente> findByCdEnte(BigDecimal cdEnte);
	
	/**
	 * Find by codice utente.
	 *
	 * @param cdUtente  codice utente
	 * @return list
	 */
	List<BdlEnteUtente> findByCdUtente(BigDecimal cdUtente);
	
	/**
	 * Find by codice ente and codice utente.
	 *
	 * @param cdEnte  codice ente
	 * @param cdUtente  codice utente
	 * @return list
	 */
	List<BdlEnteUtente> findByCdEnteAndCdUtente(BigDecimal cdEnte,BigDecimal cdUtente);
}
