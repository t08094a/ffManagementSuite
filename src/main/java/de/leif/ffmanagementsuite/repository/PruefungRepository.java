package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Pruefung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pruefung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface PruefungRepository extends JpaRepository<Pruefung, Long> {

}
