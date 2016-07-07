package it.lispa.bdl.server.rest;

import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlToc;
import it.lispa.bdl.server.repository.BdlCollezioneRepository;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlFormatoRepository;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlLinguaRepository;
import it.lispa.bdl.server.repository.BdlLivelloBiblioRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlPdfPaginaRepository;
import it.lispa.bdl.server.repository.BdlSoggettoRepository;
import it.lispa.bdl.server.repository.BdlSupportoRepository;
import it.lispa.bdl.server.repository.BdlTecnicaGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoOggettoRepository;
import it.lispa.bdl.server.repository.BdlTocRepository;
import it.lispa.bdl.server.repository.BdlVetrinaRepository;
import it.lispa.bdl.server.utils.BdlServerProperties;
import it.lispa.bdl.server.utils.MapperUtils;
import it.lispa.bdl.server.utils.RestUtils;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.FormatoDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.repackaged.org.joda.time.DateTime;

/**
 * Class Srv.
 */
public class Srv {

	/** rest utils. */
	@Autowired protected RestUtils restUtils;
	
    /** mapper. */
    @Autowired protected MapperUtils mapper;
	
	/** v oggetti. */
	@Autowired protected VOggettiUtils vOggetti;
	
	/** properties. */
	@Autowired protected BdlServerProperties properties;
	
	/** bdl immagine repository. */
	@Autowired protected BdlImmagineRepository bdlImmagineRepository;
	
	/** bdl oggetto originale repository. */
	@Autowired protected BdlOggettoOriginaleRepository bdlOggettoOriginaleRepository;
	
	/** bdl vetrina repository. */
	@Autowired protected BdlVetrinaRepository bdlVetrinaRepository;
	
	/** bdl formato repository. */
	@Autowired protected BdlFormatoRepository bdlFormatoRepository;
	
	/** bdl toc repository. */
	@Autowired protected BdlTocRepository bdlTocRepository;
	
	/** bdl collezione repository. */
	@Autowired protected BdlCollezioneRepository bdlCollezioneRepository;
	
	/** bdl tipo oggetto repository. */
	@Autowired protected BdlTipoOggettoRepository bdlTipoOggettoRepository;
	
	/** bdl ente repository. */
	@Autowired protected BdlEnteRepository bdlEnteRepository;
	
	/** bdl pdf pagina repository. */
	@Autowired protected BdlPdfPaginaRepository bdlPdfPaginaRepository;
	
	/** bdl lingua repository. */
	@Autowired protected BdlLinguaRepository bdlLinguaRepository;
	
	/** bdl soggetto repository. */
	@Autowired protected BdlSoggettoRepository bdlSoggettoRepository;
	
	/** bdl livello biblio repository. */
	@Autowired protected BdlLivelloBiblioRepository bdlLivelloBiblioRepository;
	
	/** bdl tipo grafica repository. */
	@Autowired protected BdlTipoGraficaRepository bdlTipoGraficaRepository;
	
	/** bdl supporto repository. */
	@Autowired protected BdlSupportoRepository bdlSupportoRepository;
	
	/** bdl tecnica grafica repository. */
	@Autowired protected BdlTecnicaGraficaRepository bdlTecnicaGraficaRepository;

    /** log. */
    private static Logger log = Logger.getLogger(Srv.class);
    
    /** La Costante regExprForNum. */
    protected static final String regExprForNum = "\\d*";

    /** La Costante regExprForID. */
    protected static final String regExprForID = "(?=[1-9]{1})\\d*";

    /** La Costante regExprForID. */
    protected static final String regExprForImageDoubleLeftID = "(?=[1-9]{1})\\d*DLX";
    
    /** La Costante regExprForID. */
    protected static final String regExprForImageDoubleRightID = "(?=[1-9]{1})\\d*DRX";
    
    /** La Costante regExprForTileZoom. */
    private static final String regExprForTileZoom = "\\d*";
    
    /** La Costante regExprForTileXY. */
    private static final String regExprForTileXY = "\\d*";
	
	/** La Costante METSXML_FIXURI. */
	public static final String METSXML_FIXURI = "http://www.lombardiabeniculturali.it/digital_files/";
	
	/** La Costante METSXML_ROLE. */
	public static final String METSXML_ROLE = "creator";
	
	/** La Costante METSXML_TYPE. */
	public static final String METSXML_TYPE = "organization";
	
	/** La Costante METSXML_MDTYPE. */
	public static final String METSXML_MDTYPE = "Europeana - Dublin Core";
	
	/** La Costante METSXML_TOC_TYPE. */
	public static final String METSXML_TOC_TYPE = "logical";
	
	/** La Costante METSXML_TOC_LABEL. */
	public static final String METSXML_TOC_LABEL = "toc";
	
	/** La Costante METSXML_TYPE_DIV. */
	public static final String METSXML_TYPE_DIV = "text";
	
	/** La Costante METSXML_FILEID. */
	public static final String METSXML_FILEID = "fileid";
	
	/** La Costante METSXML_SOMMARIO. */
	public static final String METSXML_SOMMARIO = "Sommario";
	
	/** La Costante METSXML_LOCTYPE. */
	public static final String METSXML_LOCTYPE = "URL";
	
	/** La Costante METSXML_OBJID_BROWSE. */
	public static final String METSXML_OBJID_BROWSE = "browse";
	
	/** La Costante METSXML_FILEGRP_USE. */
	public static final String METSXML_FILEGRP_USE = "image/view";

	/** La Costante METSXML_FORMATO_IMMAGINE. */
	public static final String METSXML_FORMATO_IMMAGINE = "large";
	
	/** La Costante METSXML_FORMATO_CHAR. */
	public static final String METSXML_FORMATO_CHAR = "L";

	/** La Costante METSXML_EUROPEANA_TYPE. */
	public static final String METSXML_EUROPEANA_TYPE = "TEXT";
	
	/** La Costante METSXML_EUROPEANA_PROVIDER. */
	public static final String METSXML_EUROPEANA_PROVIDER = "Biblioteca Digitale Lombarda";

	/** La Costante METSXML_ESTENSIONE_SEPARATOR. */
	public static final String METSXML_ESTENSIONE_SEPARATOR = "\\|";
	
	/** La Costante METSXML_PATH_SEPARATOR. */
	public static final String METSXML_PATH_SEPARATOR = "/";
	
	/** La Costante METSXML_DOT_SEPARATOR. */
	public static final String METSXML_DOT_SEPARATOR = ".";

	protected static final Integer HTTP_404 = Integer.valueOf(404);

	public static final String IMAGEPART_NONE = "none";
	public static final String IMAGEPART_LEFT = "LX";
	public static final String IMAGEPART_RIGHT = "DX";
	
