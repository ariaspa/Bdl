package it.lispa.bdl.server.rest;

import it.lispa.bdl.commons.domain.BdlCollezione;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlLivelloBiblio;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlPdfPagina;
import it.lispa.bdl.commons.domain.BdlSoggetto;
import it.lispa.bdl.commons.domain.BdlSupporto;
import it.lispa.bdl.commons.domain.BdlTecnicaGrafica;
import it.lispa.bdl.commons.domain.BdlTipoGrafica;
import it.lispa.bdl.commons.domain.BdlToc;
import it.lispa.bdl.server.rest.dto.JSONBdlAudioplayerFile;
import it.lispa.bdl.server.rest.dto.JSONBdlBookreaderPage;
import it.lispa.bdl.server.rest.dto.JSONBdlBookreaderTOC;
import it.lispa.bdl.server.rest.dto.JSONBdlCollection;
import it.lispa.bdl.server.rest.dto.JSONBdlCollectionLight;
import it.lispa.bdl.server.rest.dto.JSONBdlCover;
import it.lispa.bdl.server.rest.dto.JSONBdlInstitute;
import it.lispa.bdl.server.rest.dto.JSONBdlItem;
import it.lispa.bdl.server.rest.dto.JSONBdlItemLight;
import it.lispa.bdl.server.rest.dto.JSONBdlMapreader;
import it.lispa.bdl.server.rest.dto.JSONBdlRef;
import it.lispa.bdl.server.rest.dto.JSONBdlRefCount;
import it.lispa.bdl.server.rest.dto.JSONBdlSearch;
import it.lispa.bdl.server.rest.dto.JSONBdlSearchFilter;
import it.lispa.bdl.server.rest.dto.JSONBdlSearchPaginated;
import it.lispa.bdl.server.rest.dto.JSONBdlSemanticSearch;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.management.ManagementUtils;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Class Json.
 */
@Component
@Path("/")
public class Json extends Srv {

	/** log. */
	private static Logger log = Logger.getLogger(Json.class);
		
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return toc sommario oggetto
	 */
	private List<JSONBdlBookreaderTOC> getTocSommarioOggetto(BigDecimal cdOggettoOriginale) {		
		return getTocSommarioOggettoRecursive(cdOggettoOriginale, null);
	}
	
	/**
	 * Legge toc sommario oggetto recursive.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTocPadre  codice toc padre
	 * @return toc sommario oggetto recursive
	 */
	private List<JSONBdlBookreaderTOC> getTocSommarioOggettoRecursive(BigDecimal cdOggettoOriginale, BigDecimal cdTocPadre) {
		
		List<JSONBdlBookreaderTOC> toReturn = new ArrayList<JSONBdlBookreaderTOC>();
		
		List<BdlToc> tocs = null;
		if(cdTocPadre==null) {
			tocs = bdlTocRepository.findByCdOggettoOriginaleAndCdTocPadreIsNullOrderByCdTocAsc(cdOggettoOriginale);
		} else {
			tocs = bdlTocRepository.findByCdOggettoOriginaleAndCdTocPadreOrderByCdTocAsc(cdOggettoOriginale, cdTocPadre);
		}
		
		for(BdlToc toc : tocs) {
			Integer pageNumber = null;
			String idImg = null;
			
			if(toc.getCdImmagine()!=null) {
				BdlImmagine img = bdlImmagineRepository.findByCdImmagine(toc.getCdImmagine());
				
				if(img!=null) {
					idImg = img.getDnNomeImmagine().replaceFirst("[0]{0,3}", "");
					pageNumber = Integer.valueOf(idImg);
				}
			}
			List<JSONBdlBookreaderTOC> children = this.getTocSommarioOggettoRecursive(cdOggettoOriginale, toc.getCdToc());
			if(children.isEmpty()){
				children = null;
			}
			JSONBdlBookreaderTOC tocObj = new JSONBdlBookreaderTOC(toc.getCdToc().intValueExact(), toc.getDnNome(), pageNumber, children);
			toReturn.add(tocObj);
		}
		
		return toReturn;	
	}

