package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Schutzausruestung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Schutzausruestung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface SchutzausruestungRepository extends JpaRepository<Schutzausruestung, Long> {

}
