package com.shop.admin.controller;

import com.shop.admin.service.CategoryService;
import com.shop.model.category.dto.CategoryAdminDto;
import com.shop.model.category.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/get_all")
    public List<CategoryAdminDto> getAll() {
        return categoryService.getAll();
    }
}
