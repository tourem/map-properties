package com.bnpp.zephyr.tools.sonar.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "https.proxy")
public class ProxyConfig {
    private String host;
    private Integer port;
    private String user;
    private String password;
}
