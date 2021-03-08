package com.example.javademo.fwk.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
@Profile("local") // 해당 App. 즉 프로젝트 Config 설정 중 Active Profile이 "local"일 경우에만 적용된다.
@Configuration
@EnableJpaRepositories(basePackages = "com.example.javademo.repo.jpa",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "publicTransactionManager")
public class EmbeddedDataSource {

    @Bean(name = "embeddedPrimaryDataSource") // 메소드 리턴값을 클래스로 보고 인스턴스화(Bean) 하는 것이다.
    @DependsOn("embeddedDb") // embeddedDb 생성 후 생성해달라는 의존성
    @Order(Ordered.LOWEST_PRECEDENCE)
    public DataSource embeddedPrimaryDataSource() {

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?ssl=false&charset=utf8");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        ds.setMinimumIdle(5);
        ds.setMaximumPoolSize(100);
        ds.setIdleTimeout(3000);
        ds.setConnectionInitSql("set time zone 'Asia/Seoul'");

        return ds;
    }

    /*
       ■ ORM(Object Relational Mapping)이란?
       - 클래스와 DB 테이블을 매핑해주는 것을 의미한다.
       
       ■ JPA(Java Persistence API)란?
       - 자바 ORM 기술에 대한 API를 의미한다.
       - JPA는 ORM을 통해 SQL 쿼리 대신 직관적인 코드(메소드)를 작성함으로써 데이터를 조작할 수 있도록 돕는다.
       
       ■ Hibernate란?
       - JPA의 구현체 중 하나다.
     */
    // JPA를 위한 Bean으로, 클래스를 테이블(객체)로 인식한다.
    @Bean(name = "entityManagerFactory") // 메소드 리턴값을 클래스로 보고 인스턴스화(Bean) 하는 것이다.
    @Order(Ordered.LOWEST_PRECEDENCE)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("embeddedPrimaryDataSource") DataSource ds) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.default_schema", "public");
        properties.put("hibernate.hbm2ddl.auto", "update"); // 테이블 스키마 정보가 VO 객체와 다를 경우, 코드 기반으로 업데이트 해달라는 요청이다.
        properties.put("hibernate.ddl-auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.generate-ddl", true);
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.cache.use_query_cache", false);
        properties.put("hibernate.show_sql", true);
        properties.put("javax.persistence.validation.mode", "none");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.example.javademo.entity"); // 해당 패키지 내에 있는 클래스를 테이블로 인식한다.
        em.setJpaPropertyMap(properties);

        return em;

    }

    // Commit/Rollback 등 트랜잭션을 관장한다.
    @Bean(name = "publicTransactionManager")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory,
                                                         @Qualifier("embeddedPrimaryDataSource") DataSource dataSource
    ) {
        JpaTransactionManager jtm = new JpaTransactionManager(entityManagerFactory);
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);

        // ChainedTransactionManager는 트랜잭션을 묶어주는 역할을 한다.
        // 예를들어, MyBatis 쿼리 수행 시 트랜잭션이 달라 JPA에서 트랜잭션이 보이지 않기 때문에 트랜잭션을 묶음으로써
        // MyBatis에서 쿼리를 수행하더라도 JPA에서 트랜잭션이 보일 수 있도록 설정해주는 것이다.
        ChainedTransactionManager ctm = new ChainedTransactionManager(jtm, dstm);

        return ctm;
    }

}
