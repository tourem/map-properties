package com.bnpp.zephyr.tools.sonar.config;

import com.google.gson.Gson;
import org.sonarqube.ws.client.HttpConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class ApplicationConfig {

    private static final String GET_MEASURES_URI = "/api/measures";

    @Autowired
    private ProxyConfig proxyConfig;
    /**
     * URL of the SonarQube instance
     **/
    @Value("${sonar.url}")
    private String url;

    /**
     * Token to authenticate the user on the sonarqube server
     */
    @Value("${sonar.token}")
    private String token;

    @Bean
    public Gson gson() {
        return new Gson();
    }


    @Bean
    public HttpConnector measureHttpConnector() {
        // Initialize connexion information.
        final String proxyHost = proxyConfig.getHost();
        final Integer proxyUsedPort = proxyConfig.getPort();
        final String proxyUser = proxyConfig.getUser();
        final String proxyPass = proxyConfig.getPassword();

        // Initialize http connector builder.
        final HttpConnector.Builder builder = HttpConnector.newBuilder()
                .userAgent("tools-quality-gate")
                .url(url + GET_MEASURES_URI);

        // Set SonarQube authentication token.
        builder.credentials(token, null);

        // Set proxy settings.
        if (!proxyHost.isEmpty()) {
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyUsedPort));
            builder.proxy(proxy);

            if (!proxyUser.isEmpty()) {
                builder.proxyCredentials(proxyUser, proxyPass);
            }
        }

        // Execute the request.
        return builder.build();
    }
}
