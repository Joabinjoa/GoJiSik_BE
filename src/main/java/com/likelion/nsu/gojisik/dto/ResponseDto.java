package com.likelion.nsu.gojisik.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private ResponseStatus status;
    private String message;
    private List<T> data;
}
