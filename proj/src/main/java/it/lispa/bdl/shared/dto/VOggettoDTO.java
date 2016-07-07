package it.lispa.bdl.shared.dto;

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class VOggettoDTO.
 */
public class VOggettoDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * Interface VOggettoDTOProperties.
	 */
	public interface VOggettoDTOProperties extends PropertyAccess<VOggettoDTO> {
		
		/**
		 * O_ codice oggetto originale.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<VOggettoDTO> O_CdOggettoOriginale();
		
		/**
		 * D_ codice digitalizzatore.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> D_CdDigitalizzatore();
		
		/**
		 * D_ denominazione nome.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> D_DnNome();
		
		/**
		 * I_ codice istituto.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> I_CdIstituto();
		
		/**
		 * I_ denominazione nome.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> I_DnNome();
		
		/**
		 * P_ codice progetto.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> P_CdProgetto();
		
		/**
		 * P_ denominazione titolo.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> P_DnTitolo();
		
		/**
		 * P_ descrizione stato.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> P_DsStato();
		
		/**
		 * C_ codice collezione.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> C_CdCollezione();
		
		/**
		 * C_ denominazione titolo.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> C_DnTitolo();
		
		/**
		 * O_ denominazione titolo.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DnTitolo();
		
		/**
		 * O_ denominazione titolo frontend.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DnTitoloFe();
		
		/**
		 * O_ denominazione titolo sup.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DnTitoloSup();
		
		/**
		 * O_ codice oggetto originale sup.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> O_CdOggettoOriginaleSup();
		
		/**
		 * O_ flag oggetto superiore.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  Character> O_FlOggettoSuperiore();
		
		/**
		 * O_ nr oggetti inferiori.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> O_NrOggettiInferiori();
		
		/**
		 * T_ codice tipo oggetto.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> T_CdTipoOggetto();
		
		/**
		 * T_ denominazione nome.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> T_DnNome();
		
		/**
		 * O_ flag oggetto digitale.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  Character> O_FlOggettoDigitale();
		
		/**
		 * O_ flag correzione.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  Character> O_FlCorrezione();
		
		/**
		 * O_ descrizione nota correzione.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsNotaCorrezione();
		
		/**
		 * O_ flag anomalia immagini.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  Character> O_FlAnomaliaImmagini();
		
		/**
		 * O_ descrizione log anomalia.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsLogAnomalia();
		
		/**
		 * O_ descrizione stato.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsStato();
		
		/**
		 * O_ descrizione note non validazione.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsNoteNonValidazione();
		
		/**
		 * O_ descrizione note non pubblicazione.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsNoteNonPubblicazione();
		
		/**
		 * O_ nr immagini previste.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> O_NrImmaginiPreviste();
		
		/**
		 * O_ nr immagini digitalizzate.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> O_NrImmaginiDigitalizzate();
		
		/**
		 * O_ digitalizzatore id.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DigitalizzatoreId();
		
		/**
		 * O_ flag anomalia immagini_ human.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_FlAnomaliaImmagini_Human();
		
		/**
		 * O_ flag correzione_ human.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_FlCorrezione_Human();
		
		/**
		 * O_ flag is critico_ human.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_FlIsCritico_Human();
		
		/**
		 * O_ descrizione nota critico.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  String> O_DsNotaCritico();
		
		/**
		 * O_ data pubblicatoil.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  Date> O_DtPubblicatoil();
		
		/**
		 * O_ nr rilevanza.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoDTO,  BigDecimal> O_NrRilevanza();

		
	}
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -5367900107183257051L;

	/** D_ codice digitalizzatore. */
	private BigDecimal D_CdDigitalizzatore;
	
	/** D_ denominazione nome. */
	private String D_DnNome;
	
	/** I_ codice istituto. */
	private BigDecimal I_CdIstituto;
	
	/** I_ denominazione nome. */
	private String I_DnNome;
	
	/** P_ codice progetto. */
	private BigDecimal P_CdProgetto;
	
	/** P_ denominazione titolo. */
	private String P_DnTitolo;
	
	/** P_ descrizione stato. */
	private String P_DsStato;
	
	/** C_ codice collezione. */
	private BigDecimal C_CdCollezione;
	
	/** C_ denominazione titolo. */
	private String C_DnTitolo;
	
	/** O_ codice oggetto originale. */
	private BigDecimal O_CdOggettoOriginale;

	/** O_ denominazione titolo. */
	private String O_DnTitolo;
	
	/** O_ denominazione titolo Fe. */
	private String O_DnTitoloFe;
	
	/** O_ denominazione titolo sup. */
	private String O_DnTitoloSup;
	
	/** O_ codice oggetto originale sup. */
	private BigDecimal O_CdOggettoOriginaleSup;
	
	/** O_ flag oggetto superiore. */
	private char O_FlOggettoSuperiore;
	
	/** O_ nr oggetti inferiori. */
	private BigDecimal O_NrOggettiInferiori;
	
	/** T_ codice tipo oggetto. */
	private BigDecimal T_CdTipoOggetto;
	
	/** T_ denominazione nome. */
	private String T_DnNome;
	
	/** O_ flag oggetto digitale. */
	private char O_FlOggettoDigitale;
	
	/** O_ flag correzione. */
	private char O_FlCorrezione;
	
	/** O_ descrizione nota correzione. */
	private String O_DsNotaCorrezione;
	
	/** O_ flag anomalia immagini. */
	private char O_FlAnomaliaImmagini;
	
	/** O_ descrizione log anomalia. */
	private String O_DsLogAnomalia;
	
	/** O_ descrizione stato. */
	private String O_DsStato;
	
	/** O_ descrizione note non validazione. */
	private String O_DsNoteNonValidazione;
	
	/** O_ descrizione note non pubblicazione. */
	private String O_DsNoteNonPubblicazione;
	
	/** O_ nr immagini previste. */
	private BigDecimal O_NrImmaginiPreviste;
	
	/** O_ nr immagini digitalizzate. */
	private BigDecimal O_NrImmaginiDigitalizzate;


	/** O_ descrizione contenutistica. */
	private String O_DsContenutistica;
	
	/** O_ descrizione fisica. */
	private String O_DsFisica;
	
	/** O_ codice livello biblio. */
	private BigDecimal O_CdLivelloBiblio;
	
	/** O_ descrizione livello biblio. */
	private String O_DsLivelloBiblio;
	
	/** O_ codice soggetto. */
	private BigDecimal O_CdSoggetto;
	
	/** O_ descrizione soggetto. */
	private String O_DsSoggetto;
	
	/** O_ codice autore. */
	private BigDecimal O_CdAutore;
	
	/** O_ descrizione autore. */
	private String O_DsAutore;
	
	/** O_ codice qualifica autore. */
	private BigDecimal O_CdQualificaAutore;
	
	/** O_ descrizione qualifica autore. */
	private String O_DsQualificaAutore;
	
	/** O_ codice autore sec. */
	private BigDecimal O_CdAutoreSec;
	
	/** O_ descrizione autore sec. */
	private String O_DsAutoreSec;
	
	/** O_ codice qualifica autore sec. */
	private BigDecimal O_CdQualificaAutoreSec;
	
	/** O_ descrizione qualifica autore sec. */
	private String O_DsQualificaAutoreSec;
	
	/** O_ codice editore. */
	private BigDecimal O_CdEditore;
	
	/** O_ descrizione editore. */
	private String O_DsEditore;
	
	/** O_ codice lingua. */
	private BigDecimal O_CdLingua;
	
	/** O_ descrizione lingua. */
	private String O_DsLingua;
	
	/** O_ codice tipo identificativo. */
	private BigDecimal O_CdTipoIdentificativo;
	
	/** O_ descrizione tipo identificativo. */
	private String O_DsTipoIdentificativo;
	
	/** O_ codice identificativo. */
	private String O_CdIdentificativo;
	
	/** O_ codice identificativo isbn. */
	private String O_CdIdentificativoIsbn;
	
	/** O_ codice identificativo issn. */
	private String O_CdIdentificativoIssn;
	
	/** O_ dn_ luogo pubblicazione. */
	private String O_Dn_LuogoPubblicazione;
	
	/** O_ codice qualificatore data. */
	private BigDecimal O_CdQualificatoreData;
	
	/** O_ descrizione qualificatore data. */
	private String O_DsQualificatoreData;
	
	/** O_ descrizione data. */
	private String O_DsData;
	
	/** O_ descrizione note. */
	private String O_DsNote;
	
	/** O_ codice supporto. */
	private BigDecimal O_CdSupporto;
	
	/** O_ descrizione supporto. */
	private String O_DsSupporto;
	
	/** O_ codice tecnica grafica. */
	private BigDecimal O_CdTecnicaGrafica;
	
	/** O_ descrizione tecnica grafica. */
	private String O_DsTecnicaGrafica;
	
	/** O_ codice tipo grafica. */
	private BigDecimal O_CdTipoGrafica;
	
	/** O_ descrizione tipo grafica. */
	private String O_DsTipoGrafica;
	
	/** O_ descrizione scala. */
	private String O_DsScala;
	
	/** O_ descrizione proiezione. */
	private String O_DsProiezione;
	
	/** O_ descrizione coordinate. */
	private String O_DsCoordinate;
	
	/** O_ codice soggetto prod. */
	private BigDecimal O_CdSoggettoProd;
	
	/** O_ descrizione soggetto prod. */
	private String O_DsSoggettoProd;
	
	/** O_ codice contesto arch. */
	private BigDecimal O_CdContestoArch;
	
	/** O_ descrizione contesto arch. */
	private String O_DsContestoArch;
	
	/** O_ codice tipo archivio. */
	private BigDecimal O_CdTipoArchivio;
	
	/** O_ descrizione tipo archivio. */
	private String O_DsTipoArchivio;
	
	/** O_ descrizione link catalogo. */
	private String O_DsLinkCatalogo;
	
	/** O_ descrizione diritti. */
	private String O_DsDiritti;
	
	/** O_ descrizione altreinfo. */
	private String O_DsAltreinfo;	
	
	/** O_ flag is critico. */
	private char O_FlIsCritico;
	
	/** O_ descrizione nota critico. */
	private String O_DsNotaCritico;	
	
	/** O_ data pubblicatoil. */
	private Date O_DtPubblicatoil;
	
	/** O_ nr rilevanza. */
	private BigDecimal O_NrRilevanza;
	
	private char O_FlPdfMultipagina;
	private BigDecimal O_CdWorkFilesystem;
	private char O_FlRicercaOcr;

	
	private BigDecimal COV_CdImmagine;
	private String COV_DnNomeImmagine;
	private BigDecimal COV_NrPxLarghezzaThumb;
	private BigDecimal COV_NrPxAltezzaThumb;
	private BigDecimal COV_NrPxLarghezzaReader;
	private BigDecimal COV_NrPxAltezzaReader;
	private BigDecimal COV_NrPxLarghezzaZoom;
	private BigDecimal COV_NrPxAltezzaZoom;
	
	private String O_Segnatura;	
	
	/**
	 * Istanzia un nuovo v oggetto dto.
	 */
	public VOggettoDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo v oggetto dto.
	 *
	 * @param O_CdOggettoOriginale  o_ codice oggetto originale
	 * @param O_DnTitolo  o_ denominazione titolo
	 * @param O_FlOggettoSuperiore  o_ flag oggetto superiore
	 * @param O_FlOggettoDigitale  o_ flag oggetto digitale
	 * @param O_FlCorrezione  o_ flag correzione
	 * @param O_FlAnomaliaImmagini  o_ flag anomalia immagini
	 * @param O_DsStato  o_ descrizione stato
	 * @param O_NrImmaginiPreviste  o_ nr immagini previste
	 */
	public VOggettoDTO(BigDecimal O_CdOggettoOriginale, String O_DnTitolo,
			char O_FlOggettoSuperiore, char O_FlOggettoDigitale,
			char O_FlCorrezione, char O_FlAnomaliaImmagini, String O_DsStato,
			BigDecimal O_NrImmaginiPreviste) {
		this.O_CdOggettoOriginale = O_CdOggettoOriginale;
		this.O_DnTitolo = O_DnTitolo;
		this.O_FlOggettoSuperiore = O_FlOggettoSuperiore;
		this.O_FlOggettoDigitale = O_FlOggettoDigitale;
		this.O_FlCorrezione = O_FlCorrezione;
		this.O_FlAnomaliaImmagini = O_FlAnomaliaImmagini;
		this.O_DsStato = O_DsStato;
		this.O_NrImmaginiPreviste = O_NrImmaginiPreviste;
	}

	/**
	 * Istanzia un nuovo v oggetto dto.
	 *
	 * @param D_CdDigitalizzatore  d_ codice digitalizzatore
	 * @param D_DnNome  d_ denominazione nome
	 * @param I_CdIstituto  i_ codice istituto
	 * @param I_DnNome  i_ denominazione nome
	 * @param P_CdProgetto  p_ codice progetto
	 * @param P_DnTitolo  p_ denominazione titolo
	 * @param P_DsStato  p_ descrizione stato
	 * @param C_CdCollezione  c_ codice collezione
	 * @param C_DnTitolo  c_ denominazione titolo
	 * @param O_CdOggettoOriginale  o_ codice oggetto originale
	 * @param O_DnTitolo  o_ denominazione titolo
	 * @param O_DnTitoloSup  o_ denominazione titolo sup
	 * @param O_CdOggettoOriginaleSup  o_ codice oggetto originale sup
	 * @param O_FlOggettoSuperiore  o_ flag oggetto superiore
	 * @param O_NrOggettiInferiori  o_ nr oggetti inferiori
	 * @param T_CdTipoOggetto  t_ codice tipo oggetto
	 * @param T_DnNome  t_ denominazione nome
	 * @param O_FlOggettoDigitale  o_ flag oggetto digitale
	 * @param O_FlCorrezione  o_ flag correzione
	 * @param O_DsNotaCorrezione  o_ descrizione nota correzione
	 * @param O_FlAnomaliaImmagini  o_ flag anomalia immagini
	 * @param O_DsLogAnomalia  o_ descrizione log anomalia
	 * @param O_DsStato  o_ descrizione stato
	 * @param O_DsNoteNonValidazione  o_ descrizione note non validazione
	 * @param O_DsNoteNonPubblicazione  o_ descrizione note non pubblicazione
	 * @param O_NrImmaginiPreviste  o_ nr immagini previste
	 * @param O_NrImmaginiDigitalizzate  o_ nr immagini digitalizzate
	 * @param O_DsContenutistica  o_ descrizione contenutistica
	 * @param O_DsFisica  o_ descrizione fisica
	 * @param O_CdLivelloBiblio  o_ codice livello biblio
	 * @param O_DsLivelloBiblio  o_ descrizione livello biblio
	 * @param O_CdSoggetto  o_ codice soggetto
	 * @param O_DsSoggetto  o_ descrizione soggetto
	 * @param O_CdAutore  o_ codice autore
	 * @param O_DsAutore  o_ descrizione autore
	 * @param O_CdQualificaAutore  o_ codice qualifica autore
	 * @param O_DsQualificaAutore  o_ descrizione qualifica autore
	 * @param O_CdAutoreSec  o_ codice autore sec
	 * @param O_DsAutoreSec  o_ descrizione autore sec
	 * @param O_CdQualificaAutoreSec  o_ codice qualifica autore sec
	 * @param O_DsQualificaAutoreSec  o_ descrizione qualifica autore sec
	 * @param O_CdEditore  o_ codice editore
	 * @param O_DsEditore  o_ descrizione editore
	 * @param O_CdLingua  o_ codice lingua
	 * @param O_DsLingua  o_ descrizione lingua
	 * @param O_CdTipoIdentificativo  o_ codice tipo identificativo
	 * @param O_DsTipoIdentificativo  o_ descrizione tipo identificativo
	 * @param O_CdIdentificativo  o_ codice identificativo
	 * @param O_CdIdentificativoIsbn  o_ codice identificativo isbn
	 * @param O_CdIdentificativoIssn  o_ codice identificativo issn
	 * @param O_Dn_LuogoPubblicazione  o_ dn_ luogo pubblicazione
	 * @param O_CdQualificatoreData  o_ codice qualificatore data
	 * @param O_DsQualificatoreData  o_ descrizione qualificatore data
	 * @param O_DsData  o_ descrizione data
	 * @param O_DsNote  o_ descrizione note
	 * @param O_CdSupporto  o_ codice supporto
	 * @param O_DsSupporto  o_ descrizione supporto
	 * @param O_CdTecnicaGrafica  o_ codice tecnica grafica
	 * @param O_DsTecnicaGrafica  o_ descrizione tecnica grafica
	 * @param O_CdTipoGrafica  o_ codice tipo grafica
	 * @param O_DsTipoGrafica  o_ descrizione tipo grafica
	 * @param O_DsScala  o_ descrizione scala
	 * @param O_DsProiezione  o_ descrizione proiezione
	 * @param O_DsCoordinate  o_ descrizione coordinate
	 * @param O_CdSoggettoProd  o_ codice soggetto prod
	 * @param O_DsSoggettoProd  o_ descrizione soggetto prod
	 * @param O_CdContestoArch  o_ codice contesto arch
	 * @param O_DsContestoArch  o_ descrizione contesto arch
	 * @param O_CdTipoArchivio  o_ codice tipo archivio
	 * @param O_DsTipoArchivio  o_ descrizione tipo archivio
	 * @param O_DsLinkCatalogo  o_ descrizione link catalogo
	 * @param O_DsDiritti  o_ descrizione diritti
	 * @param O_DsAltreinfo  o_ descrizione altreinfo
	 * @param O_FlIsCritico  o_ flag is critico
	 * @param O_DsNotaCritico  o_ descrizione nota critico
	 * @param O_DtPubblicatoil  o_ data pubblicatoil
	 * @param O_NrRilevanza  o_ nr rilevanza
	 * @param o_FlPdfMultipagina
	 * @param o_CdWorkFilesystem
	 * @param o_FlRicercaOcr
	 * 
	 */
	public VOggettoDTO(BigDecimal D_CdDigitalizzatore, String D_DnNome,
			BigDecimal I_CdIstituto, String I_DnNome, BigDecimal P_CdProgetto,
			String P_DnTitolo, String P_DsStato, BigDecimal C_CdCollezione,
			String C_DnTitolo, BigDecimal O_CdOggettoOriginale,
			String O_DnTitolo, String O_DnTitoloFe, String O_DnTitoloSup, BigDecimal O_CdOggettoOriginaleSup,
			char O_FlOggettoSuperiore, BigDecimal O_NrOggettiInferiori,
			BigDecimal T_CdTipoOggetto, String T_DnNome,
			char O_FlOggettoDigitale, char O_FlCorrezione,
			String O_DsNotaCorrezione, char O_FlAnomaliaImmagini,
			String O_DsLogAnomalia, String O_DsStato,
			String O_DsNoteNonValidazione, String O_DsNoteNonPubblicazione, BigDecimal O_NrImmaginiPreviste,
			BigDecimal O_NrImmaginiDigitalizzate,
			String O_DsContenutistica, String O_DsFisica, BigDecimal O_CdLivelloBiblio, 
			String O_DsLivelloBiblio, BigDecimal O_CdSoggetto, String O_DsSoggetto, 
			BigDecimal O_CdAutore, String O_DsAutore, BigDecimal O_CdQualificaAutore, 
			String O_DsQualificaAutore, BigDecimal O_CdAutoreSec, String O_DsAutoreSec, 
			BigDecimal O_CdQualificaAutoreSec, String O_DsQualificaAutoreSec, BigDecimal O_CdEditore, 
			String O_DsEditore, BigDecimal O_CdLingua, String O_DsLingua, BigDecimal O_CdTipoIdentificativo, 
			String O_DsTipoIdentificativo, String O_CdIdentificativo, String O_CdIdentificativoIsbn, 
			String O_CdIdentificativoIssn, String O_Dn_LuogoPubblicazione, BigDecimal O_CdQualificatoreData, 
			String O_DsQualificatoreData, String O_DsData, String O_DsNote, BigDecimal O_CdSupporto, 
			String O_DsSupporto, BigDecimal O_CdTecnicaGrafica, String O_DsTecnicaGrafica, BigDecimal O_CdTipoGrafica, 
			String O_DsTipoGrafica, String O_DsScala, String O_DsProiezione, String O_DsCoordinate, BigDecimal O_CdSoggettoProd, 
			String O_DsSoggettoProd, BigDecimal O_CdContestoArch, String O_DsContestoArch, BigDecimal O_CdTipoArchivio, 
			String O_DsTipoArchivio, String O_DsLinkCatalogo, String O_DsDiritti, String O_DsAltreinfo,
			char O_FlIsCritico, String O_DsNotaCritico, Date O_DtPubblicatoil, BigDecimal O_NrRilevanza, char O_FlPdfMultipagina, 
			BigDecimal O_CdWorkFilesystem, char O_FlRicercaOcr,			
			BigDecimal COV_CdImmagine,
			String COV_DnNomeImmagine,
			BigDecimal COV_NrPxLarghezzaThumb,
			BigDecimal COV_NrPxAltezzaThumb,
			BigDecimal COV_NrPxLarghezzaReader,
			BigDecimal COV_NrPxAltezzaReader,
			BigDecimal COV_NrPxLarghezzaZoom,
			BigDecimal COV_NrPxAltezzaZoom) {
		this.D_CdDigitalizzatore = D_CdDigitalizzatore;
		this.D_DnNome = D_DnNome;
		this.I_CdIstituto = I_CdIstituto;
		this.I_DnNome = I_DnNome;
		this.P_CdProgetto = P_CdProgetto;
		this.P_DnTitolo = P_DnTitolo;
		this.P_DsStato = P_DsStato;
		this.C_CdCollezione = C_CdCollezione;
		this.C_DnTitolo = C_DnTitolo;
		this.O_CdOggettoOriginale = O_CdOggettoOriginale;
		this.O_DnTitolo = O_DnTitolo;
		this.O_DnTitoloFe = O_DnTitoloFe;
		this.O_DnTitoloSup = O_DnTitoloSup;
		this.O_CdOggettoOriginaleSup = O_CdOggettoOriginaleSup;
		this.O_FlOggettoSuperiore = O_FlOggettoSuperiore;
		this.O_NrOggettiInferiori = O_NrOggettiInferiori;
		this.T_CdTipoOggetto = T_CdTipoOggetto;
		this.T_DnNome = T_DnNome;
		this.O_FlOggettoDigitale = O_FlOggettoDigitale;
		this.O_FlCorrezione = O_FlCorrezione;
		this.O_DsNotaCorrezione = O_DsNotaCorrezione;
		this.O_FlAnomaliaImmagini = O_FlAnomaliaImmagini;
		this.O_DsLogAnomalia = O_DsLogAnomalia;
		this.O_DsStato = O_DsStato;
		this.O_DsNoteNonValidazione = O_DsNoteNonValidazione;
		this.O_DsNoteNonPubblicazione = O_DsNoteNonPubblicazione;
		this.O_NrImmaginiPreviste = O_NrImmaginiPreviste;
		this.O_NrImmaginiDigitalizzate = O_NrImmaginiDigitalizzate;
		
		this.O_DsContenutistica = O_DsContenutistica;
		this.O_DsFisica = O_DsFisica;
		this.O_CdLivelloBiblio = O_CdLivelloBiblio;
		this.O_DsLivelloBiblio = O_DsLivelloBiblio;
		this.O_CdSoggetto = O_CdSoggetto;
		this.O_DsSoggetto = O_DsSoggetto;
		this.O_CdAutore = O_CdAutore;
		this.O_DsAutore = O_DsAutore;
		this.O_CdQualificaAutore = O_CdQualificaAutore;
		this.O_DsQualificaAutore = O_DsQualificaAutore;
		this.O_CdAutoreSec = O_CdAutoreSec;
		this.O_DsAutoreSec = O_DsAutoreSec;
		this.O_CdQualificaAutoreSec = O_CdQualificaAutoreSec;
		this.O_DsQualificaAutoreSec = O_DsQualificaAutoreSec;
		this.O_CdEditore = O_CdEditore;
		this.O_DsEditore = O_DsEditore;
		this.O_CdLingua = O_CdLingua;
		this.O_DsLingua = O_DsLingua;
		this.O_CdTipoIdentificativo = O_CdTipoIdentificativo;
		this.O_DsTipoIdentificativo = O_DsTipoIdentificativo;
		this.O_CdIdentificativo = O_CdIdentificativo;
		this.O_CdIdentificativoIsbn = O_CdIdentificativoIsbn;
		this.O_CdIdentificativoIssn = O_CdIdentificativoIssn;
		this.O_Dn_LuogoPubblicazione = O_Dn_LuogoPubblicazione;
		this.O_CdQualificatoreData = O_CdQualificatoreData;
		this.O_DsQualificatoreData = O_DsQualificatoreData;
		this.O_DsData = O_DsData;
		this.O_DsNote = O_DsNote;
		this.O_CdSupporto = O_CdSupporto;
		this.O_DsSupporto = O_DsSupporto;
		this.O_CdTecnicaGrafica = O_CdTecnicaGrafica;
		this.O_DsTecnicaGrafica = O_DsTecnicaGrafica;
		this.O_CdTipoGrafica = O_CdTipoGrafica;
		this.O_DsTipoGrafica = O_DsTipoGrafica;
		this.O_DsScala = O_DsScala;
		this.O_DsProiezione = O_DsProiezione;
		this.O_DsCoordinate = O_DsCoordinate;
		this.O_CdSoggettoProd = O_CdSoggettoProd;
		this.O_DsSoggettoProd = O_DsSoggettoProd;
		this.O_CdContestoArch = O_CdContestoArch;
		this.O_DsContestoArch = O_DsContestoArch;
		this.O_CdTipoArchivio = O_CdTipoArchivio;
		this.O_DsTipoArchivio = O_DsTipoArchivio;
		this.O_DsLinkCatalogo = O_DsLinkCatalogo;
		this.O_DsDiritti = O_DsDiritti;
		this.O_DsAltreinfo = O_DsAltreinfo;	
		
		this.O_FlIsCritico = O_FlIsCritico;
		this.O_DsNotaCritico = O_DsNotaCritico;
		
		this.O_DtPubblicatoil = O_DtPubblicatoil;
		this.O_NrRilevanza = O_NrRilevanza;

		this.O_FlPdfMultipagina = O_FlPdfMultipagina;
		this.O_CdWorkFilesystem = O_CdWorkFilesystem;
		this.O_FlRicercaOcr = O_FlRicercaOcr;
		
		this.COV_CdImmagine = COV_CdImmagine;
		this.COV_DnNomeImmagine = COV_DnNomeImmagine;
		this.COV_NrPxLarghezzaThumb = COV_NrPxLarghezzaThumb;
		this.COV_NrPxAltezzaThumb = COV_NrPxAltezzaThumb;
		this.COV_NrPxLarghezzaReader = COV_NrPxLarghezzaReader;
		this.COV_NrPxAltezzaReader = COV_NrPxAltezzaReader;
		this.COV_NrPxLarghezzaZoom = COV_NrPxLarghezzaZoom;
		this.COV_NrPxAltezzaZoom = COV_NrPxAltezzaZoom;
	}
	
	/**
	 * Legge o_ digitalizzatore id.
	 *
	 * @return o_ digitalizzatore id
	 */
	public String getO_DigitalizzatoreId(){
		return I_CdIstituto.toString()+"/"+P_CdProgetto.toString()+"/"+C_CdCollezione.toString()+"/"+O_CdOggettoOriginale.toString();
	}
	
	/**
	 * Legge o_ digitalizzatore id to copy.
	 *
	 * @return o_ digitalizzatore id to copy
	 */
	public String getO_DigitalizzatoreIdToCopy(){
		return I_CdIstituto.toString()+"/"+P_CdProgetto.toString()+"/"+C_CdCollezione.toString();
	}
	
	/**
	 * Legge o_ flag is critico_ human.
	 *
	 * @return o_ flag is critico_ human
	 */
	public String getO_FlIsCritico_Human(){
		
		if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO)){
			return getO_DsStato_Human();
		}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO)){
			return getO_DsStato_Human();
		}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO)){
			return getO_DsStato_Human();
		}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO) || O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE)){
			if(((Character)O_FlAnomaliaImmagini).equals(BdlSharedConstants.FLAG_TRUE)){
				return getO_FlAnomaliaImmagini_Human();
			}else if(((Character)O_FlCorrezione).equals(BdlSharedConstants.FLAG_TRUE)){
				return getO_FlCorrezione_Human();
			}
		}
		return "";
	}
	
	/**
	 * Legge o_ flag anomalia immagini_ human.
	 *
	 * @return o_ flag anomalia immagini_ human
	 */
	public String getO_FlAnomaliaImmagini_Human(){
		if(((Character)O_FlAnomaliaImmagini).equals(BdlSharedConstants.FLAG_TRUE)){
			return BdlSharedConstants.FLAG_TRUE_HUMAN;
		}
		return BdlSharedConstants.FLAG_FALSE_HUMAN;
	}
	
	/**
	 * Legge o_ flag correzione_ human.
	 *
	 * @return o_ flag correzione_ human
	 */
	public String getO_FlCorrezione_Human(){
		if(((Character)O_FlCorrezione).equals(BdlSharedConstants.FLAG_TRUE)){
			return BdlSharedConstants.FLAG_TRUE_HUMAN;
		}
		return BdlSharedConstants.FLAG_FALSE_HUMAN;
	}

	/**
	 * Legge o_ descrizione stato_ human.
	 *
	 * @return o_ descrizione stato_ human
	 */
	public String getO_DsStato_Human(){
		String statoHuman = null;
		if(O_DsStato!=null){
			if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO_HUMAN;
			}else if(O_DsStato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO_HUMAN;
			}
		}
		return statoHuman;
	}

	/**
	 * Legge d_ codice digitalizzatore.
	 *
	 * @return d_ codice digitalizzatore
	 */
	public BigDecimal getD_CdDigitalizzatore() {
		return this.D_CdDigitalizzatore;
	}

	/**
	 * Imposta d_ codice digitalizzatore.
	 *
	 * @param D_CdDigitalizzatore nuovo d_ codice digitalizzatore
	 */
	public void setD_CdDigitalizzatore(BigDecimal D_CdDigitalizzatore) {
		this.D_CdDigitalizzatore = D_CdDigitalizzatore;
	}

	/**
	 * Legge d_ denominazione nome.
	 *
	 * @return d_ denominazione nome
	 */
	public String getD_DnNome() {
		return this.D_DnNome;
	}

	/**
	 * Imposta d_ denominazione nome.
	 *
	 * @param D_DnNome nuovo d_ denominazione nome
	 */
	public void setD_DnNome(String D_DnNome) {
		this.D_DnNome = D_DnNome;
	}

	/**
	 * Legge i_ codice istituto.
	 *
	 * @return i_ codice istituto
	 */
	public BigDecimal getI_CdIstituto() {
		return this.I_CdIstituto;
	}

	/**
	 * Imposta i_ codice istituto.
	 *
	 * @param I_CdIstituto nuovo i_ codice istituto
	 */
	public void setI_CdIstituto(BigDecimal I_CdIstituto) {
		this.I_CdIstituto = I_CdIstituto;
	}

	/**
	 * Legge i_ denominazione nome.
	 *
	 * @return i_ denominazione nome
	 */
	public String getI_DnNome() {
		return this.I_DnNome;
	}

	/**
	 * Imposta i_ denominazione nome.
	 *
	 * @param I_DnNome nuovo i_ denominazione nome
	 */
	public void setI_DnNome(String I_DnNome) {
		this.I_DnNome = I_DnNome;
	}

	/**
	 * Legge p_ codice progetto.
	 *
	 * @return p_ codice progetto
	 */
	public BigDecimal getP_CdProgetto() {
		return this.P_CdProgetto;
	}

	/**
	 * Imposta p_ codice progetto.
	 *
	 * @param P_CdProgetto nuovo p_ codice progetto
	 */
	public void setP_CdProgetto(BigDecimal P_CdProgetto) {
		this.P_CdProgetto = P_CdProgetto;
	}

	/**
	 * Legge p_ denominazione titolo.
	 *
	 * @return p_ denominazione titolo
	 */
	public String getP_DnTitolo() {
		return this.P_DnTitolo;
	}

	/**
	 * Imposta p_ denominazione titolo.
	 *
	 * @param P_DnTitolo nuovo p_ denominazione titolo
	 */
	public void setP_DnTitolo(String P_DnTitolo) {
		this.P_DnTitolo = P_DnTitolo;
	}

	/**
	 * Legge p_ descrizione stato.
	 *
	 * @return p_ descrizione stato
	 */
	public String getP_DsStato() {
		return this.P_DsStato;
	}

	/**
	 * Imposta p_ descrizione stato.
	 *
	 * @param P_DsStato nuovo p_ descrizione stato
	 */
	public void setP_DsStato(String P_DsStato) {
		this.P_DsStato = P_DsStato;
	}

	/**
	 * Legge c_ codice collezione.
	 *
	 * @return c_ codice collezione
	 */
	public BigDecimal getC_CdCollezione() {
		return this.C_CdCollezione;
	}

	/**
	 * Imposta c_ codice collezione.
	 *
	 * @param C_CdCollezione nuovo c_ codice collezione
	 */
	public void setC_CdCollezione(BigDecimal C_CdCollezione) {
		this.C_CdCollezione = C_CdCollezione;
	}

	/**
	 * Legge c_ denominazione titolo.
	 *
	 * @return c_ denominazione titolo
	 */
	public String getC_DnTitolo() {
		return this.C_DnTitolo;
	}

	/**
	 * Imposta c_ denominazione titolo.
	 *
	 * @param C_DnTitolo nuovo c_ denominazione titolo
	 */
	public void setC_DnTitolo(String C_DnTitolo) {
		this.C_DnTitolo = C_DnTitolo;
	}

	/**
	 * Legge o_ codice oggetto originale.
	 *
	 * @return o_ codice oggetto originale
	 */
	public BigDecimal getO_CdOggettoOriginale() {
		return this.O_CdOggettoOriginale;
	}

	/**
	 * Imposta o_ codice oggetto originale.
	 *
	 * @param O_CdOggettoOriginale nuovo o_ codice oggetto originale
	 */
	public void setO_CdOggettoOriginale(BigDecimal O_CdOggettoOriginale) {
		this.O_CdOggettoOriginale = O_CdOggettoOriginale;
	}

	/**
	 * Legge o_ denominazione titolo.
	 *
	 * @return o_ denominazione titolo
	 */
	public String getO_DnTitolo() {
		return this.O_DnTitolo;
	}

	/**
	 * Imposta o_ denominazione titolo.
	 *
	 * @param O_DnTitolo nuovo o_ denominazione titolo
	 */
	public void setO_DnTitolo(String O_DnTitolo) {
		this.O_DnTitolo = O_DnTitolo;
	}

	/**
	 * Legge o_ denominazione titolo frontend.
	 *
	 * @return o_ denominazione titolo frontend
	 */
	public String getO_DnTitoloFe() {
		return this.O_DnTitoloFe;
	}

	/**
	 * Imposta o_ denominazione titolo frontend.
	 *
	 * @param O_DnTitoloFe nuovo o_ denominazione titolo
	 */
	public void setO_DnTitoloFe(String O_DnTitoloFe) {
		this.O_DnTitoloFe = O_DnTitoloFe;
	}

	/**
	 * Legge o_ denominazione titolo sup.
	 *
	 * @return o_ denominazione titolo sup
	 */
	public String getO_DnTitoloSup() {
		return this.O_DnTitoloSup;
	}

	/**
	 * Imposta o_ denominazione titolo sup.
	 *
	 * @param O_DnTitoloSup nuovo o_ denominazione titolo sup
	 */
	public void setO_DnTitoloSup(String O_DnTitoloSup) {
		this.O_DnTitoloSup = O_DnTitoloSup;
	}

	/**
	 * Legge o_ codice oggetto originale sup.
	 *
	 * @return o_ codice oggetto originale sup
	 */
	public BigDecimal getO_CdOggettoOriginaleSup() {
		return this.O_CdOggettoOriginaleSup;
	}

	/**
	 * Imposta o_ codice oggetto originale sup.
	 *
	 * @param O_CdOggettoOriginaleSup nuovo o_ codice oggetto originale sup
	 */
	public void setO_CdOggettoOriginaleSup(BigDecimal O_CdOggettoOriginaleSup) {
		this.O_CdOggettoOriginaleSup = O_CdOggettoOriginaleSup;
	}

	/**
	 * Legge o_ flag oggetto superiore.
	 *
	 * @return o_ flag oggetto superiore
	 */
	public char getO_FlOggettoSuperiore() {
		return this.O_FlOggettoSuperiore;
	}

	/**
	 * Legge o_ flag oggetto superiore bool.
	 *
	 * @return o_ flag oggetto superiore bool
	 */
	public Boolean getO_FlOggettoSuperioreBool() {
		Character O_FlOggettoSuperioreCh = (Character)O_FlOggettoSuperiore;
		if(O_FlOggettoSuperioreCh!=null && O_FlOggettoSuperioreCh.equals(BdlSharedConstants.FLAG_TRUE)){
			return true;
		}else if(O_FlOggettoSuperioreCh!=null && O_FlOggettoSuperioreCh.equals(BdlSharedConstants.FLAG_FALSE)){
			return false;
		}
		return null;
	}

	/**
	 * Imposta o_ flag oggetto superiore.
	 *
	 * @param O_FlOggettoSuperiore nuovo o_ flag oggetto superiore
	 */
	public void setO_FlOggettoSuperiore(char O_FlOggettoSuperiore) {
		this.O_FlOggettoSuperiore = O_FlOggettoSuperiore;
	}

	/**
	 * Legge o_ nr oggetti inferiori.
	 *
	 * @return o_ nr oggetti inferiori
	 */
	public BigDecimal getO_NrOggettiInferiori() {
		return this.O_NrOggettiInferiori;
	}

	/**
	 * Imposta o_ nr oggetti inferiori.
	 *
	 * @param O_NrOggettiInferiori nuovo o_ nr oggetti inferiori
	 */
	public void setO_NrOggettiInferiori(BigDecimal O_NrOggettiInferiori) {
		this.O_NrOggettiInferiori = O_NrOggettiInferiori;
	}


	/**
	 * Legge t_ codice tipo oggetto.
	 *
	 * @return t_ codice tipo oggetto
	 */
	public BigDecimal getT_CdTipoOggetto() {
		return this.T_CdTipoOggetto;
	}

	/**
	 * Imposta t_ codice tipo oggetto.
	 *
	 * @param T_CdTipoOggetto nuovo t_ codice tipo oggetto
	 */
	public void setT_CdTipoOggetto(BigDecimal T_CdTipoOggetto) {
		this.T_CdTipoOggetto = T_CdTipoOggetto;
	}


	/**
	 * Legge t_ denominazione nome.
	 *
	 * @return t_ denominazione nome
	 */
	public String getT_DnNome() {
		return this.T_DnNome;
	}

	/**
	 * Imposta t_ denominazione nome.
	 *
	 * @param T_DnNome nuovo t_ denominazione nome
	 */
	public void setT_DnNome(String T_DnNome) {
		this.T_DnNome = T_DnNome;
	}


	/**
	 * Legge o_ flag oggetto digitale bool.
	 *
	 * @return o_ flag oggetto digitale bool
	 */
	public Boolean getO_FlOggettoDigitaleBool() {
		Character O_FlOggettoDigitaleCh = (Character)O_FlOggettoDigitale;
		if(O_FlOggettoDigitaleCh!=null && O_FlOggettoDigitaleCh.equals(BdlSharedConstants.FLAG_TRUE)){
			return true;
		}else if(O_FlOggettoDigitaleCh!=null && O_FlOggettoDigitaleCh.equals(BdlSharedConstants.FLAG_FALSE)){
			return false;
		}
		return null;
	}
	
	/**
	 * Legge o_ flag oggetto digitale.
	 *
	 * @return o_ flag oggetto digitale
	 */
	public char getO_FlOggettoDigitale() {
		return this.O_FlOggettoDigitale;
	}

	/**
	 * Imposta o_ flag oggetto digitale.
	 *
	 * @param O_FlOggettoDigitale nuovo o_ flag oggetto digitale
	 */
	public void setO_FlOggettoDigitale(char O_FlOggettoDigitale) {
		this.O_FlOggettoDigitale = O_FlOggettoDigitale;
	}


	/**
	 * Legge o_ flag correzione bool.
	 *
	 * @return o_ flag correzione bool
	 */
	public Boolean getO_FlCorrezioneBool() {
		Character O_FlCorrezioneCh = (Character)O_FlCorrezione;
		if(O_FlCorrezioneCh!=null && O_FlCorrezioneCh.equals(BdlSharedConstants.FLAG_TRUE)){
			return true;
		}else if(O_FlCorrezioneCh!=null && O_FlCorrezioneCh.equals(BdlSharedConstants.FLAG_FALSE)){
			return false;
		}
		return null;
	}
	
	/**
	 * Legge o_ flag correzione.
	 *
	 * @return o_ flag correzione
	 */
	public char getO_FlCorrezione() {
		return this.O_FlCorrezione;
	}

	/**
	 * Imposta o_ flag correzione.
	 *
	 * @param O_FlCorrezione nuovo o_ flag correzione
	 */
	public void setO_FlCorrezione(char O_FlCorrezione) {
		this.O_FlCorrezione = O_FlCorrezione;
	}


	/**
	 * Legge o_ descrizione nota correzione.
	 *
	 * @return o_ descrizione nota correzione
	 */
	public String getO_DsNotaCorrezione() {
		return this.O_DsNotaCorrezione;
	}

	/**
	 * Imposta o_ descrizione nota correzione.
	 *
	 * @param O_DsNotaCorrezione nuovo o_ descrizione nota correzione
	 */
	public void setO_DsNotaCorrezione(String O_DsNotaCorrezione) {
		this.O_DsNotaCorrezione = O_DsNotaCorrezione;
	}


	/**
	 * Legge o_ flag anomalia immagini bool.
	 *
	 * @return o_ flag anomalia immagini bool
	 */
	public Boolean getO_FlAnomaliaImmaginiBool() {
		Character O_FlAnomaliaImmaginiCh = (Character)O_FlAnomaliaImmagini;
		if(O_FlAnomaliaImmaginiCh!=null && O_FlAnomaliaImmaginiCh.equals(BdlSharedConstants.FLAG_TRUE)){
			return true;
		}else if(O_FlAnomaliaImmaginiCh!=null && O_FlAnomaliaImmaginiCh.equals(BdlSharedConstants.FLAG_FALSE)){
			return false;
		}
		return null;
	}
	
	/**
	 * Legge o_ flag anomalia immagini.
	 *
	 * @return o_ flag anomalia immagini
	 */
	public char getO_FlAnomaliaImmagini() {
		return this.O_FlAnomaliaImmagini;
	}

	/**
	 * Imposta o_ flag anomalia immagini.
	 *
	 * @param O_FlAnomaliaImmagini nuovo o_ flag anomalia immagini
	 */
	public void setO_FlAnomaliaImmagini(char O_FlAnomaliaImmagini) {
		this.O_FlAnomaliaImmagini = O_FlAnomaliaImmagini;
	}


	/**
	 * Legge o_ descrizione log anomalia.
	 *
	 * @return o_ descrizione log anomalia
	 */
	public String getO_DsLogAnomalia() {
		return this.O_DsLogAnomalia;
	}

	/**
	 * Imposta o_ descrizione log anomalia.
	 *
	 * @param O_DsLogAnomalia nuovo o_ descrizione log anomalia
	 */
	public void setO_DsLogAnomalia(String O_DsLogAnomalia) {
		this.O_DsLogAnomalia = O_DsLogAnomalia;
	}


	/**
	 * Legge o_ descrizione stato.
	 *
	 * @return o_ descrizione stato
	 */
	public String getO_DsStato() {
		return this.O_DsStato;
	}

	/**
	 * Imposta o_ descrizione stato.
	 *
	 * @param O_DsStato nuovo o_ descrizione stato
	 */
	public void setO_DsStato(String O_DsStato) {
		this.O_DsStato = O_DsStato;
	}


	/**
	 * Legge o_ descrizione note non validazione.
	 *
	 * @return o_ descrizione note non validazione
	 */
	public String getO_DsNoteNonValidazione() {
		return this.O_DsNoteNonValidazione;
	}

	/**
	 * Imposta o_ descrizione note non validazione.
	 *
	 * @param O_DsNoteNonValidazione nuovo o_ descrizione note non validazione
	 */
	public void setO_DsNoteNonValidazione(String O_DsNoteNonValidazione) {
		this.O_DsNoteNonValidazione = O_DsNoteNonValidazione;
	}


	/**
	 * Legge o_ nr immagini previste.
	 *
	 * @return o_ nr immagini previste
	 */
	public BigDecimal getO_NrImmaginiPreviste() {
		return this.O_NrImmaginiPreviste;
	}

	/**
	 * Imposta o_ nr immagini previste.
	 *
	 * @param O_NrImmaginiPreviste nuovo o_ nr immagini previste
	 */
	public void setO_NrImmaginiPreviste(BigDecimal O_NrImmaginiPreviste) {
		this.O_NrImmaginiPreviste = O_NrImmaginiPreviste;
	}


	/**
	 * Legge o_ nr immagini digitalizzate.
	 *
	 * @return o_ nr immagini digitalizzate
	 */
	public BigDecimal getO_NrImmaginiDigitalizzate() {
		return this.O_NrImmaginiDigitalizzate;
	}

	/**
	 * Imposta o_ nr immagini digitalizzate.
	 *
	 * @param O_NrImmaginiDigitalizzate nuovo o_ nr immagini digitalizzate
	 */
	public void setO_NrImmaginiDigitalizzate(
			BigDecimal O_NrImmaginiDigitalizzate) {
		this.O_NrImmaginiDigitalizzate = O_NrImmaginiDigitalizzate;
	}

	/**
	 * Legge o_ descrizione contenutistica.
	 *
	 * @return o_ descrizione contenutistica
	 */
	public String getO_DsContenutistica() {
		return O_DsContenutistica;
	}

	/**
	 * Imposta o_ descrizione contenutistica.
	 *
	 * @param o_DsContenutistica nuovo o_ descrizione contenutistica
	 */
	public void setO_DsContenutistica(String o_DsContenutistica) {
		O_DsContenutistica = o_DsContenutistica;
	}

	/**
	 * Legge o_ descrizione fisica.
	 *
	 * @return o_ descrizione fisica
	 */
	public String getO_DsFisica() {
		return O_DsFisica;
	}

	/**
	 * Imposta o_ descrizione fisica.
	 *
	 * @param o_DsFisica nuovo o_ descrizione fisica
	 */
	public void setO_DsFisica(String o_DsFisica) {
		O_DsFisica = o_DsFisica;
	}

	/**
	 * Legge o_ codice livello biblio.
	 *
	 * @return o_ codice livello biblio
	 */
	public BigDecimal getO_CdLivelloBiblio() {
		return O_CdLivelloBiblio;
	}

	/**
	 * Imposta o_ codice livello biblio.
	 *
	 * @param o_CdLivelloBiblio nuovo o_ codice livello biblio
	 */
	public void setO_CdLivelloBiblio(BigDecimal o_CdLivelloBiblio) {
		O_CdLivelloBiblio = o_CdLivelloBiblio;
	}

	/**
	 * Legge o_ descrizione livello biblio.
	 *
	 * @return o_ descrizione livello biblio
	 */
	public String getO_DsLivelloBiblio() {
		return O_DsLivelloBiblio;
	}

	/**
	 * Imposta o_ descrizione livello biblio.
	 *
	 * @param o_DsLivelloBiblio nuovo o_ descrizione livello biblio
	 */
	public void setO_DsLivelloBiblio(String o_DsLivelloBiblio) {
		O_DsLivelloBiblio = o_DsLivelloBiblio;
	}

	/**
	 * Legge o_ codice soggetto.
	 *
	 * @return o_ codice soggetto
	 */
	public BigDecimal getO_CdSoggetto() {
		return O_CdSoggetto;
	}

	/**
	 * Imposta o_ codice soggetto.
	 *
	 * @param o_CdSoggetto nuovo o_ codice soggetto
	 */
	public void setO_CdSoggetto(BigDecimal o_CdSoggetto) {
		O_CdSoggetto = o_CdSoggetto;
	}

	/**
	 * Legge o_ descrizione soggetto.
	 *
	 * @return o_ descrizione soggetto
	 */
	public String getO_DsSoggetto() {
		return O_DsSoggetto;
	}

	/**
	 * Imposta o_ descrizione soggetto.
	 *
	 * @param o_DsSoggetto nuovo o_ descrizione soggetto
	 */
	public void setO_DsSoggetto(String o_DsSoggetto) {
		O_DsSoggetto = o_DsSoggetto;
	}

	/**
	 * Legge o_ codice autore.
	 *
	 * @return o_ codice autore
	 */
	public BigDecimal getO_CdAutore() {
		return O_CdAutore;
	}

	/**
	 * Imposta o_ codice autore.
	 *
	 * @param o_CdAutore nuovo o_ codice autore
	 */
	public void setO_CdAutore(BigDecimal o_CdAutore) {
		O_CdAutore = o_CdAutore;
	}

	/**
	 * Legge o_ descrizione autore.
	 *
	 * @return o_ descrizione autore
	 */
	public String getO_DsAutore() {
		return O_DsAutore;
	}

	/**
	 * Imposta o_ descrizione autore.
	 *
	 * @param o_DsAutore nuovo o_ descrizione autore
	 */
	public void setO_DsAutore(String o_DsAutore) {
		O_DsAutore = o_DsAutore;
	}

	/**
	 * Legge o_ codice qualifica autore.
	 *
	 * @return o_ codice qualifica autore
	 */
	public BigDecimal getO_CdQualificaAutore() {
		return O_CdQualificaAutore;
	}

	/**
	 * Imposta o_ codice qualifica autore.
	 *
	 * @param o_CdQualificaAutore nuovo o_ codice qualifica autore
	 */
	public void setO_CdQualificaAutore(BigDecimal o_CdQualificaAutore) {
		O_CdQualificaAutore = o_CdQualificaAutore;
	}

	/**
	 * Legge o_ descrizione qualifica autore.
	 *
	 * @return o_ descrizione qualifica autore
	 */
	public String getO_DsQualificaAutore() {
		return O_DsQualificaAutore;
	}

	/**
	 * Imposta o_ descrizione qualifica autore.
	 *
	 * @param o_DsQualificaAutore nuovo o_ descrizione qualifica autore
	 */
	public void setO_DsQualificaAutore(String o_DsQualificaAutore) {
		O_DsQualificaAutore = o_DsQualificaAutore;
	}

	/**
	 * Legge o_ codice autore sec.
	 *
	 * @return o_ codice autore sec
	 */
	public BigDecimal getO_CdAutoreSec() {
		return O_CdAutoreSec;
	}

	/**
	 * Imposta o_ codice autore sec.
	 *
	 * @param o_CdAutoreSec nuovo o_ codice autore sec
	 */
	public void setO_CdAutoreSec(BigDecimal o_CdAutoreSec) {
		O_CdAutoreSec = o_CdAutoreSec;
	}

	/**
	 * Legge o_ descrizione autore sec.
	 *
	 * @return o_ descrizione autore sec
	 */
	public String getO_DsAutoreSec() {
		return O_DsAutoreSec;
	}

	/**
	 * Imposta o_ descrizione autore sec.
	 *
	 * @param o_DsAutoreSec nuovo o_ descrizione autore sec
	 */
	public void setO_DsAutoreSec(String o_DsAutoreSec) {
		O_DsAutoreSec = o_DsAutoreSec;
	}

	/**
	 * Legge o_ codice qualifica autore sec.
	 *
	 * @return o_ codice qualifica autore sec
	 */
	public BigDecimal getO_CdQualificaAutoreSec() {
		return O_CdQualificaAutoreSec;
	}

	/**
	 * Imposta o_ codice qualifica autore sec.
	 *
	 * @param o_CdQualificaAutoreSec nuovo o_ codice qualifica autore sec
	 */
	public void setO_CdQualificaAutoreSec(BigDecimal o_CdQualificaAutoreSec) {
		O_CdQualificaAutoreSec = o_CdQualificaAutoreSec;
	}

	/**
	 * Legge o_ descrizione qualifica autore sec.
	 *
	 * @return o_ descrizione qualifica autore sec
	 */
	public String getO_DsQualificaAutoreSec() {
		return O_DsQualificaAutoreSec;
	}

	/**
	 * Imposta o_ descrizione qualifica autore sec.
	 *
	 * @param o_DsQualificaAutoreSec nuovo o_ descrizione qualifica autore sec
	 */
	public void setO_DsQualificaAutoreSec(String o_DsQualificaAutoreSec) {
		O_DsQualificaAutoreSec = o_DsQualificaAutoreSec;
	}

	/**
	 * Legge o_ codice editore.
	 *
	 * @return o_ codice editore
	 */
	public BigDecimal getO_CdEditore() {
		return O_CdEditore;
	}

	/**
	 * Imposta o_ codice editore.
	 *
	 * @param o_CdEditore nuovo o_ codice editore
	 */
	public void setO_CdEditore(BigDecimal o_CdEditore) {
		O_CdEditore = o_CdEditore;
	}

	/**
	 * Legge o_ descrizione editore.
	 *
	 * @return o_ descrizione editore
	 */
	public String getO_DsEditore() {
		return O_DsEditore;
	}

	/**
	 * Imposta o_ descrizione editore.
	 *
	 * @param o_DsEditore nuovo o_ descrizione editore
	 */
	public void setO_DsEditore(String o_DsEditore) {
		O_DsEditore = o_DsEditore;
	}

	/**
	 * Legge o_ codice lingua.
	 *
	 * @return o_ codice lingua
	 */
	public BigDecimal getO_CdLingua() {
		return O_CdLingua;
	}

	/**
	 * Imposta o_ codice lingua.
	 *
	 * @param o_CdLingua nuovo o_ codice lingua
	 */
	public void setO_CdLingua(BigDecimal o_CdLingua) {
		O_CdLingua = o_CdLingua;
	}

	/**
	 * Legge o_ descrizione lingua.
	 *
	 * @return o_ descrizione lingua
	 */
	public String getO_DsLingua() {
		return O_DsLingua;
	}

	/**
	 * Imposta o_ descrizione lingua.
	 *
	 * @param o_DsLingua nuovo o_ descrizione lingua
	 */
	public void setO_DsLingua(String o_DsLingua) {
		O_DsLingua = o_DsLingua;
	}

	/**
	 * Legge o_ codice tipo identificativo.
	 *
	 * @return o_ codice tipo identificativo
	 */
	public BigDecimal getO_CdTipoIdentificativo() {
		return O_CdTipoIdentificativo;
	}

	/**
	 * Imposta o_ codice tipo identificativo.
	 *
	 * @param o_CdTipoIdentificativo nuovo o_ codice tipo identificativo
	 */
	public void setO_CdTipoIdentificativo(BigDecimal o_CdTipoIdentificativo) {
		O_CdTipoIdentificativo = o_CdTipoIdentificativo;
	}

	/**
	 * Legge o_ descrizione tipo identificativo.
	 *
	 * @return o_ descrizione tipo identificativo
	 */
	public String getO_DsTipoIdentificativo() {
		return O_DsTipoIdentificativo;
	}

	/**
	 * Imposta o_ descrizione tipo identificativo.
	 *
	 * @param o_DsTipoIdentificativo nuovo o_ descrizione tipo identificativo
	 */
	public void setO_DsTipoIdentificativo(String o_DsTipoIdentificativo) {
		O_DsTipoIdentificativo = o_DsTipoIdentificativo;
	}

	/**
	 * Legge o_ codice identificativo.
	 *
	 * @return o_ codice identificativo
	 */
	public String getO_CdIdentificativo() {
		return O_CdIdentificativo;
	}

	/**
	 * Imposta o_ codice identificativo.
	 *
	 * @param o_CdIdentificativo nuovo o_ codice identificativo
	 */
	public void setO_CdIdentificativo(String o_CdIdentificativo) {
		O_CdIdentificativo = o_CdIdentificativo;
	}

	/**
	 * Legge o_ codice identificativo isbn.
	 *
	 * @return o_ codice identificativo isbn
	 */
	public String getO_CdIdentificativoIsbn() {
		return O_CdIdentificativoIsbn;
	}

	/**
	 * Imposta o_ codice identificativo isbn.
	 *
	 * @param o_CdIdentificativoIsbn nuovo o_ codice identificativo isbn
	 */
	public void setO_CdIdentificativoIsbn(String o_CdIdentificativoIsbn) {
		O_CdIdentificativoIsbn = o_CdIdentificativoIsbn;
	}

	/**
	 * Legge o_ codice identificativo issn.
	 *
	 * @return o_ codice identificativo issn
	 */
	public String getO_CdIdentificativoIssn() {
		return O_CdIdentificativoIssn;
	}

	/**
	 * Imposta o_ codice identificativo issn.
	 *
	 * @param o_CdIdentificativoIssn nuovo o_ codice identificativo issn
	 */
	public void setO_CdIdentificativoIssn(String o_CdIdentificativoIssn) {
		O_CdIdentificativoIssn = o_CdIdentificativoIssn;
	}

	/**
	 * Legge o_ dn_ luogo pubblicazione.
	 *
	 * @return o_ dn_ luogo pubblicazione
	 */
	public String getO_Dn_LuogoPubblicazione() {
		return O_Dn_LuogoPubblicazione;
	}

	/**
	 * Imposta o_ dn_ luogo pubblicazione.
	 *
	 * @param o_Dn_LuogoPubblicazione nuovo o_ dn_ luogo pubblicazione
	 */
	public void setO_Dn_LuogoPubblicazione(String o_Dn_LuogoPubblicazione) {
		O_Dn_LuogoPubblicazione = o_Dn_LuogoPubblicazione;
	}

	/**
	 * Legge o_ codice qualificatore data.
	 *
	 * @return o_ codice qualificatore data
	 */
	public BigDecimal getO_CdQualificatoreData() {
		return O_CdQualificatoreData;
	}

	/**
	 * Imposta o_ codice qualificatore data.
	 *
	 * @param o_CdQualificatoreData nuovo o_ codice qualificatore data
	 */
	public void setO_CdQualificatoreData(BigDecimal o_CdQualificatoreData) {
		O_CdQualificatoreData = o_CdQualificatoreData;
	}

	/**
	 * Legge o_ descrizione qualificatore data.
	 *
	 * @return o_ descrizione qualificatore data
	 */
	public String getO_DsQualificatoreData() {
		return O_DsQualificatoreData;
	}

	/**
	 * Imposta o_ descrizione qualificatore data.
	 *
	 * @param o_DsQualificatoreData nuovo o_ descrizione qualificatore data
	 */
	public void setO_DsQualificatoreData(String o_DsQualificatoreData) {
		O_DsQualificatoreData = o_DsQualificatoreData;
	}

	/**
	 * Legge o_ descrizione data.
	 *
	 * @return o_ descrizione data
	 */
	public String getO_DsData() {
		return O_DsData;
	}

	/**
	 * Imposta o_ descrizione data.
	 *
	 * @param o_DsData nuovo o_ descrizione data
	 */
	public void setO_DsData(String o_DsData) {
		O_DsData = o_DsData;
	}

	/**
	 * Legge o_ descrizione note.
	 *
	 * @return o_ descrizione note
	 */
	public String getO_DsNote() {
		return O_DsNote;
	}

	/**
	 * Imposta o_ descrizione note.
	 *
	 * @param o_DsNote nuovo o_ descrizione note
	 */
	public void setO_DsNote(String o_DsNote) {
		O_DsNote = o_DsNote;
	}

	/**
	 * Legge o_ codice supporto.
	 *
	 * @return o_ codice supporto
	 */
	public BigDecimal getO_CdSupporto() {
		return O_CdSupporto;
	}

	/**
	 * Imposta o_ codice supporto.
	 *
	 * @param o_CdSupporto nuovo o_ codice supporto
	 */
	public void setO_CdSupporto(BigDecimal o_CdSupporto) {
		O_CdSupporto = o_CdSupporto;
	}

	/**
	 * Legge o_ descrizione supporto.
	 *
	 * @return o_ descrizione supporto
	 */
	public String getO_DsSupporto() {
		return O_DsSupporto;
	}

	/**
	 * Imposta o_ descrizione supporto.
	 *
	 * @param o_DsSupporto nuovo o_ descrizione supporto
	 */
	public void setO_DsSupporto(String o_DsSupporto) {
		O_DsSupporto = o_DsSupporto;
	}

	/**
	 * Legge o_ codice tecnica grafica.
	 *
	 * @return o_ codice tecnica grafica
	 */
	public BigDecimal getO_CdTecnicaGrafica() {
		return O_CdTecnicaGrafica;
	}

	/**
	 * Imposta o_ codice tecnica grafica.
	 *
	 * @param o_CdTecnicaGrafica nuovo o_ codice tecnica grafica
	 */
	public void setO_CdTecnicaGrafica(BigDecimal o_CdTecnicaGrafica) {
		O_CdTecnicaGrafica = o_CdTecnicaGrafica;
	}

	/**
	 * Legge o_ descrizione tecnica grafica.
	 *
	 * @return o_ descrizione tecnica grafica
	 */
	public String getO_DsTecnicaGrafica() {
		return O_DsTecnicaGrafica;
	}

	/**
	 * Imposta o_ descrizione tecnica grafica.
	 *
	 * @param o_DsTecnicaGrafica nuovo o_ descrizione tecnica grafica
	 */
	public void setO_DsTecnicaGrafica(String o_DsTecnicaGrafica) {
		O_DsTecnicaGrafica = o_DsTecnicaGrafica;
	}

	/**
	 * Legge o_ codice tipo grafica.
	 *
	 * @return o_ codice tipo grafica
	 */
	public BigDecimal getO_CdTipoGrafica() {
		return O_CdTipoGrafica;
	}

	/**
	 * Imposta o_ codice tipo grafica.
	 *
	 * @param o_CdTipoGrafica nuovo o_ codice tipo grafica
	 */
	public void setO_CdTipoGrafica(BigDecimal o_CdTipoGrafica) {
		O_CdTipoGrafica = o_CdTipoGrafica;
	}

	/**
	 * Legge o_ descrizione tipo grafica.
	 *
	 * @return o_ descrizione tipo grafica
	 */
	public String getO_DsTipoGrafica() {
		return O_DsTipoGrafica;
	}

	/**
	 * Imposta o_ descrizione tipo grafica.
	 *
	 * @param o_DsTipoGrafica nuovo o_ descrizione tipo grafica
	 */
	public void setO_DsTipoGrafica(String o_DsTipoGrafica) {
		O_DsTipoGrafica = o_DsTipoGrafica;
	}

	/**
	 * Legge o_ descrizione scala.
	 *
	 * @return o_ descrizione scala
	 */
	public String getO_DsScala() {
		return O_DsScala;
	}

	/**
	 * Imposta o_ descrizione scala.
	 *
	 * @param o_DsScala nuovo o_ descrizione scala
	 */
	public void setO_DsScala(String o_DsScala) {
		O_DsScala = o_DsScala;
	}

	/**
	 * Legge o_ descrizione proiezione.
	 *
	 * @return o_ descrizione proiezione
	 */
	public String getO_DsProiezione() {
		return O_DsProiezione;
	}

	/**
	 * Imposta o_ descrizione proiezione.
	 *
	 * @param o_DsProiezione nuovo o_ descrizione proiezione
	 */
	public void setO_DsProiezione(String o_DsProiezione) {
		O_DsProiezione = o_DsProiezione;
	}

	/**
	 * Legge o_ descrizione coordinate.
	 *
	 * @return o_ descrizione coordinate
	 */
	public String getO_DsCoordinate() {
		return O_DsCoordinate;
	}

	/**
	 * Imposta o_ descrizione coordinate.
	 *
	 * @param o_DsCoordinate nuovo o_ descrizione coordinate
	 */
	public void setO_DsCoordinate(String o_DsCoordinate) {
		O_DsCoordinate = o_DsCoordinate;
	}

	/**
	 * Legge o_ codice soggetto prod.
	 *
	 * @return o_ codice soggetto prod
	 */
	public BigDecimal getO_CdSoggettoProd() {
		return O_CdSoggettoProd;
	}

	/**
	 * Imposta o_ codice soggetto prod.
	 *
	 * @param o_CdSoggettoProd nuovo o_ codice soggetto prod
	 */
	public void setO_CdSoggettoProd(BigDecimal o_CdSoggettoProd) {
		O_CdSoggettoProd = o_CdSoggettoProd;
	}

	/**
	 * Legge o_ descrizione soggetto prod.
	 *
	 * @return o_ descrizione soggetto prod
	 */
	public String getO_DsSoggettoProd() {
		return O_DsSoggettoProd;
	}

	/**
	 * Imposta o_ descrizione soggetto prod.
	 *
	 * @param o_DsSoggettoProd nuovo o_ descrizione soggetto prod
	 */
	public void setO_DsSoggettoProd(String o_DsSoggettoProd) {
		O_DsSoggettoProd = o_DsSoggettoProd;
	}

	/**
	 * Legge o_ codice contesto arch.
	 *
	 * @return o_ codice contesto arch
	 */
	public BigDecimal getO_CdContestoArch() {
		return O_CdContestoArch;
	}

	/**
	 * Imposta o_ codice contesto arch.
	 *
	 * @param o_CdContestoArch nuovo o_ codice contesto arch
	 */
	public void setO_CdContestoArch(BigDecimal o_CdContestoArch) {
		O_CdContestoArch = o_CdContestoArch;
	}

	/**
	 * Legge o_ descrizione contesto arch.
	 *
	 * @return o_ descrizione contesto arch
	 */
	public String getO_DsContestoArch() {
		return O_DsContestoArch;
	}

	/**
	 * Imposta o_ descrizione contesto arch.
	 *
	 * @param o_DsContestoArch nuovo o_ descrizione contesto arch
	 */
	public void setO_DsContestoArch(String o_DsContestoArch) {
		O_DsContestoArch = o_DsContestoArch;
	}

	/**
	 * Legge o_ codice tipo archivio.
	 *
	 * @return o_ codice tipo archivio
	 */
	public BigDecimal getO_CdTipoArchivio() {
		return O_CdTipoArchivio;
	}

	/**
	 * Imposta o_ codice tipo archivio.
	 *
	 * @param o_CdTipoArchivio nuovo o_ codice tipo archivio
	 */
	public void setO_CdTipoArchivio(BigDecimal o_CdTipoArchivio) {
		O_CdTipoArchivio = o_CdTipoArchivio;
	}

	/**
	 * Legge o_ descrizione tipo archivio.
	 *
	 * @return o_ descrizione tipo archivio
	 */
	public String getO_DsTipoArchivio() {
		return O_DsTipoArchivio;
	}

	/**
	 * Imposta o_ descrizione tipo archivio.
	 *
	 * @param o_DsTipoArchivio nuovo o_ descrizione tipo archivio
	 */
	public void setO_DsTipoArchivio(String o_DsTipoArchivio) {
		O_DsTipoArchivio = o_DsTipoArchivio;
	}

	/**
	 * Legge o_ descrizione link catalogo.
	 *
	 * @return o_ descrizione link catalogo
	 */
	public String getO_DsLinkCatalogo() {
		return O_DsLinkCatalogo;
	}

	/**
	 * Imposta o_ descrizione link catalogo.
	 *
	 * @param o_DsLinkCatalogo nuovo o_ descrizione link catalogo
	 */
	public void setO_DsLinkCatalogo(String o_DsLinkCatalogo) {
		O_DsLinkCatalogo = o_DsLinkCatalogo;
	}

	/**
	 * Legge o_ descrizione diritti.
	 *
	 * @return o_ descrizione diritti
	 */
	public String getO_DsDiritti() {
		return O_DsDiritti;
	}

	/**
	 * Imposta o_ descrizione diritti.
	 *
	 * @param o_DsDiritti nuovo o_ descrizione diritti
	 */
	public void setO_DsDiritti(String o_DsDiritti) {
		O_DsDiritti = o_DsDiritti;
	}

	/**
	 * Legge o_ descrizione altreinfo.
	 *
	 * @return o_ descrizione altreinfo
	 */
	public String getO_DsAltreinfo() {
		return O_DsAltreinfo;
	}

	/**
	 * Imposta o_ descrizione altreinfo.
	 *
	 * @param o_DsAltreinfo nuovo o_ descrizione altreinfo
	 */
	public void setO_DsAltreinfo(String o_DsAltreinfo) {
		O_DsAltreinfo = o_DsAltreinfo;
	}

	/**
	 * Legge o_ flag is critico.
	 *
	 * @return o_ flag is critico
	 */
	public char getO_FlIsCritico() {
		return O_FlIsCritico;
	}

	/**
	 * Imposta o_ flag is critico.
	 *
	 * @param o_FlIsCritico nuovo o_ flag is critico
	 */
	public void setO_FlIsCritico(char o_FlIsCritico) {
		O_FlIsCritico = o_FlIsCritico;
	}
	
	/**
	 * Legge o_ descrizione nota critico.
	 *
	 * @return o_ descrizione nota critico
	 */
	public String getO_DsNotaCritico() {
		return O_DsNotaCritico;
	}

	/**
	 * Imposta o_ descrizione nota critico.
	 *
	 * @param o_DsNotaCritico nuovo o_ descrizione nota critico
	 */
	public void setO_DsNotaCritico(String o_DsNotaCritico) {
		O_DsNotaCritico = o_DsNotaCritico;
	}

	/**
	 * Legge o_ descrizione note non pubblicazione.
	 *
	 * @return o_ descrizione note non pubblicazione
	 */
	public String getO_DsNoteNonPubblicazione() {
		return O_DsNoteNonPubblicazione;
	}

	/**
	 * Imposta o_ descrizione note non pubblicazione.
	 *
	 * @param o_DsNoteNonPubblicazione nuovo o_ descrizione note non pubblicazione
	 */
	public void setO_DsNoteNonPubblicazione(String o_DsNoteNonPubblicazione) {
		O_DsNoteNonPubblicazione = o_DsNoteNonPubblicazione;
	}

	/**
	 * Legge o_ data pubblicatoil.
	 *
	 * @return o_ data pubblicatoil
	 */
	public Date getO_DtPubblicatoil() {
		return new Date(O_DtPubblicatoil.getTime());
	}

	/**
	 * Imposta o_ data pubblicatoil.
	 *
	 * @param o_DtPubblicatoil nuovo o_ data pubblicatoil
	 */
	public void setO_DtPubblicatoil(Date o_DtPubblicatoil) {
		O_DtPubblicatoil = o_DtPubblicatoil;
	}

	/**
	 * Legge o_ nr rilevanza.
	 *
	 * @return o_ nr rilevanza
	 */
	public BigDecimal getO_NrRilevanza() {
		return O_NrRilevanza;
	}

	/**
	 * Imposta o_ nr rilevanza.
	 *
	 * @param o_NrRilevanza nuovo o_ nr rilevanza
	 */
	public void setO_NrRilevanza(BigDecimal o_NrRilevanza) {
		O_NrRilevanza = o_NrRilevanza;
	}

	public char getO_FlPdfMultipagina() {
		return O_FlPdfMultipagina;
	}
	public void setO_FlPdfMultipagina(char o_FlPdfMultipagina) {
		O_FlPdfMultipagina = o_FlPdfMultipagina;
	}

	public char getO_FlRicercaOcr() {
		return O_FlRicercaOcr;
	}
	public void setO_FlRicercaOcr(char o_FlRicercaOcr) {
		O_FlRicercaOcr = o_FlRicercaOcr;
	}

	public BigDecimal getO_CdWorkFilesystem() {
		return O_CdWorkFilesystem;
	}
	public void setO_CdWorkFilesystem(BigDecimal o_CdWorkFilesystem) {
		O_CdWorkFilesystem = o_CdWorkFilesystem;
	}

	/**
	 * @return the cOV_CdImmagine
	 */
	public BigDecimal getCOV_CdImmagine() {
		return COV_CdImmagine;
	}

	/**
	 * @param cOV_CdImmagine the cOV_CdImmagine to set
	 */
	public void setCOV_CdImmagine(BigDecimal cOV_CdImmagine) {
		COV_CdImmagine = cOV_CdImmagine;
	}

	/**
	 * @return the cOV_DnNomeImmagine
	 */
	public String getCOV_DnNomeImmagine() {
		return COV_DnNomeImmagine;
	}

	/**
	 * @param cOV_DnNomeImmagine the cOV_DnNomeImmagine to set
	 */
	public void setCOV_DnNomeImmagine(String cOV_DnNomeImmagine) {
		COV_DnNomeImmagine = cOV_DnNomeImmagine;
	}

	/**
	 * @return the cOV_NrPxLarghezzaThumb
	 */
	public BigDecimal getCOV_NrPxLarghezzaThumb() {
		return COV_NrPxLarghezzaThumb;
	}

	/**
	 * @param cOV_NrPxLarghezzaThumb the cOV_NrPxLarghezzaThumb to set
	 */
	public void setCOV_NrPxLarghezzaThumb(BigDecimal cOV_NrPxLarghezzaThumb) {
		COV_NrPxLarghezzaThumb = cOV_NrPxLarghezzaThumb;
	}

	/**
	 * @return the cOV_NrPxAltezzaThumb
	 */
	public BigDecimal getCOV_NrPxAltezzaThumb() {
		return COV_NrPxAltezzaThumb;
	}

	/**
	 * @param cOV_NrPxAltezzaThumb the cOV_NrPxAltezzaThumb to set
	 */
	public void setCOV_NrPxAltezzaThumb(BigDecimal cOV_NrPxAltezzaThumb) {
		COV_NrPxAltezzaThumb = cOV_NrPxAltezzaThumb;
	}

	/**
	 * @return the cOV_NrPxLarghezzaReader
	 */
	public BigDecimal getCOV_NrPxLarghezzaReader() {
		return COV_NrPxLarghezzaReader;
	}

	/**
	 * @param cOV_NrPxLarghezzaReader the cOV_NrPxLarghezzaReader to set
	 */
	public void setCOV_NrPxLarghezzaReader(BigDecimal cOV_NrPxLarghezzaReader) {
		COV_NrPxLarghezzaReader = cOV_NrPxLarghezzaReader;
	}

	/**
	 * @return the cOV_NrPxAltezzaReader
	 */
	public BigDecimal getCOV_NrPxAltezzaReader() {
		return COV_NrPxAltezzaReader;
	}

	/**
	 * @param cOV_NrPxAltezzaReader the cOV_NrPxAltezzaReader to set
	 */
	public void setCOV_NrPxAltezzaReader(BigDecimal cOV_NrPxAltezzaReader) {
		COV_NrPxAltezzaReader = cOV_NrPxAltezzaReader;
	}

	/**
	 * @return the cOV_NrPxLarghezzaZoom
	 */
	public BigDecimal getCOV_NrPxLarghezzaZoom() {
		return COV_NrPxLarghezzaZoom;
	}

	/**
	 * @param cOV_NrPxLarghezzaZoom the cOV_NrPxLarghezzaZoom to set
	 */
	public void setCOV_NrPxLarghezzaZoom(BigDecimal cOV_NrPxLarghezzaZoom) {
		COV_NrPxLarghezzaZoom = cOV_NrPxLarghezzaZoom;
	}

	/**
	 * @return the cOV_NrPxAltezzaZoom
	 */
	public BigDecimal getCOV_NrPxAltezzaZoom() {
		return COV_NrPxAltezzaZoom;
	}

	/**
	 * @param cOV_NrPxAltezzaZoom the cOV_NrPxAltezzaZoom to set
	 */
	public void setCOV_NrPxAltezzaZoom(BigDecimal cOV_NrPxAltezzaZoom) {
		COV_NrPxAltezzaZoom = cOV_NrPxAltezzaZoom;
	}

	public String getO_Segnatura() {
		return O_Segnatura;
	}

	public void setO_Segnatura(String o_Segnatura) {
		O_Segnatura = o_Segnatura;
	}
}
