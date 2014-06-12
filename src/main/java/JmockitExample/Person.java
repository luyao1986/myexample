package JmockitExample;

/**
 * Created by shiyao on 5/19/14.
 */
public class Person {
    private String name;
    private ExternalService es;
//    private static ExternalService es_static = new ExternalService();

    public Person(String name) {
        this.name = name;
        this.es = new ExternalService();
    }

    public String getSomething() {
        System.out.println("call external public method");
        String something = es.getSomething();
        System.out.println("and then do something(call internal/external method/field)");
        String info = getName()+something;
        return info;
    }

    public String getName() {
        return name;
    }

    private static String staticName = "I am a man:";

    public static String getDetailedInfo(String name) {
        System.out.println("call external public static method");
        String detail = ExternalService.getDetails(name);
        System.out.println("and then do something");
        String info = staticName + toLower(detail);
        return info;
    }

    private static String toLower(String name) {
        return name.toLowerCase();
    }

    public static void main(String[] args) {
        getDetailedInfo("shiyao");
    }
}
