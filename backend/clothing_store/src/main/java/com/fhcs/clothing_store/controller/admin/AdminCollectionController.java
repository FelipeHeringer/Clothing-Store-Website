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

import com.fhcs.clothing_store.dto.request.product.CollectionRequest;
import com.fhcs.clothing_store.dto.response.product.CollectionResponse;
import com.fhcs.clothing_store.entity.product.Collection;
import com.fhcs.clothing_store.service.admin.AdminCollectionService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@RequestMapping("/api/admin/collections")
public class AdminCollectionController {

    @Autowired
    private AdminCollectionService adminCollectionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@RequestBody CollectionRequest collectionRequest) {
        try {
            List<Collection> collections = adminCollectionService.createCollection(collectionRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CollectionResponse.success(collections, "Coleção criada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CollectionResponse.error("Falha ao criar coleção: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<CollectionResponse> getAllCollections() {
        try {
            List<Collection> collections = adminCollectionService.getAllCollections();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CollectionResponse.success(collections, "Coleções recuperadas com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CollectionResponse.error("Falha ao recuperar coleções: " + e.getMessage()));
        }

    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionResponse> getCollectionById(@PathVariable Integer collectionId) {
        try {
            Collection collection = adminCollectionService.getCollectionById(collectionId);

            List<Collection> collections = List.of(collection);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CollectionResponse.success(collections, "Coleção recuperada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CollectionResponse.error("Falha ao recuperar coleção: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{collectionId}")
    public ResponseEntity<CollectionResponse> updateCollectionById(@RequestBody JsonPatch patch, @PathVariable Integer collectionId) {
        try {
            Collection collection = adminCollectionService.updateCollection(patch, collectionId);

            List<Collection> collections = List.of(collection);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CollectionResponse.success(collections, "Coleção atualizada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CollectionResponse.error("Falha ao atualizar coleção: " + e.getMessage()));
        }

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<CollectionResponse> deleteCollectionById(@PathVariable Integer collectionId) {
        try {
            adminCollectionService.deleteCollection(collectionId);

            List<Collection> collections = adminCollectionService.getAllCollections();

            if (collections.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(CollectionResponse.messageOnly(true, "Coleção deletada com sucesso."));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CollectionResponse.success(collections, "Coleção deletada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CollectionResponse.error("Falha ao deletar coleção: " + e.getMessage()));
        }
    }
}
