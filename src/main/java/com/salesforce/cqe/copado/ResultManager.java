/**
 * 
 */
package com.salesforce.cqe.copado;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.util.Strings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.stream.MalformedJsonException;
import com.salesforce.cqe.common.JsonHelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author gneumann
 *
 */
public class ResultManager {
	public enum Status {
		QUEUED("Queued", false), INPROGRESS("In Progress", false), SUCCESS("Completed successfully", true),
		WARNINGS("Completed with warnings", true), ERRORS("Completed with errors", true);

		private final String statusMsg;
		private final boolean isCompleted;

		Status(String statusMsg, boolean isCompleted) {
			this.statusMsg = statusMsg;
			this.isCompleted = isCompleted;
		}

		public boolean isCompleted() {
			return this.isCompleted;
		}

		public static Status fromString(String value) {
			if (Strings.isNullOrEmpty(value))
				return null;

			for (Status s : Status.values()) {
				if (value.equals(s.statusMsg))
					return s;
			}
			return null;
		}

		@Override
		public String toString() {
			return statusMsg;
		}
	}

	// generic purpose constants
	private static final String APPLICATION_TYPE = "application/json";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";
	private static final String GRANT_TYPE = "password";
	private static final String QUERYURI = "/services/data/v47.0/query/?q=";
	private static final String RETRIEVE_STATUS_QUERY_PRE = "SELECT copado__Batch_No__c,copado__Message__c,copado__Status__c FROM copado__Selenium_Test_Result__c WHERE copado__Selenium_Test_Run__c = '";
	private static final String RETRIEVE_STATUS_QUERY_POST = "' ORDER BY copado__Batch_No__c DESC NULLS LAST LIMIT 5";
	private static final String TOKEN_GENERATOR_URL = "https://test.salesforce.com/services/oauth2/token";

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Result getResult(Data data) throws Exception {
		Result cachedResult = Result.getResult();

		StringBuilder authorizationHeader = new StringBuilder(BEARER).append(getAccessToken(data));

		System.out.println("Start retrieving result");
		RequestSpecification request = RestAssured.given().headers(AUTHORIZATION, authorizationHeader)
				.contentType(APPLICATION_TYPE);
		String query = new StringBuilder(data.getDomainURI()).append(QUERYURI).append(RETRIEVE_STATUS_QUERY_PRE)
				.append(data.getTestRunRecordId()).append(RETRIEVE_STATUS_QUERY_POST).toString();
		Response response = request.when().get(query);

		response.then().statusCode(HttpStatus.SC_OK);
		// System.out.println(response.getBody().asString());
		JSONObject rootObject = new JSONObject(response.getBody().asString());
		int totalSize = rootObject.getInt("totalSize");

		double batchNo = -1d;
		Status status = null;
		String message = null;
		for (int i = 0; i < totalSize; i++) {
			JSONObject resultObject = new JSONObject(response.getBody().asString()).getJSONArray("records")
					.getJSONObject(i);
			status = Status.fromString(resultObject.getString("copado__Status__c"));
			if (status == null || !status.isCompleted())
				// skip any unfinished test runs
				continue;

			batchNo = resultObject.getDouble("copado__Batch_No__c");
			message = resultObject.getString("copado__Message__c");
			break;
		}

		if (batchNo > cachedResult.getBatchNo()) {
			System.out.println("New Result found under batch " + batchNo);
			cachedResult.setBatchNo(batchNo);
			cachedResult.setMessage(message);
			cachedResult.setStatusMsg(status.toString());
			cachedResult.setStatus(status);
			// write new result back to hd
			cachedResult.saveResult();
			return cachedResult;
		}
		System.out.println("No new result found");
		return null;
	}

