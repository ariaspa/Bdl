package it.lispa.bdl.server.auth;


import java.util.Properties;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * Class RequestGenericFilter.
 */
public class RequestGenericFilter {
	
	/** log. */
	private static Logger log = Logger.getLogger(RequestGenericFilter.class);
	
	/** La Costante PROP_HEADER_IAM_CODICE_FISCALE. */
	protected static final String PROP_HEADER_IAM_CODICE_FISCALE 	= "HEADER_IAM_CODICE_FISCALE";
	
	/** La Costante PROP_HEADER_IDPC_CODICE_FISCALE. */
	protected static final String PROP_HEADER_IDPC_CODICE_FISCALE 	= "HEADER_IDPC_CODICE_FISCALE";

	/** prop file. */
	protected Properties propFile = new Properties();
	
	protected String propNameForHeaderIAMCodiceFiscale;
	protected String propNameForHeaderIDPCCodiceFiscale;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Entro nella init di RequestGenericFilter ");
		try {
			Resource resource = new ClassPathResource("/auth.properties");
			propFile = PropertiesLoaderUtils.loadProperties(resource);
			log.debug("Ho caricato il file di properties ");
			
			// Recupero le property IDPC_CODICE_FISCALE e IAM_CODICE_FISCALE...
			propNameForHeaderIDPCCodiceFiscale 	= propFile.getProperty(PROP_HEADER_IDPC_CODICE_FISCALE);
			propNameForHeaderIAMCodiceFiscale 	= propFile.getProperty(PROP_HEADER_IAM_CODICE_FISCALE);
			
		} catch(Exception e) {
			log.error("Errore nel caricamento del file di properties ", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// do nothing
	}
}
