/*
 * 
 */
package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlAutore;
import it.lispa.bdl.commons.domain.BdlCollezione;
import it.lispa.bdl.commons.domain.BdlContestoArch;
import it.lispa.bdl.commons.domain.BdlEditore;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlLivelloBiblio;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlProgetto;
import it.lispa.bdl.commons.domain.BdlProvincia;
import it.lispa.bdl.commons.domain.BdlQrtzBdlJobs;
import it.lispa.bdl.commons.domain.BdlQrtzTrigger;
import it.lispa.bdl.commons.domain.BdlQualificaAutore;
import it.lispa.bdl.commons.domain.BdlQualificatoreData;
import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlSoggetto;
import it.lispa.bdl.commons.domain.BdlSoggettoProd;
import it.lispa.bdl.commons.domain.BdlSupporto;
import it.lispa.bdl.commons.domain.BdlTecnicaGrafica;
import it.lispa.bdl.commons.domain.BdlTipoArchivio;
import it.lispa.bdl.commons.domain.BdlTipoGrafica;
import it.lispa.bdl.commons.domain.BdlTipoIdentificativo;
import it.lispa.bdl.commons.domain.BdlTipoOggetto;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.quartz.QuartzBaseProcess;
import it.lispa.bdl.server.quartz.QuartzCaricaImmagini;
import it.lispa.bdl.server.quartz.QuartzCreaImmagini;
import it.lispa.bdl.server.repository.BdlAutoreRepository;
import it.lispa.bdl.server.repository.BdlCollezioneRepository;
import it.lispa.bdl.server.repository.BdlContestoArchRepository;
import it.lispa.bdl.server.repository.BdlEditoreRepository;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlLinguaRepository;
import it.lispa.bdl.server.repository.BdlLivelloBiblioRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlProgettoRepository;
import it.lispa.bdl.server.repository.BdlQualificaAutoreRepository;
import it.lispa.bdl.server.repository.BdlQualificatoreDataRepository;
import it.lispa.bdl.server.repository.BdlSoggettoProdRepository;
import it.lispa.bdl.server.repository.BdlSoggettoRepository;
import it.lispa.bdl.server.repository.BdlSupportoRepository;
import it.lispa.bdl.server.repository.BdlTecnicaGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoArchivioRepository;
import it.lispa.bdl.server.repository.BdlTipoGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoIdentificativoRepository;
import it.lispa.bdl.server.repository.BdlTipoOggettoRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.dto.FormatoDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
import it.lispa.bdl.shared.dto.SchedulatoreTriggerDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Trigger.TriggerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class MapperUtils.
 */
@Component
public class MapperUtils  {

	/** bdl autore repository. */
	@Autowired 
	private BdlAutoreRepository bdlAutoreRepository;
	
	/** bdl collezione repository. */
	@Autowired 
	private BdlCollezioneRepository bdlCollezioneRepository;
	
	/** bdl contesto arch repository. */
	@Autowired 
	private BdlContestoArchRepository bdlContestoArchRepository;
	
	/** bdl editore repository. */
	@Autowired 
	private BdlEditoreRepository bdlEditoreRepository;
	
	/** bdl ente repository. */
	@Autowired 
	private BdlEnteRepository bdlEnteRepository;
	
	/** bdl lingua repository. */
	@Autowired 
	private BdlLinguaRepository bdlLinguaRepository;
	
	/** bdl livello biblio repository. */
	@Autowired 
	private BdlLivelloBiblioRepository bdlLivelloBiblioRepository;
	
	/** bdl oggetto originale repository. */
	@Autowired
	private BdlOggettoOriginaleRepository bdlOggettoOriginaleRepository;
	
	/** bdl progetto repository. */
	@Autowired
	private BdlProgettoRepository bdlProgettoRepository;
	
	/** bdl qualifica autore repository. */
	@Autowired 
	private BdlQualificaAutoreRepository bdlQualificaAutoreRepository;
	
	/** bdl qualificatore data repository. */
	@Autowired 
	private BdlQualificatoreDataRepository bdlQualificatoreDataRepository;
	
	/** bdl soggetto prod repository. */
	@Autowired 
	private BdlSoggettoProdRepository bdlSoggettoProdRepository;
	
	/** bdl soggetto repository. */
	@Autowired
	private BdlSoggettoRepository bdlSoggettoRepository;
	
	/** bdl supporto repository. */
	@Autowired 
	private BdlSupportoRepository bdlSupportoRepository;
	
	/** bdl tecnica grafica repository. */
	@Autowired 
	private BdlTecnicaGraficaRepository bdlTecnicaGraficaRepository;
	
