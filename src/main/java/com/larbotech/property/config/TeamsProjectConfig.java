package com.bnpp.zephyr.tools.sonar.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "teams")
public class ProjectTeamConfig {
    private String sprint;
    private Map<String, KeyBranch> dna;
    private Map<String, KeyBranch> irma;
    private Map<String, KeyBranch> andy;

    @Getter
    @Setter
    public static class KeyBranch {
        @NotEmpty
        private String key;
        private String branch;
    }
}
