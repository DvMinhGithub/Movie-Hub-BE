package com.moviehub.moviehub.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String code;
    private List<String> details;
    private String field;

    public static ErrorResponse of(String code, List<String> details) {
        return ErrorResponse.builder().code(code).details(details).build();
    }

    public static ErrorResponse of(String code, String detail) {
        return ErrorResponse.builder().code(code).details(List.of(detail)).build();
    }

    public static ErrorResponse of(String code, String detail, String field) {
        return ErrorResponse.builder()
                .code(code)
                .details(List.of(detail))
                .field(field)
                .build();
    }
}
