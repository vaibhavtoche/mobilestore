package com.itv.mobilestore.dtos;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistorUserDto {
    private Integer id;
    @Size(min = 4,max = 10,message = "First name length should have min 4 max 10 char")
    @NotNull@NotEmpty
    private String firstname;
    @NotNull@NotEmpty
    private String lastname;
    @NotNull@NotEmpty
    private String email;
    @NotNull@NotEmpty
    private String password;
    @NotNull@NotEmpty
    private String confirmpassword;
    @NotNull@NotEmpty
    
    private String  mobile;
}