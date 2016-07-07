package it.lispa.bdl.shared.dto;

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class OggettoDTO.
 */
@XmlRootElement
public class OggettoDTO extends BaseDTO  implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -4628549124796270793L;

	/** codice oggetto originale. */
	private BigDecimal cdOggettoOriginale;
	
	/** codice tipo oggetto. */
	private BigDecimal cdTipoOggetto;
	
	/** codice oggetto originale sup. */
	private BigDecimal cdOggettoOriginaleSup;
	
	/** codice collezione. */
	private BigDecimal cdCollezione;

	/** titolo. */
	private String titolo;
	
	/** titoloFe. */
	private String titoloFe;
	
	/** oggetto superiore. */
	private Boolean oggettoSuperiore;
	
	/** oggetti inferiori. */
	private BigDecimal oggettiInferiori;
	
	/** oggetto digitale. */
	private Boolean oggettoDigitale;
	
	/** correzione. */
	private Boolean correzione;
	
	/** nota correzione. */
	private String notaCorrezione;
	
	/** anomalia immagini. */
	private Boolean anomaliaImmagini;
	
	/** log anomalia. */
	private String logAnomalia;
	
	/** stato. */
	private String stato;
	
	/** note non validazione. */
	private String noteNonValidazione;
	
	/** note non pubblicazione. */
	private String noteNonPubblicazione;
	
	/** immagini previste. */
	private BigDecimal immaginiPreviste;
	
	/** immagini digitalizzate. */
	private BigDecimal immaginiDigitalizzate;

	/** contenutistica. */
	private String contenutistica;
	
	/** fisica. */
	private String fisica;
	
	/** codice livello biblio. */
	private BigDecimal cdLivelloBiblio;
	
	/** codice soggetto. */
	private BigDecimal cdSoggetto;
	
	/** codice autore. */
	private BigDecimal cdAutore;
	
	/** codice autore sec. */
	private BigDecimal cdAutoreSec;
	
	/** codice qualifica autore. */
	private BigDecimal cdQualificaAutore;
	
	/** codice qualifica autore sec. */
	private BigDecimal cdQualificaAutoreSec;
	
	/** codice editore. */
	private BigDecimal cdEditore;
	
	/** codice lingua. */
	private BigDecimal cdLingua;
	
	/** codice tipo identificativo. */
	private BigDecimal cdTipoIdentificativo;
	
	/** identificativo. */
	private String identificativo;
	
	/** luogo pubblicazione. */
	private String luogoPubblicazione;
	
	/** codice qualificatore data. */
	private BigDecimal cdQualificatoreData;
	
	/** data. */
	private String data;
	
	/** note. */
	private String note;
	
	/** codice supporto. */
	private BigDecimal cdSupporto;
	
	/** codice tecnica grafica. */
	private BigDecimal cdTecnicaGrafica;
	
	/** codice tipo grafica. */
	private BigDecimal cdTipoGrafica;
	
	/** scala. */
	private String scala;
	
	/** proiezione. */
	private String proiezione;
	
	/** coordinate. */
	private String coordinate;
	

	/** codice soggetto prod. */
	private BigDecimal cdSoggettoProd;
	
	/** codice contesto arch. */
	private BigDecimal cdContestoArch;
	
	/** codice tipo archivio. */
	private BigDecimal cdTipoArchivio;
	
	/** link catalogo. */
	private String linkCatalogo;
	
	/** diritti. */
	private String diritti;
	
	/** altreinfo. */
	private String altreinfo;

	/** codice progetto. */
	private BigDecimal cdProgetto;
	
	/** titolo progetto. */
	private String titoloProgetto;
	
	/** titolo collezione. */
	private String titoloCollezione;
	
	/** titolo oggetto originale sup. */
	private String titoloOggettoOriginaleSup;
	
	/** nome tipo oggetto. */
	private String nomeTipoOggetto;
	
	/** descrizione livello biblio. */
	private String descrizioneLivelloBiblio;
	
	/** descrizione soggetto. */
	private String descrizioneSoggetto;
	
	/** descrizione autore. */
	private String descrizioneAutore;
	
	/** descrizione autore sec. */
	private String descrizioneAutoreSec;
	
	/** descrizione qualifica autore. */
	private String descrizioneQualificaAutore;
	
	/** descrizione qualifica autore sec. */
	private String descrizioneQualificaAutoreSec;
	
	/** descrizione editore. */
	private String descrizioneEditore;
	
	/** descrizione lingua. */
	private String descrizioneLingua;
	
	/** descrizione tipo identificativo. */
	private String descrizioneTipoIdentificativo;
	
	/** descrizione qualificatore data. */
	private String descrizioneQualificatoreData;
	
	/** descrizione supporto. */
	private String descrizioneSupporto;
	
	/** descrizione tecnica grafica. */
	private String descrizioneTecnicaGrafica;
	
	/** descrizione tipo grafica. */
	private String descrizioneTipoGrafica;
	
	/** descrizione soggetto prod. */
	private String descrizioneSoggettoProd;
	
	/** descrizione contesto arch. */
	private String descrizioneContestoArch;
	
	/** descrizione tipo archivio. */
	private String descrizioneTipoArchivio;
	
	/** data pubblicatoil. */
	private Date dtPubblicatoil;
	
	/** nr rilevanza. */
	private BigDecimal nrRilevanza;
	
	/* segnatura */
	private String segnatura;
	
	/**
	 * Istanzia un nuovo oggetto dto.
	 */
	public OggettoDTO() {
	}

	/**
	 * Istanzia un nuovo oggetto dto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param cdCollezione  codice collezione
	 * @param titolo  titolo
	 * @param oggettoSuperiore  oggetto superiore
	 * @param immaginiPreviste  immagini previste
	 */
	public OggettoDTO(
			BigDecimal cdOggettoOriginale, 
			BigDecimal cdTipoOggetto, 
			BigDecimal cdOggettoOriginaleSup, 
			BigDecimal cdCollezione, 
			String titolo, 
			Boolean oggettoSuperiore,
			BigDecimal immaginiPreviste
		) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.cdCollezione = cdCollezione;
		this.titolo = titolo;
		this.titoloFe = titolo;
		this.oggettoSuperiore = oggettoSuperiore;
		this.immaginiPreviste = immaginiPreviste;
	}
	
	/**
	 * Istanzia un nuovo oggetto dto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param nomeTipoOggetto  nome tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param titoloOggettoOriginaleSup  titolo oggetto originale sup
	 * @param cdProgetto  codice progetto
	 * @param titoloProgetto  titolo progetto
	 * @param cdCollezione  codice collezione
	 * @param titoloCollezione  titolo collezione
	 * @param titolo  titolo
	 * @param oggettoSuperiore  oggetto superiore
	 * @param oggettiInferiori  oggetti inferiori
	 * @param oggettoDigitale  oggetto digitale
	 * @param correzione  correzione
	 * @param notaCorrezione  nota correzione
	 * @param anomaliaImmagini  anomalia immagini
	 * @param logAnomalia  log anomalia
	 * @param stato  stato
	 * @param noteNonValidazione  note non validazione
	 * @param immaginiPreviste  immagini previste
	 * @param immaginiDigitalizzate  immagini digitalizzate
	 */
	public OggettoDTO(
			BigDecimal cdOggettoOriginale, 
			BigDecimal cdTipoOggetto, 
			String nomeTipoOggetto,
			BigDecimal cdOggettoOriginaleSup, 
			String titoloOggettoOriginaleSup, 
			BigDecimal cdProgetto,
			String titoloProgetto, 
			BigDecimal cdCollezione,
			String titoloCollezione, 
			String titolo, 
			Boolean oggettoSuperiore, 
			BigDecimal oggettiInferiori,
			Boolean oggettoDigitale, 
			Boolean correzione, 
			String notaCorrezione, 
			Boolean anomaliaImmagini,
			String logAnomalia, 
			String stato, 
			String noteNonValidazione, 
			BigDecimal immaginiPreviste,
			BigDecimal immaginiDigitalizzate
		) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.nomeTipoOggetto = nomeTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.titoloOggettoOriginaleSup = titoloOggettoOriginaleSup;
		this.cdProgetto = cdProgetto;
		this.titoloProgetto = titoloProgetto;
		this.cdCollezione = cdCollezione;
		this.titoloCollezione = titoloCollezione;
		this.titolo = titolo;
		this.titoloFe = titolo;
		this.oggettoSuperiore = oggettoSuperiore;
		this.oggettiInferiori = oggettiInferiori;
		this.oggettoDigitale = oggettoDigitale;
		this.correzione = correzione;
		this.notaCorrezione = notaCorrezione;
		this.anomaliaImmagini = anomaliaImmagini;
		this.logAnomalia = logAnomalia;
		this.stato = stato;
		this.noteNonValidazione = noteNonValidazione;
		this.immaginiPreviste = immaginiPreviste;
		this.immaginiDigitalizzate = immaginiDigitalizzate;
	}
	
	/**
	 * Istanzia un nuovo oggetto dto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param nomeTipoOggetto  nome tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param titoloOggettoOriginaleSup  titolo oggetto originale sup
	 * @param cdProgetto  codice progetto
	 * @param titoloProgetto  titolo progetto
	 * @param cdCollezione  codice collezione
	 * @param titoloCollezione  titolo collezione
	 * @param titolo  titolo
	 * @param oggettoSuperiore  oggetto superiore
	 * @param oggettiInferiori  oggetti inferiori
	 * @param oggettoDigitale  oggetto digitale
	 * @param correzione  correzione
	 * @param notaCorrezione  nota correzione
	 * @param anomaliaImmagini  anomalia immagini
	 * @param logAnomalia  log anomalia
	 * @param stato  stato
	 * @param noteNonValidazione  note non validazione
	 * @param immaginiPreviste  immagini previste
	 * @param immaginiDigitalizzate  immagini digitalizzate
	 * @param creatoda  creatoda
	 * @param creatoil  creatoil
	 * @param modificatoda  modificatoda
	 * @param modificatoil  modificatoil
	 */
	public OggettoDTO(BigDecimal cdOggettoOriginale, BigDecimal cdTipoOggetto, String nomeTipoOggetto,
			BigDecimal cdOggettoOriginaleSup, String titoloOggettoOriginaleSup, 
			BigDecimal cdProgetto,
			String titoloProgetto, 
			BigDecimal cdCollezione,
			String titoloCollezione, 
			String titolo, Boolean oggettoSuperiore, BigDecimal oggettiInferiori,
			Boolean oggettoDigitale, Boolean correzione, String notaCorrezione, Boolean anomaliaImmagini,
			String logAnomalia, String stato, String noteNonValidazione, BigDecimal immaginiPreviste,
			BigDecimal immaginiDigitalizzate, String creatoda, Date creatoil, String modificatoda, Date modificatoil) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.nomeTipoOggetto = nomeTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.titoloOggettoOriginaleSup = titoloOggettoOriginaleSup;
		this.cdProgetto = cdProgetto;
		this.titoloProgetto = titoloProgetto;
		this.cdCollezione = cdCollezione;
		this.titoloCollezione = titoloCollezione;
		this.titolo = titolo;
		this.titoloFe = titolo;
		this.oggettoSuperiore = oggettoSuperiore;
		this.oggettiInferiori = oggettiInferiori;
		this.oggettoDigitale = oggettoDigitale;
		this.correzione = correzione;
		this.notaCorrezione = notaCorrezione;
		this.anomaliaImmagini = anomaliaImmagini;
		this.logAnomalia = logAnomalia;
		this.stato = stato;
		this.noteNonValidazione = noteNonValidazione;
		this.immaginiPreviste = immaginiPreviste;
		this.immaginiDigitalizzate = immaginiDigitalizzate;
		setCreatoda(creatoda);
		setCreatoil(creatoil);
		setModificatoda(modificatoda);
		setModificatoil(modificatoil);
	}
	
	/**
	 * Istanzia un nuovo oggetto dto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param nomeTipoOggetto  nome tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param titoloOggettoOriginaleSup  titolo oggetto originale sup
	 * @param cdProgetto  codice progetto
	 * @param titoloProgetto  titolo progetto
	 * @param cdCollezione  codice collezione
	 * @param titoloCollezione  titolo collezione
	 * @param titolo  titolo
	 * @param oggettoSuperiore  oggetto superiore
	 * @param oggettiInferiori  oggetti inferiori
	 * @param oggettoDigitale  oggetto digitale
	 * @param correzione  correzione
	 * @param notaCorrezione  nota correzione
	 * @param anomaliaImmagini  anomalia immagini
	 * @param logAnomalia  log anomalia
	 * @param stato  stato
	 * @param noteNonValidazione  note non validazione
	 * @param noteNonPubblicazione  note non pubblicazione
	 * @param immaginiPreviste  immagini previste
	 * @param immaginiDigitalizzate  immagini digitalizzate
	 * @param contenutistica  contenutistica
	 * @param fisica  fisica
	 * @param cdLivelloBiblio  codice livello biblio
	 * @param descrizioneLivelloBiblio  descrizione livello biblio
	 * @param cdSoggetto  codice soggetto
	 * @param descrizioneSoggetto  descrizione soggetto
	 * @param cdAutore  codice autore
	 * @param descrizioneAutore  descrizione autore
	 * @param cdAutoreSec  codice autore sec
	 * @param descrizioneAutoreSec  descrizione autore sec
	 * @param cdQualificaAutore  codice qualifica autore
	 * @param descrizioneQualificaAutore  descrizione qualifica autore
	 * @param cdQualificaAutoreSec  codice qualifica autore sec
	 * @param descrizioneQualificaAutoreSec  descrizione qualifica autore sec
	 * @param cdEditore  codice editore
	 * @param descrizioneEditore  descrizione editore
	 * @param cdLingua  codice lingua
	 * @param descrizioneLingua  descrizione lingua
	 * @param cdTipoIdentificativo  codice tipo identificativo
	 * @param descrizioneTipoIdentificativo  descrizione tipo identificativo
	 * @param identificativo  identificativo
	 * @param luogoPubblicazione  luogo pubblicazione
	 * @param cdQualificatoreData  codice qualificatore data
	 * @param descrizioneQualificatoreData  descrizione qualificatore data
	 * @param data  data
	 * @param note  note
	 * @param cdSupporto  codice supporto
	 * @param descrizioneSupporto  descrizione supporto
	 * @param cdTecnicaGrafica  codice tecnica grafica
	 * @param descrizioneTecnicaGrafica  descrizione tecnica grafica
	 * @param cdTipoGrafica  codice tipo grafica
	 * @param descrizioneTipoGrafica  descrizione tipo grafica
	 * @param scala  scala
	 * @param proiezione  proiezione
	 * @param coordinate  coordinate
	 * @param cdSoggettoProd  codice soggetto prod
	 * @param descrizioneSoggettoProd  descrizione soggetto prod
	 * @param cdContestoArch  codice contesto arch
	 * @param descrizioneContestoArch  descrizione contesto arch
	 * @param cdTipoArchivio  codice tipo archivio
	 * @param descrizioneTipoArchivio  descrizione tipo archivio
	 * @param linkCatalogo  link catalogo
	 * @param diritti  diritti
	 * @param altreinfo  altreinfo
	 * @param creatoda  creatoda
	 * @param creatoil  creatoil
	 * @param modificatoda  modificatoda
	 * @param modificatoil  modificatoil
	 * @param dtPubblicatoil  data pubblicatoil
	 * @param nrRilevanza  nr rilevanza
	 */
	public OggettoDTO(BigDecimal cdOggettoOriginale, BigDecimal cdTipoOggetto, String nomeTipoOggetto, BigDecimal cdOggettoOriginaleSup,
			String titoloOggettoOriginaleSup, BigDecimal cdProgetto, String titoloProgetto, BigDecimal cdCollezione, String titoloCollezione, String titolo,String titoloFe,
			Boolean oggettoSuperiore, BigDecimal oggettiInferiori, Boolean oggettoDigitale, Boolean correzione, String notaCorrezione,
			Boolean anomaliaImmagini, String logAnomalia, String stato, String noteNonValidazione, String noteNonPubblicazione, BigDecimal immaginiPreviste,
			BigDecimal immaginiDigitalizzate, String contenutistica, String fisica, BigDecimal cdLivelloBiblio, String descrizioneLivelloBiblio,
			BigDecimal cdSoggetto, String descrizioneSoggetto, BigDecimal cdAutore, String descrizioneAutore, BigDecimal cdAutoreSec,
			String descrizioneAutoreSec, BigDecimal cdQualificaAutore, String descrizioneQualificaAutore, BigDecimal cdQualificaAutoreSec,
			String descrizioneQualificaAutoreSec, BigDecimal cdEditore, String descrizioneEditore, BigDecimal cdLingua, String descrizioneLingua,
			BigDecimal cdTipoIdentificativo, String descrizioneTipoIdentificativo, String identificativo, String luogoPubblicazione,
			BigDecimal cdQualificatoreData, String descrizioneQualificatoreData, String data, String note, BigDecimal cdSupporto,
			String descrizioneSupporto, BigDecimal cdTecnicaGrafica, String descrizioneTecnicaGrafica, BigDecimal cdTipoGrafica,
			String descrizioneTipoGrafica, String scala, String proiezione, String coordinate, BigDecimal cdSoggettoProd,
			String descrizioneSoggettoProd, BigDecimal cdContestoArch, String descrizioneContestoArch, BigDecimal cdTipoArchivio,
			String descrizioneTipoArchivio, String linkCatalogo, String diritti, String altreinfo, String creatoda, Date creatoil, String modificatoda, Date modificatoil,
			Date dtPubblicatoil, BigDecimal nrRilevanza) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.nomeTipoOggetto = nomeTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.titoloOggettoOriginaleSup = titoloOggettoOriginaleSup;
		this.cdProgetto = cdProgetto;
		this.titoloProgetto = titoloProgetto;
		this.cdCollezione = cdCollezione;
		this.titoloCollezione = titoloCollezione;
		this.titolo = titolo;
		this.titoloFe = titoloFe;
		this.oggettoSuperiore = oggettoSuperiore;
		this.oggettiInferiori = oggettiInferiori;
		this.oggettoDigitale = oggettoDigitale;
		this.correzione = correzione;
		this.notaCorrezione = notaCorrezione;
		this.anomaliaImmagini = anomaliaImmagini;
		this.logAnomalia = logAnomalia;
		this.stato = stato;
		this.noteNonValidazione = noteNonValidazione;
		this.noteNonPubblicazione = noteNonPubblicazione;
		this.immaginiPreviste = immaginiPreviste;
		this.immaginiDigitalizzate = immaginiDigitalizzate;
		this.contenutistica = contenutistica;
		this.fisica = fisica;
		this.cdLivelloBiblio = cdLivelloBiblio;
		this.descrizioneLivelloBiblio = descrizioneLivelloBiblio;
		this.cdSoggetto = cdSoggetto;
		this.descrizioneSoggetto = descrizioneSoggetto;
		this.cdAutore = cdAutore;
		this.descrizioneAutore = descrizioneAutore;
		this.cdAutoreSec = cdAutoreSec;
		this.descrizioneAutoreSec = descrizioneAutoreSec;
		this.cdQualificaAutore = cdQualificaAutore;
		this.descrizioneQualificaAutore = descrizioneQualificaAutore;
		this.cdQualificaAutoreSec = cdQualificaAutoreSec;
		this.descrizioneQualificaAutoreSec = descrizioneQualificaAutoreSec;
		this.cdEditore = cdEditore;
		this.descrizioneEditore = descrizioneEditore;
		this.cdLingua = cdLingua;
		this.descrizioneLingua = descrizioneLingua;
		this.cdTipoIdentificativo = cdTipoIdentificativo;
		this.descrizioneTipoIdentificativo = descrizioneTipoIdentificativo;
		this.identificativo = identificativo;
		this.luogoPubblicazione = luogoPubblicazione;
		this.cdQualificatoreData = cdQualificatoreData;
		this.descrizioneQualificatoreData = descrizioneQualificatoreData;
		this.data = data;
		this.note = note;
		this.cdSupporto = cdSupporto;
		this.descrizioneSupporto = descrizioneSupporto;
		this.cdTecnicaGrafica = cdTecnicaGrafica;
		this.descrizioneTecnicaGrafica = descrizioneTecnicaGrafica;
		this.cdTipoGrafica = cdTipoGrafica;
		this.descrizioneTipoGrafica = descrizioneTipoGrafica;
		this.scala = scala;
		this.proiezione = proiezione;
		this.coordinate = coordinate;
		this.cdSoggettoProd = cdSoggettoProd;
		this.descrizioneSoggettoProd = descrizioneSoggettoProd;
		this.cdContestoArch = cdContestoArch;
		this.descrizioneContestoArch = descrizioneContestoArch;
		this.cdTipoArchivio = cdTipoArchivio;
		this.descrizioneTipoArchivio = descrizioneTipoArchivio;
		this.linkCatalogo = linkCatalogo;
		this.diritti = diritti;
		this.altreinfo = altreinfo;
		setCreatoda(creatoda);
		setCreatoil(creatoil);
		setModificatoda(modificatoda);
		setModificatoil(modificatoil);
		this.dtPubblicatoil = dtPubblicatoil;
		this.nrRilevanza = nrRilevanza;
	}

	/**
	 * Legge codice oggetto originale.
	 *
	 * @return codice oggetto originale
	 */
	public BigDecimal getCdOggettoOriginale() {
		return cdOggettoOriginale;
	}

	/**
	 * Imposta codice oggetto originale.
	 *
	 * @param cdOggettoOriginale nuovo codice oggetto originale
	 */
	public void setCdOggettoOriginale(BigDecimal cdOggettoOriginale) {
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Legge codice tipo oggetto.
	 *
	 * @return codice tipo oggetto
	 */
	public BigDecimal getCdTipoOggetto() {
		return cdTipoOggetto;
	}

	/**
	 * Imposta codice tipo oggetto.
	 *
	 * @param cdTipoOggetto nuovo codice tipo oggetto
	 */
	public void setCdTipoOggetto(BigDecimal cdTipoOggetto) {
		this.cdTipoOggetto = cdTipoOggetto;
	}

	/**
	 * Legge codice oggetto originale sup.
	 *
	 * @return codice oggetto originale sup
	 */
	public BigDecimal getCdOggettoOriginaleSup() {
		return cdOggettoOriginaleSup;
	}

	/**
	 * Imposta codice oggetto originale sup.
	 *
	 * @param cdOggettoOriginaleSup nuovo codice oggetto originale sup
	 */
	public void setCdOggettoOriginaleSup(BigDecimal cdOggettoOriginaleSup) {
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
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
	 * Legge oggetto superiore.
	 *
	 * @return oggetto superiore
	 */
	public Boolean getOggettoSuperiore() {
		return oggettoSuperiore;
	}

	/**
	 * Imposta oggetto superiore.
	 *
	 * @param oggettoSuperiore nuovo oggetto superiore
	 */
	public void setOggettoSuperiore(Boolean oggettoSuperiore) {
		this.oggettoSuperiore = oggettoSuperiore;
	}

	/**
	 * Legge oggetti inferiori.
	 *
	 * @return oggetti inferiori
	 */
	public BigDecimal getOggettiInferiori() {
		return oggettiInferiori;
	}

	/**
	 * Imposta oggetti inferiori.
	 *
	 * @param oggettiInferiori nuovo oggetti inferiori
	 */
	public void setOggettiInferiori(BigDecimal oggettiInferiori) {
		this.oggettiInferiori = oggettiInferiori;
	}

	/**
	 * Legge oggetto digitale.
	 *
	 * @return oggetto digitale
	 */
	public Boolean getOggettoDigitale() {
		return oggettoDigitale;
	}

	/**
	 * Imposta oggetto digitale.
	 *
	 * @param oggettoDigitale nuovo oggetto digitale
	 */
	public void setOggettoDigitale(Boolean oggettoDigitale) {
		this.oggettoDigitale = oggettoDigitale;
	}

	/**
	 * Legge correzione.
	 *
	 * @return correzione
	 */
	public Boolean getCorrezione() {
		return correzione;
	}

	/**
	 * Imposta correzione.
	 *
	 * @param correzione nuovo correzione
	 */
	public void setCorrezione(Boolean correzione) {
		this.correzione = correzione;
	}

	/**
	 * Legge nota correzione.
	 *
	 * @return nota correzione
	 */
	public String getNotaCorrezione() {
		return notaCorrezione;
	}

	/**
	 * Imposta nota correzione.
	 *
	 * @param notaCorrezione nuovo nota correzione
	 */
	public void setNotaCorrezione(String notaCorrezione) {
		this.notaCorrezione = notaCorrezione;
	}

	/**
	 * Legge anomalia immagini.
	 *
	 * @return anomalia immagini
	 */
	public Boolean getAnomaliaImmagini() {
		return anomaliaImmagini;
	}

	/**
	 * Imposta anomalia immagini.
	 *
	 * @param anomaliaImmagini nuovo anomalia immagini
	 */
	public void setAnomaliaImmagini(Boolean anomaliaImmagini) {
		this.anomaliaImmagini = anomaliaImmagini;
	}

	/**
	 * Legge log anomalia.
	 *
	 * @return log anomalia
	 */
	public String getLogAnomalia() {
		return logAnomalia;
	}

	/**
	 * Imposta log anomalia.
	 *
	 * @param logAnomalia nuovo log anomalia
	 */
	public void setLogAnomalia(String logAnomalia) {
		this.logAnomalia = logAnomalia;
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
	 * Legge note non validazione.
	 *
	 * @return note non validazione
	 */
	public String getNoteNonValidazione() {
		return noteNonValidazione;
	}

	/**
	 * Imposta note non validazione.
	 *
	 * @param noteNonValidazione nuovo note non validazione
	 */
	public void setNoteNonValidazione(String noteNonValidazione) {
		this.noteNonValidazione = noteNonValidazione;
	}

	/**
	 * Legge note non pubblicazione.
	 *
	 * @return note non pubblicazione
	 */
	public String getNoteNonPubblicazione() {
		return noteNonPubblicazione;
	}

	/**
	 * Imposta note non pubblicazione.
	 *
	 * @param noteNonPubblicazione nuovo note non pubblicazione
	 */
	public void setNoteNonPubblicazione(String noteNonPubblicazione) {
		this.noteNonPubblicazione = noteNonPubblicazione;
	}
	
	/**
	 * Legge immagini previste.
	 *
	 * @return immagini previste
	 */
	public BigDecimal getImmaginiPreviste() {
		return immaginiPreviste;
	}

	/**
	 * Imposta immagini previste.
	 *
	 * @param immaginiPreviste nuovo immagini previste
	 */
	public void setImmaginiPreviste(BigDecimal immaginiPreviste) {
		this.immaginiPreviste = immaginiPreviste;
	}

	/**
	 * Legge immagini digitalizzate.
	 *
	 * @return immagini digitalizzate
	 */
	public BigDecimal getImmaginiDigitalizzate() {
		return immaginiDigitalizzate;
	}

	/**
	 * Imposta immagini digitalizzate.
	 *
	 * @param immaginiDigitalizzate nuovo immagini digitalizzate
	 */
	public void setImmaginiDigitalizzate(BigDecimal immaginiDigitalizzate) {
		this.immaginiDigitalizzate = immaginiDigitalizzate;
	}

	/**
	 * Legge titolo collezione.
	 *
	 * @return titolo collezione
	 */
	public String getTitoloCollezione() {
		return titoloCollezione;
	}

	/**
	 * Imposta titolo collezione.
	 *
	 * @param titoloCollezione nuovo titolo collezione
	 */
	public void setTitoloCollezione(String titoloCollezione) {
		this.titoloCollezione = titoloCollezione;
	}

	/**
	 * Legge titolo oggetto originale sup.
	 *
	 * @return titolo oggetto originale sup
	 */
	public String getTitoloOggettoOriginaleSup() {
		return titoloOggettoOriginaleSup;
	}

	/**
	 * Imposta titolo oggetto originale sup.
	 *
	 * @param titoloOggettoOriginaleSup nuovo titolo oggetto originale sup
	 */
	public void setTitoloOggettoOriginaleSup(String titoloOggettoOriginaleSup) {
		this.titoloOggettoOriginaleSup = titoloOggettoOriginaleSup;
	}

	/**
	 * Legge nome tipo oggetto.
	 *
	 * @return nome tipo oggetto
	 */
	public String getNomeTipoOggetto() {
		return nomeTipoOggetto;
	}

	/**
	 * Imposta nome tipo oggetto.
	 *
	 * @param nomeTipoOggetto nuovo nome tipo oggetto
	 */
	public void setNomeTipoOggetto(String nomeTipoOggetto) {
		this.nomeTipoOggetto = nomeTipoOggetto;
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
	 * Legge contenutistica.
	 *
	 * @return contenutistica
	 */
	public String getContenutistica() {
		return contenutistica;
	}

	/**
	 * Imposta contenutistica.
	 *
	 * @param contenutistica nuovo contenutistica
	 */
	public void setContenutistica(String contenutistica) {
		this.contenutistica = contenutistica;
	}

	/**
	 * Legge fisica.
	 *
	 * @return fisica
	 */
	public String getFisica() {
		return fisica;
	}

	/**
	 * Imposta fisica.
	 *
	 * @param fisica nuovo fisica
	 */
	public void setFisica(String fisica) {
		this.fisica = fisica;
	}

	/**
	 * Legge codice livello biblio.
	 *
	 * @return codice livello biblio
	 */
	public BigDecimal getCdLivelloBiblio() {
		return cdLivelloBiblio;
	}

	/**
	 * Imposta codice livello biblio.
	 *
	 * @param cdLivelloBiblio nuovo codice livello biblio
	 */
	public void setCdLivelloBiblio(BigDecimal cdLivelloBiblio) {
		this.cdLivelloBiblio = cdLivelloBiblio;
	}

	/**
	 * Legge codice soggetto.
	 *
	 * @return codice soggetto
	 */
	public BigDecimal getCdSoggetto() {
		return cdSoggetto;
	}

	/**
	 * Imposta codice soggetto.
	 *
	 * @param cdSoggetto nuovo codice soggetto
	 */
	public void setCdSoggetto(BigDecimal cdSoggetto) {
		this.cdSoggetto = cdSoggetto;
	}

	/**
	 * Legge codice autore.
	 *
	 * @return codice autore
	 */
	public BigDecimal getCdAutore() {
		return cdAutore;
	}

	/**
	 * Imposta codice autore.
	 *
	 * @param cdAutore nuovo codice autore
	 */
	public void setCdAutore(BigDecimal cdAutore) {
		this.cdAutore = cdAutore;
	}

	/**
	 * Legge codice autore sec.
	 *
	 * @return codice autore sec
	 */
	public BigDecimal getCdAutoreSec() {
		return cdAutoreSec;
	}

	/**
	 * Imposta codice autore sec.
	 *
	 * @param cdAutoreSec nuovo codice autore sec
	 */
	public void setCdAutoreSec(BigDecimal cdAutoreSec) {
		this.cdAutoreSec = cdAutoreSec;
	}

	/**
	 * Legge codice qualifica autore.
	 *
	 * @return codice qualifica autore
	 */
	public BigDecimal getCdQualificaAutore() {
		return cdQualificaAutore;
	}

	/**
	 * Imposta codice qualifica autore.
	 *
	 * @param cdQualificaAutore nuovo codice qualifica autore
	 */
	public void setCdQualificaAutore(BigDecimal cdQualificaAutore) {
		this.cdQualificaAutore = cdQualificaAutore;
	}

	/**
	 * Legge codice qualifica autore sec.
	 *
	 * @return codice qualifica autore sec
	 */
	public BigDecimal getCdQualificaAutoreSec() {
		return cdQualificaAutoreSec;
	}

	/**
	 * Imposta codice qualifica autore sec.
	 *
	 * @param cdQualificaAutoreSec nuovo codice qualifica autore sec
	 */
	public void setCdQualificaAutoreSec(BigDecimal cdQualificaAutoreSec) {
		this.cdQualificaAutoreSec = cdQualificaAutoreSec;
	}

	/**
	 * Legge codice editore.
	 *
	 * @return codice editore
	 */
	public BigDecimal getCdEditore() {
		return cdEditore;
	}

	/**
	 * Imposta codice editore.
	 *
	 * @param cdEditore nuovo codice editore
	 */
	public void setCdEditore(BigDecimal cdEditore) {
		this.cdEditore = cdEditore;
	}

	/**
	 * Legge codice lingua.
	 *
	 * @return codice lingua
	 */
	public BigDecimal getCdLingua() {
		return cdLingua;
	}

	/**
	 * Imposta codice lingua.
	 *
	 * @param cdLingua nuovo codice lingua
	 */
	public void setCdLingua(BigDecimal cdLingua) {
		this.cdLingua = cdLingua;
	}

	/**
	 * Legge codice tipo identificativo.
	 *
	 * @return codice tipo identificativo
	 */
	public BigDecimal getCdTipoIdentificativo() {
		return cdTipoIdentificativo;
	}

	/**
	 * Imposta codice tipo identificativo.
	 *
	 * @param cdTipoIdentificativo nuovo codice tipo identificativo
	 */
	public void setCdTipoIdentificativo(BigDecimal cdTipoIdentificativo) {
		this.cdTipoIdentificativo = cdTipoIdentificativo;
	}

	/**
	 * Legge identificativo.
	 *
	 * @return identificativo
	 */
	public String getIdentificativo() {
		return identificativo;
	}

	/**
	 * Imposta identificativo.
	 *
	 * @param identificativo nuovo identificativo
	 */
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	/**
	 * Legge luogo pubblicazione.
	 *
	 * @return luogo pubblicazione
	 */
	public String getLuogoPubblicazione() {
		return luogoPubblicazione;
	}

	/**
	 * Imposta luogo pubblicazione.
	 *
	 * @param luogoPubblicazione nuovo luogo pubblicazione
	 */
	public void setLuogoPubblicazione(String luogoPubblicazione) {
		this.luogoPubblicazione = luogoPubblicazione;
	}

	/**
	 * Legge codice qualificatore data.
	 *
	 * @return codice qualificatore data
	 */
	public BigDecimal getCdQualificatoreData() {
		return cdQualificatoreData;
	}

	/**
	 * Imposta codice qualificatore data.
	 *
	 * @param cdQualificatoreData nuovo codice qualificatore data
	 */
	public void setCdQualificatoreData(BigDecimal cdQualificatoreData) {
		this.cdQualificatoreData = cdQualificatoreData;
	}

	/**
	 * Legge data.
	 *
	 * @return data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Imposta data.
	 *
	 * @param data nuovo data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Legge note.
	 *
	 * @return note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Imposta note.
	 *
	 * @param note nuovo note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Legge codice supporto.
	 *
	 * @return codice supporto
	 */
	public BigDecimal getCdSupporto() {
		return cdSupporto;
	}

	/**
	 * Imposta codice supporto.
	 *
	 * @param cdSupporto nuovo codice supporto
	 */
	public void setCdSupporto(BigDecimal cdSupporto) {
		this.cdSupporto = cdSupporto;
	}

	/**
	 * Legge codice tecnica grafica.
	 *
	 * @return codice tecnica grafica
	 */
	public BigDecimal getCdTecnicaGrafica() {
		return cdTecnicaGrafica;
	}

	/**
	 * Imposta codice tecnica grafica.
	 *
	 * @param cdTecnicaGrafica nuovo codice tecnica grafica
	 */
	public void setCdTecnicaGrafica(BigDecimal cdTecnicaGrafica) {
		this.cdTecnicaGrafica = cdTecnicaGrafica;
	}

	/**
	 * Legge codice tipo grafica.
	 *
	 * @return codice tipo grafica
	 */
	public BigDecimal getCdTipoGrafica() {
		return cdTipoGrafica;
	}

	/**
	 * Imposta codice tipo grafica.
	 *
	 * @param cdTipoGrafica nuovo codice tipo grafica
	 */
	public void setCdTipoGrafica(BigDecimal cdTipoGrafica) {
		this.cdTipoGrafica = cdTipoGrafica;
	}

	/**
	 * Legge scala.
	 *
	 * @return scala
	 */
	public String getScala() {
		return scala;
	}

	/**
	 * Imposta scala.
	 *
	 * @param scala nuovo scala
	 */
	public void setScala(String scala) {
		this.scala = scala;
	}

	/**
	 * Legge proiezione.
	 *
	 * @return proiezione
	 */
	public String getProiezione() {
		return proiezione;
	}

	/**
	 * Imposta proiezione.
	 *
	 * @param proiezione nuovo proiezione
	 */
	public void setProiezione(String proiezione) {
		this.proiezione = proiezione;
	}

	/**
	 * Legge coordinate.
	 *
	 * @return coordinate
	 */
	public String getCoordinate() {
		return coordinate;
	}

	/**
	 * Imposta coordinate.
	 *
	 * @param coordinate nuovo coordinate
	 */
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Legge codice soggetto prod.
	 *
	 * @return codice soggetto prod
	 */
	public BigDecimal getCdSoggettoProd() {
		return cdSoggettoProd;
	}

	/**
	 * Imposta codice soggetto prod.
	 *
	 * @param cdSoggettoProd nuovo codice soggetto prod
	 */
	public void setCdSoggettoProd(BigDecimal cdSoggettoProd) {
		this.cdSoggettoProd = cdSoggettoProd;
	}

	/**
	 * Legge codice contesto arch.
	 *
	 * @return codice contesto arch
	 */
	public BigDecimal getCdContestoArch() {
		return cdContestoArch;
	}

	/**
	 * Imposta codice contesto arch.
	 *
	 * @param cdContestoArch nuovo codice contesto arch
	 */
	public void setCdContestoArch(BigDecimal cdContestoArch) {
		this.cdContestoArch = cdContestoArch;
	}

	/**
	 * Legge codice tipo archivio.
	 *
	 * @return codice tipo archivio
	 */
	public BigDecimal getCdTipoArchivio() {
		return cdTipoArchivio;
	}

	/**
	 * Imposta codice tipo archivio.
	 *
	 * @param cdTipoArchivio nuovo codice tipo archivio
	 */
	public void setCdTipoArchivio(BigDecimal cdTipoArchivio) {
		this.cdTipoArchivio = cdTipoArchivio;
	}

	/**
	 * Legge link catalogo.
	 *
	 * @return link catalogo
	 */
	public String getLinkCatalogo() {
		return linkCatalogo;
	}

	/**
	 * Imposta link catalogo.
	 *
	 * @param linkCatalogo nuovo link catalogo
	 */
	public void setLinkCatalogo(String linkCatalogo) {
		this.linkCatalogo = linkCatalogo;
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
	 * Legge altreinfo.
	 *
	 * @return altreinfo
	 */
	public String getAltreinfo() {
		return altreinfo;
	}

	/**
	 * Imposta altreinfo.
	 *
	 * @param altreinfo nuovo altreinfo
	 */
	public void setAltreinfo(String altreinfo) {
		this.altreinfo = altreinfo;
	}

	/**
	 * Legge descrizione livello biblio.
	 *
	 * @return descrizione livello biblio
	 */
	public String getDescrizioneLivelloBiblio() {
		return descrizioneLivelloBiblio;
	}

	/**
	 * Imposta descrizione livello biblio.
	 *
	 * @param descrizioneLivelloBiblio nuovo descrizione livello biblio
	 */
	public void setDescrizioneLivelloBiblio(String descrizioneLivelloBiblio) {
		this.descrizioneLivelloBiblio = descrizioneLivelloBiblio;
	}

	/**
	 * Legge descrizione soggetto.
	 *
	 * @return descrizione soggetto
	 */
	public String getDescrizioneSoggetto() {
		return descrizioneSoggetto;
	}

	/**
	 * Imposta descrizione soggetto.
	 *
	 * @param descrizioneSoggetto nuovo descrizione soggetto
	 */
	public void setDescrizioneSoggetto(String descrizioneSoggetto) {
		this.descrizioneSoggetto = descrizioneSoggetto;
	}

	/**
	 * Legge descrizione autore.
	 *
	 * @return descrizione autore
	 */
	public String getDescrizioneAutore() {
		return descrizioneAutore;
	}

	/**
	 * Imposta descrizione autore.
	 *
	 * @param descrizioneAutore nuovo descrizione autore
	 */
	public void setDescrizioneAutore(String descrizioneAutore) {
		this.descrizioneAutore = descrizioneAutore;
	}

	/**
	 * Legge descrizione autore sec.
	 *
	 * @return descrizione autore sec
	 */
	public String getDescrizioneAutoreSec() {
		return descrizioneAutoreSec;
	}

	/**
	 * Imposta descrizione autore sec.
	 *
	 * @param descrizioneAutoreSec nuovo descrizione autore sec
	 */
	public void setDescrizioneAutoreSec(String descrizioneAutoreSec) {
		this.descrizioneAutoreSec = descrizioneAutoreSec;
	}

	/**
	 * Legge descrizione qualifica autore.
	 *
	 * @return descrizione qualifica autore
	 */
	public String getDescrizioneQualificaAutore() {
		return descrizioneQualificaAutore;
	}

	/**
	 * Imposta descrizione qualifica autore.
	 *
	 * @param descrizioneQualificaAutore nuovo descrizione qualifica autore
	 */
	public void setDescrizioneQualificaAutore(String descrizioneQualificaAutore) {
		this.descrizioneQualificaAutore = descrizioneQualificaAutore;
	}

	/**
	 * Legge descrizione qualifica autore sec.
	 *
	 * @return descrizione qualifica autore sec
	 */
	public String getDescrizioneQualificaAutoreSec() {
		return descrizioneQualificaAutoreSec;
	}

	/**
	 * Imposta descrizione qualifica autore sec.
	 *
	 * @param descrizioneQualificaAutoreSec nuovo descrizione qualifica autore sec
	 */
	public void setDescrizioneQualificaAutoreSec(String descrizioneQualificaAutoreSec) {
		this.descrizioneQualificaAutoreSec = descrizioneQualificaAutoreSec;
	}

	/**
	 * Legge descrizione editore.
	 *
	 * @return descrizione editore
	 */
	public String getDescrizioneEditore() {
		return descrizioneEditore;
	}

	/**
	 * Imposta descrizione editore.
	 *
	 * @param descrizioneEditore nuovo descrizione editore
	 */
	public void setDescrizioneEditore(String descrizioneEditore) {
		this.descrizioneEditore = descrizioneEditore;
	}

	/**
	 * Legge descrizione lingua.
	 *
	 * @return descrizione lingua
	 */
	public String getDescrizioneLingua() {
		return descrizioneLingua;
	}

	/**
	 * Imposta descrizione lingua.
	 *
	 * @param descrizioneLingua nuovo descrizione lingua
	 */
	public void setDescrizioneLingua(String descrizioneLingua) {
		this.descrizioneLingua = descrizioneLingua;
	}

	/**
	 * Legge descrizione tipo identificativo.
	 *
	 * @return descrizione tipo identificativo
	 */
	public String getDescrizioneTipoIdentificativo() {
		return descrizioneTipoIdentificativo;
	}

	/**
	 * Imposta descrizione tipo identificativo.
	 *
	 * @param descrizioneTipoIdentificativo nuovo descrizione tipo identificativo
	 */
	public void setDescrizioneTipoIdentificativo(String descrizioneTipoIdentificativo) {
		this.descrizioneTipoIdentificativo = descrizioneTipoIdentificativo;
	}

	/**
	 * Legge descrizione qualificatore data.
	 *
	 * @return descrizione qualificatore data
	 */
	public String getDescrizioneQualificatoreData() {
		return descrizioneQualificatoreData;
	}

	/**
	 * Imposta descrizione qualificatore data.
	 *
	 * @param descrizioneQualificatoreData nuovo descrizione qualificatore data
	 */
	public void setDescrizioneQualificatoreData(String descrizioneQualificatoreData) {
		this.descrizioneQualificatoreData = descrizioneQualificatoreData;
	}

	/**
	 * Legge descrizione supporto.
	 *
	 * @return descrizione supporto
	 */
	public String getDescrizioneSupporto() {
		return descrizioneSupporto;
	}

	/**
	 * Imposta descrizione supporto.
	 *
	 * @param descrizioneSupporto nuovo descrizione supporto
	 */
	public void setDescrizioneSupporto(String descrizioneSupporto) {
		this.descrizioneSupporto = descrizioneSupporto;
	}

	/**
	 * Legge descrizione tecnica grafica.
	 *
	 * @return descrizione tecnica grafica
	 */
	public String getDescrizioneTecnicaGrafica() {
		return descrizioneTecnicaGrafica;
	}

	/**
	 * Imposta descrizione tecnica grafica.
	 *
	 * @param descrizioneTecnicaGrafica nuovo descrizione tecnica grafica
	 */
	public void setDescrizioneTecnicaGrafica(String descrizioneTecnicaGrafica) {
		this.descrizioneTecnicaGrafica = descrizioneTecnicaGrafica;
	}

	/**
	 * Legge descrizione tipo grafica.
	 *
	 * @return descrizione tipo grafica
	 */
	public String getDescrizioneTipoGrafica() {
		return descrizioneTipoGrafica;
	}

	/**
	 * Imposta descrizione tipo grafica.
	 *
	 * @param descrizioneTipoGrafica nuovo descrizione tipo grafica
	 */
	public void setDescrizioneTipoGrafica(String descrizioneTipoGrafica) {
		this.descrizioneTipoGrafica = descrizioneTipoGrafica;
	}

	/**
	 * Legge descrizione soggetto prod.
	 *
	 * @return descrizione soggetto prod
	 */
	public String getDescrizioneSoggettoProd() {
		return descrizioneSoggettoProd;
	}

	/**
	 * Imposta descrizione soggetto prod.
	 *
	 * @param descrizioneSoggettoProd nuovo descrizione soggetto prod
	 */
	public void setDescrizioneSoggettoProd(String descrizioneSoggettoProd) {
		this.descrizioneSoggettoProd = descrizioneSoggettoProd;
	}

	/**
	 * Legge descrizione contesto arch.
	 *
	 * @return descrizione contesto arch
	 */
	public String getDescrizioneContestoArch() {
		return descrizioneContestoArch;
	}

	/**
	 * Imposta descrizione contesto arch.
	 *
	 * @param descrizioneContestoArch nuovo descrizione contesto arch
	 */
	public void setDescrizioneContestoArch(String descrizioneContestoArch) {
		this.descrizioneContestoArch = descrizioneContestoArch;
	}

	/**
	 * Legge descrizione tipo archivio.
	 *
	 * @return descrizione tipo archivio
	 */
	public String getDescrizioneTipoArchivio() {
		return descrizioneTipoArchivio;
	}

	/**
	 * Imposta descrizione tipo archivio.
	 *
	 * @param descrizioneTipoArchivio nuovo descrizione tipo archivio
	 */
	public void setDescrizioneTipoArchivio(String descrizioneTipoArchivio) {
		this.descrizioneTipoArchivio = descrizioneTipoArchivio;
	}

	/**
	 * Legge stato human.
	 *
	 * @return stato human
	 */
	public String getStatoHuman() {
		String statoHuman = null;
		if(stato!=null){
			if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO_HUMAN;
			}
		}
		return statoHuman;
	}

	/**
	 * Legge data pubblicatoil.
	 *
	 * @return data pubblicatoil
	 */
	public Date getDtPubblicatoil() {
		return dtPubblicatoil;
	}

	/**
	 * Imposta data pubblicatoil.
	 *
	 * @param dtPubblicatoil nuovo data pubblicatoil
	 */
	public void setDtPubblicatoil(Date dtPubblicatoil) {
		this.dtPubblicatoil = dtPubblicatoil;
	}

	/**
	 * Legge nr rilevanza.
	 *
	 * @return nr rilevanza
	 */
	public BigDecimal getNrRilevanza() {
		return nrRilevanza;
	}

	/**
	 * Imposta nr rilevanza.
	 *
	 * @param nrRilevanza nuovo nr rilevanza
	 */
	public void setNrRilevanza(BigDecimal nrRilevanza) {
		this.nrRilevanza = nrRilevanza;
	}

	public String getTitoloFe() {
		return titoloFe;
	}

	public void setTitoloFe(String titoloFe) {
		this.titoloFe = titoloFe;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	
}
