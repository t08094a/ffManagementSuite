package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Inventar;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inventar entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface InventarRepository extends JpaRepository<Inventar, Long> {

}