    /**
     * Enum DefaultForEnum.
     */
	private static enum DefaultForEnum { 

		/** thumbnail. */
		THUMBNAIL, 
		/** reader. */
		READER, 
		/** zoom. */
		ZOOM; 
	}
    
    /**
     * Enum ErrorEnum.
     */
    private static enum ErrorEnum { 
    	
    	/** object. */
    	OBJECT("no_object.gif"), 
    	/** format. */
    	FORMAT("no_format.gif"), 
    	/** image. */
    	IMAGE("no_image.gif"); 
    	
	    /** image name. */
	    private String imageName;
    	
	    /**
	     * Istanzia un nuovo error enum.
	     *
	     * @param imageName  image name
	     */
	    private ErrorEnum(String imageName) {
    		this.imageName = imageName;
    	}
    	
	    /**
    	 * Legge image name.
    	 *
    	 * @return the image name
    	 */
	    public String getImageName() {
    		return this.imageName;
    	}
    }
    
    /**
     * Legge thumb image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param servletConfig  servlet config
     * @return thumb image
     * @throws Exception 
     */
    @GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/thumb/{idImg: "+regExprForID+"}")
	@Produces("image/*")
	public Response getThumbImage(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idImg") String idImgStr, @Context ServletConfig servletConfig) throws Exception {
    	log.debug("[getThumbImage] cdOggetto="+cdOggettoStr+" --- idImg="+idImgStr);
		return getImage(cdOggettoStr, idImgStr, DefaultForEnum.THUMBNAIL, servletConfig).build();
	}

    /**
     * Legge reader image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param servletConfig  servlet config
     * @return reader image
     * @throws Exception 
     */
    @GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/reader/{idImg: "+regExprForID+"}")
	@Produces("image/*")
	public Response getReaderImage(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idImg") String idImgStr, @Context ServletConfig servletConfig) throws Exception {
    	log.debug("[getReaderImage] cdOggetto="+cdOggettoStr+" --- idImg="+idImgStr);
		return getImage(cdOggettoStr, idImgStr, DefaultForEnum.READER, servletConfig).build();
	}

    /**
     * Legge reader image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param servletConfig  servlet config
     * @return reader image
     * @throws Exception 
     */
    @GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/reader/{idImg: "+regExprForImageDoubleLeftID+"}")
    @Produces({"image/jpeg,image/png,image/gif"})
	public Response getReaderImageDoubleLeft(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idImg") String idImgStr, @Context ServletConfig servletConfig) throws Exception {
    	log.debug("[getReaderImageDoubleLeft] cdOggetto="+cdOggettoStr+" --- idImg="+idImgStr);
    	idImgStr = idImgStr.replace("DLX", "");
    	idImgStr = idImgStr.replace("DRX", "");
		return getImage(cdOggettoStr, idImgStr, DefaultForEnum.READER, servletConfig,Srv.IMAGEPART_LEFT).build();
	}
    
    /**
     * Legge reader image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param servletConfig  servlet config
     * @return reader image
     * @throws Exception 
     */
    @GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/reader/{idImg: "+regExprForImageDoubleRightID+"}")
    @Produces({"image/jpeg,image/png,image/gif"})
	public Response getReaderImageDoubleRight(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idImg") String idImgStr, @Context ServletConfig servletConfig) throws Exception {
    	log.debug("[getReaderImageDoubleRight] cdOggetto="+cdOggettoStr+" --- idImg="+idImgStr);
    	idImgStr = idImgStr.replace("DLX", "");
    	idImgStr = idImgStr.replace("DRX", "");
		return getImage(cdOggettoStr, idImgStr, DefaultForEnum.READER, servletConfig,Srv.IMAGEPART_RIGHT).build();
	}
    
	/**
	 * Legge zoom image.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param idImgStr  id img str
	 * @param servletConfig  servlet config
	 * @return zoom image
	 * @throws Exception 
	 */
	@GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/zoom/{idImg: "+regExprForID+"}")
	@Produces("image/*")
	public Response getZoomImage(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idImg") String idImgStr, @Context ServletConfig servletConfig) throws Exception {
		log.debug("[getZoomImage] cdOggetto="+cdOggettoStr+" --- idImg="+idImgStr);
		return getImage(cdOggettoStr, idImgStr, DefaultForEnum.ZOOM, servletConfig).build();
	}

