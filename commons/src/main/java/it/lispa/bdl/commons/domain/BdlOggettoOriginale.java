package it.lispa.bdl.commons.domain;

// Generated 29-nov-2013 10.47.17 by Hibernate Tools 3.6.0

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * Class BdlOggettoOriginale.
 */
@Entity
@Table(name = "BDL_OGGETTO_ORIGINALE")
public class BdlOggettoOriginale extends BdlDomainBase implements java.io.Serializable {

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

	/** denominazione titolo. */
	private String dnTitolo;
	
	/** denominazione titolo fe. */
	private String dnTitoloFe;
	
	/** flag oggetto superiore. */
	private Boolean flOggettoSuperiore;
	
	/** nr oggetti inferiori. */
	private BigDecimal nrOggettiInferiori;
	
	/** flag oggetto digitale. */
	private Boolean flOggettoDigitale;
	
	/** flag correzione. */
	private Boolean flCorrezione;
	
	/** descrizione nota correzione. */
	private String dsNotaCorrezione;
	
	/** flag anomalia immagini. */
	private Boolean flAnomaliaImmagini;
	
	/** descrizione log anomalia. */
	private String dsLogAnomalia;
	
	/** descrizione stato. */
	private String dsStato;
	
	/** descrizione note non validazione. */
	private String dsNoteNonValidazione;
	
	/** descrizione note non pubblicazione. */
	private String dsNoteNonPubblicazione;
	
	/** nr immagini previste. */
	private BigDecimal nrImmaginiPreviste;
	
	/** nr immagini digitalizzate. */
	private BigDecimal nrImmaginiDigitalizzate;

	/** descrizione contenutistica. */
	private String dsContenutistica;
	
	/** descrizione fisica. */
	private String dsFisica;
	
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
	
	/** codice identificativo. */
	private String cdIdentificativo;
	
	/** codice identificativo isbn. */
	private String cdIdentificativoIsbn;
	
	/** codice identificativo issn. */
	private String cdIdentificativoIssn;
	
	/** denominazione luogo pubblicazione. */
	private String dnLuogoPubblicazione;
	
	/** codice qualificatore data. */
	private BigDecimal cdQualificatoreData;
	
	/** descrizione data. */
	private String dsData;
	
	/** descrizione note. */
	private String dsNote;
	
	/** codice supporto. */
	private BigDecimal cdSupporto;
	
	/** codice tecnica grafica. */
	private BigDecimal cdTecnicaGrafica;
	
	/** codice tipo grafica. */
	private BigDecimal cdTipoGrafica;
	
	/** descrizione scala. */
	private String dsScala;
	
	/** descrizione proiezione. */
	private String dsProiezione;
	
	/** descrizione coordinate. */
	private String dsCoordinate;
	
	/** codice soggetto prod. */
	private BigDecimal cdSoggettoProd;
	
	/** codice contesto arch. */
	private BigDecimal cdContestoArch;
	
	/** codice tipo archivio. */
	private BigDecimal cdTipoArchivio;
	
	/** descrizione link catalogo. */
	private String dsLinkCatalogo;
	
	/** descrizione diritti. */
	private String dsDiritti;
	
	/** descrizione altreinfo. */
	private String dsAltreinfo;

	/** vl map max zoom level. */
	private BigDecimal vlMapMaxZoomLevel;
	
	/** vl map min zoom level. */
	private BigDecimal vlMapMinZoomLevel;
	
	/** vl map max zoom w. */
	private BigDecimal vlMapMaxZoomW;
	
	/** vl map max zoom h. */
	private BigDecimal vlMapMaxZoomH;
	
	/** vl map border w. */
	private BigDecimal vlMapBorderW;
	
	/** vl map border h. */
	private BigDecimal vlMapBorderH;
	
	/** data pubblicatoil. */
	private Date dtPubblicatoil;
	
	/** nr rilevanza. */
	private BigDecimal nrRilevanza;

	/** fl pdf multipagina. */
	private Boolean flPdfMultipagina;

	/** fl pdf multipagina. */
	private BigDecimal cdWorkFilesystem;
	
	/** fl ricerca ocr. */
	private Boolean flRicercaOcr;
	
	/** fl doppia pagina. */
	private Boolean flDoppiaPagina;

	/** descrizione link segnatura. */
	private String segnatura;
	
	/**
	 * Istanzia un nuovo bdl oggetto originale.
	 */
	public BdlOggettoOriginale() {
	}

