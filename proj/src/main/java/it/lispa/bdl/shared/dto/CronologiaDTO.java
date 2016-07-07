package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class CronologiaDTO.
 */
public class CronologiaDTO extends BaseDTO  implements java.io.Serializable {

	/**
	 * Interface CronologiaDTOProperties.
	 */
	public interface CronologiaDTOProperties extends PropertyAccess<CronologiaDTO> {
		
		/**
		 * Codice cronologia.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<CronologiaDTO> cdCronologia();
		
		/**
		 * Data operazione.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  Date> dataOperazione();
		
		/**
		 * Ruolo.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  String> ruolo();
		
		/**
		 * Codice fiscale.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  String> codiceFiscale();
		
		/**
		 * Cognome nome.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  String> cognomeNome();
		
		/**
		 * Descrizione.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  String> descrizione();
		
		/**
		 * Codice oggetto originale.
		 *
		 * @return value provider
		 */
		ValueProvider<CronologiaDTO,  BigDecimal> cdOggettoOriginale();
		
	}
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -136079056919928842L;
	
	/** codice cronologia. */
	private BigDecimal cdCronologia;
	
	/** data operazione. */
	private Long dataOperazione;
	
	/** ruolo. */
	private String ruolo;
	
	/** codice fiscale. */
	private String codiceFiscale;
	
	/** cognome nome. */
	private String cognomeNome;

	/** descrizione. */
	private String descrizione;
	
	/** codice oggetto originale. */
	private BigDecimal cdOggettoOriginale;
	
	/**
	 * Istanzia un nuovo cronologia dto.
	 */
	public CronologiaDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo cronologia dto.
	 *
	 * @param cdCronologia  codice cronologia
	 * @param dataOperazione  data operazione
	 * @param ruolo  ruolo
	 * @param codiceFiscale  codice fiscale
	 * @param cognomeNome  cognome nome
	 * @param descrizione  descrizione
	 * @param cdOggettoOriginale  codice oggetto originale
	 */
	public CronologiaDTO(BigDecimal cdCronologia, Long dataOperazione, String ruolo, String codiceFiscale,String cognomeNome, String descrizione, BigDecimal cdOggettoOriginale) {
		this.cdCronologia = cdCronologia;
		this.dataOperazione = dataOperazione;
		this.ruolo = ruolo;
		this.codiceFiscale = codiceFiscale;
		this.cognomeNome = cognomeNome;
		this.descrizione = descrizione;
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Istanzia un nuovo cronologia dto.
	 *
	 * @param cdCronologia  codice cronologia
	 * @param dataOperazione  data operazione
	 * @param ruolo  ruolo
	 * @param codiceFiscale  codice fiscale
	 * @param cognomeNome  cognome nome
	 * @param descrizione  descrizione
	 * @param cdOggettoOriginale  codice oggetto originale
	 */
	public CronologiaDTO(BigDecimal cdCronologia, Date dataOperazione, String ruolo, String codiceFiscale, String cognomeNome,String descrizione, BigDecimal cdOggettoOriginale) {
		this.cdCronologia = cdCronologia;
		setDataOperazione(dataOperazione);
		this.ruolo = ruolo;
		this.codiceFiscale = codiceFiscale;
		this.cognomeNome = cognomeNome;
		this.descrizione = descrizione;
		this.cdOggettoOriginale = cdOggettoOriginale;
	}

	/**
	 * Legge codice cronologia.
	 *
	 * @return codice cronologia
	 */
	public BigDecimal getCdCronologia() {
		return cdCronologia;
	}

	/**
	 * Imposta codice cronologia.
	 *
	 * @param cdCronologia nuovo codice cronologia
	 */
	public void setCdCronologia(BigDecimal cdCronologia) {
		this.cdCronologia = cdCronologia;
	}

	/**
	 * Legge data operazione.
	 *
	 * @return data operazione
	 */
	public Date getDataOperazione() {
		return dateGetter(dataOperazione);
	}

	/**
	 * Imposta data operazione.
	 *
	 * @param dataOperazione nuovo data operazione
	 */
	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dateSetter(dataOperazione);
	}

	/**
	 * Legge ruolo.
	 *
	 * @return ruolo
	 */
	public String getRuolo() {
		return ruolo;
	}

	/**
	 * Imposta ruolo.
	 *
	 * @param ruolo nuovo ruolo
	 */
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	/**
	 * Legge codice fiscale.
	 *
	 * @return codice fiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * Imposta codice fiscale.
	 *
	 * @param codiceFiscale nuovo codice fiscale
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * Legge descrizione.
	 *
	 * @return descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Imposta descrizione.
	 *
	 * @param descrizione nuovo descrizione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
	 * Legge cognome nome.
	 *
	 * @return cognome nome
	 */
	public String getCognomeNome() {
		return cognomeNome;
	}

	/**
	 * Imposta cognome nome.
	 *
	 * @param cognomeNome nuovo cognome nome
	 */
	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}
}
