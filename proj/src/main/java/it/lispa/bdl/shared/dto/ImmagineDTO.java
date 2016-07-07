package it.lispa.bdl.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class ImmagineDTO.
 */
@XmlRootElement
public class ImmagineDTO implements Serializable {

	/**
	 * Interface ImmagineDTOProperties.
	 */
	public interface ImmagineDTOProperties extends PropertyAccess<ImmagineDTO> {
		
		/**
		 * Codice immagine.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<ImmagineDTO> cdImmagine();
		
		/**
		 * Nome immagine.
		 *
		 * @return label provider
		 */
		LabelProvider<ImmagineDTO> nomeImmagine();
		
		/**
		 * Path thumb.
		 *
		 * @return label provider
		 */
		LabelProvider<ImmagineDTO> pathThumb();
		
		/**
		 * Path reader.
		 *
		 * @return label provider
		 */
		LabelProvider<ImmagineDTO> pathReader();
	} 
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 325546154063220632L;
	
	/** codice immagine. */
	private BigDecimal cdImmagine;
	
	/** codice oggetto originale. */
	private BigDecimal cdOggettoOriginale;
	
	/** nome immagine. */
	private String nomeImmagine;
	
	/** nr px larghezza thumb. */
	private Integer nrPxLarghezzaThumb;
	
	/** nr px altezza thumb. */
	private Integer nrPxAltezzaThumb;
	
	/** nr px larghezza reader. */
	private Integer nrPxLarghezzaReader;
	
	/** nr px altezza reader. */
	private Integer nrPxAltezzaReader;
	
	/** nr px larghezza zoom. */
	private Integer nrPxLarghezzaZoom;
	
	/** nr px altezza zoom. */
	private Integer nrPxAltezzaZoom;
	
	/** principale. */
	private Boolean principale;

	/** path thumb. */
	private String pathThumb;
	
	/** path reader. */
	private String pathReader;	
	
	/** path zoom. */
	private String pathZoom;
	
	/** base thumb path. */
	private String baseThumbPath;
	
	/** base reader path. */
	private String baseReaderPath;
	
	/** base zoom path. */
	private String baseZoomPath;


	/**
	 * Istanzia un nuovo immagine dto.
	 */
	public ImmagineDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo immagine dto.
	 *
	 * @param cdImmagine  codice immagine
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param nomeImmagine  nome immagine
	 * @param nrPxLarghezzaThumb  nr px larghezza thumb
	 * @param nrPxAltezzaThumb  nr px altezza thumb
	 * @param nrPxLarghezzaReader  nr px larghezza reader
	 * @param nrPxAltezzaReader  nr px altezza reader
	 * @param nrPxLarghezzaZoom  nr px larghezza zoom
	 * @param nrPxAltezzaZoom  nr px altezza zoom
	 * @param principale  principale
	 * @param pathThumb  path thumb
	 * @param pathReader  path reader
	 * @param pathZoom  path zoom
	 * @param baseThumbPath  base thumb path
	 * @param baseReaderPath  base reader path
	 * @param baseZoomPath  base zoom path
	 */
	public ImmagineDTO(BigDecimal cdImmagine, BigDecimal cdOggettoOriginale, String nomeImmagine, Integer nrPxLarghezzaThumb, Integer nrPxAltezzaThumb,
			Integer nrPxLarghezzaReader, Integer nrPxAltezzaReader, Integer nrPxLarghezzaZoom, Integer nrPxAltezzaZoom, 
			Boolean principale, String pathThumb, String pathReader, String pathZoom, String baseThumbPath, String baseReaderPath, String baseZoomPath) {
		this.cdImmagine = cdImmagine;
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.nomeImmagine = nomeImmagine;
		this.nrPxLarghezzaThumb = nrPxLarghezzaThumb;
		this.nrPxAltezzaThumb = nrPxAltezzaThumb;
		this.nrPxLarghezzaReader = nrPxLarghezzaReader;
		this.nrPxAltezzaReader = nrPxAltezzaReader;
		this.nrPxLarghezzaZoom = nrPxLarghezzaZoom;
		this.nrPxAltezzaZoom = nrPxAltezzaZoom;
		this.principale = principale;
		this.pathThumb = pathThumb;
		this.pathReader = pathReader;
		this.baseThumbPath = baseThumbPath;
		this.baseReaderPath = baseReaderPath;
		this.pathZoom = pathZoom;
		this.baseZoomPath = baseZoomPath;
	}

