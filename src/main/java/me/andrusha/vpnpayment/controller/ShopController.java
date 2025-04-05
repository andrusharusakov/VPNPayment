package me.andrusha.vpnpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import me.andrusha.vpnpayment.model.shop.Category;
import me.andrusha.vpnpayment.model.shop.Duration;
import me.andrusha.vpnpayment.model.shop.Product;
import me.andrusha.vpnpayment.service.CategoryService;
import me.andrusha.vpnpayment.service.DurationService;
import me.andrusha.vpnpayment.service.PromocodeService;
import me.andrusha.vpnpayment.service.ShopService;

import java.util.List;

@RestController()
@CrossOrigin(origins = {"http://localhost:3000", "https://leafcity.vercel.app", "https://leafcity.ru"})
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private DurationService durationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PromocodeService promocodeService;

    @GetMapping("/products")
    @ResponseBody
    public List<Product> getProducts() {
        return shopService.getProducts();
    }

//    @GetMapping("/products/category/{categoryId}")
//    @ResponseBody
//    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
//        return shopService.getProductsByCategoryId(categoryId);
//    }

    @GetMapping("/product")
    @ResponseBody
    public Product getProduct(@RequestParam Long id) {
        return shopService.getProductById(id);
    }

    @GetMapping("/durations")
    @ResponseBody
    public List<Duration> getDurations() {
        return durationService.findAll();
    }

//    @GetMapping("/categories")
//    @ResponseBody
//    public List<Category> getCategories() {
//        return categoryService.findAll();
//    }
//}
