package com.itv.mobilestore.dtos;

//import java.time.Instant;
//import org.springframework.data.annotation.LastModifiedDate;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//@VerifyPassword(Filed = "password", matchingFiled = "confirmpassword")
public class RegistorMobDto {
    private Integer id;
    @Size(min = 4,max = 10,message = "First name length should have min 4 max 10 char")
    //@NotNull@NotEmpty
    private String Mobname;
    //@NotNull@NotEmpty
    private String lastname;
    //@NotNull@NotEmpty
    private String color;
    
}