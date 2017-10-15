package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Fuehrerschein;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fuehrerschein entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface FuehrerscheinRepository extends JpaRepository<Fuehrerschein, Long> {

}
