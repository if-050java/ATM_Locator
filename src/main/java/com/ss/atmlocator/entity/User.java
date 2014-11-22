package com.ss.atmlocator.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by roman on 10.11.14.
 */
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String login;

    @Column(unique=true)
    private String email;

    @Column
    private String password;

    @Column
    private String avatar;

    @Column
    private int enabled;

    @JsonIgnore //Ignoring this field in JSON serializing
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    @JoinColumn(name = "role_id")
    private Set<Role> roles;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private Set<AtmComment> atmComments;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private Set<AtmFavorite> atmFavorites;

    public User(int id, String login, String email, String password, int enabled) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Set<AtmComment> getAtmComments() {
        return atmComments;
    }

    public void setAtmComments(Set<AtmComment> atmComments) {
        this.atmComments = atmComments;
    }

    public Set<AtmFavorite> getAtmFavorites() {
        return atmFavorites;
    }

    public void setAtmFavorites(Set<AtmFavorite> atmFavorites) {
        this.atmFavorites = atmFavorites;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                ", atmComments=" + atmComments +
                ", atmFavorites=" + atmFavorites +
                '}';
    }
}

