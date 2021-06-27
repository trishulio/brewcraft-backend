package io.company.brewcraft.repository;

import java.util.SortedSet;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class RepositoryUtil {

    public static PageRequest pageRequest(SortedSet<String> sortBy, boolean ascending, int page, int size) {
        Sort sort = Sort.unsorted();
        if (sortBy != null && sortBy.size() > 0) {
            sort = Sort.by(ascending ? Direction.ASC : Direction.DESC, sortBy.toArray(new String[sortBy.size()]));
        }

        return PageRequest.of(page, size, sort);
    }
}
