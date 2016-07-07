package it.lispa.bdl.client.vc.monitoraggiocritici;

import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.messages.MonitoraggioCriticiMsg;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Window;

/**
 * Class MonitoraggioCriticiFormController.
 */
public class MonitoraggioCriticiFormController extends PresenterWidget<MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView>
implements MonitoraggioCriticiFormHandler{

	@Inject MonitoraggioCriticiMsg messages;

	/**
	 * Interface iMonitoraggioCriticiFormView.
	 */
	public interface iMonitoraggioCriticiFormView extends PopupView, HasUiHandlers<MonitoraggioCriticiFormHandler> {
		
		/**
		 * Imposta heading text.
		 *
		 * @param text nuovo heading text
		 */
		public void setHeadingText(String text);
		
		/**
		 * Refresh fields.
		 *
		 * @param item  item
		 */
		public void refreshFields(VOggettoDTO item);
		
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
		public VOggettoDTO getItem();
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;

	/**
	 * Istanzia un nuovo monitoraggio critici form controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public MonitoraggioCriticiFormController(final EventBus eventBus, final iMonitoraggioCriticiFormView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormHandler#onAnnulla()
	 */
	@Override
	public void onAnnulla() {
		getView().hide();		
	}

}
