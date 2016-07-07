package it.lispa.bdl.client.vc.ammvalidutenti;

import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Class AmmValidUtentiFormView.
 */
public class AmmValidUtentiFormView extends GXTPopupViewWithUiHandlers<AmmValidUtentiFormHandler> implements
AmmValidUtentiFormController.iAmmValidUtentiFormView{

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, AmmValidUtentiFormView> {
	}

	UtenteDTO item;
	@UiField InlineLabel nome;
	@UiField InlineLabel cognome;
	@UiField InlineLabel cf;
	@UiField InlineLabel email;
	@UiField InlineLabel telefono;
	@UiField InlineLabel ruolo;
	@UiField InlineLabel ente;
	@UiField InlineLabel stato;
	
	@UiField (provided = true) DateLabel dataRegistrazione;
	@UiField Window dialog;


	/**
	 * Istanzia un nuovo amm valid utenti form view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 */
	@Inject
	public AmmValidUtentiFormView(final EventBus eventBus, final Binder binder) {
		super(eventBus);

		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		dataRegistrazione = new DateLabel(fmt);
		
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
	public void onAnnulla(SelectEvent event){
		if (getUiHandlers() != null) {
			getUiHandlers().onAnnulla();
		}
	}

	/**
	 * On validazione.
	 *
	 * @param event  event
	 */
	@UiHandler("validazione")
	public void onValidazione(SelectEvent event){
		if (getUiHandlers() != null) {
			getUiHandlers().onValidazione();
		}
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormController.iAmmValidUtentiFormView#refreshFields(it.lispa.bdl.shared.dto.UtenteDTO)
	 */
	public void refreshFields(UtenteDTO item){
		this.item = item;
		this.nome.setText(item.getNome());
		this.cognome.setText(item.getCognome());
		this.cf.setText(item.getCf());
		this.email.setText(item.getEmail());
		this.telefono.setText(item.getTelefono());
		this.ruolo.setText(item.getRuolo());
		this.ente.setText(item.getEntiLabel());
		this.stato.setText(item.getStatoHuman());
		this.dataRegistrazione.setValue(item.getDataRegistrazione());
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormController.iAmmValidUtentiFormView#setHeadingText(java.lang.String)
	 */
	public void setHeadingText(String text){
		dialog.setHeadingText(text);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormController.iAmmValidUtentiFormView#getDialog()
	 */
	public Window getDialog() {
		return dialog;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormController.iAmmValidUtentiFormView#getItem()
	 */
	@Override
	public UtenteDTO getItem() {
		return item;
	}

}
