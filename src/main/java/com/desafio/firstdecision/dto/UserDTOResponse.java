package com.desafio.firstdecision.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTOResponse {

    private Long id;

    private String name;

    private String email;

}
