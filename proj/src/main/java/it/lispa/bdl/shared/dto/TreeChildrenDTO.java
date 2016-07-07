package it.lispa.bdl.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;

/**
 * Class TreeChildrenDTO.
 */
public class TreeChildrenDTO implements Serializable,
		TreeStore.TreeNode<TreeChildrenDTO> {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** id. */
	private Integer id;
	
	/** name. */
	private String name;

	
	/**
	 * Istanzia un nuovo tree children dto.
	 */
	protected TreeChildrenDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo tree children dto.
	 *
	 * @param id  id
	 * @param name  name
	 */
	public TreeChildrenDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Legge name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Imposta name.
	 *
	 * @param name nuovo name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.sencha.gxt.data.shared.TreeStore.TreeNode#getData()
	 */
	@Override
	public TreeChildrenDTO getData() {
		return this;
	}

	/* (non-Javadoc)
	 * @see com.sencha.gxt.data.shared.TreeStore.TreeNode#getChildren()
	 */
	@Override
	public List<? extends TreeNode<TreeChildrenDTO>> getChildren() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name != null ? name : super.toString();
	}
	
	

}
