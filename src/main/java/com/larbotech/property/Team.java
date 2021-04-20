package com.bnpp.zephyr.tools.sonar.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.apache.commons.math3.util.Precision.round;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private String name;

    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project) {
        projects.add(project);
    }

    public Double getCoverage() {
        return round(projects.stream().mapToDouble(Project::getCoverage).average().orElse(0d),2);

    }

    public Long getLineOfCode() {
        return projects.stream().mapToLong(Project::getLineOfCode).sum();
    }

    public Integer getDebt() {
        return projects.stream().mapToInt(Project::getDebt).sum();
    }

    public String readableDebt() {
        Integer debt = getDebt();
        return Project.timeConvert(debt);
    }
}
