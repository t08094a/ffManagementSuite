package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Dienstzeit;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dienstzeit entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface DienstzeitRepository extends JpaRepository<Dienstzeit, Long> {

}
