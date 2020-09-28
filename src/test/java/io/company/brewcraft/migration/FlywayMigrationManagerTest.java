package io.company.brewcraft.migration;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.sql.Connection;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.security.store.SecretsManager;

@ExtendWith(MockitoExtension.class)
public class FlywayMigrationManagerTest {

	@Mock
	private DataSource dataSourceMock;

	@Mock
	private DataSourceManager dataSourceManagerMock;

	@Mock
	private JdbcDialect jdbcDialectMock;

	@Mock
	private Connection connectionMock;

	@Mock
	private SecretsManager secretsManagerMock;

	@MockBean
	private Flyway flywayMock;

	@InjectMocks
	private FlywayMigrationManager flywayMigrationManager;

	private final String masterSchemaName = "brewcraft_master";

	private final String tenantSchemaPrefix = "brewcraft_tenant_";

	private final String masterMigrationsPath = "db/master_migrations";

	private final String tenantMigrationsPath = "db/tenant_migrations";

	@BeforeEach
	public void init() {
		ReflectionTestUtils.setField(flywayMigrationManager, "masterSchemaName", "brewcraft_master");
		ReflectionTestUtils.setField(flywayMigrationManager, "tenantSchemaPrefix", "brewcraft_tenant_");
		ReflectionTestUtils.setField(flywayMigrationManager, "masterMigrationsPath", "db/master_migrations");
		ReflectionTestUtils.setField(flywayMigrationManager, "tenantMigrationsPath", "db/tenant_migrations");
	}

	@Test
	public void migrate_doesNotCreateUserIfUserAlreadyExists() throws Exception {
		String testTenantId = "test-tenant-id";

		when(jdbcDialectMock.userExists(any(), eq(tenantSchemaPrefix + testTenantId))).thenReturn(true);
		when(dataSourceMock.getConnection()).thenReturn(connectionMock);
		when(dataSourceManagerMock.getDataSource(testTenantId)).thenReturn(dataSourceMock);

		Assertions.assertThrows(Exception.class, () -> {
			flywayMigrationManager.migrate(testTenantId);
		});

		verify(jdbcDialectMock, times(0)).createUser(any(), any(), any());
		verify(jdbcDialectMock, times(0)).grantPrivilege(any(), any(), any(), any(), any());
		verify(secretsManagerMock, times(0)).storeSecret(any(), any());
	}

	@Test
	public void migrate_createsUserIfUserDoesNotExist() throws Exception {
		String testTenantId = "test-tenant-id";

		when(jdbcDialectMock.userExists(any(), eq(tenantSchemaPrefix + testTenantId))).thenReturn(false);
		when(dataSourceMock.getConnection()).thenReturn(connectionMock);
		when(dataSourceManagerMock.getDataSource(testTenantId)).thenReturn(dataSourceMock);

		Assertions.assertThrows(Exception.class, () -> {
			flywayMigrationManager.migrate(testTenantId);
		});

		verify(jdbcDialectMock, times(1)).createUser(any(), eq(tenantSchemaPrefix + testTenantId), any());
		verify(jdbcDialectMock, times(1)).grantPrivilege(any(), eq("CONNECT"), eq("DATABASE"), eq("postgres"),
				eq(tenantSchemaPrefix + testTenantId));
		verify(jdbcDialectMock, times(1)).grantPrivilege(any(), eq("CREATE"), eq("DATABASE"), eq("postgres"),
				eq(tenantSchemaPrefix + testTenantId));
		verify(secretsManagerMock, times(1)).storeSecret(eq(tenantSchemaPrefix + testTenantId), any());
	}

}
