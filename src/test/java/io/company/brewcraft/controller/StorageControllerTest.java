package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.company.brewcraft.model.Storage;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.StorageService;

@WebMvcTest(StorageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class StorageControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private StorageService storageServiceMock;

        
//    @Test
//    public void testGetStoragesByFacilityId_ReturnsListOfStorages() throws Exception {
//       Storage storage1 = new Storage(1L, null, "testName", "testType", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//       Storage storage2 = new Storage(2L, null, "testName2", "testType2", LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);
//
//       List<Storage> storages = new ArrayList<>();
//       storages.add(storage1);
//       storages.add(storage2);
//        
//       when(facilityServiceMock.getStoragesByFacilityId(1L)).thenReturn(storages);
//        
//       this.mockMvc.perform(get("/api/facilities/1/storages").header("Authorization", "Bearer " + "test"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(content().json("["
//                + "    {"
//                + "        'id': 1,"
//                + "        'name': 'testName',"
//                + "        'type': 'testType',"
//                + "        'version': 1"
//                + "    },"
//                + "    {"
//                + "        'id': 2,"
//                + "        'name': 'testName2',"
//                + "        'type': 'testType2',"
//                + "        'version': 2"
//                + "    }"
//                + "]"));
//        
//        verify(facilityServiceMock, times(1)).getStoragesByFacilityId(1L);
//    }
    
    @Test
    public void testGetStorage_ReturnsStorage() throws Exception {
        Storage storage = new Storage(1L, null, "testName", "testType", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(storageServiceMock.getStorage(1L)).thenReturn(storage);
         
        this.mockMvc.perform(get("/api/facilities/storages/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'type': 'testType',"
                 + "        'version': 1"
                 + "    }"));
         
         verify(storageServiceMock, times(1)).getStorage(1L);
    }

    @Test
    public void testAddStorage_AddsStorage() throws Exception {        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", "testType");            
                     
        doNothing().when(storageServiceMock).addStorage(eq(1L), any(Storage.class));
        
        this.mockMvc.perform(post("/api/facilities/1/storages")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(storageServiceMock, times(1)).addStorage(eq(1L), any(Storage.class));
    }
    
    @Test
    public void testUpdateStorage_UpdatesStorage() throws Exception {       
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", "testType");        
        payload.put("version", "1");
                     
        doNothing().when(storageServiceMock).putStorage(eq(1L), any(Storage.class));
             
        this.mockMvc.perform(put("/api/facilities/storages/1")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(storageServiceMock, times(1)).putStorage(eq(1L), any(Storage.class));
    }

    @Test
    public void testDeleteStorage_DeletesStorage() throws Exception {         
        this.mockMvc.perform(delete("/api/facilities/storages/1"))
         .andExpect(status().isOk());
         
         verify(storageServiceMock, times(1)).deleteStorage(1L);
    }
}
