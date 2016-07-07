package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeCollectionLight;
import it.lispa.bdlfe.dto.BdlfeInstitute;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.dto.BdlfeSearchPaginated;
import it.lispa.bdlfe.exceptions.ResourceNotFoundException;
import it.lispa.bdlfe.form.CommandForm;
import it.lispa.bdlfe.form.SearchForm;
import it.lispa.bdlfe.utils.BdlfeConstants;
import it.lispa.bdlfe.utils.BdlfeSearchItemsPerPage;
import it.lispa.bdlfe.utils.BdlfeSearchOrderOptions;
import it.lispa.bdlfe.utils.SearchUtils;
import it.lispa.bdlfe.utils.management.ManagementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstituteController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(InstituteController.class);
	
	@RequestMapping(value = "/institute/{cdInstitute}", method = RequestMethod.GET)
	public String getInstitute(@PathVariable("cdInstitute") Integer cdInstitute, Locale locale, Model model) {
		logger.debug(ps+"[getInstitute] Entering with cdInstitute=" + cdInstitute.toString());
		
		logger.debug(ps+"[getInstitute] Req: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		BdlfeInstitute institute = JsonConsumer.getInstitute(serverProps.getBackendUrl(), cdInstitute);
		logger.debug(ps+"[getInstitute] Res: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		if(institute==null)
			throw new ResourceNotFoundException(); 
		model.addAttribute("institute", institute);
		
		model.addAttribute("pageTitle", "Dettaglio Istituto \""+institute.getNome()+"\"");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/institute/"+cdInstitute, "Istituto: "+institute.getNome()));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		return "institute";
	}
	
	@RequestMapping(value = "/institute/{cdInstitute}/items/{page}")
	public ModelAndView getPaginatedInstituteItems(@PathVariable("cdInstitute") Integer cdInstitute, @PathVariable("page") Integer page,
			Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug(ps+"[getPaginatedInstituteItems] Entering method with cdInstitute="+cdInstitute+"/page="+page);
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 
		
		/* ResPP */
		BdlfeSearchItemsPerPage objResPP = (BdlfeSearchItemsPerPage)session.getAttribute("resPP");
		if(objResPP==null)
			objResPP = BdlfeSearchItemsPerPage.NUM15; 
		Integer resPP = Integer.valueOf(objResPP.getLabel());
		
		/* Paginazione */
		Map<String, Integer> rangePaginazione = getRangePaginazione(resPP, page);
		Integer startPaginazione = rangePaginazione.get("startPaginazione");		
		Integer endPaginazione = rangePaginazione.get("endPaginazione");		
//		logger.debug(ps+"[getPaginatedInstituteItems] startPaginazione="+startPaginazione+" endPaginazione="+endPaginazione);
	    
		/* Inizializzo i filtri */
		List<BdlfeRef> languages = null;
		List<BdlfeRef> types = null;
		List<BdlfeRef> kinds = null;
		List<BdlfeRef> subjects = null;
		List<BdlfeRef> graphicMaterials = null;
		List<BdlfeRef> supports = null;
		List<BdlfeRef> tecniques = null;
		List<BdlfeCollectionLight> collections = null;

		logger.debug(ps+"[getPaginatedInstituteItems] Req: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		BdlfeInstitute institute = JsonConsumer.getInstitute(serverProps.getBackendUrl(), cdInstitute);
		logger.debug(ps+"[getPaginatedInstituteItems] Res: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
				filtersToSearch.size(),
				BdlfeConstants.JSON_LOGICALOP_AND, 
				BdlfeConstants.JSON_FILTERTYPE_INSTITUTE,
				BdlfeConstants.JSON_COMPARISONOP_EQUALID,
				institute.getCodice().toString(),
				institute.getNome()
		);
		filtersToSearch.add(filter);
		
		SearchForm sf = new SearchForm(
				orderOption,
				filtersToSearch,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null
		);	
		
		logger.debug(ps+"[getPaginatedInstituteItems] Req: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		BdlfeSearchPaginated results = SearchUtils.doSearchAdvanced(serverProps.getBackendUrl(), sf, languages, types, kinds, subjects, 
				graphicMaterials, supports, tecniques, collections, startPaginazione, endPaginazione);
		logger.debug(ps+"[getPaginatedInstituteItems] Res: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		
		/* Ho dei risultati? */
		if(results.getResults().size()>0) {
			logger.debug(ps+"[getPaginatedInstituteItems] Get "+results.getResults().size()+" of "+results.getResCount()+" results... forwarding to items");
			
			session.setAttribute("searchResult", results);       
			session.setAttribute("page", page);  
			
			String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			/* Mostro la View mappata come "itemsview" (Lista degli oggetti afferenti all'Istituto selezionato) */
			return listItems(locale, model, buildURIForListItems(myURI, 1));
		}
		
		/* Non li ho? Mostro il dettaglio */
		model.addObject("institute", institute);
		model.addObject("pageTitle", "Dettaglio Istituto \"" + institute.getNome() + "\"");
		
		/* Compongo BdlfeBreadcrumb */
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/", "Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("#null", "Istituto \"" + institute.getNome() + "\""));
		model.addObject("breadcrumbs", breadcrumbs);
		
		model.addObject("errorMessage", "Nessun risultato trovato! ");
		logger.debug(ps+"[getPaginatedInstituteItems] No results found! ");
		/* Mostro la View mappata come "institute" (Dettaglio dell'Istituto selezionato) */
		
		model.setViewName("institute");
		return model;
	}
	
	@RequestMapping(value = "/institute/{cdInstitute}/items/changeorderby", method = RequestMethod.POST)
	public ModelAndView changeOrderBy(CommandForm cf, Locale locale, ModelAndView model, HttpServletRequest req) {
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return changeOrderBy(cf, locale, model, buildURIForListItems(myURI, 1));
	}
	
	@RequestMapping(value = "/institute/{cdInstitute}/items/changeviewtype", method = RequestMethod.POST)
	public ModelAndView changeViewType(@RequestParam(value = "viewType") String viewType, Locale locale, ModelAndView model, HttpServletRequest req) {
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return changeViewType(viewType, locale, model, buildURIForListItems(myURI, 1));
	}
	
	@RequestMapping(value = "/institute/{cdInstitute}/items/changeresultsperpage", method = RequestMethod.POST)
	public ModelAndView changeResultsPerPage(@PathVariable("cdInstitute") Integer cdInstitute, CommandForm cf, Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[changeResultsPerPage] Entering the method with resPP="+cf.getCommandResPPOpt());
		session.setAttribute("resPP", cf.getCommandResPPOpt());
		return getPaginatedInstituteItems(cdInstitute, 1, locale, model, req);
	}
}
