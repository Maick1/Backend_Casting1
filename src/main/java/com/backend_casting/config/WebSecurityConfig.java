package com.backend_casting.config;

import com.backend_casting.util.DetallesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DetallesUsuarioService detallesUsuarioService;

    // Define los recursos que se pueden acceder sin autenticación
    String[] resources = new String[]{
            "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configuración para evitar el almacenamiento en caché
        http.headers()
                .defaultsDisabled()
                .cacheControl().and()
                .frameOptions().deny()
                .and()
                .authorizeRequests();

        http
                .authorizeRequests()
                .antMatchers("/login", "/menu").authenticated()
                // Permitir acceso a todos los recursos y rutas
                .antMatchers("/**").permitAll()
                .antMatchers(resources).permitAll()
                // Rutas específicas
                .antMatchers("/", "inicio", "/index", "/usuarios/cambiar_contraseña", "/usuarios/restablecer_contraseña", "/usuarios/verificar_respuesta").permitAll()
                // Acceso basado en roles
                .antMatchers("/admin*").hasAuthority("ADMIN")
                .antMatchers("/user*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/generar-pdf/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/menu")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .permitAll()
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/inicio");
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detallesUsuarioService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
