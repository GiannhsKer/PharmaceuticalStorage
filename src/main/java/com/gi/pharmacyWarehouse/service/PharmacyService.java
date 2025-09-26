package com.gi.pharmacyWarehouse.service;

import com.gi.pharmacyWarehouse.model.*;
import com.gi.pharmacyWarehouse.repository.DrugCategoryRepository;
import com.gi.pharmacyWarehouse.repository.DrugRepository;
import com.gi.pharmacyWarehouse.repository.MovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;
    private final MovementRepository movementRepository;

    @Transactional
    public Drug addNewDrug(DrugDTO request) {
        log.info("Adding new drug : {}", request.name());
        DrugCategory drugCategory = drugCategoryRepository.findByName(request.category())
                .orElseGet(() -> drugCategoryRepository.save(DrugCategory.builder()
                        .name(request.category())
                        .build())
                );

        Drug drug = drugRepository.findByName(request.category())
                .orElseGet(() -> drugRepository.save(Drug.builder()
                        .name(request.name())
                        .code(request.code())
                        .price(request.price())
                        .stock(request.stock())
                        .category(drugCategory)
                        .build())
                );

        drugRepository.save(drug);

        Movement movement = Movement.builder()
                .drug(drug)
                .quantity(drug.getStock())
                .movementDate(LocalDate.now())
                .movementType(Movement.MovementType.IN)
                .build();

        movementRepository.save(movement);
        log.info("Movement created successfully : {}", movement);
        return drug;
    }

    public List<Drug> getAllDrugs() {
        log.info("Fetching all drugs");
        return drugRepository.findAll();
    }

    public List<MovementDTO> getDrugsByDateRange(LocalDate fromDate, LocalDate toDate, List<String> drugList) {
        log.info("Fetching movements between {} and {} for {}", fromDate, fromDate, drugList);
        return movementRepository.findMovementsByDrugNames(fromDate, toDate, drugList)
                .stream()
                .map(m -> new MovementDTO(
                        m.getId(),
                        m.getDrug().getName(),
                        m.getQuantity(),
                        m.getMovementDate()
                ))
                .toList();
    }

}
