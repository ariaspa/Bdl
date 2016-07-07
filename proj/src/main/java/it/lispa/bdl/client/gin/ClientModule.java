package it.lispa.bdl.client.gin;

import it.lispa.bdl.client.images.Images;
import it.lispa.bdl.client.place.DefaultPlace;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.FooterController;
import it.lispa.bdl.client.ui.FooterView;
import it.lispa.bdl.client.ui.HeaderController;
import it.lispa.bdl.client.ui.HeaderView;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.ui.LayoutView;
import it.lispa.bdl.client.ui.LoaderController;
import it.lispa.bdl.client.ui.LoaderView;
import it.lispa.bdl.client.ui.MenuController;
import it.lispa.bdl.client.ui.MenuView;
import it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController;
import it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormController;
import it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiFormView;
import it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiView;
import it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController;
import it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormController;
import it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexFormView;
import it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexView;
import it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController;
import it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormController;
import it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiFormView;
import it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiView;
import it.lispa.bdl.client.vc.amministrazione.AmministrazioneController;
import it.lispa.bdl.client.vc.amministrazione.AmministrazioneView;
import it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController;
import it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormController;
import it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiFormView;
import it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiView;
import it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController;
import it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiFormController;
import it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiFormView;
import it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatCollezioneController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatCollezioneView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoAnteprimaImmagineController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoAnteprimaImmagineView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoDettaglioView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaCatalogoController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaCatalogoDettaglioController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaCatalogoDettaglioView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaCatalogoView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaExcelController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaExcelView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaTocController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoImportaTocView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatOggettoListaView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatProgettoView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatVuotoController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatVuotoView;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiView;
import it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiController;
import it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormController;
import it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiFormView;
import it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiView;
import it.lispa.bdl.client.vc.dashboard.DashboardController;
import it.lispa.bdl.client.vc.dashboard.DashboardView;
import it.lispa.bdl.client.vc.home.HomeController;
import it.lispa.bdl.client.vc.home.HomeRegistrazioneFormController;
import it.lispa.bdl.client.vc.home.HomeRegistrazioneFormView;
import it.lispa.bdl.client.vc.home.HomeView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOCollezioneView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoDettaglioView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoDettaglioController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoDettaglioView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaExcelView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoListaView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOProgettoView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOVuotoController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IOVuotoView;
import it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiView;
import it.lispa.bdl.client.vc.monitoraggio.MonitoraggioController;
import it.lispa.bdl.client.vc.monitoraggio.MonitoraggioView;
import it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiController;
import it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormController;
import it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiFormView;
import it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiView;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoDettaglioController;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoDettaglioView;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaController;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonCronoListaView;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonitoraggioCronoController;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonitoraggioCronoView;
import it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController;
import it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneView;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoAnteprimaImmagineController;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoAnteprimaImmagineView;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioController;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoDettaglioView;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoListaController;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubOggettoListaView;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubblicazioneOggettiController;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubblicazioneOggettiView;
import it.lispa.bdl.client.vc.schedulatore.SchedulatoreController;
import it.lispa.bdl.client.vc.schedulatore.SchedulatoreView;
import it.lispa.bdl.client.vc.schedulatorejobs.SchedulatoreJobsController;
import it.lispa.bdl.client.vc.schedulatorejobs.SchedulatoreJobsFormController;
import it.lispa.bdl.client.vc.schedulatorejobs.SchedulatoreJobsFormView;
import it.lispa.bdl.client.vc.schedulatorejobs.SchedulatoreJobsView;
import it.lispa.bdl.client.vc.schedulatoretriggers.SchedulatoreTriggersController;
import it.lispa.bdl.client.vc.schedulatoretriggers.SchedulatoreTriggersView;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoAnteprimaImmagineController;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoAnteprimaImmagineView;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoDettaglioController;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoDettaglioView;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoListaController;
import it.lispa.bdl.client.vc.validazioneoggetti.ValOggettoListaView;
import it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController;
import it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiView;
import it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiController;
import it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiFormController;
import it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiFormView;
import it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiView;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;


