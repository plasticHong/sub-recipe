package com.hong.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJpaRepositories(
        basePackages = "com.hong.repository.sub",
        entityManagerFactoryRef = "subRecipeEntityManager",
        transactionManagerRef = "subRecipeTransactionManager"
)
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean subRecipeEntityManager() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(subRecipeDatasource());
        em.setPackagesToScan("com.hong.entity.sub");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource subRecipeDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager subRecipeTransactionManager(){

        JpaTransactionManager ts = new JpaTransactionManager();
        ts.setEntityManagerFactory(subRecipeEntityManager().getObject());
        return ts;
    }


}
