package it.lispa.bdl.client.ui;



import it.lispa.bdl.client.events.ChangeActiveEnteEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;


/**
 * Class LayoutController.
 */
public class LayoutController extends Presenter<LayoutController.iLayoutView, LayoutController.LayoutProxy>{

	final PlaceManager placeManager;

	/** La Costante SLOT_content. */
	@ContentSlot public static final Type<RevealContentHandler<?>> SLOT_content = new Type<RevealContentHandler<?>>();

	@Inject MenuController cMenu;
	@Inject HeaderController cHeader;
	@Inject FooterController cFooter;
	@Inject LoaderController cLoader;

	/** La Costante SLOT_sidebar. */
	public static final Object SLOT_sidebar = new Object();
	
	/** La Costante SLOT_header. */
	public static final Object SLOT_header = new Object();
	
	/** La Costante SLOT_footer. */
	public static final Object SLOT_footer = new Object();
	
	/** La Costante SLOT_notMenu. */
	public static final Object SLOT_notMenu = new Object();

	/**
	 * Interface iLayoutView.
	 */
	public interface iLayoutView extends View{

	}

	/**
	 * Interface LayoutProxy.
	 */
	@ProxyCodeSplit
	public interface LayoutProxy extends Proxy<LayoutController> {

	}


	/**
	 * Istanzia un nuovo layout controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 * @param plcManager  plc manager
	 */
	@Inject
	public LayoutController(final EventBus eventBus, final iLayoutView view, final LayoutProxy proxy, final PlaceManager plcManager){
		super(eventBus, view, proxy);
		this.placeManager = plcManager;
		eventBus.addHandler(ChangeActiveEnteEvent.TYPE,
				new ChangeActiveEnteEvent.ChangeActiveEnteEventHandler() {
			@Override
			public void onChangeActiveEnte(ChangeActiveEnteEvent event) {
				GWT.log("onEditContact di Layout");
				cHeader.getView().setDatiUtente(event.getDatiUtente());
			}
		});
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);

	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Layout");
		
		//addToPopupSlot(cLoader);
	}

	@Override
	protected void onReset() {       
		super.onReset();
		GWT.log("onReset di Layout");

		setInSlot(SLOT_header, cHeader);
		setInSlot(SLOT_sidebar, cMenu);
		setInSlot(SLOT_footer, cFooter);
		
	}
}