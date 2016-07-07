package it.lispa.bdl.shared.utils;

import it.lispa.bdl.client.utils.GXTAlertBox;
import it.lispa.bdl.shared.messages.HomeMsg;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

/**
 * Class CustomAsyncCallback.
 *
 * @param <T> the generic type
 */
public class CustomAsyncCallback<T> implements AsyncCallback<T>
//,HasHandlers 
{

	// @Inject private EventBus eventBus;

	@Inject HomeMsg messages;
	
    private final AsyncCallback<T> asyncCallback;

    /**
     * Istanzia un nuovo custom async callback.
     *
     * @param asyncCallback  async callback
     */
    public CustomAsyncCallback(AsyncCallback<T> asyncCallback) {
        this.asyncCallback = asyncCallback;
        // eventBus.fireEvent(new CustomAsyncCallStartEvent());
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(Throwable caught) {
    	if(caught instanceof StatusCodeException && caught.getMessage().trim().equals("401")){
			GXTAlertBox box = new GXTAlertBox("Errore di sessione", "L'utente non risulta online, effettuare il login", GXTAlertBox.DO_NOT_SHOW);
			box.addHideHandler(new HideHandler() {
				public void onHide(HideEvent event) {
					String link = "../";
					Window.Location.replace(link);
				}
			});
			box.show();			
		}else if(caught.getMessage().trim().equals("0")){
			// do nothing...
		}else if(!(caught instanceof AsyncServiceException)){
			new GXTAlertBox("Errore di sistema", caught.getMessage(), GXTAlertBox.DO_SHOW);
		}else{
			asyncCallback.onFailure(caught);
		}        
        // eventBus.fireEvent(new CustomAsyncCallFailEvent());
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
     */
    @Override
    public void onSuccess(T result) {
    	asyncCallback.onSuccess(result);
        // eventBus.fireEvent(new CustomAsyncCallSucceedEvent());
    }

	//@Override
	//public void fireEvent(GwtEvent<?> event) {      
		// eventBus.fireEvent(event);
	//}
}