	/** bdl tipo archivio repository. */
	@Autowired 
	private BdlTipoArchivioRepository bdlTipoArchivioRepository;
	
	/** bdl tipo grafica repository. */
	@Autowired 
	private BdlTipoGraficaRepository bdlTipoGraficaRepository;
	
	/** bdl tipo identificativo repository. */
	@Autowired 
	private BdlTipoIdentificativoRepository bdlTipoIdentificativoRepository;
	
	/** bdl tipo oggetto repository. */
	@Autowired 
	private BdlTipoOggettoRepository bdlTipoOggettoRepository;
	
	/** bdl utente repository. */
	@Autowired 
	private BdlUtenteRepository bdlUtenteRepository;

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	/**
	 * Map bdl progetto to progetto dto.
	 *
	 * @param progetto  progetto
	 * @return progetto dto
	 */
	public ProgettoDTO mapBdlProgettoToProgettoDTO(BdlProgetto progetto){
		return new ProgettoDTO(
				progetto.getCdProgetto(),
				progetto.getDnTitolo(),
				progetto.getDnTitoloBreve(),
				progetto.getDsDescrizioneIt(),
				progetto.getDsDescrizioneEn(),
				progetto.getDtInizio(),
				progetto.getDtConclusione(),
				progetto.getDsStato()
				);
	}
	
	/**
	 * Map bdl collezione to collezione dto.
	 *
	 * @param collezione  collezione
	 * @return collezione dto
	 */
	public CollezioneDTO mapBdlCollezioneToCollezioneDTO(BdlCollezione collezione){

		BdlProgetto progetto = bdlProgettoRepository.findByCdProgetto(collezione.getCdProgetto());

		return new CollezioneDTO(
				collezione.getCdCollezione(),
				collezione.getCdProgetto(), 
				progetto.getDnTitolo(),
				collezione.getDnTitolo(),
				collezione.getDsDescrizioneIt(),
				collezione.getDsDescrizioneEn(),
				collezione.getDsDiritti(),
				collezione.getDsAmbitoDisciplinare(),
				collezione.getDsCoperturaGeografica(),
				collezione.getDsPeriodo(),
				collezione.getDsAnnoOggAntico(),
				collezione.getDsAnnoOggRecente()
				);
	}
	
