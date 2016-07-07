package it.lispa.bdl.client.ui;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Class HeaderView.
 */
public class HeaderView extends ViewImpl implements HeaderController.iHeaderView{

	ExternalServiceAsync externalService = (ExternalServiceAsync) GWT.create(ExternalService.class);
	
	@UiField HTMLPanel panelDatiUtente;
	@UiField SpanElement spanDatiUtente;
	@UiField HTMLPanel panelBottoneLogout;
	@UiField AnchorElement logout;

	private boolean userIsLoggedIn;
	private String datiUtente;
	
	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, HeaderView> {
		// do nothing
	}

	/**
	 * Istanzia un nuovo header view.
	 *
	 * @param binder  binder
	 * @param img  img
	 */
	@Inject
	public HeaderView(final Binder binder, Images img) {
		widget = binder.createAndBindUi(this);
		panelDatiUtente.setVisible(false);
		panelBottoneLogout.setVisible(false);

		externalService.getIdpcUrlLogout(new AsyncCallback<String>() {
			@Override public void onFailure(Throwable caught) {
				// do nothing
			}
			@Override public void onSuccess(String newURL) {
				logout.setHref(newURL);
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.HeaderController.iHeaderView#isUserIsLoggedIn()
	 */
	public boolean isUserIsLoggedIn() {
		return userIsLoggedIn;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.HeaderController.iHeaderView#setUserIsLoggedIn(boolean)
	 */
	public void setUserIsLoggedIn(boolean userIsLoggedIn) {
		this.userIsLoggedIn = userIsLoggedIn;
		panelDatiUtente.setVisible(userIsLoggedIn);
		panelBottoneLogout.setVisible(userIsLoggedIn);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.HeaderController.iHeaderView#getDatiUtente()
	 */
	public String getDatiUtente() {
		return datiUtente;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.ui.HeaderController.iHeaderView#setDatiUtente(java.lang.String)
	 */
	public void setDatiUtente(String datiUtente) {
		this.datiUtente = datiUtente;
		spanDatiUtente.setInnerText(datiUtente);
	}
}
