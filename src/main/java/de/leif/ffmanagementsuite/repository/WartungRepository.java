package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Wartung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Wartung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface WartungRepository extends JpaRepository<Wartung, Long> {

}
