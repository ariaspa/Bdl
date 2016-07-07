package it.lispa.bdl.commons.domain;

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
 * Class BdlSoggettoProd.
 */
@Entity
@Table(name = "BDL_SOGGETTO_PROD")
public class BdlSoggettoProd extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice soggetto prod. */
	private BigDecimal cdSoggettoProd;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl soggetto prod.
	 */
	public BdlSoggettoProd() {
	}

	/**
	 * Istanzia un nuovo bdl soggetto prod.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlSoggettoProd(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl soggetto prod.
	 *
	 * @param cdSoggettoProd  codice soggetto prod
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlSoggettoProd(BigDecimal cdSoggettoProd, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdSoggettoProd = cdSoggettoProd;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice soggetto prod.
	 *
	 * @return the codice soggetto prod
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_SOGGETTO_PROD_seqgen")
	@SequenceGenerator(name="BDL_SOGGETTO_PROD_seqgen", sequenceName="BDL_SOGGETTO_PROD_SEQ", allocationSize=1)
	@Column(name = "CD_SOGGETTO_PROD", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdSoggettoProd() {
		return this.cdSoggettoProd;
	}

	/**
	 * Imposta codice soggetto prod.
	 *
	 * @param cdSoggettoProd the new codice soggetto prod
	 */
	public void setCdSoggettoProd(BigDecimal cdSoggettoProd) {
		this.cdSoggettoProd = cdSoggettoProd;
	}

	/**
	 * Legge descrizione descrizione.
	 *
	 * @return the descrizione descrizione
	 */
	@Column(name = "DS_DESCRIZIONE", nullable = false, length = 250)
	public String getDsDescrizione() {
		return this.dsDescrizione;
	}

	/**
	 * Imposta descrizione descrizione.
	 *
	 * @param dnNome the new descrizione descrizione
	 */
	public void setDsDescrizione(String dnNome) {
		this.dsDescrizione = dnNome;
	}
}
