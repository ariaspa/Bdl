package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlTipoArchivio;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlTipoArchivioRepository.
 */
public interface BdlTipoArchivioRepository extends PagingAndSortingRepository<BdlTipoArchivio, BigDecimal> {

	/**
	 * Find by codice tipo archivio.
	 *
	 * @param cdTipoArchivio  codice tipo archivio
	 * @return bdl tipo archivio
	 */
	BdlTipoArchivio findByCdTipoArchivio(BigDecimal cdTipoArchivio);
	

}
