package it.lispa.bdl.client.vc.schedulatorejobs;

import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.messages.SchedulatoreJobsMsg;
import it.lispa.bdl.shared.services.SchedulatoreJobsService;
import it.lispa.bdl.shared.services.SchedulatoreJobsServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Window;

/**
 * Class SchedulatoreJobsFormController.
 */
public class SchedulatoreJobsFormController extends PresenterWidget<SchedulatoreJobsFormController.iSchedulatoreJobsFormView>
implements SchedulatoreJobsFormHandler{

	@Inject SchedulatoreJobsMsg messages;

	SchedulatoreJobsServiceAsync servizio = (SchedulatoreJobsServiceAsync) GWT.create(SchedulatoreJobsService.class);

	/**
	 * Interface iSchedulatoreJobsFormView.
	 */
	public interface iSchedulatoreJobsFormView extends PopupView, HasUiHandlers<SchedulatoreJobsFormHandler> {
		
		/**
		 * Legge dialog.
		 *
		 * @return dialog
		 */
		public Window getDialog();
		
		/**
		 * Legge item.
		 *
		 * @return item
		 */
		public SchedulatoreJobsDTO getItem();
		
		/**
		 * Activate view.
		 *
		 * @param item  item
		 */
		public void activateView(SchedulatoreJobsDTO item);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo amm gest index form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public SchedulatoreJobsFormController(final EventBus eventBus, final iSchedulatoreJobsFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	OggettoDTO item;
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 * On annulla.
	 */
	public void onAnnulla() {
	}

	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(SchedulatoreJobsDTO item){
		getView().activateView(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsFormHandler#onVBtnAnnulla()
	 */
	@Override
	public void onVBtnAnnulla() {
		getView().hide();		
	}



}
