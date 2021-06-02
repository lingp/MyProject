package com.shop.model.mappers.category;

import com.shop.model.category.po.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> selectAll();

    Category selectOne(Long id);

    int insert(Category category);

    int udpate(Category category);

    int delete(Long id);
}
