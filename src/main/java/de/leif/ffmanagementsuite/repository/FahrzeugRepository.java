package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Fahrzeug;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fahrzeug entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface FahrzeugRepository extends JpaRepository<Fahrzeug, Long> {

}
