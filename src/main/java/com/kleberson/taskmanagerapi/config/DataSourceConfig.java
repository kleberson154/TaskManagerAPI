package com.kleberson.taskmanagerapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSourceProperties properties;
    private final Environment env;

    @Bean
    @Primary
    public DataSource dataSource() {
        String url = properties.getUrl();
        String username = properties.getUsername();
        String password = properties.getPassword();
        String driver = properties.getDriverClassName();

        if (url == null || url.isBlank()) {
            url = env.getProperty("DB_URL", "jdbc:h2:mem:taskdb");
        }
        if (username == null) username = env.getProperty("DB_USER", "sa");
        if (password == null) password = env.getProperty("DB_PASSWORD", "");

        // Try to build primary datasource (Hikari) and test connection
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            if (driver != null && !driver.isBlank()) config.setDriverClassName(driver);
            config.setUsername(username);
            config.setPassword(password);

            HikariDataSource ds = new HikariDataSource(config);
            // Test connection quickly
            try (Connection c = ds.getConnection()) {
                if (!c.isValid(1)) {
                    throw new SQLException("Connection is not valid");
                }
            }
            return ds;
        } catch (Exception ex) {
            // If primary datasource fails (driver missing or connection refused), fallback to H2
            HikariConfig h2 = new HikariConfig();
            h2.setJdbcUrl("jdbc:h2:mem:taskdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
            h2.setDriverClassName("org.h2.Driver");
            h2.setUsername("sa");
            h2.setPassword("");
            return new HikariDataSource(h2);
        }
    }
}
