package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.service.SupplierContactService;

@WebMvcTest(SupplierContactController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class SupplierContactControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContextHolder contextHolderMock;

    @MockBean
    private SupplierContactService supplierContactServiceMock;
    
    @MockBean
    private SupplierRepository supplierRepositoryMock;

    @Test
    public void testGetAll_ReturnsListOfAllSuppliers() throws Exception {
       Supplier supplier1 = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       Supplier supplier2 = new Supplier(2L, "testName2", new ArrayList<>(), new SupplierAddress(2L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);

       SupplierContact contact1 = new SupplierContact(1L, supplier1, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1);
       SupplierContact contact2 = new SupplierContact(2L, supplier2, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1);
       
       List<SupplierContact> contacts = new ArrayList<>();
       contacts.add(contact1);
       contacts.add(contact2);
       
       Page<SupplierContact> pagedResponse = new PageImpl<>(contacts);
        
       when(supplierContactServiceMock.getSupplierContacts(0, 100, new String[]{"id"}, true)).thenReturn(pagedResponse);
        
       this.mockMvc.perform(get("/api/suppliers/contacts").header("Authorization", "Bearer " + "test"))
       .andExpect(status().isOk())
       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
       .andExpect(content().json(
               "{"
               + "'supplierContacts' : ["
               + "   {"
               + "      'id': 1,"
               + "      'firstName': 'name1',"
               + "      'lastName': 'lastName1',"
               + "      'position': 'position1',"
               + "      'email': 'email1',"
               + "      'phoneNumber': 'phoneNumber1',"
               + "      'supplier' : {"
               + "        'id': 1,"
               + "        'name': 'testName',"
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
               + "      },"
               + "      'version': 1"
               + "   },"
               + "   {"
               + "      'id': 2,"
               + "      'firstName': 'name2',"
               + "      'lastName': 'lastName2',"
               + "      'position': 'position2',"
               + "      'email': 'email2',"
               + "      'phoneNumber': 'phoneNumber2',"
               + "      'supplier' : {"
               + "        'id': 2,"
               + "        'name': 'testName2',"
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
               + "      },"
               + "      'version': 1"
               + "    }"
               + "],"
               + "'totalItems': 2,"
               + "'totalPages': 1"
               + "}"));
       
        
        verify(supplierContactServiceMock, times(1)).getSupplierContacts(0, 100, new String[]{"id"}, true);
    } 
    
    @Test
    public void testGetContact_ReturnsContact() throws Exception {
        SupplierContact supplierContact = new SupplierContact(2L, null, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1);
        
        when(supplierContactServiceMock.getContact(2L)).thenReturn(supplierContact);
         
        this.mockMvc.perform(get("/api/suppliers/contacts/2"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(content().json(
                 "{"
                 + "  'id': 2,"
                 + "  'firstName': 'name1',"
                 + "  'lastName': 'lastName1',"
                 + "  'position': 'position1',"
                 + "  'email': 'email1',"
                 + "  'phoneNumber': 'phoneNumber1',"
                 + "  'version': 1"
                 + " }"));
         
         verify(supplierContactServiceMock, times(1)).getContact(2L);
    }

    @Test
    public void testAddContact_AddsContact() throws Exception {       
        JSONObject payload = new JSONObject();
        payload.put("firstName", "name1");
        payload.put("lastName", "lastName1");
        payload.put("position", "position1");
        payload.put("email", "email1");
        payload.put("phoneNumber", "phoneNumber1");
        
        SupplierContact supplierContact = new SupplierContact(2L, new Supplier(), "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1);
        
        when(supplierContactServiceMock.addContact(eq(1L), any(SupplierContact.class))).thenReturn(supplierContact);
        
        this.mockMvc.perform(post("/api/suppliers/1/contacts/")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isCreated())
         .andExpect(content().json(
                 "{"
                 + "  'id': 2,"
                 + "  'firstName': 'name1',"
                 + "  'lastName': 'lastName1',"
                 + "  'position': 'position1',"
                 + "  'email': 'email1',"
                 + "  'phoneNumber': 'phoneNumber1',"
                 + "  'version': 1"
                 + " }"));
        
        verify(supplierContactServiceMock, times(1)).addContact(eq(1L), any(SupplierContact.class));
    }
    
    @Test
    public void testPutContact_PutsContact() throws Exception {        
        JSONObject payload = new JSONObject();
        payload.put("firstName", "name1");
        payload.put("lastName", "lastName1");
        payload.put("position", "position1");
        payload.put("email", "email1");
        payload.put("phoneNumber", "phoneNumber1");
        payload.put("version", "1");
        
        SupplierContact supplierContact = new SupplierContact(2L, new Supplier(), "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1);
                     
        when(supplierContactServiceMock.putContact(eq(1L), eq(2L), any(SupplierContact.class))).thenReturn(supplierContact);
             
        this.mockMvc.perform(put("/api/suppliers/1/contacts/2")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().json(
                 "{"
                 + "  'id': 2,"
                 + "  'firstName': 'name1',"
                 + "  'lastName': 'lastName1',"
                 + "  'position': 'position1',"
                 + "  'email': 'email1',"
                 + "  'phoneNumber': 'phoneNumber1',"
                 + "  'version': 1"
                 + " }"));
               
        verify(supplierContactServiceMock, times(1)).putContact(eq(1L), eq(2L), any(SupplierContact.class));
    }
    
    @Test
    public void testPatchContact_PatchesContact() throws Exception {        
        JSONObject payload = new JSONObject();
        payload.put("firstName", "name1");
        payload.put("lastName", "lastName1");
        payload.put("position", "position1");
        payload.put("email", "email1");
        payload.put("phoneNumber", "phoneNumber1");
        payload.put("version", "1");
        
        
        SupplierContact supplierContact = new SupplierContact(2L, new Supplier(), "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1);
                     
        when(supplierContactServiceMock.patchContact(eq(2L), any(SupplierContact.class))).thenReturn(supplierContact);
             
        this.mockMvc.perform(patch("/api/suppliers/contacts/2")
         .contentType(MediaType.APPLICATION_JSON)
         .content(payload.toString()))
         .andExpect(status().isOk())
         .andExpect(content().json(
                 "{"
                 + "  'id': 2,"
                 + "  'firstName': 'name1',"
                 + "  'lastName': 'lastName1',"
                 + "  'position': 'position1',"
                 + "  'email': 'email1',"
                 + "  'phoneNumber': 'phoneNumber1',"
                 + "  'version': 1"
                 + " }"));
        
        
        verify(supplierContactServiceMock, times(1)).patchContact(eq(2L), any(SupplierContact.class));
    }

    @Test
    public void testDeleteContact_DeletesContact() throws Exception {         
        this.mockMvc.perform(delete("/api/suppliers/1/contacts/2"))
         .andExpect(status().isOk());
         
         verify(supplierContactServiceMock, times(1)).deleteContact(2L);
    }
    
}
