package it.lispa.bdl.client.ui;

import it.lispa.bdl.client.events.CustomAsyncCallFailEvent;
import it.lispa.bdl.client.events.CustomAsyncCallFailEvent.CustomAsyncCallFailEventHandler;
import it.lispa.bdl.client.events.CustomAsyncCallStartEvent;
import it.lispa.bdl.client.events.CustomAsyncCallStartEvent.CustomAsyncCallStartEventHandler;
import it.lispa.bdl.client.events.CustomAsyncCallSucceedEvent;
import it.lispa.bdl.client.events.CustomAsyncCallSucceedEvent.CustomAsyncCallSucceedEventHandler;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
 
/**
 * Class LoaderController.
 */
public final class LoaderController extends PresenterWidget<LoaderController.iLoaderView>{
    
    /**
     * Interface iLoaderView.
     */
    public interface iLoaderView extends PopupView,View{
        void showWidget();
        void startProcessing();
        void stopProcessing();
    }
 
    @SuppressWarnings("unused")
	private final iLoaderView view;
    private final EventBus eventBus;



    /**
     * Istanzia un nuovo loader controller.
     *
     * @param view  view
     * @param eventBus  event bus
     */
    @Inject
    public LoaderController(iLoaderView view, EventBus eventBus){
        super(eventBus,view);
        this.view = view;
        this.eventBus = eventBus;
		getView().stopProcessing();
    }
 
    @Override
    protected void onBind(){
		super.onBind();
		GWT.log("onBind di LoaderController");
    	
		getView().stopProcessing();

		registerHandler(eventBus.addHandler(CustomAsyncCallStartEvent.getType(), new CustomAsyncCallStartEventHandler() {
			@Override
			public void onAsyncCallStart(CustomAsyncCallStartEvent event) {
				GWT.log("onAsyncCallStart di LoaderController");
				getView().startProcessing();		
			}
		}));
		registerHandler(eventBus.addHandler(CustomAsyncCallFailEvent.getType(), new CustomAsyncCallFailEventHandler() {
			@Override
			public void onAsyncCallFail(CustomAsyncCallFailEvent event) {
				GWT.log("onAsyncCallFail di LoaderController");
				getView().stopProcessing();		
			}
		}));
		registerHandler(eventBus.addHandler(CustomAsyncCallSucceedEvent.getType(), new CustomAsyncCallSucceedEventHandler() {
			@Override
			public void onAsyncCallSucceed(CustomAsyncCallSucceedEvent event) {
				GWT.log("onAsyncCallSucceed di LoaderController");
				getView().stopProcessing();		
			}
		}));
    }
}