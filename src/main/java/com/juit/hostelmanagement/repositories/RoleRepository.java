package com.juit.hostelmanagement.repositories;

import com.juit.hostelmanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}