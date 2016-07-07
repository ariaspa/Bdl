package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlItem.
 */
@XmlRootElement
public class JSONBdlItem extends JSONBdlItemLight {
	
	private JSONBdlRef type;
	private String contentDescr;
	private String physicalDescr;
	private List<JSONBdlRef> subjects;
	private JSONBdlRef lang;
	private String issn;
	private String isbn;
	private String linkToCatalogue;
	private String notes;
	private String rights;
	private JSONBdlRef collection;
	private JSONBdlRef isChildOf;
	private List<JSONBdlRef> children;
	private char isPdfMultipagina;
	private char isRicercaOcr;
	private String segnatura;
	
	/**
	 * 
	 */
	public JSONBdlItem(BigDecimal id, JSONBdlRef type, String title,
			String date, String contentDescr, String physicalDescr,
			List<JSONBdlRef> authors, List<JSONBdlRef> subjects,
			JSONBdlRef publisher, JSONBdlRef lang, String place, String issn,
			String isbn, String linkToCatalogue, String notes, String rights,
			JSONBdlRef collection, JSONBdlCover cover, String kind,
			String digitalReader, BigDecimal relevance, JSONBdlRef isChildOf,
			List<JSONBdlRef> children, char isPdfMultipagina, char isRicercaOcr) {
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
		this.relevance = relevance;
		this.isChildOf = isChildOf;
		this.children = children;
		this.isPdfMultipagina = isPdfMultipagina;
		this.isRicercaOcr = isRicercaOcr;
	}

