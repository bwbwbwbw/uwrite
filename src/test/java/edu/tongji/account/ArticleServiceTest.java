package edu.tongji.account;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleRepository;
import edu.tongji.article.ArticleService;
import edu.tongji.comment.ArticleComment;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @testType UNIT_TEST
 */
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


    /**
     * @unitTestId TEST_LIST_USER_ARTICLE_BY_UID_1
     * @unitTestTarget ArticleService.listUserArticleByUid()
     * @unitTestType 条件覆盖
     * @unitTestDescription 输入的ID有对应存在的用户的情况
     */
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
    /**
     * @unitTestId TEST_LIST_USER_ARTICLE_BY_UID_2
     * @unitTestTarget ArticleService.listUserArticleByUid()
     * @unitTestType 条件覆盖
     * @unitTestDescription 输入的ID没有对应存在的用户的情况
     */
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
    /**
     * @unitTestId TEST_LIST_ALL_ARTICLE
     * @unitTestTarget ArticleService.listAllArticle()
     * @unitTestType 条件覆盖
     * @unitTestDescription 列出所有的Article
     */
    @Test
    public void listAllArticle() throws Exception {
        //act
        articleService.listAllArticle();
        //verify
        verify(articleRepositoryMock, times(1)).listAllArticle();
    }
    /**
     * @unitTestId TEST_GET_ARTICLE_BY_ID_1
     * @unitTestTarget ArticleService.getArticleById()
     * @unitTestType 等价类测试
     * @unitTestDescription 输入的ID存在对应的文章的情况
     */
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
    /**
     * @unitTestId TEST_GET_ARTICLE_BY_ID_2
     * @unitTestTarget ArticleService.getArticleById()
     * @unitTestType 等价类测试
     * @unitTestDescription 输入的ID不存在对应的文章的情况
     */

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

    /**
     * @unitTestId TEST_GET_USER_ARTICLE_BY_ID_1
     * @unitTestTarget ArticleService.getUserArticleById()
     * @unitTestType 决策表测试
     * @unitTestDescription 输入的Email对应的用户不存在的情况
     */
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
    /**
     * @unitTestId TEST_GET_USER_ARTICLE_BY_ID_2
     * @unitTestTarget ArticleService.getUserArticleById()
     * @unitTestType 决策表测试
     * @unitTestDescription 输入的Email对应的用户存在，但是输入的ID对应的文章不存在的情况
     */
    @Test
    public void getUserArticleById2() throws Exception {
        //arrange
        Account account = new Account(1L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);

        //act
        articleService.getUserArticleById("exist@exit.com", 0L);

        //assert
        assertEquals(null, articleService.getUserArticleById("exist@exit.com", 0L));

    }

    /**
     * @unitTestId TEST_GET_USER_ARTICLE_BY_ID_3
     * @unitTestTarget ArticleService.getUserArticleById()
     * @unitTestType 决策表测试
     * @unitTestDescription 输入的Email对应的用户和文章ID都存在，但是文章被标记为已删除的情况
     */
    @Test
    public void getUserArticleById3() throws Exception {
        //arrange
        Account account = new Account(1L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);
        Article article = new Article(0L, true);
        when(articleRepositoryMock.getArticle(0L)).thenReturn(article);


        //act
        articleService.getUserArticleById("exist@exit.com", 0L);

        //assert
        assertEquals(null, articleService.getUserArticleById("exist@exit.com", 0L));
    }

    /**
     * @unitTestId TEST_GET_USER_ARTICLE_BY_ID_4
     * @unitTestTarget ArticleService.getUserArticleById()
     * @unitTestType 决策表测试
     * @unitTestDescription 输入的Email对应的用户和文章ID都存在，文章未被删除，但是文章对应的作者不是Email对应的用户
     */
    @Test
    public void getUserArticleById4() throws Exception {
        //arrange
        Account account = new Account(0L, "exist@exit.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exit.com")).thenReturn(account);
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        Article article = new Article(1L, false, swx);

        //act
        articleService.getUserArticleById("exist@exit.com", 1L);

        //assert
        assertEquals(null, articleService.getUserArticleById("exist@exit.com", 1L));
    }

    /**
     * @unitTestId TEST_GET_USER_ARTICLE_BY_ID_5
     * @unitTestTarget ArticleService.getUserArticleById()
     * @unitTestType 决策表测试
     * @unitTestDescription 输入的Email对应的用户和文章ID都存在，文章未被删除，文章的作者与Email对应的用户相一致
     */
    @Test
    public void getUserArticleById5() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L, false, swx);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);

        //act
        assertEquals(article, articleService.getUserArticleById("swx@swx.com", 1L));


    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_1
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic存在，title不为空，html为空，coverImage不为空，brief不为空
     */
    @Test
    public void createArticle1() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com", 1L, "notEmptyTitle", "", "test.jpg", "notEmptyBrief");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_2
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic存在，title为空，html不为空，coverImage不为空，brief为空
     */
    @Test
    public void createArticle2() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com", 1L, "", "conten", "test.jpg", "");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_3
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic存在，title为空，html不为空，coverImage为空，brief为空
     */
    @Test
    public void createArticle3() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(RuntimeException.class);

        //act
        articleService.createArticle("exist@exist.com", 1L, "", "conten", "", "");
    }


    /**
     * @unitTestId TEST_CREATE_ARTICLE_4
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic存在，title为空，html不为空，coverImage为空，brief为空
     */
    @Test
    public void createArticle4() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com", 0L, "notEmptyTitle", "conten", "", "notEmptyBrief");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_5
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic不存在，title不为空，html为空，coverImage为空，brief为空
     */
    @Test
    public void createArticle5() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com", 0L, "notEmptyTitle", "", "", "");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_6
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户存在，topic不存在，title为空，html为空，coverImage不为空，brief不为空
     */
    @Test
    public void createArticle6() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("exist@exist.com", 0L, "", "", "test.jpg", "notEmptyBrief");
    }


    /**
     * @unitTestId TEST_CREATE_ARTICLE_7
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic存在，title不为空，html不为空，coverImage为空，brief不为空
     */
    @Test
    public void createArticle7() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 1L, "notEmptyTitle", "content", "", "notEmptyBrief");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_8
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic存在，title不为空，html为空，coverImage不为空，brief为空
     */
    @Test
    public void createArticle8() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 1L, "notEmptyTitle", "", "test.jpg", "");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_9
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic存在，title为空，html为空，coverImage不为空，brief不为空
     */
    @Test
    public void createArticle9() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 1L, "", "", "test.jpg", "notEmptyBrief");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_10
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic不存在，title不为空，html不为空，coverImage不为空，brief为空
     */
    @Test
    public void createArticle10() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 0L, "notEmptyTitle", "content", "test.jpg", "");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_11
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic不存在，title为空，html不为空，coverImage不为空，brief不为空
     */
    @Test
    public void createArticle11() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 0L, "", "content", "test.jpg", "notEmptyBrief");
    }

    /**
     * @unitTestId TEST_CREATE_ARTICLE_12
     * @unitTestTarget ArticleService.createArticle()
     * @unitTestType 正交实验法
     * @unitTestDescription 用户不存在，topic不存在，title为空，html为空，coverImage为空，brief为空
     */
    @Test
    public void createArticle12() throws Exception {
        //arrange
        Account existAccount = new Account(1L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(existAccount);
        Topic existTopic = new Topic(1L);
        when(topicRepositoryMock.findById(1L)).thenReturn(existTopic);


        //expect
        exception.expect(ResourceNotFoundException.class);

        //act
        articleService.createArticle("notexist@notexist.com", 0L, "", "", "", "");
    }


    /**
     * @unitTestId TEST_DELETE_ARTICLE_1
     * @unitTestTarget ArticleService.deleteArticle()
     * @unitTestType 基路径测试法
     * @unitTestDescription email对应的用户不存在
     */
    @Test
    public void deleteArticle1() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("notexist@notexist", 1L);

        //assert
        assertEquals(false, articleService.deleteArticle("notexist@notexist", 1L));

    }

    /**
     * @unitTestId TEST_DELETE_ARTICLE_2
     * @unitTestTarget ArticleService.deleteArticle()
     * @unitTestType 基路径测试法
     * @unitTestDescription email对应的用户存在，但是ID对应的文章不存在
     */
    @Test
    public void deleteArticle2() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com", 0L);

        //assert
        assertEquals(false, articleService.deleteArticle("swx@swx.com", 0L));
    }

    /**
     * @unitTestId TEST_DELETE_ARTICLE_3
     * @unitTestTarget ArticleService.deleteArticle()
     * @unitTestType 基路径测试法
     * @unitTestDescription email对应的用户存在，对应ID的文章也存在，但该文章的作者与email对应的用户不一致
     */
    @Test
    public void deleteArticle3() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L, true);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com", 1L);

        //assert
        assertEquals(false, articleService.deleteArticle("exist@exist.com", 1L));
    }

    /**
     * @unitTestId TEST_DELETE_ARTICLE_4
     * @unitTestTarget ArticleService.deleteArticle()
     * @unitTestType 基路径测试法
     * @unitTestDescription email对应的用户存在，对应ID的文章也存在，但该文章的作者与email对应的用户不一致
     */
    @Test
    public void deleteArticle4() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L, true);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com", 1L);

        //assert
        assertEquals(false, articleService.deleteArticle("exist@exist.com", 1L));
    }

    /**
     * @unitTestId TEST_DELETE_ARTICLE_5
     * @unitTestTarget ArticleService.deleteArticle()
     * @unitTestType 基路径测试法
     * @unitTestDescription email对应的用户存在，对应ID的文章也存在，该文章的作者与email对应的用户一致，可进行删除
     */
    @Test
    public void deleteArticle5() throws Exception {
        //arrange
        Account swx = new Account(1L, "swx@swx.com", "test_user");
        when(accountRepositoryMock.findByEmail("swx@swx.com")).thenReturn(swx);
        Account exist = new Account(2L, "exist@exist.com", "test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(exist);
        Article article = new Article(1L, false);
        when(articleRepositoryMock.getArticle(swx, 1L)).thenReturn(article);
        when(articleRepositoryMock.delete(article)).thenReturn(true);

        //act
        articleService.deleteArticle("swx@swx.com", 1L);

        //assert
        assertEquals(true, articleService.deleteArticle("swx@swx.com", 1L));
    }

    @Test
    public void updateArticle() throws Exception {

    }

    /**
     * @unitTestId TEST_ADD_COMMENT_1
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户存在，id对应的文章存在，markdown不为空
     */
    @Test
    public void addComment1()throws Exception {
        //arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        //act
        ArticleComment articleComment = articleService.addComment("exist@exist.com",1L,"content");

        //verify
        verify(commentRepositoryMock,times(1)).save(articleComment);

    }

    /**
     * @unitTestId TEST_ADD_COMMENT_2
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户存在，id对应的文章存在，markdown为空
     */
    @Test
    public void addComment2()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(RuntimeException.class);

        // act
        articleService.addComment("exist@exist.com",1L,"");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_3
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户存在，id对应的文章不存在，markdown不为空
     */
    @Test
    public void addComment3()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("exist@exist.com",0L,"content");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_4
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户存在，id对应的文章不存在，markdown为空
     */
    @Test
    public void addComment4()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("exist@exist.com",0L,"");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_5
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户不存在，id对应的文章存在，markdown不为空
     */
    @Test
    public void addComment5()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("notexist@notexist.com",1L,"content");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_6
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户不存在，id对应的文章存在，markdown为空
     */
    @Test
    public void addComment6()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("notexist@notexist.com",1L,"");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_7
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户不存在，id对应的文章不存在，markdown不为空
     */
    @Test
    public void addComment7()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("notexist@notexist.com",0L,"content");
    }

    /**
     * @unitTestId TEST_ADD_COMMENT_8
     * @unitTestTarget ArticleService.addComment()
     * @unitTestType 判定组合覆盖
     * @unitTestDescription email对应的用户不存在，id对应的文章不存在，markdown为空
     */
    @Test
    public void addComment8()throws Exception {
        // arrange
        Account account = new Account(1L,"exist@exist.com","test_user");
        when(accountRepositoryMock.findByEmail("exist@exist.com")).thenReturn(account);
        Article article = new Article(1L,false);
        when(articleRepositoryMock.getArticle(1L)).thenReturn(article);

        // expect
        exception.expect(ResourceNotFoundException.class);

        // act
        articleService.addComment("notexist@notexist.com",0L,"");
    }

}