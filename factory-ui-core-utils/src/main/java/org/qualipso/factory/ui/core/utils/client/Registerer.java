package org.qualipso.factory.ui.core.utils.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Registerer extends JavaScriptObject {

    protected Registerer() {
    }

    public final native void refreshAllRegisteredWidgets() /*-{
        for (var name in this.widgets) {
            this.widgets[name].refresh();
        }
    }-*/;

    public final native void loadWidgetForResourceAndService(String service, String resource, String path, String slot) /*-{
        if ((! this.services[service]) || (! this.services[service].resources) || (! this.services[service].resources[resource])) {
            return null;
        }
        this.services[service].resources[resource].load(slot, path);
    }-*/;
    
}
