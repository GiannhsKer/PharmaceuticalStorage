package com.gi.pharmacyWarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "drug_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by JPA, but protected so not misused
@AllArgsConstructor
@Builder
public class DrugCategory {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Category name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}