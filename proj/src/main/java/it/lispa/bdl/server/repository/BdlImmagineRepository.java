package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlImmagine;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlImmagineRepository.
 */
public interface BdlImmagineRepository extends PagingAndSortingRepository<BdlImmagine, BigDecimal> {
	
	/**
	 * Find by codice immagine.
	 *
	 * @param id  id
	 * @return bdl immagine
	 */
	BdlImmagine findByCdImmagine(BigDecimal id);
	
	/**
	 * Find by codice oggetto originale and denominazione nome immagine.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param dnNomeImmagine  denominazione nome immagine
	 * @return list
	 */
	List<BdlImmagine> findByCdOggettoOriginaleAndDnNomeImmagine(BigDecimal cdOggettoOriginale, String dnNomeImmagine);
	
	/**
	 * Find by codice oggetto originale order by denominazione nome immagine asc.
	 *
	 * @param codice  codice
	 * @return list
	 */
	List<BdlImmagine> findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(BigDecimal codice);
	
	/**
	 * Find by codice oggetto originale and flag principale.
	 *
	 * @param cdOO  codice oo
	 * @param flag  flag
	 * @return bdl immagine
	 */
	BdlImmagine findByCdOggettoOriginaleAndFlPrincipale(BigDecimal cdOO, Boolean flag);
	
	/**
	 * Find by codice immagine and codice oggetto originale.
	 *
	 * @param cdImg  codice img
	 * @param cdOO  codice oo
	 * @return bdl immagine
	 */
	BdlImmagine findByCdImmagineAndCdOggettoOriginale(BigDecimal cdImg, BigDecimal cdOO);
}
