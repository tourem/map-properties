package com.bnpp.zephyr.tools.sonar;

import com.bnpp.zephyr.tools.sonar.model.Teams;
import com.bnpp.zephyr.tools.sonar.service.MeasureService;
import com.bnpp.zephyr.tools.sonar.utils.ConsoleDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MeasureService measureService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Teams teams = measureService.getReport();
        teams.getTeams().forEach(ConsoleDisplay::showResultForTeam);

        ConsoleDisplay.showResultForTeams(teams);
        System.exit(1);
    }
}
