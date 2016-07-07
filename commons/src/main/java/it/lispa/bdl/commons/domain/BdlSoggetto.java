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
 * Class BdlSoggetto.
 */
@Entity
@Table(name = "BDL_SOGGETTO")
public class BdlSoggetto extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice soggetto. */
	private BigDecimal cdSoggetto;
	
	/** descrizione descrizione. */
	private String dsDescrizione;

	/**
	 * Istanzia un nuovo bdl soggetto.
	 */
	public BdlSoggetto() {
	}

	/**
	 * Istanzia un nuovo bdl soggetto.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlSoggetto(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl soggetto.
	 *
	 * @param cdSoggetto  codice soggetto
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlSoggetto(BigDecimal cdSoggetto, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdSoggetto = cdSoggetto;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice soggetto.
	 *
	 * @return the codice soggetto
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_SOGGETTO_seqgen")
	@SequenceGenerator(name="BDL_SOGGETTO_seqgen", sequenceName="BDL_SOGGETTO_SEQ", allocationSize=1)
	@Column(name = "CD_SOGGETTO", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdSoggetto() {
		return this.cdSoggetto;
	}

	/**
	 * Imposta codice soggetto.
	 *
	 * @param cdSoggetto the new codice soggetto
	 */
	public void setCdSoggetto(BigDecimal cdSoggetto) {
		this.cdSoggetto = cdSoggetto;
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
