package thoughtwork.stage1;

import java.util.HashMap;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Stage2 {
	
	
	public void solveStage2() {
		String hiddenTools = "";
		String tools;
		
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification httpRequest = RestAssured.given().header("userid","ZvUYckbjT");
		Response response = httpRequest.get("/challenge/input");
				
		LinkedList<String> toolsList = new LinkedList<String>();
		
		//First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
	
		hiddenTools = jsonPathEvaluator.get("hiddenTools").toString();
		tools = jsonPathEvaluator.get("tools").toString();
		tools.replaceAll(" ", "");
		tools = tools.replaceAll("\\[", "").replaceAll("\\]","");
		String [] laraTools = tools.split(",");
//		System.out.println(response.asString());
//		System.out.println(hiddenTools);
//		System.out.println(tools);
//		System.out.println(tools.length());
		
		for(int i=0;i<laraTools.length;i++) {
//			laraTools[i].replaceAll("/[", "");
//			laraTools[i].replaceAll(" ", "");
//			laraTools[i].replaceAll("/]", "");
//			System.out.println(laraTools[i].replaceAll(" ", ""));
			toolsList.add(laraTools[i].replaceAll(" ", ""));
		}
		System.out.println("Input Tools " + toolsList);
		
		FindTools findtools = new FindTools();
		HashMap<String, Boolean> foundToosl = findtools.findTools(hiddenTools, toolsList);
		postDecryptedMessage(foundToosl);
		
	}
	
	@SuppressWarnings("unchecked")
	public void postDecryptedMessage(HashMap<String, Boolean> foundToosl) {
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification request = RestAssured.given()
				 .header("userid","ZvUYckbjT")
				 .header("Content-Type", "application/json");
		 
		 JSONObject requestParams = new JSONObject();
		 JSONArray jarray = new JSONArray();
		 
		 for(String s:foundToosl.keySet()) {
			 if(foundToosl.get(s)) {
				 jarray.add(s);
			 }
		 }
		 
		 requestParams.put("toolsFound", jarray);
		 
		 System.out.println(requestParams.toJSONString());
		 
		 
		 request.body(requestParams.toJSONString());
		 Response response = request.post("/challenge/output");
		 
		 int statusCode = response.getStatusCode();
		 System.out.println("The status code recieved: " + statusCode);
		 
		 System.out.println("Response body: " + response.body().asString());
		 
	}

}