	/**
	 * Map bdl oggetto originale to oggetto dto.
	 *
	 * @param oggetto  oggetto
	 * @return oggetto dto
	 */
	public OggettoDTO mapBdlOggettoOriginaleToOggettoDTO(BdlOggettoOriginale oggetto){

		BdlCollezione collezione = bdlCollezioneRepository.findByCdCollezione(oggetto.getCdCollezione());
		BdlProgetto progetto = bdlProgettoRepository.findByCdProgetto(collezione.getCdProgetto());
		BdlTipoOggetto tipoOggetto = bdlTipoOggettoRepository.findByCdTipoOggetto(oggetto.getCdTipoOggetto());

		BdlOggettoOriginale oggettoSup = null;
		if(oggetto.getCdOggettoOriginaleSup()!=null){
			oggettoSup = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(oggetto.getCdOggettoOriginaleSup());
		}

		String descrLivelloBiblio = null;
		if(oggetto.getCdLivelloBiblio()!=null){
			BdlLivelloBiblio item = bdlLivelloBiblioRepository.findByCdLivelloBiblio(oggetto.getCdLivelloBiblio());
			descrLivelloBiblio = item.getDsDescrizione();
		}
		String descrSoggetto = null;
		if(oggetto.getCdSoggetto()!=null){
			BdlSoggetto item = bdlSoggettoRepository.findByCdSoggetto(oggetto.getCdSoggetto());
			descrSoggetto = item.getDsDescrizione();
		}
		String descrAutore = null;
		if(oggetto.getCdAutore()!=null){
			BdlAutore item = bdlAutoreRepository.findByCdAutore(oggetto.getCdAutore());
			descrAutore = item.getDsDescrizione();
		}
		String descrAutoreSec = null;
		if(oggetto.getCdAutoreSec()!=null){
			BdlAutore item = bdlAutoreRepository.findByCdAutore(oggetto.getCdAutoreSec());
			descrAutoreSec = item.getDsDescrizione();
		}
		String descrizioneQualificaAutore = null;
		if(oggetto.getCdQualificaAutore()!=null){
			BdlQualificaAutore item = bdlQualificaAutoreRepository.findByCdQualificaAutore(oggetto.getCdQualificaAutore());
			descrizioneQualificaAutore = item.getDsDescrizione();
		}
		String descrizioneQualificaAutoreSec = null;
		if(oggetto.getCdQualificaAutoreSec()!=null){
			BdlQualificaAutore item = bdlQualificaAutoreRepository.findByCdQualificaAutore(oggetto.getCdQualificaAutoreSec());
			descrizioneQualificaAutoreSec = item.getDsDescrizione();
		}
		String descrizioneEditore = null;
		if(oggetto.getCdEditore()!=null){
			BdlEditore item = bdlEditoreRepository.findByCdEditore(oggetto.getCdEditore());
			descrizioneEditore = item.getDsDescrizione();
		}

		String descrizioneLingua = null;
		if(oggetto.getCdLingua()!=null){
			BdlLingua item = bdlLinguaRepository.findByCdLingua(oggetto.getCdLingua());
			descrizioneLingua = item.getDsDescrizione();
		}

		String descrizioneTipoIdentificativo = null;
		if(oggetto.getCdTipoIdentificativo()!=null){
			BdlTipoIdentificativo item = bdlTipoIdentificativoRepository.findByCdTipoIdentificativo(oggetto.getCdTipoIdentificativo());
			descrizioneTipoIdentificativo = item.getDsDescrizione();
		}
		String descrizioneQualificatoreData = null;
		if(oggetto.getCdQualificatoreData()!=null){
			BdlQualificatoreData item = bdlQualificatoreDataRepository.findByCdQualificatoreData(oggetto.getCdQualificatoreData());
			descrizioneQualificatoreData = item.getDsDescrizione();
		}
		String descrizioneSupporto = null;
		if(oggetto.getCdSupporto()!=null){
			BdlSupporto item = bdlSupportoRepository.findByCdSupporto(oggetto.getCdSupporto());
			descrizioneSupporto = item.getDsDescrizione();
		}
		String descrizioneTecnicaGrafica = null;
		if(oggetto.getCdTecnicaGrafica()!=null){
			BdlTecnicaGrafica item = bdlTecnicaGraficaRepository.findByCdTecnicaGrafica(oggetto.getCdTecnicaGrafica());
			descrizioneTecnicaGrafica = item.getDsDescrizione();
		}
		String descrizioneTipoGrafica = null;
		if(oggetto.getCdTipoGrafica()!=null){
			BdlTipoGrafica item = bdlTipoGraficaRepository.findByCdTipoGrafica(oggetto.getCdTipoGrafica());
			descrizioneTipoGrafica = item.getDsDescrizione();
		}
		String descrizioneSoggettoProd = null;
		if(oggetto.getCdSoggettoProd()!=null){
			BdlSoggettoProd item = bdlSoggettoProdRepository.findByCdSoggettoProd(oggetto.getCdSoggettoProd());
			descrizioneSoggettoProd = item.getDsDescrizione();
		}

		String descrizioneContestoArch = null;
		if(oggetto.getCdContestoArch()!=null){
			BdlContestoArch item = bdlContestoArchRepository.findByCdContestoArch(oggetto.getCdContestoArch());
			descrizioneContestoArch = item.getDsDescrizione();
		}

		String descrizioneTipoArchivio = null;
		if(oggetto.getCdTipoArchivio()!=null){
			BdlTipoArchivio item = bdlTipoArchivioRepository.findByCdTipoArchivio(oggetto.getCdTipoArchivio());
			descrizioneTipoArchivio = item.getDsDescrizione();
		}
		
		OggettoDTO oggettoDTO = new OggettoDTO(
				oggetto.getCdOggettoOriginale(), 
				tipoOggetto.getCdTipoOggetto(), 
				tipoOggetto.getDnNome(),
				oggettoSup == null ? null : oggettoSup.getCdOggettoOriginale(),
				oggettoSup == null ? null : oggettoSup.getDnTitolo(),
				progetto.getCdProgetto(),
				progetto.getDnTitolo(), 
				collezione.getCdCollezione(),
				collezione.getDnTitolo(), 
				oggetto.getDnTitolo(), 
				oggetto.getDnTitoloFe(), 
				oggetto.getFlOggettoSuperiore(), 
				oggetto.getNrOggettiInferiori(),
				oggetto.getFlOggettoDigitale(), 
				oggetto.getFlCorrezione(), 
				oggetto.getDsNotaCorrezione(), 
				oggetto.getFlAnomaliaImmagini(),
				oggetto.getDsLogAnomalia(),
				oggetto.getDsStato(),
				oggetto.getDsNoteNonValidazione(),
				oggetto.getDsNoteNonPubblicazione(),
				oggetto.getNrImmaginiPreviste(),
				oggetto.getNrImmaginiDigitalizzate(),
				oggetto.getDsContenutistica(), 
				oggetto.getDsFisica(), 
				oggetto.getCdLivelloBiblio(),
				descrLivelloBiblio,
				oggetto.getCdSoggetto(),
				descrSoggetto, 
				oggetto.getCdAutore(),
				descrAutore, 
				oggetto.getCdAutoreSec(),
				descrAutoreSec, 
				
				oggetto.getCdQualificaAutore(), 
				descrizioneQualificaAutore, 
				oggetto.getCdQualificaAutoreSec(),
				descrizioneQualificaAutoreSec, 
				oggetto.getCdEditore(), 
				descrizioneEditore, 
				oggetto.getCdLingua(), 
				descrizioneLingua,
				oggetto.getCdTipoIdentificativo(), 
				descrizioneTipoIdentificativo, 
				oggetto.getCdIdentificativo(), 
				oggetto.getDnLuogoPubblicazione(),
				oggetto.getCdQualificatoreData(), 
				descrizioneQualificatoreData, 
				oggetto.getDsData(), 
				oggetto.getDsNote(), 
				oggetto.getCdSupporto(),
				descrizioneSupporto, 
				oggetto.getCdTecnicaGrafica(), 
				descrizioneTecnicaGrafica, 
				oggetto.getCdTipoGrafica(),
				descrizioneTipoGrafica, 
				oggetto.getDsScala(), 
				oggetto.getDsProiezione(), 
				oggetto.getDsCoordinate(),
				oggetto.getCdSoggettoProd(),
				descrizioneSoggettoProd, 
				oggetto.getCdContestoArch(), 
				descrizioneContestoArch, 
				oggetto.getCdTipoArchivio(),
				descrizioneTipoArchivio, 
				oggetto.getDsLinkCatalogo(), 
				oggetto.getDsDiritti(), 
				oggetto.getDsAltreinfo(), 
				oggetto.getDsCreatoda(), 
				oggetto.getDtCreatoil(), 
				oggetto.getDsModificatoda(), 
				oggetto.getDtModificatoil(),
				oggetto.getDtPubblicatoil() instanceof Timestamp ? new Date(((Timestamp) oggetto.getDtPubblicatoil()).getTime()) : oggetto.getDtPubblicatoil(),
				oggetto.getNrRilevanza()
				);
		
		oggettoDTO.setSegnatura(oggetto.getSegnatura());
		return oggettoDTO;
	
	}
	


