package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeCollection;
import it.lispa.bdlfe.dto.BdlfeCollectionLight;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.dto.BdlfeSearchPaginated;
import it.lispa.bdlfe.exceptions.MissingParameterException;
import it.lispa.bdlfe.form.CommandForm;
import it.lispa.bdlfe.form.SearchForm;
import it.lispa.bdlfe.utils.BdlfeConstants;
import it.lispa.bdlfe.utils.BdlfeSearchItemsPerPage;
import it.lispa.bdlfe.utils.BdlfeSearchOrderOptions;
import it.lispa.bdlfe.utils.SearchUtils;
import it.lispa.bdlfe.utils.management.ManagementUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@ModelAttribute("languages")
	public List<BdlfeRef> getLanguages() {
		return JsonConsumer.getLanguages(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("kinds")
	public List<BdlfeRef> getKinds() {
		return JsonConsumer.getKinds(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("subjects")
	public List<BdlfeRef> getSubjects() {
		return JsonConsumer.getSubjects(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("graphicMaterials")
	public List<BdlfeRef> getGraphicMaterials() {
		return JsonConsumer.getGraphicMaterials(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("supports")
	public List<BdlfeRef> getSupports() {
		return JsonConsumer.getSupports(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("tecniques")
	public List<BdlfeRef> getTecniques() {
		return JsonConsumer.getTecniques(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("collections")
	public List<BdlfeCollection> getCollections() {
		return JsonConsumer.getCollections(serverProps.getBackendUrl());
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String loadForm(Locale locale, Model model) {
		model.addAttribute("pageTitle", "Ricerca Avanzata");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/search","Ricerca Avanzata"));		
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		logger.debug("[loadForm] i have no searchFormObj in session, i will init a new one");
		SearchForm sf = SearchUtils.initSearchForm();
		
		model.addAttribute("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return "search";
	}

	@RequestMapping(value = "/search/init", method = RequestMethod.GET)
	public String initForm(Locale locale, Model model) {
		
		logger.debug(ps+"[initForm] Req: initSearchForm() - Time: "+ManagementUtils.getCpuTime());
		SearchForm sf = SearchUtils.initSearchForm();
		logger.debug(ps+"[initForm] Res: initSearchForm() - Time: "+ManagementUtils.getCpuTime());
		
		model.addAttribute("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		return "redirect:/search/";
	}
	
	@RequestMapping(value = "/search/author", method = RequestMethod.POST)
	public ModelAndView doSearchByAuthor(@RequestParam("cdAuthor") Integer cdAuthor, @RequestParam("descrAuthor") String descrAuthor, 
			Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[doSearchByAuthor] Entering...");
		if(cdAuthor==null)
			throw new MissingParameterException();
		logger.debug("[doSearchByAuthor] Entering with cdAuthor="+cdAuthor+" descrAuthor=\""+descrAuthor+"\"");
		
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
			filtersToSearch.size(),
			BdlfeConstants.JSON_LOGICALOP_AND, 
			BdlfeConstants.JSON_FILTERTYPE_AUTHOR,
			BdlfeConstants.JSON_COMPARISONOP_EQUALID,
			cdAuthor.toString(), 
			descrAuthor
		);
		filtersToSearch.add(filter);

		/* Reset paginazione */
		session.setAttribute("page", new Integer(1));
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 
		
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
		
		/* Ricerco e azzero la paginazione */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(1)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty()) {
			sf = SearchUtils.initSearchForm();
		}
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
		
	@RequestMapping(value = "/search/subject", method = RequestMethod.POST)
	public ModelAndView doSearchBySubject(@RequestParam("cdSubject") Integer cdSubject, @RequestParam("descrSubject") String descrSubject, 
			Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[doSearchBySubject] Entering...");
		if(cdSubject==null)
			throw new MissingParameterException();
		logger.debug("Entering [doSearchBySubject] with cdSubject="+cdSubject+" descrSubject=\""+descrSubject+"\"");
		
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
			filtersToSearch.size(),BdlfeConstants.JSON_LOGICALOP_AND, 
			BdlfeConstants.JSON_FILTERTYPE_SUBJECT,
			BdlfeConstants.JSON_COMPARISONOP_EQUALID,
			cdSubject.toString(), 
			descrSubject
		);
		filtersToSearch.add(filter);

		/* Reset paginazione */
		session.setAttribute("page", new Integer(1));
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 
		
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
		
		/* Ricerco e azzero la paginazione */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(1)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty()) {
			sf = SearchUtils.initSearchForm();
		}
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/title", method = RequestMethod.POST)
	public ModelAndView doSearchByTitle(@RequestParam("cdItemType") Integer cdItemType, @RequestParam("descrTitle") String descrTitle, 
			Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[doSearchByTitle] Entering...");
		if(descrTitle==null)
			throw new MissingParameterException();
		logger.debug("Entering [doSearchByTitle] with cdItemType="+cdItemType.toString()+" descrTitle=\""+descrTitle+"\"");
		
		/* Reset paginazione */
		session.setAttribute("page", new Integer(1));
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 
		
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
			filtersToSearch.size(),
			BdlfeConstants.JSON_LOGICALOP_AND, 
			BdlfeConstants.JSON_FILTERTYPE_TITLE,
			BdlfeConstants.JSON_COMPARISONOP_LIKE,
			descrTitle,
			descrTitle
		);
		filtersToSearch.add(filter);

		if(cdItemType!=null && !cdItemType.equals(new Integer(0))) {
			String typeDescription = "";
			Map<String, Object> mod = model.getModel();
			List<BdlfeRef> tipi = (List<BdlfeRef>) mod.get("types");
			for(BdlfeRef tipo:tipi){
				if(tipo.getId().equals(new BigDecimal(cdItemType))){
					typeDescription=tipo.getName();
				}
			}
			BdlfeSearchFilter cmpFilterType = new BdlfeSearchFilter(
					filtersToSearch.size(),
					BdlfeConstants.JSON_LOGICALOP_AND, 
					BdlfeConstants.JSON_FILTERTYPE_TYPE , 
					BdlfeConstants.JSON_COMPARISONOP_EQUALID, 
					cdItemType.toString(), 
					typeDescription
			);
			filtersToSearch.add(cmpFilterType);
		}
		
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
		
		/* Ricerco e azzero la paginazione */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(1)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty()) {
			sf = SearchUtils.initSearchForm();
		}
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
	
	@RequestMapping(value = "/search/addtitlefilter", method = RequestMethod.POST)
	public ModelAndView addTitleFilter(@RequestParam String filterTitle, Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[addTitleFilter] Entering the method");

		BdlfeSearchPaginated bs = (BdlfeSearchPaginated) session.getAttribute("searchResult");
		if(bs==null){
			logger.debug("[addTitleFilter] No results in session");

			model.setViewName("search");
			return model;
		}
		
		/* Reset paginazione */
		session.setAttribute("page", new Integer(1));
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 

		List<BdlfeSearchFilter> filtersToSearch = bs.getFilters();
		BdlfeSearchFilter filter = new BdlfeSearchFilter(
				filtersToSearch.size(),
				BdlfeConstants.JSON_LOGICALOP_AND, 
				BdlfeConstants.JSON_FILTERTYPE_TITLE,
				BdlfeConstants.JSON_COMPARISONOP_LIKE,
				filterTitle,
				filterTitle
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
		
		/* Ricerco e azzero la paginazione */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(1)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty()) {
			sf = SearchUtils.initSearchForm();
		}
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
	
	@RequestMapping(value = "/search/addfilter/{myhash}", method = RequestMethod.GET)
	public ModelAndView addFilter(@PathVariable String myhash, Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[addFilter] Entering the method");

		BdlfeSearchPaginated bs = (BdlfeSearchPaginated) session.getAttribute("searchResult");
		if(bs==null) {
			logger.debug("[addFilter] No results in session");

			model.setViewName("search");
			return model;
		}
		
		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 

		/* Aggiorno i filtri */
		List<BdlfeSearchFilter> filtersAddizionali = bs.getOtherfilters();
		List<BdlfeSearchFilter> filtersToSearch = bs.getFilters();
		for(BdlfeSearchFilter filter:filtersAddizionali){
			if(filter.myHash().equals(myhash)){
				filter.setLogicalOperator(BdlfeConstants.JSON_LOGICALOP_AND);
				filter.setOrder(filtersToSearch.size());
				filtersToSearch.add(filter);
			}
		}

		SearchForm sf = (SearchForm) session.getAttribute("searchFormObj");
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(sf==null)
			sf = SearchUtils.initSearchForm();
		sf.setCompositionFilters(filtersToSearch);
		sf.setOrderOption(orderOption);
		
		/* Aggiorno la pagina selezionata */
		session.setAttribute("page", new Integer(1));
		/* Ricerco */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(2)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty())
			sf = SearchUtils.initSearchForm();
		
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
	
	@RequestMapping(value = "/search/removefilter/{myhash}", method = RequestMethod.GET)
	public ModelAndView removeFilter(@PathVariable String myhash, Locale locale, ModelAndView model, HttpServletRequest req) {
		logger.debug("[removeFilter] Entering the method");

		BdlfeSearchPaginated bs = (BdlfeSearchPaginated) session.getAttribute("searchResult");
		if(bs==null){
			logger.debug("[removeFilter] No results in session");
			
			model.setViewName("search");
			return model;
		}

		/* OrderBy */
		BdlfeSearchOrderOptions orderOption = (BdlfeSearchOrderOptions)session.getAttribute("orderBy");
		if(orderOption==null)
			orderOption = BdlfeSearchOrderOptions.RELEVANCE; 
		
		List<BdlfeSearchFilter> filters = bs.getFilters();
		List<BdlfeSearchFilter> filtersToSearch = new ArrayList<BdlfeSearchFilter>();
		for(BdlfeSearchFilter filter:filters){
			if(!filter.myHash().equals(myhash)){
				filtersToSearch.add(filter);
			}
		}
		
		SearchForm sf = (SearchForm) session.getAttribute("searchFormObj");
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(sf==null)
			sf = SearchUtils.initSearchForm();
		sf.setCompositionFilters(filtersToSearch);
		sf.setOrderOption(orderOption);
		
		/* Aggiorno la pagina selezionata */
		session.setAttribute("page", new Integer(1));
		/* Ricerco */
		String myURI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		model = doSearch(new Integer(1), sf, locale, model, req, buildURIForListItems(myURI, new Integer(2)));
		
		BdlfeSearchPaginated results = (BdlfeSearchPaginated) session.getAttribute("searchResult"); 
		/* Qualcosa e' andato storto? Inizializzo il form di ricerca */
		if(results==null || results.getResults().isEmpty())
			sf = SearchUtils.initSearchForm();
		
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);
		
		return model;
	}
	
	@RequestMapping(value = "/search/{page}", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView doSearch(@PathVariable("page") Integer page, SearchForm sf, 
			Locale locale, ModelAndView model, HttpServletRequest req, String myURI) {
		logger.debug("[doSearch] Entering with page="+page);
		
		/* changeResultsPerPage */
		if(sf==null) 
			sf = (SearchForm) session.getAttribute("searchFormObj");
		
		if(!sf.isNotNull())
			sf = (SearchForm) session.getAttribute("searchFormObj");
		model.addObject("searchFormObj", sf);
		session.setAttribute("searchFormObj", sf);

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
		
		Map<String, Object> md = model.getModel();
		@SuppressWarnings("unchecked")
		List<BdlfeRef> languages = (List<BdlfeRef>) md.get("languages");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> types = (List<BdlfeRef>) md.get("types");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> kinds = (List<BdlfeRef>) md.get("kinds");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> subjects = (List<BdlfeRef>) md.get("subjects");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> graphicMaterials = (List<BdlfeRef>) md.get("graphicMaterials");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> supports = (List<BdlfeRef>) md.get("supports");
		@SuppressWarnings("unchecked")
		List<BdlfeRef> tecniques = (List<BdlfeRef>) md.get("tecniques");
		@SuppressWarnings("unchecked")
		List<BdlfeCollectionLight> collections = (List<BdlfeCollectionLight>) md.get("collections");

		logger.debug(ps+"[doSearch] Req: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		BdlfeSearchPaginated results = SearchUtils.doSearchAdvanced(serverProps.getBackendUrl(), sf, languages, types, kinds, 
				subjects, graphicMaterials, supports, tecniques, collections, startPaginazione, endPaginazione);
		logger.debug(ps+"[doSearch] Res: doSearchAdvanced() - Time: "+ManagementUtils.getCpuTime());
		
		if(results!=null) {
			if(!results.getResults().isEmpty()) {
				/* Ho dei risultati? */
				logger.info(ps+"[doSearch] Get "+results.getResults().size()+" of "+results.getResCount()+" results... forwarding to items");

				session.setAttribute("searchResult", results);       
				session.setAttribute("orderBy", sf.getOrderOption());       
				session.setAttribute("page", page);     
				
				/* Mostro la view mappata come "itemsview" */
				return listItems(locale, model, myURI);
			} else {
				session.setAttribute("searchResult", results); 
			}
		}

		/* Non li ho? Mostro il form di ricerca avanzata */
		model.addObject("pageTitle", "Ricerca Avanzata");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/search","Ricerca Avanzata"));		
		model.addObject("breadcrumbs", breadcrumbs);
		
		logger.debug("[doSearch] No results found! ");
		model.addObject("errorMessage", "Nessun risultato trovato! ");
		
		/* Mostro la view mappata come "search" */
		model.setViewName("search");
		return model;
	}
	
	@RequestMapping(value = "/search/changeorderby", method = RequestMethod.POST)
	public ModelAndView changeOrderBy(CommandForm cf, Locale locale, ModelAndView model) {
		return changeOrderBy(cf, locale, model, SearchForm.mainFormAction);
	}
	@RequestMapping(value = "/search/changeviewtype", method = RequestMethod.POST)
	public ModelAndView changeViewType(@RequestParam(value = "viewType") String viewType, Locale locale, ModelAndView model) {
		return changeViewType(viewType, locale, model, SearchForm.mainFormAction);
	}
	@RequestMapping(value = "/search/changeresultsperpage", method = RequestMethod.POST)
	public ModelAndView changeResultsPerPage(CommandForm cf, Locale locale, 
			ModelAndView model, HttpServletRequest req) {
		logger.debug("[changeResultsPerPage] Entering the method with resPP="+cf.getCommandResPPOpt());
		session.setAttribute("resPP", cf.getCommandResPPOpt());
		return doSearch(new Integer(1), null, locale, model, req, SearchForm.mainFormAction);
	}
}
