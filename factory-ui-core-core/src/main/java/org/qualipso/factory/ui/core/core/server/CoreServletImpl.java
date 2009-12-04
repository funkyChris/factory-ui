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
package org.qualipso.factory.ui.core.core.server;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.auth.callback.UsernamePasswordHandler;
import org.qualipso.factory.ui.core.core.client.CoreServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the server part for the Browser core service. Access the Factory browsing service to browse through the naming tree.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 4 December 2009
 */
@SuppressWarnings("serial")
public class CoreServletImpl extends RemoteServiceServlet implements CoreServlet {

    private static final String USERNAME_SESSION_ATTRIBUTE = "USERNAME_SESSION_ATTRIBUTE"; // session attribute for username
    private static final String PASSWORD_SESSION_ATTRIBUTE = "PASSWORD_SESSION_ATTRIBUTE"; // session attribute for password

    // Logger
    private static Log logger = LogFactory.getLog(CoreServletImpl.class);

    private LoginContext loginContext;

  

    private void login() {
        String username  = null;
        String password = null;

        // check to see if there's a session existing
        HttpSession session = getThreadLocalRequest().getSession(false);
        if (session != null) {
            username = (String) session.getAttribute(USERNAME_SESSION_ATTRIBUTE);
            password = (String) session.getAttribute(PASSWORD_SESSION_ATTRIBUTE);
            logger.info("Getting log info from cookie : login for user " + username);
        }
//        // DEBUG
//        else {
//            logger.info("Using debug mode, login as root");
//            username = "root";
//            password = "tagada";
//        }

        // create the login context
        if ((username != null) && (password != null)) {
            try {
                loginContext = new LoginContext("qualipso", new UsernamePasswordHandler(username, password));
                loginContext.login();
            } catch (LoginException le) {
                logger.error("Cannot manage to use the login context. Caused by: ", le);
            }
        }
    }

    private void logout() {
        try {
            if (loginContext != null) {
                loginContext.logout();
            }
        } catch (LoginException le) {
            // just log, don't do anything else
            logger.error("Problem logging out after testing correct login. Caused by: ", le);
        }
    }

}
