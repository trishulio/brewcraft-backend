package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.FacilityEntity;

@SuppressWarnings("unchecked")
public class EquipmentRepositoryGetAllEquipmentSpecificationTest {

    private EquipmentRepositoryGetAllEquipmentSpecification specification;

    @Test
    public void testToPredicate_ReturnsCriteriaBuilderWithAllPredicates() {
        specification = spy(new EquipmentRepositoryGetAllEquipmentSpecification());

        Root<EquipmentEntity> mRoot = mock(Root.class);
        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);

        Predicate mPredicateWithPredicates = mock(Predicate.class);
        Predicate[] mPredicates = { mock(Predicate.class) };

        doReturn(mPredicates).when(specification).getPredicates(mRoot, mQuery, mBuilder);
        doReturn(mPredicateWithPredicates).when(mBuilder).and(mPredicates);

        Predicate p = specification.toPredicate(mRoot, mQuery, mBuilder);

        assertSame(mPredicateWithPredicates, p);
    }

    @Test
    public void testGetPredicates_AddsInIdsClauseToQuery_WhenIdsAreNotNull() {
        Set<Long> ids = Set.of(1L);
        
        Path<Long> mPath = mock(Path.class);
        Predicate mInPredicate = mock(Predicate.class);
        doReturn(mInPredicate).when(mPath).in(ids);

        Root<EquipmentEntity> mRoot = mock(Root.class);
        doReturn(mPath).when(mRoot).get("id");

        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        
        doReturn(mInPredicate).when(mBuilder).and(mInPredicate);

        specification = new EquipmentRepositoryGetAllEquipmentSpecification(ids, null, null, null);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mInPredicate, predicates[0]);

        verify(mRoot, times(1)).get("id");
        verify(mPath, times(1)).in(ids);
        verify(mBuilder, times(1)).and(mInPredicate); 
    }
    
    @Test
    public void testGetPredicates_AddsInTypesClauseToQuery_WhenTypesAreNotNull() {
        Set<String> types = Set.of(EquipmentType.BARREL.name());
        
        Path<Long> mPath = mock(Path.class);
        Predicate mInPredicate = mock(Predicate.class);
        doReturn(mInPredicate).when(mPath).in(types);

        Root<EquipmentEntity> mRoot = mock(Root.class);
        doReturn(mPath).when(mRoot).get("type");

        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        
        doReturn(mInPredicate).when(mBuilder).and(mInPredicate);

        specification = new EquipmentRepositoryGetAllEquipmentSpecification(null, types, null, null);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mInPredicate, predicates[0]);

        verify(mRoot, times(1)).get("type");
        verify(mPath, times(1)).in(types);
        verify(mBuilder, times(1)).and(mInPredicate); 
    }
    
    @Test
    public void testGetPredicates_AddsInStatusesClauseToQuery_WhenStatusesAreNotNull() {
        Set<String> statuses = Set.of(EquipmentStatus.ACTIVE.name());
        
        Path<Long> mPath = mock(Path.class);
        Predicate mInPredicate = mock(Predicate.class);
        doReturn(mInPredicate).when(mPath).in(statuses);

        Root<EquipmentEntity> mRoot = mock(Root.class);
        doReturn(mPath).when(mRoot).get("status");

        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        
        doReturn(mInPredicate).when(mBuilder).and(mInPredicate);

        specification = new EquipmentRepositoryGetAllEquipmentSpecification(null, null, statuses, null);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mInPredicate, predicates[0]);

        verify(mRoot, times(1)).get("status");
        verify(mPath, times(1)).in(statuses);
        verify(mBuilder, times(1)).and(mInPredicate); 
    }
    
    @Test
    public void testGetPredicates_AddsInFacilityIdsClauseToQuery_WhenFacilityIdsAreNotNull() {
        Set<Long> facilityIds = Set.of(1L);
        
        Path<Long> mPath = mock(Path.class);
        Predicate mInPredicate = mock(Predicate.class);
        doReturn(mInPredicate).when(mPath).in(facilityIds);
 
        Path<FacilityEntity> mPathFacility = mock(Path.class);
        doReturn(mPath).when(mPathFacility).get("id");

        Root<EquipmentEntity> mRoot = mock(Root.class);
        doReturn(mPathFacility).when(mRoot).get("facility");

        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        
        doReturn(mInPredicate).when(mBuilder).and(mInPredicate);

        specification = new EquipmentRepositoryGetAllEquipmentSpecification(null, null, null, facilityIds);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(1, predicates.length);
        assertSame(mInPredicate, predicates[0]);

        verify(mRoot, times(1)).get("facility");
        verify(mPathFacility, times(1)).get("id");
        verify(mPath, times(1)).in(facilityIds);
        verify(mBuilder, times(1)).and(mInPredicate); 
    }

    @Test
    public void testGetPredicates_AddsAllPredicates_WhenNoArgIsNull() {
        Set<Long> ids = Set.of(1L);
        Set<String> types = Set.of(EquipmentType.BARREL.name());
        Set<String> statuses = Set.of(EquipmentStatus.ACTIVE.name());
        Set<Long> facilityIds = Set.of(1L);

        // Ids Criteria Mock
        Path<Long> mIdsPath = mock(Path.class);
        Predicate mInIdsPredicate = mock(Predicate.class);
        doReturn(mInIdsPredicate).when(mIdsPath).in(ids);

        // Types Criteria Mock
        Path<String> mTypesPath = mock(Path.class);
        Predicate mInTypePredicate = mock(Predicate.class);
        doReturn(mInTypePredicate).when(mTypesPath).in(types);

        // Statuses Criteria Mock
        Path<String> mStatusesPath = mock(Path.class);
        Predicate mInStatusPredicate = mock(Predicate.class);
        doReturn(mInStatusPredicate).when(mStatusesPath).in(statuses);

        // FacilityIds Criteria Mock
        Path<Long> mFacilityIdsPath = mock(Path.class);
        Path<FacilityEntity> mFacilityPath = mock(Path.class);
        Predicate mInFacilityIdPredicate = mock(Predicate.class);
        doReturn(mInFacilityIdPredicate).when(mFacilityIdsPath).in(facilityIds);

        Root<EquipmentEntity> mRoot = mock(Root.class);
        doReturn(mIdsPath).when(mRoot).get("id");
        doReturn(mTypesPath).when(mRoot).get("type");
        doReturn(mStatusesPath).when(mRoot).get("status");
        doReturn(mFacilityPath).when(mRoot).get("facility");
        doReturn(mFacilityIdsPath).when(mFacilityPath).get("id");

        CriteriaQuery<EquipmentEntity> mQuery = mock(CriteriaQuery.class);
        doReturn(mQuery).when(mQuery).select(mRoot);

        CriteriaBuilder mBuilder = mock(CriteriaBuilder.class);
        
        doReturn(mInIdsPredicate).when(mBuilder).and(mInIdsPredicate);
        doReturn(mInTypePredicate).when(mBuilder).and(mInTypePredicate);
        doReturn(mInStatusPredicate).when(mBuilder).and(mInStatusPredicate);
        doReturn(mInFacilityIdPredicate).when(mBuilder).and(mInFacilityIdPredicate);

        specification = new EquipmentRepositoryGetAllEquipmentSpecification(ids, types, statuses, facilityIds);
        Predicate[] predicates = specification.getPredicates(mRoot, mQuery, mBuilder);

        assertSame(4, predicates.length);
        assertSame(mInIdsPredicate, predicates[0]);
        assertSame(mInTypePredicate, predicates[1]);
        assertSame(mInStatusPredicate, predicates[2]);
        assertSame(mInFacilityIdPredicate, predicates[3]);
    }
}
