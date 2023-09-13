package com.authentication.lab14.repositories;

import com.authentication.lab14.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepo extends JpaRepository<SiteUser,Long> {

    SiteUser findByuserName(String username);

    SiteUser findByemail(String email);
}
