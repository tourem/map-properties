package com.larbotech.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    List<Project> projects = new ArrayList<>();

    projects.add(new Project("toure", "etoe", 90.0, 2300L, 1445));
    projects.add(new Project("larbo", "asset2", 99.0, 200L, 145));
    projects.add(new Project("berah", "fc", 100.0, 2800L, 2945));
    projects.add(new Project("maig", "gao", 80.2, 50000L, 445));

    Show.showResultForTeam(new Team("Larbou", projects));

    // SpringApplication.run(Application.class, args);
  }
}