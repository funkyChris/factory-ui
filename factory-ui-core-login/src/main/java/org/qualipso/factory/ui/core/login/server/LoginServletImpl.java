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
import org.qualipso.factory.bootstrap.BootstrapService;
import org.qualipso.factory.bootstrap.BootstrapServiceException;
import org.qualipso.factory.browser.BrowserService;
import org.qualipso.factory.browser.BrowserServiceException;
import org.qualipso.factory.membership.MembershipService;
import org.qualipso.factory.membership.MembershipServiceException;
import org.qualipso.factory.ui.core.login.client.LoginServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the server part for the Login core service. Check the login and password information on the Factory, using JAAS.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 6 November 2009
 */
@SuppressWarnings("serial")
public class LoginServletImpl extends RemoteServiceServlet implements LoginServlet {

    private static final String BOOTSTRAPPED_FLAG = "BOOTSTRAPPED_FLAG";                                        // application attribute to check for factory bootstrap
    private static final String USERNAME_SESSION_ATTRIBUTE = "USERNAME_SESSION_ATTRIBUTE";      // session attribute for username
    private static final String PASSWORD_SESSION_ATTRIBUTE = "PASSWORD_SESSION_ATTRIBUTE";      // session attribute for password

    // Logger
    private static Log logger = LogFactory.getLog(LoginServletImpl.class);

    /**
     * Try to log in the factory using the given username and password.
     * 
     * @see org.qualipso.factory.ui.core.login.client.LoginServlet#login(java.lang.String, java.lang.String)
     * 
     * @param username
     *            the username
     * @param password
     *            the password
     * @return true if the user information allow him to log in, false otherwise
     */
    public Boolean login(String username, String password) {
        logger.info("User " + username + " trying to log on the factory...");

        // clean old login session if necessary
        HttpSession session = getThreadLocalRequest().getSession(false);
        if (session != null) {
            session.removeAttribute(USERNAME_SESSION_ATTRIBUTE);
            session.removeAttribute(PASSWORD_SESSION_ATTRIBUTE);
            session.invalidate();
        }
           
        // get the naming context for lookup factory services
        final Context namingContext;
        try {
            final Properties properties = new Properties();
            properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
            properties.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
            properties.put("java.naming.provider.url", "localhost:1099");
            namingContext = new InitialContext(properties);
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory through naming. Caused by: ", ne);
            return false;
        }

        // flag to check if we need to bootstrap the factory or not
        boolean needBootstrap = true;

        // check the application context to see if the bootstrap has already been done
        // thanks to Jerome for this piece of code
        String bootstrapped = (String) getThreadLocalRequest().getSession().getServletContext().getAttribute(BOOTSTRAPPED_FLAG);
        if (bootstrapped == null) {
            logger.info("No bootstrap flag found in the application context.");
            needBootstrap = true;
        } else {
            logger.info("Bootstrap flag found in the application context, no need to bootstrap.");
            needBootstrap = false;
        }

        // check if there's a bootstrap node in the naming
        if (needBootstrap) {
            try {
                BrowserService browser = (BrowserService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(BrowserService.SERVICE_NAME));
                browser.findResource(BootstrapService.BOOTSTRAP_FILE_PATH);
                getThreadLocalRequest().getSession().getServletContext().setAttribute(BOOTSTRAPPED_FLAG, BOOTSTRAPPED_FLAG);
                logger.info("Bootstrap node found in the naming, no need to bootstrap.");
                needBootstrap = false;
            } catch (NamingException ne) {
                logger.error("Cannot manage to access Factory browser service. Caused by: ", ne);
                return false;
            } catch (BrowserServiceException be) {
                logger.info("No bootstrap node found in the naming.");
                needBootstrap = true;
            }
        }

        // get the bootstrap and call it
        if (needBootstrap) {
            logger.info("Bootstrap of the factory is needed, in progress....");
            try {
                BootstrapService bootstrap = (BootstrapService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(BootstrapService.SERVICE_NAME));
                bootstrap.bootstrap();
                getThreadLocalRequest().getSession().getServletContext().setAttribute(BOOTSTRAPPED_FLAG, BOOTSTRAPPED_FLAG);
                logger.info("Bootstrap of the factory done.");
            } catch (NamingException ne) {
                logger.error("Cannot manage to access Factory bootstrap service. Caused by: ", ne);
                return false;
            } catch (BootstrapServiceException bse) {
                logger.error("Cannot manage to call Factory bootstrap service. Caused by: ", bse);
                return false;
            }
        }

        // get the membership service
        final MembershipService membership;
        try {
            membership = (MembershipService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(MembershipService.SERVICE_NAME));
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory membership service. Caused by: ", ne);
            return false;
        }

        // create a login context
        LoginContext loginContext;
        try {
            loginContext = new LoginContext("qualipso", new UsernamePasswordHandler(username, password));
            loginContext.login();
        } catch (LoginException le) {
            logger.error("Cannot manage to use the login context. Caused by: ", le);
            return false;
        }

        // test if the login context is valid by trying to call the membership service
        final String profilePath;
        try {
            profilePath = membership.getProfilePathForConnectedIdentifier();
            logger.info("Profile path for user " + username + ": " + profilePath);
        } catch (MembershipServiceException e) {
            logger.error("Cannot manage to call Factory membership service. Caused by: ", e);
            return false;
        } catch (EJBAccessException no) {
            // login is invalid
            logger.info("Login failed for user " + username);
            return false;
        }

        // if we're here, the login is valid. Put it in the session.
        session = getThreadLocalRequest().getSession();
        session.setAttribute(USERNAME_SESSION_ATTRIBUTE, username);
        session.setAttribute(PASSWORD_SESSION_ATTRIBUTE, password);
        logger.info("User " + username + " logged in, with profile path " + profilePath);

        // log out
        try {
            loginContext.logout();
        } catch (LoginException le) {
            // just log, don't do anything else
            logger.error("Problem logging out after testing correct login. Caused by: ", le);
        }
        
        return true;
    }

    /**
     * Log out from the session.
     * 
     * @see org.qualipso.factory.ui.core.login.client.LoginServlet#logout()
     */
    public void logout() {
        HttpSession session = getThreadLocalRequest().getSession(false);
        if (session != null) {
            session.removeAttribute(USERNAME_SESSION_ATTRIBUTE);
            session.removeAttribute(PASSWORD_SESSION_ATTRIBUTE);
            session.invalidate();
        }    
    }

    public int methodToTest() {
        return 1;
    }

}
