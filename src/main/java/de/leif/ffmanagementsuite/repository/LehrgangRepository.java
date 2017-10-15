package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Lehrgang;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lehrgang entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface LehrgangRepository extends JpaRepository<Lehrgang, Long> {

}
