package it.lispa.bdl.shared.dto;

import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class VOggettoCountDTO.
 */
public class VOggettoCountDTO extends BaseDTO implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 2467263177019197446L;
	
	/**
	 * Interface VOggettoCountDTOProperties.
	 */
	public interface VOggettoCountDTOProperties extends PropertyAccess<VOggettoCountDTO> {
		
		/**
		 * Stato flag.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<VOggettoCountDTO> statoFlag();
		
		/**
		 * Stato.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  String> stato();
		
		/**
		 * Flag.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  String> flag();
		
		/**
		 * Stato human.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  String> statoHuman();
		
		/**
		 * Flag human.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  String> flagHuman();		
		
		/**
		 * Oggetti.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  BigDecimal> oggetti();
		
		/**
		 * Immagini previste.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  BigDecimal> immaginiPreviste();
		
		/**
		 * Immagini digitalizzate.
		 *
		 * @return value provider
		 */
		ValueProvider<VOggettoCountDTO,  BigDecimal> immaginiDigitalizzate();
		
	}

	/** stato flag separator regex. */
	private static String statoFlagSeparatorRegex = "\\|";
	
	/** stato flag. */
	private String statoFlag;
	
	/** oggetti. */
	private BigDecimal oggetti;
	
	/** immagini previste. */
	private BigDecimal immaginiPreviste;
	
	/** immagini digitalizzate. */
	private BigDecimal immaginiDigitalizzate;
	
	/**
	 * Istanzia un nuovo v oggetto count dto.
	 */
	public VOggettoCountDTO(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo v oggetto count dto.
	 *
	 * @param statoFlag  stato flag
	 * @param oggetti  oggetti
	 * @param immaginiPreviste  immagini previste
	 * @param immaginiDigitalizzate  immagini digitalizzate
	 */
	public VOggettoCountDTO(String statoFlag, BigDecimal oggetti, BigDecimal immaginiPreviste, BigDecimal immaginiDigitalizzate) {
		this.statoFlag = statoFlag;
		this.oggetti = oggetti;
		this.immaginiPreviste = immaginiPreviste;
		this.immaginiDigitalizzate = immaginiDigitalizzate;
	}

	/**
	 * Legge stato.
	 *
	 * @return stato
	 */
	public String getStato() {
		String[] parts = statoFlag.split(statoFlagSeparatorRegex);
		String stato = null;
		stato = parts[0];
		return stato;
	}

	/**
	 * Legge flag.
	 *
	 * @return flag
	 */
	public String getFlag() {
		String[] parts = statoFlag.split(statoFlagSeparatorRegex);
		String flag = null;
		if(parts.length>1) {
			flag = parts[1];
		}
		return flag;
	}
	
	/**
	 * Legge stato human.
	 *
	 * @return stato human
	 */
	public String getStatoHuman() {
		String stato = getStato();
		String statoHuman = null; 
		if(stato!=null){
			if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_PUBBLICATO_HUMAN;
			}else if(stato.equals(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO)){
				statoHuman = BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO_HUMAN;
			}
		}
		return statoHuman;
	}

	/**
	 * Legge flag human.
	 *
	 * @return flag human
	 */
	public String getFlagHuman() {
		String flag = getFlag();
		String flagHuman = null; 
		if(flag!=null){
			if(flag.equals(BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE)){
				flagHuman = BdlSharedConstants.VOGGETTI_FILTRO_FL_CORREZIONE_HUMAN;
			}else if(flag.equals(BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI)){
				flagHuman = BdlSharedConstants.VOGGETTI_FILTRO_FL_ANOMALIA_IMMAGINI_HUMAN;
			}
		}
		return flagHuman;
	}

	
	/**
	 * Legge stato flag.
	 *
	 * @return stato flag
	 */
	public String getStatoFlag() {
		return statoFlag;
	}

	/**
	 * Imposta stato flag.
	 *
	 * @param statoFlag nuovo stato flag
	 */
	public void setStatoFlag(String statoFlag) {
		this.statoFlag = statoFlag;
	}

	/**
	 * Legge immagini previste.
	 *
	 * @return immagini previste
	 */
	public BigDecimal getImmaginiPreviste() {
		return immaginiPreviste;
	}

	/**
	 * Imposta immagini previste.
	 *
	 * @param immaginiPreviste nuovo immagini previste
	 */
	public void setImmaginiPreviste(BigDecimal immaginiPreviste) {
		this.immaginiPreviste = immaginiPreviste;
	}

	/**
	 * Legge immagini digitalizzate.
	 *
	 * @return immagini digitalizzate
	 */
	public BigDecimal getImmaginiDigitalizzate() {
		return immaginiDigitalizzate;
	}

	/**
	 * Imposta immagini digitalizzate.
	 *
	 * @param immaginiDigitalizzate nuovo immagini digitalizzate
	 */
	public void setImmaginiDigitalizzate(BigDecimal immaginiDigitalizzate) {
		this.immaginiDigitalizzate = immaginiDigitalizzate;
	}

	/**
	 * Legge oggetti.
	 *
	 * @return oggetti
	 */
	public BigDecimal getOggetti() {
		return oggetti;
	}

	/**
	 * Imposta oggetti.
	 *
	 * @param oggetti nuovo oggetti
	 */
	public void setOggetti(BigDecimal oggetti) {
		this.oggetti = oggetti;
	}

	

}
