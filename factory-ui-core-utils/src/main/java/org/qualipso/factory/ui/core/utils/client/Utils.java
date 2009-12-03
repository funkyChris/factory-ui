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
            $wnd.qf_registerer = registerer;
        }
        return registerer;
    }-*/;

}
