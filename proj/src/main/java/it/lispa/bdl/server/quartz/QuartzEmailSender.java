package it.lispa.bdl.server.quartz;

import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class QuartzEmailSender.
 */
public class QuartzEmailSender extends QuartzBaseProcess {

    /** _log. */
    private static Logger log = LoggerFactory.getLogger(QuartzEmailSender.class);

    /** La Costante JOBNAME. */
    public final static String JOBNAME = "BdlEmailer";
    
    /** La Costante TRIGGERNAME. */
    public final static String TRIGGERNAME = "TrgEmailer";
    
    /** La Costante JOBHUMANNAME. */
    public final static String JOBHUMANNAME = "Invio email";

    /** La Costante PCDUTENTE. */
    public final static String PCDUTENTE = "cdUtente";
    
    /** La Costante PCDCOMUNICAZIONE. */
    public final static String PCDCOMUNICAZIONE = "cdComunicazione";
    
    /** La Costante PRECIPIENT. */
    public final static String PRECIPIENT = "recipient";
    
    /** La Costante PMAP. */
    public final static String PMAP = "map";
    
	/** bdl emailer. */
	@Autowired
	protected BdlEmailer bdlEmailer;
	
    /**
     * Istanzia un nuovo quartz email sender.
     */
    public QuartzEmailSender() {
		// do nothing...
    }

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	
    	log.debug("Running @ " + new Date());
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        
        Trigger trg = context.getTrigger();
        
        JobDataMap dataMapTrigger = trg.getJobDataMap();

        try {
	        BigDecimal cdUtente = new BigDecimal(dataMapTrigger.getInt(QuartzEmailSender.PCDUTENTE));
	        String cdComunicazione = dataMapTrigger.getString(QuartzEmailSender.PCDCOMUNICAZIONE);
	        String recipient = dataMapTrigger.getString(QuartzEmailSender.PRECIPIENT);
	        @SuppressWarnings("unchecked")
			Map<String, String> map = new HashMap<String, String>((Map<String, String>) dataMapTrigger.get(QuartzEmailSender.PMAP));
	        
	        log.debug("Running with cdUtente=" + cdUtente+" / cdComunicazione=" + cdComunicazione+" / recipient=" + recipient);
	        
	        bdlEmailer.sendMailQrtz(cdUtente, cdComunicazione, recipient, map);
        	dataMapTrigger.put(QuartzEmailSender.PESITO, QuartzEmailSender.ESITOOK);
		} catch (AsyncServiceException e) {
        	dataMapTrigger.put(QuartzEmailSender.PESITO, QuartzEmailSender.ESITOKO);		
        	dataMapTrigger.put(QuartzEmailSender.PEXCEPTIONMSG, e.getMessage());		
        	log.debug(e.getMessage());
			
		} catch (Exception e) {
        	dataMapTrigger.put(QuartzEmailSender.PESITO, QuartzEmailSender.ESITOEXCEPTION);		
        	dataMapTrigger.put(QuartzEmailSender.PEXCEPTIONMSG, e.getMessage());
        	log.debug(e.getMessage());
        	
		}
    }
    
    /**
     * Crea trigger name.
     *
     * @param identifier  identifier
     * @param runTime  run time
     * @return string
     */
	public static String buildTriggerName(String identifier, Date runTime){
    	return QuartzEmailSender.TRIGGERNAME+"-"+identifier+"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(runTime);
    }
}
