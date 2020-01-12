package pl.pretkejshop.webstore.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        String role = user.getRole().getName();

        return org.springframework.security.core.userdetails.User.builder()
        .username(username)
        .password(user.getPassword())
        .roles(role)
        .build();
    }
}
