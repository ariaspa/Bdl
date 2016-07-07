package it.lispa.bdl.client.vc.schedulatore;

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
 * Class SchedulatoreController.
 */
public class SchedulatoreController extends Presenter<SchedulatoreController.iSchedulatoreView, SchedulatoreController.iSchedulatoreProxy> {

	/* Interfaccia condivisa tra la vista e il controller*/
	/**
	 * Interface iSchedulatoreView.
	 */
	public interface iSchedulatoreView extends View {
	}

	/**
	 * Interface iSchedulatoreProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.Schedulatore)
	public interface iSchedulatoreProxy extends ProxyPlace<SchedulatoreController> {

	}

	/**
	 * Istanzia un nuovo Schedulatore controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 */
	@Inject
	public SchedulatoreController (final EventBus eventBus, final iSchedulatoreView view, final iSchedulatoreProxy proxy) {
		super(eventBus, view, proxy);
	}

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Schedulatore");
	}
}