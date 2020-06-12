package com.cvinicius.springcomaws.api;

import com.cvinicius.springcomaws.domain.user.User;
import com.cvinicius.springcomaws.domain.user.UserRepository;
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
@RequestMapping("/users")
public class UserApi {

    @Autowired
    private UserRepository userRepository;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<User>> all(){

        List<User> list = userRepository.findAll();

        return ResponseEntity.ok(list);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id){

       Optional<User> op = userRepository.findById(id);

       return op.map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
}