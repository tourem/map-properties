package com.bnpp.zephyr.tools.sonar.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Branch {
    private String name;
    private boolean main;
    private BranchTypeEnum type;
    private Status status;
    private Date analysisDate;
}
