package it.lispa.bdlfe.utils;

import it.lispa.bdlfe.consumer.JsonConsumer;
import it.lispa.bdlfe.dto.BdlfeCollectionLight;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSearch;
import it.lispa.bdlfe.dto.BdlfeSearchComparator;
import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.dto.BdlfeSearchPaginated;
import it.lispa.bdlfe.form.SearchForm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchUtils {

	protected static final String ps = "_______________";	
	private static final Logger logger = LoggerFactory.getLogger(SearchUtils.class);
	
	private static  List<BdlfeSearchFilter> populateBdlfeSearchFilter(SearchForm sf, List<BdlfeRef> languages, List<BdlfeRef> types, 
			List<BdlfeRef> kinds, List<BdlfeRef> subjects, List<BdlfeRef> graphicMaterials, 
			List<BdlfeRef> supports, List<BdlfeRef> tecniques, List<BdlfeCollectionLight> collections) {
		List<BdlfeSearchFilter> filters = new ArrayList<BdlfeSearchFilter>();
		Integer order = 0;
		
		List<BdlfeSearchFilter> compositionFilters = sf.getCompositionFilters();
		if(compositionFilters!=null){
			for(BdlfeSearchFilter cmpFilter : compositionFilters){
				if(cmpFilter!=null){
					// devo capire se l'utente ha inserito qualcosa nel comparisonValues[0].comparisonValue
					if(cmpFilter.getComparisonValues()!=null && cmpFilter.getComparisonValues().get(0).getComparisonValue()!=""){
						String logicalOperator = cmpFilter.getLogicalOperator();
						if(logicalOperator==null || logicalOperator.equals("")){
							logicalOperator = BdlfeConstants.JSON_LOGICALOP_AND;
						}
						String filterType = cmpFilter.getFilterType();
						if(logicalOperator==null || logicalOperator.equals("")){
							logicalOperator = BdlfeConstants.JSON_FILTERTYPE_TITLE;
						}
						String comparisonOperator = cmpFilter.getComparisonOperator();
						if(comparisonOperator==null || comparisonOperator.equals("")){
							comparisonOperator = BdlfeConstants.JSON_COMPARISONOP_LIKE;
						}
						
						String cmpLabel = cmpFilter.getComparisonValues().get(0).getComparisonLabel();
						if(cmpLabel == null){
							cmpLabel = cmpFilter.getComparisonValues().get(0).getComparisonValue();
						}
						
						List<BdlfeSearchComparator> comparisonValues = new ArrayList<BdlfeSearchComparator>();
						BdlfeSearchComparator cmpVal = new BdlfeSearchComparator(cmpFilter.getComparisonValues().get(0).getComparisonValue(),cmpLabel);
						comparisonValues.add(cmpVal);
						BdlfeSearchFilter filterToAdd = new BdlfeSearchFilter(
								++order, logicalOperator,
								filterType, comparisonOperator,
								comparisonValues
								);
						filters.add(filterToAdd);
					}
				}
			}
		}
		
		/**************** Mappatura comparisonValues:
		 *  [2] dataFilterType = RANGE/SECOLO/DATACERTA/CIRCA
		 *  [0] inputDataFrom
		 *  [1] inputDataTo
		 */
		BdlfeSearchFilter dateFilter = sf.getDateFilter();
		if(dateFilter!=null && dateFilter.getComparisonValues()!=null){
			// devo capire prima di tutto se l'utente ha selezionato 
			// qualcosa nel comparisonValues[2].comparisonValue
			
			String dataFilterType = dateFilter.getComparisonValues().get(2).getComparisonValue();
			if(dataFilterType!=""){
				String inputDataFrom = dateFilter.getComparisonValues().get(0).getComparisonValue();
				String inputDataTo = dateFilter.getComparisonValues().get(1).getComparisonValue();
				
				List<BdlfeSearchComparator> dtCmpVals = new ArrayList<BdlfeSearchComparator>();
				if(dataFilterType.equals(BdlfeSearchDateFilterOptions.RANGE.name())){
					if(inputDataFrom!="" && inputDataTo!="") {
						String cmpVal = inputDataFrom+"-"+inputDataTo;
						BdlfeSearchComparator val0 = new BdlfeSearchComparator(cmpVal,cmpVal);
						dtCmpVals.add(val0);
						BdlfeSearchComparator val1 = new BdlfeSearchComparator(BdlfeSearchDateFilterOptions.RANGE.name(), BdlfeSearchDateFilterOptions.RANGE.getLabel());
						dtCmpVals.add(val1);
					}
				}else{
					if(inputDataFrom!="") {
						String cmpVal = inputDataFrom;
						BdlfeSearchComparator val0 = new BdlfeSearchComparator(cmpVal,cmpVal);
						dtCmpVals.add(val0);
						BdlfeSearchComparator val1 = new BdlfeSearchComparator(BdlfeSearchDateFilterOptions.valueOf(dataFilterType).name(), BdlfeSearchDateFilterOptions.valueOf(dataFilterType).getLabel());
						dtCmpVals.add(val1);
					}
				}
				
				if(!dtCmpVals.isEmpty()) {
					BdlfeSearchFilter dateFilterToAdd = new BdlfeSearchFilter(++order,BdlfeConstants.JSON_LOGICALOP_AND, BdlfeConstants.JSON_FILTERTYPE_DATE, BdlfeConstants.JSON_COMPARISONOP_EQUALID, dtCmpVals);
					filters.add(dateFilterToAdd);
				}
			}
		}
		
		BigDecimal[] langFilters = sf.getLangFilters();		
		BdlfeSearchFilter langToAdd = buildSingleFilter(langFilters, languages, BdlfeConstants.JSON_FILTERTYPE_LANG, order++);
		if(langToAdd!=null){
			filters.add(langToAdd);
		}
		BigDecimal[] typeFilters = sf.getTypeFilters();		
		BdlfeSearchFilter typeToAdd = buildSingleFilter(typeFilters, types, BdlfeConstants.JSON_FILTERTYPE_TYPE, order++);
		if(typeToAdd!=null){
			filters.add(typeToAdd);
		}
		BigDecimal[] kindFilters = sf.getKindFilters();		
		BdlfeSearchFilter kindToAdd = buildSingleFilter(kindFilters, kinds, BdlfeConstants.JSON_FILTERTYPE_KIND, order++);
		if(kindToAdd!=null){
			filters.add(kindToAdd);
		}		
		BigDecimal[] subjectFilters = sf.getSubjectFilters();
		BdlfeSearchFilter subjectToAdd = buildSingleFilter(subjectFilters, subjects, BdlfeConstants.JSON_FILTERTYPE_SUBJECT, order++);
		if(subjectToAdd!=null){
			filters.add(subjectToAdd);
		}
		
		BigDecimal[] collectionFilters = sf.getCollectionFilters();	
		logger.debug(ps+"[populateBdlfeSearchFilter] Req castToRef()");
		List<BdlfeRef> refCollections = castToRef(collections);
		logger.debug(ps+"[populateBdlfeSearchFilter] Res castToRef()");
		
		logger.debug(ps+"[populateBdlfeSearchFilter] Req buildSingleFilter()");
		BdlfeSearchFilter collectionToAdd = buildSingleFilter(collectionFilters, refCollections, BdlfeConstants.JSON_FILTERTYPE_COLLECTION, order++);
		logger.debug(ps+"[populateBdlfeSearchFilter] Res buildSingleFilter()");
		if(collectionToAdd!=null){
			filters.add(collectionToAdd);
		}
		
		BigDecimal graphicmaterialFilter = sf.getGraphicmaterialFilter();		
		BdlfeSearchFilter graphicmaterialToAdd = buildSingleFilter(graphicmaterialFilter, graphicMaterials, BdlfeConstants.JSON_FILTERTYPE_GRAPHICMATERIAL, order++);
		if(graphicmaterialToAdd!=null){
			filters.add(graphicmaterialToAdd);
		}
		BigDecimal supportFilter = sf.getSupportFilter();		
		BdlfeSearchFilter supportToAdd = buildSingleFilter(supportFilter, supports, BdlfeConstants.JSON_FILTERTYPE_SUPPORT, order++);
		if(supportToAdd!=null){
			filters.add(supportToAdd);
		}
		BigDecimal tecniqueFilter = sf.getTecniqueFilter();		
		BdlfeSearchFilter tecniqueToAdd = buildSingleFilter(tecniqueFilter, tecniques, BdlfeConstants.JSON_FILTERTYPE_TECNIQUE, order++);
		if(tecniqueToAdd!=null){
			filters.add(tecniqueToAdd);
		}
		
		return filters;
	}
	
	public static BdlfeSearch doSearchAdvanced(String serviceUrl, SearchForm sf, List<BdlfeRef> languages, List<BdlfeRef> types, 
			List<BdlfeRef> kinds, List<BdlfeRef> subjects, List<BdlfeRef> graphicMaterials, 
			List<BdlfeRef> supports, List<BdlfeRef> tecniques, List<BdlfeCollectionLight> collections) {
		logger.debug(ps+"[doSearchAdvanced] Req searchAdvanced()");
		BdlfeSearch ret = JsonConsumer.searchAdvanced(serviceUrl, 
			populateBdlfeSearchFilter(sf, languages, types, kinds, subjects, graphicMaterials, supports, tecniques, collections));
		logger.debug(ps+"[doSearchAdvanced] Res searchAdvanced()");
		return ret;
	}
	
	public static BdlfeSearchPaginated doSearchAdvanced(String serviceUrl, SearchForm sf, List<BdlfeRef> languages, List<BdlfeRef> types, 
			List<BdlfeRef> kinds, List<BdlfeRef> subjects, List<BdlfeRef> graphicMaterials, List<BdlfeRef> supports, 
			List<BdlfeRef> tecniques, List<BdlfeCollectionLight> collections, Integer startItem, Integer endItem) {
		logger.debug(ps+"[doSearchAdvanced] Req searchAdvancedPaginated()");
		BdlfeSearchPaginated ret = JsonConsumer.searchAdvancedPaginated(serviceUrl, 
			populateBdlfeSearchFilter(sf, languages, types, kinds, subjects, graphicMaterials, supports, tecniques, collections),
			startItem, endItem);
		logger.debug(ps+"[doSearchAdvanced] Res searchAdvancedPaginated()");
		return ret;
	}

	public static List<BdlfeRef> castToRef(List<BdlfeCollectionLight> toBeCasted) {
		if(toBeCasted==null) {
			return null;
		}
		List<BdlfeRef> toRet = new ArrayList<BdlfeRef>();
		for(BdlfeCollectionLight coll: toBeCasted){
			BdlfeRef toAdd = new BdlfeRef(coll.getId(),coll.getTitle());
			toRet.add(toAdd);
		}		
		return toRet;
	}
	
	public static SearchForm initSearchForm() {
		
		BdlfeSearchOrderOptions orderOption = BdlfeSearchOrderOptions.RELEVANCE;
		
		List<BdlfeSearchFilter> compositionFilters = new ArrayList<BdlfeSearchFilter>();

		BdlfeSearchFilter filter0 = new BdlfeSearchFilter(new Integer(1), BdlfeConstants.JSON_LOGICALOP_AND, BdlfeConstants.JSON_FILTERTYPE_TITLE, null, null);
		BdlfeSearchFilter filter1 = new BdlfeSearchFilter(new Integer(2), BdlfeConstants.JSON_LOGICALOP_AND, BdlfeConstants.JSON_FILTERTYPE_AUTHOR, null, null);
		BdlfeSearchFilter filter2 = new BdlfeSearchFilter(new Integer(3), BdlfeConstants.JSON_LOGICALOP_AND, BdlfeConstants.JSON_FILTERTYPE_SUBJECT, null, null);

		compositionFilters.add(filter0);
		compositionFilters.add(filter1);
		compositionFilters.add(filter2);
		
		BdlfeSearchFilter fakeFilter = new BdlfeSearchFilter(
				new Integer(99), BdlfeConstants.JSON_LOGICALOP_AND, 
				BdlfeConstants.JSON_FILTERTYPE_TITLE, 
				null, 
				null
		);

		BdlfeSearchFilter dateFilter = new BdlfeSearchFilter();
		
		BigDecimal[] langFilters = new BigDecimal[] {};
		BigDecimal[] typeFilters = new BigDecimal[] {};
		BigDecimal[] kindFilters = new BigDecimal[] {};
		BigDecimal[] subjectFilters = new BigDecimal[] {null,null,null,null,null,null,null,null,null};
		BigDecimal graphicmaterialFilter = null;
		BigDecimal supportFilter = null;
		BigDecimal tecniqueFilter = null;
		BigDecimal[] collectionFilters = new BigDecimal[] {null,null,null};

		return new SearchForm(
				orderOption,
				compositionFilters,
				fakeFilter,
				dateFilter,
				langFilters,
				typeFilters,
				kindFilters,
				subjectFilters,
				graphicmaterialFilter,
				supportFilter,
				tecniqueFilter,
				collectionFilters
		);	
	}

	private static BdlfeSearchFilter buildSingleFilter(BigDecimal[] filters, List<BdlfeRef> items, String filterType, Integer order){
		BdlfeSearchFilter filterToAdd = null;
		if(filters!=null){
			if(filters.length>0){
				String logicalOperator = BdlfeConstants.JSON_LOGICALOP_AND;
				String comparisonOperator = BdlfeConstants.JSON_COMPARISONOP_INID;
				String comparisonOperatorSingle = BdlfeConstants.JSON_COMPARISONOP_EQUALID;
				
				List<BdlfeSearchComparator> comparisonValues = new ArrayList<BdlfeSearchComparator>();
				
				for(BigDecimal filter : filters){
					if(filter!=null){
						BdlfeSearchComparator cmpVal = new BdlfeSearchComparator(filter.toString(),getBdlRefItemName(items, filter));
						comparisonValues.add(cmpVal);
					}
				}
				if(comparisonValues.size()>0){
					if(comparisonValues.size()==1){
						filterToAdd = new BdlfeSearchFilter(
								++order, logicalOperator,
								filterType, comparisonOperatorSingle,
								comparisonValues
								);
					}else{	
						filterToAdd = new BdlfeSearchFilter(
								++order, logicalOperator,
								filterType, comparisonOperator,
								comparisonValues
								);
					}
				}
			}
		}
		return filterToAdd;
	}
	private static BdlfeSearchFilter buildSingleFilter(BigDecimal filter, List<BdlfeRef> items, String filterType, Integer order){
		BdlfeSearchFilter filterToAdd = null;
		if(filter!=null){
			String logicalOperator = BdlfeConstants.JSON_LOGICALOP_AND;
			String comparisonOperator = BdlfeConstants.JSON_COMPARISONOP_EQUALID;
			
			List<BdlfeSearchComparator> comparisonValues = new ArrayList<BdlfeSearchComparator>();
						
			BdlfeSearchComparator cmpVal = new BdlfeSearchComparator(filter.toString(),getBdlRefItemName(items, filter));
			comparisonValues.add(cmpVal);
			
			filterToAdd = new BdlfeSearchFilter(
					++order, logicalOperator,
					filterType, comparisonOperator,
					comparisonValues
					);
		}
		return filterToAdd;
	}

	private static String getBdlRefItemName(List<BdlfeRef> items, BigDecimal id){
		String name="";
		for(BdlfeRef item : items){
			if(item.getId().equals(id)){
				name=item.getName();
			}
		}
		return name;
	}
}
