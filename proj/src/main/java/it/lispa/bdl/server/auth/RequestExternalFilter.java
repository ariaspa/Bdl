package it.lispa.bdl.server.auth;


import it.lispa.bdl.server.utils.RequestFilterUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * Class RequestExternalFilter.
 */
public class RequestExternalFilter extends RequestGenericFilter implements Filter {
	
	/** log. */
	private static Logger log = Logger.getLogger(RequestExternalFilter.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		log.debug("[RequestExternalFilter] Entro nel filtro per url "+request.getPathInfo());
		
		// Provo a recuperare le info sulla sessione e sull'utente che tenta l'accesso alla BDL...
		RequestFilterUtils reqFilterUtils = new RequestFilterUtils();
		reqFilterUtils.checkLoggedUser(req, res, request.getHeader(propNameForHeaderIDPCCodiceFiscale), request.getHeader(propNameForHeaderIAMCodiceFiscale));
		
		log.debug("[RequestExternalFilter] Proseguo nella catena...");
		chain.doFilter(req, res);
	}
}
