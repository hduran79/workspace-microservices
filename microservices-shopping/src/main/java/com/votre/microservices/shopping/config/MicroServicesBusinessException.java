package com.votre.microservices.shopping.config;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MicroServicesBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Código del error de la excepción de negocio.
	 */
	private final String code;

	/**
     * 
     * @param code
     * @param parameter
     * @param message
     * @param throwable
     */
    public MicroServicesBusinessException(String code, String message, Throwable throwable) {
        super(message, throwable);
        Objects.requireNonNull(code, "The code is required!");
        this.code = code;
    }

	/**
     * Crea un nuevo error de negocio con un código dado.
     * 
     * @param code Código del error de negocio.
     * @param parameter envio de parametros para el mensaje
     * 
     */
    public MicroServicesBusinessException(String code, String message) {
        this(code, message, null);
    }

	/**
     * Crea un nuevo error de negocio con un código dado
     * @param code Código del error de negocio.
     */
    public MicroServicesBusinessException(String code) {
        this(code, null, null);
    }

	/**
     * Crea un nuevo error de negocio con un código dado.
     * 
     * @param code Código del error de negocio.
     * @param throwable Error a encapsular.
     */
    public MicroServicesBusinessException(String code, Throwable throwable) {
        this(code, null, throwable);
    }

}
