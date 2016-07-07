package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlPdfPagina;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlPdfPaginaRepository.
 */
public interface BdlPdfPaginaRepository extends PagingAndSortingRepository<BdlPdfPagina, BigDecimal> {
	
	/**
	 * Find by codice oggetto originale.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return list
	 */
	List<BdlPdfPagina> findByCdOggettoOriginale(BigDecimal cdOggetto);
	
	/**
	 * Find by codice oggetto originale and descrizione pagina like order by nr pagina desc.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param textToSearch  text to search
	 * @return list
	 */
	List<BdlPdfPagina> findByCdOggettoOriginaleAndDsPaginaLikeOrderByNrPaginaDesc(BigDecimal cdOggetto, String textToSearch);
}
