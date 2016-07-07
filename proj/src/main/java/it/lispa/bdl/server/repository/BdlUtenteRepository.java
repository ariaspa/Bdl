package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlUtente;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlUtenteRepository.
 */
public interface BdlUtenteRepository extends PagingAndSortingRepository<BdlUtente, BigDecimal> {

	/**
	 * Find by codice utente.
	 *
	 * @param cdUtente  codice utente
	 * @return bdl utente
	 */
	BdlUtente findByCdUtente(BigDecimal cdUtente);
	
	/**
	 * Find by codice codice fiscale.
	 *
	 * @param cf  cf
	 * @return bdl utente
	 */
	BdlUtente findByCdCodiceFiscale(String cf);
	
	/**
	 * Find by descrizione stato.
	 *
	 * @param stato  stato
	 * @return list
	 */
	List<BdlUtente> findByDsStato(String stato);
	
	/**
	 * Find by codice ruolo.
	 *
	 * @param cdRuolo  codice ruolo
	 * @return list
	 */
	List<BdlUtente> findByCdRuolo(BigDecimal cdRuolo);
	
}
