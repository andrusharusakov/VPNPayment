package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.andrusha.vpnpayment.model.shop.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
