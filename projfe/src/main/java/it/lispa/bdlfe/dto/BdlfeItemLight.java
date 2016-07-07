package it.lispa.bdlfe.dto;

import it.lispa.bdlfe.utils.BdlfeConstants;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BdlfeItemLight {
	
	protected static final String IMAGE_FORMAT_THUMB = "thumb";
	protected static final String IMAGE_FORMAT_READER = "reader";
	protected static final String IMAGE_FORMAT_ZOOM = "zoom";
	
	protected BigDecimal id;
	protected String title;
	protected String date;
	protected List<BdlfeRef> authors;
	protected BdlfeRef publisher;
	protected String place;
	protected BdlfeCover cover;
	protected String kind;
	protected String digitalReader;
	protected BigDecimal relevance;

	/**
	 * 
	 */
	public BdlfeItemLight() {
	}

	/**
	 * 
	 * @param id
	 * @param title
	 * @param date
	 * @param authors
	 * @param publisher
	 * @param place
	 * @param cover
	 * @param kind
	 * @param digitalReader
	 * @param relevance
	 */
	public BdlfeItemLight(BigDecimal id, String title, String date,
			List<BdlfeRef> authors, BdlfeRef publisher,
			String place, BdlfeCover cover,
			String kind, String digitalReader, BigDecimal relevance) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.authors = authors;
		this.publisher = publisher;
		this.place = place;
		this.cover = cover;
		this.kind = kind;
		this.digitalReader = digitalReader;
		this.relevance = relevance;
	}

	/////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	
	/**
	 * Fornisce l'URL da cui recuperare la IMAGE_FORMAT_THUMB per la cover dell'OggettoOriginale.
	 * @param backendUrl
	 * @return
	 */
	public String getDefaultImageThumbUrl(String backendUrl) {
		if(this.cover==null) {
			return null;
		}
		return backendUrl + "/srv/item/" + this.cover.getCodiceOO().toString() + "/images/" + IMAGE_FORMAT_THUMB + "/" + this.cover.getId();
	}
	
	/**
	 * Fornisce l'URL da cui recuperare la IMAGE_FORMAT_READER per la cover dell'OggettoOriginale.
	 * @param backendUrl
	 * @return
	 */
	public String getDefaultImageReaderUrl(String backendUrl) {
		if(this.cover==null) {
			return null;
		}
		return backendUrl + "/srv/item/" + this.cover.getCodiceOO().toString() + "/images/" + IMAGE_FORMAT_READER + "/" + this.cover.getId();
	}
	
	/**
	 * Fornisce l'URL da cui recuperare la IMAGE_FORMAT_ZOOM per la cover dell'OggettoOriginale.
	 * @param backendUrl
	 * @return
	 */
	public String getDefaultImageZoomUrl(String backendUrl) {
		if(this.cover==null) {
			return null;
		}
		return backendUrl + "/srv/item/" + this.cover.getCodiceOO().toString() + "/images/" + IMAGE_FORMAT_ZOOM + "/" + this.cover.getId();
	}
	
	/**
	 * Fornisce l'URL per AudioPlayer/MAPREADER/BOOKREADER.
	 * @param audioplayerUrl
	 * @param mapreaderUrl
	 * @param bookreaderUrl
	 * @return
	 */
	public String getDigitalReaderUrl(String audioplayerUrl, String mapreaderUrl, String bookreaderUrl) {
		String urlToRet = null;
		if(this.digitalReader.equals(BdlfeConstants.BDLFE_DIGITAL_READER_AUDIO)) {
			/* AudioPlayer */
			urlToRet = audioplayerUrl + this.id;
		} else if(this.digitalReader.equals(BdlfeConstants.BDLFE_DIGITAL_READER_MAP)) {
			/* MapReader */
			urlToRet = mapreaderUrl + this.id;
		} else {
			/* BookReader */
			urlToRet = bookreaderUrl + this.id;
		}
		return urlToRet;
	}
	
	/**
	 * Fornisce l'URL da cui recuperare il PDF dell'OggettoOriginale.
	 * @param backendUrl
	 * @return
	 */
	public String getPdfUrl(String backendUrl) {
		return backendUrl + "/srv/item/" + this.getId().toString() + "/pdf";
	}
	
	/**
	 * Fornisce l'URL da cui recuperare il METS dell'OggettoOriginale.
	 * @param backendUrl
	 * @return
	 */
	public String getMetsUrl(String backendUrl) {
		return backendUrl + "/srv/item/" + this.getId().toString() + "/mets";
	}
	
	/////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	/**
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the authors
	 */
	public List<BdlfeRef> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<BdlfeRef> authors) {
		this.authors = authors;
	}
	
	/**
	 * @return the publisher
	 */
	public BdlfeRef getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(BdlfeRef publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the cover
	 */
	public BdlfeCover getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void setCover(BdlfeCover cover) {
		this.cover = cover;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the digitalReader
	 */
	public String getDigitalReader() {
		return digitalReader;
	}

	/**
	 * @param digitalReader the digitalReader to set
	 */
	public void setDigitalReader(String digitalReader) {
		this.digitalReader = digitalReader;
	}

	/**
	 * @return the relevance
	 */
	public BigDecimal getRelevance() {
		return relevance;
	}

	/**
	 * @param relevance the relevance to set
	 */
	public void setRelevance(BigDecimal relevance) {
		this.relevance = relevance;
	}
}