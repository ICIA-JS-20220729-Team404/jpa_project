package com.jsframe.nf_community.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table (name = "commenttbl")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cno;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Member cid;

    @ManyToOne
    @JoinColumn(name = "cbno")
    private Board cbno;

    @Column(nullable = false)
    private String ccontent;
}
