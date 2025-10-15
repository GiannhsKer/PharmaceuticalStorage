package com.gi.pharmacyWarehouse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gi.pharmacyWarehouse.dto.DrugDTO;
import com.gi.pharmacyWarehouse.model.Drug;
import com.gi.pharmacyWarehouse.model.DrugCategory;
import com.gi.pharmacyWarehouse.model.Movement;
import com.gi.pharmacyWarehouse.repository.DrugRepository;
import com.gi.pharmacyWarehouse.repository.MovementRepository;
import com.gi.pharmacyWarehouse.service.PharmacyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class PharmacyWarehouseTest {

    @Mock
    private MovementRepository movementRepository;
    private DrugRepository drugRepository;

    @InjectMocks
    private PharmacyService pharmacyService;

    private static final String drugName = "xanax";
    private static final String drugCode = "code123";
    private static final int drugPrice = 12;
    private static final int drugStock = 2;
    private static final String drugCategory = "calming";
    private static final DrugCategory drugCategoryObj = new DrugCategory(UUID.randomUUID(), drugCategory);
    private static final DrugDTO drugDTO = new DrugDTO(drugName,drugCode, drugPrice, drugStock,drugCategory);
    private static final Drug drugObj = new Drug(UUID.randomUUID(),drugCode, drugName, drugPrice, drugStock,drugCategoryObj,drugCategory);
    private static final Movement movement = Movement.builder().drug(drugObj).quantity(drugObj.getStock()).movementDate(LocalDate.now())
            .movementType(Movement.MovementType.IN)
            .build();

//    @Test
//    void createMovementTest() {
//        Drug result = pharmacyService.addNewDrug(drugDTO);
//
//        assertNotNull(result);
//        assertEquals(2, result.getStock());
//        verify(movementRepository, times(1)).save(movement);
//    }

//    @Test
//    void saveMovement_shouldThrow_whenDrugIsNull() {
//        Movement movement = new Movement();
//        movement.setQuantity(5);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            pharmacyService.saveMovement(movement);
//        });
//
//        assertEquals("Drug must not be null", exception.getMessage());
//        verifyNoInteractions(movementRepository);
//    }
}
