package io.company.brewcraft.configuration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.repository.MaterialLotAggregationRepository;

public class RepositoryConfigurationTest {

    private RepositoryConfiguration repoConf;

    @BeforeEach
    public void init() {
        repoConf = new RepositoryConfiguration();
    }

    @Test
    public void testAggrRepo_ReturnsInstanceOfAggregationRepository() {
        EntityManager mEm = mock(EntityManager.class);
        AggregationRepository repo = repoConf.aggrRepo(mEm);

        assertSame(AggregationRepository.class, repo.getClass());
    }

    @Test
    public void testLotAggrRepo_ReturnsInstanceOfMaterialLotAggregationRepository() {
        AggregationRepository mAggrRepo = mock(AggregationRepository.class);
        MaterialLotAggregationRepository repo = repoConf.lotAggrRepo(mAggrRepo);

        assertSame(MaterialLotAggregationRepository.class, repo.getClass());
    }
}
