package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlTipoGrafica;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlTipoGraficaRepository.
 */
public interface BdlTipoGraficaRepository extends PagingAndSortingRepository<BdlTipoGrafica, BigDecimal> {
	
	/**
	 * Find by codice tipo grafica.
	 *
	 * @param cdTipoGrafica  codice tipo grafica
	 * @return bdl tipo grafica
	 */
	BdlTipoGrafica findByCdTipoGrafica(BigDecimal cdTipoGrafica);
	
	/**
	 * Find graphic materials by stato oggetto.
	 *
	 * @param statoOggetto  stato oggetto
	 * @return list
	 */
	@Query(value = "SELECT tg.* FROM BDL_TIPO_GRAFICA tg WHERE tg.CD_TIPO_GRAFICA IS NOT NULL AND tg.CD_TIPO_GRAFICA IN ("+
			"SELECT vOO.O_CD_TIPO_GRAFICA FROM V_OGGETTI vOO WHERE vOO.O_DS_STATO LIKE ?1)", nativeQuery = true)
	public List<BdlTipoGrafica> findGraphicMaterialsByStatoOggetto(String statoOggetto);
}
