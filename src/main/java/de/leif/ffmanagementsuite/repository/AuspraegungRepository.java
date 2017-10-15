package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Auspraegung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Auspraegung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface AuspraegungRepository extends JpaRepository<Auspraegung, Long> {

}
