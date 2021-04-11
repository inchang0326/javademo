package com.example.javademo.fwk.config.mybatis;

import ch.qos.logback.classic.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
@Configuration
public class MyBatisConfig {

    private final Logger log = (Logger) LoggerFactory.getLogger(MyBatisConfig.class);

    final String typeAliasesPackage = "com.example.javademo.model";
    final String configLocation = "classpath:mybatis/mybatis-config.xml";
    final String mapperLocation = "classpath:mybatis/mapper/**/*.xml";

    void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactoryBean.setConfigLocation(pathResolver.getResource(configLocation));
        sessionFactoryBean.setMapperLocations(pathResolver.getResources(mapperLocation));
        sessionFactoryBean.setVfs(SpringBootVFS.class);
    }
}
