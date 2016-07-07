package it.lispa.bdl.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Class CustomAsyncCallFailEvent.
 */
public class CustomAsyncCallFailEvent extends GwtEvent<CustomAsyncCallFailEvent.CustomAsyncCallFailEventHandler> {

	/** La Costante TYPE. */
	public final static Type<CustomAsyncCallFailEventHandler> TYPE = new Type<CustomAsyncCallFailEventHandler>();

	/**
	 * Interface CustomAsyncCallFailEventHandler.
	 */
	public interface CustomAsyncCallFailEventHandler extends EventHandler {
		void onAsyncCallFail(CustomAsyncCallFailEvent event);
	}

	/**
	 * Istanzia un nuovo custom async call fail event.
	 */
	public CustomAsyncCallFailEvent() {
	}

	@Override
	protected void dispatch(CustomAsyncCallFailEventHandler handler) {
		handler.onAsyncCallFail(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CustomAsyncCallFailEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Legge type.
	 *
	 * @return type
	 */
	public static Type<CustomAsyncCallFailEventHandler> getType() {
		return TYPE;
	}

	/**
	 * Fire.
	 *
	 * @param source  source
	 */
	public static void fire(HasHandlers source) {
		source.fireEvent(new CustomAsyncCallFailEvent());
	}
}
