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
 * Class BdlQualificatoreData.
 */
@Entity
@Table(name = "BDL_QUALIFICATORE_DATA")
public class BdlQualificatoreData extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice qualificatore data. */
	private BigDecimal cdQualificatoreData;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/** descrizione validator. */
	private String dsValidator;
	
	/**
	 * Istanzia un nuovo bdl qualificatore data.
	 */
	public BdlQualificatoreData() {
	}

	/**
	 * Istanzia un nuovo bdl qualificatore data.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsValidator  descrizione validator
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlQualificatoreData(String dsDescrizione, String dsValidator, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsValidator = dsValidator;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}
	
	/**
	 * Istanzia un nuovo bdl qualificatore data.
	 *
	 * @param cdQualificatoreData  codice qualificatore data
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsValidator  descrizione validator
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlQualificatoreData(BigDecimal cdQualificatoreData, String dsDescrizione, String dsValidator, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdQualificatoreData = cdQualificatoreData;
		this.dsDescrizione = dsDescrizione;
		this.dsValidator = dsValidator;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice qualificatore data.
	 *
	 * @return the codice qualificatore data
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_QUALIFICATORE_DATA_seqgen")
	@SequenceGenerator(name="BDL_QUALIFICATORE_DATA_seqgen", sequenceName="BDL_QUALIFICATORE_DATA_SEQ", allocationSize=1)
	@Column(name = "CD_QUALIFICATORE_DATA", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdQualificatoreData() {
		return this.cdQualificatoreData;
	}

	/**
	 * Imposta codice qualificatore data.
	 *
	 * @param cdQualificatoreData the new codice qualificatore data
	 */
	public void setCdQualificatoreData(BigDecimal cdQualificatoreData) {
		this.cdQualificatoreData = cdQualificatoreData;
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

	/**
	 * Legge descrizione validator.
	 *
	 * @return the descrizione validator
	 */
	@Column(name = "DS_VALIDATOR", nullable = true, length = 250)
	public String getDsValidator() {
		return dsValidator;
	}

	/**
	 * Imposta descrizione validator.
	 *
	 * @param dsValidator the new descrizione validator
	 */
	public void setDsValidator(String dsValidator) {
		this.dsValidator = dsValidator;
	}
}
