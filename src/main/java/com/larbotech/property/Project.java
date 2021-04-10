package com.larbotech.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private static final Integer NB_MIN_J = 1440;
    private static final Integer NB_MIN_H = 60;

    private String team;
    private String name;
    private Double coverage;
    private Long locs;
    private Integer debt;

    public String readableDebt() {
        return timeConvert(debt);
    }

    public static String timeConvert(Integer time) {
        if (time < NB_MIN_H)
            return time + "MIN";
        if (time < NB_MIN_J && time >= NB_MIN_H)
            return time / 60 % 24 + "H " + time % 60 + "MIN";
        return time / 24 / 60 + "J " + time / 60 % 24 + "H " + time % 60 + "MIN";
    }
}
