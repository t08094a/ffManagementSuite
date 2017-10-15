package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Email;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface EmailRepository extends JpaRepository<Email, Long> {

}
