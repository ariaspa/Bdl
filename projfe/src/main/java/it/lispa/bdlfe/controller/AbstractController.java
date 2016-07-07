package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeItemLight;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.dto.BdlfeSearchPaginated;
import it.lispa.bdlfe.form.CommandForm;
import it.lispa.bdlfe.utils.BdlfeConstants;
import it.lispa.bdlfe.utils.BdlfeItemLightComparator;
import it.lispa.bdlfe.utils.BdlfeSearchDateFilterOptions;
import it.lispa.bdlfe.utils.BdlfeSearchFilterTypes;
import it.lispa.bdlfe.utils.BdlfeSearchItemsPerPage;
import it.lispa.bdlfe.utils.BdlfeSearchLogicalOperators;
import it.lispa.bdlfe.utils.BdlfeSearchOrderOptions;
import it.lispa.bdlfe.utils.BdlfeServerProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

	@Autowired 
	HttpSession session;
	
	@Autowired 
	protected BdlfeServerProperties serverProps;
	
	protected static final String ps = "_______________";	

	@ModelAttribute("types")
	public List<BdlfeRef> getTypes() {
		return JsonConsumer.getTypes(serverProps.getBackendUrl());
	}
	
	@ModelAttribute("backendUrl")
	public String getBackendUrl() {
		return serverProps.getBackendUrl();
	}
	
	@ModelAttribute("backendPublicUrl")
	public String getBackendPublicUrl() {
		return serverProps.getBackendPublicUrl();
	}
	
	@ModelAttribute("audioplayerUrl")
	public String getAudioplayerUrl() {
		return serverProps.getAudioplayerUrl();
	}
	
	@ModelAttribute("mapreaderUrl")
	public String getMapreaderUrl() {
		return serverProps.getMapreaderUrl();
	}
	
	@ModelAttribute("bookreaderUrl")
	public String getBookreaderUrl() {
		return serverProps.getBookreaderUrl();
	}
	
	@ModelAttribute("otherFiltersNum")
	public Integer getOtherFiltersNum() {
		return serverProps.getOtherFiltersNum();
	}
	
	@ModelAttribute("dateFilterOptions")
    public Map<String, String> getDateFilterOptions(){
		BdlfeSearchDateFilterOptions[] options = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        options = BdlfeSearchDateFilterOptions.BDLFE_SEARCH_DATEFILTER_OPTIONS.toArray(
        		new BdlfeSearchDateFilterOptions[BdlfeSearchDateFilterOptions.BDLFE_SEARCH_DATEFILTER_OPTIONS.size()]);

        for (BdlfeSearchDateFilterOptions option : options) {
            map.put(option.name(), option.getLabel());
        }
        return map;
    }
	
	@ModelAttribute("filterTypes")
    public Map<String, String> getFilterTypes(){
		BdlfeSearchFilterTypes[] options = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        options = BdlfeSearchFilterTypes.BDLFE_SEARCH_FILTER_TYPES.toArray(
        		new BdlfeSearchFilterTypes[BdlfeSearchFilterTypes.BDLFE_SEARCH_FILTER_TYPES.size()]);

        for (BdlfeSearchFilterTypes option : options) {
            map.put(option.name(), option.getLabel());
        }
        return map;
    }
	
	@ModelAttribute("resultsPerPageOptions")
    public Map<String, String> getResultsPerPageOptions(){
		BdlfeSearchItemsPerPage[] options = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        options = BdlfeSearchItemsPerPage.BDLFE_SEARCH_ITEMS_PER_PAGE.toArray(
        		new BdlfeSearchItemsPerPage[BdlfeSearchItemsPerPage.BDLFE_SEARCH_ITEMS_PER_PAGE.size()]);

        for (BdlfeSearchItemsPerPage option : options) {
            map.put(option.name(), option.getLabel());
        }
        return map;
    }
	
	@ModelAttribute("logicalOperators")
    public Map<String, String> getLogicalOperators(){
		BdlfeSearchLogicalOperators[] options = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        options = BdlfeSearchLogicalOperators.BDLFE_SEARCH_LOGICAL_OPERATORS.toArray(
        		new BdlfeSearchLogicalOperators[BdlfeSearchLogicalOperators.BDLFE_SEARCH_LOGICAL_OPERATORS.size()]);

        for (BdlfeSearchLogicalOperators option : options) {
            map.put(option.name(), option.getLabel());
        }
        return map;
    }
	
	@ModelAttribute("orderOptions")
    public Map<String, String> getOrderOptions(){
		BdlfeSearchOrderOptions[] options = null;
        Map<String, String> map = new LinkedHashMap<String, String>();

        options = BdlfeSearchOrderOptions.BDLFE_SEARCH_ORDER_OPTIONS.toArray(
        		new BdlfeSearchOrderOptions[BdlfeSearchOrderOptions.BDLFE_SEARCH_ORDER_OPTIONS.size()]);

        for (BdlfeSearchOrderOptions option : options) {
            map.put(option.name(), option.getLabel());
        }
        return map;
    }
	
	protected Map<String, Integer> getRangePaginazione(Integer resPP, Integer page) {
		Map<String, Integer> toRet = new HashMap<String, Integer>();
		Integer startPaginazione = resPP*(page-1)+1;		
		Integer endPaginazione = startPaginazione+resPP-1;
		logger.debug(ps+"[getPaginatedInstituteItems] startPaginazione="+startPaginazione+" endPaginazione="+endPaginazione);
		toRet.put("startPaginazione", startPaginazione);
		toRet.put("endPaginazione", endPaginazione);
		return toRet;
	}
	
	protected String buildURIForListItems(String myURI, Integer index) {
		String[] myURIParts = myURI.split("/");
		StringBuilder toRet = new StringBuilder();
		for(int i=0; i<myURIParts.length-index; i++) {
			toRet.append(myURIParts[i]);
			toRet.append("/");
		}
		return toRet.toString();
	}
	
	protected ModelAndView changeViewType(String viewType, Locale locale, ModelAndView model, String myURI) {
		logger.debug("[changeViewType] Entering the method with viewType="+viewType+"/myURI="+myURI);
		session.setAttribute("viewType", viewType);
		return listItems(locale, model, myURI);
	}
	
	protected ModelAndView changeOrderBy(CommandForm cf, Locale locale, ModelAndView model, String myURI) {
		logger.debug("[changeOrderBy] Entering the method with orderBy="+cf.getCommandOrdOpt()+"/myURI="+myURI);
		session.setAttribute("orderBy", cf.getCommandOrdOpt());
		return listItems(locale, model, myURI);
	}
	
	@SuppressWarnings("unchecked")
	protected ModelAndView listItems(Locale locale, ModelAndView model, String myURI) {
		Integer page = (Integer) getSessionNvl("page", 1);
		logger.debug("[listItems] Entering the method with page="+page);
		
		BdlfeSearchPaginated sf = (BdlfeSearchPaginated) session.getAttribute("searchResult");
		/* Ho in sessione dei risultati? */
		if(sf==null) {
			logger.debug("[listItems] No results in session! ");
			
			model.setViewName("redirect:/search/");
			return model;
		} else {
			model.addObject("pageTitle", "Risultati di ricerca");
			
			List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
			breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
			breadcrumbs.add(new BdlfeBreadcrumb("/items","Risultati di ricerca"));
			model.addObject("breadcrumbs", breadcrumbs);			
			
			/* Modalità di visione */
			String viewType = (String) getSessionNvl("viewType", BdlfeConstants.BDLFE_ITEMS_VIEW_TYPE_PREVIEW);
			model.addObject("viewType", viewType);
			
			if(viewType.equals(BdlfeConstants.BDLFE_ITEMS_VIEW_TYPE_LIST)){
				model.addObject("viewTypeList", true);
				model.addObject("viewTypePreview", false);
			}else{
				model.addObject("viewTypeList", false);
				model.addObject("viewTypePreview", true);
			}
			
			/* Risultati per pagina */
			BdlfeSearchItemsPerPage resPP = (BdlfeSearchItemsPerPage) getSessionNvl("resPP",BdlfeSearchItemsPerPage.NUM15);
			model.addObject("resPP", resPP);
			CommandForm resPPCommandFormObj = new CommandForm(resPP);
			model.addObject("resPPCommandFormObj", resPPCommandFormObj);
			
			/* Ordinamento */
			BdlfeSearchOrderOptions orderBy = (BdlfeSearchOrderOptions) getSessionNvl("orderBy",BdlfeSearchOrderOptions.RELEVANCE);
			model.addObject("orderBy", orderBy);
			CommandForm orderCommandFormObj = new CommandForm(orderBy);
			model.addObject("orderCommandFormObj", orderCommandFormObj);
			
			/* Risultati */
			List<BdlfeItemLight> results = sf.getResults();
			BdlfeItemLightComparator comparator = new BdlfeItemLightComparator();
			comparator.setCompareMethod(orderBy);
			Collections.sort(results, comparator);
			
			/* # Risultati */ 
			logger.debug("[listItems] I have found "+results.size()+" of "+sf.getResCount()+" items in results. ");
			model.addObject("resCount", sf.getResCount());
			
			Integer resPPInt = new Integer(resPP.getLabel());
			Double numPagesDbl = Math.ceil((float)sf.getResCount()/resPPInt);
			Integer numPages =  Math.min(numPagesDbl.intValue(), 300);			
			model.addObject("numPages",numPages);
			
			page = Math.min(page, numPages);		
			model.addObject("page",page);
			
			Integer resultIdxStart = (page-1)*resPPInt;		
			model.addObject("resultIdxStart",resultIdxStart);
			
			Integer resultIdxEnd = Math.min(resultIdxStart+resPPInt-1, sf.getResCount()-1);
			model.addObject("resultIdxEnd",resultIdxEnd);
			
			Integer printStartPage = 1;
			Integer printEndPage = numPages;
			Integer maxPrintedPages = 7;
			
			if(numPages.compareTo(maxPrintedPages)>0){
				// devo capire dove far terminare di stampare pagine...
				// so di avere maxPrintedPages slot a disposizione
				
				Integer sidePages = (new Double(Math.floor(maxPrintedPages/2))).intValue();
				
				Integer rightSlots = Math.min(numPages-page,sidePages);
				printEndPage = page + rightSlots;
				
				printStartPage = Math.max(1, printEndPage-maxPrintedPages+1 );
				
				Integer pagesReallyPrinted = printEndPage-printStartPage+1;
				
				if(pagesReallyPrinted.compareTo(maxPrintedPages)<0 ){
					printEndPage = Math.min(numPages, maxPrintedPages);
				}
			}
			model.addObject("printStartPage",printStartPage);
			model.addObject("printEndPage",printEndPage);
			model.addObject("numPages", numPages);
			
			logger.debug("[listItems] numPages = "+numPages);
			logger.debug("[listItems] page = "+page);
			
			logger.debug("[listItems] resultIdxStart = "+resultIdxStart);
			logger.debug("[listItems] resultIdxEnd = "+resultIdxEnd);
			
			logger.debug("[listItems] printStartPage = "+printStartPage);
			logger.debug("[listItems] printEndPage = "+printEndPage);
			
			/* Creo i link utilizzati per la paginazione */
			List<String> changePageUrls = new ArrayList<String>();
			if(myURI==null) {
				myURI = "";
			}
			changePageUrls.add(myURI+(1));
			for(int i=printStartPage; i<=printEndPage; i++) {
				changePageUrls.add(myURI+(i));
			}
			changePageUrls.add(myURI+(numPages));
			model.addObject("changePageUrls", changePageUrls);
			
			String changeViewTypeUrl = myURI+"changeviewtype";
			model.addObject("changeViewTypeUrl", changeViewTypeUrl);
			
			String changeOrderByUrl = myURI+"changeorderby";
			model.addObject("changeOrderByUrl", changeOrderByUrl);
			
			String changeResPPUrl = myURI+"changeresultsperpage";
			model.addObject("changeResPPUrl", changeResPPUrl);
			
			logger.debug("[listItems] changeViewTypeUrl = "+changeViewTypeUrl);
			logger.debug("[listItems] changeOrderByUrl = "+changeOrderByUrl);
			logger.debug("[listItems] changeResPPUrl = "+changeResPPUrl);
			
			model.addObject("results", results);
			
			List<BdlfeSearchFilter> activeFilters = sf.getFilters();
			List<BdlfeSearchFilter> otherFilters = sf.getOtherfilters();
			/* Active Filters */
			if(activeFilters!=null) {
				Iterator<?> activeFiltersMapItr = getOtherFiltersMap(activeFilters).entrySet().iterator();
				while (activeFiltersMapItr.hasNext()) {
					Map.Entry<String, List<BdlfeSearchFilter>> activeFiltersMapEntry = (Map.Entry<String, List<BdlfeSearchFilter>>) activeFiltersMapItr.next();
					model.addObject("active" + activeFiltersMapEntry.getKey(), activeFiltersMapEntry.getValue());
				}
			}
			/* Other Filters */
			if(otherFilters!=null) {
				Iterator<?> otherFiltersMapItr = getOtherFiltersMap(otherFilters).entrySet().iterator();
				while (otherFiltersMapItr.hasNext()) {
					Map.Entry<String, List<BdlfeSearchFilter>> otherFiltersMapEntry = (Map.Entry<String, List<BdlfeSearchFilter>>) otherFiltersMapItr.next();
					model.addObject("other" + otherFiltersMapEntry.getKey(), otherFiltersMapEntry.getValue());
				}
			}
			
			model.setViewName("itemsview");
			return model;
		}
	}
	
	protected Object getSessionNvl(String itm, Object nullvalue) {
		if(session.getAttribute(itm)==null) {
			logger.debug("[getSessionNvl] I have not found "+itm+" in session, returning nullvalue");
			return nullvalue;
		}
		logger.debug("[getSessionNvl] I have found "+itm+" in session, returning "+session.getAttribute(itm).toString());
		return session.getAttribute(itm);
	}
	
	protected Map<String, List<BdlfeSearchFilter>> getOtherFiltersMap(List<BdlfeSearchFilter> otherFilters) {
		
		List<BdlfeSearchFilter> filtersOnAuthor 		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnKind 			 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnDate 			 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnLang 			 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnSubject 		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnCollection 	 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnInstitute 		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnTecnique 		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnSupport 		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnGraphicMaterial = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnPublisher		 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnTitle			 = new ArrayList<BdlfeSearchFilter>();
		List<BdlfeSearchFilter> filtersOnType			 = new ArrayList<BdlfeSearchFilter>();
		
		for(BdlfeSearchFilter filter : otherFilters) {
			if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_AUTHOR)) {
				filtersOnAuthor.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_KIND)) {
				filtersOnKind.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_DATE)) {
				filtersOnDate.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_LANG)) {
				filtersOnLang.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_SUBJECT)) {
				filtersOnSubject.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_COLLECTION)) {
				filtersOnCollection.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_INSTITUTE)) {
				filtersOnInstitute.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_TECNIQUE)) {
				filtersOnTecnique.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_SUPPORT)) {
				filtersOnSupport.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_GRAPHICMATERIAL)) {
				filtersOnGraphicMaterial.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_PUBLISHER)) {
				filtersOnPublisher.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_TITLE)) {
				filtersOnTitle.add(filter);
			} else if(filter.getFilterType().equals(BdlfeConstants.JSON_FILTERTYPE_TYPE)) {
				filtersOnType.add(filter);
			}
		}
		
		Map<String, List<BdlfeSearchFilter>> filtersMap = new HashMap<String, List<BdlfeSearchFilter>>();
		
		filtersMap.put("FiltersOnAuthor", filtersOnAuthor);
		filtersMap.put("FiltersOnKind", filtersOnKind);
		filtersMap.put("FiltersOnDate", filtersOnDate);
		filtersMap.put("FiltersOnLang", filtersOnLang);
		filtersMap.put("FiltersOnSubject", filtersOnSubject);
		filtersMap.put("FiltersOnCollection", filtersOnCollection);
		filtersMap.put("FiltersOnInstitute", filtersOnInstitute);
		filtersMap.put("FiltersOnTecnique", filtersOnTecnique);
		filtersMap.put("FiltersOnSupport", filtersOnSupport);
		filtersMap.put("FiltersOnGraphicMaterial", filtersOnGraphicMaterial);
		filtersMap.put("FiltersOnPublisher", filtersOnPublisher);
		filtersMap.put("FiltersOnTitle", filtersOnTitle);
		filtersMap.put("FiltersOnType", filtersOnType);
		
		return filtersMap;
	}
}
