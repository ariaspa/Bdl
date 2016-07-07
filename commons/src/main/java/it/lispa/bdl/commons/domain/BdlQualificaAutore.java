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
 * Class BdlQualificaAutore.
 */
@Entity
@Table(name = "BDL_QUALIFICA_AUTORE")
public class BdlQualificaAutore extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice qualifica autore. */
	private BigDecimal cdQualificaAutore;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/** codice sbn. */
	private String cdSbn;
	
	/**
	 * Istanzia un nuovo bdl qualifica autore.
	 */
	public BdlQualificaAutore() {
	}

	/**
	 * Istanzia un nuovo bdl qualifica autore.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param cdSbn  codice sbn
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlQualificaAutore(String dsDescrizione, String cdSbn, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.cdSbn = cdSbn;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}
	
	/**
	 * Istanzia un nuovo bdl qualifica autore.
	 *
	 * @param cdQualificaAutore  codice qualifica autore
	 * @param dsDescrizione  descrizione descrizione
	 * @param cdSbn  codice sbn
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlQualificaAutore(BigDecimal cdQualificaAutore, String dsDescrizione, String cdSbn, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdQualificaAutore = cdQualificaAutore;
		this.dsDescrizione = dsDescrizione;
		this.cdSbn = cdSbn;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice qualifica autore.
	 *
	 * @return the codice qualifica autore
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_QUALIFICA_AUTORE_seqgen")
	@SequenceGenerator(name="BDL_QUALIFICA_AUTORE_seqgen", sequenceName="BDL_QUALIFICA_AUTORE_SEQ", allocationSize=1)
	@Column(name = "CD_QUALIFICA_AUTORE", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdQualificaAutore() {
		return this.cdQualificaAutore;
	}

	/**
	 * Imposta codice qualifica autore.
	 *
	 * @param cdQualificaAutore the new codice qualifica autore
	 */
	public void setCdQualificaAutore(BigDecimal cdQualificaAutore) {
		this.cdQualificaAutore = cdQualificaAutore;
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
	 * Legge codice sbn.
	 *
	 * @return the codice sbn
	 */
	@Column(name = "CD_SBN", nullable = true, length = 250)
	public String getCdSbn() {
		return cdSbn;
	}

	/**
	 * Imposta codice sbn.
	 *
	 * @param cdSbn the new codice sbn
	 */
	public void setCdSbn(String cdSbn) {
		this.cdSbn = cdSbn;
	}
}