	/**
	 * Istanzia un nuovo bdl oggetto originale.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param cdCollezione  codice collezione
	 * @param dnTitolo  denominazione titolo
	 * @param dnTitoloFe  denominazione titolo Fe
	 * @param flOggettoSuperiore  flag oggetto superiore
	 * @param nrOggettiInferiori  nr oggetti inferiori
	 * @param flOggettoDigitale  flag oggetto digitale
	 * @param flCorrezione  flag correzione
	 * @param dsNotaCorrezione  descrizione nota correzione
	 * @param flAnomaliaImmagini  flag anomalia immagini
	 * @param dsLogAnomalia  descrizione log anomalia
	 * @param dsStato  descrizione stato
	 * @param dsNoteNonValidazione  descrizione note non validazione
	 * @param dsNoteNonPubblicazione  descrizione note non pubblicazione
	 * @param nrImmaginiPreviste  nr immagini previste
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param dsContenutistica  descrizione contenutistica
	 * @param dsFisica  descrizione fisica
	 * @param cdLivelloBiblio  codice livello biblio
	 * @param cdSoggetto  codice soggetto
	 * @param cdAutore  codice autore
	 * @param cdAutoreSec  codice autore sec
	 * @param cdQualificaAutore  codice qualifica autore
	 * @param cdQualificaAutoreSec  codice qualifica autore sec
	 * @param cdEditore  codice editore
	 * @param cdLingua  codice lingua
	 * @param cdTipoIdentificativo  codice tipo identificativo
	 * @param cdIdentificativo  codice identificativo
	 * @param cdIdentificativoIsbn  codice identificativo isbn
	 * @param cdIdentificativoIssn  codice identificativo issn
	 * @param dnLuogoPubblicazione  denominazione luogo pubblicazione
	 * @param cdQualificatoreData  codice qualificatore data
	 * @param dsData  descrizione data
	 * @param dsNote  descrizione note
	 * @param cdSupporto  codice supporto
	 * @param cdTecnicaGrafica  codice tecnica grafica
	 * @param cdTipoGrafica  codice tipo grafica
	 * @param dsScala  descrizione scala
	 * @param dsProiezione  descrizione proiezione
	 * @param cdSoggettoProd  codice soggetto prod
	 * @param cdContestoArch  codice contesto arch
	 * @param dsCoordinate  descrizione coordinate
	 * @param dsLinkCatalogo  descrizione link catalogo
	 * @param dsDiritti  descrizione diritti
	 * @param dsAltreinfo  descrizione altreinfo
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 * @param dtPubblicatoil  data pubblicatoil
	 * @param nrRilevanza  nr rilevanza
	 */
	public BdlOggettoOriginale(
			BigDecimal cdOggettoOriginale,
			BigDecimal cdTipoOggetto,
			BigDecimal cdOggettoOriginaleSup,
			BigDecimal cdCollezione, String dnTitolo,String dnTitoloFe,
			Boolean flOggettoSuperiore, BigDecimal nrOggettiInferiori,
			Boolean flOggettoDigitale, Boolean flCorrezione,
			String dsNotaCorrezione, Boolean flAnomaliaImmagini,
			String dsLogAnomalia, String dsStato, String dsNoteNonValidazione, String dsNoteNonPubblicazione,
			BigDecimal nrImmaginiPreviste, BigDecimal nrImmaginiDigitalizzate,
			String dsContenutistica, String dsFisica, BigDecimal cdLivelloBiblio,
			BigDecimal cdSoggetto, BigDecimal cdAutore, BigDecimal cdAutoreSec,
			BigDecimal cdQualificaAutore,BigDecimal cdQualificaAutoreSec,
			BigDecimal cdEditore, BigDecimal cdLingua,
			BigDecimal cdTipoIdentificativo, String cdIdentificativo,
			String cdIdentificativoIsbn, String cdIdentificativoIssn,
			String dnLuogoPubblicazione, BigDecimal cdQualificatoreData,
			String dsData, String dsNote, BigDecimal cdSupporto,
			BigDecimal cdTecnicaGrafica, BigDecimal cdTipoGrafica,
			String dsScala, String dsProiezione, BigDecimal cdSoggettoProd,
			BigDecimal cdContestoArch,String dsCoordinate,
			 String dsLinkCatalogo, String dsDiritti,
			String dsAltreinfo, String dsCreatoda, Date dtCreatoil,
			String dsModificatoda, Date dtModificatoil,
			Date dtPubblicatoil, BigDecimal nrRilevanza) {
		
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.cdCollezione = cdCollezione;
		this.dnTitolo = dnTitolo;
		this.dnTitoloFe = dnTitoloFe;
		this.flOggettoSuperiore = flOggettoSuperiore;
		this.nrOggettiInferiori = nrOggettiInferiori;
		this.flOggettoDigitale = flOggettoDigitale;
		this.flCorrezione = flCorrezione;
		this.dsNotaCorrezione = dsNotaCorrezione;
		this.flAnomaliaImmagini = flAnomaliaImmagini;
		this.dsLogAnomalia = dsLogAnomalia;
		this.dsStato = dsStato;
		this.dsNoteNonValidazione = dsNoteNonValidazione;
		this.dsNoteNonPubblicazione = dsNoteNonPubblicazione;
		this.nrImmaginiPreviste = nrImmaginiPreviste;
		this.nrImmaginiDigitalizzate = nrImmaginiDigitalizzate;
		this.dsContenutistica = dsContenutistica;
		this.dsFisica = dsFisica;
		this.cdLivelloBiblio = cdLivelloBiblio;
		this.cdSoggetto = cdSoggetto;
		this.cdAutore = cdAutore;
		this.cdAutoreSec = cdAutoreSec;
		this.cdQualificaAutore = cdQualificaAutore;
		this.cdQualificaAutoreSec= cdQualificaAutoreSec;
		this.cdEditore = cdEditore;
		this.cdLingua = cdLingua;
		this.cdTipoIdentificativo = cdTipoIdentificativo;
		this.cdIdentificativo = cdIdentificativo;
		this.cdIdentificativoIsbn = cdIdentificativoIsbn;
		this.cdIdentificativoIssn = cdIdentificativoIssn;
		this.dnLuogoPubblicazione = dnLuogoPubblicazione;
		this.cdQualificatoreData = cdQualificaAutore;
		this.dsData = dsData;
		this.dsNote = dsNote;
		this.cdSupporto = cdSupporto;
		this.cdTecnicaGrafica = cdTecnicaGrafica;
		this.cdTipoGrafica = cdTipoGrafica;
		this.dsScala = dsScala;
		this.dsProiezione = dsProiezione;
		this.cdSoggettoProd= cdSoggettoProd;
		this.dsCoordinate = dsCoordinate;
	
		this.cdContestoArch = cdContestoArch;
		this.dsLinkCatalogo = dsLinkCatalogo;
		this.dsDiritti = dsDiritti;
		this.dsAltreinfo = dsAltreinfo;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
		
		this.dtPubblicatoil = dtPubblicatoil;
		this.nrRilevanza = nrRilevanza;
	}
	
