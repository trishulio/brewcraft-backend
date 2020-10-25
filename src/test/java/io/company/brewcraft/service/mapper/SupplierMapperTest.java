package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;

public class SupplierMapperTest {

    @Test
    public void afterSupplierDtoToSupplier_SetsSupplierForAllContactsTest() {
        Supplier supplier = new Supplier(1L, "Supplier 1", Arrays.asList(new SupplierContact(), new SupplierContact()), null, null, null, null);
        
        SupplierMapper.INSTANCE.afterSupplierDtoToSupplier(null, supplier);
        
        assertSame(supplier, supplier.getContacts().get(0).getSupplier());
        assertSame(supplier, supplier.getContacts().get(1).getSupplier());
    }
}
