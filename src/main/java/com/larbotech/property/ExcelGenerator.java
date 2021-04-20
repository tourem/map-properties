package com.bnpp.zephyr.tools.sonar.utils;

import com.bnpp.zephyr.tools.sonar.model.Project;
import com.bnpp.zephyr.tools.sonar.model.Team;
import com.bnpp.zephyr.tools.sonar.model.Teams;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bnpp.zephyr.tools.sonar.utils.Constants.*;

public class ExcelGenerator {
    private static final String[] columns_team = {"Equipe", "Asset", "Coverage", "Locs", "Debt"};
    private static final String[] columns_teams = {"Equipe", "Coverage", "Locs", "Debt"};

    private final Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

    public static void generate(Teams teams, String sprint) throws IOException {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        teams.getTeams().forEach(excelGenerator::addTeamSheet);
        excelGenerator.addSummarySheet(teams);
        excelGenerator.build(sprint);
    }

    private void addTeamSheet(Team team) {
        // Create a Sheet
        Sheet sheet = createSheet(team.getName(), columns_team);
        // Create Other rows and cells with employees data
        int rowNum = 1;

        rowNum = addProjectsData(team, sheet, rowNum);

        addTotalData(team, sheet, rowNum);
        // Resize all columns to fit the content size
        resizeAllColumns(sheet);
    }

    private void addSummarySheet(Teams teams) {
        // Create a Sheet
        Sheet sheet = createSheet(SUMMARY, columns_teams);
        // Create Other rows and cells with employees data
        int rowNum = 1;
        rowNum = addTeamData(teams, sheet, rowNum);
        addTotalData(teams, sheet, rowNum);
        // Resize all columns to fit the content size
        resizeAllColumns(sheet);
    }


    private void resizeAllColumns(Sheet sheet) {
        for (int i = 0; i < columns_team.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private int addProjectsData(Team team, Sheet sheet, int rowNum) {
        for (Project project : team.getProjects()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(project.getTeam());
            row.createCell(1).setCellValue(project.getName());
            row.createCell(2).setCellValue(project.getCoverage() + PERCENTAGE);
            row.createCell(3).setCellValue(project.getLineOfCode());
            row.createCell(4).setCellValue(project.readableDebt());
        }
        return rowNum;
    }

    private int addTeamData(Teams teams, Sheet sheet, int rowNum) {

        for (Team team : teams.getTeams()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(team.getName());
            row.createCell(1).setCellValue(team.getCoverage() + PERCENTAGE);
            row.createCell(2).setCellValue(team.getLineOfCode());
            row.createCell(3).setCellValue(team.readableDebt());
        }
        return rowNum;
    }


    private void addTotalData(Team team, Sheet sheet, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(TOTAL);
        row.createCell(1).setCellValue(team.getName());
        row.createCell(2).setCellValue(team.getCoverage() + PERCENTAGE);
        row.createCell(3).setCellValue(team.getLineOfCode());
        row.createCell(4).setCellValue(team.readableDebt());
    }

    private void addTotalData(Teams teams, Sheet sheet, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(TOTAL);
        row.createCell(1).setCellValue(teams.getCoverage() + PERCENTAGE);
        row.createCell(2).setCellValue(teams.getLineOfCode());
        row.createCell(3).setCellValue(teams.readableDebt());

    }

    private void build(String sprint) throws IOException {
        // Write the output to a file
        String today = new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(new Date());
        FileOutputStream fileOut = new FileOutputStream("quality-gate-" + PREFIX_SPRINT + sprint +"_"+ today + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

    private Sheet createSheet(String sheetName, String[] columns) {
        Sheet sheet = workbook.createSheet(sheetName);
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        return sheet;
    }
}
