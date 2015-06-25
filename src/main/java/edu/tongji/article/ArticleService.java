package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.comment.ArticleComment;
import edu.tongji.comment.CommentRepository;
import edu.tongji.error.ResourceNotFoundException;
import edu.tongji.topic.Topic;
import edu.tongji.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Breezewish on 6/15/15.
 */
public class ArticleService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Article> listUserArticleByUid(Long id) {
        Account account = accountRepository.findById(id);
        return articleRepository.listUserArticle(account);
    }

    public List<Article> listAllArticle() {
        return articleRepository.listAllArticle();
    }

    public Article getArticleById(Long id) {
        Article article = articleRepository.getArticle(id);
        if (article == null) {
            throw new ResourceNotFoundException();
        }
        return article;
    }

    public Article getUserArticleById(String email, Long id)
    {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new ResourceNotFoundException();
        }
        return articleRepository.getArticle(account, id);
    }

    public List<Article> listTopicArticleBySlug(String slug) {
        Topic topic = topicRepository.findBySlug(slug);
        if (topic == null) {
            throw new ResourceNotFoundException();
        }
        return articleRepository.listTopicArticle(topic);
    }

    public Article createArticle(String email, Long topicId, String title, String html, String coverImage, String brief) {
        Account account = accountRepository.findByEmail(email);
        Topic topic = topicRepository.findById(topicId);
        Article article = new Article(account, topic, title, html, coverImage, brief);
        articleRepository.save(article);
        return article;
    }

    public Article updateArticle(String email, Long id, Long topicId, String title, String html, String coverImage, String brief) {
        Account account = accountRepository.findByEmail(email);
        Topic topic = topicRepository.findById(topicId);
        if (account != null) {
            return articleRepository.update(account, topic, id, html, title, coverImage, brief);
        } else {
            return null;
        }
    }

    public Boolean deleteArticle(String email, Long id) {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            articleRepository.delete(account, id);
            return true;
        }
        return false;
    }

    public ArticleComment addComment(String email, Long id, String markdown) {
        Account account = accountRepository.findByEmail(email);
        Article article = articleRepository.getArticle(id);
        ArticleComment comment = new ArticleComment(article, account, markdown);
        commentRepository.save(comment);
        return comment;
    }

    public List<ArticleComment> getComment(Article article)
    {
        return commentRepository.listAll(article);
    }

    public Boolean hasLiked(String email, Long id) {
        Account account = accountRepository.findByEmail(email);
        return articleRepository.hasLiked(account, id);
    }

    public void like(String email, Long id) {
        Account account = accountRepository.findByEmail(email);
        articleRepository.like(account, id);
    }
}
