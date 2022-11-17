package com.jsframe.nf_community.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "anonymoustbl")
@Data
public class Anonymous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ano;

    @ManyToOne
    @JoinColumn(name = "aid")
    private Member aid;

    @Column(nullable = false, length = 50)
    private String acontent;
}
