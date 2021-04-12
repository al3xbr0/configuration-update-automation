package ru.w.automation.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("storageDataSource")
    @Primary
    @ConfigurationProperties("datasource.main")
    public DataSource storageDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("camundaBpmDataSource")
    @ConfigurationProperties("datasource.camunda")
    public DataSource camundaBpmDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}