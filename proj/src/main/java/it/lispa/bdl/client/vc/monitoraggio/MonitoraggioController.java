package it.lispa.bdl.client.vc.monitoraggio;

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
 * Class MonitoraggioController.
 */
public class MonitoraggioController extends Presenter<MonitoraggioController.iMonitoraggioView, MonitoraggioController.iMonitoraggioProxy> {

	/* Interfaccia condivisa tra la vista e il controller*/
	/**
	 * Interface iMonitoraggioView.
	 */
	public interface iMonitoraggioView extends View {
	}

	/**
	 * Interface iMonitoraggioProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.Monitoraggio)
	public interface iMonitoraggioProxy extends ProxyPlace<MonitoraggioController> {

	}

	/**
	 * Istanzia un nuovo monitoraggio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 */
	@Inject
	public MonitoraggioController (final EventBus eventBus, final iMonitoraggioView view, final iMonitoraggioProxy proxy) {
		super(eventBus, view, proxy);
	}

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Monitoraggio");
	}
}