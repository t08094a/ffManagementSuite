package de.leif.ffmanagementsuite.repository;

import de.leif.ffmanagementsuite.domain.Person;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
@JaversSpringDataAuditable
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select distinct person from Person person left join fetch person.fuehrerscheines left join fetch person.verfuegbarkeitens")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.fuehrerscheines left join fetch person.verfuegbarkeitens where person.id =:id")
    Person findOneWithEagerRelationships(@Param("id") Long id);

}
