package com.covid.analysis.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassRequest implements Serializable {

    private Long userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}