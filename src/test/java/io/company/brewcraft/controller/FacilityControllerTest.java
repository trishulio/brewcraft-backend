//package io.company.brewcraft.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//import org.json.JSONArray;
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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import io.company.brewcraft.dto.AddressDto;
//import io.company.brewcraft.dto.FacilityDto;
//import io.company.brewcraft.dto.FacilityEquipmentDto;
//import io.company.brewcraft.dto.FacilityStorageDto;
//import io.company.brewcraft.dto.QuantityDto;
//import io.company.brewcraft.model.EquipmentEntity;
//import io.company.brewcraft.model.EquipmentStatus;
//import io.company.brewcraft.model.EquipmentType;
//import io.company.brewcraft.model.FacilityEntity;
//import io.company.brewcraft.model.FacilityAddressEntity;
//import io.company.brewcraft.model.QuantityEntity;
//import io.company.brewcraft.model.StorageEntity;
//import io.company.brewcraft.model.StorageType;
//import io.company.brewcraft.model.UnitEntity;
//import io.company.brewcraft.security.session.ContextHolder;
//import io.company.brewcraft.service.FacilityService;
//
//@WebMvcTest(FacilityController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//public class FacilityControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ContextHolder contextHolderMock;
//
//    @MockBean
//    private FacilityService facilityServiceMock;
//
//    @Test
//    public void testGetFacilities_ReturnsAllFacilities() throws Exception {
//       FacilityEntity facility1 = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//       facility1.getEquipment().add(new EquipmentEntity(1L, facility1, "name1", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//       facility1.getEquipment().add(new EquipmentEntity(2L, facility1, "name2", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//       facility1.getStorages().add(new StorageEntity(1L, facility1, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//       facility1.getStorages().add(new StorageEntity(2L, facility1, "name2", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//       FacilityEntity facility2 = new FacilityEntity(2L, "facility2", new FacilityAddressEntity(2L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//       facility2.getEquipment().add(new EquipmentEntity(3L, facility2, "name1", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//       facility2.getStorages().add(new StorageEntity(3L, facility2, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//       List<FacilityEntity> facilityList = new ArrayList<>();
//       facilityList.add(facility1);
//       facilityList.add(facility2);
//
//       Page<FacilityEntity> pagedResponse = new PageImpl<>(facilityList);
//
//       when(facilityServiceMock.getAllFacilities(0, 100, new HashSet<>(Arrays.asList("id")), true)).thenReturn(pagedResponse);
//
//       this.mockMvc.perform(get("/api/v1/facilities/").header("Authorization", "Bearer " + "test"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//        .andExpect(content().json("{"
//                + "  'content': ["
//                + "    {"
//                + "        'id': 1,"
//                + "        'name': 'facility1',"
//                + "        'address': {"
//                + "            'id': 1,"
//                + "            'addressLine1': 'addressLine1',"
//                + "            'addressLine2': 'addressLine2',"
//                + "            'country': 'country',"
//                + "            'province': 'province',"
//                + "            'city': 'city',"
//                + "            'postalCode': 'postalCode'"
//                + "        },"
//                + "        'phoneNumber': '6045555555',"
//                + "        'faxNumber': '6045555555',"
//                + "        'equipment': ["
//                + "            {"
//                + "                'id': 1,"
//                + "                'name': 'name1',"
//                + "                'type': 'Barrel',"
//                + "                'status': 'Active',"
//                + "                'maxCapacity': {"
//                + "                    'symbol': 'l',"
//                + "                    'value': 100"
//                + "             },"
//                + "                'version': 1"
//                + "            },"
//                + "            {"
//                + "                'id': 2,"
//                + "                'name': 'name2',"
//                + "                'type': 'Brite Tank',"
//                + "                'status': 'Active',"
//                + "                'maxCapacity': {"
//                + "                    'symbol': 'l',"
//                + "                    'value': 100"
//                + "                 },"
//                + "                'version': 1"
//                + "            }"
//                + "        ],"
//                + "        'storages': ["
//                + "            {"
//                + "                'id': 1,"
//                + "                'name': 'name1',"
//                + "                'type': 'General',"
//                + "                'version': 1"
//                + "            },"
//                + "            {"
//                + "                'id': 2,"
//                + "                'name': 'name2',"
//                + "                'type': 'General',"
//                + "                'version': 1"
//                + "            }"
//                + "        ],"
//                + "        'version': 1"
//                + "        },"
//                + "        {"
//                + "        'id': 2,"
//                + "        'name': 'facility2',"
//                + "        'address': {"
//                + "            'id': 2,"
//                + "            'addressLine1': 'addressLine1',"
//                + "            'addressLine2': 'addressLine2',"
//                + "            'country': 'country',"
//                + "            'province': 'province',"
//                + "            'city': 'city',"
//                + "            'postalCode': 'postalCode'"
//                + "        },"
//                + "        'phoneNumber': '6045555555',"
//                + "        'faxNumber': '6045555555',"
//                + "        'equipment': ["
//                + "            {"
//                + "                'id': 3,"
//                + "                'name': 'name1',"
//                + "                'type': 'Barrel',"
//                + "                'status': 'Active',"
//                + "                'maxCapacity': {"
//                + "                    'symbol': 'l',"
//                + "                    'value': 100"
//                + "                 },"
//                + "                'version': 1"
//                + "            }"
//                + "        ],"
//                + "        'storages': ["
//                + "            {"
//                + "                'id': 3,"
//                + "                'name': 'name1',"
//                + "                'type': 'General',"
//                + "                'version': 1"
//                + "            }"
//                + "        ],"
//                + "        'version': 1"
//                + "          }"
//                + "       ],"
//                + "     'totalElements': 2,"
//                + "     'totalPages': 1"
//                + "}"));
//
//        verify(facilityServiceMock, times(1)).getAllFacilities(0, 100, new HashSet<>(Arrays.asList("id")), true);
//    }
//
//    @Test
//    public void testGetFacility_ReturnsFacility() throws Exception {
//        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        facility.getEquipment().add(new EquipmentEntity(1L, facility, "name1", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getEquipment().add(new EquipmentEntity(2L, facility, "name2", EquipmentType.BARREL, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getStorages().add(new StorageEntity(1L, facility, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getStorages().add(new StorageEntity(2L, facility, "name2", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//        when(facilityServiceMock.getFacility(1L)).thenReturn(facility);
//
//        this.mockMvc.perform(get("/api/v1/facilities/1/"))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "    'id': 1,"
//                 + "    'name': 'facility1',"
//                 + "    'address': {"
//                 + "        'id': 1,"
//                 + "        'addressLine1': 'addressLine1',"
//                 + "        'addressLine2': 'addressLine2',"
//                 + "        'country': 'country',"
//                 + "        'province': 'province',"
//                 + "        'city': 'city',"
//                 + "        'postalCode': 'postalCode'"
//                 + "    },"
//                 + "    'phoneNumber': '6045555555',"
//                 + "    'faxNumber': '6045555555',"
//                 + "    'equipment': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'Brite Tank',"
//                 + "            'status': 'Active',"
//                 + "            'maxCapacity': {"
//                 + "                'symbol': 'l',"
//                 + "                'value': 100"
//                 + "             },"
//                 + "            'version': 1"
//                 + "        },"
//                 + "        {"
//                 + "            'id': 2,"
//                 + "            'name': 'name2',"
//                 + "            'type': 'Barrel',"
//                 + "            'status': 'Active',"
//                 + "            'maxCapacity': {"
//                 + "                'symbol': 'l',"
//                 + "                'value': 100"
//                 + "             },"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'storages': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'General',"
//                 + "            'version': 1"
//                 + "        },"
//                 + "        {"
//                 + "            'id': 2,"
//                 + "            'name': 'name2',"
//                 + "            'type': 'General',"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'version': 1"
//                 + "    }"));
//
//         verify(facilityServiceMock, times(1)).getFacility(1L);
//    }
//
//    @Test
//    public void testAddFacility_AddsFacility() throws Exception {
//        JSONObject address = new JSONObject();
//        address.put("addressLine1", "addressLine1");
//        address.put("addressLine2", "addressLine2");
//        address.put("country", "country");
//        address.put("province", "province");
//        address.put("city", "city");
//        address.put("postalCode", "postalCode");
//
//        JSONObject equipment = new JSONObject();
//        equipment.put("name", "name");
//        equipment.put("type", EquipmentType.BARREL);
//        equipment.put("status", EquipmentStatus.ACTIVE);
//
//        JSONObject maxCapacity = new JSONObject();
//        maxCapacity.put("symbol", "l");
//        maxCapacity.put("value", new BigDecimal(240.0));
//
//        equipment.put("maxCapacity", maxCapacity);
//
//        JSONObject storage = new JSONObject();
//        storage.put("name", "name");
//        storage.put("type", StorageType.GENERAL);
//
//        JSONArray equipmentArray = new JSONArray();
//        equipmentArray.put(0, equipment);
//
//        JSONArray storageArray = new JSONArray();
//        storageArray.put(0, storage);
//
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("address", address);
//        payload.put("phoneNumber", "6045555555");
//        payload.put("faxNumber", "6045555555");
//        payload.put("equipment", equipmentArray);
//        payload.put("storages", storageArray);
//
//        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        facility.getEquipment().add(new EquipmentEntity(1L, facility, "name1", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getStorages().add(new StorageEntity(1L, facility, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//        when(facilityServiceMock.addFacility(any(FacilityEntity.class))).thenReturn(facility);
//
//        this.mockMvc.perform(post("/api/v1/facilities/")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isCreated())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "    'id': 1,"
//                 + "    'name': 'facility1',"
//                 + "    'address': {"
//                 + "        'id': 1,"
//                 + "        'addressLine1': 'addressLine1',"
//                 + "        'addressLine2': 'addressLine2',"
//                 + "        'country': 'country',"
//                 + "        'province': 'province',"
//                 + "        'city': 'city',"
//                 + "        'postalCode': 'postalCode'"
//                 + "    },"
//                 + "    'phoneNumber': '6045555555',"
//                 + "    'faxNumber': '6045555555',"
//                 + "    'equipment': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'Brite Tank',"
//                 + "            'status': 'Active',"
//                 + "            'maxCapacity': {"
//                 + "                'symbol': 'l',"
//                 + "                'value': 100"
//                 + "             },"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'storages': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'General',"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'version': 1"
//                 + "    }"));
//
//        verify(facilityServiceMock, times(1)).addFacility(any(FacilityEntity.class));
//    }
//
//    @Test
//    public void testPutFacility_PutsFacility() throws Exception {
//        JSONObject address = new JSONObject();
//        address.put("addressLine1", "addressLine1");
//        address.put("addressLine2", "addressLine2");
//        address.put("country", "country");
//        address.put("province", "province");
//        address.put("city", "city");
//        address.put("postalCode", "postalCode");
//
//        JSONObject equipment = new JSONObject();
//        equipment.put("id", 1L);
//        equipment.put("name", "name");
//        equipment.put("type", EquipmentType.BARREL);
//        equipment.put("status", EquipmentStatus.ACTIVE);
//
//        JSONObject maxCapacity = new JSONObject();
//        maxCapacity.put("symbol", "l");
//        maxCapacity.put("value", new BigDecimal(240.0));
//
//        equipment.put("maxCapacity", maxCapacity);
//
//        equipment.put("version", 1L);
//
//        JSONObject storage = new JSONObject();
//        storage.put("id", 1L);
//        storage.put("name", "name");
//        storage.put("type", StorageType.GENERAL);
//        storage.put("version", 1L);
//
//        JSONArray equipmentArray = new JSONArray();
//        equipmentArray.put(0, equipment);
//
//        JSONArray storageArray = new JSONArray();
//        storageArray.put(0, storage);
//
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("address", address);
//        payload.put("phoneNumber", "6045555555");
//        payload.put("faxNumber", "6045555555");
//        payload.put("equipment", equipmentArray);
//        payload.put("storages", storageArray);
//        payload.put("version", 1L);
//
//        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        facility.getEquipment().add(new EquipmentEntity(1L, facility, "name1", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getStorages().add(new StorageEntity(1L, facility, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//        FacilityDto facilityDto = new FacilityDto(1L, "facility1", new AddressDto(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), 1);
//        facilityDto.getEquipment().add(new FacilityEquipmentDto(1L, "name1", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityDto("l", new BigDecimal(100.0)), 1));
//        facilityDto.getStorages().add(new FacilityStorageDto(1L, "name1", StorageType.GENERAL, 1));
//
//        when(facilityServiceMock.putFacility(eq(1L), any(FacilityEntity.class))).thenReturn(facility);
//
//        this.mockMvc.perform(put("/api/v1/facilities/1/")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "    'id': 1,"
//                 + "    'name': 'facility1',"
//                 + "    'address': {"
//                 + "        'id': 1,"
//                 + "        'addressLine1': 'addressLine1',"
//                 + "        'addressLine2': 'addressLine2',"
//                 + "        'country': 'country',"
//                 + "        'province': 'province',"
//                 + "        'city': 'city',"
//                 + "        'postalCode': 'postalCode'"
//                 + "    },"
//                 + "    'phoneNumber': '6045555555',"
//                 + "    'faxNumber': '6045555555',"
//                 + "    'equipment': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'Brite Tank',"
//                 + "            'status': 'Active',"
//                 + "            'maxCapacity': {"
//                 + "                'symbol': 'l',"
//                 + "                'value': 100"
//                 + "             },"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'storages': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'General',"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'version': 1"
//                 + "    }"));
//
//        verify(facilityServiceMock, times(1)).putFacility(eq(1L), any(FacilityEntity.class));
//    }
//
//    @Test
//    public void testPatchFacility_PatchesFacility() throws Exception {
//        JSONObject payload = new JSONObject();
//        payload.put("name", "testName");
//        payload.put("version", 1L);
//
//        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        facility.getEquipment().add(new EquipmentEntity(1L, facility, "name1", EquipmentType.BRITE_TANK, EquipmentStatus.ACTIVE, new QuantityEntity(1L, new UnitEntity("l"), new BigDecimal(100.0)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//        facility.getStorages().add(new StorageEntity(1L, facility, "name1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
//
//        when(facilityServiceMock.patchFacility(eq(1L), any(FacilityEntity.class))).thenReturn(facility);
//
//        this.mockMvc.perform(patch("/api/v1/facilities/1/")
//         .contentType(MediaType.APPLICATION_JSON_VALUE)
//         .content(payload.toString()))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//         .andExpect(content().json("{"
//                 + "    'id': 1,"
//                 + "    'name': 'facility1',"
//                 + "    'address': {"
//                 + "        'id': 1,"
//                 + "        'addressLine1': 'addressLine1',"
//                 + "        'addressLine2': 'addressLine2',"
//                 + "        'country': 'country',"
//                 + "        'province': 'province',"
//                 + "        'city': 'city',"
//                 + "        'postalCode': 'postalCode'"
//                 + "    },"
//                 + "    'phoneNumber': '6045555555',"
//                 + "    'faxNumber': '6045555555',"
//                 + "    'equipment': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'Brite Tank',"
//                 + "            'status': 'Active',"
//                 + "            'maxCapacity': {"
//                 + "                'symbol': 'l',"
//                 + "                'value': 100"
//                 + "             },"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'storages': ["
//                 + "        {"
//                 + "            'id': 1,"
//                 + "            'name': 'name1',"
//                 + "            'type': 'General',"
//                 + "            'version': 1"
//                 + "        }"
//                 + "    ],"
//                 + "    'version': 1"
//                 + "    }"));
//
//        verify(facilityServiceMock, times(1)).patchFacility(eq(1L), any(FacilityEntity.class));
//    }
//
//    @Test
//    public void testDeleteFacility_DeletesFacility() throws Exception {
//        this.mockMvc.perform(delete("/api/v1/facilities/1/"))
//         .andExpect(status().isOk());
//
//         verify(facilityServiceMock, times(1)).deleteFacility(1L);
//    }
//}
