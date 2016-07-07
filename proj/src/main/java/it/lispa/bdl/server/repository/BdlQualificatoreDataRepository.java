package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlQualificatoreData;

import java.math.BigDecimal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlQualificatoreDataRepository.
 */
public interface BdlQualificatoreDataRepository extends PagingAndSortingRepository<BdlQualificatoreData, BigDecimal> {

	/**
	 * Find by codice qualificatore data.
	 *
	 * @param cdQualificatoreData  codice qualificatore data
	 * @return bdl qualificatore data
	 */
	BdlQualificatoreData findByCdQualificatoreData(BigDecimal cdQualificatoreData);
	

}
