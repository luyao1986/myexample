package JmockitExample;

import tool.DateUtils;

import java.util.Date;

public class JmokitExample
{
    private static ExternalService2 es = new ExternalService2("shiyao");

    public Date stringToDate(String date)
    {
        Date dd = DateUtils.YYYYMMDDToDate(date);
        return dd;
    }


    public static String fetchData2() {
        return es.getName();
    }

    //instance public -> ExternalClass public static
    public String toLower(String name) {
        return getName() + ExternalService1.toLower(name);
    }
    private String name="hello";
    private String getName() {
//        return name;
        throw new RuntimeException("Not implemented yet!");
    }

    private static String delimeter = " ";
    private static String prefix(String name){
        throw new RuntimeException("Not implemented yet!");
    }
    //Class public static -> Class private static + ExternalClass public static
    public static String fetchData(String name){
        return prefix(name) + delimeter + ExternalService1.suffix(name);
    }
}
