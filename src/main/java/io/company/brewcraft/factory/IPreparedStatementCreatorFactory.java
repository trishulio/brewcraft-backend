package io.company.brewcraft.factory;

import org.springframework.jdbc.core.PreparedStatementCreator;

import io.company.brewcraft.model.Tenant;

public interface IPreparedStatementCreatorFactory {
    /*
     * PreparedStatementCreator for inserting into tenant table
     */
    public PreparedStatementCreator newCreatorFor(Tenant tenant, String sql);
}
