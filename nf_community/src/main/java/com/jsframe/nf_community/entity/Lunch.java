package com.jsframe.nf_community.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lunchtbl")
@Data
public class Lunch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lno;

    @ManyToOne
    @JoinColumn(name = "lid")
    private Member lid;

    @Column(nullable = false, length = 50)
    private String lcontent;

    @Column(nullable = true, length = 10)
    private int lcount;
}
