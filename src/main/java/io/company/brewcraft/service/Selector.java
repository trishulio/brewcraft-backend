package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import io.company.brewcraft.model.BaseModel;

public class Selector extends BaseModel {
    private List<CriteriaSpec<?>> aggregations;

    public Selector() {
        this.aggregations = new ArrayList<>();
    }

    public Selector select(PathProvider provider) {
        return select(provider.getPath());
    }

    public Selector select(String... path) {
        return select(new PathSpec<>(path));
    }

    public Selector select(CriteriaSpec<?> aggr) {
        this.aggregations.add(aggr);
        return this;
    }

    public List<Selection<?>> getSelection(Root<?> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Selection<?>> selection = this.aggregations.stream()
                                                        .map(aggr -> aggr.getExpression(root, cq, cb))
                                                        .collect(Collectors.toList());
        return selection;
    }
}
