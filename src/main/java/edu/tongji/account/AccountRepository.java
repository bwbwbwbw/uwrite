package edu.tongji.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findById(Long id);

    Account findByEmail(String email);

    Account findByNickname(String nickname);

}
