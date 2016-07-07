package it.lispa.bdl.commons.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * BdlUtente generated by hbm2java.
 */
@Entity
@Table(name = "BDL_MAIL", uniqueConstraints = @UniqueConstraint(columnNames = "CD_COMUNICAZIONE"))
public class BdlMail implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -6612171932179553990L;
	
	/** codice mail. */
	private BigDecimal cdMail;
	
	/** codice comunicazione. */
	private String cdComunicazione;
	
	/** descrizione processo. */
	private String dsProcesso;
	
	/** descrizione destinatario. */
	private String dsDestinatario;
	
	/** descrizione oggetto. */
	private String dsOggetto;
	
	/** descrizione corpo. */
	private String dsCorpo;

	/**
	 * Istanzia un nuovo bdl mail.
	 */
	public BdlMail() {
	}
	
	/**
	 * Istanzia un nuovo bdl mail.
	 *
	 * @param cdMail  codice mail
	 * @param cdComunicazione  codice comunicazione
	 * @param dsProcesso  descrizione processo
	 * @param dsDestinatario  descrizione destinatario
	 * @param dsOggetto  descrizione oggetto
	 * @param dsCorpo  descrizione corpo
	 */
	public BdlMail(BigDecimal cdMail, String cdComunicazione, String dsProcesso, String dsDestinatario, String dsOggetto, String dsCorpo) {
		this.cdMail = cdMail;
		this.cdComunicazione = cdComunicazione;
		this.dsProcesso = dsProcesso;
		this.dsDestinatario = dsDestinatario;
		this.dsOggetto = dsOggetto;
		this.dsCorpo = dsCorpo;
	}

	/**
	 * Legge codice mail.
	 *
	 * @return the codice mail
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_MAIL_seqgen")
	@SequenceGenerator(name="BDL_MAIL_seqgen", sequenceName="BDL_MAIL_SEQ", allocationSize=1)
	@Column(name = "CD_MAIL", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdMail() {
		return this.cdMail;
	}
	
	/**
	 * Imposta codice mail.
	 *
	 * @param cdMail the new codice mail
	 */
	public void setCdMail(BigDecimal cdMail) {
		this.cdMail = cdMail;
	}

	/**
	 * Legge codice comunicazione.
	 *
	 * @return the codice comunicazione
	 */
	@Column(name = "CD_COMUNICAZIONE", nullable = false, length = 10)
	public String getCdComunicazione() {
		return cdComunicazione;
	}

	/**
	 * Imposta codice comunicazione.
	 *
	 * @param cdComunicazione the new codice comunicazione
	 */
	public void setCdComunicazione(String cdComunicazione) {
		this.cdComunicazione = cdComunicazione;
	}

	/**
	 * Legge descrizione processo.
	 *
	 * @return the descrizione processo
	 */
	@Column(name = "DS_PROCESSO", nullable = false, length = 250)
	public String getDsProcesso() {
		return dsProcesso;
	}

	/**
	 * Imposta descrizione processo.
	 *
	 * @param dsProcesso the new descrizione processo
	 */
	public void setDsProcesso(String dsProcesso) {
		this.dsProcesso = dsProcesso;
	}

	/**
	 * Legge descrizione destinatario.
	 *
	 * @return the descrizione destinatario
	 */
	@Column(name = "DS_DESTINATARIO", nullable = false, length = 100)
	public String getDsDestinatario() {
		return dsDestinatario;
	}

	/**
	 * Imposta descrizione destinatario.
	 *
	 * @param dsDestinatario the new descrizione destinatario
	 */
	public void setDsDestinatario(String dsDestinatario) {
		this.dsDestinatario = dsDestinatario;
	}

	/**
	 * Legge descrizione oggetto.
	 *
	 * @return the descrizione oggetto
	 */
	@Column(name = "DS_OGGETTO", nullable = false, length = 250)
	public String getDsOggetto() {
		return dsOggetto;
	}


	/**
	 * Imposta descrizione oggetto.
	 *
	 * @param dsOggetto the new descrizione oggetto
	 */
	public void setDsOggetto(String dsOggetto) {
		this.dsOggetto = dsOggetto;
	}

	/**
	 * Legge descrizione corpo.
	 *
	 * @return the descrizione corpo
	 */
	@Column(name = "DS_CORPO", nullable = false, length = 4000)
	public String getDsCorpo() {
		return dsCorpo;
	}

	/**
	 * Imposta descrizione corpo.
	 *
	 * @param dsCorpo the new descrizione corpo
	 */
	public void setDsCorpo(String dsCorpo) {
		this.dsCorpo = dsCorpo;
	}
}
