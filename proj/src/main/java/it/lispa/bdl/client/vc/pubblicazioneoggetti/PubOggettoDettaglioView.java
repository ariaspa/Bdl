package it.lispa.bdl.client.vc.pubblicazioneoggetti;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.messages.PubOggettoDettaglioMsg;
import it.lispa.bdl.shared.services.PubblicazioneOggettiService;
import it.lispa.bdl.shared.services.PubblicazioneOggettiServiceAsync;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
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
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

/**
 * Class PubOggettoDettaglioView.
 */
public class PubOggettoDettaglioView extends ViewWithUiHandlers<PubOggettoDettaglioHandler> implements PubOggettoDettaglioController.iPubOggettoDettaglioView {

	private Integer chiaveSommario;

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
		@XTemplate(source = "PubOggettoDettaglioViewSommario.html")
		public SafeHtml renderItem(ImmagineDTO photo, Style style);
	}
	interface ResourcesSommario extends ClientBundle {
		@Source("PubOggettoDettaglioViewSommario.css")
		Style css();
	}
	@FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
	interface RendererAnteprima extends XTemplates {
		@XTemplate(source = "PubOggettoDettaglioViewAnteprima.html")
		public SafeHtml renderItem(ImmagineDTO photo, Style style);
	}
	interface ResourcesAnteprima extends ClientBundle {
		@Source("PubOggettoDettaglioViewAnteprima.css")
		Style css();
	}

	OggettoDTO item;

	PubblicazioneOggettiServiceAsync servizioPubblicazione = (PubblicazioneOggettiServiceAsync) GWT.create(PubblicazioneOggettiService.class);

	private final Widget widget;

	ComboDTO.ComboDTOProperties cmbProps = GWT.create(ComboDTO.ComboDTOProperties.class);
	ImmagineDTO.ImmagineDTOProperties imageProps = GWT.create(ImmagineDTO.ImmagineDTOProperties.class);

	@UiField FieldSet fieldsetPubblicazione;
	@UiField VerticalLayoutContainer fieldsetVlcPubblicazione;
	
	@UiField FieldSet fieldsetCatalogazioneView;
	@UiField VerticalLayoutContainer fieldsetVlcCatalogazioneView;

	@UiField TabPanel tabPanel;

	@UiField HBoxLayoutContainer buttonsPubblicazione;
	@UiField HBoxLayoutContainer buttonsCatalogazioneView;
	@UiField HBoxLayoutContainer buttonsAnteprima;

	@UiField TextArea noteDiniegoPubblicazione;
	
	@UiField TextButton btnAnnulla;

	@UiField InlineLabel titoloCatalogazioneView;
	@UiField InlineLabel titoloFeCatalogazioneView;
	@UiField InlineLabel titoloSupCatalogazioneView;
	@UiField InlineLabel immaginiPrevisteCatalogazioneView;
	@UiField InlineLabel tipologiaOggettoCatalogazioneView;
	@UiField InlineLabel includeAltriTitoliCatalogazioneView;
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

	private ListStore<CronologiaDTO> gridCronologiaStore;
	@UiField(provided = true) Grid<CronologiaDTO> gridCronologia;


	@UiField(provided = true) TreeGrid<TocSommarioDTO> treeSommario;
	private TreeStore<TocSommarioDTO> treeSommarioStore;

	private ListStore<ImmagineDTO> listAnteprimaStore = new ListStore<ImmagineDTO>(imageProps.cdImmagine());
	@UiField (provided = true) ListView<ImmagineDTO, ImmagineDTO> listAnteprima;

	@UiField FieldSet fieldsetCorrezioni;
	@UiField TextArea noteCorrezioni;

	PubOggettoDettaglioMsg messages;


	@UiField TextButton btnEsportaMetsCatalogazioneView;

	@UiField TextButton btnBookreaderAnteprima;
	@UiField TextButton btnPdfAnteprima;

	@UiField FieldLabel descrizioneContenutisticaCatalogazioneLblView;
	@UiField FieldLabel descrizioneFisicaCatalogazioneLblView;
	@UiField FieldLabel livelloBibliograficoCatalogazioneLblView;
	@UiField FieldLabel soggettoCatalogazioneLblView;
	@UiField FieldLabel qualificatoreAutoreCatalogazioneLblView;
	@UiField FieldLabel autoreCatalogazioneLblView;
	@UiField FieldLabel qualificatoreAutore2CatalogazioneLblView;
	@UiField FieldLabel autore2CatalogazioneLblView;
	@UiField FieldLabel editoreCatalogazioneLblView;
	@UiField FieldLabel linguaCatalogazioneLblView;
	@UiField FieldLabel tipoIdentificativoCatalogazioneLblView;
	@UiField FieldLabel identificativoCatalogazioneLblView;
	@UiField FieldLabel luogoPubblicazioneCatalogazioneLblView;
	@UiField FieldLabel qualificatoreDataCatalogazioneLblView;
	@UiField FieldLabel dataCatalogazioneLblView;
	@UiField FieldLabel noteCatalogazioneLblView;
	@UiField FieldLabel scalaCatalogazioneLblView;
	@UiField FieldLabel proiezioneCatalogazioneLblView;
	@UiField FieldLabel coordinateCatalogazioneLblView;
	@UiField FieldLabel contestoArchivisticoCatalogazioneLblView;
	@UiField FieldLabel soggettiProduttoriCatalogazioneLblView;
	@UiField FieldLabel linkCatalogoCatalogazioneLblView;
	@UiField FieldLabel tipoGraficaCatalogazioneLblView;
	@UiField FieldLabel supportoPrimarioCatalogazioneLblView;
	@UiField FieldLabel tecnicaGraficaCatalogazioneLblView;
	@UiField FieldLabel tipoArchivioCatalogazioneLblView;

	@UiField Image imageSommario;


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


	/**
	 * Interface Binder.
	 */
	public interface Binder extends UiBinder<Widget, PubOggettoDettaglioView> {
	}

	/**
	 * Istanzia un nuovo pub oggetto dettaglio view.
	 *
	 * @param eventBus  event bus
	 * @param binder  binder
	 * @param myMessages  my messages
	 * @param img  img
	 */
	@Inject
	public PubOggettoDettaglioView(final EventBus eventBus, final Binder binder, PubOggettoDettaglioMsg myMessages, Images img) {

		super();


		// 1 Testo a stampa
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

		// 5 Cartografia a stampa
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
		

		treeSommario.getSelectionModel().addSelectionHandler(new SelectionHandler<TocSommarioDTO>() {
			public void onSelection(SelectionEvent<TocSommarioDTO> event) {
				TocSommarioDTO toc = event.getSelectedItem();
				if(toc!=null && toc.getNomeImmagine()!=null){
					setImageSommario(item.getCdOggettoOriginale(),toc.getNomeImmagine());
				}	
			}			
		});
		
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

		noteCorrezioni.setReadOnly(true);
		
		tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {
	        @Override
	        public void onSelection(SelectionEvent<Widget> event) {
	        	forceLayoutElements();
	        }
	    });
	}
	
	/**
	 * Imposta image sommario.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param nomeImmagine  nome immagine
	 */
	public void setImageSommario(BigDecimal cdOggetto,String nomeImmagine){
		tabPanel.mask(messages.maskImmagineSommario());
	
		servizioPubblicazione.getImmagineReader(cdOggetto,nomeImmagine,new AsyncCallback<ImmagineDTO>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
				tabPanel.unmask();
			}
			@Override
			public void onSuccess(ImmagineDTO immagine) {
				tabPanel.unmask();
				imageSommario.setUrl(immagine.getPathReaderUri());
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
	 */
	public Widget asWidget() {
		return widget;
	}


	/**
	 * On btn annulla.
	 *
	 * @param event  event
	 */
	@UiHandler("btnAnnulla")
	public void onBtnAnnulla(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnAnnulla();
		}
	}
	
	/**
	 * On btn diniega.
	 *
	 * @param event  event
	 */
	@UiHandler("btnDiniega")
	public void onBtnDiniega(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnDiniega(noteDiniegoPubblicazione.getCurrentValue());
		}
	}
	
	/**
	 * On btn pubblica.
	 *
	 * @param event  event
	 */
	@UiHandler("btnPubblica")
	public void onBtnPubblica(SelectEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onBtnPubblica();
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
	 * Refresh data.
	 *
	 * @param item  item
	 * @param naturaOggettoOriginale  natura oggetto originale
	 */
	public void refreshData(final OggettoDTO item, final String naturaOggettoOriginale) {
		this.item = item;

		// item non può essere nullo, quindi procedo a scrivere le label della visualizzazione e successivamente il form
		fieldsetCatalogazioneView.setHeadingText(messages.fieldsetCatalogazioneView(item.getTitolo()));
		titoloCatalogazioneView.setText(item.getTitolo());
		titoloFeCatalogazioneView.setText(item.getTitoloFe());
		titoloSupCatalogazioneView.setText(item.getTitoloOggettoOriginaleSup());
		immaginiPrevisteCatalogazioneView.setText(item.getImmaginiPreviste()==null ? "" : item.getImmaginiPreviste().toString());
		tipologiaOggettoCatalogazioneView.setText(item.getNomeTipoOggetto());
		if(item.getOggettoSuperiore()){
			includeAltriTitoliCatalogazioneView.setText(BdlSharedConstants.FLAG_TRUE_HUMAN);
		}else{
			includeAltriTitoliCatalogazioneView.setText(BdlSharedConstants.FLAG_FALSE_HUMAN);				
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

		/*
		 * MOCK
		gridCronologiaStore.clear();
		gridCronologiaStore.addAll(it.lispa.bdl.client.mockservice.Mock.ValOggettoDettaglioView_refreshDataGridCronologia());
		 */

		servizioPubblicazione.getCronologia(item.getCdOggettoOriginale(),new AsyncCallback<List<CronologiaDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<CronologiaDTO> oggetti) {
				gridCronologiaStore.clear();
				gridCronologiaStore.addAll(oggetti);
			}
		});



		servizioPubblicazione.getTocSommarioOggetto(item.getCdOggettoOriginale(),new AsyncCallback<List<TocSommarioDTO>>() {
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
		listSommarioStore.addAll(it.lispa.bdl.client.mockservice.Mock.ValOggettoDettaglioView_refreshDataListAnteprima());
		listAnteprimaStore.clear();
		listAnteprimaStore.addAll(it.lispa.bdl.client.mockservice.Mock.ValOggettoDettaglioView_refreshDataListAnteprima());
		comboEditorSommario.clear();
		for(int i=0;i<400;i++){
			comboEditorSommario.add(Integer.toString(i));
		}
		 */

		servizioPubblicazione.getImmaginiOggetto(item.getCdOggettoOriginale(),new AsyncCallback<List<ImmagineDTO>>() {
			public void onFailure(Throwable caught) {
				// non faccio nulla, errore gestito a monte
			}
			@Override
			public void onSuccess(List<ImmagineDTO> oggetti) {
				listAnteprimaStore.clear();
				listAnteprimaStore.addAll(oggetti);
			}
		});

		noteCorrezioni.setValue("");


		if(descrizioneContenutisticaMap.contains(item.getCdTipoOggetto().intValueExact())){
			descrizioneContenutisticaCatalogazioneLblView.show();
		}else{
			descrizioneContenutisticaCatalogazioneLblView.hide();
		}
		if(descrizioneFisicaMap.contains(item.getCdTipoOggetto().intValueExact())){
			descrizioneFisicaCatalogazioneLblView.show();
		}else{
			descrizioneFisicaCatalogazioneLblView.hide();
		}
		if(livelloBibliograficoMap.contains(item.getCdTipoOggetto().intValueExact())){
			livelloBibliograficoCatalogazioneLblView.show();
		}else{
			livelloBibliograficoCatalogazioneLblView.hide();
		}
		if(soggettoMap.contains(item.getCdTipoOggetto().intValueExact())){
			soggettoCatalogazioneLblView.show();
		}else{
			soggettoCatalogazioneLblView.hide();
		}
		if(qualificatoreAutoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreAutoreCatalogazioneLblView.show();
		}else{
			qualificatoreAutoreCatalogazioneLblView.hide();
		}
		if(autoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			autoreCatalogazioneLblView.show();
		}else{
			autoreCatalogazioneLblView.hide();
		}
		if(qualificatoreAutore2Map.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreAutore2CatalogazioneLblView.show();
		}else{
			qualificatoreAutore2CatalogazioneLblView.hide();
		}
		if(autore2Map.contains(item.getCdTipoOggetto().intValueExact())){
			autore2CatalogazioneLblView.show();
		}else{
			autore2CatalogazioneLblView.hide();
		}
		if(editoreMap.contains(item.getCdTipoOggetto().intValueExact())){
			editoreCatalogazioneLblView.show();
		}else{
			editoreCatalogazioneLblView.hide();
		}
		if(linguaMap.contains(item.getCdTipoOggetto().intValueExact())){
			linguaCatalogazioneLblView.show();
		}else{
			linguaCatalogazioneLblView.hide();
		}
		if(tipoIdentificativoMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoIdentificativoCatalogazioneLblView.show();
		}else{
			tipoIdentificativoCatalogazioneLblView.hide();
		}
		if(identificativoMap.contains(item.getCdTipoOggetto().intValueExact())){
			identificativoCatalogazioneLblView.show();
		}else{
			identificativoCatalogazioneLblView.hide();
		}
		if(luogoPubblicazioneMap.contains(item.getCdTipoOggetto().intValueExact())){
			luogoPubblicazioneCatalogazioneLblView.show();
		}else{
			luogoPubblicazioneCatalogazioneLblView.hide(); 
		}
		if(qualificatoreDataMap.contains(item.getCdTipoOggetto().intValueExact())){
			qualificatoreDataCatalogazioneLblView.show();
		}else{
			qualificatoreDataCatalogazioneLblView.hide();
		}
		if(dataMap.contains(item.getCdTipoOggetto().intValueExact())){
			dataCatalogazioneLblView.show();
		}else{
			dataCatalogazioneLblView.hide();
		}
		if(noteMap.contains(item.getCdTipoOggetto().intValueExact())){
			noteCatalogazioneLblView.show();
		}else{
			noteCatalogazioneLblView.hide();
		}
		if(scalaMap.contains(item.getCdTipoOggetto().intValueExact())){
			scalaCatalogazioneLblView.show();
		}else{
			scalaCatalogazioneLblView.hide();
		}
		if(proiezioneMap.contains(item.getCdTipoOggetto().intValueExact())){
			proiezioneCatalogazioneLblView.show();
		}else{
			proiezioneCatalogazioneLblView.hide();
		}
		if(coordinateMap.contains(item.getCdTipoOggetto().intValueExact())){
			coordinateCatalogazioneLblView.show();
		}else{
			coordinateCatalogazioneLblView.hide();
		}
		if(contestoArchivisticoMap.contains(item.getCdTipoOggetto().intValueExact())){
			contestoArchivisticoCatalogazioneLblView.show();
		}else{
			contestoArchivisticoCatalogazioneLblView.hide();
		}
		if(soggettiProduttoriMap.contains(item.getCdTipoOggetto().intValueExact())){
			soggettiProduttoriCatalogazioneLblView.show();
		}else{
			soggettiProduttoriCatalogazioneLblView.hide();
		}
		if(linkCatalogoMap.contains(item.getCdTipoOggetto().intValueExact())){
			linkCatalogoCatalogazioneLblView.show();
		}else{
			linkCatalogoCatalogazioneLblView.hide();
		}
		if(tipoGraficaMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoGraficaCatalogazioneLblView.show();
		}else{
			tipoGraficaCatalogazioneLblView.hide();  
		}
		if(supportoPrimarioMap.contains(item.getCdTipoOggetto().intValueExact())){
			supportoPrimarioCatalogazioneLblView.show();
		}else{
			supportoPrimarioCatalogazioneLblView.hide();
		}
		if(tecnicaGraficaMap.contains(item.getCdTipoOggetto().intValueExact())){
			tecnicaGraficaCatalogazioneLblView.show();
		}else{
			tecnicaGraficaCatalogazioneLblView.hide();
		}
		if(tipoArchivioMap.contains(item.getCdTipoOggetto().intValueExact())){
			tipoArchivioCatalogazioneLblView.show();
		}else{
			tipoArchivioCatalogazioneLblView.hide();
		}

		if(item.getOggettoDigitale()){
			btnBookreaderAnteprima.enable();
			if(!naturaOggettoOriginale.equals(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO)){
				btnPdfAnteprima.enable();
			}else{
				btnPdfAnteprima.disable();
			}
		}else{
			btnBookreaderAnteprima.disable();
			btnPdfAnteprima.disable();
		}
		forceLayoutElements();
	}


	private void forceLayoutElements(){
		buttonsPubblicazione.forceLayout();
		fieldsetPubblicazione.forceLayout();
		buttonsCatalogazioneView.forceLayout();
		fieldsetCatalogazioneView.forceLayout();
		fieldsetVlcCatalogazioneView.forceLayout();
		fieldsetCatalogazioneView.forceLayout();
		buttonsAnteprima.forceLayout();
		fieldsetVlcCatalogazioneView.getScrollSupport().scrollToTop();
		fieldsetCorrezioni.forceLayout();
		
		tabPanel.forceLayout();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activatePubblicazione(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activatePubblicazione(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		tabPanel.setActiveWidget(tabPanel.getWidget(0));
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activateCatalogazioneView(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCatalogazioneView(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		tabPanel.setActiveWidget(tabPanel.getWidget(1));
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activateCronologia(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCronologia(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activateSommario(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateSommario(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activateAnteprima(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateAnteprima(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController.iPubOggettoDettaglioView#activateCorrezioni(it.lispa.bdl.shared.dto.OggettoDTO, java.lang.String)
	 */
	@Override
	public void activateCorrezioni(OggettoDTO item, String naturaOggettoOriginale) {
		this.refreshData(item, naturaOggettoOriginale);
		forceLayoutElements();
	}

	private void loadTreeSommarioData(List<TocSommarioDTO> tocs){
		treeSommarioStore.clear();
		imageSommario.setUrl("");
		
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
		
		if(!tocs.isEmpty()){
			TocSommarioDTO firstToc = tocs.get(0);
			if(firstToc.getNomeImmagine()!=null  && !firstToc.getNomeImmagine().equals("")){
				setImageSommario(item.getCdOggettoOriginale(),firstToc.getNomeImmagine());
			}
		}
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
}