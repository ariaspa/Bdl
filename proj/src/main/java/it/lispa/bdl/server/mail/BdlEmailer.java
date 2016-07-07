package it.lispa.bdl.server.mail;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlMail;
import it.lispa.bdl.commons.domain.BdlQrtzTrigger;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.auth.RequestGenericFilter;
import it.lispa.bdl.server.quartz.QuartzEmailSender;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlMailRepository;
import it.lispa.bdl.server.repository.BdlQrtzTriggerRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.BdlServerProperties;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Class BdlEmailer.
 */
@Component
public class BdlEmailer {

	/** La Costante UTENTE_NOME. */
	public static final String UTENTE_NOME = "#UTENTE.NOME#";
	
	/** La Costante UTENTE_COGNOME. */
	public static final String UTENTE_COGNOME = "#UTENTE.COGNOME#";
	
	/** La Costante UTENTE_CF. */
	public static final String UTENTE_CF = "#UTENTE.CF#";
	
	/** La Costante RUOLO_NOME. */
	public static final String RUOLO_NOME = "#RUOLO.NOME#";
	
	/** La Costante ENTE_NOME. */
	public static final String ENTE_NOME = "#ENTE.NOME#";
	
	/** La Costante OGGETTO_TITOLO. */
	public static final String OGGETTO_TITOLO = "#OGGETTO.TITOLO#";
	
	/** La Costante OGGETTO_ID. */
	public static final String OGGETTO_ID = "#OGGETTO.ID#";	
	
	/** log. */
	private static Logger log = Logger.getLogger(RequestGenericFilter.class);
	
	/**
	 * Class FieldsMapper.
	 */
	public class FieldsMapper {
		
		
		/** map. */
		Map<String,String> map;
		
		
		/**
		 * Istanzia un nuovo fields mapper.
		 *
		 * @param map  map
		 * @throws AsyncServiceException async service exception
		 */
		public FieldsMapper(Map<String,String> map) throws AsyncServiceException{
			
			List<String> listFields = getFields();
			
			Set<String> chiavi = map.keySet();
			Iterator<String> chiaviItr = chiavi.iterator();
			while(chiaviItr.hasNext()){
				String chiave = chiaviItr.next();
				if(!listFields.contains(chiave)){
					throw new AsyncServiceException("La chiave "+chiave+" specificata nella mappa di configurazione del BdlEmailer non � corretta");
				}
			}
			this.map = map;
		}
		
		/**
		 * Legge fields.
		 *
		 * @return fields
		 */
		private List<String> getFields(){
			List<String> listFields = new ArrayList<String>();
			listFields.add(UTENTE_NOME);
			listFields.add(UTENTE_COGNOME);
			listFields.add(UTENTE_CF);
			listFields.add(RUOLO_NOME);
			listFields.add(ENTE_NOME);
			listFields.add(OGGETTO_TITOLO);
			listFields.add(OGGETTO_ID);
			
			return listFields;
		}
		
		/**
		 * Compila comunicazione.
		 *
		 * @param oggetto  oggetto
		 * @param testo  testo
		 * @return list
		 */
		public List<String> compilaComunicazione(String oggetto, String testo) {
			List<String> strToRet = new ArrayList<String>();
			
			Set<String> chiavi = map.keySet();
			Iterator<String> chiaviItr = chiavi.iterator();
			while(chiaviItr.hasNext()){
				String chiave = chiaviItr.next();
				String valore = map.get(chiave);
				
				oggetto = oggetto.replaceAll(chiave, valore);				
				testo = testo.replaceAll(chiave, valore);
			}
			
			strToRet.add(oggetto);
			strToRet.add(testo);
			
			return strToRet;
		}
	}
			
	/** mail repository. */
	@Autowired
	BdlMailRepository mailRepository;

	/** utente repository. */
	@Autowired
	private BdlUtenteRepository utenteRepository;

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;

	/** bdl ente repository. */
	@Autowired 
	private BdlEnteRepository bdlEnteRepository;
	
	/** bdl ente utente repository. */
	@Autowired 
	private BdlEnteUtenteRepository bdlEnteUtenteRepository;
	
	/** mail client. */
	@Autowired
	private EmailSender mailClient;

	/** properties. */
	@Autowired
	private BdlServerProperties properties;
	
	/** sched factory. */
	@Autowired
	private SchedulerFactoryBean schedFactory;
	
	@Autowired
	private BdlQrtzTriggerRepository bdlQrtzTriggerRepository;

