package TestNGExample;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataProviderExample {
    //example to use Method to reflect parameters
    @Test(dataProvider = "dataProvider")
    public void test1(int number, int expected) {
        Assert.assertEquals(number, expected);
    }

    @Test(dataProvider = "dataProvider")
    public void test2(String email, String expected) {
        Assert.assertEquals(email, expected);
    }

    @DataProvider(name = "dataProvider", parallel = true)
    public Object[][] provideData(Method method) {
        Object[][] result = null;
        if (method.getName().equals("test1")) {
            result = new Object[][] {
                    { 1, 1 }, { 200, 200 }
            };
        } else if (method.getName().equals("test2")) {
            result = new Object[][] {
                    { "test@gmail.com", "test@gmail.com" },
                    { "test@yahoo.com", "test@yahoo.com" }
            };
        }
        return result;
    }

    //example to use map as paramater
    @Test(dataProvider = "dbconfig")
    public void testConnection(Map<String, String> map) {

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("[Key] : " + entry.getKey()
                    + " [Value] : " + entry.getValue());
        }

    }

    @DataProvider(name = "dbconfig")
    public Object[][] provideDbConfig() {
        Map<String, String> map = readDbConfig();
        return new Object[][] { { map } };
    }

    public Map<String, String> readDbConfig() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        return map;
    }

    //use ITestContext to determine what runtime parameters the current test method was invoked with
    @Test(dataProvider = "dataProvider2", groups = {"groupA"})
    public void test1(int number) {
        Assert.assertEquals(number, 1);
    }

    @Test(dataProvider = "dataProvider2", groups = "groupB")
    public void test2(int number) {
        Assert.assertEquals(number, 2);
    }

    @DataProvider(name = "dataProvider2")
    public Object[][] provideData(ITestContext context) {
        Object[][] result = null;
        System.out.println(context.getName());
        for (String group : context.getIncludedGroups()) {
            System.out.println("group : " + group);
            if ("groupA".equals(group)) {
                result = new Object[][] { { 1 } };
                break;
            }
        }
        if (result == null) {
            result = new Object[][] { { 2 } };
        }
        return result;
    }
}
