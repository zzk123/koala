package org.openkoala.opencis;

import org.junit.Test;
import org.openkoala.opencis.api.Developer;

/**
 * User: zjzhai
 * Date: 1/17/14
 * Time: 4:06 PM
 */
public class DevelopTest {

    @Test(expected = DeveloperValidateRuntimeException.class)
    public void testValidate() throws Exception {
        Developer developer = new Developer();
        developer.setName("xxx");
        developer.setPassword("password");
        developer.setEmail("email");
        developer.validate();

        Developer developer1 = new Developer();
        developer1.setPassword("password");
        developer1.validate();

        Developer developer2 = new Developer();
        developer2.setEmail("email");
        developer2.validate();


        Developer developer3 = new Developer();
        developer3.setName("xxx");
        developer3.setPassword("password");
        developer3.setEmail("email");
        developer3.validate();


    }
}