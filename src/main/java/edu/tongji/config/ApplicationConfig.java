package edu.tongji.config;

import edu.tongji.Application;
import edu.tongji.account.AccountRepository;
import edu.tongji.article.ArticleRepository;
import edu.tongji.article.ArticleService;
import edu.tongji.article.HtmlFilter;
import edu.tongji.comment.CommentRepository;
import edu.tongji.image.ImageResolver;
import edu.tongji.topic.TopicRepository;
import edu.tongji.topic.TopicService;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

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
    public static HtmlFilter htmlFilter() { return new HtmlFilter(); }

    @Bean
    public static ArticleRepository articleRepository() {
        return new ArticleRepository();
    }

    @Bean
    public static ArticleService articleService() {
        return new ArticleService();
    }

    @Bean
    public static AccountRepository accountRepository() {
        return new AccountRepository();
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