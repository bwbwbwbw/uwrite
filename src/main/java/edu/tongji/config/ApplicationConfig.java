package edu.tongji.config;

import edu.tongji.Application;
import edu.tongji.account.AccountRepository;
import edu.tongji.account.AccountService;
import edu.tongji.account.AccountSignUpValidator;
import edu.tongji.article.ArticleRepository;
import edu.tongji.article.ArticleService;
import edu.tongji.article.HtmlFilter;
import edu.tongji.comment.CommentRepository;
import edu.tongji.image.ImageResolver;
import edu.tongji.search.SearchService;
import edu.tongji.topic.TopicRepository;
import edu.tongji.topic.TopicService;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(
                new ClassPathResource("/persistence.properties"),
                new ClassPathResource("/config.properties")
        );
        return ppc;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }

    /*
        @Bean
        public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();
            factory.setMaxFileSize("10MB");
            factory.setMaxRequestSize("10MB");
            return factory.createMultipartConfig();
        }
    */

    @Bean
    public static AccountService accountService() { return new AccountService(); }

    @Bean
    public static AccountSignUpValidator accountSignUpValidator() {
        return new AccountSignUpValidator();
    }

    @Bean
    public static SearchService searchService() {
        return new SearchService();
    }

    @Bean
    public static HtmlFilter htmlFilter() {
        return new HtmlFilter();
    }

    @Bean
    public static ArticleRepository articleRepository() {
        return new ArticleRepository();
    }

    @Bean
    public static ArticleService articleService() {
        return new ArticleService();
    }

    @Bean
    public static CommentRepository commentRepository() {
        return new CommentRepository();
    }

    @Bean
    public static TopicRepository topicRepository() {
        return new TopicRepository();
    }

    @Bean
    public static TopicService topicService() {
        return new TopicService();
    }

    @Bean
    public static ImageResolver imageResolver() {
        return new ImageResolver();
    }
}