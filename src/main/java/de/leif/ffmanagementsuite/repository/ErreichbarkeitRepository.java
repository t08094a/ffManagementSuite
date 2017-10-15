package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Erreichbarkeit;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Erreichbarkeit entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface ErreichbarkeitRepository extends JpaRepository<Erreichbarkeit, Long> {

}
