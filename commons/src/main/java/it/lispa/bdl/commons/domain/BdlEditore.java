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
 * Class BdlEditore.
 */
@Entity
@Table(name = "BDL_EDITORE")
public class BdlEditore extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice editore. */
	private BigDecimal cdEditore;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl editore.
	 */
	public BdlEditore() {
	}

	/**
	 * Istanzia un nuovo bdl editore.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlEditore(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl editore.
	 *
	 * @param cdEditore  codice editore
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlEditore(BigDecimal cdEditore, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdEditore = cdEditore;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice editore.
	 *
	 * @return the codice editore
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_EDITORE_seqgen")
	@SequenceGenerator(name="BDL_EDITORE_seqgen", sequenceName="BDL_EDITORE_SEQ", allocationSize=1)
	@Column(name = "CD_EDITORE", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdEditore() {
		return this.cdEditore;
	}

	/**
	 * Imposta codice editore.
	 *
	 * @param cdEditore the new codice editore
	 */
	public void setCdEditore(BigDecimal cdEditore) {
		this.cdEditore = cdEditore;
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
