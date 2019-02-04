package webServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class ServerHandler {
	
	private static final int STATUS_OK = 200;
	private static String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss";

	
	static class ReportHandler implements HttpHandler{
		private static PseudoDatabase db = PseudoDatabase.getInstance();
		
		@Override
		public void handle(HttpExchange t) throws IOException {
			Headers headers = t.getResponseHeaders();
			String output = "";
			String query = t.getRequestURI().getQuery();
			if (query == null)
				output = "{'error':'No query parameters'}";
			else {
				Map <String,String> parameters = ServerHandler.queryToMap(query);
				if ((parameters.containsKey("report")) &&
					(parameters.containsKey("business_id")) &&
					(parameters.containsKey("timeInterval")) &&
					(parameters.containsKey("start")) &&
					(parameters.containsKey("end")))
				{
					String reportType = parameters.get("report");
					UUID businessID = UUID.fromString(parameters.get("business_id"));
					String timeInterval = parameters.get("timeInterval");
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
					ReportFormatter reporter = null;
					// only 3 types of reports accepted
					switch (reportType) {
						case "LCP":
							//output = ;
							break;
						case "FCP":
							break;
						case "EGS":
							break;
					}
					if (reporter == null)
						output = "{ 'error': 'Unrecognized report requested' }";
					else  // report was successfully created - send back JSON results
						output = reporter.toString();
				}
				else {
					output = "{ 'error': 'Missing report parameter'}";
				}
			}
			headers.set("Content-Type", "application/json");
			final byte[] rawResponseBody = output.getBytes(StandardCharsets.UTF_8);
			t.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
			t.getResponseBody().write(rawResponseBody);
			t.close();
		}
	}
	/**
	   * returns the url parameters in a map
	   * @param query
	   * @return map
	   */
	public static Map<String, String> queryToMap(String query){
	    Map<String, String> result = new HashMap<String, String>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result;
	}	
	
	private static Calendar convertDateString(String inString) {
	    Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()) ;
	    SimpleDateFormat dateformat = new SimpleDateFormat(ISO8601DATEFORMAT, Locale.getDefault());
    	try {
  	      Date date = dateformat.parse(inString);
  	      calendar.setTime(date);
    	}
    	catch (ParseException ex) {
    		// would go to error handler.
    	}
	    return calendar;
	}
	
	/**
	 * Main entry point of program.  Starts out HTTP server listening at port 8000, defines
	 * entry points for each requested report.
	 */
	public static void main (String[] args)
	{
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
			server.createContext("/reporting", new ReportHandler());
			server.setExecutor(null);
			server.start();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getStackTrace());;
		}
	}
}
