package it.lispa.bdl.shared.dto;

import java.math.BigDecimal;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Class TipoOggettoDTO.
 */
public class TipoOggettoDTO implements java.io.Serializable {

	/**
	 * Interface ComboProperties.
	 */
	public interface ComboProperties extends PropertyAccess<TipoOggettoDTO> {
		
		/**
		 * Codice tipo oggetto.
		 *
		 * @return model key provider
		 */
		ModelKeyProvider<TipoOggettoDTO> cdTipoOggetto();
		
		/**
		 * Nome.
		 *
		 * @return label provider
		 */
		LabelProvider<TipoOggettoDTO> nome();
		
		/**
		 * Catalogo.
		 *
		 * @return label provider
		 */
		LabelProvider<TipoOggettoDTO> catalogo();
		
		/**
		 * Codice catalogo.
		 *
		 * @return label provider
		 */
		LabelProvider<TipoOggettoDTO> cdCatalogo();
	} 
	
	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = -965672748715294490L;
	
	/** codice tipo oggetto. */
	private BigDecimal cdTipoOggetto;
	
	/** nome. */
	private String nome;
	
	/** catalogo. */
	private String catalogo;
	
	/** codice catalogo. */
	private String cdCatalogo;

	/**
	 * Istanzia un nuovo tipo oggetto dto.
	 */
	public TipoOggettoDTO(){
		// do nothing...
	}
			
	/**
	 * Istanzia un nuovo tipo oggetto dto.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param nome  nome
	 * @param catalogo  catalogo
	 * @param cdCatalogo  codice catalogo
	 */
	public TipoOggettoDTO(BigDecimal cdTipoOggetto, String nome, String catalogo,String cdCatalogo) {
		this.cdTipoOggetto = cdTipoOggetto;
		this.nome = nome;
		this.catalogo = catalogo;
		this.cdCatalogo = cdCatalogo;
	}

	/**
	 * Legge codice tipo oggetto.
	 *
	 * @return codice tipo oggetto
	 */
	public BigDecimal getCdTipoOggetto() {
		return cdTipoOggetto;
	}

	/**
	 * Imposta codice tipo oggetto.
	 *
	 * @param cdTipoOggetto nuovo codice tipo oggetto
	 */
	public void setCdTipoOggetto(BigDecimal cdTipoOggetto) {
		this.cdTipoOggetto = cdTipoOggetto;
	}

	/**
	 * Legge nome.
	 *
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta nome.
	 *
	 * @param nome nuovo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Legge catalogo.
	 *
	 * @return catalogo
	 */
	public String getCatalogo() {
		return catalogo;
	}

	/**
	 * Imposta catalogo.
	 *
	 * @param catalogo nuovo catalogo
	 */
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	/**
	 * Legge codice catalogo.
	 *
	 * @return codice catalogo
	 */
	public String getCdCatalogo() {
		return cdCatalogo;
	}

	/**
	 * Imposta codice catalogo.
	 *
	 * @param cdCatalogo nuovo codice catalogo
	 */
	public void setCdCatalogo(String cdCatalogo) {
		this.cdCatalogo = cdCatalogo;
	}
	
}
