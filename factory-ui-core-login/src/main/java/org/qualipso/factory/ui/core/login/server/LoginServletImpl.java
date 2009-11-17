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

import java.util.Properties;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.auth.callback.UsernamePasswordHandler;
import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.membership.MembershipService;
import org.qualipso.factory.membership.MembershipServiceException;
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
        logger.info("User " + username + " trying to log on the factory...");

        // get the naming context for lookup factory services
        final Context namingContext;
        try {
            final Properties properties = new Properties();
            properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
            properties.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
            properties.put("java.naming.provider.url","localhost:1099");
            namingContext = new InitialContext(properties);
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory through naming. Caused by: ", ne);
            return false;
        }
        
        // get the membership service
        final MembershipService membership;
        try {
            membership = (MembershipService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService("MembershipService"));
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory membership service. Caused by: ", ne);
            return false;
        }
        
        // test if the login context is valid by trying to call the membership service
        LoginContext loginContext;
        try {
            loginContext = new LoginContext("qualipso", new UsernamePasswordHandler(username, password));
            loginContext.login();
        } catch (LoginException le) {
            logger.error("Cannot manage to use the login context. Caused by: ", le);
            return false;
        }
        
        final String profilePath;
        try {
            profilePath = membership.getProfilePathForConnectedIdentifier();
            logger.info("Profile path for user "  + username + ": " + profilePath);
        } catch (MembershipServiceException e) {
            logger.error("Cannot manage to call Factory membership service. Caused by: ", e);
            return false;
        } catch (EJBAccessException no) {
            // login is invalid
            logger.info("Login failed for user " + username);
            return false;
        }
        
        // if we're here, the login is valid. Put it in the session.
        HttpSession session =  getThreadLocalRequest().getSession();
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        logger.info("User " + username + " logged in, with profile path " + profilePath);
        
        return true;
    }
    
}
