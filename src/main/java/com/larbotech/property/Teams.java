package com.bnpp.zephyr.tools.sonar.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teams {

    private String name;

    private List<Team> teams = new ArrayList<>();

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Double getCoverage() {
        return teams.stream().mapToDouble(Team::getCoverage).average().orElse(0d);
    }

    public Long getLineOfCode() {
        return teams.stream().mapToLong(Team::getLineOfCode).sum();
    }

    public Integer getDebt() {
        return teams.stream().mapToInt(Team::getDebt).sum();
    }

    public String readableDebt() {
        Integer debt = getDebt();
        return Project.timeConvert(debt);
    }
}
