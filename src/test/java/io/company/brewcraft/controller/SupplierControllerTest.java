package io.company.brewcraft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.security.session.ThreadLocalContextHolder;
import io.company.brewcraft.service.IaasRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.impl.TenantService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.controller.AttributeFilter;

@WebMvcTest(SupplierController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class SupplierControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThreadLocalContextHolder contextHolderMock;

    @MockBean
    private IaasRepository<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> iaasRepo;

    @MockBean
    private TenantService tenantMgmtServiceMock;

    @MockBean
    public UtilityProvider utilityProvider;

    @MockBean
    private AttributeFilter filter;

    @MockBean
    private SupplierService supplierServiceMock;

    @Test
    public void testGetAll_ReturnsListOfAllSuppliers() throws Exception {
       Supplier supplier1 = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       supplier1.getContacts().add(new SupplierContact(1L, supplier1, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
       supplier1.getContacts().add(new SupplierContact(2L, supplier1, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));

       Supplier supplier2 = new Supplier(2L, "testName2", new ArrayList<>(), new SupplierAddress(2L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2021, 2, 3, 4, 5), LocalDateTime.of(2021, 2, 3, 4, 5), 2);
       supplier2.getContacts().add(new SupplierContact(3L, supplier2, "name3", "lastName3", "position3", "email3", "phoneNumber3", null, null, 1));
       supplier2.getContacts().add(new SupplierContact(4L, supplier2, "name4", "lastName4", "position4", "email4", "phoneNumber4", null, null, 1));

       List<Supplier> suppliers = new ArrayList<>();
       suppliers.add(supplier1);
       suppliers.add(supplier2);

       Page<Supplier> pagedResponse = new PageImpl<>(suppliers);

       when(supplierServiceMock.getSuppliers(0, 100, new TreeSet<>(List.of("id")), true)).thenReturn(pagedResponse);

       this.mockMvc.perform(get("/api/v1/suppliers").header("Authorization", "Bearer " + "test"))
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

        verify(supplierServiceMock, times(1)).getSuppliers(0, 100, new TreeSet<>(List.of("id")), true);
    }

    @Test
    public void testGetSupplier_ReturnsSupplier() throws Exception {
        Supplier supplier = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContact(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContact(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));

        when(supplierServiceMock.getSupplier(1L)).thenReturn(supplier);

        this.mockMvc.perform(get("/api/v1/suppliers/1"))
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

        Supplier supplier = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContact(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContact(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));

        when(supplierServiceMock.addSupplier(any(Supplier.class))).thenReturn(supplier);

        this.mockMvc.perform(post("/api/v1/suppliers")
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

        verify(supplierServiceMock, times(1)).addSupplier(any(Supplier.class));
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

        Supplier supplier = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContact(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContact(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));

        when(supplierServiceMock.putSupplier(eq(1L), any(Supplier.class))).thenReturn(supplier);

        this.mockMvc.perform(put("/api/v1/suppliers/1")
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

        Supplier supplier = new Supplier(1L, "testName", new ArrayList<>(), new SupplierAddress(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        supplier.getContacts().add(new SupplierContact(1L, supplier, "name1", "lastName1", "position1", "email1", "phoneNumber1", null, null, 1));
        supplier.getContacts().add(new SupplierContact(2L, supplier, "name2", "lastName2", "position2", "email2", "phoneNumber2", null, null, 1));

        when(supplierServiceMock.patchSupplier(eq(1L), any(Supplier.class))).thenReturn(supplier);

        this.mockMvc.perform(patch("/api/v1/suppliers/1")
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
    }

    @Test
    public void testDeleteSupplier_DeletesSupplier() throws Exception {
        this.mockMvc.perform(delete("/api/v1/suppliers/1"))
         .andExpect(status().isOk());

         verify(supplierServiceMock, times(1)).deleteSupplier(1L);
    }
}
