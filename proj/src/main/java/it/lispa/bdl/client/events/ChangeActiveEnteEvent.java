package it.lispa.bdl.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Class ChangeActiveEnteEvent.
 */
public class ChangeActiveEnteEvent extends GwtEvent<ChangeActiveEnteEvent.ChangeActiveEnteEventHandler>{

	/**
	 * Interface ChangeActiveEnteEventHandler.
	 */
	public interface ChangeActiveEnteEventHandler extends EventHandler {
		void onChangeActiveEnte(ChangeActiveEnteEvent event);
	}
	
	/** La Costante TYPE. */
	public final static Type<ChangeActiveEnteEventHandler> TYPE = new Type<ChangeActiveEnteEventHandler>();
	
	private final String datiUtente;

	/**
	 * Istanzia un nuovo change active ente event.
	 *
	 * @param datiUtente  dati utente
	 */
	public ChangeActiveEnteEvent(String datiUtente) {
		this.datiUtente = datiUtente;
	}

	/**
	 * Legge dati utente.
	 *
	 * @return dati utente
	 */
	public String getDatiUtente() { 
		return datiUtente; 
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<ChangeActiveEnteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeActiveEnteEventHandler handler) {
		handler.onChangeActiveEnte(this);
	}
}
