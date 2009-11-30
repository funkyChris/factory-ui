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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Browser panel.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 30 November 2009
 */
public class BrowserPanel extends Composite {
    
    interface MyUiBinder extends UiBinder<VerticalPanel, BrowserPanel> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
   
    @UiField Tree tree;
    
    private Browser browserManager;
    
    public BrowserPanel(Browser browserManager) {
        initWidget(uiBinder.createAndBindUi(this));
        this.browserManager = browserManager;
    }

    public void refresh(TreeItem rootItem) {
        tree.removeItems();
        tree.addItem(rootItem);
    }
 
}
