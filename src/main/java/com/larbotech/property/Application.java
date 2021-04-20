package com.bnpp.zephyr.tools.sonar;

import com.bnpp.zephyr.tools.sonar.config.ProjectTeamConfig;
import com.bnpp.zephyr.tools.sonar.model.Teams;
import com.bnpp.zephyr.tools.sonar.service.MeasureService;
import com.bnpp.zephyr.tools.sonar.utils.ConsoleDisplay;
import com.bnpp.zephyr.tools.sonar.utils.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MeasureService measureService;

    @Autowired
    private ProjectTeamConfig projectTeamConfig;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Teams teams = measureService.getReport();
        teams.getTeams().forEach(ConsoleDisplay::displayReport);

        ConsoleDisplay.displayReport(teams);
        ExcelGenerator.generate(teams, projectTeamConfig.getSprint());
        System.exit(1);
    }
}
