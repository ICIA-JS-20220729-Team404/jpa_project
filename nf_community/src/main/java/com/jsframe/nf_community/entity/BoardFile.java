package com.jsframe.nf_community.entity;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "boardfiletbl")
@Data
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bfnum;

    @ManyToOne
    @JoinColumn(name = "bfbid")
    private Board bfbid;

    @Column(nullable = false, length = 50)
    private String bfsysname;

    @Column(nullable = false, length = 50)
    private String bforiname;
}
