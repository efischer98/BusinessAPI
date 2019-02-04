package apiCalls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dataobjects.Business;
import dataobjects.Check;
import dataobjects.Employee;
import dataobjects.LaborEntry;
import dataobjects.MenuItem;
import dataobjects.OrderedItem;

public class Caller {
	
	private static final String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NDQ0NTgwODMsImV4cCI6MTU0NzA1MDA4M30.fQ8h7yK2zFj1WRqGnPEdu87VjbMmATKy2kaOOhcGBAs";
	
	
	private static String makeCall(String path) {
		//StringBuffer response = new StringBuffer();
		
		try {
			URL url = new URL("https://secret-lake-26389.herokuapp.com/" + path);
	 	    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	 	    connection.setRequestMethod("GET");
	 	    Base64.Encoder encoder = Base64.getEncoder();
	 	    String stuff = accessToken;
	 	    // NOTE - I also tried
	 	    // stuff = encoder.encodeToString(accessToken.getBytes());
	 	    
	 	    connection.setDoOutput(true);
	 	    connection.setRequestProperty("Content-type", "application/json");
	 	    connection.setRequestProperty("Authorization", stuff);
	 	    connection.connect();
	 	    int rspCode = connection.getResponseCode();
	 	    System.out.println("Response code is " + rspCode);
	 	    
	 	    BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	 	    StringBuilder stringBuffer = new StringBuilder();
	 	    String line;
	 	    while ((line = bf.readLine()) != null )
	 	    {
	 	        stringBuffer.append(line);
	 	    }
	 	    String response = stringBuffer.toString();
	 	    System.out.println("response"+response);
	 	    return response;
	   
	 	   
		}
		catch (IOException ex) {
			// Not doing anything here - in real life, would have an entire framework/structure
			//    for exceptions that I don't have time to do here . . .
			return ex.getMessage();
		}
		
		//return "Error";
	}
	
	public static String makeTest() {
		return makeCall("businesses");
	}
	
