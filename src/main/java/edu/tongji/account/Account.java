package edu.tongji.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tongji.article.Article;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = {"email"}),
        @UniqueConstraint(name = "nickname", columnNames = {"nickname"}),
})
@NamedQueries({
        @NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email"),
        @NamedQuery(name = Account.FIND_BY_ID, query = "select a from Account a where a.id = :id"),
})
public class Account implements java.io.Serializable {

    public static final String FIND_BY_EMAIL = "Account.findByEmail";
    public static final String FIND_BY_ID = "Account.findById";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    private String avatar;

    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<Article> collection;

    private String role = "ROLE_USER";

    protected Account() {

    }

    public Account(String email, String password, String nickname, String role) {
        this.setEmail(email);
        this.setPassword(password);
        this.setNickname(nickname);
        this.setRole(role);
        this.setAvatar("default.png");
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Article> getCollection() {
        return collection;
    }

    public void setCollection(List<Article> collection) {
        this.collection = collection;
    }

}
