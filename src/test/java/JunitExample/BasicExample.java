package JunitExample;

import org.junit.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by shiyao on 4/23/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicExample {
    @Ignore
    @Test
    public void ignore() {
        System.out.println("never run ignore");
    }

    @BeforeClass
    public static void staticBeforeClass() {
        System.out.println("run static beforeClass");
    }

    @Before
    public void setup() {
        System.out.println("run before");
    }

    @After
    public void cleanup() {
        System.out.println("run after");
    }

    @Test(timeout = 10)
    public void test1() {
        System.out.println("run test1");
    }

    @Test(expected = IOException.class)
    public void test2() throws IOException { //any test case can not have arguments
        System.out.println("run test2");
        throw new IOException("test2");
    }

    @AfterClass
    public static void staticAfterClass() {
        System.out.println("run static afterClass");
    }

    @Test
    public void testAll() throws Exception {
        System.out.println("testAll in order");
        test1();
        ignore();
    }
}
