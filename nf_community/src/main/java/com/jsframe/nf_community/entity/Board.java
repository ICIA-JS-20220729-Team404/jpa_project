package com.jsframe.nf_community.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "boardtbl")
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bno;

    @ManyToOne
    @JoinColumn(name = "bid")
    private Member bid;

    @Column(nullable = false, length = 45)
    private String btitle;

    @Column(nullable = false)
    private String bcontent;

    @CreationTimestamp
    @Column
    private Timestamp bdate;

    @Column(nullable = false)
    private Integer bcount;
}
