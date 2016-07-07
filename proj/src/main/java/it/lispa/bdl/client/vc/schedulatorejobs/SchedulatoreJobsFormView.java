package it.lispa.bdl.client.vc.schedulatorejobs;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.messages.SchedulatoreJobsMsg;
import it.lispa.bdl.shared.services.SchedulatoreJobsService;
import it.lispa.bdl.shared.services.SchedulatoreJobsServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * Class SchedulatoreJobsFormView.
 */
public class SchedulatoreJobsFormView extends GXTPopupViewWithUiHandlers<SchedulatoreJobsFormHandler> implements SchedulatoreJobsFormController.iSchedulatoreJobsFormView {

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, SchedulatoreJobsFormView> {
	}

	private final Widget widget;

	SchedulatoreJobsServiceAsync servizio = (SchedulatoreJobsServiceAsync) GWT.create(SchedulatoreJobsService.class);

	SchedulatoreJobsDTO item;

	@UiField
	Window dialog;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
	ComboStringDTO.ComboStringDTOProperties cmbStringProps = GWT.create(ComboStringDTO.ComboStringDTOProperties.class);

	@UiField
	CardLayoutContainer stepLayout;

	@UiField
	VerticalLayoutContainer vFieldsetVlc;
	@UiField
	HBoxLayoutContainer vButtons;

	
	@UiField
	InlineLabel vNomeUtente;
	
	@UiField (provided = true)
	DateLabel vDtStart;
	@UiField (provided = true)
	DateLabel vDtEnd;
	
	@UiField
	InlineLabel vDsTipo;
	@UiField
	InlineLabel vDsEsito;
	@UiField
	InlineLabel vDsInput;
	@UiField TextArea vDsOutput;

	@UiField
	TextButton vBtnAnnulla;

	@Inject
	SchedulatoreJobsMsg messages;

	/**
	 * Istanzia un nuovo amm gest index form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public SchedulatoreJobsFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		
		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		vDtStart = new DateLabel(fmt);
		vDtEnd = new DateLabel(fmt);

		widget = binder.createAndBindUi(this);

		vFieldsetVlc.setScrollMode(ScrollMode.AUTOY);

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/**
	 * On v btn annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("vBtnAnnulla")
	public void onVBtnAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onVBtnAnnulla();
		}
	}

	private void refreshItem(SchedulatoreJobsDTO item) {
		this.item = item;


		if (item == null) {

			vBtnAnnulla.setEnabled(true);
	    	
			vNomeUtente.setText(null);
			vDtStart.setValue(null);
			vDtEnd.setValue(null);
			vDsTipo.setText(null);
			vDsEsito.setText(null);
			vDsInput.setText(null);
			vDsOutput.setValue(null);

		} else {

			vBtnAnnulla.setEnabled(true);

			vNomeUtente.setText(item.getNomeUtente());
			vDtStart.setValue(item.getDtStart());
			vDtEnd.setValue(item.getDtEnd());
			vDsTipo.setText(item.getDsTipo());
			vDsEsito.setText(item.getDsEsito());
			vDsInput.setText(item.getDsInput());
			vDsOutput.setValue(item.getDsOutput());
			
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsFormController.iSchedulatoreJobsFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.SchedulatoreJobs.SchedulatoreJobsFormController.iSchedulatoreJobsFormView#getItem()
	 */
	@Override
	public SchedulatoreJobsDTO getItem() {
		return item;
	}
	
	@Override
	public void activateView(SchedulatoreJobsDTO item) {
		refreshItem(item);

		dialog.setHeadingText(messages.infoTitleView());

		stepLayout.setActiveWidget(stepLayout.getWidget(0));
		vButtons.forceLayout();
		vFieldsetVlc.forceLayout();
	}
}
