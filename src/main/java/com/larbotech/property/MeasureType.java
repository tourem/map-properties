package com.bnpp.zephyr.tools.sonar.model;

import lombok.Getter;

@Getter
public enum MeasureType {
    SQALE_INDEX("sqale_index"),
    COVERAGE("coverage"),
    NCLOC("ncloc");

    private final String name;

    MeasureType(String name) {
        this.name = name;
    }


}
