package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Leistungspruefung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Leistungspruefung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface LeistungspruefungRepository extends JpaRepository<Leistungspruefung, Long> {
    @Query("select distinct leistungspruefung from Leistungspruefung leistungspruefung left join fetch leistungspruefung.teilnehmers")
    List<Leistungspruefung> findAllWithEagerRelationships();

    @Query("select leistungspruefung from Leistungspruefung leistungspruefung left join fetch leistungspruefung.teilnehmers where leistungspruefung.id =:id")
    Leistungspruefung findOneWithEagerRelationships(@Param("id") Long id);

}
