package com.ss.atmlocator.entity;

import com.ss.atmlocator.entity.enums.AtmOffice;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Olavin on 19.11.2014.
 */
@Entity
@Table(name="favorites")
public class AtmFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private Timestamp timeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private AtmOffice atmOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
