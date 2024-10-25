package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.user.Role;
import me.andrusha.vpnpayment.model.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService() {
    }

    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null || roleService.findById(1L) == null) {
            return false;
        }
        user.setRoles(Collections.singleton(roleService.findById(1L)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /**
     * Метод для обновления роли пользователя.
     * @param unknownUser объект пользователя
     * @param role_id идентификатор роли
     */
    public void updateUserRole(User unknownUser, Long role_id) {
        if (roleService.findById(role_id) != null) {
            User user = userRepository.findByUsername(unknownUser.getUsername());
            Role role = roleService.findById(role_id);
            Set<Role> userRoles = user.getRoles();
            if (userRoles != null) {
                userRoles.clear();
            } else {
                userRoles = new HashSet<>();
            }
            userRoles.add(role);
            user.setRoles(userRoles);
            userRepository.save(user);
        }
    }

    public void deleteUser(User user){
        userRepository.deleteById(user.getId());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
