package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.company.brewcraft.model.MixtureRecording;

public interface MixtureRecordingRepository extends JpaRepository<MixtureRecording, Long>, JpaSpecificationExecutor<MixtureRecording>, ExtendedRepository<Long> {

    @Override
    @Query("select count(i) > 0 from MIXTURE_RECORDING i where i.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from MIXTURE_RECORDING i where i.id in (:ids)")
    long deleteByIds(Iterable<Long> ids);

}
