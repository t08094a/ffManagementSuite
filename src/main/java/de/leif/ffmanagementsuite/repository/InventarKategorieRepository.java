package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.InventarKategorie;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InventarKategorie entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface InventarKategorieRepository extends JpaRepository<InventarKategorie, Long> {

}