	/**
	 * Map bdl immagine to immagine dto.
	 *
	 * @param img  img
	 * @return immagine dto
	 */
	public ImmagineDTO mapBdlImmagineToImmagineDTO(BdlImmagine img){
		return mapBdlImmagineToImmagineDTO(img, null, null, null, null, null, null);
	}
	
	/**
	 * Map bdl immagine to immagine dto.
	 *
	 * @param img  img
	 * @param basePathThumb  base path thumb
	 * @param basePathReader  base path reader
	 * @param basePathZoom  base path zoom
	 * @param pathThumb  path thumb
	 * @param pathReader  path reader
	 * @param pathZoom  path zoom
	 * @return immagine dto
	 */
	public ImmagineDTO mapBdlImmagineToImmagineDTO(BdlImmagine img, String basePathThumb, String basePathReader, String basePathZoom,  String pathThumb, String pathReader, String pathZoom){
		return new ImmagineDTO(
				img.getCdImmagine(), 
				img.getCdOggettoOriginale(), 
				img.getDnNomeImmagine(), 
				img.getNrPxLarghezzaThumb().intValue(), 
				img.getNrPxAltezzaThumb().intValue(), 
				img.getNrPxLarghezzaReader().intValue(), 
				img.getNrPxAltezzaReader().intValue(), 
				img.getNrPxLarghezzaZoom()==null ? null : img.getNrPxLarghezzaZoom().intValue(), 
				img.getNrPxAltezzaZoom()==null ? null : img.getNrPxAltezzaZoom().intValue(),
				img.getFlPrincipale(),
				pathThumb,
				pathReader,
				pathZoom,
				basePathThumb,
				basePathReader,
				basePathZoom
			);
	}

