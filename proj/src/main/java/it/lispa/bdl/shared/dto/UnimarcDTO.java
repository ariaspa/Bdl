package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


/**
 * Class UnimarcDTO.
 */
public class UnimarcDTO implements java.io.Serializable {


	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 5298257489487261280L;

	/** id. */
	private String id;
	
	/** tipo oggetto. */
	private String tipoOggetto;
	
	/** titolo. */
	private String titolo;
	
	/** descrizioni fisiche. */
	private List<String> descrizioniFisiche;
	
	/** descrizione fisica. */
	private String descrizioneFisica;
	
	/** autore. */
	private String autore;
	
	/** autori secondari1. */
	private List<String> autoriSecondari1;
	
	/** autore secondario1. */
	private String autoreSecondario1;
	
	/** autori secondari2. */
	private List<String> autoriSecondari2;
	
	/** autore secondario2. */
	private String autoreSecondario2;
	
	/** editori. */
	private List<String> editori;
	
	/** editore. */
	private String editore;
	
	/** lingua. */
	private String lingua;
	
	/** isbn. */
	private List<String> ISBN;
	
	/** ISB nsingolo. */
	private String ISBNsingolo;
	
	/** issn. */
	private List<String> ISSN;
	
	/** ISS nsingolo. */
	private String ISSNsingolo;
	
	/** luoghi pubblicazione. */
	private List<String> luoghiPubblicazione;
	
	/** luogo pubblicazione. */
	private String luogoPubblicazione;
	
	/** date pubblicazione. */
	private List<String> datePubblicazione;
	
	/** data pubblicazione. */
	private String dataPubblicazione;
	
	/** titoli superiori. */
	private List<String> titoliSuperiori;
	
	/** titolo superiore. */
	private String titoloSuperiore;
	
	/** titoli inferiori. */
	private List<String> titoliInferiori;
	
	/** path. */
	private String path;
	
	/** record. */
	private String record;
	
	/** immagini. */
	private BigDecimal immagini;
	

	/**
	 * Interface UnimarcDTOProperties.
	 */
	public interface UnimarcDTOProperties extends PropertyAccess<UnimarcDTO> {
		
		/**
		 * Id.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<UnimarcDTO> id();
		
		/**
		 * Sbn.
		 *
		 * @return value provider
		 */
		@Path("id")
		ValueProvider<UnimarcDTO,  String> sbn();
		
		/**
		 * Titolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> titolo();
		
		/**
		 * Tipo oggetto.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> tipoOggetto();
		
		/**
		 * Descrizione fisica.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> descrizioneFisica();
		
		/**
		 * Autore.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> autore();
		
		/**
		 * Autore secondario1.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> autoreSecondario1();
		
		/**
		 * Autore secondario2.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> autoreSecondario2();
		
		/**
		 * Editore.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> editore();
		
		/**
		 * Lingua.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> lingua();
		
		/**
		 * ISB nsingolo.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> ISBNsingolo();
		
		/**
		 * Luogo pubblicazione.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> luogoPubblicazione();
		
		/**
		 * Data pubblicazione.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> dataPubblicazione();
		
		/**
		 * Titolo superiore.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> titoloSuperiore();
		
		/**
		 * Path.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  String> path();
		
		/**
		 * Immagini.
		 *
		 * @return value provider
		 */
		ValueProvider<UnimarcDTO,  BigDecimal> immagini();
		
	}
	
	/**
	 * Istanzia un nuovo unimarc dto.
	 */
	public UnimarcDTO(){
		
	}
	
