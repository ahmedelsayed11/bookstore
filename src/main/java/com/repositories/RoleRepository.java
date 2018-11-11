package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.domains.security.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {

	 Role findByName(String name);
}
