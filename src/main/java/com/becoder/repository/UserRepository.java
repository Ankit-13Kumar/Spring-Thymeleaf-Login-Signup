package com.becoder.repository;

import com.becoder.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    //to check user exit , email exist etc

    //boolean true false
    public boolean existsByEmail(String email);

    //Added for userDetailsServiceImpl implementation
    public UserDtls findByEmail(String email);

}