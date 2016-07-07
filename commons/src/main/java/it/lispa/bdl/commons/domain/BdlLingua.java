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
 * Class BdlLingua.
 */
@Entity
@Table(name = "BDL_LINGUA")
public class BdlLingua extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice lingua. */
	private BigDecimal cdLingua;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/** descrizione codlang. */
	private String dsCodlang;
	
	/**
	 * Istanzia un nuovo bdl lingua.
	 */
	public BdlLingua() {
	}

	/**
	 * Istanzia un nuovo bdl lingua.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCodlang  descrizione codlang
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlLingua(String dsDescrizione, String dsCodlang, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCodlang = dsCodlang;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl lingua.
	 *
	 * @param cdLingua  codice lingua
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCodlang  descrizione codlang
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlLingua(BigDecimal cdLingua, String dsDescrizione, String dsCodlang, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdLingua = cdLingua;
		this.dsDescrizione = dsDescrizione;
		this.dsCodlang = dsCodlang;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice lingua.
	 *
	 * @return the codice lingua
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_LINGUA_seqgen")
	@SequenceGenerator(name="BDL_LINGUA_seqgen", sequenceName="BDL_LINGUA_SEQ", allocationSize=1)
	@Column(name = "CD_LINGUA", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdLingua() {
		return this.cdLingua;
	}

	/**
	 * Imposta codice lingua.
	 *
	 * @param cdLingua the new codice lingua
	 */
	public void setCdLingua(BigDecimal cdLingua) {
		this.cdLingua = cdLingua;
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
	 * Legge descrizione codlang.
	 *
	 * @return the descrizione codlang
	 */
	@Column(name = "DS_CODLANG", nullable = false, length = 250)
	public String getDsCodlang() {
		return this.dsCodlang;
	}

	/**
	 * Imposta descrizione codlang.
	 *
	 * @param dsCodlang the new descrizione codlang
	 */
	public void setDsCodlang(String dsCodlang) {
		this.dsCodlang = dsCodlang;
	}
}
