package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
public class RequestFilterUtils {

	/** log. */
	private static Logger log = Logger.getLogger(RequestFilterUtils.class);
	
	/** utente repository. */
	@Autowired
	private BdlUtenteRepository utenteRepository;
	
	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** ente utente repository. */
	@Autowired
	private BdlEnteUtenteRepository enteUtenteRepository;
	
	/** La Costante SESSION_USER_IS_LOGGED_IN. */
	public static final String SESSION_USER_IS_LOGGED_IN		= "USER_IS_LOGGED";
	
	/** La Costante SESSION_USER_IS_VALIDATED. */
	public static final String SESSION_USER_IS_VALIDATED 		= "USER_IS_VALIDATED";
	
	/** La Costante SESSION_ACTIVE_USER_CD. */
	public static final String SESSION_ACTIVE_USER_CD			= "LOGGED_USER_CD";
	
	/** La Costante SESSION_ACTIVE_USER. */
	public static final String SESSION_ACTIVE_USER 				= "LOGGED_USER";
	
	/** La Costante SESSION_ACTIVE_ENTE_CD. */
	public static final String SESSION_ACTIVE_ENTE_CD 			= "ENTE_ACTIVE_CD";
	
	/** La Costante SESSION_ACTIVE_ENTE. */
	public static final String SESSION_ACTIVE_ENTE 				= "ENTE_ACTIVE";
	
	/** user is logged in. */
	protected Boolean userIsLoggedIn;
	
	/** user is validated. */
	protected Boolean userIsValidated;
	
	/** user_cd. */
	protected BigDecimal userCd;
	
	/** user. */
	protected BdlUtente user;
	
	/** ente_cd. */
	protected BigDecimal enteCd;
	
	/** ente. */
	protected BdlEnte ente;
	
	public RequestFilterUtils() {
		// do nothing...
	}

	public Boolean getUserIsLoggedIn() {
		return userIsLoggedIn;
	}
	public Boolean getUserIsValidated() {
		return userIsValidated;
	}
	public BigDecimal getUserCd() {
		return userCd;
	}
	public BdlUtente getUser() {
		return user;
	}
	public BigDecimal getEnteCd() {
		return enteCd;
	}
	public BdlEnte getEnte() {
		return ente;
	}

