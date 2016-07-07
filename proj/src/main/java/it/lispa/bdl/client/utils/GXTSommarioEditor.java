package it.lispa.bdl.client.utils;

import it.lispa.bdl.shared.messages.GXTSommarioEditorMsg;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;

/**
 * Class GXTSommarioEditor.
 *
 * @param <M> the generic type
 */
public class GXTSommarioEditor<M> extends GridRowEditing<M> {

	GXTSommarioEditorMsg myMessages = (GXTSommarioEditorMsg) GWT.create(GXTSommarioEditorMsg.class);
	
	/**
	 * Class ItRowEditorMessages.
	 */
	public class ItRowEditorMessages implements RowEditorMessages {

		/* (non-Javadoc)
		 * @see com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing.RowEditorMessages#cancelText()
		 */
		@Override
		public String cancelText() {
			return myMessages.cancelText();
		}

		/* (non-Javadoc)
		 * @see com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing.RowEditorMessages#dirtyText()
		 */
		@Override
		public String dirtyText() {
			return myMessages.dirtyText();
		}

		/* (non-Javadoc)
		 * @see com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing.RowEditorMessages#errorTipTitleText()
		 */
		@Override
		public String errorTipTitleText() {
			return myMessages.errorTipTitleText();
		}

		/* (non-Javadoc)
		 * @see com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing.RowEditorMessages#saveText()
		 */
		@Override
		public String saveText() {
			return myMessages.saveText();
		}

	}
	
	/**
	 * Istanzia un nuovo GXT sommario editor.
	 *
	 * @param editableGrid  editable grid
	 */
	public GXTSommarioEditor(Grid<M> editableGrid) {
		super(editableGrid);
		
		setMessages(new ItRowEditorMessages());
		
		this.setClicksToEdit(null);
	}



}