	/**
	 * Attenzione: le liste di stringhe implementate in questa classe sono state realizzate in accordo con
	 * le molteplicita' previste dal formato UNIMARC.
	 * In questo costruttore chiamiamo le SET in modo di impostare anche le proprieta' singolari
	 *
	 * @param id  id
	 * @param titolo  titolo
	 * @param tipoOggetto  tipo oggetto
	 * @param descrizioniFisiche  descrizioni fisiche
	 * @param autore  autore
	 * @param autoriSecondari1  autori secondari1
	 * @param autoriSecondari2  autori secondari2
	 * @param editori  editori
	 * @param lingua  lingua
	 * @param iSBN  i sbn
	 * @param iSSN  i ssn
	 * @param luoghiPubblicazione  luoghi pubblicazione
	 * @param datePubblicazione  date pubblicazione
	 * @param titoliSuperiori  titoli superiori
	 * @param titoliInferiori  titoli inferiori
	 * @param path  path
	 * @param record  record
	 */
    public UnimarcDTO(String id, String titolo, String tipoOggetto, List<String> descrizioniFisiche, String autore, List<String> autoriSecondari1, List<String> autoriSecondari2,
			List<String> editori, String lingua, List<String> iSBN, List<String> iSSN, List<String> luoghiPubblicazione, List<String> datePubblicazione,
			List<String> titoliSuperiori, List<String> titoliInferiori, String path, String record) {
		setId(id);
		setTitolo(titolo);
		setTipoOggetto(tipoOggetto);
		setDescrizioniFisiche(descrizioniFisiche);
		setAutore(autore);
		setAutoriSecondari1(autoriSecondari1);
		setAutoriSecondari2(autoriSecondari2);
		setEditori(editori);
		setLingua(lingua);
		setISBN(iSBN);
		setISSN(iSSN);
		setLuoghiPubblicazione(luoghiPubblicazione);
		setDatePubblicazione(datePubblicazione);
		setTitoliSuperiori(titoliSuperiori);
		setTitoliInferiori(titoliInferiori);
		setPath(path);
		setRecord(record);
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		result = prime * result
				+ ((ISBNsingolo == null) ? 0 : ISBNsingolo.hashCode());
		result = prime * result + ((ISSN == null) ? 0 : ISSN.hashCode());
		result = prime * result
				+ ((ISSNsingolo == null) ? 0 : ISSNsingolo.hashCode());
		result = prime * result + ((autore == null) ? 0 : autore.hashCode());
		result = prime
				* result
				+ ((autoreSecondario1 == null) ? 0 : autoreSecondario1
						.hashCode());
		result = prime
				* result
				+ ((autoreSecondario2 == null) ? 0 : autoreSecondario2
						.hashCode());
		result = prime
				* result
				+ ((autoriSecondari1 == null) ? 0 : autoriSecondari1.hashCode());
		result = prime
				* result
				+ ((autoriSecondari2 == null) ? 0 : autoriSecondari2.hashCode());
		result = prime
				* result
				+ ((dataPubblicazione == null) ? 0 : dataPubblicazione
						.hashCode());
		result = prime
				* result
				+ ((datePubblicazione == null) ? 0 : datePubblicazione
						.hashCode());
		result = prime
				* result
				+ ((descrizioneFisica == null) ? 0 : descrizioneFisica
						.hashCode());
		result = prime
				* result
				+ ((descrizioniFisiche == null) ? 0 : descrizioniFisiche
						.hashCode());
		result = prime * result + ((editore == null) ? 0 : editore.hashCode());
		result = prime * result + ((editori == null) ? 0 : editori.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((immagini == null) ? 0 : immagini.hashCode());
		result = prime * result + ((lingua == null) ? 0 : lingua.hashCode());
		result = prime
				* result
				+ ((luoghiPubblicazione == null) ? 0 : luoghiPubblicazione
						.hashCode());
		result = prime
				* result
				+ ((luogoPubblicazione == null) ? 0 : luogoPubblicazione
						.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		result = prime * result
				+ ((tipoOggetto == null) ? 0 : tipoOggetto.hashCode());
		result = prime * result
				+ ((titoliInferiori == null) ? 0 : titoliInferiori.hashCode());
		result = prime * result
				+ ((titoliSuperiori == null) ? 0 : titoliSuperiori.hashCode());
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
		result = prime * result
				+ ((titoloSuperiore == null) ? 0 : titoloSuperiore.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        if (object != null && object instanceof String){
            sameSame = this.id == ((String) object);
        }
        if (object != null && object instanceof UnimarcDTO) {
            sameSame = this.id == ((UnimarcDTO) object).id;
        }
        return sameSame;
    }
    
	/**
	 * Flatten list.
	 *
	 * @param list  list
	 * @return string
	 */
	private String flattenList(List<String> list){
    	String elm = null;
    	if(list!=null && !list.isEmpty()){
    		elm = list.get(0);
    	}
    	return elm;
    	
    }

	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}


	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Legge descrizioni fisiche.
	 *
	 * @return descrizioni fisiche
	 */
	public List<String> getDescrizioniFisiche() {
		return descrizioniFisiche;
	}
	
	/**
	 * Legge descrizione fisica.
	 *
	 * @return descrizione fisica
	 */
	public String getDescrizioneFisica() {
		return this.descrizioneFisica;
	}


	/**
	 * Imposta descrizioni fisiche.
	 *
	 * @param descrizioniFisiche nuovo descrizioni fisiche
	 */
	public void setDescrizioniFisiche(List<String> descrizioniFisiche) {
		this.descrizioniFisiche = descrizioniFisiche;
		this.descrizioneFisica = flattenList(descrizioniFisiche);
	}


	/**
	 * Legge autore.
	 *
	 * @return autore
	 */
	public String getAutore() {
		return autore;
	}


	/**
	 * Imposta autore.
	 *
	 * @param autore nuovo autore
	 */
	public void setAutore(String autore) {
		this.autore = autore;
	}


	/**
	 * Legge autori secondari1.
	 *
	 * @return autori secondari1
	 */
	public List<String> getAutoriSecondari1() {
		return autoriSecondari1;
	}
	
	/**
	 * Legge autore secondario1.
	 *
	 * @return autore secondario1
	 */
	public String getAutoreSecondario1() {
		return autoreSecondario1;
	}
	
	/**
	 * Legge autore secondario.
	 *
	 * @return autore secondario
	 */
	public String getAutoreSecondario(){
		if(this.getAutoreSecondario1()!=null && !"".equals(this.getAutoreSecondario1())){
			return this.getAutoreSecondario1();
		}else if(this.getAutoreSecondario2()!=null && !"".equals(this.getAutoreSecondario2())){
			return this.getAutoreSecondario2();
		}else {
			return null;
		}
	}


	/**
	 * Imposta autori secondari1.
	 *
	 * @param autoriSecondari1 nuovo autori secondari1
	 */
	public void setAutoriSecondari1(List<String> autoriSecondari1) {
		this.autoriSecondari1 = autoriSecondari1;
		this.autoreSecondario1 = flattenList(autoriSecondari1);
	}


	/**
	 * Legge autori secondari2.
	 *
	 * @return autori secondari2
	 */
	public List<String> getAutoriSecondari2() {
		return autoriSecondari2;
	}
	
	/**
	 * Legge autore secondario2.
	 *
	 * @return autore secondario2
	 */
	public String getAutoreSecondario2() {
		return autoreSecondario2;
	}


	/**
	 * Imposta autori secondari2.
	 *
	 * @param autoriSecondari2 nuovo autori secondari2
	 */
	public void setAutoriSecondari2(List<String> autoriSecondari2) {
		this.autoriSecondari2 = autoriSecondari2;
		this.autoreSecondario2 = flattenList(autoriSecondari2);
	}


	/**
	 * Legge editori.
	 *
	 * @return editori
	 */
	public List<String> getEditori() {
		return editori;
	}
	
	/**
	 * Legge editore.
	 *
	 * @return editore
	 */
	public String getEditore() {
		return editore;
	}


	/**
	 * Imposta editori.
	 *
	 * @param editori nuovo editori
	 */
	public void setEditori(List<String> editori) {
		this.editori = editori;
		this.editore = flattenList(editori);
	}


	/**
	 * Legge lingua.
	 *
	 * @return lingua
	 */
	public String getLingua() {
		return lingua;
	}


	/**
	 * Imposta lingua.
	 *
	 * @param lingua nuovo lingua
	 */
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}


	/**
	 * Legge isbn.
	 *
	 * @return isbn
	 */
	public List<String> getISBN() {
		return ISBN;
	}
	
	/**
	 * Legge ISB nsingolo.
	 *
	 * @return ISB nsingolo
	 */
	public String getISBNsingolo() {
		return ISBNsingolo;
	}


	/**
	 * Imposta isbn.
	 *
	 * @param iSBN nuovo isbn
	 */
	public void setISBN(List<String> iSBN) {
		ISBN = iSBN;
		ISBNsingolo = flattenList(iSBN);
	}


	/**
	 * Legge issn.
	 *
	 * @return issn
	 */
	public List<String> getISSN() {
		return ISSN;
	}
	
	/**
	 * Legge ISS nsingolo.
	 *
	 * @return ISS nsingolo
	 */
	public String getISSNsingolo() {
		return ISSNsingolo;
	}


	/**
	 * Imposta issn.
	 *
	 * @param iSSN nuovo issn
	 */
	public void setISSN(List<String> iSSN) {
		ISSN = iSSN;
		ISSNsingolo = flattenList(iSSN);
	}


	/**
	 * Legge luoghi pubblicazione.
	 *
	 * @return luoghi pubblicazione
	 */
	public List<String> getLuoghiPubblicazione() {
		return luoghiPubblicazione;
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
	 * Imposta luoghi pubblicazione.
	 *
	 * @param luoghiPubblicazione nuovo luoghi pubblicazione
	 */
	public void setLuoghiPubblicazione(List<String> luoghiPubblicazione) {
		this.luoghiPubblicazione = luoghiPubblicazione;
		this.luogoPubblicazione = flattenList(luoghiPubblicazione);
	}


	/**
	 * Legge date pubblicazione.
	 *
	 * @return date pubblicazione
	 */
	public List<String> getDatePubblicazione() {
		return datePubblicazione;
	}

	/**
	 * Legge data pubblicazione.
	 *
	 * @return data pubblicazione
	 */
	public String getDataPubblicazione() {
		return dataPubblicazione;
	}



	/**
	 * Imposta date pubblicazione.
	 *
	 * @param datePubblicazione nuovo date pubblicazione
	 */
	public void setDatePubblicazione(List<String> datePubblicazione) {
		this.datePubblicazione = datePubblicazione;
		this.dataPubblicazione = flattenList(datePubblicazione);
	}


	/**
	 * Legge titoli superiori.
	 *
	 * @return titoli superiori
	 */
	public List<String> getTitoliSuperiori() {
		return titoliSuperiori;
	}

	/**
	 * Legge titolo superiore.
	 *
	 * @return titolo superiore
	 */
	public String getTitoloSuperiore() {
		return titoloSuperiore;
	}


	/**
	 * Imposta titoli superiori.
	 *
	 * @param titoliSuperiori nuovo titoli superiori
	 */
	public void setTitoliSuperiori(List<String> titoliSuperiori) {
		this.titoliSuperiori = titoliSuperiori;
		this.titoloSuperiore = flattenList(titoliSuperiori);
	}


	/**
	 * Legge titoli inferiori.
	 *
	 * @return titoli inferiori
	 */
	public List<String> getTitoliInferiori() {
		return titoliInferiori;
	}

	/**
	 * Imposta titoli inferiori.
	 *
	 * @param titoliInferiori nuovo titoli inferiori
	 */
	public void setTitoliInferiori(List<String> titoliInferiori) {
		this.titoliInferiori = titoliInferiori;
	}


	/**
	 * Legge record.
	 *
	 * @return record
	 */
	public String getRecord() {
		return record;
	}


	/**
	 * Imposta record.
	 *
	 * @param record nuovo record
	 */
	public void setRecord(String record) {
		this.record = record;
	}


	/**
	 * Legge path.
	 *
	 * @return path
	 */
	public String getPath() {
		return path;
	}


	/**
	 * Imposta path.
	 *
	 * @param path nuovo path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Legge immagini.
	 *
	 * @return immagini
	 */
	public BigDecimal getImmagini() {
		return immagini;
	}

	/**
	 * Imposta immagini.
	 *
	 * @param immagini nuovo immagini
	 */
	public void setImmagini(BigDecimal immagini) {
		this.immagini = immagini;
	}

	/**
	 * Aggiunge titolo inferiore.
	 *
	 * @param titoloInferiore  titolo inferiore
	 */
	public void addTitoloInferiore(String titoloInferiore) {
		if(this.titoliInferiori==null){
			this.titoliInferiori = new ArrayList<String>();
		}
		this.titoliInferiori.add(titoloInferiore);
	}

	/**
	 * Legge tipo oggetto.
	 *
	 * @return tipo oggetto
	 */
	public String getTipoOggetto() {
		return tipoOggetto;
	}

	/**
	 * Imposta tipo oggetto.
	 *
	 * @param tipoOggetto nuovo tipo oggetto
	 */
	public void setTipoOggetto(String tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
}