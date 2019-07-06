package com.example.bilal.model.dao;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.example.bilal.repository.UserRepositoryConfig;
import com.example.bilal.util.Utils;
import com.example.bilal.validator.UserNameValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@KeySpace(UserRepositoryConfig.USER_MAP)
public class UserDAO implements Serializable {

    private static final long serialVersionUID = -8737449563273936453L;

    @Id
    private String id;

    @UserNameValidation
    @NotNull(message = Utils.USERNAME_REQUIRED_EXCEPTION)
    private String            username;

    @Email(message = Utils.EMAIL_NOT_VALID_EXCEPTION)
    private String            email;

    @NotNull(message = Utils.PASSWORD_REQUIRED_EXCEPTION)
    @Pattern(regexp = Utils.PASSWORD_VALIDATION_REGEX, message = Utils.PASSWORD_NOT_VALID_EXCEPTION)
    private String            password;

    private String            name;

    private String            lastname;

}
