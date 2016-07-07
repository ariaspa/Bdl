package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlEnte;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlEnteRepository.
 */
public interface BdlEnteRepository extends PagingAndSortingRepository<BdlEnte, BigDecimal> {
	
	/**
	 * Find by denominazione nome.
	 *
	 * @param enteOrIst  ente or ist
	 * @return bdl ente
	 */
	BdlEnte findByDnNome(String enteOrIst);
	
	/**
	 * Find by flag classe order by denominazione nome asc.
	 *
	 * @param flClasse  flag classe
	 * @return list
	 */
	List<BdlEnte> findByFlClasseOrderByDnNomeAsc(char flClasse);
	
	/**
	 * Find by codice ente.
	 *
	 * @param cdEnte  codice ente
	 * @return bdl ente
	 */
	BdlEnte findByCdEnte(BigDecimal cdEnte);
	
	/**
	 * Find by codice ente digit.
	 *
	 * @param cdEnteDigit  codice ente digit
	 * @return list
	 */
	List<BdlEnte> findByCdEnteDigit(BigDecimal cdEnteDigit);
	
	@Query(value = "SELECT b.* FROM BDL_ENTE b WHERE b.CD_ENTE=?1 AND b.CD_ENTE IN ("+
			"SELECT vOO.I_CD_ISTITUTO FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?2)", nativeQuery = true)
	public BdlEnte findIstitutoByCdEnteAndStatoOggetto(BigDecimal cdEnte, String statoOggetto);
	
}