	/**
	 * Map bdl formato to formato dto.
	 *
	 * @param list  list
	 * @return formato dto
	 */
	public FormatoDTO mapBdlFormatoToFormatoDTO(List<BdlFormato> list){
	
		FormatoDTO fDto = new FormatoDTO();
	
		fDto.setCdFormato(list.get(0).getCdFormato());
		fDto.setNomeFormato(list.get(0).getDnNomeFormato());
		fDto.setCreatoda(list.get(0).getDsCreatoda());
		fDto.setEstensione(list.get(0).getDsEstensione());
		fDto.setModificatoda(list.get(0).getDsModificatoda());
		
		if (list.get(0).getDtCreatoil() != null) {
			fDto.setCreatoil(list.get(0).getDtCreatoil());
		}
			
		if (list.get(0).getDtModificatoil() != null) {
			fDto.setModificatoil(list.get(0).getDtModificatoil());
		}
			
		fDto.setDefaultForReader(list.get(0).getFlDefaultforreader());
		fDto.setDefaultForThumb(list.get(0).getFlDefaultforthumb());
		fDto.setDefaultForZoom(list.get(0).getFlDefaultforzoom());
		fDto.setRegolaNaming(list.get(0).getFlRegolaNaming());
		fDto.setPixelBoxSize(list.get(0).getVlRisoluzione());
		fDto.setRisoluzione(list.get(0).getVlRisoluzione());
		
		fDto.setCdTipoOggetto(list.get(0).getCdTipoOggetto());
		
		return fDto;
	}
	
	/**
	 * Map list bdl immagine to list immagine dto.
	 *
	 * @param listImmagini  list immagini
	 * @return list
	 */
	public List<ImmagineDTO> mapListBdlImmagineToListImmagineDTO(List<BdlImmagine> listImmagini) {
		List<ImmagineDTO> immaginiDto = new ArrayList<ImmagineDTO>();
		for (BdlImmagine cImg : listImmagini){
			ImmagineDTO imgDto = mapBdlImmagineToImmagineDTO(cImg);	
			immaginiDto.add(imgDto);
		}
		return immaginiDto;
	}
	
	/**
	 * Map list bdl immagine to list immagine dto.
	 *
	 * @param lista  lista
	 * @param contextPath  context path
	 * @param servicePath  service path
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return list
	 */
	public List<ImmagineDTO> mapListBdlImmagineToListImmagineDTO(List<BdlImmagine> lista, String contextPath, String servicePath, BigDecimal cdOggettoOriginale) {
		
		List<ImmagineDTO> listaNuova = new ArrayList<ImmagineDTO>();
		
		String basePathThumb = contextPath+"/"+servicePath+"/rest/srv/item/"+cdOggettoOriginale+"/images/thumb/";
		String basePathReader = contextPath+"/"+servicePath+"/rest/srv/item/"+cdOggettoOriginale+"/images/reader/";
		String basePathZoom = contextPath+"/"+servicePath+"/rest/srv/item/"+cdOggettoOriginale+"/images/zoom/";
		
		for(BdlImmagine img:lista){
			String progressivoImmagine = img.getDnNomeImmagine().replaceFirst("[0]{0,3}", "");
			String pathThumb = basePathThumb+progressivoImmagine;
			String pathReader = basePathReader+progressivoImmagine;
			String pathZoom = basePathZoom+progressivoImmagine;
			ImmagineDTO immagineNuova = mapBdlImmagineToImmagineDTO(img, basePathThumb, basePathReader, basePathZoom, pathThumb, pathReader, pathZoom );			
			listaNuova.add(immagineNuova);
		}
		return listaNuova;
	}

	/**
	 * Map bdl ente to ente dto.
	 *
	 * @param enteJpa  ente jpa
	 * @param prov  prov
	 * @param digitalizzatore  digitalizzatore
	 * @return ente dto
	 */
	public EnteDTO mapBdlEnteToEnteDTO(BdlEnte enteJpa, BdlProvincia prov, BdlEnte digitalizzatore) {

		EnteDTO enteDto = new EnteDTO(
				enteJpa.getCdEnte(), 
				digitalizzatore!=null ? digitalizzatore.getCdEnte() : null,
				digitalizzatore!=null ? digitalizzatore.getDnNome() : null,
				enteJpa.getDnNome(), 
				enteJpa.getDsIndirizzo(), 
				enteJpa.getDnComune(), 
				prov.getCdSigla(),
				prov.getDsProvincia(), 
				enteJpa.getDsCap(), 
				enteJpa.getNrTelefono(),
				enteJpa.getNrFax(), 
				enteJpa.getDnEmail(), 
				enteJpa.getDnIndirizzoWww(), 
				enteJpa.getFlClasse(),
				BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO==enteJpa.getFlClasse() ? BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO_HUMAN : BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE_HUMAN
				);

		return enteDto;
	}