	/**
	 * Istanzia un nuovo bdl oggetto originale.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param cdCollezione  codice collezione
	 * @param dnTitolo  denominazione titolo
	 * @param dnTitoloFe  denominazione titolo Fe
	 * @param flOggettoSuperiore  flag oggetto superiore
	 * @param nrOggettiInferiori  nr oggetti inferiori
	 * @param flOggettoDigitale  flag oggetto digitale
	 * @param flCorrezione  flag correzione
	 * @param dsNotaCorrezione  descrizione nota correzione
	 * @param flAnomaliaImmagini  flag anomalia immagini
	 * @param dsLogAnomalia  descrizione log anomalia
	 * @param dsStato  descrizione stato
	 * @param dsNoteNonValidazione  descrizione note non validazione
	 * @param dsNoteNonPubblicazione  descrizione note non pubblicazione
	 * @param nrImmaginiPreviste  nr immagini previste
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param dsContenutistica  descrizione contenutistica
	 * @param dsFisica  descrizione fisica
	 * @param cdLivelloBiblio  codice livello biblio
	 * @param cdSoggetto  codice soggetto
	 * @param cdAutore  codice autore
	 * @param cdAutoreSec  codice autore sec
	 * @param cdQualificaAutore  codice qualifica autore
	 * @param cdQualificaAutoreSec  codice qualifica autore sec
	 * @param cdEditore  codice editore
	 * @param cdLingua  codice lingua
	 * @param cdTipoIdentificativo  codice tipo identificativo
	 * @param cdIdentificativo  codice identificativo
	 * @param cdIdentificativoIsbn  codice identificativo isbn
	 * @param cdIdentificativoIssn  codice identificativo issn
	 * @param dnLuogoPubblicazione  denominazione luogo pubblicazione
	 * @param cdQualificatoreData  codice qualificatore data
	 * @param dsData  descrizione data
	 * @param dsNote  descrizione note
	 * @param cdSupporto  codice supporto
	 * @param cdTecnicaGrafica  codice tecnica grafica
	 * @param cdTipoGrafica  codice tipo grafica
	 * @param dsScala  descrizione scala
	 * @param dsProiezione  descrizione proiezione
	 * @param cdSoggettoProd  codice soggetto prod
	 * @param cdContestoArch  codice contesto arch
	 * @param dsCoordinate  descrizione coordinate
	 * @param dsLinkCatalogo  descrizione link catalogo
	 * @param dsDiritti  descrizione diritti
	 * @param dsAltreinfo  descrizione altreinfo
	 * @param dsCreatoda  descrizione creatoda
	 * @param dtCreatoil  data creatoil
	 * @param dsModificatoda  descrizione modificatoda
	 * @param dtModificatoil  data modificatoil
	 * @param vlMapMaxZoomLevel  vl map max zoom level
	 * @param vlMapMinZoomLevel  vl map min zoom level
	 * @param vlMapMaxZoomW  vl map max zoom w
	 * @param vlMapMaxZoomH  vl map max zoom h
	 * @param vlMapBorderW  vl map border w
	 * @param vlMapBorderH  vl map border h
	 * @param dtPubblicatoil  data pubblicatoil
	 * @param nrRilevanza  nr rilevanza
	 */
	public BdlOggettoOriginale(
			BigDecimal cdOggettoOriginale,
			BigDecimal cdTipoOggetto,
			BigDecimal cdOggettoOriginaleSup,
			BigDecimal cdCollezione, 
			String dnTitolo,
			String dnTitoloFe,
			Boolean flOggettoSuperiore, 
			BigDecimal nrOggettiInferiori,
			Boolean flOggettoDigitale, 
			Boolean flCorrezione,
			String dsNotaCorrezione, 
			Boolean flAnomaliaImmagini,
			String dsLogAnomalia, 
			String dsStato, 
			String dsNoteNonValidazione, 
			String dsNoteNonPubblicazione,
			BigDecimal nrImmaginiPreviste, 
			BigDecimal nrImmaginiDigitalizzate,
			String dsContenutistica, 
			String dsFisica, 
			BigDecimal cdLivelloBiblio,
			BigDecimal cdSoggetto, 
			BigDecimal cdAutore, 
			BigDecimal cdAutoreSec,
			BigDecimal cdQualificaAutore,
			BigDecimal cdQualificaAutoreSec,
			BigDecimal cdEditore, 
			BigDecimal cdLingua,
			BigDecimal cdTipoIdentificativo, 
			String cdIdentificativo,
			String cdIdentificativoIsbn, 
			String cdIdentificativoIssn,
			String dnLuogoPubblicazione, 
			BigDecimal cdQualificatoreData,
			String dsData, 
			String dsNote, 
			BigDecimal cdSupporto,
			BigDecimal cdTecnicaGrafica, 
			BigDecimal cdTipoGrafica,
			String dsScala, 
			String dsProiezione, 
			BigDecimal cdSoggettoProd,
			BigDecimal cdContestoArch,
			String dsCoordinate,
			String dsLinkCatalogo, 
			String dsDiritti,
			String dsAltreinfo,
			String dsCreatoda, 
			Date dtCreatoil,
			String dsModificatoda, 
			Date dtModificatoil,
			BigDecimal vlMapMaxZoomLevel,    
			BigDecimal vlMapMinZoomLevel,    
			BigDecimal vlMapMaxZoomW,        
			BigDecimal vlMapMaxZoomH,        
			BigDecimal vlMapBorderW,         
			BigDecimal vlMapBorderH,         
			Date dtPubblicatoil, 
			BigDecimal nrRilevanza) {
		this.cdOggettoOriginale = cdOggettoOriginale;
		this.cdTipoOggetto = cdTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.cdCollezione = cdCollezione;
		this.dnTitolo = dnTitolo;
		this.dnTitoloFe = dnTitoloFe;
		this.flOggettoSuperiore = flOggettoSuperiore;
		this.nrOggettiInferiori = nrOggettiInferiori;
		this.flOggettoDigitale = flOggettoDigitale;
		this.flCorrezione = flCorrezione;
		this.dsNotaCorrezione = dsNotaCorrezione;
		this.flAnomaliaImmagini = flAnomaliaImmagini;
		this.dsLogAnomalia = dsLogAnomalia;
		this.dsStato = dsStato;
		this.dsNoteNonValidazione = dsNoteNonValidazione;
		this.dsNoteNonPubblicazione = dsNoteNonPubblicazione;
		this.nrImmaginiPreviste = nrImmaginiPreviste;
		this.nrImmaginiDigitalizzate = nrImmaginiDigitalizzate;
		this.dsContenutistica = dsContenutistica;
		this.dsFisica = dsFisica;
		this.cdLivelloBiblio = cdLivelloBiblio;
		this.cdSoggetto = cdSoggetto;
		this.cdAutore = cdAutore;
		this.cdAutoreSec = cdAutoreSec;
		this.cdQualificaAutore = cdQualificaAutore;
		this.cdQualificaAutoreSec= cdQualificaAutoreSec;
		this.cdEditore = cdEditore;
		this.cdLingua = cdLingua;
		this.cdTipoIdentificativo = cdTipoIdentificativo;
		this.cdIdentificativo = cdIdentificativo;
		this.cdIdentificativoIsbn = cdIdentificativoIsbn;
		this.cdIdentificativoIssn = cdIdentificativoIssn;
		this.dnLuogoPubblicazione = dnLuogoPubblicazione;
		this.cdQualificatoreData = cdQualificaAutore;
		this.dsData = dsData;
		this.dsNote = dsNote;
		this.cdSupporto = cdSupporto;
		this.cdTecnicaGrafica = cdTecnicaGrafica;
		this.cdTipoGrafica = cdTipoGrafica;
		this.dsScala = dsScala;
		this.dsProiezione = dsProiezione;
		this.cdSoggettoProd= cdSoggettoProd;
		this.dsCoordinate = dsCoordinate;
		this.cdContestoArch = cdContestoArch;
		this.dsLinkCatalogo = dsLinkCatalogo;
		this.dsDiritti = dsDiritti;
		this.dsAltreinfo = dsAltreinfo;
		this.dsCreatoda = dsCreatoda;
		this.dtCreatoil = dtCreatoil;
		this.dsModificatoda = dsModificatoda;
		this.dtModificatoil = dtModificatoil;
		this.vlMapBorderH = vlMapBorderH;
		this.vlMapBorderW = vlMapBorderW;
		this.vlMapMaxZoomH = vlMapMaxZoomH;
		this.vlMapMaxZoomW = vlMapMaxZoomW;
		this.vlMapMaxZoomLevel = vlMapMaxZoomLevel;
		this.vlMapMinZoomLevel = vlMapMinZoomLevel;
		this.dtPubblicatoil = dtPubblicatoil;
		this.nrRilevanza = nrRilevanza;
	}

