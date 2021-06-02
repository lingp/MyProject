package com.shop.model.category.dto;

import com.shop.model.category.po.Category;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryAdminDto {
    private Long id;
    private String name;
    private Long parentId;
    private Integer isParent;
    private Integer sort;
    private Date createdAt;
    private Date updatedAt;

    public CategoryAdminDto() {

    }

    public CategoryAdminDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentId();
        this.isParent = category.getIsParent();
        this.sort = category.getSort();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
}
