package com.bnpp.zephyr.tools.sonar.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.math3.util.Precision.round;

@Getter
@Setter
public class Teams {

    private List<Team> teams = new ArrayList<>();

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Double getCoverage() {
        return round(teams.stream().mapToDouble(Team::getCoverage).average().orElse(0d), 2);
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
