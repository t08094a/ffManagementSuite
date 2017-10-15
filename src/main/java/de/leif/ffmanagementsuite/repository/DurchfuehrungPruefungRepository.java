package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.DurchfuehrungPruefung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DurchfuehrungPruefung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface DurchfuehrungPruefungRepository extends JpaRepository<DurchfuehrungPruefung, Long> {

}
