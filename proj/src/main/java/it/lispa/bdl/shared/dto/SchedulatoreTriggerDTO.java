package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class SchedulatoreTriggerDTO.
 */
public class SchedulatoreTriggerDTO extends BaseDTO implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -2957218180110967258L;
	
	/**
	 * Interface SchedulatoreTriggerDTOProperties.
	 */
	public interface SchedulatoreTriggerDTOProperties extends PropertyAccess<SchedulatoreTriggerDTO> {
		
		/**
		 * Codice pk.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<SchedulatoreTriggerDTO> codicePk();
		
		/**
		 * Codice utente.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO,  BigDecimal> cdUtente();
		
		/**
		 * Nome utente.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, String> nomeUtente();
		
		/**
		 * Data start.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, Date> dtStart();
		
		/**
		 * Data end.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, Date> dtEnd();
		
		/**
		 * Descrizione tipo.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, String> dsTipo();
		
		/**
		 * Descrizione esito.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, String> dsEsito();
		
		/**
		 * Descrizione input.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, String> dsInput();
		
		/**
		 * Descrizione output.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreTriggerDTO, String> dsOutput();
	}	

	/** codice pk. */
	private String codicePk;
	
	/** codice utente. */
	private BigDecimal cdUtente;
	
	/** nome utente. */
	private String nomeUtente;
	
	/** data start. */
	private Long dtStart;
	
	/** data end. */
	private Long dtEnd;
	
	/** descrizione tipo. */
	private String dsTipo;
	
	/** descrizione esito. */
	private String dsEsito;
	
	/** descrizione input. */
	private String dsInput;
	
	/** descrizione output. */
	private String dsOutput;
	
	/**
	 * Istanzia un nuovo schedulatore trigger dto.
	 */
	public SchedulatoreTriggerDTO() {
		// do nothing...
	}
	

	
	/**
	 * Istanzia un nuovo schedulatore trigger dto.
	 *
	 * @param codicePk  codice pk
	 * @param cdUtente  codice utente
	 * @param nomeUtente  nome utente
	 * @param dtStart  data start
	 * @param dtEnd  data end
	 * @param dsTipo  descrizione tipo
	 * @param dsEsito  descrizione esito
	 * @param dsInput  descrizione input
	 * @param dsOutput  descrizione output
	 */
	public SchedulatoreTriggerDTO(String codicePk, BigDecimal cdUtente,
			String nomeUtente, Long dtStart, Long dtEnd, String dsTipo,
			String dsEsito, String dsInput, String dsOutput) {
		super();
		this.codicePk = codicePk;
		this.cdUtente = cdUtente;
		this.nomeUtente = nomeUtente;
		this.dtStart = dtStart;
		this.dtEnd = dtEnd;
		this.dsTipo = dsTipo;
		this.dsEsito = dsEsito;
		this.dsInput = dsInput;
		this.dsOutput = dsOutput;
	}



	/**
	 * Legge codice pk.
	 *
	 * @return codice pk
	 */
	public String getCodicePk() {
		return codicePk;
	}



	/**
	 * Imposta codice pk.
	 *
	 * @param codicePk nuovo codice pk
	 */
	public void setCodicePk(String codicePk) {
		this.codicePk = codicePk;
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
	 * Legge nome utente.
	 *
	 * @return nome utente
	 */
	public String getNomeUtente() {
		return nomeUtente;
	}



	/**
	 * Imposta nome utente.
	 *
	 * @param nomeUtente nuovo nome utente
	 */
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	
	/**
	 * Legge data start.
	 *
	 * @return data start
	 */
	public Date getDtStart() {
		return dateGetter(dtStart);
	}



	/**
	 * Imposta data start.
	 *
	 * @param dtStart nuovo data start
	 */
	public void setDtStart(Date dtStart) {
		this.dtStart = dateSetter(dtStart);
	}



	/**
	 * Legge data end.
	 *
	 * @return data end
	 */
	public Date getDtEnd() {
		return dateGetter(dtEnd);
	}



	/**
	 * Imposta data end.
	 *
	 * @param dtEnd nuovo data end
	 */
	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dateSetter(dtEnd);
	}



	/**
	 * Legge descrizione tipo.
	 *
	 * @return descrizione tipo
	 */
	public String getDsTipo() {
		return dsTipo;
	}



	/**
	 * Imposta descrizione tipo.
	 *
	 * @param dsTipo nuovo descrizione tipo
	 */
	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}



	/**
	 * Legge descrizione esito.
	 *
	 * @return descrizione esito
	 */
	public String getDsEsito() {
		return dsEsito;
	}



	/**
	 * Imposta descrizione esito.
	 *
	 * @param dsEsito nuovo descrizione esito
	 */
	public void setDsEsito(String dsEsito) {
		this.dsEsito = dsEsito;
	}



	/**
	 * Legge descrizione input.
	 *
	 * @return descrizione input
	 */
	public String getDsInput() {
		return dsInput;
	}



	/**
	 * Imposta descrizione input.
	 *
	 * @param dsInput nuovo descrizione input
	 */
	public void setDsInput(String dsInput) {
		this.dsInput = dsInput;
	}



	/**
	 * Legge descrizione output.
	 *
	 * @return descrizione output
	 */
	public String getDsOutput() {
		return dsOutput;
	}



	/**
	 * Imposta descrizione output.
	 *
	 * @param dsOutput nuovo descrizione output
	 */
	public void setDsOutput(String dsOutput) {
		this.dsOutput = dsOutput;
	}


}
