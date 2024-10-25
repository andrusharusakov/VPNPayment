package me.andrusha.vpnpayment.service;

import me.andrusha.vpnpayment.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.andrusha.vpnpayment.model.shop.Category;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public Category findCategory(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    public void createCategory(Category category){
        categoryRepository.save(category);
    }
    public void deleteCategory(Long category){
        categoryRepository.deleteById(category);
    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
