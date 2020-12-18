package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import io.company.brewcraft.model.QuantityEntity;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.EquipmentService;

@WebMvcTest(EquipmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class EquipmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private EquipmentService equipmentServiceMock;
    
    @Test
    public void testGetAllEquipment_ReturnsListOfEquipment() throws Exception {
       Equipment equipment1 = new Equipment(1L, null, "testName", EquipmentType.BARREL, "testStatus", new QuantityEntity(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       Equipment equipment2 = new Equipment(2L, null, "testName2", EquipmentType.BRITE_TANK, "testStatus2", new QuantityEntity(), LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);

       List<Equipment> equipmentList = new ArrayList<>();
       equipmentList.add(equipment1);
       equipmentList.add(equipment2);
       
       Page<Equipment> pagedResponse = new PageImpl<>(equipmentList);
        
       when(equipmentServiceMock.getAllEquipment(null, null, null, null, 0, 100, new String[]{"id"}, true)).thenReturn(pagedResponse);
        
       this.mockMvc.perform(get("/api/facilities/equipment").header("Authorization", "Bearer " + "test"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("["
                + "    {"
                + "        'id': 1,"
                + "        'name': 'testName',"
                + "        'type': 'testType',"
                + "        'status': 'testStatus',"
                + "        'maxCapacity': 100.0,"
                + "        'version': 1"
                + "    },"
                + "    {"
                + "        'id': 2,"
                + "        'name': 'testName2',"
                + "        'type': 'testType2',"
                + "        'status': 'testStatus2',"
                + "        'maxCapacity': 100.0,"
                + "        'version': 2"
                + "    }"
                + "]"));
        
        verify(equipmentServiceMock, times(1)).getAllEquipment(null, null, null, null, 0, 100, new String[]{"id"}, true);
    }
    
    @Test
    public void testGetEquipment_ReturnsEquipment() throws Exception {
        Equipment equipment = new Equipment(1L, null, "testName", EquipmentType.BARREL, "testStatus", new QuantityEntity(), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(equipmentServiceMock.getEquipment(1L)).thenReturn(equipment);
         
        this.mockMvc.perform(get("/api/facilities/equipment/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'type': 'testType',"
                 + "        'status': 'testStatus',"
                 + "        'maxCapacity': 100.0,"
                 + "        'version': 1"
                 + "    }"));
         
         verify(equipmentServiceMock, times(1)).getEquipment(1L);
    }

    @Test
    public void testAddEquipment_AddsEquipment() throws Exception {        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", "testType");        
        payload.put("status", "testStatus");        
        payload.put("maxCapacity", 100.0);        
                     
        doNothing().when(equipmentServiceMock).addEquipment(eq(1L), any(Equipment.class));
        
        this.mockMvc.perform(post("/api/facilities/1/equipment")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(equipmentServiceMock, times(1)).addEquipment(eq(1L), any(Equipment.class));
    }
    
    @Test
    public void testUpdateEquipment_UpdatesEquipment() throws Exception {       
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", "testType");        
        payload.put("status", "testStatus");        
        payload.put("maxCapacity", 100.0);        
        payload.put("version", "1");
                     
        doNothing().when(equipmentServiceMock).putEquipment(eq(1L), any(Equipment.class));
             
        this.mockMvc.perform(put("/api/facilities/equipment/1")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(equipmentServiceMock, times(1)).putEquipment(eq(1L), any(Equipment.class));
    }

    @Test
    public void testDeleteEquipment_DeletesEquipment() throws Exception {         
        this.mockMvc.perform(delete("/api/facilities/equipment/1"))
         .andExpect(status().isOk());
         
         verify(equipmentServiceMock, times(1)).deleteEquipment(1L);
    }

}
