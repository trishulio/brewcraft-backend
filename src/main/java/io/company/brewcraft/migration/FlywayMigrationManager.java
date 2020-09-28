package io.company.brewcraft.migration;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.company.brewcraft.data.DataSourceManager;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.security.store.SecretsManager;

@Component
@ConditionalOnMissingClass("org.springframework.boot.test.context.SpringBootTest")
public class FlywayMigrationManager implements MigrationManager {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private DataSourceManager dataSourceManager;

	@Autowired
	private JdbcDialect jdbcDialect;

	@Autowired
	private SecretsManager secretsManager;
	
	private Flyway flyway;

	@Value("${brewcraft.db.masterSchemaName:brewcraft_master}")
	private String masterSchemaName;

	@Value("${brewcraft.db.tenantSchemaPrefix:brewcraft_tenant_}")
	private String tenantSchemaPrefix;

	@Value("${brewcraft.db.masterMigrationPath:db/master_migrations}")
	private String masterMigrationsPath;

	@Value("${brewcraft.db.tenantMigrationsPath:db/tenant_migrations}")
	private String tenantMigrationsPath;

	private static final Logger logger = LoggerFactory.getLogger(FlywayMigrationManager.class);

	@PostConstruct
	public void migrateAll() {
		logger.debug("Executing master schema migration");

		flyway = Flyway.configure().locations(masterMigrationsPath).schemas(masterSchemaName)
				.dataSource(dataSource).load();

		try {
			flyway.migrate();
		} catch (FlywayException e) {
			logger.error("Error Executing master schema migration", e);
			throw new RuntimeException("master schema migration failed!");
		}

		logger.debug("Master schema migration complete");

		// TODO: Fetch actual list of tenants from master schema
		List<String> testTenants = new ArrayList<String>();
		testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab67");
		testTenants.add("eae07f11_4c9a_4a3b_8b23_9c05d695ab68");

		for (String tenant : testTenants) {
			this.migrate(tenant);
		}
	}

	public void migrate(String tenantId) {
		if (StringUtils.hasLength(tenantId)) {
			try {
				boolean userExists = jdbcDialect.userExists(dataSource.getConnection(), tenantSchemaPrefix + tenantId);

				if (!userExists) {
					String randomPassword = generatePassword();

					Connection conn = dataSource.getConnection();
					jdbcDialect.createUser(conn, tenantSchemaPrefix + tenantId, randomPassword);
					jdbcDialect.grantPrivilege(conn, "CONNECT", "DATABASE", "postgres", tenantSchemaPrefix + tenantId);
					jdbcDialect.grantPrivilege(conn, "CREATE", "DATABASE", "postgres", tenantSchemaPrefix + tenantId);
					conn.close();

					secretsManager.storeSecret(tenantSchemaPrefix + tenantId, randomPassword);
				}

				DataSource tenantDataSource = dataSourceManager.getDataSource(tenantId);

				logger.debug("Executing tenant schema migration for tenant: {}", tenantId);

				flyway = Flyway.configure().locations(tenantMigrationsPath)
						.schemas(tenantSchemaPrefix + tenantId).dataSource(tenantDataSource).load();

				flyway.migrate();

				logger.debug("Tenant schema migration complete for tenant: {}", tenantId);

			} catch (Exception e) {
				logger.error("Error Executing tenant schema migration for tenant: {}", tenantId, e);
				throw new RuntimeException("tenant migration failed!");
			}
		}
	}

	/*
	 * Temporary password generator
	 */
	private String generatePassword() {
		String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$%^&*_=+-/.?<>)";

		String values = capitalChars + lowercaseChars + numbers + symbols;

		Random random = new Random();
		int length = 12;

		char[] password = new char[length];
		for (int i = 0; i < length; i++) {
			password[i] = values.charAt(random.nextInt(values.length()));
		}

		return password.toString();
	}

}