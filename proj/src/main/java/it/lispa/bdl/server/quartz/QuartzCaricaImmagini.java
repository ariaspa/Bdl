package it.lispa.bdl.server.quartz;

import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class QuartzCaricaImmagini.
 */
public class QuartzCaricaImmagini extends QuartzBaseProcess {

	/** log. */
	private static Logger log = Logger.getLogger(QuartzCaricaImmagini.class);

    /** La Costante JOBNAME. */
    public final static String JOBNAME = "BdlCaricamentoImmagini";
    
    /** La Costante TRIGGERNAME. */
    public final static String TRIGGERNAME = "TrgCaricamentoImg";

    /** La Costante JOBHUMANNAME. */
    public final static String JOBHUMANNAME = "Caricamento Immagini";
    
    /** La Costante PCDOGGETTO. */
    public final static String PCDOGGETTO= "cdOggettoOriginale";

    /** La Costante PNRIMMAGINI. */
    public final static String PNRIMMAGINI = "nrImmaginiDigitalizzate";
    
    /** La Costante PCDUTENTE. */
    public final static String PCDUTENTE = "cdUtente";
    
    /** La Costante PCDDIGITALIZZATORE. */
    public final static String PCDDIGITALIZZATORE = "cdDigitalizzatore";
    
    
    /** caricamento service. */
    @Autowired
	CaricamentoImmaginiService caricamentoService;
	
    /**
     * Istanzia un nuovo quartz carica immagini.
     */
    public QuartzCaricaImmagini() {
		// do nothing...
    }
    
    /* (non-Javadoc)
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	log.debug("Running @ " + new Date());
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        
        Trigger trg = context.getTrigger();
        
        JobDataMap dataMapTrigger = trg.getJobDataMap();

        try {
	        BigDecimal cdOggetto = new BigDecimal(dataMapTrigger.getInt(QuartzCaricaImmagini.PCDOGGETTO));
	        BigDecimal nrImmagini = new BigDecimal(dataMapTrigger.getInt(QuartzCaricaImmagini.PNRIMMAGINI));
	        BigDecimal cdUtente = new BigDecimal(dataMapTrigger.getInt(QuartzCaricaImmagini.PCDUTENTE));
	        BigDecimal cdDigitalizzatore = new BigDecimal(dataMapTrigger.getInt(QuartzCaricaImmagini.PCDDIGITALIZZATORE));
	        
	        log.debug("Running with: "
	        	+ "cdOggetto=" + cdOggetto + " "
	        	+ "nrImmagini=" + nrImmagini + " "
	        	+ "cdUtente=" + cdUtente + " "
	        	+ "cdDigitalizzatore=" + cdDigitalizzatore);
	        
        	caricamentoService.caricaImmaginiQrtz(cdOggetto, nrImmagini, cdUtente, cdDigitalizzatore);
        	dataMapTrigger.put(QuartzCaricaImmagini.PESITO, QuartzCaricaImmagini.ESITOOK);
		} catch (AsyncServiceException e) {
        	dataMapTrigger.put(QuartzCaricaImmagini.PESITO, QuartzCaricaImmagini.ESITOKO);		
        	dataMapTrigger.put(QuartzCaricaImmagini.PEXCEPTIONMSG, e.getMessage());		
        	log.debug(e.getMessage());
			
		} catch (Exception e) {
        	dataMapTrigger.put(QuartzCaricaImmagini.PESITO, QuartzCaricaImmagini.ESITOEXCEPTION);		
        	dataMapTrigger.put(QuartzCaricaImmagini.PEXCEPTIONMSG, e.getMessage());
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
    	return QuartzCaricaImmagini.TRIGGERNAME+"-"+identifier+"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(runTime);
    }
}
