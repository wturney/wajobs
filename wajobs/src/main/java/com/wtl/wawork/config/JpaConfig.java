package com.wtl.wawork.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:/persistence.properties" })
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/jobs");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(Boolean.valueOf(env.getRequiredProperty("hibernate.show_sql")));
        adapter.setGenerateDdl(Boolean.valueOf(env.getRequiredProperty("hibernate.format_sql")));
        adapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));

        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();

        Properties jpaProps = new Properties();
        jpaProps.put("jadira.usertype.autoRegisterUserTypes", env.getRequiredProperty("jadira.usertype.autoRegisterUserTypes"));
        jpaProps.put("jadira.usertype.databaseZone", env.getRequiredProperty("jadira.usertype.databaseZone"));
        jpaProps.put("jadira.usertype.javaZone", env.getRequiredProperty("jadira.usertype.javaZone"));
        jpaProps.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        jpaProps.put("hibernate.hbm2ddl.import_files", env.getRequiredProperty("hibernate.hbm2ddl.import_files"));

        emfb.setJpaProperties(jpaProps);
        emfb.setJpaVendorAdapter(adapter);
        emfb.setPackagesToScan(env.getRequiredProperty("entity.package"));
        emfb.setDataSource(dataSource());

        return emfb;
    }

    @Bean
    public EntityManager entityManger() {
        return entityManagerFactory().getObject().createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}