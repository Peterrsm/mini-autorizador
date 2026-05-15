package com.pedromiranda.miniautorizador.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
        // Desabilita CSRF globalmente para facilitar o uso da API REST via Postman
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/cartoes/**").permitAll()
                .antMatchers("/transacoes").permitAll()
                .antMatchers("/h2-console/**").permitAll();

        // Necessário para o console do H2 funcionar dentro de frames
        http.headers().frameOptions().disable();

        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignora completamente a segurança do Spring/Vaadin para esses paths.
        // Isso evita que o filtro do Vaadin tente tratar a requisição REST.
        web.ignoring().antMatchers(
                "/cartoes/**",
                "/transacoes",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/h2-console/**"
        );
        super.configure(web);
    }
}