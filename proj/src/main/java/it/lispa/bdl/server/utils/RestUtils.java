package it.lispa.bdl.server.utils;

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class RestUtils.
 */
@Component
public class RestUtils {

	/** immagini utils. */
	@Autowired
	public CaricamentoImmaginiUtils immaginiUtils;

	/** log. */
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RestUtils.class);

    /** La Costante statoOggettoOriginalePubblicato. */
    public final static List<String> statoOggettoOriginalePubblicato = new ArrayList<String>() {
    	private static final long serialVersionUID = -7626183828843663412L;
    	{
    		add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO);
    	}
    };
	
    /**
     * Legge lista filtri pubblicati.
     *
     * @param servletConfig  servlet config
     * @return lista filtri pubblicati
     */
    public List<String> getListaFiltriPubblicati(ServletConfig servletConfig) {
		if(Boolean.valueOf(servletConfig.getInitParameter("isPublicURI"))) {
			return statoOggettoOriginalePubblicato;
		}
		return null;
	}
    
    /**
     * Legge service path.
     *
     * @param servletConfig  servlet config
     * @return service path
     */
    public String getServicePath(ServletConfig servletConfig) {
    	if(Boolean.valueOf(servletConfig.getInitParameter("isPublicURI"))) {
    		return BdlSharedConstants.BDL_SERVICE_PATH_PUBLIC;
    	}
		return BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE;
    }
	
    /**
     * Legge filtro pubblicato.
     *
     * @param statiOggetto  stati oggetto
     * @return filtro pubblicato
     */
    public String getFiltroPubblicato(List<String> statiOggetto) {
		String statoOggetto;
		if(statiOggetto==null || statiOggetto.isEmpty()) {
			statoOggetto = "%";
		} else {
			statoOggetto = statiOggetto.get(0);
		}
		return statoOggetto;
	}
    
    /**
     * Legge file extensions.
     *
     * @param strExts  str exts
     * @return file extensions
     */
    public String[] getFileExtensions(String strExts) {
		return immaginiUtils.getEstensioni(strExts);
	}
    
    /**
     * Legge file name.
     *
     * @param str  str
     * @return file name
     */
    public String getFileName(String str) {
		return StringUtils.leftPad(str, 4, "0");
	}
    
    /**
     * Legge file name.
     *
     * @param num  num
     * @return file name
     */
    public String getFileName(Integer num) {
		return StringUtils.leftPad(num.toString(), 4, "0");
	}
    
    /**
     * Legge tile name.
     *
     * @param str  str
     * @return tile name
     */
    public String getTileName(String str) {
    	return "cr-"+str;
    }
    
    /**
     * Legge error image.
     *
     * @param servletConfig  servlet config
     * @param imageName  image name
     * @return error image
     */
    public ResponseBuilder getErrorImage(ServletConfig servletConfig, String imageName) {
    	File imageToRet = new File(servletConfig.getServletContext().getRealPath(imageName));
//    	log.debug("[getErrorImage] imageToRet="+imageToRet.getPath());
		if(imageToRet.exists()) {
			return Response.status(200).entity(imageToRet);
		}
    	return Response.status(404);
    }
}
