package tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiyao on 4/14/14.
 */
public class DateUtils {

    public static Date incrDate(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();

    }

    public static Date incrDate(Date date, int days) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();

    }

    public static Date incrMonth(Date date, int months) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * Converts date to YYYYMMDD format
     *
     * @param date
     * @return
     */
    public static String dateToYYYYMMDD(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    /**
     * Converts date to YYYYMMDDHHMMSS format
     *
     * @param date
     * @return
     */
    public static String dateToYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    /**
     * Converts a string in the format YYYYMMDD to date
     *
     * @param dt
     * @return
     */
    public static Date YYYYMMDDToDate(String dt) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.parse(dt);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static long dateDiffInDays(Date startDate, Date endDate) {
        Calendar sCal = new GregorianCalendar();
        sCal.setTime(startDate);
        Calendar eCal = new GregorianCalendar();
        eCal.setTime(endDate);
        return (eCal.getTimeInMillis() - sCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    }

    private static Pattern w3cDateTimeFormat = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2})Z$", Pattern.CASE_INSENSITIVE);

    public static String w3cDateTimeToYYYYMMDD(String time) {
        Matcher matcher = w3cDateTimeFormat.matcher(time);
        if (matcher.find(0)) {
            return matcher.group(1) + matcher.group(2) + matcher.group(3);
        }
        throw new RuntimeException("wrong nominal time format '" + time + "'");
    }
}
