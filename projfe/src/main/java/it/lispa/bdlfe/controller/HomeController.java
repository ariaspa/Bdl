package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeCollectionLight;
import it.lispa.bdlfe.dto.BdlfeItemLight;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.form.SearchForm;
import it.lispa.bdlfe.utils.BdlfeConstants;
import it.lispa.bdlfe.utils.management.ManagementUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("searchFormObj")
public class HomeController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	

	@ModelAttribute("vTList")
	public String getViewTypeList() {
		return BdlfeConstants.BDLFE_ITEMS_VIEW_TYPE_LIST;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getHome(HttpSession session, Locale locale, ModelAndView model) {
		logger.debug(ps+"[getHome] Entering...");
		
		model.addObject("pageTitle", "Homepage");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		model.addObject("breadcrumbs", breadcrumbs);
		
		/* Vetrina */
		logger.debug(ps+"[getHome] Req: getFeaturedItems() - Time: "+ManagementUtils.getCpuTime());
		List<BdlfeItemLight> oggettiInVetrina = JsonConsumer.getFeaturedItems(serverProps.getBackendUrl(), serverProps.getNewItemsNum());
		logger.debug(ps+"[getHome] Res: getFeaturedItems() - Time: "+ManagementUtils.getCpuTime());
		model.addObject("featuredItems", oggettiInVetrina);
		
		/* Aree e Collezioni */
		Map<List<BdlfeRef>, List<BdlfeCollectionLight>> objRet = modifyCollectionsAndAreasForHome();
		
		/* Istituti e Collezioni */
		logger.debug(ps+"[getHome] Inizio: Compongo la mappa Istituti-Collezioni... - Time: "+ManagementUtils.getCpuTime());
		Iterator<Entry<List<BdlfeRef>, List<BdlfeCollectionLight>>> objRetItr = objRet.entrySet().iterator();
		while (objRetItr.hasNext()) {
			Map.Entry objRetEntry = (Map.Entry) objRetItr.next();
			
			List<BdlfeRef> areas = (List<BdlfeRef>) objRetEntry.getKey();
			List<BdlfeCollectionLight> collections = (List<BdlfeCollectionLight>) objRetEntry.getValue();
			
			Collections.sort(collections);
			
			String currInstitute = "";
			List<BdlfeCollectionLight> currListOfCollections = null;
			
			Map<String, List<BdlfeCollectionLight>> collectionsMap = new HashMap<String, List<BdlfeCollectionLight>>();
			
//			Integer numItems = Integer.valueOf(0);
			BdlfeCollectionLight c = null;
			for(int i=0; i<collections.size(); i++) {
				c = collections.get(i);
				if("".equals(currInstitute)) {
					currInstitute = c.getInstitute().getName();
					currListOfCollections = new ArrayList<BdlfeCollectionLight>();
					
				} else if(!currInstitute.equals(c.getInstitute().getName())) {
//					currInstitute += " ("+numItems+") ";
					collectionsMap.put(currInstitute, currListOfCollections);
					
					currInstitute = c.getInstitute().getName();
					currListOfCollections = new ArrayList<BdlfeCollectionLight>();
//					numItems = 0;
				}
				currListOfCollections.add(c);
//				numItems += c.getNumItems();
			}
//			currInstitute += " ("+numItems+") ";
			collectionsMap.put(currInstitute, currListOfCollections);
			model.addObject("collectionsMap", collectionsMap);
			model.addObject("areas", areas);
			model.addObject("collections", collections);
		}
		logger.debug(ps+"[getHome] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		
		@SuppressWarnings("unused")
		SearchForm sessVal = (SearchForm) session.getAttribute("searchFormObj");
		model.setViewName("home");
		
		logger.debug(ps+"[getHome] ...Exit");
		return model;
	}
	
	private Map<List<BdlfeRef>, List<BdlfeCollectionLight>> modifyCollectionsAndAreasForHome() {
		Map<List<BdlfeRef>, List<BdlfeCollectionLight>> objToRet = new HashMap<List<BdlfeRef>, List<BdlfeCollectionLight>>();
		
		/* Collezioni */
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] Req: getCollections() - Time: "+ManagementUtils.getCpuTime());
		List<BdlfeCollectionLight> collections = JsonConsumer.getCollectionsLight(serverProps.getBackendUrl());
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] Res: getCollections() - Time: "+ManagementUtils.getCpuTime());
		
		/* Aree */
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] Req: getAreas() - Time: "+ManagementUtils.getCpuTime());
		List<BdlfeRef> areas = JsonConsumer.getAreas(serverProps.getBackendUrl());
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] Res: getAreas() - Time: "+ManagementUtils.getCpuTime());
		
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] Inizio: Popolo le Aree e le Collezioni da mostrare in pagina... - Time: "+ManagementUtils.getCpuTime());
		BdlfeCollectionLight collection = null;
		for (int i = 0; i < areas.size(); i++) {
			for (int j = 0; j < collections.size(); j++) {
				collection = collections.get(j);
				if (collection.getArea().getName() != null) {
					if (collection.getArea().getName().equals(areas.get(i).getName())) {
						collection.setArea(new BdlfeRef(new BigDecimal(i+1), areas.get(i).getName()));
					}
				} else {
					collection.setArea(new BdlfeRef(new BigDecimal(0), null));
				}
			}
			areas.get(i).setId(new BigDecimal(i+1));
		}
		objToRet.put(areas, collections);
		logger.debug(ps+"[getHome][modifyCollectionsAndAreasForHome] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		
		return objToRet;
	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String getHelp(Locale locale, Model model) {
		logger.debug("[getHelp] Entering...");
		
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/help","Help"));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		model.addAttribute("pageTitle", "Help");
		logger.debug("[getHelp] ...Exit");
		return "help";
	}
}
