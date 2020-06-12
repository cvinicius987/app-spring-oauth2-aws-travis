package com.cvinicius.springcomaws.api;

import com.cvinicius.springcomaws.domain.products.Product;
import com.cvinicius.springcomaws.domain.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductApi {

    @Autowired
    private ProductRepository productRepository;

    @Secured({"ROLE_ADMIN", "ROLE_OPERATOR"})
    @GetMapping
    public ResponseEntity<List<Product>> all(){

        List<Product> list = productRepository.findAll();

        return ResponseEntity.ok(list);
    }

    @Secured({"ROLE_ADMIN", "ROLE_OPERATOR"})
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Integer id){

       Optional<Product> op = productRepository.findById(id);

       return op.map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }
}