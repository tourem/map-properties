package com.larbotech.property;

public class Show {

    public static void showResultForTeam(Team team) {
        // Print the list objects in tabular format.
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %30s %20s %15s", "Equipe", "Asset", "Coverage", "Locs", "Debt");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Project project : team.getProjects()) {
            System.out.format("%10s %10s %30f %20d %15s", project.getTeam(), project.getName(), project.getCoverage(),
                    project.getLocs(), project.readableDebt());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.format("%10s %10s %30f %20d %15s", "", "Total", team.getCoverage(), team.getLocs(),
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
