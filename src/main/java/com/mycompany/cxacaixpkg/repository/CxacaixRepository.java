package com.mycompany.cxacaixpkg.repository;

import com.mycompany.cxacaixpkg.domain.Cxacaix;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cxacaix entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CxacaixRepository extends JpaRepository<Cxacaix, Long> {}
