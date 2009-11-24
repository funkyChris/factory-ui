package org.qualipso.factory.test.ui.core.login.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.qualipso.factory.ui.core.login.server.LoginServletImpl;

public class LoginServletTest {

    @Test
    public void testLogin() {
        LoginServletImpl servlet = new LoginServletImpl();
        assertEquals(1, servlet.methodToTest());
    }

}