	/**
	 * Check logged user.
	 *
	 * @param req  req
	 * @param res  res
	 * @param chain  chain
	 * @throws ServletException servlet exception
	 */
	public void checkLoggedUser(ServletRequest req, ServletResponse res, String idpcCodiceFiscale, String iamCodiceFiscale) throws ServletException {
		log.debug("[checkLoggedUser] Entro nel checkLoggedUser...");

		// forzo l'autowiring: checkLoggedUser e' usato nelle servlet Request*...
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		HttpServletRequest request = (HttpServletRequest) req;

		HttpSession session = request.getSession();
		
		Boolean sessionUserIsLoggedIn = false;
		if(null != session){
			
			log.debug("[checkLoggedUser] La sessione NON e' nulla ");
			Boolean sessionValue = (Boolean) session.getAttribute(SESSION_USER_IS_LOGGED_IN);
			if(sessionValue!=null){
				sessionUserIsLoggedIn = sessionValue;
			}
			if(sessionUserIsLoggedIn) {
				
				log.debug("[checkLoggedUser] L'Utente e' online in sessione ");
				userIsLoggedIn 	= (Boolean) 	session.getAttribute(SESSION_USER_IS_LOGGED_IN);
				userIsValidated = (Boolean) 	session.getAttribute(SESSION_USER_IS_VALIDATED);	
				userCd 			= (BigDecimal) 	session.getAttribute(SESSION_ACTIVE_USER_CD);
				user 			= (BdlUtente) 	session.getAttribute(SESSION_ACTIVE_USER);	
				enteCd 			= (BigDecimal) 	session.getAttribute(SESSION_ACTIVE_ENTE_CD);
				ente	 		= (BdlEnte) 	session.getAttribute(SESSION_ACTIVE_ENTE);
			} else {
				
				log.debug("[checkLoggedUser] L'Utente NON e' online in sessione ");
				userIsLoggedIn	= false;
				userIsValidated	= false;
				userCd			= null;
				user			= null;
				enteCd			= null;
				ente			= null;
				session.setAttribute(SESSION_USER_IS_LOGGED_IN, 	userIsLoggedIn	);
				session.setAttribute(SESSION_USER_IS_VALIDATED, 	userIsValidated	);
				session.setAttribute(SESSION_ACTIVE_USER_CD, 		userCd			);
				session.setAttribute(SESSION_ACTIVE_USER, 			user			);
				session.setAttribute(SESSION_ACTIVE_ENTE_CD, 		enteCd			);
				session.setAttribute(SESSION_ACTIVE_ENTE, 			ente			);
				
				String cfVerifica = null;

				if(idpcCodiceFiscale != null && !idpcCodiceFiscale.equals("")) {
					log.debug("[checkLoggedUser] Utente IDPC dichiara CF=" + idpcCodiceFiscale + " ");
					cfVerifica = idpcCodiceFiscale;
				}
				if(iamCodiceFiscale != null && !iamCodiceFiscale.equals("")) {	
					log.debug("[checkLoggedUser] Utente IAM dichiara CF=" + iamCodiceFiscale + " ");
					cfVerifica = iamCodiceFiscale;
				}

				if(cfVerifica != null && !cfVerifica.equals("")) {
					user = utenteRepository.findByCdCodiceFiscale(idpcCodiceFiscale);
					if(user != null) {
						log.debug("[checkLoggedUser] Utente con CF="+cfVerifica+" trovato nella base dati ");
						try {
							log.debug("[checkLoggedUser] cdUtente="+user.getCdUtente()+" --- dsStato="+user.getDsStato());
							
							userIsLoggedIn = true;
							String userState = user.getDsStato();
							
							if(userState.equals(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO)){
								userIsValidated = true;
							}else{
								userIsValidated = false;
							}
							
							List<BdlEnte> enti = new ArrayList<BdlEnte>();
							List<BdlEnteUtente> entiRel = null;
							
							entiRel = enteUtenteRepository.findByCdUtente(user.getCdUtente());
							if(!entiRel.isEmpty()){
								Iterator<BdlEnteUtente> relItr = entiRel.iterator();
								while(relItr.hasNext()){
									BdlEnteUtente itm = relItr.next();
									enti.add(enteRepository.findByCdEnte(itm.getCdEnte()));
								}
							}
							if(!enti.isEmpty()){
								Iterator<BdlEnte> iter = enti.iterator();
								BdlEnte first = (BdlEnte) iter.next();
								ente = first;
							}else{
								ente = null;
							}
							if(user!=null){
								
								log.debug("[checkLoggedUser] Procedo ad impostare le proprieta' di sessione...");
								
								session.setAttribute(SESSION_USER_IS_LOGGED_IN, userIsLoggedIn);
								session.setAttribute(SESSION_USER_IS_VALIDATED, userIsValidated);
								session.setAttribute(SESSION_ACTIVE_USER_CD, user.getCdUtente());
								session.setAttribute(SESSION_ACTIVE_USER, user);
								log.debug("[checkLoggedUser] Ho impostato la proprieta' della sessione SESSION_USER_IS_LOGGED_IN");
								log.debug("[checkLoggedUser] Ho impostato la proprieta' della sessione SESSION_USER_IS_VALIDATED a "+userIsValidated);
								log.debug("[checkLoggedUser] Ho impostato la proprieta' della sessione SESSION_ACTIVE_USER_CD a "+user.getCdUtente()+" corrispondente a "+user.getNmNome()+" "+user.getCmCognome());
								
								if(ente!=null){
									session.setAttribute(SESSION_ACTIVE_ENTE_CD, ente.getCdEnte());
									session.setAttribute(SESSION_ACTIVE_ENTE, ente);
									log.debug("[checkLoggedUser] Ho impostato la proprieta' della sessione SESSION_ACTIVE_ENTE_CD a "+ente.getCdEnte()+" corrispondente a "+ente.getDnNome());
								}else{
									log.debug("[checkLoggedUser] Ente nullo... lo lascio immutato");
								}
							}else{
								log.debug("[checkLoggedUser] Utente con CF="+cfVerifica+" manda a null la proprietà user...");
							}
						} catch (Exception e) {
							log.debug("[checkLoggedUser] Lancio una eccezione: "+e.getMessage());
							throw new ServletException(e);
						}
					} else {
						log.debug("[checkLoggedUser] Utente con CF="+cfVerifica+" non presente nella base dati");
					}
				} else {
					log.debug("[checkLoggedUser] CF di verifica nullo o vuoto");
				}
			}
		}else{
			log.debug("[checkLoggedUser] La sessione e' nulla");
		}
		
		log.debug("[checkLoggedUser] Esco dalla checkLoggedUser...");
	}
}
