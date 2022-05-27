package com.votre.microservices.customer.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.votre.microservices.product.entity.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	private Long id;

    @NotEmpty(message = "El número de documento no puede ser vacío")
    @Size( min = 8 , max = 8, message = "El tamaño del número de documento es 8")
    private String numberID;

    @NotEmpty(message = "El nombre no puede ser vacío")
    private String firstName;

    @NotEmpty(message = "El apellido no puede ser vacío")
    private String lastName;

    @NotEmpty(message = "el correo no puede estar vacío")
    @Email(message = "no es un dirección de correo bien formada")
    private String email;

    @Column(name="photo_url")
    private String photoUrl;

    @NotNull(message = "la región no puede ser vacia")
    private Region region;
    
    private String state;

}
