package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class UtenteDTO.
 */
public class UtenteDTO extends UtenteLightDTO implements java.io.Serializable {


	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 5298257636487467280L;
	
	/**
	 * Interface UtenteDTOProperties.
	 */
	public interface UtenteDTOProperties extends PropertyAccess<UtenteDTO> {
		
		/**
		 * Codice utente.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<UtenteDTO> cdUtente();
		
		/**
		 * Codice ruolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO,  BigDecimal> cdRuolo();
		
		/**
		 * Nome.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> nome();
		
		/**
		 * Cognome.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> cognome();
		
		/**
		 * Email.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> email();
		
		/**
		 * Stato human.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> statoHuman();
		
		/**
		 * Ruolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> ruolo();
		
		/**
		 * Cf.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> cf();
		
		/**
		 * Telefono.
		 *
		 * @return value provider
		 */
		ValueProvider<UtenteDTO, String> telefono();
		
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
	
	/** enti. */
	private List<String> enti;
	
	/** codice enti. */
	private List<BigDecimal> cdEnti;
	
	
	/**
	 * Istanzia un nuovo utente dto.
	 */
	public UtenteDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo utente dto.
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
	public UtenteDTO(BigDecimal cdUtente, String nome, String cognome, String cf, String email, String telefono, BigDecimal cdRuolo, String ruolo, String stato, Date dataRegistrazione, Date dataValidazione) {
		super(cdUtente, nome, cognome, cf, email, telefono, cdRuolo, ruolo, stato, dataRegistrazione, dataValidazione);
	}

	/**
	 * Istanzia un nuovo utente dto.
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
	 * @param enti  enti
	 * @param cdEnti  codice enti
	 */
	public UtenteDTO(BigDecimal cdUtente, String nome, String cognome, String cf, String email, String telefono, BigDecimal cdRuolo, String ruolo, String stato, Date dataRegistrazione, Date dataValidazione, List<String> enti, List<BigDecimal> cdEnti) {
		super(cdUtente, nome, cognome, cf, email, telefono, cdRuolo, ruolo, stato, dataRegistrazione, dataValidazione);
		this.enti = enti;
		this.cdEnti = cdEnti;
	}

	/**
	 * Legge enti.
	 *
	 * @return enti
	 */
	public List<String> getEnti() {
		return enti;
	}

	/**
	 * Imposta enti.
	 *
	 * @param enti nuovo enti
	 */
	public void setEnti(List<String> enti) {
		this.enti = enti;
	}
	
	/**
	 * Legge codice enti.
	 *
	 * @return codice enti
	 */
	public List<BigDecimal> getCdEnti() {
		return cdEnti;
	}

	/**
	 * Imposta codice enti.
	 *
	 * @param cdEnti nuovo codice enti
	 */
	public void setCdEnti(List<BigDecimal> cdEnti) {
		this.cdEnti = cdEnti;
	}
	
	/**
	 * Legge enti label.
	 *
	 * @return enti label
	 */
	public String getEntiLabel() {
		List<String> list = new ArrayList<String>();
		list.addAll(this.enti);
		
	    StringBuilder builder = new StringBuilder();	    
	    if(!list.isEmpty()){
		    builder.append( list.remove(0));
		    for( String s : list) {
		        builder.append( ", ");
		        builder.append( s);
		    }
	    }
	    return builder.toString();
	}


}
