package com.backend_casting.config;

import com.backend_casting.dto.FormularioFilterDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class FormularioFilterConfig {

    @Bean
    public FormularioFilterDTO formularioFilterDTO() {
        return new FormularioFilterDTO();
    }

}
