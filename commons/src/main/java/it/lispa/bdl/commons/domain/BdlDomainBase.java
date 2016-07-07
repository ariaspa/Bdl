package it.lispa.bdl.commons.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class BdlDomainBase.
 */
@MappedSuperclass
public class BdlDomainBase {

	/** descrizione creatoda. */
	protected String dsCreatoda;
	
	/** data creatoil. */
	protected Date dtCreatoil;
	
	/** descrizione modificatoda. */
	protected String dsModificatoda;
	
	/** data modificatoil. */
	protected Date dtModificatoil;

	/**
	 * Legge descrizione creatoda.
	 *
	 * @return the descrizione creatoda
	 */
	@Column(name = "DS_CREATODA", nullable = false)
	public String getDsCreatoda() {
		return this.dsCreatoda;
	}

	/**
	 * Imposta descrizione creatoda.
	 *
	 * @param dsCreatoda the new descrizione creatoda
	 */
	public void setDsCreatoda(String dsCreatoda) {
		this.dsCreatoda = dsCreatoda;
	}

	/**
	 * Legge data creatoil.
	 *
	 * @return the data creatoil
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CREATOIL", nullable = false)
	public Date getDtCreatoil() {
		return this.dtCreatoil;
	}

	/**
	 * Imposta data creatoil.
	 *
	 * @param dtCreatoil the new data creatoil
	 */
	public void setDtCreatoil(Date dtCreatoil) {
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Legge descrizione modificatoda.
	 *
	 * @return the descrizione modificatoda
	 */
	@Column(name = "DS_MODIFICATODA")
	public String getDsModificatoda() {
		return this.dsModificatoda;
	}

	/**
	 * Imposta descrizione modificatoda.
	 *
	 * @param dsModificatoda the new descrizione modificatoda
	 */
	public void setDsModificatoda(String dsModificatoda) {
		this.dsModificatoda = dsModificatoda;
	}

	/**
	 * Legge data modificatoil.
	 *
	 * @return the data modificatoil
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_MODIFICATOIL")
	public Date getDtModificatoil() {
		return this.dtModificatoil;
	}

	/**
	 * Imposta data modificatoil.
	 *
	 * @param dtModificatoil the new data modificatoil
	 */
	public void setDtModificatoil(Date dtModificatoil) {
		this.dtModificatoil = dtModificatoil;
	}

}
