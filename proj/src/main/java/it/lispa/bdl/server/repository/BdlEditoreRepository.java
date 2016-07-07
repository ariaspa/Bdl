package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlEditore;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlEditoreRepository.
 */
public interface BdlEditoreRepository extends PagingAndSortingRepository<BdlEditore, BigDecimal> {
	
	/**
	 * Find by codice editore.
	 *
	 * @param codice  codice
	 * @return bdl editore
	 */
	public BdlEditore findByCdEditore(BigDecimal codice);
	
	/**
	 * Find by descrizione descrizione.
	 *
	 * @param descrizione  descrizione
	 * @return list
	 */
	public List<BdlEditore> findByDsDescrizione(String descrizione);
}
