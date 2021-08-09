////package io.company.brewcraft.controller;
////
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
////
////import org.junit.jupiter.api.Disabled;
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.http.MediaType;
////import org.springframework.test.context.ActiveProfiles;
////import org.springframework.test.context.ContextConfiguration;
////import org.springframework.test.web.servlet.MockMvc;
////
////import io.company.brewcraft.configuration.InvoiceConfiguration;
////
//////@Transactional
//////@AutoConfigureDataJpa
//////@AutoConfigureTestDatabase
//////@AutoConfigureTestEntityManager
////
////@WebMvcTest(InvoiceController.class)
////@AutoConfigureMockMvc(addFilters = false)
////@ActiveProfiles("test")
////
////@ContextConfiguration(classes = { InvoiceConfiguration.class })
//////@EnableAutoConfiguration
////public class InvoiceIntegrationTest {
////
////    @Autowired
////    private MockMvc mvc;
////
////    @Test
////    @Disabled
////    public void test() throws Exception {
////
//////        mvc.perform(
//////            get("/v1/invoices/123")
//////            .header("Authorization", "Bearer test")
//////            .contentType(MediaType.APPLICATION_JSON_VALUE)
//////        )
//////        .andExpect(status().isNotFound());
////
//////        mvc.perform(
//////                put("/v1/invoices/123")
//////                .header("Authorization", "Bearer test")
//////                .contentType(MediaType.APPLICATION_JSON_VALUE)
//////                .content("")
//////            )
//////            .andExpect(status().isAccepted())
//////            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//////            .andExpect(content().json("{}"));
////
////        mvc.perform(
////                get("/v1/invoices/")
////                .header("Authorization", "Bearer test")
////                .contentType(MediaType.APPLICATION_JSON_VALUE)
////        )
////        .andExpect(status().isCreated());
////
//////        mvc.perform(
//////            post("/v1/invoices/")
//////            .header("Authorization", "Bearer test")
//////            .contentType(MediaType.APPLICATION_JSON_VALUE)
//////            .content("")
//////        )
//////        .andExpect(status().isCreated())
//////        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//////        .andExpect(content().json("{}"));
////    }
////
////}
//
//package io.company.brewcraft.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import io.company.brewcraft.configuration.InvoiceConfiguration;
//import io.company.brewcraft.security.session.ContextHolder;
//
//@WebMvcTest(InvoiceController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//
//@Transactional
//@AutoConfigureDataJpa
//@AutoConfigureTestDatabase
//@AutoConfigureTestEntityManager
//
//@ContextConfiguration(classes = { InvoiceConfiguration.class })
////@EnableAutoConfiguration
//public class InvoiceIntegrationTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private InvoiceController controller;
//
//    @MockBean
//    private ContextHolder mContextHolder;
//
//    @Test
//    public void testGetAll_ReturnsListOfAllSuppliers() throws Exception {
//        assertNotNull(controller);
//    }
//}
