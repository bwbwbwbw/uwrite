package edu.tongji.account;

import edu.tongji.error.ConstraintException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
@Transactional
public class AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account save(Account account) {
        try {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            entityManager.persist(account);
            return account;
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException ex = (ConstraintViolationException) e.getCause();
                if (ex.getConstraintName().equals("email")) {
                    throw new ConstraintException("This email is already taken.");
                } else if (ex.getConstraintName().equals("nickname")) {
                    throw new ConstraintException("This nickname is already taken.");
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    public Account findById(Long id) {
        try {
            return entityManager.createNamedQuery(Account.FIND_BY_ID, Account.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Account findByEmail(String email) {
        try {
            return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }
    public void update(Account account)
    {
        entityManager.merge(account);

    }

}
