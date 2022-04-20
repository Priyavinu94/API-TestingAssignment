package com.Backendless.ApiAutomation;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.Backendless.ApiAutomation.BackendlessAPI.TestBase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RegisterPostRequest extends TestBase {

	@Before
	public void initialize() {
		RestAssured.baseURI = prop.getProperty("baseURI");
	}

	@Test
	public void validateRegisteredUserNotAbleToRegisterAgain() {
		// Create request object
		RequestSpecification request = RestAssured.given();

		// Added headers
		request.headers(prop.getProperty("requestHeaderKey"), prop.getProperty("requestHeaderValue"));
		// Add request body -- create json body first and then add
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", prop.getProperty("userEmail"));
		jsonObj.put("password", prop.getProperty("userPassword"));
		request.body(jsonObj);

		Response response = request.post(prop.getProperty("/register"));
		
		response.then().statusCode(Integer.valueOf(prop.getProperty("existingUserRegErrorCode")));
		
		JsonPath jsonPath = response.jsonPath();
	//	System.out.println(response.asPrettyString());
	
		// validate code number in the response
		Assert.assertEquals("Error code not as expected", prop.getProperty("userRegistrationResponseCode"),
				jsonPath.getString("code"));

		// validate message in the response
		Assert.assertEquals(true, jsonPath.getString("message").contains("User already exists"));
	}
}
