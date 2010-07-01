package org.opennms.features.poller.remote.gwt.client;

import java.util.Collection;

import org.opennms.features.poller.remote.gwt.client.events.ApplicationDeselectedEvent;
import org.opennms.features.poller.remote.gwt.client.events.ApplicationSelectedEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * <p>FilterPanel class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public class FilterPanel extends Composite {

    interface Binder extends UiBinder<Widget, FilterPanel> { }
    private static final Binder BINDER = GWT.create(Binder.class);

    private transient HandlerManager m_eventBus;
    private transient LocationManager m_locationManager;

    interface FilterStyles extends CssResource {
        String panelCaption();
        String panelEntry();
        String panelIcon();
    }

    @UiField
    FilterStyles filterStyles;
    @UiField(provided = true)
    SuggestBox applicationNameSuggestBox;
    @UiField
    Panel applicationTray;
    @UiField 
    Label noApplicationsCaption;
    @UiField
    Panel applicationFilters;
    @UiField
    ToggleButton upButton;
    @UiField
    ToggleButton marginalButton;
    @UiField
    ToggleButton downButton;
    @UiField
    ToggleButton unknownButton;

    private final MultiWordSuggestOracle applicationNames = new MultiWordSuggestOracle();
    
    private class ApplicationFilter extends HorizontalPanel {
        public ApplicationFilter(final ApplicationInfo app) {
            Image appIcon = new Image(new GWTMarkerState("filter", null, app.getStatusDetails().getStatus()).getImageURL());
            appIcon.addStyleName(filterStyles.panelIcon());
            super.add(appIcon);
            Label appName = new Label(app.getName());
            appName.addStyleName(filterStyles.panelCaption());
            super.add(appName);
            Anchor removeLink = new Anchor("remove");
            removeLink.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    m_eventBus.fireEvent(new ApplicationDeselectedEvent(app));
                }
            });
            super.add(removeLink);
            super.addStyleName(filterStyles.panelEntry());
        }
    }

    public interface FiltersChangedEventHandler extends EventHandler {
        public void onFiltersChanged(Filters filters);
    }

    public static class Filters {}

    public static class FiltersChangedEvent extends GwtEvent<FiltersChangedEventHandler>
    {
        public static Type<FiltersChangedEventHandler> TYPE = new Type<FiltersChangedEventHandler>();

        private final Filters m_filters;

        public FiltersChangedEvent(Filters filters) {
            m_filters = filters;
        }

        protected void dispatch(FiltersChangedEventHandler handler) {
            handler.onFiltersChanged(m_filters);
        }

        public GwtEvent.Type<FiltersChangedEventHandler> getAssociatedType() {
            return TYPE;
        }
    }

    public interface StatusSelectionChangedEventHandler extends EventHandler {
        public void onStatusSelectionChanged(Status status, boolean selected);
    }

    public static class StatusSelectionChangedEvent extends GwtEvent<StatusSelectionChangedEventHandler>
    {
        public static Type<StatusSelectionChangedEventHandler> TYPE = new Type<StatusSelectionChangedEventHandler>();

        private final Status m_status;
        private final boolean m_selected;

        public StatusSelectionChangedEvent(Status status, boolean selected) {
            m_status = status;
            m_selected = selected;
        }

        protected void dispatch(StatusSelectionChangedEventHandler handler) {
            handler.onStatusSelectionChanged(m_status, m_selected);
        }

        public GwtEvent.Type<StatusSelectionChangedEventHandler> getAssociatedType() {
            return TYPE;
        }
    }

    /**
     * <p>Constructor for FilterPanel.</p>
     */
    public FilterPanel() {
        super();
        applicationNameSuggestBox = new SuggestBox(applicationNames);
        initWidget(BINDER.createAndBindUi(this));
        upButton.setDown(true);
        marginalButton.setDown(true);
        downButton.setDown(true);
        unknownButton.setDown(true);
    }

    /**
     * <p>onApplicationSelect</p>
     *
     * @param event a {@link com.google.gwt.event.logical.shared.SelectionEvent} object.
     */
    @UiHandler("applicationNameSuggestBox") 
    public void onApplicationSelect(final SelectionEvent<Suggestion> event) {
        Suggestion item = event.getSelectedItem();
        ApplicationInfo app = m_locationManager.getApplicationInfo(item.getReplacementString());
        m_eventBus.fireEvent(new ApplicationSelectedEvent(app.getName()));
    }

    /**
     * <p>onUpButtonClick</p>
     *
     * @param event a {@link com.google.gwt.event.dom.client.ClickEvent} object.
     */
    @UiHandler("upButton") 
    public void onUpButtonClick(ClickEvent event) {
        m_eventBus.fireEvent(new StatusSelectionChangedEvent(Status.UP, upButton.isDown()));
    }

    /**
     * <p>onMarginalButtonClick</p>
     *
     * @param event a {@link com.google.gwt.event.dom.client.ClickEvent} object.
     */
    @UiHandler("marginalButton") 
    public void onMarginalButtonClick(ClickEvent event) {
        m_eventBus.fireEvent(new StatusSelectionChangedEvent(Status.MARGINAL, marginalButton.isDown()));
    }

    /**
     * <p>onDownButtonClick</p>
     *
     * @param event a {@link com.google.gwt.event.dom.client.ClickEvent} object.
     */
    @UiHandler("downButton") 
    public void onDownButtonClick(ClickEvent event) {
        m_eventBus.fireEvent(new StatusSelectionChangedEvent(Status.DOWN, downButton.isDown()));
    }

    /**
     * <p>onUnknownButtonClick</p>
     *
     * @param event a {@link com.google.gwt.event.dom.client.ClickEvent} object.
     */
    @UiHandler("unknownButton") 
    public void onUnknownButtonClick(ClickEvent event) {
        m_eventBus.fireEvent(new StatusSelectionChangedEvent(Status.UNKNOWN, unknownButton.isDown()));
    }

    /**
     * <p>updateApplicationNames</p>
     *
     * @param names a {@link java.util.Collection} object.
     */
    public void updateApplicationNames(Collection<String> names) {
        // Update the SuggestBox's Oracle
        applicationNames.clear();
        applicationNames.addAll(names);
    }

    /**
     * <p>updateSelectedApplications</p>
     *
     * @param apps a {@link java.util.Collection} object.
     */
    public void updateSelectedApplications(Collection<ApplicationInfo> apps) {
        // Update the contents of the application filter list
        applicationFilters.clear();
        if (apps.size() > 0) {
            noApplicationsCaption.setVisible(false);
            for (ApplicationInfo app : apps) {
                applicationFilters.add(new ApplicationFilter(app));
            }
        } else {
            noApplicationsCaption.setVisible(true);
        }
    }
    
    /**
     * <p>showApplicationFilters</p>
     *
     * @param showMe a boolean.
     */
    public void showApplicationFilters(boolean showMe) {
        applicationTray.setVisible(showMe);
    }

    /**
     * <p>setEventBus</p>
     *
     * @param eventBus a {@link com.google.gwt.event.shared.HandlerManager} object.
     */
    public void setEventBus(final HandlerManager eventBus) {
        m_eventBus = eventBus;
    }

    /**
     * <p>setLocationManager</p>
     *
     * @param manager a {@link org.opennms.features.poller.remote.gwt.client.LocationManager} object.
     */
    public void setLocationManager(final LocationManager manager) {
        m_locationManager = manager;
    }
}
