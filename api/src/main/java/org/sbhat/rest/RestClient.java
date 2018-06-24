package org.sbhat.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import utilities.LogManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Wrapper class to rest assured client.
 *
 * All http requests are forwarded here. Advantages for this :
 * 1. We can change the underlying rest client (currently restassured) if need be.
 * 2. Most often headers/authentication details remain the same across multiple apis. We
 * don't have to keep repeating baseuri and headers in our calls.
 *
 */
class RestClient {
  private Logger logger = LogManager.getLogger(RestClient.class.getSimpleName());
  private final RequestSpecification requestSpecification;

  /**
   * Constructor to connect to customized base URI.
   * @param baseURI base uri to connect to
   */
  RestClient(String baseURI) {
    this.requestSpecification = RestAssured
        .given()
        .baseUri(baseURI)
        .contentType("application/json")
        .when();
  }

  /**
   * Default constructor.
   */
  RestClient() {
    Properties prop = new Properties();
    try {
      String propFile = System.getProperty("user.dir") + File.separator +
          ".." + File.separator + "jsonplaceholdertests.prop";
      prop.load(new FileInputStream(propFile));
    } catch (IOException e) {
      logger.error("Exception thrown while reading properties file: {}" + ExceptionUtils.getStackTrace(e));
      throw new RuntimeException("Could not read properties file {}" + ExceptionUtils.getStackTrace(e));
    }
    this.requestSpecification = RestAssured
        .given()
        .baseUri(prop.getProperty("baseuri"))
        .contentType("application/json")
        .when();
  }

  /**
   * Make a simple get call with path.
   * @param path What to get
   * @return response to the get call
   */
  Response get(String path) {
    Response response = requestSpecification.get(path);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }

  /**
   * Make a simple get call with path.
   * @param path What to get
   * @param queryKey Query to be got
   * @param queryValue with query value
   * @return response to the get call
   */
  Response get(String path, String queryKey, String... queryValue) {
    Response response = requestSpecification.queryParam(queryKey, queryValue).get(path);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }


  /**
   * Make a simple post call with path.
   * @param path where to post
   * @param body what to post
   * @return response to the get call
   */
  Response post(String path, Object body) {
    Response response = requestSpecification.body(body).post(path);
    logger.debug("Request sent:{}", body);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }

  /**
   * Make a simple put call with path.
   * @param path where to post
   * @param body what to post
   * @return response to the get call
   */
  Response put(String path, Object body) {
    Response response = requestSpecification.body(body).put(path);
    logger.debug("Request sent:{}", body);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }

  /**
   * Make a simple patch call with path.
   * @param path where to post
   * @param body what to post
   * @return response to the get call
   */
  Response patch(String path, Object body) {
    Response response = requestSpecification.body(body).patch(path);
    logger.debug("Request sent:{}", body);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }

  /**
   * Delete the path.
   * @param path Path to be deleted
   * @return response to the delete call
   */
  Response delete(String path) {
    Response response = requestSpecification.delete(path);
    logger.debug("Response code :{}\nResponse statement{}",
        response.statusCode(), response.getBody().prettyPrint());
    return response;
  }
}
