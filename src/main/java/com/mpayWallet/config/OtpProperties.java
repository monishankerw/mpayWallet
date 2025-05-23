package com.mpayWallet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
    private boolean enabled = true;
    private int length = 6;
    private int expirationSeconds = 300; // 5 minutes
}