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
 * Class BdlContestoArch.
 */
@Entity
@Table(name = "BDL_CONTESTO_ARCH")
public class BdlContestoArch extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice contesto arch. */
	private BigDecimal cdContestoArch;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl contesto arch.
	 */
	public BdlContestoArch() {
	}

	/**
	 * Istanzia un nuovo bdl contesto arch.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlContestoArch(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl contesto arch.
	 *
	 * @param cdContestoArch  codice contesto arch
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlContestoArch(BigDecimal cdContestoArch, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdContestoArch = cdContestoArch;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice contesto arch.
	 *
	 * @return the codice contesto arch
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_CONTESTO_ARCH_seqgen")
	@SequenceGenerator(name="BDL_CONTESTO_ARCH_seqgen", sequenceName="BDL_CONTESTO_ARCH_SEQ", allocationSize=1)
	@Column(name = "CD_CONTESTO_ARCH", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdContestoArch() {
		return this.cdContestoArch;
	}

	/**
	 * Imposta codice contesto arch.
	 *
	 * @param cdContestoArch the new codice contesto arch
	 */
	public void setCdContestoArch(BigDecimal cdContestoArch) {
		this.cdContestoArch = cdContestoArch;
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
