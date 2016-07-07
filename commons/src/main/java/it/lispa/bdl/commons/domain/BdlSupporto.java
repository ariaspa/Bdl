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
 * Class BdlSupporto.
 */
@Entity
@Table(name = "BDL_SUPPORTO")
public class BdlSupporto extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice supporto. */
	private BigDecimal cdSupporto;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl supporto.
	 */
	public BdlSupporto() {
	}

	/**
	 * Istanzia un nuovo bdl supporto.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlSupporto(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl supporto.
	 *
	 * @param cdSupporto  codice supporto
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlSupporto(BigDecimal cdSupporto, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdSupporto = cdSupporto;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice supporto.
	 *
	 * @return the codice supporto
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_SUPPORTO_seqgen")
	@SequenceGenerator(name="BDL_SUPPORTO_seqgen", sequenceName="BDL_SUPPORTO_SEQ", allocationSize=1)
	@Column(name = "CD_SUPPORTO", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdSupporto() {
		return this.cdSupporto;
	}

	/**
	 * Imposta codice supporto.
	 *
	 * @param cdSupporto the new codice supporto
	 */
	public void setCdSupporto(BigDecimal cdSupporto) {
		this.cdSupporto = cdSupporto;
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
