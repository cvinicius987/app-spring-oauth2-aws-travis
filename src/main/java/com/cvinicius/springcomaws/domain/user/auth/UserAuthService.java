package com.cvinicius.springcomaws.domain.user.auth;

import com.cvinicius.springcomaws.domain.user.User;
import com.cvinicius.springcomaws.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s)
            throws UsernameNotFoundException {

        Optional<User> opUser = userRepository.findByEmail(s);

        return opUser.map(user -> new UserAuth(user))
                     .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));
    }
}
