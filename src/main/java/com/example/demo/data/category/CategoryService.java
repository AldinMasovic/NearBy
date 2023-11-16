package com.example.demo.data.category;

import com.example.demo.data.exceptions.BadRequestException;
import com.example.demo.data.exceptions.NotFoundException;
import com.example.demo.data.product.Product;
import com.example.demo.data.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(Category category) {
        if (category.getId() != null) {
            throw new BadRequestException("Category already exist with ID - " + category.getId());
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresent(categoryRepository::delete);
    }

    @Transactional
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + categoryId));

        if (updatedCategory.getName() != null && !updatedCategory.getName().isEmpty()) {
            category.setName(updatedCategory.getName());
        }
        return categoryRepository.save(category);
    }
}
