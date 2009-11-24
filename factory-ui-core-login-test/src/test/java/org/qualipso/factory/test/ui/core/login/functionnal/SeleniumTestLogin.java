package org.qualipso.factory.test.ui.core.login.functionnal;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;


public class SeleniumTestLogin extends SeleneseTestCase {
    protected Selenium browser;
    
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4441, "*chrome", "http://localhost:8080/");
        selenium.start();
    }
    
    public void tearDown() throws Exception {
        selenium.stop();
    }
    
    public void testLoginok() throws Exception {
        selenium.open("/factory-ui/");
        selenium.type("//input[@type='text']", "kermit");
        selenium.type("//input[@type='password']", "thefrog");
        selenium.click("//button[@type='button']");
        for (int second = 0;; second++) {
            if (second >= 15) fail("timeout");
            try { if (selenium.isTextPresent("Welcome, kermit")) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    
    public void testLoginFailure() throws Exception {
        selenium.open("/factory-ui/");
        selenium.type("//input[@type='text']", "kermit");
        selenium.type("//input[@type='password']", "theforg");
        selenium.click("//button[@type='button']");
        for (int second = 0;; second++) {
            if (second >= 15) fail("timeout");
            try { if (selenium.isTextPresent("Login failed.")) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }

}