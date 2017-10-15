package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.AtemschutzInventar;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AtemschutzInventar entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface AtemschutzInventarRepository extends JpaRepository<AtemschutzInventar, Long> {

}
