package com.qa.rest.api.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.bson.json.JsonWriterSettings;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.rest.api.utils.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC01GETReq {
	
	  public static ExtentReports ereports;
	  public static ExtentTest eTest;
	  public static String responseBody = "";
	  public static String requestPayload = "";
	
	
	@BeforeTest
	public void setUp() {
		ereports = new ExtentReports(System.getProperty("user.dir")+"\\test-output\\MyAutoReport.html");
		ereports.addSystemInfo("UserName","Krishna Pemmaraju");
		ereports.addSystemInfo("Application Running","API Testing");
		ereports.addSystemInfo("PLATFORM","WINDOWS");
	}
	
	@AfterTest
	public void extentTest() {
		ereports.flush();
	}
	
	@Test(enabled=false)
	public void getWeatherCondition() {
		
		//Specify the Base URI 
		
		RestAssured.baseURI="http://restapi.demoqa.com/utilities/weather/city";
		// Representing the Request Object 
		RequestSpecification httpRequest = RestAssured.given();
		//Create Response Object
		Response httpResponse =  httpRequest.request(Method.GET,"/Hyderabad");
		
		//Printing the Response
		System.out.println("Response Body is : " + httpResponse.getBody().asString());
		
		// Capture the Status code 
		
		int StatusCode = httpResponse.getStatusCode();
		Assert.assertEquals(StatusCode,200);
		if ( StatusCode == 200) {
			System.out.println("Passsed with valid Status code :" + StatusCode);
		}else {
			System.out.println("Passsed with In valid Status code :" + StatusCode);

		}
		
		// Status Line Verification 
		String statusLine = httpResponse.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(enabled=false)
	public void TC002_POSTREQUEST() {
		
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification httpRequest = RestAssured.given();

		// request Payload sending along with POST request
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("FirstName", "XXTEST3");
	    requestParams.put("LastName", "XXTEST4");
	    requestParams.put("UserName", "XXTESTDATA2");
	    requestParams.put("Password", "JohnterSD");
	    requestParams.put("Email", "xxtestdata2@gmail.com");
	    
	    // Specify the Header
	    httpRequest.header("Content-Type","application/json");
	    
	    System.out.println(requestParams.toJSONString());
	    
	    //Send the parameters to the Body 
	    httpRequest.body(requestParams.toJSONString());

	    Response httpResponse = httpRequest.request(Method.POST,"/register");
	    
	    String responseBody = httpResponse.getBody().asString();
	    System.out.println(responseBody);
	    
	    // Validate the Response Body
	    Assert.assertEquals(httpResponse.getStatusCode(), 201);
	    Assert.assertEquals(httpResponse.jsonPath().get("SuccessCode"),"OPERATION_SUCCESS");
	    Assert.assertEquals(httpResponse.jsonPath().get("Message"),"Operation completed successfully");
	}
	
	@Test(enabled = false)
	public void TC003_GETREQUESTVALIDHEADERS() {
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		RequestSpecification httpRequest = RestAssured.given();
		
		Response httpResponse = httpRequest.request(Method.GET,"maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyBnZKPeMCFJhSi0p9p3pFCNl7YflW4Hg2c");
	    
		String responseBody = httpResponse.getBody().asString();
		System.out.println(responseBody);

		//Capturing the Headers
		
		httpResponse.header("Content-Type");
		Assert.assertEquals(httpResponse.header("Content-Type"), "application/xml; charset=UTF-8");
		
		httpResponse.header("Content-Encoding");
		Assert.assertEquals(httpResponse.header("Content-Encoding"), "gzip");

		/*httpResponse.header("Cache-Control");
		Assert.assertEquals(httpResponse.header("Cache-Control"), "public, max-age=300");*/
		
		/*httpResponse.header("Content-Length");
		Assert.assertEquals(httpResponse.header("Content-Length"), "6756");*/
	}
	
	@Test(enabled = false)
	public void TC004_GETREQUESTPRINTALLHEADERS() {
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		RequestSpecification httpRequest = RestAssured.given();
		Response httpResponse = httpRequest.request(Method.GET,"/maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyBnZKPeMCFJhSi0p9p3pFCNl7YflW4Hg2c");
	      
		String responseBody = httpResponse.getBody().asString();
				
		Headers headersRes = httpResponse.headers();
				
		for ( Header header :  headersRes) {
			 System.out.println(header.getName() + " - " + header.getValue());
		}
	}
	
	@Test(enabled=false)
	public void TC005_GETREQUESTVALIDATARESPONSEBODY() {
		 RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		 RequestSpecification httpRequest = RestAssured.given();
		 
		 Response httpResponse = httpRequest.request(Method.GET,"/Hyderabad");
		 String responseBody = httpResponse.getBody().asString();
		 
		 System.out.println(responseBody);
		 
		 Assert.assertEquals(responseBody.contains("Hyderabad"), true);
		 Assert.assertEquals(responseBody.contains("55"), true);		 
	}
	
	@Test(enabled=false)
	public void TC006_GETREQUESTEXTRACTRESPONSEBODYVALUES() {
		 RestAssured.baseURI="http://restapi.demoqa.com/utilities/weather/city";
		 RequestSpecification httpRequest = RestAssured.given();
		 
		 Response httpResponse = httpRequest.request(Method.GET,"/Hyderabad");
		// String responseBody = httpResponse.getBody().asString();
		 
		 JsonPath jsonPath = httpResponse.jsonPath();
		 System.out.println(jsonPath.get("City").toString());
		 System.out.println(jsonPath.get("Temperature").toString());
		 System.out.println(jsonPath.get("Humidity").toString());
		 System.out.println(jsonPath.get("WeatherDescription").toString());
		 System.out.println(jsonPath.get("WindSpeed").toString());
		 
		 Assert.assertEquals(jsonPath.get("WindSpeed").toString(), "1.5 Km per hour");
	}
	
	@Test(enabled = false)
	public void TC007_GETAUTHENTICATIONREQUEST() {
		RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
		
		//Basic Authentication 
		PreemptiveBasicAuthScheme basicAuthScheme = new PreemptiveBasicAuthScheme();
		basicAuthScheme.setUserName("ToolsQA");
		basicAuthScheme.setPassword("TestPassword");

		RestAssured.authentication=basicAuthScheme;
		
		RequestSpecification httpRequest = RestAssured.given();
		
		Response httpResponse = httpRequest.request(Method.GET,"/");
		String responseBody = httpResponse.getBody().asString();
		System.out.println(responseBody);
		
		int statusCode = httpResponse.getStatusCode();
		Assert.assertEquals(statusCode,200);
	 }
	
	@DataProvider
	public Object[][] getDatafromExcel() throws EncryptedDocumentException, IOException{
		Object[][] data = TestUtils.getExcelData("TestData");
		return data;
	}
	
	@Test(enabled=true, dataProvider="getDatafromExcel")
	public void TC008_GETDATAFROMEXCEL(String FirstName,String LastName,String UserName, String Password , String Email) {
		 eTest = ereports.startTest("Starting the API Testing For Inserting User: -- " + FirstName);
		 RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		 RequestSpecification httpRequest = RestAssured.given();
		 
		 JSONObject reqPara = new JSONObject();
		 reqPara.put("FirstName",FirstName);
		 reqPara.put("LastName",LastName);
		 reqPara.put("UserName",UserName);
		 reqPara.put("Password",Password);
		 reqPara.put("Email",Email);
		 
		 httpRequest.header("Content-Type","application/json");
		 
		 httpRequest.body(reqPara.toJSONString());
		 
		 requestPayload = reqPara.toString();
		 
		 Response httpResponse = httpRequest.request(Method.POST,"/register");
		 responseBody = httpResponse.getBody().asString();	 
		 Assert.assertEquals(httpResponse.getStatusCode(),201);
	}
	
	@AfterMethod
	public void teardown(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			eTest.log(LogStatus.FAIL, result.getName() + " Got Failed ");
			eTest.log(LogStatus.FAIL, " REQUEST PAYLOAD  -- " + requestPayload);
			eTest.log(LogStatus.FAIL, " RESPONSE PAYLOAD -- " +  responseBody);
			eTest.log(LogStatus.FAIL, result.getThrowable());
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			eTest.log(LogStatus.PASS, result.getName() + " Got Passed ");
			eTest.log(LogStatus.PASS, " REQUEST PAYLOAD  -- " + requestPayload);
			eTest.log(LogStatus.PASS, " RESPONSE PAYLOAD -- " + responseBody);
		}
		if(result.getStatus() == ITestResult.SKIP) {
			eTest.log(LogStatus.SKIP, result.getName() + " Got SKIPPED ");
			eTest.log(LogStatus.SKIP, "REQUEST PAYLOAD  -- " + requestPayload);
			eTest.log(LogStatus.SKIP, "RESPONSE PAYLOAD -- "+ responseBody);
		}
	}
	
	}

