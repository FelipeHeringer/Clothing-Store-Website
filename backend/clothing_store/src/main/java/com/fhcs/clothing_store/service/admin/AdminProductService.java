package com.fhcs.clothing_store.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.ProductPatchDto;
import com.fhcs.clothing_store.dto.request.product.ProductRequest;
import com.fhcs.clothing_store.entity.product.Category;
import com.fhcs.clothing_store.entity.product.Collection;
import com.fhcs.clothing_store.entity.product.Product;
import com.fhcs.clothing_store.repository.product.ProductRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.github.fge.jsonpatch.JsonPatch;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminCollectionService collectionService;

    @Autowired
    private AdminCategoryService categoryService;

    @Autowired
    private JsonPatchUtil jsonPatchUtil;

    public Product createProduct(ProductRequest productRequest) {
        try {
            Collection collection = collectionService.getCollectionById(productRequest.getCollectionId());
            Category category = categoryService.getCategoryById(productRequest.getCategoryId());

            Product product = new Product();

            product.setCollection(collection);
            product.setCategory(category);
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setScore(productRequest.getScore());

            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Product getProductById(Integer productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

            return product;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Product updateProduct(Integer productId, JsonPatch patch) {
        try {
            Product product = getProductById(productId);

            ProductPatchDto productPatch = jsonPatchUtil.extractPatchedFields(patch, ProductPatchDto.class);

            applyChangesToProduct(product, productPatch);

            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteProduct(Integer productId) {
        try {
            Product product = getProductById(productId);

            productRepository.delete(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void applyChangesToProduct(Product product, ProductPatchDto productPatch) throws Exception {

        if (productPatch.getName() != null) {
            product.setName(productPatch.getName());
        }
        if (productPatch.getDescription() != null) {
            product.setDescription(productPatch.getDescription());
        }
        if (productPatch.getPrice() != null) {
            product.setPrice(productPatch.getPrice());
        }
        if (productPatch.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(productPatch.getCategoryId());
            product.setCategory(category);
        }
        if (productPatch.getCollectionId() != null) {
            Collection collection = collectionService.getCollectionById(productPatch.getCollectionId());
            product.setCollection(collection);
        }
        if (productPatch.getScore() != null) {
            product.setScore(productPatch.getScore());
        }
    }
}
