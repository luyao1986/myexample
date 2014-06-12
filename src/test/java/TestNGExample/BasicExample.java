package TestNGExample;

import org.testng.annotations.*;

import java.io.IOException;

public class BasicExample {
    // test case 1, invocationCount can be used to do load test
    @Test(expectedExceptions = IOException.class, invocationCount = 3)
    public void testCase1() throws IOException {
        System.out.println("run testcase1");
        throw new IOException("in test case 1");
    }

    @Test(dependsOnMethods = { "testCase1" })
    public void testCase4() {
        System.out.println("run testcase4");
    }

    // test case 2
    @Test(groups = "unittest")
    public void testCase2() {
        System.out.println("in test case 2");
    }

    @Test(enabled = false)
    public void testCase3() {
        System.out.println("in test case 3");
    }

    @BeforeGroups(groups = "unittest")
    public void beforeGroups() {
        System.out.println("before group");
    }

    @AfterGroups(groups = "unittest")
    public void afterGroups() {
        System.out.println("after group");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("in beforeMethod");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("in afterMethod");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("in beforeClass");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("in afterClass");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("in beforeTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("in afterTest");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("in beforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("in afterSuite");
    }

}
