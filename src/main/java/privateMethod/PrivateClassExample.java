package privateMethod;

/**
 * Created by shiyao on 5/9/14.
 */
public class PrivateClassExample {

    public static String publicExample(String a) {
        return privateTolower(a)+ friendToUpper(a);
    }

    private static String privateTolower(String a) {
        return a.toLowerCase();
    }

    static String friendToUpper(String a) {
        return a.toUpperCase();
    }
    //option3,if we want to test the private method, we can add a inner test class to test it
}
