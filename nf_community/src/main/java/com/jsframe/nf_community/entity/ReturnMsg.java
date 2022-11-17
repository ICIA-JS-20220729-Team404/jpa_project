package com.jsframe.nf_community.entity;

import lombok.Data;

@Data
public class ReturnMsg {
    private String msg;
    private long code;
    private boolean flag;
}
