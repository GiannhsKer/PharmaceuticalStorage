package com.gi.pharmacyWarehouse.dto;

import com.gi.pharmacyWarehouse.model.Drug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record DrugDTO(
        @NotBlank(message = "Code is required")
        String code,
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        double price,
        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        int stock,
        @NotBlank(message = "Category is required")
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