package it.lispa.bdl.server.servlet;

import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.utils.ExcelMonitoraggioLav;
import it.lispa.bdl.server.utils.ExcelWriterUtils;
import it.lispa.bdl.server.utils.RequestFilterUtils;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Class MonitoraggioLavorazioneServer.
 */
public class MonitoraggioLavorazioneServer extends MonitoraggioServer {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 8556408473612882094L;

	/** log. */
	private static Logger log = Logger.getLogger(MonitoraggioLavorazioneServer.class);

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String calledPath = request.getPathInfo();
		log.debug("Entro nella doGet con calledPath="+calledPath); // /cdOggetto
		
		ComboDTO objRet = getFilterAndFilterCode(calledPath);
	
		BigDecimal filterCode = objRet.getId();
		String filter = objRet.getDesc();

		log.debug("Servlet richiamata con parametri:");
		log.debug("filter="+filter);
		log.debug("filterCode="+filterCode);

		BigDecimal filterIstituto = null;
		BigDecimal filterProgetto = null;
		BigDecimal filterCollezione = null;
		
		if(filter!=null){
			if(filter.equals(SubMenuItemDTO.TIPO_ISTITUTO)){
				filterIstituto = filterCode;				
			}else if(filter.equals(SubMenuItemDTO.TIPO_PROGETTO)){
				filterProgetto = filterCode;
			}else if(filter.equals(SubMenuItemDTO.TIPO_COLLEZIONE)){
				filterCollezione = filterCode;
			}
		}
		
		List<BigDecimal> cdEntiAbilitati = new ArrayList<BigDecimal>();

		BdlUtente utente = utenteRepository.findByCdUtente((BigDecimal)request.getSession().getAttribute(RequestFilterUtils.SESSION_ACTIVE_USER_CD));
		BdlRuolo ruoloUtente = ruoloRepository.findByCdRuolo(utente.getCdRuolo());

		boolean flAmministratore = false;

		if(BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(ruoloUtente.getDnNome())){
			cdEntiAbilitati = secUtils.getCdEntiForUtente(utente.getCdUtente());
			flAmministratore = false;
		}else{
			flAmministratore = true;
		}
		
		List<VOggettoCountDTO> righeConteggi = vOggetti.findConteggioPerStato( filterIstituto, filterProgetto, filterCollezione, cdEntiAbilitati, flAmministratore );

		ExcelMonitoraggioLav myExcel;
		try {
			myExcel = new ExcelMonitoraggioLav(righeConteggi);
			response.setContentType(ExcelWriterUtils.xlsxMimeType);
			response.setHeader("Content-Disposition", "attachment; filename=monitoraggioLavorazione.xlsx");
			
			myExcel.getWb().write(response.getOutputStream());
			response.getOutputStream().close();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