	public JSONBdlItem() {
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
	 * Legge type.
	 *
	 * @return type
	 */
	public JSONBdlRef getType() {
		return type;
	}
	
	/**
	 * Imposta type.
	 *
	 * @param type nuovo type
	 */
	public void setType(JSONBdlRef type) {
		this.type = type;
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
	 * Legge date.
	 *
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Imposta date.
	 *
	 * @param date nuovo date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Legge content descr.
	 *
	 * @return content descr
	 */
	public String getContentDescr() {
		return contentDescr;
	}
	
	/**
	 * Imposta content descr.
	 *
	 * @param contentDescr nuovo content descr
	 */
	public void setContentDescr(String contentDescr) {
		this.contentDescr = contentDescr;
	}
	
	/**
	 * Legge physical descr.
	 *
	 * @return physical descr
	 */
	public String getPhysicalDescr() {
		return physicalDescr;
	}
	
	/**
	 * Imposta physical descr.
	 *
	 * @param physicalDescr nuovo physical descr
	 */
	public void setPhysicalDescr(String physicalDescr) {
		this.physicalDescr = physicalDescr;
	}
	
	/**
	 * Legge authors.
	 *
	 * @return authors
	 */
	public List<JSONBdlRef> getAuthors() {
		return authors;
	}
	
	/**
	 * Imposta author.
	 *
	 * @param authors nuovo author
	 */
	public void setAuthor(List<JSONBdlRef> authors) {
		this.authors = authors;
	}
	
	/**
	 * Legge subjects.
	 *
	 * @return subjects
	 */
	public List<JSONBdlRef> getSubjects() {
		return subjects;
	}
	
	/**
	 * Imposta subject.
	 *
	 * @param subjects nuovo subject
	 */
	public void setSubject(List<JSONBdlRef> subjects) {
		this.subjects = subjects;
	}
	
	/**
	 * Legge publisher.
	 *
	 * @return publisher
	 */
	public JSONBdlRef getPublisher() {
		return publisher;
	}
	
	/**
	 * Imposta publisher.
	 *
	 * @param publisher nuovo publisher
	 */
	public void setPublisher(JSONBdlRef publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * Legge lang.
	 *
	 * @return lang
	 */
	public JSONBdlRef getLang() {
		return lang;
	}
	
	/**
	 * Imposta lang.
	 *
	 * @param lang nuovo lang
	 */
	public void setLang(JSONBdlRef lang) {
		this.lang = lang;
	}
	
	/**
	 * Legge place.
	 *
	 * @return place
	 */
	public String getPlace() {
		return place;
	}
	
	/**
	 * Imposta place.
	 *
	 * @param place nuovo place
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	/**
	 * Legge issn.
	 *
	 * @return issn
	 */
	public String getIssn() {
		return issn;
	}
	
	/**
	 * Imposta issn.
	 *
	 * @param issn nuovo issn
	 */
	public void setIssn(String issn) {
		this.issn = issn;
	}
	
	/**
	 * Legge isbn.
	 *
	 * @return isbn
	 */
	public String getIsbn() {
		return isbn;
	}
	
	/**
	 * Imposta isbn.
	 *
	 * @param isbn nuovo isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	/**
	 * Legge link to catalogue.
	 *
	 * @return link to catalogue
	 */
	public String getLinkToCatalogue() {
		return linkToCatalogue;
	}
	
	/**
	 * Imposta link to catalogue.
	 *
	 * @param linkToCatalogue nuovo link to catalogue
	 */
	public void setLinkToCatalogue(String linkToCatalogue) {
		this.linkToCatalogue = linkToCatalogue;
	}
	
	/**
	 * Legge notes.
	 *
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Imposta notes.
	 *
	 * @param notes nuovo notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * Legge rights.
	 *
	 * @return rights
	 */
	public String getRights() {
		return rights;
	}
	
	/**
	 * Imposta rights.
	 *
	 * @param rights nuovo rights
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	/**
	 * Legge collection.
	 *
	 * @return collection
	 */
	public JSONBdlRef getCollection() {
		return collection;
	}
	
	/**
	 * Imposta collection.
	 *
	 * @param collection nuovo collection
	 */
	public void setCollection(JSONBdlRef collection) {
		this.collection = collection;
	}
	
	/**
	 * Legge cover.
	 *
	 * @return cover
	 */
	public JSONBdlCover getCover() {
		return cover;
	}
	
	/**
	 * Imposta cover.
	 *
	 * @param cover nuovo cover
	 */
	public void setCover(JSONBdlCover cover) {
		this.cover = cover;
	}
	
	/**
	 * Legge kind.
	 *
	 * @return kind
	 */
	public String getKind() {
		return kind;
	}
	
	/**
	 * Imposta kind.
	 *
	 * @param kind nuovo kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	/**
	 * Legge digital reader.
	 *
	 * @return digital reader
	 */
	public String getDigitalReader() {
		return digitalReader;
	}
	
	/**
	 * Imposta digital reader.
	 *
	 * @param digitalReader nuovo digital reader
	 */
	public void setDigitalReader(String digitalReader) {
		this.digitalReader = digitalReader;
	}
	
	/**
	 * Legge relevance.
	 *
	 * @return relevance
	 */
	public BigDecimal getRelevance() {
		return relevance;
	}
	
	/**
	 * Imposta relevance.
	 *
	 * @param relevance nuovo relevance
	 */
	public void setRelevance(BigDecimal relevance) {
		this.relevance = relevance;
	}
	
	/**
	 * Legge controlla se child of.
	 *
	 * @return controlla se child of
	 */
	public JSONBdlRef getIsChildOf() {
		return isChildOf;
	}
	
	/**
	 * Imposta controlla se child of.
	 *
	 * @param isChildOf nuovo controlla se child of
	 */
	public void setIsChildOf(JSONBdlRef isChildOf) {
		this.isChildOf = isChildOf;
	}
	
	/**
	 * Legge children.
	 *
	 * @return children
	 */
	public List<JSONBdlRef> getChildren() {
		return children;
	}
	
	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<JSONBdlRef> children) {
		this.children = children;
	}
	
	public char getIsPdfMultipagina() {
		return isPdfMultipagina;
	}
	public void setIsPdfMultipagina(char isPdfMultipagina) {
		this.isPdfMultipagina = isPdfMultipagina;
	}

	public char getIsRicercaOcr() {
		return isRicercaOcr;
	}
	public void setIsRicercaOcr(char isRicercaOcr) {
		this.isRicercaOcr = isRicercaOcr;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
}