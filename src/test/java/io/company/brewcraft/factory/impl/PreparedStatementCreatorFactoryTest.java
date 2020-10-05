package io.company.brewcraft.factory.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.PreparedStatementCreator;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.company.brewcraft.factory.IPreparedStatementCreatorFactory;
import io.company.brewcraft.model.Tenant;

public class PreparedStatementCreatorFactoryTest {

    private IPreparedStatementCreatorFactory preparedStatementCreatorFactory;

    private PreparedStatement preparedStatementMock;

    private Connection connectionMock;

    @BeforeEach
    public void init() {
        preparedStatementMock = Mockito.mock(PreparedStatement.class);
        connectionMock = Mockito.mock(Connection.class);
        preparedStatementCreatorFactory = new PreparedStatementCreatorFactory();
    }

    @Test
    public void testCreateForTenant_createsPreparedStatement() throws SQLException {
        Tenant tenant = new Tenant(null, "testName", "testDomain", null);

        when(connectionMock.prepareStatement(any(), any(String[].class))).thenReturn(preparedStatementMock);

        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newCreatorFor(tenant, "testSql");
        preparedStatementCreator.createPreparedStatement(connectionMock);

        verify(connectionMock, times(1)).prepareStatement(eq("testSql"), refEq(new String[] { "id" }));
        verify(preparedStatementMock, times(2)).setString(anyInt(), anyString());
        verify(preparedStatementMock).setString(1, tenant.getName());
        verify(preparedStatementMock).setString(2, tenant.getDomain());
    }
}
