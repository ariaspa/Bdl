package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class CollezioneDTO.
 */
@XmlRootElement
public class CollezioneDTO extends BaseDTO  implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -4795866998284568695L;


	/** codice collezione. */
	private BigDecimal cdCollezione;
	
	/** codice progetto. */
	private BigDecimal cdProgetto;
	
	/** titolo. */
	private String titolo;
	
	/** descrizione it. */
	private String descrizioneIt;
	
	/** descrizione en. */
	private String descrizioneEn;
	
	/** diritti. */
	private String diritti;
	
	/** ambito disciplinare. */
	private String ambitoDisciplinare;
	
	/** copertura geografica. */
	private String coperturaGeografica;
	
	/** periodo. */
	private String periodo;
	
	/** anno ogg antico. */
	private String annoOggAntico;
	
	/** anno ogg recente. */
	private String annoOggRecente;

	/** titolo progetto. */
	private String titoloProgetto;
	
	/**
	 * Istanzia un nuovo collezione dto.
	 */
	public CollezioneDTO() {
	}

	/**
	 * Istanzia un nuovo collezione dto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param cdProgetto  codice progetto
	 * @param titoloProgetto  titolo progetto
	 * @param titolo  titolo
	 * @param descrizioneIt  descrizione it
	 * @param descrizioneEn  descrizione en
	 * @param diritti  diritti
	 * @param ambitoDisciplinare  ambito disciplinare
	 * @param coperturaGeografica  copertura geografica
	 * @param periodo  periodo
	 * @param annoOggAntico  anno ogg antico
	 * @param annoOggRecente  anno ogg recente
	 */
	public CollezioneDTO(BigDecimal cdCollezione, BigDecimal cdProgetto,
			String titoloProgetto, String titolo, String descrizioneIt,
			String descrizioneEn, String diritti, String ambitoDisciplinare,
			String coperturaGeografica, String periodo, String annoOggAntico,
			String annoOggRecente) {
		this.cdCollezione = cdCollezione;
		this.cdProgetto = cdProgetto;
		this.titoloProgetto = titoloProgetto;
		this.titolo = titolo;
		this.descrizioneIt = descrizioneIt;
		this.descrizioneEn = descrizioneEn;
		this.diritti = diritti;
		this.ambitoDisciplinare = ambitoDisciplinare;
		this.coperturaGeografica = coperturaGeografica;
		this.periodo = periodo;
		this.annoOggAntico = annoOggAntico;
		this.annoOggRecente = annoOggRecente;
	}
	
	/**
	 * Istanzia un nuovo collezione dto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param cdProgetto  codice progetto
	 * @param titolo  titolo
	 * @param descrizioneIt  descrizione it
	 * @param descrizioneEn  descrizione en
	 * @param diritti  diritti
	 * @param ambitoDisciplinare  ambito disciplinare
	 * @param coperturaGeografica  copertura geografica
	 * @param periodo  periodo
	 * @param annoOggAntico  anno ogg antico
	 * @param annoOggRecente  anno ogg recente
	 */
	public CollezioneDTO(
			BigDecimal cdCollezione, 
			BigDecimal cdProgetto,
			String titolo, 
			String descrizioneIt,
			String descrizioneEn, 
			String diritti, 
			String ambitoDisciplinare,
			String coperturaGeografica, 
			String periodo, 
			String annoOggAntico,
			String annoOggRecente
	) {
		this.cdCollezione = cdCollezione;
		this.cdProgetto = cdProgetto;
		this.titolo = titolo;
		this.descrizioneIt = descrizioneIt;
		this.descrizioneEn = descrizioneEn;
		this.diritti = diritti;
		this.ambitoDisciplinare = ambitoDisciplinare;
		this.coperturaGeografica = coperturaGeografica;
		this.periodo = periodo;
		this.annoOggAntico = annoOggAntico;
		this.annoOggRecente = annoOggRecente;
	}

	/**
	 * Istanzia un nuovo collezione dto.
	 *
	 * @param cdCollezione  codice collezione
	 * @param cdProgetto  codice progetto
	 * @param titoloProgetto  titolo progetto
	 * @param titolo  titolo
	 * @param descrizioneIt  descrizione it
	 * @param descrizioneEn  descrizione en
	 * @param diritti  diritti
	 * @param ambitoDisciplinare  ambito disciplinare
	 * @param coperturaGeografica  copertura geografica
	 * @param periodo  periodo
	 * @param annoOggAntico  anno ogg antico
	 * @param annoOggRecente  anno ogg recente
	 * @param creatoda  creatoda
	 * @param creatoil  creatoil
	 * @param modificatoda  modificatoda
	 * @param modificatoil  modificatoil
	 */
	public CollezioneDTO(BigDecimal cdCollezione, BigDecimal cdProgetto,
			String titoloProgetto, String titolo, String descrizioneIt,
			String descrizioneEn, String diritti, String ambitoDisciplinare,
			String coperturaGeografica, String periodo, String annoOggAntico,
			String annoOggRecente, String creatoda, Date creatoil,
			String modificatoda, Date modificatoil) {
		this.cdCollezione = cdCollezione;
		this.cdProgetto = cdProgetto;
		this.titoloProgetto = titoloProgetto;
		this.titolo = titolo;
		this.descrizioneIt = descrizioneIt;
		this.descrizioneEn = descrizioneEn;
		this.diritti = diritti;
		this.ambitoDisciplinare = ambitoDisciplinare;
		this.coperturaGeografica = coperturaGeografica;
		this.periodo = periodo;
		this.annoOggAntico = annoOggAntico;
		this.annoOggRecente = annoOggRecente;
		setCreatoda(creatoda);
		setCreatoil(creatoil);
		setModificatoda(modificatoda);
		setModificatoil(modificatoil);
	}

	/**
	 * Legge codice collezione.
	 *
	 * @return codice collezione
	 */
	public BigDecimal getCdCollezione() {
		return cdCollezione;
	}

	/**
	 * Imposta codice collezione.
	 *
	 * @param cdCollezione nuovo codice collezione
	 */
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
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
	 * Legge titolo progetto.
	 *
	 * @return titolo progetto
	 */
	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	/**
	 * Imposta titolo progetto.
	 *
	 * @param titoloProgetto nuovo titolo progetto
	 */
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
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
	 * Legge diritti.
	 *
	 * @return diritti
	 */
	public String getDiritti() {
		return diritti;
	}

	/**
	 * Imposta diritti.
	 *
	 * @param diritti nuovo diritti
	 */
	public void setDiritti(String diritti) {
		this.diritti = diritti;
	}

	/**
	 * Legge ambito disciplinare.
	 *
	 * @return ambito disciplinare
	 */
	public String getAmbitoDisciplinare() {
		return ambitoDisciplinare;
	}

	/**
	 * Imposta ambito disciplinare.
	 *
	 * @param ambitoDisciplinare nuovo ambito disciplinare
	 */
	public void setAmbitoDisciplinare(String ambitoDisciplinare) {
		this.ambitoDisciplinare = ambitoDisciplinare;
	}

	/**
	 * Legge copertura geografica.
	 *
	 * @return copertura geografica
	 */
	public String getCoperturaGeografica() {
		return coperturaGeografica;
	}

	/**
	 * Imposta copertura geografica.
	 *
	 * @param coperturaGeografica nuovo copertura geografica
	 */
	public void setCoperturaGeografica(String coperturaGeografica) {
		this.coperturaGeografica = coperturaGeografica;
	}

	/**
	 * Legge periodo.
	 *
	 * @return periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * Imposta periodo.
	 *
	 * @param periodo nuovo periodo
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * Legge anno ogg antico.
	 *
	 * @return anno ogg antico
	 */
	public String getAnnoOggAntico() {
		return annoOggAntico;
	}

	/**
	 * Imposta anno ogg antico.
	 *
	 * @param annoOggAntico nuovo anno ogg antico
	 */
	public void setAnnoOggAntico(String annoOggAntico) {
		this.annoOggAntico = annoOggAntico;
	}

	/**
	 * Legge anno ogg recente.
	 *
	 * @return anno ogg recente
	 */
	public String getAnnoOggRecente() {
		return annoOggRecente;
	}

	/**
	 * Imposta anno ogg recente.
	 *
	 * @param annoOggRecente nuovo anno ogg recente
	 */
	public void setAnnoOggRecente(String annoOggRecente) {
		this.annoOggRecente = annoOggRecente;
	}
}
