package com.bnpp.zephyr.tools.sonar.utils;

import com.bnpp.zephyr.tools.sonar.model.Team;
import com.bnpp.zephyr.tools.sonar.model.Teams;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a8.A8_Grids;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static com.bnpp.zephyr.tools.sonar.utils.Constants.*;

@Slf4j
public final class ConsoleDisplay {

    public static void displayReport(Team team) {
        // Print the list objects in tabular format.
        displayHead(team.getName(), AsciiTable::addHeavyRule);

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Equipe", "Asset", "Coverage", "Locs", "Debt");

        team.getProjects().forEach(project -> {
            at.addRule();
            at.addRow(project.getTeam(), project.getName(), project.getCoverage() + PERCENTAGE, project.getLineOfCode(),
                    project.readableDebt());
        });

        at.addRule();
        at.addRow(TOTAL, team.getName(), team.getCoverage() + PERCENTAGE, team.getLineOfCode(), team.readableDebt());
        at.addRule();
        //log ne marche pas pour un bon display en console du table !!
        System.out.println(at.render());

    }

    public static void displayReport(Teams teams) {
        displayHead(SUMMARY, AsciiTable::addStrongRule);

        AsciiTable at = new AsciiTable();
        at.addRule();
        //at.addStrongRule();
        at.addRow("Equipe", "Coverage", "Locs", "Debt");

        teams.getTeams().forEach(team -> {
            at.addRule();
            at.addRow(team.getName(), team.getCoverage() + PERCENTAGE, team.getLineOfCode(), team.readableDebt());
        });

        at.addRule();
        at.addRow(TOTAL, teams.getCoverage() + PERCENTAGE, teams.getLineOfCode(), teams.readableDebt());
        at.addRule();
        // at.getContext().setGrid(A8_Grids.lineDoubleBlocks());
        //at.addHeavyRule();
        //log ne marche pas pour un bon display en console du table !!
        System.out.println(at.render());


    }

    private static void displayHead(String title, Consumer<AsciiTable> asciiTableConsumer) {
        AsciiTable at = new AsciiTable();
        asciiTableConsumer.accept(at);
        at.addRow("", title);
        asciiTableConsumer.accept(at);
        at.getContext().setGrid(A8_Grids.lineDoubleBlocks());
        System.out.println(at.render());
    }
}
