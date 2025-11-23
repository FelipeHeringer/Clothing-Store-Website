package com.fhcs.clothing_store.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.CategoryPatch;
import com.fhcs.clothing_store.dto.request.product.CategoryRequest;
import com.fhcs.clothing_store.entity.product.Category;
import com.fhcs.clothing_store.repository.product.CategoryRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.github.fge.jsonpatch.JsonPatch;

@Service
public class AdminCategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JsonPatchUtil jsonPatchUtil;

    public List<Category> createCategory(CategoryRequest categoryRequest) {

        try {
            Category category = new Category();
            category.setCategoryName(categoryRequest.getCategoryName());

            categoryRepository.save(category);

            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Category getCategoryById(Integer id) {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria nao encontrada com id: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Category updateCategory(JsonPatch patch, Integer categoryId) {
        try {
            Category category = getCategoryById(categoryId);

            CategoryPatch categoryPatch = jsonPatchUtil.extractPatchedFields(patch, CategoryPatch.class);
            applyChangesToCategory(category, categoryPatch);

            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteCategoryById(Integer categoryId) {
        try {
            Category category = getCategoryById(categoryId);
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void applyChangesToCategory(Category category, CategoryPatch categoryPatch) {
        if (categoryPatch.getCategoryName() != null) {
            category.setCategoryName(categoryPatch.getCategoryName());
        }
    }
}
