package dataobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTester {
	public static String[] testDates = {
		"2018-05-03T15:00:00.000Z",	
		"2018-07-30T19:23:32.265Z",
		"2018-07-27T16:34:12.910Z"
	};
	
	public static void main (String[] args)
	{
		String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss";
	    Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()) ;
	    SimpleDateFormat dateformat = new SimpleDateFormat(ISO8601DATEFORMAT, Locale.getDefault());
	    for (String testDate: testDates) {
	    	try {
	    	      Date date = dateformat.parse(testDate);
	    	      calendar.setTime(date);
	    	      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh mm ");
	    	      System.out.println(format1.format(date));
	    	    } catch (ParseException e) {
	    	      e.printStackTrace();
	    	    }	    
	    	}
	    	
	}
}
