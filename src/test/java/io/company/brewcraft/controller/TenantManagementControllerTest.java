package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.controller.AttributeFilter;

@WebMvcTest(TenantManagementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class TenantManagementControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private UtilityProvider utilityProvider;

    @MockBean
    private AttributeFilter filter;

    @MockBean
    private TenantManagementService tenantManagementServiceMock;

    @Test
    public void testGetAll_ReturnsListOfAllTenants() throws Exception {
       TenantDto tenant1 = new TenantDto(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"), "testName", "testUrl", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4));
       TenantDto tenant2 = new TenantDto(UUID.fromString("33581762-ff4b-494f-bc40-c66216e2b420"), "testName2", "testUrl2", LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5));
       List<TenantDto> tenantList = new ArrayList<>();
       tenantList.add(tenant1);
       tenantList.add(tenant2);
        
       when(tenantManagementServiceMock.getTenants()).thenReturn(tenantList);
        
       this.mockMvc.perform(get("/operations/tenants").header("Authorization", "Bearer " + "test"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[ {'id':'b3c97f3f-dd2d-42d0-9032-954f361de632',"
                                  + " 'name':'testName',"
                                  + " 'url':'testUrl',"
                                  + " 'created':'2020-01-02T03:04:00',"
                                  + " 'lastUpdated':'2020-01-02T03:04:00' },"
                                  + "{'id':'33581762-ff4b-494f-bc40-c66216e2b420',"
                                  + " 'name':'testName2',"
                                  + " 'url':'testUrl2',"
                                  + " 'created':'2021-02-03T04:05:00',"
                                  + " 'lastUpdated':'2021-02-03T04:05:00' } ]"));
        
        verify(tenantManagementServiceMock, times(1)).getTenants();
    }
    
    @Test
    public void testGetTenant_ReturnsTenant() throws Exception {
        TenantDto tenant = new TenantDto(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"), "testName", "testUrl", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4));
         
        when(tenantManagementServiceMock.getTenant(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"))).thenReturn(tenant);
         
        this.mockMvc.perform(get("/operations/tenants/b3c97f3f-dd2d-42d0-9032-954f361de632"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json("{'id':'b3c97f3f-dd2d-42d0-9032-954f361de632',"
                                 + " 'name':'testName',"
                                 + " 'url':'testUrl',"
                                 + " 'created':'2020-01-02T03:04:00'}"));
         
         verify(tenantManagementServiceMock, times(1)).getTenant(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"));
    }

    @Test
    public void testAddTenant_ReturnsNewTenantId() throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("name", "testName");
        payload.put("url", "testUrl");
     
        when(tenantManagementServiceMock.addTenant(any())).thenReturn(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"));
        
        this.mockMvc.perform(post("/operations/tenants")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(content().json("{'id':'b3c97f3f-dd2d-42d0-9032-954f361de632'}"));
        
        verify(tenantManagementServiceMock, times(1)).addTenant(any(TenantDto.class));
    }
    
    @Test
    public void testUpdateTenant_UpdatesTenant() throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("name", "testName");
        payload.put("url", "testUrl");
             
        this.mockMvc.perform(put("/operations/tenants/b3c97f3f-dd2d-42d0-9032-954f361de632")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").doesNotExist());
        
        verify(tenantManagementServiceMock, times(1)).updateTenant(any(TenantDto.class), eq(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632")));
    }

    @Test
    public void testDeleteTenant_DeletesTenant() throws Exception {         
        this.mockMvc.perform(delete("/operations/tenants/b3c97f3f-dd2d-42d0-9032-954f361de632"))
         .andExpect(status().isOk());
         
         verify(tenantManagementServiceMock, times(1)).deleteTenant(UUID.fromString("b3c97f3f-dd2d-42d0-9032-954f361de632"));
    }
}
