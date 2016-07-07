package it.lispa.bdl.client.vc.home;


import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Class HomeView.
 */
public class HomeView extends ViewWithUiHandlers<HomeHandler> implements HomeController.iHomeView{

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, HomeView> {

	}

	@UiField
	ContentPanel panel;
	
	/**
	 * Istanzia un nuovo home view.
	 *
	 * @param binder  binder
	 */
	@Inject
	public HomeView(final Binder binder) {

		widget = binder.createAndBindUi(this);

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}


	@UiHandler("btnIdpc")
	void onIdpc(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().goIdpc();
		}

	}
	@UiHandler("btnRegistrazione")
	void onRegistrazione(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().goRegistrazione();
		}

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#addToSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void addToSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#removeFromSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void removeFromSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.IsWidget)
	 */
	@Override
	public void setInSlot(Object slot, IsWidget content) {
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeController.iHomeView#getPanel()
	 */
	public ContentPanel getPanel() {
		return panel;
	}

}
