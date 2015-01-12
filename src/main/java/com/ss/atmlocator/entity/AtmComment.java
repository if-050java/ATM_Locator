package com.ss.atmlocator.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Olavin on 19.11.2014.
 */

@Entity
@Table(name="comments")
public class AtmComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String text;

    @Column
    private Timestamp timeCreated;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private AtmOffice atmOffice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public AtmOffice getAtmOffice() {
        return atmOffice;
    }

    public void setAtmOffice(AtmOffice atmOffice) {
        this.atmOffice = atmOffice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AtmComment comment = (AtmComment) o;

        if (!timeCreated.equals(comment.timeCreated)) return false;
        if (!user.equals(comment.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timeCreated.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
