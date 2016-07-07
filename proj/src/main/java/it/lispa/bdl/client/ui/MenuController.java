package it.lispa.bdl.client.ui;


import it.lispa.bdl.shared.dto.MenuItemDTO;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;
import it.lispa.bdl.shared.services.MenuService;
import it.lispa.bdl.shared.services.MenuServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.tree.Tree;


/**
 * Class MenuController.
 */
public class MenuController extends PresenterWidget<MenuController.iMenuView>{

	private final ExternalServiceAsync externalService = GWT.create(ExternalService.class);
	private final MenuServiceAsync menuService = GWT.create(MenuService.class);

	/**
	 * Interface iMenuView.
	 */
	public interface iMenuView extends View, IsWidget{
		Tree<MenuItemDTO, String> getTree();
		boolean isUserIsLoggedIn();
		void setUserIsLoggedIn(boolean userIsLoggedIn);

		/**
		 * Interface Presenter.
		 */
		public interface Presenter {
			void selectMenutem(MenuItemDTO item);
		}

	}

	@Inject
	PlaceManager placeManager;

	/**
	 * Istanzia un nuovo menu controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public MenuController(EventBus eventBus, iMenuView view) {
		super(eventBus, view);
	}


	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Menu");

		SelectionChangedHandler<MenuItemDTO> selectHandler = new SelectionChangedHandler<MenuItemDTO>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<MenuItemDTO> event) {
				List<MenuItemDTO> sels = event.getSelection();
				if (!sels.isEmpty()) {
					MenuItemDTO mnu = sels.get(0);
					GWT.log("Voce del menu selezionata: " + mnu.getPlaceLabel());
					selectMenutem(mnu);
				}
			}
		};

		getView().getTree().getSelectionModel().addSelectionChangedHandler(selectHandler);

		/*
		 * MOCK
		getView().setUserIsLoggedIn(true);
		List<MenuItemDTO> result = it.lispa.bdl.client.mockservice.Mock.MenuController_onBind();
		TreeStore<MenuItemDTO> store = getView().getTree().getStore();
		store.clear();
		Iterator<MenuItemDTO> mnuItr = result.iterator();
		while(mnuItr.hasNext()){
			MenuItemDTO item = (MenuItemDTO) mnuItr.next();
			store.add(item);
			if(item.hasChildren()){
				List<MenuItemDTO> children = item.getChildren();
				store.add(item, children);
			}
		}
		 */

		externalService.isUserOnline(new AsyncCallback<String>() {
			@Override public void onFailure(Throwable caught) {
				// do nothing
			}
			@Override public void onSuccess(String result) {
				boolean loggedUser = false;
				if(result.equals(BdlSharedConstants.USER_VALIDATED)){
					loggedUser = true;
				}
				getView().setUserIsLoggedIn(loggedUser);
				if(loggedUser){
					menuService.findFunzioniAccessibili(new AsyncCallback<List<MenuItemDTO>>() {
						@Override public void onFailure(Throwable caught) {
							// do nothing
						}
						@Override public void onSuccess(List<MenuItemDTO> result) {
							TreeStore<MenuItemDTO> store = getView().getTree().getStore();
							store.clear();
							Iterator<MenuItemDTO> mnuItr = result.iterator();
							while(mnuItr.hasNext()){
								MenuItemDTO item = (MenuItemDTO) mnuItr.next();
								store.add(item);
								if(item.hasChildren()){
									List<MenuItemDTO> children = item.getChildren();
									store.add(item, children);
								}
							}
						}
					});
				}
			}
		});
	}

	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di Menu");
		String token = placeManager.getCurrentPlaceRequest().getNameToken();
		List<MenuItemDTO> mnus = getView().getTree().getStore().getAll();
		for(MenuItemDTO mnu: mnus){
			if(mnu.getPlaceToken().equals(token)){
				getView().getTree().setExpanded(mnu, true, false);
				getView().getTree().scrollIntoView(mnu);
				getView().getTree().getSelectionModel().select(mnu, true);
				break;
			}			
		}
	}


	/**
	 * Select menutem.
	 *
	 * @param mnu  mnu
	 */
	public void selectMenutem(MenuItemDTO mnu) {
		GWT.log("Vado su "+mnu.getPlaceToken());

		PlaceRequest request = new PlaceRequest.Builder().nameToken(mnu.getPlaceToken()).build();
		this.placeManager.revealPlace(request);		

	}

}
