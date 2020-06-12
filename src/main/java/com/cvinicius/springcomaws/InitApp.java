package com.cvinicius.springcomaws;

import com.cvinicius.springcomaws.domain.products.Category;
import com.cvinicius.springcomaws.domain.products.Product;
import com.cvinicius.springcomaws.domain.products.ProductRepository;
import com.cvinicius.springcomaws.domain.user.Permission;
import com.cvinicius.springcomaws.domain.user.PermissionRepository;
import com.cvinicius.springcomaws.domain.user.User;
import com.cvinicius.springcomaws.domain.user.UserRepository;
import com.cvinicius.springcomaws.domain.user.auth.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class InitApp implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        generateUsers();

        generateProducts();

        List<User> list = userRepository.findAll();

        for(User u : list){
            log.info(""+u);
        }
    }

    private void generateUsers() {

        if (userRepository.count() <= 0) {

            //salva as permisssões
            Permission admin    = new Permission(Roles.ROLE_ADMIN);
            Permission operator = new Permission(Roles.ROLE_OPERATOR);

            permissionRepository.save(admin);
            permissionRepository.save(operator);

            //Salva os usuarios
            userRepository.save(new User(null,"admin","admin@teste.com.br", passwordEncoder.encode("admin"), Arrays.asList(admin, operator)));
            userRepository.save(new User(null,"operator","operator@teste.com.br", passwordEncoder.encode("operator"), Arrays.asList(operator)));
        }
    }

    private void generateProducts() {

        if (productRepository.count() <= 0) {

            //Salva os produtos
            productRepository.save(new Product("TV", "TV 42 polegadas", Category.TV));
            productRepository.save(new Product("DVD", "DVD de ultima geração", Category.DVD));
            productRepository.save(new Product("Refrigerator", "Refrigerador de 350 litros", Category.REFRIGERATOR));
        }
    }
}
