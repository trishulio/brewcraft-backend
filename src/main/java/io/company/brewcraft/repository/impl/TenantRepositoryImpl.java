package io.company.brewcraft.repository.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import io.company.brewcraft.factory.KeyHolderFactory;
import io.company.brewcraft.factory.IPreparedStatementCreatorFactory;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.rowmapper.TenantDaoRowMapper;

public class TenantRepositoryImpl implements TenantRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM TENANT";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM TENANT WHERE ID = ?";

    private static final String SAVE_QUERY = "INSERT INTO TENANT (NAME, DOMAIN) VALUES (?, ?)";

    private static final String DELETE_BY_ID_QUERY = "DELETE FROM TENANT WHERE ID = ?";

    private TenantDaoRowMapper mapper;

    private JdbcTemplate jdbcTemplate;

    private IPreparedStatementCreatorFactory preparedStatementCreatorFactory;

    private KeyHolderFactory keyHolderFactory;

    public TenantRepositoryImpl(JdbcTemplate jdbcTemplate, TenantDaoRowMapper mapper,
            IPreparedStatementCreatorFactory statementCreatorFactory, KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.preparedStatementCreatorFactory = statementCreatorFactory;
        this.keyHolderFactory = keyHolderFactory;
    }

    public List<Tenant> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, mapper);
    }

    public UUID save(Tenant tenant) {
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newCreatorFor(tenant, SAVE_QUERY);
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (UUID) keyHolder.getKeys().get("id");
    }

    public Tenant findById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new Object[] { id }, mapper);
    }

    public int deleteById(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }
}
