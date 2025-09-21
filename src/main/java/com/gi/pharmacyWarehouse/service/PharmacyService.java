package com.gi.pharmacyWarehouse.service;

import com.gi.pharmacyWarehouse.model.Drug;
import com.gi.pharmacyWarehouse.model.DrugCategory;
import com.gi.pharmacyWarehouse.model.DrugRequest;
import com.gi.pharmacyWarehouse.model.WarehouseMovement;
import com.gi.pharmacyWarehouse.repository.DrugCategoryRepository;
import com.gi.pharmacyWarehouse.repository.DrugRepository;
import com.gi.pharmacyWarehouse.repository.MovementRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@Slf4j
public class PharmacyService {

    private final DrugRepository drugRepository;
    private final DrugCategoryRepository drugCategoryRepository;
    private final MovementRepository movementRepository;

//    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

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

        Drug savedDrug = drugRepository.save(drug);
        log.info("Drug added successfully with id: {}", savedDrug.getId());

        WarehouseMovement movement = WarehouseMovement.builder()
                .drug(savedDrug)
                .quantity(savedDrug.getStock())
                .movementDate(LocalDate.now())
                .movementType(WarehouseMovement.MovementType.IN)
                .build();

        WarehouseMovement savedMovement = movementRepository.save(movement);
        log.info("Movement created successfully : {}", movement.toString());
        return savedDrug;
    }

//        public List<Appointment> getAllAppointments() {
//            log.info("Fetching all appointments");
//            return appointmentRepository.findAll();
//        }
//
//        public Optional<Appointment> getAppointmentById(UUID id) {
//            log.info("Fetching appointment with id: {}", id);
//            return appointmentRepository.findById(id);
//        }
//
//        public Optional<Appointment> getAppointmentByName(String name) {
//            log.info("Fetching appointment with name: {}", name);
//            return appointmentRepository.findByName(name);
//        }
//
//        public Optional<Appointment> getAppointmentByPhone(String phoneNumber) {
//            log.info("Fetching appointment with phone: {}", phoneNumber);
//            return appointmentRepository.findByPhone(phoneNumber);
//        }
//
//        public List<Appointment> getAppointmentsByDate(LocalDate date) {
//            log.info("Fetching appointments for date: {}", date);
//            LocalDateTime startOfDay = date.atStartOfDay();
//            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
//            return appointmentRepository.findByDate(startOfDay, endOfDay);
//        }
//
//        public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
//            log.info("Fetching appointments between {} and {}", start, end);
//            return appointmentRepository.findByDateRange(start, end, null);
//        }
//
//        @Transactional
//        public Appointment updateAppointment(AppointmentRequest request, UUID id) {
//            log.info("Updating appointment with id: {}", id);
//
//            Appointment existingAppointment = appointmentRepository.findById(id)
//                    .orElseThrow(() -> new AppointmentException("Appointment with id " + id + " does not exist"));
//
//            // Check for conflicts (excluding current appointment)
//            checkForConflictAppointments(request.getDateTime(), id);
//
//            existingAppointment.setName(request.getName());
//            existingAppointment.setPhoneNumber(request.getPhoneNumber());
//            existingAppointment.setDateTime(request.getDateTime());
//
//            Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
//            log.info("Appointment updated successfully with id: {}", updatedAppointment.getId());
//            return updatedAppointment;
//        }
//
//        @Transactional
//        public void deleteAppointment(UUID id) {
//            log.info("Deleting appointment with id: {}", id);
//
//            if (!appointmentRepository.existsById(id)) {
//                throw new AppointmentException("Appointment with id " + id + " does not exist");
//            }
//
//            appointmentRepository.deleteById(id);
//            log.info("Appointment deleted successfully with id: {}", id);
//        }

}
