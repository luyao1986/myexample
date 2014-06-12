package JavaVersion;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shiyao on 5/12/14.
 */
public class Java8 {
    public static void main(String[] args) {
        lambdaExample();
    }
//lambda expression: ([int] arg...) -> (body)
// type of the parameters can be explicitly declared or it can be inferred from the context
//lambda expression can have zero, one or more parameters
//When there is a single parameter, if its type is inferred, it is not mandatory to use parentheses, e.g. a -> return a*a
    public static void lambdaExample() {
        //replace anonymous class with lambda expressions,
        new Thread(new Runnable() {
            @Override
            public void run()  {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();
        new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();

        //we can use functionalinterface defined by ourselves
        execute( () -> System.out.println("Worker invoked using Lambda expression") );

        //Iterating over List using Lambda expressions
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 2);
        list.sort((a,b)->a>b?1:0);
        list.forEach(n -> System.out.print(n));
        System.out.println();
        list.stream().filter((a)->a>1).distinct().forEach(System.out::print);
        //java.util.function contains lot of classes to enable functional programming in Java, for example Predict


    }

    private static void execute(FunctionalInterfaceExample worker) {
        worker.doSomeWork();
    }

    // Functional Interface is an interface with just one abstract method declared in it, for example Runnable, ActionListener
    @FunctionalInterface
    public interface FunctionalInterfaceExample {
        default void defaultFunction() {
            System.out.println("now jave 8 interface can has default function which make java multiple extends");
        }
        public void doSomeWork();
    }


    //stream API
}
