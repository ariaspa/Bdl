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
 * Class BdlTipoArchivio.
 */
@Entity
@Table(name = "BDL_TIPO_ARCHIVIO")
public class BdlTipoArchivio extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice tipo archivio. */
	private BigDecimal cdTipoArchivio;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl tipo archivio.
	 */
	public BdlTipoArchivio() {
	}

	/**
	 * Istanzia un nuovo bdl tipo archivio.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlTipoArchivio(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl tipo archivio.
	 *
	 * @param cdTipoArchivio  codice tipo archivio
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlTipoArchivio(BigDecimal cdTipoArchivio, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdTipoArchivio = cdTipoArchivio;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice tipo archivio.
	 *
	 * @return the codice tipo archivio
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_TIPO_ARCHIVIO_seqgen")
	@SequenceGenerator(name="BDL_TIPO_ARCHIVIO_seqgen", sequenceName="BDL_TIPO_ARCHIVIO_SEQ", allocationSize=1)
	@Column(name = "CD_TIPO_ARCHIVIO", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdTipoArchivio() {
		return this.cdTipoArchivio;
	}

	/**
	 * Imposta codice tipo archivio.
	 *
	 * @param cdTipoArchivio the new codice tipo archivio
	 */
	public void setCdTipoArchivio(BigDecimal cdTipoArchivio) {
		this.cdTipoArchivio = cdTipoArchivio;
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
