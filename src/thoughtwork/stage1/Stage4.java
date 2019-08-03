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

public class Stage4 {
	
	
	public void solveStage4() throws ParseException {
		
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification httpRequest = RestAssured.given().header("userid","ZvUYckbjT");
		Response response = httpRequest.get("/challenge/input");
		
		
		LinkedList<String> nameList = new LinkedList<String>();
		LinkedList<String> weightList = new LinkedList<String>();
		LinkedList<String> valueList = new LinkedList<String>();
		LinkedList<String> finalList = new LinkedList<String>();
		
		HashMap<String, Integer> nameValueMap = new HashMap<String, Integer>();
		HashMap<String, Integer> nameWeightMap = new HashMap<String, Integer>();
	
		
		
			
		//First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
	
		String name = jsonPathEvaluator.get("tools.name").toString();
		String weight = jsonPathEvaluator.get("tools.weight").toString();
		String value = jsonPathEvaluator.get("tools.value").toString();
		String maximumWeight = jsonPathEvaluator.get("maximumWeight").toString();
		int maxWeight = Integer.parseInt(maximumWeight.replaceAll(" ", ""));
		
		name.replaceAll(" ", "");
		name = name.replaceAll("\\[", "").replaceAll("\\]","");
		String [] nameArray = name.split(",");
		
		
		weight.replaceAll(" ", "");
		weight = weight.replaceAll("\\[", "").replaceAll("\\]","");
		String [] weightTimeArray = weight.split(",");
		
		value.replaceAll(" ", "");
		value = value.replaceAll("\\[", "").replaceAll("\\]","");
		String [] valueArray = value.split(",");
		
		
		for(int i=0;i<nameArray.length;i++) {
			nameList.add(nameArray[i].replaceAll(" ", ""));
		}
		
		for(int i=0;i<weightTimeArray.length;i++) {
			weightList.add(weightTimeArray[i].replaceAll("", ""));
		}
		
		for(int i=0;i<valueArray.length;i++) {
			valueList.add(valueArray[i].replaceAll("", ""));
		}
		
		
		for(int i=0;i<nameList.size();i++) {
			nameValueMap.put(nameList.get(i), Integer.parseInt(valueList.get(i).toString().replaceAll(" ", "")));
			nameWeightMap.put(nameList.get(i), Integer.parseInt(weightList.get(i).toString().replaceAll(" ", "")));
		}
		
		nameValueMap = sortByValue(nameValueMap);
		
		int totalWeight = 0;
		int totalValue = 0;
		for(String n : nameValueMap.keySet()) {
			
			if(totalWeight < maxWeight) {

				totalWeight = totalWeight + nameWeightMap.get(n);
				if(totalWeight <= maxWeight) {
					finalList.add(n);
				}else {
					totalWeight = totalWeight - nameWeightMap.get(n);
				}
				System.out.println("Total Weight: " + totalWeight);
				totalValue = totalValue + nameValueMap.get(n);
				System.out.println("Total Value: " + totalValue);
			}
			

		}
		
		
		
		System.out.println(response.asString());
		System.out.println("Max Weight: " + maxWeight );
		System.out.println(name);
		System.out.println(weight);
		System.out.println(value);
		
		System.out.println(nameList);
		System.out.println(weightList);
		System.out.println(valueList);
		
		System.out.println(nameValueMap);
		System.out.println(nameWeightMap);
		
		System.out.println(finalList);
		
		System.out.println("--------------------");



		postDecryptedMessage(finalList);
		
	}
	
	@SuppressWarnings("unchecked")
	public void postDecryptedMessage(LinkedList<String> finalList) {
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification request = RestAssured.given()
				 .header("userid","ZvUYckbjT")
				 .header("Content-Type", "application/json");
		 
		
		JSONObject requestParams = new JSONObject();
		JSONArray jarray = new JSONArray();
		
		for(String s : finalList) {
			jarray.add(s);
		}
		
		requestParams.put("toolsToTakeSorted", jarray);

		 
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
