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
package org.qualipso.factory.ui.core.login.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User interface for the Login core service. Provide a user form for login and password, and call the Login servlet to check the login.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 6 November 2009
 */
public class Login implements EntryPoint {

    private final LoginServletAsync loginServlet = GWT.create(LoginServlet.class);
    private LoginPanel loginPanel;

    /**
     * Entry point, method called when the module is loaded. Set up the form in the element of id "login".
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    @Override
    public void onModuleLoad() {
        loginPanel = new LoginPanel(this);
        RootPanel.get("loginComponent").add(loginPanel);
    }

    
    /**
     * Do the real login through the servlet.
     * 
     * @param username      the username
     * @param password       the password
     */
    public void login(final String username, final String password) {
        loginServlet.login(username, password, new AsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean logged) {
                if (logged) {
                    RootPanel.get("loginComponent").remove(loginPanel);
                    RootPanel.get("loginComponent").add(new Label("Welcome, " + username));
                    return;
                }
                loginPanel.error("Login failed.");
            }

            @Override
            public void onFailure(Throwable ex) {
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setText("Something bad happened...");
                VerticalPanel dialogVPanel = new VerticalPanel();
                dialogVPanel.add(new Label("An error occured while trying to log in to the Factory server:"));
                dialogVPanel.add(new Label(ex.toString()));
                final Button closeButton = new Button("Close");
                dialogVPanel.add(closeButton);
                dialogBox.setWidget(dialogVPanel);
                
                closeButton.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        dialogBox.hide();
                    }
                });
                
                dialogBox.center();
                closeButton.setFocus(true);
            }
        });
    }

}
