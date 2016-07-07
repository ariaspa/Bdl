package it.lispa.bdl.client.vc.amministrazione;

import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * Class AmministrazioneController.
 */
public class AmministrazioneController extends Presenter<AmministrazioneController.iAmministrazioneView, AmministrazioneController.iAmministrazioneProxy> {

	/* Interfaccia condivisa tra la vista e il controller*/
	/**
	 * Interface iAmministrazioneView.
	 */
	public interface iAmministrazioneView extends View {
	}

	/**
	 * Interface iAmministrazioneProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.Amministrazione)
	public interface iAmministrazioneProxy extends ProxyPlace<AmministrazioneController> {

	}

	/**
	 * Istanzia un nuovo amministrazione controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 */
	@Inject
	public AmministrazioneController (final EventBus eventBus, final iAmministrazioneView view, final iAmministrazioneProxy proxy) {
		super(eventBus, view, proxy);
	}

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Amministrazione");
	}
}