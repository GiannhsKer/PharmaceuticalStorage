package com.gi.pharmacyWarehouse.controller;

import com.gi.pharmacyWarehouse.exception.DrugException;
import com.gi.pharmacyWarehouse.model.*;
import com.gi.pharmacyWarehouse.service.PharmacyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacy")
@Slf4j
@CrossOrigin(origins = "*")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/addDrug")
    public ResponseEntity<ApiResponse<Drug>> createAppointment(@Valid @RequestBody DrugRequest request) {
        log.info("POST /api/v1/pharmacy - New drug added : {} !", request.getName());
        try {
            Drug addedDrug = pharmacyService.addNewDrug(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.success("New drug added successfully", addedDrug));
        } catch (DrugException e) {
            log.error("Error adding drug: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<DrugResponse>>> getAllDrugs() {
        log.info("GET /api/v1/pharmacy - Fetching all drugs");
        List<DrugResponse> drugsList = new ArrayList<>(pharmacyService.getAllDrugs());
        String dbResponse = drugsList.isEmpty() ? "No drugs found in the warehouse" : "Drugs retrieved successfully";
        return ResponseEntity.ok(ApiResponse.success(dbResponse, drugsList));
    }

    @GetMapping("/movements")
    public ResponseEntity<ApiResponse<List<MovementResponse>>> searchMovements(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate,
            @RequestParam List<String> drugNames) {

        log.info("GET /api/v1/pharmacy/movements - Searching movements for drugs : {}, from : {}, to: {}",
                drugNames, fromDate, toDate);

        List<MovementResponse> appointments = pharmacyService.getDrugsByDateRange(fromDate,toDate,drugNames);

        return ResponseEntity.ok(ApiResponse.success("Appointments retrieved successfully",
                appointments));
    }

}
