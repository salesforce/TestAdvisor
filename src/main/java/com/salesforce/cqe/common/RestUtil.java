package com.salesforce.cqe.common;

import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;

/*
common REST API utility for HTTP methods
 */
public class RestUtil {

    /**
     * common method to initiate the HTTP GET request with headers and parameters. Response object is returned.
     * @param url rest api url
     * @param headers required headers to be passed in request
     * @param parameters required parameters to be passed in request
     * @return response object received
     */
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
            // exception is thrown if get request is not sent e.g. no internet connectivity etc.
            throw new RuntimeException(ex);
        }
    }


    /**
     * common method to initiate the HTTP GET request with basic authentication type authorization
     * and returning the response object
     * @param url      rest api url
     * @param username basic auth key to be passed in request
     * @param password basic auth password to be passed in request
     * @return response object received  to be passed in request
     */
    public static Response getResponseWithBasicAuth(String url, String username, String password) {
        try {
            return given()
                    .auth().preemptive().basic(username, password)
                    .when()
                    .get(url);
        } catch (Exception ex) {
            // exception is thrown if get request is not sent e.g. no internet connectivity etc.
            throw new RuntimeException(ex);
        }
    }

}
