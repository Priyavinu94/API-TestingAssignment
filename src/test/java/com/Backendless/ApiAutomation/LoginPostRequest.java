package com.Backendless.ApiAutomation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.Backendless.ApiAutomation.BackendlessAPI.TestBase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoginPostRequest extends TestBase {

	@Before
	public void initialize() {
		RestAssured.baseURI = prop.getProperty("baseURI");
	}

	@Test
	public void validateSuccessStatusCodeAfterUserLogin() {

		RequestSpecification request = RestAssured.given();
		request.headers(prop.getProperty("requestHeaderKey"), prop.getProperty("requestHeaderValue"));

		request.body(createRequestBody());

		Response response = request.post("/login");

		Assert.assertEquals("Status code not as expected", 200, response.statusCode());

		JsonPath jsonpath = response.jsonPath();

		String ownerId = jsonpath.getString("ownerId");
		String userToken = jsonpath.getString("user-token");

		// multiple assertions
		Assert.assertEquals(prop.getProperty("loginUserStatus"), jsonpath.getString("userStatus"));
		Assert.assertEquals(prop.getProperty("loginAccountType"), jsonpath.getString("accountType"));
		Assert.assertTrue("User token should not be null", userToken != null);
		Assert.assertTrue("Owner ID should match object ID", ownerId.equals(jsonpath.getString("objectId")));
	}

	@Test
	public void validateFailureStatusCode() {
		// calling using chained method
		RestAssured.given().headers("", "").body(createRequestBody()).when().post("/login").then().statusCode(400);
	}

}
