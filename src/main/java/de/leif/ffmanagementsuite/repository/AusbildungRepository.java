package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Ausbildung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ausbildung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface AusbildungRepository extends JpaRepository<Ausbildung, Long> {

}
