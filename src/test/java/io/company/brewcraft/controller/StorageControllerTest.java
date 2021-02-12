package io.company.brewcraft.controller;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.FacilityAddress;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.model.StorageType;
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

    @Test
    public void testGetStorages_ReturnsListOfStorages() throws Exception {
        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
        StorageEntity storage1 = new StorageEntity(1L, facility, "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        StorageEntity storage2 = new StorageEntity(2L, facility, "testName2", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       List<StorageEntity> storageList = new ArrayList<>();
       storageList.add(storage1);
       storageList.add(storage2);
       
       Page<StorageEntity> pagedResponse = new PageImpl<>(storageList);
        
       when(storageServiceMock.getAllStorages(0, 100, new HashSet<>(Arrays.asList("id")), true)).thenReturn(pagedResponse);

       this.mockMvc.perform(get("/api/v1/facilities/storages").header("Authorization", "Bearer " + "test"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json("{"
                + "  'content': ["
                + "     {"
                + "        'id': 1,"
                + "        'facility': {"
                + "             'id': 1,"
                + "             'name': 'testName',"
                + "             'address': {"
                + "                 'id': 1,"
                + "                 'addressLine1': 'addressLine1',"
                + "                 'addressLine2': 'addressLine2',"
                + "                 'country': 'country',"
                + "                 'province': 'province',"
                + "                 'city': 'city',"
                + "                 'postalCode': 'postalCode'"
                + "             },"
                + "             'phoneNumber': '6045555555',"
                + "             'faxNumber': '6045555555',"
                + "             'version': 1"
                + "         },"
                + "        'name': 'testName1',"
                + "        'type': 'General',"
                + "        'version': 1"
                + "      },"
                + "      {"
                + "        'id': 2,"
                + "        'facility': {"
                + "             'id': 1,"
                + "             'name': 'testName',"
                + "             'address': {"
                + "                 'id': 1,"
                + "                 'addressLine1': 'addressLine1',"
                + "                 'addressLine2': 'addressLine2',"
                + "                 'country': 'country',"
                + "                 'province': 'province',"
                + "                 'city': 'city',"
                + "                 'postalCode': 'postalCode'"
                + "             },"
                + "             'phoneNumber': '6045555555',"
                + "             'faxNumber': '6045555555',"
                + "             'version': 1"
                + "         },"
                + "        'name': 'testName2',"
                + "        'type': 'General',"
                + "        'version': 1"
                + "      }"
                + "   ],"
                + " 'totalElements': 2,"
                + " 'totalPages': 1"
                + "}"));
        
        verify(storageServiceMock, times(1)).getAllStorages(0, 100, new HashSet<>(Arrays.asList("id")), true);
    }
    
    @Test
    public void testGetStorage_ReturnsStorage() throws Exception {
        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
        StorageEntity storage = new StorageEntity(1L, facility, "testName", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        when(storageServiceMock.getStorage(1L)).thenReturn(storage);

        this.mockMvc.perform(get("/api/v1/facilities/storages/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'facility': {"
                 + "             'id': 1,"
                 + "             'name': 'testName',"
                 + "             'address': {"
                 + "                 'id': 1,"
                 + "                 'addressLine1': 'addressLine1',"
                 + "                 'addressLine2': 'addressLine2',"
                 + "                 'country': 'country',"
                 + "                 'province': 'province',"
                 + "                 'city': 'city',"
                 + "                 'postalCode': 'postalCode'"
                 + "             },"
                 + "             'phoneNumber': '6045555555',"
                 + "             'faxNumber': '6045555555',"
                 + "             'version': 1"
                 + "         },"
                 + "        'name': 'testName',"
                 + "        'type': 'General',"
                 + "        'version': 1"
                 + "    }"));
         
         verify(storageServiceMock, times(1)).getStorage(1L);
    }

    @Test
    public void testAddStorage_AddsStorage() throws Exception {        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", StorageType.GENERAL);
        
        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
        StorageEntity storage = new StorageEntity(1L,facility, "testName", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        when(storageServiceMock.addStorage(eq(1L), any(StorageEntity.class))).thenReturn(storage);

        this.mockMvc.perform(post("/api/v1/facilities/1/storages")
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'facility': {"
                 + "             'id': 1,"
                 + "             'name': 'testName',"
                 + "             'address': {"
                 + "                 'id': 1,"
                 + "                 'addressLine1': 'addressLine1',"
                 + "                 'addressLine2': 'addressLine2',"
                 + "                 'country': 'country',"
                 + "                 'province': 'province',"
                 + "                 'city': 'city',"
                 + "                 'postalCode': 'postalCode'"
                 + "             },"
                 + "             'phoneNumber': '6045555555',"
                 + "             'faxNumber': '6045555555',"
                 + "             'version': 1"
                 + "         },"
                 + "        'name': 'testName',"
                 + "        'type': 'General',"
                 + "        'version': 1"
                 + "    }"));
        
        verify(storageServiceMock, times(1)).addStorage(eq(1L), any(StorageEntity.class));
    }
    
    @Test
    public void testPutStorage_putsStorage() throws Exception {       
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("type", StorageType.GENERAL);        
        payload.put("version", "1");
        
        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
        StorageEntity storage = new StorageEntity(1L, facility, "testName", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        when(storageServiceMock.putStorage(eq(1L), eq(1L), any(StorageEntity.class))).thenReturn(storage);
             
        this.mockMvc.perform(put("/api/v1/facilities/1/storages/1")
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'facility': {"
                 + "             'id': 1,"
                 + "             'name': 'testName',"
                 + "             'address': {"
                 + "                 'id': 1,"
                 + "                 'addressLine1': 'addressLine1',"
                 + "                 'addressLine2': 'addressLine2',"
                 + "                 'country': 'country',"
                 + "                 'province': 'province',"
                 + "                 'city': 'city',"
                 + "                 'postalCode': 'postalCode'"
                 + "             },"
                 + "             'phoneNumber': '6045555555',"
                 + "             'faxNumber': '6045555555',"
                 + "             'version': 1"
                 + "         },"
                 + "        'name': 'testName',"
                 + "        'type': 'General',"
                 + "        'version': 1"
                 + "    }"));
        
        verify(storageServiceMock, times(1)).putStorage(eq(1L), eq(1L), any(StorageEntity.class));
    }
    
    @Test
    public void testPatchStorage_patchesStorage() throws Exception {       
        JSONObject payload = new JSONObject();
        payload.put("name", "testName"); 
        payload.put("version", "1");
        
        FacilityEntity facility = new FacilityEntity(1L, "testName", new FacilityAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", null, null, null, null, 1);
        StorageEntity storage = new StorageEntity(1L, facility, "testName", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        when(storageServiceMock.patchStorage(eq(1L), any(StorageEntity.class))).thenReturn(storage);

        this.mockMvc.perform(patch("/api/v1/facilities/storages/1")
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'facility': {"
                 + "             'id': 1,"
                 + "             'name': 'testName',"
                 + "             'address': {"
                 + "                 'id': 1,"
                 + "                 'addressLine1': 'addressLine1',"
                 + "                 'addressLine2': 'addressLine2',"
                 + "                 'country': 'country',"
                 + "                 'province': 'province',"
                 + "                 'city': 'city',"
                 + "                 'postalCode': 'postalCode'"
                 + "             },"
                 + "             'phoneNumber': '6045555555',"
                 + "             'faxNumber': '6045555555',"
                 + "             'version': 1"
                 + "         },"
                 + "        'name': 'testName',"
                 + "        'type': 'General',"
                 + "        'version': 1"
                 + "    }", true));
        
        verify(storageServiceMock, times(1)).patchStorage(eq(1L), any(StorageEntity.class));
    }

    @Test
    public void testDeleteStorage_DeletesStorage() throws Exception {         
        this.mockMvc.perform(delete("/api/v1/facilities/storages/1"))
         .andExpect(status().isOk());
         
         verify(storageServiceMock, times(1)).deleteStorage(1L);
    }
}
