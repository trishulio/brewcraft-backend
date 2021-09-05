package io.company.brewcraft.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.TreeSet;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.repository.AggregationRepository;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.AggregationService;
import io.company.brewcraft.service.NullAggregation;
import io.company.brewcraft.service.PathProvider;
import io.company.brewcraft.service.Selector;

public class AggregationServiceTest {
    public static class TestEntity extends BaseModel {
        private Long id;

        public TestEntity(Long id) {
            this.id = id;
        }
    }

    class TestPathProvider implements PathProvider {
        private String[] path;

        public TestPathProvider(String field) {
            path = new String[] { field };
        }

        @Override
        public String[] getPath() {
            return path;
        }

    }

    private AggregationService service;

    private AggregationRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(AggregationRepository.class);
        service = new AggregationService(mRepo);
    }

    @Test
    public void getAggregations_ReturnsPageOfContentAndTotalCount() {
        Specification<TestEntity> mSpec = mock(Specification.class);

        Selector selector = new Selector().select("col_1").select("col_2").select(AggregationFunction.SUM.getAggregation("col_3"));
        Selector groupBy = new Selector().select("col_1").select("col_2");

        doReturn(List.of(new TestEntity(1L))).when(mRepo).getAggregation(TestEntity.class, selector, groupBy, mSpec, PageRequest.of(1, 10, Direction.ASC, "col_1"));
        doReturn(99L).when(mRepo).getResultCount(TestEntity.class, new Selector().select(new NullAggregation()), groupBy, mSpec, null);

        Page<TestEntity> page = this.service.getAggregation(TestEntity.class, mSpec, AggregationFunction.SUM, new TestPathProvider("col_3"), new PathProvider[] { new TestPathProvider("col_1"), new TestPathProvider("col_2") },new TreeSet<>(List.of("col_1")), true, 1, 10);

        Page<TestEntity> expected = new PageImpl<>(List.of(new TestEntity(1L)), PageRequest.of(1, 10, Direction.ASC, "col_1"), 99L);

        assertEquals(expected, page);
    }

    @Test
    public void getResultCount_ReturnsCountOfRepoCount() {
        Specification<TestEntity> mSpec = mock(Specification.class);
        Selector groupBy = new Selector().select("col_1").select("col_2");

        doReturn(100L).when(mRepo).getResultCount(TestEntity.class, new Selector().select(new NullAggregation()), groupBy, mSpec, null);

        Long total = this.service.getResultCount(TestEntity.class, groupBy, mSpec);

        assertEquals(100L, total);
    }
}
