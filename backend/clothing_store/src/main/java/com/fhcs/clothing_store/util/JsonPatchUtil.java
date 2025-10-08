package com.fhcs.clothing_store.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Component
public class JsonPatchUtil {

    @Autowired
    private ObjectMapper objectMapper;

    private <T> T applyPatchAndGetModifiedFields(JsonPatch patch, Class<T> targetClass)
            throws JsonPatchException, JsonProcessingException, IllegalArgumentException {

        T emptyInstance;
        try {
            emptyInstance = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao criar instância vazia da classe alvo: " + e.getMessage());
        }

        JsonNode emptyNode = objectMapper.valueToTree(emptyInstance);
        JsonNode patchedNode = patch.apply(emptyNode);
        return objectMapper.treeToValue(patchedNode, targetClass);
    }

    // Applies a JsonPatch to an existing target object and returns full updated
    // object
    public <T> T applyPatch(JsonPatch patch, Object target, Class<T> targetClass) {

        try {
            JsonNode targetNode = objectMapper.valueToTree(target);
            JsonNode patchedNode = patch.apply(targetNode);
            return objectMapper.treeToValue(patchedNode, targetClass);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao aplicar patch ao usuário: " + e.getMessage());
        }
    }

    public <T> T extractPatchedFields(JsonPatch patch, Class<T> targetClass) {
        try {
            return applyPatchAndGetModifiedFields(patch, targetClass);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair campos modificados do patch: " + e.getMessage());
        }
    }
}
