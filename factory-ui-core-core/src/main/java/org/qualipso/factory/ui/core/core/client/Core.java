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
package org.qualipso.factory.ui.core.core.client;

import org.qualipso.factory.ui.core.utils.client.Registerer;
import org.qualipso.factory.ui.core.utils.client.Utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * User interface for the Browser core service. Provide a tree displaying the content of the naming tree, using the Browser servlet to access the factory.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 4 December 2009
 */
public class Core implements EntryPoint {

    /**
     * Entry point, method called when the module is loaded. Create the tree.
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        Utils.registerService("core");
        Utils.registerServiceResource("core", "folder");
        Utils.registerServiceResource("core", "file");
        
        registerResourceWidgets(Utils.getRegistererInstance(), this);
        
        GWT.log("Loaded Core", null);
    }
    
    private final native void registerResourceWidgets(Registerer registerer, Core core) /*-{
        registerer.service["core"].resources["file"].widget = function() {
            return core.@org.qualipso.factory.ui.core.core.client.Core::getFileWidget()();
        }
        registerer.service["core"].resources["folder"].widget = function() {
            return core.@org.qualipso.factory.ui.core.core.client.Core::getFolderWidget()();
        }
    }-*/;

    public Widget getFileWidget() {
        return null;
    }
    
    public Widget getFolderWidget() {
        return null;
    }
}
