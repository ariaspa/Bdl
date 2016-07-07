package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlItem.
 */
@XmlRootElement
public class JSONBdlItemLight {
	
	protected BigDecimal id;
	protected String title;
	protected String date;
	protected List<JSONBdlRef> authors;
	protected JSONBdlRef publisher;
	protected String place;
	protected JSONBdlCover cover;
	protected String kind;
	protected String digitalReader;
	protected BigDecimal relevance;
	
	/**
	 * 
	 */
	public JSONBdlItemLight() {
	}
	/**
	 * 
	 */
	public JSONBdlItemLight(BigDecimal id, String title, String date,
			List<JSONBdlRef> authors, JSONBdlRef publisher, String place,
			JSONBdlCover cover, String kind, String digitalReader, BigDecimal relevance) {
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
	public List<JSONBdlRef> getAuthors() {
		return authors;
	}
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<JSONBdlRef> authors) {
		this.authors = authors;
	}
	/**
	 * @return the publisher
	 */
	public JSONBdlRef getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(JSONBdlRef publisher) {
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
	public JSONBdlCover getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(JSONBdlCover cover) {
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