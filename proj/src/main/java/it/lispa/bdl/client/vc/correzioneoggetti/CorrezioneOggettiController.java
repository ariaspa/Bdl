package it.lispa.bdl.client.vc.correzioneoggetti;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CorrezioneOggettiMsg;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.CaricamentoImmaginiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class CorrezioneOggettiController.
 */
public class CorrezioneOggettiController extends Presenter<CorrezioneOggettiController.iCorrezioneOggettiView, CorrezioneOggettiController.iCorrezioneOggettiProxy> implements CorrezioneOggettiHandler{
	
	CaricamentoImmaginiServiceAsync servizioFunzione = (CaricamentoImmaginiServiceAsync) GWT.create(CaricamentoImmaginiService.class);
	
	@Inject CorrezioneOggettiMsg messages;

	/**
	 * Interface iCorrezioneOggettiView.
	 */
	public interface iCorrezioneOggettiView extends View, HasUiHandlers<CorrezioneOggettiHandler> {
		
		/**
		 * Load grid data.
		 */
		public void loadGridData();
		
		/**
		 * Load tree data.
		 */
		public void loadTreeData();
		Tree<SubMenuItemDTO, String> getTree();
		TreeStore<SubMenuItemDTO> getTreeStore();
		
		/**
		 * Legge grid.
		 *
		 * @return grid
		 */
		public Grid<VOggettoDTO> getGrid();		
		
		/**
		 * Legge panel.
		 *
		 * @return panel
		 */
		public ContentPanel getPanel();
		
		/**
		 * Imposta tree item.
		 *
		 * @param treeItem nuovo tree item
		 */
		public void setTreeItem(SubMenuItemDTO treeItem);
	}

	/**
	 * Interface iCorrezioneOggettiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.CorrezioneOggetti)
	public interface iCorrezioneOggettiProxy extends ProxyPlace<CorrezioneOggettiController> {
	}

	private CorrezioneOggettiFormController editorPresenter;
	@SuppressWarnings("unused")
	private VOggettoDTO gridItem;	
	
	/**
	 * Istanzia un nuovo correzione oggetti controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public CorrezioneOggettiController(final EventBus eventBus,
			final iCorrezioneOggettiView view, final iCorrezioneOggettiProxy proxy, CorrezioneOggettiFormController editorPresenter) {

		super(eventBus, view, proxy);
		this.editorPresenter = editorPresenter;
		getView().setUiHandlers(this);
	}

	private final SaveHandler saveHandler = new SaveHandler() {
		
		public void onSave(ChangeGridState event) {
			getView().loadGridData();
			getView().loadTreeData();
		}
	};
	
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();

		registerHandler(getEventBus().addHandler(ChangeGridState.getType(), saveHandler));

		editorPresenter.getView().getDialog().addHideHandler(new HideHandler() {

			public void onHide(HideEvent event) {
				getView().getPanel().unmask();
			}
		});
		editorPresenter.getView().getDialog().addShowHandler(new ShowHandler() {

			public void onShow(ShowEvent event) {
				getView().getPanel().mask();
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		getView().setTreeItem(null);
		getView().loadGridData();
		getView().loadTreeData();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiHandler#onLAction()
	 */
	@Override
	public void onLAction() {

		VOggettoDTO gridItemLocal = getView().getGrid().getSelectionModel().getSelectedItem();

		editorPresenter.getView().getAction().setText(messages.btnItemAction());
		editorPresenter.getView().getStepLayout().setActiveWidget(editorPresenter.getView().getStepLayout().getWidget(0));
		editorPresenter.getView().refreshFields(gridItemLocal);

		editorPresenter.getView().setHeadingText(gridItemLocal.getO_DnTitolo());

		addToPopupSlot(editorPresenter);
	}
	
}