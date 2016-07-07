
package it.lispa.bdl.client.vc.home;

import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.messages.HomeMsg;
import it.lispa.bdl.shared.services.AuthService;
import it.lispa.bdl.shared.services.AuthServiceAsync;
import it.lispa.bdl.shared.services.ExternalService;
import it.lispa.bdl.shared.services.ExternalServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;

/**
 * Class HomeController.
 */
public class HomeController extends Presenter<HomeController.iHomeView, HomeController.iHomeProxy> implements HomeHandler{

	ExternalServiceAsync externalService = (ExternalServiceAsync) GWT.create(ExternalService.class);
	@SuppressWarnings("unused")
	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	@Inject HomeMsg messages;
	
	/**
	 * Interface iHomeView.
	 */
	public interface iHomeView extends View, HasUiHandlers<HomeHandler> {
		Component getPanel();		
	}

	/* Proxy di navigazione */
	/**
	 * Interface iHomeProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.Home)
	public interface iHomeProxy extends ProxyPlace<HomeController> {
	}
	
	final PlaceManager placeManager;
	final iHomeView iView;

	@Inject
	private HomeRegistrazioneFormController editorPresenter;
	
	/**
	 * Istanzia un nuovo home controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param placeManager  place manager
	 * @param editorPresenter  editor presenter
	 */
	@Inject
	public HomeController (final EventBus eventBus, final iHomeView view, final iHomeProxy proxy,
			final PlaceManager placeManager, HomeRegistrazioneFormController editorPresenter) {
		super(eventBus, view, proxy);
		this.iView = view;

		getView().setUiHandlers(this);
		this.placeManager = placeManager;
		this.editorPresenter = editorPresenter;
	}

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Home");

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
		/*
		 * MOCK
		PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.Dashboard).build();
		placeManager.revealPlace(request);
		 */
		externalService.isUserOnline(new AsyncCallback<String>() {
			@Override public void onFailure(Throwable caught) {
				// do nothing
			}
			@Override public void onSuccess(String result) {
				if(result.equals(BdlSharedConstants.USER_VALIDATED)){
					//PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.Dashboard).build();
					//placeManager.revealPlace(request);
					externalService.getIdpcUrlLogin(new AsyncCallback<String>() {
						@Override public void onFailure(Throwable caught) {
							// do nothing
						}
						@Override public void onSuccess(String newURL) {
							Window.Location.replace(newURL);
						}
					});
				}
			}
		});
	}
	@Override
	protected void onReveal() {
		super.onReveal();
		GWT.log("onReveal di Home");

		externalService.isUserOnline(new AsyncCallback<String>() {
			@Override public void onFailure(Throwable caught) {
				// do nothing
			}
			@Override public void onSuccess(String result) {
				if(result.equals(BdlSharedConstants.USER_REGISTERED)){
					externalService.getUserEmail(new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							AlertMessageBox box = new AlertMessageBox(messages.utenteNonValidatoTitle(), messages.utenteNonValidatoMessage(messages.utenteNonValidatoEmail()));
							box.setPredefinedButtons(PredefinedButton.OK);
							box.setIcon(MessageBox.ICONS.error());
							box.show();
						}
						@Override
						public void onSuccess(String result) {
							AlertMessageBox box = new AlertMessageBox(messages.utenteNonValidatoTitle(), messages.utenteNonValidatoMessage(result));
							box.setPredefinedButtons(PredefinedButton.OK);
							box.setIcon(MessageBox.ICONS.error());
							box.show();
						}
					});
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeHandler#goIdpc()
	 */
	@Override
	public void goIdpc() {
		GWT.log("Click su IDPC");
		externalService.getIdpcUrlLogin(new AsyncCallback<String>() {
			@Override public void onFailure(Throwable caught) {
				// do nothing
			}
			@Override public void onSuccess(String newURL) {
				Window.Location.replace(newURL);
			}
		});
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.home.HomeHandler#goRegistrazione()
	 */
	@Override
	public void goRegistrazione() {
		GWT.log("Click su Registrazione");		
		editorPresenter.getView().refreshFields();
		editorPresenter.getView().setHeadingText(messages.infoTitle());
		addToPopupSlot(editorPresenter);
	}
}