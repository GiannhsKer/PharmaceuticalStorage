package com.gi.pharmacyWarehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "pharmacy_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // required by JPA, but protected so not misused
@AllArgsConstructor
@Getter
@Builder
@ToString
public class DrugCategory {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Exclude
    private UUID id;

    @NotBlank(message = "Category name is required")
    @Column(nullable = false, unique = true)
    private String name;
}