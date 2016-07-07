package it.lispa.bdlfe.dto;

import java.math.BigDecimal;
import java.util.List;

public class BdlfeInstitute {

	private BigDecimal codice;
	private String nome;
	private String indirizzo;
	private String comune;
	private String provincia;
	private String cap;
	private String nroTelefono;
	private String nroFax;
	private String email;
	private String indirizzoWWW;
	private Integer nroOggetti;
	private BdlfeItem oggettoPrincipale;
	private List<BdlfeRefCount> collezioniPrincipali;
	
	public BdlfeInstitute(BigDecimal codice, String nome, String indirizzo,
			String comune, String provincia, String cap, String nroTelefono,
			String nroFax, String email, String indirizzoWWW,
			Integer nroOggetti, BdlfeItem oggettoPrincipale,
			List<BdlfeRefCount> collezioniPrincipali) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.comune = comune;
		this.provincia = provincia;
		this.cap = cap;
		this.nroTelefono = nroTelefono;
		this.nroFax = nroFax;
		this.email = email;
		this.indirizzoWWW = indirizzoWWW;
		this.nroOggetti = nroOggetti;
		this.oggettoPrincipale = oggettoPrincipale;
		this.collezioniPrincipali = collezioniPrincipali;
	}

	public BdlfeInstitute() {
	}
	
	public BigDecimal getCodice() {
		return codice;
	}
	public void setCodice(BigDecimal codice) {
		this.codice = codice;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getNroTelefono() {
		return nroTelefono;
	}
	public void setNroTelefono(String nroTelefono) {
		this.nroTelefono = nroTelefono;
	}
	
	public String getNroFax() {
		return nroFax;
	}
	public void setNroFax(String nroFax) {
		this.nroFax = nroFax;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIndirizzoWWW() {
		return indirizzoWWW;
	}
	public void setIndirizzoWWW(String indirizzoWWW) {
		this.indirizzoWWW = indirizzoWWW;
	}

	public Integer getNroOggetti() {
		return nroOggetti;
	}
	public void setNroOggetti(Integer nroOggetti) {
		this.nroOggetti = nroOggetti;
	}

	public BdlfeItem getOggettoPrincipale() {
		return oggettoPrincipale;
	}
	public void setOggettoPrincipale(BdlfeItem oggettoPrincipale) {
		this.oggettoPrincipale = oggettoPrincipale;
	}

	public List<BdlfeRefCount> getCollezioniPrincipali() {
		return collezioniPrincipali;
	}
	public void setCollezioniPrincipali(List<BdlfeRefCount> collezioniPrincipali) {
		this.collezioniPrincipali = collezioniPrincipali;
	}
}
