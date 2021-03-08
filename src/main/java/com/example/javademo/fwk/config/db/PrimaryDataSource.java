package com.example.javademo.fwk.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

// DataSource로 데이터베이스에 접근함
@Profile("dev") // 해당 App. 즉 프로젝트 Config 설정 중 Active Profile이 "dev"일 경우에만 적용된다.
@Configuration
@EnableJpaRepositories(basePackages = "com.example.javademo.repo.jpa",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "publicTransactionManager")
public class PrimaryDataSource {

    @Bean(name = "embeddedPrimaryDataSource")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public DataSource embeddedPrimaryDataSource() {

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://mugu.synology.me:55432/postgres?ssl=false&charset=utf8");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        ds.setMinimumIdle(5);
        ds.setMaximumPoolSize(100);
        ds.setIdleTimeout(3000);
        ds.setConnectionInitSql("set time zone 'Asia/Seoul'");

        return ds;
    }

    @Bean(name = "entityManagerFactory")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("embeddedPrimaryDataSource") DataSource ds) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.default_schema", "public");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.ddl-auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.cache.use_query_cache", false);
        properties.put("hibernate.show_sql", false);
        properties.put("javax.persistence.validation.mode", "none");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.example.demo.entity");
        em.setJpaPropertyMap(properties);

        return em;

    }

    @Bean(name = "publicTransactionManager")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory,
                                                         @Qualifier("embeddedPrimaryDataSource") DataSource dataSource
    ) {
        JpaTransactionManager jtm = new JpaTransactionManager(entityManagerFactory);
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);

        ChainedTransactionManager ctm = new ChainedTransactionManager(jtm, dstm);
        return ctm;
    }

}