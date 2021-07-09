package com.example.demo.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configuration.properties")
@ConfigurationProperties("")
public class ConfigProperties {
    private String formatDbDate;
    private String formatStandardDate;


    public String getFormatStandardDate() {
        return formatStandardDate;
    }

    public void setFormatStandardDate(String formatStandardDate) {
        this.formatStandardDate = formatStandardDate;
    }

    public String getFormatDbDate() {
        return formatDbDate;
    }

    public void setFormatDbDate(String formatDbDate) {
        this.formatDbDate = formatDbDate;
    }
}
