package com.mpayWallet.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class EmailConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private Map<String, String> properties = new HashMap<>();
}
