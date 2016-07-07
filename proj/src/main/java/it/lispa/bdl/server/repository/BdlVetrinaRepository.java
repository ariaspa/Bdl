package it.lispa.bdl.server.repository;

import it.lispa.bdl.commons.domain.BdlVetrina;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BdlVetrinaRepository extends PagingAndSortingRepository<BdlVetrina, BigDecimal> {
	
	@Query(value = "SELECT * FROM(" +
		"SELECT v.* FROM BDL_VETRINA v " +
    		"WHERE v.CD_OGGETTO_ORIGINALE=?1 AND v.FL_ATTIVO='Y' ORDER BY v.DT_INSERITOIL DESC" +
    	") WHERE ROWNUM<=?2", nativeQuery=true)
	public List<BdlVetrina> findOggettiAttiviByCodiceOrderByDtInseritoilDesc(BigDecimal cdOggettoOriginale, Integer rowNum);
	
	@Query(value = "SELECT * FROM(" +
		"SELECT v.* FROM BDL_VETRINA v " +
	    	"WHERE v.CD_OGGETTO_ORIGINALE=?1 AND v.FL_ATTIVO='Y' AND " + 
				"(DT_SCADEIL IS NULL OR DT_SCADEIL>=sysdate) ORDER BY v.DT_INSERITOIL DESC" +
		") WHERE ROWNUM<=?2", nativeQuery=true)
	public List<BdlVetrina> findOggettiAttiviNonScadutiByCodiceOrderByDtInseritoilDesc(BigDecimal cdOggettoOriginale, Integer rowNum);
}
