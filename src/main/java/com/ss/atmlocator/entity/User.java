package com.ss.atmlocator.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String login;

    @Column
    private String name;

    @Column(unique=true)
    private String email;

  //  @JsonIgnore //Ignoring this field in JSON serializing
    @Column
    private String password;

    @Column
    private String avatar;

    @Column
    private UserStatus enabled;

    @Column
    private Date lastLoging;

    @JsonIgnore //Ignoring this field in JSON serializing
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    @JoinColumn(name = "roles_id")
    private Set<Role> roles;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private Set<AtmComment> atmComments;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private Set<AtmFavorite> atmFavorites;

    public User(int id, String login, String email, String password, UserStatus enabled) {
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

    public Integer getId() {
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

    public UserStatus getEnabled() {
        return enabled;
    }

    public void setEnabled(UserStatus enabled) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastLoging() {
        return lastLoging;
    }

    public void setLastLoging(Date lastLoging) {
        this.lastLoging = lastLoging;
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

