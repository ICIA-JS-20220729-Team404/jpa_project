package com.jsframe.nf_community.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "membertbl")
@Data
public class Member {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mno;

    @Id
    @Column(unique = true, nullable = false, length = 45)
    private String mid;

    @Column(nullable = false, length = 200)
    private String mpw;

    @Column(nullable = false, length = 45)
    private String mname;

    @Column(length = 45)
    private String mphone;
}