	/**
	 * Retrieve access token for session.
	 *
	 * @return token
	 */
	private static String getAccessToken(Data data) throws Exception {
		RestAssured.useRelaxedHTTPSValidation();

		String token = null;

		System.out.println("Start retrieving access token");
		RequestSpecification request = RestAssured.given().formParam("grant_type", GRANT_TYPE)
				.formParam("client_id", data.getClientId()).formParam("client_secret", data.getClientSecret())
				.formParam("username", data.getUsername()).formParam("password", data.getPassword())
				.contentType(ContentType.URLENC);

		Response response = request.when().post(TOKEN_GENERATOR_URL);
		if (response.getStatusCode() != HttpStatus.SC_OK) {
			if (response.then().extract().contentType().equals(ContentType.HTML.toString())) {
				XmlPath xml = new XmlPath(CompatibilityMode.HTML, response.then().extract().response().asString());
				throw new Exception("Unable to retrieve access token: " + xml.getString("html.head.title"));
			} else if (response.then().extract().contentType().equals(ContentType.JSON.toString())) {
				throw new Exception("Unable to retrieve access token: "
						+ new JsonPath(response.then().extract().response().asString()).getString("error_description"));
			} else {
				throw new Exception("Unable to retrieve access token: Incorrect content type");
			}
		}

		if (response.statusCode() != HttpStatus.SC_OK) {
			throw new Exception("Unable to retrieve access token: incorrect HTTP Response: " + response.statusLine());
		}

		JsonPath jsonPath = new JsonPath(response.then().extract().response().asString());
		token = jsonPath.getString("access_token");

		if (Strings.isNullOrEmpty(token)) {
			throw new Exception("Unable to retrieve access token from response");
		}

		System.out.println("Successfully retrieved access token");
		return token;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "domainURI", "clientId", "clientSecret", "username", "password", "testRunRecordId" })
	public static class Data {
		private static final String JSON_FILENAME = "data.json";

		@JsonProperty("domainURI")
		private String domainURI;
		@JsonProperty("clientId")
		private String clientId;
		@JsonProperty("clientSecret")
		private String clientSecret;
		@JsonProperty("username")
		private String username;
		@JsonProperty("password")
		private String password;
		@JsonProperty("testRunRecordId")
		private String testRunRecordId;

		private Data() {
			; // prohibit default constructor
		}

		/**
		 * Reads JSON file "data.json" from test project's root directory.
		 * 
		 * @return last result retrieved from sandbox
		 * @throws MalformedJsonException failure during de-serialization of json file
		 */
		public static Data getData() throws MalformedJsonException {
			return JsonHelper.toObject(Data.JSON_FILENAME, Data.class);
		}

		@JsonProperty("domainURI")
		public String getDomainURI() {
			return this.domainURI;
		}

		@JsonProperty("clientId")
		public String getClientId() {
			return this.clientId;
		}

		@JsonProperty("clientSecret")
		public String getClientSecret() {
			return this.clientSecret;
		}

		@JsonProperty("username")
		public String getUsername() {
			return this.username;
		}

		@JsonProperty("password")
		public String getPassword() {
			return this.password;
		}

		@JsonProperty("testRunRecordId")
		public String getTestRunRecordId() {
			return this.testRunRecordId;
		}
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "batch_no", "message", "status_msg" })
	public static class Result {
		private static final String JSON_FILENAME = "last_result.json";

		@JsonProperty("batch_no")
		private double batchNo;
		@JsonProperty("message")
		private String message;
		@JsonProperty("status_msg")
		private String statusMsg;
		
		private Status status = null;

		private Result() {
			; // prohibit default constructor
		}

		/**
		 * Reads JSON file "last_result.json" from test project's root directory.
		 * 
		 * @return last result retrieved from sandbox
		 */
		public static Result getResult() {
			Result result = null;
			try {
				result = JsonHelper.toObject(Result.JSON_FILENAME, Result.class);
			} catch (MalformedJsonException mje) {
				result = new Result();
				result.batchNo = -1d;
				result.message = "undefined";
				result.statusMsg = "undefined";
			}
			return result;
		}

		/**
		 * Saves JSON file "last_result.json" to test project's root directory.
		 */
		public void saveResult() {
			try {
				JsonHelper.toFile(JSON_FILENAME, this);
			} catch (MalformedJsonException e) {
				e.printStackTrace();
			}
		}

		@JsonProperty("batch_no")
		public double getBatchNo() {
			return this.batchNo;
		}

		@JsonProperty("batch_no")
		public void setBatchNo(double batchNo) {
			this.batchNo = batchNo;
		}

		@JsonProperty("message")
		public String getMessage() {
			return this.message;
		}

		@JsonProperty("message")
		public void setMessage(String message) {
			this.message = message;
		}

		@JsonProperty("status_msg")
		public String getStatusMsg() {
			return this.statusMsg;
		}

		@JsonProperty("status_msg")
		public void setStatusMsg(String statusMsg) {
			this.statusMsg = statusMsg;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
	}
}
