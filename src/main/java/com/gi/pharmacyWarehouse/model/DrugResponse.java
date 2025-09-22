package com.gi.pharmacyWarehouse.model;

import java.util.UUID;

public record DrugResponse(
        UUID id,
        String code,
        String name,
        double price,
        int stock,
        String categoryName
) {
    public static DrugResponse fromEntity(Drug drug) {
        return new DrugResponse(
                drug.getId(),
                drug.getCode(),
                drug.getName(),
                drug.getPrice(),
                drug.getStock(),
                drug.getCategory().getName()
        );
    }
}
