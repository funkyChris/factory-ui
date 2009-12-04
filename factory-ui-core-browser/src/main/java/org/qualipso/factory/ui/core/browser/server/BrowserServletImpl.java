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
package org.qualipso.factory.ui.core.browser.server;

import java.util.Properties;

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
import org.qualipso.factory.FactoryResource;
import org.qualipso.factory.binding.InvalidPathException;
import org.qualipso.factory.binding.PathNotFoundException;
import org.qualipso.factory.browser.BrowserService;
import org.qualipso.factory.browser.BrowserServiceException;
import org.qualipso.factory.security.pep.AccessDeniedException;
import org.qualipso.factory.ui.core.browser.client.BrowserServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of the server part for the Browser core service. Access the Factory browsing service to browse through the naming tree.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 30 November 2009
 */
@SuppressWarnings("serial")
public class BrowserServletImpl extends RemoteServiceServlet implements BrowserServlet {

    private static final String USERNAME_SESSION_ATTRIBUTE = "USERNAME_SESSION_ATTRIBUTE"; // session attribute for username
    private static final String PASSWORD_SESSION_ATTRIBUTE = "PASSWORD_SESSION_ATTRIBUTE"; // session attribute for password

    // Logger
    private static Log logger = LogFactory.getLog(BrowserServletImpl.class);

    private LoginContext loginContext;

    @Override
    public String[] getChildren(String path) {
        logger.info("Getting children of " + path);

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
            return null;
        }

        login();
        
        // get the browsing service
        final BrowserService browserService;
        try {
            browserService = (BrowserService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(BrowserService.SERVICE_NAME));
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory browser service. Caused by: ", ne);
            return null;
        }

        String[] children = null;
        try {
            children = browserService.listChildren(path);
        } catch (BrowserServiceException bse) {
            logger.error("Cannot manage to call Factory browser service. Caused by: ", bse);
            return null;
        } catch (AccessDeniedException ade) {
            logger.error("Access denied for path " + path );
            return null;
        } catch (InvalidPathException ipe) {
            logger.error("Path " + path + " is invalid.");
            return null;
        } catch (PathNotFoundException pnfe) {
            logger.error("Path " + path + " cannot be found.");
            return null;
        }

        logout();
        
        return children;
    }

    @Override
    public Boolean hasChildren(String path) {
        logger.info("Asking node " + path + " if it has children");

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
            return null;
        }

        login();
        
        // get the browsing service
        final BrowserService browserService;
        try {
            browserService = (BrowserService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(BrowserService.SERVICE_NAME));
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory browser service. Caused by: ", ne);
            return null;
        }

        Boolean hasChildren = null;
        try {
            hasChildren = browserService.hasChildren(path);
        } catch (BrowserServiceException bse) {
            logger.error("Cannot manage to call Factory browser service. Caused by: ", bse);
            return null;
        } catch (AccessDeniedException ade) {
            logger.error("Access denied for path " + path );
            return null;
        } catch (InvalidPathException ipe) {
            logger.error("Path " + path + " is invalid.");
            return null;
        } catch (PathNotFoundException pnfe) {
            logger.error("Path " + path + " cannot be found.");
            return null;
        }
        
        logout();

        return hasChildren;
    }

    private FactoryResource findResource(String path) {
        logger.info("Getting resource for path " + path);

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
            return null;
        }

        login();
        
        // get the browsing service
        final BrowserService browserService;
        try {
            browserService = (BrowserService) namingContext.lookup(FactoryNamingConvention.getJNDINameForService(BrowserService.SERVICE_NAME));
        } catch (NamingException ne) {
            logger.error("Cannot manage to access Factory browser service. Caused by: ", ne);
            return null;
        }

        FactoryResource resource = null;
        try {
            resource = browserService.findResource(path);
        } catch (BrowserServiceException bse) {
            logger.error("Cannot manage to call Factory browser service. Caused by: ", bse);
            return null;
        } catch (AccessDeniedException ade) {
            logger.error("Access denied for path " + path );
            return null;
        } catch (InvalidPathException ipe) {
            logger.error("Path " + path + " is invalid.");
            return null;
        } catch (PathNotFoundException pnfe) {
            logger.error("Path " + path + " cannot be found.");
            return null;
        }
        
        logout();

        return resource;
    }    
    
    @Override
    public String getResourceService(String path) {
        logger.info("Getting resource service name for path " + path);
        
        //get the factory resource
        FactoryResource resource = findResource(path);
        if (resource == null) {
            return null;
        }

        return resource.getFactoryResourceIdentifier().getService();
    }

    @Override
    public String getResourceType(String path) {
        logger.info("Getting resource type for path " + path);
        
        //get the factory resource
        FactoryResource resource = findResource(path);
        if (resource == null) {
            return null;
        }

        return resource.getFactoryResourceIdentifier().getType();
    }
    
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
