package it.lispa.bdl.client.vc.caricamentoimmagini;

import it.lispa.bdl.client.events.ChangeGridState;
import it.lispa.bdl.client.events.ChangeGridState.SaveHandler;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.CaricamentoImmaginiMsg;
import it.lispa.bdl.shared.services.CaricamentoImmaginiService;
import it.lispa.bdl.shared.services.CaricamentoImmaginiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
 * Class CaricamentoImmaginiController.
 */
public class CaricamentoImmaginiController extends Presenter<CaricamentoImmaginiController.iCaricamentoImmaginiView, CaricamentoImmaginiController.iCaricamentoImmaginiProxy> 
implements CaricamentoImmaginiHandler{
	
	CaricamentoImmaginiServiceAsync servizioFunzione = (CaricamentoImmaginiServiceAsync) GWT.create(CaricamentoImmaginiService.class);
	
	@Inject CaricamentoImmaginiMsg messages;

	/**
	 * Interface iCaricamentoImmaginiView.
	 */
	public interface iCaricamentoImmaginiView extends View, HasUiHandlers<CaricamentoImmaginiHandler> {
		
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
	 * Interface iCaricamentoImmaginiProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.CaricamentoImmagini)
	public interface iCaricamentoImmaginiProxy extends ProxyPlace<CaricamentoImmaginiController> {
	}

	private CaricamentoImmaginiFormController editorPresenter;
	@SuppressWarnings("unused")
	private VOggettoDTO gridItem;	
	
	/**
	 * Istanzia un nuovo caricamento immagini controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public CaricamentoImmaginiController(final EventBus eventBus,
			final iCaricamentoImmaginiView view, final iCaricamentoImmaginiProxy proxy, CaricamentoImmaginiFormController editorPresenter) {

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
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiHandler#onLAction()
	 */
	@Override
	public void onLAction() {
		VOggettoDTO gridItemLocal = getView().getGrid().getSelectionModel().getSelectedItem();
		editorPresenter.getView().setIsGenerazioneImmagini(Boolean.FALSE);
		editorPresenter.getView().getAction().setText(messages.btnItemAction());
		editorPresenter.getView().getStepLayout().setActiveWidget(editorPresenter.getView().getStepLayout().getWidget(0));
		editorPresenter.getView().refreshFields(gridItemLocal);

		editorPresenter.getView().setHeadingText(gridItemLocal.getO_DnTitolo());

		addToPopupSlot(editorPresenter);
		
//		new GXTAlertBox("Carica", editorPresenter.getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiHandler#onLAction()
	 */
	@Override
	public void onLActionDue() {
		
		VOggettoDTO gridItemLocal = getView().getGrid().getSelectionModel().getSelectedItem();
		
		servizioFunzione.hasNaturaMapOrAudio(gridItemLocal.getO_CdOggettoOriginale(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean hasNaturaMapOrAudio) {
				if(hasNaturaMapOrAudio) {
					editorPresenter.getView().setIsGenerazioneImmagini(Boolean.FALSE);
				} else {
					editorPresenter.getView().setIsGenerazioneImmagini(Boolean.TRUE);
				}
			}
			public void onFailure(Throwable caught) {
				editorPresenter.getView().setIsGenerazioneImmagini(Boolean.FALSE);
			}
		});
		
		editorPresenter.getView().getAction().setText(messages.btnItemAction());
		editorPresenter.getView().getStepLayout().setActiveWidget(editorPresenter.getView().getStepLayout().getWidget(0));
		editorPresenter.getView().refreshFields(gridItemLocal);

		editorPresenter.getView().setHeadingText(gridItemLocal.getO_DnTitolo());

		addToPopupSlot(editorPresenter);
		
//		new GXTAlertBox("Carica e genera", editorPresenter.getView().getIsGenerazioneImmagini().toString(), GXTAlertBox.DO_SHOW);
	}
}