package it.lispa.bdl.server.auth;


import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.RequestFilterUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * Class RequestIntSupervisoreFilter.
 */
public class RequestIntSupervisoreFilter extends RequestGenericFilter implements Filter {

	/** log. */
	private static Logger log = Logger.getLogger(RequestIntSupervisoreFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		log.debug("[RequestIntSupervisoreFilter] Entro nel filtro per url "+request.getPathInfo());
		
		// Provo a recuperare le info sulla sessione e sull'utente che tenta l'accesso alla BDL...
		RequestFilterUtils reqFilterUtils = new RequestFilterUtils();
		reqFilterUtils.checkLoggedUser(req, res, request.getHeader(propNameForHeaderIDPCCodiceFiscale), request.getHeader(propNameForHeaderIAMCodiceFiscale));

		if(reqFilterUtils.getUserIsLoggedIn() && reqFilterUtils.getUserIsValidated() && reqFilterUtils.getUser().getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_SUPERVISORE)) {
			log.debug("[RequestIntSupervisoreFilter] Proseguo nella catena, l'utente risulta Supervisore");
			chain.doFilter(req, res);
		} else {
			((HttpServletResponse)res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
