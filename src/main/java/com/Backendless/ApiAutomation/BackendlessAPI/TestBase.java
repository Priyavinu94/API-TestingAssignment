package com.Backendless.ApiAutomation.BackendlessAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;

public class TestBase {
	
	public static Properties prop;
	// using which we can load configuration file, read data from the file

	public FileInputStream configFile;
	// file in which all configuration related data is stored in key-value
	
	public TestBase() {
		prop = new Properties(); // instantiating Properties class
		try {

			configFile = new FileInputStream(
					"src//main//java//com//Backendless//ApiAutomation//config//configuration.properties");
			prop.load(configFile);

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	
	public JSONObject createRequestBody() {
		JSONObject reqBody = new JSONObject();
		reqBody.put("login", prop.getProperty("userEmail"));
		reqBody.put("password", prop.getProperty("userPassword"));
		return reqBody;
	}
	
	
}
