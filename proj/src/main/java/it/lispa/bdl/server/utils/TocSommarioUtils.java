package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlToc;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlTocRepository;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class TocSommarioUtils.
 */
@Component
public class TocSommarioUtils {

	/** immagine repository. */
	@Autowired
	private BdlImmagineRepository immagineRepository;
	
	/** toc repository. */
	@Autowired
	private BdlTocRepository tocRepository;

	/** bdl oggetto originale repository. */
	@Autowired 
	private BdlOggettoOriginaleRepository oggettoOriginaleRepository;
	
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return toc sommario oggetto
	 */
	public List<TocSommarioDTO> getTocSommarioOggetto(BigDecimal cdOggettoOriginale){
		return getTocSommarioOggettoRecursive(cdOggettoOriginale, null);
	}
	
	/**
	 * Legge toc sommario oggetto recursive.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param cdTocPadre  codice toc padre
	 * @return toc sommario oggetto recursive
	 */
	private List<TocSommarioDTO> getTocSommarioOggettoRecursive(BigDecimal cdOggettoOriginale, BigDecimal cdTocPadre){
		List<TocSommarioDTO> toReturn = new ArrayList<TocSommarioDTO>();
		
		List<BdlToc> tocs = null;
		if(cdTocPadre==null){
			tocs = tocRepository.findByCdOggettoOriginaleAndCdTocPadreIsNullOrderByCdTocAsc(cdOggettoOriginale);
		}else{
			tocs = tocRepository.findByCdOggettoOriginaleAndCdTocPadreOrderByCdTocAsc(cdOggettoOriginale, cdTocPadre);
		}
		for(BdlToc toc:tocs){
			TocSommarioDTO tocObj = new TocSommarioDTO(toc.getCdToc().intValueExact(),toc.getDnNome());
			if(toc.getCdImmagine()!=null){
				BdlImmagine img = immagineRepository.findByCdImmagine(toc.getCdImmagine());
				if(img!=null){
					tocObj.setNomeImmagine(img.getDnNomeImmagine());
					tocObj.setCdImmagine(img.getCdImmagine());
				}
			}
			List<TocSommarioDTO> children = this.getTocSommarioOggettoRecursive(cdOggettoOriginale, toc.getCdToc());
			if(!children.isEmpty()){
				tocObj.setChildren(children);
			}
			toReturn.add(tocObj);
		}
		return toReturn;
		
	}

	/**
	 * Salva toc.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param saveTreeSommarioData  save tree sommario data
	 * @param operatore  operatore
	 * @throws AsyncServiceException async service exception
	 */
	public void salvaToc(BigDecimal cdOggettoOriginale, List<TocSommarioDTO> saveTreeSommarioData, BdlUtente operatore) throws AsyncServiceException{

		BdlOggettoOriginale oggetto2save = oggettoOriginaleRepository.findByCdOggettoOriginale(cdOggettoOriginale);
		oggetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggetto2save.setDtModificatoil(new Date());
		
		// Questo risulta necessario nel caso in cui l'oggetto arrivi da NON_VALIDATO
		oggetto2save.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);

		oggettoOriginaleRepository.save(oggetto2save);
		
		tocRepository.delete(tocRepository.findByCdOggettoOriginaleOrderByCdTocAsc(cdOggettoOriginale));
		for(TocSommarioDTO dato: saveTreeSommarioData){
			this.salvaTocItem(cdOggettoOriginale, dato, null,operatore);
		}
	}

	/**
	 * Salva toc item.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param tocDato  toc dato
	 * @param cdTocPadre  codice toc padre
	 * @param operatore  operatore
	 * @throws AsyncServiceException async service exception
	 */
	private void salvaTocItem(BigDecimal cdOggettoOriginale, TocSommarioDTO tocDato, BigDecimal cdTocPadre, BdlUtente operatore) throws AsyncServiceException{
				
		BigDecimal cdImmagine = tocDato.getCdImmagine();
		if(cdImmagine == null && tocDato.getNomeImmagine()!=null && !tocDato.getNomeImmagine().equals("")){

			List<BdlImmagine> imgs = immagineRepository.findByCdOggettoOriginaleAndDnNomeImmagine(cdOggettoOriginale, tocDato.getNomeImmagine());
			if(imgs.isEmpty()){
				throw new AsyncServiceException(tocDato.getNomeToc());
			}
			BdlImmagine img = imgs.get(0);
			cdImmagine = img.getCdImmagine();
			
		}
		
		BdlToc toc = tocRepository.save(new BdlToc(
				null,
				tocDato.getNomeToc(),
				cdImmagine,
				cdOggettoOriginale,
				cdTocPadre,
				operatore.getCdCodiceFiscale(), 
				new Date()));
		
		for(TocSommarioDTO dato: tocDato.getChildren()){
			this.salvaTocItem(cdOggettoOriginale, dato, toc.getCdToc(),operatore);
		}
	}
}
