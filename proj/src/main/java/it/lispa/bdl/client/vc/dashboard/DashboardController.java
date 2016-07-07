package it.lispa.bdl.client.vc.dashboard;

import it.lispa.bdl.client.events.ChangeActiveEnteEvent;
import it.lispa.bdl.client.place.NameTokens;
import it.lispa.bdl.client.ui.LayoutController;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.services.AuthService;
import it.lispa.bdl.shared.services.AuthServiceAsync;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;

/**
 * Class DashboardController.
 */
public class DashboardController extends Presenter<DashboardController.iDashboardView, DashboardController.iDashboardProxy> {

	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	/* Interfaccia condivisa tra la vista e il controller*/
	/**
	 * Interface iDashboardView.
	 */
	public interface iDashboardView extends View {
		ComboBox<ComboDTO> getCmbEnti();
		void setUserIsMultiente(Boolean isMultiEnte);
	}

	/* Proxy di navigazione */
	/**
	 * Interface iDashboardProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.Dashboard)
	public interface iDashboardProxy extends ProxyPlace<DashboardController> {

	}
	private final EventBus eventBus;

	/**
	 * Istanzia un nuovo dashboard controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 * @param proxy  proxy
	 */
	@Inject
	public DashboardController (final EventBus eventBus, final iDashboardView view, final iDashboardProxy proxy) {
		super(eventBus, view, proxy);
		this.eventBus = eventBus;
	}

	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutController.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		GWT.log("onBind di Dashboard");

		SelectionHandler<ComboDTO> selectHandler = new SelectionHandler<ComboDTO>() {
			@Override
			public void onSelection(SelectionEvent<ComboDTO> event) {
				ComboDTO mnu = event.getSelectedItem();
				GWT.log("Voce del della combo enti selezionata: " + mnu.getId());

				authService.activateEnte(mnu.getId(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Non fare nulla: l'errore generico viene gestito a monte...
						// e il metodo non tira un AsyncServiceException
					}
					@Override
					public void onSuccess(String result) {
						eventBus.fireEvent(new ChangeActiveEnteEvent(result));
					}
				});

			}
		};
		getView().getCmbEnti().addSelectionHandler(selectHandler);
		getView().getCmbEnti().setWidth("300px");
		refreshMultiEnte();
	}

	@Override
	protected void onReset() {
		super.onReset();
		GWT.log("onReset di Dashboard");

		refreshMultiEnte();
	}
	
	private void refreshMultiEnte(){
		GWT.log("Imposto la UserIsMultiente della Dashboard view a false preventivamente");
		getView().setUserIsMultiente(false);
		authService.getEntiAttivabiliCmbCatalogatore(new AsyncCallback<List<ComboDTO>>() {
			public void onFailure(Throwable caught) {
				// Non fare nulla: l'errore generico viene gestito a monte...
				// e il metodo non tira un AsyncServiceException
			}
			@Override
			public void onSuccess(List<ComboDTO> result) {
				if(result.size()>1){
					GWT.log("Imposto la UserIsMultiente della Dashboard view a true essendoci più enti");
					getView().setUserIsMultiente(true);
					getView().getCmbEnti().getStore().clear();
					getView().getCmbEnti().getStore().addAll(result);
					for (ComboDTO cmbItem : result){
						if(cmbItem.isSelected()){
							GWT.log("Imposto come selezionato "+cmbItem.getId()+ " - "+cmbItem.getDesc());
							getView().getCmbEnti().setValue(cmbItem);
						}
					}
				}else{
					GWT.log("Imposto la UserIsMultiente della Dashboard view a false essendoci un solo ente");
					getView().setUserIsMultiente(false);
				}
			}
		});	
	}
}