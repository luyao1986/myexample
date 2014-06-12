package JmockitExample;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static mockit.Deencapsulation.invoke;
import static org.junit.Assert.assertEquals;

/**
 * Created by shiyao on 5/20/14.
 */
@RunWith(JMockit.class)
public class BasicExample {
    static final class User {
        final String name() {
            return "joe";
        }
        Address address() {
            return new Address();
        }

        public String getUsername() {
            return username;
        }

        private String username;
        public User() {
            this.username = "joe";
        }

        private final String name = "joe";
        private final String getMyname() {
            return name;
        }

        static String getName() {
            return "joe";
        }

    }

    static class Address {
        String postCode() {
            return "sw1";
        }
        static {
            System.out.println("initialed in static method");
            add = "beijing";
        }
        static String add = "";
    }


    @Injectable User user;

    @Test
    public void mockFinalClass() {
        new Expectations() {
            {
                user.name();
                returns("fred");
            }
        };
        assertEquals("fred", user.name());
    }


    @Mocked
    Address address;

    @Test
    public void mockInternalInstantiation() {
        User user = new User();
        new Expectations(user) {
            {
                user.address();
                result = address;
                address.postCode();
                returns("w14");
            }
        };
        String postCode = user.address().postCode();
        assertEquals("w14", postCode);
    }

    @Test
    public void mockStaticMethod(/*@Mocked User user*/) {
        new Expectations(User.class) {
            {
                User.getName();
                returns("fred");
            }
        };
        assertEquals("fred", User.getName());
    }

    @Test
    public void mockNativeMethod() {
        new MockUp<Runtime>() {
            @Mock
            @SuppressWarnings("unused")
            int availableProcessors() {
                return 999;
            }
        };
        assertEquals(999, Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void mockPrivateMethod() {
        new Expectations() {
            {
                invoke(user, "name");
                returns("fred");
            }
        };
        //TODO try use multiple Expectation
        assertEquals("fred", invoke(user, "name"));
    }

    @Test
    public void mockDefaultConstructor(final @Mocked User tmp) {
        new MockUp<User>() {
            @Mock
            @SuppressWarnings("unused")
            public void $init() {       //$init mock the constructor while $clinit mock static initialize
                tmp.username = "fred";
            }
        };
        new User();
        assertEquals("fred", tmp.username);
    }

    @Test
    public void mockStaticInitialiserBlock() throws ClassNotFoundException {
        new MockUp<Address>() {
            @Mock
            @SuppressWarnings("unused")
            public void $clinit() {
                System.out.println("initialized in mock");
            }
        };
        //TODO
//        Class.forName(Address.class.getName());
        String add = Address.add;
        System.out.println("address: "+add);
        new Address().toString();
    }

}
