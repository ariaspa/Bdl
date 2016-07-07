package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlCollection.
 */
@XmlRootElement
public class JSONBdlCollection extends JSONBdlCollectionLight {
	
	private JSONBdlItem mainItem;
	
	/**
	 * 
	 */
	public JSONBdlCollection() {
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
	 * @param mainItem  main item
	 */
	public JSONBdlCollection(BigDecimal id, JSONBdlRef institute,
			String title, JSONBdlRef area, String geoCoverage,
			String period, String descrIT, String descrEN, Integer numItems, JSONBdlItem mainItem) {
		this.id = id;
		this.institute = institute;
		this.title = title;
		this.area = area;
		this.geoCoverage = geoCoverage;
		this.period = period;
		this.descrIT = descrIT;
		this.descrEN = descrEN;
		this.numItems = numItems;
		this.mainItem = mainItem;
	}
	

	/**
	 * Legge main item.
	 *
	 * @return main item
	 */
	public JSONBdlItem getMainItem() {
		return mainItem;
	}
	
	/**
	 * Imposta main item.
	 *
	 * @param mainItem nuovo main item
	 */
	public void setMainItem(JSONBdlItem mainItem) {
		this.mainItem = mainItem;
	}
}