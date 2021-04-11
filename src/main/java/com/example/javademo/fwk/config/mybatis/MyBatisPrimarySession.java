package com.example.javademo.fwk.config.mybatis;

import ch.qos.logback.classic.Logger;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.example.javademo.repo.mybatis",
        sqlSessionFactoryRef = "primarySqlSessionFactory"
)
public class MyBatisPrimarySession extends MyBatisConfig {

    private final Logger log = (Logger) LoggerFactory.getLogger(MyBatisPrimarySession.class);

    @Bean
    @Primary
    SqlSessionFactory primarySqlSessionFactory(@Qualifier("tmpDataSource") DataSource dataSource) throws Exception {
        log.info("=============== embeddedSqlSessionFactory Start ===============");
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, dataSource);
        log.info("=============== embeddedSqlSessionFactory End   ===============");
        return sessionFactoryBean.getObject();
    }
}

