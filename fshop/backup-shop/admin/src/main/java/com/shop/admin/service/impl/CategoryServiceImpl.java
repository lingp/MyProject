package com.shop.admin.service.impl;

import com.shop.admin.dto.CategorySearchDto;
import com.shop.admin.service.CategoryService;
import com.shop.model.category.dto.CategoryAdminDto;
import com.shop.model.category.po.Category;
import com.shop.model.mappers.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryAdminDto> getAll() {
        List<Category> categoryList = categoryMapper.selectAll();
        List<CategoryAdminDto> rsCategoryList = new ArrayList<>();
        for (Category category: categoryList) {
            rsCategoryList.add(new CategoryAdminDto(category));
        }
        return rsCategoryList;
    }

    @Override
    public List<CategoryAdminDto> getList(CategorySearchDto categorySearchDto) {
        return null;
    }

    @Override
    public CategoryAdminDto add(CategoryAdminDto categoryAdminDto) {
        return null;
    }

    @Override
    public CategoryAdminDto update(CategoryAdminDto categoryAdminDto) {
        return null;
    }

    @Override
    public Boolean del(Long id) {
        return null;
    }

    @Override
    public CategoryAdminDto getOne(CategoryAdminDto categoryAdminDto) {
        return null;
    }
}