/**
 * Class ClientModule.
 */
public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(PlaceManager.class));
		bind(Images.class).in(Singleton.class);

		bindPresenter(LayoutController.class, LayoutController.iLayoutView.class,LayoutView.class, LayoutController.LayoutProxy.class);
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.Home);

		bindPresenterWidget(HeaderController.class, HeaderController.iHeaderView.class, HeaderView.class);
		bindPresenterWidget(FooterController.class, FooterController.iFooterView.class, FooterView.class);
		bindPresenterWidget(MenuController.class, MenuController.iMenuView.class,MenuView.class);
		bindPresenterWidget(LoaderController.class, LoaderController.iLoaderView.class,LoaderView.class);
		
		bindPresenter(HomeController.class, HomeController.iHomeView.class, HomeView.class, HomeController.iHomeProxy.class);
		bindPresenterWidget(HomeRegistrazioneFormController.class,HomeRegistrazioneFormController.iHomeRegistrazioneFormView.class, HomeRegistrazioneFormView.class);
		
		/* Internal pages */
		bindPresenter(DashboardController.class, DashboardController.iDashboardView.class, DashboardView.class, DashboardController.iDashboardProxy.class);

		
		bindPresenter(AmministrazioneController.class, AmministrazioneController.iAmministrazioneView.class, AmministrazioneView.class, AmministrazioneController.iAmministrazioneProxy.class);

		bindPresenter(AmmGestUtentiController.class, AmmGestUtentiController.iAmmGestUtentiView.class, AmmGestUtentiView.class, AmmGestUtentiController.iAmmGestUtentiProxy.class);
		bindPresenterWidget(AmmGestUtentiFormController.class,AmmGestUtentiFormController.iAmmGestUtentiFormView.class, AmmGestUtentiFormView.class);

		bindPresenter(AmmGestEntiController.class, AmmGestEntiController.iAmmGestEntiView.class, AmmGestEntiView.class, AmmGestEntiController.iAmmGestEntiProxy.class);
		bindPresenterWidget(AmmGestEntiFormController.class,AmmGestEntiFormController.iAmmGestEntiFormView.class, AmmGestEntiFormView.class);
		
		
		bindPresenter(AmmValidUtentiController.class,AmmValidUtentiController.iAmmValidUtentiView.class, AmmValidUtentiView.class, AmmValidUtentiController.iAmmValidUtentiProxy.class);
		bindPresenterWidget(AmmValidUtentiFormController.class,AmmValidUtentiFormController.iAmmValidUtentiFormView.class, AmmValidUtentiFormView.class);
		
		bindPresenter(AmmGestIndexController.class, AmmGestIndexController.iAmmGestIndexView.class, AmmGestIndexView.class, AmmGestIndexController.iAmmGestIndexProxy.class);
		bindPresenterWidget(AmmGestIndexFormController.class,AmmGestIndexFormController.iAmmGestIndexFormView.class, AmmGestIndexFormView.class);
		

		bindPresenter(IdentificazioneOggettiController.class, IdentificazioneOggettiController.iIdentificazioneOggettiView.class, IdentificazioneOggettiView.class, IdentificazioneOggettiController.iIdentificazioneOggettiProxy.class);
		bindPresenterWidget(IOVuotoController.class, IOVuotoController.iIOVuotoView.class, IOVuotoView.class);
		bindPresenterWidget(IOProgettoController.class, IOProgettoController.iIOProgettoView.class, IOProgettoView.class);
		bindPresenterWidget(IOCollezioneController.class, IOCollezioneController.iIOCollezioneView.class, IOCollezioneView.class);
		bindPresenterWidget(IOOggettoListaController.class, IOOggettoListaController.iIOOggettoListaView.class, IOOggettoListaView.class);
		bindPresenterWidget(IOOggettoDettaglioController.class, IOOggettoDettaglioController.iIOOggettoDettaglioView.class, IOOggettoDettaglioView.class);
		bindPresenterWidget(IOOggettoImportaExcelController.class,IOOggettoImportaExcelController.iIOOggettoImportaExcelView.class, IOOggettoImportaExcelView.class);
		bindPresenterWidget(IOOggettoImportaCatalogoController.class,IOOggettoImportaCatalogoController.iIOOggettoImportaCatalogoView.class, IOOggettoImportaCatalogoView.class);
		bindPresenterWidget(IOOggettoImportaCatalogoDettaglioController.class,IOOggettoImportaCatalogoDettaglioController.iIOOggettoImportaCatalogoDettaglioView.class, IOOggettoImportaCatalogoDettaglioView.class);
		
		

		bindPresenter(VerificaOggettiController.class,VerificaOggettiController.iVerificaOggettiView.class, VerificaOggettiView.class, VerificaOggettiController.iVerificaOggettiProxy.class);
		bindPresenterWidget(VerificaOggettiFormController.class,VerificaOggettiFormController.iVerificaOggettiFormView.class,VerificaOggettiFormView.class);
		
		bindPresenter(CaricamentoImmaginiController.class,CaricamentoImmaginiController.iCaricamentoImmaginiView.class, CaricamentoImmaginiView.class, CaricamentoImmaginiController.iCaricamentoImmaginiProxy.class);
		bindPresenterWidget(CaricamentoImmaginiFormController.class,CaricamentoImmaginiFormController.iCaricamentoImmaginiFormView.class,CaricamentoImmaginiFormView.class);
		

		bindPresenter(CatalogazioneOggettiController.class, CatalogazioneOggettiController.iCatalogazioneOggettiView.class, CatalogazioneOggettiView.class, CatalogazioneOggettiController.iCatalogazioneOggettiProxy.class);
		bindPresenterWidget(CatVuotoController.class, CatVuotoController.iCatVuotoView.class, CatVuotoView.class);
		bindPresenterWidget(CatProgettoController.class, CatProgettoController.iCatProgettoView.class, CatProgettoView.class);
		bindPresenterWidget(CatCollezioneController.class, CatCollezioneController.iCatCollezioneView.class, CatCollezioneView.class);
		bindPresenterWidget(CatOggettoListaController.class, CatOggettoListaController.iCatOggettoListaView.class, CatOggettoListaView.class);
		bindPresenterWidget(CatOggettoDettaglioController.class, CatOggettoDettaglioController.iCatOggettoDettaglioView.class, CatOggettoDettaglioView.class);
		bindPresenterWidget(CatOggettoAnteprimaImmagineController.class, CatOggettoAnteprimaImmagineController.iCatOggettoAnteprimaImmagineView.class, CatOggettoAnteprimaImmagineView.class);
		bindPresenterWidget(CatOggettoImportaExcelController.class,CatOggettoImportaExcelController.iCatOggettoImportaExcelView.class, CatOggettoImportaExcelView.class);
		bindPresenterWidget(CatOggettoImportaTocController.class,CatOggettoImportaTocController.iCatOggettoImportaTocView.class, CatOggettoImportaTocView.class);
		bindPresenterWidget(CatOggettoImportaCatalogoController.class,CatOggettoImportaCatalogoController.iCatOggettoImportaCatalogoView.class, CatOggettoImportaCatalogoView.class);
		bindPresenterWidget(CatOggettoImportaCatalogoDettaglioController.class,CatOggettoImportaCatalogoDettaglioController.iCatOggettoImportaCatalogoDettaglioView.class, CatOggettoImportaCatalogoDettaglioView.class);
		
		
		bindPresenter(CorrezioneOggettiController.class,CorrezioneOggettiController.iCorrezioneOggettiView.class, CorrezioneOggettiView.class, CorrezioneOggettiController.iCorrezioneOggettiProxy.class);
		bindPresenterWidget(CorrezioneOggettiFormController.class,CorrezioneOggettiFormController.iCorrezioneOggettiFormView.class,CorrezioneOggettiFormView.class);
		
		bindPresenter(ValidazioneOggettiController.class, ValidazioneOggettiController.iValidazioneOggettiView.class, ValidazioneOggettiView.class, ValidazioneOggettiController.iValidazioneOggettiProxy.class);
		bindPresenterWidget(ValOggettoListaController.class, ValOggettoListaController.iValOggettoListaView.class, ValOggettoListaView.class);
		bindPresenterWidget(ValOggettoDettaglioController.class, ValOggettoDettaglioController.iValOggettoDettaglioView.class, ValOggettoDettaglioView.class);
		bindPresenterWidget(ValOggettoAnteprimaImmagineController.class, ValOggettoAnteprimaImmagineController.iValOggettoAnteprimaImmagineView.class, ValOggettoAnteprimaImmagineView.class);
	
		
		bindPresenter(PubblicazioneOggettiController.class, PubblicazioneOggettiController.iPubblicazioneOggettiView.class, PubblicazioneOggettiView.class, PubblicazioneOggettiController.iPubblicazioneOggettiProxy.class);
		bindPresenterWidget(PubOggettoListaController.class, PubOggettoListaController.iPubOggettoListaView.class, PubOggettoListaView.class);
		bindPresenterWidget(PubOggettoDettaglioController.class, PubOggettoDettaglioController.iPubOggettoDettaglioView.class, PubOggettoDettaglioView.class);
		bindPresenterWidget(PubOggettoAnteprimaImmagineController.class, PubOggettoAnteprimaImmagineController.iPubOggettoAnteprimaImmagineView.class, PubOggettoAnteprimaImmagineView.class);
	
		
		bindPresenter(MonitoraggioController.class, MonitoraggioController.iMonitoraggioView.class, MonitoraggioView.class, MonitoraggioController.iMonitoraggioProxy.class);
		
		bindPresenter(MonitoraggioLavorazioneController.class, MonitoraggioLavorazioneController.iMonitoraggioLavorazioneView.class, MonitoraggioLavorazioneView.class, MonitoraggioLavorazioneController.iMonitoraggioLavorazioneProxy.class);
		
		bindPresenter(MonitoraggioCriticiController.class, MonitoraggioCriticiController.iMonitoraggioCriticiView.class, MonitoraggioCriticiView.class, MonitoraggioCriticiController.iMonitoraggioCriticiProxy.class);
		bindPresenterWidget(MonitoraggioCriticiFormController.class,MonitoraggioCriticiFormController.iMonitoraggioCriticiFormView.class,MonitoraggioCriticiFormView.class);
		
		bindPresenter(MonitoraggioCronoController.class, MonitoraggioCronoController.iMonitoraggioCronoView.class, MonitoraggioCronoView.class, MonitoraggioCronoController.iMonitoraggioCronoProxy.class);
		bindPresenterWidget(MonCronoListaController.class, MonCronoListaController.iMonCronoListaView.class, MonCronoListaView.class);
		bindPresenterWidget(MonCronoDettaglioController.class, MonCronoDettaglioController.iMonCronoDettaglioView.class, MonCronoDettaglioView.class);
		
		bindPresenter(SchedulatoreController.class, SchedulatoreController.iSchedulatoreView.class, SchedulatoreView.class, SchedulatoreController.iSchedulatoreProxy.class);
		bindPresenter(SchedulatoreTriggersController.class, SchedulatoreTriggersController.iSchedulatoreTriggersView.class, SchedulatoreTriggersView.class, SchedulatoreTriggersController.iSchedulatoreTriggersProxy.class);
		bindPresenter(SchedulatoreJobsController.class, SchedulatoreJobsController.iSchedulatoreJobsView.class, SchedulatoreJobsView.class, SchedulatoreJobsController.iSchedulatoreJobsProxy.class);
		bindPresenterWidget(SchedulatoreJobsFormController.class,SchedulatoreJobsFormController.iSchedulatoreJobsFormView.class, SchedulatoreJobsFormView.class);
		
		
	}

}
