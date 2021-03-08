package com.example.javademo.fwk.config.db;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import javax.sql.DataSource;
import java.io.IOException;

// Ddocker로 띄움
// Java Config로 해야 나중에 동적으로 다른 DBMS를 교체할 수 있다.
@Profile("local") // 해당 App. 즉 프로젝트 Config 설정 중 Active Profile이 "local"일 경우에만 적용된다.
@Configuration
public class EmbeddedDb {

    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean(name = "embeddedPrimaryDb")
    public DataSource embeddedPrimaryDb() throws IOException {
        return EmbeddedPostgres.builder()
                .setServerConfig("timezone", "Asia/Seoul")
                .setPort(5432)
                .start().getPostgresDatabase();
    }

}
