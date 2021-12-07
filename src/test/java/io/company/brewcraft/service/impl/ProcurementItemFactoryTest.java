package io.company.brewcraft.service.impl;

import org.junit.jupiter.api.BeforeEach;

import io.company.brewcraft.service.impl.procurement.ProcurementItemFactory;

public class ProcurementItemFactoryTest {
    private ProcurementItemFactory procurementItemFactory;

    @BeforeEach
    public void init() {
        procurementItemFactory = ProcurementItemFactory.INSTANCE;
    }
}
