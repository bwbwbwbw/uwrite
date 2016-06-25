package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.comment.CommentRepository;
import edu.tongji.error.ResourceNotFoundException;
import edu.tongji.search.SearchService;
import edu.tongji.topic.Topic;
import edu.tongji.topic.TopicRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleService articleService;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private ArticleRepository articleRepositoryMock;

    @Mock
    private TopicRepository topicRepositoryMock;

    @Mock
    private CommentRepository commentRepositoryMock;

    @Mock
    private SearchService searchServiceMock;

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void listUserArticleByUid1() throws Exception {
        // arrange
        Account account = new Account(1L, "test@example.com", "test_user");
        when(accountRepositoryMock.findById(1L)).thenReturn(account);

        // act
        articleService.listUserArticleByUid(1L);

        // verify
        verify(articleRepositoryMock, times(1)).listUserArticle(account);
    }

    @Test
    public void listUserArticleByUid2() throws Exception {
        // arrange
        Account account = new Account(1L, "test@example.com", "test_user");
        when(accountRepositoryMock.findById(1L)).thenReturn(account);

        // act
        articleService.listUserArticleByUid(0L);

        // verify
        verify(articleRepositoryMock, times(0)).listUserArticle(account);
    }

    @Test
    public void listAllArticle() throws Exception {
        //act
        articleService.listAllArticle();
        //verify
        verify(articleRepositoryMock, times(1)).listAllArticle();
    }

    @Test
    public void getArticleById1() throws Exception {
        //arrange
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        //act
        articleService.getArticleById(1L);

        //verify
        verify(articleRepositoryMock, times(1)).getArticle(1L);


    }

    @Test
    public void getArticleById2() throws Exception {
        //arrange
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.getArticleById(0L);
    }


    @Test
    public void getUserArticleById1() throws Exception {
        // arrange
        Account account = new Account(1L, "test@example.com", "test_user");
        when(accountRepositoryMock.findByEmail("test@example.com")).thenReturn(account);
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.getUserArticleById("notExist@notExist.com", 1L);
    }

    @Test
    public void getUserArticleById2() throws Exception {
        //arrange
        Account account = new Account(1L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);

        //act
        articleService.getUserArticleById("exist@exit.com", 0L);

        //assert
        assertEquals(null,articleService.getUserArticleById("exist@exit.com", 0L));

    }

    @Test
    public void getUserArticleById3() throws Exception {
        //arrange
        Account account = new Account(1L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);
        Article article = new Article(0L, true);
        when(articleRepositoryMock.getArticle(0L)).thenReturn(article);


        //act
        articleService.getUserArticleById("exist@exit.com",0L);

        //assert
        assertEquals(null,articleService.getUserArticleById("exist@exit.com", 0L));
    }

    @Test
    public void getUserArticleById4() throws Exception {
        //arrange
        Account account = new Account(0L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);
        Account swx = new Account(1L,"swx@swx.com","test_user");
        Article article = new Article(1L,false,swx);

        //act
        articleService.getUserArticleById("exist@exit.com",1L);

        //assert
        assertEquals(null,articleService.getUserArticleById("exist@exit.com",1L));
    }

    @Test
    public void getUserArticleById5() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L,false,swx);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);

        //act
        assertEquals(article,articleService.getUserArticleById("swx@swx.com",1L));


    }
    @Test
    public void createArticle1() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com",1L,"notEmptyTitle","","test.jpg","notEmptyBrief");
    }

    @Test
    public void createArticle2() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com",1L,"","conten","test.jpg","");
    }

    @Test
    public void createArticle3() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com",1L,"","conten","","");
    }

    @Test
    public void createArticle4() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com",0L,"notEmptyTitle","conten","","notEmptyBrief");
    }

    @Test
    public void createArticle5() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com",0L,"notEmptyTitle","","","");
    }

    @Test
    public void createArticle6() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com",0L,"","","test.jpg","notEmptyBrief");
    }

    @Test
    public void createArticle7() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",1L,"notEmptyTitle","content","","notEmptyBrief");
    }

    @Test
    public void createArticle8() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",1L,"notEmptyTitle","","test.jpg","");
    }

    @Test
    public void createArticle9() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",1L,"","","test.jpg","notEmptyBrief");
    }

    @Test
    public void createArticle10() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",0L,"notEmptyTitle","content","test.jpg","");
    }

    @Test
    public void createArticle11() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",0L,"","content","test.jpg","notEmptyBrief");
    }

    @Test
    public void createArticle12() throws Exception {
        //arrange
        Account existAccount = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com",0L,"","","","");
    }



    @Test
    public void deleteArticle1() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("notexist@notexist",1L);

        //assert
        assertEquals(false,articleService.deleteArticle("notexist@notexist",1L));

    }

    @Test
    public void deleteArticle2() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com",0L);

        //assert
        assertEquals(false,articleService.deleteArticle("swx@swx.com",0L));
    }

    @Test
    public void deleteArticle3() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com",1L);

        //assert
        assertEquals(false,articleService.deleteArticle("exist@exist.com",1L));
    }

    @Test
    public void deleteArticle4() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L,true);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com",1L);

        //assert
        assertEquals(false,articleService.deleteArticle("exist@exist.com",1L));
    }

    @Test
    public void deleteArticle5() throws Exception {
        //arrange
        Account swx = new Account(1L,"swx@swx.com","test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(swx,1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com",1L);

        //assert
        assertEquals(false,articleService.deleteArticle("exist@exist.com",1L));
    }

    @Test
    public void updateArticle() throws Exception {

    }

}