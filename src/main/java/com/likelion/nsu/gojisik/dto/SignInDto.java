package com.likelion.nsu.gojisik.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    private String phonenum;

//   public void phone(String username, String phonenum){
//       username = phonenum;
//   }
}
