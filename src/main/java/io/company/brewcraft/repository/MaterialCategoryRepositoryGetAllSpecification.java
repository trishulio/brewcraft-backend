//package io.company.brewcraft.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import io.company.brewcraft.model.MaterialCategoryEntity;
//import io.company.brewcraft.util.entity.ReflectionManipulator;
//
//public class MaterialCategoryRepositoryGetAllSpecification implements Specification<MaterialCategoryEntity> {
//    private static final long serialVersionUID = 6226156798761769364L;
//   
//    private Set<Long> ids;
//    private Set<String> names;
//    private Set<Long> parentCategoryIds;
//    private Set<String> parentNames;
//    private boolean includeDescendants;
//
//    public MaterialCategoryRepositoryGetAllSpecification() {
//        this(null, null, null, null, false);
//    }
//
//    public MaterialCategoryRepositoryGetAllSpecification(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames, boolean includeDescendants) {
//        this.ids = ids;
//        this.names = names;
//        this.parentCategoryIds = parentCategoryIds;
//        this.parentNames = parentNames;
//        this.includeDescendants = includeDescendants;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<MaterialCategoryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        @SuppressWarnings("unchecked")
//        CriteriaQuery<MaterialCategoryEntity> materialCateogryQuery = (CriteriaQuery<MaterialCategoryEntity>) query;
//        return criteriaBuilder.and(this.getPredicates(root, materialCateogryQuery, criteriaBuilder).g);
//    }
//
//    public Predicate[] getPredicates(Root<MaterialCategoryEntity> root, CriteriaQuery<MaterialCategoryEntity> query, CriteriaBuilder criteriaBuilder) {
//        List<Predicate> predicates = new ArrayList<Predicate>(10);
//        
//        if (ids != null && ids.size() > 0) {
//            if (includeDescendants) {
//                predicates.add(criteriaBuilder.or(root.get("id").in(ids)));
//                predicates.add(criteriaBuilder.or(root.get("parentCategory").get("id").in(ids)));
//            } else {
//                predicates.add(criteriaBuilder.and(root.get("id").in(ids)));
//            }
//        }
//        
//        if (names != null && names.size() > 0) {
//            if (includeDescendants) {
//                predicates.add(criteriaBuilder.or(root.get("name").in(names)));
//                predicates.add(criteriaBuilder.or(root.get("parentCategory").get("name").in(names)));
//            } else {
//                predicates.add(criteriaBuilder.and(root.get("name").in(names)));
//            }
//        }
//
//        if (parentCategoryIds != null && parentCategoryIds.size() > 0) {
//            predicates.add(criteriaBuilder.and(root.get("parentCategory").get("id").in(parentCategoryIds)));
//        }
//        
//        if (parentNames != null && parentNames.size() > 0) {
//            predicates.add(criteriaBuilder.and(root.get("parentCategory").get("name").in(names)));
//        }
//
//        return predicates.toArray(new Predicate[predicates.size()]);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        return ReflectionManipulator.INSTANCE.equals(this, o);
//    }
//
//}
