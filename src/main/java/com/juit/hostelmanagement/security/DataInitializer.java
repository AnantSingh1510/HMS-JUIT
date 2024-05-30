package com.juit.hostelmanagement.security;

import com.juit.hostelmanagement.models.Role;
import com.juit.hostelmanagement.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role studentRole = roleRepository.findByName("STUDENT");
        if (studentRole == null) {
            studentRole = new Role();
            studentRole.setName("STUDENT");
            roleRepository.save(studentRole);
        }

        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        Role wardenRole = roleRepository.findByName("WARDEN");
        if (wardenRole == null) {
            wardenRole = new Role();
            wardenRole.setName("WARDEN");
            roleRepository.save(wardenRole);
        }
    }
}