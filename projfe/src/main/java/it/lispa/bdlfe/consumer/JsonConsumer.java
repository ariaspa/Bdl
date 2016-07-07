package it.lispa.bdlfe.consumer;

import it.lispa.bdlfe.dto.BdlfeAudioplayerFile;
import it.lispa.bdlfe.dto.BdlfeBookreaderPage;
import it.lispa.bdlfe.dto.BdlfeBookreaderTOC;
import it.lispa.bdlfe.dto.BdlfeCollection;
import it.lispa.bdlfe.dto.BdlfeCollectionLight;
import it.lispa.bdlfe.dto.BdlfeInstitute;
import it.lispa.bdlfe.dto.BdlfeItem;
import it.lispa.bdlfe.dto.BdlfeItemLight;
import it.lispa.bdlfe.dto.BdlfeMapreader;
import it.lispa.bdlfe.dto.BdlfeRef;
import it.lispa.bdlfe.dto.BdlfeSearch;
import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.dto.BdlfeSearchPaginated;
import it.lispa.bdlfe.dto.BdlfeSemanticSearch;
import it.lispa.bdlfe.exceptions.JsonConnectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class JsonConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonConsumer.class);
	
	private static <T> List<T> buildList(T... a){
		List<T> toRet = new ArrayList<T>();
		if(a!=null){
			toRet = Arrays.asList(a);
		}
		return toRet;		
	}
	  
	public static List<BdlfeBookreaderTOC> getBookreaderToc(String serverAddr,Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeBookreaderTOC[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/bookreader/toc", BdlfeBookreaderTOC[].class, params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}

	public static List<BdlfeBookreaderPage> getBookreaderPages(String serverAddr,Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeBookreaderPage[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/bookreader/pages", BdlfeBookreaderPage[].class, params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}
	
	public static BdlfeItem getItem(String serverAddr, Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeItem item = null;
		try{
			item = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/", BdlfeItem.class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return item;
	}

	public static List<BdlfeRef> getAreas(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/areas/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}

	public static List<BdlfeCollection> getArea(String serverAddr,Integer cdCollezione){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdCollezione", cdCollezione);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeCollection[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/area/{cdCollezione}", BdlfeCollection[].class,params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getTypes(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/types/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getLanguages(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/languages/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getKinds(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/kinds/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getSubjects(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/subjects/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getGraphicMaterials(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/graphic-materials/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getSupports(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/supports/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}
	
	public static List<BdlfeRef> getTecniques(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeRef[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/tecniques/", BdlfeRef[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}

	public static List<BdlfeCollection> getCollections(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeCollection[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/collections/", BdlfeCollection[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}

	public static List<BdlfeCollectionLight> getCollectionsLight(String serverAddr){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeCollectionLight[] items = null;
		try{
			items = restTemplate.getForObject(serverAddr+"/json/collections-light/", BdlfeCollectionLight[].class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(items);
	}

	public static BdlfeCollection getCollection(String serverAddr,Integer cdCollezione){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdCollezione", cdCollezione);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeCollection item = null;
		try{
			item = restTemplate.getForObject(serverAddr+"/json/collection/{cdCollezione}", BdlfeCollection.class, params);
		} catch(RestClientException e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return item;
	}
	
	public static List<BdlfeItemLight> getFeaturedItems(String serverAddr, Integer num){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("num", num);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeItemLight[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/featured/{num}", BdlfeItemLight[].class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}
	
	public static List<BdlfeItem> getItemsInCollection(String serverAddr,Integer cdCollection){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdCollection", cdCollection);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeItem[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/items/collection/{cdCollection}", BdlfeItem[].class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}
	
	public static BdlfeMapreader getMapreader(String serverAddr,Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeMapreader item = null;
		try{
			item = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/mapreader", BdlfeMapreader.class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return item;
	}

	public static List<BdlfeAudioplayerFile> getAudioplayerFiles(String serverAddr,Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeAudioplayerFile[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/audioplayer", BdlfeAudioplayerFile[].class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}

	public static List<BdlfeBookreaderPage> searchBookreaderOcr(String serverAddr, Integer cdItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdItem", cdItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeBookreaderPage[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/item/{cdItem}/bookreader/ocr", BdlfeBookreaderPage[].class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}

	public static BdlfeSemanticSearch searchSemantic(String serverAddr, String term, String type){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeSemanticSearch itm = null;
		try{
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("textToSearch", term);
			map.add("typeToSearch", type);
			
			itm = restTemplate.postForObject(serverAddr+"/json/semantic-search", map, BdlfeSemanticSearch.class);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return itm;
	}
	public static BdlfeSearch searchAdvanced(String serverAddr,List<BdlfeSearchFilter> filters){
		RestTemplate restTemplate = new RestTemplate();
		BdlfeSearch itm = null;
		try{
			itm = restTemplate.postForObject(serverAddr+"/json/search-advanced", filters, BdlfeSearch.class);
			if(itm.getResults()==null){
				itm.setResults(new ArrayList<BdlfeItemLight>());
			}
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		return itm;
	}
	
	public static BdlfeSearchPaginated searchAdvancedPaginated(String serverAddr, List<BdlfeSearchFilter> filters, Integer startItem, Integer endItem){
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("startItem", startItem);
        params.put("endItem", endItem);
		RestTemplate restTemplate = new RestTemplate();
		BdlfeSearchPaginated itm = null;
		try{
			itm = restTemplate.postForObject(serverAddr+"/json/search-advanced-paginated/{startItem}/{endItem}", filters, BdlfeSearchPaginated.class, params);
			if(itm.getResults()==null){
				itm.setResults(new ArrayList<BdlfeItemLight>());
			}
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		return itm;
	}
	
	public static BdlfeInstitute getInstitute(String serverAddr, Integer cdEnte) {
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdEnte", cdEnte);
        
		RestTemplate restTemplate = new RestTemplate();
		BdlfeInstitute item = null;
		try{
			item = restTemplate.getForObject(serverAddr+"/json/institute/{cdEnte}", BdlfeInstitute.class, params);
		} catch(RestClientException e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return item;
	}
	
	public static List<BdlfeItem> getItemsInInstitute(String serverAddr, Integer cdIstituto) {
		Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("cdIstituto", cdIstituto);
        
		RestTemplate restTemplate = new RestTemplate();
		BdlfeItem[] itms = null;
		try{
			itms = restTemplate.getForObject(serverAddr+"/json/items/institute/{cdIstituto}", BdlfeItem[].class , params);
		} catch (Exception e){
			logger.error(e.getMessage());
			throw new JsonConnectionException();
		}
		
		return buildList(itms);
	}
}
