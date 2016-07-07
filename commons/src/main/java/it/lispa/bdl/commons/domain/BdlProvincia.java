package it.lispa.bdl.commons.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class BdlProvincia.
 */
@Entity
@Table(name = "BDL_PROVINCE")
public class BdlProvincia implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice prov. */
	private BigDecimal cdProv;
	
	/** codice prov istat. */
	private String cdProvIstat;
	
	/** descrizione provincia. */
	private String dsProvincia;
	
	/** codice sigla. */
	private String cdSigla;
	
	/** codice reg. */
	private BigDecimal cdReg;
	
	/** data inizio validita. */
	private Date dtInizioValidita;
	
	/** data fine validita. */
	private Date dtFineValidita;
	
	/** flag annulla. */
	private String flAnnulla;
	
	/** data insert. */
	private Date dtInsert;
	
	/** data update. */
	private Date dtUpdate;
	
	/** codice user. */
	private String cdUser;
	
	/**
	 * Istanzia un nuovo bdl provincia.
	 */
	public BdlProvincia() {
	}
	
	/**
	 * Istanzia un nuovo bdl provincia.
	 *
	 * @param cdProv  codice prov
	 * @param cdProvIstat  codice prov istat
	 * @param dsProvincia  descrizione provincia
	 * @param cdSigla  codice sigla
	 * @param cdReg  codice reg
	 * @param dtInizioValidita  data inizio validita
	 * @param dtFineValidita  data fine validita
	 * @param flAnnulla  flag annulla
	 * @param dtInsert  data insert
	 * @param dtUpdate  data update
	 * @param cdUser  codice user
	 */
	public BdlProvincia(BigDecimal cdProv, String cdProvIstat, String dsProvincia, String cdSigla,  
						BigDecimal cdReg, Date dtInizioValidita, Date dtFineValidita, String flAnnulla, 
						Date dtInsert, Date dtUpdate, String cdUser) {
		
		this.cdProv = cdProv; 
		this.cdProvIstat = cdProvIstat;
		this.dsProvincia = dsProvincia;
		this.cdSigla = cdSigla;
		this.cdReg = cdReg;
		this.dtInizioValidita = dtInizioValidita;
		this.dtFineValidita = dtFineValidita;
		this.flAnnulla = flAnnulla;
		this.dtInsert = dtInsert;
		this.dtUpdate = dtUpdate;
		this.cdUser = cdUser;
		
	}

	/**
	 * Legge codice prov.
	 *
	 * @return the codice prov
	 */
	@Id
	@Column(name = "PROV_KEY_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getCdProv() {
		return this.cdProv;
	}

	/**
	 * Imposta codice prov.
	 *
	 * @param cdProv the new codice prov
	 */
	public void setCdProv(BigDecimal cdProv) {
		this.cdProv = cdProv;
	}

	/**
	 * Legge codice prov istat.
	 *
	 * @return the codice prov istat
	 */
	@Column(name = "CD_PROVINCIA_ISTAT", nullable = false, length = 3)
	public String getCdProvIstat() {
		return this.cdProvIstat;
	}

	/**
	 * Imposta codice prov istat.
	 *
	 * @param cdProvIstat the new codice prov istat
	 */
	public void setCdProvIstat(String cdProvIstat) {
		this.cdProvIstat = cdProvIstat;
	}

	/**
	 * Legge descrizione provincia.
	 *
	 * @return the descrizione provincia
	 */
	@Column(name = "DS_PROVINCIA", nullable = false, length = 40)
	public String getDsProvincia() {
		return this.dsProvincia;
	}

	/**
	 * Imposta descrizione provincia.
	 *
	 * @param dsProvincia the new descrizione provincia
	 */
	public void setDsProvincia(String dsProvincia) {
		this.dsProvincia = dsProvincia;
	}

	/**
	 * Legge codice sigla.
	 *
	 * @return the codice sigla
	 */
	@Column(name = "CD_SIGLA", nullable = false, length = 3)
	public String getCdSigla() {
		return this.cdSigla;
	}

	/**
	 * Imposta codice sigla.
	 *
	 * @param cdSigla the new codice sigla
	 */
	public void setCdSigla(String cdSigla) {
		this.cdSigla= cdSigla;
	}
	
	/**
	 * Legge codice reg.
	 *
	 * @return the codice reg
	 */
	@Column(name = "REG_KEY_ID", nullable = false, precision = 10, scale = 0)
	public BigDecimal getCdReg() {
		return this.cdReg;
	}

	/**
	 * Imposta codice reg.
	 *
	 * @param cdReg the new codice reg
	 */
	public void setCdReg(BigDecimal cdReg) {
		this.cdReg = cdReg;
	}
	
	/**
	 * Legge data inizio validita.
	 *
	 * @return the data inizio validita
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INIZIO_VALIDITA")
	public Date getDtInizioValidita() {
		return this.dtInizioValidita;
	}
	
	/**
	 * Imposta data inizio validita.
	 *
	 * @param dtInizioValidita the new data inizio validita
	 */
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	/**
	 * Legge data fine validita.
	 *
	 * @return the data fine validita
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FINE_VALIDITA")
	public Date getDtFineValidita() {
		return this.dtFineValidita;
	}
	
	/**
	 * Imposta data fine validita.
	 *
	 * @param dtFineValidita the new data fine validita
	 */
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	/**
	 * Legge flag annulla.
	 *
	 * @return the flag annulla
	 */
	@Column(name = "FL_ANNULLA")
	public String getFlAnnulla() {
		return this.flAnnulla;
	}
	
	/**
	 * Imposta flag annulla.
	 *
	 * @param flAnnulla the new flag annulla
	 */
	public void setFlAnnulla(String flAnnulla) {
		this.flAnnulla = flAnnulla;
	}
	
	/**
	 * Legge data insert.
	 *
	 * @return the data insert
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INSERT", nullable = false)
	public Date getDtInsert() {
		return this.dtInsert;
	}

	/**
	 * Imposta data insert.
	 *
	 * @param dtInsert the new data insert
	 */
	public void setDtInsert(Date dtInsert) {
		this.dtInsert = dtInsert;
	}
	
	/**
	 * Legge data update.
	 *
	 * @return the data update
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_UPDATE", nullable = false)
	public Date getDtUpdate() {
		return this.dtUpdate;
	}

	/**
	 * Imposta data update.
	 *
	 * @param dtUpdate the new data update
	 */
	public void setDtUpdate(Date dtUpdate) {
		this.dtUpdate = dtUpdate;
	}
	
	/**
	 * Legge codice user.
	 *
	 * @return the codice user
	 */
	@Column(name = "CD_USERID", nullable = false, length = 3)
	public String getCdUser() {
		return this.cdUser;
	}

	/**
	 * Imposta codice user.
	 *
	 * @param cdUser the new codice user
	 */
	public void setCdUser(String cdUser) {
		this.cdUser = cdUser;
	}
}
