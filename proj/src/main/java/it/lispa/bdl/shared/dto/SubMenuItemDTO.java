package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


/**
 * Class SubMenuItemDTO.
 */
public class SubMenuItemDTO implements java.io.Serializable {

	/**
	 * Interface SubMenuItemDTOProperties.
	 */
	public interface SubMenuItemDTOProperties extends PropertyAccess<SubMenuItemDTO> {
		
		/**
		 * Chiave.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<SubMenuItemDTO> chiave();
		
		/**
		 * Label.
		 *
		 * @return value provider
		 */
		ValueProvider<SubMenuItemDTO, String> label();
	}
	
	/** La Costante CHIAVE_SEPARATOR. */
	public static final String CHIAVE_SEPARATOR = "|";

	/** La Costante TIPO_ROOT. */
	public static final String TIPO_ROOT = "root";
	
	/** La Costante TIPO_ISTITUTO. */
	public static final String TIPO_ISTITUTO = "istituto";
	
	/** La Costante TIPO_PROGETTO. */
	public static final String TIPO_PROGETTO = "progetto";
	
	/** La Costante TIPO_COLLEZIONE. */
	public static final String TIPO_COLLEZIONE = "collezione";
	
	/** La Costante TIPO_COLLOGGETTI. */
	public static final String TIPO_COLLOGGETTI = "colloggetti";
	
	/** La Costante TIPO_OGGETTO. */
	public static final String TIPO_OGGETTO = "oggetto";
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 553575121060451732L;

	/** chiave. */
	private String chiave;
	
	/** codice. */
	private BigDecimal codice;
	
	/** label. */
	private String label;
	
	/** tipo. */
	private String tipo;
	
	/** children. */
	private List<SubMenuItemDTO> children;
	
	/**
	 * Istanzia un nuovo sub menu item dto.
	 */
	public SubMenuItemDTO(){
		// do nothing...
	}
			
	/**
	 * Istanzia un nuovo sub menu item dto.
	 *
	 * @param codice  codice
	 * @param label  label
	 * @param tipo  tipo
	 */
	public SubMenuItemDTO(BigDecimal codice,String label, String tipo) {
		this.codice = codice;
		this.label = label;
		this.tipo = tipo;
		refreshChiave();
		this.children = new ArrayList<SubMenuItemDTO>();
	}

	/**
	 * Aggiunge child.
	 *
	 * @param mnu  mnu
	 */
	public void addChild(SubMenuItemDTO mnu){
		if(this.children==null){
			this.children = new ArrayList<SubMenuItemDTO>();
		}
		this.children.add(mnu);
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
	
	/**
	 * Refresh chiave.
	 */
	public void refreshChiave(){
		this.chiave = this.tipo+CHIAVE_SEPARATOR+this.codice.toString();
	}
	
	/**
	 * Legge codice.
	 *
	 * @return codice
	 */
	public BigDecimal getCodice() {
		return codice;
	}

	/**
	 * Legge label.
	 *
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Legge tipo.
	 *
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Legge children.
	 *
	 * @return children
	 */
	public List<SubMenuItemDTO> getChildren() {
		return children;
	}

	/**
	 * Imposta codice.
	 *
	 * @param codice nuovo codice
	 */
	public void setCodice(BigDecimal codice) {
		this.codice = codice;
		refreshChiave();
	}

	/**
	 * Imposta label.
	 *
	 * @param label nuovo label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Imposta tipo.
	 *
	 * @param tipo nuovo tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
		refreshChiave();
	}

	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<SubMenuItemDTO> children) {
		this.children = children;
	}

	/**
	 * Legge chiave.
	 *
	 * @return chiave
	 */
	public String getChiave() {
		return chiave;
	}

}
