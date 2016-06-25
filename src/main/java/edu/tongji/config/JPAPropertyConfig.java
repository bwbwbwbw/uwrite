package edu.tongji.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class JPAPropertyConfig {
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
    public Properties jpaProperties() {
        Properties ret = new Properties();
        ret.put(Environment.DIALECT, dialect);
        ret.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        ret.put(Environment.HBM2DDL_IMPORT_FILES, "/data/account.sql,/data/topic.sql,/data/article.sql");
        return ret;
    }
}
