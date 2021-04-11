package com.bnpp.zephyr.tools.sonar.exception;


/**
 * Thrown when an error occurred when contacting SonarQube
 */
public class SonarQubeException extends Exception {

    /**
     * Extended constructor
     * @param message the text to print (exception's details)
     * @param exception the parent exception of this one
     */
    public SonarQubeException(final String message, final Exception exception) {
        super(message, exception);
    }

    /**
     * Constructor
     * @param message the text to print (exception's details)
     */
    public SonarQubeException(final String message) {
        super(message);
    }
}
