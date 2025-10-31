package com.Jet_Fans.web.repository;

import com.Jet_Fans.web.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>{

    public Admin findByEmail(String email);

    public Admin findByEmailAndPassword(String email, String password);
}
