
package it.lispa.bdl.client.gin;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.client.vc.ammgestenti.AmmGestEntiController;
import it.lispa.bdl.client.vc.ammgestindex.AmmGestIndexController;
import it.lispa.bdl.client.vc.ammgestutenti.AmmGestUtentiController;
import it.lispa.bdl.client.vc.amministrazione.AmministrazioneController;
import it.lispa.bdl.client.vc.ammvalidutenti.AmmValidUtentiController;
import it.lispa.bdl.client.vc.caricamentoimmagini.CaricamentoImmaginiController;
import it.lispa.bdl.client.vc.catalogazioneoggetti.CatalogazioneOggettiController;
import it.lispa.bdl.client.vc.correzioneoggetti.CorrezioneOggettiController;
import it.lispa.bdl.client.vc.dashboard.DashboardController;
import it.lispa.bdl.client.vc.home.HomeController;
import it.lispa.bdl.client.vc.identificazioneoggetti.IdentificazioneOggettiController;
import it.lispa.bdl.client.vc.monitoraggio.MonitoraggioController;
import it.lispa.bdl.client.vc.monitoraggiocritici.MonitoraggioCriticiController;
import it.lispa.bdl.client.vc.monitoraggiocrono.MonitoraggioCronoController;
import it.lispa.bdl.client.vc.monitoraggiolavorazione.MonitoraggioLavorazioneController;
import it.lispa.bdl.client.vc.pubblicazioneoggetti.PubblicazioneOggettiController;
import it.lispa.bdl.client.vc.schedulatore.SchedulatoreController;
import it.lispa.bdl.client.vc.schedulatorejobs.SchedulatoreJobsController;
import it.lispa.bdl.client.vc.schedulatoretriggers.SchedulatoreTriggersController;
import it.lispa.bdl.client.vc.validazioneoggetti.ValidazioneOggettiController;
import it.lispa.bdl.client.vc.verificaoggetti.VerificaOggettiController;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * Interface ClientGinjector.
 */
@GinModules(ClientModule.class)
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LayoutController> getLayoutController();
	
	AsyncProvider<HomeController> getHomeController();
	
	AsyncProvider<DashboardController> getDashboardController();
	
	AsyncProvider<AmministrazioneController> getAmministrazioneController();
	
	AsyncProvider<AmmGestUtentiController> getAmmGestUtentiController();
	
	AsyncProvider<AmmGestEntiController> getAmmGestEntiController();
	AsyncProvider<AmmValidUtentiController> getAmmValidUtentiController();
	AsyncProvider<AmmGestIndexController> getAmmGestIndexController();
	
	AsyncProvider<IdentificazioneOggettiController> getIdentificazioneOggettiController();
	
	
	
	AsyncProvider<VerificaOggettiController> getVerificaOggettiController();	
	AsyncProvider<CaricamentoImmaginiController> getCaricamentoImmaginiController();

	AsyncProvider<CatalogazioneOggettiController> getCatalogazioneOggettiController();
	AsyncProvider<CorrezioneOggettiController> getCorrezioneOggettiController();

	AsyncProvider<ValidazioneOggettiController> getValidazioneOggettiController();

	AsyncProvider<PubblicazioneOggettiController> getPubblicazioneOggettiController();

	
	AsyncProvider<MonitoraggioController> getMonitoraggioController();
	AsyncProvider<MonitoraggioLavorazioneController> getMonitoraggioLavorazioneController();
	AsyncProvider<MonitoraggioCriticiController> getMonitoraggioCriticiController();
	AsyncProvider<MonitoraggioCronoController> getMonitoraggioCronoController();

	AsyncProvider<SchedulatoreController> getSchedulatoreController();
	AsyncProvider<SchedulatoreTriggersController> getSchedulatoreTriggersController();
	AsyncProvider<SchedulatoreJobsController> getSchedulatoreJobsController();
	
}
