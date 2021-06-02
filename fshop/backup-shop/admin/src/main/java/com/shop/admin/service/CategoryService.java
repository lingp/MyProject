package com.shop.admin.service;

import com.shop.admin.dto.CategorySearchDto;
import com.shop.model.category.dto.CategoryAdminDto;

import java.util.List;

public interface CategoryService {

    // 全部
    public List<CategoryAdminDto> getAll();

    // 分页
    public List<CategoryAdminDto> getList(CategorySearchDto categorySearchDto);

    // 新增
    public CategoryAdminDto add(CategoryAdminDto categoryAdminDto);

    // 修改
    public CategoryAdminDto update(CategoryAdminDto categoryAdminDto);

    // 删除
    public Boolean del(Long id);

    // 获取一个
    public CategoryAdminDto getOne(CategoryAdminDto categoryAdminDto);
}