	public static List<Business> getBusinesses() {
		List<Business> bizList = new ArrayList<Business>();
		// String callSuffix = "businesses";
		//String jsonResponse =  makeCall("businesses");
		String jsonResponse = "{\r\n" + 
				"   \"count\":2,\r\n" + 
				"   \"data\":[\r\n" + 
				"      {\r\n" + 
				"         \"id\":\"b2aeb27b-c85c-4ad8-83d4-d9511063d418\",\r\n" + 
				"         \"name\":\"Red BBQ\",\r\n" + 
				"         \"hours\":[\r\n" + 
				"            11,12,13,14,15,16,17,18,19,20,21,22\r\n" + 
				"         ],\r\n" + 
				"         \"updated_at\":\"2018-07-27T16:34:12.910Z\",\r\n" + 
				"         \"created_at\":\"2018-07-27T16:34:12.910Z\"\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"id\":\"b2aeb27b-c85c-4ad8-83d4-d9511063d419\",\r\n" + 
				"         \"name\":\"The Annex\",\r\n" + 
				"         \"hours\":[\r\n" + 
				"            11,12,13,14,17,18,19,20,21,22,23\r\n" + 
				"         ],\r\n" + 
				"         \"updated_at\":\"2018-07-27T16:34:12.910Z\",\r\n" + 
				"         \"created_at\":\"2018-07-27T16:34:12.910Z\"\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}";
		
		try {
			Object obj = new JSONParser().parse(jsonResponse);
			JSONObject jObj = (JSONObject)obj;
			JSONArray ja = (JSONArray) jObj.get("data"); 
			Iterator<Map<String,Object>> iter = ja.iterator();
			while (iter.hasNext()) {
				Map<String,Object> vals = (iter.next());
				String name = (String)vals.get("name");
				String idString = (String)vals.get("id");
				UUID id = UUID.fromString(idString);
				JSONArray hours = (JSONArray) vals.get("hours");
				Business biz = new Business();
				biz.setId(id);
				biz.setName(name);;
				Iterator<Long> hoursIter = hours.iterator();
				while (hoursIter.hasNext()) {
					Long nxtHour = hoursIter.next();
					biz.addHour(nxtHour);
				}
				biz.setUpdatedAt((String) vals.get("updated_at"));
				biz.setCreatedAt((String) vals.get("created_at"));
				bizList.add(biz);
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return bizList;
		
	}
	public static List<MenuItem> getMenuItems(UUID bizId) {
		List<MenuItem> items = new ArrayList<MenuItem>();
		// String callSuffix = "businesses";
		//String jsonResponse =  makeCall("menuItems?business_id=" + bizId.toString());
		String jsonResponse =	"{\r\n" + 
				"   \"count\":2,\r\n" + 
				"   \"data\":[\r\n" + 
				"      {\r\n" + 
				"         \"id\":\"a7c9f9f0-4936-4a4a-88d7-f9f76ebce1bd\",\r\n" + 
				"         \"business_id\":\"b2aeb27b-c85c-4ad8-83d4-d9511063d418\",\r\n" + 
				"         \"name\":\"Smokin Ribs\",\r\n" + 
				"         \"cost\":12,\r\n" + 
				"         \"price\":20,\r\n" + 
				"         \"updated_at\":\"2018-07-27T16:46:19.847Z\",\r\n" + 
				"         \"created_at\":\"2018-07-27T16:46:19.847Z\"\r\n" + 
				"      },\r\n" + 
				"      {\r\n" + 
				"         \"id\":\"a7c9f9f0-4936-4a4a-88d7-f9f76ebce1be\",\r\n" + 
				"         \"business_id\":\"b2aeb27b-c85c-4ad8-83d4-d9511063d418\",\r\n" + 
				"         \"name\":\"Crusty Brussels Sprouts\",\r\n" + 
				"         \"cost\":3,\r\n" + 
				"         \"price\":12,\r\n" + 
				"         \"updated_at\":\"2018-07-27T16:46:19.847Z\",\r\n" + 
				"         \"created_at\":\"2018-07-27T16:46:19.847Z\"\r\n" + 
				"      }\r\n" + 
				"   ]\r\n" + 
				"}";
		try {
			Object obj = new JSONParser().parse(jsonResponse);
			JSONObject jObj = (JSONObject)obj;
			JSONArray ja = (JSONArray) jObj.get("data"); 
			Iterator<Map> iter = ja.iterator();
			while (iter.hasNext()) {
				Map vals = (iter.next());
				String name = (String)vals.get("name");
				UUID id = UUID.fromString((String)vals.get("id"));
				UUID business_id = UUID.fromString((String)vals.get("business_id"));
				Long cost = (Long)vals.get("cost");
				Long price = (Long) vals.get("price");
				String updatedAt = (String) vals.get("updated_at");
				String createdAt = (String) vals.get("created_at");
				MenuItem item = new MenuItem(id,business_id,name, cost, price, updatedAt,createdAt);
				items.add(item);
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return items;
	}
	
	public static List<Employee> getEmployees(UUID bizId) {
		List<Employee> empList = new ArrayList<Employee>();
		
		return empList;
	}
	public static List<Check> getChecks(UUID biz_id) {
		List<Check> checks = new ArrayList<Check>();
		
		
		return checks;
	}
	
	/**
	 * Retrieve all the info on menu items ordered for the specified business.  Sort the
	 * as a map from check-id to a list of ordered items
	 * @param biz_id
	 * @return
	 */
	public static List<OrderedItem> getOrderedItems(UUID biz_id) {
		List<OrderedItem> items = new ArrayList<OrderedItem>();
		
		
		return items;
	}
	
	public static List<LaborEntry> getLaborEntries(UUID biz_id, UUID employeeID)
	{
		List<LaborEntry> entries = new ArrayList<LaborEntry>();
		
		return entries;
	}


}

