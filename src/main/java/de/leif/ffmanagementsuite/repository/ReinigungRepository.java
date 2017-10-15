package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Reinigung;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reinigung entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface ReinigungRepository extends JpaRepository<Reinigung, Long> {

}
