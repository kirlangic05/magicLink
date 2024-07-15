package com.magic3.magic3.repository;

import com.magic3.magic3.model.Usera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Usera,Long> {

    @Query("SELECT u FROM Usera u WHERE u.email = ?1")
    Usera findByEmail(String email);
    Usera findByUsername(String username);
    @Query("SELECT u FROM Usera u WHERE u.token = ?1")
    Usera findByToken(String token);

}
