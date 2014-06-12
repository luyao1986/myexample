package JmockitExample;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static mockit.Deencapsulation.invoke;
import static mockit.Deencapsulation.setField;

/**
 * Created by shiyao on 5/19/14.
 */
@RunWith(JMockit.class)
public class PersonTest {
/*    @Mocked ExternalService es = null;*/

    @Test
    public void testGetSomething(@Mocked ExternalService es) throws Exception {
        Person person = new Person("shiyao");

        new Expectations(person){           //we must add person in the argument, otherwise the getName will return shiyao
            /*@Mocked ExternalService es;*/
            {
            es.getSomething();              //we can mock the public method of external object
            result = "[mocked something]";
            times = 1;
            invoke(person, "getName");      //we can mock the private non-static method of the object
            result = "[mocked name]";
            times = 1;
            }
        };


        String info = person.getSomething();
        Assert.assertEquals(info, "[mocked name][mocked something]");
    }

    @Test
    public void testGetDetailedInfo() throws Exception {
        new Expectations(ExternalService.class, Person.class){{
            List<String> names = new ArrayList<>();
            ExternalService.getDetails(withCapture(names));              //we can mock the public static method of external class
            for(String n : names) {
                System.out.println(n);
            }
            result = "[mocked details]";
            times = 1;
            System.out.println("done mock details");
            setField(Person.class, "staticName", "[mocked static]");
            System.out.println("done set static name");
            invoke(Person.class, "toLower", anyString);      //we can mock the private static method of the class
            result = "[mocked lower]";
            times = 1;
            System.out.println("done mock private method");
        }};

        String info = invoke(Person.class, "getDetailedInfo", "shiyao");      //test private static method
//        String info = Person.getDetailedInfo("shiyao");
        System.out.println(info);
    }

    final static class MockPerson extends MockUp<Person> {
        @Mock
        public static String getDetailedInfo(String name) {
            return "";
        }
    };
}
