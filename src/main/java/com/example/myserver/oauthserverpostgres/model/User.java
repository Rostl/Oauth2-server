package com.example.myserver.oauthserverpostgres.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "system_user", schema = "oauth_server")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enable = user.isEnable();
        this.accountNonExpired = user.isAccountNonExpired();
        this.credentialNonExpired = user.isCredentialNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.roles = user.getRoles();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "enable")
    private boolean enable;
    @Column(name = "account_non_expired ")
    private boolean accountNonExpired;
    @Column(name = "credentials_non_expired")
    private boolean credentialNonExpired;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user",schema = "oauth_server",joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();
}