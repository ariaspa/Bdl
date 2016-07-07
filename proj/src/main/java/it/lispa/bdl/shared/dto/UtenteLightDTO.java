package it.lispa.bdl.shared.dto;

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


/**
 * Class UtenteLightDTO.
 */
public class UtenteLightDTO extends BaseDTO  implements java.io.Serializable {

	/**
	 * Interface UtenteLightDTOProperties.
	 */
	public interface UtenteLightDTOProperties extends PropertyAccess<UtenteLightDTO> {
		
		/**
		 * Codice utente.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<UtenteLightDTO> cdUtente();
		
		/**
		 * Codice ruolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO,  BigDecimal> cdRuolo();
		
		/**
		 * Nome.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> nome();
		
		/**
		 * Cognome.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> cognome();
		
		/**
		 * Email.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> email();
		
		/**
		 * Stato human.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> statoHuman();
		
		/**
		 * Ruolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> ruolo();
		
		/**
		 * Cf.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> cf();
		
		/**
		 * Telefono.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, String> telefono();
		
		/**
		 * Data registrazione.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, Date> dataRegistrazione();
		
		/**
		 * Data validazione.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteLightDTO, Date> dataValidazione();
	}
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -5696105083643295152L;

	/** codice utente. */
	protected BigDecimal cdUtente;
	
	/** nome. */
	protected String nome;
	
	/** cognome. */
	protected String cognome;
	
	/** cf. */
	protected String cf;
	
	/** email. */
	protected String email;
	
	/** telefono. */
	protected String telefono;
	
	/** codice ruolo. */
	protected BigDecimal cdRuolo;
	
	/** ruolo. */
	protected String ruolo;
	
	/** stato. */
	protected String stato;
	
	/** data registrazione. */
	protected Long dataRegistrazione;
	
	/** data validazione. */
	protected Long dataValidazione;
	
	/**
	 * Istanzia un nuovo utente light dto.
	 */
	public UtenteLightDTO(){
		// do nothing...
	}
			
	/**
	 * Istanzia un nuovo utente light dto.
	 *
	 * @param cdUtente  codice utente
	 * @param nome  nome
	 * @param cognome  cognome
	 * @param cf  cf
	 * @param email  email
	 * @param telefono  telefono
	 * @param cdRuolo  codice ruolo
	 * @param ruolo  ruolo
	 * @param stato  stato
	 * @param dataRegistrazione  data registrazione
	 * @param dataValidazione  data validazione
	 */
	public UtenteLightDTO(BigDecimal cdUtente, String nome, String cognome, String cf, String email, String telefono, BigDecimal cdRuolo, String ruolo, String stato, Date dataRegistrazione, Date dataValidazione) {
		this.cdUtente = cdUtente;
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.email = email;
		this.telefono = telefono;
		this.cdRuolo = cdRuolo;
		this.ruolo = ruolo;
		this.stato = stato;
		setDataRegistrazione(dataRegistrazione);
		setDataValidazione(dataValidazione);
	}

	/**
	 * Legge codice utente.
	 *
	 * @return codice utente
	 */
	public BigDecimal getCdUtente() {
		return cdUtente;
	}

	/**
	 * Imposta codice utente.
	 *
	 * @param cdUtente nuovo codice utente
	 */
	public void setCdUtente(BigDecimal cdUtente) {
		this.cdUtente = cdUtente;
	}

	/**
	 * Legge nome.
	 *
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta nome.
	 *
	 * @param nome nuovo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Legge cognome.
	 *
	 * @return cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * Imposta cognome.
	 *
	 * @param cognome nuovo cognome
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * Legge cf.
	 *
	 * @return cf
	 */
	public String getCf() {
		return cf;
	}

	/**
	 * Imposta cf.
	 *
	 * @param cf nuovo cf
	 */
	public void setCf(String cf) {
		this.cf = cf;
	}

	/**
	 * Legge email.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Imposta email.
	 *
	 * @param email nuovo email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Legge telefono.
	 *
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Imposta telefono.
	 *
	 * @param telefono nuovo telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Legge data registrazione.
	 *
	 * @return data registrazione
	 */
	public Date getDataRegistrazione() {
		return dateGetter(dataRegistrazione);
	}

	/**
	 * Imposta data registrazione.
	 *
	 * @param dataRegistrazione nuovo data registrazione
	 */
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dateSetter(dataRegistrazione);
	}

	/**
	 * Legge data validazione.
	 *
	 * @return data validazione
	 */
	public Date getDataValidazione() {
		return dateGetter(dataValidazione);
	}

	/**
	 * Imposta data validazione.
	 *
	 * @param dataValidazione nuovo data validazione
	 */
	public void setDataValidazione(Date dataValidazione) {
		this.dataValidazione = dateSetter(dataValidazione);
	}

	/**
	 * Legge codice ruolo.
	 *
	 * @return codice ruolo
	 */
	public BigDecimal getCdRuolo() {
		return cdRuolo;
	}

	/**
	 * Imposta codice ruolo.
	 *
	 * @param cdRuolo nuovo codice ruolo
	 */
	public void setCdRuolo(BigDecimal cdRuolo) {
		this.cdRuolo = cdRuolo;
	}

	/**
	 * Legge stato human.
	 *
	 * @return stato human
	 */
	public String getStatoHuman() {
		if(stato.equals(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO)){
			return BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO_HUMAN;
		}else if(stato.equals(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE)){
			return BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE_HUMAN;
		}else{
			return null;
		}
	}
}
