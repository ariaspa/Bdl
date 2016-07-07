package it.lispa.bdl.client.ui;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.MenuItemDTO;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Class MenuView.
 */
public class MenuView extends ViewImpl implements MenuController.iMenuView{
	class KeyProvider implements ModelKeyProvider<MenuItemDTO> {
		@Override
		public String getKey(MenuItemDTO item) {
			return item.getPlaceToken();
		}
	}

	@UiField HTMLPanel panelMenu;
	@UiField HTMLPanel panelImage;

	@UiField(provided = true)
	TreeStore<MenuItemDTO> treeStore = new TreeStore<MenuItemDTO>(new KeyProvider());

	@UiField
	Tree<MenuItemDTO, String> tree;

	@Inject
	PlaceManager placeManager;

	private boolean userIsLoggedIn;

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, MenuView> {
	}

	/**
	 * Istanzia un nuovo menu view.
	 *
	 * @param binder  binder
	 * @param img  img
	 */
	@Inject
	public MenuView(final Binder binder, Images img) {

		widget = binder.createAndBindUi(this);


		tree.getStyle().setLeafIcon(img.getFrecciaOff());
		
		tree.getStyle().setJointOpenIcon(img.getClear());
		tree.getStyle().setJointCloseIcon(img.getClear());
		
		tree.getStyle().setNodeOpenIcon(img.getFrecciaOn());
		tree.getStyle().setNodeCloseIcon(img.getFrecciaOff());
		
		tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.MenuController.iMenuView#getTree()
	 */
	public Tree<MenuItemDTO, String> getTree() {
		return tree;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.MenuController.iMenuView#isUserIsLoggedIn()
	 */
	public boolean isUserIsLoggedIn() {
		return userIsLoggedIn;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.MenuController.iMenuView#setUserIsLoggedIn(boolean)
	 */
	public void setUserIsLoggedIn(boolean userIsLoggedIn) {
		this.userIsLoggedIn = userIsLoggedIn;
		panelMenu.setVisible(userIsLoggedIn);
		panelImage.setVisible(!userIsLoggedIn);
	}
	
	/**
	 * Crea value provider.
	 *
	 * @return value provider
	 */
	@UiFactory
	public ValueProvider<MenuItemDTO, String> createValueProvider() {
		return new ValueProvider<MenuItemDTO, String>() {

			@Override
			public String getValue(MenuItemDTO object) {
				return object.getPlaceLabel();
			}

			@Override
			public void setValue(MenuItemDTO object, String value) {
			}

			@Override
			public String getPath() {
				return "placeToken";
			}
		};
	}
}
