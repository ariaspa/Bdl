package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.client.utils.GXTSommarioEditor;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.messages.CatOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.CatalogazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.dnd.core.client.TreeGridDragSource;
import com.sencha.gxt.dnd.core.client.TreeGridDropTarget;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

/**
 * Class CatOggettoDettaglioView.
 */
public class CatOggettoDettaglioView extends ViewWithUiHandlers<CatOggettoDettaglioHandler> implements CatOggettoDettaglioController.iCatOggettoDettaglioView {

	private Integer chiaveSommario;

	private Boolean insertingSommario; 
	
	static class ShortenFactory {
		public static Shorten getFormat(int length) {
			return new Shorten(length);
		}
	}
	static class Shorten implements Formatter<String> {
		private int length;
		public Shorten(int length) {
			this.length = length;
		}
		@Override
		public String format(String data) {
			return Format.ellipse(data, length);
		}
	}
	interface Style extends CssResource {
		String over();
		String select();
		String thumb();
		String thumbWrap();
	}

	@FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
	interface RendererSommario extends XTemplates {
		@XTemplate(source = "CatOggettoDettaglioViewSommario.html")
		public SafeHtml renderItem(ImmagineDTO photo, Style style);
	}
	interface ResourcesSommario extends ClientBundle {
		@Source("CatOggettoDettaglioViewSommario.css")
		Style css();
	}
	@FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
	interface RendererAnteprima extends XTemplates {
		@XTemplate(source = "CatOggettoDettaglioViewAnteprima.html")
		public SafeHtml renderItem(ImmagineDTO photo, Style style);
	}
	interface ResourcesAnteprima extends ClientBundle {
		@Source("CatOggettoDettaglioViewAnteprima.css")
		Style css();
	}

	OggettoDTO item;

	CatalogazioneOggettiServiceAsync servizioCatalogazione = (CatalogazioneOggettiServiceAsync) GWT.create(CatalogazioneOggettiService.class);

	private final Widget widget;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
	ImmagineDTO.ImmagineDTOProperties imageProps = GWT.create(ImmagineDTO.ImmagineDTOProperties.class);

	@UiField FieldSet fieldsetCatalogazioneView;
	@UiField VerticalLayoutContainer fieldsetVlcCatalogazioneView;
	@UiField FieldSet fieldsetCatalogazioneForm;
	@UiField VerticalLayoutContainer fieldsetVlcCatalogazioneForm;

	@UiField TabPanel tabPanel;
	@UiField CardLayoutContainer stepLayoutCatalogazione;

	@UiField HBoxLayoutContainer buttonsCatalogazioneView;
	@UiField HBoxLayoutContainer buttonsCatalogazioneForm;
	@UiField HBoxLayoutContainer buttonsAnteprima;
	@UiField HBoxLayoutContainer buttonsCorrezioni;

	@UiField InlineLabel idCatalogazioneView;
	@UiField InlineLabel titoloCatalogazioneView;
	@UiField InlineLabel titoloSupCatalogazioneView;
	@UiField TextArea titoliInfCatalogazioneView;
	@UiField InlineLabel immaginiPrevisteCatalogazioneView;
	@UiField InlineLabel tipologiaOggettoCatalogazioneView;
	@UiField InlineLabel includeAltriTitoliCatalogazioneView;
	@UiField InlineLabel statoCatalogazioneView;
	@UiField InlineLabel notaNonValidazioneCatalogazioneView;
	@UiField InlineLabel notaNonPubblicazioneCatalogazioneView;
	/* TitoloFE */
	@UiField InlineLabel titoloFeCatalogazioneView;
	
	@UiField InlineLabel descrizioneContenutisticaCatalogazioneView;
	@UiField InlineLabel descrizioneFisicaCatalogazioneView;
	@UiField InlineLabel livelloBibliograficoCatalogazioneView;
	@UiField InlineLabel soggettoCatalogazioneView;
	@UiField InlineLabel qualificatoreAutoreCatalogazioneView;
	@UiField InlineLabel autoreCatalogazioneView;
	@UiField InlineLabel qualificatoreAutore2CatalogazioneView;
	@UiField InlineLabel autore2CatalogazioneView;
	@UiField InlineLabel editoreCatalogazioneView;
	@UiField InlineLabel linguaCatalogazioneView;
	@UiField InlineLabel tipoIdentificativoCatalogazioneView;
	@UiField InlineLabel identificativoCatalogazioneView;
	@UiField InlineLabel luogoPubblicazioneCatalogazioneView;
	@UiField InlineLabel qualificatoreDataCatalogazioneView;
	@UiField InlineLabel dataCatalogazioneView;
	@UiField InlineLabel noteCatalogazioneView;
	@UiField InlineLabel scalaCatalogazioneView;
	@UiField InlineLabel proiezioneCatalogazioneView;
	@UiField InlineLabel coordinateCatalogazioneView;
	@UiField InlineLabel contestoArchivisticoCatalogazioneView;
	@UiField InlineLabel soggettiProduttoriCatalogazioneView;
	@UiField InlineLabel linkCatalogoCatalogazioneView;
	@UiField InlineLabel tipoGraficaCatalogazioneView;
	@UiField InlineLabel supportoPrimarioCatalogazioneView;
	@UiField InlineLabel tecnicaGraficaCatalogazioneView;
	@UiField InlineLabel tipoArchivioCatalogazioneView;
	
	@UiField InlineLabel segnaturaView;

	/* TitoloFE */
	@UiField TextField titoloFeCatalogazioneForm;
	@UiField InlineLabel titoloCatalogazioneForm;
	@UiField InlineLabel titoloSupCatalogazioneForm;
	@UiField TextArea titoliInfCatalogazioneForm;
	@UiField InlineLabel immaginiPrevisteCatalogazioneForm;
	@UiField InlineLabel tipologiaOggettoCatalogazioneForm;
	@UiField InlineLabel includeAltriTitoliCatalogazioneForm;
	@UiField TextArea descrizioneContenutisticaCatalogazioneForm;
	@UiField TextArea descrizioneFisicaCatalogazioneForm;

