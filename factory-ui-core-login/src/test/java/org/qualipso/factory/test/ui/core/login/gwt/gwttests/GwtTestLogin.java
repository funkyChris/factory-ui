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
package org.qualipso.factory.test.ui.core.login.gwt.gwttests;

import org.qualipso.factory.ui.core.login.client.Login;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Unit tests for the login interface.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 23 November 2009
 */
public class GwtTestLogin extends GWTTestCase {

    /**
     * Return the name of the tested GWT Module.
     * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
     */
    @Override
    public String getModuleName() {
        return "org.qualipso.factory.test.ui.core.login.gwt.TestLogin";
    }

    /**
     * Simple test.
     */
    public void testMethodInJS() {
        Login loginManager = new Login();
        assertEquals(1, loginManager.methodToTestInJS());
    }
    
}
