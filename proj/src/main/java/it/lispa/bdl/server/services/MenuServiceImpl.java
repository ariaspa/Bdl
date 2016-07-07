package it.lispa.bdl.server.services;

import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.shared.dto.MenuItemDTO;
import it.lispa.bdl.shared.services.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class MenuServiceImpl.
 */
@Service("MenuService")
@Repository
@Transactional
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
	
//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;
	
	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.MenuMsg", new UTF8Control());

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.MenuService#findFunzioniAccessibili()
	 */
	@Transactional(readOnly=true)
	public List<MenuItemDTO> findFunzioniAccessibili() {
		
		BdlUtente  utente = this.getActiveUtente();
		
		List<MenuItemDTO> funzioniAccessibili = new ArrayList<MenuItemDTO>();
		
		Boolean debug = Boolean.FALSE;
		debug = Boolean.valueOf(properties.getDebugMainmenu());
		
		/*
		 * 1	Amministratore
		 * 2	Supervisore
		 * 3	Catalogatore
		 * 4	Digitalizzatore
		 */
		
		if(debug){
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.Home,serverMessages.getString("Home")));
			
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.Dashboard,serverMessages.getString("Dashboard")));

			MenuItemDTO amm = new MenuItemDTO(NameTokens.Amministrazione,serverMessages.getString("Amministrazione"));
			amm.addChild(new MenuItemDTO(NameTokens.AmmGestUtenti,serverMessages.getString("AmmGestUtenti")));
			amm.addChild(new MenuItemDTO(NameTokens.AmmGestEnti,serverMessages.getString("AmmGestEnti")));
			amm.addChild(new MenuItemDTO(NameTokens.AmmValidUtenti,serverMessages.getString("AmmValidUtenti")));
			amm.addChild(new MenuItemDTO(NameTokens.AmmGestIndex,serverMessages.getString("AmmGestIndex")));
			funzioniAccessibili.add(amm);
			
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.IdentificazioneOggetti,serverMessages.getString("IdentificazioneOggetti")));
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.VerificaOggetti,serverMessages.getString("VerificaOggetti")));
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.CaricamentoImmagini,serverMessages.getString("CaricamentoImmagini")));
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.CatalogazioneOggetti,serverMessages.getString("CatalogazioneOggetti")));
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.CorrezioneOggetti,serverMessages.getString("CorrezioneOggetti")));
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.ValidazioneOggetti,serverMessages.getString("ValidazioneOggetti")));
			
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.PubblicazioneOggetti,serverMessages.getString("PubblicazioneOggetti")));
			
			MenuItemDTO monit = new MenuItemDTO(NameTokens.Monitoraggio,serverMessages.getString("Monitoraggio"));
			monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioLavorazione,serverMessages.getString("MonitoraggioLavorazione")));
			monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCritici,serverMessages.getString("MonitoraggioCritici")));
			monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCrono,serverMessages.getString("MonitoraggioCrono")));
			funzioniAccessibili.add(monit);
			
			MenuItemDTO sched = new MenuItemDTO(NameTokens.Schedulatore,serverMessages.getString("Schedulatore"));
			funzioniAccessibili.add(sched);
		}else{
			funzioniAccessibili.add(new MenuItemDTO(NameTokens.Dashboard,serverMessages.getString("Dashboard")));
			if(utente.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_AMMINISTRATORE)){
				MenuItemDTO amm = new MenuItemDTO(NameTokens.Amministrazione,serverMessages.getString("Amministrazione"));
				amm.addChild(new MenuItemDTO(NameTokens.AmmGestUtenti,serverMessages.getString("AmmGestUtenti")));
				amm.addChild(new MenuItemDTO(NameTokens.AmmGestEnti,serverMessages.getString("AmmGestEnti")));
				amm.addChild(new MenuItemDTO(NameTokens.AmmValidUtenti,serverMessages.getString("AmmValidUtenti")));
				amm.addChild(new MenuItemDTO(NameTokens.AmmGestIndex,serverMessages.getString("AmmGestIndex")));
				funzioniAccessibili.add(amm);
				
				MenuItemDTO monit = new MenuItemDTO(NameTokens.Monitoraggio,serverMessages.getString("Monitoraggio"));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioLavorazione,serverMessages.getString("MonitoraggioLavorazione")));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCritici,serverMessages.getString("MonitoraggioCritici")));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCrono,serverMessages.getString("MonitoraggioCrono")));
				funzioniAccessibili.add(monit);

			}
			if(utente.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_SUPERVISORE)){
				
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.VerificaOggetti,serverMessages.getString("VerificaOggetti")));
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.ValidazioneOggetti,serverMessages.getString("ValidazioneOggetti")));
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.PubblicazioneOggetti,serverMessages.getString("PubblicazioneOggetti")));

				MenuItemDTO monit = new MenuItemDTO(NameTokens.Monitoraggio,serverMessages.getString("Monitoraggio"));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioLavorazione,serverMessages.getString("MonitoraggioLavorazione")));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCritici,serverMessages.getString("MonitoraggioCritici")));
				monit.addChild(new MenuItemDTO(NameTokens.MonitoraggioCrono,serverMessages.getString("MonitoraggioCrono")));
				funzioniAccessibili.add(monit);
			}
			if(utente.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_CATALOGATORE)){
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.IdentificazioneOggetti,serverMessages.getString("IdentificazioneOggetti")));
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.CatalogazioneOggetti,serverMessages.getString("CatalogazioneOggetti")));
			}
			if(utente.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_DIGITALIZZATORE)){
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.CaricamentoImmagini,serverMessages.getString("CaricamentoImmagini")));
				funzioniAccessibili.add(new MenuItemDTO(NameTokens.CorrezioneOggetti,serverMessages.getString("CorrezioneOggetti")));
			}
			MenuItemDTO sched = new MenuItemDTO(NameTokens.Schedulatore,serverMessages.getString("Schedulatore"));
			sched.addChild(new MenuItemDTO(NameTokens.SchedulatoreTriggers,serverMessages.getString("SchedulatoreTriggers")));
			sched.addChild(new MenuItemDTO(NameTokens.SchedulatoreJobs,serverMessages.getString("SchedulatoreJobs")));
			funzioniAccessibili.add(sched);
		}
		
        return funzioniAccessibili;
	}
}
