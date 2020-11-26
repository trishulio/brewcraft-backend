package io.company.brewcraft.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class RepositoryUtil {

    public static PageRequest pageRequest(List<String> orderBys, boolean ascending, int page, int size) {
        Sort sort = Sort.unsorted();
        if (orderBys != null && orderBys.size() > 0) {
            sort = Sort.by(ascending ? Direction.ASC : Direction.DESC, orderBys.toArray(new String[orderBys.size()]));
        }

        return PageRequest.of(page, size, sort);
    }
}
