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
package org.qualipso.factory.ui.core.membership.client;

import org.qualipso.factory.ui.core.utils.client.Registerer;
import org.qualipso.factory.ui.core.utils.client.Utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * User interface for the Browser core service. Provide a tree displaying the content of the naming tree, using the Browser servlet to access the factory.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 7 December 2009
 */
public class Membership implements EntryPoint {

    /**
     * Entry point, method called when the module is loaded. Create the tree.
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        GWT.log("Loaded Membership", null);
        registerResourceWidgets(Utils.getRegistererInstance(), this);
    }

    private final native void registerResourceWidgets(Registerer registerer, Membership core) /*-{
        registerer.services["membership"] = {};
        registerer.services["membership"].resources = {};
        registerer.services["membership"].resources["profile"] = {};
        registerer.services["membership"].resources["profile"].load = function(slot, data) {
            return core.@org.qualipso.factory.ui.core.membership.client.Membership::loadProfileWidget(Ljava/lang/String;Ljava/lang/String;)(slot, data);
        }
    }-*/;

    public void loadProfileWidget(String slot, String data) {
        Label l = new Label("Profile: " + data);
        RootPanel.get(slot).add(l);
    }

}
