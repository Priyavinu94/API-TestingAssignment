package com.Backendless.ApiAutomation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.Backendless.ApiAutomation.BackendlessAPI.TestBase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateUserPutRequest extends TestBase {

	JsonPath jsonpath;
	String userToken;
	String userId;

	@Before
	public void initialize() {
		RestAssured.baseURI = prop.getProperty("baseURI");
		jsonpath = RestAssured.given()
				.header(prop.getProperty("requestHeaderKey"), prop.getProperty("requestHeaderValue"))
				.body(createRequestBody()).when().post("/login").jsonPath();
		userToken = jsonpath.getString("user-token");
		userId = jsonpath.getString("objectId");
	}

	@Test
	public void validateUpdateUserObjectSuccessCode() {

		Response response = RestAssured.given()
				.header(prop.getProperty("requestHeaderKey"), prop.getProperty("requestHeaderValue"))
				.header("user-token", userToken).body(createRequestBody()).when().put("/" + userId);
		response.then().statusCode(200);

		Assert.assertEquals("User email does not match", prop.getProperty("userEmail"),
				response.jsonPath().getString("email"));
	}

	@Test
	public void validateUpdateUserObjectFailureCode() {
		RestAssured.given().header("", "").header("user-token", userToken).body(createRequestBody()).when()
				.put("/" + userId).then().statusCode(400);
	}
}
