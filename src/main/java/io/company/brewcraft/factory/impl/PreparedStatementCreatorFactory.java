package io.company.brewcraft.factory.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

import io.company.brewcraft.factory.IPreparedStatementCreatorFactory;
import io.company.brewcraft.model.Tenant;

public class PreparedStatementCreatorFactory implements IPreparedStatementCreatorFactory {

    public PreparedStatementCreator newCreatorFor(Tenant tenant, String sql) {
        return new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
                ps.setString(1, tenant.getName());
                ps.setString(2, tenant.getUrl());
                return ps;
            }
        };
    }

}
