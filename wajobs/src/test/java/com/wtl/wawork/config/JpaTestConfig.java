package com.wtl.wawork.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wtl.wawork.core.util.HsqldbDataTypeFactory;

/**
 * Persistence configuration for unit tests
 * 
 * @author Weston Turney-Loos
 * 
 */
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence.properties" })
public class JpaTestConfig {

    @Autowired
    private transient Environment env;

    @Bean
    public IDatabaseConnection databaseConnection() throws SQLException
    {
        IDatabaseConnection conn = new DatabaseDataSourceConnection(dataSource());
        conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        return conn;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.user"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
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

        emfb.setJpaProperties(jpaProps);
        emfb.setJpaVendorAdapter(adapter);
        emfb.setPackagesToScan(env.getRequiredProperty("entity.package"));
        emfb.setDataSource(dataSource());
        emfb.setPersistenceUnitName("Swag");

        return emfb;
    }

    @Bean
    public EntityManager entityManger() {
        return entityManagerFactory().getObject().createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}