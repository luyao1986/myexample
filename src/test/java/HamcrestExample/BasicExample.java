package HamcrestExample;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.CombinableMatcher.either;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;


/**
 * Created by shiyao on 5/6/14.
 */
class A{
}

class B extends A {
    public B() {
        super();
    }
}
public class BasicExample {
    @Test
    public void testAll() throws Exception {
        assertThat("example", is(notNullValue()));
        assertThat(new B(), is(instanceOf(A.class)));
        assertThat(new B(), either(instanceOf(A.class)).or(nullValue()));
        assertThat(new B(), both(instanceOf(A.class)).and(notNullValue()));
        //there are more matcher about Maps,Arrasy,Collections,Iterables,Strings,Numbers...
    }
}
