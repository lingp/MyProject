package com.shop.model.category.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private Integer isParent;
    private Integer sort;
    private Date createdAt;
    private Date updatedAt;
}
