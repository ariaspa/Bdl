package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlAutore;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlAutoreRepository.
 */
public interface BdlAutoreRepository extends PagingAndSortingRepository<BdlAutore, BigDecimal> {
	
	/**
	 * Find by codice autore.
	 *
	 * @param codice  codice
	 * @return bdl autore
	 */
	public BdlAutore findByCdAutore(BigDecimal codice);
	
	/**
	 * Find by descrizione descrizione.
	 *
	 * @param descrizione  descrizione
	 * @return list
	 */
	public List<BdlAutore> findByDsDescrizione(String descrizione);
}