	/**
	 * Legge path thumb uri.
	 *
	 * @return path thumb uri
	 */
	public SafeUri getPathThumbUri() {
		return UriUtils.fromString(pathThumb);
	}
	
	/**
	 * Legge path reader uri.
	 *
	 * @return path reader uri
	 */
	public SafeUri getPathReaderUri() {
		return UriUtils.fromString(pathReader);
	}

	/**
	 * Legge short name.
	 *
	 * @return short name
	 */
	public String getShortName() {
		return Format.ellipse(nomeImmagine, 15);
	}

	/**
	 * Legge codice immagine.
	 *
	 * @return codice immagine
	 */
	public BigDecimal getCdImmagine() {
		return cdImmagine;
	}

	/**
	 * Imposta codice immagine.
	 *
	 * @param cdImmagine nuovo codice immagine
	 */
	public void setCdImmagine(BigDecimal cdImmagine) {
		this.cdImmagine = cdImmagine;
	}

	/**
	 * Legge codice oggetto originale.
	 *
	 * @return codice oggetto originale
	 */
	public BigDecimal getCdOggettoOriginale() {
		return cdOggettoOriginale;
	}

	/**
	 * Imposta codice oggetto originale.
	 *
	 * @param cdOggettoOriginale nuovo codice oggetto originale
	 */
	public void setCdOggettoOriginale(BigDecimal cdOggettoOriginale) {
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Legge nome immagine.
	 *
	 * @return nome immagine
	 */
	public String getNomeImmagine() {
		return nomeImmagine;
	}

	/**
	 * Imposta nome immagine.
	 *
	 * @param nomeImmagine nuovo nome immagine
	 */
	public void setNomeImmagine(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	/**
	 * Legge nr px larghezza thumb.
	 *
	 * @return nr px larghezza thumb
	 */
	public Integer getNrPxLarghezzaThumb() {
		return nrPxLarghezzaThumb;
	}

	/**
	 * Imposta nr px larghezza thumb.
	 *
	 * @param nrPxLarghezzaThumb nuovo nr px larghezza thumb
	 */
	public void setNrPxLarghezzaThumb(Integer nrPxLarghezzaThumb) {
		this.nrPxLarghezzaThumb = nrPxLarghezzaThumb;
	}

	/**
	 * Legge nr px altezza thumb.
	 *
	 * @return nr px altezza thumb
	 */
	public Integer getNrPxAltezzaThumb() {
		return nrPxAltezzaThumb;
	}

	/**
	 * Imposta nr px altezza thumb.
	 *
	 * @param nrPxAltezzaThumb nuovo nr px altezza thumb
	 */
	public void setNrPxAltezzaThumb(Integer nrPxAltezzaThumb) {
		this.nrPxAltezzaThumb = nrPxAltezzaThumb;
	}

	/**
	 * Legge nr px larghezza reader.
	 *
	 * @return nr px larghezza reader
	 */
	public Integer getNrPxLarghezzaReader() {
		return nrPxLarghezzaReader;
	}

	/**
	 * Imposta nr px larghezza reader.
	 *
	 * @param nrPxLarghezzaReader nuovo nr px larghezza reader
	 */
	public void setNrPxLarghezzaReader(Integer nrPxLarghezzaReader) {
		this.nrPxLarghezzaReader = nrPxLarghezzaReader;
	}

	/**
	 * Legge nr px altezza reader.
	 *
	 * @return nr px altezza reader
	 */
	public Integer getNrPxAltezzaReader() {
		return nrPxAltezzaReader;
	}

	/**
	 * Imposta nr px altezza reader.
	 *
	 * @param nrPxAltezzaReader nuovo nr px altezza reader
	 */
	public void setNrPxAltezzaReader(Integer nrPxAltezzaReader) {
		this.nrPxAltezzaReader = nrPxAltezzaReader;
	}

	/**
	 * Legge principale.
	 *
	 * @return principale
	 */
	public Boolean getPrincipale() {
		return principale;
	}

	/**
	 * Imposta principale.
	 *
	 * @param principale nuovo principale
	 */
	public void setPrincipale(Boolean principale) {
		this.principale = principale;
	}

	/**
	 * Legge path reader.
	 *
	 * @return path reader
	 */
	public String getPathReader() {
		return pathReader;
	}

	/**
	 * Imposta path reader.
	 *
	 * @param path nuovo path reader
	 */
	public void setPathReader(String path) {
		this.pathReader = path;
	}
	
	/**
	 * Legge path thumb.
	 *
	 * @return path thumb
	 */
	public String getPathThumb() {
		return pathThumb;
	}

	/**
	 * Imposta path thumb.
	 *
	 * @param path nuovo path thumb
	 */
	public void setPathThumb(String path) {
		this.pathThumb = path;
	}

	/**
	 * Legge base thumb path.
	 *
	 * @return base thumb path
	 */
	public String getBaseThumbPath() {
		return baseThumbPath;
	}

	/**
	 * Imposta base thumb path.
	 *
	 * @param baseThumbPath nuovo base thumb path
	 */
	public void setBaseThumbPath(String baseThumbPath) {
		this.baseThumbPath = baseThumbPath;
	}

	/**
	 * Legge base reader path.
	 *
	 * @return base reader path
	 */
	public String getBaseReaderPath() {
		return baseReaderPath;
	}

	/**
	 * Imposta base reader path.
	 *
	 * @param baseReaderPath nuovo base reader path
	 */
	public void setBaseReaderPath(String baseReaderPath) {
		this.baseReaderPath = baseReaderPath;
	}

	/**
	 * Legge nr px larghezza zoom.
	 *
	 * @return nr px larghezza zoom
	 */
	public Integer getNrPxLarghezzaZoom() {
		return nrPxLarghezzaZoom;
	}

	/**
	 * Imposta nr px larghezza zoom.
	 *
	 * @param nrPxLarghezzaZoom nuovo nr px larghezza zoom
	 */
	public void setNrPxLarghezzaZoom(Integer nrPxLarghezzaZoom) {
		this.nrPxLarghezzaZoom = nrPxLarghezzaZoom;
	}

	/**
	 * Legge nr px altezza zoom.
	 *
	 * @return nr px altezza zoom
	 */
	public Integer getNrPxAltezzaZoom() {
		return nrPxAltezzaZoom;
	}

	/**
	 * Imposta nr px altezza zoom.
	 *
	 * @param nrPxAltezzaZoom nuovo nr px altezza zoom
	 */
	public void setNrPxAltezzaZoom(Integer nrPxAltezzaZoom) {
		this.nrPxAltezzaZoom = nrPxAltezzaZoom;
	}

	/**
	 * Legge path zoom.
	 *
	 * @return path zoom
	 */
	public String getPathZoom() {
		return pathZoom;
	}

	/**
	 * Imposta path zoom.
	 *
	 * @param pathZoom nuovo path zoom
	 */
	public void setPathZoom(String pathZoom) {
		this.pathZoom = pathZoom;
	}

	/**
	 * Legge base zoom path.
	 *
	 * @return base zoom path
	 */
	public String getBaseZoomPath() {
		return baseZoomPath;
	}

	/**
	 * Imposta base zoom path.
	 *
	 * @param baseZoomPath nuovo base zoom path
	 */
	public void setBaseZoomPath(String baseZoomPath) {
		this.baseZoomPath = baseZoomPath;
	}


}
