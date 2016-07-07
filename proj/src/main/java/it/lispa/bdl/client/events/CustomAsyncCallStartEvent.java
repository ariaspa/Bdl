package it.lispa.bdl.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Class CustomAsyncCallStartEvent.
 */
public class CustomAsyncCallStartEvent extends GwtEvent<CustomAsyncCallStartEvent.CustomAsyncCallStartEventHandler> {

	/** La Costante TYPE. */
	public final static Type<CustomAsyncCallStartEventHandler> TYPE = new Type<CustomAsyncCallStartEventHandler>();

	/**
	 * Interface CustomAsyncCallStartEventHandler.
	 */
	public interface CustomAsyncCallStartEventHandler extends EventHandler {
		void onAsyncCallStart(CustomAsyncCallStartEvent event);
	}

	/**
	 * Istanzia un nuovo custom async call start event.
	 */
	public CustomAsyncCallStartEvent() {
	}

	@Override
	protected void dispatch(CustomAsyncCallStartEventHandler handler) {
		handler.onAsyncCallStart(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CustomAsyncCallStartEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Legge type.
	 *
	 * @return type
	 */
	public static Type<CustomAsyncCallStartEventHandler> getType() {
		return TYPE;
	}

	/**
	 * Fire.
	 *
	 * @param source  source
	 */
	public static void fire(HasHandlers source) {
		source.fireEvent(new CustomAsyncCallStartEvent());
	}
}
