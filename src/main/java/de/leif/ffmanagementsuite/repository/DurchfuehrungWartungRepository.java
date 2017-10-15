package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.DurchfuehrungWartung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DurchfuehrungWartung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface DurchfuehrungWartungRepository extends JpaRepository<DurchfuehrungWartung, Long> {

}
