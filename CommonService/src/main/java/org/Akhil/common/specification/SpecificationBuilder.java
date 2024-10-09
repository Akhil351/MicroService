package org.Akhil.common.specification;

import org.Akhil.common.util.Utils;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder<T> {
    public Specification<T> contains(String columnName, String expression) {
        return (root, query, builder) -> builder.like(root.get(columnName), Utils.contains(expression));
    }
}
