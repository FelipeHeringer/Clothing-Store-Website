package com.fhcs.clothing_store.service.admin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.CollectionPatchDto;
import com.fhcs.clothing_store.dto.request.product.CollectionRequest;
import com.fhcs.clothing_store.entity.product.Collection;
import com.fhcs.clothing_store.repository.product.CollectionRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.github.fge.jsonpatch.JsonPatch;

@Service
public class AdminCollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private JsonPatchUtil jsonPatchUtil;

    public List<Collection> createCollection(CollectionRequest collectionRequest) {
        try {
            Collection collection = new Collection();
            collection.setCollectionName(collectionRequest.getCollectionName());
            collection.setDescription(collectionRequest.getDescription());
            collection.setLaunchDate(
                    LocalDate.parse(collectionRequest.getLaunchDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            collectionRepository.save(collection);

            return collectionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Collection> getAllCollections() {
        try {
            return collectionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Collection getCollectionById(Integer collectionId) {
        try {
            return collectionRepository.findById(collectionId)
                    .orElseThrow(() -> new RuntimeException("Coleção não encontrada com ID: " + collectionId));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Collection updateCollection(JsonPatch patch, Integer collectionId) {
        try {
            Collection collection = getCollectionById(collectionId);

            CollectionPatchDto patchedCollection = jsonPatchUtil.extractPatchedFields(patch, CollectionPatchDto.class);

            applyPatchesToCollection(collection, patchedCollection);

            return collectionRepository.save(collection);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteCollection(Integer collectionId) {
        try {
            Collection collection = collectionRepository.findById(collectionId)
                    .orElseThrow(() -> new RuntimeException("Coleção não encontrada com id: " + collectionId));
            collectionRepository.delete(collection);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void applyPatchesToCollection(Collection collection, CollectionPatchDto patchedCollection) {
        if (patchedCollection.getCollectionName() != null) {
            collection.setCollectionName(patchedCollection.getCollectionName());
        }
        if (patchedCollection.getDescription() != null) {
            collection.setDescription(patchedCollection.getDescription());
        }
        if (patchedCollection.getLaunchDate() != null) {
            collection.setLaunchDate(
                    LocalDate.parse(patchedCollection.getLaunchDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
}
