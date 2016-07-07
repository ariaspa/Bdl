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
 * Class BdlAutore.
 */
@Entity
@Table(name = "BDL_AUTORE")
public class BdlAutore extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice autore. */
	private BigDecimal cdAutore;
	
	/** descrizione descrizione. */
	private String dsDescrizione;

	/**
	 * Istanzia un nuovo bdl autore.
	 */
	public BdlAutore() {
	}

	/**
	 * Istanzia un nuovo bdl autore.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlAutore(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl autore.
	 *
	 * @param cdAutore  codice autore
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlAutore(BigDecimal cdAutore, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdAutore = cdAutore;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice autore.
	 *
	 * @return the codice autore
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_AUTORE_seqgen")
	@SequenceGenerator(name="BDL_AUTORE_seqgen", sequenceName="BDL_AUTORE_SEQ", allocationSize=1)
	@Column(name = "CD_AUTORE", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdAutore() {
		return this.cdAutore;
	}

	/**
	 * Imposta codice autore.
	 *
	 * @param cdAutore the new codice autore
	 */
	public void setCdAutore(BigDecimal cdAutore) {
		this.cdAutore = cdAutore;
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
