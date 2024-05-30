package com.juit.hostelmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/register", "/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/warden/**").hasAnyRole("ADMIN", "WARDEN")
                .requestMatchers("/manage/**").hasAnyRole("ADMIN", "WARDEN")
                .requestMatchers("/student/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/register").permitAll() // Allowing registration endpoint
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomAuthenticationSuccessHandler()) // Use custom success handler
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403"); // Page to show when access is denied

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN").build());
        manager.createUser(User.withUsername("warden").password(passwordEncoder().encode("wardenPass")).roles("WARDEN").build());
        manager.createUser(User.withUsername("231030231").password(passwordEncoder().encode("studentPass")).roles("STUDENT").build());
        return manager;
    }
}