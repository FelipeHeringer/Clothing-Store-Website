package com.fhcs.clothing_store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPatchDto {
    
    private String collectionName;
    private String description;
    private String launchDate;
}
