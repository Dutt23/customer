package com.sd.customer.utils;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class SpecificationUtils {

    public static Specification buildSpecificationFromMap(Map<String, String> filters) {
        Specification spec = null;
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (entry.getValue() != null) {
                Specification fieldSpec = SpecificationUtils.hasNestedFieldEqualTo(entry.getKey(), entry.getValue());
                spec = (spec == null) ? fieldSpec : spec.and(fieldSpec);
            }
        }
        return spec;
    }

    public static  Specification hasNestedFieldEqualTo(String nestedField, Object value) {
        return (root, query, cb) -> {
            if (value == null) return null;
            String[] fields = nestedField.split("\\.");
            Path<?> path = root;
            for (String field : fields) {
                path = path.get(field);
            }
            return cb.equal(path, value);
        };
    }
}
