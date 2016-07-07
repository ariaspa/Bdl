package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlQrtzBdlJobs;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.quartz.QuartzEmailSender;
import it.lispa.bdl.server.repository.BdlQrtzBdlJobsRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.GridDataUtils;
import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.services.SchedulatoreJobsService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class AmmGestUtentiServiceImpl.
 */
@Service("schedulatoreJobsService")
@Repository
@Transactional
public class SchedulatoreJobsServiceImpl extends BaseServiceImpl implements SchedulatoreJobsService {

	/** item repository. */
	@Autowired
	private BdlQrtzBdlJobsRepository itemRepository;
	
	/* METODI CRUD STANDARD */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.SchedulatoreJobsService#getItems(java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Transactional
	public PagingLoadResult<SchedulatoreJobsDTO> getItems(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit,
			String orderFieldDefault, String orderDirDefault) {
		List<SchedulatoreJobsDTO> items = new ArrayList<SchedulatoreJobsDTO>();
		for (BdlQrtzBdlJobs item : itemRepository.findAll()) {
			SchedulatoreJobsDTO itm = mapper.mapBdlQrtzBdlJobsToSchedulatoreJobsDTO(item);
			if(isRecordAccessible(itm) && !item.getDsTipo().equals(QuartzEmailSender.JOBNAME)){
				items.add(itm);
			}
		}
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItems(java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItems(List<SchedulatoreJobsDTO> items) throws AsyncServiceException {
		for (SchedulatoreJobsDTO item : items) {
			cancellaItemByCodice(item.getCdBdlJobs());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItemByCodice(java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItemByCodice(BigDecimal codicePk) throws AsyncServiceException {
		for (BdlQrtzBdlJobs item : itemRepository.findAll()) {
			SchedulatoreJobsDTO trg = mapper.mapBdlQrtzBdlJobsToSchedulatoreJobsDTO(item);
			if(isRecordAccessible(trg)){
				if(trg.getCdBdlJobs().equals(codicePk)){
					itemRepository.delete(item);
				}
			}
		}
	}

	/* METODI CUSTOM */
	/**
	 * Controlla se record accessible.
	 *
	 * @param itm  itm
	 * @return boolean
	 */
	private Boolean isRecordAccessible(SchedulatoreJobsDTO itm){
		BdlUtente ut = getActiveUtente();
		if(ut.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_AMMINISTRATORE)){
			return true;
		}
		if(ut.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_SUPERVISORE)){
			return true;
		}
		if(ut.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_CATALOGATORE) && itm.getCdUtente().equals(ut.getCdUtente())){
			return true;
		}
		if(ut.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_DIGITALIZZATORE) && itm.getCdUtente().equals(ut.getCdUtente())){
			return true;
		}
		return false;
	}
}
