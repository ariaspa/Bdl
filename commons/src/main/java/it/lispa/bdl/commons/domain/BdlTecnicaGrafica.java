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
 * Class BdlTecnicaGrafica.
 */
@Entity
@Table(name = "BDL_TECNICA_GRAFICA")
public class BdlTecnicaGrafica extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice tecnica grafica. */
	private BigDecimal cdTecnicaGrafica;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl tecnica grafica.
	 */
	public BdlTecnicaGrafica() {
	}

	/**
	 * Istanzia un nuovo bdl tecnica grafica.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlTecnicaGrafica(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl tecnica grafica.
	 *
	 * @param cdTecnicaGrafica  codice tecnica grafica
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlTecnicaGrafica(BigDecimal cdTecnicaGrafica, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdTecnicaGrafica = cdTecnicaGrafica;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice tecnica grafica.
	 *
	 * @return the codice tecnica grafica
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_TECNICA_GRAFICA_seqgen")
	@SequenceGenerator(name="BDL_TECNICA_GRAFICA_seqgen", sequenceName="BDL_TECNICA_GRAFICA_SEQ", allocationSize=1)
	@Column(name = "CD_TECNICA_GRAFICA", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdTecnicaGrafica() {
		return this.cdTecnicaGrafica;
	}

	/**
	 * Imposta codice tecnica grafica.
	 *
	 * @param cdTecnicaGrafica the new codice tecnica grafica
	 */
	public void setCdTecnicaGrafica(BigDecimal cdTecnicaGrafica) {
		this.cdTecnicaGrafica = cdTecnicaGrafica;
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
