package com.gi.pharmacyWarehouse.service;

import com.gi.pharmacyWarehouse.model.*;
import com.gi.pharmacyWarehouse.repository.DrugCategoryRepository;
import com.gi.pharmacyWarehouse.repository.DrugRepository;
import com.gi.pharmacyWarehouse.repository.MovementRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PharmacyService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;
    private final MovementRepository movementRepository;

    @Autowired
    public PharmacyService(DrugRepository drugRepository, DrugCategoryRepository drugCategoryRepository, MovementRepository movementRepository) {
        this.drugRepository = drugRepository;
        this.drugCategoryRepository = drugCategoryRepository;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public Drug addNewDrug(DrugRequest request) {
        log.info("Adding new drug : {}", request.getName());
        DrugCategory drugCategory = drugCategoryRepository.findByName(request.getCategory())
                .orElseGet(() -> drugCategoryRepository.save(DrugCategory.builder()
                        .name(request.getCategory())
                        .build())
                );

        Drug drug = drugRepository.findByName(request.getCategory())
                .orElseGet(() -> drugRepository.save(Drug.builder()
                        .name(request.getName())
                        .code(request.getCode())
                        .price(request.getPrice())
                        .stock(request.getStock())
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

    public List<DrugResponse> getAllDrugs() {
        log.info("Fetching all drugs");
        return drugRepository.findAll().stream()
                .map(DrugResponse::fromEntity)
                .toList();
    }

    public List<MovementResponse> getDrugsByDateRange(LocalDate fromDate, LocalDate toDate, List<String> drugList) {
        log.info("Fetching movements between {} and {} for {}", fromDate, fromDate, drugList);
        return movementRepository.findMovementsByDrugNames(fromDate, toDate, drugList)
                .stream()
                .map(m -> new MovementResponse(
                        m.getId(),
                        m.getDrug().getName(),
                        m.getQuantity(),
                        m.getMovementDate()
                ))
                .toList();
    }

}
