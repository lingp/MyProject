package com.shop.model.brand.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BrandAdminDto {
    private Long id;
    private String name;
    private String image;
    private String letter;
    private Date createdAt;
    private Date updatedAt;
}
