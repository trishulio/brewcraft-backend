package io.company.brewcraft.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import io.company.brewcraft.factory.impl.GeneratedKeyHolderFactory;
import io.company.brewcraft.factory.impl.PreparedStatementCreatorFactory;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.rowmapper.TenantDaoRowMapper;

public class TenantRepositoryTest {

    private TenantRepositoryImpl tenantRepository;

    private TenantDaoRowMapper mapperMock;

    private JdbcTemplate jdbcTemplateMock;

    private PreparedStatementCreatorFactory preparedStatementCreatorFactoryMock;

    private PreparedStatementCreator preparedStatementCreatorMock;

    private GeneratedKeyHolderFactory keyHolderFactoryMock;

    private KeyHolder keyHolderMock;

    @BeforeEach
    public void init() {
        mapperMock = mock(TenantDaoRowMapper.class);
        jdbcTemplateMock = mock(JdbcTemplate.class);
        preparedStatementCreatorFactoryMock = mock(PreparedStatementCreatorFactory.class);
        preparedStatementCreatorMock = mock(PreparedStatementCreator.class);
        keyHolderFactoryMock = mock(GeneratedKeyHolderFactory.class);
        keyHolderMock = mock(KeyHolder.class);
        tenantRepository = new TenantRepositoryImpl(jdbcTemplateMock, mapperMock, preparedStatementCreatorFactoryMock, keyHolderFactoryMock);
    }

    @Test
    public void testFindAll() {
        List<Tenant> expectedTenants = new ArrayList<Tenant>();
        String findAllSql = "SELECT * FROM TENANT";

        when(keyHolderFactoryMock.newKeyHolder()).thenReturn(keyHolderMock);
        when(jdbcTemplateMock.query(anyString(), any(TenantDaoRowMapper.class))).thenReturn(expectedTenants);

        List<Tenant> actualTenants = tenantRepository.findAll();

        verify(jdbcTemplateMock, times(1)).query(findAllSql, mapperMock);
        assertSame(expectedTenants, actualTenants);
    }

    @Test
    public void testSave() {
        Tenant tenant = new Tenant(null, "testName", "testDomain", null);
        String insertSql = "INSERT INTO TENANT (NAME, DOMAIN) VALUES (?, ?)";
        UUID uuid = UUID.randomUUID();
        Object expectedId = uuid;

        Map<String, Object> map = new HashMap<>();
        map.put("id", expectedId);

        when(preparedStatementCreatorFactoryMock.newCreatorFor(tenant, insertSql)).thenReturn(preparedStatementCreatorMock);
        when(keyHolderFactoryMock.newKeyHolder()).thenReturn(keyHolderMock);
        when(jdbcTemplateMock.update(any(PreparedStatementCreator.class), any())).thenReturn(1);
        when(keyHolderMock.getKeys()).thenReturn(map);

        UUID actualId = tenantRepository.save(tenant);

        verify(preparedStatementCreatorFactoryMock, times(1)).newCreatorFor(tenant, insertSql);
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testFindById() {
        Tenant expectedTenant = new Tenant();
        String findByIdSql = "SELECT * FROM TENANT WHERE ID = ?";

        when(jdbcTemplateMock.queryForObject(eq(findByIdSql), refEq(new Object[] { "id" }), eq(mapperMock)))
                .thenReturn(expectedTenant);
        Tenant actualTenant = tenantRepository.findById(UUID.randomUUID());

        verify(jdbcTemplateMock, times(1)).queryForObject(eq(findByIdSql), refEq(new Object[] { "id" }),
                eq(mapperMock));
        assertSame(expectedTenant, actualTenant);
    }

    @Test
    public void testDeleteById() {
        UUID id = UUID.randomUUID();
        String deleteByIdSql = "DELETE FROM TENANT WHERE ID = ?";

        tenantRepository.deleteById(id);

        verify(jdbcTemplateMock, times(1)).update(deleteByIdSql, id);
    }
}
