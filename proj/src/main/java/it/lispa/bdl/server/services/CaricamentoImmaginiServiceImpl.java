package it.lispa.bdl.server.services;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlPdfPagina;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.commons.domain.BdlWorkFilesystem;
import it.lispa.bdl.commons.utils.ConvertWrapper;
import it.lispa.bdl.commons.utils.IdentifyWrapper;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.quartz.QuartzCaricaImmagini;
import it.lispa.bdl.server.quartz.QuartzCreaImmagini;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlFormatoRepository;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlPdfPaginaRepository;
import it.lispa.bdl.server.repository.BdlWorkFilesystemRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.CaricamentoImmaginiUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;
import it.lispa.bdl.utils.core.BdlUtilsApp;
import it.lispa.bdl.utils.core.immagini.BaseImmaginiUtils;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.im4java.core.InfoException;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class CaricamentoImmaginiServiceImpl.
 */
@Service("caricamentoImmaginiService")
@Repository
@Transactional
public class CaricamentoImmaginiServiceImpl extends BaseServiceImpl implements CaricamentoImmaginiService {

	/** bdl immagine repository. */
	@Autowired 
	private BdlImmagineRepository bdlImmagineRepository;
	
	/** bdl ente repository. */
	@Autowired 
	private BdlEnteRepository bdlEnteRepository;
	
	/** bdl formato repository. */
	@Autowired
	private BdlFormatoRepository bdlFormatoRepository;

	/** pdf pagina repository. */
	@Autowired
	private BdlPdfPaginaRepository pdfPaginaRepository;
	
	/** work filesystem repository. */
	@Autowired
	private BdlWorkFilesystemRepository bdlWorkFilesystemRepository;
	
	/** immagini utils. */
	@Autowired
	private CaricamentoImmaginiUtils immaginiUtils;
	
	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	
	/** sched factory. */
	@Autowired
	private SchedulerFactoryBean schedFactory;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.CaricamentoImmaginiMsg", new UTF8Control());
	
	/** log. */
	private static Logger log = Logger.getLogger(CaricamentoImmaginiServiceImpl.class);
	
	/** stati oggetto originale. */
	private static List<String> statiOggettoOriginale = new ArrayList<String>() {
		private static final long serialVersionUID = -7626183828844664412L;
	{
		add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
		add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
	}};
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CaricamentoImmaginiService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public VOggettoDTO getOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		/* In prima battuta verifico che l'item sia leggibile dall'utente... */
		BigDecimal  cdEnte = this.getActiveCdEnte();
		
