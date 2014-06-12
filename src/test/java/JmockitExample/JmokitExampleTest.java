package JmockitExample;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testng.Assert;
import tool.DateUtils;

import java.util.Date;

import static mockit.Deencapsulation.invoke;
import static mockit.Deencapsulation.setField;
import static org.junit.Assert.assertEquals;

/**
 * Created by shiyao on 5/9/14.
 */
//use @RunWith(JMockit.class) for test class, otherwise, we need to make sure jmockit.jar is before junit.jar in the classpath
@RunWith(JMockit.class)
public class JmokitExampleTest {
    //@Mocked Collaborator mockCollaborator; 为了防止和该测试类的其他不需要mock的字段属性,只有放在类的mock才必须要添加@Mocked，在方法内部或者参数里面的mock都不是必须添加@Mocked的
    @Test
    public void testByExpectation() throws Exception {
        //第一步，准备数据
        final Date mockdate = new Date();

        new Expectations(DateUtils.class) {{    //DateUtils是要mock的类
            // 第二步：Record phase: expectations on mocks are recorded;
            DateUtils.YYYYMMDDToDate(anyString);
            result = mockdate;
            times = 1;
        }};

        new NonStrictExpectations() {{
            DateUtils.incrDate(new Date()); times = 0; // a non-strict expectation requires a constraint if expected
        }};

        // 第三步:Replay phase: invocations on mocks are "replayed"; here the code under test is exercised.
        Date dd = new JmokitExample().stringToDate("20130101");
        assertEquals(dd, mockdate);
    }

    @Test
    public void testByVerification() throws Exception
    {
        // No expectations recorded in this case.
        new JmokitExample().stringToDate("20130101");

        new Verifications() {{ DateUtils.YYYYMMDDToDate("20130101"); }};
    }

    @Test
    public void testByMockup() throws Exception
    {
        //使用MockUp甚至可以修改被测试方法／类的内部逻辑, State-oriented,上面两种都是behavior-based
        new MockUp<DateUtils>() {
            @Mock(invocations = 1)
            Date YYYYMMDDToDate(Invocation inv, String day)
            {
                assertEquals("20130101", day);
                return inv.getInvokedInstance();
            }
        };
        // No expectations recorded in this case.
        JmokitExample je = new JmokitExample();
        // Replay phase: invocations on mocks are "replayed"; here the code under test is exercised.
        je.stringToDate("20130101");
    }



    @Test(expected = RuntimeException.class)
    public void testPrefix() throws Exception {
        Deencapsulation.invoke(JmokitExample.class, "prefix", "shiyao");
        System.out.println("won't execute");
    }

    //@Tested:自动的初始化被测试类实例,并且可以灵活的为该实例注入其他依赖属性(mocked)
    //@Injectable标注的只是当前实例，其他注解（@Mocked,@NonStrict）修饰的变量并不会被用来做注入。

    //final类,静态方法或者构造函数
    @Test
    public void testBasic() throws Exception {

    }

    @Test
    public void testToLower1() throws Exception {
        new MockUp<ExternalService1>() {
            @Mock(invocations = 1)
            String toLower(String name)
            {
                return "mock";
            }
        };

        //MockUp can mock any static or non static method/field
        new MockUp<JmokitExample>() {
            @Mock(invocations = 1)
            String getName()
            {
                return "tmp";
            }
        };

        JmokitExample example = new JmokitExample();
        String name = example.toLower("Shiyao");
        Assert.assertEquals(name, "tmpmock");
    }

//    @Tested JmokitExample example;
    @Test
    public void testToLower2() throws Exception {
        new Expectations(ExternalService1.class) {
            //we can define local mocked field here;
            @Mocked(value = "getName") JmokitExample mockExample;
            {
                invoke(mockExample, "getName");
                result = "tmp";
                ExternalService1.toLower(anyString);
                result = "mock";
            }
        };
        String name = new JmokitExample().toLower("Shiyao");
        Assert.assertEquals(name, "tmpmock");
    }

    @Test
    public void testFetchData1() throws Exception {
        new Expectations(JmokitExample.class, ExternalService1.class) {{
            invoke(JmokitExample.class, "prefix", anyString);   // 对私有static方法进行mock, we can also invoke(anInstance, "aMethod") to invoke instance method
            result = "mockprefix";
            setField(JmokitExample.class, "delimeter", "!!!!"); // 对私有static类型mock, we can also setField(anInstance, "intField2", 901) to set instance fields.
            //we can use getField(anInstance, "intField") to get private instance field or getField(A.class, "buffer") to get static fields
            ExternalService1.suffix(anyString);
            result = "mocksuffix";
            //SubClass instance = newInstance(SubClass.class.getName()); we can instantial class with private constructor
        }};
        String actual = JmokitExample.fetchData("shiyao");
        assertEquals(actual, "mockprefix!!!!mocksuffix");
    }

    @Test
    public void testFetchData2() throws Exception {
        new MockUp<ExternalService1>() {
            @Mock(invocations = 1)
            String suffix(String name)
            {
                return "mocksuffix";
            }
        };

        //MockUp can mock any static or non static method/field
        new MockUp<JmokitExample>() {
            @Mock(invocations = 1)
            String prefix(String name)
            {
                return "mockprefix";
            }
        };
    }

    @Test
    public void testFetchData3() throws Exception {
        new Expectations(JmokitExample.class, ExternalService1.class) {{
            invoke(JmokitExample.class, "prefix", anyString);   // 对私有static方法进行mock, we can also invoke(anInstance, "aMethod") to invoke instance method
            result = new Delegate() {
                //we can return anything as you like by override the function
                public String prefix(String type) {
                    if(type.equals("prefix")) {
                        return "prefix";
                    }
                    else {
                        return "mockprefix";
                    }
                }
            };
            setField(JmokitExample.class, "delimeter", "!!!!"); // 对私有static类型mock, we can also setField(anInstance, "intField2", 901) to set instance fields.
            //we can use getField(anInstance, "intField") to get private instance field or getField(A.class, "buffer") to get static fields
            ExternalService1.suffix(anyString);
            result = "mocksuffix";
            //SubClass instance = newInstance(SubClass.class.getName()); we can instantial class with private constructor
        }};
//        String actual = JmokitExample.fetchData("shiyao");
//        assertEquals(actual, "mockprefix!!!!mocksuffix");
        String actual = JmokitExample.fetchData("prefix");
        assertEquals(actual, "prefix!!!!mocksuffix");
    }
}
