package com.Jet_Fans.web.service;

import com.Jet_Fans.web.entity.Admin;
import com.Jet_Fans.web.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public List<Admin> getAll() {
        return adminRepo.findAll();
    }

    public Admin getById(Long id) {
        return adminRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin with id " + id + "not found."));
    }

    public Admin getByEmailAndPassword(String email, String password) {
        return adminRepo.findByEmailAndPassword(email, password);
    }

    public void createAdmin(Admin admin) {
        adminRepo.save(admin);
    }

    public void updateAdmin(Admin admin) {
        Admin adminFromDb = adminRepo.findById(admin.getId()).orElseThrow(() -> new RuntimeException("Admin with id " + admin.getId() + "not found."));

        if (admin.getName() != null) {
            adminFromDb.setName(admin.getName());
        }

        if (admin.getEmail() != null) {
            adminFromDb.setEmail(admin.getEmail());
        }

        if (admin.getPassword() != null) {
            adminFromDb.setPassword(admin.getPassword());
        }

        adminRepo.save(adminFromDb);
    }

    public void deleteById(Long id) {
        adminRepo.deleteById(id);
    }

    public boolean verifyAdmin(String email, String password) {
        Admin admin = adminRepo.findByEmail(email);

        if (admin == null) {
            return false;
        }

        return admin.getPassword().equals(password);
    }
}
