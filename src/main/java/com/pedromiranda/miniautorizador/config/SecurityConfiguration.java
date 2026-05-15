package com.pedromiranda.miniautorizador.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends VaadinWebSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/cartoes/**").permitAll()
                .antMatchers("/transacoes/**").permitAll()
                .antMatchers("/painel/**").permitAll()
                .antMatchers("/VAADIN/**", "/frontend/**", "/webjars/**").permitAll();

        http.headers().frameOptions().disable();

        // os endpoints internos se as anotações nas views estiverem corretas.
        super.configure(http);
    }
}