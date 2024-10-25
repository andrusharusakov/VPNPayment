package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.andrusha.vpnpayment.model.shop.Duration;

public interface DurationRepository extends JpaRepository<Duration, Long> {
}
