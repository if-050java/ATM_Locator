package com.ss.atmlocator.entity;

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
    private String login;
    private String email;
    private String password;
    private String avatar;
    @ManyToMany
    @JoinTable(name = "user_role")
    @JoinColumn(name = "role_id")
    private Set<Role> roles;
    @Column
    private int enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<AtmComment> atmComments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<AtmFavorite> atmFavorites;

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
}

