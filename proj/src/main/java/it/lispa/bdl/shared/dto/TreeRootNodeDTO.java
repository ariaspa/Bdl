package it.lispa.bdl.shared.dto;

import java.util.List;

/**
 * Class TreeRootNodeDTO.
 */
public class TreeRootNodeDTO extends TreeChildrenDTO {
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** children. */
	private List<TreeChildrenDTO> children;

	/**
	 * Istanzia un nuovo tree root node dto.
	 */
	protected TreeRootNodeDTO() {
		// do nothing...
	}

	/**
	 * Istanzia un nuovo tree root node dto.
	 *
	 * @param id  id
	 * @param name  name
	 */
	public TreeRootNodeDTO(Integer id, String name) {
		super(id, name);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.dto.TreeChildrenDTO#getChildren()
	 */
	public List<TreeChildrenDTO> getChildren() {
		return children;
	}

	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<TreeChildrenDTO> children) {
		this.children = children;
	}

	/**
	 * Aggiunge child.
	 *
	 * @param child  child
	 */
	public void addChild(TreeChildrenDTO child) {
		getChildren().add(child);
	}
	
	/**
	 * Have children.
	 *
	 * @return true, se vero
	 */
	public boolean haveChildren(){
		return !getChildren().isEmpty();
	}
}
