package com.example.demo.domain.category;

import com.example.demo.data.category.Category;

public class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryDto mapToDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category mapToCategory(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        return new Category(dto.getId(), dto.getName());
    }
}
