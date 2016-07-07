package it.lispa.bdl.commons.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * Class BdlVetrina.
 */
@Entity
@Table(name="BDL_VETRINA")
public class BdlVetrina implements java.io.Serializable {

	private static final long serialVersionUID = -4628548456555570793L;
	
	private BigDecimal cdOggettoOriginale;
	private Boolean flAttivo;
	private Date dtInseritoil;
	private Date dtScadeil;
	
	/**
	 * 
	 */
	public BdlVetrina() {
	}

	/**
	 * @param cdOggettoOriginale
	 * @param flAttivo
	 * @param dtInseritoil
	 * @param dtScadeil
	 */
	public BdlVetrina(BigDecimal cdOggettoOriginale, Boolean flAttivo,
			Date dtInseritoil, Date dtScadeil) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.flAttivo = flAttivo;
		this.dtInseritoil = dtInseritoil;
		this.dtScadeil = dtScadeil;
	}

	@Id
	@Column(name="CD_OGGETTO_ORIGINALE", unique=true, nullable=false, precision=31, scale=0)
	public BigDecimal getCdOggettoOriginale() {
		return cdOggettoOriginale;
	}
	public void setCdOggettoOriginale(BigDecimal cdOggettoOriginale) {
		this.cdOggettoOriginale = cdOggettoOriginale;
	}
	
	@Column(name="FL_ATTIVO", nullable=false)
	@Type(type="true_false")
	public Boolean getFlAttivo() {
		return this.flAttivo;
	}
	public void setFlAttivo(Boolean flAttivo) {
		this.flAttivo = flAttivo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_SCADEIL")
	public Date getDtScadeil() {
		return dtScadeil;
	}
	public void setDtScadeil(Date dtScadeil) {
		this.dtScadeil = dtScadeil;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INSERITOIL")
	public Date getDtInseritoil() {
		return dtInseritoil;
	}
	public void setDtInseritoil(Date dtInseritoil) {
		this.dtInseritoil = dtInseritoil;
	}
}
