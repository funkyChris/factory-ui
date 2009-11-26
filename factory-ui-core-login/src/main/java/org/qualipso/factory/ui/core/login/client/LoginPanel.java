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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Login panel.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 10 November 2009
 */
public class LoginPanel extends Composite {
    
    interface MyUiBinder extends UiBinder<VerticalPanel, LoginPanel> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    
    @UiField Label errorLabel;
    @UiField TextBox usernameTextBox;
    @UiField PasswordTextBox passwordTextBox;
    @UiField Button loginButton;
    
    private Login loginManager;
    
    public LoginPanel(Login loginManager) {
        initWidget(uiBinder.createAndBindUi(this));
        this.loginManager = loginManager;
    }

    @UiHandler("loginButton")
    public void onClick(ClickEvent arg0) {
        loginManager.login(usernameTextBox.getText(), passwordTextBox.getText());
    }

    public void error(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
 
}
