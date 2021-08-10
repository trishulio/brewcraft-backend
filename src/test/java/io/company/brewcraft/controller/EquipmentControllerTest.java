//package io.company.brewcraft.controller;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verify;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import io.company.brewcraft.model.EquipmentEntity;
//import io.company.brewcraft.model.EquipmentStatus;
//import io.company.brewcraft.model.EquipmentType;
//import io.company.brewcraft.model.FacilityEntity;
//import io.company.brewcraft.model.FacilityAddressEntity;
//import io.company.brewcraft.model.QuantityEntity;
//import io.company.brewcraft.model.UnitEntity;
//import io.company.brewcraft.security.session.ContextHolder;
//import io.company.brewcraft.service.EquipmentService;
//
//@WebMvcTest(EquipmentController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//public class EquipmentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ContextHolder contextHolderMock;
//
//    @MockBean
//    private EquipmentService equipmentServiceMock;
//
//    @Test
//    public void testGetAllEquipment_ReturnsListOfEquipment() throws Exception {
//       FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null),"6045555555", "6045555555", null, null, null, null, 1);
//
//       EquipmentEntity equipment1 = new EquipmentEntity(1L, facility, "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//       EquipmentEntity equipment2 = new EquipmentEntity(2L, facility, "testName2", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);
//
//       List<EquipmentEntity> equipmentList = new ArrayList<>();
//       equipmentList.add(equipment1);
//       equipmentList.add(equipment2);
//
//       Page<EquipmentEntity> pagedResponse = new PageImpl<>(equipmentList);
//
//       when(equipmentServiceMock.getAllEquipment(null, null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true)).thenReturn(pagedResponse);
//
//       this.mockMvc.perform(get("/api/v1/facilities/equipment").header("Authorization", "Bearer " + "test"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        .andExpect(content().json("{"
//                + "  'content': ["
//                + "     {"
//                + "        'id': 1,"
//                + "        'facility': {"
//                + "             'id': 1,"
//                + "             'name': 'testName',"
//                + "             'address': {"
//                + "                 'id': 1,"
//                + "                 'addressLine1': 'addressLine1',"
//                + "                 'addressLine2': 'addressLine2',"
//                + "                 'country': 'country',"
//                + "                 'province': 'province',"
//                + "                 'city': 'city',"
//                + "                 'postalCode': 'postalCode'"
//                + "             },"
//                + "             'phoneNumber': '6045555555',"
//                + "             'faxNumber': '6045555555',"
//                + "             'version': 1"
//                + "         },"
//                + "        'name': 'testName',"
//                + "        'type': 'Barrel',"
//                + "        'status': 'Active',"
//                + "        'maxCapacity': {"
//                + "            'symbol': 'l',"
//                + "            'value': 100"
//                + "         },"
//                + "        'version': 1"
//                + "      },"
//                + "      {"
//                + "        'id': 2,"
//                + "        'facility': {"
//                + "             'id': 1,"
//                + "             'name': 'testName',"
//                + "             'address': {"
//                + "                 'id': 1,"
//                + "                 'addressLine1': 'addressLine1',"
//                + "                 'addressLine2': 'addressLine2',"
//                + "                 'country': 'country',"
//                + "                 'province': 'province',"
//                + "                 'city': 'city',"
//                + "                 'postalCode': 'postalCode'"
//                + "             },"
//                + "             'phoneNumber': '6045555555',"
//                + "             'faxNumber': '6045555555',"
//                + "             'version': 1"
//                + "         },"
//                + "        'name': 'testName2',"
//                + "        'type': 'Brite Tank',"
//                + "        'status': 'Active',"
//                + "        'maxCapacity': {"
//                + "            'symbol': 'l',"
//                + "            'value': 100"
//                + "         },"
//                + "        'version': 2"
//                + "      }"
//                + "   ],"
//                + " 'totalElements': 2,"
//                + " 'totalPages': 1"
//                + "}"));
//
//        verify(equipmentServiceMock, times(1)).getAllEquipment(null, null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true);
//    }
//
//    @Test
//    public void testGetEquipment_ReturnsEquipment() throws Exception {
//        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
//        EquipmentEntity equipment = new EquipmentEntity(1L, facility, "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//
//        when(equipmentServiceMock.getEquipment(1L)).thenReturn(equipment);
//
//        this.mockMvc.perform(get("/api/v1/facilities/equipment/1"))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "        'id': 1,"
//                 + "        'facility': {"
//                 + "             'id': 1,"
//                 + "             'name': 'testName',"
//                 + "             'address': {"
//                 + "                 'id': 1,"
//                 + "                 'addressLine1': 'addressLine1',"
//                 + "                 'addressLine2': 'addressLine2',"
//                 + "                 'country': 'country',"
//                 + "                 'province': 'province',"
//                 + "                 'city': 'city',"
//                 + "                 'postalCode': 'postalCode'"
//                 + "             },"
//                 + "             'phoneNumber': '6045555555',"
//                 + "             'faxNumber': '6045555555',"
//                 + "             'version': 1"
//                 + "         },"
//                 + "        'name': 'testName',"
//                 + "        'type': 'Barrel',"
//                 + "        'status': 'Active',"
//                 + "        'maxCapacity': {"
//                 + "            'symbol': 'l',"
//                 + "            'value': 100"
//                 + "         },"
//                 + "        'version': 1"
//                 + "    }"));
//
//         verify(equipmentServiceMock, times(1)).getEquipment(1L);
//    }
//
//    @Test
//    public void testAddEquipment_AddsEquipment() throws Exception {
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("type", EquipmentType.BARREL);
//        payload.put("status", EquipmentStatus.ACTIVE);
//
//        JSONObject maxCapacity = new JSONObject();
//        maxCapacity.put("symbol", "l");
//        maxCapacity.put("value", new BigDecimal(240.0));
//
//        payload.put("maxCapacity", maxCapacity);
//
//        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
//        EquipmentEntity equipment = new EquipmentEntity(1L, facility, "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//
//        when(equipmentServiceMock.addEquipment(eq(1L), any(EquipmentEntity.class))).thenReturn(equipment);
//
//        this.mockMvc.perform(post("/api/v1/facilities/1/equipment")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isCreated())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "        'id': 1,"
//                 + "        'facility': {"
//                 + "             'id': 1,"
//                 + "             'name': 'testName',"
//                 + "             'address': {"
//                 + "                 'id': 1,"
//                 + "                 'addressLine1': 'addressLine1',"
//                 + "                 'addressLine2': 'addressLine2',"
//                 + "                 'country': 'country',"
//                 + "                 'province': 'province',"
//                 + "                 'city': 'city',"
//                 + "                 'postalCode': 'postalCode'"
//                 + "             },"
//                 + "             'phoneNumber': '6045555555',"
//                 + "             'faxNumber': '6045555555',"
//                 + "             'version': 1"
//                 + "         },"
//                 + "        'name': 'testName',"
//                 + "        'type': 'Barrel',"
//                 + "        'status': 'Active',"
//                 + "        'maxCapacity': {"
//                 + "            'symbol': 'l',"
//                 + "            'value': 100"
//                 + "         },"
//                 + "        'version': 1"
//                 + "    }"));
//
//        verify(equipmentServiceMock, times(1)).addEquipment(eq(1L), any(EquipmentEntity.class));
//    }
//
//    @Test
//    public void testPutEquipment_putsEquipment() throws Exception {
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("type", EquipmentType.BARREL);
//        payload.put("status", EquipmentStatus.ACTIVE);
//
//        JSONObject maxCapacity = new JSONObject();
//        maxCapacity.put("symbol", "l");
//        maxCapacity.put("value", new BigDecimal(240.0));
//
//        payload.put("maxCapacity", maxCapacity);
//        payload.put("version", "1");
//
//        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
//        EquipmentEntity equipment = new EquipmentEntity(1L, facility, "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//
//        when(equipmentServiceMock.putEquipment(eq(1L), eq(1L), any(EquipmentEntity.class))).thenReturn(equipment);
//
//        this.mockMvc.perform(put("/api/v1/facilities/1/equipment/1")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "        'id': 1,"
//                 + "        'facility': {"
//                 + "             'id': 1,"
//                 + "             'name': 'testName',"
//                 + "             'address': {"
//                 + "                 'id': 1,"
//                 + "                 'addressLine1': 'addressLine1',"
//                 + "                 'addressLine2': 'addressLine2',"
//                 + "                 'country': 'country',"
//                 + "                 'province': 'province',"
//                 + "                 'city': 'city',"
//                 + "                 'postalCode': 'postalCode'"
//                 + "             },"
//                 + "             'phoneNumber': '6045555555',"
//                 + "             'faxNumber': '6045555555',"
//                 + "             'version': 1"
//                 + "         },"
//                 + "        'name': 'testName',"
//                 + "        'type': 'Barrel',"
//                 + "        'status': 'Active',"
//                 + "        'maxCapacity': {"
//                 + "            'symbol': 'l',"
//                 + "            'value': 100"
//                 + "         },"
//                 + "        'version': 1"
//                 + "    }"));
//
//        verify(equipmentServiceMock, times(1)).putEquipment(eq(1L), eq(1L), any(EquipmentEntity.class));
//    }
//
//    @Test
//    public void testPatchEquipment_patchesEquipment() throws Exception {
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("version", "1");
//
//        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
//        EquipmentEntity equipment = new EquipmentEntity(1L, facility, "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//
//        when(equipmentServiceMock.patchEquipment(eq(1L), any(EquipmentEntity.class))).thenReturn(equipment);
//
//        this.mockMvc.perform(patch("/api/v1/facilities/equipment/1")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "        'id': 1,"
//                 + "        'facility': {"
//                 + "             'id': 1,"
//                 + "             'name': 'testName',"
//                 + "             'address': {"
//                 + "                 'id': 1,"
//                 + "                 'addressLine1': 'addressLine1',"
//                 + "                 'addressLine2': 'addressLine2',"
//                 + "                 'country': 'country',"
//                 + "                 'province': 'province',"
//                 + "                 'city': 'city',"
//                 + "                 'postalCode': 'postalCode'"
//                 + "             },"
//                 + "             'phoneNumber': '6045555555',"
//                 + "             'faxNumber': '6045555555',"
//                 + "             'version': 1"
//                 + "         },"
//                 + "        'name': 'testName',"
//                 + "        'type': 'Barrel',"
//                 + "        'status': 'Active',"
//                 + "        'maxCapacity': {"
//                 + "            'symbol': 'l',"
//                 + "            'value': 100"
//                 + "         },"
//                 + "        'version': 1"
//                 + "    }"));
//
//        verify(equipmentServiceMock, times(1)).patchEquipment(eq(1L), any(EquipmentEntity.class));
//    }
//
//    @Test
//    public void testDeleteEquipment_DeletesEquipment() throws Exception {
//        this.mockMvc.perform(delete("/api/v1/facilities/equipment/1"))
//         .andExpect(status().isOk());
//
//         verify(equipmentServiceMock, times(1)).deleteEquipment(1L);
//    }
//
//}
