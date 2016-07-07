package it.lispa.bdl.client.mockservice;

import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.MenuItemDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * Class Mock.
 */
public class Mock implements Serializable {
	
	private static final long serialVersionUID = -3389527169231391627L;
	
	/**
	 * Istanzia un nuovo mock.
	 */
	public Mock(){
		
	}
	
	/**
	 * Cat oggetto dettaglio view_refresh data list anteprima.
	 *
	 * @return list
	 */
	public static List<ImmagineDTO> CatOggettoDettaglioView_refreshDataListAnteprima(){

		List<ImmagineDTO> items = new ArrayList<ImmagineDTO>();
		items.add(new ImmagineDTO(new BigDecimal(1), new BigDecimal(22),"0001", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=1", "http://placehold.it/850x850?text=1", "http://placehold.it/750x750?text=1","","",""));
		items.add(new ImmagineDTO(new BigDecimal(2), new BigDecimal(22),"0002", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=2", "http://placehold.it/850x850?text=2", "http://placehold.it/750x750?text=2","","",""));
		items.add(new ImmagineDTO(new BigDecimal(3), new BigDecimal(22),"0003", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=3", "http://placehold.it/850x850?text=3", "http://placehold.it/750x750?text=3","","",""));
		items.add(new ImmagineDTO(new BigDecimal(4), new BigDecimal(22),"0004", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=4", "http://placehold.it/850x850?text=4", "http://placehold.it/750x750?text=4","","",""));
		items.add(new ImmagineDTO(new BigDecimal(5), new BigDecimal(22),"0005", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=5", "http://placehold.it/850x850?text=5", "http://placehold.it/750x750?text=5","","",""));
		items.add(new ImmagineDTO(new BigDecimal(6), new BigDecimal(22),"0006", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=6", "http://placehold.it/850x850?text=6", "http://placehold.it/750x750?text=6","","",""));
		items.add(new ImmagineDTO(new BigDecimal(7), new BigDecimal(22),"0007", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=7", "http://placehold.it/850x850?text=7", "http://placehold.it/750x750?text=7","","",""));
		items.add(new ImmagineDTO(new BigDecimal(8), new BigDecimal(22),"0008", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=8", "http://placehold.it/850x850?text=8", "http://placehold.it/750x750?text=8","","",""));
		items.add(new ImmagineDTO(new BigDecimal(9), new BigDecimal(22),"0009", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=9", "http://placehold.it/850x850?text=9", "http://placehold.it/750x750?text=9","","",""));
		items.add(new ImmagineDTO(new BigDecimal(10), new BigDecimal(22),"0010", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=10", "http://placehold.it/850x850?text=10", "http://placehold.it/750x750?text=10","","",""));
		items.add(new ImmagineDTO(new BigDecimal(11), new BigDecimal(22),"0011", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=11", "http://placehold.it/850x850?text=11", "http://placehold.it/750x750?text=11","","",""));
		items.add(new ImmagineDTO(new BigDecimal(12), new BigDecimal(22),"0012", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=12", "http://placehold.it/850x850?text=12", "http://placehold.it/750x750?text=12","","",""));
		items.add(new ImmagineDTO(new BigDecimal(13), new BigDecimal(22),"0013", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=13", "http://placehold.it/850x850?text=13", "http://placehold.it/750x750?text=13","","",""));
		items.add(new ImmagineDTO(new BigDecimal(14), new BigDecimal(22),"0014", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=14", "http://placehold.it/850x850?text=14", "http://placehold.it/750x750?text=14","","",""));
		items.add(new ImmagineDTO(new BigDecimal(15), new BigDecimal(22),"0015", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=15", "http://placehold.it/850x850?text=15", "http://placehold.it/750x750?text=15","","",""));
		items.add(new ImmagineDTO(new BigDecimal(16), new BigDecimal(22),"0016", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=16", "http://placehold.it/850x850?text=16", "http://placehold.it/750x750?text=16","","",""));
		items.add(new ImmagineDTO(new BigDecimal(17), new BigDecimal(22),"0017", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=17", "http://placehold.it/850x850?text=17", "http://placehold.it/750x750?text=17","","",""));
		items.add(new ImmagineDTO(new BigDecimal(18), new BigDecimal(22),"0018", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=18", "http://placehold.it/850x850?text=18", "http://placehold.it/750x750?text=18","","",""));
		items.add(new ImmagineDTO(new BigDecimal(19), new BigDecimal(22),"0019", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=19", "http://placehold.it/850x850?text=19", "http://placehold.it/750x750?text=19","","",""));
		items.add(new ImmagineDTO(new BigDecimal(20), new BigDecimal(22),"0020", 150, 150, 400, 400, 350, 350, true, "http://placehold.it/150x150?text=20", "http://placehold.it/850x850?text=20", "http://placehold.it/750x750?text=20","","",""));
		return items;
	}
	
	/**
	 * Cat oggetto dettaglio view_refresh data grid cronologia.
	 *
	 * @return list
	 */
	public static List<CronologiaDTO> CatOggettoDettaglioView_refreshDataGridCronologia(){

		List<CronologiaDTO> items = new ArrayList<CronologiaDTO>();
		items.add(new CronologiaDTO(new BigDecimal(1),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(2),new Date(), "Suervisiore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(3),new Date(), "Amministratore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(4),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(5),new Date(), "Digitalizzatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(6),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(7),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(8),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(9),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(10),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(11),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(12),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(13),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		items.add(new CronologiaDTO(new BigDecimal(14),new Date(), "Catalogatore","CPRSMN76P01F952J","Capra Simone","Inserito",new BigDecimal(1)));
		return items;
	}
	
	/**
	 * Cat oggetto lista controller_on l visualizza.
	 *
	 * @return oggetto dto
	 */
	public static OggettoDTO CatOggettoListaController_onLVisualizza(){
		OggettoDTO oggetto = new OggettoDTO(
				new BigDecimal(12), 
				new BigDecimal(1), "Testo a stampa",
				new BigDecimal(2), "Il signore degli anelli", 
				new BigDecimal(33), "Il mio progetto", 
				new BigDecimal(12), "La mia collezione", 
				"Il tutolodi questo", false, new BigDecimal(12),
				false, false, "", false,
				"", it.lispa.bdl.shared.utils.BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO, "", new BigDecimal(122),
				new BigDecimal(12),"me",new java.util.Date(), "", new java.util.Date());
		return oggetto;
	}
	
	/**
	 * Catalogazione oggetti controller_change content proj.
	 *
	 * @return progetto dto
	 */
	public static ProgettoDTO CatalogazioneOggettiController_changeContentProj(){
		ProgettoDTO proj=new ProgettoDTO(new BigDecimal(23),"Il progetto di me","Il titolo breve","La descrizione IT","la descrizione EN",new java.util.Date(), null,it.lispa.bdl.shared.utils.BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO_HUMAN);
		return proj;
	}
	
	/**
	 * Catalogazione oggetti controller_change content coll.
	 *
	 * @return collezione dto
	 */
	public static CollezioneDTO CatalogazioneOggettiController_changeContentColl(){
		CollezioneDTO coll=new CollezioneDTO(new BigDecimal(31) , new BigDecimal(23),
				"Il progetto di me", "La collezione di me", "La descrizione di me in italiano",
				"La descrizione di me in inglese","diritti","ambitodisciplinare",
				"Copertura geografica", "PERIODONE", "1209 forse 12010",
				"1390 forse 1391");
		return coll;
	}
	
	/**
	 * Catalogazione oggetti view_load tree data.
	 *
	 * @return list
	 */
	public static List<SubMenuItemDTO> CatalogazioneOggettiView_loadTreeData(){
		return loadTreeData();
	}
	
	private static PagingLoadResult<VOggettoDTO> loadViewGrid(FilterPagingLoadConfig config) {
		List<VOggettoDTO> items = new ArrayList<VOggettoDTO>();

		items.add(
			new VOggettoDTO(
				new BigDecimal(1),
				"PAPERONI s.p.a.",
				new BigDecimal(3),
				"Biblioteca Civica U. Pozzoli",
				new BigDecimal(4),
				"Progetto Lecco 1",
				"incorso",
				new BigDecimal(8),
				"Collezione Lecco 1.3",
				new BigDecimal(43),
				"Paradiso",
				"Paradiso",
				(String)null,
				(BigDecimal)null,
				'F',
				(BigDecimal)null,
				new BigDecimal(1),
				"Testo a stampa",
				'F',
				'F',
				(String)null,
				'F',
				(String)null,
				"inserito",
				(String)null,
				(String)null,
				new BigDecimal(5),
				(BigDecimal)null,
				(String) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(String) null,
				(String) null,
				(String) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(String) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(String) null,
				(String) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(BigDecimal) null,
				(String) null,
				(String) null,
				(String) null,
				(String) null,
				'F',
				(String)null,
				(Date)null,
				(BigDecimal)null,
				'F',
				(BigDecimal)null,
				'F',
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null				
			)
		);
		items.add(
				new VOggettoDTO(
					new BigDecimal(2),
					"PAPERONI s.p.a.",
					new BigDecimal(3),
					"Biblioteca Civica U. Pozzoli",
					new BigDecimal(4),
					"Progetto Lecco 1",
					"incorso",
					new BigDecimal(8),
					"Collezione Lecco 1.3",
					new BigDecimal(44),
					"Inferno",
					"Inferno",
					(String)null,
					(BigDecimal)null,
					'F',
					(BigDecimal)null,
					new BigDecimal(1),
					"Testo a stampa",
					'F',
					'F',
					(String)null,
					'F',
					(String)null,
					"inserito",
					(String)null,
					(String)null,
					new BigDecimal(5),
					(BigDecimal)null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					'F',
					(String)null,
					(Date)null,
					(BigDecimal)null,
					'F',
					(BigDecimal)null,
					'F',
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null
				)
			);
		items.add(
				new VOggettoDTO(
					new BigDecimal(3),
					"PAPERONI s.p.a.",
					new BigDecimal(3),
					"Biblioteca Civica U. Pozzoli",
					new BigDecimal(4),
					"Progetto Lecco 1",
					"incorso",
					new BigDecimal(8),
					"Collezione Lecco 1.3",
					new BigDecimal(45),
					"Purgatorio",
					"Purgatorio",
					(String)null,
					(BigDecimal)null,
					'F',
					(BigDecimal)null,
					new BigDecimal(1),
					"Testo a stampa",
					'F',
					'F',
					(String)null,
					'T',
					"Sti errori di log anomalia immagini\nSti errori di log anomalia immaginiSti errori di log anomalia immagini\n Sti errori di log anomalia immagini\n Sti errori di log anomalia immaginiSti errori di log anomalia immaginiSti errori di log anomalia immaginiSti errori di log anomalia immagini",
					"inserito",
					(String)null,
					(String)null,
					new BigDecimal(5),
					(BigDecimal)null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(BigDecimal) null,
					(String) null,
					(String) null,
					(String) null,
					(String) null,
					'F',
					(String)null,
					(Date)null,
					(BigDecimal)null,
					'F',
					(BigDecimal)null,
					'F',
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null
				)
			);
		List<VOggettoDTO> sublist = new ArrayList<VOggettoDTO>();  
		int start = config.getOffset();  
		int limit = items.size();  
		if (config.getLimit()>0) {  
			limit = Math.min(start + config.getLimit(), limit);  
		}  
		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(items.get(i));       
		}         
		return new PagingLoadResultBean<VOggettoDTO> (sublist, items.size(), config.getOffset());  
	}
	
	/**
	 * Identificazione oggetto lista view grid.
	 *
	 * @param config  config
	 * @return paging load result
	 */
	public PagingLoadResult<VOggettoDTO> IdentificazioneOggettoListaViewGrid(FilterPagingLoadConfig config) {
		return loadViewGrid(config);
	}
	
	/**
	 * Cat oggetto lista view grid.
	 *
	 * @param config  config
	 * @return paging load result
	 */
	public static PagingLoadResult<VOggettoDTO> CatOggettoListaViewGrid(FilterPagingLoadConfig config) {
		return loadViewGrid(config);
	}
	
	/**
	 * Menu controller_on bind.
	 *
	 * @return list
	 */
	public static List<MenuItemDTO> MenuController_onBind(){

		List<MenuItemDTO> funzioniAccessibili = new java.util.ArrayList<MenuItemDTO>();

		funzioniAccessibili.add(new MenuItemDTO(NameTokens.Home,"Homepage"));
		
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.Dashboard,"Dashboard"));

		MenuItemDTO amm = new MenuItemDTO(NameTokens.Amministrazione,"Amministrazione");
		amm.addChild(new MenuItemDTO(NameTokens.AmmGestUtenti,"Gestione Utenti"));
		amm.addChild(new MenuItemDTO(NameTokens.AmmGestEnti,"Gestione Enti"));
		amm.addChild(new MenuItemDTO(NameTokens.AmmValidUtenti,"Validazione Utenti"));
		amm.addChild(new MenuItemDTO(NameTokens.AmmGestIndex,"Gestione Indice Generale"));
		funzioniAccessibili.add(amm);
		
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.IdentificazioneOggetti,"Identificazione Oggetti"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.VerificaOggetti,"Verifica Oggetti"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.CaricamentoImmagini,"Caricamento Immagini"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.CatalogazioneOggetti,"Catalogazione"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.CorrezioneOggetti,"Gestione Correzioni"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.ValidazioneOggetti,"Validazione"));
		funzioniAccessibili.add(new MenuItemDTO(NameTokens.PubblicazioneOggetti,"Pubblicazione"));
		
		MenuItemDTO monit = new MenuItemDTO(NameTokens.Monitoraggio,"Monitoraggio");
		monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioLavorazione,"Stato di lavorazione"));
		monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCritici,"Lista oggetti con criticità"));
		monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCrono,"Cronologica per oggetto"));
		funzioniAccessibili.add(monit);
		return funzioniAccessibili;
	}

	private static List<SubMenuItemDTO> loadTreeData() {
		List<SubMenuItemDTO> items = new ArrayList<SubMenuItemDTO>();

		SubMenuItemDTO progetto11 = new SubMenuItemDTO(new BigDecimal(1), "Progetto 1.1", SubMenuItemDTO.TIPO_PROGETTO);
		SubMenuItemDTO progetto12 = new SubMenuItemDTO(new BigDecimal(2), "Progetto 1.2", SubMenuItemDTO.TIPO_PROGETTO);
		items.add(progetto11);
		items.add(progetto12);
		

		SubMenuItemDTO collezione111 = new SubMenuItemDTO(new BigDecimal(1), "Collezione 1.1.1", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione112 = new SubMenuItemDTO(new BigDecimal(2), "Collezione 1.1.2", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione113 = new SubMenuItemDTO(new BigDecimal(3), "Collezione 1.1.3", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione114 = new SubMenuItemDTO(new BigDecimal(4), "Collezione 1.1.4", SubMenuItemDTO.TIPO_COLLEZIONE);
		progetto11.addChild(collezione111);
		progetto11.addChild(collezione112);
		progetto11.addChild(collezione113);
		progetto11.addChild(collezione114);

		SubMenuItemDTO collezione121 = new SubMenuItemDTO(new BigDecimal(5), "Collezione 1.2.1", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione122 = new SubMenuItemDTO(new BigDecimal(6), "Collezione 1.2.2", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione123 = new SubMenuItemDTO(new BigDecimal(7), "Collezione 1.2.3", SubMenuItemDTO.TIPO_COLLEZIONE);
		SubMenuItemDTO collezione124 = new SubMenuItemDTO(new BigDecimal(8), "Collezione 1.2.4", SubMenuItemDTO.TIPO_COLLEZIONE);
		progetto12.addChild(collezione121);
		progetto12.addChild(collezione122);
		progetto12.addChild(collezione123);
		progetto12.addChild(collezione124);

		
		SubMenuItemDTO oggetto1111 = new SubMenuItemDTO(new BigDecimal(9), "3 Oggetti", SubMenuItemDTO.TIPO_COLLOGGETTI);
		SubMenuItemDTO oggetto1112 = new SubMenuItemDTO(new BigDecimal(10), "33 Oggetti", SubMenuItemDTO.TIPO_COLLOGGETTI);
		collezione111.addChild(oggetto1111);
		collezione111.addChild(oggetto1112);
		
		return items;
	}
	
	/**
	 * Identificazione oggetti view_load tree data.
	 *
	 * @return list
	 */
	public static List<SubMenuItemDTO> IdentificazioneOggettiView_loadTreeData(){
		return loadTreeData();
	}
	
	/**
	 * IO oggetto importa catalogo view_get data for grid.
	 *
	 * @return list
	 */
	public static List<UnimarcDTO> IOOggettoImportaCatalogoView_getDataForGrid(){
		
		List<UnimarcDTO> items = new ArrayList<UnimarcDTO>();
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\ANA\\0485152", // id
						"The lord of the rings / by J. R. R. Tolkien", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(){
							private static final long serialVersionUID = -916860912447191709L;
						{
							add("XXV, 1178 p. ; 20 cm");
						}}, // descrizionifisiche 
						"Tolkien, J. R. R.", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(){
							private static final long serialVersionUID = 6552903627954339744L;
						{
							add("HarperCollins");
						}}, // editori
						"ger", // lingua 
						new ArrayList<String>(){
							private static final long serialVersionUID = 4747340950791275536L;
						{
							add("9780261103252");
						}}, // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(){
							private static final long serialVersionUID = -6509265384010071397L;
						{
							add("London");
						}}, // luoghiPubblicazione 
						new ArrayList<String>(){
							private static final long serialVersionUID = 4094464148561386908L;
						{
							add("2007");
						}}, // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BVE\\0110502", // id
						"1: The fellowship of the ring : being the first part of The lord of the ring / J R R Tolkien ; illustrated by Alan Lee", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(){
							private static final long serialVersionUID = 1232407869266099026L;
						{
							add("427 p., \19! c. di tav. : ill. ; 25 cm.");
						}}, // descrizionifisiche 
						"Tolkien, J. R. R.", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(){
							private static final long serialVersionUID = -6415405447358982788L;
						{
							add("HarperCollins");
						}}, // editori
						"ger", // lingua 
						new ArrayList<String>(){
							private static final long serialVersionUID = 8129723255981746225L;
						{
							add(" 0261103385");
						}}, // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(){
							private static final long serialVersionUID = 3889056503617000039L;
						{
							add("London");
						}}, // luoghiPubblicazione 
						new ArrayList<String>(){
							private static final long serialVersionUID = 2993396327894484579L;
						{
							add("1996");
						}}, // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"IT\\ICCU\\ANA\\0485152", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BVE\\0110644", // id
						"3: The return of the king : being the third part of The lord of the ring / J R R Tolkien ; illustrated by Alan Lee", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(){
							private static final long serialVersionUID = -7633675466506416972L;
						{
							add("427 p., \19! c. di tav. : ill. ; 25 cm.");
						}}, // descrizionifisiche 
						"Tolkien, J. R. R.", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(){
							private static final long serialVersionUID = -6775834558032425879L;
						{
							add("HarperCollins");
						}}, // editori
						"ger", // lingua 
						new ArrayList<String>(){
							private static final long serialVersionUID = -1581189473238412524L;
						{
							add(" 0261103385");
						}}, // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(){
							private static final long serialVersionUID = -8365433307015189491L;
						{
							add("London");
						}}, // luoghiPubblicazione 
						new ArrayList<String>(){
							private static final long serialVersionUID = -3187377453761122770L;
						{
							add("1996");
						}}, // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"IT\\ICCU\\ANA\\0485152", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BVE\\0110630", // id
						"2: The two towers : being the second part of The lord of the ring / J R R Tolkien ; illustrated by Alan Lee", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(){
							private static final long serialVersionUID = 8353709224871878610L;
						{
							add("427 p., \19! c. di tav. : ill. ; 25 cm.");
						}}, // descrizionifisiche 
						"Tolkien, J. R. R.", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(){
							private static final long serialVersionUID = -6028932647165410070L;
						{
							add("HarperCollins");
						}}, // editori
						"ger", // lingua 
						new ArrayList<String>(){
							private static final long serialVersionUID = -6408591201630652709L;
						{
							add(" 0261103385");
						}}, // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(){
							private static final long serialVersionUID = -952115354434544188L;
						{
							add("London");
						}}, // luoghiPubblicazione 
						new ArrayList<String>(){
							private static final long serialVersionUID = -6068843482551170265L;
						{
							add("1996");
						}}, // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"IT\\ICCU\\ANA\\0485152", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243681", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243682", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243683", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243684", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243685", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243686", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243687", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243688", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243689", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		items.add(
				new UnimarcDTO(
						"IT\\ICCU\\BAS\\0243612", // id
						"100 colpi di spazzola prima di andare a dormire / Melissa P", // titolo
						"d", // tipoOggetto
						new ArrayList<String>(), // descrizionifisiche 
						"", // autore
						new ArrayList<String>(), // autoriSecondari1
						new ArrayList<String>(), // autoriSecondari2
						new ArrayList<String>(), // editori
						"", // lingua 
						new ArrayList<String>(), // iSBN
						new ArrayList<String>(), // iSSN
						new ArrayList<String>(), // luoghiPubblicazione
						new ArrayList<String>(), // datePubblicazione
						new ArrayList<String>(), // titoliSuperiori
						new ArrayList<String>(), // titoliInferiori
						"", // path
						""
						)
				);
		return items;
	}
}
