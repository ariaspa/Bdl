package it.lispa.bdl.shared.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Class MenuItemDTO.
 */
public class MenuItemDTO implements java.io.Serializable {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -7434567368746232022L;
	
	/** place token. */
	private String placeToken;
	
	/** place label. */
	private String placeLabel;
	
	/** children. */
	private List<MenuItemDTO> children;
	
	/**
	 * Istanzia un nuovo menu item dto.
	 */
	public MenuItemDTO(){
		// do nothing...
	}
			
	/**
	 * Istanzia un nuovo menu item dto.
	 *
	 * @param token  token
	 * @param label  label
	 */
	public MenuItemDTO(String token,String label) {
		this.placeToken = token;
		this.placeLabel = label;
		this.children = new ArrayList<MenuItemDTO>();
	}

	/**
	 * Aggiunge child.
	 *
	 * @param mnu  mnu
	 */
	public void addChild(MenuItemDTO mnu){
		if(this.children==null){
			this.children = new ArrayList<MenuItemDTO>();
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
	 * Legge place token.
	 *
	 * @return place token
	 */
	public String getPlaceToken() {
		return placeToken;
	}

	/**
	 * Imposta place token.
	 *
	 * @param placeToken nuovo place token
	 */
	public void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}

	/**
	 * Legge place label.
	 *
	 * @return place label
	 */
	public String getPlaceLabel() {
		return placeLabel;
	}

	/**
	 * Imposta place label.
	 *
	 * @param placeLabel nuovo place label
	 */
	public void setPlaceLabel(String placeLabel) {
		this.placeLabel = placeLabel;
	}

	/**
	 * Legge children.
	 *
	 * @return children
	 */
	public List<MenuItemDTO> getChildren() {
		return children;
	}

	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<MenuItemDTO> children) {
		this.children = children;
	}

}
