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
 * Class BdlTipoGrafica.
 */
@Entity
@Table(name = "BDL_TIPO_GRAFICA")
public class BdlTipoGrafica extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice tipo grafica. */
	private BigDecimal cdTipoGrafica;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl tipo grafica.
	 */
	public BdlTipoGrafica() {
	}

	/**
	 * Istanzia un nuovo bdl tipo grafica.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlTipoGrafica(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl tipo grafica.
	 *
	 * @param cdTipoGrafica  codice tipo grafica
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlTipoGrafica(BigDecimal cdTipoGrafica, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdTipoGrafica = cdTipoGrafica;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice tipo grafica.
	 *
	 * @return the codice tipo grafica
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_TIPO_GRAFICA_seqgen")
	@SequenceGenerator(name="BDL_TIPO_GRAFICA_seqgen", sequenceName="BDL_TIPO_GRAFICA_SEQ", allocationSize=1)
	@Column(name = "CD_TIPO_GRAFICA", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdTipoGrafica() {
		return this.cdTipoGrafica;
	}

	/**
	 * Imposta codice tipo grafica.
	 *
	 * @param cdTipoGrafica the new codice tipo grafica
	 */
	public void setCdTipoGrafica(BigDecimal cdTipoGrafica) {
		this.cdTipoGrafica = cdTipoGrafica;
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
