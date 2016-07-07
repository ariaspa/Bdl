package it.lispa.bdlfe.controller;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeBreadcrumb;
import it.lispa.bdlfe.dto.BdlfeItem;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.exceptions.ResourceNotFoundException;
import it.lispa.bdlfe.utils.management.ManagementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@RequestMapping(value = "/item/{cdItem}", method = RequestMethod.GET)
	public String getItem(@PathVariable("cdItem") Integer cdItem, Locale locale, Model model) {
		logger.debug("[getItem] Entering with cdItem="+cdItem.toString());
		
		logger.debug(ps+"[getItem] Req: getItem() - Time: "+ManagementUtils.getCpuTime());
		BdlfeItem item = JsonConsumer.getItem(serverProps.getBackendUrl(),cdItem);
		logger.debug(ps+"[getItem] Req: getItem() - Time: "+ManagementUtils.getCpuTime());
		if(item==null)
			throw new ResourceNotFoundException(); 
		model.addAttribute("item", item);
		
		logger.debug(ps+"[getItem] Req: getChildren() - Time: "+ManagementUtils.getCpuTime());
		if(item.getChildren()!=null) {
			model.addAttribute("children", getChildren(item.getChildren()));
		}
		logger.debug(ps+"[getItem] Res: getChildren() - Time: "+ManagementUtils.getCpuTime());
		
		/* Nel caso sono una Monografia Superiore... */
		if(item.getIsChildOf()!=null && item.getKind()!=null && item.getKind().equalsIgnoreCase("Monografia")) {
			logger.debug(ps+"[getItem] Req: getFather() - Time: "+ManagementUtils.getCpuTime());
			model.addAttribute("father", getFather(item.getIsChildOf()));
			logger.debug(ps+"[getItem] Res: getFather() - Time: "+ManagementUtils.getCpuTime());
		}
		
		model.addAttribute("pageTitle", ""+item.getTitle()+"");
		List<BdlfeBreadcrumb> breadcrumbs = new ArrayList<BdlfeBreadcrumb>();
		breadcrumbs.add(new BdlfeBreadcrumb("/","Homepage"));
		breadcrumbs.add(new BdlfeBreadcrumb("/item/"+cdItem, item.getTitle()));
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		logger.debug(ps+"[getHome] ...Exit");
		return "item";
	}
	private List<BdlfeItem> getChildren(List<BdlfeRef> children) {
		List<BdlfeItem> objToRet = new ArrayList<BdlfeItem>();
		for(BdlfeRef child : children) {
			objToRet.add(JsonConsumer.getItem(serverProps.getBackendUrl(), child.getId().intValueExact()));
		}
		return objToRet;
	}
	private BdlfeItem getFather(BdlfeRef isChildOf) {
		BdlfeItem objToRet = null;
		objToRet = JsonConsumer.getItem(serverProps.getBackendUrl(), isChildOf.getId().intValueExact());
		return objToRet;
	}
}
