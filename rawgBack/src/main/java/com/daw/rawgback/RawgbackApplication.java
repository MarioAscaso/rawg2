package com.daw.rawgback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RawgbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RawgbackApplication.class, args);
    }

}

//Esos "servicios" (RawgService, UserService) son en realidad implementaciones técnicas de interfaces de tu dominio.
// Al contener código que toca HTTP o JPA directamente, están obligados a vivir en la capa de Infraestructura para mantener
// tu Dominio y tu Aplicación limpios de tecnología.