	ListStore<ComboDTO> livelloBibliograficoCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> livelloBibliograficoCatalogazioneForm = new ComboBox<ComboDTO>(livelloBibliograficoCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> soggettoCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> soggettoCatalogazioneForm = new ComboBox<ComboDTO>(soggettoCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> qualificatoreAutoreCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> qualificatoreAutoreCatalogazioneForm = new ComboBox<ComboDTO>(qualificatoreAutoreCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> autoreCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> autoreCatalogazioneForm = new ComboBox<ComboDTO>(autoreCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> qualificatoreAutore2CatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> qualificatoreAutore2CatalogazioneForm = new ComboBox<ComboDTO>(qualificatoreAutore2CatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> autore2CatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> autore2CatalogazioneForm = new ComboBox<ComboDTO>(autore2CatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> editoreCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> editoreCatalogazioneForm = new ComboBox<ComboDTO>(editoreCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> linguaCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> linguaCatalogazioneForm = new ComboBox<ComboDTO>(linguaCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> tipoIdentificativoCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> tipoIdentificativoCatalogazioneForm = new ComboBox<ComboDTO>(tipoIdentificativoCatalogazioneFormStore, cmbProps.desc());

	@UiField TextField identificativoCatalogazioneForm;
	@UiField TextField luogoPubblicazioneCatalogazioneForm;
	@UiField TextField segnaturaForm;

	ListStore<ComboDTO> qualificatoreDataCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> qualificatoreDataCatalogazioneForm = new ComboBox<ComboDTO>(qualificatoreDataCatalogazioneFormStore, cmbProps.desc());

	@UiField TextField dataCatalogazioneForm;
	@UiField TextArea noteCatalogazioneForm;
	@UiField TextField scalaCatalogazioneForm;
	@UiField TextField proiezioneCatalogazioneForm;
	@UiField TextField coordinateCatalogazioneForm;

	ListStore<ComboDTO> contestoArchivisticoCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> contestoArchivisticoCatalogazioneForm = new ComboBox<ComboDTO>(contestoArchivisticoCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> soggettiProduttoriCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> soggettiProduttoriCatalogazioneForm = new ComboBox<ComboDTO>(soggettiProduttoriCatalogazioneFormStore, cmbProps.desc());

	@UiField TextField linkCatalogoCatalogazioneForm;

	ListStore<ComboDTO> tipoGraficaCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> tipoGraficaCatalogazioneForm = new ComboBox<ComboDTO>(tipoGraficaCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> supportoPrimarioCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> supportoPrimarioCatalogazioneForm = new ComboBox<ComboDTO>(supportoPrimarioCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> tecnicaGraficaCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> tecnicaGraficaCatalogazioneForm = new ComboBox<ComboDTO>(tecnicaGraficaCatalogazioneFormStore, cmbProps.desc());

	ListStore<ComboDTO> tipoArchivioCatalogazioneFormStore = new ListStore<ComboDTO>(cmbProps.id());
	@UiField  (provided = true) ComboBox<ComboDTO> tipoArchivioCatalogazioneForm = new ComboBox<ComboDTO>(tipoArchivioCatalogazioneFormStore, cmbProps.desc());

	private ListStore<CronologiaDTO> gridCronologiaStore;
	@UiField(provided = true) Grid<CronologiaDTO> gridCronologia;


	@UiField(provided = true) TreeGrid<TocSommarioDTO> treeSommario;
	private TreeStore<TocSommarioDTO> treeSommarioStore;

	private SimpleComboBox<String> comboEditorSommario;

	private ListStore<ImmagineDTO> listSommarioStore = new ListStore<ImmagineDTO>(imageProps.cdImmagine());
	@UiField (provided = true) ListView<ImmagineDTO, ImmagineDTO> listSommario;

	private GXTSommarioEditor<TocSommarioDTO> editingSommario;

	private ListStore<ImmagineDTO> listAnteprimaStore = new ListStore<ImmagineDTO>(imageProps.cdImmagine());
	@UiField (provided = true) ListView<ImmagineDTO, ImmagineDTO> listAnteprima;

	@UiField ToolBar toolBarSommario;
	@UiField VerticalLayoutContainer containerSommario;


	@UiField FieldSet fieldsetCorrezioni;
	@UiField TextArea noteCorrezioni;

	CatOggettoDettaglioMsg messages;


	@UiField TextButton btnAnnullaCatalogazioneView;
	@UiField TextButton btnModificaCatalogazioneView;
	@UiField TextButton btnEsportaMetsCatalogazioneView;
	@UiField TextButton btnAnnullaCatalogazioneForm;
	@UiField TextButton btnSalvaCatalogazioneForm;

	@UiField TextButton btnSalvaSommario;
	@UiField TextButton btnInserisciSommario;
	@UiField TextButton btnRinominaSommario;
	@UiField TextButton btnCancellaSommario;
	@UiField TextButton btnDeselezionaSommario;

	@UiField TextButton btnBookreaderAnteprima;
	@UiField TextButton btnPdfAnteprima;
	@UiField TextButton btnInviaCorrezioni;
	
	@UiField FieldLabel notaNonValidazioneCatalogazioneLblView;
	@UiField FieldLabel notaNonPubblicazioneCatalogazioneLblView;

	
	@UiField FieldLabel descrizioneContenutisticaCatalogazioneLblView;
	@UiField FieldLabel descrizioneContenutisticaCatalogazioneLblForm;
	@UiField FieldLabel descrizioneFisicaCatalogazioneLblView;
	@UiField FieldLabel descrizioneFisicaCatalogazioneLblForm;
	@UiField FieldLabel livelloBibliograficoCatalogazioneLblView;
	@UiField FieldLabel livelloBibliograficoCatalogazioneLblForm;
	@UiField FieldLabel soggettoCatalogazioneLblView;
	@UiField FieldLabel soggettoCatalogazioneLblForm;
	@UiField FieldLabel qualificatoreAutoreCatalogazioneLblView;
	@UiField FieldLabel qualificatoreAutoreCatalogazioneLblForm;
	@UiField FieldLabel autoreCatalogazioneLblView;
	@UiField FieldLabel autoreCatalogazioneLblForm;
	@UiField FieldLabel qualificatoreAutore2CatalogazioneLblView;
	@UiField FieldLabel qualificatoreAutore2CatalogazioneLblForm;
	@UiField FieldLabel autore2CatalogazioneLblView;
	@UiField FieldLabel autore2CatalogazioneLblForm;
	@UiField FieldLabel editoreCatalogazioneLblView;
	@UiField FieldLabel editoreCatalogazioneLblForm;
	@UiField FieldLabel linguaCatalogazioneLblView;
	@UiField FieldLabel linguaCatalogazioneLblForm;
	@UiField FieldLabel tipoIdentificativoCatalogazioneLblView;
	@UiField FieldLabel tipoIdentificativoCatalogazioneLblForm;
	@UiField FieldLabel identificativoCatalogazioneLblView;
	@UiField FieldLabel identificativoCatalogazioneLblForm;
	@UiField FieldLabel luogoPubblicazioneCatalogazioneLblView;
	@UiField FieldLabel luogoPubblicazioneCatalogazioneLblForm;
	@UiField FieldLabel qualificatoreDataCatalogazioneLblView;
	@UiField FieldLabel qualificatoreDataCatalogazioneLblForm;
	@UiField FieldLabel dataCatalogazioneLblView;
	@UiField FieldLabel dataCatalogazioneLblForm;
	@UiField FieldLabel noteCatalogazioneLblView;
	@UiField FieldLabel noteCatalogazioneLblForm;
	@UiField FieldLabel scalaCatalogazioneLblView;
	@UiField FieldLabel scalaCatalogazioneLblForm;
	@UiField FieldLabel proiezioneCatalogazioneLblView;
	@UiField FieldLabel proiezioneCatalogazioneLblForm;
	@UiField FieldLabel coordinateCatalogazioneLblView;
	@UiField FieldLabel coordinateCatalogazioneLblForm;
	@UiField FieldLabel contestoArchivisticoCatalogazioneLblView;
	@UiField FieldLabel contestoArchivisticoCatalogazioneLblForm;
	@UiField FieldLabel soggettiProduttoriCatalogazioneLblView;
	@UiField FieldLabel soggettiProduttoriCatalogazioneLblForm;
	@UiField FieldLabel linkCatalogoCatalogazioneLblView;
	@UiField FieldLabel linkCatalogoCatalogazioneLblForm;
	@UiField FieldLabel tipoGraficaCatalogazioneLblView;
	@UiField FieldLabel tipoGraficaCatalogazioneLblForm;
	@UiField FieldLabel supportoPrimarioCatalogazioneLblView;
	@UiField FieldLabel supportoPrimarioCatalogazioneLblForm;
	@UiField FieldLabel tecnicaGraficaCatalogazioneLblView;
	@UiField FieldLabel tecnicaGraficaCatalogazioneLblForm;
	@UiField FieldLabel tipoArchivioCatalogazioneLblView;
	@UiField FieldLabel tipoArchivioCatalogazioneLblForm;
	/* TitoloFE */
	@UiField FieldLabel titoloFeCatalogazioneLblView;
	@UiField FieldLabel titoloFeCatalogazioneLblForm;
	
	@UiField FieldLabel segnaturaLblView;
	@UiField FieldLabel segnaturaLblForm;

	List<Integer> descrizioneContenutisticaMap = new ArrayList<Integer>();
	List<Integer> descrizioneFisicaMap = new ArrayList<Integer>();
	List<Integer> livelloBibliograficoMap = new ArrayList<Integer>();
	List<Integer> soggettoMap = new ArrayList<Integer>();
	List<Integer> qualificatoreAutoreMap = new ArrayList<Integer>();
	List<Integer> autoreMap = new ArrayList<Integer>();
	List<Integer> qualificatoreAutore2Map = new ArrayList<Integer>();
	List<Integer> autore2Map = new ArrayList<Integer>();
	List<Integer> editoreMap = new ArrayList<Integer>();
	List<Integer> linguaMap = new ArrayList<Integer>();
	List<Integer> tipoIdentificativoMap = new ArrayList<Integer>();
	List<Integer> identificativoMap = new ArrayList<Integer>();
	List<Integer> luogoPubblicazioneMap = new ArrayList<Integer>();
	List<Integer> qualificatoreDataMap = new ArrayList<Integer>();
	List<Integer> dataMap = new ArrayList<Integer>();
	List<Integer> noteMap = new ArrayList<Integer>();
	List<Integer> scalaMap = new ArrayList<Integer>();
	List<Integer> proiezioneMap = new ArrayList<Integer>();
	List<Integer> coordinateMap = new ArrayList<Integer>();
	List<Integer> contestoArchivisticoMap = new ArrayList<Integer>();
	List<Integer> soggettiProduttoriMap = new ArrayList<Integer>();
	List<Integer> linkCatalogoMap = new ArrayList<Integer>();
	List<Integer> tipoGraficaMap = new ArrayList<Integer>();
	List<Integer> supportoPrimarioMap = new ArrayList<Integer>();
	List<Integer> tecnicaGraficaMap = new ArrayList<Integer>();
	List<Integer> tipoArchivioMap = new ArrayList<Integer>();
	List<Integer> segnaturaMap = new ArrayList<Integer>();
	/* TitoloFE */
	List<Integer> titoloFeMap = new ArrayList<Integer>();


	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, CatOggettoDettaglioView> {
	}

	/**
	 * Istanzia un nuovo cat oggetto dettaglio view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param myMessages  my messages
	 * @param img  img
	 */
	@Inject
	public CatOggettoDettaglioView(final EventBus eventBus, final Binder binder, CatOggettoDettaglioMsg myMessages, Images img) {

		super();

		insertingSommario = false;

		// 1 Testo a stampa
		titoloFeMap.add(1);
		identificativoMap.add(1);
		descrizioneContenutisticaMap.add(1);
		descrizioneFisicaMap.add(1);
		livelloBibliograficoMap.add(1);
		soggettoMap.add(1);
		qualificatoreAutoreMap.add(1);
		autoreMap.add(1);
		qualificatoreAutore2Map.add(1);
		autore2Map.add(1);
		editoreMap.add(1);
		linguaMap.add(1);
		tipoIdentificativoMap.add(1);
		identificativoMap.add(1);
		luogoPubblicazioneMap.add(1);
		qualificatoreDataMap.add(1);
		dataMap.add(1);
		noteMap.add(1);
		linkCatalogoMap.add(1);

		// 10 Materiale grafico
		titoloFeMap.add(10);
		identificativoMap.add(10);
		descrizioneContenutisticaMap.add(10);
		descrizioneFisicaMap.add(10);
		livelloBibliograficoMap.add(10);
		soggettoMap.add(10);
		qualificatoreAutoreMap.add(10);
		autoreMap.add(10);
		qualificatoreAutore2Map.add(10);
		autore2Map.add(10);
		editoreMap.add(10);
		tipoIdentificativoMap.add(10);
		identificativoMap.add(10);
		luogoPubblicazioneMap.add(10);
		qualificatoreDataMap.add(10);
		dataMap.add(10);
		noteMap.add(10);
		contestoArchivisticoMap.add(10);
		linkCatalogoMap.add(10);
		tipoGraficaMap.add(10);
		supportoPrimarioMap.add(10);
		tecnicaGraficaMap.add(10);

		// 12 Archivi
		titoloFeMap.add(12);
		identificativoMap.add(12);
		descrizioneContenutisticaMap.add(12);
		descrizioneFisicaMap.add(12);
		livelloBibliograficoMap.add(12);
		qualificatoreDataMap.add(12);
		dataMap.add(12);
		noteMap.add(12);
		contestoArchivisticoMap.add(12);
		soggettiProduttoriMap.add(12);
		linkCatalogoMap.add(12);
		tipoArchivioMap.add(12);

		// 11 Incunaboli
		titoloFeMap.add(11);
		identificativoMap.add(11);
		descrizioneContenutisticaMap.add(11);
		descrizioneFisicaMap.add(11);
		livelloBibliograficoMap.add(11);
		soggettoMap.add(11);
		qualificatoreAutoreMap.add(11);
		autoreMap.add(11);
		qualificatoreAutore2Map.add(11);
		autore2Map.add(11);
		editoreMap.add(11);
		linguaMap.add(11);
		tipoIdentificativoMap.add(11);
		identificativoMap.add(11);
		luogoPubblicazioneMap.add(11);
		qualificatoreDataMap.add(11);
		dataMap.add(11);
		noteMap.add(11);
		linkCatalogoMap.add(11);

		// 2 Materiale Manoscritto
		titoloFeMap.add(2);
		identificativoMap.add(2);
		descrizioneContenutisticaMap.add(2);
		descrizioneFisicaMap.add(2);
		livelloBibliograficoMap.add(2);
		soggettoMap.add(2);
		qualificatoreAutoreMap.add(2);
		autoreMap.add(2);
		qualificatoreAutore2Map.add(2);
		autore2Map.add(2);
		linguaMap.add(2);
		tipoIdentificativoMap.add(2);
		identificativoMap.add(2);
		luogoPubblicazioneMap.add(2);
		qualificatoreDataMap.add(2);
		dataMap.add(2);
		noteMap.add(2);
		linkCatalogoMap.add(2);
		supportoPrimarioMap.add(2);
		segnaturaMap.add(2);

		// 5 Cartografia a stampa
		titoloFeMap.add(5);
		identificativoMap.add(5);
		descrizioneContenutisticaMap.add(5);
		descrizioneFisicaMap.add(5);
		livelloBibliograficoMap.add(5);
		soggettoMap.add(5);
		qualificatoreAutoreMap.add(5);
		autoreMap.add(5);
		qualificatoreAutore2Map.add(5);
		autore2Map.add(5);
		editoreMap.add(5);
		tipoIdentificativoMap.add(5);
		identificativoMap.add(5);
		luogoPubblicazioneMap.add(5);
		qualificatoreDataMap.add(5);
		dataMap.add(5);
		noteMap.add(5);
		scalaMap.add(5);
		proiezioneMap.add(5);
		coordinateMap.add(5);
		linkCatalogoMap.add(5);

		// 6 Cartografia Manoscritta
		titoloFeMap.add(6);
		identificativoMap.add(6);
		descrizioneContenutisticaMap.add(6);
		descrizioneFisicaMap.add(6);
		livelloBibliograficoMap.add(6);
		soggettoMap.add(6);
		qualificatoreAutoreMap.add(6);
		autoreMap.add(6);
		qualificatoreAutore2Map.add(6);
		autore2Map.add(6);
		editoreMap.add(6);
		tipoIdentificativoMap.add(6);
		identificativoMap.add(6);
		luogoPubblicazioneMap.add(6);
		qualificatoreDataMap.add(6);
		dataMap.add(6);
		noteMap.add(6);
		scalaMap.add(6);
		proiezioneMap.add(6);
		coordinateMap.add(6);
		linkCatalogoMap.add(6);

		// 3 Musica a stampa
		titoloFeMap.add(3);
		identificativoMap.add(3);
		descrizioneContenutisticaMap.add(3);
		descrizioneFisicaMap.add(3);
		livelloBibliograficoMap.add(3);
		soggettoMap.add(3);
		qualificatoreAutoreMap.add(3);
		autoreMap.add(3);
		qualificatoreAutore2Map.add(3);
		autore2Map.add(3);
		editoreMap.add(3);
		linguaMap.add(3);
		tipoIdentificativoMap.add(3);
		identificativoMap.add(3);
		luogoPubblicazioneMap.add(3);
		qualificatoreDataMap.add(3);
		dataMap.add(3);
		noteMap.add(3);
		linkCatalogoMap.add(3);

		// 4 Musica manoscritta
		titoloFeMap.add(4);
		identificativoMap.add(4);
		descrizioneContenutisticaMap.add(4);
		descrizioneFisicaMap.add(4);
		livelloBibliograficoMap.add(4);
		soggettoMap.add(4);
		qualificatoreAutoreMap.add(4);
		autoreMap.add(4);
		qualificatoreAutore2Map.add(4);
		autore2Map.add(4);
		editoreMap.add(4);
		linguaMap.add(4);
		tipoIdentificativoMap.add(4);
		identificativoMap.add(4);
		luogoPubblicazioneMap.add(4);
		qualificatoreDataMap.add(4);
		dataMap.add(4);
		noteMap.add(4);
		linkCatalogoMap.add(4);

		// 7 Materiali video
		titoloFeMap.add(7);
		identificativoMap.add(7);
		descrizioneContenutisticaMap.add(7);
		descrizioneFisicaMap.add(7);
		livelloBibliograficoMap.add(7);
		soggettoMap.add(7);
		qualificatoreAutoreMap.add(7);
		autoreMap.add(7);
		qualificatoreAutore2Map.add(7);
		autore2Map.add(7);
		editoreMap.add(7);
		linguaMap.add(7);
		tipoIdentificativoMap.add(7);
		identificativoMap.add(7);
		luogoPubblicazioneMap.add(7);
		qualificatoreDataMap.add(7);
		dataMap.add(7);
		noteMap.add(7);
		linkCatalogoMap.add(7);

		// 8 Registrazioni sonore non musicali
		titoloFeMap.add(8);
		identificativoMap.add(8);
		descrizioneContenutisticaMap.add(8);
		descrizioneFisicaMap.add(8);
		livelloBibliograficoMap.add(8);
		soggettoMap.add(8);
		qualificatoreAutoreMap.add(8);
		autoreMap.add(8);
		qualificatoreAutore2Map.add(8);
		autore2Map.add(8);
		editoreMap.add(8);
		linguaMap.add(8);
		tipoIdentificativoMap.add(8);
		identificativoMap.add(8);
		luogoPubblicazioneMap.add(8);
		qualificatoreDataMap.add(8);
		dataMap.add(8);
		noteMap.add(8);
		linkCatalogoMap.add(8);

		// 9 Registrazioni sonore musicali
		titoloFeMap.add(9);
		identificativoMap.add(9);
		descrizioneContenutisticaMap.add(9);
		descrizioneFisicaMap.add(9);
		livelloBibliograficoMap.add(9);
		soggettoMap.add(9);
		qualificatoreAutoreMap.add(9);
		autoreMap.add(9);
		qualificatoreAutore2Map.add(9);
		autore2Map.add(9);
		editoreMap.add(9);
		linguaMap.add(9);
		tipoIdentificativoMap.add(9);
		identificativoMap.add(9);
		luogoPubblicazioneMap.add(9);
		qualificatoreDataMap.add(9);
		dataMap.add(9);
		noteMap.add(9);
		linkCatalogoMap.add(9);

		chiaveSommario = Integer.valueOf(0);

		this.messages = myMessages;

		CronologiaDTO.CronologiaDTOProperties gridCronologiaProps = GWT.create(CronologiaDTO.CronologiaDTOProperties.class);

		gridCronologiaStore = new ListStore<CronologiaDTO>(gridCronologiaProps.cdCronologia());

		ColumnConfig<CronologiaDTO, String> col1Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.descrizione(), 150, messages.colGridDescrizione());
		ColumnConfig<CronologiaDTO, String> col2Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.ruolo(), 80, messages.colGridRuolo());
		ColumnConfig<CronologiaDTO, String> col3Cronologia = new ColumnConfig<CronologiaDTO, String>(gridCronologiaProps.cognomeNome(), 80, messages.colGridCognomeNome());
		ColumnConfig<CronologiaDTO, Date>   col4Cronologia = new ColumnConfig<CronologiaDTO, Date>(gridCronologiaProps.dataOperazione(), 150, messages.colGridDataOperazione());
		DateTimeFormat fmt = DateTimeFormat.getFormat(BdlSharedConstants.DATE_FORMAT_FULL);
		DateCell dcData = new DateCell(fmt);
		col4Cronologia.setCell(dcData);
		List<ColumnConfig<CronologiaDTO, ?>> lCronologia = new ArrayList<ColumnConfig<CronologiaDTO, ?>>();
		lCronologia.add(col1Cronologia);
		lCronologia.add(col2Cronologia);
		lCronologia.add(col3Cronologia);
		lCronologia.add(col4Cronologia);

		ColumnModel<CronologiaDTO> cmCronologia = new ColumnModel<CronologiaDTO>(lCronologia);

		gridCronologia = new Grid<CronologiaDTO>(gridCronologiaStore, cmCronologia);

		gridCronologia.getView().setStripeRows(true);
		gridCronologia.getView().setAutoFill(true);


		TocSommarioDTO.TocSommarioDTOProperties treeSommarioProps = GWT.create(TocSommarioDTO.TocSommarioDTOProperties.class);
		treeSommarioStore = new TreeStore<TocSommarioDTO>(treeSommarioProps.chiave());

		IdentityValueProvider<TocSommarioDTO> identitySommario = new IdentityValueProvider<TocSommarioDTO>();
		final CheckBoxSelectionModel<TocSommarioDTO> smSommario = new CheckBoxSelectionModel<TocSommarioDTO>(identitySommario);
		smSommario.setSelectionMode(SelectionMode.SINGLE);

		ColumnConfig<TocSommarioDTO, String> col1Sommario = new ColumnConfig<TocSommarioDTO, String>(treeSommarioProps.nomeToc(), 120, messages.lblLabelSommario());
		col1Sommario.setSortable(false);
		col1Sommario.setMenuDisabled(true);

		ColumnConfig<TocSommarioDTO, String> col2Sommario = new ColumnConfig<TocSommarioDTO, String>(treeSommarioProps.nomeImmagine(), 25, messages.lblImmagineSommario());
		col2Sommario.setSortable(false);
		col2Sommario.setMenuDisabled(true);

		List<ColumnConfig<TocSommarioDTO, ?>> lSommario = new ArrayList<ColumnConfig<TocSommarioDTO, ?>>();
		lSommario.add(col1Sommario);
		lSommario.add(col2Sommario);
		ColumnModel<TocSommarioDTO> cmSommario = new ColumnModel<TocSommarioDTO>(lSommario);


		treeSommario = new TreeGrid<TocSommarioDTO>(treeSommarioStore, cmSommario, col1Sommario);

		treeSommario.getStyle().setLeafIcon(img.pagina());
		treeSommario.getStyle().setNodeOpenIcon(img.cartella());
		treeSommario.getStyle().setNodeCloseIcon(img.cartella());

		treeSommario.getView().setAutoExpandColumn(col1Sommario);
		treeSommario.getView().setStripeRows(true);
		treeSommario.getView().setColumnLines(true);
		treeSommario.getView().setAutoFill(true);
		treeSommario.setSelectionModel(smSommario);	
		treeSommario.setLoadMask(true);
		treeSommario.setAutoExpand(true);


		comboEditorSommario = new SimpleComboBox<String>(new StringLabelProvider<String>());
		comboEditorSommario.setTriggerAction(TriggerAction.ALL);
		comboEditorSommario.setForceSelection(true);

		// EDITING//
		editingSommario = new GXTSommarioEditor<TocSommarioDTO>(treeSommario);
		editingSommario.addEditor(col1Sommario, new TextField());
		editingSommario.addEditor(col2Sommario, comboEditorSommario);
		editingSommario.addCompleteEditHandler(new CompleteEditHandler<TocSommarioDTO>(){
			@Override
			public void onCompleteEdit(CompleteEditEvent<TocSommarioDTO> event) {
				treeSommarioStore.commitChanges();
				btnSalvaSommario.enable();
			}

		});
		editingSommario.addCancelEditHandler(new CancelEditHandler<TocSommarioDTO>(){
			@Override
			public void onCancelEdit(CancelEditEvent<TocSommarioDTO> event) {
				if(insertingSommario){
					TocSommarioDTO sel = treeSommario.getSelectionModel().getSelectedItem();
					treeSommarioStore.remove(sel);					
				}				
			}

		});
		// EDITING//

		treeSommario.getSelectionModel().addSelectionHandler(new SelectionHandler<TocSommarioDTO>() {
			public void onSelection(SelectionEvent<TocSommarioDTO> event) {
				btnDeselezionaSommario.enable();
				btnRinominaSommario.enable();
				btnCancellaSommario.enable();
			}			
		});


		final ResourcesSommario resourcesSommario = GWT.create(ResourcesSommario.class);
		resourcesSommario.css().ensureInjected();
		final Style styleSommario = resourcesSommario.css();

		final RendererSommario rSommario = GWT.create(RendererSommario.class);

		ListViewCustomAppearance<ImmagineDTO> appearanceSommario = new ListViewCustomAppearance<ImmagineDTO>("." + styleSommario.thumbWrap(), styleSommario.over(), styleSommario.select()) {
			@Override
			public void renderEnd(SafeHtmlBuilder builder) {
				String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
				builder.appendHtmlConstant(markup);
			}
			@Override
			public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
				builder.appendHtmlConstant("<div class='" + styleSommario.thumbWrap() + "' style='border: 1px solid white'>");
				builder.append(content);
				builder.appendHtmlConstant("</div>");
			}
		};
		listSommario  = new ListView<ImmagineDTO, ImmagineDTO>(listSommarioStore, new IdentityValueProvider<ImmagineDTO>() {
			@Override
			public void setValue(ImmagineDTO object, ImmagineDTO value) {

			}
		}, appearanceSommario);
		listSommario.setCell(new SimpleSafeHtmlCell<ImmagineDTO>(new AbstractSafeHtmlRenderer<ImmagineDTO>() {
			@Override
			public SafeHtml render(ImmagineDTO object) {
				return rSommario.renderItem(object, styleSommario);
			}
		}));
		listSommario.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listSommario.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<ImmagineDTO>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<ImmagineDTO> event) {

			}
		});
		listSommario.setBorders(false);


		// ORDERING //
		new TreeGridDragSource<TocSommarioDTO>(treeSommario);

		TreeGridDropTarget<TocSommarioDTO> dndTargetSommario = new TreeGridDropTarget<TocSommarioDTO>(treeSommario);
		dndTargetSommario.setAllowSelfAsSource(true);
		dndTargetSommario.setAllowDropOnLeaf(true);
		dndTargetSommario.setFeedback(Feedback.BOTH);
		dndTargetSommario.addDropHandler(new DndDropHandler(){
			@Override
			public void onDrop(DndDropEvent event) {
				Element itemDroppedOnElement = event.getDragEndEvent().getNativeEvent().getEventTarget().<Element> cast();
				TreeNode<TocSommarioDTO> itemTarget = treeSommario.findNode(itemDroppedOnElement);
				TocSommarioDTO destinazione;
				if(itemTarget==null){
					destinazione = null; 
				}else{
					destinazione = itemTarget.getModel();
					treeSommario.setExpanded(destinazione, true);
				}
				btnSalvaSommario.enable();
			}

		});

		// ORDERING //

		// ASSEGNAZIONE //

		/*
		 * Questo codice non funziona in quanto tira un classcast exception: i due modelli ImmagineDTO
		 */
		ListViewDropTarget<ImmagineDTO> dndTargetImmaginiSommario = new ListViewDropTarget<ImmagineDTO>(listSommario){
			@Override
			protected void onDragDrop(DndDropEvent event) {	
				insertIndex = -1;
				activeItem = null;
			}
		};

		dndTargetImmaginiSommario.setFeedback(Feedback.BOTH);
		dndTargetImmaginiSommario.setOperation(Operation.COPY);
		dndTargetImmaginiSommario.addDropHandler(new DndDropHandler(){
			@SuppressWarnings("unchecked")
			@Override
			public void onDrop(DndDropEvent event) {

				Element itemDroppedOnElement = event.getDragEndEvent().getNativeEvent().getEventTarget().<Element> cast();
				Integer idx = listSommario.findElementIndex(itemDroppedOnElement);
				ImmagineDTO destinazione = listSommarioStore.get(idx);
				if(destinazione!=null){
					List<Object> listSource = (ArrayList<Object>) event.getData();
					com.sencha.gxt.data.shared.TreeStore.TreeNode<TocSommarioDTO> sorgenteObj = (com.sencha.gxt.data.shared.TreeStore.TreeNode<TocSommarioDTO>) listSource.get(0);
					TocSommarioDTO sommario = sorgenteObj.getData();
					TocSommarioDTO sommario2 = treeSommarioStore.findModel(sommario);
					sommario2.setNomeImmagine(destinazione.getNomeImmagine());
					treeSommarioStore.commitChanges();
					treeSommarioStore.update(sommario2);
					treeSommario.refresh(sommario2);
					btnSalvaSommario.enable();
				}
			}

		});

		// ASSEGNAZIONE //





		final ResourcesAnteprima resourcesAnteprima = GWT.create(ResourcesAnteprima.class);
		resourcesAnteprima.css().ensureInjected();
		final Style styleAnteprima = resourcesAnteprima.css();

		final RendererAnteprima rAnteprima = GWT.create(RendererAnteprima.class);

		ListViewCustomAppearance<ImmagineDTO> appearanceAnteprima = new ListViewCustomAppearance<ImmagineDTO>("." + styleAnteprima.thumbWrap(), styleAnteprima.over(), styleAnteprima.select()) {
			@Override
			public void renderEnd(SafeHtmlBuilder builder) {
				String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
				builder.appendHtmlConstant(markup);
			}
			@Override
			public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
				builder.appendHtmlConstant("<div class='" + styleAnteprima.thumbWrap() + "' style='border: 1px solid white'>");
				builder.append(content);
				builder.appendHtmlConstant("</div>");
			}
		};
		listAnteprima  = new ListView<ImmagineDTO, ImmagineDTO>(listAnteprimaStore, new IdentityValueProvider<ImmagineDTO>() {
			@Override
			public void setValue(ImmagineDTO object, ImmagineDTO value) {

			}
		}, appearanceAnteprima);
		listAnteprima.setCell(new SimpleSafeHtmlCell<ImmagineDTO>(new AbstractSafeHtmlRenderer<ImmagineDTO>() {
			@Override
			public SafeHtml render(ImmagineDTO object) {
				return rAnteprima.renderItem(object, styleAnteprima);
			}
		}));
		listAnteprima.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listAnteprima.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<ImmagineDTO>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<ImmagineDTO> event) {
				if (getUiHandlers() != null) {

					getUiHandlers().onImgAnteprimaSelection(event.getSelection().get(0));

				}
				event.getSelection().remove(0);
			}
		});
		listAnteprima.setBorders(false);

		widget = binder.createAndBindUi(this);

		fieldsetVlcCatalogazioneView.setScrollMode(ScrollMode.AUTOY);
		fieldsetVlcCatalogazioneForm.setScrollMode(ScrollMode.AUTOY);

		/*
		 * Questi servono per gli autocomplete
		 */
		soggettoCatalogazioneForm.getCell().setPropertyEditor(new ComboDTO.ComboDTOPropertyEditor(soggettoCatalogazioneForm.getCell()));
		autoreCatalogazioneForm.getCell().setPropertyEditor(new ComboDTO.ComboDTOPropertyEditor(autoreCatalogazioneForm.getCell()));
		autore2CatalogazioneForm.getCell().setPropertyEditor(new ComboDTO.ComboDTOPropertyEditor(autore2CatalogazioneForm.getCell()));
		editoreCatalogazioneForm.getCell().setPropertyEditor(new ComboDTO.ComboDTOPropertyEditor(editoreCatalogazioneForm.getCell()));

		servizioCatalogazione.getLivelloBiblio(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				livelloBibliograficoCatalogazioneFormStore.clear();
				livelloBibliograficoCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getSoggetto(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				soggettoCatalogazioneFormStore.clear();
				soggettoCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getQualificaAutore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				qualificatoreAutoreCatalogazioneFormStore.clear();
				qualificatoreAutoreCatalogazioneFormStore.addAll(oggetti);
				qualificatoreAutore2CatalogazioneFormStore.clear();
				qualificatoreAutore2CatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getAutore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				autoreCatalogazioneFormStore.clear();
				autoreCatalogazioneFormStore.addAll(oggetti);
				autore2CatalogazioneFormStore.clear();
				autore2CatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getEditore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				editoreCatalogazioneFormStore.clear();
				editoreCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getLingua(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				linguaCatalogazioneFormStore.clear();
				linguaCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getTipoIdentificativo(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				tipoIdentificativoCatalogazioneFormStore.clear();
				tipoIdentificativoCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getQualificatoreData(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				qualificatoreDataCatalogazioneFormStore.clear();
				qualificatoreDataCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getContestoArchivistico(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				contestoArchivisticoCatalogazioneFormStore.clear();
				contestoArchivisticoCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getSoggettoProd(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				contestoArchivisticoCatalogazioneFormStore.clear();
				contestoArchivisticoCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getTipoGrafica(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				tipoGraficaCatalogazioneFormStore.clear();
				tipoGraficaCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getSupporto(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				supportoPrimarioCatalogazioneFormStore.clear();
				supportoPrimarioCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getTecnicaGrafica(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				tecnicaGraficaCatalogazioneFormStore.clear();
				tecnicaGraficaCatalogazioneFormStore.addAll(oggetti);
			}
		});

		servizioCatalogazione.getTipoArchivio(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				tipoArchivioCatalogazioneFormStore.clear();
				tipoArchivioCatalogazioneFormStore.addAll(oggetti);
			}
		});
		tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {
	        @Override
	        public void onSelection(SelectionEvent<Widget> event) {
	        	forceLayoutElements();
	        }
	    });
		
		titoliInfCatalogazioneView.setBorders(false);
		titoliInfCatalogazioneForm.setBorders(false);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}


	/**
	 * On btn annulla catalogazione view.
	 *
	 * @param event  event
	 */
	@UiHandler("btnAnnullaCatalogazioneView")
	public void onBtnAnnullaCatalogazioneView(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnAnnullaCatalogazioneView();
		}
	}
	
	/**
	 * On btn modifica catalogazione view.
	 *
	 * @param event  event
	 */
	@UiHandler("btnModificaCatalogazioneView")
	public void onBtnModificaCatalogazioneView(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnModificaCatalogazioneView();
		}
	}
	
	/**
	 * On btn esporta mets catalogazione view.
	 *
	 * @param event  event
	 */
	@UiHandler("btnEsportaMetsCatalogazioneView")
	public void onBtnEsportaMetsCatalogazioneView(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnEsportaMetsCatalogazioneView();
		}
	}

	/**
	 * On btn annulla catalogazione form.
	 *
	 * @param event  event
	 */
	@UiHandler("btnAnnullaCatalogazioneForm")
	public void onBtnAnnullaCatalogazioneForm(SelectEvent event) {
		// Necessario per updatare i valori di CurrentValue e Value del form
		btnAnnullaCatalogazioneForm.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnAnnullaCatalogazioneForm();
		}
	}
	
	/**
	 * On btn salva catalogazione form.
	 *
	 * @param event  event
	 */
	@UiHandler("btnSalvaCatalogazioneForm")
	public void onBtnSalvaCatalogazioneForm(SelectEvent event) {
		// Necessario per updatare i valori di CurrentValue e Value del form
		btnSalvaCatalogazioneForm.focus();
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnSalvaCatalogazioneForm();
		}
	}

