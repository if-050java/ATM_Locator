package com.ss.atmlocator.entity;

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
    private int Id;

    @Column
    private String text;

    @Column
    private Timestamp timeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private AtmOffice atmOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
