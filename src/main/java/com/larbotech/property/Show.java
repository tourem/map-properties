package com.larbotech.property;

import de.vandermeer.asciitable.AsciiTable;

public class Show {

    public static void showResultForTeam(Team team) {
        // Print the list objects in tabular format.

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Equipe", "Asset", "Coverage", "Locs", "Debt");

        team.getProjects().forEach(project -> {
            at.addRule();
            at.addRow(project.getTeam(), project.getName(), project.getCoverage(), project.getLocs(),
                    project.readableDebt());
        });

        at.addRule();
        at.addRow("Total", team.getName(), team.getCoverage(), team.getLocs(), team.readableDebt());
        at.addRule();
        System.out.println(at.render());

    }

    public static void displayReport(Teams teams) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Equipe", "Coverage", "Locs", "Debt");

        teams.getTeams().forEach(team -> {
            at.addRule();
            at.addRow(team.getName(), team.getCoverage(), team.getLocs(), team.readableDebt());
        });

        at.addRow("Total", teams.getName(), teams.getCoverage(), teams.getLocs(), teams.readableDebt());
        at.addRule();
        System.out.println(at.render());

    }

    public static void showResultForTeams(Teams teams) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s %15s", "Equipe", "Coverage", "Locs", "Debt");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        for (Team team : teams.getTeams()) {
            System.out.format("%10s %30f %20d %15s", team.getName(), team.getCoverage(), team.getLocs(),
                    team.readableDebt());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.format("%10s %30f %20d %15s", "Total", teams.getCoverage(), teams.getLocs(), teams.readableDebt());
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

    }
}
