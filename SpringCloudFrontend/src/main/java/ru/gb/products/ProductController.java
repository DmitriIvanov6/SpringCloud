package ru.gb.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    private final String serverUrl = "http://backend/shop/products";


    @GetMapping
    public String getAllProducts(Model model) {
        Product[] products =  restTemplate.getForObject(serverUrl, Product[].class);
        Product newProduct = new Product();
        model.addAttribute("products", products);
        model.addAttribute("newProduct", newProduct);
        return "/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(long id) {
        restTemplate.delete(serverUrl + "/delete/" + id);

        return "redirect:http://localhost:8080/shop/products";
    }

    @PostMapping()
    public String addProduct(@ModelAttribute ("newProduct") Product newProduct) {
        restTemplate.postForLocation(  serverUrl +"/add", newProduct);
        return "redirect:http://localhost:8080/shop/products";
    }





}