	/**
	 * Map bdl utente to utente light dto.
	 *
	 * @param utenteJpa  utente jpa
	 * @param ruolo  ruolo
	 * @return utente light dto
	 */
	public UtenteLightDTO mapBdlUtenteToUtenteLightDTO(BdlUtente utenteJpa, BdlRuolo ruolo) {
		UtenteLightDTO utenteDto = new UtenteDTO(utenteJpa.getCdUtente(), utenteJpa.getNmNome(), utenteJpa.getCmCognome(), utenteJpa.getCdCodiceFiscale(),
				utenteJpa.getDnEmail(), utenteJpa.getNrTelefono(), utenteJpa.getCdRuolo(), ruolo.getDnNome(), utenteJpa.getDsStato(),
				utenteJpa.getDtRegistrazione(), utenteJpa.getDtValidazione());

		return utenteDto;
	}

	/**
	 * Map bdl utente to utente dto.
	 *
	 * @param triggerJpa  trigger jpa
	 * @return utente dto
	 */
	public SchedulatoreTriggerDTO mapBdlQrtzTriggerToSchedulatoreTriggerDTO(BdlQrtzTrigger triggerJpa) {
		
		String codicePk = triggerJpa.getComp_id().toString();
		BigDecimal cdUtente = new BigDecimal(triggerJpa.getComp_id().getTriggerGroup());
		String nomeUtente = "";
		BdlUtente ut = bdlUtenteRepository.findByCdUtente(cdUtente);
		if(ut!=null){
			nomeUtente = ut.getCmCognome()+", "+ut.getNmNome();
		}
		Long dtStart = triggerJpa.getStartTime();
		Long dtEnd = null;
		String dsTipo = "";
		String dsEsito = "";
		String dsInput = "";
		String dsOutput = "";
		if(QuartzCaricaImmagini.JOBNAME.equals(triggerJpa.getJobName())) {
			/* Job=QuartzCaricaImmagini.JOBNAME */
			dsTipo = QuartzCaricaImmagini.JOBHUMANNAME;
			dsInput = QuartzCaricaImmagini.getTriggerIdentifier(triggerJpa.getComp_id().getTriggerName());
			VOggettoDTO oggetto = vOggetti.findOggettoByCodice(new BigDecimal(dsInput));
        	if(oggetto!=null){
        		dsInput = oggetto.getO_DigitalizzatoreId();
        	}
    		if(TriggerState.COMPLETE.toString().equals(triggerJpa.getTriggerState())){
    			dsEsito = QuartzBaseProcess.ESITORUNNING;
    		}else{
    			dsEsito = QuartzBaseProcess.ESITOATTESA;
    		}
		} else if(QuartzCreaImmagini.JOBNAME.equals(triggerJpa.getJobName())) {
			/* Job=QuartzCreaImmagini.JOBNAME */
			dsTipo = QuartzCreaImmagini.JOBHUMANNAME;
			dsInput = QuartzCreaImmagini.getTriggerIdentifier(triggerJpa.getComp_id().getTriggerName());
			VOggettoDTO oggetto = vOggetti.findOggettoByCodice(new BigDecimal(dsInput));
			if(oggetto!=null){
				dsInput = oggetto.getO_DigitalizzatoreId();
			}
			if(TriggerState.COMPLETE.toString().equals(triggerJpa.getTriggerState())){
				dsEsito = QuartzBaseProcess.ESITORUNNING;
			}else{
				dsEsito = QuartzBaseProcess.ESITOATTESA;
			}
		}
		
		SchedulatoreTriggerDTO itemDto = new SchedulatoreTriggerDTO(
				codicePk, cdUtente, nomeUtente, dtStart, dtEnd, dsTipo,
				dsEsito, dsInput, dsOutput
		);
		return itemDto;
	}
	
