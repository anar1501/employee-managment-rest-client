package com.company.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.company.data.repository",
        entityManagerFactoryRef = "employee-em",
        transactionManagerRef = "employee-tm"
)
public class DatabaseConfig {

    @Value("${employee.datasource.username}")
    private String datasourceUser;
    @Value("${employee.datasource.password}")
    private String datasourcePassword;
    @Value("${employee.datasource.url}")
    private String datasourceHost;
    @Value("${employee.datasource.driver}")
    private String datasourceDriver;
    @Value("${employee.hibernate.database-platform}")
    private String hibernateDatabasePlatform;
    @Value("${employee.hibernate.show-sql}")
    private boolean hibernateShowSql;
    @Value("${employee.hibernate.format-sql}")
    private boolean hibernateFormatSql;
    @Value("${employee.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Bean
    public DataSource dataSourceSphere() {
        HikariConfig config = new HikariConfig();
        config.setUsername(datasourceUser);
        config.setPassword(datasourcePassword);
        config.setJdbcUrl(datasourceHost);
        config.setDriverClassName(datasourceDriver);
        return new HikariDataSource(config);
    }

    @Bean(name = "employee-em")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceSphere());
        em.setPackagesToScan("com.company.data.entity");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        properties.put("hibernate.dialect", hibernateDatabasePlatform);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.format_sql", hibernateFormatSql);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "employee-tm")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

}