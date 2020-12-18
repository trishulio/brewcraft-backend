package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any; 
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.FacilityAddress;
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.FacilityService;

@WebMvcTest(FacilityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class FacilityControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private FacilityService facilityServiceMock;

    @Test
    public void testGetFacilities_ReturnsAllFacilities() throws Exception {
       Facility facility1 = new Facility(1L, "facility1", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       facility1.getEquipment().add(new Equipment(1L, facility1, "name1", EquipmentType.BARREL, "status1", new QuantityEntity(1L, new UnitEntity("L", "L"), new BigDecimal("100.0")), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       facility1.getEquipment().add(new Equipment(2L, facility1, "name2", EquipmentType.BRITE_TANK, "status2", new QuantityEntity(2L, new UnitEntity("L", "L"), new BigDecimal("100.0")), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       facility1.getStorages().add(new Storage(1L, facility1, "name1", "type1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       facility1.getStorages().add(new Storage(2L, facility1, "name2", "type2", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       
       Facility facility2 = new Facility(2L, "facility2", new FacilityAddress(2L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       facility2.getEquipment().add(new Equipment(3L, facility2, "name1", EquipmentType.BARREL, "status1", new QuantityEntity(3L, new UnitEntity("L", "L"), new BigDecimal("100.0")), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       facility2.getStorages().add(new Storage(3L, facility2, "name1", "type1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
       
       List<Facility> facilityList = new ArrayList<>();
       facilityList.add(facility1);
       facilityList.add(facility2);
       
       Page<Facility> pagedResponse = new PageImpl<>(facilityList);
        
       when(facilityServiceMock.getAllFacilities(0, 100, new String[]{"id"}, true)).thenReturn(pagedResponse);
        
       this.mockMvc.perform(get("/v1/facilities/").header("Authorization", "Bearer " + "test"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("["
                + "    {"
                + "    'id': 1,"
                + "    'name': 'facility1',"
                + "    'address': {"
                + "        'id': 1,"
                + "        'addressLine1': 'addressLine1',"
                + "        'addressLine2': 'addressLine2',"
                + "        'country': 'country',"
                + "        'province': 'province',"
                + "        'city': 'city',"
                + "        'postalCode': 'postalCode'"
                + "    },"
                + "    'equipment': ["
                + "        {"
                + "            'id': 1,"
                + "            'name': 'name1',"
                + "            'type': 'type1',"
                + "            'status': 'status1',"
                + "            'maxCapacity': 100.0,"
                + "            'version': 1"
                + "        },"
                + "        {"
                + "            'id': 2,"
                + "            'name': 'name2',"
                + "            'type': 'type2',"
                + "            'status': 'status2',"
                + "            'maxCapacity': 100.0,"
                + "            'version': 1"
                + "        }"
                + "    ],"
                + "    'storages': ["
                + "        {"
                + "            'id': 1,"
                + "            'name': 'name1',"
                + "            'type': 'type1',"
                + "            'version': 1"
                + "        },"
                + "        {"
                + "            'id': 2,"
                + "            'name': 'name2',"
                + "            'type': 'type2',"
                + "            'version': 1"
                + "        }"
                + "    ],"
                + "    'version': 1"
                + "    },"
                + "    {"
                + "    'id': 2,"
                + "    'name': 'facility2',"
                + "    'address': {"
                + "        'id': 2,"
                + "        'addressLine1': 'addressLine1',"
                + "        'addressLine2': 'addressLine2',"
                + "        'country': 'country',"
                + "        'province': 'province',"
                + "        'city': 'city',"
                + "        'postalCode': 'postalCode'"
                + "    },"
                + "    'equipment': ["
                + "        {"
                + "            'id': 3,"
                + "            'name': 'name1',"
                + "            'type': 'type1',"
                + "            'status': 'status1',"
                + "            'maxCapacity': 100.0,"
                + "            'version': 1"
                + "        }"
                + "    ],"
                + "    'storages': ["
                + "        {"
                + "            'id': 3,"
                + "            'name': 'name1',"
                + "            'type': 'type1',"
                + "            'version': 1"
                + "        }"
                + "    ],"
                + "    'version': 1"
                + "    }"
                + "]"));
       
        verify(facilityServiceMock, times(1)).getAllFacilities(0, 100, new String[]{"id"}, true);
    }
    
    @Test
    public void testGetFacility_ReturnsFacility() throws Exception {
        Facility facility = new Facility(1L, "facility1", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), new ArrayList<>(), new ArrayList<>(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        facility.getEquipment().add(new Equipment(1L, facility, "name1", EquipmentType.BRITE_TANK, "status1", new QuantityEntity(1L, new UnitEntity("L", "L"), new BigDecimal("100.0")), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
        facility.getEquipment().add(new Equipment(2L, facility, "name2", EquipmentType.BARREL, "status2", new QuantityEntity(1L, new UnitEntity("L", "L"), new BigDecimal("100.0")), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
        facility.getStorages().add(new Storage(1L, facility, "name1", "type1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
        facility.getStorages().add(new Storage(2L, facility, "name2", "type2", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1));
        
        when(facilityServiceMock.getFacility(1L)).thenReturn(facility);
         
        this.mockMvc.perform(get("/v1/facilities/1/"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json("{"
                 + "    'id': 1,"
                 + "    'name': 'facility1',"
                 + "    'address': {"
                 + "        'id': 1,"
                 + "        'addressLine1': 'addressLine1',"
                 + "        'addressLine2': 'addressLine2',"
                 + "        'country': 'country',"
                 + "        'province': 'province',"
                 + "        'city': 'city',"
                 + "        'postalCode': 'postalCode'"
                 + "    },"
                 + "    'equipment': ["
                 + "        {"
                 + "            'id': 1,"
                 + "            'name': 'name1',"
                 + "            'type': 'type1',"
                 + "            'status': 'status1',"
                 + "            'maxCapacity': 100.0,"
                 + "            'version': 1"
                 + "        },"
                 + "        {"
                 + "            'id': 2,"
                 + "            'name': 'name2',"
                 + "            'type': 'type2',"
                 + "            'status': 'status2',"
                 + "            'maxCapacity': 100.0,"
                 + "            'version': 1"
                 + "        }"
                 + "    ],"
                 + "    'storages': ["
                 + "        {"
                 + "            'id': 1,"
                 + "            'name': 'name1',"
                 + "            'type': 'type1',"
                 + "            'version': 1"
                 + "        },"
                 + "        {"
                 + "            'id': 2,"
                 + "            'name': 'name2',"
                 + "            'type': 'type2',"
                 + "            'version': 1"
                 + "        }"
                 + "    ],"
                 + "    'version': 1"
                 + "    }"));
         
         verify(facilityServiceMock, times(1)).getFacility(1L);
    }

    @Test
    public void testAddFacility_AddsFacility() throws Exception {        
        JSONObject address = new JSONObject();
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("country", "country");
        address.put("province", "province");
        address.put("city", "city");
        address.put("postalCode", "postalCode");
        
        JSONObject equipment = new JSONObject();
        equipment.put("name", "name");
        equipment.put("type", "type");
        equipment.put("status", "status");
        equipment.put("maxCapacity", 100.0);
        
        JSONObject storage = new JSONObject();
        storage.put("name", "name");
        storage.put("type", "type");
        
        JSONArray equipmentArray = new JSONArray();
        equipmentArray.put(0, equipment);
        
        JSONArray storageArray = new JSONArray();
        storageArray.put(0, storage);
        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("address", address); 
        payload.put("equipment", equipmentArray);      
        payload.put("storages", storageArray);      
                     
        doNothing().when(facilityServiceMock).addFacility(any(Facility.class));
        
        this.mockMvc.perform(post("/v1/facilities/")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(facilityServiceMock, times(1)).addFacility(any(Facility.class));
    }
    
    @Test
    public void testPutFacility_PutsFacility() throws Exception {       
        JSONObject address = new JSONObject();
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("country", "country");
        address.put("province", "province");
        address.put("city", "city");
        address.put("postalCode", "postalCode");
        
        JSONObject equipment = new JSONObject();
        equipment.put("id", 1L);
        equipment.put("name", "name");
        equipment.put("type", "type");
        equipment.put("status", "status");
        equipment.put("maxCapacity", 100.0);
        equipment.put("version", 1L);
        
        JSONObject storage = new JSONObject();
        storage.put("id", 1L);
        storage.put("name", "name");
        storage.put("type", "type");
        storage.put("version", 1L);
        
        JSONArray equipmentArray = new JSONArray();
        equipmentArray.put(0, equipment);
        
        JSONArray storageArray = new JSONArray();
        storageArray.put(0, storage);
        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("address", address); 
        payload.put("equipment", equipmentArray);      
        payload.put("storages", storageArray);    
        payload.put("version", 1L);
                     
        doNothing().when(facilityServiceMock).putFacility(eq(1L), any(Facility.class));
             
        this.mockMvc.perform(put("/v1/facilities/1/")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(facilityServiceMock, times(1)).putFacility(eq(1L), any(Facility.class));
    }

    @Test
    public void testDeleteFacility_DeletesFacility() throws Exception {         
        this.mockMvc.perform(delete("/v1/facilities/1/"))
         .andExpect(status().isOk());
         
         verify(facilityServiceMock, times(1)).deleteFacility(1L);
    }
}
