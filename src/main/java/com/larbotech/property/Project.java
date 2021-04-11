package com.bnpp.zephyr.tools.sonar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private static final Integer NB_MIN_J = 480; //8 (working hours per day)
    private static final Integer NB_MIN_H = 60;

    private String team;
    private String name;
    private Double coverage;
    private Long lineOfCode;
    private Integer debt;

    public String readableDebt() {
        return timeConvert(debt);
    }

    /**
     * The measure of the technical debt is stored in minutes in the DB. Which is consistent with what you see:
     * 15750 (minutes) / 60 (minutes per hour) / 8 (working hours per day) = 32.8 days
     */
    public static String timeConvert(Integer time) {
        if (time < NB_MIN_H)
            return time + "MIN";
        if (time < NB_MIN_J)
            return time / 60 % 8 + "H " + time % 60 + "MIN";
        return time / 8 / 60 + "J " + time / 60 % 8 + "H " + time % 60 + "MIN";
    }
}
