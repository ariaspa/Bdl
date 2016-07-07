package it.lispa.bdl.client.gin;

import it.lispa.bdl.client.place.DefaultPlace;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * Class PlaceManager.
 */
public class PlaceManager extends PlaceManagerImpl{
	private final PlaceRequest defaultPlaceRequest;
	
	/**
	 * Istanzia un nuovo place manager.
	 *
	 * @param eventBus  event bus
	 * @param tokenFormatter  token formatter
	 * @param defaultPlaceNameToken  default place name token
	 */
	@Inject
	public PlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace final String defaultPlaceNameToken) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest.Builder().nameToken(defaultPlaceNameToken).build();
		
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.proxy.PlaceManager#revealDefaultPlace()
	 */
	@Override
	public void revealDefaultPlace() {
	    // Using false as a second parameter ensures that the URL in the browser bar
	    // is not updated, so the user is able to leave the application using the
	    // browser's back navigation button.
	    revealPlace(defaultPlaceRequest, false );
	 }
}
