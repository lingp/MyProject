package com.shop.admin.dto;

import com.shop.model.category.dto.CategoryAdminDto;
import lombok.Data;

@Data
public class CategorySearchDto extends CategoryAdminDto {
    private Long pageSize;
    private Long totalNums;
}