    /**
     * Legge image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param tipoFormato  tipo formato
     * @param servletConfig  servlet config
     * @return image
     * @throws Exception 
     */
    private ResponseBuilder getImage(String cdOggettoStr, String idImgStr, DefaultForEnum tipoFormato, ServletConfig servletConfig) throws Exception {
    	return getImage(cdOggettoStr, idImgStr, tipoFormato, servletConfig, Srv.IMAGEPART_NONE);
    }
    /**
     * Legge image.
     *
     * @param cdOggettoStr  codice oggetto str
     * @param idImgStr  id img str
     * @param tipoFormato  tipo formato
     * @param servletConfig  servlet config
     * @return image
     * @throws Exception 
     */
    private ResponseBuilder getImage(String cdOggettoStr, String idImgStr, DefaultForEnum tipoFormato, ServletConfig servletConfig, String imagePart) throws Exception {
    	
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		String dnNomeImmagine = restUtils.getFileName(idImgStr);
		log.debug("[getImage] cdOggettoOriginale="+cdOggettoOriginale+" --- dnNomeImmagine="+dnNomeImmagine);
		
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);		
		if(vOggettoOriginale!=null) {
	    	BdlFormato formatoToTreat = null;
	    	if(tipoFormato.equals(DefaultForEnum.THUMBNAIL)) {
	    		formatoToTreat = bdlFormatoRepository.findByFlDefaultforthumbAndCdTipoOggetto(true, vOggettoOriginale.getT_CdTipoOggetto()).get(0);
	    	} else if(tipoFormato.equals(DefaultForEnum.READER)) {
	    		formatoToTreat = bdlFormatoRepository.findByFlDefaultforreaderAndCdTipoOggetto(true, vOggettoOriginale.getT_CdTipoOggetto()).get(0);
	    	} else if(tipoFormato.equals(DefaultForEnum.ZOOM)) {
	    		/* L'AUDIO non ha immagini defaultForZoom... */
	    		if(!restUtils.immaginiUtils.hasNaturaAudio(vOggettoOriginale.getT_CdTipoOggetto())) {
	    			formatoToTreat = bdlFormatoRepository.findByFlDefaultforzoomAndCdTipoOggetto(true, vOggettoOriginale.getT_CdTipoOggetto()).get(0);
				}else{
					/* Problemi con il FORMATO? */
		    		return restUtils.getErrorImage(servletConfig, ErrorEnum.FORMAT.getImageName());
		    	}
	    	}else{
	    		return restUtils.getErrorImage(servletConfig, ErrorEnum.FORMAT.getImageName());
	    	}
			log.debug("[getImage"+tipoFormato.toString().toUpperCase()+"] formatoToTreat="+formatoToTreat.getDnNomeFormato());
			
			File file = restUtils.immaginiUtils.getExistingFile(
				restUtils.immaginiUtils.makeFilePathWrk(
					vOggettoOriginale.getO_CdWorkFilesystem(),
					vOggettoOriginale.getO_DigitalizzatoreId(), 
					formatoToTreat.getDnNomeFormato()
				), 
				dnNomeImmagine, 
				formatoToTreat.getDsEstensione()
			);
			if(file!=null) {
				if(imagePart.equals(Srv.IMAGEPART_NONE)){
					return Response.ok(file);
				}else{
					BufferedImage originalImage=ImageIO.read(file);
					Integer width = originalImage.getWidth();
					Integer height = originalImage.getHeight();
					Integer widthCropped = new Integer((int) Math.floor(new Float(width/2)));
					Integer heightCropped = height;

					Integer xCropped = new Integer(0);
					Integer yCropped = new Integer(0);
					
					if(imagePart.equals(Srv.IMAGEPART_RIGHT)){
						xCropped = width-widthCropped;
					}
					BufferedImage resizedImage=Scalr.crop(originalImage,xCropped,yCropped,widthCropped,heightCropped);
					return Response.ok(resizedImage);
		
				}
			}
		} else {
			/* Problemi con l'OggettoOriginale? */
			return restUtils.getErrorImage(servletConfig, ErrorEnum.OBJECT.getImageName());
		}
		/* Problemi con l'IMMAGINE? */
		return restUtils.getErrorImage(servletConfig, ErrorEnum.IMAGE.getImageName());
    }
    
	/**
	 * Legge tile image.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param zoom  zoom
	 * @param x  x
	 * @param y  y
	 * @param servletConfig  servlet config
	 * @return tile image
	 * @throws Exception 
	 */
	@GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/images/map/{zoom: "+regExprForTileZoom+"}/{x: "+regExprForTileXY+"}/{y: "+regExprForTileXY+"}")
	@Produces("image/*")
	public Response getTileImage(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("zoom") String zoom,
			@PathParam("x") String x, @PathParam("y") String y, @Context ServletConfig servletConfig) throws Exception {
		
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		log.debug("[getTileImage] cdOggettoOriginale="+cdOggettoOriginale+" --- zoom="+zoom+" --- x="+x+" --- y="+y);
		
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			List<BdlFormato> fmts = restUtils.immaginiUtils.getFormatiNaturaMap(vOggettoOriginale.getT_CdTipoOggetto());
			if(!fmts.isEmpty()){
				BdlFormato fmt = fmts.get(0);
				File file = restUtils.immaginiUtils.getExistingFile(restUtils.immaginiUtils.makeFilePathWrk(vOggettoOriginale.getO_CdWorkFilesystem(),vOggettoOriginale.getO_DigitalizzatoreId(), BdlSharedConstants.BDL_FORMATO_NATURA_MAP)+File.separator+zoom+File.separator, "img_"+x+"_"+y, fmt.getDsEstensione());
				if(file!=null) {
				    String fileMime = new MimetypesFileTypeMap().getContentType(file);
					return Response.ok(file, fileMime).build();
				}
			} else {
				/* Problemi con il FORMATO? */
				return restUtils.getErrorImage(servletConfig, ErrorEnum.FORMAT.getImageName()).build();
			}
		} else {
			/* Problemi con l'OggettoOriginale? */
			return restUtils.getErrorImage(servletConfig, ErrorEnum.OBJECT.getImageName()).build();
		}
		/* Problemi con l'IMMAGINE? */
		return Response.status(HTTP_404).build();
	}
	
	/**
	 * Legge audio.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param idAudioStr  id audio str
	 * @param servletConfig  servlet config
	 * @return audio
	 * @throws Exception 
	 */
	@GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/audio/{idAudio: "+regExprForID+"}")
	@Produces("audio/mpeg")
	public Response getAudio(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("idAudio") String idAudioStr, @Context ServletConfig servletConfig) throws Exception {
		
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		String dnNomeAudio = restUtils.getFileName(idAudioStr);
		log.debug("[getAudio] cdOggettoOriginale="+cdOggettoOriginale+" --- dnNomeAudio="+dnNomeAudio);
		
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {

			List<BdlFormato> fmts = restUtils.immaginiUtils.getFormatiNaturaAudio(vOggettoOriginale.getT_CdTipoOggetto());
			if(!fmts.isEmpty()){
				BdlFormato fmt = fmts.get(0);
				
				File file = restUtils.immaginiUtils.getExistingAudio(restUtils.immaginiUtils.makeFilePathWrk(vOggettoOriginale.getO_CdWorkFilesystem(),vOggettoOriginale.getO_DigitalizzatoreId(), fmt.getDnNomeFormato()), dnNomeAudio, fmt.getDsEstensione());
				if(file!=null) {
					ResponseBuilder response = Response.ok((Object) file);
					response.header("Content-Disposition", "attachment; filename="+file.getName());
					return response.build();
				}
			}
			
		}
		return Response.status(HTTP_404).build();
	}
	
	/**
	 * Legge pdf.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return pdf
	 * @throws Exception 
	 */
	@GET
	
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/pdf{slash:/?}{numPDFPage:("+regExprForID+")?}")
	@Produces("application/pdf")
	public Response getPDF(@PathParam("cdOggetto") String cdOggettoStr, @PathParam("numPDFPage") String numPDFPage, @Context ServletConfig servletConfig) throws Exception {
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		log.debug("[getPDF] cdOggettoOriginale="+cdOggettoOriginale+"___numPDFPage="+numPDFPage);
		
		VOggettoDTO vOggettoOriginale = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);
		if(vOggettoOriginale!=null) {
			File file = null;
			if(!numPDFPage.equals("")) {
				numPDFPage = restUtils.getFileName(numPDFPage);
				log.debug("[getPDF] numPDFPage="+numPDFPage);
				file = new File(restUtils.immaginiUtils.makeFilePathWrk(vOggettoOriginale.getO_CdWorkFilesystem(),vOggettoOriginale.getO_DigitalizzatoreId(), "pdfsingle", numPDFPage, "pdf"));
			} else {
				file = new File(restUtils.immaginiUtils.makeFilePathWrk(vOggettoOriginale.getO_CdWorkFilesystem(),vOggettoOriginale.getO_DigitalizzatoreId(), "pdf", cdOggettoOriginale.toString(), "pdf"));
			}
			log.debug("[getPDF] file="+file.getPath());
			if(file.exists()) {
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename="+file.getName());
				return response.build();
			}
		}
		return Response.status(HTTP_404).build();
	}
	
	private MetsHdr buildMetsHdr(VOggettoDTO vOggettoDto) {
		MetsHdr mhdr = new MetsHdr();
		DateTime dt = new DateTime();

		mhdr.setCreateDate(dt.toString());
		mhdr.setLastModDate(dt.toString());

		MetsAgent agent = new MetsAgent();
		agent.setName(vOggettoDto.getI_DnNome());
		agent.setRole(METSXML_ROLE);
		agent.setType(METSXML_TYPE);

		mhdr.setAgent(agent);
		
		return mhdr;
	}
	
	/**
	 * Legge mets.
	 *
	 * @param cdOggettoStr  codice oggetto str
	 * @param servletConfig  servlet config
	 * @return mets
	 */
	@GET
	@Path("srv/item/{cdOggetto: "+regExprForID+"}/mets")
	@Produces(MediaType.APPLICATION_XML)
	public Response getMets(@PathParam("cdOggetto") String cdOggettoStr, @Context ServletConfig servletConfig) {

		MetsDTO metsDto = new MetsDTO();

		String fixImageUri = METSXML_FIXURI;
		BigDecimal cdOggettoOriginale = new BigDecimal(cdOggettoStr);
		
		VOggettoDTO vOggettoDto = vOggetti.findOggettoByCodice(cdOggettoOriginale, null, restUtils.getListaFiltriPubblicati(servletConfig), null);

		StringBuilder strObjId = new StringBuilder();
		strObjId.append(fixImageUri);
		strObjId.append(vOggettoDto.getC_CdCollezione()).append(METSXML_PATH_SEPARATOR);

		/* ====== HEADER SECTION ====== */
		MetsHdr mhdr = buildMetsHdr(vOggettoDto);
		
		/* ====== METADATA SECTION ===== */
		MetsDmdSec dmdSec = new MetsDmdSec();
		dmdSec.setId("DM1");

		MetsMdWrap mdWrap = new MetsMdWrap();
		mdWrap.setLabel(vOggettoDto.getO_DnTitolo());
		mdWrap.setMdType(METSXML_MDTYPE);

		/* Oggetto xml data */
		MetsXmlData xmlData = new MetsXmlData();
		Dc dc = new Dc();
		dc.setTitle(vOggettoDto.getO_DnTitolo());
		dc.setDate(vOggettoDto.getO_DsData());

		dc.setType(vOggettoDto.getT_DnNome());

		dc.setIdentifier(vOggettoDto.getO_CdIdentificativo());
		// Da attivare in base ai dati
		// dc.setDescription("XXXXX");
		// dc.setSubject("Test");

		dc.setCreator(vOggettoDto.getO_DsAutore());
		List<String> listContributor = new ArrayList<String>();
		listContributor.add(vOggettoDto.getO_DsAutoreSec());
		dc.setContributor(listContributor);

		dc.setPublisher(vOggettoDto.getO_DsEditore());
		dc.setLanguage(vOggettoDto.getO_DsLingua());
		dc.setCoverage(vOggettoDto.getO_Dn_LuogoPubblicazione());

		xmlData.setDc(dc);

		dmdSec.setMdWrap(mdWrap);

		/* ====== IMAGES SECTION ===== */
		MetsFileSec mFileSec = new MetsFileSec();
		MetsFileGrp mFileGrp = new MetsFileGrp();

		List<ImmagineDTO> listImmagini = mapper.mapListBdlImmagineToListImmagineDTO(bdlImmagineRepository.findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(cdOggettoOriginale));
		List<MetsFile> listMFile = new ArrayList<MetsFile>();

		/* Popolamento della lista di immagini */
		int seq = 0;
		String strEuropeanaObj = null;
		for (ImmagineDTO img : listImmagini) {

			StringBuilder nomeImmagine = new StringBuilder();
			nomeImmagine.append(img.getNomeImmagine());
			nomeImmagine.append(METSXML_DOT_SEPARATOR);

			FormatoDTO fmtDto = mapper.mapBdlFormatoToFormatoDTO(bdlFormatoRepository.findByFlDefaultforreaderAndCdTipoOggetto(true, vOggettoDto.getT_CdTipoOggetto()));
			
			String[] strEstensione = fmtDto.getEstensione().split(METSXML_ESTENSIONE_SEPARATOR);
			String strExt = "";
			if (strEstensione != null && strEstensione.length > 0) {
				strExt = strEstensione[0];
				nomeImmagine.append(strExt);
			}

			/**
			 * TODO: Questa URL e' da determinare a valle della pubblicazione dei dati.
			 */
			StringBuilder nameUri = new StringBuilder();
			nameUri.append(fixImageUri);
			nameUri.append(vOggettoDto.getO_CdOggettoOriginale());
			nameUri.append(METSXML_PATH_SEPARATOR);
			nameUri.append(METSXML_FORMATO_CHAR);
			nameUri.append(METSXML_PATH_SEPARATOR);
			nameUri.append(nomeImmagine.toString());

			if(seq==0){
				strEuropeanaObj = nameUri.toString();
			}

			MetsFile mFileTmp = new MetsFile();

			MetsFlocat mfLocat = new MetsFlocat();
			mfLocat.setLoctype(METSXML_LOCTYPE);
			mfLocat.setHref(nameUri.toString());


			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			String mimeType = fileNameMap.getContentTypeFor(nomeImmagine.toString());

			mFileTmp.setAdmid("ADM"+img.getCdImmagine());
			mFileTmp.setGroupid("GID"+img.getCdImmagine());
			mFileTmp.setId("FID"+img.getCdImmagine());
			mFileTmp.setMimetype(mimeType);
			mFileTmp.setSeq(String.valueOf(++seq));

			mFileTmp.setFlocat(mfLocat);
			listMFile.add(mFileTmp);

		}

		/* ==== XMLDATA SECTION => EUROPEANA SECTION ==== */
		Europeana eu = new Europeana();
		eu.setObject(strEuropeanaObj);
		eu.setProvider(METSXML_EUROPEANA_PROVIDER);
		eu.setType(METSXML_EUROPEANA_TYPE);
		eu.setIsShownAt(strObjId.toString());

		strObjId.append(METSXML_OBJID_BROWSE);
		eu.setIsShownBy(strObjId.toString());
		xmlData.setEu(eu);

		/* ==== ===================================== ==== */
		mdWrap.setXmlData(xmlData);

		/* Impostazione della lista di MetsFile */
		mFileGrp.setFile(listMFile);
		mFileGrp.setUse(METSXML_FILEGRP_USE);

		mFileSec.setFileGrp(mFileGrp);

		/* ====== TOC SECTION ===== */
		MetsStructMap structMap = new MetsStructMap();
		MetsDiv mDiv = new MetsDiv();

		List<MetsDiv> listDiv = new ArrayList<MetsDiv>();
		MetsDiv innerDiv = new MetsDiv();
		innerDiv.setDmdid("ADM");
		innerDiv.setOrder(vOggettoDto.getO_CdOggettoOriginale().toString());
		innerDiv.setLabel(METSXML_SOMMARIO);

		List<MetsDiv> listDivInner = getMetsTocSommarioOggetto(cdOggettoOriginale);
		innerDiv.setDiv(listDivInner);

		listDiv.add(innerDiv);

		MetsFptr mFptr1 = new MetsFptr();
		mFptr1.setFileid(METSXML_FILEID);
		mDiv.setFptr(mFptr1);

		mDiv.setType(METSXML_TYPE_DIV);
		mDiv.setLabel(vOggettoDto.getO_DnTitolo());
		mDiv.setDiv(listDiv);

		/* Imposto il TOC label e il tipo */
		structMap.setType(METSXML_TOC_TYPE);
		structMap.setLabel(METSXML_TOC_LABEL);
		structMap.setDiv(mDiv);

		/* ====== TOP SECTION ===== */
		metsDto.setLabel(vOggettoDto.getO_DnTitolo());
		metsDto.setObjid(strObjId.toString());

		metsDto.setStructMap(structMap);
		metsDto.setFileSec(mFileSec);
		metsDto.setDmdSec(dmdSec);

		metsDto.setMetsHdr(mhdr);

		return Response.ok(metsDto).build();
	}

	/**
	 * Class MetsDTO.
	 */
	@XmlRootElement(name = "mets", namespace = "http://www.loc.gov/METS/")
	@XmlType(propOrder = { "titolo", "metsHdr", "dmdSec", "fileSec", "structMap" })
	public static class MetsDTO {
		
		/** titolo. */
		private String titolo;
		
		/** label. */
		private String label;
		
		/** objid. */
		private String objid;
		
		/** mets hdr. */
		private MetsHdr metsHdr;
		
		/** dmd sec. */
		private MetsDmdSec dmdSec;
		
		/** file sec. */
		private MetsFileSec fileSec;
		
		/** struct map. */
		private MetsStructMap structMap;
		
		/**
		 * Legge titolo.
		 *
		 * @return the titolo
		 */
		@XmlElement
		public String getTitolo() {
			return titolo;
		}
		
		/**
		 * Imposta titolo.
		 *
		 * @param titolo the new titolo
		 */
		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}
		
		/**
		 * Legge mets hdr.
		 *
		 * @return the mets hdr
		 */
		@XmlElement
		public MetsHdr getMetsHdr() {
			return metsHdr;
		}
		
		/**
		 * Imposta mets hdr.
		 *
		 * @param metsHdr the new mets hdr
		 */
		public void setMetsHdr(MetsHdr metsHdr) {
			this.metsHdr = metsHdr;
		}
		
		/**
		 * Legge label.
		 *
		 * @return the label
		 */
		@XmlAttribute
		public String getLabel() {
			return label;
		}
		
		/**
		 * Imposta label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		
		/**
		 * Legge objid.
		 *
		 * @return the objid
		 */
		@XmlAttribute
		public String getObjid() {
			return objid;
		}
		
		/**
		 * Imposta objid.
		 *
		 * @param objid the new objid
		 */
		public void setObjid(String objid) {
			this.objid = objid;
		}
		
		/**
		 * Legge dmd sec.
		 *
		 * @return the dmd sec
		 */
		@XmlElement
		public MetsDmdSec getDmdSec() {
			return dmdSec;
		}
		
		/**
		 * Imposta dmd sec.
		 *
		 * @param dmdSec the new dmd sec
		 */
		public void setDmdSec(MetsDmdSec dmdSec) {
			this.dmdSec = dmdSec;
		}
		
		/**
		 * Legge file sec.
		 *
		 * @return the file sec
		 */
		@XmlElement
		public MetsFileSec getFileSec() {
			return fileSec;
		}
		
		/**
		 * Imposta file sec.
		 *
		 * @param fileSec the new file sec
		 */
		public void setFileSec(MetsFileSec fileSec) {
			this.fileSec = fileSec;
		}
		
		/**
		 * Legge struct map.
		 *
		 * @return the struct map
		 */
		@XmlElement
		public MetsStructMap getStructMap() {
			return structMap;
		}
		
		/**
		 * Imposta struct map.
		 *
		 * @param structMap the new struct map
		 */
		public void setStructMap(MetsStructMap structMap) {
			this.structMap = structMap;
		}
	}

	/**
	 * Class MetsHdr.
	 */
	public static class MetsHdr {
		
		/** create date. */
		private String createDate;
		
		/** last mod date. */
		private String lastModDate;
		
		/** agent. */
		private MetsAgent agent;

		/**
		 * Legge agent.
		 *
		 * @return the agent
		 */
		@XmlElement
		public MetsAgent getAgent() {
			return agent;
		}
		
		/**
		 * Imposta agent.
		 *
		 * @param metsAgent the new agent
		 */
		public void setAgent(MetsAgent metsAgent) {
			this.agent = metsAgent;
		}
		
		/**
		 * Legge create date.
		 *
		 * @return the create date
		 */
		@XmlAttribute(name = "createdate")
		public String getCreateDate() {
			return createDate;
		}
		
		/**
		 * Imposta create date.
		 *
		 * @param createDate the new create date
		 */
		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}
		
		/**
		 * Legge last mod date.
		 *
		 * @return the last mod date
		 */
		@XmlAttribute(name = "lastmoddate")
		public String getLastModDate() {
			return lastModDate;
		}
		
		/**
		 * Imposta last mod date.
		 *
		 * @param lastModDate the new last mod date
		 */
		public void setLastModDate(String lastModDate) {
			this.lastModDate = lastModDate;
		}
	}

	/**
	 * Class MetsAgent.
	 */
	public static class MetsAgent {
		
		/** type. */
		private String type;
		
		/** role. */
		private String role;
		
		/** name. */
		private String name;

		/**
		 * Legge role.
		 *
		 * @return the role
		 */
		@XmlAttribute
		public String getRole() {
			return role.toUpperCase();
		}
		
		/**
		 * Imposta role.
		 *
		 * @param role the new role
		 */
		public void setRole(String role) {
			this.role = role;
		}
		
		/**
		 * Legge name.
		 *
		 * @return the name
		 */
		@XmlElement
		public String getName() {
			return name;
		}
		
		/**
		 * Imposta name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Legge type.
		 *
		 * @return the type
		 */
		@XmlAttribute
		public String getType() {
			return type.toUpperCase();
		}
		
		/**
		 * Imposta type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}
	}

	/**
	 * Class MetsXmlData.
	 */
	public static class MetsXmlData {
		
		/** dc. */
		private Dc dc;
		
		/** eu. */
		private Europeana eu;

		/**
		 * Legge dc.
		 *
		 * @return the dc
		 */
		@XmlElement(name = "dc", namespace = "http://purl.org/dc/elements/1.1/")
		public Dc getDc() {
			return dc;
		}
		
		/**
		 * Imposta dc.
		 *
		 * @param dc the new dc
		 */
		public void setDc(Dc dc) {
			this.dc = dc;
		}
		
		/**
		 * Legge eu.
		 *
		 * @return the eu
		 */
		@XmlElement(name = "europeana", namespace = "http://www.europeana.eu/schemas/ese/")
		public Europeana getEu() {
			return eu;
		}
		
		/**
		 * Imposta eu.
		 *
		 * @param eu the new eu
		 */
		public void setEu(Europeana eu) {
			this.eu = eu;
		}
	}

	/**
	 * Class Dc.
	 */
	@XmlType(namespace = "http://purl.org/dc/elements/1.1/", propOrder = { "title", "date", "identifier", "extent", "description", "type", "subject",
			"creator", "contributor", "publisher", "language", "coverage", "source", "format" })
	public static class Dc {
		
		/** title. */
		private String title;
		
		/** date. */
		private String date;
		
		/** identifier. */
		private String identifier;
		
		/** extent. */
		private String extent;
		
		/** description. */
		private String description;
		
		/** type. */
		private String type;
		
		/** subject. */
		private String subject;
		
		/** creator. */
		private String creator;
		
		/** contributor. */
		private List<String> contributor;
		
		/** language. */
		private String language;
		
		/** publisher. */
		private String publisher;
		
		/** coverage. */
		private String coverage;
		
		/** source. */
		private String source;
		
		/** format. */
		private String format;
		
		/**
		 * Legge title.
		 *
		 * @return the title
		 */
		@XmlElement
		public String getTitle() {
			return title;
		}
		
		/**
		 * Imposta title.
		 *
		 * @param title the new title
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		
		/**
		 * Legge date.
		 *
		 * @return the date
		 */
		@XmlElement
		public String getDate() {
			return date;
		}
		
		/**
		 * Imposta date.
		 *
		 * @param date the new date
		 */
		public void setDate(String date) {
			this.date = date;
		}
		
		/**
		 * Legge identifier.
		 *
		 * @return the identifier
		 */
		@XmlElement
		public String getIdentifier() {
			return identifier;
		}
		
		/**
		 * Imposta identifier.
		 *
		 * @param identifier the new identifier
		 */
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}
		
		/**
		 * Legge extent.
		 *
		 * @return the extent
		 */
		@XmlElement
		public String getExtent() {
			return extent;
		}
		
		/**
		 * Imposta extent.
		 *
		 * @param extent the new extent
		 */
		public void setExtent(String extent) {
			this.extent = extent;
		}
		
		/**
		 * Legge description.
		 *
		 * @return the description
		 */
		@XmlElement
		public String getDescription() {
			return description;
		}
		
		/**
		 * Imposta description.
		 *
		 * @param description the new description
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		/**
		 * Legge type.
		 *
		 * @return the type
		 */
		@XmlElement
		public String getType() {
			return type;
		}
		
		/**
		 * Imposta type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * Legge subject.
		 *
		 * @return the subject
		 */
		@XmlElement
		public String getSubject() {
			return subject;
		}
		
		/**
		 * Imposta subject.
		 *
		 * @param subject the new subject
		 */
		public void setSubject(String subject) {
			this.subject = subject;
		}
		
		/**
		 * Legge creator.
		 *
		 * @return the creator
		 */
		@XmlElement
		public String getCreator() {
			return creator;
		}
		
		/**
		 * Imposta creator.
		 *
		 * @param creator the new creator
		 */
		public void setCreator(String creator) {
			this.creator = creator;
		}
		
		/**
		 * Legge contributor.
		 *
		 * @return the contributor
		 */
		@XmlElement
		public List<String> getContributor() {
			return contributor;
		}
		
		/**
		 * Imposta contributor.
		 *
		 * @param contributor the new contributor
		 */
		public void setContributor(List<String> contributor) {
			this.contributor = contributor;
		}
		
		/**
		 * Legge language.
		 *
		 * @return the language
		 */
		@XmlElement
		public String getLanguage() {
			return language;
		}
		
		/**
		 * Imposta language.
		 *
		 * @param language the new language
		 */
		public void setLanguage(String language) {
			this.language = language;
		}
		
		/**
		 * Legge publisher.
		 *
		 * @return the publisher
		 */
		@XmlElement
		public String getPublisher() {
			return publisher;
		}
		
		/**
		 * Imposta publisher.
		 *
		 * @param publisher the new publisher
		 */
		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}
		
		/**
		 * Legge coverage.
		 *
		 * @return the coverage
		 */
		@XmlElement
		public String getCoverage() {
			return coverage;
		}
		
		/**
		 * Imposta coverage.
		 *
		 * @param coverage the new coverage
		 */
		public void setCoverage(String coverage) {
			this.coverage = coverage;
		}
		
		/**
		 * Legge source.
		 *
		 * @return the source
		 */
		@XmlElement
		public String getSource() {
			return source;
		}
		
		/**
		 * Imposta source.
		 *
		 * @param source the new source
		 */
		public void setSource(String source) {
			this.source = source;
		}
		
		/**
		 * Legge format.
		 *
		 * @return the format
		 */
		@XmlElement
		public String getFormat() {
			return format;
		}
		
		/**
		 * Imposta format.
		 *
		 * @param format the new format
		 */
		public void setFormat(String format) {
			this.format = format;
		}
	}
	
	/**
	 * Class Europeana.
	 */
	@XmlType(namespace = "http://www.europeana.eu/schemas/ese/", propOrder = { "unstored", "object", "provider", "type", "isShownBy", "isShownAt" })
	public static class Europeana {
		
		/** unstored. */
		private String unstored;
		
		/** object. */
		private String object;
		
		/** provider. */
		private String provider;
		
		/** type. */
		private String type;
		
		/** is shown by. */
		private String isShownBy;
		
		/** is shown at. */
		private String isShownAt;
		
		/**
		 * Legge unstored.
		 *
		 * @return the unstored
		 */
		@XmlElement
		public String getUnstored() {
			return unstored;
		}
		
		/**
		 * Imposta unstored.
		 *
		 * @param unstored the new unstored
		 */
		public void setUnstored(String unstored) {
			this.unstored = unstored;
		}
		
		/**
		 * Legge object.
		 *
		 * @return the object
		 */
		@XmlElement
		public String getObject() {
			return object;
		}
		
		/**
		 * Imposta object.
		 *
		 * @param object the new object
		 */
		public void setObject(String object) {
			this.object = object;
		}
		
		/**
		 * Legge provider.
		 *
		 * @return the provider
		 */
		@XmlElement
		public String getProvider() {
			return provider;
		}
		
		/**
		 * Imposta provider.
		 *
		 * @param provider the new provider
		 */
		public void setProvider(String provider) {
			this.provider = provider;
		}
		
		/**
		 * Legge type.
		 *
		 * @return the type
		 */
		@XmlElement
		public String getType() {
			return type;
		}
		
		/**
		 * Imposta type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * Legge is shown by.
		 *
		 * @return the is shown by
		 */
		@XmlElement
		public String getIsShownBy() {
			return isShownBy;
		}
		
		/**
		 * Imposta is shown by.
		 *
		 * @param isShownBy the new is shown by
		 */
		public void setIsShownBy(String isShownBy) {
			this.isShownBy = isShownBy;
		}
		
		/**
		 * Legge is shown at.
		 *
		 * @return the is shown at
		 */
		@XmlElement
		public String getIsShownAt() {
			return isShownAt;
		}
		
		/**
		 * Imposta is shown at.
		 *
		 * @param isShownAt the new is shown at
		 */
		public void setIsShownAt(String isShownAt) {
			this.isShownAt = isShownAt;
		}
	}

	/**
	 * Class MetsDiv.
	 */
	public static class MetsDiv {
		
		/** type. */
		private String type;
		
		/** label. */
		private String label;
		
		/** dmdid. */
		private String dmdid;
		
		/** order. */
		private String order;
		
		/** div. */
		private List<MetsDiv> div;
		
		/** fptr. */
		private MetsFptr fptr;
		
		/**
		 * Legge type.
		 *
		 * @return the type
		 */
		@XmlAttribute
		public String getType() {
			return type;
		}
		
		/**
		 * Imposta type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * Legge label.
		 *
		 * @return the label
		 */
		@XmlAttribute
		public String getLabel() {
			return label;
		}
		
		/**
		 * Imposta label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		
		/**
		 * Legge fptr.
		 *
		 * @return the fptr
		 */
		@XmlElement
		public MetsFptr getFptr() {
			return fptr;
		}
		
		/**
		 * Imposta fptr.
		 *
		 * @param fptr the new fptr
		 */
		public void setFptr(MetsFptr fptr) {
			this.fptr = fptr;
		}
		
		/**
		 * Legge dmdid.
		 *
		 * @return the dmdid
		 */
		@XmlAttribute
		public String getDmdid() {
			return dmdid;
		}
		
		/**
		 * Imposta dmdid.
		 *
		 * @param dmdid the new dmdid
		 */
		public void setDmdid(String dmdid) {
			this.dmdid = dmdid;
		}
		
		/**
		 * Legge order.
		 *
		 * @return the order
		 */
		@XmlAttribute
		public String getOrder() {
			return order;
		}
		
		/**
		 * Imposta order.
		 *
		 * @param order the new order
		 */
		public void setOrder(String order) {
			this.order = order;
		}
		
		/**
		 * Legge div.
		 *
		 * @return the div
		 */
		@XmlElement
		public List<MetsDiv> getDiv() {
			return div;
		}
		
		/**
		 * Imposta div.
		 *
		 * @param div the new div
		 */
		public void setDiv(List<MetsDiv> div) {
			this.div = div;
		}
	}
	
	/**
	 * Class MetsMdWrap.
	 */
	public static class MetsMdWrap {
		
		/** md type. */
		private String mdType;
		
		/** label. */
		private String label;
		
		/** xml data. */
		private MetsXmlData xmlData;
		
		/**
		 * Legge label.
		 *
		 * @return the label
		 */
		@XmlAttribute
		public String getLabel() {
			return label;
		}
		
		/**
		 * Imposta label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		
		/**
		 * Legge md type.
		 *
		 * @return the md type
		 */
		@XmlAttribute
		public String getMdType() {
			return mdType;
		}
		
		/**
		 * Imposta md type.
		 *
		 * @param mdType the new md type
		 */
		public void setMdType(String mdType) {
			this.mdType = mdType;
		}
		
		/**
		 * Legge xml data.
		 *
		 * @return the xml data
		 */
		@XmlElement(name="xmlData")
		public MetsXmlData getXmlData() {
			return xmlData;
		}
		
		/**
		 * Imposta xml data.
		 *
		 * @param xmlData the new xml data
		 */
		public void setXmlData(MetsXmlData xmlData) {
			this.xmlData = xmlData;
		}
	}

	/**
	 * Class MetsFile.
	 */
	public static class MetsFile {
		
		/** admid. */
		private String admid;
		
		/** id. */
		private String id;
		
		/** seq. */
		private String seq;
		
		/** groupid. */
		private String groupid;
		
		/** mimetype. */
		private String mimetype;
		
		/** flocat. */
		private MetsFlocat flocat;
		
		/**
		 * Legge admid.
		 *
		 * @return the admid
		 */
		@XmlAttribute
		public String getAdmid() {
			return admid;
		}
		
		/**
		 * Imposta admid.
		 *
		 * @param admid the new admid
		 */
		public void setAdmid(String admid) {
			this.admid = admid;
		}
		
		/**
		 * Legge id.
		 *
		 * @return the id
		 */
		@XmlAttribute
		public String getId() {
			return id;
		}
		
		/**
		 * Imposta id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}
		
		/**
		 * Legge seq.
		 *
		 * @return the seq
		 */
		@XmlAttribute
		public String getSeq() {
			return seq;
		}
		
		/**
		 * Imposta seq.
		 *
		 * @param seq the new seq
		 */
		public void setSeq(String seq) {
			this.seq = seq;
		}
		
		/**
		 * Legge groupid.
		 *
		 * @return the groupid
		 */
		@XmlAttribute
		public String getGroupid() {
			return groupid;
		}
		
		/**
		 * Imposta groupid.
		 *
		 * @param groupid the new groupid
		 */
		public void setGroupid(String groupid) {
			this.groupid = groupid;
		}
		
		/**
		 * Legge mimetype.
		 *
		 * @return the mimetype
		 */
		@XmlAttribute
		public String getMimetype() {
			return mimetype;
		}
		
		/**
		 * Imposta mimetype.
		 *
		 * @param mimetype the new mimetype
		 */
		public void setMimetype(String mimetype) {
			this.mimetype = mimetype;
		}
		
		/**
		 * Legge flocat.
		 *
		 * @return the flocat
		 */
		@XmlElement
		public MetsFlocat getFlocat() {
			return flocat;
		}
		
		/**
		 * Imposta flocat.
		 *
		 * @param flocat the new flocat
		 */
		public void setFlocat(MetsFlocat flocat) {
			this.flocat = flocat;
		}
	}

	/**
	 * Class MetsDmdSec.
	 */
	public static class MetsDmdSec {
		
		/** id. */
		private String id;
		
		/** md wrap. */
		private MetsMdWrap mdWrap;
		
		/**
		 * Legge id.
		 *
		 * @return the id
		 */
		@XmlAttribute
		public String getId() {
			return id;
		}
		
		/**
		 * Imposta id.
		 *
		 * @param id the new id
		 */
		public void setId(String id) {
			this.id = id;
		}
		
		/**
		 * Legge md wrap.
		 *
		 * @return the md wrap
		 */
		@XmlElement
		public MetsMdWrap getMdWrap() {
			return mdWrap;
		}
		
		/**
		 * Imposta md wrap.
		 *
		 * @param mdWrap the new md wrap
		 */
		public void setMdWrap(MetsMdWrap mdWrap) {
			this.mdWrap = mdWrap;
		}
	}

	/**
	 * Class MetsFileGrp.
	 */
	public static class MetsFileGrp {
		
		/** use. */
		private String use;
		
		/** file. */
		private List<MetsFile> file;
		
		/**
		 * Legge file.
		 *
		 * @return the file
		 */
		@XmlElement
		public List<MetsFile> getFile() {
			return file;
		}
		
		/**
		 * Imposta file.
		 *
		 * @param file the new file
		 */
		public void setFile(List<MetsFile> file) {
			this.file = file;
		}
		
		/**
		 * Legge use.
		 *
		 * @return the use
		 */
		@XmlAttribute
		public String getUse() {
			return use;
		}
		
		/**
		 * Imposta use.
		 *
		 * @param use the new use
		 */
		public void setUse(String use) {
			this.use = use;
		}
	}
	
	/**
	 * Class MetsFileSec.
	 */
	public static class MetsFileSec {
		
		/** file grp. */
		private MetsFileGrp fileGrp;
		
		/**
		 * Legge file grp.
		 *
		 * @return the file grp
		 */
		@XmlElement
		public MetsFileGrp getFileGrp() {
			return fileGrp;
		}
		
		/**
		 * Imposta file grp.
		 *
		 * @param fileGrp the new file grp
		 */
		public void setFileGrp(MetsFileGrp fileGrp) {
			this.fileGrp = fileGrp;
		}
	}
	
	/**
	 * Class MetsFptr.
	 */
	public static class MetsFptr {
		
		/** fileid. */
		private String fileid;
		
		/**
		 * Legge fileid.
		 *
		 * @return the fileid
		 */
		@XmlAttribute
		public String getFileid() {
			return fileid;
		}
		
		/**
		 * Imposta fileid.
		 *
		 * @param fileid the new fileid
		 */
		public void setFileid(String fileid) {
			this.fileid = fileid;
		}
	}
	
	/**
	 * Class MetsFlocat.
	 */
	public static class MetsFlocat {
		
		/** href. */
		private String href;
		
		/** loctype. */
		private String loctype;
		
		/**
		 * Legge href.
		 *
		 * @return the href
		 */
		@XmlAttribute(namespace="http://www.w3.org/1999/xlink")
		public String getHref() {
			return href;
		}
		
		/**
		 * Imposta href.
		 *
		 * @param href the new href
		 */
		public void setHref(String href) {
			this.href = href;
		}
		
		/**
		 * Legge loctype.
		 *
		 * @return the loctype
		 */
		@XmlAttribute
		public String getLoctype() {
			return loctype;
		}
		
		/**
		 * Imposta loctype.
		 *
		 * @param loctype the new loctype
		 */
		public void setLoctype(String loctype) {
			this.loctype = loctype;
		}
	}
	
	/**
	 * Class MetsStructMap.
	 */
	public static class MetsStructMap {
		
		/** type. */
		private String type;
		
		/** label. */
		private String label;
		
		/** div. */
		private MetsDiv div;
		
		/**
		 * Legge type.
		 *
		 * @return the type
		 */
		@XmlAttribute
		public String getType() {
			return type.toUpperCase();
		}
		
		/**
		 * Imposta type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * Legge label.
		 *
		 * @return the label
		 */
		@XmlAttribute
		public String getLabel() {
			return label.toUpperCase();
		}
		
		/**
		 * Imposta label.
		 *
		 * @param label the new label
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		
		/**
		 * Legge div.
		 *
		 * @return the div
		 */
		@XmlElement
		public MetsDiv getDiv() {
			return div;
		}
		
		/**
		 * Imposta div.
		 *
		 * @param div the new div
		 */
		public void setDiv(MetsDiv div) {
			this.div = div;
		}
	}
	
	/**
	 * Legge mets toc sommario oggetto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return mets toc sommario oggetto
	 */
	private List<MetsDiv> getMetsTocSommarioOggetto(BigDecimal cdOggettoOriginale) {		
		return getMetsTocSommarioOggetto(cdOggettoOriginale, null);
	}
	
	/**
	 * Legge mets toc sommario oggetto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTocPadre  codice toc padre
	 * @return mets toc sommario oggetto
	 */
	private List<MetsDiv> getMetsTocSommarioOggetto(BigDecimal cdOggettoOriginale, BigDecimal cdTocPadre) {
		List<MetsDiv> toReturn = new ArrayList<MetsDiv>();
		
		List<BdlToc> tocs = null;
		if(cdTocPadre==null) {
			tocs = bdlTocRepository.findByCdOggettoOriginaleAndCdTocPadreIsNullOrderByCdTocAsc(cdOggettoOriginale);
		} else {
			tocs = bdlTocRepository.findByCdOggettoOriginaleAndCdTocPadreOrderByCdTocAsc(cdOggettoOriginale, cdTocPadre);
		}
		Integer seq = 0;
		for(BdlToc toc : tocs) {
			MetsDiv tocObj = new MetsDiv();
			tocObj.setOrder((seq++).toString());
			tocObj.setLabel(toc.getDnNome());
			if(toc.getCdImmagine()!=null) {
				tocObj.setDmdid("ADM"+toc.getCdToc());
				MetsFptr mfptr = new MetsFptr();
				mfptr.setFileid("FID"+toc.getCdImmagine());
				tocObj.setFptr(mfptr);
			}
			List<MetsDiv> children = this.getMetsTocSommarioOggetto(cdOggettoOriginale, toc.getCdToc());
			if(!children.isEmpty()) {
				tocObj.setDiv(children);
			}
			toReturn.add(tocObj);
		}
		return toReturn;
	}
}