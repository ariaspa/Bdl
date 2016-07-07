package it.lispa.bdl.client.vc.monitoraggiocritici;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Class MonitoraggioCriticiFormView.
 */
public class MonitoraggioCriticiFormView extends
		GXTPopupViewWithUiHandlers<MonitoraggioCriticiFormHandler> implements
		MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, MonitoraggioCriticiFormView> {
	}

	VOggettoDTO item;

	@UiField InlineLabel istituto;
	@UiField InlineLabel progetto;
	@UiField InlineLabel collezione;
	@UiField InlineLabel titolo;
	@UiField InlineLabel titoloFe;
	@UiField InlineLabel titoloSup;
	@UiField InlineLabel immagini;
	@UiField InlineLabel tipologiaOggetto;
	@UiField InlineLabel statoFlag;
	@UiField InlineLabel notaCriticita;
	
	
	@UiField
	Window dialog;

	/**
	 * Istanzia un nuovo monitoraggio critici form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public MonitoraggioCriticiFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}

	/**
	 * On annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("annulla")
	public void onAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onAnnulla();
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView#refreshFields(it.lispa.bdl.shared.dto.VOggettoDTO)
	 */
	public void refreshFields(VOggettoDTO item) {
		this.item = item;
		this.istituto.setText(item.getI_DnNome());
		this.progetto.setText(item.getP_DnTitolo());
		this.collezione.setText(item.getC_DnTitolo());
		this.titolo.setText(item.getO_DnTitolo());
		this.titoloFe.setText(item.getO_DnTitoloFe());
		this.titoloSup.setText(item.getO_DnTitoloSup());
		this.immagini.setText(item.getO_NrImmaginiPreviste().toString());
		this.tipologiaOggetto.setText(item.getT_DnNome());
		this.statoFlag.setText(item.getO_FlIsCritico_Human());
		this.notaCriticita.setText(item.getO_DsNotaCritico());
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView#setHeadingText(java.lang.String)
	 */
	public void setHeadingText(String text) {
		dialog.setHeadingText(text);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView#getItem()
	 */
	@Override
	public VOggettoDTO getItem() {
		return item;
	}

}
