package com.everis.salamanca.repository;
import com.everis.salamanca.domain.Paso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Paso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PasoRepository extends JpaRepository<Paso, Long> {

}