		VOggettoDTO itemCheck = vOggetti.findOggettoByDigitalizzatore(cdOggetto, cdEnte, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		return itemCheck;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CaricamentoImmaginiService#isNrImmaginiVisibile(java.math.BigDecimal)
	 */
	@Override
	public Boolean isNrImmaginiVisibile(BigDecimal cdOggetto) throws AsyncServiceException {
		log.debug("[isNrImmaginiVisibile] cdOggetto="+cdOggetto);
		
		Boolean isNrImmaginiVisible = true;
		
		/* In prima battuta verifico che l'item sia leggibile dall'utente... */
		BigDecimal  cdEnte = this.getActiveCdEnte();
		
		VOggettoDTO itemCheck = vOggetti.findOggettoByDigitalizzatore(cdOggetto, cdEnte, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		
		if(immaginiUtils.hasNaturaMap(itemCheck.getT_CdTipoOggetto())) {
			isNrImmaginiVisible = false;
		}
		log.debug("[isNrImmaginiVisibile] isNrImmaginiVisible="+isNrImmaginiVisible);
		return isNrImmaginiVisible;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CaricamentoImmaginiService#isNrImmaginiVisibile(java.math.BigDecimal)
	 */
	@Override
	public Boolean hasNaturaMapOrAudio(BigDecimal cdOggetto) throws AsyncServiceException {
		log.debug("[hasNaturaMapOrAudio] cdOggetto="+cdOggetto);
		/* In prima battuta verifico che l'item sia leggibile dall'utente... */
		BigDecimal cdEnte = this.getActiveCdEnte();
		/* Recupero dalla V_OGGETTI l'oggetto da trattare */
		VOggettoDTO itemCheck = vOggetti.findOggettoByDigitalizzatore(cdOggetto, cdEnte, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null);
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		/* Verifico... */
		if(immaginiUtils.hasNaturaMap(itemCheck.getT_CdTipoOggetto()) || 
				immaginiUtils.hasNaturaAudio(itemCheck.getT_CdTipoOggetto())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@SuppressWarnings("unchecked")
	private Boolean isJobAlreadyScheduled(Scheduler scheduler, JobKey jobID, BigDecimal cdOggettoOriginale) throws SchedulerException {
	    List<Trigger> scheduledTriggersForMyJob = (List<Trigger>) scheduler.getTriggersOfJob(jobID);
	    BigDecimal cdOggettoOriginaleTrg = null;
	    for(Trigger trg : scheduledTriggersForMyJob) {
	    	cdOggettoOriginaleTrg = new BigDecimal(trg.getJobDataMap().getInt(QuartzCreaImmagini.PCDOGGETTO));
	    	if(cdOggettoOriginale.equals(cdOggettoOriginaleTrg)) {
	    		return Boolean.TRUE;
	    	}
	    }
	    return Boolean.FALSE;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CaricamentoImmaginiService#caricaImmagini(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public void caricaImmagini(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, 
			Boolean isCreaImmaginiDerivate) throws AsyncServiceException, Exception {
		log.debug("[caricaImmagini] Entro nel metodo");
		log.debug("[caricaImmagini] cdOggettoOriginale=" + cdOggettoOriginale);
		
		final Date firstRunTime = evenMinuteDate(new Date(System.currentTimeMillis()+1*15*1000));

		/*
		 * In questo modo verifico che il digitalizzatore abbia diritto di 
		 * scrittura sul codice oggetto originale.
		 */
		getOggetto(cdOggettoOriginale);
		
		/* Definizione Scheduler */
	    Scheduler scheduler = schedFactory.getScheduler();
	    
	    Date runTimeForCreaImmagini = firstRunTime;
	    Date runTimeForCaricamentoImmagini = firstRunTime;
	    if(isCreaImmaginiDerivate) {
		    if(!isJobAlreadyScheduled(scheduler, jobKey(QuartzCreaImmagini.JOBNAME), cdOggettoOriginale) && 
		    		!isJobAlreadyScheduled(scheduler, jobKey(QuartzCaricaImmagini.JOBNAME), cdOggettoOriginale)) {
		    	/* Imposto il runTime di CreaImmagini */
		    	runTimeForCreaImmagini = firstRunTime;
			    /* Definizione Job */
		    	JobDetail job = scheduler.getJobDetail(jobKey(QuartzCreaImmagini.JOBNAME));
			    if(job==null) {
			    	job = newJob(QuartzCreaImmagini.class)
			    			.withIdentity(QuartzCreaImmagini.JOBNAME)
			    			.storeDurably(true)
			    			.build();
			    	scheduler.addJob(job, true);
			    }
			    /* Definizione Trigger */
			    Trigger trigger = newTrigger().withIdentity(QuartzCreaImmagini.buildTriggerName(cdOggettoOriginale.toPlainString(), runTimeForCreaImmagini), getActiveCdUtente().toString())
		    			.usingJobData(QuartzCreaImmagini.PCDOGGETTO, Integer.valueOf(cdOggettoOriginale.intValue()))
		    			.usingJobData(QuartzCreaImmagini.PNRIMMAGINI, Integer.valueOf(nrImmaginiDigitalizzate.intValue()))
		    			.usingJobData(QuartzCreaImmagini.PCDUTENTE,Integer.valueOf(getActiveCdUtente().intValue()))
		    			.usingJobData(QuartzCreaImmagini.PCDDIGITALIZZATORE,Integer.valueOf(getActiveCdEnte().intValue()))
		    			.startAt(runTimeForCreaImmagini)
		    			.forJob(jobKey(QuartzCreaImmagini.JOBNAME))
		    			.withSchedule(simpleSchedule()
		    					.withMisfireHandlingInstructionFireNow())
		    			.build();
			    /* Schedulo... */
			    scheduler.scheduleJob(trigger);
			    
		    } else {
		    	throw new AsyncServiceException("Non e' possibile procedere alla schedulazione del job \"" + QuartzCreaImmagini.JOBNAME + "\": "
		    			+ "e' già stato schedulato un job per lo stesso oggetto digitale! ");
		    }
	    } else {
		    if(!isJobAlreadyScheduled(scheduler, jobKey(QuartzCreaImmagini.JOBNAME), cdOggettoOriginale) && 
		    		!isJobAlreadyScheduled(scheduler, jobKey(QuartzCaricaImmagini.JOBNAME), cdOggettoOriginale)) {
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
			    Trigger trigger = newTrigger().withIdentity(QuartzCaricaImmagini.buildTriggerName(cdOggettoOriginale.toPlainString(), runTimeForCaricamentoImmagini), getActiveCdUtente().toString())
		    			.usingJobData(QuartzCaricaImmagini.PCDOGGETTO, Integer.valueOf(cdOggettoOriginale.intValue()))
		    			.usingJobData(QuartzCaricaImmagini.PNRIMMAGINI, Integer.valueOf(nrImmaginiDigitalizzate.intValue()))
		    			.usingJobData(QuartzCaricaImmagini.PCDUTENTE,Integer.valueOf(getActiveCdUtente().intValue()))
		    			.usingJobData(QuartzCaricaImmagini.PCDDIGITALIZZATORE,Integer.valueOf(getActiveCdEnte().intValue()))
		    			.startAt(runTimeForCaricamentoImmagini)
		    			.forJob(jobKey(QuartzCaricaImmagini.JOBNAME))
		    			.withSchedule(simpleSchedule()
		    					.withMisfireHandlingInstructionFireNow())
		    			.build();
			    /* Schedulo... */
			    scheduler.scheduleJob(trigger);
			    
		    } else {
		    	throw new AsyncServiceException("Non e' possibile procedere alla schedulazione del job \"" + QuartzCaricaImmagini.JOBNAME + "\": "
		    			+ "e' già stato schedulato un job per lo stesso oggetto digitale! ");
		    }
	    }
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CaricamentoImmaginiService#caricaImmaginiQrtz(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 */
	public void caricaImmaginiQrtz(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, 
			BigDecimal cdUtente ,BigDecimal cdDigitalizzatore) throws AsyncServiceException, Exception {
		log.debug("[caricaImmaginiQrtz] "
				+ "cdOggettoOriginale=" + cdOggettoOriginale + " "
				+ "nrImmaginiDigitalizzate=" + nrImmaginiDigitalizzate + " "
				+ "cdUtente=" + cdUtente + " "
				+ "cdDigitalizzatore=" + cdDigitalizzatore);
		caricaImmaginiStep1(cdOggettoOriginale, nrImmaginiDigitalizzate, cdUtente, cdDigitalizzatore);
		caricaImmaginiStep2(cdOggettoOriginale, nrImmaginiDigitalizzate, cdUtente, cdDigitalizzatore);
	}
	
	public String creaImmaginiQrtz(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, 
			BigDecimal cdUtente ,BigDecimal cdDigitalizzatore) throws AsyncServiceException, Exception {
		
		log.debug("[creaImmaginiQrtz] Entro nel metodo ");
		
		List<String> erroriOccorsi = new ArrayList<String>();
		String myErrorLog = "";
		
		log.debug("[creaImmaginiQrtz] "
				+ "cdOggettoOriginale=" + cdOggettoOriginale + " "
				+ "nrImmaginiDigitalizzate=" + nrImmaginiDigitalizzate + " "
				+ "cdUtente=" + cdUtente + " "
				+ "cdDigitalizzatore=" + cdDigitalizzatore);
		
		/* 0. Recupero l'ID usato dal Digitalizzatore in fase di caricamento delle immagini */
		VOggettoDTO item = vOggetti.findOggettoByCodice(cdOggettoOriginale);
		String pathOggettoOriginale = item.getO_DigitalizzatoreId();
		
		String pathImmaginiDigitalizzate = immaginiUtils.makeFilePathTmp(pathOggettoOriginale);
		File pathFileImmaginiDigitalizzate = new File(pathImmaginiDigitalizzate);
		if (pathFileImmaginiDigitalizzate.exists()) {
			
			/* 1. Faccio un check sulla natura del tipoOggetto che sto per trattare */
			Boolean hasNaturaAudio 	= immaginiUtils.hasNaturaAudio(item.getT_CdTipoOggetto());
			Boolean hasNaturaMap 	= immaginiUtils.hasNaturaMap(item.getT_CdTipoOggetto());
			if(!hasNaturaMap && !hasNaturaAudio) {
				log.debug("[creaImmaginiQrtz] cdTipoOggetto="+item.getT_CdTipoOggetto()+" e' di natura IMAGE ");
				/* 2. Recupero i formati di natura IMAGE da trattare */
				List<String> nature = Arrays.asList(BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE, BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE);
				List<BdlFormato> formatiImage = bdlFormatoRepository.findByDsNaturaInAndCdTipoOggetto(nature, item.getT_CdTipoOggetto());
				/* 3. Richiamo il metodo per la generazione delle immagini derivate */
				erroriOccorsi.addAll(BdlUtilsApp.handleBackEnd(pathImmaginiDigitalizzate, nrImmaginiDigitalizzate, formatiImage, properties.getImagemagickBin()));
			} else {
				erroriOccorsi.add(BaseImmaginiUtils.prependImg + " Non e' possibile procedere alla generazione delle immagini derivate per la tipologia di oggetto \"" + item.getT_DnNome() + "\"");
			}
		} else {
			erroriOccorsi.add(BaseImmaginiUtils.prependDirectory + "La cartella " + pathOggettoOriginale + " non esiste, è stato effettuato il caricamento via SFTP?");
		}
		
		/* 4. Verifico se ho degli errori e creo myErrorLog */
		if(!erroriOccorsi.isEmpty()) {
			myErrorLog = createErrorLogRecord(erroriOccorsi).toString();
			if(myErrorLog.length()>3900){
				myErrorLog = myErrorLog.substring(0, 3899);
			}
		} else {
			log.debug("[creaImmaginiQrtz] Immagini generate correttamente! ");
		}
		
		return myErrorLog;
	}
	
	/**
	 * Carica immagini step1.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @throws AsyncServiceException async service exception
	 * @throws Exception exception
	 */
	public void caricaImmaginiStep1(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, BigDecimal cdUtente, BigDecimal cdDigitalizzatore) throws AsyncServiceException, Exception {
		log.debug("[caricaImmagini] Entro nel metodo... ");
		
		/* Recupero il vOggetto per l'oggetto */
		VOggettoDTO vOggetto = vOggetti.findOggettoByCodice(cdOggettoOriginale);
		/* Recupero il DigitalizzatoreID per l'oggetto */
		String pathOggettoOriginale = vOggetto.getO_DigitalizzatoreId();
	    /* Recupero il bdlOggettoOriginale per l'oggetto */
		BdlOggettoOriginale bdlOggettoOriginale =  bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggettoOriginale);
		/*Recupero il Digitalizzatore */
		BdlEnte digitalizzatore = bdlEnteRepository.findByCdEnte(cdDigitalizzatore);
		
		/* Vado a verificare che il materiale sia stato inserito 
		 * correttamente dal digitalizzatore */
		List<String> erroriOccorsi = immaginiUtils.verificaCaricamentoImmagini(bdlOggettoOriginale, digitalizzatore, nrImmaginiDigitalizzate);
		if(!erroriOccorsi.isEmpty()) {
			bdlOggettoOriginale.setFlAnomaliaImmagini(true);
			StringBuilder logError = createErrorLogRecord(erroriOccorsi);
			String logLocal = logError.toString();
			if(logLocal.length()>3900){
				logLocal = logLocal.substring(0, 3899);
			}
			bdlOggettoOriginale.setDsLogAnomalia(logLocal);
			bdlOggettoOriginaleRepository.save(bdlOggettoOriginale);
			throw new AsyncServiceException(pathOggettoOriginale);
		} else {
			log.debug("[caricaImmagini] Tutte le verifiche sono andate a buon fine.");
			bdlOggettoOriginale.setNrImmaginiDigitalizzate(nrImmaginiDigitalizzate);
		}
		
		/* Qui gestisco il nuovo flusso del materiale cartografico */
		log.debug("[caricaImmagini] Tratto un oggetto con cdTipoOggetto=" + bdlOggettoOriginale.getCdTipoOggetto());
		if(immaginiUtils.hasNaturaMap(bdlOggettoOriginale.getCdTipoOggetto())) {
			log.debug("[caricaImmagini] Ho una CARTOGRAFIA. Procedo alla creazione del mosaico... ");
			
			BdlFormato fmt = immaginiUtils.getFormatiNaturaMap(bdlOggettoOriginale.getCdTipoOggetto()).get(0);
			
			/* Vado a prendere l'immagine da tassellare all'interno della dir con natura map */
			StringBuilder pathImg = new StringBuilder();
			pathImg.append(immaginiUtils.makeFilePathTmp(pathOggettoOriginale, fmt.getDnNomeFormato()));

			File path = new File(pathImg.toString());
			String[] listOfFile = path.list();
			
			pathImg.append(File.separator);
			pathImg.append(listOfFile[0]);
			log.debug("[caricaImmagini] pathImg="+pathImg.toString());
			
			String pathTmpDirImg = immaginiUtils.makeFilePathTmp(pathOggettoOriginale, BdlSharedConstants.BDL_FORMATO_NATURA_MAP);
			log.debug("[caricaImmagini] pathTmpDirImg="+pathTmpDirImg);
			ConvertWrapper convertWrapper = new ConvertWrapper(properties.getImagemagickBin());
			Map<String, BigDecimal> returnedMap = convertWrapper.tileImage(pathImg.toString(), pathTmpDirImg);
			
			bdlOggettoOriginale.setVlMapBorderH(returnedMap.get("MapBorderHeight"));
			bdlOggettoOriginale.setVlMapBorderW(returnedMap.get("MapBorderWidth"));
			bdlOggettoOriginale.setVlMapMaxZoomH(returnedMap.get("MapHeight"));
			bdlOggettoOriginale.setVlMapMaxZoomW(returnedMap.get("MapWidth"));
			bdlOggettoOriginale.setVlMapMaxZoomLevel(returnedMap.get("MapMaxZoomLevel"));
			bdlOggettoOriginale.setVlMapMinZoomLevel(returnedMap.get("MapMinZoomLevel"));
		} else {
			log.debug("[caricaImmagini] NON sto trattando una CARTOGRAFIA. ");
			
			bdlOggettoOriginale.setVlMapBorderH(null);
			bdlOggettoOriginale.setVlMapBorderW(null);
			bdlOggettoOriginale.setVlMapMaxZoomH(null);
			bdlOggettoOriginale.setVlMapMaxZoomW(null);
			bdlOggettoOriginale.setVlMapMaxZoomLevel(null);
			bdlOggettoOriginale.setVlMapMinZoomLevel(null);
		}
		
		/* Scrivo il file .accolto e muovo tutte le directory (eccetto la master) dalla TMP alla WORK */
		try{
			/** 
			 * Se non ci sono errori copio nella cartella work le immagini e scrivo un file vuoto
			 * .accolto nella cartella TMP 
			 */
			
			List<BdlWorkFilesystem> fs = bdlWorkFilesystemRepository.findByFlInUso(true);
			if(fs.size()==0){
				throw new Exception("Non sono configurati mount point attivi");
			}
			BdlWorkFilesystem mountPoint = fs.get(0);
			bdlOggettoOriginale.setCdWorkFilesystem(mountPoint.getCdWorkFilesystem());
			
			String strSrcPath = immaginiUtils.makeFilePathTmp(pathOggettoOriginale);
			String strDestPath = immaginiUtils.makeFilePathWrk(mountPoint.getCdWorkFilesystem(), pathOggettoOriginale);
			log.debug("[caricaImmagini] Muovo da "+strSrcPath+" a "+strDestPath+".");
			
			/* creo un file vuoto .accolto */
			StringBuilder strEmptyFile = new StringBuilder();
			strEmptyFile.append(properties.getDirNetappTmp());
			strEmptyFile.append(File.separator);
			strEmptyFile.append(pathOggettoOriginale);
			strEmptyFile.append(File.separator);
			strEmptyFile.append(BdlServerConstants.FILE_ACCOLTO);
			
			File source = new File(strSrcPath);
			
			FileFilter directoryFilter = new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory() && !file.getName().equalsIgnoreCase("MASTER");
				}
			};

			File dest = new File(strDestPath);
			if(dest.exists()){
				FileUtils.deleteDirectory(dest);
			}
			File[] files = source.listFiles(directoryFilter);
			for (File file : files) {
				FileUtils.moveDirectoryToDirectory(file,dest,true);
			}

			log.debug("[caricaImmagini] Eseguo il touch di "+strEmptyFile.toString()+".");
			FileUtils.touch(new File(strEmptyFile.toString()));
		} catch (Exception e){
			throw e;
		}
		bdlOggettoOriginaleRepository.save(bdlOggettoOriginale);

	}

	/**
	 * Carica immagini step2.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @throws Exception exception
	 */
	public void caricaImmaginiStep2(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, BigDecimal cdUtente, BigDecimal cdDigitalizzatore) throws Exception {

		log.debug("[caricaImmaginiStep2] entro nel metodo");
		
		
		BdlUtente operatore = bdlUtenteRepository.findByCdUtente(cdUtente);

		VOggettoDTO item = vOggetti.findOggettoByCodice(cdOggettoOriginale);
		String pathOggettoOriginale = item.getO_DigitalizzatoreId();
//		String pathOggettoToCopy = item.getO_DigitalizzatoreIdToCopy();
		
		BdlOggettoOriginale bdlOggettoOriginale =  bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggettoOriginale);

		log.debug("[caricaImmaginiStep2] Cancello le immagini relative all'oggetto "+bdlOggettoOriginale.getCdOggettoOriginale()+".");
		bdlImmagineRepository.delete(bdlImmagineRepository.findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(bdlOggettoOriginale.getCdOggettoOriginale()));

		log.debug("[caricaImmaginiStep2] Imposto le directory dei defaults");
		/* Directory for defaults */
		BdlFormato formatoForThumb = defaultForThumb(bdlOggettoOriginale);
		String defaultNameForThumb = formatoForThumb.getDnNomeFormato();
		StringBuilder pathDirForThumb = new StringBuilder();
		pathDirForThumb.append(immaginiUtils.makeFilePathWrk(item.getO_CdWorkFilesystem(), pathOggettoOriginale, defaultNameForThumb));
		log.debug("[caricaImmaginiStep2] pathDirForThumb="+pathDirForThumb);
		
		BdlFormato formatoForReader = defaultForReader(bdlOggettoOriginale);
		String defaultNameForReader= formatoForReader.getDnNomeFormato();
		StringBuilder pathDirForReader = new StringBuilder();
		pathDirForReader.append(immaginiUtils.makeFilePathWrk(item.getO_CdWorkFilesystem(), pathOggettoOriginale, defaultNameForReader));
		log.debug("[caricaImmaginiStep2] pathDirForReader="+pathDirForReader);
		
		BdlFormato formatoForZoom = defaultForZoom(bdlOggettoOriginale);
		String defaultNameForZoom;
		StringBuilder pathDirForZoom = new StringBuilder();
		if(formatoForZoom!=null){
			defaultNameForZoom = formatoForZoom.getDnNomeFormato();
			pathDirForZoom.append(immaginiUtils.makeFilePathWrk(item.getO_CdWorkFilesystem(), pathOggettoOriginale, defaultNameForZoom));			
		}
		log.debug("[caricaImmaginiStep2] pathDirForZoom="+pathDirForZoom);

		
		
				
		File folder = new File(pathDirForReader.toString());
		File[] listOfFiles = folder.listFiles();
		List<String> listOfFileNames = new ArrayList<String>();
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	        if (listOfFiles[i].isFile() && !(listOfFiles[i].getName().contains("_"))) {
	    	    listOfFileNames.add(listOfFiles[i].getName());
	        } 
	    }

		log.debug("[caricaImmaginiStep2] Procedo al caricamento di "+listOfFileNames.size()+" immagini");
		int imgIdx = 0;
		for (String filename : listOfFileNames) {
			imgIdx++;
			log.debug("[caricaImmaginiStep2] Tratto immagine "+imgIdx+" di "+listOfFileNames.size()+":"+filename);
			String[] splitFilename = filename.split("\\.");
			
			StringBuilder tmpFilename = new StringBuilder();
			if (splitFilename.length > 0) {
				tmpFilename.append(splitFilename[0]);
			}
			
			BdlImmagine bdlImmagine = new BdlImmagine();
			bdlImmagine.setDnNomeImmagine(tmpFilename.toString());
			bdlImmagine.setCdOggettoOriginale(bdlOggettoOriginale.getCdOggettoOriginale());
		
			try {
				File thumb = immaginiUtils.getExistingFile(pathDirForThumb.toString(), tmpFilename.toString(), formatoForThumb.getDsEstensione());
				if (thumb!=null) {
					IdentifyWrapper imageInfo = new IdentifyWrapper(thumb.toString(), properties.getImagemagickBin());
					bdlImmagine.setNrPxLarghezzaThumb(new BigDecimal(imageInfo.getWidth()));
					bdlImmagine.setNrPxAltezzaThumb(new BigDecimal(imageInfo.getHeight()));
				}  else {
					throw new Exception(serverMessages.getString("eccezioneFileNonEsiste")+" ("+(pathDirForThumb.toString()+"/"+ tmpFilename.toString())+")"); 
				}

				File reader = immaginiUtils.getExistingFile(pathDirForReader.toString(), tmpFilename.toString(), formatoForReader.getDsEstensione());
				if (reader!=null) {
					IdentifyWrapper imageInfo = new IdentifyWrapper(reader.toString(), properties.getImagemagickBin());
					bdlImmagine.setNrPxLarghezzaReader(new BigDecimal(imageInfo.getWidth()));
					bdlImmagine.setNrPxAltezzaReader(new BigDecimal(imageInfo.getHeight()));
				}  else {
					throw new Exception(serverMessages.getString("eccezioneFileNonEsiste")+" ("+(pathDirForReader.toString()+"/"+ tmpFilename.toString())+")"); 
				}
				
				if(formatoForZoom!=null){
					File zoom = immaginiUtils.getExistingFile(pathDirForZoom.toString(), tmpFilename.toString(), formatoForZoom.getDsEstensione());
					if (zoom!=null) {
						IdentifyWrapper imageInfo = new IdentifyWrapper(zoom.toString(), properties.getImagemagickBin());
						bdlImmagine.setNrPxLarghezzaZoom(new BigDecimal(imageInfo.getWidth()));
						bdlImmagine.setNrPxAltezzaZoom(new BigDecimal(imageInfo.getHeight()));
					}  else {
						throw new Exception(serverMessages.getString("eccezioneFileNonEsiste")+" ("+(pathDirForZoom.toString()+"/"+ tmpFilename.toString())+")"); 
					}
				}
				
			} catch (InfoException e) {
				Exception myException = new Exception(serverMessages.getString("eccezioneCalcoloPx"));
				myException.setStackTrace(e.getStackTrace());
				throw myException;
			}	
			
			bdlImmagine.setDtCreatoil(new Date());
			bdlImmagine.setDsCreatoda(operatore.getCdCodiceFiscale());
			/* Se il file immagine e' il primo della sequenza (0001) imposto il flag FlPrincipale a true */
			if (tmpFilename.toString().equals(BdlServerConstants.FIRST_IMAGE_SEQUENCE)){
				bdlImmagine.setFlPrincipale(true); 
			} else {
				bdlImmagine.setFlPrincipale(false);
			}
			
			bdlImmagineRepository.save(bdlImmagine);
		}
		
		// Se il tipo oggetto ha anche solo un formato di natura image, procedo al caricamento
		// del PDF (se e solo se questo esiste)		
		if(immaginiUtils.hasNaturaSingleImage(bdlOggettoOriginale.getCdTipoOggetto()) || immaginiUtils.hasNaturaMultiImage(bdlOggettoOriginale.getCdTipoOggetto()) ) {
			log.debug("[caricaImmaginiStep2] Il tipo oggetto "+bdlOggettoOriginale.getCdTipoOggetto()+" ha un formato con natura image");
			
			/* Procedo a caricare i testi del file pdf */
			log.debug("[caricaImmaginiStep2] Cancello le pagine pdf relative all'oggetto "+bdlOggettoOriginale.getCdOggettoOriginale()+".");
			pdfPaginaRepository.delete(pdfPaginaRepository.findByCdOggettoOriginale(bdlOggettoOriginale.getCdOggettoOriginale()));
			List<String> testiPdf = new ArrayList<String>();
			String pdfDir = immaginiUtils.makeFilePathWrk(item.getO_CdWorkFilesystem(), pathOggettoOriginale, "pdf");
			File pdf = new File(pdfDir);
			if (pdf.exists()) {
				String pdfFilePath = pdfDir + File.separator + bdlOggettoOriginale.getCdOggettoOriginale().toString() + ".pdf";
				log.debug("[caricaImmaginiStep2] Tratto il file pdf="+pdfFilePath);
				File pdfFile = new File(pdfFilePath);
				if (pdfFile.exists()) {
					try{
						testiPdf = immaginiUtils.managePdf(pdfFilePath);
					}catch(Exception e){
						log.error("[caricaImmaginiStep2] errore in managePdf " + e);
					}
					
					if(!testiPdf.isEmpty()){

						log.debug("[caricaImmaginiStep2] Nel file pdf ho trovato "+testiPdf.size()+" pagine.");
						for(int i=0; i<testiPdf.size(); i++){
							int page = i;
							page = page+1;
							log.debug("[caricaImmaginiStep2] Tratto pagina "+page+" di "+testiPdf.size()+"");
							
							String contenuto = testiPdf.get(i);
							String contenutoUno = null;
							String contenutoDue = null;
							Integer contenutoLenght = contenuto.length();
							Integer mxLength = 3900;
							if(contenutoLenght>0){
								BdlPdfPagina pagina = new BdlPdfPagina();
								contenuto = contenuto.replaceAll("\\s+", " ");
								contenutoLenght = contenuto.length();
								if(contenutoLenght>0){
									log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " ha contenuto con lunghezza "+contenutoLenght);
									contenutoUno = contenuto;
									if(contenutoLenght>mxLength) {
										log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " viene spezzata in due");
										contenutoUno = contenuto.substring(0, mxLength);
										if(contenutoLenght>mxLength*2) {
											log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " ha lunghezza piu ampia di due pagine... trimmo");
											contenutoDue = contenuto.substring(mxLength, mxLength*2);
										}else{
											log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " ha lunghezza piu ampia di una pagina, trimmo a "+(contenutoLenght-1)+" caratteri");
											contenutoDue = contenuto.substring(mxLength, contenutoLenght-1);
										}
									}
									
									if(contenutoUno!=null){
										contenutoUno = immaginiUtils.cutStringUtf8(contenutoUno,mxLength);
										log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " contenutoUno ha lunghezza "+contenutoUno.length());
									}
									if(contenutoDue!=null){
										contenutoDue = immaginiUtils.cutStringUtf8(contenutoDue,mxLength);
										log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " contenutoDue ha lunghezza "+contenutoDue.length());
									}
									
									
									pagina.setDsPagina(contenutoUno);
									pagina.setDsPaginaDue(contenutoDue);
									pagina.setCdOggettoOriginale(bdlOggettoOriginale.getCdOggettoOriginale());
									pagina.setNrPagina(new BigDecimal(page));
									
									pdfPaginaRepository.save(pagina);
									/* Abilito la Ricerca OCR */
									bdlOggettoOriginale.setFlRicercaOcr(true);
								}else{
									log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " ha contenuto vuoto dopo la sostituzione degli spazi...");	
								}
							}else{
								log.debug("[caricaImmaginiStep2] La pagina del PDF " + page + " ha contenuto vuoto...");								
							}
						}
					}else{
						log.debug("[caricaImmaginiStep2] Nel file pdf non ho trovato pagine.");
					}
				}
			}
		}else{
			log.debug("[caricaImmaginiStep2] Il tipo oggetto non ha nemmeno un formato con natura image");
		}
		
		/* Imposto il flag esisteOggettoDigitale */
		bdlOggettoOriginale.setFlOggettoDigitale(true);
		bdlOggettoOriginale.setFlAnomaliaImmagini(false);
		boolean oldFlCorrezione = bdlOggettoOriginale.getFlCorrezione();
		bdlOggettoOriginale.setFlCorrezione(false);
		bdlOggettoOriginale.setDsLogAnomalia(null);
		bdlOggettoOriginale.setDtModificatoil(new Date());
		bdlOggettoOriginale.setDsModificatoda(operatore.getDsCreatoda());
		bdlOggettoOriginaleRepository.save(bdlOggettoOriginale);

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(bdlOggettoOriginale.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		String comunicazione = "";
		if(!oldFlCorrezione){
			comunicazione = "OGGETTOCARICATO";
		}else{
			comunicazione = "OGGETTOCORRETTO";
		}
		bdlEmailer.sendMailToCatalogatore(operatore.getCdUtente(),bdlOggettoOriginale.getCdOggettoOriginale(),comunicazione, map);
		log.debug("[caricaImmaginiStep2] Ho terminato il processo di caricamento");
	}
	
	/* ===========================================
	 * 	Metodi privati di utility per il servizio 
	 * ===========================================
	 * */
	
	/**
	 * Default for thumb.
	 *
	 * @param bdlOggettoOriginale  bdl oggetto originale
	 * @return bdl formato
	 */
	private BdlFormato defaultForThumb(BdlOggettoOriginale bdlOggettoOriginale){
		BdlFormato thumb = null;
		for (BdlFormato formato : bdlFormatoRepository.findByCdTipoOggetto(bdlOggettoOriginale.getCdTipoOggetto())) {
			if (formato.getFlDefaultforthumb()) {
				thumb = new BdlFormato();
				thumb.setDnNomeFormato(formato.getDnNomeFormato());
				thumb.setDsEstensione(formato.getDsEstensione());
			}
		}
		return thumb;
	}
	
	/**
	 * Default for reader.
	 *
	 * @param bdlOggettoOriginale  bdl oggetto originale
	 * @return bdl formato
	 */
	private BdlFormato defaultForReader(BdlOggettoOriginale bdlOggettoOriginale){		
		BdlFormato reader = null;
		for (BdlFormato formato : bdlFormatoRepository.findByCdTipoOggetto(bdlOggettoOriginale.getCdTipoOggetto())) {
			if (formato.getFlDefaultforreader()) {
				reader = new BdlFormato();
				reader.setDnNomeFormato(formato.getDnNomeFormato());
				reader.setDsEstensione(formato.getDsEstensione());
			}
		}
		return reader;
	}
	
	/**
	 * Default for zoom.
	 *
	 * @param bdlOggettoOriginale  bdl oggetto originale
	 * @return bdl formato
	 */
	private BdlFormato defaultForZoom(BdlOggettoOriginale bdlOggettoOriginale){
		
		BdlFormato zoom = null;
		for (BdlFormato formato : bdlFormatoRepository.findByCdTipoOggetto(bdlOggettoOriginale.getCdTipoOggetto())) {
			if (formato.getFlDefaultforzoom()) {
				zoom = new BdlFormato();
				zoom.setDnNomeFormato(formato.getDnNomeFormato());
				zoom.setDsEstensione(formato.getDsEstensione());
			}
		}
		return zoom;
	}
	
	/**
	 * Crea error log record.
	 *
	 * @param errors  errors
	 * @return string builder
	 */
	private StringBuilder createErrorLogRecord (List<String> errors) {
		StringBuilder strErrorLog = new StringBuilder();
		
		for(String error : errors) {
			strErrorLog.append(error.replace(properties.getDirNetappTmp(), "").replace(properties.getDirNetappWrk(), ""));
			strErrorLog.append(BdlServerConstants.LOGANOMALIARECORDSEPARATOR);
		}
		
		return strErrorLog;
	}
}
