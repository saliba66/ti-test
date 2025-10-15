package com.unipark.vaga;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VagaDataLoader {

    @Bean
    CommandLineRunner initVagas(VagaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                for (int i = 1; i <= 10; i++) {
                    Vaga v = new Vaga();
                    v.setNumero(String.valueOf(i));
                    v.setTipo(i % 5 == 0 ? "PCD" : (i % 4 == 0 ? "FUNCIONARIO" : "COMUM"));
                    v.setStatus("LIVRE");
                    repository.save(v);
                }
            }
        };
    }
}
