package it.lispa.bdl.client.ui;


import it.lispa.bdl.shared.services.AuthService;
import it.lispa.bdl.shared.services.AuthServiceAsync;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;


/**
 * Class HeaderController.
 */
public class HeaderController extends PresenterWidget<HeaderController.iHeaderView>{

	final PlaceManager placeManager;

	private final ExternalServiceAsync externalService = GWT.create(ExternalService.class);
	private final AuthServiceAsync authService = GWT.create(AuthService.class);


	/**
	 * Interface iHeaderView.
	 */
	public interface iHeaderView extends View {
		boolean isUserIsLoggedIn();
		void setUserIsLoggedIn(boolean userIsLoggedIn);
		String getDatiUtente();
		void setDatiUtente(String datiUtente);
	}

	/**
	 * Istanzia un nuovo header controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param mng  mng
	 */
	@Inject
	public HeaderController(EventBus eventBus, iHeaderView view, final PlaceManager mng) {
		super(eventBus, view);	
		this.placeManager = mng;
	}


	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Header");
		/*
		 * MOCK
		 * 
		getView().setUserIsLoggedIn(true);
		getView().setDatiUtente("Area di mockup!");
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
					authService.getHeaderString(new AsyncCallback<String>() {
						@Override public void onFailure(Throwable caught) {
							// do nothing
						}
						@Override public void onSuccess(String result) {
							getView().setDatiUtente(result);
						}
					});

				}
			}
		});
	}

	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di Header");

	}

}