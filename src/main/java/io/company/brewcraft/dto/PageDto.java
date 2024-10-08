package io.company.brewcraft.dto;

import java.util.Iterator;
import java.util.List;

public class PageDto<T extends BaseDto> extends BaseDto implements Iterable<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;

    public PageDto() {
        this(null, 0, 0);
    }

    public PageDto(List<T> content, int totalPages, long totalElements) {
        setContent(content);
        setTotalElements(totalElements);
        setTotalPages(totalPages);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }
}
