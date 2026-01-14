package com.daw.rawgback.domain.services;

import java.io.IOException;

/**
 * INTERFAZ DEL DOMINIO (PUERTO)
 * -----------------------------
 * Esta interfaz actúa como un "contrato" o "puerto" en nuestra Arquitectura Hexagonal.
 * Define QUÉ funcionalidad necesitamos (buscar usuarios), pero no CÓMO se hace.
 * * Ventaja para el examen:
 * Permite desacoplar el núcleo de la aplicación (Domain) de la implementación concreta (Base de datos).
 * El dominio no sabe si los usuarios vienen de MySQL, MongoDB o un fichero de texto.
 */
public interface InternalUserService {

    /**
     * Busca información de usuarios en el sistema de persistencia.
     * * @param username El nombre del usuario a buscar.
     * @return Un String con la información del usuario encontrado (o mensaje de error).
     * @throws IOException Se declara porque la implementación podría implicar operaciones de entrada/salida
     * (como leer de un archivo o conectar a una BD remota) que pueden fallar.
     */
    String fetchUsersFromDataBase(String username) throws IOException;
}