	/**
	 * On btn salva sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnSalvaSommario")
	public void onBtnSalvaSommario(SelectEvent event) {
		btnSalvaSommario.disable();
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnSalvaSommario(this.saveTreeSommarioData(event));
		}

	}

	/**
	 * On btn esplodi sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnEsplodiSommario")
	public void onBtnEsplodiSommario(SelectEvent event) {
		treeSommario.expandAll();
	}

	/**
	 * On btn comprimi sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnComprimiSommario")
	public void onBtnComprimiSommario(SelectEvent event) {
		treeSommario.collapseAll();
	}

	/**
	 * On btn inserisci sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnInserisciSommario")
	public void onBtnInserisciSommario(SelectEvent event) {
		if(editingSommario.isEditing()){
			editingSommario.completeEditing();
		}
		TocSommarioDTO sel = treeSommario.getSelectionModel().getSelectedItem();
		TocSommarioDTO child = new TocSommarioDTO(chiaveSommario++, messages.lblNuovoSommario());
		if (sel != null) {
			sel.addChild(child);
			treeSommarioStore.add(sel, child);
		}else{
			treeSommarioStore.add(child);
		}
		treeSommario.setExpanded(child, true);
		treeSommario.getSelectionModel().select(child,false);
		sel = treeSommario.getSelectionModel().getSelectedItem();
		GridCell cell = new GridCell(treeSommario.getStore().indexOf(sel), 0);
		insertingSommario = true;
		editingSommario.startEditing(cell);  
		btnSalvaSommario.enable();
	}

	/**
	 * On btn rinomina sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnRinominaSommario")
	public void onBtnRinominaSommario(SelectEvent event) {
		if(editingSommario.isEditing()){
			editingSommario.completeEditing();
		}
		TocSommarioDTO sel = treeSommario.getSelectionModel().getSelectedItem();
		GridCell cell = new GridCell(treeSommario.getStore().indexOf(sel), 0);
		if(editingSommario.isEditing()){
			editingSommario.completeEditing();
		}else{
			editingSommario.startEditing(cell);
		}
		insertingSommario = false;
		btnSalvaSommario.enable();

	}
	
	/**
	 * On btn cancella sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnCancellaSommario")
	public void onBtnCancellaSommario(SelectEvent event) {
		if(editingSommario.isEditing()){
			editingSommario.completeEditing();
		}
		TocSommarioDTO sel = treeSommario.getSelectionModel().getSelectedItem();
		treeSommarioStore.remove(sel);
		insertingSommario = false;
		btnSalvaSommario.enable();
	}

	/**
	 * On btn deseleziona sommario.
	 *
	 * @param event  event
	 */
	@UiHandler("btnDeselezionaSommario")
	public void onBtnDeselezionaSommario(SelectEvent event) {
		if(editingSommario.isEditing()){
			editingSommario.completeEditing();
		}
		TocSommarioDTO sel = treeSommario.getSelectionModel().getSelectedItem();
		treeSommario.getSelectionModel().deselect(sel);
		insertingSommario = false;
		btnDeselezionaSommario.disable();
	}