	/**
	 * Map bdl qrtz bdl jobs to schedulatore jobs dto.
	 *
	 * @param triggerJpa  trigger jpa
	 * @return schedulatore jobs dto
	 */
	public SchedulatoreJobsDTO mapBdlQrtzBdlJobsToSchedulatoreJobsDTO(BdlQrtzBdlJobs triggerJpa) {
		
		BigDecimal codicePk = triggerJpa.getCdBdlJobs();																
		BigDecimal cdUtente = triggerJpa.getCdUtente();
		String nomeUtente = "";
		BdlUtente ut = bdlUtenteRepository.findByCdUtente(cdUtente);
		if(ut!=null){
			nomeUtente = ut.getCmCognome()+", "+ut.getNmNome();
		}
		Long dtStart = triggerJpa.getDtStart()!=null?triggerJpa.getDtStart().getTime():null;
		Long dtEnd = triggerJpa.getDtEnd()!=null?triggerJpa.getDtEnd().getTime():null;
		String dsTipo = "";
		if(QuartzCaricaImmagini.JOBNAME.equals(triggerJpa.getDsTipo())){
			/* Job=QuartzCaricaImmagini.JOBNAME */
			dsTipo = "Carica Immagini";
		} else if(QuartzCreaImmagini.JOBNAME.equals(triggerJpa.getDsTipo())){
			/* Job=QuartzCreaImmagini.JOBNAME */
			dsTipo = "Crea Immagini";
		}
		String dsEsito = triggerJpa.getDsEsito();
		String dsInput = triggerJpa.getDsInput();
		String dsOutput = triggerJpa.getDsOutput();

		SchedulatoreJobsDTO itemDto = new SchedulatoreJobsDTO(
				codicePk, cdUtente, nomeUtente, dtStart, dtEnd, dsTipo,
				dsEsito, dsInput, dsOutput
		);
		return itemDto;
	}
	/**
	 * Map bdl utente to utente dto.
	 *
	 * @param utenteJpa  utente jpa
	 * @param ruolo  ruolo
	 * @param bdlEntiUtente  bdl enti utente
	 * @return utente dto
	 */
	public UtenteDTO mapBdlUtenteToUtenteDTO(BdlUtente utenteJpa, BdlRuolo ruolo, List<BdlEnteUtente> bdlEntiUtente) {

		List<String> entiRelazionati = new ArrayList<String>();
		List<BigDecimal> cdEnti = new ArrayList<BigDecimal>();
		if (bdlEntiUtente != null) {
			for (BdlEnteUtente enteUtente : bdlEntiUtente) {
				BdlEnte ente = bdlEnteRepository.findByCdEnte(enteUtente.getCdEnte());
				cdEnti.add(ente.getCdEnte());
				entiRelazionati.add(ente.getDnNome());
			}
		}

		UtenteDTO utenteDto = new UtenteDTO(utenteJpa.getCdUtente(), utenteJpa.getNmNome(), utenteJpa.getCmCognome(), utenteJpa.getCdCodiceFiscale(),
				utenteJpa.getDnEmail(), utenteJpa.getNrTelefono(), utenteJpa.getCdRuolo(), ruolo.getDnNome(), utenteJpa.getDsStato(),
				utenteJpa.getDtRegistrazione(), utenteJpa.getDtValidazione(), entiRelazionati, cdEnti);

		return utenteDto;
	}
	


	/**
	 * Map unimarc dto to oggetto dto.
	 *
	 * @param item  item
	 * @param cdTitoloSuperiore  codice titolo superiore
	 * @param cdCollezione  codice collezione
	 * @param isOggettoSuperiore  is oggetto superiore
	 * @param tipoSbnCd  tipo sbn codice
	 * @return oggetto dto
	 * @throws AsyncServiceException async service exception
	 */
	public OggettoDTO mapUnimarcDTOToOggettoDTO(UnimarcDTO item, BigDecimal cdTitoloSuperiore, BigDecimal cdCollezione, Boolean isOggettoSuperiore, BigDecimal tipoSbnCd) throws AsyncServiceException{
		
		if(item.getTipoOggetto()==null || item.getTipoOggetto().equals("")){
			throw new AsyncServiceException("Il tipo oggetto proveniente dalla ricerca SBN OPAC non e' popolato!");
		}
		List<BdlTipoOggetto> tipi = bdlTipoOggettoRepository.findByCdCatalogo(item.getTipoOggetto());
		if(tipi==null){
			throw new AsyncServiceException("Non trovo il tipo oggetto corrispondente a "+item.getTipoOggetto());
		}
		BdlTipoOggetto tipo = tipi.get(0);
		BigDecimal cdTipoOggetto = tipo.getCdTipoOggetto();
		
		OggettoDTO obj = new OggettoDTO(null, cdTipoOggetto, cdTitoloSuperiore, cdCollezione, item.getTitolo(), isOggettoSuperiore, item.getImmagini());
		obj.setFisica(item.getDescrizioneFisica());
		obj.setDescrizioneAutore(item.getAutore());
		obj.setDescrizioneAutoreSec(item.getAutoreSecondario());
		obj.setDescrizioneEditore(item.getEditore());
		obj.setDescrizioneLingua(item.getLingua());
		obj.setCdTipoIdentificativo(tipoSbnCd);
		obj.setIdentificativo(item.getId());
		obj.setLuogoPubblicazione(item.getLuogoPubblicazione());
		obj.setData(item.getDataPubblicazione());
		return obj;
	}
	
