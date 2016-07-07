package it.lispa.bdl.server.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Class QuartzBaseProcess.
 */
public class QuartzBaseProcess extends QuartzJobBean {

    /** La Costante PESITO. */
    public final static String PESITO= "esito";
	
	/** La Costante ESITOOK. */
	public final static String ESITOOK = "OK";
	
	/** La Costante ESITOKO. */
	public final static String ESITOKO = "KO";
	
	/** La Costante ESITORUNNING. */
	public final static String ESITORUNNING = "ESECUZIONE";
	
	/** La Costante ESITOATTESA. */
	public final static String ESITOATTESA = "ATTESA"; 
	
	/** La Costante ESITOEXCEPTION. */
	public final static String ESITOEXCEPTION = "ECCEZIONE";
    
    /** La Costante PEXCEPTIONMSG. */
    public final static String PEXCEPTIONMSG= "EXCEPTIONMSG";

    /**
     * Istanzia un nuovo quartz base process.
     */
    public QuartzBaseProcess() {
		// do nothing...
    }

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// do nothing...
	}
	
    /**
     * Legge trigger identifier.
     *
     * @param triggerName  trigger name
     * @return trigger identifier
     */
	public static String getTriggerIdentifier(String triggerName){
    	String identifier = null;
    	String[] parts = triggerName.split("-");
    	identifier = parts[1];
    	return identifier;
    }
}
