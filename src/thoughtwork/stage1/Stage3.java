package thoughtwork.stage1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Stage3 {
	
	
	public void solveStage3() throws ParseException {
		String name = "";
		String startTime="";
		String endTime="";
		
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification httpRequest = RestAssured.given().header("userid","ZvUYckbjT");
		Response response = httpRequest.get("/challenge/input");
		
		LinkedList<String> nameList = new LinkedList<String>();
		LinkedList<String> startTimeList = new LinkedList<String>();
		LinkedList<String> endTimeList = new LinkedList<String>();
		
		HashMap<String,Integer> usedTime = new HashMap<String,Integer>() ;
		
			
		//First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
	
		name = jsonPathEvaluator.get("toolUsage.name").toString();
		startTime = jsonPathEvaluator.get("toolUsage.useStartTime").toString();
		endTime = jsonPathEvaluator.get("toolUsage.useEndTime").toString();
		
		
		name.replaceAll(" ", "");
		name = name.replaceAll("\\[", "").replaceAll("\\]","");
		String [] nameArray = name.split(",");
		
		startTime.replaceAll(" ", "");
		startTime = startTime.replaceAll("\\[", "").replaceAll("\\]","");
		String [] startTimeArray = startTime.split(",");
		
		endTime.replaceAll(" ", "");
		endTime = endTime.replaceAll("\\[", "").replaceAll("\\]","");
		String [] endTimeArray = endTime.split(",");
		
		for(int i=0;i<nameArray.length;i++) {
			nameList.add(nameArray[i].replaceAll(" ", ""));
		}
		
		for(int i=0;i<startTimeArray.length;i++) {
			startTimeList.add(startTimeArray[i].replaceAll("", ""));
		}
		
		for(int i=0;i<endTimeArray.length;i++) {
			endTimeList.add(endTimeArray[i].replaceAll("", ""));
		}

		
		System.out.println(response.asString());
		System.out.println(name);
		System.out.println(startTime);
		System.out.println(endTime);
		
		System.out.println(nameList);
		System.out.println(startTimeList);
		System.out.println(endTimeList);
		

		//String date1 = "2017-01-30 10:10:00", date2 = "2017-01-30 10:40:00";
		
		
		for(int i=0;i<nameList.size();i++) {
			if(!usedTime.containsKey(nameList.get(i))) {
				usedTime.put(nameList.get(i), timeDiff(startTimeList.get(i),endTimeList.get(i)));
			}else {
			usedTime.put(nameList.get(i), usedTime.get(nameList.get(i)) + timeDiff(startTimeList.get(i),endTimeList.get(i)));
			}
		}

		usedTime = sortByValue(usedTime);
		System.out.println(usedTime);
		postDecryptedMessage(usedTime);
		
	}
	
	@SuppressWarnings("unchecked")
	public void postDecryptedMessage(HashMap<String, Integer> usedTime) {
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification request = RestAssured.given()
				 .header("userid","ZvUYckbjT")
				 .header("Content-Type", "application/json");
		 
		 JSONObject requestParams = new JSONObject();
		 JSONArray jarray = new JSONArray();
		 
		 for(String s:usedTime.keySet()) {
			 JSONObject requestParams1 = new JSONObject();
			 requestParams1.put("name", s);
			 requestParams1.put("timeUsedInMinutes", usedTime.get(s));
			 jarray.add(requestParams1);
		 }
		 
		 requestParams.put("toolsSortedOnUsage", jarray);
		 
		 System.out.println(requestParams.toJSONString());
		 
		 
		 request.body(requestParams.toJSONString());
		 
		 Response response = request.post("/challenge/output");
		 
		 int statusCode = response.getStatusCode();
		 System.out.println("The status code recieved: " + statusCode);
		 
		 System.out.println("Response body: " + response.body().asString());
		 
	}

	public int timeDiff(String date1, String date2) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1;
			d1 = df.parse(date1);
			Date d2;
			d2 = df.parse(date2);
			long diffInMilliseconds = Math.abs(d1.getTime() - d2.getTime());
			long time = diffInMilliseconds/(1000*60);
			System.out.println(time);
			return (int) time;
	}
	
	
	// function to sort hashmap by values 
    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm){
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list =   new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        Collections.reverse(list);
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
    

}
