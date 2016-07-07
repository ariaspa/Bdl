package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


/**
 * Class TocSommarioDTO.
 */
@XmlRootElement
public class TocSommarioDTO implements java.io.Serializable {

	/**
	 * Interface TocSommarioDTOProperties.
	 */
	public interface TocSommarioDTOProperties extends PropertyAccess<TocSommarioDTO> {
		
		/**
		 * Chiave.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<TocSommarioDTO> chiave();
		
		/**
		 * Nome toc.
		 *
		 * @return value provider
		 */
		ValueProvider<TocSommarioDTO, String> nomeToc();
		
		/**
		 * Nome immagine.
		 *
		 * @return value provider
		 */
		ValueProvider<TocSommarioDTO, String> nomeImmagine();
	}

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 1177043671654040805L;

	/** chiave. */
	private Integer chiave;
	
	/** nome toc. */
	private String nomeToc;
	
	/** nome immagine. */
	private String nomeImmagine;
	
	/** codice immagine. */
	private BigDecimal cdImmagine;
	
	/** children. */
	private List<TocSommarioDTO> children;
	
	/**
	 * Istanzia un nuovo toc sommario dto.
	 */
	public TocSommarioDTO(){
		// do nothing...
	}

	/**
	 * Istanzia un nuovo toc sommario dto.
	 *
	 * @param chiave  chiave
	 * @param nomeToc  nome toc
	 */
	public TocSommarioDTO(Integer chiave, String nomeToc) {
		this.chiave = chiave;
		this.nomeToc = nomeToc;
		this.children = new ArrayList<TocSommarioDTO>();
	}
	
	/**
	 * Istanzia un nuovo toc sommario dto.
	 *
	 * @param chiave  chiave
	 * @param nomeToc  nome toc
	 * @param nomeImmagine  nome immagine
	 */
	public TocSommarioDTO(Integer chiave, String nomeToc, String nomeImmagine) {
		
		this.chiave = chiave;
		this.nomeToc = nomeToc;
		this.nomeImmagine = nomeImmagine;
		this.children = new ArrayList<TocSommarioDTO>();
	}
	
	/**
	 * Istanzia un nuovo toc sommario dto.
	 *
	 * @param chiave  chiave
	 * @param nomeToc  nome toc
	 * @param cdImmagine  codice immagine
	 */
	public TocSommarioDTO(Integer chiave, String nomeToc, BigDecimal cdImmagine) {
		
		this.chiave = chiave;
		this.nomeToc = nomeToc;
		this.cdImmagine = cdImmagine;
		this.children = new ArrayList<TocSommarioDTO>();
	}

	/**
	 * Aggiunge child.
	 *
	 * @param itm  itm
	 */
	public void addChild(TocSommarioDTO itm){
		if(this.children==null){
			this.children = new ArrayList<TocSommarioDTO>();
		}
		this.children.add(itm);
	}
	
	/**
	 * Controlla children.
	 *
	 * @return true, se vero
	 */
	public boolean hasChildren(){
		if(this.children!=null){
			if(!this.children.isEmpty()){
				return true;
			}
		}
		return false;
	}

	public TocSommarioDTO findChildByName(String nome){
		if(this.children!=null && !this.children.isEmpty()){
			for(TocSommarioDTO toc:this.children){
				if(toc.getNomeToc().equals(nome)){
					return toc;
				}
			}
		}
		return null;
	}
	/**
	 * Legge chiave.
	 *
	 * @return chiave
	 */
	public Integer getChiave() {
		return chiave;
	}

	/**
	 * Imposta chiave.
	 *
	 * @param chiave nuovo chiave
	 */
	public void setChiave(Integer chiave) {
		this.chiave = chiave;
	}

	/**
	 * Legge nome toc.
	 *
	 * @return nome toc
	 */
	public String getNomeToc() {
		return nomeToc;
	}

	/**
	 * Imposta nome toc.
	 *
	 * @param nomeToc nuovo nome toc
	 */
	public void setNomeToc(String nomeToc) {
		this.nomeToc = nomeToc;
	}

	/**
	 * Legge nome immagine.
	 *
	 * @return nome immagine
	 */
	public String getNomeImmagine() {
		return nomeImmagine;
	}

	/**
	 * Imposta nome immagine.
	 *
	 * @param nomeImmagine nuovo nome immagine
	 */
	public void setNomeImmagine(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	/**
	 * Legge codice immagine.
	 *
	 * @return codice immagine
	 */
	public BigDecimal getCdImmagine() {
		return cdImmagine;
	}

	/**
	 * Imposta codice immagine.
	 *
	 * @param cdImmagine nuovo codice immagine
	 */
	public void setCdImmagine(BigDecimal cdImmagine) {
		this.cdImmagine = cdImmagine;
	}

	/**
	 * Legge children.
	 *
	 * @return children
	 */
	public List<TocSommarioDTO> getChildren() {
		return children;
	}

	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<TocSommarioDTO> children) {
		this.children = children;
	}
	
}