	/**
	 * Istanzia un nuovo bdl oggetto originale.
	 *
	 * @param cdCollezione  codice collezione
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdOggettoOriginaleSup  codice oggetto originale sup
	 * @param dnTitolo  denominazione titolo
	 * @param flOggettoSuperiore  flag oggetto superiore
	 * @param nrImmaginiPreviste  nr immagini previste
	 */
	public BdlOggettoOriginale(
			BigDecimal cdCollezione,
			BigDecimal cdTipoOggetto,
			BigDecimal cdOggettoOriginaleSup,
			String dnTitolo,
			Boolean flOggettoSuperiore, 
			BigDecimal nrImmaginiPreviste
	) {
		this.cdCollezione = cdCollezione;
		this.cdTipoOggetto = cdTipoOggetto;
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
		this.dnTitolo = dnTitolo;
		this.dnTitoloFe = dnTitolo;
		this.flOggettoSuperiore = flOggettoSuperiore;
		this.nrImmaginiPreviste = nrImmaginiPreviste;
	}
	
	/**
	 * Legge codice oggetto originale.
	 *
	 * @return the codice oggetto originale
	 */
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator="BDL_OGGETTO_ORIGINALE_seqgen")
	@SequenceGenerator(name="BDL_OGGETTO_ORIGINALE_seqgen", sequenceName="BDL_OGGETTO_ORIGINALE_SEQ", allocationSize=1)
	@Column(name = "CD_OGGETTO_ORIGINALE", unique = true, nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdOggettoOriginale() {
		return this.cdOggettoOriginale;
	}

	/**
	 * Imposta codice oggetto originale.
	 *
	 * @param cdOggettoOriginale the new codice oggetto originale
	 */
	public void setCdOggettoOriginale(BigDecimal cdOggettoOriginale) {
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Legge codice tipo oggetto.
	 *
	 * @return the codice tipo oggetto
	 */
	@Column(name = "CD_TIPO_OGGETTO", nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdTipoOggetto() {
		return this.cdTipoOggetto;
	}

	/**
	 * Imposta codice tipo oggetto.
	 *
	 * @param cdTipoOggetto the new codice tipo oggetto
	 */
	public void setCdTipoOggetto(BigDecimal cdTipoOggetto) {
		this.cdTipoOggetto = cdTipoOggetto;
	}

	/**
	 * Legge codice oggetto originale sup.
	 *
	 * @return the codice oggetto originale sup
	 */
	@Column(name = "CD_OGGETTO_ORIGINALE_SUP", precision = 31, scale = 0)
	public BigDecimal getCdOggettoOriginaleSup() {
		return this.cdOggettoOriginaleSup;
	}

	/**
	 * Imposta codice oggetto originale sup.
	 *
	 * @param cdOggettoOriginaleSup the new codice oggetto originale sup
	 */
	public void setCdOggettoOriginaleSup(BigDecimal cdOggettoOriginaleSup) {
		this.cdOggettoOriginaleSup = cdOggettoOriginaleSup;
	}

	/**
	 * Legge codice collezione.
	 *
	 * @return the codice collezione
	 */
	@Column(name = "CD_COLLEZIONE", nullable = false, precision = 31, scale = 0)
	public BigDecimal getCdCollezione() {
		return this.cdCollezione;
	}

	/**
	 * Imposta codice collezione.
	 *
	 * @param cdCollezione the new codice collezione
	 */
	public void setCdCollezione(BigDecimal cdCollezione) {
		this.cdCollezione = cdCollezione;
	}

	/**
	 * Legge denominazione titolo.
	 *
	 * @return the denominazione titolo
	 */
	@Column(name = "DN_TITOLO", nullable = false, length = 500)
	public String getDnTitolo() {
		return this.dnTitolo;
	}

	/**
	 * Imposta denominazione titolo.
	 *
	 * @param dnTitolo the new denominazione titolo
	 */
	public void setDnTitolo(String dnTitolo) {
		this.dnTitolo = dnTitolo;
	}
	
	

	/**
	 * Legge denominazione titolo Fe.
	 *
	 * @return the denominazione titolo
	 */
	@Column(name = "DN_TITOLO_FE", nullable = true, length = 500)
	public String getDnTitoloFe() {
		return this.dnTitoloFe;
	}

	/**
	 * Imposta denominazione titolo Fe.
	 *
	 * @param dnTitolo the new denominazione titolo
	 */
	public void setDnTitoloFe(String dnTitoloFe) {
		this.dnTitoloFe = dnTitoloFe;
	}
	

	/**
	 * Legge flag oggetto superiore.
	 *
	 * @return the flag oggetto superiore
	 */
	@Column(name = "FL_OGGETTO_SUPERIORE", nullable = false)
	@Type(type="true_false")
	public Boolean getFlOggettoSuperiore() {
		return this.flOggettoSuperiore;
	}

	/**
	 * Imposta flag oggetto superiore.
	 *
	 * @param flOggettoSuperiore the new flag oggetto superiore
	 */
	public void setFlOggettoSuperiore(Boolean flOggettoSuperiore) {
		this.flOggettoSuperiore = flOggettoSuperiore;
	}

	/**
	 * Legge nr oggetti inferiori.
	 *
	 * @return the nr oggetti inferiori
	 */
	@Column(name = "NR_OGGETTI_INFERIORI", precision = 31, scale = 0)
	public BigDecimal getNrOggettiInferiori() {
		return this.nrOggettiInferiori;
	}

	/**
	 * Imposta nr oggetti inferiori.
	 *
	 * @param nrOggettiInferiori the new nr oggetti inferiori
	 */
	public void setNrOggettiInferiori(BigDecimal nrOggettiInferiori) {
		this.nrOggettiInferiori = nrOggettiInferiori;
	}

	/**
	 * Legge flag oggetto digitale.
	 *
	 * @return the flag oggetto digitale
	 */
	@Column(name = "FL_OGGETTO_DIGITALE")
	@Type(type="true_false")
	public Boolean getFlOggettoDigitale() {
		return this.flOggettoDigitale;
	}

	/**
	 * Imposta flag oggetto digitale.
	 *
	 * @param flOggettoDigitale the new flag oggetto digitale
	 */
	public void setFlOggettoDigitale(Boolean flOggettoDigitale) {
		this.flOggettoDigitale = flOggettoDigitale;
	}

	/**
	 * Legge flag correzione.
	 *
	 * @return the flag correzione
	 */
	@Column(name = "FL_CORREZIONE")
	@Type(type="true_false")	
	public Boolean getFlCorrezione() {
		return this.flCorrezione;
	}

	/**
	 * Imposta flag correzione.
	 *
	 * @param flCorrezione the new flag correzione
	 */
	public void setFlCorrezione(Boolean flCorrezione) {
		this.flCorrezione = flCorrezione;
	}

	/**
	 * Legge descrizione nota correzione.
	 *
	 * @return the descrizione nota correzione
	 */
	@Column(name = "DS_NOTA_CORREZIONE", length = 1000)
	public String getDsNotaCorrezione() {
		return this.dsNotaCorrezione;
	}

	/**
	 * Imposta descrizione nota correzione.
	 *
	 * @param dsNotaCorrezione the new descrizione nota correzione
	 */
	public void setDsNotaCorrezione(String dsNotaCorrezione) {
		this.dsNotaCorrezione = dsNotaCorrezione;
	}

	/**
	 * Legge flag anomalia immagini.
	 *
	 * @return the flag anomalia immagini
	 */
	@Column(name = "FL_ANOMALIA_IMMAGINI")
	@Type(type="true_false")
	public Boolean getFlAnomaliaImmagini() {
		return this.flAnomaliaImmagini;
	}

	/**
	 * Imposta flag anomalia immagini.
	 *
	 * @param flAnomaliaImmagini the new flag anomalia immagini
	 */
	public void setFlAnomaliaImmagini(Boolean flAnomaliaImmagini) {
		this.flAnomaliaImmagini = flAnomaliaImmagini;
	}

	/**
	 * Legge descrizione log anomalia.
	 *
	 * @return the descrizione log anomalia
	 */
	@Column(name = "DS_LOG_ANOMALIA", length = 4000)
	public String getDsLogAnomalia() {
		return this.dsLogAnomalia;
	}

	/**
	 * Imposta descrizione log anomalia.
	 *
	 * @param dsLogAnomalia the new descrizione log anomalia
	 */
	public void setDsLogAnomalia(String dsLogAnomalia) {
		this.dsLogAnomalia = dsLogAnomalia;
	}

	/**
	 * Legge descrizione stato.
	 *
	 * @return the descrizione stato
	 */
	@Column(name = "DS_STATO", length = 50)
	public String getDsStato() {
		return this.dsStato;
	}

	/**
	 * Imposta descrizione stato.
	 *
	 * @param dsStato the new descrizione stato
	 */
	public void setDsStato(String dsStato) {
		this.dsStato = dsStato;
	}

	/**
	 * Legge descrizione note non validazione.
	 *
	 * @return the descrizione note non validazione
	 */
	@Column(name = "DS_NOTE_NON_VALIDAZIONE", length = 4000)
	public String getDsNoteNonValidazione() {
		return this.dsNoteNonValidazione;
	}

	/**
	 * Imposta descrizione note non validazione.
	 *
	 * @param dsNoteNonValidazione the new descrizione note non validazione
	 */
	public void setDsNoteNonValidazione(String dsNoteNonValidazione) {
		this.dsNoteNonValidazione = dsNoteNonValidazione;
	}
	
	/**
	 * Legge descrizione note non pubblicazione.
	 *
	 * @return the descrizione note non pubblicazione
	 */
	@Column(name = "DS_NOTE_NON_PUBBLICAZIONE", length = 4000)
	public String getDsNoteNonPubblicazione() {
		return this.dsNoteNonPubblicazione;
	}

	/**
	 * Imposta descrizione note non pubblicazione.
	 *
	 * @param dsNoteNonPubblicazione the new descrizione note non pubblicazione
	 */
	public void setDsNoteNonPubblicazione(String dsNoteNonPubblicazione) {
		this.dsNoteNonPubblicazione = dsNoteNonPubblicazione;
	}

	/**
	 * Legge nr immagini previste.
	 *
	 * @return the nr immagini previste
	 */
	@Column(name = "NR_IMMAGINI_PREVISTE", nullable = true, precision = 31, scale = 0)
	public BigDecimal getNrImmaginiPreviste() {
		return this.nrImmaginiPreviste;
	}

	/**
	 * Imposta nr immagini previste.
	 *
	 * @param nrImmaginiPreviste the new nr immagini previste
	 */
	public void setNrImmaginiPreviste(BigDecimal nrImmaginiPreviste) {
		this.nrImmaginiPreviste = nrImmaginiPreviste;
	}

	/**
	 * Legge nr immagini digitalizzate.
	 *
	 * @return the nr immagini digitalizzate
	 */
	@Column(name = "NR_IMMAGINI_DIGITALIZZATE", precision = 31, scale = 0)
	public BigDecimal getNrImmaginiDigitalizzate() {
		return this.nrImmaginiDigitalizzate;
	}

	/**
	 * Imposta nr immagini digitalizzate.
	 *
	 * @param nrImmaginiDigitalizzate the new nr immagini digitalizzate
	 */
	public void setNrImmaginiDigitalizzate(BigDecimal nrImmaginiDigitalizzate) {
		this.nrImmaginiDigitalizzate = nrImmaginiDigitalizzate;
	}

	/**
	 * Legge descrizione contenutistica.
	 *
	 * @return the descrizione contenutistica
	 */
	@Column(name = "DS_CONTENUTISTICA", length = 1000)
	public String getDsContenutistica() {
		return this.dsContenutistica;
	}

	/**
	 * Imposta descrizione contenutistica.
	 *
	 * @param dsContenutistica the new descrizione contenutistica
	 */
	public void setDsContenutistica(String dsContenutistica) {
		this.dsContenutistica = dsContenutistica;
	}

	/**
	 * Legge descrizione fisica.
	 *
	 * @return the descrizione fisica
	 */
	@Column(name = "DS_FISICA", length = 1000)
	public String getDsFisica() {
		return this.dsFisica;
	}

	/**
	 * Imposta descrizione fisica.
	 *
	 * @param dsFisica the new descrizione fisica
	 */
	public void setDsFisica(String dsFisica) {
		this.dsFisica = dsFisica;
	}

	/**
	 * Legge codice livello biblio.
	 *
	 * @return the codice livello biblio
	 */
	@Column(name = "CD_LIVELLO_BIBLIO", precision = 31, scale = 0)
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
	 * Legge codice soggetto.
	 *
	 * @return the codice soggetto
	 */
	@Column(name = "CD_SOGGETTO", precision = 31, scale = 0)
	public BigDecimal getCdSoggetto() {
		return this.cdSoggetto;
	}

	/**
	 * Imposta codice soggetto.
	 *
	 * @param cdSoggetto the new codice soggetto
	 */
	public void setCdSoggetto(BigDecimal cdSoggetto) {
		this.cdSoggetto = cdSoggetto;
	}

	/**
	 * Legge codice autore.
	 *
	 * @return the codice autore
	 */
	@Column(name = "CD_AUTORE", precision = 31, scale = 0)
	public BigDecimal getCdAutore() {
		return this.cdAutore;
	}

	/**
	 * Imposta codice autore.
	 *
	 * @param cdAutore the new codice autore
	 */
	public void setCdAutore(BigDecimal cdAutore) {
		this.cdAutore = cdAutore;
	}

	/**
	 * Legge codice autore sec.
	 *
	 * @return the codice autore sec
	 */
	@Column(name = "CD_AUTORE_SEC", precision = 31, scale = 0)
	public BigDecimal getCdAutoreSec() {
		return this.cdAutoreSec;
	}

	/**
	 * Imposta codice autore sec.
	 *
	 * @param cdAutoreSec the new codice autore sec
	 */
	public void setCdAutoreSec(BigDecimal cdAutoreSec) {
		this.cdAutoreSec = cdAutoreSec;
	}

	/**
	 * Legge codice editore.
	 *
	 * @return the codice editore
	 */
	@Column(name = "CD_EDITORE", precision = 31, scale = 0)
	public BigDecimal getCdEditore() {
		return this.cdEditore;
	}

	/**
	 * Imposta codice editore.
	 *
	 * @param cdEditore the new codice editore
	 */
	public void setCdEditore(BigDecimal cdEditore) {
		this.cdEditore = cdEditore;
	}

	/**
	 * Legge codice lingua.
	 *
	 * @return the codice lingua
	 */
	@Column(name = "CD_LINGUA", precision = 31, scale = 0)
	public BigDecimal getCdLingua() {
		return this.cdLingua;
	}

	/**
	 * Imposta codice lingua.
	 *
	 * @param cdLingua the new codice lingua
	 */
	public void setCdLingua(BigDecimal cdLingua) {
		this.cdLingua = cdLingua;
	}

	/**
	 * Legge codice tipo identificativo.
	 *
	 * @return the codice tipo identificativo
	 */
	@Column(name = "CD_TIPO_IDENTIFICATIVO", precision = 31, scale = 0)
	public BigDecimal getCdTipoIdentificativo() {
		return this.cdTipoIdentificativo;
	}

	/**
	 * Imposta codice tipo identificativo.
	 *
	 * @param cdTipoIdentificativo the new codice tipo identificativo
	 */
	public void setCdTipoIdentificativo(BigDecimal cdTipoIdentificativo) {
		this.cdTipoIdentificativo = cdTipoIdentificativo;
	}

	/**
	 * Legge codice identificativo.
	 *
	 * @return the codice identificativo
	 */
	@Column(name = "CD_IDENTIFICATIVO", length = 250)
	public String getCdIdentificativo() {
		return this.cdIdentificativo;
	}

	/**
	 * Imposta codice identificativo.
	 *
	 * @param cdIdentificativo the new codice identificativo
	 */
	public void setCdIdentificativo(String cdIdentificativo) {
		this.cdIdentificativo = cdIdentificativo;
	}

	/**
	 * Legge codice identificativo isbn.
	 *
	 * @return the codice identificativo isbn
	 */
	@Column(name = "CD_IDENTIFICATIVO_ISBN", length = 250)
	public String getCdIdentificativoIsbn() {
		return this.cdIdentificativoIsbn;
	}

	/**
	 * Imposta codice identificativo isbn.
	 *
	 * @param cdIdentificativoIsbn the new codice identificativo isbn
	 */
	public void setCdIdentificativoIsbn(String cdIdentificativoIsbn) {
		this.cdIdentificativoIsbn = cdIdentificativoIsbn;
	}

	/**
	 * Legge codice identificativo issn.
	 *
	 * @return the codice identificativo issn
	 */
	@Column(name = "CD_IDENTIFICATIVO_ISSN", length = 250)
	public String getCdIdentificativoIssn() {
		return this.cdIdentificativoIssn;
	}

	/**
	 * Imposta codice identificativo issn.
	 *
	 * @param cdIdentificativoIssn the new codice identificativo issn
	 */
	public void setCdIdentificativoIssn(String cdIdentificativoIssn) {
		this.cdIdentificativoIssn = cdIdentificativoIssn;
	}

	/**
	 * Legge denominazione luogo pubblicazione.
	 *
	 * @return the denominazione luogo pubblicazione
	 */
	@Column(name = "DN_LUOGO_PUBBLICAZIONE", length = 250)
	public String getDnLuogoPubblicazione() {
		return this.dnLuogoPubblicazione;
	}

	/**
	 * Imposta denominazione luogo pubblicazione.
	 *
	 * @param dnLuogoPubblicazione the new denominazione luogo pubblicazione
	 */
	public void setDnLuogoPubblicazione(String dnLuogoPubblicazione) {
		this.dnLuogoPubblicazione = dnLuogoPubblicazione;
	}

	
	/**
	 * Legge descrizione data.
	 *
	 * @return the descrizione data
	 */
	@Column(name = "DS_DATA", length = 250)
	public String getDsData() {
		return this.dsData;
	}

	/**
	 * Imposta descrizione data.
	 *
	 * @param dsData the new descrizione data
	 */
	public void setDsData(String dsData) {
		this.dsData = dsData;
	}

	/**
	 * Legge descrizione note.
	 *
	 * @return the descrizione note
	 */
	@Column(name = "DS_NOTE", length = 1000)
	public String getDsNote() {
		return this.dsNote;
	}

	/**
	 * Imposta descrizione note.
	 *
	 * @param dsNote the new descrizione note
	 */
	public void setDsNote(String dsNote) {
		this.dsNote = dsNote;
	}

	/**
	 * Legge codice supporto.
	 *
	 * @return the codice supporto
	 */
	@Column(name = "CD_SUPPORTO", precision = 31, scale = 0)
	public BigDecimal getCdSupporto() {
		return this.cdSupporto;
	}

	/**
	 * Imposta codice supporto.
	 *
	 * @param cdSupporto the new codice supporto
	 */
	public void setCdSupporto(BigDecimal cdSupporto) {
		this.cdSupporto = cdSupporto;
	}

	/**
	 * Legge codice tecnica grafica.
	 *
	 * @return the codice tecnica grafica
	 */
	@Column(name = "CD_TECNICA_GRAFICA", precision = 31, scale = 0)
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
	 * Legge codice tipo grafica.
	 *
	 * @return the codice tipo grafica
	 */
	@Column(name = "CD_TIPO_GRAFICA", precision = 31, scale = 0)
	public BigDecimal getCdTipoGrafica() {
		return this.cdTipoGrafica;
	}

	/**
	 * Imposta codice tipo grafica.
	 *
	 * @param cdTipoGrafica the new codice tipo grafica
	 */
	public void setCdTipoGrafica(BigDecimal cdTipoGrafica) {
		this.cdTipoGrafica = cdTipoGrafica;
	}

	/**
	 * Legge descrizione scala.
	 *
	 * @return the descrizione scala
	 */
	@Column(name = "DS_SCALA", length = 250)
	public String getDsScala() {
		return this.dsScala;
	}

	/**
	 * Imposta descrizione scala.
	 *
	 * @param dsScala the new descrizione scala
	 */
	public void setDsScala(String dsScala) {
		this.dsScala = dsScala;
	}

	/**
	 * Legge descrizione proiezione.
	 *
	 * @return the descrizione proiezione
	 */
	@Column(name = "DS_PROIEZIONE", length = 250)
	public String getDsProiezione() {
		return this.dsProiezione;
	}

	/**
	 * Imposta descrizione proiezione.
	 *
	 * @param dsProiezione the new descrizione proiezione
	 */
	public void setDsProiezione(String dsProiezione) {
		this.dsProiezione = dsProiezione;
	}

	/**
	 * Legge descrizione coordinate.
	 *
	 * @return the descrizione coordinate
	 */
	@Column(name = "DS_COORDINATE", length = 250)
	public String getDsCoordinate() {
		return this.dsCoordinate;
	}

	/**
	 * Imposta descrizione coordinate.
	 *
	 * @param dsCoordinate the new descrizione coordinate
	 */
	public void setDsCoordinate(String dsCoordinate) {
		this.dsCoordinate = dsCoordinate;
	}

	

	/**
	 * Legge descrizione link catalogo.
	 *
	 * @return the descrizione link catalogo
	 */
	@Column(name = "DS_LINK_CATALOGO", length = 1000)
	public String getDsLinkCatalogo() {
		return this.dsLinkCatalogo;
	}

	/**
	 * Imposta descrizione link catalogo.
	 *
	 * @param dsLinkCatalogo the new descrizione link catalogo
	 */
	public void setDsLinkCatalogo(String dsLinkCatalogo) {
		this.dsLinkCatalogo = dsLinkCatalogo;
	}

	/**
	 * Legge descrizione diritti.
	 *
	 * @return the descrizione diritti
	 */
	@Column(name = "DS_DIRITTI", length = 250)
	public String getDsDiritti() {
		return this.dsDiritti;
	}

	/**
	 * Imposta descrizione diritti.
	 *
	 * @param dsDiritti the new descrizione diritti
	 */
	public void setDsDiritti(String dsDiritti) {
		this.dsDiritti = dsDiritti;
	}

	/**
	 * Legge descrizione altreinfo.
	 *
	 * @return the descrizione altreinfo
	 */
	@Column(name = "DS_ALTREINFO", length = 250)
	public String getDsAltreinfo() {
		return this.dsAltreinfo;
	}

	/**
	 * Imposta descrizione altreinfo.
	 *
	 * @param dsAltreinfo the new descrizione altreinfo
	 */
	public void setDsAltreinfo(String dsAltreinfo) {
		this.dsAltreinfo = dsAltreinfo;
	}

	
	/**
	 * Legge codice qualifica autore.
	 *
	 * @return the codice qualifica autore
	 */
	@Column(name = "CD_QUALIFICA_AUTORE")
	public BigDecimal getCdQualificaAutore() {
		return cdQualificaAutore;
	}

	/**
	 * Imposta codice qualifica autore.
	 *
	 * @param cdQualificaAutore the new codice qualifica autore
	 */
	public void setCdQualificaAutore(BigDecimal cdQualificaAutore) {
		this.cdQualificaAutore = cdQualificaAutore;
	}
	
	/**
	 * Legge codice qualifica autore sec.
	 *
	 * @return the codice qualifica autore sec
	 */
	@Column(name = "CD_QUALIFICA_AUTORE_SEC")
	public BigDecimal getCdQualificaAutoreSec() {
		return cdQualificaAutoreSec;
	}

	/**
	 * Imposta codice qualifica autore sec.
	 *
	 * @param cdQualificaAutoreSec the new codice qualifica autore sec
	 */
	public void setCdQualificaAutoreSec(BigDecimal cdQualificaAutoreSec) {
		this.cdQualificaAutoreSec = cdQualificaAutoreSec;
	}

	/**
	 * Legge codice qualificatore data.
	 *
	 * @return the codice qualificatore data
	 */
	@Column(name = "CD_QUALIFICATORE_DATA")
	public BigDecimal getCdQualificatoreData() {
		return cdQualificatoreData;
	}

	/**
	 * Imposta codice qualificatore data.
	 *
	 * @param cdQualificatoreData the new codice qualificatore data
	 */
	public void setCdQualificatoreData(BigDecimal cdQualificatoreData) {
		this.cdQualificatoreData = cdQualificatoreData;
	}

	
	/**
	 * Legge codice soggetto prod.
	 *
	 * @return the codice soggetto prod
	 */
	@Column(name = "CD_SOGGETTO_PROD")
	public BigDecimal getCdSoggettoProd() {
		return cdSoggettoProd;
	}

	/**
	 * Imposta codice soggetto prod.
	 *
	 * @param cdSoggettoProd the new codice soggetto prod
	 */
	public void setCdSoggettoProd(BigDecimal cdSoggettoProd) {
		this.cdSoggettoProd = cdSoggettoProd;
	}

	/**
	 * Legge codice contesto arch.
	 *
	 * @return the codice contesto arch
	 */
	@Column(name = "CD_CONTESTO_ARCH")
	public BigDecimal getCdContestoArch() {
		return cdContestoArch;
	}

	/**
	 * Imposta codice contesto arch.
	 *
	 * @param cdContestoArch the new codice contesto arch
	 */
	public void setCdContestoArch(BigDecimal cdContestoArch) {
		this.cdContestoArch = cdContestoArch;
	}

	/**
	 * Legge codice tipo archivio.
	 *
	 * @return the codice tipo archivio
	 */
	@Column(name = "CD_TIPO_ARCHIVIO")
	public BigDecimal getCdTipoArchivio() {
		return cdTipoArchivio;
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
	 * Legge vl map max zoom level.
	 *
	 * @return the vl map max zoom level
	 */
	@Column(name = "VL_MAP_MAX_ZOOM_LEVEL", precision = 31, scale = 0)
	public BigDecimal getVlMapMaxZoomLevel() {
		return vlMapMaxZoomLevel;
	}

	/**
	 * Imposta vl map max zoom level.
	 *
	 * @param vlMapMaxZoomLevel the new vl map max zoom level
	 */
	public void setVlMapMaxZoomLevel(BigDecimal vlMapMaxZoomLevel) {
		this.vlMapMaxZoomLevel = vlMapMaxZoomLevel;
	}

	/**
	 * Legge vl map min zoom level.
	 *
	 * @return the vl map min zoom level
	 */
	@Column(name = "VL_MAP_MIN_ZOOM_LEVEL", precision = 31, scale = 0)
	public BigDecimal getVlMapMinZoomLevel() {
		return vlMapMinZoomLevel;
	}

	/**
	 * Imposta vl map min zoom level.
	 *
	 * @param vlMapMinZoomLevel the new vl map min zoom level
	 */
	public void setVlMapMinZoomLevel(BigDecimal vlMapMinZoomLevel) {
		this.vlMapMinZoomLevel = vlMapMinZoomLevel;
	}

	/**
	 * Legge vl map max zoom w.
	 *
	 * @return the vl map max zoom w
	 */
	@Column(name = "VL_MAP_MAX_ZOOM_W", precision = 31, scale = 0)
	public BigDecimal getVlMapMaxZoomW() {
		return vlMapMaxZoomW;
	}

	/**
	 * Imposta vl map max zoom w.
	 *
	 * @param vlMapMaxZoomW the new vl map max zoom w
	 */
	public void setVlMapMaxZoomW(BigDecimal vlMapMaxZoomW) {
		this.vlMapMaxZoomW = vlMapMaxZoomW;
	}

	/**
	 * Legge vl map max zoom h.
	 *
	 * @return the vl map max zoom h
	 */
	@Column(name = "VL_MAP_MAX_ZOOM_H", precision = 31, scale = 0)
	public BigDecimal getVlMapMaxZoomH() {
		return vlMapMaxZoomH;
	}

	/**
	 * Imposta vl map max zoom h.
	 *
	 * @param vlMapMaxZoomH the new vl map max zoom h
	 */
	public void setVlMapMaxZoomH(BigDecimal vlMapMaxZoomH) {
		this.vlMapMaxZoomH = vlMapMaxZoomH;
	}

	/**
	 * Legge vl map border w.
	 *
	 * @return the vl map border w
	 */
	@Column(name = "VL_MAP_BORDER_W", precision = 31, scale = 0)
	public BigDecimal getVlMapBorderW() {
		return vlMapBorderW;
	}

	/**
	 * Imposta vl map border w.
	 *
	 * @param vlMapBorderW the new vl map border w
	 */
	public void setVlMapBorderW(BigDecimal vlMapBorderW) {
		this.vlMapBorderW = vlMapBorderW;
	}

	/**
	 * Legge vl map border h.
	 *
	 * @return the vl map border h
	 */
	@Column(name = "VL_MAP_BORDER_H", precision = 31, scale = 0)
	public BigDecimal getVlMapBorderH() {
		return vlMapBorderH;
	}

	/**
	 * Imposta vl map border h.
	 *
	 * @param vlMapBorderH the new vl map border h
	 */
	public void setVlMapBorderH(BigDecimal vlMapBorderH) {
		this.vlMapBorderH = vlMapBorderH;
	}

	/**
	 * Legge data pubblicatoil.
	 *
	 * @return the data pubblicatoil
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_PUBBLICATOIL")
	public Date getDtPubblicatoil() {
		return dtPubblicatoil;
	}

	/**
	 * Imposta data pubblicatoil.
	 *
	 * @param dtPubblicatoil the new data pubblicatoil
	 */
	public void setDtPubblicatoil(Date dtPubblicatoil) {
		this.dtPubblicatoil = dtPubblicatoil;
	}

	/**
	 * Legge nr rilevanza.
	 *
	 * @return the nr rilevanza
	 */
	@Column(name = "NR_RILEVANZA", precision = 31, scale = 0)
	public BigDecimal getNrRilevanza() {
		return nrRilevanza;
	}

	/**
	 * Imposta nr rilevanza.
	 *
	 * @param nrRilevanza the new nr rilevanza
	 */
	public void setNrRilevanza(BigDecimal nrRilevanza) {
		this.nrRilevanza = nrRilevanza;
	}
	
	
	/**
	 * Legge flag pdf multipagina.
	 *
	 * @return the flag pdf multipagina
	 */
	@Column(name = "FL_PDF_MULTIPAGINA", nullable = false)
	@Type(type="true_false")
	public Boolean getFlPdfMultipagina() {
		return flPdfMultipagina;
	}

	/**
	 * Imposta flag pdf multipagina.
	 *
	 * @param flPdfMultipagina the new flag pdf multipagina
	 */
	public void setFlPdfMultipagina(Boolean flPdfMultipagina) {
		this.flPdfMultipagina = flPdfMultipagina;
	}
	/**
	 * Legge codice work filesystem.
	 *
	 * @return the codice work filesystem
	 */
	@Column(name = "CD_WORK_FILESYSTEM", nullable = true, precision = 31, scale = 0)
	public BigDecimal getCdWorkFilesystem() {
		return this.cdWorkFilesystem;
	}

	/**
	 * Imposta codice work filesystem.
	 *
	 * @param cdTipoOggetto the new codice work filesystem
	 */
	public void setCdWorkFilesystem(BigDecimal cdWorkFilesystem) {
		this.cdWorkFilesystem = cdWorkFilesystem;
	}

	/**
	 * @return the flRicercaOcr
	 */
	@Column(name = "FL_RICERCA_OCR", nullable = false)
	@Type(type="true_false")
	public Boolean getFlRicercaOcr() {
		return flRicercaOcr;
	}

	/**
	 * @param flRicercaOcr the flRicercaOcr to set
	 */
	public void setFlRicercaOcr(Boolean flRicercaOcr) {
		this.flRicercaOcr = flRicercaOcr;
	}

	/**
	 * @return the flDoppiaPagina
	 */
	@Column(name = "FL_DOPPIA_PAGINA", nullable = true)
	@Type(type="true_false")
	public Boolean getFlDoppiaPagina() {
		return flDoppiaPagina;
	}

	public void setFlDoppiaPagina(Boolean flDoppiaPagina) {
		this.flDoppiaPagina = flDoppiaPagina;
	}

	/**
	 * Legge segnatura
	 *
	 * @return segnatura
	 */
	@Column(name = "SEGNATURA", nullable = true, length = 60)
	
	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
}
