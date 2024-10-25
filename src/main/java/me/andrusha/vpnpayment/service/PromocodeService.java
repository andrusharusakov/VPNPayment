package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.PromocodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.shop.Promocode;

@Service
public class PromocodeService {
    private final PromocodeRepository promoCodeRepository;

    @Autowired
    public PromocodeService(PromocodeRepository promocodeRepository) {
        this.promoCodeRepository = promocodeRepository;
    }
    public void createPromocode(Promocode promocode){
        promoCodeRepository.save(promocode);
    }

    public float getDiscountByCode(String code) {
        Promocode promoCode = promoCodeRepository.findByCode(code);
        return promoCode != null ? promoCode.getDiscount() : 0.0f; // Если промокод не найден, вернуть 0
    }
}

