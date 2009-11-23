/**
 * 
 */
package org.qualipso.factory.test.ui.core.login.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.qualipso.factory.ui.core.login.server.LoginServletImpl;

/**
 * @author chris
 *
 */
public class LoginServletTest {

    /**
     * Test method for {@link org.qualipso.factory.ui.core.login.server.LoginServletImpl#login(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogin() {
        LoginServletImpl servlet = new LoginServletImpl();
        assertEquals(1, servlet.maMethod());
    }

}
