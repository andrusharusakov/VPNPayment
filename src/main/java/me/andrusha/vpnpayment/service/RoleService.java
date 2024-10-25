package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.user.Role;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String roleDoctor) {
        return roleRepository.findByName(roleDoctor);
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    public void createRole(Role role){
        roleRepository.save(role);
    }
}
