package privateMethod;

import org.junit.Test;
import org.testng.Assert;

import java.lang.reflect.Method;

/**
 * Created by shiyao on 5/9/14.
 */
public class PrivateClassExampleTest {
    @Test
    public void testPublicExample() throws Exception {
        //option1, test the public methods which call the private method to make sure the private method works fine
        String name = "shiyao";
        Assert.assertEquals(PrivateClassExample.publicExample(name), "shiyaoSHIYAO");
    }

    @Test
    public void testFriendToUpper() throws Exception {
        //option2, we change the private method to package-level, so we can access it in the test code
        String name = "shiyao";
        Assert.assertEquals(PrivateClassExample.friendToUpper(name), "SHIYAO");
    }

    @Test
    public void testPrivateToLower() throws Exception {
        //option4, we use reflection to do the test
        PrivateClassExample myClass = new PrivateClassExample();
        Method method = PrivateClassExample.class.getDeclaredMethod("privateTolower", String.class);
        method.setAccessible(true);
        String output = (String) method.invoke(myClass, "SHIyao");
        Assert.assertEquals(output, "shiyao");
    }
}
