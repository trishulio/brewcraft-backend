package io.company.brewcraft.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import io.company.brewcraft.model.Tenant;

public class TenantDaoRowMapper implements RowMapper<Tenant> {

    @Override
    public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tenant tenant = new Tenant();
        tenant.setId(rs.getObject(1, java.util.UUID.class));
        tenant.setName(rs.getString(2));
        tenant.setUrl(rs.getString(3));
        tenant.setCreated(rs.getObject(4, LocalDateTime.class));

        return tenant;
    }
}
