package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Model.SecurityUser;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username,username).orElseThrow(()-> new UsernameNotFoundException("username not found ::"+username));
        return new SecurityUser(user);
    }
}
