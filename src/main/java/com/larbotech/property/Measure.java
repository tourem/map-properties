package com.bnpp.zephyr.tools.sonar.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a measure
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Measure {
    /**
     * Name of the metric
     */
    private String metric;
    /**
     * Value of the metric
     */
    private String value;
}
