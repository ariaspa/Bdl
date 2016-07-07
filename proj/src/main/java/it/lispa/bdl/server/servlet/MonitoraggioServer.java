package it.lispa.bdl.server.servlet;

import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.server.utils.SecurityUtils;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.ComboDTO;

import java.math.BigDecimal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class MonitoraggioCriticiServer.
 */
public class MonitoraggioServer extends HttpServlet {


	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 8556408473612882094L;

	/** log. */
	private static Logger log = Logger.getLogger(MonitoraggioServer.class);

	/** v oggetti. */
	@Autowired
	protected VOggettiUtils vOggetti;
	
	/** sec utils. */
	@Autowired
	protected SecurityUtils secUtils;
	
	/** ruolo repository. */
	@Autowired
	protected BdlRuoloRepository ruoloRepository;
	
	/** utente repository. */
	@Autowired
	protected BdlUtenteRepository utenteRepository;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
	
	protected ComboDTO getFilterAndFilterCode(String calledPath) {
		String filter = null;
		BigDecimal filterCode = null;
		try {
			String[] pathParts = calledPath.split("/");
			for(int i =0;i<pathParts.length;i++){
				log.debug("pathParts["+i+"]="+pathParts[i]);
			}
			if(pathParts[1]!=null && pathParts[2]!=null){
				filter = pathParts[1];
				filterCode = new BigDecimal(Long.valueOf(pathParts[2]));
			}			
		}catch (Exception e){
			log.debug("Exception nella parsificazione della riga di input: "+e.getMessage());
		}
		
		return new ComboDTO(filterCode, filter);
	}
	
}
