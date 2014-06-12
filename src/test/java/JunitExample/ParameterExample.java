package JunitExample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


/**
 * we can test a same method by multiple groups of parameters.
 */
@RunWith(Parameterized.class)   //Parameterized is a runner inside JUnit that will run the same test case with different set of inputs.
public class ParameterExample {
    private int expected;
    private int first;
    private int second;

    public ParameterExample(int expectedResult, int firstNumber, int secondNumber) {
        System.out.println("construct ParameterExample");
        this.expected = expectedResult;
        this.first = firstNumber;
        this.second = secondNumber;
    }

    @Parameterized.Parameters   //test data must be annotated with @Parameters and return a Collection of Arrays
    public static Collection<Integer[]> addedNumbers() {
        System.out.println("add test data called");
        return Arrays.asList(new Integer[][]{{3, 1, 3}, {3, 2, 3}, {4, 3, 4}, {5, 4, 5},});
    }

    @Test
    public void sum() {
        System.out.println("Max with parameters : " + first + " and " + second);
        Assert.assertEquals(expected, Math.max(first, second));
        System.out.println("done verification");
    }
}
