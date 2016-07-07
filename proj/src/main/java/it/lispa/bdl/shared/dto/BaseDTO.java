package it.lispa.bdl.shared.dto;

import java.util.Date;

/**
 * Class BaseDTO.
 */
public class BaseDTO implements java.io.Serializable {

	/** La Costante serialVersionUID. */
	private static final long serialVersionUID = 3155735377887134396L;

	/** creatoda. */
	private String creatoda;
	
	/** creatoil. */
	private Long creatoil;
	
	/** modificatoda. */
	private String modificatoda;
	
	/** modificatoil. */
	private Long modificatoil;
	
	/**
	 * Legge creatoda.
	 *
	 * @return creatoda
	 */
	public String getCreatoda() {
		return creatoda;
	}

	/**
	 * Imposta creatoda.
	 *
	 * @param creatoda nuovo creatoda
	 */
	public void setCreatoda(String creatoda) {
		this.creatoda = creatoda;
	}

	/**
	 * Legge creatoil.
	 *
	 * @return creatoil
	 */
	public Date getCreatoil() {
		 
		return dateGetter(creatoil);
	}

	/**
	 * Imposta creatoil.
	 *
	 * @param creatoil nuovo creatoil
	 */
	public void setCreatoil(Date creatoil) {
		this.creatoil = dateSetter(creatoil);
	}

	/**
	 * Legge modificatoda.
	 *
	 * @return modificatoda
	 */
	public String getModificatoda() {
		return modificatoda;
	}

	/**
	 * Imposta modificatoda.
	 *
	 * @param modificatoda nuovo modificatoda
	 */
	public void setModificatoda(String modificatoda) {
		this.modificatoda = modificatoda;
	}

	/**
	 * Legge modificatoil.
	 *
	 * @return modificatoil
	 */
	public Date getModificatoil() {
		return dateGetter(modificatoil);
	}

	/**
	 * Imposta modificatoil.
	 *
	 * @param modificatoil nuovo modificatoil
	 */
	public void setModificatoil(Date modificatoil) {
		this.modificatoil = dateSetter(modificatoil);
	}
	
	/**
	 * Date setter.
	 *
	 * @param dt  data
	 * @return long
	 */
	protected Long dateSetter(Date dt){
		if(dt!=null) {
			return dt.getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * Date getter.
	 *
	 * @param dt  data
	 * @return date
	 */
	protected Date dateGetter(Long dt){
		if(dt!=null) {
			return new Date(dt);
		} else {
			return null;
		}
	}

}
