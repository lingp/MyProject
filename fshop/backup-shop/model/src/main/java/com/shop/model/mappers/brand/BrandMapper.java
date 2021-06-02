package com.shop.model.mappers.brand;

import com.shop.model.brand.po.Brand;

import java.util.List;

public interface BrandMapper {
    List<Brand> selectAll();
}
