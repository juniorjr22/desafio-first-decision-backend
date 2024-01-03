package com.desafio.firstdecision.dto;

import com.desafio.firstdecision.exception.PasswordException;
import com.desafio.firstdecision.util.ConstantUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTORequest {

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3, max = 50, message = "Name field must be between 3 and 50 characters.")
    private String name;

    @NotNull(message = "E-mail cannot be null.")
    @Email(message = "Invalid email.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 6, max = 20, message = "Password field must be between 6 and 20 characters.")
    private String password;

    @NotNull(message = "Password Confirmation cannot be null.")
    @Size(min = 6, max = 20, message = "Password Confirmation field must be between 6 and 20 characters.")
    private String passwordConfirmation;

    public void validatePassword() {
        if (!password.equals(passwordConfirmation)) {
            throw new PasswordException(ConstantUtil.MSG_INVALID_PASSWORD);
        }

    }

}
