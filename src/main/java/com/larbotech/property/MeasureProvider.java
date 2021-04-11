package com.bnpp.zephyr.tools.sonar.providers;


import com.bnpp.zephyr.tools.sonar.exception.BadSonarQubeRequestException;
import com.bnpp.zephyr.tools.sonar.exception.SonarQubeException;
import com.bnpp.zephyr.tools.sonar.model.Measure;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.sonarqube.ws.client.GetRequest;
import org.sonarqube.ws.client.HttpConnector;
import org.sonarqube.ws.client.WsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides issue items
 */
@Component
public class MeasureProvider {

    private static final String GET_MEASURES_PATH = "/component?componentKey=%s&metricKeys=coverage,sqale_index,ncloc&branch=%s";

    private static final String SPACE = " ";

    /**
     * Field to search in json to get the component
     */
    private static final String COMPONENT = "component";
    /**
     * Field to search in json to get measures
     */
    private static final String MEASURES = "measures";

    @Autowired
    private HttpConnector httpConnector;

    @Autowired
    private Gson gson;

    /**
     * Get all the measures of a project
     *
     * @return Array containing all the measures
     * @throws BadSonarQubeRequestException when the server does not understand the request
     * @throws SonarQubeException           When SonarQube server is not callable.
     */
    public List<Measure> getMeasures(String projectKey, String branch) throws BadSonarQubeRequestException, SonarQubeException {
        // send a request to sonarqube server and return th response as a json object
        // if there is an error on server side this method throws an exception
        final JsonObject jo = request(String.format(GET_MEASURES_PATH,
                projectKey, branch));

        // json element containing measure information
        final JsonElement measuresJE = jo.get(COMPONENT).getAsJsonObject().get(MEASURES);
        // put json in a list of measures
        final Measure[] tmp = (gson.fromJson(measuresJE, Measure[].class));

        // then add all measure to the results list
        // return the list
        return new ArrayList<>(Arrays.asList(tmp));
    }


    /**
     * Execute a given request
     *
     * @param path Url for the request, for example /toto/list
     * @return Server's response as a JsonObject
     * @throws BadSonarQubeRequestException if SonarQube Server sent an error
     * @throws SonarQubeException           When SonarQube server is not callable.
     */
    private JsonObject request(final String path)
            throws BadSonarQubeRequestException, SonarQubeException {
        // do the request to the server and return a string answer
        final String raw = stringRequest(path);

        // prepare json
        final JsonElement json;

        // verify that the server response was correct
        try {
            json = gson.fromJson(raw, JsonElement.class);
        } catch (Exception e) {
            throw new BadSonarQubeRequestException("Server answered: " + raw +
                    SPACE + e.getMessage());
        }

        // get the json object version
        final JsonObject jsonObject;

        try {
            jsonObject = json.getAsJsonObject();
        } catch (NullPointerException e) {
            throw new BadSonarQubeRequestException("Empty server response, reason might be : " +
                    "server certificate not in JRE/JDK truststore, ...");
        }

        // verify if an error occurred
        isErrorFree(jsonObject);

        return jsonObject;
    }


    /**
     * Check if the server has sent an error
     *
     * @param jsonObject The response from the server
     * @throws BadSonarQubeRequestException thrown if the server do not understand our request
     */
    private void isErrorFree(final JsonObject jsonObject) throws BadSonarQubeRequestException {
        // we retrieve the exception
        final JsonElement error = jsonObject.get("errors");
        // if there is an error we search the message and throw an exception
        if (error != null) {
            // Json object of the error
            final JsonObject errorJO = error.getAsJsonArray().get(0).getAsJsonObject();
            // get the error message
            final JsonElement errorElement = errorJO.get("msg");
            final String errorMessage = (gson.fromJson(errorElement, String.class));
            // throw exception if there was a problem when dealing with the server
            throw new BadSonarQubeRequestException(errorMessage);
        }
    }

    /**
     * Get the raw string response
     *
     * @param path
     * @return the server's response as a string
     * @throws SonarQubeException           When SonarQube server is not callable.
     * @throws BadSonarQubeRequestException if SonarQube Server sent an error
     */
    private String stringRequest(final String path) throws SonarQubeException, BadSonarQubeRequestException {
        // prepare the request by replacing some relevant special characters
        // replace spaces
        String preparedRequest = path.replace(" ", "%20");
        // replace + characters
        preparedRequest = preparedRequest.replace("+", "%2B");

        // launch the request on SonarQube server and retrieve resources into a string
        return call(preparedRequest);
    }

    private String call(String path) throws SonarQubeException, BadSonarQubeRequestException {
        WsResponse response;
        try {
            response = httpConnector.call(new GetRequest(path));
        } catch (Exception e) {
            throw new SonarQubeException("Impossible to reach SonarQube instance.", e);
        }
        // Throws exception with advice to cnesreport user
        switch (response.code()) {
            case 401:
                throw new BadSonarQubeRequestException("Unauthorized error sent by SonarQube server (code 401), please provide a valid authentication token to cnesreport.");
            case 403:
                throw new BadSonarQubeRequestException("Insufficient privileges error sent by SonarQube server (code 403), please check your permissions in SonarQube configuration.");
            case 404:
                throw new BadSonarQubeRequestException(String.format("Not found error sent by SonarQube server (code 404, URL %s, Error %s), please check cnesreport compatibility with your SonarQube server version.", response.requestUrl(), response.content()));
            default:
                break;
        }
        return response.content();
    }
}
