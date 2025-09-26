package com.gi.pharmacyWarehouse.dto;

import com.gi.pharmacyWarehouse.model.Drug;

public record DrugDTO(
        String code,
        String name,
        double price,
        int stock,
        String category
) {
    public static DrugDTO fromEntity(Drug drug) {
        return new DrugDTO(
                drug.getCode(),
                drug.getName(),
                drug.getPrice(),
                drug.getStock(),
                drug.getCategory().getName()
        );
    }
}