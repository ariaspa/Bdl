package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;

/**
 * Class FormatoDTO.
 */
public class FormatoDTO extends BaseDTO implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -2635209590647574252L;
	
	/** codice formato. */
	private BigDecimal cdFormato;
	
	/** nome formato. */
	private String nomeFormato;
	
	/** regola naming. */
	private Boolean regolaNaming;
	
	/** estensione. */
	private String estensione;
	
	/** default for thumb. */
	private Boolean defaultForThumb;
	
	/** default for reader. */
	private Boolean defaultForReader;
	
	/** default for zoom. */
	private Boolean defaultForZoom;
	
	/** risoluzione. */
	private BigDecimal risoluzione;
	
	/** pixel box size. */
	private BigDecimal pixelBoxSize;
	
	/** codice tipo oggetto. */
	private BigDecimal cdTipoOggetto;
	
	/**
	 * Istanzia un nuovo formato dto.
	 */
	public FormatoDTO(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo formato dto.
	 *
	 * @param cdFormato  codice formato
	 * @param nomeFormato  nome formato
	 * @param regolaNaming  regola naming
	 * @param estensione  estensione
	 * @param defaultForThumb  default for thumb
	 * @param defaultForReader  default for reader
	 * @param defaultForZoom  default for zoom
	 * @param risoluzione  risoluzione
	 * @param pixelBoxSize  pixel box size
	 */
	public FormatoDTO(BigDecimal cdFormato, String nomeFormato, Boolean regolaNaming, String estensione, Boolean defaultForThumb, Boolean defaultForReader,Boolean defaultForZoom,
			BigDecimal risoluzione, BigDecimal pixelBoxSize) {
		this.cdFormato = cdFormato;
		this.nomeFormato = nomeFormato;
		this.regolaNaming = regolaNaming;
		this.estensione = estensione;
		this.defaultForThumb = defaultForThumb;
		this.defaultForReader = defaultForReader;
		this.defaultForZoom = defaultForZoom;
		this.risoluzione = risoluzione;
		this.pixelBoxSize = pixelBoxSize;
	}
	
	/**
	 * Legge codice formato.
	 *
	 * @return codice formato
	 */
	public BigDecimal getCdFormato() {
		return cdFormato;
	}
	
	/**
	 * Imposta codice formato.
	 *
	 * @param cdFormato nuovo codice formato
	 */
	public void setCdFormato(BigDecimal cdFormato) {
		this.cdFormato = cdFormato;
	}
	
	/**
	 * Legge nome formato.
	 *
	 * @return nome formato
	 */
	public String getNomeFormato() {
		return nomeFormato;
	}
	
	/**
	 * Imposta nome formato.
	 *
	 * @param nomeFormato nuovo nome formato
	 */
	public void setNomeFormato(String nomeFormato) {
		this.nomeFormato = nomeFormato;
	}
	
	/**
	 * Legge regola naming.
	 *
	 * @return regola naming
	 */
	public Boolean getRegolaNaming() {
		return regolaNaming;
	}
	
	/**
	 * Imposta regola naming.
	 *
	 * @param regolaNaming nuovo regola naming
	 */
	public void setRegolaNaming(Boolean regolaNaming) {
		this.regolaNaming = regolaNaming;
	}
	
	/**
	 * Legge estensione.
	 *
	 * @return estensione
	 */
	public String getEstensione() {
		return estensione;
	}
	
	/**
	 * Imposta estensione.
	 *
	 * @param estensione nuovo estensione
	 */
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}
	
	/**
	 * Legge default for thumb.
	 *
	 * @return default for thumb
	 */
	public Boolean getDefaultForThumb() {
		return defaultForThumb;
	}
	
	/**
	 * Imposta default for thumb.
	 *
	 * @param defaultForThumb nuovo default for thumb
	 */
	public void setDefaultForThumb(Boolean defaultForThumb) {
		this.defaultForThumb = defaultForThumb;
	}
	
	/**
	 * Legge default for reader.
	 *
	 * @return default for reader
	 */
	public Boolean getDefaultForReader() {
		return defaultForReader;
	}
	
	/**
	 * Imposta default for reader.
	 *
	 * @param defaultForReader nuovo default for reader
	 */
	public void setDefaultForReader(Boolean defaultForReader) {
		this.defaultForReader = defaultForReader;
	}
	
	/**
	 * Legge risoluzione.
	 *
	 * @return risoluzione
	 */
	public BigDecimal getRisoluzione() {
		return risoluzione;
	}
	
	/**
	 * Imposta risoluzione.
	 *
	 * @param risoluzione nuovo risoluzione
	 */
	public void setRisoluzione(BigDecimal risoluzione) {
		this.risoluzione = risoluzione;
	}
	
	/**
	 * Legge pixel box size.
	 *
	 * @return pixel box size
	 */
	public BigDecimal getPixelBoxSize() {
		return pixelBoxSize;
	}
	
	/**
	 * Imposta pixel box size.
	 *
	 * @param pixelBoxSize nuovo pixel box size
	 */
	public void setPixelBoxSize(BigDecimal pixelBoxSize) {
		this.pixelBoxSize = pixelBoxSize;
	}
	
	/**
	 * Legge codice tipo oggetto.
	 *
	 * @return codice tipo oggetto
	 */
	public BigDecimal getCdTipoOggetto() {
		return cdTipoOggetto;
	}
	
	/**
	 * Imposta codice tipo oggetto.
	 *
	 * @param cdTipoOggetto nuovo codice tipo oggetto
	 */
	public void setCdTipoOggetto(BigDecimal cdTipoOggetto) {
		this.cdTipoOggetto = cdTipoOggetto;
	}
	
	/**
	 * Legge default for zoom.
	 *
	 * @return default for zoom
	 */
	public Boolean getDefaultForZoom() {
		return defaultForZoom;
	}
	
	/**
	 * Imposta default for zoom.
	 *
	 * @param defaultForZoom nuovo default for zoom
	 */
	public void setDefaultForZoom(Boolean defaultForZoom) {
		this.defaultForZoom = defaultForZoom;
	}
}
