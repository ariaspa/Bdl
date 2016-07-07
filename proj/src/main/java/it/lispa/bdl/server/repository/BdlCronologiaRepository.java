package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlCronologia;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlCronologiaRepository.
 */
public interface BdlCronologiaRepository extends PagingAndSortingRepository<BdlCronologia, BigDecimal> {
	
	/**
	 * Find by codice oggetto originale order by data operazione desc.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return list
	 */
	List<BdlCronologia> findByCdOggettoOriginaleOrderByDtOperazioneDesc(BigDecimal cdOggetto);
}
