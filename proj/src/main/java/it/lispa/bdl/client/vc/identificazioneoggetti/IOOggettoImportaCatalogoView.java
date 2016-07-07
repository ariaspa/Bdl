package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.client.utils.GXTPopupViewWithUiHandlers;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent.ParseErrorHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.BigDecimalPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

/**
 * Class IOOggettoImportaCatalogoView.
 */
public class IOOggettoImportaCatalogoView extends
GXTPopupViewWithUiHandlers<IOOggettoImportaCatalogoHandler> implements
IOOggettoImportaCatalogoController.iIOOggettoImportaCatalogoView {

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);

	private final Widget widget;

	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, IOOggettoImportaCatalogoView> {
	}

	@UiField Window dialog;
	@UiField CardLayoutContainer stepLayout;

	@UiField FramedPanel step1Panel;
	@UiField FramedPanel stepOpacSbnPanel;

	@UiField TextButton btnAvanti;
	@UiField TextButton btnOpacSbnRicerca;
	@UiField TextButton btnOpacSbnVisualizza;
	@UiField TextButton btnOpacSbnImporta;

	private TreeStore<UnimarcDTO> opacSbnGridStore;	
	@UiField(provided = true) TreeGrid<UnimarcDTO> opacSbnGrid;

	ComboStringDTO.ComboStringDTOProperties cmbStringProps = GWT.create(ComboStringDTO.ComboStringDTOProperties.class);

	ListStore<ComboStringDTO> cmbTipoOggettoStore = new ListStore<ComboStringDTO>(cmbStringProps.id());
	@UiField (provided = true) ComboBox<ComboStringDTO> cmbTipoOggetto = new ComboBox<ComboStringDTO>(cmbTipoOggettoStore, cmbStringProps.desc());


	@UiField TextField fOpacSbnSbn;
	@UiField TextField fOpacSbnAutore;
	@UiField TextField fOpacSbnTitolo;
	@UiField TextField fOpacSbnEditore;
	@UiField TextField fOpacSbnAnnoPubblicazione;

	final IOOggettoImportaCatalogoMsg messages;

	/**
	 * Istanzia un nuovo IO oggetto importa catalogo view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param myMessages  my messages
	 * @param img  img
	 */
	@Inject
	public IOOggettoImportaCatalogoView(final EventBus eventBus, final Binder binder, IOOggettoImportaCatalogoMsg myMessages, Images img) {
		super(eventBus);
		this.messages = myMessages;

		/*
		 * MOCK
		List<TipoOggettoDTO> items = new ArrayList<TipoOggettoDTO>();
		items.add(new TipoOggettoDTO(new BigDecimal(1),"OPAC-SBN","Testo a stampa"));
		items.add(new TipoOggettoDTO(new BigDecimal(2),"Pippo","Carta geografica"));
		cmbTipoOggettoStore.clear();
		cmbTipoOggettoStore.addAll(items);
		 */

		servizioIdentOggetti.getTipologiaOggettiConCatalogo(new AsyncCallback<List<ComboStringDTO>>() {
			public void onFailure(Throwable caught) {
				// gestisco l'errore AsyncServiceException
				new GXTAlertBox(messages.infoPanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
			}
			@Override
			public void onSuccess(List<ComboStringDTO> items) {
				cmbTipoOggettoStore.clear();
				cmbTipoOggettoStore.addAll(items);
			}
		});

		cmbTipoOggetto.addSelectionHandler(new SelectionHandler<ComboStringDTO>() {
			@Override
			public void onSelection(SelectionEvent<ComboStringDTO> event) {
				ComboStringDTO valueSelected = event.getSelectedItem();
				if(valueSelected!=null) {
					btnAvanti.setEnabled(true);
				} else {
					btnAvanti.setEnabled(false);
				}
			}
		});



		UnimarcDTO.UnimarcDTOProperties gridProps = GWT.create(UnimarcDTO.UnimarcDTOProperties.class);

		IdentityValueProvider<UnimarcDTO> identity = new IdentityValueProvider<UnimarcDTO>();
		final CheckBoxSelectionModel<UnimarcDTO> sm = new CheckBoxSelectionModel<UnimarcDTO>(identity);
		sm.setSelectionMode(SelectionMode.SIMPLE);

		ColumnConfig<UnimarcDTO, String> col1 = new ColumnConfig<UnimarcDTO, String>(gridProps.sbn(), 120, messages.lblOpacSbnSbn());
		ColumnConfig<UnimarcDTO, String> col2 = new ColumnConfig<UnimarcDTO, String>(gridProps.autore(), 100, messages.lblOpacSbnAutore());
		ColumnConfig<UnimarcDTO, String> col3 = new ColumnConfig<UnimarcDTO, String>(gridProps.titolo(), 160, messages.lblOpacSbnTitolo());
		ColumnConfig<UnimarcDTO, String> col4 = new ColumnConfig<UnimarcDTO, String>(gridProps.editore(), 80, messages.lblOpacSbnEditore());
		ColumnConfig<UnimarcDTO, String> col5 = new ColumnConfig<UnimarcDTO, String>(gridProps.dataPubblicazione(), 40, messages.lblOpacSbnAnnoPubblicazioneLight());
		ColumnConfig<UnimarcDTO, BigDecimal> col6 = new ColumnConfig<UnimarcDTO, BigDecimal>(gridProps.immagini(), 40, messages.lblOpacSbnImmagini());

		List<ColumnConfig<UnimarcDTO, ?>> l = new ArrayList<ColumnConfig<UnimarcDTO, ?>>();
		l.add(col1);
		l.add(col2);
		l.add(col3);
		l.add(col4);
		l.add(col5);
		l.add(col6);
		l.add(sm.getColumn());
		ColumnModel<UnimarcDTO> cm = new ColumnModel<UnimarcDTO>(l);


		opacSbnGridStore = new TreeStore<UnimarcDTO>(gridProps.id());

		opacSbnGrid = new TreeGrid<UnimarcDTO>(opacSbnGridStore, cm, col1);   
		//opacSbnGrid.getStyle().setLeafIcon(img.getFrecciaOff());
		opacSbnGrid.getView().setAutoExpandColumn(col3);
		opacSbnGrid.getView().setStripeRows(true);
		opacSbnGrid.getView().setColumnLines(true);
		opacSbnGrid.getView().setAutoFill(true);
		opacSbnGrid.setSelectionModel(sm);	
		opacSbnGrid.setLoadMask(true);

		opacSbnGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<UnimarcDTO>() {
			public void onSelectionChanged(SelectionChangedEvent<UnimarcDTO> event) {
				int size = event.getSelection().size();
				if(size > 1){
					btnOpacSbnVisualizza.setEnabled(false);
					btnOpacSbnImporta.setEnabled(true);
				}else if(size == 1){
					btnOpacSbnVisualizza.setEnabled(true);
					btnOpacSbnImporta.setEnabled(true);
				}else{
					btnOpacSbnVisualizza.setEnabled(false);
					btnOpacSbnImporta.setEnabled(false);
				}
			}
		});    

		NumberField<BigDecimal> immagini = new NumberField<BigDecimal>(new BigDecimalPropertyEditor());
		immagini.setAllowDecimals(false);
		immagini.setAllowNegative(false);
		immagini.addParseErrorHandler(new ParseErrorHandler() {
			@Override
			public void onParseError(ParseErrorEvent event) {
				Info.display("Attenzione: ", event.getErrorValue() + " non e' un numero valido");
			}
		});


		// EDITING//
		GridInlineEditing<UnimarcDTO> editing = new GridInlineEditing<UnimarcDTO>(opacSbnGrid);
		editing.addEditor(col6, immagini);
		// EDITING//
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
	
	/**
	 * On avanti.
	 *
	 * @param event  event
	 */
	@UiHandler("btnAvanti")
	public void onAvanti(SelectEvent event) {
		activateStep2();
		if (getUiHandlers() != null) {
			getUiHandlers().onAvanti();
		}
	}
	
	/**
	 * On opac sbn ricerca.
	 *
	 * @param event  event
	 */
	@UiHandler("btnOpacSbnRicerca")
	public void onOpacSbnRicerca(SelectEvent event) {
		btnOpacSbnRicerca.focus();
		getDataForGrid();
	}
	
	/**
	 * On opac sbn visualizza.
	 *
	 * @param event  event
	 */
	@UiHandler("btnOpacSbnVisualizza")
	public void onOpacSbnVisualizza(SelectEvent event) {
		btnOpacSbnVisualizza.focus();
		final List<UnimarcDTO> gridItems = opacSbnGrid.getSelectionModel().getSelectedItems();
		if (getUiHandlers() != null && gridItems.size()==1) {
			getUiHandlers().onOpacSbnVisualizza(gridItems);
		}	
	}
	
	/**
	 * On opac sbn importa.
	 *
	 * @param event  event
	 */
	@UiHandler("btnOpacSbnImporta")
	public void onOpacSbnImporta(SelectEvent event) {
		opacSbnGridStore.commitChanges();
		btnOpacSbnRicerca.focus();

		Boolean errore = false;

		final List<UnimarcDTO> gridItems = opacSbnGrid.getSelectionModel().getSelectedItems();
		Iterator<UnimarcDTO> gridItemsItr = gridItems.iterator();
		while(gridItemsItr.hasNext()){
			UnimarcDTO item = gridItemsItr.next();
			if((item.getTitoliInferiori()==null || item.getTitoliInferiori().isEmpty()) && (item.getImmagini()==null || item.getImmagini().compareTo(new BigDecimal(0))<=0)){
				errore = true;
				new GXTAlertBox(messages.infoOpacSbnPanel(), messages.opacSbnErroreMancanzaImmagini(item.getId() + " " +item.getTitolo()), GXTAlertBox.DO_SHOW);
				break;
			}else if(item.getTitoliInferiori()!=null && !item.getTitoliInferiori().isEmpty() && item.getImmagini()!=null){
				errore = true;
				new GXTAlertBox(messages.infoOpacSbnPanel(), messages.opacSbnErroreImmaginiOggettoSuperiore(item.getId() + " " +item.getTitolo()), GXTAlertBox.DO_SHOW);
				break;				
			}
		}
		if(!errore){
			if (getUiHandlers() != null) {
				getUiHandlers().onOpacSbnImporta(gridItems);
			}	
		}

	}




	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoController.iIOOggettoImportaCatalogoView#resetStep1Form()
	 */
	public void resetStep1Form() {
		cmbTipoOggetto.setValue(null);
	}

	/**
	 * Reset step opac sbn form.
	 */
	public void resetStepOpacSbnForm() {		
		fOpacSbnSbn.setValue("");
		fOpacSbnAutore.setValue("");
		fOpacSbnTitolo.setValue("");
		fOpacSbnEditore.setValue("");
		fOpacSbnAnnoPubblicazione.setValue("");
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoController.iIOOggettoImportaCatalogoView#activateStep1()
	 */
	public void activateStep1(){
		btnAvanti.setEnabled(false);
		getStepLayout().setActiveWidget(getStepLayout().getWidget(0));
		resetStep1Form();
		step1Panel.forceLayout();
	}
	
	/**
	 * Activate step2.
	 */
	public void activateStep2(){
		if(cmbTipoOggetto.getCurrentValue().getId().equals("OPAC-SBN")){
			activateStepOpacSbn();
		}
	}
	
	/**
	 * Activate step opac sbn.
	 */
	public void activateStepOpacSbn(){

		btnOpacSbnVisualizza.setEnabled(false);
		btnOpacSbnImporta.setEnabled(false);
		opacSbnGridStore.clear();
		resetStepOpacSbnForm();
		getStepLayout().setActiveWidget(getStepLayout().getWidget(1));

		stepOpacSbnPanel.forceLayout();
	}

	/**
	 * Legge step layout.
	 *
	 * @return step layout
	 */
	public CardLayoutContainer getStepLayout() {
		return stepLayout;
	}

	private void getDataForGrid() {  

		String sbn = fOpacSbnSbn.getCurrentValue();
		String autore = fOpacSbnAutore.getCurrentValue();
		String titolo = fOpacSbnTitolo.getCurrentValue();
		String editore = fOpacSbnEditore.getCurrentValue();
		String annoPubblicazione = fOpacSbnAnnoPubblicazione.getCurrentValue();

		if(sbn!=null){
			sbn = sbn.trim();
		}else{
			sbn = "";
		}
		if(autore!=null){
			autore = autore.trim();
		}else{
			autore = "";
		}
		if(titolo!=null){
			titolo = titolo.trim();
		}else{
			titolo = "";
		}
		if(editore!=null){
			editore = editore.trim();
		}else{
			editore = "";
		}
		if(annoPubblicazione!=null){
			annoPubblicazione = annoPubblicazione.trim();
		}else{
			annoPubblicazione = "";
		}

		if("".equals(sbn) && "".equals(autore) && "".equals(titolo) && "".equals(editore) && "".equals(annoPubblicazione)){
			new GXTAlertBox(messages.infoPanel(), messages.opacSbnRicercaErrore(), GXTAlertBox.DO_SHOW);
		}else{

			/*
			 * MOCK
			List<UnimarcDTO> items = it.lispa.bdl.client.mockservice.it.lispa.bdl.client.mockservice.Mock.IOOggettoImportaCatalogoView_getDataForGrid();
			for (UnimarcDTO root : items) {
				if(root.getPath()==null || root.getPath().equals("")){
					// sicuramente un oggetto che non ha padre
					opacSbnGridStore.add(root);

					for (UnimarcDTO child : items) {
						if(child.getPath()!=null && child.getPath().equals(root.getId())){
							// sicuramente un oggetto figlio del padre corrente
							opacSbnGridStore.add(root,child);
							// questo processo e' fondamentale
							root.addTitoloInferiore(child.getId());
						}
					}
				}
			}
			opacSbnGrid.expandAll();
			 */
			dialog.mask("Ricerca in corso...");
			servizioIdentOggetti.opacSbnSearch(sbn, autore, titolo, editore, annoPubblicazione, new AsyncCallback<List<UnimarcDTO>>() {
				public void onFailure(Throwable caught) {
					// gestisco l'errore AsyncServiceException
					new GXTAlertBox(messages.infoPanel(), caught.getMessage(), GXTAlertBox.DO_SHOW);
					dialog.unmask();
				}
				@Override
				public void onSuccess(List<UnimarcDTO> items) {
					dialog.unmask();
					opacSbnGridStore.clear();

					for (UnimarcDTO root : items) {
						if(root.getPath()==null || root.getPath().equals("")){
							// sicuramente un oggetto che non ha padre
							opacSbnGridStore.add(root);

							for (UnimarcDTO child : items) {
								if(child.getPath()!=null && child.getPath().equals(root.getId())){
									// sicuramente un oggetto figlio del padre corrente
									opacSbnGridStore.add(root,child);
									// questo processo e' fondamentale
									root.addTitoloInferiore(child.getId());
								}
							}
						}
					}
					opacSbnGrid.expandAll();
				}
			});

		}
	}
}
