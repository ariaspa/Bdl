package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeCollection;
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
public class CollectionController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

	@RequestMapping(value = "/collection/{cdCollection}", method = RequestMethod.GET)
	public String getCollection(@PathVariable("cdCollection") Integer cdCollection, Locale locale, Model model) {
		logger.debug("[getCollection] Entering with cdCollection="+cdCollection.toString());
		
		logger.debug(ps+"[getCollection] Req: getCollection() - Time: "+ManagementUtils.getCpuTime());
		BdlfeCollection collection = JsonConsumer.getCollection(serverProps.getBackendUrl(), cdCollection);
		logger.debug(ps+"[getCollection] Res: getCollection() - Time: "+ManagementUtils.getCpuTime());
		if(collection==null)
			throw new ResourceNotFoundException(); 
		model.addAttribute("collection", collection);
		
		model.addAttribute("pageTitle", "Dettaglio Collezione \""+collection.getTitle()+"\"");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/collection/"+cdCollection,"Collezione: "+collection.getTitle()));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		return "collection";
	}
	
	@RequestMapping(value = "/collection/{cdCollection}/institute", method = RequestMethod.GET)
	public String getCollectionFromInstitute(@PathVariable("cdCollection") Integer cdCollection, Locale locale, Model model) {
		logger.debug("[getCollectionFromInstitute] Entering with cdCollection="+cdCollection.toString());
		
		logger.debug(ps+"[getCollectionFromInstitute] Req: getCollection() - Time: "+ManagementUtils.getCpuTime());
		BdlfeCollection collection = JsonConsumer.getCollection(serverProps.getBackendUrl(), cdCollection);
		logger.debug(ps+"[getCollectionFromInstitute] Res: getCollection() - Time: "+ManagementUtils.getCpuTime());
		if(collection==null)
			throw new ResourceNotFoundException(); 
		model.addAttribute("collection", collection);
		
		logger.debug(ps+"[getCollectionFromInstitute] Req: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		BdlfeInstitute institute = JsonConsumer.getInstitute(serverProps.getBackendUrl(), collection.getInstitute().getId().intValue());
		logger.debug(ps+"[getCollectionFromInstitute] Res: getInstitute() - Time: "+ManagementUtils.getCpuTime());
		
		model.addAttribute("pageTitle", "Dettaglio Collezione \""+collection.getTitle()+"\"");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/institute/"+institute.getCodice(), "Istituto: "+institute.getNome()));
		breadcrumbs.add(new BdlfeBreadcrumb("/collection/"+collection.getId(),"Collezione: "+collection.getTitle()));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		return "collection";
	}
	
	@RequestMapping(value = "/collection/{cdCollection}/items/{page}", method = RequestMethod.GET)
	public ModelAndView getPaginatedCollectionItems(@PathVariable("cdCollection") Integer cdCollection, @PathVariable("page") Integer page, 
			Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[getPaginatedCollectionItems] Entering with cdCollection="+cdCollection+"/page="+page);
		
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

		logger.debug(ps+"[getPaginatedCollectionItems] Req: getCollection() - Time: "+ManagementUtils.getCpuTime());
		BdlfeCollection collection = JsonConsumer.getCollection(serverProps.getBackendUrl(), cdCollection);
		logger.debug(ps+"[getPaginatedCollectionItems] Res: getCollection() - Time: "+ManagementUtils.getCpuTime());
		
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
				filtersToSearch.size(),
				BdlfeConstants.JSON_LOGICALOP_AND, 
				BdlfeConstants.JSON_FILTERTYPE_COLLECTION,
				BdlfeConstants.JSON_COMPARISONOP_EQUALID,
				collection.getId().toString(),
				collection.getTitle()
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

		logger.debug(ps+"[getPaginatedCollectionItems] Req: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		BdlfeSearchPaginated results = SearchUtils.doSearchAdvanced(serverProps.getBackendUrl(),sf, languages, types, kinds, subjects, 
				graphicMaterials, supports, tecniques, collections, startPaginazione, endPaginazione);
		logger.debug(ps+"[getPaginatedCollectionItems] Res: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		
		/* Ho dei risultati? Li mostro */
		if(results.getResults().size()>0) {
			logger.debug(ps+"[getPaginatedCollectionItems] Get "+results.getResults().size()+" of "+results.getResCount()+" results... forwarding to items");

			session.setAttribute("searchResult", results);       
//			session.setAttribute("orderBy", sf.getOrderOption());       
			session.setAttribute("page", page);   
			
			String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
			/* Mostro la View mappata come "itemsview" (Lista degli oggetti afferenti alla Collezione selezionata) */
			return listItems(locale, model, buildURIForListItems(myURI, 1));
		}
		
		/* Non li ho? Mostro il dettaglio */
		model.addObject("collection", collection);
		
		model.addObject("pageTitle", "Dettaglio collezione \""+collection.getTitle()+"\"");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("#null","Collezione \""+collection.getTitle()+"\""));
		model.addObject("breadcrumbs", breadcrumbs);
		
		logger.debug("[getPaginatedCollectionItems] No results found! ");
		model.addObject("errorMessage", "Nessun risultato trovato! ");
		/* Mostro la View mappata come "collection" (Dettaglio della Collezione selezionata) */

		model.setViewName("collection");
		return model;
	}
	
	@RequestMapping(value = "/collection/{cdCollection}/items/changeorderby", method = RequestMethod.POST)
	public ModelAndView changeOrderBy(CommandForm cf, Locale locale, ModelAndView model, HttpServletRequest req) {
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return changeOrderBy(cf, locale, model, buildURIForListItems(myURI, 1));
	}
	
	@RequestMapping(value = "/collection/{cdCollection}/items/changeviewtype", method = RequestMethod.POST)
	public ModelAndView changeViewType(@RequestParam(value = "viewType") String viewType, Locale locale, ModelAndView model, HttpServletRequest req) {
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return changeViewType(viewType, locale, model, buildURIForListItems(myURI, 1));
	}
	
	@RequestMapping(value = "/collection/{cdCollection}/items/changeresultsperpage", method = RequestMethod.POST)
	public ModelAndView changeResultsPerPage(@PathVariable("cdCollection") Integer cdCollection, CommandForm cf, Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[changeResultsPerPage] Entering the method with resPP="+cf.getCommandResPPOpt());
		session.setAttribute("resPP", cf.getCommandResPPOpt());
		return getPaginatedCollectionItems(cdCollection, 1, locale, model, req);
	}
}