	/**
	 * Map unimarc dto to oggetto dto.
	 *
	 * @param item  item
	 * @param cdCollezione  codice collezione
	 * @param isOggettoSuperiore  is oggetto superiore
	 * @param tipoSbnCd  tipo sbn codice
	 * @return oggetto dto
	 * @throws AsyncServiceException async service exception
	 */
	public OggettoDTO mapUnimarcDTOToOggettoDTO(UnimarcDTO item, BigDecimal cdCollezione, Boolean isOggettoSuperiore, BigDecimal tipoSbnCd) throws AsyncServiceException{
		return mapUnimarcDTOToOggettoDTO(item, null, cdCollezione, isOggettoSuperiore, tipoSbnCd); 
	}
	

	/**
	 * Map v oggetto dto to oggetto dto.
	 *
	 * @param oggetto  oggetto
	 * @return oggetto dto
	 */
	public OggettoDTO mapVOggettoDTOToOggettoDTO(VOggettoDTO oggetto){
		return new OggettoDTO(
				oggetto.getO_CdOggettoOriginale(), 
				oggetto.getT_CdTipoOggetto(), 
				oggetto.getT_DnNome(),
				oggetto.getO_CdOggettoOriginaleSup(),
				oggetto.getO_DnTitoloSup(),
				oggetto.getP_CdProgetto(),
				oggetto.getP_DnTitolo(), 
				oggetto.getC_CdCollezione(),
				oggetto.getC_DnTitolo(), 
				oggetto.getO_DnTitolo(), 
				oggetto.getO_DnTitoloFe(), 
				oggetto.getO_FlOggettoSuperioreBool(), 
				oggetto.getO_NrOggettiInferiori(),
				oggetto.getO_FlOggettoDigitaleBool(), 
				oggetto.getO_FlCorrezioneBool(), 
				oggetto.getO_DsNotaCorrezione(), 
				oggetto.getO_FlAnomaliaImmaginiBool(),
				oggetto.getO_DsLogAnomalia(),
				oggetto.getO_DsStato(),
				oggetto.getO_DsNoteNonValidazione(),
				oggetto.getO_DsNoteNonPubblicazione(),
				oggetto.getO_NrImmaginiPreviste(),
				oggetto.getO_NrImmaginiDigitalizzate(),
				oggetto.getO_DsContenutistica(), 
				oggetto.getO_DsFisica(), 
				oggetto.getO_CdLivelloBiblio(),
				oggetto.getO_DsLivelloBiblio(),
				oggetto.getO_CdSoggetto(),
				oggetto.getO_DsSoggetto(),
				oggetto.getO_CdAutore(),
				oggetto.getO_DsAutore(),
				oggetto.getO_CdAutoreSec(),
				oggetto.getO_DsAutoreSec(),
				
				oggetto.getO_CdQualificaAutore(), 
				oggetto.getO_DsQualificaAutore(),
				oggetto.getO_CdQualificaAutoreSec(), 
				oggetto.getO_DsQualificaAutoreSec(),
				oggetto.getO_CdEditore(), 
				oggetto.getO_DsEditore(),
				oggetto.getO_CdLingua(),  
				oggetto.getO_DsLingua(),
				oggetto.getO_CdTipoIdentificativo(),  
				oggetto.getO_DsTipoIdentificativo(),
				oggetto.getO_CdIdentificativo(), 
				oggetto.getO_Dn_LuogoPubblicazione(),
				oggetto.getO_CdQualificatoreData(), 
				oggetto.getO_DsQualificatoreData(), 
				oggetto.getO_DsData(), 
				oggetto.getO_DsNote(), 
				oggetto.getO_CdSupporto(),
				oggetto.getO_DsSupporto(), 
				oggetto.getO_CdTecnicaGrafica(),
				oggetto.getO_DsTecnicaGrafica(), 
				oggetto.getO_CdTipoGrafica(), 
				oggetto.getO_DsTipoGrafica(), 
				oggetto.getO_DsScala(), 
				oggetto.getO_DsProiezione(), 
				oggetto.getO_DsCoordinate(),
				oggetto.getO_CdSoggettoProd(),
				oggetto.getO_DsSoggettoProd(), 
				oggetto.getO_CdContestoArch(), 
				oggetto.getO_DsContestoArch(), 
				oggetto.getO_CdTipoArchivio(), 
				oggetto.getO_DsTipoArchivio(), 
				oggetto.getO_DsLinkCatalogo(), 
				oggetto.getO_DsDiritti(), 
				oggetto.getO_DsAltreinfo(), 
				null, 
				null, 
				null, 
				null,
				oggetto.getO_DtPubblicatoil(),
				oggetto.getO_NrRilevanza()
				);
	
	}
}
