/*
 * Qualipso Factory UI
 * Copyright (C) 2006-2010 INRIA
 * http://www.inria.fr
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation. See the GNU
 * Lesser General Public License in LGPL.txt for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * Initial authors :
 *
 *  Jérôme Blanchard / INRIA
 * Christophe Bouthier / INRIA
 */
package org.qualipso.factory.ui.core.utils.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Utils classes for the factor..
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 3 December 2009
 */
public class Utils {

    public static void registerWidget(String name, FactoryWidget widget) {
        Registerer registerer = getRegistererInstance();
        registerer.registerWidget(name, widget);
    }

    public static void registerService(String name) {
        Registerer registerer = getRegistererInstance();
        registerer.registerService(name);
    }
    
    
    public static void loadService(String name) {
        addScript(name + "/" + name + ".nocache.js");
    }
    
    public static FactoryWidget getRegisteredWidget(String name) {
        Registerer registerer = getRegistererInstance();
        return registerer.getRegisteredWidget(name);
    }

    public static void refreshAllRegisteredWidgets() {
        Registerer registerer = getRegistererInstance();
        registerer.refreshAllRegisteredWidgets();
    }

    public final static native Registerer getRegistererInstance() /*-{
        var registerer = $wnd.qf_registerer;
        if (registerer == null) {
            registerer = {};
            registerer.widgets = {};
            registerer.services =  {};
            $wnd.qf_registerer = registerer;
        }
        return registerer;
    }-*/;

    private static void addScript(String url) {
        Element e = DOM.createElement("script");
        e.setAttribute("type", "text/javascript");
        e.setAttribute("language", "JavaScript");
        e.setAttribute("src", url);
        DOM.appendChild(RootPanel.getBodyElement(), e);
      }

    public static void registerServiceResource(String string, String string2) {
        // TODO Auto-generated method stub
        
    }

    public static Widget getRegisteredServiceResourceWidget(String service, String type) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
