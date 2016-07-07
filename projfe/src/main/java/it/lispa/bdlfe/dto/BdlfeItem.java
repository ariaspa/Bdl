package it.lispa.bdlfe.dto;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BdlfeItem extends BdlfeItemLight {
	
	private BdlfeRef type;
	private String contentDescr;
	private String physicalDescr;
	private List<BdlfeRef> subjects;
	private BdlfeRef lang;
	private String issn;
	private String isbn;
	private String linkToCatalogue;
	private String notes;
	private String rights;
	private BdlfeRef collection;
	private BdlfeRef isChildOf;
	private List<BdlfeRef> children;
	private String segnatura;

	/**
	 * 
	 */
	public BdlfeItem() {
	}

    /**
     * 
     * @param id
     * @param type
     * @param title
     * @param date
     * @param contentDescr
     * @param physicalDescr
     * @param authors
     * @param subjects
     * @param publisher
     * @param lang
     * @param place
     * @param issn
     * @param isbn
     * @param linkToCatalogue
     * @param notes
     * @param rights
     * @param collection
     * @param cover
     * @param kind
     * @param digitalReader
     * @param isChildOf
     * @param children
     */
	public BdlfeItem(BigDecimal id, BdlfeRef type, String title, String date,
			String contentDescr, String physicalDescr, List<BdlfeRef> authors,
			List<BdlfeRef> subjects, BdlfeRef publisher, BdlfeRef lang,
			String place, String issn, String isbn, String linkToCatalogue,
			String notes, String rights, BdlfeRef collection, BdlfeCover cover,
			String kind, String digitalReader,
			BdlfeRef isChildOf, List<BdlfeRef> children) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.date = date;
		this.contentDescr = contentDescr;
		this.physicalDescr = physicalDescr;
		this.authors = authors;
		this.subjects = subjects;
		this.publisher = publisher;
		this.lang = lang;
		this.place = place;
		this.issn = issn;
		this.isbn = isbn;
		this.linkToCatalogue = linkToCatalogue;
		this.notes = notes;
		this.rights = rights;
		this.collection = collection;
		this.cover = cover;
		this.kind = kind;
		this.digitalReader = digitalReader;
		this.isChildOf = isChildOf;
		this.children = children;
	}

	/**
	 * @return the type
	 */
	public BdlfeRef getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(BdlfeRef type) {
		this.type = type;
	}

	/**
	 * @return the contentDescr
	 */
	public String getContentDescr() {
		return contentDescr;
	}

	/**
	 * @param contentDescr the contentDescr to set
	 */
	public void setContentDescr(String contentDescr) {
		this.contentDescr = contentDescr;
	}

	/**
	 * @return the physicalDescr
	 */
	public String getPhysicalDescr() {
		return physicalDescr;
	}

	/**
	 * @param physicalDescr the physicalDescr to set
	 */
	public void setPhysicalDescr(String physicalDescr) {
		this.physicalDescr = physicalDescr;
	}

	/**
	 * @return the subjects
	 */
	public List<BdlfeRef> getSubjects() {
		return subjects;
	}

	/**
	 * @param subjects the subjects to set
	 */
	public void setSubjects(List<BdlfeRef> subjects) {
		this.subjects = subjects;
	}

	/**
	 * @return the lang
	 */
	public BdlfeRef getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(BdlfeRef lang) {
		this.lang = lang;
	}

	/**
	 * @return the issn
	 */
	public String getIssn() {
		return issn;
	}

	/**
	 * @param issn the issn to set
	 */
	public void setIssn(String issn) {
		this.issn = issn;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the linkToCatalogue
	 */
	public String getLinkToCatalogue() {
		return linkToCatalogue;
	}

	/**
	 * @param linkToCatalogue the linkToCatalogue to set
	 */
	public void setLinkToCatalogue(String linkToCatalogue) {
		this.linkToCatalogue = linkToCatalogue;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * @param rights the rights to set
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}

	/**
	 * @return the collection
	 */
	public BdlfeRef getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(BdlfeRef collection) {
		this.collection = collection;
	}

	/**
	 * @return the isChildOf
	 */
	public BdlfeRef getIsChildOf() {
		return isChildOf;
	}

	/**
	 * @param isChildOf the isChildOf to set
	 */
	public void setIsChildOf(BdlfeRef isChildOf) {
		this.isChildOf = isChildOf;
	}

	/**
	 * @return the children
	 */
	public List<BdlfeRef> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<BdlfeRef> children) {
		this.children = children;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
}