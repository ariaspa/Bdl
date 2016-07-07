package it.lispa.bdl.server.services;

import static org.quartz.TriggerKey.triggerKey;
import it.lispa.bdl.commons.domain.BdlQrtzTrigger;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.quartz.QuartzBaseProcess;
import it.lispa.bdl.server.quartz.QuartzEmailSender;
import it.lispa.bdl.server.repository.BdlQrtzTriggerRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.GridDataUtils;
import it.lispa.bdl.shared.dto.SchedulatoreTriggerDTO;
import it.lispa.bdl.shared.services.SchedulatoreTriggersService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class AmmGestUtentiServiceImpl.
 */
@Service("schedulatoreTriggersService")
@Repository
@Transactional
public class SchedulatoreTriggersServiceImpl extends BaseServiceImpl implements SchedulatoreTriggersService {

	/** log. */
	private static Logger log = Logger.getLogger(SchedulatoreTriggersServiceImpl.class);

	/** sched factory. */
	@Autowired
	private SchedulerFactoryBean schedFactory;
	
	/** item repository. */
	@Autowired
	private BdlQrtzTriggerRepository itemRepository;
	
	/* METODI CRUD STANDARD */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.SchedulatoreTriggersService#getItems(java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Transactional
	public PagingLoadResult<SchedulatoreTriggerDTO> getItems(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit,
			String orderFieldDefault, String orderDirDefault) {
		List<SchedulatoreTriggerDTO> items = new ArrayList<SchedulatoreTriggerDTO>();
		for (BdlQrtzTrigger item : itemRepository.findAll()) {
			SchedulatoreTriggerDTO itm = mapper.mapBdlQrtzTriggerToSchedulatoreTriggerDTO(item);
			if(isRecordAccessible(itm) && !item.getJobName().equals(QuartzEmailSender.JOBNAME)){
				items.add(itm);	
			}
		}
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItems(java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItems(List<SchedulatoreTriggerDTO> items) throws AsyncServiceException {
		for (SchedulatoreTriggerDTO item : items) {
			cancellaItemByCodice(item.getCodicePk());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItemByCodice(java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItemByCodice(String codicePk) throws AsyncServiceException {

	    Scheduler scheduler = schedFactory.getScheduler();
		for (BdlQrtzTrigger item : itemRepository.findAll()) {
			SchedulatoreTriggerDTO trg = mapper.mapBdlQrtzTriggerToSchedulatoreTriggerDTO(item);
			if(isRecordAccessible(trg)){
				String codiceTrg = trg.getCodicePk();
				if(codiceTrg.equals(codicePk)){
					try {
						if(QuartzBaseProcess.ESITORUNNING.endsWith(trg.getDsEsito())){
							throw new AsyncServiceException("Non posso cancellare processi in esecuzione");
						}
						scheduler.unscheduleJob(triggerKey(item.getComp_id().getTriggerName(),item.getComp_id().getTriggerGroup()));
					} catch (SchedulerException e) {
						log.debug(e.getMessage());
						
						AsyncServiceException myAsyncServiceException = new AsyncServiceException(e.getMessage());
						myAsyncServiceException.setStackTrace(e.getStackTrace());
						throw myAsyncServiceException;
					}
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
	private Boolean isRecordAccessible(SchedulatoreTriggerDTO itm){
		
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
