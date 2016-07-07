
package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlProvincia;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlProvinciaRepository.
 */
public interface BdlProvinciaRepository extends PagingAndSortingRepository<BdlProvincia, BigDecimal> {
	
	/**
	 * Find by codice prov.
	 *
	 * @param cdProv  codice prov
	 * @return bdl provincia
	 */
	BdlProvincia findByCdProv(BigDecimal cdProv);
	
	/**
	 * Find by codice sigla.
	 *
	 * @param cdSigla  codice sigla
	 * @return bdl provincia
	 */
	BdlProvincia findByCdSigla(String cdSigla);
}
