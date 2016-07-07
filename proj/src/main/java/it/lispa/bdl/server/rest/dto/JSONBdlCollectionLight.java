package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlCollection.
 */
@XmlRootElement
public class JSONBdlCollectionLight {
	
	protected BigDecimal id;
	protected JSONBdlRef institute;
	protected String title;
	protected JSONBdlRef area;
	protected String geoCoverage;
	protected String period;
	protected String descrIT;
	protected String descrEN;
	protected Integer numItems;
	
	/**
	 * 
	 */
	public JSONBdlCollectionLight() {
	}

	/**
	 * Istanzia un nuovo JSON bdl collection.
	 *
	 * @param id  id
	 * @param institute  institute
	 * @param title  title
	 * @param area  area
	 * @param geoCoverage  geo coverage
	 * @param period  period
	 * @param descrIT  descr it
	 * @param descrEN  descr en
	 * @param numItems  num items
	 */
	public JSONBdlCollectionLight(BigDecimal id, JSONBdlRef institute,
			String title, JSONBdlRef area, String geoCoverage,
			String period, String descrIT, String descrEN, Integer numItems) {
		this.id = id;
		this.institute = institute;
		this.title = title;
		this.area = area;
		this.geoCoverage = geoCoverage;
		this.period = period;
		this.descrIT = descrIT;
		this.descrEN = descrEN;
		this.numItems = numItems;
	}
	
	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public BigDecimal getId() {
		return id;
	}
	
	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	/**
	 * Legge institute.
	 *
	 * @return institute
	 */
	public JSONBdlRef getInstitute() {
		return institute;
	}
	
	/**
	 * Imposta institute.
	 *
	 * @param institute nuovo institute
	 */
	public void setInstitute(JSONBdlRef institute) {
		this.institute = institute;
	}
	
	/**
	 * Legge title.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Imposta title.
	 *
	 * @param title nuovo title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Legge area.
	 *
	 * @return area
	 */
	public JSONBdlRef getArea() {
		return area;
	}
	
	/**
	 * Imposta area.
	 *
	 * @param area nuovo area
	 */
	public void setArea(JSONBdlRef area) {
		this.area = area;
	}
	
	/**
	 * Legge geo coverage.
	 *
	 * @return geo coverage
	 */
	public String getGeoCoverage() {
		return geoCoverage;
	}
	
	/**
	 * Imposta geo coverage.
	 *
	 * @param geoCoverage nuovo geo coverage
	 */
	public void setGeoCoverage(String geoCoverage) {
		this.geoCoverage = geoCoverage;
	}
	
	/**
	 * Legge period.
	 *
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}
	
	/**
	 * Imposta period.
	 *
	 * @param period nuovo period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	
	/**
	 * Legge descr it.
	 *
	 * @return descr it
	 */
	public String getDescrIT() {
		return descrIT;
	}
	
	/**
	 * Imposta descr it.
	 *
	 * @param descrIT nuovo descr it
	 */
	public void setDescrIT(String descrIT) {
		this.descrIT = descrIT;
	}
	
	/**
	 * Legge descr en.
	 *
	 * @return descr en
	 */
	public String getDescrEN() {
		return descrEN;
	}
	
	/**
	 * Imposta descr en.
	 *
	 * @param descrEN nuovo descr en
	 */
	public void setDescrEN(String descrEN) {
		this.descrEN = descrEN;
	}

	/**
	 * Legge num items.
	 *
	 * @return num items
	 */
	public Integer getNumItems() {
		return numItems;
	}
	
	/**
	 * Imposta num items.
	 *
	 * @param numItems nuovo num items
	 */
	public void setNumItems(Integer numItems) {
		this.numItems = numItems;
	}
}