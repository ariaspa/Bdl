package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlFormato;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface BdlFormatoRepository.
 */
public interface BdlFormatoRepository extends PagingAndSortingRepository<BdlFormato, BigDecimal> {
	
	/**
	 * Find by flag regola naming and codice tipo oggetto.
	 *
	 * @param regola  regola
	 * @param codice  codice
	 * @return list
	 */
	List<BdlFormato> findByFlRegolaNamingAndCdTipoOggetto(Boolean regola, BigDecimal codice);
	
	/**
	 * Find by codice tipo oggetto.
	 *
	 * @param codice  codice
	 * @return list
	 */
	List<BdlFormato> findByCdTipoOggetto(BigDecimal codice);
	
	/**
	 * Find by denominazione nome formato and codice tipo oggetto.
	 *
	 * @param formato  formato
	 * @param codice  codice
	 * @return bdl formato
	 */
	BdlFormato findByDnNomeFormatoAndCdTipoOggetto(String formato, BigDecimal codice);
	
	/**
	 * Find by flag defaultforthumb and codice tipo oggetto.
	 *
	 * @param regola  regola
	 * @param codice  codice
	 * @return list
	 */
	List<BdlFormato> findByFlDefaultforthumbAndCdTipoOggetto(Boolean regola, BigDecimal codice);
	
	/**
	 * Find by flag defaultforreader and codice tipo oggetto.
	 *
	 * @param regola  regola
	 * @param codice  codice
	 * @return list
	 */
	List<BdlFormato> findByFlDefaultforreaderAndCdTipoOggetto(Boolean regola, BigDecimal codice);
	
	/**
	 * Find by flag defaultforzoom and codice tipo oggetto.
	 *
	 * @param regola  regola
	 * @param codice  codice
	 * @return list
	 */
	List<BdlFormato> findByFlDefaultforzoomAndCdTipoOggetto(Boolean regola, BigDecimal codice);

	/**
	 * Find by descrizione natura and codice tipo oggetto.
	 *
	 * @param dsNatura  descrizione natura
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return list
	 */
	List<BdlFormato> findByDsNaturaAndCdTipoOggetto(String dsNatura, BigDecimal cdTipoOggetto);
	/**
	 * Find by descrizione natura or descrizione natura and codice tipo oggetto.
	 *
	 * @param dsNatura1  descrizione natura1
	 * @param dsNatura2  descrizione natura2
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return list
	 */
	List<BdlFormato> findByDsNaturaInAndCdTipoOggetto(List<String> dsNatura, BigDecimal cdTipoOggetto);
}
