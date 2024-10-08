package io.company.brewcraft.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.CriteriaSpec;

public class ColumnSpecAccumulator extends BaseModel {
    private List<CriteriaSpec<?>> aggregations;

    public ColumnSpecAccumulator() {
        this.aggregations = new ArrayList<>();
    }

    public void add(CriteriaSpec<?> spec) {
        if (spec != null) {
            this.aggregations.add(spec);
        }
    }

    public <T extends Selection<?>> List<T> getColumns(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        @SuppressWarnings("unchecked")
        List<T> columns = this.aggregations.stream()
                                           .map(spec -> (T) spec.getExpression(root, cq, cb))
                                           .toList();
        return columns;
    }
}
