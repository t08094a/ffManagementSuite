package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Berichtselement;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Berichtselement entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface BerichtselementRepository extends JpaRepository<Berichtselement, Long> {

}
