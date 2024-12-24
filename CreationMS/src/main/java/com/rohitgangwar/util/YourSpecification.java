package com.rohitgangwar.util;

import com.rohitgangwar.entity.Pin;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YourSpecification {

    public static Specification<Pin> buildSpecification(Map<String, String> params) {
        return (Root<Pin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if ("title".equals(entry.getKey())) {
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + entry.getValue() + "%"));
                } else if ("boardId".equals(entry.getKey())) {
                    predicates.add(criteriaBuilder.equal(root.get("boardId"), Long.parseLong(entry.getValue())));
                } else if ("createdBy".equals(entry.getKey())) {
                    predicates.add(criteriaBuilder.equal(root.get("createdBy"), Long.parseLong(entry.getValue())));
                } else if ("tags".equals(entry.getKey())) {
                    predicates.add(criteriaBuilder.like(root.get("tags"), "%" + entry.getValue() + "%"));
                }
            }

            predicates.add(criteriaBuilder.equal(root.get("privacy"), false)); // Exclude private pins

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

