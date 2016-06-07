package com.tvalerts.utils;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTimeConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by anita on 17/02/16.
 */
public class DatesUtil {

    private static final String TAG = "DatesUtil";

    private static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

    public static List<String> getMonthStrings(int currentMonth){
        //Update the currentMonth since in the Calendar starts at 0 for January
        List<String> results = new ArrayList<>();
        LocalDate date = new LocalDate();
        date = date.withMonthOfYear(currentMonth);
        LocalDate firstDayMonth = date.minusDays(date.getDayOfMonth() - 1);
        LocalDate lastDayMonth = firstDayMonth.plusDays(firstDayMonth.dayOfMonth().getMaximumValue() - 1);

        date = firstDayMonth;
        int i = 1;
        while (! date.equals(lastDayMonth)){
            results.add(date.toString());
            i++;
            date = date.withDayOfMonth(i);
        }

        return results;
    }

    public static Date stringToDate(String dateString){
        return DateTime.parse(dateString).toDate();
    }

    public static String dateWithFormat(Date date){
        DateTime dateTime = new DateTime(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEEE, dd MMMM yyyy");
        return dateTimeFormatter.withLocale(Locale.ENGLISH).print(dateTime);
    }

    public static String dateWithSimpleFormat(Date date){
        DateTime dateTime = new DateTime(date);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_DATE_PATTERN);
        return dateTimeFormatter.withLocale(Locale.ENGLISH).print(dateTime);
    }

    public static int getMonthIndex(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.getMonthOfYear();
    }

    public static Date firstDayOfMonth(int month, int year){
        return new DateTime(year, month, 1, 0, 0).toDate();
    }

    public static String getDayOfWeek(String date){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(STANDARD_DATE_PATTERN);
        DateTime dateTime = dateTimeFormat.parseDateTime(date);
        int dayOfWeekInt = dateTime.getDayOfWeek();
        String dayOfWeek = "";
        switch (dayOfWeekInt){
            case DateTimeConstants.MONDAY:
                dayOfWeek = "Monday";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "Tuesday";
                break;
            case  DateTimeConstants.WEDNESDAY:
                dayOfWeek = "Wednesday";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "Thursday";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "Friday";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "Saturday";
                break;
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "Sunday";
                break;
        }

        return dayOfWeek;
    }

}
