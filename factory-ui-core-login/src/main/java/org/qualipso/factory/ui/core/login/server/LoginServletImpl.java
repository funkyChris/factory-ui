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
package org.qualipso.factory.ui.core.login.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.qualipso.factory.ui.core.login.client.LoginServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the server part for the Login core service.
 * Check the login and password information on the Factory, using JAAS. 
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 6 November 2009
 */
@SuppressWarnings("serial")
public class LoginServletImpl extends RemoteServiceServlet implements LoginServlet {

    // Logger
    private static Log logger = LogFactory.getLog(LoginServletImpl.class);
    
    /**
     * Try to log in the factory using the given username and password.
     * 
     * @see org.qualipso.factory.ui.core.login.client.LoginServlet#login(java.lang.String, java.lang.String)
     * 
     * @param username      the username
     * @param password       the password
     * @return                       true if the user information allow him to log in, false otherwise
     */
    public Boolean login(String username, String password) {
        Boolean loggedIn = false;
        
        logger.info("User " + username + " trying to log on the factory...");
        
        logger.info("Login failed for user " + username);
        return loggedIn;
    }
    
}
