package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlToc;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlTocRepository.
 */
public interface BdlTocRepository extends PagingAndSortingRepository<BdlToc, BigDecimal> {

	/**
	 * Find by codice oggetto originale order by codice toc asc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return list
	 */
	public List<BdlToc> findByCdOggettoOriginaleOrderByCdTocAsc(BigDecimal cdOggettoOriginale);
	
	/**
	 * Find by codice oggetto originale and codice toc padre is null order by codice toc asc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return list
	 */
	public List<BdlToc> findByCdOggettoOriginaleAndCdTocPadreIsNullOrderByCdTocAsc(BigDecimal cdOggettoOriginale);
	
	/**
	 * Find by codice oggetto originale and codice toc padre order by codice toc asc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTocPadre  codice toc padre
	 * @return list
	 */
	public List<BdlToc> findByCdOggettoOriginaleAndCdTocPadreOrderByCdTocAsc(BigDecimal cdOggettoOriginale, BigDecimal cdTocPadre);
	
}
