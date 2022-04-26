package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import io.company.brewcraft.service.impl.TenantService;

@SpringBootTest
@ActiveProfiles("test")
class BrewcraftApplicationTests {
    private static Logger log = LoggerFactory.getLogger(BrewcraftApplicationTests.class);

    @MockBean
    private TenantService tenantService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAutoCommitIsSetToFalse(@Autowired DataSource ds) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            log.debug("Database Product Name: {}", conn.getMetaData().getDatabaseProductName());
            log.debug("Database Username: {}", conn.getMetaData().getUserName());
            assertFalse(conn.getAutoCommit());
        }
    }
}
