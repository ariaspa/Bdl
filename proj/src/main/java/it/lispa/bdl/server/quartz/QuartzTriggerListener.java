/*
 * 
 */
package it.lispa.bdl.server.quartz;
import it.lispa.bdl.commons.domain.BdlQrtzBdlJobs;
import it.lispa.bdl.server.repository.BdlQrtzBdlJobsRepository;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The listener interface for receiving quartzTrigger events.
 * The class that is interested in processing a quartzTrigger
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addQuartzTriggerListener<code> method. When
 * the quartzTrigger event occurs, that object's appropriate
 * method is invoked.
 *
 * @see QuartzTriggerEvent
 */
public class QuartzTriggerListener implements TriggerListener {

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;

	/** bdl qrtz bdl jobs repository. */
	@Autowired 
	private BdlQrtzBdlJobsRepository bdlQrtzBdlJobsRepository;
	
    /** name. */
    private String name;

	/** log. */
	private static Logger log = Logger.getLogger(QuartzTriggerListener.class);

    /**
     * Istanzia un nuovo quartz trigger listener.
     */
    public QuartzTriggerListener() {
		// do nothing...
    }
    
    /**
     * Istanzia un nuovo quartz trigger listener.
     *
     * @param name  name
     */
    public QuartzTriggerListener(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#getName()
     */
    public String getName() {
        return name;
    }
    
    /**
     * Imposta name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#triggerComplete(org.quartz.Trigger, org.quartz.JobExecutionContext, org.quartz.Trigger.CompletedExecutionInstruction)
     */
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
		log.debug("[triggerComplete] Entering the method");

        JobDetail job = context.getJobDetail();
        
        JobDataMap dataMapTrigger = trigger.getJobDataMap();
        
        String esito = dataMapTrigger.getString(QuartzBaseProcess.PESITO);
        String utente = trigger.getKey().getGroup();
        
    	BigDecimal cdUtente = new BigDecimal(utente);
    	
    	String input = "n.d.";
    	String output = "n.d.";
    	String outputObj = "";
    	String outputEcc = "";
    	
        if(job.getKey().getName().equals(QuartzCaricaImmagini.JOBNAME)) {
        	/* Job=QuartzCaricaImmagini.JOBNAME */
        	
        	BigDecimal cdOggetto = new BigDecimal(dataMapTrigger.getInt(QuartzCaricaImmagini.PCDOGGETTO));
        	VOggettoDTO oggetto = vOggetti.findOggettoByCodice(cdOggetto);
        	
        	if(oggetto!=null) {
        		input = oggetto.getO_DigitalizzatoreId();
        		outputObj = oggetto.getO_DsLogAnomalia();
        	}
        	if(esito.equals(QuartzBaseProcess.ESITOEXCEPTION) || esito.equals(QuartzBaseProcess.ESITOKO)) {
        		outputEcc = dataMapTrigger.getString(QuartzBaseProcess.PEXCEPTIONMSG);
        	}
        	if(!"".equals(outputObj) || !"".equals(outputEcc)) {
        		output = "";
        		if(outputObj!=null){
        			output += outputObj;
        		}
        		if(!"".equals(outputObj) && !"".equals(outputEcc)){
            		output += "\n";
        		}
        		if(outputEcc!=null){
        			output += outputEcc;
        		}
        	}
        	BdlQrtzBdlJobs bdlJob = new BdlQrtzBdlJobs(cdUtente, trigger.getStartTime(), new Date(), QuartzCaricaImmagini.JOBNAME, esito, input, output);
        	bdlQrtzBdlJobsRepository.save(bdlJob);
        	
        } else if(job.getKey().getName().equals(QuartzCreaImmagini.JOBNAME)) {
        	/* Job=QuartzCreaImmagini.JOBNAME */
        	
        	BigDecimal cdOggetto = new BigDecimal(dataMapTrigger.getInt(QuartzCreaImmagini.PCDOGGETTO));
        	VOggettoDTO oggetto = vOggetti.findOggettoByCodice(cdOggetto);
        	
        	if(oggetto!=null) {
        		input = oggetto.getO_DigitalizzatoreId();
        	}
        	if(esito.equals(QuartzBaseProcess.ESITOEXCEPTION) || esito.equals(QuartzBaseProcess.ESITOKO)) {
        		outputEcc = dataMapTrigger.getString(QuartzBaseProcess.PEXCEPTIONMSG);
        	}
        	if(!"".equals(outputObj) || !"".equals(outputEcc)) {
        		output = "";
        		if(outputObj!=null){
        			output += outputObj;
        		}
        		if(!"".equals(outputObj) && !"".equals(outputEcc)){
            		output += "\n";
        		}
        		if(outputEcc!=null){
        			output += outputEcc;
        		}
        	}
        	BdlQrtzBdlJobs bdlJob = new BdlQrtzBdlJobs(cdUtente, trigger.getStartTime(), new Date(), QuartzCreaImmagini.JOBNAME, esito, input, output);
        	bdlQrtzBdlJobsRepository.save(bdlJob);
        	
        } else if(job.getKey().getName().equals(QuartzEmailSender.JOBNAME)) {
        	/* Job=QuartzEmailSender.JOBNAME */
        	
    		input = dataMapTrigger.getString(QuartzEmailSender.PCDCOMUNICAZIONE);
    		input += " "+dataMapTrigger.getString(QuartzEmailSender.PRECIPIENT);
        	if(esito.equals(QuartzBaseProcess.ESITOEXCEPTION) || esito.equals(QuartzBaseProcess.ESITOKO)){
        		output = dataMapTrigger.getString(QuartzBaseProcess.PEXCEPTIONMSG);
        	}
        	BdlQrtzBdlJobs bdlJob = new BdlQrtzBdlJobs(cdUtente, trigger.getStartTime(),new Date(), QuartzEmailSender.JOBNAME, esito, input, output);
        	bdlQrtzBdlJobsRepository.save(bdlJob);
        }
    }

    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#triggerFired(org.quartz.Trigger, org.quartz.JobExecutionContext)
     */
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.debug("[triggerFired] Entering the method");

    }

    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#triggerMisfired(org.quartz.Trigger)
     */
    public void triggerMisfired(Trigger trigger) {
		log.debug("[triggerMisfired] Entering the method");
	
    }

    /* (non-Javadoc)
     * @see org.quartz.TriggerListener#vetoJobExecution(org.quartz.Trigger, org.quartz.JobExecutionContext)
     */
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		log.debug("[vetoJobExecution] Entering the method");

        return false;
    }

}