	/**
	 * Send mail.
	 *
	 * @param cdUtente  codice utente
	 * @param cdComunicazione  codice comunicazione
	 * @param recipient  recipient
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 * @throws SchedulerException scheduler exception
	 */
	public void sendMail(BigDecimal cdUtente, String cdComunicazione, String recipient, Map<String,String> map) throws AsyncServiceException {
		log.debug("[sendMail] Entro nel metodo ");

		Scheduler scheduler = schedFactory.getScheduler();
	    
		//Date runTime = new Date(System.currentTimeMillis()+1*15*1000);
	    Date runTime = evenMinuteDate(new Date(System.currentTimeMillis()+1*300*1000));
	    
	    JobDetail job;
		try {
		    log.debug("[sendMail] Creo il job ");
		    job = scheduler.getJobDetail(jobKey(QuartzEmailSender.JOBNAME));
		    if(job==null){
		    	job = newJob(QuartzEmailSender.class)
		    			.withIdentity(QuartzEmailSender.JOBNAME)
		    			.storeDurably(true)
		    			.build();
		    	scheduler.addJob(job, true);
		    }
		    
		    log.debug("[sendMail] Popolo i dati del job ");
		    JobDataMap dataMapTrigger = new JobDataMap();
		    dataMapTrigger.putAsString(QuartzEmailSender.PCDUTENTE, Integer.valueOf(cdUtente.intValue()));
		    dataMapTrigger.put(QuartzEmailSender.PCDCOMUNICAZIONE, cdComunicazione);
		    dataMapTrigger.put(QuartzEmailSender.PRECIPIENT, recipient);
		    dataMapTrigger.put(QuartzEmailSender.PMAP, map);
		    
		    log.debug("[sendMail] Creo il trigger ");
		    
			String triggerName = checkTriggerNameForBdlEmailer(cdComunicazione);
		    Trigger trigger = newTrigger().withIdentity(QuartzEmailSender.buildTriggerName(triggerName,runTime), cdUtente.toPlainString())
	    			.usingJobData(dataMapTrigger)
	    			.startAt(runTime)
	    			.forJob(jobKey(QuartzEmailSender.JOBNAME))
	    			.withSchedule(simpleSchedule()
	    					.withMisfireHandlingInstructionFireNow())
	    			.build();
		    
		    log.debug("[sendMail] Schedulo il job ");
		    scheduler.scheduleJob(trigger);
		    
		} catch (SchedulerException e) {
			AsyncServiceException myAsyncServiceException = new AsyncServiceException(e.getMessage());
			myAsyncServiceException.setStackTrace(e.getStackTrace());
			throw myAsyncServiceException;
		}
	}
	
	private String checkTriggerNameForBdlEmailer(String cdComunicazione) {
		String triggerName = cdComunicazione+"_"+getRandomInteger();
		log.debug("[sendMail] triggerName="+triggerName);
		List<BdlQrtzTrigger> myTrigger = bdlQrtzTriggerRepository.findByTriggerNameLike("%"+triggerName+"%");
		if(myTrigger.isEmpty()) {
			return triggerName;
		}
		return checkTriggerNameForBdlEmailer(cdComunicazione);
	}

	public void sendMailQrtz(BigDecimal cdUtente, String cdComunicazione, String recipient, Map<String,String> map) throws AsyncServiceException {

		FieldsMapper fieldsMap = new FieldsMapper(map);
		
		if (cdComunicazione != null && recipient!=null && map!=null) {
			try {
				BdlMail comunicazione = mailRepository.findByCdComunicazione(cdComunicazione);
				if(comunicazione!=null){				
					
					List<String> strRet = fieldsMap.compilaComunicazione(comunicazione.getDsOggetto(), comunicazione.getDsCorpo());
					String oggettoMail = strRet.get(0);
					String corpoMail = strRet.get(1);
					log.debug("[sendMail] oggettoMail=\""+oggettoMail+"\" --- corpoMail=\""+corpoMail+"\"");
					
					SimpleMailMessage mail = mailClient.getSimpleMailMessage();

					mail.setFrom(properties.getMailSenderDefaultFrom());
					
					if(properties.getMailSenderDefaultCc()!=null && !"".equals(properties.getMailSenderDefaultCc().trim())) {
						mail.setCc(properties.getMailSenderDefaultCc().trim().split(" "));
					}
					
					if(properties.getMailSenderDefaultBcc()!=null && !"".equals(properties.getMailSenderDefaultBcc().trim())) {
						mail.setBcc(properties.getMailSenderDefaultBcc().trim().split(" "));
					}
					
					String debugAdvice = "";
					
					if(properties.getMailSenderDebugRecipient()!=null && !"".equals(properties.getMailSenderDebugRecipient().trim())){
						mail.setTo(properties.getMailSenderDebugRecipient().trim());
						
						debugAdvice = "\n\n-------------------------DEBUG--------------------------";
						debugAdvice+= "\n\nDestinatario reale della comunicazione: "+recipient;
						debugAdvice+= "\ncdComunicazione: "+cdComunicazione;
					}else{
						mail.setTo(recipient);
					}
					
					mail.setSubject(oggettoMail);
					mail.setText(corpoMail+debugAdvice);
					mail.setSentDate(new Date());
			    
				    
					JavaMailSenderImpl ms = (JavaMailSenderImpl) mailClient.getMailSender();
					//Properties props = ms.getJavaMailProperties();
					ms.send(mail);
					
				}
			} catch (Exception e1) {
				log.error("Eccezione rilevata nell'invio della mail ", e1);
				
				AsyncServiceException myAsyncServiceException = new AsyncServiceException("Eccezione nell'invio della email: "+e1.getMessage());
				myAsyncServiceException.setStackTrace(e1.getStackTrace());
				throw myAsyncServiceException;
			}
		}
	}
	
