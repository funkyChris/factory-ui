package org.qualipso.factory.ui.core.utils.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.ui.Widget;

public class Registerer extends JavaScriptObject {

    protected Registerer() {

    }

    public final native void registerWidget(String name, FactoryWidget widget) /*-{
        this.widgets[name] = {};
        this.widgets[name].instance = widget;
    }-*/;

    public final native FactoryWidget getRegisteredWidget(String name) /*-{
        if (this.widgets[name]) {
            return this.widgets[name].instance;
        } else {
            return null;
        }
    }-*/;

    public final native JsArrayString getAllRegisteredWidgetsNames() /*-{
        var names = new Array();
        for (var name in this.widgets) {
            names.push(name);
        }
        return names;
    }-*/;

    public final native void refreshAllRegisteredWidgets() /*-{
        for (var name in this.widgets) {
            this.widgets[name].refresh();
        }
    }-*/;

    public final native void registerService(String name) /*-{
        this.services[name] = {};
        this.services[name].resources = {};
    }-*/;
    
   

}
