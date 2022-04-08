package io.company.brewcraft.repository;

import java.util.SortedSet;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class RepositoryUtil {
    public static PageRequest pageRequest(SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Sort sortBy = Sort.unsorted();
        if (sort != null && sort.size() > 0) {
            sortBy = Sort.by(orderAscending ? Direction.ASC : Direction.DESC, sort.toArray(new String[sort.size()]));
        }

        return PageRequest.of(page, size, sortBy);
    }
}