    /**
     * Legge bookreader toc.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param servletConfig  servlet config
     * @return bookreader toc
     */
    @GET
	@Path("json/item/{cdOggetto: "+regExprForID+"}/bookreader/toc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlBookreaderTOC> getBookreaderToc(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {
    	BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		log.debug("[getBookreaderToc] cdOggettoOriginale="+cdOggettoOriginale.toString());
		List<JSONBdlBookreaderTOC> objToRet = null;
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			objToRet = getTocSommarioOggetto(vOggettoOriginale.getO_CdOggettoOriginale());
		}
		return objToRet;
	}
    
	/**
	 * Legge bookreader pages.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return bookreader pages
	 */
	@GET
	@Path("json/item/{cdOggetto: "+regExprForID+"}/bookreader/pages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlBookreaderPage> getBookreaderPages(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);		
		log.debug("[getBookreaderPages] cdOggettoOriginale="+cdOggettoOriginale.toString());
		List<JSONBdlBookreaderPage> objToRet = null;
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			BdlOggettoOriginale oggettoOriginale = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(vOggettoOriginale.getO_CdOggettoOriginale());
			List<ImmagineDTO> immagini = mapper.mapListBdlImmagineToListImmagineDTO(
					bdlImmagineRepository.findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(cdOggettoOriginale),
					servletConfig.getServletContext().getContextPath(),
					restUtils.getServicePath(servletConfig),
					vOggettoOriginale.getT_CdTipoOggetto()
			);

			Integer larg = new Integer(0);
			Integer alt = new Integer(0);
			for(ImmagineDTO img : immagini) {
				larg += img.getNrPxLarghezzaReader();
				alt += img.getNrPxAltezzaReader(); 
			}
			
			Integer numImages = new Integer(immagini.size());

			Float largMean = larg.floatValue()/numImages;
			Float altMean = alt.floatValue()/numImages;
			
			Float ratioMean = largMean / altMean;
			
			objToRet = new ArrayList<JSONBdlBookreaderPage>();
			for(ImmagineDTO img : immagini) {
				Float ratio = img.getNrPxLarghezzaReader().floatValue()/img.getNrPxAltezzaReader().floatValue();
				Float diff = ratio-ratioMean;
				Boolean doublePage = false;
				
				String imageId = new Integer(Integer.parseInt(img.getNomeImmagine())).toString();
				if(Math.abs(diff)>0.3*Math.max(ratio, ratioMean) && oggettoOriginale.getFlDoppiaPagina()){
					doublePage = true;

					Integer lReader = new Integer((int) Math.floor(new Float(img.getNrPxLarghezzaReader()/2)));
					
					objToRet.add(new JSONBdlBookreaderPage(
							imageId+"DLX",
							img.getNrPxLarghezzaThumb(), 
							img.getNrPxAltezzaThumb(), 
							lReader, 
							img.getNrPxAltezzaReader(), 
							img.getNrPxLarghezzaZoom(), 
							img.getNrPxAltezzaZoom(),
							doublePage
					));
					objToRet.add(new JSONBdlBookreaderPage(
							imageId+"DRX",
							img.getNrPxLarghezzaThumb(), 
							img.getNrPxAltezzaThumb(), 
							lReader, 
							img.getNrPxAltezzaReader(), 
							img.getNrPxLarghezzaZoom(), 
							img.getNrPxAltezzaZoom(),
							doublePage
					));
				}else{
					objToRet.add(new JSONBdlBookreaderPage(
							imageId,
							img.getNrPxLarghezzaThumb(), 
							img.getNrPxAltezzaThumb(), 
							img.getNrPxLarghezzaReader(), 
							img.getNrPxAltezzaReader(), 
							img.getNrPxLarghezzaZoom(), 
							img.getNrPxAltezzaZoom(),
							doublePage
					));	
				}
			}
		}
		return objToRet;
	}

	/**
	 * Legge item.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return item
	 */
	@GET
	@Path("json/item/{cdOggetto: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlItem getItem(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {
    	BigDecimal cdOggetto = new BigDecimal(cdOggettoStr);
		log.debug("[getItem] cdOggetto="+cdOggetto.toString());
		
		log.debug(ManagementUtils.ps+"[getItem] Req: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggetto, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		log.debug(ManagementUtils.ps+"[getItem] Res: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getItem] Req: updateNrRilevanza()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
		JSONBdlItem objToRet = null;
		if(vOggettoOriginale!=null) {
			bdlOggettoOriginaleRepository.updateNrRilevanza(vOggettoOriginale.getO_CdOggettoOriginale());
			objToRet = createJSONBdlItemByVOggettoOriginale(vOggettoOriginale, servletConfig);
		}
		log.debug(ManagementUtils.ps+"[getItem] Res: updateNrRilevanza()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
		return objToRet;
	}
	
	/**
	 * Legge areas.
	 *
	 * @param servletConfig  servlet config
	 * @return areas
	 */
	@GET
	@Path("json/areas/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getAreas(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		
		log.debug(ManagementUtils.ps+"[getAreas] Req: findAmbitiDisciplinariForJson() - Time: "+ManagementUtils.getCpuTime());
		List<SubMenuItemDTO> ambitiDisciplinari = vOggetti.findAmbitiDisciplinariForJson(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		log.debug(ManagementUtils.ps+"[getAreas] Res: findAmbitiDisciplinariForJson() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getAreas] Compongo l'objToRet... - Time: "+ManagementUtils.getCpuTime());
		SubMenuItemDTO ambitoDisciplinare = null;
		for(int i=0; i<ambitiDisciplinari.size(); i++) {
			ambitoDisciplinare = ambitiDisciplinari.get(i);
			objToRet.add(new JSONBdlRef(ambitoDisciplinare.getCodice(), ambitoDisciplinare.getLabel()));
		}
		log.debug(ManagementUtils.ps+"[getAreas] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		
		return objToRet;
	}
	
	/**
	 * Legge area.
	 *
	 * @param cdCollezioneStr  codice collezione str
	 * @param servletConfig  servlet config
	 * @return area
	 */
	@GET
	@Path("json/area/{cdCollezione: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlCollection> getArea(@PathParam("cdCollezione") String cdCollezioneStr, @Context ServletConfig servletConfig) {
		List<JSONBdlCollection> objToRet = vOggetti.jsonGetCollezioniForAree(restUtils.getListaFiltriPubblicati(servletConfig));
		JSONBdlCollection jsonCollezione = null;
		for(int index=0; index<objToRet.size(); index++) {
			jsonCollezione = objToRet.get(index);
			log.debug(ManagementUtils.ps+"[getArea] Req: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
			VOggettoDTO mainItemVOggettoDTO = vOggetti.findOggettoByCodice(jsonCollezione.getMainItem().getId());
			log.debug(ManagementUtils.ps+"[getArea] Res/Req: findOggettoByCodice()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			JSONBdlItem mainItemJSONBdlItem = createJSONBdlItemByVOggettoOriginale(mainItemVOggettoDTO);
			log.debug(ManagementUtils.ps+"[getArea] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			jsonCollezione.setMainItem(mainItemJSONBdlItem);
		}
		return objToRet;
	}
	
	/**
	 * Legge types.
	 *
	 * @param servletConfig  servlet config
	 * @return types
	 */
	@GET
	@Path("json/types/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getTypes(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		
		log.debug(ManagementUtils.ps+"[getTypes] Req: findTipiOggettoForJson() - Time: "+ManagementUtils.getCpuTime());
		List<SubMenuItemDTO> tipiOggetto = vOggetti.findTipiOggettoForJson(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		log.debug(ManagementUtils.ps+"[getTypes] Req: findTipiOggettoForJson() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getTypes] Compongo l'objToRet... - Time: "+ManagementUtils.getCpuTime());
		SubMenuItemDTO tipoOggetto = null;
		for(int i=0; i<tipiOggetto.size(); i++) {
			tipoOggetto = tipiOggetto.get(i);
			objToRet.add(new JSONBdlRef(tipoOggetto.getCodice(), tipoOggetto.getLabel()));
		}
		log.debug(ManagementUtils.ps+"[getTypes] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		
		return objToRet;
	}
	
	/**
	 * Legge collections.
	 *
	 * @param servletConfig  servlet config
	 * @return collections
	 */
	@GET
	@Path("json/collections/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlCollection> getCollections(@Context ServletConfig servletConfig) {
		List<JSONBdlCollection> objToRet = vOggetti.jsonGetCollezioni(restUtils.getListaFiltriPubblicati(servletConfig));
		JSONBdlCollection jsonCollezione = null;
		for(int index=0; index<objToRet.size(); index++) {
			jsonCollezione = objToRet.get(index);
			log.debug(ManagementUtils.ps+"[getCollections] Req: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
			VOggettoDTO mainItemVOggettoDTO = vOggetti.findOggettoByCodice(jsonCollezione.getMainItem().getId());
			log.debug(ManagementUtils.ps+"[getCollections] Res/Req: findOggettoByCodice()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			JSONBdlItem mainItemJSONBdlItem = createJSONBdlItemByVOggettoOriginale(mainItemVOggettoDTO);
			log.debug(ManagementUtils.ps+"[getCollections] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			jsonCollezione.setMainItem(mainItemJSONBdlItem);
		}
		return objToRet;
	}
	
	/**
	 * Legge collection.
	 *
	 * @param cdCollezioneStr  codice collezione str
	 * @param servletConfig  servlet config
	 * @return collection
	 */
	@GET
	@Path("json/collection/{cdCollezione: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlCollection getCollection(@PathParam("cdCollezione") String cdCollezioneStr, @Context ServletConfig servletConfig) {
		List<JSONBdlCollection> objToRet = vOggetti.jsonGetCollezione(restUtils.getListaFiltriPubblicati(servletConfig), new BigDecimal(cdCollezioneStr));
		JSONBdlCollection jsonCollezione = null;
		for(int index=0; index<objToRet.size(); index++) {
			jsonCollezione = objToRet.get(index);
			log.debug(ManagementUtils.ps+"[getCollection] Req: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
			VOggettoDTO mainItemVOggettoDTO = vOggetti.findOggettoByCodice(jsonCollezione.getMainItem().getId());
			log.debug(ManagementUtils.ps+"[getCollection] Res/Req: findOggettoByCodice()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			JSONBdlItem mainItemJSONBdlItem = createJSONBdlItemByVOggettoOriginale(mainItemVOggettoDTO);
			log.debug(ManagementUtils.ps+"[getCollection] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			jsonCollezione.setMainItem(mainItemJSONBdlItem);
		}
		return objToRet.get(0);
	}
	
	@GET
	@Path("json/institute/{cdEnte: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlInstitute getInstitute(@PathParam("cdEnte") String cdEnteStr, @Context ServletConfig servletConfig) {
		List<String> filtriOOPubblicati = restUtils.getListaFiltriPubblicati(servletConfig);
		List<JSONBdlInstitute> objToRet = vOggetti.jsonGetIstituto(filtriOOPubblicati, new BigDecimal(cdEnteStr));
		
//		BdlEnte myIstituto = null;
//		if(filtriOOPubblicati!=null)
//			myIstituto = bdlEnteRepository.findIstitutoByCdEnteAndStatoOggetto(cdEnte, filtriOOPubblicati.get(0)); // public
//		else
//			myIstituto = bdlEnteRepository.findByCdEnte(cdEnte); // private
		
		VOggettoDTO mainItemVOggettoDTO = null;
		JSONBdlItem mainItemJSONBdlItem = null;
		JSONBdlInstitute jsonIstituto = null;
		for(int index=0; index<objToRet.size(); index++) {
			jsonIstituto = objToRet.get(index);
			log.debug(ManagementUtils.ps+"[getInstitute] Req: findOggettoByCodice() - Time: "+ManagementUtils.getCpuTime());
			mainItemVOggettoDTO = vOggetti.findOggettoByCodice(jsonIstituto.getOggettoPrincipale().getId());
			log.debug(ManagementUtils.ps+"[getInstitute] Res/Req: findOggettoByCodice()/createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			mainItemJSONBdlItem = createJSONBdlItemByVOggettoOriginale(mainItemVOggettoDTO);
			log.debug(ManagementUtils.ps+"[getInstitute] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			jsonIstituto.setOggettoPrincipale(mainItemJSONBdlItem);
		}
		return objToRet.get(0);
	}
	
	@GET
	@Path("json/items/institute/{cdIstituto: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlItem> getItemsInInstitute(@PathParam("cdIstituto") String cdIstitutoStr, @Context ServletConfig servletConfig) {
		List<JSONBdlItem> objToRet = new ArrayList<JSONBdlItem>();
		BigDecimal cdIstituto = new BigDecimal(cdIstitutoStr);
		
		log.debug(ManagementUtils.ps+"[getItemsInInstitute] Req: jsonGetOggettiInIstituto() - Time: "+ManagementUtils.getCpuTime());
		List<VOggettoDTO> itemsInInstitute = vOggetti.jsonGetOggettiInIstituto(restUtils.getListaFiltriPubblicati(servletConfig), cdIstituto);
		log.debug(ManagementUtils.ps+"[getItemsInInstitute] Res: jsonGetOggettiInIstituto() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getItemsInInstitute] Compongo l'objToRet... - Time: "+ManagementUtils.getCpuTime());
		VOggettoDTO itemInInstitute = null;
		for(int index=0; index<itemsInInstitute.size(); index++) {
			itemInInstitute = itemsInInstitute.get(index);
			log.debug(ManagementUtils.ps+"[getItemsInInstitute] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			objToRet.add(createJSONBdlItemByVOggettoOriginale(itemInInstitute));
			log.debug(ManagementUtils.ps+"[getItemsInInstitute] Res: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
		}
		log.debug(ManagementUtils.ps+"[getItemsInInstitute] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		return objToRet;
	}
	
	@POST
	@Path("json/item")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlRef searchTitle(@FormParam("titleToSearch") String titoloOggetto, @Context ServletConfig servletConfig) {
		log.debug("[searchTitle] titleToSearch="+titoloOggetto);
		/* Non voglio cercare il titolo esatto? */
//		String myText = "%" + titoloOggetto.replaceAll("\\W", "") + "%";
//		log.debug("[searchTitle] textToSearch=\"" + myText+"\"");
		
		JSONBdlRef objToRet = null;
		VOggettoDTO item = vOggetti.findOggettoByTitolo(titoloOggetto, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(item!=null)
			objToRet = new JSONBdlRef(item.getO_CdOggettoOriginale(), item.getO_DnTitolo());
		return objToRet;
	}
	
	/**
	 * Legge items in collection.
	 *
	 * @param cdCollezioneStr  codice collezione str
	 * @param servletConfig  servlet config
	 * @return items in collection
	 */
	@GET
	@Path("json/items/collection/{cdCollezione: "+regExprForID+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlItem> getItemsInCollection(@PathParam("cdCollezione") String cdCollezioneStr, @Context ServletConfig servletConfig) {
		List<JSONBdlItem> objToRet = new ArrayList<JSONBdlItem>();
		BigDecimal cdCollezione = new BigDecimal(cdCollezioneStr);
		
		log.debug(ManagementUtils.ps+"[getItemsInCollection] Req: jsonGetOggettiInCollezione() - Time: "+ManagementUtils.getCpuTime());
		List<VOggettoDTO> itemsInCollection = vOggetti.jsonGetOggettiInCollezione(restUtils.getListaFiltriPubblicati(servletConfig), cdCollezione);
		log.debug(ManagementUtils.ps+"[getItemsInCollection] Res: jsonGetOggettiInCollezione() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getItemsInCollection] Compongo l'objToRet... - Time: "+ManagementUtils.getCpuTime());
		VOggettoDTO itemInCollection = null;
		for(int index=0; index<itemsInCollection.size(); index++) {
			itemInCollection = itemsInCollection.get(index);
			log.debug(ManagementUtils.ps+"[getItemsInCollection] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			objToRet.add(createJSONBdlItemByVOggettoOriginale(itemInCollection));
			log.debug(ManagementUtils.ps+"[getItemsInCollection] Res: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
		}
		log.debug(ManagementUtils.ps+"[getItemsInCollection] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		return objToRet;
	}
	
	@GET
	@Path("json/featured/{num: "+regExprForNum+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlItemLight> getFeaturedItems(@PathParam("num") String rowNum, @Context ServletConfig servletConfig) {
		List<JSONBdlItemLight> objToRet = new ArrayList<JSONBdlItemLight>();
		Integer nroOggetti = Integer.parseInt(rowNum);
		
		log.debug(ManagementUtils.ps+"[getFeaturedItems] Req: jsonGetOggettiInVetrina() - Time: "+ManagementUtils.getCpuTime());
		List<VOggettoDTO> featuredItems = vOggetti.jsonGetOggettiInVetrina(restUtils.getListaFiltriPubblicati(servletConfig), nroOggetti);
		log.debug(ManagementUtils.ps+"[getFeaturedItems] Res: jsonGetOggettiInVetrina() - Time: "+ManagementUtils.getCpuTime());
		
		log.debug(ManagementUtils.ps+"[getFeaturedItems] Compongo l'objToRet... - Time: "+ManagementUtils.getCpuTime());
		VOggettoDTO vOO = null;
		for(int i=0; i<featuredItems.size(); i++) {
			vOO = featuredItems.get(i);
			
			log.debug(ManagementUtils.ps+"[getFeaturedItems] Req: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
			objToRet.add(createJSONBdlItemLightByVOggettoOriginale(vOO, servletConfig));
			log.debug(ManagementUtils.ps+"[getFeaturedItems] Res: createJSONBdlItemByVOggettoOriginale() - Time: "+ManagementUtils.getCpuTime());
		}
		log.debug(ManagementUtils.ps+"[getFeaturedItems] ...Finisco - Time: "+ManagementUtils.getCpuTime());
		
		return objToRet;
	}
	
	/**
	 * Legge mapreader.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return mapreader
	 */
	@GET
	@Path("json/item/{cdOggetto: "+regExprForID+"}/mapreader")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlMapreader getMapreader(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);		
		log.debug("[getMapreader] cdOggettoOriginale="+cdOggettoOriginale.toString());
		JSONBdlMapreader objToRet = null;
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			if(restUtils.immaginiUtils.hasNaturaMap(vOggettoOriginale.getT_CdTipoOggetto())) {
				BdlOggettoOriginale oggettoOriginale = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(vOggettoOriginale.getO_CdOggettoOriginale());
				objToRet = new JSONBdlMapreader(
						oggettoOriginale.getVlMapMaxZoomLevel().intValueExact(), 
						oggettoOriginale.getVlMapMinZoomLevel().intValueExact(), 
						oggettoOriginale.getVlMapMaxZoomW().intValueExact(), 
						oggettoOriginale.getVlMapMaxZoomH().intValueExact(),
						oggettoOriginale.getVlMapBorderW().intValueExact(),
						oggettoOriginale.getVlMapBorderH().intValueExact()
				);
			}
		}
		return objToRet;
	}
	
	/**
	 * Legge audioplayer files.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return audioplayer files
	 * @throws Exception 
	 */
	@GET
	@Path("json/item/{cdOggetto: "+regExprForID+"}/audioplayer")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlAudioplayerFile> getAudioplayerFiles(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) throws Exception {
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);		
		log.debug("[getAudioplayerFiles] cdOggettoOriginale="+cdOggettoOriginale.toString());
		List<JSONBdlAudioplayerFile> objToRet = null;
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			if(restUtils.immaginiUtils.hasNaturaAudio(vOggettoOriginale.getT_CdTipoOggetto())) {
				BdlFormato fmt = restUtils.immaginiUtils.getFormatiNaturaAudio(vOggettoOriginale.getT_CdTipoOggetto()).get(0);
				String basePath = restUtils.immaginiUtils.makeFilePathWrk(vOggettoOriginale.getO_CdWorkFilesystem(),vOggettoOriginale.getO_DigitalizzatoreId(), fmt.getDnNomeFormato());
				
				objToRet = new ArrayList<JSONBdlAudioplayerFile>();
				
				File myDir = new File(basePath);
				FileFilter dirFilter = new FileFilter() {
					public boolean accept(File file) {
						return file.isFile();
					}
				};
				File[] myListOfFiles = myDir.listFiles(dirFilter);
				Arrays.sort(
					myListOfFiles,
					new Comparator<File>() {
						public int compare(File a, File b) {
							return a.getName().compareTo(b.getName());
						}
					}
				);
				for (File myAudio : myListOfFiles) {
					
					String codice = myAudio.getName().substring(0, 4).replaceFirst("[0]{0,3}", "");
					int id = Integer.parseInt(codice);
					
					String title = getAudioTitle(myAudio.getName());
					objToRet.add(new JSONBdlAudioplayerFile(id, title));
				}
			}
		}
		return objToRet;
	}
	
	/**
	 * Search bookreader ocr.
	 *
	 * @param textToSearch  text to search
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return list
	 */
	@POST
	@Path("json/item/{cdOggetto: "+regExprForID+"}/bookreader/ocr")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlBookreaderPage> searchBookreaderOcr(@FormParam("textToSearch") String textToSearch, @PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {
		List<JSONBdlBookreaderPage> objToRet = new ArrayList<JSONBdlBookreaderPage>();
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);		
		log.debug("[searchBookreaderOcr] cdOggettoOriginale="+cdOggettoOriginale.toString());
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			if(
					restUtils.immaginiUtils.hasNaturaMultiImage(vOggettoOriginale.getT_CdTipoOggetto()) || 
					restUtils.immaginiUtils.hasNaturaSingleImage(vOggettoOriginale.getT_CdTipoOggetto())
			) {
				String myText = "%"+textToSearch.replaceAll("\\W", "")+"%";
				log.debug("[searchBookreaderOcr] textToSearch=\""+myText+"\"");
				
				List<BdlPdfPagina> paginePdf = bdlPdfPaginaRepository.findByCdOggettoOriginaleAndDsPaginaLikeOrderByNrPaginaDesc(cdOggettoOriginale, myText);
				if(!paginePdf.isEmpty()) {
					for(BdlPdfPagina paginaPdf : paginePdf) {
						String progressivoImage = StringUtils.leftPad(paginaPdf.getNrPagina().toString(), 4, "0");
						
						List<BdlImmagine> image  = bdlImmagineRepository.findByCdOggettoOriginaleAndDnNomeImmagine(cdOggettoOriginale, progressivoImage);
						
						JSONBdlBookreaderPage pageToAdd = new JSONBdlBookreaderPage(
								new Integer(paginaPdf.getNrPagina().intValueExact()).toString(),
								image.get(0).getNrPxLarghezzaThumb().intValueExact(), 
								image.get(0).getNrPxAltezzaThumb().intValueExact(), 
								image.get(0).getNrPxLarghezzaReader().intValueExact(), 
								image.get(0).getNrPxAltezzaReader().intValueExact(), 
								image.get(0).getNrPxLarghezzaZoom().intValueExact(), 
								image.get(0).getNrPxAltezzaZoom().intValueExact()
						);
						objToRet.add(pageToAdd);
					}
				}
			}
		}
		return objToRet;
	}
	
	/**
	 * Search semantic.
	 *
	 * @param textToSearch  text to search
	 * @param typeToSearchStr  type to search str
	 * @param servletConfig  servlet config
	 * @return JSON bdl semantic search
	 */
	@POST
	@Path("json/semantic-search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlSemanticSearch searchSemantic(@FormParam("textToSearch") String textToSearch, @FormParam("typeToSearch") String typeToSearchStr, @Context ServletConfig servletConfig) {
		BigDecimal typeToSearch = new BigDecimal(typeToSearchStr);
		String myText = textToSearch.replaceAll("[^ \\w\\d]", "");
		log.debug("[searchSemantic] textToSearch=\""+myText+"\" --- typeToSearch="+typeToSearch.toString());
		return createJSONBdlSemanticSearch(vOggetti.jsonSearchSemantic(myText, typeToSearch));
	}
	
	/**
	 * Search advanced.
	 *
	 * @param filters  filters
	 * @param servletConfig  servlet config
	 * @return JSON bdl search
	 */
	@POST
	@Path("json/search-advanced")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlSearch searchAdvanced(List<JSONBdlSearchFilter> filters, @Context ServletConfig servletConfig) {
		log.debug(ManagementUtils.ps+"[searchAdvanced] Entering");
		
		log.debug(ManagementUtils.ps+"[searchAdvanced] Req: jsonSearch");
		List<VOggettoDTO> vOggettiResult = vOggetti.jsonSearch(filters, restUtils.getListaFiltriPubblicati(servletConfig));
		log.debug(ManagementUtils.ps+"[searchAdvanced] Res: jsonSearch");
		
		log.debug(ManagementUtils.ps+"[searchAdvanced] Popolo objToRet... ");
		JSONBdlSearch objToRet = new JSONBdlSearch();
		List<JSONBdlItemLight> ris = new ArrayList<JSONBdlItemLight>();
		Iterator<VOggettoDTO> oggItr = vOggettiResult.iterator();
		while(oggItr.hasNext()){
			ris.add(createJSONBdlItemLightByVOggettoOriginale(oggItr.next(),servletConfig));
		}
		objToRet.setResults(ris);
		objToRet.setFilters(filters);
		objToRet.setOtherfilters(populateJSONBdlSearchFilter(filters, restUtils.getListaFiltriPubblicati(servletConfig)));
		log.debug(ManagementUtils.ps+"[searchAdvanced] ...Popolato objToRet! ");
		
		log.debug(ManagementUtils.ps+"[searchAdvanced] Exiting");
		return objToRet;
	}
	
	/**
	 * Search advanced.
	 *
	 * @param servletConfig  servlet config
	 * @return JSON bdl search
	 */
	@GET
	@Path("json/search-advanced")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlSearch searchAdvanced(@Context ServletConfig servletConfig) {
		List<JSONBdlSearchFilter> filters = null;
		return searchAdvanced(filters, servletConfig);
	}

	private List<JSONBdlSearchFilter> populateJSONBdlSearchFilter(List<JSONBdlSearchFilter> filters, List<String> listaFiltriPubblicati) {
		List<JSONBdlSearchFilter> otherFilters = new ArrayList<JSONBdlSearchFilter>();
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_KIND");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_KIND, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_AUTHOR");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_AUTHOR, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_DATE");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_DATE, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_LANG");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_LANG, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_SUBJECT");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_SUBJECT, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_COLLECTION");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_COLLECTION, listaFiltriPubblicati)); 
		log.debug(ManagementUtils.ps+"[populateJSONBdlSearchFilter] Req: getOtherJsonFilter-JSON_FILTERTYPE_INSTITUTE");
		otherFilters.addAll(vOggetti.getOtherJsonFilter(filters,BdlServerConstants.JSON_FILTERTYPE_INSTITUTE, listaFiltriPubblicati));
		return otherFilters;
	}
	
	/**
	 * Legge languages.
	 *
	 * @param servletConfig  servlet config
	 * @return languages
	 */
	@GET
	@Path("json/languages/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getLanguages(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlLingua> languages = bdlLinguaRepository.findLanguagesByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlLingua language : languages) {
			objToRet.add(new JSONBdlRef(language.getCdLingua(), language.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Legge kinds.
	 *
	 * @param servletConfig  servlet config
	 * @return kinds
	 */
	@GET
	@Path("json/kinds/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getKinds(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlLivelloBiblio> kinds = bdlLivelloBiblioRepository.findKindsByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlLivelloBiblio kind : kinds) {
			objToRet.add(new JSONBdlRef(kind.getCdLivelloBiblio(), kind.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Legge subjects.
	 *
	 * @param servletConfig  servlet config
	 * @return subjects
	 */
	@GET
	@Path("json/subjects/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getSubjects(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlSoggetto> subjects = bdlSoggettoRepository.findSubjectsByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlSoggetto subject : subjects) {
			objToRet.add(new JSONBdlRef(subject.getCdSoggetto(), subject.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Legge graphic materials.
	 *
	 * @param servletConfig  servlet config
	 * @return graphic materials
	 */
	@GET
	@Path("json/graphic-materials/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getGraphicMaterials(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlTipoGrafica> graphicMaterials = bdlTipoGraficaRepository.findGraphicMaterialsByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlTipoGrafica graphicMaterial : graphicMaterials) {
			objToRet.add(new JSONBdlRef(graphicMaterial.getCdTipoGrafica(), graphicMaterial.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Legge tecniques.
	 *
	 * @param servletConfig  servlet config
	 * @return tecniques
	 */
	@GET
	@Path("json/tecniques/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getTecniques(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlTecnicaGrafica> tecniques = bdlTecnicaGraficaRepository.findTecniquesByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlTecnicaGrafica tecnique : tecniques) {
			objToRet.add(new JSONBdlRef(tecnique.getCdTecnicaGrafica(), tecnique.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Legge supports.
	 *
	 * @param servletConfig  servlet config
	 * @return supports
	 */
	@GET
	@Path("json/supports/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlRef> getSupports(@Context ServletConfig servletConfig) {
		List<JSONBdlRef> objToRet = new ArrayList<JSONBdlRef>();
		List<BdlSupporto> supports = bdlSupportoRepository.findSupportsByStatoOggetto(restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
		for(BdlSupporto support : supports) {
			objToRet.add(new JSONBdlRef(support.getCdSupporto(), support.getDsDescrizione()));
		}
		return objToRet;
	}
	
	/**
	 * Search advanced.
	 *
	 * @param filters  filters
	 * @param servletConfig  servlet config
	 * @return JSON bdl search
	 */
	@POST
	@Path("json/search-advanced-paginated/{startItem: "+regExprForNum+"}/{endItem: "+regExprForNum+"}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlSearchPaginated searchAdvancedPaginated(List<JSONBdlSearchFilter> filters, 
			@PathParam("startItem") String startItemStr, @PathParam("endItem") String endItemStr, @Context ServletConfig servletConfig) {
		BigDecimal startItem = new BigDecimal(startItemStr);
		BigDecimal endItem = new BigDecimal(endItemStr);
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Entering con startItem="+startItem+" --- endItem="+endItem);
		
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Req: jsonSearchPaginated");
		List<VOggettoDTO> vOggettiResult = vOggetti.jsonSearchPaginated(filters, restUtils.getListaFiltriPubblicati(servletConfig), startItem, endItem);
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Res: jsonSearchPaginated --- vOggettiResult.size()="+vOggettiResult.size());
		
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Popolo objToRet... ");
		JSONBdlSearchPaginated objToRet = new JSONBdlSearchPaginated();
		List<JSONBdlItemLight> ris = new ArrayList<JSONBdlItemLight>();
		Iterator<VOggettoDTO> oggItr = vOggettiResult.iterator();
		while(oggItr.hasNext()){
			ris.add(createJSONBdlItemLightByVOggettoOriginale(oggItr.next(),servletConfig));
		}
		objToRet.setResults(ris);
		objToRet.setFilters(filters);
		objToRet.setOtherfilters(populateJSONBdlSearchFilter(filters, restUtils.getListaFiltriPubblicati(servletConfig)));
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Req: jsonSearchCount");
		BigDecimal resCount = vOggetti.jsonSearchCount(filters, restUtils.getListaFiltriPubblicati(servletConfig));
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Res: jsonSearchCount --- resCount="+resCount);
		objToRet.setResCount(resCount.intValueExact());
		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] ...Popolato objToRet! ");

		log.debug(ManagementUtils.ps+"[searchAdvancedPaginated] Exiting");
		return objToRet;
	}
	
	/**
	 * Search advanced.
	 *
	 * @param servletConfig  servlet config
	 * @return JSON bdl search
	 */
	@GET
	@Path("json/search-advanced-paginated/{startItem: "+regExprForNum+"}/{endItem: "+regExprForNum+"}")
    @Produces(MediaType.APPLICATION_JSON)
	public JSONBdlSearch searchAdvancedPaginated(@PathParam("startItem") String startItemStr, 
			@PathParam("endItem") String endItemStr, @Context ServletConfig servletConfig) {
		List<JSONBdlSearchFilter> filters = null;
		return searchAdvancedPaginated(filters, startItemStr, endItemStr, servletConfig);
	}
	
    /**
     * xxx.
     *
     * @param objToTreat  obj to treat
     * @return JSON bdl semantic search
     */
	private JSONBdlSemanticSearch createJSONBdlSemanticSearch(List<List<SubMenuItemDTO>> objToTreat) {
		List<List<JSONBdlRef>> objTmpUno = new ArrayList<List<JSONBdlRef>>();
		for(List<SubMenuItemDTO> listToTreat : objToTreat) {
			List<JSONBdlRef> objTmpDue = new ArrayList<JSONBdlRef>();
			for(SubMenuItemDTO dtoToTreat : listToTreat) {
				objTmpDue.add(new JSONBdlRef(dtoToTreat.getCodice(), dtoToTreat.getLabel()));
			}
			objTmpUno.add(objTmpDue);
		}
		return new JSONBdlSemanticSearch(objTmpUno.get(0), objTmpUno.get(1), objTmpUno.get(2));
	}
	
	private List<JSONBdlRef> createAutoriByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		List<JSONBdlRef> autori = new ArrayList<JSONBdlRef>();
		autori.add(new JSONBdlRef(vOggettoOriginale.getO_CdAutore(), vOggettoOriginale.getO_DsAutore()));
		autori.add(new JSONBdlRef(vOggettoOriginale.getO_CdAutoreSec(), vOggettoOriginale.getO_DsAutoreSec()));
		return autori;
	}
	
	private List<JSONBdlRef> createSoggettiByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		List<JSONBdlRef> soggetti = new ArrayList<JSONBdlRef>();
		soggetti.add(new JSONBdlRef(vOggettoOriginale.getO_CdSoggetto(), vOggettoOriginale.getO_DsSoggetto()));
		soggetti.add(new JSONBdlRef(vOggettoOriginale.getO_CdSoggettoProd(), vOggettoOriginale.getO_DsSoggettoProd()));
		return soggetti;
	}
	
	private JSONBdlRef createEditoreByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		JSONBdlRef editore = new JSONBdlRef();
		editore.setId(vOggettoOriginale.getO_CdEditore());
		editore.setName(vOggettoOriginale.getO_DsEditore());
		return editore;
	}
	
	private JSONBdlRef createTipoOggettoByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		JSONBdlRef tipoOggetto = new JSONBdlRef();
		tipoOggetto.setId(vOggettoOriginale.getT_CdTipoOggetto());
		tipoOggetto.setName(vOggettoOriginale.getT_DnNome());
		return tipoOggetto;
	}
	
	private JSONBdlRef createLinguaByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		JSONBdlRef lingua = new JSONBdlRef();
		lingua.setId(vOggettoOriginale.getO_CdLingua());
		lingua.setName(vOggettoOriginale.getO_DsLingua());
		return lingua;
	}
	
	private JSONBdlRef createCollezioneByVOggettoDTO(VOggettoDTO vOggettoOriginale) {
		JSONBdlRef collezione = new JSONBdlRef();
		collezione.setId(vOggettoOriginale.getC_CdCollezione());
		collezione.setName(vOggettoOriginale.getC_DnTitolo());
		return collezione;
	}
	
	private String getDigitalReader(VOggettoDTO vOggettoOriginale) {
		String digitalReader = null;
		if(restUtils.immaginiUtils.hasNaturaAudio(vOggettoOriginale.getT_CdTipoOggetto())) {
			digitalReader = BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO;
		} else if(restUtils.immaginiUtils.hasNaturaMap(vOggettoOriginale.getT_CdTipoOggetto())) {
			digitalReader = BdlSharedConstants.BDL_FORMATO_NATURA_MAP;
		} else if(restUtils.immaginiUtils.hasNaturaSingleImage(vOggettoOriginale.getT_CdTipoOggetto())) {
			digitalReader = BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE;
		} else if(restUtils.immaginiUtils.hasNaturaMultiImage(vOggettoOriginale.getT_CdTipoOggetto())) {
			digitalReader = BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE;
		}
		return digitalReader;
	}

	/**
	 * Crea json bdl item by v oggetto originale.
	 *
	 * @param vOggettoOriginale  v oggetto originale
	 * @return JSON bdl item
	 */
	private JSONBdlItem createJSONBdlItemByVOggettoOriginale(VOggettoDTO vOggettoOriginale) {
		return createJSONBdlItemByVOggettoOriginale(vOggettoOriginale, null);
	}
	
	private JSONBdlItemLight createJSONBdlItemLightByVOggettoOriginale(VOggettoDTO vOggettoOriginale, ServletConfig servletConfig) { 
		/* Autori */
		List<JSONBdlRef> autori = createAutoriByVOggettoDTO(vOggettoOriginale);
		/* Editore */
		JSONBdlRef editore = createEditoreByVOggettoDTO(vOggettoOriginale);
		
		/* Digital Reader */
		String digitalReader = getDigitalReader(vOggettoOriginale);

		/* Cover? */
		JSONBdlCover coverJSONBdlCover = null;
		String coverNrImg = null;
		
		/* Non riesco a recuperare l'immagine? */
		if(vOggettoOriginale.getCOV_CdImmagine()!=null) {
			coverNrImg = vOggettoOriginale.getCOV_DnNomeImmagine().replaceFirst("[0]{0,3}", "");
			coverJSONBdlCover = new JSONBdlCover(
					new Integer(Integer.parseInt(coverNrImg)).toString(),
					vOggettoOriginale.getCOV_NrPxLarghezzaThumb().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxAltezzaThumb().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxLarghezzaReader().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxAltezzaReader().intValueExact(), 
					!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?vOggettoOriginale.getCOV_NrPxLarghezzaZoom().intValueExact():null, 
					!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?vOggettoOriginale.getCOV_NrPxAltezzaZoom().intValueExact():null,
					vOggettoOriginale.getO_CdOggettoOriginale()
			);
		} else {
			/* Ho dei figli? */
			if(servletConfig!=null) {
				if(vOggettoOriginale.getO_NrOggettiInferiori()!=null && !vOggettoOriginale.getO_NrOggettiInferiori().equals(BigDecimal.ZERO)){
					log.debug(ManagementUtils.ps+"[createJSONBdlItemLightByVOggettoOriginale] Cerco gli oggetti inferiori per costruire la cover... - Time: "+ManagementUtils.getCpuTime());
	    			List<SubMenuItemDTO> items = vOggetti.findOggettiContenuti(vOggettoOriginale.getO_CdOggettoOriginale(), restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
	    			for(SubMenuItemDTO obj : items) {
						BdlImmagine coverImage = null;
						coverImage = bdlImmagineRepository.findByCdOggettoOriginaleAndFlPrincipale(obj.getCodice(), true);
						if(coverImage!=null) {
							coverNrImg = coverImage.getDnNomeImmagine().replaceFirst("[0]{0,3}", "");
							/* Ho una Cover! */
							coverJSONBdlCover = new JSONBdlCover(
								new Integer(Integer.parseInt(coverNrImg)).toString(),
								coverImage.getNrPxLarghezzaThumb().intValueExact(), 
								coverImage.getNrPxAltezzaThumb().intValueExact(), 
								coverImage.getNrPxLarghezzaReader().intValueExact(), 
								coverImage.getNrPxAltezzaReader().intValueExact(), 
								!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?coverImage.getNrPxLarghezzaZoom().intValueExact():null, 
								!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?coverImage.getNrPxAltezzaZoom().intValueExact():null,
								obj.getCodice()
							);

							break;
						}
	    			}
	    			log.debug(ManagementUtils.ps+"[createJSONBdlItemLightByVOggettoOriginale] ...Finisco - Time: "+ManagementUtils.getCpuTime());
				}
			}
		}
		log.debug(ManagementUtils.ps+"[createJSONBdlItemByVOggettoOriginale] ...Finisco - Time: "+ManagementUtils.getCpuTime());

		return new JSONBdlItemLight(
				vOggettoOriginale.getO_CdOggettoOriginale(), 
				vOggettoOriginale.getO_DnTitoloFe(),
				vOggettoOriginale.getO_DsData(),
				autori, 
				editore, 
				vOggettoOriginale.getO_Dn_LuogoPubblicazione(), 
				coverJSONBdlCover,
				vOggettoOriginale.getO_DsLivelloBiblio(),
				digitalReader,
				vOggettoOriginale.getO_NrRilevanza()
		);
	}
	
	/**
	 * Crea json bdl item by v oggetto originale.
	 *
	 * @param vOggettoOriginale  v oggetto originale
	 * @param servletConfig  servlet config
	 * @return JSON bdl item
	 */
	private JSONBdlItem createJSONBdlItemByVOggettoOriginale(VOggettoDTO vOggettoOriginale, ServletConfig servletConfig) {
		
		/* Autori */
		List<JSONBdlRef> autori = createAutoriByVOggettoDTO(vOggettoOriginale);
		/* Soggetti */
		List<JSONBdlRef> soggetti = createSoggettiByVOggettoDTO(vOggettoOriginale);
		/* Editore */
		JSONBdlRef editore = createEditoreByVOggettoDTO(vOggettoOriginale);
		/* Tipo oggetto */
		JSONBdlRef tipoOggetto = createTipoOggettoByVOggettoDTO(vOggettoOriginale);
		/* Lingua */
		JSONBdlRef lingua = createLinguaByVOggettoDTO(vOggettoOriginale);
		/* Collezione */
		JSONBdlRef collezione = createCollezioneByVOggettoDTO(vOggettoOriginale);
		
		/* Digital Reader */
		String digitalReader = getDigitalReader(vOggettoOriginale);
		
		/* Ho dei figli? */
		List<JSONBdlRef> children = new ArrayList<JSONBdlRef>();
		JSONBdlRef isChildOf = null;
		if(servletConfig!=null) {
			if(vOggettoOriginale.getO_NrOggettiInferiori()!=null && !vOggettoOriginale.getO_NrOggettiInferiori().equals(BigDecimal.ZERO)){
				log.debug(ManagementUtils.ps+"[createJSONBdlItemByVOggettoOriginale] Cerco gli oggetti inferiori... - Time: "+ManagementUtils.getCpuTime());
    			List<SubMenuItemDTO> items = vOggetti.findOggettiContenuti(vOggettoOriginale.getO_CdOggettoOriginale(), restUtils.getFiltroPubblicato(restUtils.getListaFiltriPubblicati(servletConfig)));
    			for(SubMenuItemDTO obj : items) {
    				children.add(new JSONBdlRef(obj.getCodice(), obj.getLabel()));
    			}
    			log.debug(ManagementUtils.ps+"[createJSONBdlItemByVOggettoOriginale] ...Finisco - Time: "+ManagementUtils.getCpuTime());
			}
		}
		if(vOggettoOriginale.getO_CdOggettoOriginaleSup()!=null) {
			isChildOf = new JSONBdlRef(vOggettoOriginale.getO_CdOggettoOriginaleSup(), vOggettoOriginale.getO_DnTitoloSup());
		}
		
		/* Cover? */
		JSONBdlCover coverJSONBdlCover = null;
		String coverNrImg = null;
		log.debug(ManagementUtils.ps+"[createJSONBdlItemByVOggettoOriginale] Cerco la Cover... - Time: "+ManagementUtils.getCpuTime());
		/* Non riesco a recuperare l'immagine? */
		if(vOggettoOriginale.getCOV_CdImmagine()!=null) {
			coverNrImg = vOggettoOriginale.getCOV_DnNomeImmagine().replaceFirst("[0]{0,3}", "");
			coverJSONBdlCover = new JSONBdlCover(
					new Integer(Integer.parseInt(coverNrImg)).toString(),
					vOggettoOriginale.getCOV_NrPxLarghezzaThumb().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxAltezzaThumb().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxLarghezzaReader().intValueExact(), 
					vOggettoOriginale.getCOV_NrPxAltezzaReader().intValueExact(), 
					!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?vOggettoOriginale.getCOV_NrPxLarghezzaZoom().intValueExact():null, 
					!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?vOggettoOriginale.getCOV_NrPxAltezzaZoom().intValueExact():null,
					vOggettoOriginale.getO_CdOggettoOriginale()
			);
		} else {
			/* Ho dei figli? */
			if(!children.isEmpty()) {
				for(JSONBdlRef child : children) {
					BdlImmagine coverImage = null;
					coverImage = bdlImmagineRepository.findByCdOggettoOriginaleAndFlPrincipale(child.getId(), true);
					if(coverImage!=null) {
						coverNrImg = coverImage.getDnNomeImmagine().replaceFirst("[0]{0,3}", "");
						/* Ho una Cover! */
						coverJSONBdlCover = new JSONBdlCover(
								new Integer(Integer.parseInt(coverNrImg)).toString(),
								coverImage.getNrPxLarghezzaThumb().intValueExact(), 
								coverImage.getNrPxAltezzaThumb().intValueExact(), 
								coverImage.getNrPxLarghezzaReader().intValueExact(), 
								coverImage.getNrPxAltezzaReader().intValueExact(), 
								!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?coverImage.getNrPxLarghezzaZoom().intValueExact():null, 
								!digitalReader.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)?coverImage.getNrPxAltezzaZoom().intValueExact():null,
								child.getId()
						);

						break;
					}
				}
			}
		}
		log.debug(ManagementUtils.ps+"[createJSONBdlItemByVOggettoOriginale] ...Finisco - Time: "+ManagementUtils.getCpuTime());

		List<JSONBdlRef> oggettiInferiori = null;
		if(!children.isEmpty()) {
			oggettiInferiori = children;
		}
		
		JSONBdlItem jsonBdlItem = new JSONBdlItem(
				vOggettoOriginale.getO_CdOggettoOriginale(), 
				tipoOggetto, 
				vOggettoOriginale.getO_DnTitoloFe(),
				vOggettoOriginale.getO_DsData(),
				vOggettoOriginale.getO_DsContenutistica(), 
				vOggettoOriginale.getO_DsFisica(), 
				autori, 
				soggetti, 
				editore,
				lingua,
				vOggettoOriginale.getO_Dn_LuogoPubblicazione(), 
				vOggettoOriginale.getO_CdIdentificativoIssn(), 
				vOggettoOriginale.getO_CdIdentificativoIsbn(), 
				vOggettoOriginale.getO_DsLinkCatalogo(), 
				vOggettoOriginale.getO_DsNote(), 
				vOggettoOriginale.getO_DsDiritti(), 
				collezione,
				coverJSONBdlCover,
				vOggettoOriginale.getO_DsLivelloBiblio(),
				digitalReader,
				vOggettoOriginale.getO_NrRilevanza(),
				isChildOf,
				oggettiInferiori,
				vOggettoOriginale.getO_FlPdfMultipagina(),
				vOggettoOriginale.getO_FlRicercaOcr()
		);
		jsonBdlItem.setSegnatura(vOggettoOriginale.getO_Segnatura());
		return jsonBdlItem;
    }

	/**
	 * Legge collections light.
	 *
	 * @param servletConfig  servlet config
	 * @return collections
	 */
	@GET
	@Path("json/collections-light/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<JSONBdlCollectionLight> getCollectionsLight(@Context ServletConfig servletConfig) {
		List<JSONBdlCollectionLight> objToRet = vOggetti.jsonGetCollezioniLight(restUtils.getListaFiltriPubblicati(servletConfig));
		return objToRet;
	}
    
    /**
     * Legge audio title.
     *
     * @param str  str
     * @return audio title
     */
    private String getAudioTitle(String str) {
    	str = str.replaceAll("/^\\d{4}\\s*-\\s*/i", "");
    	str = str.replaceAll("/\\.[^.]*$/i", "");
    	str = str.replaceAll("/[._-]/i", " ");
    	return str;
    }
}
