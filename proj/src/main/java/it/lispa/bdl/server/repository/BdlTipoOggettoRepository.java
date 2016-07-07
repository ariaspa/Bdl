package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlTipoOggetto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface BdlTipoOggettoRepository.
 */
public interface BdlTipoOggettoRepository extends PagingAndSortingRepository<BdlTipoOggetto, BigDecimal> {

	/**
	 * Find by codice tipo oggetto.
	 *
	 * @param codice  codice
	 * @return bdl tipo oggetto
	 */
	BdlTipoOggetto findByCdTipoOggetto(BigDecimal codice);
	
	/**
	 * Find by denominazione nome.
	 *
	 * @param strDnNome  str denominazione nome
	 * @return bdl tipo oggetto
	 */
	BdlTipoOggetto findByDnNome(String strDnNome);
	
    /**
     * Find by denominazione nome ci.
     *
     * @param nome  nome
     * @return list
     */
    @Query("SELECT b FROM BdlTipoOggetto b WHERE LOWER(b.dnNome) = LOWER(:nome)")
    List<BdlTipoOggetto> findByDnNomeCI(@Param("nome") String nome);

    /**
     * Find by distinct catalogo.
     *
     * @return list
     */
    @Query("SELECT b.dsCatalogo FROM BdlTipoOggetto b WHERE b.dsCatalogo IS NOT NULL GROUP BY b.dsCatalogo")
    List<String> findByDistinctCatalogo();
    
    /**
     * Find by codice catalogo.
     *
     * @param cdCatalogo  codice catalogo
     * @return list
     */
    List<BdlTipoOggetto> findByCdCatalogo(String cdCatalogo);
    
    /**
     * Find by denominazione nome is not null.
     *
     * @return list
     */
    public List<BdlTipoOggetto> findByDnNomeIsNotNull();
}
