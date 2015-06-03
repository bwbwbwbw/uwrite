package edu.tongji.account;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collections;

public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @PostConstruct
    protected void initialize() {
        accountRepository.save(new Account("admin", "admin", "admin", "ROLE_ADMIN"));
        accountRepository.save(new Account("test", "test", "Test User", "ROLE_USER"));
        articleRepository.save(new Article(Long.MAX_VALUE,"test","test"));
        articleRepository.save(new Article(Long.MAX_VALUE,"test2","test2"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    public void signin(Account account) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account) {
        return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));
    }

    private User createUser(Account account) {
        return new User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
    }

    private GrantedAuthority createAuthority(Account account) {
        return new SimpleGrantedAuthority(account.getRole());
    }

}
