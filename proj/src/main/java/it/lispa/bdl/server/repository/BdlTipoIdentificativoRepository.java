package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlTipoIdentificativo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlTipoIdentificativoRepository.
 */
public interface BdlTipoIdentificativoRepository extends PagingAndSortingRepository<BdlTipoIdentificativo, BigDecimal> {

	/**
	 * Find by codice tipo identificativo.
	 *
	 * @param codice  codice
	 * @return bdl tipo identificativo
	 */
	public BdlTipoIdentificativo findByCdTipoIdentificativo(BigDecimal codice);
	
	/**
	 * Find by descrizione descrizione.
	 *
	 * @param descrizione  descrizione
	 * @return list
	 */
	public List<BdlTipoIdentificativo> findByDsDescrizione(String descrizione);

}
