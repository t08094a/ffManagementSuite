package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Verfuegbarkeit;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Verfuegbarkeit entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface VerfuegbarkeitRepository extends JpaRepository<Verfuegbarkeit, Long> {

}
