package it.lispa.bdl.client.vc.dashboard;

import it.lispa.bdl.shared.dto.ComboDTO;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.ComboBox;

/**
 * Class DashboardView.
 */
public class DashboardView extends ViewImpl implements DashboardController.iDashboardView {

	/**
	 * Interface ComboEntiProperties.
	 */
	public interface ComboEntiProperties extends PropertyAccess<ComboDTO> {
		ModelKeyProvider<ComboDTO> id();
		LabelProvider<ComboDTO> desc();
		@Path("desc")
		ValueProvider<ComboDTO, String> descProp();		
	}
	ComboEntiProperties cmbEntiProps = GWT.create(ComboEntiProperties.class);

	@UiField(provided = true)
	LabelProvider<ComboDTO> labelProvider = cmbEntiProps.desc();

	@UiField HTMLPanel panelEnti;
	
	@UiField ContentPanel panel;

	@UiField(provided = true)
	ListStore<ComboDTO> cmbEntiStore = new ListStore<ComboDTO>(cmbEntiProps.id());

	@UiField
	ComboBox<ComboDTO> cmbEnti;

    private final Widget widget;

    /**
     * Interface Binder.
     */
    public interface Binder extends UiBinder<Widget, DashboardView> {
    	
    }

    /**
     * Istanzia un nuovo dashboard view.
     *
     * @param binder  binder
     */
    @Inject
    public DashboardView(final Binder binder) {
            widget = binder.createAndBindUi(this);
    }

    /* (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#asWidget()
     */
    public Widget asWidget() {
            return widget;
    }
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.dashboard.DashboardController.iDashboardView#getCmbEnti()
	 */
	@Override
	public ComboBox<ComboDTO> getCmbEnti() {
		return cmbEnti;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.dashboard.DashboardController.iDashboardView#setUserIsMultiente(java.lang.Boolean)
	 */
	@Override
	public void setUserIsMultiente(Boolean isMultiEnte) {
		panelEnti.setVisible(isMultiEnte);
		panel.forceLayout();
		
	}
}