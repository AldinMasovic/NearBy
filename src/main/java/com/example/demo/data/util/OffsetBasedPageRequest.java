package com.example.demo.data.util;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

@EqualsAndHashCode
public class OffsetBasedPageRequest implements Pageable, Serializable {

    private final long limit;
    private final long offset;
    private final Sort sort;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   can be {@literal null}.
     */
    public OffsetBasedPageRequest(long offset, long limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */
    public OffsetBasedPageRequest(long offset, long limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    public OffsetBasedPageRequest(long offset, long limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return (int) limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious() ? new OffsetBasedPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }


    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }
}
