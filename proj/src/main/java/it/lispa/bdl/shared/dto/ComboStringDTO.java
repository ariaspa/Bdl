package it.lispa.bdl.shared.dto;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class ComboStringDTO.
 */
public class ComboStringDTO implements java.io.Serializable {
	
	/**
	 * Interface ComboStringDTOProperties.
	 */
	public interface ComboStringDTOProperties extends PropertyAccess<ComboStringDTO> {
		
		/**
		 * Id.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<ComboStringDTO> id();
		
		/**
		 * Desc.
		 *
		 * @return label provider
		 */
		LabelProvider<ComboStringDTO> desc();
	} 
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -2635209590647574252L;
	
	/** id. */
	private String id;
	
	/** desc. */
	private String desc;
	
	/** selected. */
	private boolean selected;
	
	/**
	 * Istanzia un nuovo combo string dto.
	 */
	public ComboStringDTO(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo combo string dto.
	 *
	 * @param id  id
	 * @param desc  desc
	 */
	public ComboStringDTO(String id, String desc){
		this.id = id;
		this.desc = desc;
	}
	
	/**
	 * Istanzia un nuovo combo string dto.
	 *
	 * @param id  id
	 * @param desc  desc
	 * @param selected  selected
	 */
	public ComboStringDTO(String id, String desc, boolean selected){
		this.id = id;
		this.desc = desc;
		this.selected = selected;
	}
	
	/**
	 * Imposta desc.
	 *
	 * @param desc nuovo desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Legge desc.
	 *
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public String getId(){
		return id; 
	}
	
	/**
	 * Controlla se Ã¨ selected.
	 *
	 * @return true, se Ã¨ selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Imposta selected.
	 *
	 * @param selected nuovo selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
