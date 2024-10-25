package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.shop.Product;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DurationService durationService;
    @Autowired
    CategoryService categoryService;

    public List<Product> getProducts() {
        return productRepository.findAllSortedByOrder();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product){ productRepository.save(product);}
    public void deleteProduct(Product product){
        productRepository.deleteById(product.getId());
    }

    public void patchProduct(Product product){
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow();
        if(product.getName() != null){
            existingProduct.setName(product.getName());
        }
        if(product.getOrderNum() > 0 ){
            existingProduct.setOrderNum(product.getOrderNum());
        }
        if(product.getAbout() != null){
            existingProduct.setAbout(product.getAbout());
        }
        if(product.getPrice() != 0f){
            existingProduct.setPrice(product.getPrice());
        }
        if(product.getSale() >= 0f){
            existingProduct.setSale(product.getSale());
        }
        if(product.getDuration()!= null){
            existingProduct.setDuration(durationService.findDuration(product.getDuration().getId()));
        }
        if(product.getCategory()!= null){
            existingProduct.setCategory(categoryService.findCategory(product.getCategory().getId()));
        }
        if(product.getImageUrl() != null){
            existingProduct.setImageUrl(product.getImageUrl());
        }
        if(product.getcanApplyPromoCode() != null) {
            existingProduct.setcanApplyPromoCode(product.getcanApplyPromoCode());
        }
        productRepository.save(existingProduct);
    }

}
