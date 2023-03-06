package com.hairong.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 * @author hairong
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -7562694009079356956L;
    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 当前是第几页
     */
    protected int pageNum = 1;
}
