package com.iongroup.restraining.config;

import com.iongroup.restraining.dao.UserDAO;
import com.iongroup.restraining.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDAO userDAO;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/registration", "/login*", "/webjars/**", "/css/**", "/js/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/deletemessage**").authenticated()
                                .anyRequest().authenticated()
                )
                .headers((headers) ->
                        headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/chat")
                                .failureUrl("/login?error=true").permitAll()
                )
                .logout((logout) ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login").permitAll()
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChainWebSock(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> {
                            try {
                                csrf
                                        .disable().authorizeHttpRequests((authorize) ->
                                                authorize
                                                        .requestMatchers("/ws/**").permitAll()
                                                        .anyRequest().authenticated());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final UserDetailsService userDetailsService = username -> {
            final UserEntity user = userDAO.findByUsername(username);
            final GrantedAuthority userAuthority = new SimpleGrantedAuthority("USER");
            return new User(user.getUsername(), user.getPassword(), Collections.singletonList(userAuthority));
        };
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}