package tool;

import org.testng.AssertJUnit;

public class SomeUtilsTest {
    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {

    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetPid() throws Exception {
        int pid = SomeUtils.getPid();
        AssertJUnit.assertNotNull(pid);
        System.out.println("pid is "+pid);
    }
}
