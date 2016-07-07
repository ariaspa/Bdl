package it.lispa.bdl.shared.services.exceptions;

import java.io.Serializable;

/**
 * Class AsyncServiceException.
 */
public class AsyncServiceException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String messaggio;
	
	/**
	 * Istanzia un nuovo async service exception.
	 */
	public AsyncServiceException() {
		// do nothing
	}
	
	/**
	 * Istanzia un nuovo async service exception.
	 *
	 * @param messaggio  messaggio
	 */
	public AsyncServiceException(String messaggio) {
		this.messaggio = messaggio;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		if(this.messaggio!=null){
			return this.messaggio;
		}else{
			return super.getMessage();
		}
	}
	
}
