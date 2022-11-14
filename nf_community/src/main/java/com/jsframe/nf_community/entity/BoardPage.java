package com.jsframe.nf_community.entity;

import lombok.Data;

import java.util.List;

@Data
public class BoardPage {
    private int numList;
    private int currentPage;
    private int totalPage;
    private List<Board> boardList;
}
