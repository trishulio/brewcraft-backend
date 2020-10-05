package io.company.brewcraft.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import io.company.brewcraft.factory.impl.GeneratedKeyHolderFactory;
import io.company.brewcraft.factory.impl.PreparedStatementCreatorFactory;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.impl.TenantRepositoryImpl;
import io.company.brewcraft.rowmapper.TenantDaoRowMapper;

@Configuration
public class RepositoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TenantRepositoryImpl.class)
    public TenantRepository tenantRepository(JdbcTemplate jdbcTemplate) {
        TenantRepository tenantRepository = new TenantRepositoryImpl(jdbcTemplate, new TenantDaoRowMapper(),
                new PreparedStatementCreatorFactory(), new GeneratedKeyHolderFactory());
        return tenantRepository;
    }
}
