package com.fhcs.clothing_store.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.dto.request.product.CategoryRequest;
import com.fhcs.clothing_store.dto.response.product.CategoryResponse;
import com.fhcs.clothing_store.entity.product.Category;
import com.fhcs.clothing_store.service.admin.AdminCategoryService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService adminCategoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        try {
            List<Category> categories = adminCategoryService.createCategory(categoryRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.success(categories, "Categoria criada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryResponse.error("Error ao criar categoria: " + e.getMessage()));
        }
        
    }

    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories() {
        try {
            List<Category> categories = adminCategoryService.getAllCategories();

            return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.success(categories, "Categorias recuperadas com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryResponse.error("Error ao recuperar todas as categorias: " + e.getMessage()));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer categoryId) {
        try {
            Category category = adminCategoryService.getCategoryById(categoryId);

            List<Category> categoryList = List.of(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.success(categoryList, "Categoria recuperada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryResponse.error("Error ao recuperar categoria por id: " + e.getMessage()));
        }

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> udpateCategoryById(@RequestBody JsonPatch patch, @PathVariable Integer categoryId) {
        try {
            Category category = adminCategoryService.updateCategory(patch, categoryId);
            
            List<Category> categoryList = List.of(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.success(categoryList, "Categoria atualizada com sucesso!"));
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryResponse.error("Error ao atualizar categoria por id: " + e.getMessage()));
        }

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategoryById(@PathVariable Integer categoryId) {

        try {
            adminCategoryService.deleteCategoryById(categoryId);

            List<Category> categories = adminCategoryService.getAllCategories();

            if(categories.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(CategoryResponse.messageOnly(true, "Categoria deletada com sucesso"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(CategoryResponse.success(categories, "Categoria deletada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryResponse.error("Error ao deletar categoria por id: " + e.getMessage()));
        }

    }
}