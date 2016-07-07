package it.lispa.bdl.commons.domain;

// Generated 29-nov-2013 10.47.17 by Hibernate Tools 3.6.0

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * BdlTocLv1 generated by hbm2java.
 */
@Entity
@Table(name = "BDL_TOC")
public class BdlToc extends BdlDomainBase implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 5433084703074786892L;
	
	/** codice toc. */
	private BigDecimal cdToc;
	
	/** denominazione nome. */
	private String dnNome;
	
	/** codice immagine. */
	private BigDecimal cdImmagine;
	
	/** codice oggetto originale. */
	private BigDecimal cdOggettoOriginale;
	
	/** codice toc padre. */
	private BigDecimal cdTocPadre;
	
	/**
	 * Istanzia un nuovo bdl toc.
	 */
	public BdlToc() {
	}

	/**
	 * Istanzia un nuovo bdl toc.
	 *
	 * @param cdToc  codice toc
	 * @param dnNome  denominazione nome
	 * @param cdImmagine  codice immagine
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTocPadre  codice toc padre
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlToc(
			BigDecimal cdToc,
			String dnNome,
			BigDecimal cdImmagine,
			BigDecimal cdOggettoOriginale,
			BigDecimal cdTocPadre,
			String dsCreatoda, 
			Date dtCreatoil
	) {
		this.cdToc = cdToc;
		this.dnNome = dnNome;
		this.cdImmagine = cdImmagine;
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTocPadre = cdTocPadre;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Legge codice toc.
	 *
	 * @return the codice toc
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_TOC_seqgen")
	@SequenceGenerator(name="BDL_TOC_seqgen", sequenceName="BDL_TOC_SEQ", allocationSize=1)
	@Column(name = "CD_TOC", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdToc() {
		return this.cdToc;
	}

	/**
	 * Imposta codice toc.
	 *
	 * @param cdToc the new codice toc
	 */
	public void setCdToc(BigDecimal cdToc) {
		this.cdToc = cdToc;
	}

	/**
	 * Legge denominazione nome.
	 *
	 * @return the denominazione nome
	 */
	@Column(name = "DN_NOME", nullable = false, length = 100)
	public String getDnNome() {
		return this.dnNome;
	}

	/**
	 * Imposta denominazione nome.
	 *
	 * @param dnNome the new denominazione nome
	 */
	public void setDnNome(String dnNome) {
		this.dnNome = dnNome;
	}

	/**
	 * Legge codice immagine.
	 *
	 * @return the codice immagine
	 */
	@Column(name = "CD_IMMAGINE", nullable = true, precision = 31, scale = 0)
	public BigDecimal getCdImmagine() {
		return cdImmagine;
	}

	/**
	 * Imposta codice immagine.
	 *
	 * @param cdImmagine the new codice immagine
	 */
	public void setCdImmagine(BigDecimal cdImmagine) {
		this.cdImmagine = cdImmagine;
	}

	/**
	 * Legge codice oggetto originale.
	 *
	 * @return the codice oggetto originale
	 */
	@Column(name = "CD_OGGETTO_ORIGINALE", nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdOggettoOriginale() {
		return cdOggettoOriginale;
	}

	/**
	 * Imposta codice oggetto originale.
	 *
	 * @param cdOggettoOriginale the new codice oggetto originale
	 */
	public void setCdOggettoOriginale(BigDecimal cdOggettoOriginale) {
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Legge codice toc padre.
	 *
	 * @return the codice toc padre
	 */
	@Column(name = "CD_TOC_PADRE", nullable = true, precision = 31, scale = 0)
	public BigDecimal getCdTocPadre() {
		return cdTocPadre;
	}

	/**
	 * Imposta codice toc padre.
	 *
	 * @param cdTocPadre the new codice toc padre
	 */
	public void setCdTocPadre(BigDecimal cdTocPadre) {
		this.cdTocPadre = cdTocPadre;
	}
}
