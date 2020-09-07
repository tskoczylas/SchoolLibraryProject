package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Role;
import com.tomsapp.Toms.V2.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Role role) {

        roleRepository.save(role);

    }

    @Override
    public Role getById(int id) {
        return roleRepository.findById(id).get();
    }
}
