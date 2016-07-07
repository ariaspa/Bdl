package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlLivelloBiblio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlLivelloBiblioRepository.
 */
public interface BdlLivelloBiblioRepository extends PagingAndSortingRepository<BdlLivelloBiblio, BigDecimal> {
	
	/**
	 * Find by codice livello biblio.
	 *
	 * @param codice  codice
	 * @return bdl livello biblio
	 */
	BdlLivelloBiblio findByCdLivelloBiblio(BigDecimal codice);

	/**
	 * Find kinds by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT lb.* FROM BDL_LIVELLO_BIBLIO lb WHERE lb.CD_LIVELLO_BIBLIO IS NOT NULL AND lb.CD_LIVELLO_BIBLIO IN ("+
			"SELECT vOO.O_CD_LIVELLO_BIBLIO FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlLivelloBiblio> findKindsByStatoOggetto(String statoOggetto);
}
