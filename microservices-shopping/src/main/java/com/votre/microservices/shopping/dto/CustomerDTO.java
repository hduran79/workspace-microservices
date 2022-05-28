package com.votre.microservices.shopping.dto;

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
    private String numberID;
    private String firstName;	
    private String lastName;
    private String email;
    private String photoUrl;
    private RegionDTO region;
    private String state;

}
