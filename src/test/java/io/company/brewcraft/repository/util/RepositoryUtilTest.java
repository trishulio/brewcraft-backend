package io.company.brewcraft.repository.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import io.company.brewcraft.repository.RepositoryUtil;

public class RepositoryUtilTest {
    @Test
    public void testPageRequest_ReturnsPageRequestUnsorted_WhenOrderByIsNull() {
        PageRequest expected = PageRequest.of(1, 10, Sort.unsorted());
        PageRequest req = RepositoryUtil.pageRequest(null, true, 1, 10);

        assertEquals(expected, req);
    }

    @Test
    public void testPageRequest_ReturnsPageRequestUnsorted_WhenOrderByIsEmptyList() {
        PageRequest expected = PageRequest.of(1, 10, Sort.unsorted());
        PageRequest req = RepositoryUtil.pageRequest(new TreeSet<String>(), true, 1, 10);

        assertEquals(expected, req);
    }

    @Test
    public void testPageRequest_ReturnsPageRequestAscending_WhenAscendingArgIsTrue() {
        PageRequest expected = PageRequest.of(1, 10, Sort.by(Direction.ASC, new String[] { "col_1" }));
        PageRequest req = RepositoryUtil.pageRequest(new TreeSet<String>(List.of("col_1")), true, 1, 10);

        assertEquals(expected, req);
    }

    @Test
    public void testPageRequest_ReturnsPageRequestDescending_WhenAscendingArgIsFalse() {
        PageRequest expected = PageRequest.of(1, 10, Sort.by(Direction.DESC, new String[] { "col_1" }));
        PageRequest req = RepositoryUtil.pageRequest(new TreeSet<String>(List.of("col_1")), false, 1, 10);

        assertEquals(expected, req);
    }

    @Test
    public void testPageRequest_ReturnsPageRequestWithAllColumns_WhenOrderByIsNotNull() {
        PageRequest expected = PageRequest.of(1, 10, Sort.by(Direction.DESC, new String[] { "col_1", "col_2" }));
        PageRequest req = RepositoryUtil.pageRequest(new TreeSet<String>(List.of("col_1", "col_2")), false, 1, 10);

        assertEquals(expected, req);
    }
}