	/**
	 * On btn bookreader anteprima.
	 *
	 * @param event  event
	 */
	@UiHandler("btnBookreaderAnteprima")
	public void onBtnBookreaderAnteprima(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnBookreaderAnteprima();
		}
	}
	
	/**
	 * On btn pdf anteprima.
	 *
	 * @param event  event
	 */
	@UiHandler("btnPdfAnteprima")
	public void onBtnPdfAnteprima(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnPdfAnteprima();
		}
	}
	
	/**
	 * On btn invia correzioni.
	 *
	 * @param event  event
	 */
	@UiHandler("btnInviaCorrezioni")
	public void onBtnInviaCorrezioni(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnInviaCorrezioni();
		}		
	}

	/**
	 * Refresh data.
	 *
	 * @param item  item
	 * @param naturaOggettoOriginale  natura oggetto originale
	 */
	public void refreshData(final OggettoDTO item, final String naturaOggettoOriginale) {
		this.item = item;

		// resetto ogni tipo di errore di validazione
		descrizioneContenutisticaCatalogazioneForm.reset();
		descrizioneFisicaCatalogazioneForm.reset();
		livelloBibliograficoCatalogazioneForm.reset();
		soggettoCatalogazioneForm.reset();
		qualificatoreAutoreCatalogazioneForm.reset();
		autoreCatalogazioneForm.reset();
		qualificatoreAutore2CatalogazioneForm.reset();
		autore2CatalogazioneForm.reset();
		editoreCatalogazioneForm.reset();
		linguaCatalogazioneForm.reset();
		tipoIdentificativoCatalogazioneForm.reset();
		identificativoCatalogazioneForm.reset();
		luogoPubblicazioneCatalogazioneForm.reset();
		segnaturaForm.reset();
		qualificatoreDataCatalogazioneForm.reset();
		dataCatalogazioneForm.reset();
		noteCatalogazioneForm.reset();
		scalaCatalogazioneForm.reset();
		proiezioneCatalogazioneForm.reset();
		coordinateCatalogazioneForm.reset();
		contestoArchivisticoCatalogazioneForm.reset();
		soggettiProduttoriCatalogazioneForm.reset();
		linkCatalogoCatalogazioneForm.reset();
		tipoGraficaCatalogazioneForm.reset();
		supportoPrimarioCatalogazioneForm.reset();
		tecnicaGraficaCatalogazioneForm.reset();
		tipoArchivioCatalogazioneForm.reset();
		/* TitoloFE */
		titoloFeCatalogazioneForm.reset();
		
		// item non può essere nullo, quindi procedo a scrivere le label della visualizzazione e successivamente il form
		fieldsetCatalogazioneView.setHeadingText(messages.fieldsetCatalogazioneView(item.getTitolo()));

		servizioCatalogazione.getOggettoId(item.getCdOggettoOriginale(),new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(String id) {
				idCatalogazioneView.setText(id);
			}
		});
		
		if(item.getCdOggettoOriginaleSup()==null && item.getTitoloOggettoOriginaleSup()==null) {
			servizioCatalogazione.getTitoliInf(item.getCdOggettoOriginale(),new AsyncCallback<List<ComboDTO>>() {
				public void onFailure(Throwable caught) {
					// non faccio nulla, errore gestito a monte...
				}
				@Override
				public void onSuccess(List<ComboDTO> titoliInf) {
					StringBuilder sb = new StringBuilder();
					sb.append("");
					if(titoliInf!=null && titoliInf.size()>0) {
						for(int i=0; i<titoliInf.size(); i++) {
							sb.append(titoliInf.get(i).getDesc()+"\n");
						}
					}
					sb.deleteCharAt(sb.lastIndexOf("\n"));
					
					titoliInfCatalogazioneView.setText(sb.toString());
					titoliInfCatalogazioneForm.setText(sb.toString());
					
					titoliInfCatalogazioneView.setReadOnly(true);
					titoliInfCatalogazioneForm.setReadOnly(true);
				}
			});
		}

		titoloCatalogazioneView.setText(item.getTitolo());
		titoloSupCatalogazioneView.setText(item.getTitoloOggettoOriginaleSup());
		immaginiPrevisteCatalogazioneView.setText(item.getImmaginiPreviste() == null ? "" : item.getImmaginiPreviste().toString());
		tipologiaOggettoCatalogazioneView.setText(item.getNomeTipoOggetto());
		if(item.getOggettoSuperiore()){
			includeAltriTitoliCatalogazioneView.setText(BdlSharedConstants.FLAG_TRUE_HUMAN);
		}else{
			includeAltriTitoliCatalogazioneView.setText(BdlSharedConstants.FLAG_FALSE_HUMAN);				
		}
		statoCatalogazioneView.setText(item.getStatoHuman());
		if(item.getStato().equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO)){
			notaNonValidazioneCatalogazioneLblView.show();
			notaNonValidazioneCatalogazioneView.setText(item.getNoteNonValidazione());
		}else{
			notaNonValidazioneCatalogazioneLblView.hide();
			notaNonValidazioneCatalogazioneView.setText("");
		}
		
		if(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO.equals(item.getStato())){
			notaNonPubblicazioneCatalogazioneLblView.show();
			notaNonPubblicazioneCatalogazioneView.setText(item.getNoteNonPubblicazione());
		}else{
			notaNonPubblicazioneCatalogazioneLblView.hide();
			notaNonPubblicazioneCatalogazioneView.setText("");
		}
		descrizioneContenutisticaCatalogazioneView.setText(item.getContenutistica());
		descrizioneFisicaCatalogazioneView.setText(item.getFisica());
		livelloBibliograficoCatalogazioneView.setText(item.getDescrizioneLivelloBiblio());
		soggettoCatalogazioneView.setText(item.getDescrizioneSoggetto());
		qualificatoreAutoreCatalogazioneView.setText(item.getDescrizioneQualificaAutore());
		autoreCatalogazioneView.setText(item.getDescrizioneAutore());
		qualificatoreAutore2CatalogazioneView.setText(item.getDescrizioneQualificaAutoreSec());
		autore2CatalogazioneView.setText(item.getDescrizioneAutoreSec());
		editoreCatalogazioneView.setText(item.getDescrizioneEditore());
		linguaCatalogazioneView.setText(item.getDescrizioneLingua());
		tipoIdentificativoCatalogazioneView.setText(item.getDescrizioneTipoIdentificativo());
		identificativoCatalogazioneView.setText(item.getIdentificativo());
		luogoPubblicazioneCatalogazioneView.setText(item.getLuogoPubblicazione());
		segnaturaView.setText(item.getSegnatura());
		
		qualificatoreDataCatalogazioneView.setText(item.getDescrizioneQualificatoreData());
		dataCatalogazioneView.setText(item.getData());
		noteCatalogazioneView.setText(item.getNote());
		scalaCatalogazioneView.setText(item.getScala());
		proiezioneCatalogazioneView.setText(item.getProiezione());
		coordinateCatalogazioneView.setText(item.getCoordinate());
		contestoArchivisticoCatalogazioneView.setText(item.getDescrizioneContestoArch());
		soggettiProduttoriCatalogazioneView.setText(item.getDescrizioneSoggettoProd());
		linkCatalogoCatalogazioneView.setText(item.getLinkCatalogo());
		tipoGraficaCatalogazioneView.setText(item.getDescrizioneTipoGrafica());
		supportoPrimarioCatalogazioneView.setText(item.getDescrizioneSupporto());
		tecnicaGraficaCatalogazioneView.setText(item.getDescrizioneTecnicaGrafica());
		tipoArchivioCatalogazioneView.setText(item.getDescrizioneTipoArchivio());
		/* TitoloFE */
		titoloFeCatalogazioneView.setText(item.getTitoloFe());

		fieldsetCatalogazioneForm.setHeadingText(messages.fieldsetCatalogazioneForm(item.getTitolo()));
		titoloCatalogazioneForm.setText(item.getTitolo());
		titoloSupCatalogazioneForm.setText(item.getTitoloOggettoOriginaleSup());
		immaginiPrevisteCatalogazioneForm.setText(item.getImmaginiPreviste() == null ? "" : item.getImmaginiPreviste().toString());
		tipologiaOggettoCatalogazioneForm.setText(item.getNomeTipoOggetto());
		if(item.getOggettoSuperiore()){
			includeAltriTitoliCatalogazioneForm.setText(BdlSharedConstants.FLAG_TRUE_HUMAN);
		}else{
			includeAltriTitoliCatalogazioneForm.setText(BdlSharedConstants.FLAG_FALSE_HUMAN);				
		}
		descrizioneContenutisticaCatalogazioneForm.setValue(item.getContenutistica());
		descrizioneFisicaCatalogazioneForm.setValue(item.getFisica());
		ComboDTO livelloBibliograficoCatalogazioneFormSelected = new ComboDTO(item.getCdLivelloBiblio(),item.getDescrizioneLivelloBiblio());
		livelloBibliograficoCatalogazioneForm.setValue(livelloBibliograficoCatalogazioneFormSelected);

		servizioCatalogazione.getSoggetto(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				soggettoCatalogazioneFormStore.clear();
				soggettoCatalogazioneFormStore.addAll(oggetti);
				ComboDTO soggettoCatalogazioneFormSelected = new ComboDTO(item.getCdSoggetto(),item.getDescrizioneSoggetto());
				soggettoCatalogazioneForm.setValue(soggettoCatalogazioneFormSelected);
			}
		});


		ComboDTO qualificatoreAutoreCatalogazioneFormSelected = new ComboDTO(item.getCdQualificaAutore(),item.getDescrizioneQualificaAutore());
		qualificatoreAutoreCatalogazioneForm.setValue(qualificatoreAutoreCatalogazioneFormSelected);

		ComboDTO qualificatoreAutore2CatalogazioneFormSelected = new ComboDTO(item.getCdQualificaAutoreSec(),item.getDescrizioneQualificaAutoreSec());
		qualificatoreAutore2CatalogazioneForm.setValue(qualificatoreAutore2CatalogazioneFormSelected);

		servizioCatalogazione.getAutore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				autoreCatalogazioneFormStore.clear();
				autoreCatalogazioneFormStore.addAll(oggetti);
				autore2CatalogazioneFormStore.clear();
				autore2CatalogazioneFormStore.addAll(oggetti);

				ComboDTO autoreCatalogazioneFormSelected = new ComboDTO(item.getCdAutore(),item.getDescrizioneAutore());
				autoreCatalogazioneForm.setValue(autoreCatalogazioneFormSelected);
				ComboDTO autore2CatalogazioneFormSelected = new ComboDTO(item.getCdAutoreSec(),item.getDescrizioneAutoreSec());
				autore2CatalogazioneForm.setValue(autore2CatalogazioneFormSelected);
			}
		});

		servizioCatalogazione.getEditore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ComboDTO> oggetti) {
				editoreCatalogazioneFormStore.clear();
				editoreCatalogazioneFormStore.addAll(oggetti);
				ComboDTO editoreCatalogazioneFormSelected = new ComboDTO(item.getCdEditore(),item.getDescrizioneEditore());
				editoreCatalogazioneForm.setValue(editoreCatalogazioneFormSelected);
			}
		});

		ComboDTO linguaCatalogazioneFormSelected = new ComboDTO(item.getCdLingua(),item.getDescrizioneLingua());
		linguaCatalogazioneForm.setValue(linguaCatalogazioneFormSelected);
		ComboDTO tipoIdentificativoCatalogazioneFormSelected = new ComboDTO(item.getCdTipoIdentificativo(),item.getDescrizioneTipoIdentificativo());
		tipoIdentificativoCatalogazioneForm.setValue(tipoIdentificativoCatalogazioneFormSelected);
		identificativoCatalogazioneForm.setValue(item.getIdentificativo());
		luogoPubblicazioneCatalogazioneForm.setValue(item.getLuogoPubblicazione());
		segnaturaForm.setValue(item.getSegnatura());
		ComboDTO qualificatoreDataCatalogazioneFormSelected = new ComboDTO(item.getCdQualificatoreData(),item.getDescrizioneQualificatoreData());
		qualificatoreDataCatalogazioneForm.setValue(qualificatoreDataCatalogazioneFormSelected);
		dataCatalogazioneForm.setValue(item.getData());
		noteCatalogazioneForm.setValue(item.getNote());
		scalaCatalogazioneForm.setValue(item.getScala());
		proiezioneCatalogazioneForm.setValue(item.getProiezione());
		coordinateCatalogazioneForm.setValue(item.getCoordinate());
		ComboDTO contestoArchivisticoCatalogazioneFormSelected = new ComboDTO(item.getCdContestoArch(),item.getDescrizioneContestoArch());
		contestoArchivisticoCatalogazioneForm.setValue(contestoArchivisticoCatalogazioneFormSelected);
		ComboDTO soggettiProduttoriCatalogazioneFormSelected = new ComboDTO(item.getCdSoggettoProd(),item.getDescrizioneSoggettoProd());
		soggettiProduttoriCatalogazioneForm.setValue(soggettiProduttoriCatalogazioneFormSelected);
		linkCatalogoCatalogazioneForm.setValue(item.getLinkCatalogo());
		ComboDTO tipoGraficaCatalogazioneFormSelected = new ComboDTO(item.getCdTipoGrafica(),item.getDescrizioneTipoGrafica());
		tipoGraficaCatalogazioneForm.setValue(tipoGraficaCatalogazioneFormSelected);
		ComboDTO supportoPrimarioCatalogazioneFormSelected = new ComboDTO(item.getCdSupporto(),item.getDescrizioneSupporto());
		supportoPrimarioCatalogazioneForm.setValue(supportoPrimarioCatalogazioneFormSelected);
		ComboDTO tecnicaGraficaCatalogazioneFormSelected = new ComboDTO(item.getCdTecnicaGrafica(),item.getDescrizioneTecnicaGrafica());
		tecnicaGraficaCatalogazioneForm.setValue(tecnicaGraficaCatalogazioneFormSelected);
		ComboDTO tipoArchivioCatalogazioneFormSelected = new ComboDTO(item.getCdTipoArchivio(),item.getDescrizioneTipoArchivio());
		tipoArchivioCatalogazioneForm.setValue(tipoArchivioCatalogazioneFormSelected);
		/* TitoloFE */
		titoloFeCatalogazioneForm.setValue(item.getTitoloFe());
		
		/*
		 * MOCK
		gridCronologiaStore.clear();
		gridCronologiaStore.addAll(it.lispa.bdl.client.mockservice.Mock.CatOggettoDettaglioView_refreshDataGridCronologia());
		 */

		servizioCatalogazione.getCronologia(item.getCdOggettoOriginale(),new AsyncCallback<List<CronologiaDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<CronologiaDTO> oggetti) {
				gridCronologiaStore.clear();
				gridCronologiaStore.addAll(oggetti);
			}
		});



		servizioCatalogazione.getTocSommarioOggetto(item.getCdOggettoOriginale(),new AsyncCallback<List<TocSommarioDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<TocSommarioDTO> oggetti) {
				loadTreeSommarioData(oggetti);
			}
		});


		/*
		 * MOCK
		 * 
		listSommarioStore.clear();
		listSommarioStore.addAll(it.lispa.bdl.client.mockservice.Mock.CatOggettoDettaglioView_refreshDataListAnteprima());
		listAnteprimaStore.clear();
		listAnteprimaStore.addAll(it.lispa.bdl.client.mockservice.Mock.CatOggettoDettaglioView_refreshDataListAnteprima());
		comboEditorSommario.clear();
		for(int i=0;i<400;i++){
			comboEditorSommario.add(Integer.toString(i));
		}
		 */
		
		listAnteprimaStore.clear();
		listSommarioStore.clear();
		comboEditorSommario.getStore().clear();
		comboEditorSommario.clear();
				
		if(naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE) || naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE )) {			
			containerSommario.unmask();
		}else{			
			containerSommario.mask("Sommario non disponibile per oggetti di tipo "+naturaOggettoOriginale);
		}
		servizioCatalogazione.getImmaginiOggetto(item.getCdOggettoOriginale(),new AsyncCallback<List<ImmagineDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ImmagineDTO> oggetti) {
				listAnteprimaStore.addAll(oggetti);
				if(naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE) || naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE ) ) {
					listSommarioStore.addAll(oggetti);
					for(ImmagineDTO img:oggetti){
						comboEditorSommario.add(img.getNomeImmagine());
					}
				}
			}
		});

		noteCorrezioni.setValue("");
		
		if(descrizioneContenutisticaMap.contains(item.getCdTipoOggetto().intValueExact())){
			descrizioneContenutisticaCatalogazioneLblView.show();
			descrizioneContenutisticaCatalogazioneLblForm.show();
		}else{
			descrizioneContenutisticaCatalogazioneLblView.hide();
			descrizioneContenutisticaCatalogazioneLblForm.hide();   
		}
		if(descrizioneFisicaMap.contains(item.getCdTipoOggetto().intValueExact())){
			descrizioneFisicaCatalogazioneLblView.show();
			descrizioneFisicaCatalogazioneLblForm.show();
		}else{
			descrizioneFisicaCatalogazioneLblView.hide();
			descrizioneFisicaCatalogazioneLblForm.hide();   
		}
		if(livelloBibliograficoMap.contains(item.getCdTipoOggetto().intValueExact())){
			livelloBibliograficoCatalogazioneLblView.show();
			livelloBibliograficoCatalogazioneLblForm.show();
		}else{
			livelloBibliograficoCatalogazioneLblView.hide();
			livelloBibliograficoCatalogazioneLblForm.hide();   
		}
		if(soggettoMap.contains(item.getCdTipoOggetto().intValueExact())){
			soggettoCatalogazioneLblView.show();
			soggettoCatalogazioneLblForm.show();
		}else{
			soggettoCatalogazioneLblView.hide();
			soggettoCatalogazioneLblForm.hide();   
		}
		if(qualificatoreAutoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreAutoreCatalogazioneLblView.show();
			qualificatoreAutoreCatalogazioneLblForm.show();
		}else{
			qualificatoreAutoreCatalogazioneLblView.hide();
			qualificatoreAutoreCatalogazioneLblForm.hide();   
		}
		if(autoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			autoreCatalogazioneLblView.show();
			autoreCatalogazioneLblForm.show();
		}else{
			autoreCatalogazioneLblView.hide();
			autoreCatalogazioneLblForm.hide();   
		}
		if(qualificatoreAutore2Map.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreAutore2CatalogazioneLblView.show();
			qualificatoreAutore2CatalogazioneLblForm.show();
		}else{
			qualificatoreAutore2CatalogazioneLblView.hide();
			qualificatoreAutore2CatalogazioneLblForm.hide();   
		}
		if(autore2Map.contains(item.getCdTipoOggetto().intValueExact())){
			autore2CatalogazioneLblView.show();
			autore2CatalogazioneLblForm.show();
		}else{
			autore2CatalogazioneLblView.hide();
			autore2CatalogazioneLblForm.hide();   
		}
		if(editoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			editoreCatalogazioneLblView.show();
			editoreCatalogazioneLblForm.show();
		}else{
			editoreCatalogazioneLblView.hide();
			editoreCatalogazioneLblForm.hide();   
		}
		if(linguaMap.contains(item.getCdTipoOggetto().intValueExact())){
			linguaCatalogazioneLblView.show();
			linguaCatalogazioneLblForm.show();
		}else{
			linguaCatalogazioneLblView.hide();
			linguaCatalogazioneLblForm.hide();   
		}
		if(tipoIdentificativoMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoIdentificativoCatalogazioneLblView.show();
			tipoIdentificativoCatalogazioneLblForm.show();
		}else{
			tipoIdentificativoCatalogazioneLblView.hide();
			tipoIdentificativoCatalogazioneLblForm.hide();   
		}
		if(identificativoMap.contains(item.getCdTipoOggetto().intValueExact())){
			identificativoCatalogazioneLblView.show();
			identificativoCatalogazioneLblForm.show();
		}else{
			identificativoCatalogazioneLblView.hide();
			identificativoCatalogazioneLblForm.hide();   
		}
		if(luogoPubblicazioneMap.contains(item.getCdTipoOggetto().intValueExact())){
			luogoPubblicazioneCatalogazioneLblView.show();
			luogoPubblicazioneCatalogazioneLblForm.show();
		}else{
			luogoPubblicazioneCatalogazioneLblView.hide();
			luogoPubblicazioneCatalogazioneLblForm.hide();   
		}
		if(qualificatoreDataMap.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreDataCatalogazioneLblView.show();
			qualificatoreDataCatalogazioneLblForm.show();
		}else{
			qualificatoreDataCatalogazioneLblView.hide();
			qualificatoreDataCatalogazioneLblForm.hide();   
		}
		if(dataMap.contains(item.getCdTipoOggetto().intValueExact())){
			dataCatalogazioneLblView.show();
			dataCatalogazioneLblForm.show();
		}else{
			dataCatalogazioneLblView.hide();
			dataCatalogazioneLblForm.hide();   
		}
		if(noteMap.contains(item.getCdTipoOggetto().intValueExact())){
			noteCatalogazioneLblView.show();
			noteCatalogazioneLblForm.show();
		}else{
			noteCatalogazioneLblView.hide();
			noteCatalogazioneLblForm.hide();   
		}
		if(scalaMap.contains(item.getCdTipoOggetto().intValueExact())){
			scalaCatalogazioneLblView.show();
			scalaCatalogazioneLblForm.show();
		}else{
			scalaCatalogazioneLblView.hide();
			scalaCatalogazioneLblForm.hide();   
		}
		if(proiezioneMap.contains(item.getCdTipoOggetto().intValueExact())){
			proiezioneCatalogazioneLblView.show();
			proiezioneCatalogazioneLblForm.show();
		}else{
			proiezioneCatalogazioneLblView.hide();
			proiezioneCatalogazioneLblForm.hide();   
		}
		if(coordinateMap.contains(item.getCdTipoOggetto().intValueExact())){
			coordinateCatalogazioneLblView.show();
			coordinateCatalogazioneLblForm.show();
		}else{
			coordinateCatalogazioneLblView.hide();
			coordinateCatalogazioneLblForm.hide();   
		}
		if(contestoArchivisticoMap.contains(item.getCdTipoOggetto().intValueExact())){
			contestoArchivisticoCatalogazioneLblView.show();
			contestoArchivisticoCatalogazioneLblForm.show();
		}else{
			contestoArchivisticoCatalogazioneLblView.hide();
			contestoArchivisticoCatalogazioneLblForm.hide();   
		}
		if(soggettiProduttoriMap.contains(item.getCdTipoOggetto().intValueExact())){
			soggettiProduttoriCatalogazioneLblView.show();
			soggettiProduttoriCatalogazioneLblForm.show();
		}else{
			soggettiProduttoriCatalogazioneLblView.hide();
			soggettiProduttoriCatalogazioneLblForm.hide();   
		}
		if(linkCatalogoMap.contains(item.getCdTipoOggetto().intValueExact())){
			linkCatalogoCatalogazioneLblView.show();
			linkCatalogoCatalogazioneLblForm.show();
		}else{
			linkCatalogoCatalogazioneLblView.hide();
			linkCatalogoCatalogazioneLblForm.hide();   
		}
		if(tipoGraficaMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoGraficaCatalogazioneLblView.show();
			tipoGraficaCatalogazioneLblForm.show();
		}else{
			tipoGraficaCatalogazioneLblView.hide();
			tipoGraficaCatalogazioneLblForm.hide();   
		}
		if(supportoPrimarioMap.contains(item.getCdTipoOggetto().intValueExact())){
			supportoPrimarioCatalogazioneLblView.show();
			supportoPrimarioCatalogazioneLblForm.show();
		}else{
			supportoPrimarioCatalogazioneLblView.hide();
			supportoPrimarioCatalogazioneLblForm.hide();   
		}
		if(tecnicaGraficaMap.contains(item.getCdTipoOggetto().intValueExact())){
			tecnicaGraficaCatalogazioneLblView.show();
			tecnicaGraficaCatalogazioneLblForm.show();
		}else{
			tecnicaGraficaCatalogazioneLblView.hide();
			tecnicaGraficaCatalogazioneLblForm.hide();   
		}
		if(tipoArchivioMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoArchivioCatalogazioneLblView.show();
			tipoArchivioCatalogazioneLblForm.show();
		}else{
			tipoArchivioCatalogazioneLblView.hide();
			tipoArchivioCatalogazioneLblForm.hide();   
		}
		if(titoloFeMap.contains(item.getCdTipoOggetto().intValueExact())){
			titoloFeCatalogazioneLblView.show();
			titoloFeCatalogazioneLblForm.show();
		}else{
			titoloFeCatalogazioneLblView.hide();
			titoloFeCatalogazioneLblForm.hide();   
		}
		if(segnaturaMap.contains(item.getCdTipoOggetto().intValueExact())){
			segnaturaLblView.show();
			segnaturaLblForm.show();
		}else{
			segnaturaLblView.hide();
			segnaturaLblForm.hide();   
		}
		
		if(item.getOggettoDigitale()){
			btnBookreaderAnteprima.enable();
			if(!naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)){
				btnPdfAnteprima.enable();
			}else{
				btnPdfAnteprima.disable();
			}
			btnInviaCorrezioni.enable();
		}else{
			btnBookreaderAnteprima.disable();
			btnPdfAnteprima.disable();
			btnInviaCorrezioni.disable();
		}
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#getOggettoDTO()
	 */
	public OggettoDTO getOggettoDTO() {

		OggettoDTO oggettoDTO = new OggettoDTO(
				item.getCdOggettoOriginale(),
				item.getCdTipoOggetto(), 
				item.getNomeTipoOggetto(), 
				item.getCdOggettoOriginaleSup(), 
				item.getTitoloOggettoOriginaleSup(),
				item.getCdProgetto(),
				item.getTitoloProgetto(),
				item.getCdCollezione(),
				item.getTitoloCollezione(),
				item.getTitolo(),
				/* TitoloFE */
				titoloFeCatalogazioneForm.getCurrentValue()!=null ? titoloFeCatalogazioneForm.getCurrentValue() : item.getTitolo(),
				item.getOggettoSuperiore(),
				item.getOggettiInferiori(),
				item.getOggettoDigitale(),
				item.getCorrezione(),
				item.getNotaCorrezione(),
				item.getAnomaliaImmagini(),
				item.getLogAnomalia(),
				item.getStato(),
				item.getNoteNonValidazione(),
				item.getNoteNonPubblicazione(),
				item.getImmaginiPreviste(),
				item.getImmaginiDigitalizzate(),

				descrizioneContenutisticaCatalogazioneForm.getCurrentValue(),
				descrizioneFisicaCatalogazioneForm.getCurrentValue(),
				livelloBibliograficoCatalogazioneForm.getCurrentValue()!=null ? livelloBibliograficoCatalogazioneForm.getCurrentValue().getId() : null,
				livelloBibliograficoCatalogazioneForm.getCurrentValue()!=null ? livelloBibliograficoCatalogazioneForm.getCurrentValue().getDesc() : null,
				soggettoCatalogazioneForm.getCurrentValue()!=null ? soggettoCatalogazioneForm.getCurrentValue().getId() : null,
				soggettoCatalogazioneForm.getCurrentValue()!=null ? soggettoCatalogazioneForm.getCurrentValue().getDesc() : null,
				autoreCatalogazioneForm.getCurrentValue()!=null ? autoreCatalogazioneForm.getCurrentValue().getId() : null,
				autoreCatalogazioneForm.getCurrentValue()!=null ? autoreCatalogazioneForm.getCurrentValue().getDesc() : null,
				autore2CatalogazioneForm.getCurrentValue()!=null ? autore2CatalogazioneForm.getCurrentValue().getId() : null,
				autore2CatalogazioneForm.getCurrentValue()!=null ? autore2CatalogazioneForm.getCurrentValue().getDesc() : null,
				qualificatoreAutoreCatalogazioneForm.getCurrentValue()!=null ? qualificatoreAutoreCatalogazioneForm.getCurrentValue().getId() : null,
				qualificatoreAutoreCatalogazioneForm.getCurrentValue()!=null ? qualificatoreAutoreCatalogazioneForm.getCurrentValue().getDesc() : null,
				qualificatoreAutore2CatalogazioneForm.getCurrentValue()!=null ? qualificatoreAutore2CatalogazioneForm.getCurrentValue().getId() : null,
				qualificatoreAutore2CatalogazioneForm.getCurrentValue()!=null ? qualificatoreAutore2CatalogazioneForm.getCurrentValue().getDesc() : null,
				editoreCatalogazioneForm.getCurrentValue()!=null ? editoreCatalogazioneForm.getCurrentValue().getId() : null,
				editoreCatalogazioneForm.getCurrentValue()!=null ? editoreCatalogazioneForm.getCurrentValue().getDesc() : null,
				linguaCatalogazioneForm.getCurrentValue()!=null ? linguaCatalogazioneForm.getCurrentValue().getId() : null,
				linguaCatalogazioneForm.getCurrentValue()!=null ? linguaCatalogazioneForm.getCurrentValue().getDesc() : null,
				tipoIdentificativoCatalogazioneForm.getCurrentValue()!=null ? tipoIdentificativoCatalogazioneForm.getCurrentValue().getId() : null,
				tipoIdentificativoCatalogazioneForm.getCurrentValue()!=null ? tipoIdentificativoCatalogazioneForm.getCurrentValue().getDesc() : null,
				identificativoCatalogazioneForm.getCurrentValue()!=null ? identificativoCatalogazioneForm.getCurrentValue() : null,
				luogoPubblicazioneCatalogazioneForm.getCurrentValue()!=null ? luogoPubblicazioneCatalogazioneForm.getCurrentValue() : null,
				qualificatoreDataCatalogazioneForm.getCurrentValue()!=null ? qualificatoreDataCatalogazioneForm.getCurrentValue().getId() : null,
				qualificatoreDataCatalogazioneForm.getCurrentValue()!=null ? qualificatoreDataCatalogazioneForm.getCurrentValue().getDesc() : null,
				dataCatalogazioneForm.getCurrentValue()!=null ? dataCatalogazioneForm.getCurrentValue() : null,
				noteCatalogazioneForm.getCurrentValue()!=null ? noteCatalogazioneForm.getCurrentValue() : null,
				supportoPrimarioCatalogazioneForm.getCurrentValue()!=null ? supportoPrimarioCatalogazioneForm.getCurrentValue().getId() : null,
				supportoPrimarioCatalogazioneForm.getCurrentValue()!=null ? supportoPrimarioCatalogazioneForm.getCurrentValue().getDesc() : null,
				tecnicaGraficaCatalogazioneForm.getCurrentValue()!=null ? tecnicaGraficaCatalogazioneForm.getCurrentValue().getId() : null,
				tecnicaGraficaCatalogazioneForm.getCurrentValue()!=null ? tecnicaGraficaCatalogazioneForm.getCurrentValue().getDesc() : null,
				tipoGraficaCatalogazioneForm.getCurrentValue()!=null ? tipoGraficaCatalogazioneForm.getCurrentValue().getId() : null,
				tipoGraficaCatalogazioneForm.getCurrentValue()!=null ? tipoGraficaCatalogazioneForm.getCurrentValue().getDesc() : null,
				scalaCatalogazioneForm.getCurrentValue()!=null ? scalaCatalogazioneForm.getCurrentValue() : null,
				proiezioneCatalogazioneForm.getCurrentValue()!=null ? proiezioneCatalogazioneForm.getCurrentValue() : null,
				coordinateCatalogazioneForm.getCurrentValue()!=null ? coordinateCatalogazioneForm.getCurrentValue() : null,
				soggettiProduttoriCatalogazioneForm.getCurrentValue()!=null ? soggettiProduttoriCatalogazioneForm.getCurrentValue().getId() : null,
				soggettiProduttoriCatalogazioneForm.getCurrentValue()!=null ? soggettiProduttoriCatalogazioneForm.getCurrentValue().getDesc() : null,
				contestoArchivisticoCatalogazioneForm.getCurrentValue()!=null ? contestoArchivisticoCatalogazioneForm.getCurrentValue().getId() : null,
				contestoArchivisticoCatalogazioneForm.getCurrentValue()!=null ? contestoArchivisticoCatalogazioneForm.getCurrentValue().getDesc() : null,
				tipoArchivioCatalogazioneForm.getCurrentValue()!=null ? tipoArchivioCatalogazioneForm.getCurrentValue().getId() : null,
				tipoArchivioCatalogazioneForm.getCurrentValue()!=null ? tipoArchivioCatalogazioneForm.getCurrentValue().getDesc() : null,
				linkCatalogoCatalogazioneForm.getCurrentValue()!=null ? linkCatalogoCatalogazioneForm.getCurrentValue() : null,
				null, //diritti
				null, //altreinfo
				null, //creatoda
				null, //creatoil
				null, //modificatoda
				null, //modificatoil
				item.getDtPubblicatoil(),
				item.getNrRilevanza()
				);
		
		oggettoDTO.setSegnatura(segnaturaForm.getCurrentValue());
		return oggettoDTO; 
	}


	private void forceLayoutElements(){
		
		stepLayoutCatalogazione.forceLayout();
		buttonsCatalogazioneView.forceLayout();
		fieldsetCatalogazioneView.forceLayout();
		fieldsetVlcCatalogazioneView.forceLayout();
		fieldsetCatalogazioneView.forceLayout();
		buttonsCatalogazioneForm.forceLayout();
		fieldsetCatalogazioneForm.forceLayout();
		fieldsetVlcCatalogazioneForm.forceLayout();
		fieldsetCatalogazioneForm.forceLayout();
		buttonsAnteprima.forceLayout();
		fieldsetVlcCatalogazioneView.getScrollSupport().scrollToTop();
		fieldsetVlcCatalogazioneForm.getScrollSupport().scrollToTop();
		fieldsetCatalogazioneView.forceLayout();
		fieldsetVlcCatalogazioneForm.forceLayout();
		fieldsetCorrezioni.forceLayout();
		buttonsCorrezioni.forceLayout();
		
		containerSommario.forceLayout();
		toolBarSommario.forceLayout();
		
		tabPanel.forceLayout();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateCatalogazioneView(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCatalogazioneView(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		tabPanel.setActiveWidget(tabPanel.getWidget(0));
		stepLayoutCatalogazione.setActiveWidget(stepLayoutCatalogazione.getWidget(0));
		forceLayoutElements();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateCatalogazioneForm(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCatalogazioneForm(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		stepLayoutCatalogazione.setActiveWidget(stepLayoutCatalogazione.getWidget(1));
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateCronologia(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCronologia(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateSommario(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateSommario(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateAnteprima(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateAnteprima(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#activateCorrezioni(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCorrezioni(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController.iCatOggettoDettaglioView#getNoteCorrezioni()
	 */
	public String getNoteCorrezioni(){
		if(noteCorrezioni.getCurrentValue()!=null){
			return noteCorrezioni.getCurrentValue();
		}
		return "";
	}

	private void loadTreeSommarioData(List<TocSommarioDTO> tocs){
		treeSommarioStore.clear();
		
		chiaveSommario = 0;
		for(TocSommarioDTO toc:tocs){
			TocSommarioDTO itm = new TocSommarioDTO(chiaveSommario++, toc.getNomeToc());
			itm.setCdImmagine(toc.getCdImmagine());
			itm.setNomeImmagine(toc.getNomeImmagine());
			treeSommarioStore.add(itm);
			if(toc.getChildren()!=null && !toc.getChildren().isEmpty()){
				loadTreeSommarioChildren(toc.getChildren(),itm);
			}
		}
		treeSommario.expandAll();
	}

	private void loadTreeSommarioChildren(List<TocSommarioDTO> tocs, TocSommarioDTO father){
		for(TocSommarioDTO toc:tocs){
			TocSommarioDTO itm = new TocSommarioDTO(chiaveSommario++, toc.getNomeToc());
			itm.setCdImmagine(toc.getCdImmagine());
			itm.setNomeImmagine(toc.getNomeImmagine());
			treeSommarioStore.add(father,itm);
			if(toc.getChildren()!=null && !toc.getChildren().isEmpty()){
				loadTreeSommarioChildren(toc.getChildren(),itm);
			}
		}
	}

	/**
	 * Save tree sommario data.
	 *
	 * @param event  event
	 * @return list
	 */
	public List<TocSommarioDTO> saveTreeSommarioData(SelectEvent event) {
		treeSommario.expandAll();
		List<TocSommarioDTO> rootItems = treeSommario.getTreeStore().getRootItems();
		List<TocSommarioDTO> newSommario = new ArrayList<TocSommarioDTO>();
		for(TocSommarioDTO root:rootItems){
			TocSommarioDTO newItm = new TocSommarioDTO();
			newItm.setNomeToc(root.getNomeToc());
			newItm.setNomeImmagine(root.getNomeImmagine());
			saveTreeRecursiveSommario(newItm,root);
			newSommario.add(newItm);
		}
		return newSommario;
	}
	private void saveTreeRecursiveSommario(TocSommarioDTO newParent, TocSommarioDTO parent){
		List<TocSommarioDTO> subItems = treeSommario.getTreeStore().getChildren(parent);
		List<TocSommarioDTO> newSubSommario = new ArrayList<TocSommarioDTO>();
		for(TocSommarioDTO itm:subItems){
			TocSommarioDTO newItm = new TocSommarioDTO();
			newItm.setNomeToc(itm.getNomeToc());
			newItm.setNomeImmagine(itm.getNomeImmagine());
			saveTreeRecursiveSommario(newItm,itm);
			newSubSommario.add(newItm);
		}
		newParent.setChildren(newSubSommario);		
	}
}