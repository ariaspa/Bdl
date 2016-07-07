package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlTecnicaGrafica;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlTecnicaGraficaRepository.
 */
public interface BdlTecnicaGraficaRepository extends PagingAndSortingRepository<BdlTecnicaGrafica, BigDecimal> {
	
	/**
	 * Find by codice tecnica grafica.
	 *
	 * @param cdTecnicaGrafica  codice tecnica grafica
	 * @return bdl tecnica grafica
	 */
	BdlTecnicaGrafica findByCdTecnicaGrafica(BigDecimal cdTecnicaGrafica);
	
	/**
	 * Find tecniques by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT tg.* FROM BDL_TECNICA_GRAFICA tg WHERE tg.CD_TECNICA_GRAFICA IS NOT NULL AND tg.CD_TECNICA_GRAFICA IN ("+
			"SELECT vOO.O_CD_TECNICA_GRAFICA FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlTecnicaGrafica> findTecniquesByStatoOggetto(String statoOggetto);
}
