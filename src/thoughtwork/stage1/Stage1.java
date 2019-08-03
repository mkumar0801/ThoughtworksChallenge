package thoughtwork.stage1;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Stage1 {
	
	public static void DisplayAllNodesInWeatherAPI()
	{
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + jsonPathEvaluator.get("City"));

		// Print the temperature node
		System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));

		// Print the humidity node
		System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));

		// Print weather description
		System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));

		// Print Wind Speed
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindSpeed"));

		// Print Wind Direction Degree
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
	}

	public static void checkStage() {
		
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification httpRequest = RestAssured.given().header("userid","ZvUYckbjT");
		Response response = httpRequest.get("/challenge");
		System.out.println(response.asString());

		//First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
		
		System.out.println(jsonPathEvaluator.get("sampleInput.input.encryptedMessage").toString());
		System.out.println(jsonPathEvaluator.get("sampleInput.input.key").toString());

	}
	
	public void solveStage1() {
		String encryptedMessage = "";
		int key=0;
		DecryptMessage decrypt = new DecryptMessage();
		String decryptedMessage = "";
		
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification httpRequest = RestAssured.given().header("userid","ZvUYckbjT");
		Response response = httpRequest.get("/challenge/input");
				

		//First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();
	
		encryptedMessage = jsonPathEvaluator.get("encryptedMessage").toString();
		key= Integer.parseInt(jsonPathEvaluator.get("key").toString());
		
		//decrypt the message
		decryptedMessage = decrypt.dycriptMessage(encryptedMessage, key);
		
		//post the answer
		postDecryptedMessage(decryptedMessage);
		
		
		System.out.println(response.asString());
		System.out.println(jsonPathEvaluator.get("encryptedMessage").toString());
		System.out.println(jsonPathEvaluator.get("key").toString());
		
		
		System.out.println("Decrypted String: " + decryptedMessage);
	}
	
	@SuppressWarnings("unchecked")
	public void postDecryptedMessage(String decryptedMessage ) {
		RestAssured.baseURI = "https://http-hunt.thoughtworks-labs.net";
		RequestSpecification request = RestAssured.given()
				 .header("userid","ZvUYckbjT")
				 .header("Content-Type", "application/json");
		 
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("message", decryptedMessage); // Cast
		 
/*		 requestParams.put("LastName", "Singh");
		 requestParams.put("UserName", "sdimpleuser2dd2011");
		 requestParams.put("Password", "password1"); 
		 requestParams.put("Email",  "sample2ee26d9@gmail.com");*/
		 
		 request.body(requestParams.toJSONString());
		 Response response = request.post("/challenge/output");
		 
		 int statusCode = response.getStatusCode();
		 System.out.println("The status code recieved: " + statusCode);
		 
		 System.out.println("Response body: " + response.body().asString());
	}

}
