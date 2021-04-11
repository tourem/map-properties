package com.bnpp.zephyr.tools.sonar.service;

import com.bnpp.zephyr.tools.sonar.config.ProjectsConfig;
import com.bnpp.zephyr.tools.sonar.exception.BadSonarQubeRequestException;
import com.bnpp.zephyr.tools.sonar.exception.SonarQubeException;
import com.bnpp.zephyr.tools.sonar.model.*;
import com.bnpp.zephyr.tools.sonar.providers.MeasureProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MeasureService {

    private final ProjectsConfig projectsConfig;

    private final MeasureProvider measureProvider;

    @Autowired
    public MeasureService(ProjectsConfig projectsConfig, MeasureProvider measureProvider) {
        this.projectsConfig = projectsConfig;
        this.measureProvider = measureProvider;
    }


    public Teams getReport() throws BadSonarQubeRequestException, SonarQubeException {
        Teams teams = new Teams();
        teams.addTeam(getReport(projectsConfig.getAndy(), SquadEnum.ANDY));
        teams.addTeam(getReport(projectsConfig.getDna(), SquadEnum.DNA));
        teams.addTeam(getReport(projectsConfig.getIrma(), SquadEnum.IRMA));
        return teams;
    }

    private Team getReport(Map<String, ProjectsConfig.KeyBranch> squadConfig, SquadEnum squad) throws BadSonarQubeRequestException, SonarQubeException {
        List<Measure> measures;
        Team team = new Team();
        team.setName(squad.name());
        for (Map.Entry<String, ProjectsConfig.KeyBranch> entry : squadConfig.entrySet()) {
            measures = measureProvider.getMeasures(entry.getValue().getKey(), entry.getValue().getBranch());
            team.addProject(toProject(squad, entry.getKey(), measures));
        }
        return team;
    }


    private Project toProject(SquadEnum squad, String projectName, List<Measure> measures) {
        Double converage = measures.stream().filter(m -> MeasureType.COVERAGE.getName().equals(m.getMetric())).findFirst().map(c -> Double.parseDouble(c.getValue())).orElse(0d);
        Integer debt = measures.stream().filter(m -> MeasureType.SQALE_INDEX.getName().equals(m.getMetric())).findFirst().map(c -> Integer.parseInt(c.getValue())).orElse(0);
        Long loc = measures.stream().filter(m -> MeasureType.NCLOC.getName().equals(m.getMetric())).findFirst().map(c -> Long.parseLong(c.getValue())).orElse(0L);
        return new Project(squad.name(), projectName, converage, loc, debt);
    }
}
