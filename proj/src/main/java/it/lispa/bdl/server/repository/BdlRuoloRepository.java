package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlRuolo;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlRuoloRepository.
 */
public interface BdlRuoloRepository extends PagingAndSortingRepository<BdlRuolo, BigDecimal> {

	/**
	 * Find by codice ruolo.
	 *
	 * @param cdRuolo  codice ruolo
	 * @return bdl ruolo
	 */
	BdlRuolo findByCdRuolo(BigDecimal cdRuolo);

	/**
	 * Find by denominazione nome.
	 *
	 * @param strDnNome  str denominazione nome
	 * @return bdl ruolo
	 */
	BdlRuolo findByDnNome(String strDnNome);
}
