package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.services.MonitoraggioCronoService;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class MonitoraggoCronoServiceImpl.
 */
@Service("monitoraggioCronoService")
@Repository
@Transactional
public class MonitoraggoCronoServiceImpl extends BaseServiceImpl implements MonitoraggioCronoService {
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.MonitoraggioCronoService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public OggettoDTO getOggetto(BigDecimal cdOggetto) {
		
		BdlOggettoOriginale oggetto = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto);
	}
}