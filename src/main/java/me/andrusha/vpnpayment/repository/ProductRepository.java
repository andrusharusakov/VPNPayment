package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import me.andrusha.vpnpayment.model.shop.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p ORDER BY p.orderNum ASC")
    List<Product> findAllSortedByOrder();
    List<Product> findByCategoryId(Long categoryId);
}
