package JunitExample;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * we can run multiple class
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BasicExample.class, ParameterExample.class})
public class JunitSuitExample {
}
