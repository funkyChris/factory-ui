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
package org.qualipso.factory.ui.core.browser.client;

import org.qualipso.factory.ui.core.utils.client.FactoryWidget;
import org.qualipso.factory.ui.core.utils.client.Registerer;
import org.qualipso.factory.ui.core.utils.client.Utils;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * User interface for the Browser core service. Provide a tree displaying the content of the naming tree, using the Browser servlet to access the factory.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 30 November 2009
 */
public class Browser  implements EntryPoint, FactoryWidget {

    private final BrowserServletAsync browserServlet = GWT.create(BrowserServlet.class);

    private BrowserPanel browserPanel;
    private TreeItem rootNode;

    /**
     * Entry point, method called when the module is loaded. Create the tree.
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        browserPanel = new BrowserPanel(this);
        RootPanel.get("browserComponent").add(browserPanel);
        refresh();
        Utils.registerWidget("browser", this);
        registerRefresh(Utils.getRegistererInstance(), this);
    }
    
    private final native void registerRefresh(Registerer registerer, Browser browser) /*-{
        registerer.widgets["browser"].refresh = function() {
            return browser.@org.qualipso.factory.ui.core.browser.client.Browser::refresh()();
        }
    }-*/;

    public void refresh() {
        rootNode = new TreeItem("/");
        getChildrenOf("/", rootNode);
        browserPanel.refresh(rootNode);
    }

    private void getChildrenOf(final String path, final TreeItem node) {
        browserServlet.hasChildren(path, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable exception) {
                return;
            }

            @Override
            public void onSuccess(Boolean hasChildren) {
                if ((hasChildren != null) && (hasChildren)) {
                    buildChildrenOf(path, node);
                }
            }
        });
    }

    private void buildChildrenOf(final String path, final TreeItem node) {
        browserServlet.getChildren(path, new AsyncCallback<String[]>() {

            @Override
            public void onFailure(Throwable exception) {
                GWT.log("something wrong happened", null);
                return;
            }

            @Override
            public void onSuccess(String[] children) {
                if (children != null) {
                    buildRecursivelyChildren(node, path, children);
                }
            }
        });
    }

    private void buildRecursivelyChildren(TreeItem node, String path, String[] children) {
        for (int i = 0; i < children.length; i++) {
            TreeItem nodeChildren = new TreeItem(children[i]);
            node.addItem(nodeChildren);
            getChildrenOf(children[i], nodeChildren);
        }
    }

}
