package it.lispa.bdl.client;

import it.lispa.bdl.client.gin.ClientGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point per l'intera applicazione.
 */
public class Bdl implements EntryPoint {
	
	/** ginjector. */
	public final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		DelayedBindRegistry.bind(ginjector);
		ginjector.getPlaceManager().revealCurrentPlace();  
	}
}
