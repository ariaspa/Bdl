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
 * Class BdlTipoIdentificativo.
 */
@Entity
@Table(name = "BDL_TIPO_IDENTIFICATIVO")
public class BdlTipoIdentificativo extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice tipo identificativo. */
	private BigDecimal cdTipoIdentificativo;
	
	/** descrizione descrizione. */
	private String dsDescrizione;

	/**
	 * Istanzia un nuovo bdl tipo identificativo.
	 */
	public BdlTipoIdentificativo() {
	}

	/**
	 * Istanzia un nuovo bdl tipo identificativo.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlTipoIdentificativo(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl tipo identificativo.
	 *
	 * @param cdTipoIdentificativoa  codice tipo identificativoa
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlTipoIdentificativo(BigDecimal cdTipoIdentificativoa, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdTipoIdentificativo = cdTipoIdentificativoa;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice tipo identificativo.
	 *
	 * @return the codice tipo identificativo
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_TIPO_IDENTIFICATIVO_seqgen")
	@SequenceGenerator(name="BDL_TIPO_IDENTIFICATIVO_seqgen", sequenceName="BDL_TIPO_IDENTIFICATIVO_SEQ", allocationSize=1)
	@Column(name = "CD_TIPO_IDENTIFICATIVO", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdTipoIdentificativo() {
		return this.cdTipoIdentificativo;
	}

	/**
	 * Imposta codice tipo identificativo.
	 *
	 * @param cdTipoIdentificativo the new codice tipo identificativo
	 */
	public void setCdTipoIdentificativo(BigDecimal cdTipoIdentificativo) {
		this.cdTipoIdentificativo = cdTipoIdentificativo;
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
