package edu.tongji.config;

import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

@Configuration
@Profile("test")
public class EmbeddedJpaPropertyConfig {
    @Bean
    public Properties jpaProperties() {
        Properties ret = new Properties();
        ret.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
        ret.put(Environment.HBM2DDL_AUTO, "create");
        ret.put(Environment.HBM2DDL_IMPORT_FILES, "/data/account_test.sql,/data/topic_test.sql,/data/article_test.sql");
        return ret;
    }
}
