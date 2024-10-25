package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.andrusha.vpnpayment.model.shop.Promocode;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
    Promocode findByCode(String code);
}

