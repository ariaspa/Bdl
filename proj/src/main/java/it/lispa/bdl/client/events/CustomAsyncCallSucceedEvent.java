package it.lispa.bdl.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Class CustomAsyncCallSucceedEvent.
 */
public class CustomAsyncCallSucceedEvent extends GwtEvent<CustomAsyncCallSucceedEvent.CustomAsyncCallSucceedEventHandler> {

	/** La Costante TYPE. */
	public final static Type<CustomAsyncCallSucceedEventHandler> TYPE = new Type<CustomAsyncCallSucceedEventHandler>();

	/**
	 * Interface CustomAsyncCallSucceedEventHandler.
	 */
	public interface CustomAsyncCallSucceedEventHandler extends EventHandler {
		void onAsyncCallSucceed(CustomAsyncCallSucceedEvent event);
	}

	/**
	 * Istanzia un nuovo custom async call succeed event.
	 */
	public CustomAsyncCallSucceedEvent() {
	}

	@Override
	protected void dispatch(CustomAsyncCallSucceedEventHandler handler) {
		handler.onAsyncCallSucceed(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<CustomAsyncCallSucceedEventHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Legge type.
	 *
	 * @return type
	 */
	public static Type<CustomAsyncCallSucceedEventHandler> getType() {
		return TYPE;
	}

	/**
	 * Fire.
	 *
	 * @param source  source
	 */
	public static void fire(HasHandlers source) {
		source.fireEvent(new CustomAsyncCallSucceedEvent());
	}
}
