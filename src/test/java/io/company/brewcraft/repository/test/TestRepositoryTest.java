// package io.company.brewcraft.repository.test;

// import static org.junit.jupiter.api.Assertions.*;

// import java.math.BigDecimal;
// import java.sql.SQLException;

// import javax.sql.DataSource;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.test.context.ActiveProfiles;

// import io.company.brewcraft.migration.MigrationManager;
// import tec.uom.se.quantity.Quantities;
// import io.company.brewcraft.utils.SupportedUnits;

// @DataJpaTest
// @EnableJpaRepositories
// @ActiveProfiles("test")
// public class TestRepositoryTest {
    
//     @MockBean // Mocking migration manager to avoid running the PostConstuct during the test.
//     private MigrationManager migrationMgr;

//     @Autowired
//     private TestRepository repo;
    
//     @Autowired
//     private DataSource ds;
    
//     @Test
//     public void testEverything() throws SQLException {
        
//         ds.getConnection().prepareStatement("TRUNCATE SCHEMA public AND COMMIT").executeUpdate();
//         ds.getConnection().prepareStatement("DROP TABLE QTY_UNIT;").executeUpdate();

//         ds.getConnection().prepareStatement(
//                 "CREATE TABLE QTY_UNIT ("
//                 + "    SYMBOL VARCHAR(4) PRIMARY KEY,"
//                 + "    NAME VARCHAR(255)"
//                 + ");").executeUpdate();
        
//         ds.getConnection().prepareStatement(
//                 "CREATE TABLE QTY ("
//                 + "    ID INTEGER PRIMARY KEY,"
//                 + "    UNIT_SYMBOL VARCHAR(4) REFERENCES QTY_UNIT(SYMBOL),"
//                 + "    VALUE NUMERIC(20, 4)"
//                 + ");"
//         ).executeUpdate();
        

//         ds.getConnection().prepareStatement("CREATE TABLE TEST_ENTITY (ID INTEGER PRIMARY KEY, QTY_ID INTEGER REFERENCES QTY(ID));").executeUpdate();

//         ds.getConnection().prepareStatement(
//                 "INSERT INTO QTY_UNIT VALUES"
//                 + "    ('g', 'Gram'),"
//                 + "    ('kg', 'Kilogram'),"
//                 + "    ('l', 'Litre'),"
//                 + "    ('hl', 'Hectolitre'),"
//                 + "    ('each', 'Each');"
//               ).executeUpdate();

        
        
//         TestEntity entity = new TestEntity();
        
//         entity.setId(1L);
//         entity.setQuantity(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM));
        
//         repo.save(entity);
        
//         entity = repo.findById(1L).get();
        
//         assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), entity.getQuantity());
//     }
// }
