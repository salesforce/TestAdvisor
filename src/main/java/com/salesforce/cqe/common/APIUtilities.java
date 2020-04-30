package com.salesforce.cqe.common;

import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

/*
common API utility methods
 */
public class APIUtilities {


    public static Response getResponse(String url, Map<String, String> headers, Map<String, String> parameters) {
        try {
            Response response = given()
                    .headers(headers)
                    .params(parameters)
                    .when()
                    .get(url);
            System.out.println("get method response: " + response.asString());
            return response;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     * make a get call with basic auth parameters
     *
     * @param url      rest api url
     * @param username basic auth key
     * @param password basic auth password
     * @return response object received
     */
    public static Response getResponseWithBasicAuth(String url, String username, String password) {
        try {
            return given()
                    .auth().preemptive().basic(username, password)
                    .when()
                    .get(url);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
