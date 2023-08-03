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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Define los recursos que se pueden acceder sin autenticación
    String[] resources = new String[]{
            "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .defaultsDisabled()
                .cacheControl().and()
                .frameOptions().deny()
                .and()
                .authorizeRequests()
                .antMatchers(resources).permitAll()  // Permite el acceso a los recursos definidos
                .antMatchers("/", "/index", "/usuarios/cambiar_contraseña", "/usuarios/restablecer_contraseña", "/usuarios/verificar_respuesta").permitAll()  // Permitir el acceso a la raíz, la ruta "/index", la ruta de restablecimiento de contraseña y la ruta de verificación de la respuesta de seguridad
                .antMatchers("/admin*").hasAuthority("ADMIN")  // Aquí puedes definir el acceso basado en roles
                .antMatchers("/user*").hasAnyAuthority("USER", "ADMIN")  // Aquí puedes definir el acceso basado en roles
                .antMatchers("/generar-pdf/**").access("hasRole('USER')")
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otra solicitud
                .and()
                .formLogin()
                .loginPage("/login")  // Establece la URL de la página de inicio de sesión personalizada
                .permitAll()  // Permite el acceso a la página de inicio de sesión sin autenticación
                .defaultSuccessUrl("/menu")  // Redirige a "/menu" después de un inicio de sesión exitoso
                .failureUrl("/login?error=true")  // Redirige a "/login?error=true" después de un inicio de sesión fallido
                .usernameParameter("username")  // Establece el nombre del parámetro de formulario para el campo de nombre de usuario
                .passwordParameter("password")  // Establece el nombre del parámetro de formulario para el campo de contraseña
                .and()
                .logout()
                .permitAll()  // Permite el acceso a la URL de cierre de sesión sin autenticación
                .logoutSuccessUrl("/login?logout");  // Redirige a "/login?logout" después de un cierre de sesión exitoso
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detallesUsuarioService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
