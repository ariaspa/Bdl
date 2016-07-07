package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldSet;

/**
 * Class IOOggettoImportaCatalogoDettaglioView.
 */
public class IOOggettoImportaCatalogoDettaglioView extends
GXTPopupViewWithUiHandlers<IOOggettoImportaCatalogoDettaglioHandler> implements
IOOggettoImportaCatalogoDettaglioController.iIOOggettoImportaCatalogoDettaglioView {

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOOggettoImportaCatalogoDettaglioView> {
	}

	@UiField Window dialog;
	@UiField FieldSet vFieldset;

	@UiField InlineLabel sbn;
	@UiField InlineLabel titolo;
	@UiField InlineLabel descrizioneFisica;
	@UiField InlineLabel autore;
	@UiField InlineLabel autoreSecondario;
	@UiField InlineLabel editore;
	@UiField InlineLabel ISBNsingolo;
	@UiField InlineLabel luogoPubblicazione;
	@UiField InlineLabel dataPubblicazione;
	@UiField InlineLabel titoloSuperiore;
	
	IOOggettoImportaCatalogoMsg messages;

	/**
	 * Istanzia un nuovo IO oggetto importa catalogo dettaglio view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param messages  messages
	 * @param img  img
	 */
	@Inject
	public IOOggettoImportaCatalogoDettaglioView(final EventBus eventBus, final Binder binder,IOOggettoImportaCatalogoMsg messages, Images img) {
		super(eventBus);
		this.messages = messages;

		widget = binder.createAndBindUi(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}
	
	/**
	 * On chiudi.
	 *
	 * @param event  event
	 */
	@UiHandler("btnChiudi")
	public void onChiudi(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onChiudi();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoDettaglioController.iIOOggettoImportaCatalogoDettaglioView#resetForm(it.lispa.bdl.shared.dto.UnimarcDTO)
	 */
	public void resetForm(UnimarcDTO item) {
		if(item == null){
			vFieldset.setHeadingText("");
			sbn.setText("");
			titolo.setText("");
			descrizioneFisica.setText("");
			autore.setText("");
			autoreSecondario.setText("");
			editore.setText("");
			ISBNsingolo.setText("");
			luogoPubblicazione.setText("");
			dataPubblicazione.setText("");
			titoloSuperiore.setText("");
		}else{
			vFieldset.setHeadingText(item.getTitolo());
			sbn.setText(item.getId());
			titolo.setText(item.getTitolo());
			descrizioneFisica.setText(item.getDescrizioneFisica());
			autore.setText(item.getAutore());
			autoreSecondario.setText(item.getAutoreSecondario());
			editore.setText(item.getEditore());
			ISBNsingolo.setText(item.getISBNsingolo());
			luogoPubblicazione.setText(item.getLuogoPubblicazione());
			dataPubblicazione.setText(item.getDataPubblicazione());
			titoloSuperiore.setText(item.getTitoloSuperiore());
		}
	}
}
