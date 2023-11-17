package com.example.demo.domain.category;

import com.example.demo.data.category.Category;
import com.example.demo.data.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories()
                .stream()
                .map(CategoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(CategoryMapper.mapToCategory(categoryDto));
        return CategoryMapper.mapToDto(category);
    }

    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
        Category category = categoryService.updateCategory(categoryId, CategoryMapper.mapToCategory(categoryDto));
        return CategoryMapper.mapToDto(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

}
