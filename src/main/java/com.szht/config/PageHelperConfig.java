package com.szht.config;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {
    @Value("${pagehelper.helperDialect}")
    private String helperDialect;

    @Value("${pagehelper.reasonable}")
    private String reasonable;

    @Value("${pagehelper.supportMethodsArguments}")
    private String supportMethodsArguments;

    @Value("${pagehelper.params}")
    private String params;

    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect",helperDialect);
        properties.setProperty("reasonable",reasonable);
        properties.setProperty("supportMethodsArguments",supportMethodsArguments);
        properties.setProperty("params",params);
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
