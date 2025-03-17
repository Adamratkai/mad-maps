package com.codecool.madmaps.repository;

import com.codecool.madmaps.model.Role.Role;
import com.codecool.madmaps.model.Role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
