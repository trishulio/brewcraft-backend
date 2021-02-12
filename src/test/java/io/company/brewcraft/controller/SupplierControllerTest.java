package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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

import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.model.SupplierAddressEntity;
import io.company.brewcraft.model.SupplierContactEntity;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.SupplierService;

@WebMvcTest(SupplierController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class SupplierControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private SupplierService supplierServiceMock;

    @Test
    public void testGetAll_ReturnsListOfAllSuppliers() throws Exception {
       SupplierEntity supplier1 = new SupplierEntity(1L, "testName", new ArrayList<>(), new SupplierAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       supplier1.getContacts().add(new SupplierContactEntity(1L, supplier1, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
       supplier1.getContacts().add(new SupplierContactEntity(2L, supplier1, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));
       
       SupplierEntity supplier2 = new SupplierEntity(2L, "testName2", new ArrayList<>(), new SupplierAddressEntity(2L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);
       supplier2.getContacts().add(new SupplierContactEntity(3L, supplier2, "name3", "lastName3", "position3", "email3", "phoneNumber3", null, null, 1));
       supplier2.getContacts().add(new SupplierContactEntity(4L, supplier2, "name4", "lastName4", "position4", "email4", "phoneNumber4", null, null, 1));

       List<SupplierEntity> suppliers = new ArrayList<>();
       suppliers.add(supplier1);
       suppliers.add(supplier2);
       
       Page<SupplierEntity> pagedResponse = new PageImpl<>(suppliers);
        
       when(supplierServiceMock.getSuppliers(0, 100, new String[]{"id"}, true)).thenReturn(pagedResponse);
        
       this.mockMvc.perform(get("/api/suppliers").header("Authorization", "Bearer " + "test"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
                "{"
                + "'suppliers': ["
                + "    {"
                + "        'id': 1,"
                + "        'name': 'testName',"
                + "        'contacts': ["
                + "            {"
                + "                'id': 1,"
                + "                'firstName': 'name1',"
                + "                'lastName': 'lastName1',"
                + "                'position': 'position1',"
                + "                'email': 'email1',"
                + "                'phoneNumber': 'phoneNumber1',"
                + "                'version': 1"
                + "            },"
                + "            {"
                + "                'id': 2,"
                + "                'firstName': 'name2',"
                + "                'lastName': 'lastName2',"
                + "                'position': 'position2',"
                + "                'email': 'email2',"
                + "                'phoneNumber': 'phoneNumber2',"
                + "                'version': 1"
                + "            }"
                + "        ],"
                + "        'address': {"
                + "            'id': 1,"
                + "            'addressLine1': 'addressLine1',"
                + "            'addressLine2': 'addressLine2',"
                + "            'country': 'country',"
                + "            'province': 'province',"
                + "            'city': 'city',"
                + "            'postalCode': 'postalCode'"
                + "        },"
                + "        'version': 1"
                + "    },"
                + "    {"
                + "        'id': 2,"
                + "        'name': 'testName2',"
                + "        'contacts': ["
                + "            {"
                + "                'id': 3,"
                + "                'firstName': 'name3',"
                + "                'lastName': 'lastName3',"
                + "                'position': 'position3',"
                + "                'email': 'email3',"
                + "                'phoneNumber': 'phoneNumber3',"
                + "                'version': 1"
                + "            },"
                + "            {"
                + "                'id': 4,"
                + "                'firstName': 'name4',"
                + "                'lastName': 'lastName4',"
                + "                'position': 'position4',"
                + "                'email': 'email4',"
                + "                'phoneNumber': 'phoneNumber4',"
                + "                'version': 1"
                + "            }"
                + "        ],"
                + "        'address': {"
                + "            'id': 2,"
                + "            'addressLine1': 'addressLine1',"
                + "            'addressLine2': 'addressLine2',"
                + "            'country': 'country',"
                + "            'province': 'province',"
                + "            'city': 'city',"
                + "            'postalCode': 'postalCode'"
                + "        },"
                + "        'version': 2"
                + "    }"
                + "],"
                + "'totalItems': 2,"
                + "'totalPages': 1"
                + "}"));
        
        verify(supplierServiceMock, times(1)).getSuppliers(0, 100, new String[]{"id"}, true);
    }
    
    @Test
    public void testGetSupplier_ReturnsSupplier() throws Exception {
        SupplierEntity supplier = new SupplierEntity(1L, "testName", new ArrayList<>(), new SupplierAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContactEntity(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContactEntity(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));
        
        when(supplierServiceMock.getSupplier(1L)).thenReturn(supplier);
         
        this.mockMvc.perform(get("/api/suppliers/1"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'contacts': ["
                 + "            {"
                 + "                'id': 1,"
                 + "                'firstName': 'name1',"
                 + "                'lastName': 'lastName1',"
                 + "                'position': 'position1',"
                 + "                'email': 'email1',"
                 + "                'phoneNumber': 'phoneNumber1',"
                 + "                'version': 1"
                 + "            },"
                 + "            {"
                 + "                'id': 2,"
                 + "                'firstName': 'name2',"
                 + "                'lastName': 'lastName2',"
                 + "                'position': 'position2',"
                 + "                'email': 'email2',"
                 + "                'phoneNumber': 'phoneNumber2',"
                 + "                'version': 1"
                 + "            }"
                 + "        ],"
                 + "        'address': {"
                 + "            'id': 1,"
                 + "            'addressLine1': 'addressLine1',"
                 + "            'addressLine2': 'addressLine2',"
                 + "            'country': 'country',"
                 + "            'province': 'province',"
                 + "            'city': 'city',"
                 + "            'postalCode': 'postalCode'"
                 + "        },"
                 + "        'version': 1"
                 + "    }"));
         
         verify(supplierServiceMock, times(1)).getSupplier(1L);
    }

    @Test
    public void testAddSupplier_AddsSupplier() throws Exception {
        JSONObject address = new JSONObject();
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("country", "country");
        address.put("province", "province");
        address.put("city", "city");
        address.put("postalCode", "postalCode");
        
        JSONObject contact1 = new JSONObject();
        contact1.put("firstName", "name1");
        contact1.put("lastName", "lastName1");
        contact1.put("position", "position1");
        contact1.put("email", "email1");
        contact1.put("phoneNumber", "phoneNumber1");
        
        JSONObject contact2 = new JSONObject();
        contact2.put("firstName", "name2");
        contact2.put("lastName", "lastName2");
        contact2.put("position", "position2");
        contact2.put("email", "email2");
        contact2.put("phoneNumber", "phoneNumber2");
        
        JSONArray contacts = new JSONArray();
        contacts.put(0, contact1);
        contacts.put(1, contact2);
        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName");        
        payload.put("address", address);        
        payload.put("contacts", contacts);
                     
        SupplierEntity supplier = new SupplierEntity(1L, "testName", new ArrayList<>(), new SupplierAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContactEntity(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContactEntity(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));
        
        when(supplierServiceMock.addSupplier(any(SupplierEntity.class))).thenReturn(supplier);
        
        this.mockMvc.perform(post("/api/suppliers")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'contacts': ["
                 + "            {"
                 + "                'id': 1,"
                 + "                'firstName': 'name1',"
                 + "                'lastName': 'lastName1',"
                 + "                'position': 'position1',"
                 + "                'email': 'email1',"
                 + "                'phoneNumber': 'phoneNumber1',"
                 + "                'version': 1"
                 + "            },"
                 + "            {"
                 + "                'id': 2,"
                 + "                'firstName': 'name2',"
                 + "                'lastName': 'lastName2',"
                 + "                'position': 'position2',"
                 + "                'email': 'email2',"
                 + "                'phoneNumber': 'phoneNumber2',"
                 + "                'version': 1"
                 + "            }"
                 + "        ],"
                 + "        'address': {"
                 + "            'id': 1,"
                 + "            'addressLine1': 'addressLine1',"
                 + "            'addressLine2': 'addressLine2',"
                 + "            'country': 'country',"
                 + "            'province': 'province',"
                 + "            'city': 'city',"
                 + "            'postalCode': 'postalCode'"
                 + "        },"
                 + "        'version': 1"
                 + "    }"));
        
        verify(supplierServiceMock, times(1)).addSupplier(any(SupplierEntity.class));
    }
    
    @Test
    public void testPutSupplier_PutsSupplier() throws Exception {
        JSONObject address = new JSONObject();
        address.put("id", "1");
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("country", "country");
        address.put("province", "province");
        address.put("city", "city");
        address.put("postalCode", "postalCode");
        
        JSONObject contact1 = new JSONObject();
        contact1.put("id", "1");
        contact1.put("firstName", "name1");
        contact1.put("lastName", "lastName1");
        contact1.put("position", "position1");
        contact1.put("email", "email1");
        contact1.put("phoneNumber", "phoneNumber1");
        
        JSONObject contact2 = new JSONObject();
        contact2.put("id", "2");
        contact2.put("firstName", "name2");
        contact2.put("lastName", "lastName2");
        contact2.put("position", "position2");
        contact2.put("email", "email2");
        contact2.put("phoneNumber", "phoneNumber2");
        
        JSONArray contacts = new JSONArray();
        contacts.put(0, contact1);
        contacts.put(1, contact2);
        
        JSONObject payload = new JSONObject();
        payload.put("name", "testName");        
        payload.put("address", address);        
        payload.put("contacts", contacts);
        payload.put("version", "1");
        
        
        SupplierEntity supplier = new SupplierEntity(1L, "testName", new ArrayList<>(), new SupplierAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContactEntity(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContactEntity(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));
        
        when(supplierServiceMock.putSupplier(eq(1L), any(SupplierEntity.class))).thenReturn(supplier);
                                          
        this.mockMvc.perform(put("/api/suppliers/1")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'contacts': ["
                 + "            {"
                 + "                'id': 1,"
                 + "                'firstName': 'name1',"
                 + "                'lastName': 'lastName1',"
                 + "                'position': 'position1',"
                 + "                'email': 'email1',"
                 + "                'phoneNumber': 'phoneNumber1',"
                 + "                'version': 1"
                 + "            },"
                 + "            {"
                 + "                'id': 2,"
                 + "                'firstName': 'name2',"
                 + "                'lastName': 'lastName2',"
                 + "                'position': 'position2',"
                 + "                'email': 'email2',"
                 + "                'phoneNumber': 'phoneNumber2',"
                 + "                'version': 1"
                 + "            }"
                 + "        ],"
                 + "        'address': {"
                 + "            'id': 1,"
                 + "            'addressLine1': 'addressLine1',"
                 + "            'addressLine2': 'addressLine2',"
                 + "            'country': 'country',"
                 + "            'province': 'province',"
                 + "            'city': 'city',"
                 + "            'postalCode': 'postalCode'"
                 + "        },"
                 + "        'version': 1"
                 + "    }"));
        
        verify(supplierServiceMock, times(1)).putSupplier(eq(1L), any(SupplierEntity.class));
    }
    
    @Test
    public void testPatchSupplier_PatchesSupplier() throws Exception {
        JSONObject address = new JSONObject();
        address.put("id", "1");
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("country", "country");
        address.put("province", "province");
        address.put("city", "city");
        address.put("postalCode", "postalCode");
        
        JSONObject payload = new JSONObject();
        payload.put("address", address);        
        payload.put("version", "1");
        
        SupplierEntity supplier = new SupplierEntity(1L, "testName", new ArrayList<>(), new SupplierAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContactEntity(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContactEntity(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));
        
        when(supplierServiceMock.patchSupplier(eq(1L), any(SupplierEntity.class))).thenReturn(supplier);
                                          
        this.mockMvc.perform(patch("/api/suppliers/1")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().json("{"
                 + "        'id': 1,"
                 + "        'name': 'testName',"
                 + "        'contacts': ["
                 + "            {"
                 + "                'id': 1,"
                 + "                'firstName': 'name1',"
                 + "                'lastName': 'lastName1',"
                 + "                'position': 'position1',"
                 + "                'email': 'email1',"
                 + "                'phoneNumber': 'phoneNumber1',"
                 + "                'version': 1"
                 + "            },"
                 + "            {"
                 + "                'id': 2,"
                 + "                'firstName': 'name2',"
                 + "                'lastName': 'lastName2',"
                 + "                'position': 'position2',"
                 + "                'email': 'email2',"
                 + "                'phoneNumber': 'phoneNumber2',"
                 + "                'version': 1"
                 + "            }"
                 + "        ],"
                 + "        'address': {"
                 + "            'id': 1,"
                 + "            'addressLine1': 'addressLine1',"
                 + "            'addressLine2': 'addressLine2',"
                 + "            'country': 'country',"
                 + "            'province': 'province',"
                 + "            'city': 'city',"
                 + "            'postalCode': 'postalCode'"
                 + "        },"
                 + "        'version': 1"
                 + "    }"));
        
        verify(supplierServiceMock, times(1)).patchSupplier(eq(1L), any(SupplierEntity.class));
    }


    @Test
    public void testDeleteSupplier_DeletesSupplier() throws Exception {         
        this.mockMvc.perform(delete("/api/suppliers/1"))
         .andExpect(status().isOk());
         
         verify(supplierServiceMock, times(1)).deleteSupplier(1L);
    }
}
