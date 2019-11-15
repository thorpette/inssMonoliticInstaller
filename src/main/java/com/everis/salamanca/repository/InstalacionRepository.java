package com.everis.salamanca.repository;
import com.everis.salamanca.domain.Instalacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Instalacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstalacionRepository extends JpaRepository<Instalacion, Long> {

}
