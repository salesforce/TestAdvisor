/**
 * 
 */
package com.salesforce.cqe.copado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.util.Strings;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

/**
 * @author gneumann
 *
 */
public class ResultManager {
	// generic purpose constants
	private static final String APPLICATION_TYPE = "application/json";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";
	private static final String GRANT_TYPE = "password";
	private static final String QUERYURI = "/services/data/v37.0/query/?q=";
	private static final String RETRIEVE_STATUS_QUERY = "SELECT copado__Last_Status__c,copado__Last_Status_Date__c from copado__Selenium_Test_Run__c";
	private static final String TOKEN_GENERATOR_URL = "https://test.salesforce.com/services/oauth2/token";

	public static void retrieveStatus(String domainURI, String accessToken) {
		StringBuilder authorizationHeader = null;
		try {
			authorizationHeader = new StringBuilder(BEARER).append(accessToken);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		RequestSpecification request = RestAssured.given().headers(AUTHORIZATION, authorizationHeader)
				.contentType(APPLICATION_TYPE);
		Response response = request.when()
				.get(new StringBuilder(domainURI).append(QUERYURI).append(RETRIEVE_STATUS_QUERY).toString());

//        System.out.println(response.getBody().asString());
		response.then().statusCode(HttpStatus.SC_OK);

		JSONObject rootObject = new JSONObject(response.getBody().asString());
		int totalSize = rootObject.getInt("totalSize");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
		List<Date> runDates = new ArrayList<>();
		Map<Date, String> statusByDate = new HashMap<>();

		for (int i = 0; i < totalSize; i++) {
			JSONObject runObject = new JSONObject(response.getBody().asString()).getJSONArray("records")
					.getJSONObject(i);
			Date dateAndTimestamp = null;
			try {
				dateAndTimestamp = dateFormat.parse(runObject.getString("copado__Last_Status_Date__c"));
				statusByDate.put(dateAndTimestamp, runObject.getString("copado__Last_Status__c"));
				runDates.add(dateAndTimestamp);
			} catch (ParseException e) {
				e.printStackTrace();
				continue;
			}
		}
		Collections.sort(runDates, Collections.reverseOrder());
		statusByDate.get(runDates.get(0));

		System.out.println("Last Test Run Status? '" + statusByDate.get(runDates.get(0)) + "' on " + runDates.get(0));
	}

	/**
	 * Retrieve access token for session.
	 *
	 * @return token
	 */
	public static String getAccessToken(String clientID, String clientSecret, String username, String password)
			throws Exception {
		RestAssured.useRelaxedHTTPSValidation();

		ValidatableResponse validatableResponse;
		String token = "";
		int attempts = 0;

		while (attempts < 2) {
			try {
				System.out.println("Retrieve access token");
				RequestSpecification request = RestAssured.given().formParam("grant_type", GRANT_TYPE)
						.formParam("client_id", clientID).formParam("client_secret", clientSecret)
						.formParam("username", username).formParam("password", password)
						.contentType(ContentType.URLENC);

				Response response = request.when().post(TOKEN_GENERATOR_URL);
				if (response.getStatusCode() != HttpStatus.SC_OK) {
					if (response.then().extract().contentType().equals(ContentType.HTML.toString())) {
						XmlPath xml = new XmlPath(CompatibilityMode.HTML,
								response.then().extract().response().asString());
						throw new Exception("Unable to retrieve access token: " + xml.getString("html.head.title"));
					} else if (response.then().extract().contentType().equals(ContentType.JSON.toString())) {
						throw new Exception("Unable to retrieve access token: "
								+ new JsonPath(response.then().extract().response().asString())
										.getString("error_description"));
					} else {
						throw new Exception("Unable to retrieve access token: Incorrect content type");
					}
				}

				validatableResponse = response.then().statusCode(HttpStatus.SC_OK);
				System.out.println("Response: " + validatableResponse.extract().statusLine());

				JsonPath jsonPath = new JsonPath(response.then().extract().response().asString());
				token = jsonPath.getString("access_token");

				if (Strings.isNotNullAndNotEmpty(token)) {
//                    System.out.println("Token: " + token);
					System.out.println("Token successfully retrieved");
					return token;
				}
			} catch (Exception e) {
				System.err.println("Unable to retrieve access token: encountered exception " + e);
				attempts++;
			}
		}
		return token;
	}
}
