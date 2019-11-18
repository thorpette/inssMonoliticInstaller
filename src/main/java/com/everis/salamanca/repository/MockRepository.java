package com.everis.salamanca.repository;
import com.everis.salamanca.domain.Mock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MockRepository extends JpaRepository<Mock, Long>, JpaSpecificationExecutor<Mock> {

}
