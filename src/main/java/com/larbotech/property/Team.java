package com.larbotech.property;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        return projects.stream().mapToDouble(Project::getCoverage).average().orElse(0d);

    }

    public Long getLocs() {
        return projects.stream().mapToLong(Project::getLocs).sum();
    }

    public Integer getDebt() {
        return projects.stream().mapToInt(Project::getDebt).sum();
    }

    public String readableDebt() {
        Integer debt = getDebt();
        return Project.timeConvert(debt);
    }
}