	/**
	 * Send mail to supervisors.
	 *
	 * @param cdUtente  codice utente
	 * @param cdComunicazione  codice comunicazione
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 */
	public void sendMailToSupervisors(BigDecimal cdUtente, String cdComunicazione, Map<String, String> map) throws AsyncServiceException {
		List<BdlUtente> destinatari = utenteRepository.findByCdRuolo(BdlServerConstants.BDL_RUOLO_CD_SUPERVISORE);
		for(BdlUtente destinatario:destinatari){
			if(destinatario.getDnEmail()!=null && !"".equals(destinatario.getDnEmail())){
				sendMail(cdUtente,cdComunicazione, destinatario.getDnEmail(), map);
			}
		}		
	}

	/**
	 * Send mail to user.
	 *
	 * @param cdUtente  codice utente
	 * @param cdCodiceFiscale  codice codice fiscale
	 * @param cdComunicazione  codice comunicazione
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 */
	public void sendMailToUser(BigDecimal cdUtente, String cdCodiceFiscale, String cdComunicazione, Map<String, String> map) throws AsyncServiceException {
		BdlUtente destinatario = utenteRepository.findByCdCodiceFiscale(cdCodiceFiscale);
		if(destinatario.getDnEmail()!=null && !"".equals(destinatario.getDnEmail())){
			sendMail(cdUtente,cdComunicazione, destinatario.getDnEmail(), map);
		}
	}

	/**
	 * Send mail to user.
	 *
	 * @param cdUtente  codice utente
	 * @param cdutente  cdutente
	 * @param cdComunicazione  codice comunicazione
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 */
	public void sendMailToUser(BigDecimal cdUtente, BigDecimal cdutente, String cdComunicazione, Map<String, String> map) throws AsyncServiceException {
		BdlUtente destinatario = utenteRepository.findByCdUtente(cdutente);
		if(destinatario.getDnEmail()!=null && !"".equals(destinatario.getDnEmail())){
			sendMail(cdUtente,cdComunicazione, destinatario.getDnEmail(), map);
		}
	}
	
	/**
	 * Send mail to catalogatore.
	 *
	 * @param cdUtenteCorrente  codice utente corrente
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdComunicazione  codice comunicazione
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 */
	public void sendMailToCatalogatore(BigDecimal cdUtenteCorrente, BigDecimal cdOggettoOriginale, String cdComunicazione, Map<String, String> map) throws AsyncServiceException {
		
		VOggettoDTO oggetto = vOggetti.findOggettoByCodice(cdOggettoOriginale);
		if(oggetto!=null){
			if(oggetto.getI_CdIstituto()!=null){
				BdlEnte ente = bdlEnteRepository.findByCdEnte(oggetto.getI_CdIstituto());
				if(ente!=null){
					List<BdlEnteUtente> enteUtenti = bdlEnteUtenteRepository.findByCdEnte(ente.getCdEnte());
					for(BdlEnteUtente enteUtente:enteUtenti){
						sendMailToUser(cdUtenteCorrente, enteUtente.getCdUtente(),cdComunicazione,map);
					}
					
				}
			}
		}
	}
	
	/**
	 * Send mail to digitalizzatore.
	 *
	 * @param cdUtenteCorrente  codice utente corrente
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdComunicazione  codice comunicazione
	 * @param map  map
	 * @throws AsyncServiceException async service exception
	 */
	public void sendMailToDigitalizzatore(BigDecimal cdUtenteCorrente,BigDecimal cdOggettoOriginale, String cdComunicazione, Map<String, String> map) throws AsyncServiceException {

		VOggettoDTO oggetto = vOggetti.findOggettoByCodice(cdOggettoOriginale);
		if(oggetto!=null){
			if(oggetto.getD_CdDigitalizzatore()!=null){
				BdlEnte ente = bdlEnteRepository.findByCdEnte(oggetto.getD_CdDigitalizzatore());
				if(ente!=null){
					List<BdlEnteUtente> enteUtenti = bdlEnteUtenteRepository.findByCdEnte(ente.getCdEnte());
					for(BdlEnteUtente enteUtente:enteUtenti){
						sendMailToUser(cdUtenteCorrente, enteUtente.getCdUtente(),cdComunicazione,map);
					}					
				}
			}
		}
	}
	
	public Integer getRandomInteger() {
		final Integer estrSupForRandom = Integer.valueOf(999999);
	    Random randomGenerator = new Random();
	    return new Integer(randomGenerator.nextInt(estrSupForRandom));
	}
}
