package it.lispa.bdl.server.quartz;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class QuartzCreaImmagini.
 */
public class QuartzCreaImmagini extends QuartzBaseProcess {

	/** log. */
	private static Logger log = Logger.getLogger(QuartzCreaImmagini.class);

    /** La Costante JOBNAME. */
    public final static String JOBNAME = "BdlCreazioneImmagini";
    
    /** La Costante TRIGGERNAME. */
    public final static String TRIGGERNAME = "TrgCreazioneImg";

    /** La Costante JOBHUMANNAME. */
    public final static String JOBHUMANNAME = "Creazione Immagini";
    
    /** La Costante PCDOGGETTO. */
    public final static String PCDOGGETTO= "cdOggettoOriginale";

    /** La Costante PNRIMMAGINI. */
    public final static String PNRIMMAGINI = "nrImmaginiDigitalizzate";
    
    /** La Costante PCDUTENTE. */
    public final static String PCDUTENTE = "cdUtente";
    
    /** La Costante PCDDIGITALIZZATORE. */
    public final static String PCDDIGITALIZZATORE = "cdDigitalizzatore";
    
	/** sched factory. */
	@Autowired
	private SchedulerFactoryBean schedFactory;
    
    /** caricamento service. */
    @Autowired
	CaricamentoImmaginiService caricamentoService;
	
    /**
     * Istanzia un nuovo quartz crea immagini.
     */
    public QuartzCreaImmagini() {
		// do nothing...
    }
    
    /* (non-Javadoc)
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	log.debug("Running @ " + new Date());
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        
		/* Definizione Scheduler */
	    Scheduler scheduler = context.getScheduler();
	    
	    final Date runTimeForCaricamentoImmagini = evenMinuteDate(new Date(System.currentTimeMillis()+1*15*1000));
	    
        Trigger trg = context.getTrigger();
        
        JobDataMap dataMapTrigger = trg.getJobDataMap();

        try {
	        BigDecimal cdOggetto = new BigDecimal(dataMapTrigger.getInt(QuartzCreaImmagini.PCDOGGETTO));
	        BigDecimal nrImmagini = new BigDecimal(dataMapTrigger.getInt(QuartzCreaImmagini.PNRIMMAGINI));
	        BigDecimal cdUtente = new BigDecimal(dataMapTrigger.getInt(QuartzCreaImmagini.PCDUTENTE));
	        BigDecimal cdDigitalizzatore = new BigDecimal(dataMapTrigger.getInt(QuartzCreaImmagini.PCDDIGITALIZZATORE));
	        
	        log.debug("Running with: "
		        	+ "cdOggetto=" + cdOggetto + " "
		        	+ "nrImmagini=" + nrImmagini + " "
		        	+ "cdUtente=" + cdUtente + " "
		        	+ "cdDigitalizzatore=" + cdDigitalizzatore);
	        
			String myErrorLog = caricamentoService.creaImmaginiQrtz(cdOggetto, nrImmagini, cdUtente, cdDigitalizzatore);
			
        	if(!"".equals(myErrorLog)) {
            	dataMapTrigger.put(QuartzCreaImmagini.PESITO, QuartzCreaImmagini.ESITOKO);		
            	dataMapTrigger.put(QuartzCreaImmagini.PEXCEPTIONMSG, myErrorLog);
            	log.debug(myErrorLog);
        	} else {
        		dataMapTrigger.put(QuartzCreaImmagini.PESITO, QuartzCreaImmagini.ESITOOK);

        		/* Schedulo il Job di CARICAMENTO IMMAGINI */
    		    /* Definizione Job */
    		    JobDetail job = scheduler.getJobDetail(jobKey(QuartzCaricaImmagini.JOBNAME));
    		    if(job==null){
    		    	job = newJob(QuartzCaricaImmagini.class)
    		    			.withIdentity(QuartzCaricaImmagini.JOBNAME)
    		    			.storeDurably(true)
    		    			.build();
    		    	scheduler.addJob(job, true);
    		    }
    		    /* Definizione Trigger */
    		    Trigger trigger = newTrigger().withIdentity(QuartzCaricaImmagini.buildTriggerName(cdOggetto.toString(), runTimeForCaricamentoImmagini), cdUtente.toString())
    	    			.usingJobData(QuartzCaricaImmagini.PCDOGGETTO, Integer.valueOf(cdOggetto.intValue()))
    	    			.usingJobData(QuartzCaricaImmagini.PNRIMMAGINI, Integer.valueOf(nrImmagini.intValue()))
    	    			.usingJobData(QuartzCaricaImmagini.PCDUTENTE,Integer.valueOf(cdUtente.intValue()))
    	    			.usingJobData(QuartzCaricaImmagini.PCDDIGITALIZZATORE,Integer.valueOf(cdDigitalizzatore.intValue()))
    	    			.startAt(runTimeForCaricamentoImmagini)
    	    			.forJob(jobKey(QuartzCaricaImmagini.JOBNAME))
    	    			.withSchedule(simpleSchedule()
    	    					.withMisfireHandlingInstructionFireNow())
    	    			.build();
    		    /* Schedulo... */
    		    scheduler.scheduleJob(trigger);
        	}
		} catch (AsyncServiceException e) {
        	dataMapTrigger.put(QuartzCreaImmagini.PESITO, QuartzCreaImmagini.ESITOKO);		
        	dataMapTrigger.put(QuartzCreaImmagini.PEXCEPTIONMSG, e.getMessage());		
        	log.debug(e.getMessage());
		} catch (Exception e) {
        	dataMapTrigger.put(QuartzCreaImmagini.PESITO, QuartzCreaImmagini.ESITOEXCEPTION);		
        	dataMapTrigger.put(QuartzCreaImmagini.PEXCEPTIONMSG, e.getMessage());
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
    	return QuartzCreaImmagini.TRIGGERNAME+"-"+identifier+"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(runTime);
    }
}
