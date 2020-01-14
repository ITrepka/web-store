package pl.pretkejshop.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(myUserDetailsService);
        dap.setPasswordEncoder(passwordEncoder());

        auth.authenticationProvider(dap);
    }

    private String[] staticResources = {
            "/css/**",
            "/img/**",
            "/js/**",
            "/vendor/**",
            "/scss/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // tymczasowe wylaczenie zabezpieczenia przed csrf
                .authorizeRequests() //autoryzuj wszystkie zadania
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers(staticResources).permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() //generowanie formularza logowania
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout();
    }
}
