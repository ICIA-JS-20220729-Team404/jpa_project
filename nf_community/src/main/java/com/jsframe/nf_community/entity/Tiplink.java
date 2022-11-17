package com.jsframe.nf_community.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tiplinktbl")
public class Tiplink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tno;

    @ManyToOne
    @JoinColumn(name = "tmid")
    private Member tmid;
    
    @Column(nullable = false)
    private String tcontent;

    @Column(nullable = false)
    private String tlink;
}
