package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class SchedulatoreTriggerDTO.
 */
public class SchedulatoreJobsDTO extends BaseDTO implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -1351476017798934580L;



	/**
	 * Interface SchedulatoreTriggerDTOProperties.
	 */
	public interface SchedulatoreJobsDTOProperties extends PropertyAccess<SchedulatoreJobsDTO> {
		
		/**
		 * Codice bdl jobs.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<SchedulatoreJobsDTO> cdBdlJobs();
		
		/**
		 * Codice utente.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO,  BigDecimal> cdUtente();
		
		/**
		 * Nome utente.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, String> nomeUtente();
		
		/**
		 * Data start.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, Date> dtStart();
		
		/**
		 * Data end.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, Date> dtEnd();
		
		/**
		 * Descrizione tipo.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, String> dsTipo();
		
		/**
		 * Descrizione esito.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, String> dsEsito();
		
		/**
		 * Descrizione input.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, String> dsInput();
		
		/**
		 * Descrizione output.
		 *
		 * @return value provider
		 */
		ValueProvider<SchedulatoreJobsDTO, String> dsOutput();
	}	

	/** codice bdl jobs. */
	private BigDecimal cdBdlJobs;
	
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
	public SchedulatoreJobsDTO() {
		// do nothing...
	}
	

	
	/**
	 * Istanzia un nuovo schedulatore trigger dto.
	 *
	 * @param cdBdlJobs  codice pk
	 * @param cdUtente  codice utente
	 * @param nomeUtente  nome utente
	 * @param dtStart  data start
	 * @param dtEnd  data end
	 * @param dsTipo  descrizione tipo
	 * @param dsEsito  descrizione esito
	 * @param dsInput  descrizione input
	 * @param dsOutput  descrizione output
	 */
	public SchedulatoreJobsDTO(BigDecimal cdBdlJobs, BigDecimal cdUtente,
			String nomeUtente, Long dtStart, Long dtEnd, String dsTipo,
			String dsEsito, String dsInput, String dsOutput) {
		super();
		this.cdBdlJobs = cdBdlJobs;
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
	 * Legge codice bdl jobs.
	 *
	 * @return codice bdl jobs
	 */
	public BigDecimal getCdBdlJobs() {
		return cdBdlJobs;
	}



	/**
	 * Imposta codice bdl jobs.
	 *
	 * @param cdBdlJobs nuovo codice bdl jobs
	 */
	public void setCdBdlJobs(BigDecimal cdBdlJobs) {
		this.cdBdlJobs = cdBdlJobs;
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
