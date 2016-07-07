package it.lispa.bdl.client.vc.validazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.messages.ValidazioneOggettiMsg;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.VOggettiServiceAsync;
import it.lispa.bdl.shared.services.ValidazioneOggettiService;
import it.lispa.bdl.shared.services.ValidazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class ValidazioneOggettiView.
 */
public class ValidazioneOggettiView extends ViewWithUiHandlers<ValidazioneOggettiHandler> implements
		ValidazioneOggettiController.iValidazioneOggettiView {

	ValidazioneOggettiServiceAsync servizioCatalogazione = (ValidazioneOggettiServiceAsync) GWT.create(ValidazioneOggettiService.class);

	VOggettiServiceAsync servizioTreeGrid = (VOggettiServiceAsync) GWT.create(VOggettiService.class);
	
	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, ValidazioneOggettiView> {

	}

	private SubMenuItemDTO treeItem;	
	
	private final Widget widget;

	@Inject ValidazioneOggettiMsg messages;
	
	@UiField(provided = true)
	Tree<SubMenuItemDTO, String> tree;
	
	private TreeStore<SubMenuItemDTO> treeStore;

	@UiField SimpleContainer panelContent;
	
	/**
	 * Istanzia un nuovo validazione oggetti view.
	 *
	 * @param binder  binder
	 * @param img  img
	 */
	@Inject
	public ValidazioneOggettiView(final Binder binder, Images img) {
		
		
		SubMenuItemDTO.SubMenuItemDTOProperties treeProps = GWT.create(SubMenuItemDTO.SubMenuItemDTOProperties.class);
		treeStore = new TreeStore<SubMenuItemDTO>(treeProps.chiave());

		tree = new Tree<SubMenuItemDTO, String>(treeStore, treeProps.label());


		tree.getStyle().setLeafIcon(img.getFrecciaOff());
		
		tree.getStyle().setJointOpenIcon(img.getClear());
		tree.getStyle().setJointCloseIcon(img.getClear());
		
		tree.getStyle().setNodeOpenIcon(img.getFrecciaOn());
		tree.getStyle().setNodeCloseIcon(img.getFrecciaOff());
		
		tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		tree.setAutoExpand(true);
		
		loadTreeData();
		
		widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#loadTreeData()
	 */
	public void loadTreeData(){

		
		/*
		 * MOCK
		List<SubMenuItemDTO> items = it.lispa.bdl.client.mockservice.Mock.ValidazioneOggettiView_loadTreeData();
		treeStore.clear();
		Iterator<SubMenuItemDTO> mnuItr = items.iterator();
		while(mnuItr.hasNext()){
			SubMenuItemDTO item = (SubMenuItemDTO) mnuItr.next();
			treeStore.add(item);
			if(item.hasChildren()){
				processTreeChildren(item);
			}
		}
		tree.expandAll();
		*/

		List<String> statiOggetto = new ArrayList<String>();
		statiOggetto.add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE);
		Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
		servizioTreeGrid.getTreeItemsForSupervisore(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggetto,filtriFlag, new AsyncCallback<List<SubMenuItemDTO>>() {
			public void onFailure(Throwable caught) {
				// Non fare nulla: l'errore generico viene gestito a monte...
				// e il metodo non tira un AsyncServiceException
			}			
			@Override
			public void onSuccess(List<SubMenuItemDTO> items) {
				treeStore.clear();
				Iterator<SubMenuItemDTO> mnuItr = items.iterator();
				while(mnuItr.hasNext()){
					SubMenuItemDTO item = (SubMenuItemDTO) mnuItr.next();
					treeStore.add(item);
					if(item.hasChildren()){
						processTreeChildren(item);
					}
				}
				tree.expandAll();
			}
		});
	}	
	
	private void processTreeChildren(SubMenuItemDTO rootNode) {
		for (SubMenuItemDTO child : rootNode.getChildren()) {
			treeStore.add(rootNode, child);
			if (child.hasChildren()) {
				processTreeChildren((SubMenuItemDTO) child);
			}
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#resetContentSlot()
	 */
	public void resetContentSlot(){
    	panelContent.clear();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#setContentSlot(com.gwtplatform.mvp.client.PresenterWidget)
	 */
	public void setContentSlot(PresenterWidget<?> content){
    	panelContent.clear();
    	panelContent.add(content);
    	panelContent.forceLayout();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
	    if (slot == ValidazioneOggettiController.IDOGG_CONTENT) {
	    	panelContent.clear();
	    	panelContent.add(content);
	    } else {
	        super.setInSlot(slot, content);
	    }
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#getTreeItem()
	 */
	public SubMenuItemDTO getTreeItem() {
		return treeItem;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#setTreeItem(it.lispa.bdl.shared.dto.SubMenuItemDTO)
	 */
	public void setTreeItem(SubMenuItemDTO itm) {
		treeItem = itm;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#getTree()
	 */
	public Tree<SubMenuItemDTO, String> getTree() {
		return tree;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController.iValidazioneOggettiView#resetTreeSelection()
	 */
	public void resetTreeSelection(){
		tree.getSelectionModel().deselectAll();
	}
}


