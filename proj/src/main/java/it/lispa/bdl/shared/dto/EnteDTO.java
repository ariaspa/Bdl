package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class EnteDTO.
 */
public class EnteDTO implements java.io.Serializable{

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 2401572222932499763L;
	
	/** codice ente. */
	private BigDecimal cdEnte;
	
	/** codice ente digit. */
	private BigDecimal cdEnteDigit;
	
	/** ente digit. */
	private String enteDigit;
	
	/** nome. */
	private String nome;
	
	/** indirizzo. */
	private String indirizzo;
	
	/** comune. */
	private String comune;
	
	/** provincia. */
	private String provincia;
	
	/** provincia human. */
	private String provinciaHuman;
	
	/** cap. */
	private String cap;
	
	/** telefono. */
	private String telefono;
	
	/** fax. */
	private String fax;
	
	/** email. */
	private String email;
	
	/** indirizzo www. */
	private String indirizzoWww;
	
	/** classe. */
	private char classe;
	
	/** classe human. */
	private String classeHuman;
	
	/**
	 * Interface EnteDTOProperties.
	 */
	public interface EnteDTOProperties extends PropertyAccess<EnteDTO> {
		
		/**
		 * Codice ente.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<EnteDTO> cdEnte();
		
		/**
		 * Codice ente digit.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO,  BigDecimal> cdEnteDigit();
		
		/**
		 * Ente digit.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> enteDigit();
		
		/**
		 * Nome.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> nome();
		
		/**
		 * Indirizzo.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> indirizzo();
		
		/**
		 * Comune.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> comune();
		
		/**
		 * Provincia.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> provincia();
		
		/**
		 * Provincia human.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> provinciaHuman();
		
		/**
		 * Cap.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> cap();
		
		/**
		 * Telefono.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> telefono();
		
		/**
		 * Fax.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> fax();
		
		/**
		 * Email.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> email();
		
		/**
		 * Indirizzo www.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> indirizzoWww();
		
		/**
		 * Classe human.
		 *
		 * @return value provider
		 */
		ValueProvider<EnteDTO, String> classeHuman();
	}
	
	/**
	 * Istanzia un nuovo ente dto.
	 */
	public EnteDTO(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo ente dto.
	 *
	 * @param cdEnte  codice ente
	 * @param cdEnteDigit  codice ente digit
	 * @param enteDigit  ente digit
	 * @param nome  nome
	 * @param indirizzo  indirizzo
	 * @param comune  comune
	 * @param provincia  provincia
	 * @param provinciaHuman  provincia human
	 * @param cap  cap
	 * @param telefono  telefono
	 * @param fax  fax
	 * @param email  email
	 * @param indirizzoWww  indirizzo www
	 * @param classe  classe
	 * @param classeHuman  classe human
	 */
	public EnteDTO(BigDecimal cdEnte, BigDecimal cdEnteDigit, String enteDigit, String nome, String indirizzo, String comune, String provincia,
			String provinciaHuman, String cap, String telefono, String fax, String email, String indirizzoWww, char classe, String classeHuman) {
		this.cdEnte = cdEnte;
		this.cdEnteDigit = cdEnteDigit;
		this.enteDigit = enteDigit;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.provincia = provincia;
		this.provinciaHuman = provinciaHuman;
		this.cap = cap;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;
		this.indirizzoWww = indirizzoWww;
		this.classe = classe;
		this.classeHuman = classeHuman;
	}
	
	/**
	 * Legge codice ente.
	 *
	 * @return codice ente
	 */
	public BigDecimal getCdEnte() {
		return cdEnte;
	}
	
	/**
	 * Imposta codice ente.
	 *
	 * @param cdEnte nuovo codice ente
	 */
	public void setCdEnte(BigDecimal cdEnte) {
		this.cdEnte = cdEnte;
	}
	
	/**
	 * Legge codice ente digit.
	 *
	 * @return codice ente digit
	 */
	public BigDecimal getCdEnteDigit() {
		return cdEnteDigit;
	}
	
	/**
	 * Imposta codice ente digit.
	 *
	 * @param cdEnteDigit nuovo codice ente digit
	 */
	public void setCdEnteDigit(BigDecimal cdEnteDigit) {
		this.cdEnteDigit = cdEnteDigit;
	}
	
	/**
	 * Legge ente digit.
	 *
	 * @return ente digit
	 */
	public String getEnteDigit() {
		return enteDigit;
	}
	
	/**
	 * Imposta ente digit.
	 *
	 * @param enteDigit nuovo ente digit
	 */
	public void setEnteDigit(String enteDigit) {
		this.enteDigit = enteDigit;
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
	 * Legge indirizzo.
	 *
	 * @return indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	
	/**
	 * Imposta indirizzo.
	 *
	 * @param indirizzo nuovo indirizzo
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	/**
	 * Legge comune.
	 *
	 * @return comune
	 */
	public String getComune() {
		return comune;
	}
	
	/**
	 * Imposta comune.
	 *
	 * @param comune nuovo comune
	 */
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	/**
	 * Legge provincia.
	 *
	 * @return provincia
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/**
	 * Imposta provincia.
	 *
	 * @param provincia nuovo provincia
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	/**
	 * Legge provincia human.
	 *
	 * @return provincia human
	 */
	public String getProvinciaHuman() {
		return provinciaHuman;
	}
	
	/**
	 * Imposta provincia human.
	 *
	 * @param provinciaHuman nuovo provincia human
	 */
	public void setProvinciaHuman(String provinciaHuman) {
		this.provinciaHuman = provinciaHuman;
	}
	
	/**
	 * Legge cap.
	 *
	 * @return cap
	 */
	public String getCap() {
		return cap;
	}
	
	/**
	 * Imposta cap.
	 *
	 * @param cap nuovo cap
	 */
	public void setCap(String cap) {
		this.cap = cap;
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
	 * Legge fax.
	 *
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}
	
	/**
	 * Imposta fax.
	 *
	 * @param fax nuovo fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
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
	 * Legge indirizzo www.
	 *
	 * @return indirizzo www
	 */
	public String getIndirizzoWww() {
		return indirizzoWww;
	}
	
	/**
	 * Imposta indirizzo www.
	 *
	 * @param indirizzoWww nuovo indirizzo www
	 */
	public void setIndirizzoWww(String indirizzoWww) {
		this.indirizzoWww = indirizzoWww;
	}
	
	/**
	 * Legge classe.
	 *
	 * @return classe
	 */
	public char getClasse() {
		return classe;
	}
	
	/**
	 * Imposta classe.
	 *
	 * @param classe nuovo classe
	 */
	public void setClasse(char classe) {
		this.classe = classe;
	}
	
	/**
	 * Legge classe human.
	 *
	 * @return classe human
	 */
	public String getClasseHuman() {
		return classeHuman;
	}
	
	/**
	 * Imposta classe human.
	 *
	 * @param classeHuman nuovo classe human
	 */
	public void setClasseHuman(String classeHuman) {
		this.classeHuman = classeHuman;
	}
	
}
