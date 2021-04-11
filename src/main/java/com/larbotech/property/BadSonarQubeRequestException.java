package com.bnpp.zephyr.tools.sonar.exception;
/**
 * Thrown when a request is not recognize by SonarQube
 */
public class BadSonarQubeRequestException extends Exception {

    /**
     * Constructor
     * @param message the text to print (exception's details)
     */
    public BadSonarQubeRequestException(final String message) {
        super(message);
    }
}
