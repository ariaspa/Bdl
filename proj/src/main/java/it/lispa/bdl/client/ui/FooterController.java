package it.lispa.bdl.client.ui;


import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;


/**
 * Class FooterController.
 */
public class FooterController extends PresenterWidget<FooterController.iFooterView>{

	/**
	 * Interface iFooterView.
	 */
	public interface iFooterView extends View {

	}


	/**
	 * Istanzia un nuovo footer controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public FooterController(EventBus eventBus, iFooterView view) {
		super(eventBus, view);	
	}


	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}




}