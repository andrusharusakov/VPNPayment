package me.andrusha.vpnpayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import me.andrusha.vpnpayment.model.payment.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
        List<Payment> findByStatus(String status);
        @Query("SELECT COALESCE(MAX(p.shortId), 0) FROM Payment p")
        Long findMaxShortId();
}
