package it.lispa.bdl.shared.dto;

// Generated 29-nov-2013 10.47.17 by Hibernate Tools 3.6.0

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class ProgettoDTO.
 */
public class ProgettoDTO extends BaseDTO implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -1359215423946833930L;
	
	/** codice progetto. */
	private BigDecimal cdProgetto;
	
	/** titolo. */
	private String titolo;
	
	/** titolo breve. */
	private String titoloBreve;
	
	/** descrizione it. */
	private String descrizioneIt;
	
	/** descrizione en. */
	private String descrizioneEn;
	
	/** inizio. */
	private Long inizio;
	
	/** conclusione. */
	private Long conclusione;
	
	/** stato. */
	private String stato;
	
	/**
	 * Istanzia un nuovo progetto dto.
	 */
	public ProgettoDTO() {
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo progetto dto.
	 *
	 * @param cdProgetto  codice progetto
	 * @param titolo  titolo
	 * @param titoloBreve  titolo breve
	 * @param descrizioneIt  descrizione it
	 * @param descrizioneEn  descrizione en
	 * @param inizio  inizio
	 * @param conclusione  conclusione
	 * @param stato  stato
	 */
	public ProgettoDTO(
			BigDecimal cdProgetto, 
			String titolo,
			String titoloBreve, 
			String descrizioneIt, 
			String descrizioneEn,
			Date inizio, 
			Date conclusione, 
			String stato
	) {
		this.cdProgetto = cdProgetto;
		this.titolo = titolo;
		this.titoloBreve = titoloBreve;
		this.descrizioneIt = descrizioneIt;
		this.descrizioneEn = descrizioneEn;
		setInizio(inizio);
		setConclusione(conclusione);
		this.stato = stato;
	}

	/**
	 * Istanzia un nuovo progetto dto.
	 *
	 * @param cdProgetto  codice progetto
	 * @param titolo  titolo
	 * @param titoloBreve  titolo breve
	 * @param descrizioneIt  descrizione it
	 * @param descrizioneEn  descrizione en
	 * @param inizio  inizio
	 * @param conclusione  conclusione
	 * @param stato  stato
	 * @param creatoda  creatoda
	 * @param creatoil  creatoil
	 * @param modificatoda  modificatoda
	 * @param modificatoil  modificatoil
	 */
	public ProgettoDTO(BigDecimal cdProgetto, String titolo,
			String titoloBreve, String descrizioneIt, String descrizioneEn,
			Date inizio, Date conclusione, String stato, String creatoda,
			Date creatoil, String modificatoda, Date modificatoil) {
		this.cdProgetto = cdProgetto;
		this.titolo = titolo;
		this.titoloBreve = titoloBreve;
		this.descrizioneIt = descrizioneIt;
		this.descrizioneEn = descrizioneEn;
		setInizio(inizio);
		setConclusione(conclusione);
		this.stato = stato;
		setCreatoda(creatoda);
		setCreatoil(creatoil);
		setModificatoda(modificatoda);
		setModificatoil(modificatoil);
	}

	/**
	 * Legge codice progetto.
	 *
	 * @return codice progetto
	 */
	public BigDecimal getCdProgetto() {
		return cdProgetto;
	}

	/**
	 * Imposta codice progetto.
	 *
	 * @param cdProgetto nuovo codice progetto
	 */
	public void setCdProgetto(BigDecimal cdProgetto) {
		this.cdProgetto = cdProgetto;
	}

	/**
	 * Legge titolo.
	 *
	 * @return titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * Imposta titolo.
	 *
	 * @param titolo nuovo titolo
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * Legge titolo breve.
	 *
	 * @return titolo breve
	 */
	public String getTitoloBreve() {
		return titoloBreve;
	}

	/**
	 * Imposta titolo breve.
	 *
	 * @param titoloBreve nuovo titolo breve
	 */
	public void setTitoloBreve(String titoloBreve) {
		this.titoloBreve = titoloBreve;
	}

	/**
	 * Legge descrizione it.
	 *
	 * @return descrizione it
	 */
	public String getDescrizioneIt() {
		return descrizioneIt;
	}

	/**
	 * Imposta descrizione it.
	 *
	 * @param descrizioneIt nuovo descrizione it
	 */
	public void setDescrizioneIt(String descrizioneIt) {
		this.descrizioneIt = descrizioneIt;
	}

	/**
	 * Legge descrizione en.
	 *
	 * @return descrizione en
	 */
	public String getDescrizioneEn() {
		return descrizioneEn;
	}

	/**
	 * Imposta descrizione en.
	 *
	 * @param descrizioneEn nuovo descrizione en
	 */
	public void setDescrizioneEn(String descrizioneEn) {
		this.descrizioneEn = descrizioneEn;
	}

	/**
	 * Legge inizio.
	 *
	 * @return inizio
	 */
	public Date getInizio() {
		return dateGetter(inizio);
	}

	/**
	 * Imposta inizio.
	 *
	 * @param inizio nuovo inizio
	 */
	public void setInizio(Date inizio) {
		this.inizio = dateSetter(inizio);
	}

	/**
	 * Legge conclusione.
	 *
	 * @return conclusione
	 */
	public Date getConclusione() {
		return dateGetter(conclusione);
	}

	/**
	 * Imposta conclusione.
	 *
	 * @param conclusione nuovo conclusione
	 */
	public void setConclusione(Date conclusione) {
		this.conclusione = dateSetter(conclusione);
	}

	/**
	 * Legge stato.
	 *
	 * @return stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * Imposta stato.
	 *
	 * @param stato nuovo stato
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * Legge stato human.
	 *
	 * @return stato human
	 */
	public String getStatoHuman() {
		String statoHuman = null;
		if(stato!=null){
			if(stato.equals(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO)){
				statoHuman = BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_PROGETTO_STATO_CONCLUSO)){
				statoHuman = BdlSharedConstants.BDL_PROGETTO_STATO_CONCLUSO_HUMAN;
			}
		}
		return statoHuman;
	}
}
