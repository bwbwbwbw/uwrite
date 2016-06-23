package edu.tongji.account;

import edu.tongji.error.ConstraintException;
import org.hibernate.exception.ConstraintViolationException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account signUp(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return account;
    }

    public void signIn(Account account) {
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(account.getRole()));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new User(account.getEmail(), account.getPassword(), authorities),
                null, authorities
        ));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account findByNickname(String nickname) {
        return accountRepository.findByNickname(nickname);
    }

}
