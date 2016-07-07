package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class ExternalServiceImpl.
 */
@Service("externalService")
@Repository
@Transactional
public class ExternalServiceImpl extends BaseServiceImpl implements ExternalService {


	/** bdl ruolo repository. */
	@Autowired
	private BdlRuoloRepository bdlRuoloRepository;

	/** bdl ente repository. */
	@Autowired
	private BdlEnteRepository bdlEnteRepository;

	/** bdl ente utente repository. */
	@Autowired
	private BdlEnteUtenteRepository bdlEnteUtenteRepository;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.HomeMsg", new UTF8Control());

	/** log. */
	private static Logger log = Logger.getLogger(ExternalServiceImpl.class);

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#isUserOnline()
	 */
	@Transactional(readOnly=true)
	public String isUserOnline() {
		log.debug("[isUserOnline] Entro nel metodo");

		Boolean userIsLoggedIn = getUserIsLoggedIn();
		Boolean userIsValidated = getUserIsValidated();

		String respObject = "";
		if(userIsValidated&&userIsLoggedIn){
			respObject = BdlSharedConstants.USER_VALIDATED;
		}else if(userIsLoggedIn){
			respObject = BdlSharedConstants.USER_REGISTERED;
		}else{
			respObject = BdlSharedConstants.USER_UNKNOWN;
		}
		log.debug("[isUserOnline] ritorno "+respObject);

		return respObject;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#getRuoliRegistrabili()
	 */
	@Transactional(readOnly=true)
	public List<ComboDTO> getRuoliRegistrabili() {

		List<ComboDTO> aRuoli = new ArrayList<ComboDTO>();
		for (BdlRuolo ruolo : bdlRuoloRepository.findAll()) {
			if (ruolo.getFlRegistrabile()) {
				aRuoli.add(new ComboDTO(ruolo.getCdRuolo(),ruolo.getDnNome()));
			}
		}

		return aRuoli;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#getEnti(java.math.BigDecimal)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<ComboDTO> getEnti(BigDecimal ruolo) {

		BdlRuolo ruoloJpa = bdlRuoloRepository.findByCdRuolo(ruolo);

		List<ComboDTO> listEntiOrIst = new ArrayList<ComboDTO>();
		char fsRuoloChar = 0;

		if (ruoloJpa.getDnNome().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_CATALOGATORE)) {
			fsRuoloChar = BdlSharedConstants.BDL_RUOLO_CATALOGATORE_CHAR;
		} else if (ruoloJpa.getDnNome().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE)) {
			fsRuoloChar = BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE_CHAR;
		}

		for (BdlEnte ente: bdlEnteRepository.findAll()) {
			if (ente.getFlClasse() == fsRuoloChar ) {
				listEntiOrIst.add(new ComboDTO(ente.getCdEnte(),ente.getDnNome()));
			}
		}

		return listEntiOrIst;
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#registraUtente(it.lispa.bdl.shared.dto.UtenteDTO)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void registraUtente(UtenteDTO user) throws AsyncServiceException {

		BigDecimal cdRuolo = user.getCdRuolo();
		BigDecimal cdEnteOrIst = user.getCdEnti().get(0);
		
		BdlRuolo ruolo = bdlRuoloRepository.findByCdRuolo(cdRuolo);
		if(ruolo == null) {
			throw new AsyncServiceException(serverMessages.getString("registrazioneKOnoruolo"));
		}
		if(!ruolo.getFlRegistrabile()){
			throw new AsyncServiceException(serverMessages.getString("registrazioneKOruolonoreg"));
		}

		BdlEnte ente = bdlEnteRepository.findByCdEnte(cdEnteOrIst);
		if(ente == null) {
			throw new AsyncServiceException(serverMessages.getString("registrazioneKOnoente"));
		}

		if (user.getCf().length()!=16) {
			throw new AsyncServiceException(serverMessages.getString("registrazioneKOnocf"));
		}
		BdlUtente userCheck = bdlUtenteRepository.findByCdCodiceFiscale(user.getCf().toUpperCase());
		if(userCheck != null) {
			throw new AsyncServiceException(serverMessages.getString("registrazioneKOutentedup"));
		}
		BdlUtente bdlUser = new BdlUtente(
				null,
				cdRuolo,
				user.getNome(),
				user.getCognome(),
				user.getCf().toUpperCase(),
				user.getEmail(),
				user.getTelefono(),
				BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE,
				new Date(),
				user.getCf().toUpperCase(),
				new Date()		
				);

		bdlUser = bdlUtenteRepository.save(bdlUser);

		BdlEnteUtente enteUtente = new BdlEnteUtente();
		enteUtente.setCdEnte(ente.getCdEnte());
		enteUtente.setCdUtente(bdlUser.getCdUtente());
		bdlEnteUtenteRepository.save(enteUtente);
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.UTENTE_NOME, bdlUser.getNmNome());
		map.put(BdlEmailer.UTENTE_COGNOME, bdlUser.getCmCognome());
		map.put(BdlEmailer.UTENTE_CF, bdlUser.getCdCodiceFiscale());
		
		bdlEmailer.sendMailToSupervisors(getActiveCdUtente(),"UTENTEDAVALIDARE", map);
		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#getUserEmail()
	 */
	@Transactional(readOnly=true)
	public String getUserEmail() {

		BigDecimal cdUtente = getActiveCdUtente();

		BdlUtente utenteJpa = bdlUtenteRepository.findByCdUtente(cdUtente);

		return utenteJpa.getDnEmail();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#getIdpcUrlLogin()
	 */
	@Transactional(readOnly=true)
	public String getIdpcUrlLogin() {
		return properties.getIdpcUrlLogin();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ExternalService#getIdpcUrlLogout()
	 */
	@Transactional(readOnly=true)
	public String getIdpcUrlLogout() {
		return properties.getIdpcUrlLogout();
	}
}
