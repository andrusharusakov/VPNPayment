package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.andrusha.vpnpayment.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
