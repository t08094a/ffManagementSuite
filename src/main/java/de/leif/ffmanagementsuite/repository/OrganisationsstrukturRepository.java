package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Organisationsstruktur;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Organisationsstruktur entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface OrganisationsstrukturRepository extends JpaRepository<Organisationsstruktur, Long> {

}
