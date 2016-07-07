package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;
import java.text.ParseException;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;

/**
 * Class ComboDTO.
 */
public class ComboDTO implements java.io.Serializable {

	/**
	 * Class ComboDTOPropertyEditor.
	 */
	public static class ComboDTOPropertyEditor extends PropertyEditor<ComboDTO> {
		
		/** cella. */
		private ComboBoxCell<ComboDTO> cella;

		/**
		 * Istanzia un nuovo combo dto property editor.
		 *
		 * @param cella  cella
		 */
		public ComboDTOPropertyEditor(ComboBoxCell<ComboDTO> cella){
			this.cella = cella;
		}
	    
    	/* (non-Javadoc)
    	 * @see com.google.gwt.text.shared.Parser#parse(java.lang.CharSequence)
    	 */
    	@Override
	    public ComboDTO parse(CharSequence text) throws ParseException {
	    	ListStore<ComboDTO> store = cella.getStore();
	    	String toFind = text==null ? "" : text.toString();
	    	ComboDTO found = null;
	    	int count = store.size();
	        for (int i = 0; i < count; i++) {
	          ComboDTO item = store.get(i);
	          String v = item.getDesc();
	          if (v != null && v.equals(toFind)) {
	        	  found = item;
	          }
	        }
	        if(found!=null){
	        	cella.select(found);
	        	return found;
	        }else if(!"".equals(toFind)){
	        	return new ComboDTO(null,toFind); 
	        }
	        
	    	return null;
	    }
	
	
	    /* (non-Javadoc)
    	 * @see com.google.gwt.text.shared.Renderer#render(java.lang.Object)
    	 */
    	@Override
	    public String render(ComboDTO object) {
	        return object.getDesc();
	    }
	}
	
	/**
	 * Interface ComboDTOProperties.
	 */
	public interface ComboDTOProperties extends PropertyAccess<ComboDTO> {
		
		/**
		 * Id.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<ComboDTO> id();
		
		/**
		 * Desc.
		 *
		 * @return label provider
		 */
		LabelProvider<ComboDTO> desc();
		
		/**
		 * Value.
		 *
		 * @return value provider
		 */
		@Path("desc")
		ValueProvider<ComboDTO, String> value();
	} 
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -2635209590647574252L;
	
	/** id. */
	private BigDecimal id;
	
	/** desc. */
	private String desc;
	
	/** selected. */
	private boolean selected;
	
	/**
	 * Istanzia un nuovo combo dto.
	 */
	public ComboDTO(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo combo dto.
	 *
	 * @param id  id
	 * @param desc  desc
	 */
	public ComboDTO(BigDecimal id, String desc){
		this.id = id;
		this.desc = desc;
	}
	
	/**
	 * Istanzia un nuovo combo dto.
	 *
	 * @param id  id
	 * @param desc  desc
	 * @param selected  selected
	 */
	public ComboDTO(BigDecimal id, String desc, boolean selected){
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
	public void setId(BigDecimal id){
		this.id = id;
	}
	
	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public BigDecimal getId(){
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
