package com.bnpp.zephyr.tools.sonar.utils;

import com.bnpp.zephyr.tools.sonar.model.Project;
import com.bnpp.zephyr.tools.sonar.model.Team;
import com.bnpp.zephyr.tools.sonar.model.Teams;
import de.vandermeer.asciitable.AsciiTable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ConsoleDisplay {

    public static void showResultForTeam(Team team) {
        // Print the list objects in tabular format.
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %30s %20s %15s", "Equipe", "Asset", "Coverage", "Locs", "Debt");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Project project : team.getProjects()) {
            System.out.format("%10s %10s %30f %20d %15s", project.getTeam(), project.getName(), project.getCoverage(),
                    project.getLineOfCode(), project.readableDebt());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.format("%10s %10s %30f %20d %15s", "", "Total", team.getCoverage(), team.getLineOfCode(),
                team.readableDebt());
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");

    }

    public static void showResultForTeams(Teams teams) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s %15s", "Equipe", "Coverage", "Locs", "Debt");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        for (Team team : teams.getTeams()) {
            System.out.format("%10s %30f %20d %15s", team.getName(), team.getCoverage(), team.getLineOfCode(),
                    team.readableDebt());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.format("%10s %30f %20d %15s", "Total", teams.getCoverage(), teams.getLineOfCode(), teams.readableDebt());
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

    }

    public static void displayReport(Team team) {
        // Print the list objects in tabular format.

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Equipe", "Asset", "Coverage", "Locs", "Debt");

        team.getProjects().forEach(project -> {
            at.addRule();
            at.addRow(project.getTeam(), project.getName(), project.getCoverage(), project.getLineOfCode(),
                    project.readableDebt());
        });

        at.addRule();
        at.addRow("Total", team.getName(), team.getCoverage(), team.getLineOfCode(), team.readableDebt());
        at.addRule();
        System.out.println(at.render());

    }

    public static void displayReport(Teams teams) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Equipe", "Coverage", "Locs", "Debt");

        teams.getTeams().forEach(team -> {
            at.addRule();
            at.addRow(team.getName(), team.getCoverage(), team.getLineOfCode(), team.readableDebt());
        });

        at.addRule();
        at.addRow("Total", teams.getCoverage(), teams.getLineOfCode(), teams.readableDebt());
        at.addRule();
        System.out.println(at.render());

    }
}
