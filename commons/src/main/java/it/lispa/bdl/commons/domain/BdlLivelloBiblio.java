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
 * Class BdlLivelloBiblio.
 */
@Entity
@Table(name = "BDL_LIVELLO_BIBLIO")
public class BdlLivelloBiblio extends BdlDomainBase implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3877302236029708435L;
	
	/** codice livello biblio. */
	private BigDecimal cdLivelloBiblio;
	
	/** descrizione descrizione. */
	private String dsDescrizione;
	
	/**
	 * Istanzia un nuovo bdl livello biblio.
	 */
	public BdlLivelloBiblio() {
	}

	/**
	 * Istanzia un nuovo bdl livello biblio.
	 *
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 */
	public BdlLivelloBiblio(String dsDescrizione, String dsCreatoda, Date dtCreatoil) {
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
	}

	/**
	 * Istanzia un nuovo bdl livello biblio.
	 *
	 * @param cdLivelloBiblio  codice livello biblio
	 * @param dsDescrizione  descrizione descrizione
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 */
	public BdlLivelloBiblio(BigDecimal cdLivelloBiblio, String dsDescrizione, String dsCreatoda, Date dtCreatoil, String dsModificatoda, Date dtModificatoil) {
		this.cdLivelloBiblio = cdLivelloBiblio;
		this.dsDescrizione = dsDescrizione;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
	}

	/**
	 * Legge codice livello biblio.
	 *
	 * @return the codice livello biblio
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_LIVELLO_BIBLIO_seqgen")
	@SequenceGenerator(name="BDL_LIVELLO_BIBLIO_seqgen", sequenceName="BDL_LIVELLO_BIBLIO_SEQ", allocationSize=1)
	@Column(name = "CD_LIVELLO_BIBLIO", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdLivelloBiblio() {
		return this.cdLivelloBiblio;
	}

	/**
	 * Imposta codice livello biblio.
	 *
	 * @param cdLivelloBiblio the new codice livello biblio
	 */
	public void setCdLivelloBiblio(BigDecimal cdLivelloBiblio) {
		this.cdLivelloBiblio = cdLivelloBiblio;
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
