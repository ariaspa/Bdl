package it.lispa.bdl.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Class ChangeGridState.
 */
public class ChangeGridState extends GwtEvent<ChangeGridState.SaveHandler> {

	/** La Costante TYPE. */
	public final static Type<SaveHandler> TYPE = new Type<SaveHandler>();

	/**
	 * Interface SaveHandler.
	 */
	public interface SaveHandler extends EventHandler {
		void onSave(ChangeGridState event);
	}

	/**
	 * Istanzia un nuovo change grid state.
	 */
	public ChangeGridState() {
	}

	@Override
	protected void dispatch(SaveHandler handler) {
		handler.onSave(this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public Type<SaveHandler> getAssociatedType() {
		return TYPE;
	}

	/**
	 * Legge type.
	 *
	 * @return type
	 */
	public static Type<SaveHandler> getType() {
		return TYPE;
	}

	/**
	 * Fire.
	 *
	 * @param source  source
	 */
	public static void fire(HasHandlers source) {
		source.fireEvent(new ChangeGridState());
	}
}
