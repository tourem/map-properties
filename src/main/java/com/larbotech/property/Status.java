package com.bnpp.zephyr.tools.sonar.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
    private QualityGateStatusEnum qualityGateStatus;
    private Integer bugs;
    private Integer vulnerabilities;
    private Integer codeSmells;
}
