package com.hong.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJpaRepositories(
        basePackages = "com.hong.repository.lim",
        entityManagerFactoryRef = "limEntityManagerFactory",
        transactionManagerRef = "LIM"
)
@Configuration
public class LimDataSourceConfig {


    @Bean("limEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean limEntityManager() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(limDatasource());
        em.setPackagesToScan("com.hong.entity.lim");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean("limDataSource")
    @ConfigurationProperties(prefix = "spring.monster-datasource")
    public DataSource limDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("LIM")
    public PlatformTransactionManager limTransactionManager(){

        JpaTransactionManager ts = new JpaTransactionManager();
        ts.setEntityManagerFactory(limEntityManager().getObject());

        return ts;
    }

}
