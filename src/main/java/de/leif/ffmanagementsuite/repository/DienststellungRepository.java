package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Dienststellung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dienststellung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface DienststellungRepository extends JpaRepository<Dienststellung, Long> {

}
